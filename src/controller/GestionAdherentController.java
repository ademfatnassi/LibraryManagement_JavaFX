package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionAdherentController implements Initializable {

	@FXML
	private JFXButton deleteUserbtn;

	@FXML
	private JFXButton listeUserBtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXButton ModifUserBtn;

	@FXML
	private JFXButton ajoutUserBtn;

	@FXML
	private JFXTextField CINInput;

	@FXML
	private AnchorPane anPanGA;

	@FXML
	private Pane pane;

	connection conOBJ = new connection();
	Connection con;
	private PreparedStatement pst;

	@FXML
	void RetourBtnClick(ActionEvent event) throws IOException {

		RetourBtn.getScene().getWindow().hide();

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
		home.setScene(new Scene(root));
		home.show();
	}

	@FXML
	void ajoutUser(ActionEvent event) throws IOException {

		ajoutUserBtn.getScene().getWindow().hide();

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/AjoutAdherentView.fxml"));
		home.setScene(new Scene(root));
		home.show();

	}

	@FXML
	void ModifUser(ActionEvent event) throws IOException {
		ModifUserBtn.getScene().getWindow().hide();
		Stage home = new Stage();
		home.setMaximized(true);
		Parent root;
		root = FXMLLoader.load(getClass().getResource("/view/ModifAdherentView.fxml"));
		home.setScene(new Scene(root));
		home.show();
	}

	@FXML
	void afficheUser(ActionEvent event) {

	}

	@FXML
	void deleteUser(ActionEvent event) {
		try {
			con = conOBJ.getConnection();
			String deleteAdherent = "DELETE FROM `librarydb`.`adherent` WHERE `cin_adherent` = " + CINInput.getText();
			pst = con.prepareStatement(deleteAdherent);
			pst.executeUpdate();
			pst.close();

			if (chercheUser() == 1) {
				String deleteEnseignant = "DELETE FROM `librarydb`.`enseignant` WHERE `cin_adherent` = "
						+ CINInput.getText();
				pst = con.prepareStatement(deleteEnseignant);
				pst.executeUpdate();
				pst.close();
			} else {
				String deleteEtudiant = "DELETE FROM `librarydb`.`etudiant` WHERE `cin_adherent` = "
						+ CINInput.getText();
				pst = con.prepareStatement(deleteEtudiant);
				pst.executeUpdate();
				pst.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong I can feel it... !");
		}

	}

	int chercheUser() {

		int countetud = 0, countEnsei = 0;
		try {

			con = conOBJ.getConnection();

			String query1 = "SELECT * from adherent inner join etudiant on etudiant.cin_adherent = adherent.cin_adherent where adherent.cin_adherent= ?";
			pst = con.prepareStatement(query1);

			pst.setString(1, CINInput.getText());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				countetud = countetud + 1;
			}

			String query2 = "SELECT * from adherent inner join enseignant on enseignant.cin_adherent = adherent.cin_adherent where adherent.cin_adherent= ?";
			pst = con.prepareStatement(query2);

			pst.setString(1, CINInput.getText());

			rs = pst.executeQuery();

			while (rs.next()) {
				countEnsei = countEnsei + 1;
			}

			if (countEnsei == 0 && countetud == 0) {
				AlertMessage alert = new AlertMessage();
				alert.setMessage("Adherent n\'existe pas");
			}
			rs.close();
			pst.close();

		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong I can feel it... !");
		}

		if (countEnsei != 0) {
			return 1;// S'il est un enseignant
		} else
			return 0;// S'il est un etudiant

		/*
		 * 
		 */

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
