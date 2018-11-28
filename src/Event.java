public class Event {

    protected int type; 	//0 correspond à  une arrivee, 1 pour un départ
    protected double date; 	//date ou l'evenement s'est produit

    public Event() {
    	type=0;
    	date=0;
    }
    
    public Event(int type, double date) {
        this.type = type;
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public double getTime() {
        return date;
    }
}
