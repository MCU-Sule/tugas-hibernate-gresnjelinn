package com.example.utspt2072028graceag;

import com.example.utspt2072028graceag.dao.*;
import com.example.utspt2072028graceag.model.*;
import com.example.utspt2072028graceag.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FirstPageController {

    public ComboBox cmbGenre;
    public ListView lvUser;


    public Button btnAddUser;
    public Button btnDeleteUser;
    public TableView tableMovie;
    public TableColumn columnMovieTitle;
    public TableColumn columnMovieGenre;
    public TableColumn columnMovieDurasi;
    public TableView tableWatchList;
    public TableColumn columnWLMovieTitle;
    public TableColumn columnWLLastWatch;
    public TableColumn columnWLIsFavorite;
    public Button btnPrintReport;
    Stage addUserStage;

    ObservableList<User> userListTampilan;
    ObservableList<UserEntity> uList;
    ObservableList<Movie> movieListTampilan;
    ObservableList<MovieEntity> mList;
    ObservableList<WatchList> wlListTampilan;
    ObservableList<WatchListEntity> wlList;
    ObservableList<WatchListEntity> filteredWLList;
    ObservableList<MovieEntity> filteredMovieList;
    ObservableList<String> genreOption;

    public void initialize() {
        // Menampilkan data user di listview
        UserDao uDao = new UserDao();
        uList = FXCollections.observableArrayList(uDao.getData());
        lvUser.setItems(uList);

        // Menampilkan data movie di tabel
        MovieDao mDao = new MovieDao();
        mList = FXCollections.observableArrayList(mDao.getData());
        tableMovie.setItems(mList);
        columnMovieTitle.setCellValueFactory(new PropertyValueFactory<MovieEntity, String>("title"));
        columnMovieGenre.setCellValueFactory(new PropertyValueFactory<MovieEntity, String>("genre"));
        columnMovieDurasi.setCellValueFactory(new PropertyValueFactory<MovieEntity, Integer>("durasi"));

        // Menampilkan data watch list di tabel
        WatchListDao wlDao = new WatchListDao();
        wlList = FXCollections.observableArrayList(wlDao.getData());
        tableWatchList.setItems(wlList);
        columnWLMovieTitle.setCellValueFactory(new PropertyValueFactory<WatchListEntity, String>("movieByMovieIdMovie"));
        columnWLLastWatch.setCellValueFactory(new PropertyValueFactory<WatchListEntity, String>("lastWatchString"));
        columnWLIsFavorite.setCellValueFactory(new PropertyValueFactory<WatchListEntity, Boolean>("favorite"));

        // Memasukkan daftar genre ke combo genre
        genreOption = FXCollections.observableArrayList();
        genreOption.add("All");
        genreOption.add("Action");
        genreOption.add("Musical");
        genreOption.add("Comedy");
        genreOption.add("Animated");
        genreOption.add("Fantasy");
        genreOption.add("Drama");
        genreOption.add("Mystery");
        genreOption.add("Thriller");
        genreOption.add("Horror");
        cmbGenre.setItems(genreOption);
    }

    public void changeCombo(ActionEvent actionEvent) {
        String selectedGenre = String.valueOf(cmbGenre.getSelectionModel().getSelectedItem());
        String paramGenre = "%" + selectedGenre + "%";
        System.out.println(paramGenre);
        MovieDao mDao = new MovieDao();
        if (selectedGenre != "All") {
            filteredMovieList = FXCollections.observableArrayList(mDao.getFilteredData(paramGenre));
            tableMovie.setItems(filteredMovieList);
            columnMovieTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
            columnMovieGenre.setCellValueFactory(new PropertyValueFactory<Movie, String>("genre"));
            columnMovieDurasi.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("durasi"));
        }
    }

    public void AddUserAction(ActionEvent actionEvent) throws IOException {
        addUserStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UTSSecondPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 333, 168);

        SecondPageController sc = fxmlLoader.getController();

        addUserStage.setTitle("Add Data");
        addUserStage.setScene(scene);
        addUserStage.showAndWait();

        UserDao uDao = new UserDao();
        UserEntity u = new UserEntity();
        u.setUserName(sc.getUserNameDiinput());
        u.setUserPassword(sc.getUserPasswordDiinput());
        uDao.addData(u);
        uList = FXCollections.observableArrayList(uDao.getData());
        lvUser.setItems(uList);
    }

    public void printReport(ActionEvent actionEvent) {
        JasperPrint jp;
        Connection conn = MyConnection.getConnection();
        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("report/MovieReport.jasper", param, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("All Data Report");
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void DelUserAction(ActionEvent actionEvent) {
        UserEntity selectedUser;
        selectedUser = (UserEntity) lvUser.getSelectionModel().getSelectedItem();
        UserDao uDao = new UserDao();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete this data?");
        alert.setTitle("Delete Confirmation");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            uDao.delData(selectedUser);
            uList = FXCollections.observableArrayList(uDao.getData());
            lvUser.setItems(uList);
            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
            alertInfo.setContentText("Data berhasil di delete");
            alertInfo.setTitle("Message");
            alertInfo.showAndWait();
        }
    }

    public void refreshData(ObservableList<Movie> movieListTampilan) {
        tableMovie.setItems(movieListTampilan);
        columnMovieTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        columnMovieGenre.setCellValueFactory(new PropertyValueFactory<Movie, String>("genre"));
        columnMovieDurasi.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("durasi"));
    }

    public void filterUserWL(MouseEvent mouseEvent) {
        int selectedIndex = lvUser.getSelectionModel().getSelectedIndex();
        UserEntity selectedUser = uList.get(selectedIndex);
        WatchListDao wlDao = new WatchListDao();
        filteredWLList = FXCollections.observableArrayList(wlDao.getFilteredData(selectedUser.getIdUser()));
        tableWatchList.setItems(filteredWLList);
        columnWLMovieTitle.setCellValueFactory(new PropertyValueFactory<WatchList, String>("movie"));
        columnWLLastWatch.setCellValueFactory(new PropertyValueFactory<WatchList, String>("lastWatchString"));
        columnWLIsFavorite.setCellValueFactory(new PropertyValueFactory<WatchList, Boolean>("favorite"));
    }
}
