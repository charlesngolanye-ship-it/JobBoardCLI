package com.charlesngolanye.board.model;

public class Applicant {
    private int id;
    private String name;
    private String email;
    private String skills;

    public Applicant(){}

    public Applicant(String name, String email, String skills) {
       validateName(name);
       validateEmail(email);
       validateSkills(skills);

        this.name = name;
        this.email = email;
        this.skills = skills;
    }

    public Applicant(int id, String name, String email, String skills) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        validateName(name);
        validateEmail(email);
        validateSkills(skills);

        this.id = id;
        this.name = name;
        this.email = email;
        this.skills = skills;
    }

    public Applicant(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid Email format");
        }
    }

    private void validateSkills(String skills) {
        if (skills == null || skills.isBlank()) {
            throw new IllegalArgumentException("Skills cannot be empty");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        validateSkills(skills);
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
