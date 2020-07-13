package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import DBConnection.connection;
import alertMessage.AlertMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomePageController implements Initializable {

	@FXML
	private JFXButton retourBTN;

	@FXML
	private JFXButton suppBookBtn;

	@FXML
	private GridPane gridPaneHP;

	@FXML
	private JFXButton afficheUserBtn;

	@FXML
	private JFXButton modifUserBtn;

	@FXML
	private JFXButton afficheBookBtn;

	@FXML
	private JFXButton modifBookBtn;

	@FXML
	private JFXButton listUserEmpruntBtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private JFXButton ajoutBookBtn;

	@FXML
	private JFXButton ajoutUserBtn;

	@FXML
	private JFXButton empruntBTN;

	@FXML
	private JFXButton suppUserBtn;

	@FXML
	private JFXButton listBookEmpruntBtn;

	@FXML
	private AnchorPane anPanHP;

	@FXML
	private Pane PaneHP;

	@FXML
	void ajoutUser(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/AjoutAdherentView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		ajoutUserBtn.getScene().getWindow().hide();
	}

	@FXML
	void suppUser(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/SupprimerAdherentView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		suppUserBtn.getScene().getWindow().hide();
	}

	@FXML
	void modifUser(ActionEvent event) {

		try {
			Stage home = new Stage();
			home.setMaximized(true);
			Parent root;
			root = FXMLLoader.load(getClass().getResource("/view/ModifAdherentView.fxml"));
			home.setScene(new Scene(root));
			home.show();
			modifUserBtn.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void afficheUser(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListeAdherent.fxml"));
		home.setScene(new Scene(root));
		home.show();
		afficheUserBtn.getScene().getWindow().hide();
	}

	@FXML
	void ajoutBook(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/AjoutOuvrageView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		ajoutBookBtn.getScene().getWindow().hide();
	}

	@FXML
	void suppBook(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/SupprimerOuvrageView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		suppBookBtn.getScene().getWindow().hide();
	}

	@FXML
	void modifBook(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/ModifOuvrageView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		ajoutBookBtn.getScene().getWindow().hide();
	}

	@FXML
	void afficheBook(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListeOuvrageView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		afficheBookBtn.getScene().getWindow().hide();
	}

	@FXML
	void empruntBook(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/EmpruntView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		empruntBTN.getScene().getWindow().hide();
	}

	@FXML
	void retourBook(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/RetourView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		retourBTN.getScene().getWindow().hide();
	}

	@FXML
	void listUserEmprunt(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListeAdherentEmpruntView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		listUserEmpruntBtn.getScene().getWindow().hide();
	}

	@FXML
	void listBookEmprunt(ActionEvent event) throws IOException {

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListeOuvrageEmpruntView.fxml"));
		home.setScene(new Scene(root));
		home.show();
		listBookEmpruntBtn.getScene().getWindow().hide();
	}

	connection conOBJ = new connection();
	Connection con;
	private PreparedStatement pst;

	void ModifEmprunt() {
		try {
			con = conOBJ.getConnection();
			String query = "SET SQL_SAFE_UPDATES=0; update librarydb.emprunt set retard = CASE WHEN DATEDIFF(CURRENT_DATE(),Date_Ret)>0 THEN 1 ELSE retard END where retard=0";
			pst = con.prepareStatement(query);
			pst.executeUpdate();
			pst.close();

		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong I can feel it... !");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
