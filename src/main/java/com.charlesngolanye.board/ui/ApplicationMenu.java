package com.charlesngolanye.board.ui;


import com.charlesngolanye.board.dto.ApplicationHistoryView;
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

    public ApplicationMenu(ApplicationService applicationService, JobService jobService) {
        this.applicationService = applicationService;
        this.jobService = jobService;
    }

    public void start() {
        Applicant loggedInApplicant = null;

        while (true) {
            printApplicantMenu();

            int choice = userInput.nextInt();
            userInput.nextLine();

            switch (choice) {
                case 1:
                    Applicant applicant = registerApplicant();
                    applicationService.addApplicant(applicant);
                    break;

                case 2:
                    String email = loginApplicant();
                    Optional<Applicant> applicantOptional = applicationService.getApplicantByEmail(email);
                    if (applicantOptional.isPresent()) {
                        loggedInApplicant = applicantOptional.get();
                        System.out.println("Successfully logged in");
                    }
                    else {
                        System.out.println("Invalid email");
                    }
                    break;

                case 3:
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
                    break;

                case 4:
                    if(loggedInApplicant == null) {
                        System.out.println("Please login first");
                        break;
                    }
                    Application application = applyJob(loggedInApplicant.getId());
                    applicationService.addApplication(application);
                    System.out.println("Application submitted");

                    break;
                case 5:
                    if(loggedInApplicant == null) {
                        System.out.println("Please login first");
                        break;
                    }
                    List<ApplicationHistoryView> history = applicationService.viewApplicationHistory(loggedInApplicant.getId());

                    if(history.isEmpty()) {
                        System.out.println("No application found");
                    } else {
                        for (ApplicationHistoryView item : history) {
                            System.out.println(item.job().getTitle() + " | " + item.status() + " | Applied: " + item.appliedAt());
                        }
                    }
                    break;

                case 6:
                    break;

                case 0:
                    return;
            }
        }




    }

    private static void printApplicantMenu(){
        System.out.println("""
                    1. Register Applicant
                    2. Login Applicant
                    3. Search open jobs (keyword, location, type, salary range)
                    4. Apply Job
                    5. View Job Applications
                    0. Exit
        """);

    }

    private Applicant registerApplicant() {
        System.out.println("Enter name");
        String name = userInput.nextLine();

        System.out.println("Enter email");
        String email = userInput.nextLine();

        System.out.println("Enter skills");
        String skills = userInput.nextLine();

        System.out.println("Applicant registered");

        return new Applicant(name, email, skills);
    }

    private String loginApplicant() {
        System.out.println("Enter email");
        return userInput.nextLine();
    }

    private Application applyJob(int applicantId) {
        System.out.print("Enter jobId");
        int jobId = userInput.nextInt();
        userInput.nextLine();


        LocalDate appliedAt = LocalDate.now();

        Status status = Status.PENDING;

        return new Application(jobId, applicantId, appliedAt, status);
    }
}
