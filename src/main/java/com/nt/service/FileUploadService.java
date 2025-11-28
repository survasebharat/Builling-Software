package com.nt.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	//upload File
	String uploadFile(MultipartFile file);
	
	//delete The Uploaded File
	boolean deleteFile(String imgUrl);
}
