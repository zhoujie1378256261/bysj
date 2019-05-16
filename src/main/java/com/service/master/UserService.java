package com.service.master;


import com.model.User;

public class UserService {
    public static final UserService me = new UserService();
    private User dao = new User().dao();

    /**
     * 采购人手机号密码登陆
     * @param
     * @param password
     * @return
     */
    public int login(String username,String password){

        User bean = dao.findFirst("select * from t_user where username=? and password=? ",username,password);
        if(bean == null){
            return 1;
        }else{
            return 2;
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
