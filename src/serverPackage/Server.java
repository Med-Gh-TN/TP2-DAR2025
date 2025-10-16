package serverPackage;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Le serveur attend une connexion client sur le port 1234...");

            // Le serveur attend une connexion client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Un client s'est connecté depuis : " + clientSocket.getRemoteSocketAddress());

            // Outils pour recevoir et envoyer des chaînes de caractères (CC)
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String operation;
            // Le serveur lit les opérations envoyées par le client en boucle
            // La boucle se termine si le client se déconnecte (in.readLine() renverra null)
            while ((operation = in.readLine()) != null) {
                System.out.println("Opération reçue du client : '" + operation + "'");

                try {
                    // b) Le serveur calcule l'opération demandée
                    String result = calculate(operation);

                    // c) Le serveur envoie le résultat trouvé au client
                    out.println(result);
                    System.out.println("Résultat '" + result + "' envoyé au client.");

                } catch (Exception e) {
                    // En cas d'erreur de calcul (ex: division par zéro), on informe le client
                    String errorMessage = "Erreur lors du calcul : " + e.getMessage();
                    out.println(errorMessage);
                    System.err.println(errorMessage);
                }
            }

            System.out.println("Le client s'est déconnecté. Fermeture de la connexion.");
            clientSocket.close();

        } catch (IOException e) {
            System.err.println("Une erreur est survenue sur le serveur : " + e.getMessage());
        }
    }

    /**
     * Méthode pour parser et calculer le résultat d'une opération sous forme de chaîne.
     * @param operation La chaîne de caractères (ex: "55 * 25")
     * @return Le résultat du calcul sous forme de chaîne.
     */
    private static String calculate(String operation) {
        // On utilise split avec une expression régulière "\\s+" pour séparer par un ou plusieurs espaces
        String[] parts = operation.trim().split("\\s+");

        // On s'attend à avoir 3 parties : [opérande1, opérateur, opérande2]
        if (parts.length != 3) {
            throw new IllegalArgumentException("Format de l'opération invalide.");
        }

        double operand1 = Double.parseDouble(parts[0]);
        String operator = parts[1];
        double operand2 = Double.parseDouble(parts[2]);

        double result;

        switch (operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Division par zéro impossible.");
                }
                result = operand1 / operand2;
                break;
            default:
                throw new IllegalArgumentException("Opérateur non supporté : " + operator);
        }

        // On retourne le résultat sous forme de String
        return String.valueOf(result);
    }
}