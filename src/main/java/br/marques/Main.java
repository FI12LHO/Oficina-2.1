package br.marques;


import br.marques.connection.Database;
import br.marques.model.Client;
import br.marques.model.Estimate;

public class Main {
    public static void main(String[] args) {
        Database database = new Database(true, true);
    }
}