package com.charlesngolanye.board.model;

import java.time.LocalDate;

public class Application {
    private int id;
    private int jobId;
    private int applicantId;
    private LocalDate appliedAt;
    private Status status;

    public Application(){};

    public Application(int jobId, int applicantId, LocalDate appliedAt, Status status) {
        this.jobId = jobId;
        this.applicantId = applicantId;
        this.appliedAt = appliedAt;
        this.status = status;
    }

    public Application(int id, int jobId, int applicantId, LocalDate appliedAt, Status status) {
        this.id = id;
        this.jobId = jobId;
        this.applicantId = applicantId;
        this.appliedAt = appliedAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public LocalDate getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDate appliedAt) {
        this.appliedAt = appliedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", applicantId=" + applicantId +
                ", appliedAt=" + appliedAt +
                ", status=" + status +
                '}';
    }
}
