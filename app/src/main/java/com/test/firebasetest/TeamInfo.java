package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TeamInfo extends AppCompatActivity {
    Button InfoInsert, playerInsert, InfoChk, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_info);

        InfoInsert = (Button) findViewById(R.id.InfoInsert);
        playerInsert = (Button) findViewById(R.id.playerInsert);
        InfoChk = (Button) findViewById(R.id.InfoChk);
        backBtn = (Button) findViewById(R.id.backBtn);
        // 인스턴스들 만듦


        // 팀 정보 입력 버튼 클릭 시
        InfoInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamInfo.this, InsertTeamInfo.class);
                startActivity(intent);
            }
        });

        // 선수 입력 버튼 클릭 시
        playerInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamInfo.this, PlayerInsert.class);
                startActivity(intent);
            }
        });

        // 팀 정보 보기 버튼 클릭 시
        InfoChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamInfo.this, ChkTeamInfo.class);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼 클릭 시
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamInfo.this, MainPage.class);
                startActivity(intent);
            }
        });

    }
}
