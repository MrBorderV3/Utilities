package me.border.utilities.database.sql;

import me.border.utilities.scheduler.async.AsyncTask;
import me.border.utilities.scheduler.async.AsyncTaskBuilder;
import me.border.utilities.terminable.Terminable;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public abstract class IMySQLDB implements Terminable {

    protected Connection connection;

    private final String host;
    private final String database;
    private final String username;
    private final String password;
    private final int port;

    public IMySQLDB(String host, String database, String username, String password, int port) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
        open();
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        AsyncTaskBuilder.builder()
                .task(new AsyncTask() {
                    @Override
                    public void run() {
                        if (isClosed()) {
                            this.closeSilently();
                            return;
                        }
                        ping();
                    }
                })
                .after(7200000, TimeUnit.MILLISECONDS)
                .every(7200000, TimeUnit.MILLISECONDS)
                .build();
    }

    // MUST BE RUN IN AN ASYNC TASK
    public ResultSet executeQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // MUST BE RUN IN AN ASYNC TASK
    public ResultSet executeQuery(PreparedStatement ps){
        try {
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ASYNC BY DEFAULT
    public void execute(String sql) {
        AsyncTaskBuilder.builder()
                .after(0, TimeUnit.MILLISECONDS)
                .task(new AsyncTask() {
                    @Override
                    public void run() {
                        try {
                            Statement statement = connection.createStatement();
                            statement.execute(sql);
                            statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .build();
    }

    // ASYNC BY DEFAULT
    public void execute(PreparedStatement ps) {
        AsyncTaskBuilder.builder()
                .after(0, TimeUnit.MILLISECONDS)
                .task(new AsyncTask() {
                    @Override
                    public void run() {
                        try {
                            ps.execute();
                            ps.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .build();
    }

    // ASYNC BY DEFAULT
    public void executeUpdate(String sql) {
        AsyncTaskBuilder.builder()
                .after(0, TimeUnit.MILLISECONDS)
                .task(new AsyncTask() {
                    @Override
                    public void run() {
                        try {
                            Statement statement = connection.createStatement();
                            statement.executeUpdate(sql);
                            statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .build();
    }

    // ASYNC BY DEFAULT
    public void executeUpdate(PreparedStatement ps) {
        AsyncTaskBuilder.builder()
                .after(0, TimeUnit.MILLISECONDS)
                .task(new AsyncTask() {
                    @Override
                    public void run() {
                        try {
                            ps.executeUpdate();
                            ps.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .build();
    }

    public PreparedStatement createPreparedStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        validate();
        try {
            if (connection != null && !isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isClosed() {
        if (connection == null)
            return true;
        try {
            return connection.isClosed();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
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
            close();
        }
    }

}