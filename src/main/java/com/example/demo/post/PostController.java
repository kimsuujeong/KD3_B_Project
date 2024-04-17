package com.example.demo.post;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Adminpage.AdminService;
import com.example.demo.board.Board;
import com.example.demo.board.BoardService;
import com.example.demo.category.CategoryService;
import com.example.demo.file.FileService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class PostController {

	@Autowired
	BoardService boardService;
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	FileService fileService;
	@Autowired
	AdminService adminService;
	
//  post write
	@GetMapping(value = "/register/new/{categoryID}")
	public String writePost(@PathVariable(name = "categoryID") Integer categoryID, 
			HttpSession session,Model model) {
		Board board=new Board();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
	    
	    // 사용자가 관리자인지 여부를 확인합니다.
	    boolean isAuth = false;
	    if (loggedInUser != null) {
	        isAuth = adminService.isUserAuth(loggedInUser.getUserID());
	    }
	    boolean isAdmin = false;
	    if (loggedInUser != null) {
	        isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    }
		session.setAttribute("board", board);
		model.addAttribute("isAuth", isAuth);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("board", board);
		model.addAttribute("categoryID", categoryID);
		String url = (categoryID==1) ? "/Write/Companywrite1" : "/Write/Artistwrite1";
		return url;
	}
	@PostMapping(value = "/register/new/{categoryID}")
    public String registerNewBoard(@PathVariable(name = "categoryID") Integer categoryID, 
    		@RequestParam("postName") String postName,
    		@RequestParam("eventStart") Date eventStart,
    		@RequestParam("eventEnd") Date eventEnd,
    		@RequestParam("authorLink") String authorLink,
    		@RequestParam("costID") Integer costID,
            HttpSession session) {
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
	        // 로그인되지 않은 경우 로그인 페이지로 이동하도록 처리
	        return "redirect:/login";
	    }
		Board board=new Board();
		
		board.setPostName(postName);
		board.setEventStart(eventStart);
		board.setEventEnd(eventEnd);
		board.setAuthorName(loggedInUser.getUserName());
		board.setUserID(loggedInUser.getUserID());
		board.setAuthorLink(authorLink);
		board.setCostID(costID);
		board.setCategoryID(categoryID);
        // 세션에 데이터 저장
        session.setAttribute("board", board);
        return "redirect:/board/register/new/{categoryID}/2";
    }

	@GetMapping(value = "/register/new/{categoryID}/2")
	public String writePost2(@PathVariable(name = "categoryID") Integer categoryID
			, HttpSession session,
            Model model) {
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
	        // 로그인되지 않은 경우 로그인 페이지로 이동하도록 처리
	        return "redirect:/login";
	    }
		Board board = (Board) session.getAttribute("board");
		if (board == null) {
			// 세션에 저장된 데이터가 없는 경우, 오류 처리 또는 초기화를 수행할 수 있음
			return "redirect:/board/register/new/{categoryID}";
		}
		model.addAttribute("categoryID", categoryID);
		model.addAttribute("board", board);
		String url = (categoryID==1) ? "/Write/Companywrite2" : "/Write/Artistwrite2";
		return url;
	}
	
	@PostMapping(value = "/register/new/{categoryID}/2")
    public String registerNewBoard2(@PathVariable(name = "categoryID") Integer categoryID, 
            @RequestParam("file")MultipartFile file, 
    		@RequestParam("content") String content,
    		HttpSession session,Model model) {
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
	        // 로그인되지 않은 경우 로그인 페이지로 이동하도록 처리
	        return "redirect:/login";
	    }
		Board sessionBoard = (Board) session.getAttribute("board");
		if (sessionBoard == null) {
            // 세션에 저장된 데이터가 없는 경우, 오류 처리 또는 초기화를 수행할 수 있음
            return "redirect:/board/register/new/{categoryID}";
        }

        sessionBoard.setFile(file);
        sessionBoard.setUserID(loggedInUser.getUserID());
        sessionBoard.setCategoryID(categoryID);
		sessionBoard.setContent(content);
		System.out.println(sessionBoard);
        postService.saveBoard(sessionBoard);
        return "redirect:/board/{categoryID}"; 
    }
	@PostMapping("/uploadImageForCKEditor")
    public ResponseEntity<String> uploadImageForCKEditor(@RequestParam("upload") MultipartFile file) {
        try {
            // 이미지를 저장하고 저장된 파일의 URL을 반환하는 서비스 메서드 호출
            String imageUrl = fileService.uploadImageForCKEditor(file);
            // 클라이언트로 이미지 URL을 반환
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드에 실패했습니다.");
        }
    }
	
//  post delete
	@GetMapping(value = "/delete/{postID}")
	public String delete(@PathVariable(name = "postID") Integer postID,HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		Board board = boardService.getPostById(postID);
		if (loggedInUser == null || !loggedInUser.getUserID().equals(board.getUserID())) {
	        // 권한이 없는 경우 뒤로가기
	        return "redirect:/";
	    }
		postService.delete(postID);
		return "redirect:/";
	}
	
//  post modify (수정)
	@GetMapping(value = "/modify/{postID}")
	public String modifyPost(
			@PathVariable(name = "postID") Integer postID,
			HttpSession session ,Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

	    // 로그인한 사용자 정보 확인
	    if (loggedInUser == null) {
	        // 로그인되지 않은 경우 로그인 페이지로 이동하도록 처리
	        return "redirect:/login";
	    }	    
		Board board = boardService.getPostById(postID);
		if(board==null) {
			return "redirect:/";
		}
		if (!loggedInUser.getUserID().equals(board.getUserID())) {
	        // 작성자가 아닌 경우 
	        return "redirect:/";
	    }
//        board.setPostName(board_temp.getPostName());
//        board.setContent(board_temp.getContent());
//		board.setAuthorName(board_temp.getAuthorName());
//		board.setAuthorLink(board_temp.getAuthorLink());
//		board.setEventStart(board_temp.getEventStart());
//		board.setEventEnd(board_temp.getEventEnd());
		
		model.addAttribute("board", board);
		model.addAttribute("postID", postID);
		
		String url = (board.getCategoryID()==1) ? "/Write/Companywrite1" : "/Write/Artistmodify1";
		return url;
	}
	
	@PostMapping(value = "/modify/{postID}")
	public String updatePost(@PathVariable(name = "postID") Integer postID, 
			@ModelAttribute("board") Board board,
			
    		@RequestParam("postName") String postName,
    		@RequestParam("eventStart") Date eventStart,
    		@RequestParam("eventEnd") Date eventEnd,
    		@RequestParam("authorLink") String authorLink,
    		@RequestParam("costID") Integer costID,
			HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null || !loggedInUser.getUserID().equals(board.getUserID())) {
	        // 권한이 없는 경우 오류 처리
	        return "redirect:/";
	    }
		board.setPostName(postName);
		board.setEventStart(eventStart);
		board.setEventEnd(eventEnd);
		board.setAuthorName(loggedInUser.getUserName());
//		board.setUserID(loggedInUser.getUserID());
		board.setAuthorLink(authorLink);
		board.setCostID(costID);

		
		postService.updateBoard(board);

		return "redirect:/board/modify/{postID}/2";
	}
	@GetMapping(value = "/modify/{postID}/2")
	public String modifyPost2(
			@PathVariable(name = "postID") Integer postID,
			HttpSession session ,Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		// 로그인한 사용자 정보 확인
		if (loggedInUser == null) {
			// 로그인되지 않은 경우 로그인 페이지로 이동하도록 처리
			return "redirect:/login";
		}	    
		Board board = boardService.getPostById(postID);
		if(board==null) {
			return "redirect:/";
		}
		if (!loggedInUser.getUserID().equals(board.getUserID())) {
			// 작성자가 아닌 경우 
			return "redirect:/";
		}

		
		model.addAttribute("board", board);
		model.addAttribute("postID", postID);
		
		String url = (board.getCategoryID()==1) ? "/Write/Companywrite2" : "/Write/Artistmodify2";
		return url;
	}
	
	@PostMapping(value = "/modify/{postID}/2")
	public String updatePost2(@PathVariable(name = "postID") Integer postID, 
			@ModelAttribute("board") Board board,
			@PathVariable(name = "categoryID") Integer categoryID, 
            @RequestParam("file")MultipartFile file, 
    		@RequestParam("content") String content,
			HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (loggedInUser == null || !loggedInUser.getUserID().equals(board.getUserID())) {
			// 권한이 없는 경우 오류 처리
			return "redirect:/";
		}
		board.setFile(file);
//        board.setUserID(loggedInUser.getUserID());
//        board.setCategoryID(categoryID);
		board.setContent(content);
		postService.updateBoard(board);
		return "redirect:/";
	}
}
