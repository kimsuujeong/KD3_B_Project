package com.example.demo.file;

import java.util.List;

public interface FileService {

	String uploadFiles(UploadFile uploadFile);

	void deleteFileBucket(String saveName);
	void deleteFileDB(String uuid);

	String getDownLink(String saveName);

	List<UploadFile> getFilesByUserID(String userID);

	Integer findFileID(String saveName);

	String getLinkByFileID(Integer fileID);

	
	
}
