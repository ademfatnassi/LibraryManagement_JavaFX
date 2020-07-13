package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

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
import DBConnection.connection;
import alertMessage.AlertMessage;

public class LoginController implements Initializable {
	@FXML
	private JFXPasswordField password;

	@FXML
	private Pane pane;

	@FXML
	private JFXButton login;

	@FXML
	private Label textViewL;

	@FXML
	private AnchorPane anPane;

	@FXML
	private JFXTextField username;

	private PreparedStatement pst;

	Connection con;

	connection conClass = new connection();

	@FXML
	void createLogin(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		con = conClass.getConnection();
		System.out.println(con);
		System.out.println("Connected to Database");

		String query = "SELECT * FROM employe where username_employe=? and password_employe=?";

		pst = con.prepareStatement(query);

		pst.setString(1, username.getText());
		pst.setString(2, password.getText());

		ResultSet rs = pst.executeQuery();
		int count = 0;

		while (rs.next()) {
			count++;
		}

		if (count == 1) {
			System.out.println("Login Successfull ");
			login.getScene().getWindow().hide();

			Stage home = new Stage();
			home.setMaximized(true);
			Parent root = FXMLLoader.load(getClass().getResource("/view/MenuPage.fxml"));
			home.setScene(new Scene(root));
			home.show();

		} else {
			System.out.println("Login Faild");
			AlertMessage alert = new AlertMessage();
			alert.setMessage("Login Faild!\n\tWrong Password/Username.");
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
