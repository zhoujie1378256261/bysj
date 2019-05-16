package com.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGoods<M extends BaseGoods<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setGoodsname(java.lang.String goodsname) {
		set("goodsname", goodsname);
		return (M)this;
	}
	
	public java.lang.String getGoodsname() {
		return getStr("goodsname");
	}

	public M setShopid(java.lang.Integer shopid) {
		set("shopid", shopid);
		return (M)this;
	}
	
	public java.lang.Integer getShopid() {
		return getInt("shopid");
	}

	public M setGoodscategoryid(java.lang.Integer goodscategoryid) {
		set("goodscategoryid", goodscategoryid);
		return (M)this;
	}
	
	public java.lang.Integer getGoodscategoryid() {
		return getInt("goodscategoryid");
	}

	public M setGoodstypeid(java.lang.Integer goodstypeid) {
		set("goodstypeid", goodstypeid);
		return (M)this;
	}
	
	public java.lang.Integer getGoodstypeid() {
		return getInt("goodstypeid");
	}

	public M setGoodsmasteImgurl(java.lang.String goodsmasteImgurl) {
		set("goodsmaste_imgurl", goodsmasteImgurl);
		return (M)this;
	}
	
	public java.lang.String getGoodsmasteImgurl() {
		return getStr("goodsmaste_imgurl");
	}

	public M setGoodsimgurl(java.lang.String goodsimgurl) {
		set("goodsimgurl", goodsimgurl);
		return (M)this;
	}
	
	public java.lang.String getGoodsimgurl() {
		return getStr("goodsimgurl");
	}

	public M setGoodsbrand(java.lang.String goodsbrand) {
		set("goodsbrand", goodsbrand);
		return (M)this;
	}
	
	public java.lang.String getGoodsbrand() {
		return getStr("goodsbrand");
	}

	public M setAddress(java.lang.String address) {
		set("address", address);
		return (M)this;
	}
	
	public java.lang.String getAddress() {
		return getStr("address");
	}

	public M setMarketprice(java.math.BigDecimal marketprice) {
		set("marketprice", marketprice);
		return (M)this;
	}
	
	public java.math.BigDecimal getMarketprice() {
		return get("marketprice");
	}

	public M setGoodsdetail(java.lang.String goodsdetail) {
		set("goodsdetail", goodsdetail);
		return (M)this;
	}
	
	public java.lang.String getGoodsdetail() {
		return getStr("goodsdetail");
	}

	public M setAftersale(java.lang.String aftersale) {
		set("aftersale", aftersale);
		return (M)this;
	}
	
	public java.lang.String getAftersale() {
		return getStr("aftersale");
	}

	public M setPasstime(java.util.Date passtime) {
		set("passtime", passtime);
		return (M)this;
	}
	
	public java.util.Date getPasstime() {
		return get("passtime");
	}

	public M setAuditor(java.lang.Integer auditor) {
		set("auditor", auditor);
		return (M)this;
	}
	
	public java.lang.Integer getAuditor() {
		return getInt("auditor");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public M setGoodsstatus(java.lang.Integer goodsstatus) {
		set("goodsstatus", goodsstatus);
		return (M)this;
	}
	
	public java.lang.Integer getGoodsstatus() {
		return getInt("goodsstatus");
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
