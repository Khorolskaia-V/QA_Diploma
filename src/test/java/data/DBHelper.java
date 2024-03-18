package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.sql.SQLException;

public class DBHelper {
    private static final String DB_URL = System.getProperty("datasource.url");

    @SneakyThrows
    private static Connection getConnection()
    {
        return DriverManager.getConnection(DB_URL, "app", "pass");
    }

    @SneakyThrows
    public static void clear() {
        QueryRunner runner = new QueryRunner();
        var connection = getConnection();
        runner.update(connection, "DELETE FROM credit_request_entity");
        runner.update(connection, "DELETE FROM order_entity");
        runner.update(connection, "DELETE FROM payment_entity");

    }

    @SneakyThrows
    public static String getLastPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        var sqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var connection = getConnection();
        return runner.query(connection, sqlStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getLastCreditPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        var sqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        var connection = getConnection();
        return runner.query(connection, sqlStatus, new ScalarHandler<>());
    }
}
