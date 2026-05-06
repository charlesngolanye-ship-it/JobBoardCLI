package com.charlesngolanye.board.util;

import com.charlesngolanye.board.dao.ApplicantDAO;
import com.charlesngolanye.board.dao.ApplicationDAO;
import com.charlesngolanye.board.dao.EmployerDAO;
import com.charlesngolanye.board.dao.JobDAO;

import java.sql.SQLException;

public class DatabaseInitializer {

    public static void databaseInitializer(ApplicantDAO applicantDAO, ApplicationDAO applicationDAO,
                                           EmployerDAO employerDAO, JobDAO jobDAO) throws SQLException {
        employerDAO.createTable();
        jobDAO.createTable();
        applicantDAO.createTable();
        applicationDAO.createTable();
    }
}
