package serveur_ftp.model;

import java.io.File;

public class Client {

    protected boolean user, pass;
    protected File file, fileUser;

    public Client() {
        this.user = false;
        this.pass = false;
        this.file = new File(".").getAbsoluteFile();
        this.fileUser = new File(".").getAbsoluteFile();
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFileUser() {
        return fileUser;
    }

    public void setFileUser(File fileUser) {
        this.fileUser = fileUser;
    }
}