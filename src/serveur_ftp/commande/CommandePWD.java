package serveur_ftp.commande;

import java.io.PrintStream;

public class CommandePWD extends Commande {

    public CommandePWD(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute() {
        String s = CommandExecutor.MAP.get(id).getFile().getAbsoluteFile().toString();
        ps.println("0 " + s);
    }

}
