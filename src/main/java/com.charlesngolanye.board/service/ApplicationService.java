package com.charlesngolanye.board.service;

import com.charlesngolanye.board.dao.ApplicantDAO;
import com.charlesngolanye.board.dao.ApplicationDAO;
import com.charlesngolanye.board.dao.JobDAO;
import com.charlesngolanye.board.dto.ApplicantApplicationView;
import com.charlesngolanye.board.dto.ApplicationHistoryView;
import com.charlesngolanye.board.exception.ApplicantExistsException;
import com.charlesngolanye.board.exception.DuplicateApplicationException;
import com.charlesngolanye.board.model.Applicant;
import com.charlesngolanye.board.model.Application;
import com.charlesngolanye.board.model.Job;
import com.charlesngolanye.board.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicationService {
    private final ApplicationDAO applicationDAO;
    private final ApplicantDAO applicantDAO;
    private final JobDAO jobDAO;

    public ApplicationService(ApplicationDAO applicationDAO, ApplicantDAO applicantDAO, JobDAO jobDAO) {
        this.applicationDAO = applicationDAO;
        this.applicantDAO = applicantDAO;
        this.jobDAO = jobDAO;
    }

    public void addApplication(Application application) {
        Optional<Application> optionalApplication = applicationDAO.findApplicationById(application.getId());
        if (optionalApplication.isPresent()) {
            throw new DuplicateApplicationException("Duplicate application with id: " + application.getId());
        }
        applicationDAO.create(application);
    }

    public void addApplicant(Applicant applicant) {
        if (applicantDAO.findById(applicant.getId()).isPresent()) {
            throw new ApplicantExistsException("Applicant with id: " + applicant.getId() + " already exists");
        }
        applicantDAO.create(applicant);
    }

    public List<Applicant> getAllApplicants() {
        return applicantDAO.findAll();
    }

    public List<Application> getAllApplications() {
        return applicationDAO.findAll();
    }

    public Optional<Applicant> getApplicantByID(int id) {
        if (applicantDAO.findById(id).isEmpty()) {
            return Optional.empty();
        }
        return applicantDAO.findById(id);
    }

    public Optional<Applicant> getApplicantByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return applicantDAO.findByEmail(email);
    }

    public void updateApplicationStatus(int applicationId, Status status) {
        applicationDAO.updateApplicationStatus(applicationId, status);
    }

    public List<ApplicationHistoryView> viewApplicationHistory(int applicantId) {
        List<Application> applications = applicationDAO.findApplicationByApplicantId(applicantId);
        List<ApplicationHistoryView> result = new ArrayList<>();

        for (Application application : applications) {
            Job job = jobDAO.findById(application.getJobId()).orElseThrow();
            result.add(
                    new ApplicationHistoryView(
                    job,
                    application.getStatus(),
                    application.getAppliedAt()
            )

            );
        }
        return result;
    }
}
