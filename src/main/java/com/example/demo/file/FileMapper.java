package com.example.demo.file;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.post.ImageFile;

@Repository
public class FileMapper {

	@Autowired
	SqlSession session;

	// file에 파일 정보 저장
	public void insertFile(UploadFile file) {
		session.insert("insertFile",file);
	}

	// uuid로 파일 삭제
	public void deleteFileSaveName(String saveName) {
		session.delete("deleteFile",saveName);
	}

	// 모든 파일 가져오기
	public List<UploadFile> getAllFiles() {
		
		return session.selectList("getAllFiles");
	}

	// uuid로 정보 가져오기 -- ?안쓰는듯
	public String findSaveName(String uuid) {
		return session.selectOne("findSaveName",uuid);
	}

	// 유저아이디로 uuid가져오기 -- ?
	public String findSaveNameUserID(String userID) {
		return session.selectOne("findSaveNameUserID",userID);
	}

	public List<UploadFile> getFileUserID(String userID) {
		return session.selectList("getFileUserID",userID);
	}

	// uuid로 파일아이디 찾기
	public Integer findFileID(String saveName) {
		return session.selectOne("findFileID", saveName);
	}

	// 링크 만들기
	public String getLinkByFileID(Integer fileID) {
		String path=session.selectOne("getPathFileID", fileID);
		String name=session.selectOne("getNameFileID", fileID);
		return path+"/"+name;
	}

	// board에 image 파일 
	public void insertImFile(ImageFile fileInfo) {
		session.insert("insertImageFile",fileInfo);
	}

	// uuid로 파일아이디 가져오기
	public Integer findImageFileID(String saveName) {
		return session.selectOne("findImageFileID", saveName);
	}

	// 파일아이디로 uuid가져오기
	public String getSaveNameFileID(Integer fileID) {
		return session.selectOne("getsaveNameByFileID", fileID);
	}

	
	
}
