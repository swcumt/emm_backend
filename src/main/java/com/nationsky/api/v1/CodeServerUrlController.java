package com.nationsky.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.CodeServerUrl;
import com.nationsky.service.CodeServerUrlManager;
import com.nationsky.webapp.controller.BaseFormController;
import com.nationsky.webapp.controller.PooledConn;

@Controller
@RequestMapping("/v1/codeServerUrl")
@Scope("prototype")
public class CodeServerUrlController extends BaseFormController {
	@Autowired
	private CodeServerUrlManager codeServerUrlManager = null;

	@Autowired
	private Root root;
	
	/**
	 * 保存
	 * @param codeServerUrl
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Root save(CodeServerUrl codeServerUrl) {
		CodeServerUrl csu = codeServerUrlManager.save(codeServerUrl);
		root.setObject(csu);
		return root;
	}

	/**
	 * 获取所有信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Root getAll() {
		List<CodeServerUrl> csuList = codeServerUrlManager.getAll();
		root.setObject(csuList);
		return root;
	}

	/**
	 * 获取mdm地址信息
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/mdmDbServer")
	public @ResponseBody Root getMdmDbServer() {
		CodeServerUrl csu = codeServerUrlManager.getMdmDbServer();
		root.setObject(csu);
		return root;
	}

	/**
	 * 获取sdk打包地址信息
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/sdkPackingServer")
	public @ResponseBody Root getSdkPackingServer() {
		CodeServerUrl csu = codeServerUrlManager.getSdkPackingServer();
		root.setObject(csu);
		return root;
	}
	
	/**
	 * 保存MDM数据库地址
	 * @param codeServerUrl
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/mdmDbServer")
	public @ResponseBody Root saveMdmDbServer(CodeServerUrl codeServerUrl) {
		CodeServerUrl csu = codeServerUrlManager.saveMdmDbServer(codeServerUrl);
		root.setObject(csu);
		return root;
	}

	/**
	 * 保存SDK打包地址
	 * @param sdkPackingServer
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/sdkPackingServer")
	public @ResponseBody Root saveSdkPackingServer(CodeServerUrl codeServerUrl) {
		CodeServerUrl csu = codeServerUrlManager.saveSdkPackingServer(codeServerUrl);
		root.setObject(csu);
		return root;
	}
}
