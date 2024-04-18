package com.example.demo.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Adminpage.AdminService;
import com.example.demo.file.FileService;
import com.example.demo.post.ImageFile;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	UserService userService;
	
	@Autowired
	FileService fileService;

	@Autowired
	AdminService adminService;
	
//	main page
	@GetMapping("/")
	public String main(Model model,HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
	    
	    // 사용자가 관리자인지 여부를 확인합니다.
	    boolean isAdmin = false;
	    if (loggedInUser != null) {
	        isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    }
		List<Board> posts = boardService.getAllPosts();
		model.addAttribute("posts", posts);
		model.addAttribute("isAdmin", isAdmin);
		return "/MainPage/Main";
	}

//	board page separate categoryId
	@GetMapping("/board/{categoryID}")
	public String showBoard3(@PathVariable(name = "categoryID") Integer categoryID, 
							Model model, Pageable page,HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
	    // 사용자가 관리자인지 여부를 확인합니다.
	    boolean isAdmin = false;
	    if (loggedInUser != null) {
	        isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    }
		// 카테고리 아이디별로 게시물 보이기
		Page<Board> posts = this.boardService.getPostsByCategoryId(categoryID, page);
		List<String> imageLinks = new ArrayList<>();
	    for (Board post : posts) {
	    	if (post.getFileID() != null) {// 파일아이디가 있을때만 가져옴
	    		// board에 fileID로 이미지 정보 가져오기
	            ImageFile file = boardService.getImageFile(post.getFileID());
	            // 이미지 정보로 주소 가져오기
	            String fileLink = fileService.getDownLink(file.getSaveImName());
	            imageLinks.add(fileLink);
	        } else {
	            // 이미지가 없는 경우 빈 문자열을 추가합니다.
	            imageLinks.add("");
	        }
	    }

	    model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("posts", posts);
		model.addAttribute("imageLinks", imageLinks);
		
		String url = (categoryID==1) ? "/BoardListPage/BoardListPageCompany" : "/BoardListPage/BoardListPageArtist";
		return url;
	}
//	post detail 
	@GetMapping("/board/{categoryID}/{postID}")
	public String showPostDetail(@PathVariable(name = "categoryID") Integer categoryID, 
			@PathVariable(name = "postID") Integer postID,  Model model,HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
	    
	    // 사용자가 관리자인지 여부를 확인합니다.
	    boolean isAdmin = false;
	    if (loggedInUser != null) {
	        isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    }
	    // postID에 따른 게시물 정보
		Board post = (Board) boardService.getPostById(postID);
		// 게시물의 파일아이디로 이미지 정보 가져오기
		ImageFile file=boardService.getImageFile(post.getFileID());
		// 이미지 주소 가져오기
		String fileLink=fileService.getDownLink(file.getSaveImName());
		// 조회수 증가
		boardService.visitCnt(postID);
		
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("post", post);
		model.addAttribute("image", fileLink);
		
		String url = (categoryID==1) ? "/BoardViewPage/BoardViewPageCompany" : "/BoardViewPage/BoardViewPageArtist";
		return url;
	}

	@RequestMapping("/board/{categoryID}/search")
	public String search(@PathVariable(name = "categoryID") Integer categoryID, Model model,
			@RequestParam(value="order", defaultValue="visitCnt") String order, 
			@ModelAttribute Search search, Pageable pageable) {
		// 검색한 결과 게시물 가져오기
		Page<Board> searchPost = boardService.searchCtg(categoryID, search, order, pageable);
		model.addAttribute("posts", searchPost);

		String url = (categoryID==1) ? "/BoardListPage/BoardListPageCompany" : "/BoardListPage/BoardListPageArtist";
		return url;
	}
	
	
}
