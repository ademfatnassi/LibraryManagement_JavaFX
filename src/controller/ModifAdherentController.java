package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import DBConnection.connection;
import alertMessage.AlertMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Adherent;

public class ModifAdherentController implements Initializable {
	@FXML
	private JFXRadioButton RadioBTN2;

	@FXML
	private JFXRadioButton RadioBTN1;

	@FXML
	private JFXTextField inputDepartEns;

	@FXML
	private JFXButton cherchebtn;

	@FXML
	private JFXTextField CINInput1;// Champ de CIN à rechercher

	@FXML
	private JFXTextField CINInput;

	@FXML
	private JFXButton modifbtn;

	@FXML
	private JFXButton resetbtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXTextField inputProfesEn;

	@FXML
	private JFXTextField NomInput;

	@FXML
	private JFXTextField PrenomInput;

	@FXML
	private JFXTextField inputFiliereEtud;

	@FXML
	private JFXTextField EmailInput;

	@FXML
	private JFXTextField PhoneInput;

	connection conOBJ = new connection();
	Connection con;
	private PreparedStatement pst, ps;

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
	void EnseignentClick(ActionEvent event) {
		inputProfesEn.setVisible(true);
		inputDepartEns.setVisible(true);

		inputFiliereEtud.setVisible(false);
		inputFiliereEtud.setStyle("-fx-opacity: 0");
		inputProfesEn.setStyle("-fx-opacity: 1");
		inputDepartEns.setStyle("-fx-opacity: 1");
	}

	@FXML
	void EtudiantClick(ActionEvent event) {
		inputFiliereEtud.setStyle("-fx-opacity: 1");
		inputProfesEn.setStyle("-fx-opacity: 0");
		inputDepartEns.setStyle("-fx-opacity: 0");
		inputProfesEn.setVisible(false);
		inputDepartEns.setVisible(false);

		inputFiliereEtud.setVisible(true);
	}

	@FXML
	void resetForm(ActionEvent event) {
		CINInput.clear();
		CINInput1.clear();
		NomInput.clear();
		EmailInput.clear();
		PrenomInput.clear();
		PhoneInput.clear();
		inputFiliereEtud.clear();
		inputProfesEn.clear();
		inputDepartEns.clear();
	}

	@FXML
	void ModifUser(ActionEvent event) {

		/*
		 * ALERT! MODIFICATION/CHANGEMENT IMPORTANT! On n'est pas besoin de modifier le
		 * type d'adherent de letudiant a l'enseignant , et vis versa !MAGIC! Si
		 * l"adhenrt est etudiant on l'affiche seulement l'input Filiere Si l"adhrent
		 * est enseignant on l'affiche Department et Profession
		 * 
		 */

		try {
			con = conOBJ.getConnection();
			String update1 = "UPDATE `librarydb`.`adherent` SET `cin_adherent`=?, `nom_adherent`=?, `prenom_adherent`=?, `phone_adherent`=?, `email_adherent`=?, `nbCopieEmpr`=? WHERE `cin_adherent`="
					+ CINInput1.getText();

			ps = con.prepareStatement(update1);
			Adherent adherent = new Adherent(CINInput1.getText(), NomInput.getText(), PrenomInput.getText(),
					PhoneInput.getText(), EmailInput.getText(), 0);

			ps.setString(1, adherent.getCin());
			ps.setString(2, adherent.getNom());
			ps.setString(3, adherent.getPrenom());
			ps.setString(4, adherent.getPhone());
			ps.setString(5, adherent.getEmail());
			ps.setInt(6, adherent.getNbCopieEmp());
			ps.executeUpdate();
			ps.close();

			System.out.println(
					inputDepartEns.getText() + ":" + inputFiliereEtud.getText() + ":" + inputProfesEn.getText());
			if (!inputFiliereEtud.getText().equals("")) {
				String updateEtud = "UPDATE `librarydb`.`etudiant` SET `cin_adherent`=?, `filiere`=? WHERE `cin_adherent`="
						+ CINInput1.getText();
				ps = con.prepareStatement(updateEtud);
				ps.setString(1, adherent.getCin());
				ps.setString(2, inputFiliereEtud.getText());
				ps.executeUpdate();
				ps.close();
			} else {
				String updateEns = "UPDATE `librarydb`.`enseignant` SET `cin_adherent`=?, `departement`=?, `profession`=? WHERE `cin_adherent`="
						+ CINInput1.getText();
				ps = con.prepareStatement(updateEns);
				ps.setString(1, adherent.getCin());
				ps.setString(2, inputDepartEns.getText());
				ps.setString(3, inputProfesEn.getText());
				ps.executeUpdate();
				ps.close();

			}
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Alert Message");
			alert.setContentText("L'Adherent `" + CINInput.getText() + "` a bien été modifié .");
			alert.show();
		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong I can feel it... !");
		}

		resetForm(event);

	}

	@FXML
	void chercheUser(ActionEvent event) {

		try {
			con = conOBJ.getConnection();

			String query1 = "SELECT * from adherent inner join etudiant on etudiant.cin_adherent = adherent.cin_adherent where adherent.cin_adherent= ?";
			pst = con.prepareStatement(query1);

			pst.setString(1, CINInput1.getText());

			ResultSet rs = pst.executeQuery();
			int countetud = 0;

			while (rs.next()) {
				countetud = countetud + 1;
				CINInput.setText(rs.getString(1));
				NomInput.setText(rs.getString(2));
				PrenomInput.setText(rs.getString(3));
				PhoneInput.setText(rs.getString(4));
				EmailInput.setText(rs.getString(5));

				inputFiliereEtud.setStyle("-fx-opacity: 1");
				inputProfesEn.setStyle("-fx-opacity: 0");
				inputDepartEns.setStyle("-fx-opacity: 0");
				inputFiliereEtud.setVisible(true);
				inputFiliereEtud.setText(rs.getString(8));
			}

			String query2 = "SELECT * from adherent inner join enseignant on enseignant.cin_adherent = adherent.cin_adherent where adherent.cin_adherent= ?";
			pst = con.prepareStatement(query2);

			pst.setString(1, CINInput1.getText());

			rs = pst.executeQuery();
			int countEnsei = 0;

			while (rs.next()) {
				countEnsei = countEnsei + 1;
				CINInput.setText(rs.getString(1));
				NomInput.setText(rs.getString(2));
				PrenomInput.setText(rs.getString(3));
				PhoneInput.setText(rs.getString(4));
				EmailInput.setText(rs.getString(5));

				inputProfesEn.setVisible(true);
				inputDepartEns.setVisible(true);
				inputProfesEn.setStyle("-fx-opacity: 1");
				inputDepartEns.setStyle("-fx-opacity: 1");
				inputFiliereEtud.setStyle("-fx-opacity: 0");

				inputProfesEn.setText(rs.getString(8));
				inputDepartEns.setText(rs.getString(9));
			}

			if (countEnsei == 0 && countetud == 0) {
				AlertMessage alert = new AlertMessage();
				alert.setMessage("Adherent n\'existe pas");
			}

		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong ... !");
		}

		/*
		 * 
		 */

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
