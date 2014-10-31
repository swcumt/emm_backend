package com.nationsky.vo;

import java.util.ArrayList;
import java.util.List;

public class Page {

	private List objList = new ArrayList();
	
	private Integer countPage;

	public List getObjList() {
		return objList;
	}

	public void setObjList(List objList) {
		this.objList = objList;
	}

	public Integer getCountPage() {
		return countPage;
	}

	public void setCountPage(Integer countPage) {
		this.countPage = countPage;
	}
}
