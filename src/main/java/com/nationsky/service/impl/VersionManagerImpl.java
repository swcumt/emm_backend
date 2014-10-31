package com.nationsky.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationsky.dao.VersionDao;
import com.nationsky.model.Version;
import com.nationsky.service.VersionManager;
import com.nationsky.vo.Page;
import com.nationsky.webapp.util.Utils;

@Service("versionManager")
@WebService(serviceName = "VersionService", endpointInterface = "com.nationsky.service.VersionManager")
public class VersionManagerImpl extends GenericManagerImpl<Version, Long> implements VersionManager {
    VersionDao versionDao;

    @Autowired
    public VersionManagerImpl(VersionDao versionDao) {
        super(versionDao);
        this.versionDao = versionDao;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public Version getMaxVersion() throws ParseException {
		List<Version> versionList = versionDao.getMaxVersion();
		Version version = null;
		if(versionList != null && versionList.size()>0){
			Map map = (Map)versionList.get(0);
			version = new Version();
			version.setContent(map.get("content").toString());
			version.setId(Long.valueOf(map.get("id").toString()));
			version.setUrl(map.get("url").toString());
			version.setVersionId(map.get("versionId").toString());
			version.setCreateTime(Utils.getFormatDate(map.get("createTime").toString(),"yyyy-MM-dd hh:mm:ss"));
		}
		return version;
	}

	public Page getVersionList(String pageSize) {
		return versionDao.getVersionList(pageSize);
	}
}