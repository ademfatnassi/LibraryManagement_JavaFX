package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import DBConnection.connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.XOuvrage;

public class ListeOuvrageController implements Initializable {

	@FXML
	private TableColumn<XOuvrage, String> categorie;

	@FXML
	private TableColumn<XOuvrage, String> dateEdition;

	@FXML
	private TableColumn<XOuvrage, Integer> nbcopie;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private TableColumn<XOuvrage, String> titre;

	@FXML
	private TableColumn<XOuvrage, String> idouvrage;

	@FXML
	private TableView<XOuvrage> tableOuvrage;

	@FXML
	private TableColumn<XOuvrage, String> auteur;

	@FXML
	private TableColumn<XOuvrage, String> status;

	@FXML
	void RetourBtnClick(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
		home.setScene(new Scene(root));
		home.show();
		RetourBtn.getScene().getWindow().hide();

	}

	connection conOBJ = new connection();
	Connection con;
	private PreparedStatement pst;

	void RetreiveData() throws ClassNotFoundException, SQLException {

		con = conOBJ.getConnection();
		ObservableList<XOuvrage> data = FXCollections.observableArrayList();
		tableOuvrage.setItems(data);

		String str = "SELECT ouvrage.*,exemplaire.status,exemplaire.nbrcopie from ouvrage inner join exemplaire on ouvrage.idouvrage = exemplaire.idouvrage ";

		pst = con.prepareStatement(str);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			XOuvrage ouvrage = new XOuvrage(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getInt(7));
			data.add(ouvrage);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			RetreiveData();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		idouvrage.setCellValueFactory(new PropertyValueFactory<XOuvrage, String>("idouvrage"));
		titre.setCellValueFactory(new PropertyValueFactory<XOuvrage, String>("titre"));
		auteur.setCellValueFactory(new PropertyValueFactory<XOuvrage, String>("auteur"));
		categorie.setCellValueFactory(new PropertyValueFactory<XOuvrage, String>("categorie"));
		dateEdition.setCellValueFactory(new PropertyValueFactory<XOuvrage, String>("dateEdition"));
		status.setCellValueFactory(new PropertyValueFactory<XOuvrage, String>("status"));
		nbcopie.setCellValueFactory(new PropertyValueFactory<XOuvrage, Integer>("nbcopie"));
	}

}
