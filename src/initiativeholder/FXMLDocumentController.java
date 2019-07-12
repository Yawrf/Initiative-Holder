/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package initiativeholder;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 *
 * @author rewil
 */
public class FXMLDocumentController implements Initializable {
    
    //<editor-fold>
    @FXML
    private TextArea Slot1;
    @FXML
    private TextArea Slot2;
    @FXML
    private TextArea Slot3;
    @FXML
    private TextArea Slot4;
    @FXML
    private TextArea Slot5;
    @FXML
    private TextArea itemName;
    @FXML
    private TextArea initValue;
    @FXML
    private Button nextButton;
    @FXML
    private Button addTempButton;
    @FXML
    private Button addPermButton;
    @FXML
    private Button removePermButton;
    @FXML
    private Button initSetButton;
    @FXML
    private Button readyActionButton;
    @FXML
    private Button triggerButton;
    @FXML
    private Button startButton;
    @FXML
    private Button endButton;
    @FXML
    private Button removeInitButton;
    @FXML
    private ComboBox initPicker;
    @FXML
    private ComboBox readiedPicker;
    
//    private final ArrayList<String> permList = new ArrayList<>();
//    private ArrayList<String> tempList = new ArrayList<>();
//    private ArrayList<String> useList = new ArrayList<>();
//    private HashMap<String, Integer> initSorting = new HashMap<>();
//    private final ArrayList<String> readiedList = new ArrayList<>();
    private final String blank = "";
    private final InitTrack track = new InitTrack();
    private InitItem currentItem = null;
    
    //</editor-fold>
    
    @FXML
    private void nextButtonAction(ActionEvent event) {
        //Cycle Initiatives
        currentItem = track.getNextItem(currentItem);
        currentItem.setReadied(false);
        setSlots();
    }
    
    @FXML
    private void addTempAction(ActionEvent event) {
        //Add Item Name to tempList
        String name = itemName.textProperty().getValue();
        if(!name.equals(blank)) {
            InitItem newItem = new InitItem(name, false);
            track.addInitItem(newItem);
            itemName.textProperty().set(blank);
        }
    }
    
    @FXML
    private void addPermAction(ActionEvent event) {
        //Add Item Name to permList
        String name = itemName.textProperty().getValue();
        if(!name.equals(blank)) {
            InitItem newItem = new InitItem(name, true);
            track.addInitItem(newItem);
            itemName.textProperty().set(blank);
        }
    }
    
    @FXML
    private void removePermButtonAction(ActionEvent event) {
        //Remove InitPicker from permList
        String name = (String) initPicker.valueProperty().getValue();
        name = name.split(" - ")[0];
        InitItem tempItem = null;
        for(InitItem i : track.getTrack()) {
            if(i.getName().equals(name)) {
                tempItem = i;
            }
        }
        if(tempItem != null) {
            track.getTrack().remove(tempItem);
            initPicker.setValue(initPicker.getPromptText());
            initValue.setText(blank);
        }
    }
    
    @FXML
    private void initSetAction(ActionEvent event) {
        //Add Item Name and Init to initSorting
        String name = (String)initPicker.valueProperty().getValue();
        String initTemp = initValue.textProperty().getValue();
        Integer init = Integer.valueOf(initTemp);
        name = name.split(" - ")[0];
        InitItem tempItem = null;
        for(InitItem i : track.getTrack()) {
            if(i.getName().equals(name)) {
                tempItem = i;
            }
        }
        if(tempItem != null) {
            tempItem.setInit(init);
            tempItem.setInUse(true);
            initPicker.valueProperty().set(initPicker.promptTextProperty().getValue());
            initValue.textProperty().set(blank);
            setSlots();
        }
    }
    
    @FXML
    private void setInitComboBox() {
        //Set initPicker item list to all Perm and Temp items
        ArrayList<String> temp = new ArrayList<>();
        for(InitItem i : track.getNameSort()) {
            temp.add(i.toString());
        }
        ObservableList<String> options = FXCollections.observableArrayList(temp);
        initPicker.setItems(options);
    }
    
    @FXML
    private void initPickerAction(ActionEvent event) {
        //Recall associated init, if any
        String name = (String)initPicker.valueProperty().getValue();
        if(name != null) {
            name = name.split(" - ")[0];
            InitItem tempItem = null;
            for(InitItem i : track.getTrack()) {
                if(i.getName().equals(name)) {
                    tempItem = i;
                }
            }
            if(tempItem != null) {
                initValue.setText("" + tempItem.getInit());
            }
        }
    }
    
    @FXML
    private void startButtonAction(ActionEvent event) {
        setDisabled(true);
        currentItem = track.getInitSort().get(0);
        setSlots();
    }

    @FXML
    private void endButtonAction(ActionEvent event) {
        setDisabled(false);
        track.endTrack();
        currentItem = null;
        setSlots();
    }
    
    @FXML
    private void readyActionButtonAction(ActionEvent event) {
        currentItem.setReadied(true);
        nextButtonAction(event);
    }
    
    @FXML 
    private void triggerButtonAction(ActionEvent event) {
        String name = (String) readiedPicker.valueProperty().getValue();
        InitItem tempItem = null;
        for(InitItem i : track.getReadiedTrack()) {
            if(i.getName().equals(name)) {
                tempItem = i;
            }
        }
        if(tempItem != null) {
            tempItem.setInit(currentItem.getInit());
            tempItem.setReadied(false);
            currentItem = tempItem;
        } else {
            System.out.println("Trigger Error - No Match");
        }
        
        setSlots();
        readiedPicker.valueProperty().set(readiedPicker.promptTextProperty().getValue());
    }
    
    @FXML 
    private void removeInitButtonAction(ActionEvent event) {
        currentItem = track.unUseItem(currentItem);
        setSlots();
        if(!currentItem.isInUse()) {
            endButtonAction(event);
        }
    }
    
    @FXML
    private void setReadiedComboBox() {
        ArrayList<String> readiedList = new ArrayList<>();
        for(InitItem i : track.getReadiedTrack()) {
            readiedList.add(i.getName());
        }
        
        ObservableList<String> options = FXCollections.observableArrayList(readiedList);
        readiedPicker.setItems(options);
    }
    
    @FXML
    private void numberOnlyFilter() {
        //Ensure there are only numbers
        ArrayList<Character> initChars = new ArrayList<>();
        char[] init = initValue.textProperty().getValue().toCharArray();
        for(char c : init) {
            initChars.add(c);
        }
        for(int i = 0; i < initChars.size();) {
            if((int)initChars.get(i) < 47 || (int)initChars.get(i) > 58) {
                initChars.remove(i);
            } else {
                ++i;
            }
        } 
        String initNew = "";
        for(char c : initChars) {
            initNew += c;
        }
        initValue.textProperty().set(initNew);
        initValue.end();
    }
    
    private void setSlots() {
        InitItem tempItem = currentItem;
        Slot1.textProperty().set(tempItem == null ? blank : tempItem.toString());
        tempItem = track.getNextItem(tempItem);
        Slot2.textProperty().set(tempItem == null ? blank : tempItem.toString());
        tempItem = track.getNextItem(tempItem);
        Slot3.textProperty().set(tempItem == null ? blank : tempItem.toString());
        tempItem = track.getNextItem(tempItem);
        Slot4.textProperty().set(tempItem == null ? blank : tempItem.toString());
        tempItem = track.getNextItem(tempItem);
        Slot5.textProperty().set(tempItem == null ? blank : tempItem.toString());
    }
    
    private void setDisabled(boolean start) {
        startButton.setDisable(start);
        endButton.setDisable(!start);
        nextButton.setDisable(!start);
        triggerButton.setDisable(!start);
        readyActionButton.setDisable(!start);
        readiedPicker.setDisable(!start);
        removePermButton.setDisable(start);
        removeInitButton.setDisable(!start);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
