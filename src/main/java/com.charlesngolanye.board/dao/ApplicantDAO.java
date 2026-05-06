package com.charlesngolanye.board.dao;

import com.charlesngolanye.board.model.Applicant;
import com.charlesngolanye.board.model.Application;
import com.charlesngolanye.board.model.Employer;
import com.charlesngolanye.board.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicantDAO {
    private Connection connection;

    public ApplicantDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable(){
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS applicants (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(150) NOT NULL,
                       email VARCHAR(150) UNIQUE NOT NULL,
                       skills TEXT
                    )
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Applicant applicant){
        String sql = "INSERT INTO applicants (name, email, skills) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, applicant.getName());
            preparedStatement.setString(2, applicant.getEmail());
            preparedStatement.setString(3, applicant.getSkills());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Applicant> findById(int id){
        String sql = "SELECT * FROM applicants WHERE id = ?";
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

    public Optional<Applicant> findByEmail(String email){
        String sql = "SELECT * FROM applicants WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Applicant> findByJob(Job job){
        String sql = "SELECT * FROM applicants WHERE job = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, job);// trying to set an enum - check if potential error
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Applicant> findAll(){
        List<Applicant> list = new ArrayList<>();
        String sql = "SELECT * FROM applicants ORDER BY name";

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) list.add(mapRow(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int update(Applicant applicant) {
        String sql = "UPDATE applicants SET name = ?, email = ?, skills = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, applicant.getName());
            preparedStatement.setString(2, applicant.getEmail());
            preparedStatement.setString(3, applicant.getSkills());
            preparedStatement.setInt(4, applicant.getId());

            return preparedStatement.executeUpdate(); // returns no. of rows affected
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -0; // no row updated
    }


    public int delete(int id){
        String sql = "DELETE FROM applicants WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate(); // executeUpdate always returns no. of rows affected which is always an int

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // error
    }

    private Applicant mapRow(ResultSet resultSet){
        try {
            return new Applicant(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("skills")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // not sure abt the null
    }
}
