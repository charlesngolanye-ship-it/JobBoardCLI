package com.charlesngolanye.board.model;

public class Employer {
    private int id;
    private String name;
    private String email;
    private String industry;

    public Employer() {};

    public Employer(String email) {
        validateEmail(email);
        this.email = email;
    }

    public Employer(String name, String email, String industry) {
        validateName(name);
        validateEmail(email);
        validateIndustry(industry);

        this.name = name;
        this.email = email;
        this.industry = industry;
    }

    public Employer(int id, String name, String email, String industry) {
        validateId(id);
        validateName(name);
        validateEmail(email);
        validateIndustry(industry);

        this.id = id;
        this.name = name;
        this.email = email;
        this.industry = industry;
    }

    private void validateId(int id){
        if(id < 0){
            throw new IllegalArgumentException(
                    "ID cannot be negative"
            );
        }
    }

    private void validateName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException(
                    "Name cannot be empty"
            );
        }
    }

    private void validateEmail(String email){
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException(
                    "Email cannot be empty"
            );
        }
    }

    private void validateIndustry(String industry){
        if(industry == null || industry.isEmpty()){
            throw new IllegalArgumentException(
                    "Industry cannot be empty"
            );
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        validateId(id);
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        validateIndustry(industry);
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
