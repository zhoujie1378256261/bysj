package com.controller.master;

import com.alibaba.fastjson.JSON;
import com.config.AuthInterceptor;
import com.config.BaseController;
import com.config.ControllerInterface;
import com.config.SystemConstant;
import com.jfinal.aop.Clear;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.service.master.WeixinService;
import com.tools.JwtUtils;
import com.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信请求处理
 * 2018-10-23
 * wush
 */
@Clear(AuthInterceptor.class)
@ControllerInterface(path="/weixin")
public class WeixinController extends BaseController {

    WeixinService service = WeixinService.me;

    public void checkWeixinInfo(){
        String code = getPara("code");
        String appId = PropKit.get("appId");
        String appSecret = PropKit.get("appSecret");
        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
        String tokeninfo = CommonUtils.requestUrl(tokenUrl);
        Map tokenMap = (Map)JSON.parse(tokeninfo);
        if(CommonUtils.isNotNullOrNotEmpty(tokenMap) && CommonUtils.isNotNullOrNotEmpty(tokenMap.get("openid"))){
            String openid = tokenMap.get("openid").toString();
            Record record = service.findUserInfoByOpenId(openid);
            if(CommonUtils.isNotNullOrNotEmpty(record)) {
                Map info = new HashMap();
                info.put("userid",record.get("id"));
                String token = JwtUtils.createJWT(record.get("id")+"",info, SystemConstant.TOKEN_JWT_LIFE_APP);
                redirect("/weixin/view/index/index.html?token=" + token+"&orgid="+record.get("orgid"));
                return;
            }
            redirect("/weixin/view/login.html?openid="+openid);
        } else {
            redirect("/weixin/view/error.html");
        }

    }

    public void bindWeixin(){
        String username = this.getPara("username");
        Integer orgid = this.getParaToInt("orgid");
        String password = this.getPara("password");
        String openid = this.getPara("openid");
        if (CommonUtils.isNullOrEmpty(openid) || CommonUtils.isNullOrEmpty(orgid) || CommonUtils.isNullOrEmpty(password) || CommonUtils.isNullOrEmpty(username)){
            fail(1,"请求参数不完整");
            return;
        }
        Record reader = Db.findFirst("select id from t_reader where orgid = ? and password = ? and cardnumber = ?",orgid,password,username);
        if (CommonUtils.isNotNullOrNotEmpty(reader)){
            Map info = new HashMap();
            info.put("userid",reader.get("id"));
            String token = JwtUtils.createJWT(reader.getInt("id")+"",info, SystemConstant.TOKEN_JWT_LIFE_APP);
            Db.update("update t_reader set openid = ? where id = ? ",openid,reader.getInt("id"));
            success(token);
        }else {
            fail(0,"查询不到读者信息");
            return;
        }
    }

    public void testWeixin(){
        Map info = new HashMap();
        info.put("userid","5");
        String token = JwtUtils.createJWT("5",info, SystemConstant.TOKEN_JWT_LIFE_APP);
        redirect("/weixin/view/index/index.html?token=" + token+"&orgid=111006");
    }
}
