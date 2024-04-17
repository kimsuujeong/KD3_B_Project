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

	public void insertFile(UploadFile file) {
		session.insert("insertFile",file);
	}

	public void deleteFileSaveName(String saveName) {
		session.delete("deleteFile",saveName);
	}

	public List<UploadFile> getAllFiles() {
		
		return session.selectList("getAllFiles");
	}

	public String findSaveName(String uuid) {
		return session.selectOne("findSaveName",uuid);
	}

	public String findSaveNameUserID(String userID) {
		return session.selectOne("findSaveNameUserID",userID);
	}

	public List<UploadFile> getFileUserID(String userID) {
		return session.selectList("getFileUserID",userID);
	}

	public Integer findFileID(String saveName) {
		return session.selectOne("findFileID", saveName);
	}

	public String getLinkByFileID(Integer fileID) {
		String path=session.selectOne("getPathFileID", fileID);
		String name=session.selectOne("getNameFileID", fileID);
		return path+"/"+name;
	}

	// board에 image 파일
	public void insertImFile(ImageFile fileInfo) {
		session.insert("insertImageFile",fileInfo);
	}

	public Integer findImageFileID(String saveName) {
		return session.selectOne("findImageFileID", saveName);
	}

	public String getSaveNameFileID(Integer fileID) {
		return session.selectOne("getsaveNameByFileID", fileID);
	}

	
	
}
