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
	private MailController mailController;

	@Autowired
	private RedisUtil redisUtil;

	private String Email = null;

	@GetMapping("/Testmain") // test home
	public String Testmain() {
		return "TestHtml/user/Testmain";
	}
	@GetMapping("/main") //home
	public String main() {
		return "MainPage/Main";
	}

	// Login
	@GetMapping("/login")
	public String loginForm() {
		return "LogIn/login";
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginForm(@RequestParam("userID") String userID,
			@RequestParam("password") String password, Model model, HttpSession httpSession) {

		Map<String, String> response = new HashMap<>();
		
		try {

			User user = userService.Login(userID, password);

			if (userID.isEmpty()) {

				response.put("redirectUrl", "http://localhost:8085/login");
				response.put("message", "아이디를 입력해 주세요");

				return ResponseEntity.ok().body(response);

			}

			if (password.isEmpty()) {
				
				response.put("redirectUrl", "http://localhost:8085/login");
				response.put("message", "비밀번호를 입력해 주세요");

				return ResponseEntity.ok().body(response);
				
			}

			if (user != null) { // 로그인 성공
				
				model.addAttribute("loggedInUser", user); // 세션에 사용자 정보 저장
				httpSession.setAttribute("loggedInUser", user);

				response.put("redirectUrl", "http://localhost:8085/Testmain");
				response.put("message", "로그인 성공 했습니다.");

				return ResponseEntity.ok().body(response);
				
			} else { 
				
				response.put("redirectUrl", "http://localhost:8085/login");
				response.put("message", "아이디와 비밀번호를 다시 확인해주세요.");

				return ResponseEntity.ok().body(response);
				
			}

		} catch (Exception e) { // 로그인 실패
			
			response.put("redirectUrl", "http://localhost:8085/Testmain");
			response.put("message", "서버 오류 입니다.");
			
		}
		
		return ResponseEntity.badRequest().body(response); // 서버 오류코드 넣기

	}

	// 아이디 찾기 이메일 받기
	@GetMapping("/FindID")
	public String FindID() {
		return "LogIn/searchId";
	}
	@PostMapping("/FindID")
	public ResponseEntity<Map<String, Object>> FindID(@RequestParam("email") String email) {

		Map<String, Object> response = new HashMap<>();

		try {

			if (userService.FindID(email).getEmail() != null) {

				mailController.mailSend(email); // 이메일 실제로 보내는 소스코드 입니다.
				// 하루에 보내는 이메일 한도가 정해져 있어서 주석처리 해놨습니다.

				response.put("redirectUrl", "http://localhost:8085/cerid");
				response.put("message", "인증번호가 전송 되었습니다.");

				this.Email = email;

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

				this.IdForEmail = getKey;

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

			String id = userService.FindID(IdForEmail).getUserID();

			model.addAttribute("data", id);

		} catch (Exception e) { // null값

		}

		return "LogIn/findId";
	}

	// FindPassword
	@GetMapping("/FindPW")
	public String FindPW() {
		return "LogIn/searchPw";
	}

	@PostMapping("/FindPW")
	public ResponseEntity<Map<String, String>> FindPW(@RequestParam("userID") String userID,
			@RequestParam("email") String email) {

		Map<String, String> response = new HashMap<>();

		try {

			String id = userService.FindPW(email, userID).getUserID();
			String mail = userService.FindPW(email, userID).getEmail();

			if (id.equals(userID) && mail.equals(mail)) {

				mailController.mailSend(email); // 이메일 실제로 보내는 소스코드 입니다.
				// 하루에 보내는 이메일 한도가 정해져 있어서 주석처리 해놨습니다.

				response.put("redirectUrl", "http://localhost:8085/cerpw");
				response.put("message", "인증번호가 전송 되었습니다.");

				this.Email = mail;

				return ResponseEntity.ok().body(response);

			}

		} catch (Exception e) {

			response.put("redirectUrl", "http://localhost:8085/FindPW");
			response.put("message", "아이디와 비밀번호가 맞지 않습니다.");

			return ResponseEntity.ok().body(response);

		}

		return ResponseEntity.badRequest().body(response);

	}

	@GetMapping("/cerpw")
	public String PWEmailToken() {
		return "LogIn/cerPW";
	}

	String pwEmail = null;

	// 비밀번호 찾기 토큰 확인
	@PostMapping("/cerpw")
	public ResponseEntity<Map<String, Object>> PWEmailToken(@RequestParam("token") String token) {

		Map<String, Object> response = new HashMap<>();
		String getKey = redisUtil.getData(Integer.parseInt(token));

		try {

			System.out.println("입력한 토큰: " + token);

			System.out.println("getKeyTest: " + getKey);

			System.out.println("아까 받은 이메일임: " + Email);

			if (getKey == null) {

				response.put("redirectUrl", "http://localhost:8085/cerpw");
				response.put("message", "존재하지 않는 토큰 입니다.");

				return ResponseEntity.ok().body(response);

			}

			if (Email.equals(getKey)) {

				response.put("redirectUrl", "http://localhost:8085/cerpw/pwrs");
				response.put("message", "토큰 인증 되었습니다.");

				pwEmail = Email;

				System.out.println(pwEmail);

				return ResponseEntity.ok().body(response);

			} else {

				response.put("redirectUrl", "http://localhost:8085/cerpw");
				response.put("message", "토큰을 다시 확인해주세요.");

				return ResponseEntity.ok().body(response);

			}

		} catch (Exception e) {

			System.out.println("존재하지 않는 토큰 입니다.");

		}

		return ResponseEntity.badRequest().body(response);

	}

	@GetMapping("cerpw/pwrs")
	public String pwrs() {
		return "LogIn/pwrs";
	}

	@PostMapping("cerpw/pwrs")
	public String pwrs(@RequestParam("pwNew") String pwNew) {


		if (pwEmail != null) {

			userService.Update(pwNew, pwEmail);

		} else {
			return "LogIn/pwrs"; // 서버 이상 페이지 추가 예정
		}

		return "redirect:/Testmain";
	}
	@GetMapping("login/bye") //회원탈퇴
	public String bye() {
		return "Login/bye";
	}

}
