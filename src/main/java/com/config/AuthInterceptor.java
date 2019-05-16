package com.config;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.PathKit;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AuthInterceptor implements Interceptor {
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


    @Override
    public void intercept(Invocation invocation) {
        try{
            String target = invocation.getActionKey();
            if (ignore(target)){
                invocation.invoke();
                return;
            }
            /*User user = (User)invocation.getController().getSession().getAttribute("userInfo");
            if(user  == null) {
                HttpServletRequest httpServletRequest=invocation.getController().getRequest();
                if (httpServletRequest.getHeader("x-requested-with") !=null){
                    Map result = new HashMap();
                    result.put("code","3");
                    result.put("msg","长时间没有使用系统，请先重新登录！");
                    invocation.getController().renderJson(result);
                }else {
                    invocation.getController().redirect(PropKit.get("login_page_url"));
                }
            }else{
                invocation.invoke();
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean ignore(String target){
        for (String url :noauthlist){
            if (url.equals("")){
                continue;
            }
            if (url.endsWith("*") && target.indexOf(url.substring(0,url.length()-2)) !=-1){
                return true;
            }
            if (target.endsWith(url) ){
                return true;
            }
        }
        return false;
    }
}
