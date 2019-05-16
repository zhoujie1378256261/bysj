package com.tools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.config.SystemConstant;
import com.jfinal.json.Json;
import com.jfinal.json.JsonManager;
import com.utils.CheckResult;
import io.jsonwebtoken.*;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt加密和解密的工具类
 */
public class JwtUtils {

	/**
	 * 签发JWT
	 *
	 * @param userId
	 * @param info
	 * @param ttlMillis 有效期时长
	 * @return String
	 */
	public static String createJWT(String userId, Map info, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
				.claim("userId", userId)
				.claim("info", Json.getJson().toJson(info))
				.setIssuedAt(now)      // 签发时间
				.signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
		if (ttlMillis >= 0) {
			//取得当前日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(now);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//取得当日3点的毫秒数
			long timeTemp = 0;
			try {
				timeTemp = sdf2.parse(dateStr + " 03:00:00").getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//过期时间 = 当日3点 + 有效期时长
//			long expMillis = timeTemp + ttlMillis;
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			builder.setExpiration(expDate); // 过期时间
		}
		return builder.compact();
	}
	/**
	 * 验证JWT
	 * @param jwtStr
	 * @return
	 */
	public static CheckResult validateJWT(String jwtStr) {
		CheckResult checkResult = new CheckResult();
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setErrCode(SystemConstant.JWT_ERRCODE_EXPIRE);
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setErrCode(SystemConstant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setErrCode(SystemConstant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		}
		return checkResult;
	}
	public static int validateJWTForUserId(String jwtStr) {
		if(StringUtils.isEmpty(jwtStr)){
			return 0;
		}
		try {
			return Integer.parseInt((String)validateJWT(jwtStr).getClaims().get("userId"));

		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	public static Map<String,Object> validateJWTForMap(String jwtStr) {
		if(StringUtils.isEmpty(jwtStr)){
			return null;
		}
		try {
			//int userid = Integer.parseInt((String)validateJWT(jwtStr).getClaims().get("userId"));
			String info = (String)validateJWT(jwtStr).getClaims().get("info");
			return JSON.parseObject(info);
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成密钥
	 * @return
	 */
	private static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(SystemConstant.JWT_SECERT);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}
	
	/**
	 * 解析JWT字符串
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	private static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwt)
			.getBody();
	}

	public static void main(String[] args) throws InterruptedException {
		//小明失效 10s
		Map map = new HashMap();
		map.put("orgid",111);
		map.put("orgname","sdfasfdsafds");
		String sc = createJWT("1",map, 1000*60*500);
		System.out.println(sc);
		//System.out.println(validateJWT(sc).getErrCode());
		System.out.println(validateJWTForMap(sc));

	}
}
