package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamSettingFragment  extends Fragment {
    private FirebaseFirestore db;
    private EditText editTextTeamName, editTextTeamArea;
    private Button settingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_setting, container, false);

        db = FirebaseFirestore.getInstance();
        editTextTeamName = view.findViewById(R.id.teamName);
        editTextTeamArea = view.findViewById(R.id.teamArea);
        settingButton = view.findViewById(R.id.settingBtn);
        Bundle bundle = getArguments();
        String userName = bundle.getString("userId");
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamName = editTextTeamName.getText().toString();
                String teamArea = editTextTeamArea.getText().toString();
                Query query = db.collection("TeamData")
                        .whereEqualTo("teamName", teamName)
                        .whereEqualTo("teamArea", teamArea);
                // 쿼리를 실행하여 중복된 값이 있는지 확인합니다.
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().size() > 0) {
                            db.collection("GaipInfo")
                                    .document(userName)
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        saveRecord(teamName, teamArea);
                                        Intent intent = new Intent(getContext(), MainPage.class);
                                        intent.putExtra("userId",userName);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        // 데이터베이스 접근 실패 등의 오류 처리
                                        Toast.makeText(getContext(), "사용자 데이터베이스 접근 불가", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(getContext(), "존재하지 않는 팀입니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 쿼리 실행 중 오류가 발생한 경우 처리합니다.
                        Toast.makeText(getContext(), "중복 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return view;
    }
    private void saveRecord(String teamName, String teamArea) {
        Bundle bundle = getArguments();
        String userName = bundle.getString("userId");
        Map<String, Object> data = new HashMap<>();
        data.put("TeamName", teamName);
        data.put("TeamArea", teamArea);
        db.collection("GaipInfo")
                .document(userName)
                .update(data)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "팀 설정 성공!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "경기 후기 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }
    private void clearInputFields() {
        editTextTeamName.setText("");
        editTextTeamArea.setText("");
    }
}
