package com.example.demo.redis;

import java.net.Socket;

public class RedisPortCheck {
    public static void main(String[] args) {
        String host = "127.0.0.1";  // Redis 서버 호스트
        int port = 6379;            // Redis 포트 번호

        try {
            Socket socket = new Socket(host, port);
            System.out.println("Redis 서버에 연결되었습니다.");
            socket.close();
        } catch (Exception e) {
            System.out.println("Redis 서버에 연결할 수 없습니다: " + e.getMessage());
        }
    }
}
