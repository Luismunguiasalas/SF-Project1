package model.sfproject1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Objects;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * the modifyPartController provides all the properties and methods necessary to handle user interaction and data input for the
 * ModifyPart.fxml file
 * @author Luis Munguia
 */
public class ModifyPartController implements Initializable {

    private Stage stage;
    private Object scene;
    private int partIndex;


    @FXML private RadioButton OutsourcedBtn;
    @FXML private RadioButton inHouse;
    @FXML private Label InOrOut;
    @FXML private TextField ID;
    @FXML private TextField Name;
    @FXML private TextField Price;
    @FXML private TextField Inventory;
    @FXML private TextField Minimum;
    @FXML private TextField Maximum;
    @FXML private TextField CompanyMachineID;




    public void radioBtn() {
        if (OutsourcedBtn.isSelected()) {
            this.InOrOut.setText("Company Name");

        } else {
            this.InOrOut.setText("Machine ID");
        }
    }

    /**
     * function will create either a OutsourcedObj or InHouseObj, depends on the toggle btn
     * @param inHouse,id,name,price,stock,min,max,
     */
    public void inOrOut(RadioButton inHouse,int id, String name, Double price, int stock, int min, int max, int partIndex) {
        if (inHouse.isSelected()) {
            int inID = Integer.parseInt(CompanyMachineID.getText());
            InHouse temp = new InHouse(id, name, price, stock, min, max, inID);
            model.sfproject1.Inventory.updatePart(partIndex, temp);

        } else {
            String outID = CompanyMachineID.getText();
            Outsourced temp = new Outsourced(id, name, price, stock, min, max, outID);
            model.sfproject1.Inventory.updatePart(partIndex, temp);
        }
    }

    public void setPart(Part part) {
        this.partIndex = model.sfproject1.Inventory.getAllParts().indexOf(part);
        ID.setText(Integer.toString(part.getId()));
        Name.setText(part.getName());
        Inventory.setText(Integer.toString(part.getStock()));
        Price.setText(Double.toString(part.getPrice()));
        Minimum.setText(Integer.toString(part.getMin()));
        Maximum.setText(Integer.toString(part.getMax()));
        if (part instanceof InHouse) {
            int machineId = ((InHouse) part).getMachineId();
            inHouse.setSelected(true);
            InOrOut.setText("Machine ID");
            CompanyMachineID.setText(Integer.toString(machineId));

        } else {
            String companyName = ((Outsourced) part).getCompanyName();
            OutsourcedBtn.setSelected(true);
            this.InOrOut.setText("Company Name");
            CompanyMachineID.setText(companyName);
        }

    }


    // public void setParts(Part )

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

            int id  = Integer.parseInt(ID.getText());
            String name = Name.getText();
            double price = Double.parseDouble(Price.getText());
            int inventory = Integer.parseInt(Inventory.getText());
            int minimum =  Integer.parseInt(Minimum.getText());
            int maximum = Integer.parseInt(Maximum.getText());

            if (minimum > maximum) {
                WindowController.infoDialog("Input Error", "Error in min and max fields", "min should be less than max");
                return;
            } else if (minimum > inventory || inventory > maximum) {
                WindowController.infoDialog("Input Error", "Error is inventory field", "Inventory should be between min and max values");
                return;
            }

            if (WindowController.confirmDialog("Save?", "Would you like to save this part?")) {
                inOrOut(inHouse, id, name, price, inventory, minimum, maximum, this.partIndex);
                // render main window
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
    @Override public void initialize(URL location, ResourceBundle resources) {}

}
