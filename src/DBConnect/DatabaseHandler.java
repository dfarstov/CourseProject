package DBConnect;

import Model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException, ClassNotFoundException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=Europe/Moscow&useSSL=false";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void regUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USER_LOGIN + ","
                + Const.USER_PASSWORD + "," + Const.USER_NAME + "," + Const.USER_LASTNAME
                + "," + Const.USER_PHONE_NUMBER + "," + Const.USER_EMAIL + ") Values(?,?,?,?,?,?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getName());
            prSt.setString(4, user.getLastname());
            prSt.setString(5, user.getPhone());
            prSt.setString(6, user.getEmail());

            prSt.executeUpdate();
    }

    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE
                + " WHERE " + Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());

        resSet = prSt.executeQuery();

        return resSet;
    }

    public void loginUser(User user) throws SQLException, ClassNotFoundException{
        String update = "UPDATE cinema.users SET logged = 1 WHERE (login = \"" + user.getLogin() + "\" );";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.executeUpdate();
    }

    public void logOutUser() throws SQLException, ClassNotFoundException{
        String select = "SELECT users.id_user FROM users WHERE users.logged = 1";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        ResultSet resSet = prSt.executeQuery();

        if(resSet.next()){
            String update = "UPDATE cinema.users SET logged = 0 WHERE (id_user = " + resSet.getString(1) + " );";

            prSt = getDbConnection().prepareStatement(update);
            prSt.executeUpdate();
        }
    }

    public String getLoggedUserID() throws SQLException, ClassNotFoundException{
        String loggedUserID = null;
        DatabaseHandler dbHandler = new DatabaseHandler();

        String select = "SELECT id_user FROM cinema.users WHERE(logged = 1);";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        ResultSet resSet = prSt.executeQuery();

        if(resSet.next())
            loggedUserID = resSet.getString(1);

        return loggedUserID;
    }

    public void orderTicket(Seance seance, String raw, String place) throws SQLException, ClassNotFoundException{
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resSet = dbHandler.getHall(seance.getHallName());
        String insert = "INSERT tickets(id_seance, id_place, id_price, id_user) VALUES(?,?,?,?);";

        String seanceID = seance.getID();

        String hallID = null;
        if(resSet.next())
            hallID = resSet.getString(1);

        String placeID = null;
        resSet = dbHandler.getPlace(hallID, raw, place);
        if(resSet.next())
            placeID = resSet.getString(1);

        Vector<Price> prices = dbHandler.getPrices();

        String priceID = null;

        for(Price price : prices)
            if(dateIsGreater(seance.getTime(), price.getTimeFrom()) && dateIsGreater(price.getTimeTo(), seance.getTime()))
                priceID = price.getID();

        String userID = dbHandler.getLoggedUserID();

        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, seanceID);
            prSt.setString(2, placeID);
            prSt.setString(3, priceID);
            prSt.setString(4, userID);

            prSt.executeUpdate();
    }

    public Vector<Price> getPrices() throws SQLException, ClassNotFoundException{
        Vector<Price> prices = new Vector<Price>();

        DatabaseHandler dbHandler = new DatabaseHandler();

        String select = "SELECT * FROM cinema.ticket_prices;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        ResultSet resSet = prSt.executeQuery();

        while (resSet.next())
            prices.add(new Price(
                    resSet.getString(1),
                    resSet.getString(2),
                    resSet.getString(3),
                    resSet.getString(4)));

        return prices;
    }

    public ResultSet getHall(String hallName) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT id_hall FROM halls WHERE(name = \"" + hallName + "\");";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();

        return resSet;
    }

    public Film getFilm(String name) throws SQLException, ClassNotFoundException {
        ResultSet resSet = getFilms();
        Film film = null;

        while(resSet.next())
            if(name.equals(resSet.getString(1)))
                film = new Film(resSet.getString(1),
                        resSet.getString(2),
                        resSet.getString(3),
                        Integer.parseInt(resSet.getString(4)),
                        resSet.getString(5),
                        resSet.getString(6)+" "+
                                resSet.getString(7),
                        resSet.getString(8)+" "+
                                resSet.getString(9),
                        resSet.getString(10),
                        resSet.getString(11)
                );

        return film;
    }

    public ResultSet getFilms() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select =
                    "SELECT \n" +
                    "    f.name,\n" +
                    "    c.name,\n" +
                    "    f.date,\n" +
                    "    f.box_office,\n" +
                    "    f.time,\n" +
                    "    s1.name,\n" +
                    "    s1.lastname,\n" +
                    "    s2.name,\n" +
                    "    s2.lastname,\n" +
                    "    f.poster,\n" +
                    "    f.description\n" +
                    "FROM\n" +
                    "    films f\n" +
                    "        JOIN\n" +
                    "    countries c ON f.id_country = c.id_country\n" +
                    "        JOIN\n" +
                    "    screenwriters_directors s1 ON f.id_screen_writer = s1.id_screenwriter_director\n" +
                    "\t\tJOIN\n" +
                    "    screenwriters_directors s2 ON f.id_producer = s2.id_screenwriter_director;";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();

        return resSet;
    }

    public ResultSet getSeances() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select =
                "SELECT \n" +
                        "    s.id_seance,\n" +
                        "    f.name,\n" +
                        "    h.name,\n" +
                        "    s.date,\n" +
                        "    s.time\n" +
                        "FROM\n" +
                        "    seances s\n" +
                        "        JOIN\n" +
                        "    films f ON s.id_film = f.id_film\n" +
                        "        JOIN\n" +
                        "\thalls h ON s.id_hall = h.id_hall;";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();

        return resSet;
    }

    public ResultSet getTickets(String ID) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select =
                "SELECT \n" +
                        "\t  s.id_seance,\n" +
                        "    f.name, \n" +
                        "    s.time,\n" +
                        "    p1.row,\n" +
                        "    p2.place_number,\n" +
                        "    pr.price,\n" +
                        "    u1.name,\n" +
                        "    u2.lastname\n" +
                        "FROM\n" +
                        "    tickets t\n" +
                        "        JOIN\n" +
                        "    seances s ON t.id_seance = s.id_seance\n" +
                        "\t\tJOIN\n" +
                        "\tfilms f ON s.id_film = f.id_film\n" +
                        "\t\tJOIN\n" +
                        "    places p1 ON t.id_place = p1.id_place\n" +
                        "\t\tJOIN\n" +
                        "    places p2 ON t.id_place = p2.id_place\n" +
                        "\t\tJOIN\n" +
                        "    ticket_prices pr ON t.id_price = pr.id_price\n" +
                        "\t\tJOIN\n" +
                        "    users u1 ON t.id_user = u1.id_user\n" +
                        "\t\tJOIN\n" +
                        "    users u2 ON t.id_user = u2.id_user " +
                        "WHERE s.id_seance = " + ID;

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        return resSet;
    }

    public Vector<Ticket> getTicketsVector(String userID) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select =
                "SELECT \n" +
                        "\t  s.id_seance,\n" +
                        "    f.name, \n" +
                        "    s.time,\n" +
                        "    p1.row,\n" +
                        "    p2.place_number,\n" +
                        "    pr.price,\n" +
                        "    u1.name,\n" +
                        "    u2.lastname\n" +
                        "FROM\n" +
                        "    tickets t\n" +
                        "        JOIN\n" +
                        "    seances s ON t.id_seance = s.id_seance\n" +
                        "\t\tJOIN\n" +
                        "\tfilms f ON s.id_film = f.id_film\n" +
                        "\t\tJOIN\n" +
                        "    places p1 ON t.id_place = p1.id_place\n" +
                        "\t\tJOIN\n" +
                        "    places p2 ON t.id_place = p2.id_place\n" +
                        "\t\tJOIN\n" +
                        "    ticket_prices pr ON t.id_price = pr.id_price\n" +
                        "\t\tJOIN\n" +
                        "    users u1 ON t.id_user = u1.id_user\n" +
                        "\t\tJOIN\n" +
                        "    users u2 ON t.id_user = u2.id_user " +
                        "WHERE u1.id_user = " + userID;

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();

        Vector<Ticket> tickets = new Vector<Ticket>();

        while(resSet.next()){
            tickets.add(new Ticket(
                    resSet.getString(1),
                    resSet.getString(2),
                    resSet.getString(3),
                    resSet.getString(4),
                    resSet.getString(5),
                    resSet.getString(6),
                    resSet.getString(7) + " " + resSet.getString(8)));
        }

        return tickets;
    }

    public ResultSet getPlace(String hallID, String raw, String place) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select =
                "SELECT id_place FROM cinema.places WHERE(places.id_hall = ? AND places.row = ? AND places.place_number = ?);";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, hallID);
            prSt.setString(2, raw);
            prSt.setString(3, place);

        resSet = prSt.executeQuery();

        return resSet;
    }

    private boolean dateIsGreater(String date1, String date2){
        String[] sub1 = date1.split(":", 3);
        String[] sub2 = date2.split(":", 3);

        int hours1 = Integer.parseInt(sub1[0]);
        int hours2 = Integer.parseInt(sub2[0]);
        int mins1 = Integer.parseInt(sub1[1]);
        int mins2 = Integer.parseInt(sub2[1]);

        if(hours1 > hours2)
            return true;
        else if(hours1 == hours2)
            if(mins1 > mins2)
                return true;
        return false;
    }
}
