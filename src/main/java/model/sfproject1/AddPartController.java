package model.sfproject1;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * the AddPartController provides all the properties and methods necessary to handle user interaction and data input for the
 * AddPart.fxml file
 *
 * */

public class AddPartController implements Initializable {

    /**
     * Properties
     */
    private Stage stage;
    private Object scene;
    @FXML private RadioButton OutsourcedBtn;
    @FXML private Label InOrOut;
    @FXML private TextField Name;
    @FXML private TextField Price;
    @FXML private TextField Inventory;
    @FXML private TextField Minimum;
    @FXML private TextField Maximum;
    @FXML private TextField CompanyMachineID;



    /**
     * function will toggle radio button between 'company name' or 'machine id'
     */
    public void radioBtn() {
        if (OutsourcedBtn.isSelected()) {
            this.InOrOut.setText("Company Name");

        } else {
            this.InOrOut.setText("Machine ID");
        }
    }

    /**
     * @return id returns next available id number
     */
    public int getNewID() {
        int id = 1;
        for (int i = 0; i < model.sfproject1.Inventory.getAllParts().size(); i++) {
            id++;
        }
        return id;
    }

    /**
     * function will create either a OutsourcedObj or InHouseObj, depends on the toggle btn
     * @param OutsourcedBtn,id,name,price,stock,min,max,
     */
    public void inOrOut(RadioButton OutsourcedBtn,int id, String name, Double price, int stock, int min, int max) {
        if (OutsourcedBtn.isSelected()) {
            String outID = CompanyMachineID.getText();
            Outsourced temp = new Outsourced(id, name, price, stock, min, max, outID);
            model.sfproject1.Inventory.addPart(temp);
        } else {
            int inID = Integer.parseInt(CompanyMachineID.getText());
            InHouse temp = new InHouse(id, name, price, stock, min, max, inID);
            model.sfproject1.Inventory.addPart(temp);
        }
    }

    /**
     * function will display informative modal window to verify if user wants to cancel adding a part
     * @param event ActionEvent objet
     */
    @FXML public void cancel(ActionEvent event) throws IOException {
        if (WindowController.confirmDialog("Cancel?", "Are you sure you want to cancel?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Window.fxml")));
            stage.setTitle("Iventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**
     * RUNTIME ERROR: save never threw IOException FIXED with: I removed it
     * FXMLLoader.load might return a null obj FIXED with: FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Window.fxml")));
     * @param event ActionEvent object
     */
    @FXML public void save(ActionEvent event) {
        try {

            if (Name.getText().isEmpty() || Price.getText().isEmpty() || Inventory.getText().isEmpty() || Minimum.getText().isEmpty() || Maximum.getText().isEmpty()) {
                WindowController.infoDialog("ERROR", "Blank fields detected", "You must enter values on all input fields");
                return;
            }
            if (WindowController.isDouble(Price.getText()) == false || WindowController.isNumeric(Inventory.getText()) == false || WindowController.isNumeric(Minimum.getText()) == false || WindowController.isNumeric(Maximum.getText()) == false) {
                WindowController.infoDialog("Error", "Inventory, price, min, max", "Verify that Inventory, price, min and max fields contains numbers and not alphabetical characters");
                return;
            }

            // get values from fields
            int id = getNewID();
            String name = Name.getText();
            double price = Double.parseDouble(Price.getText());
            int inventory = Integer.parseInt(Inventory.getText());
            int minimum = Integer.parseInt(Minimum.getText());
            int maximum = Integer.parseInt(Maximum.getText());

            if (minimum > maximum) {
                WindowController.infoDialog("Input Error", "Error in min and max fields", "min should be less than max");
                return;
            } else if (minimum > inventory || inventory > maximum) {
                WindowController.infoDialog("Input Error", "Error is inventory field", "Inventory should be between min and max values");
                return;
            }

            if (WindowController.confirmDialog("Save?", "Would you like to save this part?")) {

                inOrOut(OutsourcedBtn, id, name, price, inventory, minimum, maximum);
                // re-create a scene
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Window.fxml")));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}