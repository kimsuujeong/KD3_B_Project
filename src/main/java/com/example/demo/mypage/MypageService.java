package com.example.demo.myPage;

public interface MypageService {
	
	// MY PAGE_Form
	///////////////////////////////////////////////////////////////////////
	// 관심목록 > 관심태그
	
	// 관계자 인증
	
	// 개인정보 수정(passwordUpdate : 현재 비밀번호 -> 새로운 비밀번호) + (userNameUpdate : 바꿀 이름 적기 = 닉네임 *보류) > 관계자인증 > 회원탈퇴
	
	// 로그아웃
	//////////////////////////////////////////////////////////////////////
	
	
	// user information update
	public void UpdatePW(String UserPWcurrent, String UserPWnext);// UpdatePW (UserPWcurrent -> UserPWnext)
	
	// deleteUser
	public void DeleteUser(String password);

}
