import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Stats extends Utile {

    public Stats(double lambda, double mu) {
        super(lambda, mu);
    }
    
    public void simuler(double simLength,int debogage) {
        double beginTime = System.currentTimeMillis();
        //La date courante
        double time = 0;
        //la date de l'evenement precedant
        double timeavant=0;
        //on crée le premier evenement a la date 0 avec une arrivée comme hypothese de l'enonce
        Event s1 = new Event(0,0);
        //on ajoute cet evenement dans le vecteur liste qui represente l'echeancier
        this.liste.addEvent(s1);
        //on initialiser notre compteur 
        this.compteur = 0;
        //on crée la variable de depart qui stocke la variable de depart pour chaque client  
        double tempsdepart=0;
        //hypothese:la duree de simulation n'est pas infinie 
        while (time < simLength) {
            this.compteur++;
            //on prend la premier valeur de notre vecteur et on le stocke apres on le supprime de notre echeancier
            Event next_Event = (Event) this.liste.events.get(0);
            this.liste.events.remove(0);
            //le temps d'avant devient le courant
            timeavant=time;
            //le temps courant est le temps de l'evenement prochain
            time = next_Event.getTime();
            //on traite les temps de service en créant des queues qui stocke les client avec la structure de vecteur 
            if (next_Event.getType() == 1) {
            	//on prend le premier client de notre vecteur (premier entré premier servie) et on le supprime
            	Client c = (Client) this.q.Clients.get(0);
                this.q.Clients.remove(0);
                //le nombre de client est incrementé
                this.nombre_Clients++;
                //le nombre de client present est decrements  puisqu'il s'agit d'un depart
                this.nombre_Clients_Presents--;
                //on incremente le nombre totale par le nombre de clients presents dans la queue             
                this.nombre_Clients_Presents_Total += this.nombre_Clients_Presents;
                //ca sert pour la variance
                this.Somme_Caree_Clients_Attente += Math.pow(this.nombre_Clients_Presents,2.0);
                //le calcul de temps de service = tems courant(depart) - temps arrivée
                this.compteurTempsService += time - c.getArrivalTime();
               	
                
                
                //on voit s'il y'a des clients dans la file 
                if (this.q.Clients.size() > 0) {
                	//il y'a encore d'attente vue que la queue n'est pas vide
                	this.ProportionClientAvecAttente++;
                    Client client = (Client) this.q.Clients.get(0);
                    double serviceTime = client.getServiceTime();
                    this.liste.addEvent(new Event(1, time + serviceTime));
                    
                    if(debogage==1)
                    	System.out.println("Date="+(float)(time+serviceTime)+"\t\t\t Depart client#"+(int)this.nombre_Clients+"\t\t\t arrive a t="+client.getArrivalTime());
                    
                	}  		
                }
            //on traite le cas des interarrivée de la même facon
             else {

            	this.liste.addEvent(new Event(0, time + expo(this.lambda)));
                if(debogage==1) {
	                if(time >= timeavant ) {
	                	System.out.println("Date="+time+"\t\t arrivee client#"+(int)(this.nombre_Clients+this.nombre_Clients_Presents));
	                }
	                else {
	                	System.out.println("Date="+time+"\t\t arrivee client#"+(int)(this.nombre_Clients));
	                }
                }    
               
                this.nombre_Clients_Presents++;
                this.nombre_Clients_Presents_Total += this.nombre_Clients_Presents;
                this.Somme_Caree_Clients_Attente += Math.pow(this.nombre_Clients_Presents,2.0);
                
                double serviceTime = expo(this.mu);                 
                this.q.Clients.addElement(new Client(time, serviceTime));
                if (this.q.Clients.size() == 1) {
                	tempsdepart=time+serviceTime; 
                    this.liste.addEvent(new Event(1, tempsdepart));
                    if(debogage==1)
                    	System.out.println("Date="+(float)tempsdepart+"\t\t\t Depart client#"+(int)(this.nombre_Clients)+"\t\t arrive a t="+time);
                }
             }
        } 
        
        System.out.println("\n\n\t\tTemps de calcul des resultats théoriques et pratiques: " + (System.currentTimeMillis() - beginTime) * 0.001 + " secondes\n");
        System.out.println("\n==========================RESULTATS THEORIQUES================================");
        if(this.lambda<this.mu) {
        	System.out.println("lambda<mu: file stable");
        }
        else {
        	System.out.println("lambda<mu: file instable");
        }
        
        System.out.println("Temps d'attente moyen ro=lambda/mu : " + this.a);
        System.out.println("nombre de clients attendus (lambda x duree) :" + this.getNbrClientsAttendus(simLength));
        System.out.println("Prob de service sans attente (1 - ro) : "+ (1-this.a));
        System.out.println("Prob file occupee : " + this.a);
        System.out.println("Debit(lambda) : " + this.lambda);
        System.out.println("Nombre de clients moyen (Esperance) dans le systeme : " + this.getNombreTotalClientsSystemeTheorique());
        System.out.println("temps moyen de sejour  : " + this.getTempsAttenteMoyenTheorique());
        System.out.println("Variance du nombre de client dans la file d'attente: "+ this.getVarianceNbrClienttheo());
        double erreur_confiance=1.96*Math.sqrt(this.getVarianceNbrClienttheo()/(this.getNbrClientsAttendus(simLength)));
        System.out.println("Intervalle de confiance du nombre moyen de clients dans le systeme : " + "["+(float)(this.getNombreTotalClientsSystemeTheorique()-erreur_confiance)+" : "+(float)(this.getNombreTotalClientsSystemeTheorique()+erreur_confiance)+"]");

        System.out.println("\n==========================RESULTATS PRATIQUES================================");
        System.out.println("Nombre de clients servis pendant toute la simulation: " + this.getNombreTotalClients());
        System.out.println("Proportion clients avec attente : " + this.getTempsAttenteMoyenPratique());
        System.out.println("Proportion clients sans attente : " + (1-this.getTempsAttenteMoyenPratique()));
        System.out.println("Debit pratique : " + this.getNombreTotalClients()/simLength);
        System.out.println("Nombre de clients moyen dans le systeme (Esperance) : " + this.getNombreMoyClientsSystemePratique());
        System.out.println("Temps moyen de séjour : " + this.getTempsSejourMoyPratique());
        System.out.println("Variance du temps d'attente dans le systeme : " + this.getVarianceTempsAttente());
        
       
        
        
    }
    
    public void ecrirefichier() {	
		try
		{
			
			FileWriter fw = new FileWriter(this.adressedufichier, true);
			BufferedWriter output = new BufferedWriter(fw);
			output.write(this.getNombreMoyClientsSystemePratique()+"\r\n");
			output.flush();
			output.close();
			System.out.println("fichier créé");
		}
		catch(IOException ioe){
			System.out.print("Erreur : ");
			ioe.printStackTrace();
			}
    }
}
