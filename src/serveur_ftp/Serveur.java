package serveur_ftp;/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import serveur_ftp.commande.CommandExecutor;
import serveur_ftp.model.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur implements Runnable {

    BufferedReader br;
    PrintStream ps;
    Socket s;

    Serveur(Socket sock) {
        this.s = sock;
    }

    public void run() {

        try {
            br = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            ps = new PrintStream(this.s.getOutputStream());

            CommandExecutor.MAP.put(CommandExecutor.MAP.size(), new Client());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Le Serveur FTP");

        ps.println("1 Bienvenue ! ");
        ps.println("1 Serveur FTP Personnel.");
        ps.println("0 Authentification : ");
        ps.println(CommandExecutor.MAP.size() - 1);

        String commande;

        try {
            // Attente de reception de commandes et leur execution
            while (!(commande = br.readLine()).startsWith("bye")) {
                System.out.println(">> " + commande);
                CommandExecutor.executeCommande(ps, commande);
            }
            ps.println("0 See you next time !");
            System.out.println("Client parti !");

        } catch (Exception e) {
            System.out.println("Client déconnecté !");
        } finally {
            try {
                br.close();
                ps.close();
                this.s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket serveurFTP = new ServerSocket(2124);
        Socket socket;
        int count = 0;

        try {
            while (true) {
                socket = serveurFTP.accept();
                Serveur client = new Serveur(socket);
                System.out.println("ID N°" + count);
                count++;
                new Thread(client).start();
            }
        } catch (Exception e) {
            System.out.println("probleme de connexion");
        }
    }
}
