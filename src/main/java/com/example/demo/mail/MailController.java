package com.example.demo.mail;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.redis.RedisUtil;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailController {

	@Autowired
	private final MailService mailService;

	private int number;

	// 인증 이메일 전송
	public HashMap<String, Object> mailSend(@RequestParam("mail") String mail) {

		HashMap<String, Object> map = new HashMap<>();

		try {

			number = mailService.sendMail(mail);
			String num = String.valueOf(number);

			map.put("success", Boolean.TRUE);
			map.put("number", num);

		} catch (Exception e) {

			map.put("success", Boolean.FALSE);
			map.put("error", e.getMessage());

		}

		return map;

	}

	// 승인 이메일 전송
	public HashMap<String, Object> approvalMailSend(@RequestParam("mail") String mail) {

		HashMap<String, Object> map = new HashMap<>();

		try {

			mailService.sendCreateApprovalMail(mail);

			map.put("success", Boolean.TRUE);

		} catch (Exception e) {

			map.put("success", Boolean.FALSE);
			map.put("error", e.getMessage());

		}

		return map;

	}

	// 거절 이메일 전송
	public HashMap<String, Object> refusalMailSend(@RequestParam("mail") String mail) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			
			mailService.sendCreateRefusalMail(mail);

			map.put("success", Boolean.TRUE);

		} catch (Exception e) {

			map.put("success", Boolean.FALSE);
			map.put("error", e.getMessage());

		}

		return map;

	}

	// 인증번호 일치여부 확인
	@GetMapping("/mailCheck")
	public ResponseEntity<?> mailCheck(@RequestParam("userNumber") String userNumber) {

		boolean isMatch = userNumber.equals(String.valueOf(number));

		System.out.println("성공했습니다.");

		return ResponseEntity.ok(isMatch);

	}

}
