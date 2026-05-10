package com.charlesngolanye.board.ui;

import com.charlesngolanye.board.dto.ApplicantApplicationView;
import com.charlesngolanye.board.model.Employer;
import com.charlesngolanye.board.model.Job;
import com.charlesngolanye.board.model.JobType;
import com.charlesngolanye.board.dto.JobApplicationCount;
import com.charlesngolanye.board.model.Status;
import com.charlesngolanye.board.service.ApplicationService;
import com.charlesngolanye.board.service.JobService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmployerMenu {
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final Scanner userInput = new Scanner(System.in);

    public EmployerMenu(JobService jobService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    public void start() {
        Employer loggedInEmployer = null;

        while (true) {
            printEmployerMenu();

            int choice = userInput.nextInt();
            userInput.nextLine();

            switch (choice) {
                case 1:
                    Employer employer = registerEmployer();
                    jobService.addEmployer(employer);
                    break;

                case 2:
                    String email = loginEmployer();
                    Optional<Employer> employerOptional = jobService.findEmployerByEmail(email);
                    if (employerOptional.isPresent()) {
                        loggedInEmployer = employerOptional.get();
                        System.out.println("Successfully logged in");
                    }
                    else {
                        System.out.println("Invalid email");
                    }
                    break;

                case 3:
                    Job job = postJob();
                    jobService.addJob(job);
                    break;

                case 4:
                    if (loggedInEmployer == null) {
                        System.out.println("Please login first");
                        break;
                    }

                    List<JobApplicationCount> jobs = jobService.findJobsByEmployerId(loggedInEmployer.getId());
                    for (JobApplicationCount jobApplicationCount : jobs) {
                        System.out.println(jobApplicationCount.job().getTitle()
                        + "| Applications: " + jobApplicationCount.applicationCount());

                    }

                    break;
                case 5:
                    System.out.print("Enter job id:");
                    int jobId = userInput.nextInt();
                    userInput.nextLine();

                    List<ApplicantApplicationView> applicants = jobService.viewApplicantsForJob(jobId);
                    for (ApplicantApplicationView applicant : applicants) {
                        System.out.println(applicant.applicant().getName()
                                + " | " + applicant.status()
                        );
                    }

                    break;

                case 6:
                    System.out.println("Enter application id:");
                    int applicationId = userInput.nextInt();

                    System.out.println("""
                            1. Shortlist
                            2. Reject
                            """);
                    int statusChoice = userInput.nextInt();
                    userInput.nextLine();

                    Status status;
                    if (statusChoice == 1) {
                        status = Status.SHORTLISTED;
                    } else if (statusChoice == 2) {
                        status = Status.REJECTED;
                    } else {
                        System.out.println("Invalid choice");
                        break;
                    }

                    applicationService.updateApplicationStatus(applicationId, status);
                    System.out.println("Application updated");
                    break;

                case 7:
                    System.out.println("Enter job id to close: ");
                    int closeJobId = userInput.nextInt();
                    userInput.nextLine();

                    jobService.closeJob(closeJobId);
                    System.out.println("Job closed");
                    break;

                case 0:
                    return;
            }
        }




    }

    private static void printEmployerMenu(){
        System.out.println("""
                    1. Register Employer
                    2. Login Employer
                    3. Post Job
                    4. View Job Listings with application counts
                    5. View Job Applicants
                    6. Shortlist or Reject Applicant
                    7. Close a job listing (sets is_open = false)
                    0. Exit
        """);

    }

    private Employer registerEmployer() {
        System.out.println("Enter name");
        String name = userInput.nextLine();

        System.out.println("Enter email");
        String email = userInput.nextLine();

        System.out.println("Enter industry");
        String industry = userInput.nextLine();

        System.out.println("Employer registered");

        return new Employer(name, email, industry);
    }

    private String loginEmployer() {
        System.out.println("Enter email");

        return userInput.nextLine();
    }

    private Job postJob() {
        System.out.print("Enter employer employerId");
        int employerId = userInput.nextInt();
        userInput.nextLine();

        System.out.print("Enter job title");
        String title = userInput.nextLine();

        System.out.print("Enter job description");
        String description = userInput.nextLine();

        System.out.print("Enter job location");
        String location = userInput.nextLine();

        System.out.print("Enter job type");
        JobType jobType = JobType.valueOf(userInput.nextLine());

        System.out.print("Enter minimum salary");
        Double minimumSalary = userInput.nextDouble();

        System.out.print("Enter maximum salary");
        Double maximumSalary = userInput.nextDouble();
        userInput.nextLine();

        LocalDate postedAt = LocalDate.now();

        System.out.print("Enter deadline date (YYYY-MM-DD)");
        LocalDate deadline = LocalDate.parse(userInput.nextLine());

        boolean isOpen = true;

        return new Job(employerId, title, description, location, jobType,
                        minimumSalary, maximumSalary, postedAt, deadline, isOpen);

    }
}

