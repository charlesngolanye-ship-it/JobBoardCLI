package com.charlesngolanye.board.dao;

import com.charlesngolanye.board.model.Employer;
import com.charlesngolanye.board.model.Job;
import com.charlesngolanye.board.model.JobType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobDAO {
    private Connection connection;

    public JobDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable(){
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS jobs (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       employer_id INT NOT NULL,
                       title VARCHAR(150) NOT NULL,
                       description TEXT,
                       location VARCHAR(100),
                       job_type VARCHAR(20),
                       salary_min DOUBLE,
                       salary_max DOUBLE,
                       posted_at DATE NOT NULL,
                       deadline DATE,
                       is_open BOOLEAN DEFAULT TRUE,
                       FOREIGN KEY (employer_id) REFERENCES employers(id)
                    )
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Job job){
        String sql = "INSERT INTO jobs (employer_id, title, description, location, " +
                     "job_type, salary_min, salary_max, posted_at, deadline, is_open) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, job.getEmployerId());
            preparedStatement.setString(2, job.getTitle());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setString(4, job.getLocation());
            preparedStatement.setString(5, job.getJobType().name());
            // You want to store it as a String (since your DB column is VARCHAR). .name() converts:JobType.FULL_TIME → "FULL_TIME"
            // when reading from DB JobType.valueOf(resultSet.getString("job_type"));
            // This converts "FULL_TIME" back into your enum
            preparedStatement.setDouble(6, job.getSalaryMin());
            preparedStatement.setDouble(7, job.getSalaryMax());
            preparedStatement.setObject(8, job.getPostedAt());
            preparedStatement.setObject(9, job.getDeadline());
            preparedStatement.setBoolean(10, job.isOpen());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Job> findById(int id){
        String sql = "SELECT * FROM jobs WHERE id = ?";
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

    public List<Job> findByEmployerId(int id){
        List<Job> jobList = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE employer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    jobList.add(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return jobList;
    }

    public List<Job> findOpenJobsByType(JobType jobType){
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE job_type = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, jobType.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                jobs.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }


    public List<Job> findAll(){
        List<Job> list = new ArrayList<>();
        String sql = "SELECT * FROM jobs ORDER BY title";

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) list.add(mapRow(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int update(Job job) {
        String sql = "UPDATE jobs SET employer_id = ?, title = ?, description = ?, location = ? job_type = ?" +
                "salary_min = ?, salary_max = ?, posted_at = ? deadline = ?" +
                "is_open = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, job.getEmployerId());
            preparedStatement.setString(2, job.getTitle());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setString(4, job.getLocation());
            preparedStatement.setString(5, job.getJobType().name());
            preparedStatement.setDouble(6, job.getSalaryMin());
            preparedStatement.setDouble(7, job.getSalaryMax());
            preparedStatement.setObject(8, job.getPostedAt());
            preparedStatement.setObject(9, job.getDeadline());
            preparedStatement.setBoolean(10, job.isOpen());
            return preparedStatement.executeUpdate(); // returns no. of rows affected
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -0; // no row updated
    }


    public int delete(int id){
        String sql = "DELETE FROM jobs WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate(); // executeUpdate always returns no. of rows affected which is always an int

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // error
    }

    public void closeJob(int jobId) {
        String sql = "UPDATE jobs SET is_open = false WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, jobId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Job not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Job mapRow(ResultSet resultSet){
        try {
            return new Job(
                    resultSet.getInt("id"),
                    resultSet.getInt("employer_id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("location"),
                    JobType.valueOf(resultSet.getString("job_type")),
                    (Double)resultSet.getObject("salary_min"),
                    (Double)resultSet.getObject("salary_max"),
                    resultSet.getObject("posted_at", LocalDate.class),
                    resultSet.getObject("deadline", LocalDate.class),
                    resultSet.getBoolean("is_open")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // not sure abt the null
    }
}
