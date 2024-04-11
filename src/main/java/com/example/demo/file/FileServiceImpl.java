package com.example.demo.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.auth.AuthRequest;
import com.example.demo.user.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FileServiceImpl implements FileService {

	@Value("${spring.cloud.gcp.storage.bucket}")
	String bucketName;

	@Autowired
	FileMapper fileMapper;

	@Override
	public String uploadFiles(UploadFile uploadFile) throws FileNotFoundException, IOException {
		System.out.println(bucketName);
		String keyFileName = "projectb-419201-70f6627fba41.json";
		InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();
		Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(keyFile)).build()
				.getService();
		MultipartFile multipartFile = uploadFile.getFile();
		String originalFilename = multipartFile.getOriginalFilename();
		String fileName = StringUtils.cleanPath(originalFilename);

		String objectName = UUID.randomUUID().toString() + "_" + fileName;
		BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName)
				.setContentType(multipartFile.getContentType()).build();

		Blob blob = storage.create(blobInfo, uploadFile.getFile().getInputStream());

		String uri = UriComponentsBuilder.newInstance().scheme("https").host("storage.googleapis.com")
				.path("/" + bucketName + "/" + objectName).build().toUriString();

		UploadFile fileInfo = new UploadFile();

		fileInfo.setPath("https://storage.googleapis.com/" + bucketName);
		fileInfo.setOriName(originalFilename);
		fileInfo.setSaveName(objectName);
		fileInfo.setUri(uri);
		fileInfo.setUserID(uploadFile.getUserID());
		fileMapper.insertFile(fileInfo);

//		String downloadLink = "https://storage.googleapis.com/" + bucketName + "/" + originalFilename;
//		System.out.println(fileName);
//		System.out.println(downloadLink);

		return objectName;
	}

	@Override
	public String getDownLink(String saveName) {
		String[] part=saveName.split("_");
		String originalFilename=part[1];
//	    String encodedFilename = URLEncoder.encode(originalFilename, StandardCharsets.UTF_8);
	    String encodedFilename = URLEncoder.encode(saveName, StandardCharsets.UTF_8)
	    		.replaceAll("\\+", "%20")
	    		.replaceAll("\\%21", "!")
	            .replaceAll("\\%27", "'")
	            .replaceAll("\\%28", "(")
	            .replaceAll("\\%29", ")")
	            .replaceAll("\\%7E", "~");
//		String encodedFilename = UriComponentsBuilder.newInstance()
//	            .queryParam("filename", originalFilename)
//	            .build().encode().toUriString();
//		return "https://storage.googleapis.com/" + bucketName + "/" + saveName.replace("_", "_%5F");
		return "https://storage.googleapis.com/" + bucketName + "/" + encodedFilename;
	}

	@Override
	public List<UploadFile> getFilesByUserID(String userID) {
		return fileMapper.getFileUserID(userID);
	}
	
	@Override
	public void deleteFileBucket(String saveName) throws FileNotFoundException, IOException {
		// 파일 이름 추출
		String[] part=saveName.split("_");
		if (part.length < 2) {
            throw new IllegalArgumentException("잘못된 파일 이름 형식입니다.");
        }
		String oriName=part[1];
		System.out.println(oriName);
		// 저장소 정보
		String keyFileName = "projectb-419201-70f6627fba41.json";
		InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();
	    Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(keyFile)).build()
	            .getService();
		
		BlobId blobId = BlobId.of(bucketName, oriName);
		
		// gcp에서 파일 삭제
		boolean deleted = storage.delete(blobId);
		if (deleted) {
//			fileMapper.deleteFileSaveName(oriName);
			System.out.println("파일 삭제 성공");
		} else {
			throw new IOException("파일 삭제 실패");
		}
	}

	@Override
	public void deleteFileDB(String saveName) {
		try {
			// 데이터베이스에서 찾기
	        String storedSaveName = fileMapper.findSaveName(saveName);
	        
	        if (storedSaveName != null) {
	        	// 데이터베이스 삭제
	            fileMapper.deleteFileSaveName(storedSaveName);
	            System.out.println("데이터베이스에서 파일 삭제했습니다.");
	        } else {
	            System.out.println("UUID에 해당하는 파일이 데이터베이스에 없습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("데이터베이스에서 파일을 삭제하는 동안 오류가 발생했습니다.");
	    }
	}

	@Override
	public Integer findFileID(String saveName) {
		return fileMapper.findFileID(saveName);
	}

	@Override
	public String getLinkByFileID(Integer fileID) {
		
		return fileMapper.getLinkByFileID(fileID);
	}

	

	
}
