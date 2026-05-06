package com.charlesngolanye.board.model;

import java.time.LocalDate;

public class Job {
    private int id;
    private int employerId;
    private String title;
    private String description;
    private String location;
    private JobType jobType;
    private Double salaryMin;
    private Double salaryMax;
    private LocalDate postedAt;
    private LocalDate deadline;
    private boolean isOpen;


    public Job(){};

    public Job(int employerId, String title, String description, String location,
               JobType jobType, Double salaryMin, Double salaryMax, LocalDate postedAt,
               LocalDate deadline, boolean isOpen) {
        this.employerId = employerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.postedAt = postedAt;
        this.deadline = deadline;
        this.isOpen = isOpen;
    }

    public Job(int id, int employerId, String title, String description, String location,
               JobType jobType, Double salaryMin, Double salaryMax, LocalDate postedAt,
               LocalDate deadline, boolean isOpen) {
        this.id = id;
        this.employerId = employerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.postedAt = postedAt;
        this.deadline = deadline;
        this.isOpen = isOpen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
        this.postedAt = postedAt;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", employerId=" + employerId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", jobType=" + jobType +
                ", salaryMin=" + salaryMin +
                ", salaryMax=" + salaryMax +
                ", postedAt=" + postedAt +
                ", deadline=" + deadline +
                ", isOpen=" + isOpen +
                '}';
    }
}
