package com.controller.sysconfig;

import com.config.BaseController;
import com.config.ControllerInterface;
import com.jfinal.log.Log;
import com.jfinal.upload.UploadFile;
import com.tools.JwtUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 图书操作
 * 2018-10-23
 * zj
 */
@ControllerInterface(path="/file")
public class FileController extends BaseController {

    Log log =Log.getLog(this.getClass().getName());

    public void index() {
        render("/filedemo.html");
    }

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");


    public void uploadImage(){
        try{
            UploadFile file =getFile();
            Map<String,String> map = new HashMap<String,String>();
            String path ="/" + sf.format(new Date()) + "/" + file.getFileName();
            File targetFile =new File(getRequest().getRealPath("upload")+path);
            if (targetFile.exists()){
                FileUtils.forceDelete(targetFile);
            }
            FileUtils.moveFile(file.getFile(), targetFile);
            map.put("src","/upload/" + sf.format(new Date()) + "/" + file.getFileName());
            map.put("title",file.getFileName());
            success(0,"调用成功",map);
        }catch (Exception e) {
            fail();
        }
    }

    public String createDir(String path){
        String paths[] = path.split("/");
        String fullpath =getRequest().getRealPath("upload");
        for(String s : paths){
            File file = new File(fullpath+File.separator+s);
            if(!file.exists()){
                file.mkdir();
            }
            fullpath = file.getPath();
        }
        return fullpath;
    }
    public void uploadSingleFile(){
        try{
            Map info = JwtUtils.validateJWTForMap(this.getPara("token"));
            if(info == null){
                fail_token();
                return;
            }
            int orgid = (int)info.get("orgid");
            UploadFile file = getFile();
            String dirpath = orgid +"/"+ sf.format(new Date()) + "/"+System.currentTimeMillis();
            String path =createDir(dirpath)+"/"+ file.getOriginalFileName();
            FileUtils.moveFile(file.getFile(),new File(path));
            Map<String, String> map = new HashMap();
            map.put("url","/upload/"+dirpath+"/"+file.getOriginalFileName());
            map.put("filename", file.getOriginalFileName());
            success(map);
        }catch (Exception e) {
            fail();
        }
    }

    public void uploadMultipleFile(){
        try{
            String token = this.getPara("token");
            Map info = JwtUtils.validateJWTForMap(token);
            if(info == null){
                fail_token();
                return;
            }
            int orgid = (int)info.get("orgid");
            List<UploadFile> fileList =getFiles();
            List<String> upfileList = new ArrayList<String>();
            for (UploadFile file : fileList) {

                String path ="/" + orgid+"/"+sf.format(new Date()) + "/"+System.currentTimeMillis() + file.getFileName();
                File targetFile =new File(getRequest().getRealPath("upload")+path);
                FileUtils.moveFile(file.getFile(), targetFile);
                upfileList.add(path);
            }
            success(upfileList);
        }catch (Exception e) {
            fail(0,e.getMessage());
        }
    }

    public void downloadFile(){
        try {
            String filename =getPara("filename");
            String path =getRequest().getRealPath("upload")+File.separator+  filename;
            log.debug(path);
            File targetFile =new File(path);
            log.debug("is exists="+targetFile.exists());
            renderFile(targetFile);

        } catch (Exception e) {
            e.printStackTrace();
            fail(0,e.getMessage());
        }
    }
}
