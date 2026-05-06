package com.charlesngolanye.board.model;

public class Applicant {
    private int id;
    private String name;
    private String email;
    private String skills;

    public Applicant(){};

    public Applicant(String name, String email, String skills) {
        this.name = name;
        this.email = email;
        this.skills = skills;
    }

    public Applicant(int id, String name, String email, String skills) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.skills = skills;
    }

    public Applicant(String email) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", skills='" + skills + '\'' +
                '}';
    }
}
