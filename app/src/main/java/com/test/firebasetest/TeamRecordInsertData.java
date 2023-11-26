package com.test.firebasetest;

public class TeamRecordInsertData {
    private String TeamName, TeamArea, Score, result,Date;

    // 기본 생성자
    public TeamRecordInsertData(){ }

    public TeamRecordInsertData(String TeamName, String TeamArea, String Score, String result, String Date){
        this.TeamName = TeamName;
        this.TeamArea = TeamArea;
        this.Score = Score;
        this.result = result;
        this.Date = Date;

    }

    public String getTeamName() {return TeamName; }
    public void setTeamName(String TeamName) {this.TeamName = TeamName;}

    public String getTeamArea() {return TeamArea; }
    public void setTeamArea(String TeamArea) {this.TeamArea = TeamArea;}


    public String getScore() {return Score; }
    public void setScore(String Score) {this.Score = Score;}

    public String getresult() {return result; }
    public void setresult(String result) {this.result = result;}

    public String getDate() {return Date; }
    public void setDate(String Date) {this.Date = Date;}
}
