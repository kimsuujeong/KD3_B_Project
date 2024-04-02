package com.example.demo.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.redis.RedisUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender javaMailSender;
	private static final String senderEmail = "gsujeong91@gmail.com";
	private static int number;
	
	 @Autowired
	private final RedisUtil redisUtil;
	

	// 랜덤으로 숫자 생성
	public static int createNumber() {

		number = (int) (Math.random() * (90000)) + 100000;

		return number;
	}

	public MimeMessage CreateMail(String mail) {

		createNumber();

		MimeMessage message = javaMailSender.createMimeMessage();

		try {

			System.out.println("메일을 보냈습니다."); // test

			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, mail);
			message.setSubject("이메일 인증");

			String body = "";

			body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
			body += "<h1>" + number + "</h1>";
			body += "<h3>" + "감사합니다." + "</h3>";

			message.setText(body, "UTF-8", "html");

		} catch (MessagingException e) {

			e.printStackTrace();
			System.out.println("메일 보내기에 실패했습니다."); // test

		}

		return message;

	}

	public int sendMail(String mail) {

		MimeMessage message = CreateMail(mail);
		javaMailSender.send(message);
		
		System.out.println(mail);
		System.out.println(number);
        
        redisUtil.setDataExpire(number, mail, 60*5L);

		return number;

	}

}
