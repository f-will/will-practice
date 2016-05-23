package com.will.framework.pojo;

/**
 * 支付链接请求参数
 * @author will
 * @2015年7月2日
 */
public class PayUrlRequestPojo extends BasePojo {

	private static final long serialVersionUID = 1L;
	private String apiClientNo;		//渠道编号
	private String backUrl;			//成与否返回地址
	private String openId;			//OpenId微信
	private long userId;			//用户ID
	
	public String getApiClientNo() {
		return apiClientNo;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public String getOpenId() {
		return openId;
	}
	public long getUserId() {
		return userId;
	}
	public void setApiClientNo(String apiClientNo) {
		this.apiClientNo = apiClientNo;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
