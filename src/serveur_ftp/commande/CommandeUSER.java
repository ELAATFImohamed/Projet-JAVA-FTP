package serveur_ftp.commande;

import java.io.File;
import java.io.PrintStream;

public class CommandeUSER extends Commande {

    public CommandeUSER(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute() {
        File file = new File(CommandExecutor.file, "database");

        if(!CommandExecutor.MAP.get(id).getFile().equals(file)){
            CommandExecutor.MAP.get(id).setFile(new File(CommandExecutor.MAP.get(id).getFile().getParent(), "database"));
        }

        File[] files = CommandExecutor.MAP.get(id).getFile().listFiles();
        assert files != null;
        for (File f : files) {
            if (f.getName().equalsIgnoreCase(commandeArgs[0])) {
                CommandExecutor.MAP.get(id).setUser(true);
                CommandExecutor.MAP.get(id).setFile(new File(CommandExecutor.MAP.get(id).getFile().getAbsoluteFile(), commandeArgs[0]));
                CommandExecutor.MAP.get(id).setFileUser(new File(String.valueOf(CommandExecutor.MAP.get(id).getFile().getAbsoluteFile())));
                ps.println("0 Commande user OK");
            }
        }
        if (!CommandExecutor.MAP.get(id).isUser()) ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
    }

}
