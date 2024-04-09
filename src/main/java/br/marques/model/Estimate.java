package br.marques.model;

import br.marques.connection.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Estimate {
    private String id;
    private String client_id;
    private String date;
    private String seller;
    private String description;
    private Double value;
    private final Connection connection;

    public Estimate(String client_id, String seller, String description, Double value) {
        Database database = new Database(false, false);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter idFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        this.id = now.format(idFormatter);
        this.date = now.format(dateFormatter);
        this.client_id = client_id;
        this.seller = seller;
        this.description = description;
        this.value = value;
        this.connection = database.getConnection();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public boolean registerEstimative() {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(
            "INSERT INTO Estimates " +
                "(id, client_id, date, seller, description, value)" +
                String.format(
                        "VALUES ('%s', '%s', '%s', '%s', '%s', %s)",
                        this.id, this.client_id, this.date, this.seller, this.description, this.value
                )
            );

            return  true;
        } catch (SQLException error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

    public static ArrayList<ArrayList<String>> getAllEstimates() {
        try {
            Database database = new Database(false, false);
            Statement statement = database.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Estimates ORDER BY date DESC");

            ArrayList<ArrayList<String>> clients = new ArrayList<>();

            while (result.next()) {
                ArrayList<String> array = new ArrayList<>();
                array.add(result.getString("id"));
                array.add(result.getString("client_id"));
                array.add(result.getString("seller"));
                array.add(result.getString("description"));
                array.add(result.getString("date"));
                array.add(result.getString("value"));
                clients.add(array);
            }

            return clients;

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }

        return null;
    }

    public static ArrayList<String> getEstimate(String id) {
        try {
            Database database = new Database(false, false);
            Statement statement = database.getConnection().createStatement();
            ResultSet result = statement.executeQuery(
                    String.format("SELECT * FROM Estimates WHERE id = '%s'", id)
            );

            ArrayList<String> array = new ArrayList<>();
            array.add(result.getString("id"));
            array.add(result.getString("client_id"));
            array.add(result.getString("seller"));
            array.add(result.getString("description"));
            array.add(result.getString("date"));
            array.add(result.getString("value"));

            return array;

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }

        return null;
    }

    public static void deleteEstimate(String id) {
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
