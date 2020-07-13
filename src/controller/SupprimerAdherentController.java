package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DBConnection.connection;
import alertMessage.AlertMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SupprimerAdherentController {

	@FXML
	private JFXButton deleteUserbtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXTextField CINInput;

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

	@FXML
	void deleteUser(ActionEvent event) {
		try {
			con = conOBJ.getConnection();
			String deleteAdherent = "DELETE FROM `librarydb`.`adherent` WHERE `cin_adherent` = " + CINInput.getText();
			pst = con.prepareStatement(deleteAdherent);
			pst.executeUpdate();
			pst.close();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Alert Message");
			alert.setContentText("L'Adherent `" + CINInput.getText() + "` a bien été supprimé .");
			alert.show();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong I can feel it... !");
		}
		CINInput.clear();
	}

}
