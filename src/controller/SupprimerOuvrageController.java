package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class SupprimerOuvrageController {
	connection conOBJ = new connection();
	Connection con;
	private PreparedStatement pst;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private JFXTextField IdOuvrageInput;

	@FXML
	private JFXButton deleteBookbtn;

	@FXML
	void deleteBook(ActionEvent event) {
		try {
			con = conOBJ.getConnection();
			String deleteOuvrage = "DELETE FROM `librarydb`.`ouvrage` WHERE `idouvrage` =?";
			pst = con.prepareStatement(deleteOuvrage);
			pst.setString(1, IdOuvrageInput.getText());
			pst.executeUpdate();
			pst.close();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Alert Message");
			alert.setContentText("L'Ouvrage `" + IdOuvrageInput.getText() + "` a bien été supprimé .");
			alert.show();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			AlertMessage alert = new AlertMessage();

			alert.setMessage("Something Wrong I can feel it... !");
		}
		IdOuvrageInput.clear();

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

}
