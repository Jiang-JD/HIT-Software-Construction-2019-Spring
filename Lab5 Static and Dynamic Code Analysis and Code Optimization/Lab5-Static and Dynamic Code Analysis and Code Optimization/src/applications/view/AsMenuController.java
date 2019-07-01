package applications.view;

import apis.CircularOrbitApis;
import applications.MainAppGui;
import applications.tools.BuildVisualPane;
import applications.tools.MementoCareTaker;
import circularorbit.AtomStructure;
import constant.ReusablePool;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import physicalobject.Electron;
import physicalobject.ElectronFactory;

/**.
 * Menu of Atom Structure
 */
public class AsMenuController {

  @FXML
  private Label erroraddtrack;

  @FXML
  private TextField removetrack;

  @FXML
  private Label erorremovetrack;

  @FXML
  private TextField arelenum;

  @FXML
  private TextField addtrack;

  @FXML
  private Label errorele;

  @FXML
  private TableView<Integer> trackmap;

  @FXML
  private TableColumn<Integer, String> tracksColumn;

  @FXML
  private TableColumn<Integer, String> elenumColumn;

  @FXML
  private Label name;

  @FXML
  private Label tracknum;

  @FXML
  private Label electronnum;

  @FXML
  private Label entropy;

  @FXML
  private StackPane vispane;

  @FXML
  private Button btnVis;

  private MainAppGui mainapp;
  private AtomStructure current;
  private ObservableList<Integer> il = FXCollections.observableArrayList();
  private MementoCareTaker mck = new MementoCareTaker();
  private Logger logger = Logger.getLogger(AsMenuController.class);

  @FXML
  void handleAbout(ActionEvent event) {

  }

  @FXML
  void handleAddEle(ActionEvent event) {
    if (checkAddEle()) {
      ElectronFactory ef = new ElectronFactory();
      int index = Integer.parseInt(addtrack.getText());
      for (int i = 0; i < Integer.parseInt(arelenum.getText()); i++) {
        current.addObject((Electron)ef.create(), index);
      }
      updateAtomStructure();
      logger.info("User add " + Integer.parseInt(arelenum.getText()) 
              + " electrons on track " + index);
    }
  }

  @FXML
  void handleAddTrack(ActionEvent event) {
    int index = -1;
    int newNumber = current.getTrackNum() + 1;
    do {
      index = current.addTrack(newNumber);
      newNumber++;
    } while (index == -1);
    updateAtomStructure();
    logger.info("User add a new track");
  }

  @FXML
  void handleLog(ActionEvent event) {
    mainapp.initLogSystem();
  }

  @FXML
  void handleRemoveEle(ActionEvent event) {
    if (checkRemoveEle()) {
      ElectronFactory ef = new ElectronFactory();
      int index = Integer.parseInt(addtrack.getText());
      for (int i = 0; i < Integer.parseInt(arelenum.getText()); i++) {
        current.remove((Electron)ef.create(), index);
      }
      updateAtomStructure();
      logger.info("User remove " + Integer.parseInt(arelenum.getText()) 
            + " electrons on track " + index);

    }
  }

  @FXML
  void handleRemoveTrack(ActionEvent event) {
    if (checkRemoveTrack()) {
      current.remove(Integer.parseInt(removetrack.getText()));
      updateAtomStructure();
      logger.info("User remove track " + Integer.parseInt(removetrack.getText()));

    }
  }

  @FXML
  void handleRestore(ActionEvent event) {
    if (mainapp.initRestore(mck)) {
      mck.add(current.backup());
      current.restore(mainapp.getBackUp());
    }
    updateAtomStructure();
  }

  @FXML
  void handleTransit(ActionEvent event) {
    if (mainapp.initTransit(current)) {
      mck.add(current.backup()); // backup
      for (int i = 0; i < mainapp.getEleNumber(); i++) {
        current.transit(mainapp.getAsStartIndex(), mainapp.getAsEndIndex());
      }
      updateAtomStructure();
    }
  }

  @FXML
  void handleVisualize(ActionEvent event) {
    mainapp.initVisualize(current);
  }
  
  @FXML
  void handleOutput(ActionEvent event) {
    mainapp.initOutput(current);
  }

  @FXML
  private void initialize() {
    tracksColumn.setCellValueFactory(cellData -> {
      int i = cellData.getValue();
      return new ReadOnlyStringWrapper(String.valueOf(current.getNumber(i)));
    });
    elenumColumn.setCellValueFactory(cellData -> {
      int i = cellData.getValue();
      return new ReadOnlyStringWrapper(String.valueOf(current.getObjects(i).size()));
    });
  }

  private boolean checkRemoveTrack() {
    if (!removetrack.getText().matches("^\\d+$")) {
      erorremovetrack.setText("Illegal Index format, should be integer >= 0, try again");
      return false;
    }
    if (Integer.parseInt(removetrack.getText()) >= current.getTrackNum()) {
      erorremovetrack.setText("Index out of range, try again");
      return false;
    }
    erorremovetrack.setText("");
    return true;
  }

  private boolean checkAddEle() {
    if (!arelenum.getText().matches("^\\d+$")) {
      errorele.setText("Illegal Number format, should be integer >= 0, try again");
      return false;
    }
    if (!addtrack.getText().matches("^\\d+$")) {
      errorele.setText("Illegal Index format, should be integer >= 0, try again");
      return false;
    }
    if (Integer.parseInt(addtrack.getText()) >= current.getTrackNum()) {
      errorele.setText("Index out of range, try again");
      return false;
    }
    errorele.setText("");
    return true;
  }

  private boolean checkRemoveEle() {
    if (!arelenum.getText().matches("^\\d+$")) {
      errorele.setText("Illegal Number format, should be integer >= 0, try again");
      return false;
    }
    if (!addtrack.getText().matches("^\\d+$")) {
      errorele.setText("Illegal Index format, should be integer >= 0, try again");
      return false;
    }
    if (Integer.parseInt(addtrack.getText()) >= current.getTrackNum()) {
      errorele.setText("Index out of range, try again");
      return false;
    }
    if (Integer.parseInt(arelenum.getText()) 
        > current.getObjects(Integer.parseInt(addtrack.getText())).size()) {
      errorele.setText("Remove too much electrons, try again");
      return false;
    }
    errorele.setText("");
    return true;
  }

  public void setMainApp(MainAppGui mainapp) {
    this.mainapp = mainapp;
  }

  public void setAtomStructure(AtomStructure as) {
    this.current = as;
    updateAtomStructure();
  }

  /**.
   * Update atom structure info after change system
   */
  public void updateAtomStructure() {
    name.setText(String.valueOf(current.getName()));
    tracknum.setText(String.valueOf(current.getTrackNum()));
    electronnum.setText(String.valueOf(current.getObjectNum()));
    entropy.setText(String.valueOf(new CircularOrbitApis().getObjectDistributionEntropy(current)));
    il.clear();
    for (int i = 0; i < current.getTrackNum(); i++) {
      il.add(i);
    }
    trackmap.setItems(il);
    refreshVis();
  }

  /**.
   * Update system visual after change system
   */
  public void refreshVis() {
    vispane.getChildren().clear();
    vispane.getChildren()
        .add(BuildVisualPane.drawCircularOrbit(
            current, vispane.getPrefWidth(), vispane.getPrefHeight()));
  }

}
