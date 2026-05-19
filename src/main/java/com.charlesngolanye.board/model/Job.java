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

        validateEmployerId(employerId);
        validateTitle(title);
        validateDescription(description);
        validateLocation(location);
        validateJobType(jobType);
        validateSalaryMin(salaryMin);
        validateSalaryMax(salaryMax);
        validatePostedAt(postedAt);
        validateDeadline(deadline);
        validateIsOpen(isOpen);

        this.employerId = employerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.postedAt = postedAt;
        this.deadline = deadline;
        this.isOpen = true;
    }

    public Job(int id, int employerId, String title, String description, String location,
               JobType jobType, Double salaryMin, Double salaryMax, LocalDate postedAt,
               LocalDate deadline, boolean isOpen) {
        validateId(id);
        validateEmployerId(employerId);
        validateTitle(title);
        validateDescription(description);
        validateLocation(location);
        validateJobType(jobType);
        validateSalaryMin(salaryMin);
        validateSalaryMax(salaryMax);
        validatePostedAt(postedAt);
        validateDeadline(deadline);
        validateIsOpen(isOpen);

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
        this.isOpen = true;
    }

    private void validateId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException(
                    "ID cannot be negative."
            );
        }
    }

    private void validateEmployerId(int employerId) {
        if (employerId <= 0) {
            throw new IllegalArgumentException(
                    "Employer ID cannot be negative."
            );
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException(
                    "Title cannot be empty."
            );
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException(
                    "Description cannot be empty."
            );
        }
    }

    private void validateLocation(String location) {
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException(
                    "Location cannot be empty."
            );
        }
    }

    private void validateJobType(JobType jobType) {
        if (jobType == null) {
            throw new IllegalArgumentException(
                    "Job type cannot be null."
            );
        }
    }

    private void validateSalaryMin(Double salaryMin) {
        if (salaryMin == null) {
            throw new IllegalArgumentException(
                    "Salary min cannot be null."
            );
        }
    }

    private void validateSalaryMax(Double salaryMax) {
        if (salaryMax == null) {
            throw new IllegalArgumentException(
                    "Salary max cannot be null."
            );
        }
    }

    private void validatePostedAt(LocalDate postedAt) {
        if (postedAt == null) {
            throw new IllegalArgumentException(
                    "Posted at cannot be null."
            );
        }
    }

    private void validateDeadline(LocalDate deadline) {
        if (deadline == null) {
            throw new IllegalArgumentException(
                    "Deadline cannot be null."
            );
        }
    }

    private void validateIsOpen(boolean isOpen) {
        if (!isOpen) {
            throw new IllegalArgumentException(
                    "Job must be open."
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

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        validateEmployerId(employerId);
        this.employerId = employerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        validateLocation(location);
        this.location = location;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        validateJobType(jobType);
        this.jobType = jobType;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Double salaryMin) {
        validateSalaryMin(salaryMin);
        this.salaryMin = salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Double salaryMax) {
        validateSalaryMax(salaryMax);
        this.salaryMax = salaryMax;
    }

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
        validatePostedAt(postedAt);
        this.postedAt = postedAt;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        validateDeadline(deadline);
        this.deadline = deadline;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        validateIsOpen(open);
        isOpen = true;
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
