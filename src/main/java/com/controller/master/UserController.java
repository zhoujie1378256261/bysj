package com.controller.master;

import com.config.BaseController;
import com.config.ControllerInterface;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Redis;
import com.model.Customer;
import com.model.SysUser;
import com.service.master.UserService;
import com.tools.JwtUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@ControllerInterface(path="/user")
public class UserController extends BaseController {
    Log log = Log.getLog(this.getClass());
    private UserService service = UserService.me;

    public void index() {
        render("/weixin/view/index/index.html");
    }
    /**
     * 增加/修改
     */
//    public void addOrUpdate(){
//        String token = this.getPara("token");
//        int userid = checkLoginStatusForSysUserId(token);
//        if (userid == 0) {
//            fail_token();
//            return;
//        }
//        SysUser bean = new SysUser();
//        bean.setId(this.getParaToInt("id")).set("username",getPara("username")).set("password",getPara("password"))
//                .setRealname(getPara("realname"));
//        service.saveOrUpdate(bean,userid);
//        success();
//    }
//
//    /**
//     * 用户列表
//     */
//    public void list(){
//        int userid = checkLoginStatusForSysUserId(this.getPara("token"));
//        if(userid == 0){
//            fail_token();return;
//        }
//        Page page = service.mypage(this.getParaToInt("pageSize", PAGESIZE),this.getParaToInt("pageNumber",1));
//        success(page);
//    }
//
//
//    /**
//     * 修改密码
//     */
//    public void updatePassword(){
//        try {
//            String token = this.getPara("token");
//            String password = this.getPara("password");
//            int userid = checkLoginStatusForSysUserId(token);
//            if (userid == 0) {
//                fail_token();
//                return;
//            }
//            service.updatePassword(userid, password);
//            success();
//        }catch(Exception e){
//            log.error("", e);
//            fail();
//        }
//    }
    public void login(){
        try {
            String username = this.getPara("username");
            String password = this.getPara("password");
            success(service.login(username, password));

        }catch(Exception e){
            log.error("",e);
            fail();
        }
    }
    public void logout(){
        try {
            String token = this.getPara("token");
            int userid = JwtUtils.validateJWTForUserId(token);
            Redis.use().del(REDIS_SYSLOGINKEY_PERFIX+userid);
            success();
        }catch(Exception e){
            log.error("", e);
            fail();
        }
    }

    /**
     * 登陆状态下获取 个人信息
     */
//    public void getById(){
//        SysUser bean = checkLoginStatusForSysUser(this.getPara("token"));
//        if (bean == null) {
//            fail_token();
//            return;
//        }
//        int id = this.getParaToInt("id", 0);
//        if(id==0){
//            fail();
//            return;
//        }
//        success(service.getById(id));
//    }
//    public void delete(){
//        try {
//            SysUser bean = checkLoginStatusForSysUser(this.getPara("token"));
//            if (bean == null) {
//                fail_token();
//                return;
//            }
//            int id = this.getParaToInt("id", 0);
//            if (id == 0) {
//                fail();
//                return;
//            }
//            service.delete(id);
//            success();
//        }catch(Exception e){
//            log.error("", e);
//            fail();
//        }
//    }

}
