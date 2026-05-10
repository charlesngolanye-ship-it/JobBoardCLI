package com.charlesngolanye.board.service;

import com.charlesngolanye.board.dao.ApplicantDAO;
import com.charlesngolanye.board.dao.ApplicationDAO;
import com.charlesngolanye.board.dao.EmployerDAO;
import com.charlesngolanye.board.dao.JobDAO;
import com.charlesngolanye.board.dto.ApplicantApplicationView;
import com.charlesngolanye.board.model.*;
import com.charlesngolanye.board.dto.JobApplicationCount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobService {
    private final JobDAO jobDAO;
    private final EmployerDAO employerDAO;
    private final ApplicationDAO applicationDAO;
    private final ApplicantDAO applicantDAO;

    public JobService(JobDAO jobDAO, EmployerDAO employerDAO, ApplicationDAO applicationDAO, ApplicantDAO applicantDAO) {
        this.jobDAO = jobDAO;
        this.employerDAO = employerDAO;
        this.applicationDAO = applicationDAO;
        this.applicantDAO = applicantDAO;
    }

    public void addJob(Job job) {

        if (employerDAO.findById(job.getEmployerId()).isEmpty()) {
            throw new IllegalArgumentException("Employer does not exist");
        }

        if (jobDAO.findById(job.getId()).isPresent()) {
            throw new IllegalArgumentException("Job exists already");
        }

        if (job.getSalaryMin() > job.getSalaryMax()) {
            throw new IllegalArgumentException("Minimum salary exceeds maximum");
        }

        if (job.getDeadline().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline cannot be in the past");
        }

        if (job.getTitle() == null || job.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (!job.isOpen()) {
            throw new IllegalArgumentException("Job is not open for application");
        }

        jobDAO.create(job);
    }

    public void addEmployer(Employer employer) {

        if (employer.getName() == null || employer.getName().isBlank()) {
            throw new IllegalArgumentException("Employer name cannot be empty");
        }

        if (employer.getEmail() == null || employer.getEmail().isBlank()) {
            throw new IllegalArgumentException("Employer email cannot be empty");
        }

        if (!employer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        Optional<Employer> existingEmployer = employerDAO.findByEmail(employer.getEmail());
        if (existingEmployer.isPresent()) {
            throw new IllegalArgumentException("Employer already registered");
        }

        employerDAO.create(employer);
    }


    public List<Job> findOpenJobsByType(JobType jobType) {
        return jobDAO.findOpenJobsByType(jobType);
    }

    public Optional<Employer> findEmployerByEmail(String email) {
        if (employerDAO.findByEmail(email).isEmpty()) {
            return Optional.empty();
        }
        return employerDAO.findByEmail(email);
    }

    public List<JobApplicationCount> findJobsByEmployerId(int employerId) {
        List<Job> jobList = jobDAO.findByEmployerId(employerId);
        List<JobApplicationCount> jobApplicationCountList = new ArrayList<>();
        for (Job job : jobList) {
            int count = applicationDAO.countApplicationsForJob(job.getId());
            jobApplicationCountList.add(new JobApplicationCount(job, count));
        }
        return jobApplicationCountList;

    }

    public List<ApplicantApplicationView> viewApplicantsForJob(int jobId) {
        List<Application> applications = applicationDAO.findApplicationByJobId(jobId);
        List<ApplicantApplicationView> result = new ArrayList<>();

        for (Application application : applications) {
            Applicant applicant = applicantDAO.findById(application.getApplicantId()).orElseThrow();
            result.add(new ApplicantApplicationView(applicant,
                                                    application.getStatus(),
                                                    application.getAppliedAt()
                    )
            );

        }
        return result;
    }

    public void closeJob(int jobId) {
        Optional<Job> job = jobDAO.findById(jobId);
        if (job.isEmpty()) {
            throw new IllegalArgumentException("Job does not exist");
        }
        if (!job.get().isOpen()) {
            throw new IllegalArgumentException("Job already closed");
        }
        jobDAO.closeJob(jobId);
    }


}
