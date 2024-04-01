package com.example.demo.mail;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MailController {
	
	private final MailService mailService = null;
    private int number; // 이메일 인증 숫자를 저장하는 변수

	// 인증 이메일 전송
	@PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(String mail) {
		
        HashMap<String, Object> map = new HashMap<>();

        try {
        	
            number = mailService.sendMail(mail);
            String num = String.valueOf(number);

            map.put("success", Boolean.TRUE);
            map.put("number", num);
            System.out.println("메일 보내기에 성공했습니다.");
            
        } catch (Exception e) {
        	
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
            System.out.println("메일 보내기에 실패했습니다.");
            
        }

        return map;
        
    }

	// 인증번호 일치여부 확인
    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber) {

        boolean isMatch = userNumber.equals(String.valueOf(number));

        return ResponseEntity.ok(isMatch);
        
    }

}
