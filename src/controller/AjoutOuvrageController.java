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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Exemplaire;

public class AjoutOuvrageController implements Initializable {

	@FXML
	private JFXButton ajoutbtn;

	@FXML
	private JFXTextField titreInput;

	@FXML
	private JFXTextField categorieInput;

	@FXML
	private DatePicker DateEditionInput;

	@FXML
	private JFXTextField auteurInput;

	@FXML
	private Label label2;

	@FXML
	private JFXButton resetbtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXTextField idouvrage;

	@FXML
	private JFXTextField nbCopieExemp;

	@FXML
	private JFXTextField status;

	connection conOBJ = new connection();
	Connection con;
	private PreparedStatement ps;

	AlertMessage alert = new AlertMessage();

	/*
	 * Controle de saise des inputs: -Status: un seul caractère: D/d ET N/n
	 * [Possibilité de changet l'input en radio btn ou Spinner(boite selection)]
	 * -Nombre de copie -Generation de Code ouvrage aleatoirement
	 */
	@FXML
	void ajoutBook(ActionEvent event) {
		try {
			con = conOBJ.getConnection();

			String insert1 = "INSERT INTO `librarydb`.`ouvrage` (`idouvrage`, `titre`, `auteur`, `categorie`, `date_edition`) VALUES (?, ?, ?, ?, ?)";
			ps = con.prepareStatement(insert1);

			LocalDate localDate = DateEditionInput.getValue();
			int nb = Integer.parseInt(nbCopieExemp.getText());

			Exemplaire exemplaire = null;

			if (nb > 0) {
				exemplaire = new Exemplaire(idouvrage.getText(), titreInput.getText(), auteurInput.getText(), localDate,
						categorieInput.getText(), "D", nb);
			} else {
				exemplaire = new Exemplaire(idouvrage.getText(), titreInput.getText(), auteurInput.getText(), localDate,
						categorieInput.getText(), "N", nb);
			}

			ps.setString(1, exemplaire.getIdOuvrage());
			ps.setString(2, exemplaire.getTitreOuvrage());
			ps.setString(3, exemplaire.getAuteurOuvrage());
			ps.setString(4, exemplaire.getCategorieOuvrage());
			ps.setString(5, exemplaire.getDateEdition().toString());

			ps.executeUpdate();

			ps.close();

			String insert2 = "INSERT INTO `librarydb`.`exemplaire` (`idouvrage`, `status`, `nbrcopie`) VALUES (?, ?, ?)";

			ps = con.prepareStatement(insert2);

			ps.setString(1, idouvrage.getText());

			if (nb > 0)
				ps.setString(2, "D");
			else
				ps.setString(2, "N");
			ps.setInt(3, nb);

			ps.executeUpdate();
			System.out.println("Data inserted ouvra" + exemplaire);
			System.out.println("Data inserted exemp");
			ps.close();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Alert Message");
			alert.setContentText("L'Ouvrage `" + idouvrage.getText() + "` a bien été ajouté .");
			alert.show();

		} catch (ClassNotFoundException | SQLException e) {
			alert.setMessage("Something Wrong I can feel it... !");
		}
		resetForm(event);
	}

	@FXML
	void resetForm(ActionEvent event) {
		titreInput.clear();
		auteurInput.clear();
		idouvrage.clear();
		categorieInput.clear();
		nbCopieExemp.clear();
		DateEditionInput.setValue(null);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

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
		DateEditionInput.setConverter(converter);
		DateEditionInput.setPromptText("yyyy-MM-dd");
		DateEditionInput.setShowWeekNumbers(true);

	}

}
