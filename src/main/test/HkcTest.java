import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.config.BaseController;
import com.jfinal.json.Json;
import com.jfinal.kit.Base64Kit;
import com.model.Book;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HkcTest {
    public static void main(String args[]){
        try {

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < 2; i++) {
                Map<String, Object> book = new HashMap<String, Object>();
                book.put("typecode", "A01");
                book.put("isbn", "289182/232");
                book.put("title", "毛泽东思想");
                book.put("bltitle", "并列提名");
                book.put("subtitle", "副题名");
                book.put("bookno", "索书号");
                book.put("publisher", "出版社");
                book.put("publishdate", "出版日期，格式为：2008-09-08");
                book.put("publishaddress", "出版地");
                book.put("price", "定价,带两位小数得数值");
                book.put("keyword", "主题词");
                book.put("edition", "版本班次");
                book.put("author", "作者");
                book.put("otherauthor", "其他责任人");
                book.put("moneysource", "经费来源");
                book.put("totalpages", "总页数");
                book.put("workmode", "著作方式");
                book.put("guobie", "国别");
                book.put("size", "尺寸");
                book.put("language", "语种");
                book.put("binding", "装订形式");
                book.put("congshuming", "丛书名");
                book.put("congshuauthor", "丛书责任人");
                book.put("shucihao", "书次号");
                book.put("juanci", "卷次");
                book.put("ceci", "册次");
                book.put("pici", "批次号");
                book.put("createtime","创建时间 格式为：2008-09-08 11:00:22");
                book.put("operatorid","编目人");
                book.put("updatetime","修改时间 格式为：2008-09-08 11:00:22");
                book.put("updateuserid","修改人");

                List<Map<String, Object>> mxlist = new ArrayList<Map<String, Object>>();
                for (int j = 0; j < 2; j++) {
                    Map<String, Object> mx = new HashMap<String, Object>();
                    mx.put("gcd", "A图书馆");
                    mx.put("barcode", "条形码");
                    mx.put("status", "1");
                    mx.put("putindate", "到馆日期,格式为：2008-09-08");
                    mx.put("destroyreason", "注销原因");
                    mxlist.add(mx);
                }
                book.put("mxlist", mxlist);
                list.add(book);
            }
            String encodedata = URLEncoder.encode(JSONArray.toJSON(list).toString(), "utf-8");
            System.out.println(encodedata);
            String decodedata = URLDecoder.decode(encodedata,"utf-8");
            System.out.println(decodedata);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
