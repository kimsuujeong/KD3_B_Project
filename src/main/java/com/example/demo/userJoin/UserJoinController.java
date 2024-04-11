package com.example.demo.userJoin;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mail.MailController;
import com.example.demo.redis.RedisUtil;
import com.example.demo.user.User;

@Controller
public class UserJoinController {

	@Autowired
	UserJoinService userJoinService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private MailController mailController;

	private String TokenEmail = null; // 이메일 요청 처리 유,무
	private String Email = null; // 이메일 토큰 처리 유,무

	@GetMapping("/joinForm")
	public String joinForm() {
		return "JoinPage/join";
	}
	// 이메일 요청 처리
	@ResponseBody
	@PostMapping("/submitEmailToken")
	public ResponseEntity<Map<String, String>> postEmail(@RequestParam("email") String email,
			@ModelAttribute User userDTO, Model model) {
	    Map<String, String> response = new HashMap<>();

	    try {
	        if (userJoinService.emailchk(email) != 1) {
	        	
	        	mailController.mailSend(email); // 이메일 보내기
	        	
	            response.put("redirectUrl", "http://localhost:8085/joinForm");
	            response.put("message", "토큰이 보내졌습니다.");
	            
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
	            
	            Email = TokenEmail;
	            
	            System.out.println("Email : " + Email);
	            
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
	public int idCheck(@RequestParam("id") String id, @RequestParam("type") String type){
		
		int cnt = userJoinService.idchk(id);
		
		if (cnt==1) {
			System.out.println(id); // test
			return 1;
		}
		
		System.out.println(id); //test
		return 0;
		
	}
	
	@GetMapping("/JoinPage/c")
	public String showJoinPageC(Model model) {
	    // 모델에 필요한 데이터를 추가하거나 로직을 수행한 후에
	    // 해당하는 HTML 파일의 경로를 리턴합니다.
	    return "JoinPage/c";
	}

	@GetMapping("/JoinPage/d")
	public String showJoinPageD(Model model) {
	    // 모델에 필요한 데이터를 추가하거나 로직을 수행한 후에
	    // 해당하는 HTML 파일의 경로를 리턴합니다.
	    return "JoinPage/d";
	}

	@GetMapping("/JoinPage/e")
	public String showJoinPageE(Model model) {
	    // 모델에 필요한 데이터를 추가하거나 로직을 수행한 후에
	    // 해당하는 HTML 파일의 경로를 리턴합니다.
	    return "JoinPage/e";
	}


	
	// 아이디 중복 체크 처리
	@PostMapping("/nicknameCheck")
	@ResponseBody
	public int nicknameCheck(@RequestParam("nickname") String nickname, @RequestParam("type") String type){
		
		int cnt = userJoinService.namechk(nickname);
		
		if (cnt==1) {
			System.out.println(nickname); // test
			return 1;
		}
		
		System.out.println(nickname); //test
		return 0;
		
	}
	
	@PostMapping("/joinForm")
	@ResponseBody
	public ResponseEntity<Map<String, String>> joinForm(@RequestParam("email") String email,
														@RequestParam("email_Check_key") String email_Check_key,
														@RequestParam("mid_id") String mid_id,
														@RequestParam("mid_nickname") String mid_nickname,
														@RequestParam("password") String password,
														@RequestParam("password_ck") String password_ck,
														@ModelAttribute User userDTO, Model model){
		
		String getKey = redisUtil.getData(Integer.parseInt(email_Check_key));
		
		Map<String, String> response = new HashMap<>();
		
		if (TokenEmail == null) {
			System.out.println("이메일 인증 해주세요"); // test
			response.put("redirectUrl", "http://localhost:8085/joinForm");
			response.put("message", "이메일 인증 해주세요");

			return ResponseEntity.ok().body(response);
		}
		
		if (Email == null) {
			System.out.println("인증번호 확인 해주세요"); // test
			response.put("redirectUrl", "http://localhost:8085/joinForm");
			response.put("message", "인증번호 확인 해주세요");

			return ResponseEntity.ok().body(response);
		}

		
		if (!TokenEmail.equals(Email)) {
			System.out.println("토큰 번호를 보낸 이메일과 다릅니다."); // test
			response.put("redirectUrl", "http://localhost:8085/joinForm");
			response.put("message", "토큰 번호를 보낸 이메일과 다릅니다.");

			return ResponseEntity.ok().body(response);
		}
		
		System.out.println(Email);
		System.out.println(Email.isEmpty());
		System.out.println(Email.equals(email));
		System.out.println(email);
		
		if (Email.isEmpty() && Email.equals(email)) {
			System.out.println("토큰 번호를 확인해 주세요"); // test
			response.put("redirectUrl", "http://localhost:8085/joinForm");
			response.put("message", "토큰 번호를 확인해 주세요");

			return ResponseEntity.ok().body(response);
		}
		
		
		if (email_Check_key == null) {
			System.out.println("인증번호를 확인해 주세요"); // test
			response.put("redirectUrl", "http://localhost:8085/joinForm");
			response.put("message", "인증번호를 확인해 주세요");

			return ResponseEntity.ok().body(response);
		}
		
		if (!Email.equals(getKey)) {
			System.out.println("토큰번호를 다시 확인해 주세요");// test
			response.put("redirectUrl", "http://localhost:8085/joinForm");
			response.put("message", "토큰번호를 다시 확인해 주세요");

			return ResponseEntity.ok().body(response);
		}
		
		if (!password.equals(password_ck)) {
			System.out.println("비밀번호가 맞지 않습니다."); // test
			response.put("redirectUrl", "http://localhost:8085/joinForm");
			response.put("message", "비밀번호가 맞지 않습니다.");

			return ResponseEntity.ok().body(response);
		}

		try {
			
			if (userJoinService.idchk(mid_id)==1) {
				System.out.println("아이디를 다시 확인해 주세요"); // test
				response.put("redirectUrl", "http://localhost:8085/joinForm");
				response.put("message", "아이디를 다시 확인해 주세요");

				return ResponseEntity.ok().body(response);
			}
			
			if (userJoinService.namechk(mid_nickname)==1) {
				System.out.println("닉네임을 다시 확인해 주세요"); // test
				response.put("redirectUrl", "http://localhost:8085/joinForm");
				response.put("message", "닉네임을 다시 확인해 주세요");

				return ResponseEntity.ok().body(response);
			}
			
			if (Email.equals(email) && Email.equals(getKey) 
					&& userJoinService.idchk(mid_id)==0
					&& userJoinService.namechk(mid_nickname)==0
					&& password.equals(password_ck)) {
				
				userDTO.setEmail(email);
				userDTO.setUserID(mid_id);
				userDTO.setUserName(mid_nickname);
				userDTO.setPassword(password_ck);
				
				userJoinService.InsertUser(userDTO);
				
				System.out.println("회원가입 완료"); // test
				
				response.put("redirectUrl", "http://localhost:8085/Testmain");
				response.put("message", "회원가입 완료");

				return ResponseEntity.ok().body(response);
				
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("서버 오류 입니다."); // test
			response.put("redirectUrl", "http://localhost:8085/joinForm");
			response.put("message", "서버 오류 입니다.");

			return ResponseEntity.ok().body(response);
		}
		
		return null;
		
	}

}
