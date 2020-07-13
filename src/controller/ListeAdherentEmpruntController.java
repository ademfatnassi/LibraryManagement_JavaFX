package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import DBConnection.connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.XAdherentOuvrage;

public class ListeAdherentEmpruntController implements Initializable {
	@FXML
	private TableView<XAdherentOuvrage> tableAdherentEmprunt;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private TableColumn<XAdherentOuvrage, String> mobile;

	@FXML
	private TableColumn<XAdherentOuvrage, String> cin;

	@FXML
	private TableColumn<XAdherentOuvrage, String> idOuvrage;

	@FXML
	private TableColumn<XAdherentOuvrage, String> nom;

	@FXML
	private TableColumn<XAdherentOuvrage, String> prenom;

	@FXML
	private TableColumn<XAdherentOuvrage, String> email;

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

	void RetreiveData() throws ClassNotFoundException, SQLException {

		con = conOBJ.getConnection();
		ObservableList<XAdherentOuvrage> data = FXCollections.observableArrayList();
		tableAdherentEmprunt.setItems(data);

		String str = "SELECT adherent.cin_adherent, emprunt.idOuvrage,adherent.nom_adherent,adherent.prenom_adherent,adherent.phone_adherent, adherent.email_adherent from adherent inner join emprunt on adherent.cin_adherent = emprunt.cin_adherent where emprunt.Retourne=0";

		pst = con.prepareStatement(str);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			XAdherentOuvrage adherentOuvrage = new XAdherentOuvrage(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getString(4), rs.getString(5), rs.getString(6));
			data.add(adherentOuvrage);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			RetreiveData();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		cin.setCellValueFactory(new PropertyValueFactory<XAdherentOuvrage, String>("cin"));
		idOuvrage.setCellValueFactory(new PropertyValueFactory<XAdherentOuvrage, String>("idOuvrage"));
		nom.setCellValueFactory(new PropertyValueFactory<XAdherentOuvrage, String>("nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<XAdherentOuvrage, String>("prenom"));
		mobile.setCellValueFactory(new PropertyValueFactory<XAdherentOuvrage, String>("phone"));
		email.setCellValueFactory(new PropertyValueFactory<XAdherentOuvrage, String>("email"));

	}
}
