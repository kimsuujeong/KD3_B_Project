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
//		List<Board> posts = boardService.getAllPosts();//모든 게시물
		List<Board> postsByLatest = boardService.getAllPostsByLatest(); // 최신순
	    List<Board> postsByViews = boardService.getAllPostsByViews(); // 조회수별
	    List<Board> postsByEnd = boardService.getAllPostsByEnd(); // 마감임박	    
//		List<String> imageLinks = new ArrayList<>();
		List<String> imageLinkLate = getImageLinks(postsByLatest);
		List<String> imageLinkView = getImageLinks(postsByViews);
		List<String> imageLinkEnd = getImageLinks(postsByEnd);

//		model.addAttribute("posts", posts);
//		model.addAttribute("imageLinks", imageLinks);
		model.addAttribute("postsByLatest", postsByLatest);
	    model.addAttribute("postsByViews", postsByViews);
	    model.addAttribute("postsByEnd", postsByEnd);
	    model.addAttribute("imageLinkLate", imageLinkLate);
	    model.addAttribute("imageLinkView", imageLinkView);
	    model.addAttribute("imageLinkEnd", imageLinkEnd);
		model.addAttribute("isAdmin", isAdmin);
		return "/MainPage/Main";
	}
	// 이미지 링크 목록을 가져오는 메서드
	private List<String> getImageLinks(List<Board> posts) {
	    List<String> imageLinks = new ArrayList<>();
	    for (Board post : posts) {
	        String fileLink = ""; // 이미지 링크 초기화
	        if (post.getFileID() != null) {// 파일아이디가 있을때만 가져옴
	            // board에 fileID로 이미지 정보 가져오기
	            ImageFile file = boardService.getImageFile(post.getFileID());
	            if (file != null) {
	                // 이미지 정보로 주소 가져오기
	                fileLink = fileService.getDownLink(file.getSaveImName());
	            }
	        }
	        imageLinks.add(fileLink); // 이미지 링크 목록에 추가
	    }
	    return imageLinks;
	}
	
	private List<String> getCostName(List<Board> posts) {
		List<String> costs=new ArrayList<>();
		for (Board post : posts) {
			if(post.getCostID()!=null) {
		    	Integer costID=post.getCostID();
		    	String costName=boardService.getCostName(costID);
		    	costs.add(costName);
	    	}else {
	    		costs.add("");
	    	} 
		}
		return costs;
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
		List<String> costs=new ArrayList<>();
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
	    	if(post.getCostID()!=null) {
		    	Integer costID=post.getCostID();
		    	String costName=boardService.getCostName(costID);
		    	costs.add(costName);
	    	}else {
	    		costs.add("");
	    	}
	    }
		
	    model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("posts", posts);
		model.addAttribute("imageLinks", imageLinks);
		model.addAttribute("costs", costs);
		
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
		// 조회수+카테고리 아이디 별 게시물
		List<Board> postsByVC = boardService.getPostsViewCategory(categoryID);
		List<String> imageLinkVC = getImageLinks(postsByVC);
		List<String> costs = getCostName(postsByVC);
		Integer costID=post.getCostID();
		String costName=boardService.getCostName(costID);
		model.addAttribute("postsByViews", postsByVC);
		model.addAttribute("imageLinkView", imageLinkVC);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("post", post);
		model.addAttribute("cost", costName);
		model.addAttribute("costs", costs);
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
		List<String> imageLinks = new ArrayList<>();
	    for (Board post : searchPost) {
	        if (post.getFileID() != null) {
	            // 파일아이디가 있을때만 이미지 정보 가져옴
	            ImageFile file = boardService.getImageFile(post.getFileID());
	            // 이미지 정보로 주소 가져오기
	            String fileLink = fileService.getDownLink(file.getSaveImName());
	            imageLinks.add(fileLink);
	        } else {
	            // 이미지가 없는 경우 빈 문자열을 추가
	            imageLinks.add("");
	        }
	    }
		
		model.addAttribute("posts", searchPost);
		model.addAttribute("imageLinks", imageLinks);
		String url = (categoryID==1) ? "/BoardListPage/BoardListPageCompany" : "/BoardListPage/BoardListPageArtist";
		return url;
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(value = "categoryID", required = false) Integer categoryID,
			@RequestParam(value = "order", defaultValue = "visitCnt") String order, @ModelAttribute Search search,
			Pageable pageable, Model model) {

		// categoryID가 없을 경우에는 전체 카테고리에서 검색을 수행
		Page<Board> searchResult;
		List<String> imageLinks = new ArrayList<>();
		if (categoryID != null) {
			searchResult = boardService.searchCtg(categoryID, search, order, pageable);
		} else {
			searchResult = boardService.search(search, pageable);
		}
		for (Board post : searchResult) {
	        if (post.getFileID() != null) {
	            // 파일아이디가 있을때만 이미지 정보 가져옴
	            ImageFile file = boardService.getImageFile(post.getFileID());
	            // 이미지 정보로 주소 가져오기
	            String fileLink = fileService.getDownLink(file.getSaveImName());
	            imageLinks.add(fileLink);
	        } else {
	            // 이미지가 없는 경우 빈 문자열을 추가
	            imageLinks.add("");
	        }
	    }
		// 검색 결과를 모델에 추가
		model.addAttribute("posts", searchResult);
		model.addAttribute("imageLinks", imageLinks);
		// 검색 결과 페이지로 이동
		String url = "/BoardListPage/BoardListPageCompany";
		return url;
	}
}
