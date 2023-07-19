package com.example.signup;

import com.google.firebase.database.Exclude;

public class ModelClass {
    String heading;
    String description;
    String eventData;
    String preprationDate;
    String   numVolunteers;

    @Exclude
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ModelClass(){

    }

    public ModelClass(String heading, String description, String eventDate, String preprationDate, String  numVolunteers) {
        this.heading = heading;
        this.description = description;
        this.eventData = eventDate;
        this.preprationDate = preprationDate;
        this.numVolunteers = numVolunteers;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getEventData() {
        return eventData;
    }

    public String getPreprationDate() {
        return preprationDate;
    }

    public String  getNumVolunteers() {
        return numVolunteers;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEventDate(String eventData) {
        this.eventData = eventData;
    }

    public void setPreprationDate(String preprationDate) {
        this.preprationDate = preprationDate;
    }

    public void setNumVolunteers(String  numVolunteers) {
        this.numVolunteers = numVolunteers;
    }


}
