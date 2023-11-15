package serveur_ftp.commande;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeCD extends Commande {

    public CommandeCD(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute() {
        try {
            File f = new File(CommandExecutor.MAP.get(id).getFile().getCanonicalPath(), commandeArgs[0]);

            if (f.isDirectory()) {
                if (commandeArgs[0].equals("..") && !CommandExecutor.MAP.get(id).getFileUser().equals(CommandExecutor.MAP.get(id).getFile())) {
                    CommandExecutor.MAP.get(id).setFile(new File(CommandExecutor.MAP.get(id).getFile().getParent()));
                } else if (!commandeArgs[0].equals("..")) CommandExecutor.MAP.get(id).setFile(new File(CommandExecutor.MAP.get(id).getFile().getCanonicalPath(), commandeArgs[0]));
            }
            ps.println("0 Vous etes désormais ici : " + CommandExecutor.MAP.get(id).getFile().getAbsoluteFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
            ps.println("2 Le dossier n'existe pas !");
        }
    }

}
