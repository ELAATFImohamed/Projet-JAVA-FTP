package serveur_ftp.commande;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeSTOR extends Commande {

    public CommandeSTOR(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute() {
        File src = new File(System.getProperty("user.dir") + "\\Client\\ficToUpload\\" + commandeArgs[0]);

        if (src.isFile()) {
            ps.println("1 Le fichier existe et prêt pour l'envoi");
            ps.println("0 Sur le port : " + CommandExecutor.portStor);

            File dest = new File(CommandExecutor.MAP.get(id).getFile(), commandeArgs[0]);

            try {
                ServerSocket serverStor = new ServerSocket(CommandExecutor.portStor);
                Socket sockStor = serverStor.accept();  //we wait for the client

                BufferedReader reader = new BufferedReader(new FileReader(src));
                FileWriter writer = new FileWriter(dest);

                String str;
                while ((str = reader.readLine()) != null) {
                    writer.write(str);
                    writer.write("\n");
                    writer.flush();
                }

                ps.println("0 Fichier uploadé ");

                writer.close();
                reader.close();
                sockStor.close();
                serverStor.close();

                CommandExecutor.portStor += 1;

            } catch (IOException e) {
                e.printStackTrace();
                ps.println("2 " + e.getMessage());
            }

        } else {
            ps.println("2 Le fichier " + commandeArgs[0] + " est introuvable");
        }
    }

}
