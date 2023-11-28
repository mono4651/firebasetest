package com.test.firebasetest;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        recyclerViewTeamInfo.addItemDecoration(new RecyclerView.ItemDecoration() { // 아이템 간 간격 설정
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position != parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = 16;  // 아이템 사이에 16dp 간격 추가
                }
            }
        });
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
                            .whereEqualTo("team", Team)
                            .whereEqualTo("area", Area);
                    query.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            TeamRecordList.clear(); // 기존 데이터 초기화
                            if (querySnapshot.isEmpty()) {
                                Toast.makeText(TeamRecord.this, "해당 팀의 경기 기록이 없습니다..", Toast.LENGTH_SHORT).show();
                                // 헤더를 보여주지 않도록 설정
                                teamRecordInfoAdapter.setShowHeader(false);
                            } else {
                                // 헤더를 보여주도록 설정
                                teamRecordInfoAdapter.setShowHeader(true);

                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    Log.d("TeamRecord", "DocumentSnapshot data: " + document.getData());
                                    TeamRecordInsertData TeamRecordData = document.toObject(TeamRecordInsertData.class);
                                    Log.d("TeamRecord", "TeamRecordData: " + TeamRecordData); // toString()
                                    Log.d("TeamRecord", "TeamRecordData team: " + TeamRecordData.getDate());
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