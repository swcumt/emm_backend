package com.nationsky.entity;


/**
 * 状态对象
 * 
 * @author LeiPeng
 * 
 */
public class Status {
	/**
	 * 状态码
	 */
	private Integer code = 0;

	/**
	 * 消息
	 */
	private String msg = "";

	public Status() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Status(String msg) {
		super();
		this.msg = msg;
	}

	public Status(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
