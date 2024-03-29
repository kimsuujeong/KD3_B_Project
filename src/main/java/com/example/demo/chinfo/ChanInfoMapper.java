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

	public void updateName(User userid) {
		sqlSession.update("nmodi",userid);
	}

	public void updateEmail(User userid) {
		sqlSession.update("emodi", userid);
	}


	public void updatePw(User userid) {
		sqlSession.update("pwmodi",userid);
	}

	public int checkpw(String userID, String oldPw) {
		return sqlSession.selectOne("checkpw",Map.of("userID",userID,"oldPw",oldPw));
	}
	
}
