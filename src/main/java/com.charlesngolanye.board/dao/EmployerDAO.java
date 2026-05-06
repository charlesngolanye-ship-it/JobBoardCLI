package com.charlesngolanye.board.dao;

import com.charlesngolanye.board.model.Employer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployerDAO {
    private Connection connection;

    public EmployerDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable(){
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS employers (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(150) NOT NULL,
                       email VARCHAR(150) UNIQUE NOT NULL,
                       industry VARCHAR(100)
                    )
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Employer employer){
        String sql = "INSERT INTO employers (name, email, industry) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employer.getName());
            preparedStatement.setString(2, employer.getEmail());
            preparedStatement.setString(3, employer.getIndustry());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Employer> findById(int id){
        String sql = "SELECT * FROM employers WHERE id = ?";
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

    public Optional<Employer> findByEmail(String email){
        String sql = "SELECT * FROM employers WHERE email = ?";
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

    public List<Employer> findAll(){
        List<Employer> list = new ArrayList<>();
        String sql = "SELECT * FROM employers ORDER BY name";

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) list.add(mapRow(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int update(Employer employer) {
        String sql = "UPDATE employers SET name = ?, email = ?, industry = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employer.getName());
            preparedStatement.setString(2, employer.getEmail());
            preparedStatement.setString(3, employer.getIndustry());
            preparedStatement.setInt(4, employer.getId());

            return preparedStatement.executeUpdate(); // returns no. of rows affected
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -0; // no row updated
    }


    public int delete(int id){
        String sql = "DELETE FROM employers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate(); // executeUpdate always returns no. of rows affected which is always an int

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // error
    }

    private Employer mapRow(ResultSet resultSet){
        try {
            return new Employer(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("industry")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // not sure abt the null
    }

}
