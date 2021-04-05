package com.bjowernode.CRM.utils;

public class ServiceFactory {
	//传入对象返回代理类对象的过程
	public static Object getService(Object service){
		//.getProxy()必须要有这个方法才能调用出来代理类对象
		return new TransactionInvocationHandler(service).getProxy();
		
	}
	
}
