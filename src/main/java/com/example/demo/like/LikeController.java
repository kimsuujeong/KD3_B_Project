package com.example.demo.like;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/like")
public class LikeController {

	@Autowired
	LikeService likeService;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, ModelAndView mv) {
		
		Map<String, Object> map = new HashMap<>();
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
	
	@RequestMapping(value = "/insert", method = {RequestMethod.POST})
	public String insert(Like like, HttpSession session) {
		
		//String userID = (String) session.getAttribute("userID");
		
		String userID = "test1";
//		if(userID == null) {
//			return "redirect:/TestMain";
//		}
		
		like.setUserID(userID);
		likeService.insert(like);
		
		return "redirect:/like/list";
	}
	
	
	
	
}
