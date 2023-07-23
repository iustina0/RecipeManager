CREATE TABLE Recipes(
    id NUMBER CONSTRAINT unique_recipe_id PRIMARY KEY NOT NULL,
    name VARCHAR2(50)CONSTRAINT unique_recipe_name UNIQUE NOT NULL,
    time INTERVAL DAY TO SECOND,
    difficulty NUMBER
);

CREATE TABLE Users(
    id NUMBER CONSTRAINT unique_user_id PRIMARY KEY NOT NULL,
    username VARCHAR(255)  CONSTRAINT unique_user_name UNIQUE NOT NULL,
    email VARCHAR(255),
    password_hash VARCHAR(250)
);
CREATE TABLE Ingredients(
    id NUMBER CONSTRAINT unique_ingredient_id PRIMARY KEY NOT NULL,
    name VARCHAR2(50) CONSTRAINT unique_ingredient_name UNIQUE NOT NULL,
    category VARCHAR2(50)
);

CREATE TABLE RecipeIngredient (
    id_recipe NUMBER,
    id_ingredient NUMBER,
    quantity NUMBER,
    quantity_unit VARCHAR2(50),
    CONSTRAINT pk_recipeingredient PRIMARY KEY (id_recipe, id_ingredient),
    CONSTRAINT fk_recipeingredient_recipe FOREIGN KEY (id_recipe) REFERENCES Recipes (id),
    CONSTRAINT fk_recipeingredient_ingredient FOREIGN KEY (id_ingredient) REFERENCES Ingredients (id)
);
CREATE TABLE UserIngredient (
    id_user NUMBER,
    id_ingredient NUMBER,
    CONSTRAINT pk_useringredient PRIMARY KEY (id_user, id_ingredient),
    CONSTRAINT fk_useringredient_user FOREIGN KEY (id_user) REFERENCES Users (id)
);
CREATE TABLE recipeImages (
  recipe_id INT,
  filePath VARCHAR2(250),
  CONSTRAINT pk_recipeImages PRIMARY KEY (recipe_id, filePath)
);

CREATE TABLE UserRecipeRating (
    user_id INT,
    recipe_id INT,
    rating INT,
    CONSTRAINT pk_UserRecipeRating PRIMARY KEY (user_id, recipe_id),
    CONSTRAINT fk_UserRecipeRating_user FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE RecipeRating (
    recipe_id INT,
    rating NUMBER(4,2),
    CONSTRAINT pk_RecipeRating PRIMARY KEY (recipe_id)
);

