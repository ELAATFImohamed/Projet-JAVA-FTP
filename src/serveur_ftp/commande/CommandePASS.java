package serveur_ftp.commande;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandePASS extends Commande {

    public CommandePASS(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute() {
        if(CommandExecutor.MAP.get(id).isUser()){
            File path = new File(CommandExecutor.MAP.get(id).getFile(), "pwd.txt");
            String password = path.toString();

            try (Stream<String> lines = Files.lines(Paths.get(password))) {
                String mdp = lines.collect(Collectors.joining(System.lineSeparator()));

                if (mdp.equals(commandeArgs[0])) {
                    CommandExecutor.MAP.get(id).setPass(true);
                    ps.println("1 Commande pass OK");
                    ps.println("0 Vous êtes bien connecté sur notre serveur");
                } else ps.println("2 Le mot de passe est faux");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }else{
            ps.println("2 Rentrez d'abord votre pseudonyme");
        }
    }
}
