package com.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class CommonUtils {

    /**
     * 将List转换为数组对象
     * @param list
     * @return
     */
    public static Object[] listToArray(List<Object> list){
        return list.toArray(new Object[list.size()]);
    }



    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return obj.equals("");
        } else if (obj instanceof Collection) {
            return ((Collection) obj).size() == 0;
        } else if (obj instanceof Map) {
            return ((Map) obj).size() == 0;
        } else if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        } else {
            return false;
        }
    }

    public static boolean isNotNullOrNotEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     *
     * @param queryurl 链接地址
     * @return
     */
    public static String z3950server(String queryurl){
        try {
            // 1.URL类封装了大量复杂的实现细节，这里将一个字符串构造成一个URL对象
            URL url = new URL(queryurl);
            // 2.获取HttpURRLConnection对象
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            // 3.调用connect方法连接远程资源
            connection.connect();
            // 4.访问资源数据，使用getInputStream方法获取一个输入流用以读取信息
            BufferedReader bReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));

            // 对数据进行访问
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // 关闭流
            bReader.close();
            // 关闭链接
            connection.disconnect();
            // 打印获取的结果
            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List z3950serverTolist(String queryurl){
        List relist = new ArrayList();
        String datastr = z3950server(queryurl);
        Map map = (Map)JSON.parse(datastr);
        if(null != map) {
            List<Record> expRecordList = Db.find("select formname,basename from t_sysmkf where formname is not null");
            List list = JSON.parseArray(String.valueOf(map.get("data")));
            for(int i = 0; i < list.size(); i ++ ) {
                JSONObject jo = (JSONObject) list.get(i);
                Iterator iteratorMap = jo.entrySet().iterator();
                Record record = new Record();
                while (iteratorMap.hasNext()){
                    Map.Entry<String, String> next = (Map.Entry<String,String>)iteratorMap.next();
                    String type = next.getKey();
                    for (int j = 0; j < expRecordList.size(); j ++ ) {
                        Record expRecord = expRecordList.get(j);
                        String basename = expRecord.get("basename");
                        String sysfield = expRecord.get("formname");
                        if(basename.equals(type)){
                            record.set(sysfield,next.getValue());
                        }else{
                            continue;
                        }
                    }
                }
                relist.add(record);
            }
            return relist;
        }
        return null;
    }

    public static Map showMarc(byte[] bytes) {
        Map map = new HashMap();
        try {
            // 读取此条数据的总长度
            byte marcB[] = new byte[5];
            for (int i = 0; i < 5; i++) {
                marcB[i] = bytes[i];
            }
            String marcS = new String(marcB);
            int marcLen = Integer.parseInt(marcS);
            // System.out.println(marcLen);
            // 读取数据基地址
            byte marcB2[] = new byte[5];
            for (int i = 0; i < 5; i++) {
                marcB2[i] = bytes[i + 12];
            }
            String marcS2 = new String(marcB2);
            int dataStart = Integer.parseInt(marcS2);
            int cmLength = dataStart - 24 - 1;
            byte marcB3[] = new byte[cmLength];
            for (int i = 0; i < cmLength; i++) {
                marcB3[i] = bytes[i + 24];
            }
            // 读取记录控制信息
            String marcS3 = new String(marcB3);
            int n = cmLength / 12;
            String controls[] = new String[n];
            for (int i = 0; i < n; i++) {
                controls[i] = marcS3.substring(i * 12, (i + 1) * 12);
            }
            // 读取数据区信息
            int dataLength = marcLen - dataStart - 1;
            byte data[] = new byte[dataLength];
            for (int i = 0; i < dataLength; i++) {
                data[i] = bytes[i + dataStart];
            }
            String OKData[][] = new String[n][2];
            for (int i = 0; i < n; i++) {
                OKData[i][0] = controls[i].substring(0, 3);
                int length = Integer.parseInt(controls[i].substring(3, 7));
                int start = Integer.parseInt(controls[i].substring(7));
                //不取每个字段数据最后的结束符
                byte temp[] = new byte[length - 1];
                for (int j = start; j < start + length - 1; j++) {
                    if (data[j] == 31) {
                        //分隔符
                        temp[j - start] = 124;
                    } else {
                        temp[j - start] = data[j];
                    }
                }
                OKData[i][1] = new String(temp, "GBK");
                if(!"001".equals(OKData[i][0])){
                    map.put(OKData[i][0],OKData[i][1]);
                }
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    public static List z3950format(List<Record> expRecordList){
        for (int i = 0; i < expRecordList.size(); i ++ ) {
            Record expRecord = expRecordList.get(i);

        }
        return null;
    }
}
