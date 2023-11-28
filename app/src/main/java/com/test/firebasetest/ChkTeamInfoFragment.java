package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChkTeamInfoFragment extends Fragment {

    private FirebaseFirestore db;
    private EditText editTextTeamName, editTextTeamArea;
    private Button buttonCheckInfo, backButton;
    private RecyclerView recyclerViewPlayerInfo;
    private PlayerInfoAdapter playerInfoAdapter;
    private List<PlayerInsertData> playerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chk_team_info, container, false);

        db = FirebaseFirestore.getInstance();

        editTextTeamName = (EditText) view.findViewById(R.id.teamName);
        editTextTeamArea = (EditText) view.findViewById(R.id.teamArea);
        buttonCheckInfo = (Button) view.findViewById(R.id.showBtn);
        recyclerViewPlayerInfo = (RecyclerView) view.findViewById(R.id.recyclerViewPlayerInfo);

        playerList = new ArrayList<>();
        playerInfoAdapter = new PlayerInfoAdapter(playerList);
        recyclerViewPlayerInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPlayerInfo.setAdapter(playerInfoAdapter);

        buttonCheckInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamName = editTextTeamName.getText().toString();
                String teamArea = editTextTeamArea.getText().toString();

                checkTeamInfo(teamName, teamArea);
            }
        });

        return view;
    }

    private void checkTeamInfo(String teamName, String teamArea) {
        Query query = db.collection("PlayerData")
                .whereEqualTo("tName", teamName)
                .whereEqualTo("tArea", teamArea);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                playerList.clear(); // 기존 데이터 초기화
                if (querySnapshot.isEmpty()) {
                    Toast.makeText(getContext(), "해당 팀에는 소속 선수가 없습니다.", Toast.LENGTH_SHORT).show();

                    // 헤더를 보여주지 않도록 설정
                    playerInfoAdapter.setShowHeader(false);
                } else {
                    // 헤더를 보여주도록 설정
                    playerInfoAdapter.setShowHeader(true);

                    for (QueryDocumentSnapshot document : querySnapshot) {
                        PlayerInsertData playerData = document.toObject(PlayerInsertData.class);
                        playerList.add(playerData);
                    }
                }
                playerInfoAdapter.notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
            } else {
                Toast.makeText(getContext(), "팀 정보 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // RecyclerView 설정 및 데이터 로딩 등의 작업을 수행하세요
    }
}

