package com.nationsky.api.v1;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nationsky.entity.Root;
import com.nationsky.model.P12;
import com.nationsky.service.P12Manager;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.util.Utils;

@Controller
@RequestMapping("/v1/p12")
@Scope("prototype")
public class P12Controller extends BaseFormController {
	@Autowired
	private P12Manager p12Manager = null;

	@Autowired
	private Root root;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Root save(MultipartFile p12File, P12 p12) {
		try {
			// 上传p12文件
			if (p12File != null && !p12File.isEmpty()) {
				String originalFilename = p12File.getOriginalFilename();
				String contentType = originalFilename.substring(originalFilename.lastIndexOf('.'), originalFilename.length());
				String p12Folder = Utils.getPropertiesValue("P12_FILE_PATH");
				String uuid = Utils.getUUID();
				String filePath = p12Folder + uuid + contentType;
				// 上传拷贝文件
				FileUtils.copyInputStreamToFile(p12File.getInputStream(), new File(filePath));
				System.err.println("上传p12成功");
				p12.setFilename(originalFilename);
				p12.setFilepath(filePath);
			}
			// p12信息入库操作
			p12.setCreateTime(new Date());
			root.setObject(p12Manager.save(p12));
		} catch (IOException e) {
			root.setMessage(-2, "上传文件异常");
		}
		return root;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Root getAll() {
		root.setObject(p12Manager.getAll());
		return root;
	}

	/**
	 * 根据AppVersionId获取该App的P12文件列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getByAppId/{appVersionId}")
	public @ResponseBody Root getByAppId(@PathVariable Long appVersionId) {
		List<P12> p12List = p12Manager.getAllByAppId(appVersionId);
		if (p12List != null && p12List.size() > 0) {
			root.setObject(p12Manager.getAllByAppId(appVersionId).get(0));
		}
		return root;
	}

	/**
	 * 根据AppVersionId获取该App的P12文件列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getAllByAppId/{appVersionId}")
	public @ResponseBody Root getAllByAppId(@PathVariable Long appVersionId) {
		root.setObject(p12Manager.getAllByAppId(appVersionId));
		return root;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody Root getById(@PathVariable Long id) {
		root.setObject(p12Manager.get(id));
		return root;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public @ResponseBody Root delete(@PathVariable Long id) {
		p12Manager.remove(id);
		return root;
	}
}
