package serveur_ftp.commande;

import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {

    public CommandeLS(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute() {
        File[] files = CommandExecutor.MAP.get(id).getFile().listFiles();
        assert files != null;
        for (File f : files) ps.println("1 " + f.getName());
        ps.println("0 Voici tous les fichiers présents de " + CommandExecutor.MAP.get(id).getFile().getAbsoluteFile().toString());
    }

}
