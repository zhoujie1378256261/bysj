package com.config;


import com.controller.master.UserController;
import com.jfinal.config.Constants;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import com.model._MappingKit;
import com.utils.ClassUtils;
import java.util.Iterator;
import java.util.Set;


/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API引导式配置
 */
public class MyJfinalConfig extends JFinalConfig {
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		//JFinal.start("src/main/webapp", 81, "/", 5);
		
		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		 JFinal.start("src/main/webapp", 80, "/");
	}

	/**
	 * 定时器插件
	 * 在此处添加定时任务
	 * 时间设置规则 参照jfinal官方文档
	 * @param me
	 */
	private void loderCron4jPlugin(Plugins me){
		Cron4jPlugin task = new Cron4jPlugin();
		//task.addTask("0 1 * * *",new RegionSummary());
		me.add(task);
		me.add(new EhCachePlugin(getClass().getClassLoader().getResource("resources/ehcache.xml")));
	}
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", true));
		//me.setBaseUploadPath(baseUploadPath);//设置文件上传路径
		//me.setInjectDependency(true); //设置可以注解依赖注入
		//me.setJsonFactory(new FastJsonFactory()); //设置json对象
		//me.setJsonFactory(new JacksonFactory()); //设置json对象
		//me(new ContextPathHandler("contex_path"));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		//me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
		//me.add("/blog", BlogController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		try{
			registAction(me,com.controller.sysconfig.SysConfigController.class.getPackage());
			//registAction(me,AuthController.class.getPackage());
			registAction(me,UserController.class.getPackage());
//			registAction(me,GoodsCategoryController.class.getPackage());
//			registAction(me,ShopAuthController.class.getPackage());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void registAction(Routes me,Package pack) throws Exception{
		Set<Class<?>> classSet = getClasses(pack);
		Iterator ite = classSet.iterator();
		while(ite.hasNext()){
			Class cls = (Class) ite.next();
			if(cls.isAnnotationPresent(ControllerInterface.class)){
				ControllerInterface first = (ControllerInterface)cls.getAnnotation(ControllerInterface.class);
				me.add(first.path(),cls);
			}
		}
	}

	public void configEngine(Engine me) {
		//me.addSharedFunction("/common/_layout.html"); //教育局-后台模板
		//me.addSharedFunction("/teachBefore/_beforeLayout.html"); //教育局-前台模板
		//me.addSharedFunction("/common/_paginate.html");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(druidPlugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		_MappingKit.mapping(arp);
		me.add(arp);
		loderCron4jPlugin(me);
		RedisPlugin newsRedis = new RedisPlugin("shop", PropKit.get("redis_host"),PropKit.get("redis_password"));
		me.add(newsRedis);
	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new AuthInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
	//	me.add(new AuthHandler());
	}

	private Set<Class<?>> getClasses(Package pack) throws Exception{
		return ClassUtils.getClasses(pack);
		/*if(JFinal.me().getConstants().getDevMode() == true){//如果是开发模式，读取WEB-INFO/classes目录下的文件

		}
		//如果不是开发模式，读取mes.jar包中的class
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		String packageName = pack.getName();
		String packageDirName = packageName.replace('.', '/');
		java.net.URL url = ReaderController.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");

		Enumeration enu = new JarFile(filePath).entries();
		while(enu.hasMoreElements()){
			JarEntry element = (JarEntry) enu.nextElement();
			String name = element.getName();
			if(name.indexOf(packageDirName)>-1 &&name.toUpperCase().endsWith(".CLASS")){
				String className = name.substring(packageName.length() + 1, name.length() - 6);
				classes.add(Class.forName(pack.getName()+"."+className));
			}
		}
		return classes;*/
	}
	public void afterJFinalStart() {
		//new Thread(new LoadData()).start();
		//new Thread(new RegionSummary()).start();
	}
}
