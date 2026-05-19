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

        validateJobId(jobId);
        validateApplicantId(applicantId);
        validateAppliedAt(appliedAt);
        validateStatus(status);

        this.jobId = jobId;
        this.applicantId = applicantId;
        this.appliedAt = appliedAt;
        this.status = status;
    }

    public Application(int id, int jobId, int applicantId, LocalDate appliedAt, Status status) {

        validateId(id);
        validateJobId(jobId);
        validateApplicantId(applicantId);
        validateAppliedAt(appliedAt);
        validateStatus(status);

        this.id = id;
        this.jobId = jobId;
        this.applicantId = applicantId;
        this.appliedAt = appliedAt;
        this.status = status;
    }

    private void validateId(int id){
        if(id < 0){
            throw new IllegalArgumentException(
                    "ID cannot be negative"
            );
        }
    }

    private void validateJobId(int jobId){
        if(jobId <= 0){
            throw new IllegalArgumentException(
                    "Job ID cannot be negative"
            );
        }
    }

    private void validateApplicantId(int applicantId){
        if(applicantId <= 0){
            throw new IllegalArgumentException(
                    "Applicant ID cannot be negative"
            );
        }
    }

    private void validateAppliedAt(LocalDate appliedAt){
        if(appliedAt == null){
            throw new IllegalArgumentException(
                    "AppliedAt cannot be null"
            );
        }
    }

    private void validateStatus(Status status){
        if(status == null){
            throw new IllegalArgumentException(
                    "Status cannot be null"
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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        validateJobId(jobId);
        this.jobId = jobId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        validateApplicantId(applicantId);
        this.applicantId = applicantId;
    }

    public LocalDate getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDate appliedAt) {
        validateAppliedAt(appliedAt);
        this.appliedAt = appliedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        validateStatus(status);
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
