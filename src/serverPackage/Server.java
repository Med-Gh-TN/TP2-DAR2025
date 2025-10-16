package serverPackage;

import model.Operation; // Import de notre classe
import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Le serveur attend une connexion client sur le port 1234...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Un client s'est connecté depuis : " + clientSocket.getRemoteSocketAddress());

            // Outils pour envoyer et recevoir des OBJETS
            // IMPORTANT: L'OutputStream doit être créé AVANT l'InputStream pour éviter un blocage
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            try {
                // Le serveur est en attente de recevoir un objet
                while (true) {
                    // c) Le serveur reçoit l'objet
                    Operation op = (Operation) ois.readObject();
                    System.out.println("Reçu l'objet : " + op);

                    // d) Le serveur récupère les données et e) effectue le calcul
                    calculate(op);

                    // f) Le serveur renvoie l'objet modifié (avec le résultat) au client
                    oos.writeObject(op);
                    System.out.println("Envoyé l'objet avec résultat : " + op.getResult());
                }
            } catch (EOFException e) {
                System.out.println("Le client a fermé la connexion.");
            } catch (ClassNotFoundException e) {
                System.err.println("Classe de l'objet non trouvée : " + e.getMessage());
            }

            clientSocket.close();

        } catch (IOException e) {
            System.err.println("Une erreur est survenue sur le serveur : " + e.getMessage());
        }
    }

    /**
     * Calcule le résultat à partir d'un objet Operation et met à jour le champ résultat de l'objet.
     * @param op L'objet Operation contenant les données à calculer.
     */
    private static void calculate(Operation op) {
        double result;
        switch (op.getOperator()) {
            case '+':
                result = op.getOperand1() + op.getOperand2();
                break;
            case '-':
                result = op.getOperand1() - op.getOperand2();
                break;
            case '*':
                result = op.getOperand1() * op.getOperand2();
                break;
            case '/':
                if (op.getOperand2() == 0) {
                    // On pourrait lancer une exception, mais ici on met un résultat spécial
                    result = Double.NaN; // "Not a Number" pour représenter une erreur
                } else {
                    result = op.getOperand1() / op.getOperand2();
                }
                break;
            default:
                result = Double.NaN; // Opérateur invalide
                break;
        }
        op.setResult(result); // Met à jour l'objet directement
    }
}