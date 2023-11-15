package serveur_ftp.commande;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {

    public CommandeGET(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute() {

        File src = new File(CommandExecutor.MAP.get(id).getFile().getPath(), commandeArgs[0]);

        if (src.isFile()) {
            ps.println("1 Le fichier existe et prêt pour l'envoi");
            ps.println("0 Sur le port : " + CommandExecutor.portGet);

            File dest = new File(System.getProperty("user.dir") + "\\Client\\ficDownloaded\\" + commandeArgs[0]); //create a empty file

            try {
                ServerSocket serverGet = new ServerSocket(CommandExecutor.portGet);
                Socket sockGet = serverGet.accept();  //we wait for the client

                BufferedReader reader = new BufferedReader(new FileReader(src));
                FileWriter writer = new FileWriter(dest);

                String str;
                while ((str = reader.readLine()) != null) {
                    writer.write(str);
                    writer.write("\n");
                    writer.flush();
                }
                ps.println("0 Fichier téléchargé ");

                writer.close();
                reader.close();
                sockGet.close();
                serverGet.close();

                CommandExecutor.portGet += 1;

            } catch (IOException e) {
                e.printStackTrace();
                ps.println("2 " + e.getMessage());
            }

        } else {
            ps.println("2 Le fichier " + commandeArgs[0] + " n'existe pas");
        }
    }

}
