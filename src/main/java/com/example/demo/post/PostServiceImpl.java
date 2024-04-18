package com.example.demo.post;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.Board;
import com.example.demo.file.FileService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostMapper postMapper;
	
	@Autowired
	FileService fileService;
	
	public List<Board> getBoardList() {
		List<Board> boardList = Collections.emptyList();

		int boardTotalCount = postMapper.selectBoardTotalCount();

		if (boardTotalCount > 0) {
			boardList = postMapper.selectBoardList();
		}

		return boardList;
	}

	// 게시물 저장
	public void saveBoard(Board board) {
		MultipartFile file=board.getFile();
		if (file == null || file.isEmpty()) {
		    // 사용자에게 알림 메시지 전송
		    throw new IllegalArgumentException("파일을 첨부해주세요.");
		}
		ImageFile uploadFile=new ImageFile();
		
		uploadFile.setUserID(board.getUserID());
		uploadFile.setFile(file);
		// 저장된 이름으로 파일 아이디 가져와서 설정
		String saveName = fileService.uploadImFiles(uploadFile);
		Integer fileID = fileService.findImageFileID(saveName);
		board.setFileID(fileID);
		// 게시물 테이블에 저장
		postMapper.insert(board);
	}

	// board detail
	public Board getBoardDetail(Integer postID) {
		// TODO Auto-generated method stub
		return postMapper.selectBoardDetail(postID);
	}

	// board delete
	public void delete(Integer postID) {
		postMapper.delete(postID);
	}
	// 게시물 수정
	public void updateBoard(Board board) {
		// TODO Auto-generated method stub
		postMapper.update(board);
	}
}
