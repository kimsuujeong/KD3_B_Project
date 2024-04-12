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
	
	// 관계자 인증 신청에 보내는 내용 
	@Override
	public void authRequest(AuthRequest request, String name, String link , int i) {
		
		try {
			// 파일 업로드
			MultipartFile file=request.getFile();
			UploadFile uploadFile=new UploadFile();
			
			uploadFile.setUserID(request.getUserID());
			uploadFile.setFile(file);
			// 저장된 이름으로 파일 아이디 가져와서 설정
			String saveName = fileService.uploadFiles(uploadFile);
			Integer fileID = fileService.findFileID(saveName);
			request.setFileID(fileID);
			request.setAuthorizeID(i);
		} catch (FileNotFoundException e) {// 예외처리 자세히 해야함
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 신청테이블에 저장
        authMapper.saveAuthRequest(request);
	}

	// 신청 내역 가져오기
	@Override
	public AuthRequest getAuthRequestUserIDType(String userID, int i) {
		
		return authMapper.getAuthrequest(userID,i);
	}

	
	
	
	

}
