package Common;

import java.sql.Connection;
import java.sql.DriverManager;

public class Voting_DB {
	static Voting_DB db;
	Connection connection = null;

    public Voting_DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Voting";
           
            this.connection = DriverManager.getConnection(url, "ragavi-zstk352", "Karagavi3/");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public static Voting_DB getDB() {
        if (db == null) {
        	db = new Voting_DB();
        }
        return db;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
