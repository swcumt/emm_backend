package com.nationsky.adapter;


public class MapEntity {
	private Object key;
	private Object value;

	public MapEntity(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	public MapEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
