package com.audioapp.cms.service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import com.aliyun.oss.model.PutObjectRequest;
import com.audioapp.cms.listener.PutObjectProgressListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/*
 * 上传图片方法
 */
@Service
public class UploadService {
	
	@Value(value="${aliyun.endpoint}")
	private String endpoint;
	
	@Value(value="${aliyun.accessKeyId}")
	private String accessKeyId;
	
	@Value(value="${aliyun.accessKeySecret}")
	private String accessKeySecret;
	
	@Value(value="${aliyun.bucketName}")
	private String bucketName;
	
	public String uploadFile(InputStream inputStream, String extensionName){
	
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		ossClient.putObject(bucketName, uuid+"." + extensionName, inputStream);
	
		// 关闭OSSClient。
		ossClient.shutdown();
	
		return "http://bclh.oss-cn-zhangjiakou.aliyuncs.com/" + uuid +"." + extensionName;
	}

	public String uploadFile(File file, String extensionName, HttpSession session){

		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		ossClient.putObject(new PutObjectRequest(bucketName, uuid+"." + extensionName, file).
				<PutObjectRequest>withProgressListener(new PutObjectProgressListener(session)));

		// 关闭OSSClient。
		ossClient.shutdown();

		return "http://bclh.oss-cn-zhangjiakou.aliyuncs.com/" + uuid +"." + extensionName;
	}
}

