package com.controller.master;

import com.config.AuthInterceptor;
import com.config.BaseController;
import com.config.ControllerInterface;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tools.JwtUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Clear(AuthInterceptor.class)
@ControllerInterface(path="weixinwode")
public class WodeController extends BaseController {

//    WXYujieService jieyueService = WXYujieService.me;

//    /**
//     * 读者证挂失
//     */
//    public void reporttheloss(){
//        try {
//            String token = this.getPara("token");
//            String mm = this.getPara("mm");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            Record reader = Db.findFirst("select count(*) as count from t_reader where id = ? and password = ? ",userId,mm);
//            int count = reader.getInt("count");
//            if(count==0){
//                Map result = new HashMap();
//                result.put("resCode","200");
//                result.put("resMsg", "密码错误");
//                renderJson(result);
//                return;
//            }
//            int sftj = Db.update("update t_reader set status = 2 where id = ? ",userId);
//            if(sftj==1){
//                renderJson(weixinSuccess("操作成功"));
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//    /**
//     * 我的-->个人资料(一)查询信息
//     */
//    public void findUserInfo(){
//        try {
//            String token = this.getPara("token");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            //查出个人信息
//            Record reader = Db.findFirst("SELECT r.status,r.cardnumber AS jszh,r.realname AS xm,r.phone lxfs,r.createtime AS bzrq,r.losedate yxjzrq,a.jieyuetianshu ts,a.jieyueshuliang sl," +
//                    "a.yuyueyouxiaotianshu yyts,a.yuyueshuliang yysl,a.yujieshuliang yzsl,a.xujiecishu xjcs FROM t_reader r " +
//                    "LEFT JOIN t_reader_auth a ON r.readerauthid = a.id WHERE r.id =?",userId);
//            int status = reader.getInt("status");
//            int xjcs = reader.getInt("xjcs");
//            if(1 == status){
//                reader.set("sfyx","有效");
//            }else{
//                reader.set("sfyx","已注销");
//            }
//            if(xjcs == 0){
//                reader.set("sfxj","0");
//            }else{
//                reader.set("sfxj","1");
//            }
//            reader.remove("status");
//            renderJson(weixinSuccess(reader));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//    /**
//     * 我的-->个人资料(一)更改信息
//     */
//    public void updateUserInfo(){
//        try {
//            String token = this.getPara("token");
//            //原密码
//            String mm = this.getPara("mm");
//            //新姓名
//            String xxm = this.getPara("xxm");
//            //新联系方式
//            String xlxfs = this.getPara("xlxfs");
//            //新密码
//            String xmm = this.getPara("xmm");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            if(xxm!=""){
//                Db.update("update t_reader set password =? where id =? ",xxm,userId);
//            }
//            if(xlxfs!=""){
//                Db.update("update t_reader set phone = ? where id =? ",xlxfs,userId);
//            }
//
//            if(xmm!="" && mm!=""){
//                //判断原密码是否正确
//                Record reader = Db.findFirst("select count(*) as count from t_reader where id = ? and password = ? ",userId,mm);
//                int count = reader.getInt("count");
//                if(count==0){
//                    Map result = new HashMap();
//                    result.put("resCode","200");
//                    result.put("resMsg", "原密码错误");
//                    renderJson(result);
//                    return;
//                }
//                //更改数据
//                Db.update("update t_reader set password =? where id =? ",xxm,userId);
//            }
//            renderJson(weixinSuccess("操作成功"));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//    /**
//     * 图书馆基本信息-->规章制度
//     */
//    public void rulse(){
//        Record webJj = Db.findFirst("select content as gzzd from t_rules");
//        renderJson(weixinSuccess(webJj));
//    }
//    /**
//     * 图书馆基本信息-->馆藏简介
//     */
//    public void introduce(){
//        Record webJj = Db.findFirst("select content as jj from t_rules");
//        renderJson(weixinSuccess(webJj));
//    }
//    /**
//     * 图书馆公告
//     */
//    public void news_bulletin(){
//        try {
//            String orgid = this.getPara("orgid");
//            String select ="select title as xwbt,content as xwlr,createtime as xwrq ";
//            StringBuilder where = new StringBuilder(" from t_news where orgid = ? order by createtime desc ");
//            Page<Record> page = Db.paginate(this.getParaToInt("pageIndex", 1), 10, select, where.toString(),orgid);
//            formatDate(page.getList(),format1);
//            renderJson(weixinSuccess(page));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//    /**
//     * 我的收藏
//     */
//    public void my_collect(){
//        try {
//            String token = this.getPara("token");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            String select ="SELECT b.publishdate cbny,b.publisher cbsm,b.typecode flh,b.id lsh,b.bookno ssh,b.title ztm,b.author zz ,c.id sclsh";
//            StringBuilder where = new StringBuilder(" FROM t_collection c ,t_book b WHERE c.bookid = b.id and c.flag = 0 and c.readerid =? ");
//            Page<Record> page = Db.paginate(this.getParaToInt("pageIndex", 1), 10, select, where.toString(),userId);
//            formatDate(page.getList(),format1);
//            renderJson(weixinSuccess(page));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//    /**
//     * 取消收藏
//     */
//    public void cancel_collect(){
//        try {
//            String s = this.getPara("sclsh");
//            if(s!=null){
//                Db.delete("delete from t_collection where id in(?)",s);
//                renderJson(weixinSuccess("操作成功"));
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//
//    /**
//     * 系统消息
//     */
//    public void readerMessage(){
//        try {
//            String token = this.getPara("token");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            String select ="select n.content,o.flag,o.id,n.title,DATE_FORMAT(o.createtime,'%Y-%m-%d') createtime ";
//            StringBuilder where = new StringBuilder("FROM t_notice_org o,t_notice n WHERE o.noticeid = n.id AND o.readeruserid = ? ORDER BY o.createtime DESC,o.flag ASC");
//            Page<Record> page = Db.paginate(this.getParaToInt("pageIndex", 1), 10, select, where.toString(),userId);
//            Date date = new Date();
//            String dat = format1.format(date);
//            for(Record r : page.getList()){
//                r.set("date",dat);
//            }
//            formatDate(page.getList(),format1);
//            renderJson(weixinSuccess(page));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//
//    /**
//     * 查询系统消息详情
//     */
//    public void findReaderMessageById(){
//        String token = this.getPara("token");
//        int userId = JwtUtils.validateJWTForUserId(token);
//        if (userId == 0) {
//            renderJson(weixinTokenFail());
//            return;
//        }
//        String id = this.getPara("newsid");
//        Record record = Db.findFirst("select n.content,o.flag,o.id,n.title,DATE_FORMAT(o.createtime,'%Y-%m-%d') createtime FROM t_notice_org o,t_notice n WHERE o.noticeid = n.id AND o.readeruserid = ? and o.id=?",userId,id);
//        renderJson(weixinSuccess(record));
//    }
//
//    /**
//     * 系统消息已读设置
//     */
//    public void xtxx_read(){
//        int lsh = this.getParaToInt("lsh");
//        String token = this.getPara("token");
//        int userId = JwtUtils.validateJWTForUserId(token);
//        if (userId == 0) {
//            renderJson(weixinTokenFail());
//            return;
//        }
//        Db.update("update t_notice_org set flag = 1,readertime = now() where readeruserid =? and id =? ",userId,lsh);
//        renderJson(weixinSuccess("操作成功"));
//        }
//
//    /**
//     * 在借中
//     */
//    public void borrow(){
//        try {
//            String token = this.getPara("token");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            Integer status = this.getParaToInt("status");
//            if (CommonUtils.isNullOrEmpty(status)){
//                renderJson(weixinFail("参数不完整"));
//                return;
//            }
//            String author = this.getPara("author");
//            String title = this.getPara("title");
//            String select = "SELECT b.id AS lsh,a.id AS alsh,a.bookdetailid szlsh,b.title ,d.barcode ,a.returntime,b.author ,b.publisher ,ifnull(b.publishdate,'') as publishdate ,a.continuenum ,a.duetime ,a.createtime ,l.library ,b.bookno  ";
//            StringBuilder where = new StringBuilder("FROM t_jieyue a LEFT JOIN t_book_detail d ON d.id = a.bookdetailid LEFT JOIN t_book b ON d.bookid = b.id LEFT JOIN t_library l ON l.id = a.libraryid WHERE a.readerid =? and a.status=? ");
//            if (CommonUtils.isNotNullOrNotEmpty(author)){
//                where.append(" and b.author like %"+author.trim()+"% ");
//            }
//            if (CommonUtils.isNotNullOrNotEmpty(title)){
//                where.append(" and b.title like %"+title.trim()+"% ");
//            }
//            where.append(" ORDER BY a.duetime DESC");
//            Page<Record> page = Db.paginate(this.getParaToInt("pageNumber", 1), this.getParaToInt("pageSize", 10), select, where.toString(),userId,status);
//            Date dat = new Date();
//            for (Record r : page.getList()){
//                int lsh = r.getInt("lsh");
//                if (status == 0){
//                    String date = format1.format(dat);
//                    Date date1 = format1.parse(date);
//                    String date2 = r.getStr("duetime");
//                    Date date3 = format1.parse(date2);
//                    int ts = (int) ((date3.getTime() - date1.getTime()) / (1000*3600*24));
//                    r.set("dueday",ts);
//                }
//                Record pls = Db.findFirst("select count(bookid) as pjsl from t_book_evaluation b where readerid =? and b.bookid = ? ",userId,lsh);
//                int pj = pls.getInt("pjsl");
//                if(pj>0){
//                    r.set("pj","1");
//                }else {
//                    r.set("pj","0");
//                }
//            }
//
//            formatDate(page.getList(),format1);
//            renderJson(weixinSuccess(page));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//    /**
//     * 超期未还
//     */
//    public void overdue(){
//        try {
//            String token = this.getPara("token");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            Date dat = new Date();
//            String date = format1.format(dat);
//            Date date1 = format1.parse(date);
//            Record reader = Db.findFirst("SELECT a.chaoqifakuan AS fkdj FROM t_reader r LEFT JOIN t_reader_auth a ON r.readerauthid = a.id WHERE r.id = ? ",userId);
//            Double fkdj = reader.getDouble("fkdj");
//            if(fkdj == null){
//                fkdj = 0.0;
//            }
//            String select ="SELECT b.id AS lsh,a.id AS alsh,a.bookdetailid szlsh,b.title ,d.barcode ,b.author ,b.publisher ,ifnull(b.publishdate,'') as publishdate ,a.continuenum ,a.duetime  ";
//            StringBuilder where = new StringBuilder("FROM t_jieyue a LEFT JOIN t_book_detail d ON d.id = a.bookdetailid LEFT JOIN t_book b ON d.bookid = b.id WHERE a.readerid =? AND a.duetime < ? ORDER BY a.duetime DESC");
//            Page<Record> page = Db.paginate(this.getParaToInt("pageIndex", 1), 10, select, where.toString(),userId,date);
//            for (Record r : page.getList()){
//                String date2 = r.getStr("duetime");
//                Date date3 = format1.parse(date2);
//                int ts = (int) ((date1.getTime()-date3.getTime() ) / (1000*3600*24));
//                r.set("dueday",ts);
//                Double je = ts * fkdj;
//                r.set("duefee",je);
//                r.set("ycq","已超期");
//            }
//            formatDate(page.getList(),format1);
//            renderJson(weixinSuccess(page));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//    /**
//     * 续借
//     */
//    public  void renew_book(){
//        try {
//            String token = this.getPara("token");
//            int alsh = this.getParaToInt("alsh");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            int orgid = Db.queryInt("SELECT orgid FROM t_reader WHERE id = ?",userId);
//            Record bookInfoRecord = Db.findFirst("select '0' as booktype,b.id bookid,b.title,b.bookno,b.author,b.price,b.publisher,l.library,ifnull(l.isopen,1) isopen,j.* from t_book b inner JOIN t_book_detail d on b.id = d.bookid left join t_jieyue j on j.bookdetailid = d.id inner join t_library l on d.libraryid = l.id where j.id = ? and d.bookstatus=1 ORDER BY j.status asc,j.createtime desc",alsh);
//            if(CommonUtils.isNullOrEmpty(bookInfoRecord)) {
//                renderJson(weixinFail("查询不到借阅记录"));
//                return;
//            }
//            if(bookInfoRecord.getInt("isopen") == 0) {
//                renderJson(weixinFail("该书所在馆藏地禁止借阅"));
//                return;
//            }
//            Record authRecord = Db.findFirst("select a.* from t_reader r left join t_reader_auth a on r.readerauthid=a.id where r.id='" + userId + "'");
//
//            long currentTime = System.currentTimeMillis();
//            int xujietianshu = authRecord.get("xujietianshu"); //用户当前权限下续借一次的天数
//            Timestamp duetime = bookInfoRecord.get("duetime");
//            int shifounengchaoqijieshu = authRecord.get("shifounengchaoqijieshu");
//            if(1 == shifounengchaoqijieshu) {
//                if(currentTime > duetime.getTime()) { //超期
//                    renderJson(weixinFail("超期不允许续借"));
//                    return;
//                }
//            }
//
//            int shifounengqiankuanjieshu = authRecord.get("shifounengqiankuanjieshu");
//            if(1 == shifounengqiankuanjieshu) {
//                List list = Db.find("select * from t_book_penalty where readerid = ? and penaltystatus = 0",userId);
//                if (CommonUtils.isNotNullOrNotEmpty(list) && list.size() > 0) {
//                    renderJson(weixinFail("欠款不允许续借"));
//                    return;
//                }
//            }
//            long residualtime = duetime.getTime()-currentTime;
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DATE,xujietianshu);
//            long youxiaoshijian = residualtime + calendar.getTimeInMillis();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String str = sdf.format(youxiaoshijian);
//            int detailid = bookInfoRecord.get("bookdetailid");
//            Date temp = sdf2.parse(str+" 23:59:59");
//            int xujiecishu = authRecord.get("xujiecishu");
//            Record record = Db.findFirst("select count(1) jieyuenumber from t_jieyue where continuenum =1 and readerid = ? and bookdetailid = ? ",userId,detailid);
//            int jieyueNumber = record.getInt("jieyuenumber");
//            if(xujiecishu > jieyueNumber) {
//                Jieyue jieyue = new Jieyue();
//                jieyue.setReaderid(userId);
//                jieyue.setOrgid(orgid);
//                jieyue.setBookdetailid(detailid);
//                jieyue.setFlag(0);
//                jieyue.setStatus(0);
//                jieyue.setContinuenum(0);
//                jieyue.setCreatetime(new Date());
//                jieyue.setDuetime(temp);
//                jieyue.setOperatorid(userId);
//                jieyueService.xujie(userId,alsh,jieyue);
//            }else{
//                renderJson(weixinFail("超过续借次数"));
//                return;
//            }
//            renderJson(weixinSuccess("续借成功"));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//
//    /**
//     * 临过期
//     */
//    public void almostOverdue(){
//        try {
//            String token = this.getPara("token");
//            int userId = JwtUtils.validateJWTForUserId(token);
//            if (userId == 0) {
//                renderJson(weixinTokenFail());
//                return;
//            }
//            //获取当前日期
//            Date dat = new Date();
//            String dateStr = format1.format(dat);
//            Date date1 = format1.parse(dateStr);
//            //获取3天后日期
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DAY_OF_YEAR,3);
//            Date dat2 = calendar.getTime();
//            String data2Str = format1.format(dat2);
//            String select ="SELECT b.id AS lsh,a.id AS alsh,a.bookdetailid ,b.title,d.barcode ,b.author ,ifnull(b.publisher,'') as publisher ,ifnull(b.publishdate,'') as publishdate ,a.continuenum ,a.duetime  ";
//            StringBuilder where = new StringBuilder("FROM t_jieyue a LEFT JOIN t_book_detail d ON d.id = a.bookdetailid LEFT JOIN t_book b ON d.bookid = b.id WHERE a.readerid =? AND a.duetime <= ? AND a.duetime >= ? ORDER BY a.duetime DESC");
//            Page<Record> page = Db.paginate(this.getParaToInt("pageIndex", 1), 10, select, where.toString(),userId,data2Str,dateStr);
//            for (Record r : page.getList()){
//                String fsrqStr = r.getStr("duetime");
//                Date fsrq = format1.parse(fsrqStr);
//                int ts = (int) ((fsrq.getTime() - date1.getTime()) / (1000*3600*24));
//                r.set("dueday",ts);
//            }
//            formatDate(page.getList(),format1);
//            renderJson(weixinSuccess(page));
//        }catch(Exception e){
//            e.printStackTrace();
//            renderJson(weixinFail("操作失败"));
//        }
//    }
//


}
