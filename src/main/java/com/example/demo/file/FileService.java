package com.example.demo.file;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.post.ImageFile;

public interface FileService {

	String uploadFiles(UploadFile uploadFile);
	
	String uploadImFiles(ImageFile imageFile);

	void deleteFileBucket(String saveName);
	void deleteFileDB(String uuid);

	String getDownLink(String saveName);

	List<UploadFile> getFilesByUserID(String userID);

	Integer findFileID(String saveName);

	String getSaveNameByFileID(Integer fileID);
	String getLinkByFileID(Integer fileID);

	Integer findImageFileID(String saveName);

	String uploadImageForCKEditor(MultipartFile file);


	
	
}
