package com.charlesngolanye.board.ui;

import com.charlesngolanye.board.dto.ApplicantApplicationView;
import com.charlesngolanye.board.exception.EmailNotFoundException;
import com.charlesngolanye.board.exception.EmployerExistsException;
import com.charlesngolanye.board.exception.JobExistsException;
import com.charlesngolanye.board.exception.JobNotFoundException;
import com.charlesngolanye.board.model.Employer;
import com.charlesngolanye.board.model.Job;
import com.charlesngolanye.board.model.JobType;
import com.charlesngolanye.board.dto.JobApplicationCount;
import com.charlesngolanye.board.model.Status;
import com.charlesngolanye.board.service.ApplicationService;
import com.charlesngolanye.board.service.JobService;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmployerMenu {
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final Scanner userInput = new Scanner(System.in);
    public static Employer loggedInEmployer = null;

    public EmployerMenu(JobService jobService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    public void start() {
        //Employer loggedInEmployer = null;

        while (true) {
            printEmployerMenu();

            int choice = userInput.nextInt();
            userInput.nextLine();

            switch (choice) {
                case 1:
                    registerEmployer();
                    break;

                case 2:
                    loginEmployer();
                    break;

                case 3:
                    postJob();
                    break;

                case 4:
                    viewJobListings();
                    break;
                case 5:
                    viewJobApplicants();
                    break;

                case 6:
                    processApplication();
                    break;

                case 7:
                    closeJob();
                    break;

                case 0:
                    return;
            }
        }


    }



    private static void printEmployerMenu() {
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

    private void registerEmployer() {
        while (true) {
            System.out.println("Enter name");
            String name = userInput.nextLine();

            System.out.println("Enter email");
            String email = userInput.nextLine();

            System.out.println("Enter industry");
            String industry = userInput.nextLine();

            try {
                Employer employer = new Employer(name, email, industry);
                jobService.addEmployer(employer);
                System.out.println("Employer registered");
                break;

            } catch (EmployerExistsException e) {
                System.out.println(e.getMessage() + " please try again");
            }

        }
    }

    private void loginEmployer() {
        while (true) {


                System.out.println("Enter email");
                String email = userInput.nextLine();

                Optional<Employer> employerOptional = jobService.findEmployerByEmail(email);
                if (employerOptional.isPresent()) {
                    loggedInEmployer = employerOptional.get();
                    System.out.println("Successfully logged in");
                    break;
                } else {
                    System.out.println("Email not found, please try again");
                }

                // what does e.getMessage() return? I would like email not found, please try again
                // throw new DuplicateApplicationException("Duplicate application with id: " + application.getId());
                // what is the difference with throw new DuplicationException ...one throws, the other catches

        }

    }


    private void postJob() {
        while (true) {
            if (loggedInEmployer == null) {
                System.out.println("Please login first");
                return;
            }
            int employerId = loggedInEmployer.getId();

            System.out.print("Enter job title");
            String title = userInput.nextLine();

            System.out.print("Enter job description");
            String description = userInput.nextLine();

            System.out.print("Enter job location");
            String location = userInput.nextLine();

            System.out.print("Enter job type");
            JobType jobType;
            try {
                jobType = JobType.valueOf(userInput.nextLine().trim().toUpperCase()
                        .replace(" ", "_")
                        .replace("-", "_"));
            } catch (IllegalArgumentException e) {
                System.out.println(" Invalid job type");
                continue;
            }

            System.out.print("Enter minimum salary");
            Double minimumSalary;
            try {
                minimumSalary = userInput.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input");
                userInput.nextLine();
                continue;
            }

            System.out.print("Enter maximum salary");
            Double maximumSalary;
            try {
                maximumSalary = userInput.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input");
                userInput.nextLine();
                continue;
            }

            LocalDate postedAt = LocalDate.now();

            System.out.print("Enter deadline date (YYYY-MM-DD)");
            LocalDate deadline;
            try {
                deadline = LocalDate.parse(userInput.nextLine());
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input");
                userInput.nextLine();
                continue;
            }


            boolean isOpen = true;

            try {
                Job job = new Job(employerId, title, description, location, jobType,
                        minimumSalary, maximumSalary, postedAt, deadline, isOpen);
                jobService.addJob(job);
                System.out.println("Job added");
                break;

            } catch (JobExistsException e) {
                System.out.println(e.getMessage() + " please try again");
            }
        }

    }


    private void viewJobListings() {
        while (true) {
            if (loggedInEmployer == null) {
                System.out.println("Please login first");
                break;
            }

            List<JobApplicationCount> jobs = jobService.findJobsByEmployerId(loggedInEmployer.getId());
            for (JobApplicationCount jobApplicationCount : jobs) {
                System.out.println(jobApplicationCount.job().getTitle()
                        + "| Applications: " + jobApplicationCount.applicationCount());

            break;
            }
        }
    }

    private void viewJobApplicants() {
        while (true) {
            try {
                if (loggedInEmployer == null) {
                    System.out.println("Please login first");
                    return;
                }

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

            } catch (JobNotFoundException e) {
                System.out.println(e.getMessage() + " please try again");
            }
        }
    }

    private void processApplication() {
        while (true) {
            if (loggedInEmployer == null) {
                System.out.println("Please login first");
                return;
            }

            System.out.println("Enter application id:");
            int applicationId = userInput.nextInt();
            userInput.nextLine();

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
        }
    }

    private void closeJob() {
        while (true) {
            try {
                if (loggedInEmployer == null) {
                    System.out.println("Please login first");
                    return;
                }

                System.out.println("Enter job id to close: ");
                int closeJobId = userInput.nextInt();
                userInput.nextLine();

                jobService.closeJob(closeJobId);
                System.out.println("Job closed");
                break;

            } catch (JobNotFoundException e) {
                System.out.println("Job not found");
            }

        }
    }
}

