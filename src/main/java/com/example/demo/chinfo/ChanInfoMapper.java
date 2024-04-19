package com.example.demo.chinfo;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.user.User;

@Repository
public class ChanInfoMapper {

	@Autowired
	SqlSession sqlSession;

	// 닉네임 변경
	public void updateName(User userid) {
		sqlSession.update("nmodi",userid); 
	}

	// 비밀번호 변경
	public void updatePw(User userid) {
		sqlSession.update("pwmodi",userid);
	}

//	// 비밀번호 확인
//	public int checkpw(String userID, String oldPw) {
//		return sqlSession.selectOne("checkpw",Map.of("userID",userID,"oldPw",oldPw));
//	}
	
}
