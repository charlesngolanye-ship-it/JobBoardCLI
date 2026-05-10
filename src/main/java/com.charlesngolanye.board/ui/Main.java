package com.charlesngolanye.board.ui;

import com.charlesngolanye.board.dao.ApplicantDAO;
import com.charlesngolanye.board.dao.ApplicationDAO;
import com.charlesngolanye.board.dao.EmployerDAO;
import com.charlesngolanye.board.dao.JobDAO;
import com.charlesngolanye.board.service.ApplicationService;
import com.charlesngolanye.board.service.JobService;
import com.charlesngolanye.board.util.DatabaseConfig;
import com.charlesngolanye.board.util.DatabaseInitializer;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
                try (Connection connection = DatabaseConfig.getConnection()) {

                    // DAOs
                    EmployerDAO employerDAO = new EmployerDAO(connection);
                    JobDAO jobDAO = new JobDAO(connection);
                    ApplicantDAO applicantDAO = new ApplicantDAO(connection);
                    ApplicationDAO applicationDAO = new ApplicationDAO(connection);

                    // Init DB
                    DatabaseInitializer.databaseInitializer(
                            applicantDAO, applicationDAO, employerDAO, jobDAO
                    );

                    // Services
                    JobService jobService = new JobService(jobDAO, employerDAO, applicationDAO, applicantDAO);
                    ApplicationService applicationService = new ApplicationService(applicationDAO, applicantDAO, jobDAO);

                    // Menus
                    EmployerMenu employerMenu = new EmployerMenu(jobService,applicationService);
                    ApplicationMenu applicationMenu = new ApplicationMenu(applicationService, jobService);

                    // Routing
                    System.out.println("Select role (Employer(E) / Applicant(A)");
                    String role = userInput.nextLine();

                    if (role.equals("E")) {
                        employerMenu.start();
                    } else if (role.equals("A")) {
                        applicationMenu.start();
                    } else  {
                        System.out.println("Invalid input");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }



}