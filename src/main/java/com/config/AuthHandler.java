package com.config;

import com.alibaba.fastjson.JSON;
import com.jfinal.handler.Handler;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;

import com.tools.JwtUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jfinal.kit.HandlerKit.redirect;

public class AuthHandler extends Handler {
    public static List<String> noauthlist = initpath();
    public static List<String> initpath(){
        List<String> list = new ArrayList<String>();
        try{
            list = FileUtils.readLines(new File(PathKit.getRootClassPath()+ File.separatorChar +"noauth.txt"),"utf-8");
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void handle(String target, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean[] booleans) {
        try{
         //   if (httpServletRequest.getHeader("x-requested-with") ==null){
                if (!ignore(target)){
                    /*User user = (User)httpServletRequest.getSession().getAttribute("userInfo");
                    if(user  == null) {
                        redirectLogin(target,httpServletRequest,httpServletResponse,booleans);
                        return;
                    }

                    String token=user.get("token").toString();
                    if(StringUtils.isBlank(token)){
                        token = httpServletRequest.getParameter("token");
                    }

                    if(JwtUtils.validateJWTForUserId(token) == 0 ){
                        redirectLogin(target,httpServletRequest,httpServletResponse,booleans);
                        return;
                    }*/
                }
           // }

            next.handle(target,httpServletRequest,httpServletResponse,booleans);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean ignore(String target){
        for (String url :noauthlist){
            if (url.endsWith("*") && target.indexOf(url.substring(0,url.length()-1)) !=-1){
                return true;
            }
            if (target.endsWith(url)){
                return true;
            }
        }
        return false;
    }

    private void redirectLogin(String target,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,boolean[] booleans){
        URL url = null;
        try {
            if (httpServletRequest.getHeader("x-requested-with") !=null){
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json; charset=utf-8");
                Map result = new HashMap();
                result.put("code","0");
                result.put("msg","xxxxxx");
                System.out.println(JSON.toJSON(result));

                httpServletResponse.getWriter().write(JSON.toJSON(result).toString());
                httpServletResponse.getWriter().flush();
                httpServletResponse.setStatus(200);

            }else{
                url = new URL(httpServletRequest.getRequestURL().toString());
                String path =url.getProtocol()+"://"+url.getHost()+":"+url.getPort()+PropKit.get("login_page_url");
                redirect(PropKit.get("login_page_url"),httpServletRequest,httpServletResponse,booleans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
