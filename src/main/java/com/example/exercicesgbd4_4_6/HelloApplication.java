package com.example.exercicesgbd4_4_6;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private TextField displayField = new TextField();
    private String currentInput = "";
    private double firstOperand = 0;
    private String operator = "";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Calculatrice_Exo_4.4.6");

        // Configuration du champ d'affichage
        displayField.setEditable(false);
        displayField.setStyle("-fx-font-size: 20px;");

        // Pavé numérique et opérateurs
        GridPane gridPane = new GridPane();
        String[][] buttons = {
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"0", "00", ".", "+"}
        };

        // Ajouter les boutons à la grille
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                String text = buttons[i][j];
                if (!text.isEmpty()) {
                    Button button = new Button(text);
                    button.setMinSize(50, 50);
                    button.setStyle("-fx-font-size: 20px;");
                    button.setOnAction(e -> handleButtonClick(text));
                    gridPane.add(button, j, i);
                }
            }
        }

        // Bouton Calculer
        Button calculateButton = new Button("Calculer");
        calculateButton.setMinSize(200, 50);
        calculateButton.setStyle("-fx-font-size: 20px;");
        calculateButton.setOnAction(e -> calculateResult());

        // Configuration de la scène
        VBox vBox = new VBox(10, displayField, gridPane, calculateButton);
        Scene scene = new Scene(vBox, 300, 400);

        // Définir la taille de la fenêtre
        stage.setWidth(220);
        stage.setHeight(360);
        stage.setResizable(false);

        // Gérer les entrées au clavier
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);

        stage.setScene(scene);
        stage.show();
    }

    // Gestion des clics de bouton
    private void handleButtonClick(String text) {
        switch (text) {
            case "+":
            case "-":
            case "*":
            case "/":
                setOperator(text);
                break;
            case ".":
                appendNumber(".");
                break;
            case "00":
                appendNumber("00");
                break;
            default:
                appendNumber(text);
                break;
        }
    }

    // Ajouter un chiffre ou un point au champ actuel
    private void appendNumber(String number) {
        currentInput += number;
        displayField.setText(currentInput);
    }

    // Enregistrer l'opérateur et le premier opérande
    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            firstOperand = Double.parseDouble(currentInput);
            operator = op;
            currentInput = "";
        }
    }

    // Calculer le résultat
    private void calculateResult() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        displayField.setText("Erreur : Division par zéro");
                        return;
                    }
                    break;
            }

            displayField.setText(String.valueOf(result));
            currentInput = "";
            operator = "";
        }
    }

    // Gestion des touches du clavier
    private void handleKeyPress(KeyEvent event) {
        String keyText = event.getText();
        switch (keyText) {
            case "+":
            case "-":
            case "*":
            case "/":
                setOperator(keyText);
                break;
            case ".":
                appendNumber(".");
                break;
            default:
                if (keyText.matches("[0-9]")) {
                    appendNumber(keyText);
                }
                break;
        }
        if (event.getCode().toString().equals("ENTER")) {
            calculateResult();
        }
        if (event.getCode().toString().equals("BACK_SPACE") && currentInput.length() > 0) {
            // Supprimer le dernier caractère
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            displayField.setText(currentInput);
        }
    }
}
