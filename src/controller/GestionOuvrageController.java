package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import alertMessage.AlertMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionOuvrageController {
	@FXML
	private Label textViewHeader;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXButton ModifLivrerBtn;

	@FXML
	private JFXButton listeLivreBtn;

	@FXML
	private JFXButton deleteLivrebtn;

	@FXML
	private JFXTextField CINInput;

	@FXML
	private Pane pane;

	@FXML
	private JFXButton ajoutLivreBtn;

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
	void ajoutLivre(ActionEvent event) {
		ajoutLivreBtn.getScene().getWindow().hide();

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/AjoutOuvrageView.fxml"));
		} catch (IOException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong ... !");
		}
		home.setScene(new Scene(root));
		home.show();

	}

	@FXML
	void ModifLivre(ActionEvent event) {

	}

	@FXML
	void afficheLivre(ActionEvent event) {

	}

	@FXML
	void deleteLivre(ActionEvent event) {

	}
}
