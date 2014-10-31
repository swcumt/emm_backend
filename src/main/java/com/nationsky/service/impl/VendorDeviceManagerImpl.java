package com.nationsky.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.VendorDeviceDao;
import com.nationsky.model.VendorDevice;
import com.nationsky.service.VendorDeviceManager;

@Service("vendorDeviceManager")
@WebService(serviceName = "VendorDeviceService", endpointInterface = "com.nationsky.service.VendorDeviceManager")
public class VendorDeviceManagerImpl extends GenericManagerImpl<VendorDevice, Long> implements VendorDeviceManager {

	private VendorDeviceDao vendorDeviceDao;
	@Autowired
	public VendorDeviceManagerImpl(VendorDeviceDao vendorDeviceDao) {
		super(vendorDeviceDao);
		this.vendorDeviceDao = vendorDeviceDao;
	}

	@Override
	public boolean isExist(String vendorid, String password) {
		List<VendorDevice> list = vendorDeviceDao.findDevice(vendorid,password);
		if(list != null && list.size() > 0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<VendorDevice> findDevicesByUserId(String fromUserId) {
		return vendorDeviceDao.findDevicesByUserId(fromUserId);
	}
	
	
}
