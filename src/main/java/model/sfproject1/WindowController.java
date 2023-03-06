package model.sfproject1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.Objects;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * the WindowController provides all the properties and methods necessary to handle user interaction and data input for the
 * Window.fxml file
 * @author Luis Munguia
 */
public class WindowController implements Initializable {

    /**
     * Properties
     */
    private Parent scene;

    @FXML private TableView<Part> PartsTable;
    @FXML private TableColumn<Part, Integer> PartsIDCol;
    @FXML private TableColumn<Part, String> PartsNameCol;
    @FXML private TableColumn<Part, Integer> PartsInventoryCol;
    @FXML private TableColumn<Part, Double> PartsPriceCol;
    @FXML private TextField PartsArea;

    @FXML private TableView<Product> ProductsTable;
    @FXML private TableColumn<Product, Integer> ProductsIDCol;
    @FXML private TableColumn<Product, String> ProductsNameCol;
    @FXML private TableColumn<Product, String> ProductsInventoryCol;
    @FXML private TableColumn<Product, Double> ProductsPriceCol;
    @FXML private TextField ProductsArea;

    static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     *
     * @param title
     * @param content
     * @return
     */
    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * @param title
     * @param header
     * @param content
     */
    static void infoDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     *
     * @param event
     */
    @FXML public void exitBtn(ActionEvent event) {
        if (confirmDialog("Exit", "Are you sure?")) {
            System.exit(0);
        }
    }

    /**
     *
     * @param event
     */
    @FXML public void addPartBtn(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
            stage.setTitle("Add Part");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param event
     */
    @FXML public void addProductBtn(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct.fxml")));
            stage.setTitle("Add Product");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param event
     */
    @FXML public void modifyPartBtn(ActionEvent event) {
        try {
            Part selectedPart = PartsTable.getSelectionModel().getSelectedItem();

            if (selectedPart != null) {

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader fxml = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
                scene = fxml.load();

                ModifyPartController controller = fxml.getController();
                controller.setPart(selectedPart);

                stage.setTitle("Modify Part");
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                return;
            }
        } catch (Exception e) {
            infoDialog("Error", "Error with FXML file","Failed to create window");
        }
    }

    /**
     *
     * @param event
     */
    @FXML public void modifyProductBtn(ActionEvent event) {
        try {
            Product selectedProduct = ProductsTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader fxml = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
                scene = fxml.load();

                ModifyProductController controller = fxml.getController();
                controller.setProduct(selectedProduct);

                stage.setTitle("Modify Product");
                stage.setScene(new Scene(scene));
                stage.show();

            } else {
                return;
            }
        } catch (Exception e) {
            infoDialog("Error", "Error with FXML file","Failed to create window");
        }
    }

    /**
     *
     * @param event
     */
    @FXML public void searchPartBtn(ActionEvent event) {
        String term = PartsArea.getText();
        ObservableList<Part> searchResults = Inventory.lookupPartName(term);
        boolean numeric = isNumeric(term);

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
     *
     * @param event
     */
    @FXML public void searchProductBtn(ActionEvent event) {
        String term = ProductsArea.getText();
        ObservableList<Product> searchResults = Inventory.lookupProductName(term);
        boolean numeric = isNumeric(term);

        if (searchResults.isEmpty() && numeric == false) {
            WindowController.confirmDialog("No Match Found", "No such part exists in inventory.");
        } else if (numeric == true) {

            Product product = Inventory.lookupProductId(Integer.parseInt(term));
            ObservableList<Product> temp = FXCollections.observableArrayList(product);
            ProductsTable.setItems(temp);
        } else {
            ProductsTable.setItems(searchResults);
        }
    }

    /**
     *
     * @param event
     */
    @FXML public void deletePartBtn(ActionEvent event) {
        Part part = PartsTable.getSelectionModel().getSelectedItem();

        if (part != null) {
            WindowController.confirmDialog("Deleting parts", "Are you sure you want to delete " + part.getName() + " from the product?");
            PartsTable.getItems().remove(part);
        } else {
            WindowController.infoDialog("No part selected", "No selection", "Choose a part to remove");
        }
    }

    /**
     *
     * @param event
     */
    @FXML public void deleteProductBtn(ActionEvent event) {
        Product product = ProductsTable.getSelectionModel().getSelectedItem();

        if (product != null) {
            if (product.getAllAssociatedParts().isEmpty() == false) {
                WindowController.infoDialog("Delete Error", "Product Error ", "Cannot delete a product that has associated part");
                return;
            } else {
                WindowController.confirmDialog("Deleting parts", "Are you sure you want to delete " + product.getName() + " from the product?");
                ProductsTable.getItems().remove(product);
            }
        } else {
            WindowController.infoDialog("No product selected", "No selection", "Choose a product to remove");
        }
    }

    /**
     *
     * @param location
     * @param resources
     */
    @Override public void initialize(URL location, ResourceBundle resources) {

        // render Columns and Table for part
        PartsTable.setItems(model.sfproject1.Inventory.getAllParts());
        PartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // render Columns and Table for products table
        ProductsTable.setItems(model.sfproject1.Inventory.getAllProducts());
        ProductsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
