/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import com.opencsv.CSVReader;
import static frontendControllers.RecipePageController.getCuisineFromId;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Iustina
 */
public class Recipe {
    int id;
    String name;
    String imageUrl;
    String source;
    String cuisine;
    String url;
    
    public Recipe(int id){
        this.id=id; 
        this.name="";
        this.source="";
        this.cuisine="";
        String csvFilePath = "D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\src\\CulinaryDB\\01_Recipe_Details.csv"; 

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            boolean headerSkipped = false;

            while ((nextLine = reader.readNext()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                int readid = Integer.parseInt(nextLine[0]);

                if (readid == id) {
                    this.name = nextLine[1].trim();
                    this.source = nextLine[2].trim();
                    this.cuisine = nextLine[3].trim();
                    switch (this.source) {
                        case "EPICURIOUS" -> url = "https://www.epicurious.com/";
                        case "TARLA_DALAL" -> url = "https://www.tarladalal.com/";
                        case "FOOD_NETWORK" -> url = "https://foodnetwork.co.uk/";
                        case "ALLRECIPES" -> url = "https://www.allrecipes.com/";
                        default -> {
                            url = "https://www.example.com";
                            System.out.println("Invalid source selected");
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
        }
        if(RecipeListController.getFirstImagePath(this.id).isEmpty()){
            this.imageUrl="D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\resources\\images\\Papirus-Team-Papirus-Status-Image-missing.512.png";
        }else{
            this.imageUrl=RecipeListController.getFirstImagePath(this.id);
        }
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
    
}
