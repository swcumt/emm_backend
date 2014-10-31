package com.nationsky.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nationsky.dao.VendorDeviceDao;
import com.nationsky.model.VendorDevice;

@Repository("vendorDeviceDao")
public class VendorDeviceDaoHibernate extends GenericDaoHibernate<VendorDevice, Long> implements VendorDeviceDao{

	public VendorDeviceDaoHibernate() {
		super(VendorDevice.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendorDevice> findDevice(String vendorid, String password) {
		List<VendorDevice> list = getSession().createCriteria(VendorDevice.class).add(Restrictions.eq("vendorid", vendorid)).add(Restrictions.eq("password", password)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendorDevice> findDevicesByUserId(String fromUserId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from VendorDevice c where 1=1 ");
		sql.append("and c.vendorid = '"+fromUserId+"' ");
		Query query = getSession().createQuery(sql.toString());
		List<VendorDevice> list = query.list();
//		List<VendorDevice> list = getSession().createCriteria(VendorDevice.class).add(Restrictions.eq("vendorid", fromUserId)).list();
		return list;
	}

}
