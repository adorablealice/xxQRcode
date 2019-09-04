package com.bootdo.qrcode.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author adorablexx
 * @email 1992lcg@163.com
 * @date 2019-07-08 16:03:27
 */
public class QrcodeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String qqurl;
	//
	private String wechaturl;
	//
	private String alipayurl;
	//
	private String resulturl;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setQqurl(String qqurl) {
		this.qqurl = qqurl;
	}
	/**
	 * 获取：
	 */
	public String getQqurl() {
		return qqurl;
	}
	/**
	 * 设置：
	 */
	public void setWechaturl(String wechaturl) {
		this.wechaturl = wechaturl;
	}
	/**
	 * 获取：
	 */
	public String getWechaturl() {
		return wechaturl;
	}
	/**
	 * 设置：
	 */
	public void setAlipayurl(String alipayurl) {
		this.alipayurl = alipayurl;
	}
	/**
	 * 获取：
	 */
	public String getAlipayurl() {
		return alipayurl;
	}
	/**
	 * 设置：
	 */
	public void setResulturl(String resulturl) {
		this.resulturl = resulturl;
	}
	/**
	 * 获取：
	 */
	public String getResulturl() {
		return resulturl;
	}
}
