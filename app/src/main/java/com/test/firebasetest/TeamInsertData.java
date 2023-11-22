package com.test.firebasetest;

public class TeamInsertData {
    private String teamName, teamArea, teamIntro;

    // 기본 생성자
    public TeamInsertData(){ }

    public TeamInsertData(String teamName, String teamArea, String teamIntro){
        this.teamName = teamName;
        this.teamArea = teamArea;
        this.teamIntro = teamIntro;
    }

    public String getTeamName() {return teamName; }
    public void setTeamName(String teamName) {this.teamName = teamName;}

    public String getTeamArea() {return teamArea; }
    public void setTeamArea(String teamArea) {this.teamArea = teamArea;}


    public String getTeamIntro() {return teamIntro; }
    public void setTeamIntro(String teamIntro) {this.teamIntro = teamIntro;}
}
