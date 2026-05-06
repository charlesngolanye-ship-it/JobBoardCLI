package com.charlesngolanye.board.ui;

import com.charlesngolanye.board.model.Employer;
import com.charlesngolanye.board.model.Job;
import com.charlesngolanye.board.model.JobType;
import com.charlesngolanye.board.model.Status;
import com.charlesngolanye.board.service.JobService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmployerMenu {
    private final JobService jobService;
    private final Scanner userInput = new Scanner(System.in);

    public EmployerMenu(JobService jobService) {
        this.jobService = jobService;
    }

    public void start() {
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
                    List<Job> jobs = jobService.jobList();
                    if (jobs.isEmpty()) System.out.println("No listed jobs.");
                    else jobs.forEach(System.out::println);

                    break;
                case 5:

                    break;

                case 6:
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
                    4. View Job Listings
                    5. View Job Applicants
                    6. Shortlist or Reject Applicant
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
        String email = userInput.nextLine();

        //System.out.println("Successfully logged in");

        return email;
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

        boolean isOpen = false;

        return new Job(employerId, title, description, location, jobType,
                        minimumSalary, maximumSalary, postedAt, deadline, isOpen);

    }
}

