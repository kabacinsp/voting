package pl.kabacinsp.voting;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionFactory {
    Connection createConnection() throws SQLException {
        String jdbcURL = "jdbc:h2:mem:testdb";
        String username = "sa";
        String password = "password";
        return DriverManager.getConnection(jdbcURL, username, password);
    }
}
