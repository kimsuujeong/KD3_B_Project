package com.example.demo.myPage;

public interface MypageService {
	
	// ########################
	// ##### MY PAGE_Form #####
	// ########################

	// 관심목록 > 관심태그
	
	// 관계자 인증
	
	
	// 개인정보 수정(passwordUpdate) + (userNameUpdate) > 관계자인증 > 회원탈퇴
	// passwordUpdate 현재 비밀번호 / 바꿀 비밀번호 / 바꿀 비밀번호 확인
	// usrnameUpdate 현재 비밀번호 / 바꿀 닉네임(바꿀 닉네임 중복)	
	
	// userPassword information update
	public void UpdatePW(String UserPWCurrent, String UserPWNext);// UpdatePW (UserPWcurrent -> UserPWnext)
	
	// userName information update
	public void userNameUpdate(String UserNameCurrent, String UserNameNext);
	
	// deleteUser
	public void DeleteUser(String password);
	
	// 로그아웃

}
