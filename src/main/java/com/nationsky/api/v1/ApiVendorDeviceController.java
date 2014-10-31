package com.nationsky.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nationsky.entity.Root;
import com.nationsky.model.VendorDevice;
import com.nationsky.service.VendorDeviceManager;
import com.nationsky.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/v1/vendorDevice*")
@Scope("prototype")
public class ApiVendorDeviceController extends BaseFormController{

	private VendorDeviceManager vendorDeviceManager;
	@Autowired
	public void setVendorDeviceManager(VendorDeviceManager vendorDeviceManager) {
		this.vendorDeviceManager = vendorDeviceManager;
	}
	@Autowired
  	private Root root;
	
	/**
	 * 用户添加设备
	 * @param vendorDevice
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public @ResponseBody
	Root add(VendorDevice vendorDevice) {
		try{
			VendorDevice device = vendorDeviceManager.save(vendorDevice);
			if (device == null) {
				root.setMessage(1, "添加失败");
			} else {
				root.setMessage("添加成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
			root.setMessage(1, "添加失败");
		}
		
		return root;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/find")
	public @ResponseBody
	Root isExist(String vendorid,String password) {
		try{
			boolean device = vendorDeviceManager.isExist(vendorid,password);
			if ( device ) {
				root.setMessage("该用户已注册");
			} else {
				root.setMessage(1, "该用户还没注册");
			}
		}catch (Exception e) {
			e.printStackTrace();
			root.setMessage(1, "查询失败");
		}
		
		return root;
	}
	
}
