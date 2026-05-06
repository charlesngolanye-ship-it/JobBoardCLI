package com.charlesngolanye.board.service;

import com.charlesngolanye.board.dao.EmployerDAO;
import com.charlesngolanye.board.dao.JobDAO;
import com.charlesngolanye.board.model.Employer;
import com.charlesngolanye.board.model.Job;

import java.util.List;
import java.util.Optional;

public class JobService {
    private final JobDAO jobDAO;
    private final EmployerDAO employerDAO;

    public JobService(JobDAO jobDAO, EmployerDAO employerDAO) {
        this.jobDAO = jobDAO;
        this.employerDAO = employerDAO;
    }

    public void addJob(Job job) {
        jobDAO.create(job);
    }

    public void addEmployer(Employer employer) {
        employerDAO.create(employer);
    }

    public List<Job> jobList () {
        return jobDAO.findAll();
    }

    public Optional<Employer> findEmployerByEmail(String email) {
        return employerDAO.findByEmail(email);
    }
//
//    public Optional<Course> findCourseByCode(String code) {
//        return courseDAO.findCourseByCode(code);
//    }
//
//    public int deleteCourse(int id)  {
//        return courseDAO.delete(id);
//    }
}
