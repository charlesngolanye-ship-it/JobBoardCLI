package com.charlesngolanye.board.service;

import com.charlesngolanye.board.dao.ApplicantDAO;
import com.charlesngolanye.board.dao.ApplicationDAO;
import com.charlesngolanye.board.model.Applicant;
import com.charlesngolanye.board.model.Application;

import java.util.List;
import java.util.Optional;

public class ApplicationService {
    private final ApplicationDAO applicationDAO;
    private final ApplicantDAO applicantDAO;

    public ApplicationService(ApplicationDAO applicationDAO, ApplicantDAO applicantDAO) {
        this.applicationDAO = applicationDAO;
        this.applicantDAO = applicantDAO;
    }

    public void addApplication(Application application) {
        applicationDAO.create(application);
    }

    public void addApplicant(Applicant applicant) {
        applicantDAO.create(applicant);
    }

    public List<Applicant> getAllApplicants() {
        return applicantDAO.findAll();
    }

    public List<Application> getAllApplications() {
        return applicationDAO.findAll();
    }

    public Optional<Applicant> getApplicantByID(int id) {
        return applicantDAO.findById(id);
    }

    public Optional<Applicant> getApplicantByEmail(String email) {
        return applicantDAO.findByEmail(email);
    }
}
