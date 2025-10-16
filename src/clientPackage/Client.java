package clientPackage;

import model.Operation; // Import de notre classe
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234)) {
            System.out.println("Connecté au serveur. Entrez une opération (ex: 55 * 25) ou 'exit' pour quitter.");

            // Outils pour envoyer et recevoir des OBJETS
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            String userInput;

            while (true) {
                System.out.print("Opération > ");
                userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                // Valider la syntaxe avant de créer l'objet
                if (!userInput.matches("^\\d+(\\.\\d+)?\\s*[+\\-*/]\\s*\\d+(\\.\\d+)?$")) {
                    System.out.println("Erreur: Format invalide. Ex: 4 * 5.2");
                    continue;
                }

                try {
                    // Parser l'entrée utilisateur pour créer un objet Operation
                    String[] parts = userInput.trim().split("\\s+");
                    double op1 = Double.parseDouble(parts[0]);
                    char operator = parts[1].charAt(0);
                    double op2 = Double.parseDouble(parts[2]);

                    Operation operationToSend = new Operation(op1, op2, operator);

                    // b) Le client envoie l'objet au serveur
                    oos.writeObject(operationToSend);
                    System.out.println("Objet '" + operationToSend + "' envoyé au serveur.");

                    // g) Le client lit l'objet modifié et affiche le résultat
                    Operation resultOperation = (Operation) ois.readObject();

                    // Afficher le résultat contenu dans l'objet reçu
                    System.out.println("Réponse du serveur : " + resultOperation.getResult());

                } catch (Exception e) {
                    System.err.println("Erreur lors de la création ou l'envoi de l'objet : " + e.getMessage());
                }
            }

            System.out.println("Déconnexion du client.");
            scanner.close();

        } catch (IOException e) {
            System.err.println("Erreur de connexion ou de communication : " + e.getMessage());
        }
    }
}