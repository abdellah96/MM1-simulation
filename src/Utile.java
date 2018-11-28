//Cette classe regroupe les outils statistiques et opération mathématiques servant pour la simulation de la classe Stats
public abstract class Utile {

	protected String adressedufichier;
	
    protected double lambda;
    protected double mu;
    protected double a;
    protected Echeancier liste;
    protected FileAttente q;
    protected double t;
    protected double ProportionClientAvecAttente;
    protected double compteurTempsService;
    protected double compteur;
    protected double nombre_Clients;
    protected double nombre_Clients_Presents;
    protected double nombre_Clients_Presents_Total;
    protected double varianceNbrClient;
    
    protected double Somme_Caree_Clients_Attente;

    public Utile(double lambda, double mu) {
        this.adressedufichier = System.getProperty("user.dir") + "/"+ "fichier1.dat";
    	this.lambda = lambda;
        this.mu = mu;
        this.a = this.lambda / this.mu;
        this.q = new FileAttente();
        this.liste = new Echeancier();
        this.ProportionClientAvecAttente = 0;
        this.compteurTempsService = 0;
        this.compteur = 0;
        this.nombre_Clients = 0;
        this.nombre_Clients_Presents = 0;
        this.nombre_Clients_Presents_Total = 0;
        this.Somme_Caree_Clients_Attente=0;
    }

    public double expo(double taux) {
        return -Math.log(Math.random()) / taux;
    }
    
    public double getNbrClientsAttendus(double simLength) {
        return this.lambda*simLength;
    }
    
    public double getTempsAttenteMoyenPratique() {
        return this.ProportionClientAvecAttente / this.nombre_Clients;
    }

    public double getTempsServiceMoyen() {
        return this.compteurTempsService / this.nombre_Clients;
    }

    public double getNombreTotalClients() {
        return this.nombre_Clients;
    }

    public double getNombreMoyClientsSystemePratique() {
        return this.nombre_Clients_Presents_Total / this.compteur;
    }
    public double getTempsAttenteMoyenTheorique() {
        double tpsMoyen = 1 / (this.mu * (1 - this.a));
        return tpsMoyen;
    }

    public double getNombreTotalClientsSystemeTheorique() {
        double moyenne = this.a / (1 - this.a);
        return moyenne;
    }
    public double getTempsSejourMoyPratique() {
    	return this.compteurTempsService/this.getNombreTotalClients();
    }
    public double getVarianceNbrClienttheo() {
    	return this.a*Math.pow((1-this.a),-2.0);
    }
    //Variance(TempsAttente)=Esperance(TempsAttente^2)-Esperance(TempsAttente)^2
    public double getVarianceTempsAttente() {
    	double varianceAttente=(float)(this.Somme_Caree_Clients_Attente / this.compteur) - Math.pow((this.nombre_Clients_Presents_Total / this.compteur),2.0);
    	return varianceAttente;  
    }
    
  
    
    public abstract void simuler(double simLength,int debogage);
    public abstract void ecrirefichier();

}
