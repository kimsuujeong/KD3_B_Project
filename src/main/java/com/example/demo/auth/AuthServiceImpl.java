package com.example.demo.auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.file.FileService;
import com.example.demo.file.UploadFile;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	AuthMapper authMapper;

	@Autowired
	FileService fileService;
	
	@Override
	public void authRequest(AuthRequest request, String name, String link , int i) {
		
		try {
			MultipartFile file=request.getFile();
			UploadFile uploadFile=new UploadFile();
			
			uploadFile.setUserID(request.getUserID());
			uploadFile.setFile(file);
			String saveName = fileService.uploadFiles(uploadFile);
			Integer fileID = fileService.findFileID(saveName);
			request.setFileID(fileID);
			request.setAuthorizeID(i);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        authMapper.saveAuthRequest(request);
	}

	@Override
	public AuthRequest getAuthRequestByUserIDAndType(String userID, int i) {
		
		return authMapper.getAuthrequest(userID,i);
	}

	
	
	
	

}
