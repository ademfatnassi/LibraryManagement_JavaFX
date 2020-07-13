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
import model.XOuvrageEmprunt;

public class ListeOuvrageEmpruntController implements Initializable {

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private TableColumn<XOuvrageEmprunt, String> titre;

	@FXML
	private TableView<XOuvrageEmprunt> tableOuvrageEmprunt;

	@FXML
	private TableColumn<XOuvrageEmprunt, String> cin;

	@FXML
	private TableColumn<XOuvrageEmprunt, String> idOuvrage;

	@FXML
	private TableColumn<XOuvrageEmprunt, String> auteur;

	connection conOBJ = new connection();
	Connection con;
	private PreparedStatement pst;

	@FXML
	void RetourBtnClick(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
		home.setScene(new Scene(root));
		home.show();
		RetourBtn.getScene().getWindow().hide();

	}

	void RetreiveData() throws ClassNotFoundException, SQLException {

		con = conOBJ.getConnection();
		ObservableList<XOuvrageEmprunt> data = FXCollections.observableArrayList();
		tableOuvrageEmprunt.setItems(data);

		String str = "SELECT ouvrage.idOuvrage, emprunt.cin_adherent,ouvrage.titre,ouvrage.auteur from ouvrage inner join emprunt on ouvrage.idOuvrage = emprunt.idOuvrage where emprunt.Retourne=0";
		pst = con.prepareStatement(str);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			XOuvrageEmprunt ouvrageEmprunt = new XOuvrageEmprunt(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getString(4));
			data.add(ouvrageEmprunt);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			RetreiveData();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		idOuvrage.setCellValueFactory(new PropertyValueFactory<XOuvrageEmprunt, String>("idOuvrage"));
		cin.setCellValueFactory(new PropertyValueFactory<XOuvrageEmprunt, String>("cin"));
		titre.setCellValueFactory(new PropertyValueFactory<XOuvrageEmprunt, String>("titreOuvrage"));
		auteur.setCellValueFactory(new PropertyValueFactory<XOuvrageEmprunt, String>("auteurOuvrage"));

	}

}
