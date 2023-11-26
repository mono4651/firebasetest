package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class TeamRecord extends AppCompatActivity {
    private FirebaseFirestore db;
    private String Team;
    private String Area;

    private RecyclerView recyclerViewTeamInfo;
    private TeamRecordInfoAdapter teamRecordInfoAdapter;
    private List<TeamRecordInsertData> TeamRecordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_record);
        recyclerViewTeamInfo = findViewById(R.id.recyclerViewTeamInfo);
        recyclerViewTeamInfo.setLayoutManager(new LinearLayoutManager(this));
        TeamRecordList = new ArrayList<>();
        teamRecordInfoAdapter = new TeamRecordInfoAdapter(TeamRecordList);
        recyclerViewTeamInfo.setAdapter(teamRecordInfoAdapter);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userId");
        db.collection("GaipInfo")
                .document(userName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Team = documentSnapshot.getString("TeamName");
                    Area = documentSnapshot.getString("TeamArea");
                    Query query = db.collection("TeamRecord")
                            .whereEqualTo("TeamName", Team)
                            .whereEqualTo("TeamArea", Area);
                    query.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            TeamRecordList.clear(); // 기존 데이터 초기화
                            if (querySnapshot.isEmpty()) {
                                Toast.makeText(TeamRecord.this, "해당 팀의 경기 기록이 없습니다..", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                                // 헤더를 보여주지 않도록 설정
                                teamRecordInfoAdapter.setShowHeader(false);
                            } else {
                                // 헤더를 보여주도록 설정
                                teamRecordInfoAdapter.setShowHeader(true);

                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    TeamRecordInsertData TeamRecordData = document.toObject(TeamRecordInsertData.class);
                                    TeamRecordList.add(TeamRecordData);
                                }
                            }
                            teamRecordInfoAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
                        } else {
                            Toast.makeText(TeamRecord.this, "팀 정보 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .addOnFailureListener(e -> {
                    // 데이터베이스 접근 실패 등의 오류 처리
                    Toast.makeText(TeamRecord.this, "경기기록 없음", Toast.LENGTH_SHORT).show();
                });
    }
}