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
//    public void saveOrUpdate(SysUser bean,int userid){
//        if(bean.getId() != null && bean.getId()>0){
//            dao.findById(bean.getId()).setRealname(bean.getRealname()).setUpdateby(userid).setStatus(1).setDeletestatus(0).setUpdatetime(new Date()).update();
//        }else{
//            bean.setCreateby(userid).setStatus(1).setDeletestatus(0).setCreatetime(new Date()).save();
//        }
//    }
//
//    public Page mypage(int pageSize, int pageNumber){
//        String select = "select * ";
//        StringBuilder where = new StringBuilder(" from t_sys_user where deletestatus=0 order by createtime desc");
//        List<Object> values = new ArrayList();
//        return Db.paginate(pageNumber,pageSize,select,where.toString(),CommonUtils.listToArray(values));
//    }
//
//    public void updatePassword(int id, String password){
//        SysUser bean = dao.findById(id);
//        bean.setPassword(password).update();
//    }
//    public SysUser getById(int id){
//        return dao.findById(id);
//    }
//    public void delete(int id){
//        dao.findById(id).setDeletestatus(1).update();
//    }
}
