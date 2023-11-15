package serveur_ftp.commande;

import serveur_ftp.model.Client;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;

public class CommandExecutor {

    public static HashMap<Integer, Client> MAP = new HashMap<>();
    public static File file = new File(".").getAbsoluteFile();
    public static int portGet = 5000;
    public static int portStor = 4000;

    public static void executeCommande(PrintStream ps, String commande) {
        String[] vars = commande.split(" ");
        int id = Integer.parseInt(vars[vars.length - 1]);

        if (MAP.get(id).isUser() && MAP.get(id).isPass()) {

            switch (vars[0]) {
                // Changer de repertoire. Un (..) permet de revenir au repertoire superieur
                case "cd":
                    (new CommandeCD(ps, commande)).execute();
                    break;
                // Telecharger un fichier
                case "get":
                    (new CommandeGET(ps, commande)).execute();
                    break;
                // Afficher la liste des fichiers et des dossiers du repertoire courant
                case "ls":
                    (new CommandeLS(ps, commande)).execute();
                    break;
                // Afficher le repertoire courant
                case "pwd":
                    (new CommandePWD(ps, commande)).execute();
                    break;
                // Envoyer (uploader) un fichier
                case "stor":
                    (new CommandeSTOR(ps, commande)).execute();
                    break;
                default:
                    ps.println("2 Commande inexistante");
                    break;
            }
        } else {
            if (vars[0].equals("pass") || vars[0].equals("user")) {
                // Le mot de passe pour l'authentification
                if (vars[0].equals("pass")) (new CommandePASS(ps, commande)).execute();

                // Le login pour l'authentification
                if (vars[0].equals("user")) (new CommandeUSER(ps, commande)).execute();
            } else ps.println("2 Vous n'êtes pas connecté !");
        }
    }

}
