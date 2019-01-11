/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package initiative.holder;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Button addTrackButton;
    
    @FXML
    private ComboBox initPicker;
    
    @FXML
    private ComboBox readiedPicker;
    
    private final ArrayList<String> permList = new ArrayList<>();
    private ArrayList<String> tempList = new ArrayList<>();
    private ArrayList<String> useList = new ArrayList<>();
    private HashMap<String, Integer> initSorting = new HashMap<>();
    private final ArrayList<String> readiedList = new ArrayList<>();
    private final String blank = "";
    
    @FXML
    private void nextButtonAction(ActionEvent event) {
        //Cycle Initiatives
        if(useList.size() > 0) {
            useList.add(useList.remove(0));
            setSlots();
        }
    }
    
    @FXML
    private void addTempAction(ActionEvent event) {
        //Add Item Name to tempList
        String name = itemName.textProperty().getValue();
        if(!name.equals(blank)) {
            tempList.add(0,name);
            itemName.textProperty().set(blank);
        }
    }
    
    @FXML
    private void addPermAction(ActionEvent event) {
        //Add Item Name to permList
        String name = itemName.textProperty().getValue();
        if(!name.equals(blank)) {
            permList.add(0,name);
            itemName.textProperty().set(blank);
        }
    }
    
    @FXML
    private void removePermButtonAction(ActionEvent event) {
        //Remove InitPicker from permList
        String name = (String) initPicker.valueProperty().getValue();
        if(permList.contains(name)) {
            permList.remove(name);
            initPicker.valueProperty().set(initPicker.promptTextProperty().getValue());
        }
    }
    
    @FXML
    private void initSetAction(ActionEvent event) {
        //Add Item Name and Init to initSorting
        String name = (String)initPicker.valueProperty().getValue();
        String initTemp = initValue.textProperty().getValue();
        Integer init = Integer.valueOf(initTemp);
        if(!name.equals(blank) && !name.equals(initPicker.promptTextProperty().getValue())) {
            initSorting.put(name, init);
            initPicker.valueProperty().set(initPicker.promptTextProperty().getValue());
            initValue.textProperty().set(blank);
        }
    }
    
    @FXML
    private void setInitComboBox() {
        //Set initPicker item list to all Perm and Temp items
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(tempList);
        temp.addAll(permList);
        ObservableList<String> options = FXCollections.observableArrayList(temp);
        initPicker.setItems(options);
    }
    
    @FXML
    private void initPickerAction(ActionEvent event) {
        //Recall associated init, if any
        String name = (String)initPicker.valueProperty().getValue();
        if(initSorting.containsKey(name)) {
            initValue.textProperty().set(initSorting.get(name).toString());
        }
    }
    
    @FXML
    private void startButtonAction(ActionEvent event) {
        //Sort initSorting into useList and initialize Slots
        ArrayList<String> names = new ArrayList<>();
        names.addAll(initSorting.keySet());
        ArrayList<Integer> inits = new ArrayList<>();
        inits.addAll(initSorting.values());
        
        ArrayList<String> sorted = new ArrayList<>();
        while(names.size() > 0) {
            int temp = -1;
            int spot = 0;
            for(int i = 0; i < names.size(); ++i) {
                if(inits.get(i) > temp) {
                    spot = i;
                    temp = inits.get(i);
                }
            }
            sorted.add(names.remove(spot));
            inits.remove(spot);
        }
        useList = sorted;
        
        setSlots();
        
        setDisabled(true);
    }

    @FXML
    private void endButtonAction(ActionEvent event) {
        useList = new ArrayList<>();
        tempList = new ArrayList<>();
        initSorting = new HashMap<>();
        
        setSlots();
        
        setDisabled(false);
    }
    
    @FXML
    private void readyActionButtonAction(ActionEvent event) {
        readiedList.add(useList.get(0));
        nextButtonAction(event);
    }
    
    @FXML 
    private void triggerButtonAction(ActionEvent event) {
        String name = (String) readiedPicker.valueProperty().getValue();
        useList.add(0,useList.remove(useList.indexOf(name)));
        readiedList.remove(name);
        setSlots();
        readiedPicker.valueProperty().set(readiedPicker.promptTextProperty().getValue());
    }
    
    @FXML 
    private void removeInitButtonAction(ActionEvent event) {
        String name = Slot1.textProperty().getValue();
        useList.remove(name);
        setSlots();
    }
    
    @FXML
    private void addTrackButtonAction(ActionEvent event) {
        String name = itemName.getText();
        if(!name.equals(blank) && !name.equals(itemName.getPromptText())) {
            useList.add(name);
            setSlots();
        }
        itemName.setText(blank);
    }
    
    @FXML
    private void setReadiedComboBox() {
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
        Slot1.textProperty().set(useList.size() > 0 ? useList.get(0) : blank);
        Slot2.textProperty().set(useList.size() > 1 ? useList.get(1) : blank);
        Slot3.textProperty().set(useList.size() > 2 ? useList.get(2) : blank);
        Slot4.textProperty().set(useList.size() > 3 ? useList.get(3) : blank);
        Slot5.textProperty().set(useList.size() > 4 ? useList.get(4) : blank);
        
        String name = Slot1.textProperty().getValue();
        if(readiedList.contains(name)) {
            readiedList.remove(name);
        }
    }
    
    private void setDisabled(boolean start) {
        startButton.setDisable(start);
        endButton.setDisable(!start);
        nextButton.setDisable(!start);
        initSetButton.setDisable(start);
        initPicker.setDisable(start);
        addTempButton.setDisable(start);
        addPermButton.setDisable(start);
        triggerButton.setDisable(!start);
        readyActionButton.setDisable(!start);
        readiedPicker.setDisable(!start);
        removePermButton.setDisable(start);
        removeInitButton.setDisable(!start);
        addTrackButton.setDisable(!start);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        for(int i = 1; i <= 10; ++i) {
            String s = "";
            s += i;
            permList.add(s);
        }
    }    
    
}
