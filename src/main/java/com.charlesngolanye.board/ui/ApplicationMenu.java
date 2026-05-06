package com.charlesngolanye.board.ui;


import com.charlesngolanye.board.model.Applicant;
import com.charlesngolanye.board.model.Application;
import com.charlesngolanye.board.model.Status;
import com.charlesngolanye.board.service.ApplicationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ApplicationMenu {
    private final ApplicationService applicationService;
    private final Scanner userInput = new Scanner(System.in);

    public ApplicationMenu(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void start() {
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
                    Applicant applicantLogin = loginApplicant();
                    applicationService.getApplicantByEmail(applicantLogin.getEmail());
                    break;

                case 3:
                    List<Application> applications = applicationService.getAllApplications();
                    if (applications.isEmpty()) System.out.println("No applications found.");
                    else applications.forEach(System.out::println);
                    break;

                case 4:
                    Application applyJob = applyJob();
                    applicationService.addApplication(applyJob);
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

    private Applicant loginApplicant() {
        System.out.println("Enter email");
        String email = userInput.nextLine();

        System.out.println("Successfully logged in");

        return new Applicant(email);
    }

    private Application applyJob() {
        System.out.print("Enter jobId");
        int jobId = userInput.nextInt();
        userInput.nextLine();

        System.out.print("Enter applicantId");
        int applicantId = userInput.nextInt();
        userInput.nextLine();

        LocalDate appliedAt = LocalDate.now();

        Status status = Status.PENDING;

        return new Application(jobId, applicantId, appliedAt, status);
    }
}
