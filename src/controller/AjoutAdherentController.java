package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Enseignant;
import model.Etudiant;
import DBConnection.connection;
import alertMessage.AlertMessage;;

public class AjoutAdherentController implements Initializable {
	@FXML
	private JFXButton ajoutbtn;

	@FXML
	private JFXRadioButton RadioBTN2;// Enseignant

	@FXML
	private JFXRadioButton RadioBTN1;// Etudiant

	@FXML
	private JFXTextField inputDepartEns;

	@FXML
	private JFXTextField CINInput;

	@FXML
	private JFXTextField EmailInput;

	@FXML
	private DatePicker DateNaissanceInput;

	@FXML
	private JFXButton resetbtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXTextField inputProfesEn;

	@FXML
	private JFXTextField NomInput;

	@FXML
	private JFXTextField PrenomInput;

	@FXML
	private JFXTextField inputFiliereEtud;

	@FXML
	private JFXTextField PhoneInput;

	connection conOBJ = new connection();
	Connection con;
	private PreparedStatement ps;

	@FXML
	void ajoutUser(ActionEvent event) {
		try {
			con = conOBJ.getConnection();
			String insert1 = "INSERT INTO `librarydb`.`adherent` (`cin_adherent`, `nom_adherent`, `prenom_adherent`, `phone_adherent`, `email_adherent`, `nbCopieEmpr`) VALUES (?, ?, ?, ?, ?, ?)";

			ps = con.prepareStatement(insert1);

			if (RadioBTN2.isSelected()) {

				DateNaissanceInput.getValue();

				/* j'ajoute birth date ou non */

				Enseignant enseignant = new Enseignant(CINInput.getText(), NomInput.getText(), PrenomInput.getText(),
						PhoneInput.getText(), EmailInput.getText(), 0, inputProfesEn.getText(),
						inputDepartEns.getText());

				String insert2 = "INSERT INTO `librarydb`.`enseignant` (`cin_adherent`, `departement`, `profession`) VALUES (?, ?, ?)";

				ps = con.prepareStatement(insert1);

				ps.setString(1, enseignant.getCin());
				ps.setString(2, enseignant.getNom());
				ps.setString(3, enseignant.getPrenom());
				ps.setString(4, enseignant.getPhone());
				ps.setString(5, enseignant.getEmail());
				ps.setInt(6, enseignant.getNbCopieEmp());

				ps.executeUpdate();

				System.out.println("Data inserted adherent");

				ps = con.prepareStatement(insert2);// enseig

				ps.setString(1, enseignant.getCin());
				ps.setString(2, enseignant.getDepartement());
				ps.setString(3, enseignant.getProfession());

				ps.executeUpdate();

				System.out.println("Data inserted ensei");

			}

			if (RadioBTN1.isSelected()) {

				Etudiant etudiant = new Etudiant(CINInput.getText(), NomInput.getText(), PrenomInput.getText(),
						PhoneInput.getText(), EmailInput.getText(), 0, inputFiliereEtud.getText());

				String insert3 = "INSERT INTO `librarydb`.`etudiant` (`cin_adherent`, `filiere`) VALUES (?, ?)";

				ps = con.prepareStatement(insert1);

				ps.setString(1, etudiant.getCin());
				ps.setString(2, etudiant.getNom());
				ps.setString(3, etudiant.getPrenom());
				ps.setString(4, etudiant.getPhone());
				ps.setString(5, etudiant.getEmail());
				ps.setInt(6, etudiant.getNbCopieEmp());

				ps.executeUpdate();

				System.out.println("Data inserted adherent");

				ps = con.prepareStatement(insert3);// etud

				ps.setString(1, etudiant.getCin());
				ps.setString(2, etudiant.getFiliere());

				ps.executeUpdate();

				System.out.println("Data inserted etud");

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Alert Message");
				alert.setContentText("L'Adherent `" + CINInput.getText() + "` a bien été ajouté .");
				alert.show();
			}
		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong I can feel it... !");
		}

		resetForm(event);
	}

	@FXML
	void resetForm(ActionEvent event) {
		CINInput.clear();
		NomInput.clear();
		EmailInput.clear();
		PrenomInput.clear();
		PhoneInput.clear();
		inputFiliereEtud.clear();
		inputProfesEn.clear();
		inputDepartEns.clear();
		DateNaissanceInput.setValue(null);

		RadioBTN1.setToggleGroup(group);
		RadioBTN2.setToggleGroup(group);

		if (RadioBTN1.isSelected() || RadioBTN2.isSelected()) {
			group.selectToggle(null);

			inputFiliereEtud.setStyle("-fx-opacity: 0");
			inputProfesEn.setStyle("-fx-opacity: 0");
			inputDepartEns.setStyle("-fx-opacity: 0");

			inputProfesEn.setVisible(false);
			inputDepartEns.setVisible(false);
			inputFiliereEtud.setVisible(false);
		}
	}

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
	void EtudiantClick(ActionEvent event) {
		inputFiliereEtud.setStyle("-fx-opacity: 1");
		inputProfesEn.setStyle("-fx-opacity: 0");
		inputDepartEns.setStyle("-fx-opacity: 0");
		inputProfesEn.setVisible(false);
		inputDepartEns.setVisible(false);

		inputFiliereEtud.setVisible(true);
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

	ToggleGroup group = new ToggleGroup();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		RadioBTN1.setToggleGroup(group);
		RadioBTN2.setToggleGroup(group);

		Color selectedColor = Color.rgb(236, 140, 43);
		RadioBTN1.setSelectedColor(selectedColor);
		RadioBTN2.setSelectedColor(selectedColor);

		// Converter
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		DateNaissanceInput.setConverter(converter);
		DateNaissanceInput.setPromptText("yyyy-MM-dd");
		DateNaissanceInput.setShowWeekNumbers(true);
	}

}
