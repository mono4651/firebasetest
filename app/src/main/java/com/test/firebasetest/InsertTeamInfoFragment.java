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

public class InsertTeamInfoFragment extends Fragment {
    private FirebaseFirestore db;
    private EditText teamNameEditText;
    private EditText teamAreaEditText;
    private EditText teamIntroEditText;
    private Button storeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_team_info, container, false);


        db = FirebaseFirestore.getInstance();
        teamNameEditText = (EditText) view.findViewById(R.id.teamName);
        teamAreaEditText = (EditText)  view.findViewById(R.id.teamArea);
        teamIntroEditText = (EditText)  view.findViewById(R.id.teamIntro);
        storeButton =  (Button) view.findViewById(R.id.storeBtn);

        // 저장 버튼 누르면 DB에 저장
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
        String teamIntro = teamIntroEditText.getText().toString();

        // 중복 여부를 확인하고 중복된 값이 존재하는지 체크합니다.
        checkDuplicateData(teamName, teamArea, teamIntro);
    }
    private void checkDuplicateData(String teamName, String teamArea, String teamIntro) {
        // 중복 여부를 확인하기 위한 쿼리를 생성합니다.
        Query query = db.collection("TeamData")
                .whereEqualTo("teamName", teamName)
                .whereEqualTo("teamArea", teamArea);

        // 쿼리를 실행하여 중복된 값이 있는지 확인합니다.
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // 쿼리 결과로부터 중복된 값이 존재하는지 확인합니다.
                if (task.getResult().size() > 0) {
                    // 중복된 값이 존재하므로 저장을 중단하고 사용자에게 알립니다.
                    Toast.makeText(getContext(), "이미 존재하는 팀 정보입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 중복된 값이 없으므로 팀 정보를 저장합니다.
                    saveTeamData(teamName, teamArea, teamIntro);
                }
            } else {
                // 쿼리 실행 중 오류가 발생한 경우 처리합니다.
                Toast.makeText(getContext(), "중복 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTeamData(String teamName, String teamArea, String teamIntro) {
        // TeamInsertData 객체 생성
        TeamInsertData teamdata = new TeamInsertData(teamName, teamArea, teamIntro);

        // 팀 정보를 저장합니다.
        db.collection("TeamData")
                .document(teamName)
                .set(teamdata)
                .addOnSuccessListener(aVoid -> {
                    teamNameEditText.setText("");
                    teamAreaEditText.setText("");
                    teamIntroEditText.setText("");
                    Toast.makeText(getContext(), "팀 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "팀 정보 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }
}