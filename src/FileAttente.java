import java.util.Vector;

public class FileAttente {
    /* Liste des clients dans la queue */

    Vector<Client> Clients;

    /*Constructeur de queue vide par default*/
    public FileAttente() {
        Clients = new Vector<Client>();
    }
}
