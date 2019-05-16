package com.service.master;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 微信公众号
 * 2018-10-22
 *
 */
public class WeixinService {
	public static final WeixinService me = new WeixinService();

	public Record findUserInfoByOpenId(String openId){
		return Db.findFirst("select * from t_reader where openid = ?",openId);
	}

	public List<Record> selectAllArea(){
		List<Record> shenList = Db.find("SELECT id value,orgid,name text FROM t_basic_area WHERE parentid = 0");
		for(Record shenRecord : shenList) {
			int shenId = shenRecord.getInt("value");
			List<Record> shiList = Db.find("SELECT id value,orgid,name text FROM t_basic_area WHERE parentid = ?",shenId);
			for(Record shiRecord : shiList) {
				int shiId = shiRecord.getInt("value");
				List<Record> quList = Db.find("SELECT id ,orgid value,name text FROM t_basic_area WHERE parentid = ?",shiId);
				shiRecord.set("children",quList);
			}
			shenRecord.set("children",shiList);
		}
		return shenList;
	}

}
