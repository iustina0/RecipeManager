/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontendControllers;

import backend.DataBaseController;
import backend.DataBaseManager;
import backend.User;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;

/**
 *
 * @author Iustina
 */
public class IngredientsToggleButtons {
   private  List<ToggleButtonObject> buttons=new ArrayList<>();
    String category;
    int lastX=33, lastY=33;
    IngredientsToggleButtons(String category) throws SQLException, IOException {
        this.category = category;
        String filePath = "D:/an2sem2/PA-java/work-java/proiect_final/RecipeManager/src/CulinaryDB/02_Ingredients.csv";

        List<Integer> userIngredientIds = DataBaseController.getUserIngredientIds();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            nextLine = reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                String name = nextLine[0];
                String entryCategory = nextLine[3].trim();
                int id;

                try {
                    id = Integer.parseInt(nextLine[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing ID: " + nextLine[2]);
                    continue;  // Skip this line and continue with the next one
                }

                boolean validate = false;

                if (this.category == null ? entryCategory == null : this.category.equals(entryCategory)) {
                    validate = true;
                } else {
                    validate = switch (this.category) {
                        case "Other" -> "Flower".equals(entryCategory) || "Maize".equals(entryCategory) || "Bakery".equals(entryCategory) || "Dish".equals(entryCategory);
                        case "Nuts & Cereal" -> "Nuts & Seed".contains(entryCategory) || "Cereal".equals(entryCategory);
                        case "Fungus & Legume" -> "Fungus".equals(entryCategory) || "Legume".equals(entryCategory);
                        case "Hearbs & Spices" -> "Herb".equals(entryCategory) || "Spice".equals(entryCategory);
                        case "Additive & Oil" -> "Essential Oil".contains(entryCategory) || "Additive".equals(entryCategory);
                        case "Beverage" -> "Beverage Alcoholic".contains(entryCategory) || "Beverage".equals(entryCategory);
                        default -> false;
                    };
                }

                if (validate) {
                    int weight = name.length();
                    if (weight <= 5) {
                        weight = weight * 10 + 20;
                    } else {
                        weight = weight * 10 + 6;
                    }

                    boolean haveIngredient = userIngredientIds.contains(id);
                    buttons.add(new ToggleButtonObject(id, lastX, lastY, weight, name, haveIngredient));

                    if (lastX + weight < 750) {
                        lastX += weight + 10;
                    } else {
                        lastY += 40;
                        lastX = 33;
                    }
                }
            }
        } catch (FileNotFoundException e) {
        }
    }

   @SuppressWarnings("NonPublicExported")
    public List<ToggleButtonObject> getButtons() {
        return buttons;
    }
    
}
