package com.test.firebasetest;

public class User {
    private String userId;
    private String password;

    // 생성자, 게터, 세터 등 필요한 메서드 추가

    public User() {
        // 기본 생성자가 필요합니다.
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}