package com.nationsky.api.v1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nationsky.entity.Root;
import com.nationsky.model.AppServiceRelation;
import com.nationsky.model.CodeServerUrl;
import com.nationsky.model.Service;
import com.nationsky.service.AppServiceRelationManager;
import com.nationsky.service.CodeServerUrlManager;
import com.nationsky.service.ServiceManager;
import com.nationsky.utils.FileUtils;
import com.nationsky.utils.Http;
import com.nationsky.utils.ZipAnt;
import com.nationsky.vo.Page;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;

@Controller
@RequestMapping("/v1/service")
@Scope("prototype")
public class ServiceController extends BaseFormController {
	@Autowired
	private ServiceManager serviceManager = null;

	@Autowired
	private AppServiceRelationManager appServiceRelationManager;

	@Autowired
	private CodeServerUrlManager codeServerUrlManager;
	
	@Autowired
	private Root root;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Root save(MultipartFile sdkZipFile, MultipartFile iconFile, Service service, HttpServletRequest request) throws IOException {
		// tomcat的Webapps绝对路径
		String tomcatWebappsPath = Utils.getTomcatWebappsPath(request);
		// (当前/静态 )服务器地址
		String serverAddr = Utils.getPropertiesValue("emm_backend_server");
		// 静态资源存放项目名称
		String emm_backend_static = Utils.getPropertiesValue("emm_backend_static");
		// Service的SDK图标存放路径,存放在TOMCAT路径/webapps/emm_backend_static/serviceSDK/ICONS/1.png
		String iconSavePath = File.separatorChar + "serviceSDK" + File.separatorChar + "ICONS" + File.separatorChar;
		// ICON实际路径位置
		String iconRealPath = "";
		// ICON网络地址
		String iconRomotePath = "";
		// 配置文件实际路径位置
		String configFilePath = "";

		// service信息入库,先保存起来,因为后面要使用到service的Id
		if (service == null) {
			root.setError("请填写服务信息");
			return root;
		}
		if (service.getId() == null) {
			service.setCreateTime(new Date());
			service = serviceManager.save(service);
		}
		Long serviceId = service.getId();

		if (sdkZipFile != null && !sdkZipFile.isEmpty()) {
			//判断SDK后缀格式是否正确.ZIP
			String name = sdkZipFile.getOriginalFilename();
			String ext = Utils.getExtName(name);
			if(ext==null || ext.equals("") || !ext.equals("ZIP")){
				root.setMessage(1, "SDK文件格式不符合,请上传.ZIP文件");
				return root;
			}
			// 上传到SDK存放目录
			String SERVICE_SDK_FOLDER_PATH = Utils.getPropertiesValue("SERVICE_SDK_FOLDER_PATH");
//			String sdkFileName = sdkZipFile.getOriginalFilename();
			String sdkFileName = serviceId+".zip";
			
			String sdkFilePath = SERVICE_SDK_FOLDER_PATH + sdkFileName;
			FileUtils.copyInputStreamToFile(sdkZipFile.getInputStream(), new File(sdkFilePath));
			System.err.println("上传SDK成功");

			// 解压文件到临时目录
			String SERVICE_TEMP_FOLDER_PATH = Utils.getPropertiesValue("SERVICE_TEMP_FOLDER_PATH");
			String folderPath = SERVICE_TEMP_FOLDER_PATH;
			String zipFilePath = sdkFilePath;
			ZipAnt.unZip(folderPath, zipFilePath);
			System.err.println("解压SDK成功");

			// 从临时目录拷贝config.json到指定目录
			String SERVICE_CONFIG_FOLDER_PATH = Utils.getPropertiesValue("SERVICE_CONFIG_FOLDER_PATH");
			String SERVICE_CONFIG_FILE_NAME = Utils.getPropertiesValue("SERVICE_CONFIG_FILE_NAME");
			String filePath = SERVICE_TEMP_FOLDER_PATH + SERVICE_CONFIG_FILE_NAME;
			String filePathTo = SERVICE_CONFIG_FOLDER_PATH + serviceId + ".json";
			FileUtils.copyFile(filePath, filePathTo);
			configFilePath = filePathTo;
			System.err.println("拷贝config.json到指定目录成功");

			// 将上传的SDK包发送到打包服务器端
			String SDK_SERVER_CONFIG_ID = Utils.getPropertiesValue("SDK_SERVER_CONFIG_ID");
			CodeServerUrl csu = codeServerUrlManager.get(Long.valueOf(SDK_SERVER_CONFIG_ID));
			String httpUrl = csu.getUrl()+"/upload";
//			String httpUrl = Utils.getPropertiesValue("SERVICE_PACKAGING_SERVER_URL");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("serviceId", serviceId);
			paramMap.put("sdkZipFile", new File(sdkFilePath));
			Http.sendPost(httpUrl, paramMap);
			System.err.println("SDK发送成功");
			
			service.setConfig(configFilePath);
		}

		// 上传ICON图标
		if (iconFile != null && !iconFile.isEmpty()) {
			//判断ICON后缀格式是否正确.ZIP
			String iconExtName = Utils.getExtName(iconFile.getOriginalFilename());
			if(iconExtName==null || iconExtName.equals("")){
				root.setMessage(2, "服务图标格式不符合(JPEG/JPG/PNG/BMP)");
				return root;
			}
			boolean flg = true;
			String imageExtName = "JPEG#JPG#PNG#BMP";
			String extFilterArr[] = imageExtName.split("#");
			for (String e : extFilterArr) {
				if(e.equals(iconExtName)){
					flg = false;
				}
			}
			if(flg){
				root.setMessage(2, "服务图标格式不符合");
				return root;
			}
			
			String fileName = service.getId() + iconExtName;
			iconRealPath = tomcatWebappsPath + emm_backend_static + iconSavePath + fileName;
			iconRomotePath = serverAddr + emm_backend_static + iconSavePath + fileName;
			FileUtils.copyInputStreamToFile(iconFile.getInputStream(), new File(iconRealPath));
			System.err.println("上传ICON成功");
			service.setIcon(iconRomotePath);
		}

		// 入库保存信息
		service.setCreateTime(new Date());
		serviceManager.save(service);
		System.err.println("入库保存成功");

		root.setMessage("保存成功");
		return root;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Root getAll(String pageSize) {
		Page page = serviceManager.getServiceList(pageSize);
		root.setObject(page.getObjList());
		root.setPageCount(page.getCountPage());
		return root;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody
	Root getById(@PathVariable Long id) {
		root.setObject(serviceManager.get(id));
		return root;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody
	Root delete(@PathVariable Long id) {
		serviceManager.remove(id);
		root.setMessage("删除成功");
		return root;
	}

	/**
	 * 根据服务Id获取该服务的配置项(config.json配置文件)
	 * @param response
	 * @param serviceId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getConfig/{serviceId}/{appVersionId}")
	public @ResponseBody
	void getConfig(HttpServletResponse response, @PathVariable Long serviceId, @PathVariable Long appVersionId) {
		response.setContentType("application/json; charset=UTF-8");
		//判断是否填写过配置信息,如果填写过,查询出来显示.
		AppServiceRelation appServiceRelation = appServiceRelationManager.get(appVersionId,serviceId);
		if(appServiceRelation!=null && appServiceRelation.getConfig()!=null && !appServiceRelation.getConfig().equals("")){
			try {
				String json = appServiceRelation.getConfig();
				response.getWriter().write(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//如果没有填写过配置信息,返回配置模板
			String SERVICE_CONFIG_FOLDER_PATH = Utils.getPropertiesValue("SERVICE_CONFIG_FOLDER_PATH");
			SERVICE_CONFIG_FOLDER_PATH += serviceId + ".json";
			String json = FileUtils.readFileByLines(SERVICE_CONFIG_FOLDER_PATH);
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 保存某应用所选的服务的配置项
	 * @param response HttpServletResponse
	 * @param serviceId
	 * @param config
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveConfig")
	public @ResponseBody
	Root saveConfig(HttpServletResponse response, @RequestParam Long appVersionId, @RequestParam Long serviceId, @RequestParam String config) {
		appServiceRelationManager.saveConfig(appVersionId, serviceId, config);
		root.setMessage("保存成功");
		return root;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/packagIng/{appVersionId}")
	public @ResponseBody
	Root packagIng(HttpServletRequest request, HttpServletResponse response, @PathVariable Long appVersionId) {
		String SDK_SERVER_CONFIG_ID = Utils.getPropertiesValue("SDK_SERVER_CONFIG_ID");
		CodeServerUrl csu = codeServerUrlManager.get(Long.valueOf(SDK_SERVER_CONFIG_ID));
		try {
			System.out.println(appVersionId );
			// 获取配置信息并组装Json配置结构并返回
			String configJson = createConfigJsonFile(appVersionId);

			System.out.println("发送URL" + csu.getUrl()+"/config");
			System.out.println("发送JSON" + configJson);
			// 发送HTTP请求告诉打包服务器进行打包.
//			String downloadURL = Http.sendPost(Utils.getPropertiesValue("APP_PACKAGING_URL"), "json=" + configJson);
			String downloadURL = Http.sendPost(csu.getUrl()+"/config", "json=" + configJson);
			//String downloadURL = "http://192.168.6.105:8080/emm_backend_static/test.zip";
			System.out.println("downloadURL:"+downloadURL);

			// 打包完成,返回下载地址
			root.setMessage(downloadURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			root.setError("打包出错");
		}
		return root;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private String createConfigJsonFile(Long appVersionId) {
		// SDK文件保存路径
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		ObjectNode configNode = mapper.createObjectNode();
		Map<String, Object> objMap;
		/*
		 * 获取该appVersionId对应的service所配置config信息
		 */
		try {
			List<AppServiceRelation> appSRList = appServiceRelationManager.getListByAppVersion(appVersionId);
			for (AppServiceRelation appServiceRelation : appSRList) {
				objMap = new HashMap<String, Object>();
				String config = appServiceRelation.getConfig();
				if (config != null && !config.equals("")) {
//					mapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
//					mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
					objMap = mapper.readValue(config, HashMap.class);
				}
				objMap.put("serviceId", appServiceRelation.getService().getId());
				arrayNode.addPOJO(objMap);
			}

			configNode.put("output_name", "NQSDK");
			configNode.put("app_version_id", appVersionId);
			configNode.put("sdk_list", arrayNode);

			rootNode.put("sdk", configNode);

			return mapper.defaultPrettyPrintingWriter().writeValueAsString(rootNode);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 下载文件(输出文件)
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param folderPath 文件所在目录路径
	 * @param fileName 文件名称
	 * @throws Exception Exception
	 */
	public void download(HttpServletRequest request, HttpServletResponse response, String folderPath, String fileName) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String downLoadPath = folderPath + fileName;

		long fileLength = new File(downLoadPath).length();

		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
}
