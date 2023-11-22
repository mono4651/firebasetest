package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    Button backbtn;
    Button loginbtn, gaipbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        backbtn = (Button) findViewById(R.id.goback);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        gaipbtn = (Button) findViewById(R.id.gaipbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this,FirstActivity.class);
                startActivity(intent);
            }
        });
        // 뒤로 가기 이동

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this,Login.class);
                startActivity(intent);
            }
        });
        // 로그인 이동

        gaipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this,Gaip.class);
                startActivity(intent);
            }
        });
        // 회원가입 이동
    }
}