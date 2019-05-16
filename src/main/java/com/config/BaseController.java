package com.config;




import com.jfinal.core.Controller;
import com.jfinal.log.Log;


import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Redis;

import com.model.Customer;
import com.model.SysUser;
import com.tools.JwtUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提取一些经常的代码，主要用于继承
 * @author hkc
 *
 */
public class BaseController extends Controller {
	public Log logger = Log.getLog(BaseController.class);
	public SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	public SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final String REDIS_LOGINKEY_PERFIX = "loginflag_";
	public static final String REDIS_SYSLOGINKEY_PERFIX = "sysloginflag_";
	public static final int REDIS_LOGINSESSION_TIMES=60*60*24;//24小时
	//public static final int PAGESIZE = 20;//每页现实条数
	public static final int PAGESIZE = 20;//每页现实条数

	public static final int CODE_AUTH=201;

	/**
	 * 验证用户是否处于登陆状态
	 * @param token
	 * @return
	 */
	public static int checkLoginStatusForWebCustomerid(String token){
		int webcustomerid = JwtUtils.validateJWTForUserId(token);
		if(webcustomerid == 0){
			return 0;
		}
		Customer bean = Redis.use().get(REDIS_LOGINKEY_PERFIX+webcustomerid);
		if(bean == null){
			return 0;
		}
		return webcustomerid;
	}
	public static Customer checkLoginStatusForWebCustomer(String token){
		int webcustomerid = JwtUtils.validateJWTForUserId(token);
		if(webcustomerid == 0){
			return null;
		}
		Customer bean = Redis.use().get(REDIS_LOGINKEY_PERFIX+webcustomerid);
		if(bean == null){
			return null;
		}
		return bean;
	}
	public static SysUser checkLoginStatusForSysUser(String token){
		int sysUserId = JwtUtils.validateJWTForUserId(token);
		if(sysUserId == 0){
			return null;
		}
		SysUser bean = Redis.use().get(REDIS_SYSLOGINKEY_PERFIX+sysUserId);
		if(bean == null){
			return null;
		}
		return bean;
	}
	public static int checkLoginStatusForSysUserId(String token){
		int sysUserId = JwtUtils.validateJWTForUserId(token);
		if(sysUserId == 0){
			return 0;
		}
		SysUser bean = Redis.use().get(REDIS_SYSLOGINKEY_PERFIX+sysUserId);
		if(bean == null){
			return 0;
		}
		return sysUserId;
	}

	/**
	 * 将List转换为数组对象
	 * @param list
	 * @return
	 */
	public static Object[] listToArray(List<Object> list){
		return list.toArray(new Object[list.size()]);
	}

	public void success(Object o){
		Map result = new HashMap();
		result.put("code",200);
		result.put("msg","调用成功");
		result.put("data",o);
		this.renderJson(result);
	}

	public void reData(Object o){
		this.renderJson(o);
	}

	public void success(int code,String msg,Object o){
		Map result = new HashMap();
		result.put("code",code);
		result.put("msg",msg);
		result.put("data",o);
		this.renderJson(result);
	}

	public void success(int code,String msg){
		Map result = new HashMap();
		result.put("code",code);
		result.put("msg",msg);
		this.renderJson(result);
	}
	public void success(){
		Map result = new HashMap();
		result.put("code",200);
		result.put("msg","操作成功");
		this.renderJson(result);
	}
	public void fail(int code, String msg){
		Map result = new HashMap();
		result.put("code",code);
		result.put("msg",msg);

		this.renderJson(result);
	}
	public void fail(){
		Map result = new HashMap();
		result.put("code","0");
		result.put("msg","请求错误");
		this.renderJson(result);
	}
	public void fail_token(){
		Map result = new HashMap();
		result.put("code","3");
		result.put("msg","token验证错误");
		this.renderJson(result);
	}


	public void formatDate(List<Record> list, SimpleDateFormat format){
		for(Record r : list){
			String[] keys = r.getColumnNames();
			for(String key : keys){
				if(r.get(key) instanceof java.util.Date){
					r.set(key, format.format(r.getDate(key)));
				}
				if(r.get(key) instanceof String){
					if(r.get(key) != null){
						r.set(key, r.getStr(key).trim());
					}

				}
			}
		}
	}


	private int getOperatorid(String token ){
		int operatorid = JwtUtils.validateJWTForUserId(token);
		if(operatorid == 0){
			fail(3,"token错误");
		}
		return operatorid;
	}

}
