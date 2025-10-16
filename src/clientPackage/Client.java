package clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234)) {
            System.out.println("Connecté au serveur. Entrez une opération (ex: 55 * 25) ou 'exit' pour quitter.");

            // Outils pour envoyer et recevoir des chaînes de caractères (CC)
            // OutputStream -> OutputStreamWriter -> PrintWriter (pour envoyer du texte facilement)
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // InputStream -> InputStreamReader -> BufferedReader (pour lire du texte facilement)
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Outil pour lire l'entrée de l'utilisateur au clavier
            Scanner scanner = new Scanner(System.in);
            String userInput;

            while (true) {
                System.out.print("Opération > ");
                userInput = scanner.nextLine();

                // Si l'utilisateur tape "exit", on quitte la boucle
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                // NB: Validation de la syntaxe CÔTÉ CLIENT avant l'envoi
                // Regex: un ou plusieurs chiffres, espace(s), un opérateur, espace(s), un ou plusieurs chiffres
                if (!userInput.matches("^\\d+\\s*[+\\-*/]\\s*\\d+$")) {
                    System.out.println("Erreur: Format de l'opération invalide. Exemples valides : 4 * 5, 100+2, 9 / 3");
                    continue; // On redemande une nouvelle opération
                }

                // a) Le client envoie l'opération entière sous forme de chaîne de caractères
                out.println(userInput);
                System.out.println("Opération '" + userInput + "' envoyée au serveur.");

                // d) Le client lit et affiche le résultat reçu
                String serverResponse = in.readLine();
                System.out.println("Réponse du serveur : " + serverResponse);
            }

            System.out.println("Déconnexion du client.");
            scanner.close();

        } catch (IOException e) {
            System.err.println("Erreur de connexion ou de communication avec le serveur : " + e.getMessage());
        }
    }
}