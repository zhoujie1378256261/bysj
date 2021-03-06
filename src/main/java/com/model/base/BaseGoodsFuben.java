package com.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGoodsFuben<M extends BaseGoodsFuben<M>> extends Model<M> implements IBean {

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

	public M setPrice(java.math.BigDecimal price) {
		set("price", price);
		return (M)this;
	}
	
	public java.math.BigDecimal getPrice() {
		return get("price");
	}

	public M setGroupname(java.lang.String groupname) {
		set("groupname", groupname);
		return (M)this;
	}
	
	public java.lang.String getGroupname() {
		return getStr("groupname");
	}

	public M setStock(java.lang.Integer stock) {
		set("stock", stock);
		return (M)this;
	}
	
	public java.lang.Integer getStock() {
		return getInt("stock");
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
