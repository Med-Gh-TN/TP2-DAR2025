package model; // Or any package name you prefer

import java.io.Serializable;

/**
 * Cette classe représente une opération mathématique.
 * Elle implémente l'interface Serializable pour permettre sa transmission
 * via les flux d'objets (ObjectInputStream / ObjectOutputStream).
 */
public class Operation implements Serializable {

    // Recommandé pour la sérialisation afin d'assurer la compatibilité des versions
    private static final long serialVersionUID = 1L;

    private double operand1;
    private double operand2;
    private char operator;
    private double result; // Ajout d'un champ pour le résultat

    public Operation(double operand1, double operand2, char operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
        this.result = 0; // Initialisé à 0 par défaut
    }

    // Getters pour que le serveur puisse lire les données
    public double getOperand1() {
        return operand1;
    }

    public double getOperand2() {
        return operand2;
    }

    public char getOperator() {
        return operator;
    }

    public double getResult() {
        return result;
    }

    // Setter pour que le serveur puisse stocker le résultat dans l'objet
    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return operand1 + " " + operator + " " + operand2;
    }
}