public class Client {

    protected double tempsArrivee; //Le temps d'arrivée d'un client
    protected double dureeService; //La duree du service 

    public Client(double tempsArrivee, double dureeService) {
        this.tempsArrivee = tempsArrivee;
        this.dureeService = dureeService;
    }
    
    public double getArrivalTime() {
        return this.tempsArrivee;
    }

    public double getServiceTime() {
        return this.dureeService;
    }
}
