package app.View;

import app.Controller.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;


public class AutoCompleteTextField extends TextField {

    private SortedSet<String> entries;

    private ContextMenu entriesPopup;
    private ChangeListener<String> changeListener;

    public AutoCompleteTextField() {
        super();
        entries = new TreeSet<>();
        entriesPopup = new ContextMenu();
        this.changeListener = (observableValue, s, s2) -> {
            if (getText().length() == 0) {
                entriesPopup.hide();
            } else {
                LinkedList<String> searchResult = new LinkedList<>();
                searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                if (entries.size() > 0) {
                    populatePopup(searchResult);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        };
        textProperty().addListener(this.changeListener);

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });
    }


    public SortedSet<String> getEntries() { return entries; }

    public void setEntries(SortedSet<String> entries) {
        this.entries = entries;
        this.changeListener = (observableValue, s, s2) -> {
            if (getText().length() == 0) {
                entriesPopup.hide();
            } else {
                ArrayList<String> searchResult = new ArrayList<>();
                for (String str: this.entries) {
                    if (str.toLowerCase().contains(getText().toLowerCase())) {
                        searchResult.add(str);
                    }

                }
                if (entries.size() > 0) {
                    populatePopup(searchResult);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        };
        textProperty().removeListener(this.changeListener);
        textProperty().addListener(this.changeListener);
        this.entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
    }


    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Text entryText = new Text(result);
            entryText.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
            CustomMenuItem item = new CustomMenuItem(entryText, true);
            item.setOnAction(actionEvent -> {
                setText(result);
                entriesPopup.hide();
                MainController.setStartLoc(getId());
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }
}