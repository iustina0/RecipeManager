package backend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDBController {
    private static final String HTML_TEMPLATE_PATH ="D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\src\\graphicStyle\\template.html";
    private static final String OUTPUT_FILE_PATH = "output.html";

    public static void generateHTMLReport(List<Recipe> recipes) {
        try (BufferedReader reader = new BufferedReader(new FileReader(HTML_TEMPLATE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH))) {

            StringBuilder templateBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                templateBuilder.append(line);
            }
            String htmlTemplate = templateBuilder.toString();

            StringBuilder reportBuilder = new StringBuilder();
            for (Recipe recipe : recipes) {
                String modifiedTemplate = htmlTemplate
                        .replace("{{RECIPE_NAME}}", recipe.getName())
                        .replace("{{RECIPE_IMAGE_URL}}", recipe.getImageUrl())
                        .replace("{{RECIPE_SOURCE}}", recipe.getSource())
                        .replace("{{RECIPE_SOURCE_WEBISTE}}", recipe.getUrl())
                        .replace("{{RECIPE_CUISINE}}", recipe.getCuisine());


                reportBuilder.append(modifiedTemplate);
            }

            writer.write(reportBuilder.toString());

            System.out.println("HTML report generated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Recipe> recipeList=new ArrayList<>();
        recipeList.add(new Recipe(1));
        recipeList.add(new Recipe(12));
        recipeList.add(new Recipe(3));
        generateHTMLReport(recipeList);
    }
}