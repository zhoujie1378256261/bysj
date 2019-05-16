package com.service.master;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.model.User;

public class UserService {
    public static final UserService me = new UserService();
    private User dao = new User().dao();


    public int login(String username,String password){

        User bean = dao.findFirst("select * from t_user where username=? and password=? ",username,password);
        if(bean == null){
            return 1;
        }else{
            return 2;
        }
    }

    public void logout(String username,String password,String gender,Integer deptid,String position,String phone,String email){

        User user = new User();
        user.setUsername(username).setPassword(password).setGender(gender).setDeptid(deptid).setPosition(position).setPhone(phone).setEmail(email).save();
    }
    public Record findUserInfo(int userid){
        Record r = Db.findFirst("SELECT a.*,b.dept FROM t_user a LEFT JOIN t_dept b ON a.deptid = b.id WHERE a.id = ? ",userid);
        return r;
    }
    public int updatepwd(int userid,String mm,String xmm){
        Record r = Db.findFirst("SELECT * FROM t_user WHERE id = ? AND `password` = ? ",userid,mm);
        if(r == null){
            return 0;
        }else {
            Db.update("UPDATE t_user SET `password` = ? WHERE id = ?",xmm,userid);
            return 1;
        }

    }
    public void updatUserInfo(int userid,String phone,String email){
        Db.update("UPDATE t_user SET phone = ?,email = ? WHERE id = ?",phone,email,userid);
    }

}
