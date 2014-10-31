package com.nationsky.service;

import java.util.List;

import javax.jws.WebService;

import org.appfuse.service.GenericManager;

import com.nationsky.model.VendorDevice;

@WebService
public interface VendorDeviceManager extends GenericManager<VendorDevice, Long>{

	boolean isExist(String vendorid, String password);

	List<VendorDevice> findDevicesByUserId(String fromUserId);

}
