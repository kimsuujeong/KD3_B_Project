package com.example.demo.auth;

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
		
		// 파일 업로드
		MultipartFile file=request.getFile();
		if (file == null || file.isEmpty()) {
		    // 사용자에게 알림 메시지 전송
		    throw new IllegalArgumentException("파일을 첨부해주세요.");
		}
		UploadFile uploadFile=new UploadFile();
		
		uploadFile.setUserID(request.getUserID());
		uploadFile.setFile(file);
		// 저장된 이름으로 파일 아이디 가져와서 설정
		String saveName = fileService.uploadFiles(uploadFile);
		Integer fileID = fileService.findFileID(saveName);
		request.setFileID(fileID);
		request.setAuthorizeID(i);
		// 신청테이블에 저장
        authMapper.saveAuthRequest(request);
	}

	// 신청 내역 가져오기
	@Override
	public AuthRequest getAuthRequestUserIDType(String userID, int i) {
		
		return authMapper.getAuthrequest(userID,i);
	}

	
	
	
	

}
