/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontendControllers;

import javafx.scene.control.ToggleButton;

/**
 *
 * @author Iustina
 */
class ToggleButtonObject {
    ToggleButton toggleButton;
    ToggleButtonObject(int id, int x, int y, int wight, String name, boolean toggel){
        this.toggleButton=new ToggleButton();
        toggleButton.setSelected(toggel); 
        toggleButton.setPrefWidth(wight);
        toggleButton.setPrefHeight(30);
        if(toggel){
            toggleButton.setStyle("-fx-background-color: #548f64; -fx-text-fill: #eeeeee;-fx-font-size: 14px;-fx-border-color: #909090; -fx-border-width: 1px;");
            toggleButton.setOnMouseEntered(e -> {
                    toggleButton.setStyle("-fx-background-color: #426F4E; -fx-text-fill: #eeeeee; -fx-font-size: 14px; -fx-border-color: #909090; -fx-border-width: 1px;");
                });
                toggleButton.setOnMouseExited(e -> {
                    toggleButton.setStyle("-fx-background-color: #548f64; -fx-text-fill: #eeeeee;-fx-font-size: 14px;-fx-border-color: #909090; -fx-border-width: 1px;");
                });
        }
        else{
            toggleButton.setStyle("-fx-background-color: #e2e2e2; -fx-text-fill: #363636; -fx-font-size: 14px; -fx-border-color: #909090; -fx-border-width: 1px;");
            toggleButton.setOnMouseEntered(e -> {
                toggleButton.setStyle("-fx-background-color: #cecece; -fx-text-fill: #363636; -fx-font-size: 14px; -fx-border-color: #909090; -fx-border-width: 1px;");
            });

            toggleButton.setOnMouseExited(e -> {
                toggleButton.setStyle("-fx-background-color: #e2e2e2; -fx-text-fill: #363636; -fx-font-size: 14px; -fx-border-color: #909090; -fx-border-width: 1px;");
            });
        }
       
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { //toggled
                toggleButton.setOnMouseEntered(e -> {
                    toggleButton.setStyle("-fx-background-color: #426F4E; -fx-text-fill: #eeeeee; -fx-font-size: 14px; -fx-border-color: #909090; -fx-border-width: 1px;");
                });
                toggleButton.setOnMouseExited(e -> {
                    toggleButton.setStyle("-fx-background-color: #548f64; -fx-text-fill: #eeeeee;-fx-font-size: 14px;-fx-border-color: #909090; -fx-border-width: 1px;");
                });
            } 
            
            else { //not toggled
                toggleButton.setOnMouseEntered(e -> {
                    toggleButton.setStyle("-fx-background-color: #cecece; -fx-text-fill: #363636; -fx-font-size: 14px; -fx-border-color: #909090; -fx-border-width: 1px;");
                });
                
                toggleButton.setOnMouseExited(e -> {
                    toggleButton.setStyle("-fx-background-color: #e2e2e2; -fx-text-fill: #363636; -fx-font-size: 14px; -fx-border-color: #909090; -fx-border-width: 1px;");
                });
            }
        });
        toggleButton.setLayoutX(x); 
        toggleButton.setLayoutY(y); 
        toggleButton.setText(name); 
        toggleButton.setId(String.valueOf(id)); 
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }
    
}
