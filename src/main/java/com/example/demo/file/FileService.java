package com.example.demo.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.User;

public interface FileService {

	String uploadFiles(UploadFile uploadFile) throws FileNotFoundException, IOException;

	void deleteFileBucket(String saveName) throws FileNotFoundException, IOException;

	void deleteFileDB(String uuid);

	String getDownLink(String saveName);

	List<UploadFile> getFilesByUserID(String userID);

	Integer findFileID(String saveName);

	String getLinkByFileID(Integer fileID);

	
	
}
