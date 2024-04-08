package com.example.demo.userJoin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mail.MailController;
import com.example.demo.redis.RedisUtil;
import com.example.demo.user.User;

@Controller
public class UserJoinController {

	@Autowired
	UserJoinService userService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private MailController mailController;

	private String TokenEmail = null;
	private String Email = null;

	@GetMapping("/joinForm")
	public String joinForm() {
		return "JoinPage/join";
	}
	// 이메일 요청 처리
	@ResponseBody
	@PostMapping("/submitEmailToken")
	public ResponseEntity<Map<String, String>> joinForm(@RequestParam("email") String email,
			@ModelAttribute User userDTO, Model model) {
	    Map<String, String> response = new HashMap<>();

	    try {
	        if (userService.emailchk(email) != 1) {
	        	
	        	mailController.mailSend(email); // 이메일 보내기
	        	
	            response.put("redirectUrl", "http://localhost:8085/joinForm");
	            response.put("message", "토큰이 보내졌습니다.");
	            System.out.println(email);
	            
	            TokenEmail = email;
	            
	            return ResponseEntity.ok().body(response);
	        } else {
	            response.put("redirectUrl", "http://localhost:8085/joinForm");
	            response.put("message", "이미 가입된 이메일입니다.");
	            return ResponseEntity.ok().body(response);
	        }
	    } catch (Exception e) {
	        // 예외 처리
	    }
	    return ResponseEntity.badRequest().body(response);
	}
	// 이메일 토큰 처리
	@ResponseBody
	@PostMapping("/checkEmailToken") // 토큰 확인 요청 처리
	public ResponseEntity<Map<String, String>> checkEmailToken(@RequestParam("email_Check_key") String email_Check_key) {
	    
		System.out.println(email_Check_key);
		
		Map<String, String> response = new HashMap<>();
	    String getKey = redisUtil.getData(Integer.parseInt(email_Check_key));

	    try {
	        if (getKey == null) {
	            response.put("redirectUrl", "http://localhost:8085/joinForm");
	            response.put("message", "존재하지 않는 토큰입니다.");
	            return ResponseEntity.ok().body(response);
	        }

	        if (TokenEmail.equals(getKey)) {
	            response.put("redirectUrl", "http://localhost:8085/joinForm");
	            response.put("message", "토큰 인증되었습니다.");
	            
	            TokenEmail = Email;
	            
	            return ResponseEntity.ok().body(response);
	            
	        } else {
	            response.put("redirectUrl", "http://localhost:8085/cerpw");
	            response.put("message", "토큰을 다시 확인해주세요.");
	            return ResponseEntity.ok().body(response);
	        }
	        
	    } catch (Exception e) {
	        // 예외 처리
	    }
	    return ResponseEntity.badRequest().body(response);
	}
	// 아이디 중복 체크 처리
	@PostMapping("/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {
		
		int cnt = userService.idchk(id);
		
		return cnt;
		
	}
}
