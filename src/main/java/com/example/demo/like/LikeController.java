package com.example.demo.like;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/like")
public class LikeController {

	@Autowired
	LikeService likeService;
	
	// 관심 목록 리스트
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, ModelAndView mv) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		//String userID = (String) session.getAttribute("userID");
		String userID = "test1";
		
		if(userID != null) {
			List<Like> list = likeService.list(userID);
			map.put("list", list);
			map.put("count", list.size());
			mv.setViewName("/TestHtml/board/like_list");
			mv.addObject("map", map);
			return mv;
		}
		else {
			return new ModelAndView("/board");
		}
	}
	
	// 관심 목록 추가
	@RequestMapping(value = "/insert", method = {RequestMethod.POST})
	public String insert(Like like, HttpSession session) {
		
		//String userID = (String) session.getAttribute("userID");
		
		// 임시로 userID 설정 (로그인 연결 후 수정)
		String userID = "test1";
//		if(userID == null) {
//			return "redirect:/TestMain";
//		}
		
		like.setUserID(userID);
		
		// 관심 목록에 있는지 검사
		int count = likeService.countLike(like.getPostID(), userID);
		if(count == 0) {
			likeService.insert(like);
		} else {
			return "/TestHtml/board/like_popup";
		}
	
		return "redirect:/like/list";
	}
	
	// 관심 목록 삭제
	@RequestMapping(value = "/delete/{likeID}")
	public String delete(@PathVariable(name = "likeID") Integer likeID) {
		likeService.delete(likeID);
		return "redirect:/like/list";
	}
	
}