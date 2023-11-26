package com.test.firebasetest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamRecordInfoAdapter extends RecyclerView.Adapter<TeamRecordInfoAdapter.ViewHolder> {

    private List<TeamRecordInsertData> TeamRecordList;
    private boolean showHeader; // 헤더를 보여줄지 여부를 저장하는 변수

    public TeamRecordInfoAdapter(List<TeamRecordInsertData> TeamRecordList) {
        this.TeamRecordList = TeamRecordList;
        this.showHeader = false; // 초기값은 헤더를 보여주지 않음
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 헤더를 보여줄지 여부에 따라 데이터 표시 여부 결정
        if (showHeader) {
            if (position == 0) {
                holder.textViewTeamName.setText("팀 이름");
                holder.textViewTeamArea.setText("팀 지역");
                holder.textScore.setText("스코어");
                holder.textresult.setText("결과");
                holder.textDate.setText("날짜");
                return;
            }
            position--; // 헤더를 제외한 아이템 위치 계산
        }

        TeamRecordInsertData TeamRecordData = TeamRecordList.get(position);

        holder.textViewTeamName.setText(TeamRecordData.getTeamName());
        holder.textViewTeamArea.setText(TeamRecordData.getTeamArea());
        holder.textScore.setText(TeamRecordData.getScore());
        holder.textresult.setText(TeamRecordData.getresult());
        holder.textDate.setText(TeamRecordData.getDate());

    }

    @Override
    public int getItemCount() {
        if (TeamRecordList == null) {
            return 0;
        }
        // 헤더를 보여줄지 여부에 따라 아이템 개수 결정
        if (showHeader) {
            return TeamRecordList.size() + 1;
        } else {
            return TeamRecordList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTeamName, textViewTeamArea, textScore, textresult, textDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTeamName = itemView.findViewById(R.id.textViewTeamName);
            textViewTeamArea = itemView.findViewById(R.id.textViewTeamArea);
            textScore = itemView.findViewById(R.id.textScore);
            textresult = itemView.findViewById(R.id.textresult);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }
}

