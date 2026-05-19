package com.charlesngolanye.board.ui;


import com.charlesngolanye.board.dto.ApplicationHistoryView;
import com.charlesngolanye.board.exception.ApplicantExistsException;
import com.charlesngolanye.board.exception.ApplicantNotFoundException;
import com.charlesngolanye.board.model.Status;
import com.charlesngolanye.board.model.Applicant;
import com.charlesngolanye.board.model.Application;
import com.charlesngolanye.board.model.Job;
import com.charlesngolanye.board.model.JobType;
import com.charlesngolanye.board.service.ApplicationService;
import com.charlesngolanye.board.service.JobService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ApplicationMenu {
    private final ApplicationService applicationService;
    private final JobService jobService;

    private final Scanner userInput = new Scanner(System.in);
    public static Applicant loggedInApplicant = null;

    public ApplicationMenu(ApplicationService applicationService, JobService jobService) {
        this.applicationService = applicationService;
        this.jobService = jobService;
    }

    public void start() {

        while (true) {
            printApplicantMenu();

            int choice = userInput.nextInt();
            userInput.nextLine();

            switch (choice) {
                case 1:
                    registerApplicant();
                    break;

                case 2:
                    loginApplicant();
                    break;

                case 3:
                    searchOpenJobs();
                    break;

                case 4:
                    applyJob();
                    break;
                case 5:
                    viewJobApplications();
                    break;
                case 0:
                    return;
            }
        }


    }


    private static void printApplicantMenu() {
        System.out.println("""
                            1. Register Applicant
                            2. Login Applicant
                            3. Search open jobs (keyword, location, type, salary range)
                            4. Apply Job
                            5. View Job Applications
                            0. Exit
                """);

    }

    private void registerApplicant() {
        while (true) {
            try {
                System.out.println("Enter name");
                String name = userInput.nextLine();

                System.out.println("Enter email");
                String email = userInput.nextLine();

                System.out.println("Enter skills");
                String skills = userInput.nextLine();

                Applicant applicant = new Applicant(name, email, skills);
                applicationService.addApplicant(applicant);
                System.out.println("Applicant registered");
                return;

            } catch (IllegalArgumentException | ApplicantExistsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void loginApplicant() {
        while (true) {
            try {
                System.out.println("Enter email");
                String email = userInput.nextLine();

                Optional<Applicant> applicantOptional = applicationService.getApplicantByEmail(email);
                if (applicantOptional.isPresent()) {
                    loggedInApplicant = applicantOptional.get();
                    System.out.println("Successfully logged in");
                    return;
                } else {
                    System.out.println("Applicant not found");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void searchOpenJobs() {
        while (true) {
            System.out.print("""
                    Enter job type:
                    FULL_TIME
                    PART_TIME
                    CONTRACT
                    REMOTE
                    """);
            String typeInput = userInput.nextLine();

            JobType jobType = JobType.valueOf(typeInput.toUpperCase());
            List<Job> jobs = jobService.findOpenJobsByType(jobType);

            if (jobs.isEmpty()) {
                System.out.println("No jobs found");
                return;
            } else {
                for (Job job : jobs) {
                    System.out.println(
                            job.getId() + " | "
                                    + job.getTitle()
                                    + " | " + job.getLocation()
                                    + " | " + job.getJobType()
                    );
                }
            }
        }
    }

    private void applyJob() {
        while (true) {
            if (loggedInApplicant == null) {
                System.out.println("Please login first");
                return;
            }

            System.out.print("Enter jobId");
            int jobId = userInput.nextInt();
            userInput.nextLine();
            LocalDate appliedAt = LocalDate.now();
            Status status = Status.PENDING;

            Application application = new Application(jobId, loggedInApplicant.getId(), appliedAt, status);
            applicationService.addApplication(application);
            System.out.println("Application submitted");

        }
    }

    private void viewJobApplications() {
        while (true) {
            if (loggedInApplicant == null) {
                System.out.println("Please login first");
                return;
            }
            List<ApplicationHistoryView> history = applicationService.viewApplicationHistory(loggedInApplicant.getId());

            if (history.isEmpty()) {
                System.out.println("No application found");
            } else {
                for (ApplicationHistoryView item : history) {
                    System.out.println(item.job().getTitle() + " | " + item.status() + " | Applied: " + item.appliedAt());
                }
            }
        }
    }
}
