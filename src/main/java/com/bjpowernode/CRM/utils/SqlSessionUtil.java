package com.bjpowernode.CRM.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionUtil {
	
	private SqlSessionUtil(){}
	
	private static SqlSessionFactory factory;
	//静态代码块，类加载时一次执行就可以了，如果调方法时再加载就不合适了
	static{
		
		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		factory = new SqlSessionFactoryBuilder().build(inputStream);
		
	}
	//创建一个线程来存放session对象，放进去之后。后面的代码就可以从这里拿出来session对象了
	private static ThreadLocal<SqlSession> t = new ThreadLocal<SqlSession>();
	
	public static SqlSession getSqlSession(){
		
		SqlSession session = t.get();
		
		if(session==null){
			
			session = factory.openSession();
			t.set(session);
		}
		
		return session;
		
	}
	
	public static void myClose(SqlSession session){
		
		if(session!=null){
			session.close();
			//把线程清空，手动把session对象和线程剥离开来
			//清空后把线程归还给线程池（线程池是之前由服务器创建的）。
			//如果不剥离就会有奇奇怪怪的问题出现，所以一定不能忘记加！
			t.remove();
		}
		
	}
	
	
}
