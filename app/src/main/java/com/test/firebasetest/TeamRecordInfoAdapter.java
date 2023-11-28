package com.test.firebasetest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamRecordInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TeamRecordInsertData> TeamRecordList;
    private boolean showHeader; // 헤더를 보여줄지 여부를 저장하는 변수

    public TeamRecordInfoAdapter(List<TeamRecordInsertData> TeamRecordList) {
        this.TeamRecordList = TeamRecordList;
        this.showHeader = false; // 초기값은 헤더를 보여주지 않음
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && showHeader) {
            return 0; // 0은 헤더를 의미
        } else {
            return 1; // 1은 일반 아이템을 의미
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) { // 헤더 레이아웃 사용
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else { // 일반 아이템 레이아웃 사용
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_info, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) { // 헤더 ViewHolder인 경우
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.textViewAnotherTeam.setText("상대 팀");
            headerHolder.textScore.setText("스코어");
            headerHolder.textresult.setText("결과");
            headerHolder.textDate.setText("날짜");
            headerHolder.textViewMVP.setText("MVP");
            return;
        } else if (holder instanceof ItemViewHolder) { // 아이템 ViewHolder인 경우
            if (showHeader) {
                position--; // 헤더를 고려하여 position 조정
            }
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            TeamRecordInsertData TeamRecordData = TeamRecordList.get(position);
            itemHolder.textViewAnotherTeam.setText(TeamRecordData.getAnotherteam());
            itemHolder.textScore.setText(TeamRecordData.getScore());
            itemHolder.textresult.setText(TeamRecordData.getresult());
            itemHolder.textDate.setText(TeamRecordData.getDate());
            itemHolder.textViewMVP.setText(TeamRecordData.getMVP());
        }
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

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewAnotherTeam, textScore, textresult, textDate, textViewMVP;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAnotherTeam = itemView.findViewById(R.id.textViewAnotherTeam);
            textScore = itemView.findViewById(R.id.textScore);
            textresult = itemView.findViewById(R.id.textresult);
            textDate = itemView.findViewById(R.id.textDate);
            textViewMVP = itemView.findViewById(R.id.textViewMVP);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewAnotherTeam, textScore, textresult, textDate, textViewMVP;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAnotherTeam = itemView.findViewById(R.id.textViewAnotherTeam);
            textScore = itemView.findViewById(R.id.textScore);
            textresult = itemView.findViewById(R.id.textresult);
            textDate = itemView.findViewById(R.id.textDate);
            textViewMVP = itemView.findViewById(R.id.textViewMVP);
        }
    }
}
