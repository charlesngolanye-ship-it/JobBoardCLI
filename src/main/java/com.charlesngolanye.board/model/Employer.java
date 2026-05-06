package com.charlesngolanye.board.model;

public class Employer {
    private int id;
    private String name;
    private String email;
    private String industry;

    public Employer() {};

    public Employer(String email) {
        this.email = email;
    }

    public Employer(String name, String email, String industry) {
        this.name = name;
        this.email = email;
        this.industry = industry;
    }

    public Employer(int id, String name, String email, String industry) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.industry = industry;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
