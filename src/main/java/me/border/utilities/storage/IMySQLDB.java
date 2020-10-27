package me.border.utilities.storage;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class IMySQLDB {

    protected Connection connection;

    private String host;
    private String database;
    private String username;
    private String password;
    private int port;

    public IMySQLDB(String host, String database, String username, String password, int port) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
        open();
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ping();
            }
        }, 7200000, 7200000);

    }

    // MUST BE RUN IN AN ASYNC TASK
    public ResultSet executeQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            ResultSet query = statement.executeQuery(sql);
            statement.close();
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // MUST BE RUN IN AN ASYNC TASK
    public ResultSet executeQuery(PreparedStatement ps){
        try {
            ResultSet query = ps.executeQuery();
            ps.close();
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ASYNC BY DEFAULT
    public void execute(String sql) {
        new Thread(() -> {
            try {
                Statement statement = connection.createStatement();
                statement.execute(sql);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // ASYNC BY DEFAULT
    public void execute(PreparedStatement ps) {
        new Thread(() -> {
            try {
                ps.execute();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // ASYNC BY DEFAULT
    public void executeUpdate(String sql) {
        new Thread(() -> {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // ASYNC BY DEFAULT
    public void executeUpdate(PreparedStatement ps) {
        new Thread(() -> {
            try {
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public PreparedStatement createPreparedStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ping() {
        executeQuery("/* ping */ SELECT 1;");
    }

    private void open() {
        try {
            synchronized (this) {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}