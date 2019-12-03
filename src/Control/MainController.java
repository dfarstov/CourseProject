package Control;
//java
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//javafx
import com.mysql.cj.xdevapi.Table;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.cell.PropertyValueFactory;
//
import Model.Ticket;
import Model.Seance;
import Model.Film;
import DBConnect.DatabaseHandler;
import javafx.stage.Stage;

public class MainController extends Controller{
    Vector<Seance> seances;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Button filmsButton;

    @FXML
    private Button nextMButton;

    @FXML
    private Button ticketsButton;

    @FXML
    private Button arhButton;

    @FXML
    private Button showButton;

    @FXML
    private Button orderButton;

    @FXML
    private AnchorPane filmsList;

    @FXML
    private TableView<Seance> filmTable;

    @FXML
    private ImageView poster;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label boxLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label scenLabel;

    @FXML
    private Label dirLabel;

    @FXML
    private ChoiceBox<String> row;

    @FXML
    private ChoiceBox<String> place;

    @FXML
    void initialize() {
        seances =  new Vector<Seance>();
        setButtonsEvents();
    }

    private void setButtonsEvents(){
        filmsButton.setOnAction(actionEvent -> {
            enableOrder();
            showFilms();
        });

        nextMButton.setOnAction(actionEvent -> {
            enableOrder();
            showNFilms();
        });

        ticketsButton.setOnAction(actionEvent -> {
            showTickets();
        });

        arhButton.setOnAction(actionEvent -> {
            disableOrder();
            showOFilms();
        });

        showButton.setOnAction(actionEvent -> {
            try {
                if(filmTable.getSelectionModel().getSelectedItem() != null)
                    showFilmInfo();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        orderButton.setOnAction(actionEvent -> {
            Seance seance = filmTable.getSelectionModel().getSelectedItem();
            try {
                orderTicket(seance, row.getValue(), place.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void showFilms(){
        try {
            showButton.setDisable(false);
            loadFilms();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createTable();
    }

    private void showNFilms(){
        try {
            showButton.setDisable(false);
            loadNFilms();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createTable();
    }

    private void showOFilms(){
        try {
            showButton.setDisable(false);
            loadOFilms();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createTable();
    }

    private void showTickets(){
        try {
            loadTickets();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showFilmInfo() throws SQLException, ClassNotFoundException {
        DatabaseHandler db = new DatabaseHandler();
        Film film = db.getFilm(filmTable.getSelectionModel().getSelectedItem().getFilmName());

        poster.setImage(new Image("Assets/" + film.getPoster() + ".jpg"));

        nameLabel.setText(film.getName());

        countryLabel.setText("Страна: " + film.getCountry());

        dateLabel.setText("Показ с: " + film.getDate());

        boxLabel.setText("Сборы: " + String.valueOf(film.getBoxOffice()) + "$");

        timeLabel.setText("Длительность: " + film.getTime());

        scenLabel.setText("Сценарист: " + film.getScreenWriter());

        dirLabel.setText("Продюсер: " + film.getProducer());

        descriptionLabel.setWrapText(true);
        descriptionLabel.setText(film.getDescription());

        loadPlacesInfo();
    }

    private void loadPlacesInfo() throws SQLException, ClassNotFoundException {
        row.getItems().clear();
        place.getItems().clear();

        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resSet = dbHandler.getTickets(filmTable.getSelectionModel().getSelectedItem().getID());

        Vector<String> rowVector = new Vector<String>();
        Vector<String> placeVector = new Vector<String>();

        for(int i = 0; i < 10; ++i)
            rowVector.add(String.valueOf(i+1));
        for(int i = 0; i < 20; ++i)
            placeVector.add(String.valueOf(i+1));

        while (resSet.next()){
            for(int i = 0; i < 10; ++i)
                if(resSet.getString(2).equals(rowVector.get(i)))
                    for(int j = 0; j < 20; ++j)
                        if(resSet.getString(3).equals(placeVector.get(j)))
                            placeVector.set(j," ");
        }

        placeVector.remove(" ");

        row.getItems().addAll(rowVector);
        place.getItems().addAll(placeVector);

        row.setValue(row.getItems().get(0));
        place.setValue(place.getItems().get(0));
    }

    private void enableOrder(){
        row.setDisable(false);
        place.setDisable(false);
        orderButton.setDisable(false);
    }

    private void disableOrder(){
        row.setDisable(true);
        place.setDisable(true);
        orderButton.setDisable(true);

        row.getItems().clear();
        place.getItems().clear();
    }

    private void loadFilms() throws SQLException, ClassNotFoundException {
        seances.clear();

        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resSet = dbHandler.getSeances();

        while(resSet.next())
            if(resSet.getString(4).equals(LocalDate.now().toString()))
                addNewSeance(resSet);
    }

    private void loadNFilms() throws SQLException, ClassNotFoundException {
        seances.clear();

        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resSet = dbHandler.getSeances();

        while(resSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate date =  LocalDate.parse(resSet.getString(4), formatter);

            if(date.isAfter(LocalDate.now()))
                addNewSeance(resSet);
        }
    }

    private void loadOFilms() throws SQLException, ClassNotFoundException {
        seances.clear();

        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resSet = dbHandler.getSeances();

        while(resSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate date =  LocalDate.parse(resSet.getString(4), formatter);
            if(date.isBefore(LocalDate.now()))
                addNewSeance(resSet);
        }
    }

    private void loadTickets() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String userID = dbHandler.getLoggedUserID();

        Vector<Ticket> tickets = dbHandler.getTicketsVector(userID);
        TableView<Ticket> ticketTable = new TableView<Ticket>();
        //
        TableColumn<Ticket,String> column0 = new TableColumn<>("№ Сеанса");
            column0.setCellValueFactory(new PropertyValueFactory<>("idSeance"));

        TableColumn<Ticket,String> column1 = new TableColumn<>("Название фильма");
            column1.setCellValueFactory(new PropertyValueFactory<>("filmName"));

        TableColumn<Ticket, String> column2 = new TableColumn<>("Время");
            column2.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Ticket, String> column3 = new TableColumn<>("№ ряда");
            column3.setCellValueFactory(new PropertyValueFactory<>("row"));

        TableColumn<Ticket, String> column4 = new TableColumn<>("№ места");
            column4.setCellValueFactory(new PropertyValueFactory<>("place"));

        TableColumn<Ticket, String> column5 = new TableColumn<>("Цена");
            column5.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Ticket, String> column6 = new TableColumn<>("ФИО");
            column6.setCellValueFactory(new PropertyValueFactory<>("userName"));

        ticketTable.getColumns().addAll(column0, column1, column2, column3, column4, column5, column6);

        for(Ticket ticket: tickets)
            ticketTable.getItems().add(ticket);
        //

        StackPane secondaryLayout = new StackPane();

        secondaryLayout.getChildren().add(ticketTable);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
            newWindow.setTitle("Билеты");
            newWindow.setScene(secondScene);
            newWindow.setWidth(600);

            newWindow.show();
    }

    private void orderTicket(Seance seance, String row, String place) throws SQLException, ClassNotFoundException{
        DatabaseHandler dbHandler = new DatabaseHandler();
            dbHandler.orderTicket(seance, row, place);
        loadPlacesInfo();
    }

    private void addNewSeance(ResultSet resSet) throws SQLException {
        seances.add(new Seance(resSet.getString(1),
                resSet.getString(2),
                resSet.getString(3),
                resSet.getString(4),
                resSet.getString(5)));
    }

    private void createTable(){
        filmTable.getColumns().clear();
        filmTable.getItems().clear();

        TableColumn<Seance,String> column0 = new TableColumn<>("№ Сеанса");
            column0.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Seance,String> column1 = new TableColumn<>("Название");
            column1.setCellValueFactory(new PropertyValueFactory<>("filmName"));

        TableColumn<Seance, String> column2 = new TableColumn<>("Зал");
            column2.setCellValueFactory(new PropertyValueFactory<>("hallName"));

        TableColumn<Seance, String> column3 = new TableColumn<>("Дата");
            column3.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Seance, String> column4 = new TableColumn<>("Время");
            column4.setCellValueFactory(new PropertyValueFactory<>("time"));


        filmTable.getColumns().addAll(column0, column1, column2, column3, column4);

        for(Seance seance: seances)
            filmTable.getItems().add(seance);
    }
}