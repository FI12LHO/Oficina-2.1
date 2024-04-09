package br.marques.model;

import br.marques.connection.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class Client {
    private String id;
    private String name;
    private String email;
    private String phone;
    private final Connection connection;

    public Client(String name, String email, String phone) {
        Database database = new Database(false, false);

        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.connection = database.getConnection();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean registerClient() {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(
            "INSERT INTO Clients " +
                "(id, name, email, phone)" +
                String.format(
                        "VALUES ('%s', '%s', '%s', '%s')",
                        this.id, this.name, this.email, this.phone
                )
            );

            return  true;
        } catch (SQLException error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

    public static ArrayList<ArrayList<String>> getAllClients() {
        try {
            Database database = new Database(false, false);
            Statement statement = database.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Clients ORDER BY name ASC");

            ArrayList<ArrayList<String>> clients = new ArrayList<>();

            while (result.next()) {
                ArrayList<String> array = new ArrayList<>();
                array.add(result.getString("id"));
                array.add(result.getString("name"));
                array.add(result.getString("email"));
                array.add(result.getString("phone"));
                clients.add(array);
            }

            return clients;

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }

        return null;
    }

    public static ArrayList<String> getClient(String id) {
        try {
            Database database = new Database(false, false);
            Statement statement = database.getConnection().createStatement();
            ResultSet result = statement.executeQuery(
                String.format("SELECT * FROM Clients WHERE id = '%s'", id)
            );

            ArrayList<String> array = new ArrayList<>();
            array.add(result.getString("id"));
            array.add(result.getString("name"));
            array.add(result.getString("email"));
            array.add(result.getString("phone"));

            return array;

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }

        return null;
    }

    public static void deleteClient(String id) {
        try {
            Database database = new Database(false, false);
            Statement statement = database.getConnection().createStatement();
            statement.executeUpdate(
                    String.format("DELETE FROM Clients WHERE id = '%s'", id)
            );

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }
}
