package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainPage extends AppCompatActivity {
    LinearLayout teamInfo, teamRecord, teamSchedule, playerRecord, gallery;
    private FirebaseFirestore db;
    public String Team, Area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        teamInfo = findViewById(R.id.teamInfo);
        teamRecord = findViewById(R.id.teamRecord);
        teamSchedule = findViewById(R.id.teamSchedule);
        playerRecord = findViewById(R.id.player_record);
        db = FirebaseFirestore.getInstance();
        gallery = findViewById(R.id.gallery);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userId");
        db.collection("GaipInfo")
                .document(userName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Team = documentSnapshot.getString("TeamName");
                    Area = documentSnapshot.getString("TeamArea");
                    // 가져온 필드를 사용하는 코드는 여기에 작성하시면 됩니다.
                })
                .addOnFailureListener(e -> {
                    // 데이터베이스 접근 실패 등의 오류 처리
                    Toast.makeText(MainPage.this, "데이터베이스 접근 불가", Toast.LENGTH_SHORT).show();
                });
        TextView textView = findViewById(R.id.test);
        textView.setText(userName);
        teamInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, TeamInfo_2.class);
                intent.putExtra("userId",userName);
                startActivity(intent);
            }
        });

        teamRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Team == null || Area == null){
                    Toast.makeText(MainPage.this, "팀설정이 안되어 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainPage.this, TeamRecord.class);
                    intent.putExtra("userId",userName);
                    startActivity(intent);
                }
            }
        });

        teamSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, TeamSchedule.class);
                intent.putExtra("userId",userName);
                startActivity(intent);
            }
        });

        playerRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Team == null || Area == null){
                    Toast.makeText(MainPage.this, "팀설정이 안되어 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainPage.this, PlayerRecord.class);
                    intent.putExtra("userId",userName);
                    startActivity(intent);
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Team == null || Area == null){
                    Toast.makeText(MainPage.this, "팀설정이 안되어 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainPage.this, TeamRecordWrite.class);
                    intent.putExtra("userId",userName);
                    startActivity(intent);
                }
            }
        });

    }
}
