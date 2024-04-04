package com.example.demo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.mail.MailController;
import com.example.demo.redis.RedisUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController { // 로그인, 아이디&비밀번호 찾기

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper usermapper; // test

	@Autowired
	private MailController mailController;

	@Autowired
	private RedisUtil redisUtil;

	@GetMapping("/Testmain") // test home
	public String Testmain() {
		return "TestHtml/user/Testmain";
	}

	// Login
	@GetMapping("/login") // login
	public String loginForm() {
		return "TestHtml/user/login";
	}

	@PostMapping("/login")
	public String loginForm(@RequestParam("userID") String userID, @RequestParam("password") String password,
			Model model,HttpSession httpSession) {
		User user = userService.Login(userID, password);

		// 예외처리로 변경하기
		if (userID.isEmpty()) {
			System.out.println("아이디를 입력해 주세요");
			return "TestHtml/user/login";
		}

		if (password.isEmpty()) {
			System.out.println("비밀번호를 입력해 주세요");
			return "TestHtml/user/login";
		}

		if (user != null) {
			// 로그인 성공
			model.addAttribute("loggedInUser", user); // 세션에 사용자 정보 저장
			httpSession.setAttribute("loggedInUser", user);
			System.out.println("성공입니다");
			System.out.println(user.toString());
			return "redirect:/";
		} else {
			// 로그인 실패
			System.out.println("실패입니다");
			return "TestHtml/user/login";
		}

	}

	// 아이디 찾기 이메일 받기

	private String Email = null;

	// FindID
	@GetMapping("/FindID")
	public String FindID() {
		return "LogIn/searchId";
	}

	@PostMapping("/FindID") // test FindID
	public ResponseEntity<Map<String, Object>> FindID(@RequestParam("email") String email) {

		Email = email;

		Map<String, Object> response = new HashMap<>();
//		userService.FindID(email); 

		try {

			if (usermapper.FindID(email).getEmail() != null) {

//				mailController.mailSend(email); // 이메일 실제로 보내는 소스코드 입니다.
				// 하루에 보내는 이메일 한도가 정해져 있어서 주석처리 해놨습니다.

				response.put("redirectUrl", "http://localhost:8085/cerid");
				response.put("message", "인증번호가 전송 되었습니다.");

				return ResponseEntity.ok().body(response); // 페이지 넘어가면서 ID가 보이는 형식

			}

		} catch (Exception e) {

			response.put("redirectUrl", "http://localhost:8085/FindID");
			response.put("message", "존재하지 않는 이메일 입니다.");

			return ResponseEntity.ok().body(response);

		}

		return ResponseEntity.badRequest().body(response);
	}

	@GetMapping("/cerid")
	public String IdEmailToken() {
		return "LogIn/cerId";
	}

	// 아이디 찾기 토큰 확인

	private String IdForEmail = null; // 아이디를 찾기 위한 이메일

	@PostMapping("/cerid")
	public ResponseEntity<Map<String, Object>> IdEmailToken(@RequestParam("token") String token) {

		Map<String, Object> response = new HashMap<>();
		String getKey = redisUtil.getData(Integer.parseInt(token));

		try {

			System.out.println("입력한 토큰: " + token);

			System.out.println("getKeyTest: " + getKey);

			System.out.println("아까 받은 이메일임: " + Email);

			if (getKey == null) {

				response.put("redirectUrl", "http://localhost:8085/cerid");
				response.put("message", "존재하지 않는 토큰 입니다.");

				return ResponseEntity.ok().body(response);

			}

			if (Email.equals(getKey)) {

				response.put("redirectUrl", "http://localhost:8085/cerid/userid");
				response.put("message", "토큰 인증 되었습니다.");

				IdForEmail = getKey;

				System.out.println(IdForEmail);

				return ResponseEntity.ok().body(response);

			} else {

				response.put("redirectUrl", "http://localhost:8085/cerid");
				response.put("message", "토큰을 다시 확인해주세요.");

				return ResponseEntity.ok().body(response);

			}

		} catch (Exception e) {

			// TODO: handle exception
			System.out.println("존재하지 않는 토큰 입니다.");

		}

		return ResponseEntity.badRequest().body(response);

	}

	// 아이디 띄워주는 페이지
	@RequestMapping("/cerid/userid")
	public String ShowIdToken(Model model) {

		try {
			
			String id = usermapper.FindID(IdForEmail).getUserID();

			model.addAttribute("data", id);
			
		} catch (Exception e) { //null값
			// TODO: handle exception
		}

		return "LogIn/findId";
	}

	// FindPassword
	@GetMapping("/FindPW")
	public String FindPW() {
		return "TestHtml/user/FindPW";
	}

	@PostMapping("/FindPW") // test FindPW 아이디랑 이메일로 보내서 토큰받는걸로 바꾸기
							// 바로 비밀번호 바꾸는 페이지로 넘어가기
	public String FindPW(@RequestParam("email") String email, @RequestParam("userID") String userID) {
		User user = userService.FindPW(email, userID);

		System.out.println(usermapper.FindPW(email, userID));

		return "TestHtml/user/FindPW";
	}

}
