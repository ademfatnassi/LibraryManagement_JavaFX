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
import model.XAdherent;

public class ListeAdherentController implements Initializable {

	@FXML
	private TableColumn<XAdherent, Integer> nbCopie;

	@FXML
	private TableColumn<XAdherent, String> profession;

	@FXML
	private TableColumn<XAdherent, String> departement;

	@FXML
	private Label textViewHeader;

	@FXML
	private Pane paneAU;

	@FXML
	private JFXButton RetourBtn;

	@FXML
	private TableColumn<XAdherent, String> mobile;

	@FXML
	private TableView<XAdherent> tableAdherent;

	@FXML
	private TableColumn<XAdherent, String> cin;

	@FXML
	private TableColumn<XAdherent, String> nom;

	@FXML
	private TableColumn<XAdherent, String> filiere;

	@FXML
	private TableColumn<XAdherent, String> prenom;

	@FXML
	private TableColumn<XAdherent, String> email;

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
		ObservableList<XAdherent> data = FXCollections.observableArrayList();
		tableAdherent.setItems(data);

		String str = "SELECT adherent.*,enseignant.departement,enseignant.profession, '(vide)' as filiere from adherent inner join enseignant on adherent.cin_adherent = enseignant.cin_adherent union all SELECT adherent.*,'(vide)' AS departement, '(vide)' AS profession, etudiant.filiere from adherent inner join etudiant on adherent.cin_adherent = etudiant.cin_adherent ";

		pst = con.prepareStatement(str);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			XAdherent adherent = new XAdherent(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9));
			data.add(adherent);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			RetreiveData();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cin.setCellValueFactory(new PropertyValueFactory<XAdherent, String>("cin"));
		email.setCellValueFactory(new PropertyValueFactory<XAdherent, String>("email"));
		nom.setCellValueFactory(new PropertyValueFactory<XAdherent, String>("nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<XAdherent, String>("prenom"));
		mobile.setCellValueFactory(new PropertyValueFactory<XAdherent, String>("phone"));
		nbCopie.setCellValueFactory(new PropertyValueFactory<XAdherent, Integer>("nbCopieEmp"));
		profession.setCellValueFactory(new PropertyValueFactory<XAdherent, String>("profession"));
		departement.setCellValueFactory(new PropertyValueFactory<XAdherent, String>("departement"));
		filiere.setCellValueFactory(new PropertyValueFactory<XAdherent, String>("filiere"));

	}

}
