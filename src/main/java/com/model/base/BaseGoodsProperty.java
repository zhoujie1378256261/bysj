package com.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGoodsProperty<M extends BaseGoodsProperty<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setGoodsid(java.lang.Integer goodsid) {
		set("goodsid", goodsid);
		return (M)this;
	}
	
	public java.lang.Integer getGoodsid() {
		return getInt("goodsid");
	}

	public M setPropertyid(java.lang.Integer propertyid) {
		set("propertyid", propertyid);
		return (M)this;
	}
	
	public java.lang.Integer getPropertyid() {
		return getInt("propertyid");
	}

	public M setPropertievaluesid(java.lang.Integer propertievaluesid) {
		set("propertievaluesid", propertievaluesid);
		return (M)this;
	}
	
	public java.lang.Integer getPropertievaluesid() {
		return getInt("propertievaluesid");
	}

	public M setGoodsfubenid(java.lang.Integer goodsfubenid) {
		set("goodsfubenid", goodsfubenid);
		return (M)this;
	}
	
	public java.lang.Integer getGoodsfubenid() {
		return getInt("goodsfubenid");
	}

	public M setCreatetime(java.util.Date createtime) {
		set("createtime", createtime);
		return (M)this;
	}
	
	public java.util.Date getCreatetime() {
		return get("createtime");
	}

	public M setUpdatetime(java.util.Date updatetime) {
		set("updatetime", updatetime);
		return (M)this;
	}
	
	public java.util.Date getUpdatetime() {
		return get("updatetime");
	}

	public M setCreateby(java.lang.Integer createby) {
		set("createby", createby);
		return (M)this;
	}
	
	public java.lang.Integer getCreateby() {
		return getInt("createby");
	}

	public M setUpdateby(java.lang.Integer updateby) {
		set("updateby", updateby);
		return (M)this;
	}
	
	public java.lang.Integer getUpdateby() {
		return getInt("updateby");
	}

	public M setDeletestatus(java.lang.Integer deletestatus) {
		set("deletestatus", deletestatus);
		return (M)this;
	}
	
	public java.lang.Integer getDeletestatus() {
		return getInt("deletestatus");
	}

}