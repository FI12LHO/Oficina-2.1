package br.marques.connection;

import java.sql.*;

public class Database {
    private Connection conn = null;

    public Database(boolean runMigration, boolean runSeeds) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:sample.db");

            if (runMigration) {
                this.runMigrations();
            }

            if (runSeeds) {
                this.runSeeds();
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Metodo responsavel por retornar a conexao
     * @return {Connection || null}
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Metodo responsavel por criar as tabelas no banco de dados
     */
    public void runMigrations() {
        if (conn == null) {
            return;
        }

        try {
            Statement statement = conn.createStatement();

            // Client
            statement.executeUpdate("DROP TABLE IF EXISTS Clients");
            statement.executeUpdate(
            "CREATE TABLE Clients (id string, name string, email string, phone string, PRIMARY KEY (id))"
            );

            // Estimate
            statement.executeUpdate("DROP TABLE IF EXISTS Estimates");
            statement.executeUpdate(
            "CREATE TABLE Estimates (id string, client_id string, date string, seller string, description string, value float, PRIMARY KEY (id))"
            );

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Metodo responsavel por popular o banco de dados
     */
    public void runSeeds() {
        if (conn == null) {
            return;
        }

        try {
            Statement statement = conn.createStatement();

            // Client
            statement.executeUpdate(
            "INSERT INTO Clients " +
                "(id, name, email, phone)" +
                "VALUES ('abc123', 'Jane Doe', 'jane-doe@mail.com', '0087654321')"
            );

            // Estimate
            statement.executeUpdate(
            "INSERT INTO Estimates " +
                "(id, client_id, date, seller, description, value)" +
                "VALUES ('123abc', 'abc123', '15-01-2022 14:10', 'John Doe', 'Ole de freio', 34.99)"
            );

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }
}
