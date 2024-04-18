package com.example.demo.like;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Adminpage.AdminService;
import com.example.demo.board.Board;
import com.example.demo.board.BoardService;
import com.example.demo.file.FileService;
import com.example.demo.post.ImageFile;
import com.example.demo.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class LikeController {

	@Autowired
	LikeService likeService;
	@Autowired
	BoardService boardService;
	@Autowired
	FileService fileService;
	@Autowired
	AdminService adminService;
	
	// 관심 목록 리스트
	@RequestMapping(value = "/savelist")
	public ModelAndView list(HttpSession session, ModelAndView mv) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return new ModelAndView("redirect:/login");
		}
	    // 사용자가 관리자인지 여부를 확인합니다.
	    boolean isAdmin = false;
	    if (loggedInUser != null) {
	        isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    }
	    // 유저아이디 가져오기
		String userID = loggedInUser.getUserID();
		
		if(userID != null) {
			List<Like> list = likeService.list(userID);
			List<Board> posts=new ArrayList<>();
			for (Like like : list) {
	            // 각 like에 해당하는 post 정보를 가져와서 posts 리스트에 추가
	            Board post = boardService.getPostByLikeID(like.getPostID());
	            posts.add(post);
	        }
			List<String> imageLinks = new ArrayList<>();
	        for (Board post : posts) {
	            if (post.getFileID() != null) {// 파일이 있으면 가져옴
	            	// 파일아이디로 image 정보 가져오기
	                ImageFile file = boardService.getImageFile(post.getFileID());
	                // 이미지 정보로 주소 가져오기
	                String fileLink = fileService.getDownLink(file.getSaveImName());
	                imageLinks.add(fileLink);
	            } else {
	                // 이미지가 없는 경우 빈 문자열을 추가합니다.
	                imageLinks.add("");
	            }
	        }
			map.put("list", list);
			map.put("posts", posts);
	        map.put("count", posts.size());
	        mv.addObject("isAdmin", isAdmin);
	        mv.addObject("imageLinks", imageLinks);
			mv.setViewName("/MyPage/mypage1");
			mv.addObject("map", map);
			return mv;
		}
		else {
			return new ModelAndView("/");
		}
	}
	
	// 관심 목록 추가
	@RequestMapping(value = "/savelist/insert", method = {RequestMethod.POST})
	public String insert(Like like, HttpSession session) {
		
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
	        // 로그인되지 않은 경우 로그인 페이지로 이동하도록 처리
	        return "redirect:/login";
	    }
		
		String userID = loggedInUser.getUserID();

		
		like.setUserID(userID);
		Integer postID=like.getPostID();
		Board post= boardService.getPostById(postID);
		// 관심 목록에 있는지 검사
		int count = likeService.countLike(like.getPostID(), userID);
		if(count == 0) {
			likeService.insert(like);
			return "redirect:/board/"+post.getCategoryID()+"/"+post.getPostID();
		} else {
			return "redirect:/board/"+post.getCategoryID()+"/"+post.getPostID();
		}
	
//		return "redirect:/savelist";
	}
	
	// 관심 목록 삭제
	@RequestMapping(value = "/savelist/delete/{likeID}")
	public String delete(@PathVariable(name = "likeID") Integer likeID) {
		// 삭제
		likeService.delete(likeID);
		return "redirect:/savelist";
	}
	
}