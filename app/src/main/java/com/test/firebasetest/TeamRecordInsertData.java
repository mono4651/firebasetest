package com.test.firebasetest;

public class TeamRecordInsertData {
    public String team, area, scoreText, resultText,dateText,anotherTeamText, mvpText;

    // 기본 생성자
    public TeamRecordInsertData(){ }
    public TeamRecordInsertData(String team, String area, String scoreText, String resultText, String dateText, String anotherTeamText, String mvpText){
        this.team = team;
        this.area = area;
        this.scoreText = scoreText;
        this.resultText = resultText;
        this.dateText = dateText;
        this.anotherTeamText = anotherTeamText;
        this.mvpText = mvpText;
    }

    public String getTeamName() {return team; }
    public void setTeamName(String team) {this.team = team;}

    public String getTeamArea() {return area; }
    public void setTeamArea(String area) {this.area = area;}


    public String getScore() {return scoreText; }
    public void setScore(String scoreText) {this.scoreText = scoreText;}

    public String getresult() {return resultText; }
    public void setresult(String resultText) {this.resultText = resultText;}

    public String getDate() {return dateText; }
    public void setDate(String dateText) {this.dateText = dateText;}

    public String getAnotherteam() {return anotherTeamText; }
    public void setAnotherteam(String anotherTeamText) {this.anotherTeamText = anotherTeamText;}

    public String getMVP() {return mvpText; }
    public void setMVP(String mvpText) {this.mvpText = mvpText;}
}