package serveur_ftp.commande;

import java.io.IOException;
import java.io.PrintStream;

public abstract class Commande {

    protected PrintStream ps;
    protected String commandeNom;
    protected String[] commandeArgs;
    protected int id;

    public Commande(PrintStream ps, String commandeStr) {
        this.ps = ps;
        String[] args = commandeStr.split(" ");
        commandeNom = args[0];
        commandeArgs = new String[args.length - 1];

        System.arraycopy(args, 1, commandeArgs, 0, commandeArgs.length);
        id = Integer.parseInt(commandeArgs[commandeArgs.length - 1]);
    }

    public abstract void execute() throws IOException;

}
