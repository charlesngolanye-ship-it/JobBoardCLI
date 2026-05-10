package com.charlesngolanye.board.dao;

import com.charlesngolanye.board.model.Application;
import com.charlesngolanye.board.model.Job;
import com.charlesngolanye.board.model.Status;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicationDAO {
    private Connection connection;

    public ApplicationDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable(){
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS applications (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       job_id INT NOT NULL,
                       applicant_id INT NOT NULL,
                       applied_at DATE NOT NULL,
                       status VARCHAR(20) DEFAULT 'PENDING',
                       UNIQUE (job_id, applicant_id),
                       FOREIGN KEY (job_id) REFERENCES jobs(id),
                       FOREIGN KEY (applicant_id) REFERENCES applicants(id)
                    )
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Application application){
        String sql = "INSERT INTO applications (job_id, applicant_id, applied_at, status) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, application.getJobId());
            preparedStatement.setInt(2, application.getApplicantId());
            preparedStatement.setObject(3, application.getAppliedAt()); // look up
            preparedStatement.setString(4, application.getStatus().name());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateApplicationStatus(int applicationId, Status status){
        String sql = "UPDATE applications SET status = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, applicationId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new IllegalArgumentException("Application not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Optional<Application> findApplicationById(int id){
        String sql = "SELECT * FROM applications WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Application> findApplicationByJobId(int jobId){
        List<Application> list = new ArrayList<>();

        String sql = "SELECT * FROM applications WHERE job_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             preparedStatement.setInt(1, jobId);
             ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) list.add(mapRow(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Application> findApplicationByApplicantId(int applicantId){
        List<Application> list = new ArrayList<>();

        String sql = "SELECT * FROM applications WHERE applicant_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, applicantId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) list.add(mapRow(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Optional<Application> findByJobAndApplicant(int jobId, int applicantId){
        String sql = "SELECT * FROM applications WHERE job_id = ? AND applicant_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, jobId);
            preparedStatement.setInt(2, applicantId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Application> findByStatus(Status status){
        String sql = "SELECT * FROM applications WHERE status = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, status);// look up if issue
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Application> findAll(){
        List<Application> list = new ArrayList<>();
        String sql = "SELECT * FROM applications ORDER BY status";

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) list.add(mapRow(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countApplicationsForJob(int jobId){
        String sql = "SELECT COUNT(*) FROM applications WHERE job_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // error
    }

    public int delete(int id){
        String sql = "DELETE FROM applications WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate(); // executeUpdate always returns no. of rows affected which is always an int

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // error
    }

    private Application mapRow(ResultSet resultSet){
        try {
            return new Application(
                    resultSet.getInt("id"),
                    resultSet.getInt("job_id"),
                    resultSet.getInt("applicant_id"),
                    resultSet.getObject("applied_at", LocalDate.class),
                    Status.valueOf(resultSet.getString("status"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // not sure abt the null
    }

    /*
    *Applicant flow:
    * Register / log in by email
    * Search open jobs by keyword (title or description), location, job type, or salary range
    * Apply to a job (one application per job per applicant)
    * View their application history(find by applicant) and current status(find by status)
     */
    /*
    * Employer flow:
    * Register / log in by email (no passwords — just email lookup for simplicity
    * Post a job listing
    * View their own listings with application count -> find job by employer_id?
    * View applicants for a specific job -> find application by job_id
    * Shortlist or reject an applicant
    * Close a job listing (sets is_open = false)
   */
}
