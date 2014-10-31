package com.nationsky.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.nationsky.model.VendorDevice;

/**
 * An interface that provides a data management interface to the VendorDevice table.
 */
public interface VendorDeviceDao extends GenericDao<VendorDevice, Long>{

	List<VendorDevice> findDevice(String vendorid, String password);

	List<VendorDevice> findDevicesByUserId(String fromUserId);

}
