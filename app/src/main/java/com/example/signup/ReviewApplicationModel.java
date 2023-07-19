package com.example.signup;

public class ReviewApplicationModel {

    String name;
    String skills;
    String pastExp;
    String heading;
    String status;

    public String getStatus() {
        return status;
    }

    public String getHeading() {
        return heading;
    }

    public String getUid() {
        return uid;
    }

    String contri;
    String uid;
    public ReviewApplicationModel() {

    }

    public ReviewApplicationModel(String name, String skills, String pastExp, String contri) {
        this.name = name;
        this.skills = skills;
        this.pastExp = pastExp;
        this.contri = contri;
    }

    public String getName() {
        return name;
    }

    public String getSkills() {
        return skills;
    }

    public String getPastExp() {
        return pastExp;
    }

    public String getContri() {
        return contri;
    }
}
