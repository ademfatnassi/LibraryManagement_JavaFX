package alertMessage;

import javafx.scene.control.Alert;

public class AlertMessage {
	public void setMessage(String str) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("Alert Message");
		alert.setContentText(str);
		alert.show();
	}
}
