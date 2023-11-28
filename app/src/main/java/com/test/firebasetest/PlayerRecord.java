package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlayerRecord extends AppCompatActivity {
    private FirebaseFirestore db;
    private String Team, Area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_record);
        TextView firstPrize = findViewById(R.id.firstPrize);
        TextView secondPrize = findViewById(R.id.secondPrize);
        TextView thirdPrize = findViewById(R.id.thirdPrize);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userId");
        db.collection("GaipInfo")
                .document(userName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Team = documentSnapshot.getString("TeamName");
                    Area = documentSnapshot.getString("TeamArea");
                    Log.d("MVP COUNT", "선수 이름: " + userName );
                    db.collection("TeamRecord")
                            .whereEqualTo("team", Team)
                            .whereEqualTo("area", Area)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Map<String, Integer> mvpCounts = new HashMap<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String mvpName = document.getString("mvpText");
                                        // MVP 횟수를 카운트하는 Map 업데이트
                                        if (mvpCounts.containsKey(mvpName)) {
                                            mvpCounts.put(mvpName, mvpCounts.get(mvpName) + 1);
                                        } else {
                                            mvpCounts.put(mvpName, 1);
                                        }
                                    }
                                    List<Map.Entry<String, Integer>> entryList = new ArrayList<>(mvpCounts.entrySet());
                                    entryList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
                                    // 상위 3명의 선수 이름 출력
                                    if (entryList.size() > 0) {
                                        firstPrize.setText(entryList.get(0).getKey());
                                    }
                                    if (entryList.size() > 1) {
                                        secondPrize.setText(entryList.get(1).getKey());
                                    }
                                    if (entryList.size() > 2) {
                                        thirdPrize.setText(entryList.get(2).getKey());
                                    }
                                    // 모든 선수의 MVP 횟수 출력

                                } else {
                                    Log.d("MVP COUNT", "Error getting documents: ", task.getException());
                                }
                            });
                })
                .addOnFailureListener(e -> {
                    // 데이터베이스 접근 실패 등의 오류 처리
                    Toast.makeText(PlayerRecord.this, "데이터베이스 접근 불가", Toast.LENGTH_SHORT).show();
                });

            }

}