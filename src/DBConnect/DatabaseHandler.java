package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public  Connection getDbConnection() throws SQLException, ClassNotFoundException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=Europe/Moscow&useSSL=false";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void regUser(String login, String password,
                           String name, String lastname,
                           String phone, String email) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USER_LOGIN + ","
                + Const.USER_PASSWORD + "," + Const.USER_NAME + "," + Const.USER_LASTNAME
                + "," + Const.USER_PHONE_NUMBER + "," + Const.USER_EMAIL + ") Values(?,?,?,?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.setString(3, name);
            prSt.setString(4, lastname);
            prSt.setString(5, phone);
            prSt.setString(6, email);

            prSt.executeUpdate();
    }
}
