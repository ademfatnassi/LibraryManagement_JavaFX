package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

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

public class MenuPageController implements Initializable {

	@FXML
	private Label textViewHeader;

	@FXML
	private AnchorPane anPanMP;

	@FXML
	private JFXButton gestOuvrage;

	@FXML
	private JFXButton gestAdherentbtn;

	@FXML
	private Pane pane;

	@FXML
	private JFXButton EmpRetbtn;

	@FXML
	void gestAdherent(ActionEvent event) throws IOException {

		gestAdherentbtn.getScene().getWindow().hide();

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = FXMLLoader.load(getClass().getResource("/view/GestionAdherent.fxml"));
		home.setScene(new Scene(root));
		home.show();

	}

	@FXML
	void gestOuvrage(ActionEvent event) {

		gestOuvrage.getScene().getWindow().hide();

		Stage home = new Stage();
		home.setMaximized(true);
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/GestionAdherent.fxml"));
		} catch (IOException e) {
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Something Wrong ... !");
		}
		home.setScene(new Scene(root));
		home.show();
	}

	@FXML
	void EmpRetbtn(ActionEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
