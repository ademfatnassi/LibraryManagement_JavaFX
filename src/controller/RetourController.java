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

public class RetourController {

	@FXML
	private JFXButton retourBtn;

	@FXML
	private JFXButton resetbtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXTextField cin;

	@FXML
	private JFXTextField idOuvrage;

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

	@FXML
	void retourBook(ActionEvent event) {

		try {
			con = conOBJ.getConnection();

			// Verification d'existance d'adherent

			String queryAdherent = "SELECT * FROM librarydb.adherent where cin_adherent=?";
			pst = con.prepareStatement(queryAdherent);

			pst.setString(1, cin.getText());

			ResultSet rs = pst.executeQuery();
			int countAdherent = 0;

			while (rs.next()) {
				countAdherent = countAdherent + 1;
			}
			rs.close();
			pst.close();

			// Verification d'existance d'exemplaire
			String queryExemplaire = "SELECT * FROM librarydb.exemplaire where exemplaire.idouvrage=?";
			pst = con.prepareStatement(queryExemplaire);

			pst.setString(1, idOuvrage.getText());

			ResultSet rst = pst.executeQuery();
			int countExemplaire = 0;

			while (rst.next()) {
				countExemplaire = countExemplaire + 1;
			}
			rst.close();
			pst.close();

			// Verifier si un adherent a emprunter un exemplaire idOuvrage
			String queryNonRerourne = "SELECT * FROM librarydb.emprunt where cin_adherent=? and idOuvrage=? and Retourne='0';";
			pst = con.prepareStatement(queryNonRerourne);

			pst.setString(1, cin.getText());
			pst.setString(2, idOuvrage.getText());

			ResultSet rs1 = pst.executeQuery();
			int isRetourne = 0;

			while (rs1.next()) {
				isRetourne = isRetourne + 1;
			}
			rs1.close();
			pst.close();

			if (countAdherent == 0) {
				// on determine est ce qu'il existe un adhérent ou pas, si non on alerte le
				// bibliothécaire
				AlertMessage alert = new AlertMessage();
				alert.setMessage("Adherent introuvable ... !");
			} else {
				if (countExemplaire == 0) {
					// on determine est ce qu'il existe un ouvrage ou pas, si non on alerte le
					// bibliothécaire
					AlertMessage alert = new AlertMessage();
					alert.setMessage("Ouvrage introuvable ... !");
				} else {
					if (isRetourne == 0) {
						AlertMessage alert = new AlertMessage();
						alert.setMessage("Il n'y a pas de transaction d'emprunt OU Cette copie a été retournée  ... !");
					} else {
						String queryUpdate1 = "UPDATE exemplaire SET exemplaire.nbrcopie=exemplaire.nbrcopie+1, exemplaire.status = CASE WHEN exemplaire.nbrcopie>0 THEN 'D'  ELSE exemplaire.status END WHERE idouvrage=?";
						pst = con.prepareStatement(queryUpdate1);
						pst.setString(1, idOuvrage.getText());
						pst.executeUpdate();
						pst.close();

						String queryUpdate2 = "UPDATE `librarydb`.`adherent` SET nbCopieEmpr = nbCopieEmpr-1 WHERE `cin_adherent` = ?;";
						pst = con.prepareStatement(queryUpdate2);
						pst.setString(1, cin.getText());
						pst.executeUpdate();
						pst.close();

						String queryUpdate3 = "UPDATE `librarydb`.`emprunt` SET `Retourne` = '1' WHERE (`cin_adherent` = ?) and (`idOuvrage` = ?);";
						pst = con.prepareStatement(queryUpdate3);
						pst.setString(1, cin.getText());
						pst.setString(2, idOuvrage.getText());
						pst.executeUpdate();
						pst.close();

						String queryDelete = "Delete FRom `librarydb`.`emprunt` where cin_adherent=? and idOuvrage=?";
						pst = con.prepareStatement(queryDelete);
						pst.setString(1, cin.getText());
						pst.setString(2, idOuvrage.getText());
						pst.executeUpdate();
						pst.close();

						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setHeaderText("Alert Message");
						alert.setContentText("Exemplaire Retournée avec succes");
						alert.show();
					}
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong ... !");
		}

	}

	@FXML
	void resetMethode(ActionEvent event) {

	}

}
