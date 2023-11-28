package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;

public class PlayerInsertFragment extends Fragment {
    private FirebaseFirestore db;
    private EditText teamNameEditText;
    private EditText teamAreaEditText;
    private EditText playerNameEditText;
    private EditText playerAgeEditText;
    private Button storeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_insert, container, false);

        db = FirebaseFirestore.getInstance();
        teamNameEditText = view.findViewById(R.id.teamName);
        teamAreaEditText = view.findViewById(R.id.teamArea);
        playerNameEditText = view.findViewById(R.id.playerName);
        playerAgeEditText = view.findViewById(R.id.playerAge);
        storeButton = view.findViewById(R.id.storeBtn);

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
        return view;
    }
        private void saveUserData() {
            String teamName = teamNameEditText.getText().toString();
            String teamArea = teamAreaEditText.getText().toString();
            String playerName = playerNameEditText.getText().toString();
            String playerAge = playerAgeEditText.getText().toString();

            // 중복 여부를 확인하고 중복된 값이 존재하는지 체크합니다.
            checkDuplicateData(teamName, teamArea, playerName, playerAge);
        }

    private void checkDuplicateData(String teamName, String teamArea, String playerName, String playerAge) {
        // 중복 여부를 확인하기 위한 쿼리를 생성합니다.
        Query query = db.collection("TeamData")
                .whereEqualTo("teamName", teamName);

        // 쿼리를 실행하여 중복된 값이 있는지 확인합니다.
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // 쿼리 결과로부터 중복된 값이 존재하는지 확인합니다.
                if (task.getResult().size() > 0) {
                    // 중복된 값이 존재하므로 플레이어 정보를 저장합니다.
                    savePlayerData(teamName, teamArea, playerName, playerAge);
                } else {
                    // 중복된 값이 없으므로 저장을 중단하고 사용자에게 알립니다.
                    Toast.makeText(getContext(), "해당 팀은 등록되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 쿼리 실행 중 오류가 발생한 경우 처리합니다.
                Toast.makeText(getContext(), "중복 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void savePlayerData(String teamName, String teamArea, String playerName, String playerAge) {
        // PlayerInsertData 객체 생성
        PlayerInsertData playerData = new PlayerInsertData(teamName, teamArea, playerName, playerAge);

        // 선수 정보를 저장합니다.
        db.collection("PlayerData")
                .document(playerName)
                .set(playerData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "선수 정보 입력 성공!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "선수 정보 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearInputFields() {
        teamNameEditText.setText("");
        teamAreaEditText.setText("");
        playerNameEditText.setText("");
        playerAgeEditText.setText("");
    }
}
