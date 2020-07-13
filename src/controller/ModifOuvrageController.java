package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Exemplaire;

public class ModifOuvrageController implements Initializable {

	@FXML
	private JFXTextField titreInput;

	@FXML
	private JFXTextField auteurInput;

	@FXML
	private JFXButton Moudifbtn;

	@FXML
	private JFXTextField idouvrageCh;

	@FXML
	private JFXTextField categorieInput;

	@FXML
	private DatePicker DateEditionInput;

	@FXML
	private JFXButton resetbtn;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXButton Cherchebtn;

	@FXML
	private JFXTextField idouvrage;

	@FXML
	private JFXTextField nbCopieExemp;

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
	void ModifBook(ActionEvent event) {

		try {
			con = conOBJ.getConnection();
			String updateQuery1 = "UPDATE `librarydb`.`ouvrage` SET `idouvrage` = ?, `titre` = ?, `auteur` = ?, `categorie` = ?, `date_edition` = ? WHERE (`idouvrage` = \""
					+ idouvrage.getText() + "\");";
			String updateQuery2 = "UPDATE `librarydb`.`exemplaire` SET `status` = ?, `nbrcopie` = ? WHERE (`idouvrage` = \""
					+ idouvrage.getText() + "\");";

			pst = con.prepareStatement(updateQuery1);

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

			pst.setString(1, exemplaire.getIdOuvrage());
			pst.setString(2, exemplaire.getTitreOuvrage());
			pst.setString(3, exemplaire.getAuteurOuvrage());
			pst.setString(4, exemplaire.getCategorieOuvrage());
			pst.setString(5, exemplaire.getDateEdition().toString());

			pst.executeUpdate();

			pst.close();

			pst = con.prepareStatement(updateQuery2);

			if (nb > 0) {
				pst.setString(1, "D");
			} else {
				pst.setString(1, "N");
			}
			pst.setInt(2, nb);

			pst.executeUpdate();
			pst.close();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Alert Message");
			alert.setContentText("L'Ouvrage `" + idouvrage.getText() + "` a bien été modifié .");
			alert.show();

		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong I can feel it... !");
		}

		resetForm(event);

	}

	@FXML
	void resetForm(ActionEvent event) {
		titreInput.clear();
		auteurInput.clear();
		idouvrage.clear();
		idouvrageCh.clear();
		categorieInput.clear();
		nbCopieExemp.clear();
		DateEditionInput.setValue(null);
	}

	@FXML
	void ChercheBook(ActionEvent event) {
		try {
			con = conOBJ.getConnection();
			String query = "SELECT ouvrage.*,exemplaire.status,exemplaire.nbrcopie FROM librarydb.ouvrage inner join exemplaire on ouvrage.idouvrage=exemplaire.idouvrage where ouvrage.idouvrage=?";

			pst = con.prepareStatement(query);

			pst.setString(1, idouvrageCh.getText());

			ResultSet rs = pst.executeQuery();
			int countOuvrage = 0;

			while (rs.next()) {
				countOuvrage = countOuvrage + 1;

				idouvrage.setText(rs.getString(1));
				titreInput.setText(rs.getString(2));
				auteurInput.setText(rs.getString(3));
				categorieInput.setText(rs.getString(4));

				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.parse(rs.getString(5), dateFormatter);

				DateEditionInput.setValue(localDate);

				nbCopieExemp.setText(rs.getString(7));

			}
			rs.close();
			pst.close();

		} catch (ClassNotFoundException | SQLException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong ... !");
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		DateEditionInput.setConverter(converter);
		DateEditionInput.setPromptText("yyyy-MM-dd");
		DateEditionInput.setShowWeekNumbers(true);

	}

}
