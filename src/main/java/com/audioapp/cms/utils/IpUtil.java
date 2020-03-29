package com.audioapp.cms.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class IpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);
	
	private static final long workerIdBits = 5L;
	private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	
	public static String getServerIp() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			return address.getHostAddress();
		} catch (UnknownHostException e) {
			logger.error("获取本机IP地址失败", e);
			return "";
		}
	}
	
	/**
	 * 获取本机终端ID
	 * 当集群机器数量较多时，dataCenter也需要用到
	 * @return
	 */
	public static int getWorkerId() {
		String localAddress = getServerIp();
		if(StringUtils.isEmpty(localAddress)) {
			//如果获取本机IP地址失败，终端workerId则使用默认值
			return 0;
		}else {
			//内网IP地址HashCode取值为负数
			int hashcode = Math.abs(localAddress.hashCode());
			int workerId = (int)(hashcode % maxWorkerId + 1);
			return workerId;
		}
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("172.24.95.16");
		list.add("172.24.95.18");
		list.add("172.24.95.21");
		list.add("172.24.95.22");
		list.add("172.24.95.23");
		list.add("172.24.95.24");
		list.add("172.24.95.25");
		list.add("172.24.95.26");
		list.add("10.21.0.30");
		list.add("10.21.0.76");
		list.add("10.21.0.243");
		list.add("10.21.0.244");
		list.add("10.21.171.16");
		for(String item: list) {
			//Console.log(Math.abs(item.hashCode()));
			//Console.log(Math.abs(item.hashCode() % 32));
		}
	}
}
