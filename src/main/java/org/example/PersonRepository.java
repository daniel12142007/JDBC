package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private final Connection connection = ConnectionDB.getConnection();
    private final String CREATE_TABLE = "create table if not exists person(id serial primary key, name varchar, age varchar)";
    private final String INSERT_INTO = "insert into person(name,age)values(?,?)";
    private final String FIND_BY_ID = "select * from person where id = ?";
    private final String FIND_ALL = "select * from person";
    private final String DELETE_BY_ID = "delete from person where id = ?";
    private final String DELETE_ALL = "truncate table person";

    public void create() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(CREATE_TABLE);
        System.out.println("created");
    }

    public void save(Person person) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO);
        preparedStatement.setString(1, person.getName());
        preparedStatement.setInt(2, person.getAge());
        preparedStatement.executeUpdate();
        System.out.println("save");
    }

    public Person findById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Person person = new Person();
        while (resultSet.next()) {
            person.setId(resultSet.getLong("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
        }
        return person;
    }

    public List<Person> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(FIND_ALL);
        List<Person> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    findById(resultSet.getLong("id"))
            );
        }
        return list;
    }

    public void deleteById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    public void deleteAll() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(DELETE_ALL);
    }
}