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
    public void findUserInfo(){
        try {
            int userid = this.getParaToInt("userid");
            if (userid == 0) {
                fail_token();
                return;
            }
            success(service.findUserInfo(userid));
        }catch(Exception e){
            log.error("", e);
            fail();
        }
    }

    public void updatepwd(){
        try {
            int userid = this.getParaToInt("userid");
            String mm = this.getPara("mm");
            String xmm = this.getPara("xmm");
            if (userid == 0) {
                fail_token();
                return;
            }
            int r = service.updatepwd(userid,mm,xmm);
            if(r == 0){
                success(1,"原密码错误");
            }else {
                success();
            }
        }catch(Exception e){
            log.error("", e);
            fail();
        }
    }
    public void updatUserInfo(){
        try {
            int userid = this.getParaToInt("userid");
            String phone = this.getPara("phone");
            String email = this.getPara("email");
            if (userid == 0) {
                fail_token();
                return;
            }
            service.updatUserInfo(userid,phone,email);
            success();
        }catch(Exception e){
            log.error("", e);
            fail();
        }
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
    //登录
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
    //注册
    public void logout(){
        try {
            String username = this.getPara("username");
            String password = this.getPara("password");
            String gender = this.getPara("gender");
            Integer deptid = this.getParaToInt("deptid");
            String position = this.getPara("position");
            String phone = this.getPara("phone");
            String email = this.getPara("email");
            service.logout(username,password,gender,deptid,position,phone,email);
            success();
        }catch(Exception e){
            log.error("", e);
            fail();
        }
    }


}
