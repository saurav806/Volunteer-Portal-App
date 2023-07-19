package com.example.signup;

public class UserModelDetails {
    String skills;
    String pastExp;
    String contri;

    String uid;

    String heading;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public UserModelDetails(String skills, String pastExp, String contri,String uid,String heading) {
        this.skills = skills;
        this.uid=uid;
        this.heading=heading;
        this.pastExp = pastExp;
        this.contri = contri;
        this.status="0";
    }

    public UserModelDetails() {
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getPastExp() {
        return pastExp;
    }

    public void setPastExp(String pastExp) {
        this.pastExp = pastExp;
    }

    public String getContri() {
        return contri;
    }

    public void setContri(String contri) {
        this.contri = contri;
    }
}
