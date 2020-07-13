package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

public class EmpruntController {

	@FXML
	private JFXButton resetbtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXButton EmpruntBtn;

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
	void EmpruntBook(ActionEvent event) {
		// First thing, lezem id elli ktebto ena ikoun mawjoud 9bal kolchay
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
			String queryExemplaire = "SELECT * FROM librarydb.exemplaire where exemplaire.idouvrage=? and exemplaire.nbrcopie!=0";
			pst = con.prepareStatement(queryExemplaire);

			pst.setString(1, idOuvrage.getText());

			ResultSet rst = pst.executeQuery();
			int countOuvrage = 0;

			while (rst.next()) {
				countOuvrage = countOuvrage + 1;
			}
			rst.close();
			pst.close();

			// Verifier si un adherent a emprunter un exemplaire idOuvrage
			String queryEmprunt = "Select * from emprunt where (cin_adherent=? and idOuvrage=?) and Retourne='0'  and retard='1' ";
			pst = con.prepareStatement(queryEmprunt);

			pst.setString(1, cin.getText());
			pst.setString(2, idOuvrage.getText());

			ResultSet rs1 = pst.executeQuery();
			int isEmprunted = 0;

			while (rs1.next()) {
				isEmprunted = isEmprunted + 1;
			}
			rs1.close();
			pst.close();

			// Count emprunt row from table, c a d, pour compter le nombre de fois qu'un
			// adhrent emprunter un livre et non retourné (0:false,1:true)
			String queryCountEmprunt = "Select * from emprunt where cin_adherent=? and Retourne='0'";
			pst = con.prepareStatement(queryCountEmprunt);

			pst.setString(1, cin.getText());

			ResultSet rs2 = pst.executeQuery();
			int countEmprunt = 0;

			while (rs2.next()) {
				countEmprunt = countEmprunt + 1;
			}
			rs2.close();
			pst.close();

			if (countAdherent == 0) {
				// on determine est ce qu'il existe un adhérent ou pas, si non on alerte le
				// bibliothécaire
				AlertMessage alert = new AlertMessage();
				alert.setMessage("Adherent introuvable ... !");
			} else {
				if (countOuvrage == 0) {
					// on determine est ce qu'il existe un ouvrage ou pas, si non on alerte le
					// bibliothécaire
					AlertMessage alert = new AlertMessage();
					alert.setMessage("Ouvrage introuvable ... !");
				} else {
					if (isEmprunted == 1) {
						//
						AlertMessage alert = new AlertMessage();
						alert.setMessage("Le membre a déjà emprunté ce livre... !");
					} else if (countEmprunt >= 2) {
						// Le membre dépasse la limite supérieure des emprunts d'exemplaire
						AlertMessage alert = new AlertMessage();
						alert.setMessage("Le membre depasse le limite des emprunts d'exemplaire... !");
					} else {
						String queryUpdate1 = "UPDATE exemplaire SET exemplaire.nbrcopie=exemplaire.nbrcopie-1, exemplaire.status = CASE WHEN exemplaire.nbrcopie=0 THEN 'N'  ELSE exemplaire.status END WHERE idouvrage=?";
						pst = con.prepareStatement(queryUpdate1);
						pst.setString(1, idOuvrage.getText());
						pst.executeUpdate();
						pst.close();

						// Get the current date and time
						LocalDateTime currentTime = LocalDateTime.now();

						LocalDate date1 = currentTime.toLocalDate();
						LocalDate date2 = currentTime.toLocalDate().plus(2, ChronoUnit.DAYS);// +2 days
						System.out.println("Date courante : " + date1);

						String queryInsert = "INSERT INTO `librarydb`.`emprunt` (`cin_adherent`, `idOuvrage`, `Retourne`, `retard`, `Date_Emprunt`, `Date_Ret`) VALUES (?, ?, 0, 1, ?, ?)";

						pst = con.prepareStatement(queryInsert);
						pst.setString(1, cin.getText());
						pst.setString(2, idOuvrage.getText());
						pst.setString(3, date1.toString());
						pst.setString(4, date2.toString());

						pst.executeUpdate();

						pst.close();

						String queryUpdate2 = "UPDATE `librarydb`.`adherent` SET nbCopieEmpr = nbCopieEmpr+1 WHERE `cin_adherent` = ?;";
						pst = con.prepareStatement(queryUpdate2);
						pst.setString(1, cin.getText());
						pst.executeUpdate();
						pst.close();

						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setHeaderText("Alert Message");
						alert.setContentText("Exemplaire empruntée avec succes");
						alert.show();
					}

				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong ... .!");
			e.printStackTrace();
		}
		resetMethode(event);
	}

	@FXML
	void resetMethode(ActionEvent event) {
		cin.clear();
		idOuvrage.clear();
	}
}
