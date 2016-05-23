package com.will.framework.util.validate;

import com.will.framework.pojo.BasePojo;

/**
 * 阿里巴巴电话检测
 * @author will
 * @2015年3月30日
 */
public class AlibabaPhoneResponse extends BasePojo{

	private static final long serialVersionUID = 1L;
	private Long areaVid;					//区域id
	private String carrier;					//电话类型
	private String catName;					//电话类型\中国移动
	private Long ispVid;					//IP验证
	private String mts;						//电话消息
	private String province;				//省
	private Long telString;					//电话号码
	
	public Long getAreaVid() {
		return areaVid;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getCatName() {
		return catName;
	}
	public Long getIspVid() {
		return ispVid;
	}
	public String getMts() {
		return mts;
	}
	public String getProvince() {
		return province;
	}
	public Long getTelString() {
		return telString;
	}
	public final boolean isSuccess(){
		return (null!=telString&&telString>100);
	}
	public void setAreaVid(Long areaVid) {
		this.areaVid = areaVid;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public void setIspVid(Long ispVid) {
		this.ispVid = ispVid;
	}
	public void setMts(String mts) {
		this.mts = mts;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setTelString(Long telString) {
		this.telString = telString;
	}
}
