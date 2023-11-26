package com.test.firebasetest;

public class TeamRecordWriteSave {
    private String Team, Area, anotherTeamText, dateText, resultText, mvpText,ScoreText;

    // 생성자, 게터, 세터 등 필요한 메서드 추가

    public TeamRecordWriteSave() {
        // 기본 생성자가 필요합니다.
    }

    public TeamRecordWriteSave(String Team, String Area, String anotherTeamText, String dateText,String ScoreText, String resultText, String mvpText) {
        this.Team = Team;
        this.Area = Area;
        this.anotherTeamText = anotherTeamText;
        this.dateText = dateText;
        this.ScoreText= ScoreText;
        this.resultText = resultText;
        this.mvpText = mvpText;

    }

    public String getTeam() {
        return Team;
    }
    public void setTeam(String Team) {
        this.Team = Team;
    }

    public String getArea() {
        return Area;
    }
    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getanotherTeamText() {return anotherTeamText; }
    public void setanotherTeamText(String anotherTeamText) {this.anotherTeamText = anotherTeamText;}
    public String getdateText() {return dateText; }
    public void setdateText(String dateText) {this.dateText = dateText;}
    public String getScoreText() {return ScoreText; }
    public void setScoreText(String ScoreText) {this.ScoreText = ScoreText;}

    public String getresultText() {return resultText; }
    public void setresultText(String resultText) {this.resultText = resultText;}
    public String getmvpText() {return mvpText; }
    public void setmvpText(String mvpText) {this.mvpText = mvpText;}
}