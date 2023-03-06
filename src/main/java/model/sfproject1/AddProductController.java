package model.sfproject1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Objects;


/**
 * the AddProductController provides all the properties and methods necessary to handle user interaction and data input for the
 * AddProduct.fxml file
 * @author Luis Munguia
 */
public class AddProductController implements Initializable {

    /**
     * Properties
     */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    private ObservableList<Part> originalParts = FXCollections.observableArrayList();
    private Stage stage;
    private Object scene;

    @FXML private TableView<Part> PartsTable;
    @FXML private TableColumn<Part, Integer> PartsIDCol;
    @FXML private TableColumn<Part, String> PartsNameCol;
    @FXML private TableColumn<Part, Integer> PartsInventoryCol;
    @FXML private TableColumn<Part, Double> PartsPriceCol;

    @FXML private TableView<Part> AssocPartsTable;
    @FXML private TableColumn<Product, Integer> AssocPartsIDCol;
    @FXML private TableColumn<Product, String> AssocPartsNameCol;
    @FXML private TableColumn<Product, Integer> AssocPartsInventoryCol;
    @FXML private TableColumn<Product, Double> AssocPartsPriceCol;

    @FXML private TextField AssocName;
    @FXML private TextField AssocPrice;
    @FXML private TextField AssocInventory;
    @FXML private TextField AssocMinimum;
    @FXML private TextField AssocMaximum;
    @FXML private TextField AssocSearchBox;

    /**
     * function will iterate over allProducts list and return next available id
     * @return new id
     */
    public int getNewID() {
        int id = 1;
        for (int i = 0; i < model.sfproject1.Inventory.getAllProducts().size(); i++) {
            id++;
        }
        return id;
    }

    /**
     * will add all parts from inventory to the parts table
     */
    public void updatePartsTable() {
        PartsTable.setItems(Inventory.getAllParts());
    }

    /**
     * will add all associated parts to the associated parts table
     */
    public void updateAssocPartsTable() {
        AssocPartsTable.setItems(assocParts);
    }

    /**
     *
     * @param list
     * @param product
     */
    public void addAllAssociatedParts(ObservableList<Part> list, Product product) {
        for (Part part: list) {
            product.addAssociatedPart(part);
        }
    }


    /**
     * function will search current inventory for matched part names
     * @param event takes in action event
     */

    @FXML public void searchPartBtn(ActionEvent event) {
        String term = AssocSearchBox.getText();
        ObservableList<Part> searchResults = Inventory.lookupPartName(term);
        boolean numeric = WindowController.isNumeric(term);

        if (searchResults.isEmpty() && numeric == false) {
            WindowController.confirmDialog("No Match Found", "No such part exists in inventory.");
        } else if (numeric == true) {
            Part part = Inventory.lookupPartId(Integer.parseInt(term));
            ObservableList<Part> temp = FXCollections.observableArrayList(part);
            PartsTable.setItems(temp);
        } else {
            PartsTable.setItems(searchResults);
        }
    }

    /**
     * function will associate the selected part to the Product and update the parts tables
     * @param event takes in action event
     */
    @FXML public void addPartToProduct(ActionEvent event) {
        Part part = PartsTable.getSelectionModel().getSelectedItem();

        if (part != null) {
            assocParts.add(part);
            updatePartsTable();
            updateAssocPartsTable();
        } else {
            WindowController.infoDialog("Missing part selection", "Select a part", "Select a part to associate it with the product.");
        }
    }


    /**
     *
     * @param event takes in action event
     */


    @FXML public void deletePartFromProduct(ActionEvent event) {
        Part part = AssocPartsTable.getSelectionModel().getSelectedItem();

        if (part != null) {
            WindowController.confirmDialog("Deleting parts", "Are you sure you want to delete " + part.getName() + " from the product?");
            assocParts.remove(part);
            updateAssocPartsTable();
        } else {
            WindowController.infoDialog("No part selected", "No selection", "Choose a part to remove");
        }
    }

    /**
     * @param event ActionEvent objet
     * function will display informative modal window to verify if user wants to cancel adding a part
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
     *
     * @param event takes in action event
     */

    @FXML public void save(ActionEvent event) {
        try {
            if (AssocName.getText().isEmpty() || AssocPrice.getText().isEmpty() || AssocInventory.getText().isEmpty() || AssocMinimum.getText().isEmpty() || AssocMaximum.getText().isEmpty()) {
                WindowController.infoDialog("ERROR", "Blank fields detected", "You must enter values on all input fields");
                return;
            }

            if (WindowController.isDouble(AssocPrice.getText()) == false || WindowController.isNumeric(AssocInventory.getText()) == false || WindowController.isNumeric(AssocMinimum.getText()) == false || WindowController.isNumeric(AssocMaximum.getText()) == false) {
                WindowController.infoDialog("Error", "Inventory, price, min, max", "Verify that Inventory, price, min and max fields contains numbers and not alphabetical characters");
                return;
            }
            int id = getNewID();
            String name = AssocName.getText();
            double price = Double.parseDouble(AssocPrice.getText());
            int inventory = Integer.parseInt(AssocInventory.getText());
            int minimum = Integer.parseInt(AssocMinimum.getText());
            int maximum = Integer.parseInt(AssocMaximum.getText());

            if (minimum > maximum) {
                WindowController.infoDialog("Input Error", "Error in min and max fields", "min should be less than max");
                return;
            } else if (minimum > inventory || inventory > maximum) {
                WindowController.infoDialog("Input Error", "Error is inventory field", "Inventory should be between min and max values");
                return;
            }

            if (WindowController.confirmDialog("Save?", "Would you like to save this part?")) {

                Product product = new Product(id, name, price, inventory, minimum, maximum);
//                product.addAssociatedPart(assocParts);
                addAllAssociatedParts(assocParts, product);
                Inventory.addProduct(product);

                // re-create a scene
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Window.fxml"))); // Argument might be null, added "objects.requireNonNull
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
    @Override public void initialize(URL location, ResourceBundle resources) {

        // render Columns and Table for un-associated parts.
        PartsTable.setItems(model.sfproject1.Inventory.getAllParts());
        PartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // render Columns and Table for associated parts
        AssocPartsTable.setItems(assocParts);
        AssocPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssocPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        updatePartsTable();
        updateAssocPartsTable();
    }

}
