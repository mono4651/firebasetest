package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {
    LinearLayout teamInfo, teamRecord, teamSchedule, playerRecord, gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        teamInfo = findViewById(R.id.teamInfo);
        teamRecord = findViewById(R.id.teamRecord);
        teamSchedule = findViewById(R.id.teamSchedule);
        playerRecord = findViewById(R.id.player_record);
        gallery = findViewById(R.id.gallery);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userId");

        TextView textView = findViewById(R.id.test);
        textView.setText(userName);
        teamInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, TeamInfo.class);
                startActivity(intent);
            }
        });

        teamRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, TeamRecord.class);
                intent.putExtra("userId",userName);
                startActivity(intent);
            }
        });

        teamSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, TeamSchedule.class);
                startActivity(intent);
            }
        });

        playerRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, PlayerRecord.class);
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, TeamRecordWrite.class);
                intent.putExtra("userId",userName);
                startActivity(intent);
            }
        });

    }
}
