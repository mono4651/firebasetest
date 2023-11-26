package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TeamRecordWrite extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText AnotherTeam, Date, Score, Result, MVP ;
    private String Team, Area;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_record_write);
        db = FirebaseFirestore.getInstance();
        saveButton = findViewById(R.id.storeBtn);
        AnotherTeam = findViewById(R.id.AnotherTeam);
        Date = findViewById(R.id.RecordDate);
        Score = findViewById(R.id.RecordScore);
        Result = findViewById(R.id.RecordResult);
        MVP = findViewById(R.id.RecordMVP);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userId");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("GaipInfo")
                        .document(userName)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            Team = documentSnapshot.getString("TeamName");
                            Area = documentSnapshot.getString("TeamArea");
                            String AnotherTeamText = AnotherTeam.getText().toString();
                            String DateText = Date.getText().toString();
                            String ScoreText = Score.getText().toString();
                            String ResultText = Result.getText().toString();
                            String MvpText = MVP.getText().toString();
                            saveRecord(Team, Area, AnotherTeamText,DateText,ScoreText,ResultText,MvpText);
                            Intent intent = new Intent(TeamRecordWrite.this, MainPage.class);
                            intent.putExtra("userId",userName);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            // 데이터베이스 접근 실패 등의 오류 처리
                            Toast.makeText(TeamRecordWrite.this, "데이터베이스 접근 불가", Toast.LENGTH_SHORT).show();
                        });

            }
        });

    }

    private void saveRecord(String Team, String Area, String anotherTeamText, String dateText,String ScoreText, String resultText, String mvpText) {
        TeamRecordWriteSave teamRecordWriteSave = new TeamRecordWriteSave(Team, Area, anotherTeamText, dateText, ScoreText, resultText, mvpText);
        db.collection("TeamRecord")
                .document(dateText+" "+Team)
                .set(teamRecordWriteSave)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(TeamRecordWrite.this, "경기 후기 저장 성공!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "경기 후기 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }
    private void clearInputFields() {
        AnotherTeam.setText("");
        Date.setText("");
        Score.setText("");
        Result.setText("");
        MVP.setText("");
    }
}