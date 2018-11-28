import java.util.Vector;

public class Echeancier {
    // cette classe mod�lise la liste des �venements futurs 

    Vector<Event> events;

    public Echeancier() {
        events = new Vector<Event>();
    }
    
    public void addEvent(Event newEvent) {
        int insertIndex = 0; //sert à rep�rer l'enndroit ou l'evenement newEvent doit �tre ins�rer 
        //on compare l'instant de newEvent avec l'instant des autres evenements d�j� dans la liste
        while (insertIndex < events.size()) {
            Event e = (Event) events.elementAt(insertIndex); //on extrait le insertIndex
            if (e.getTime() > newEvent.getTime()) {
                break; //si l'instant de newEvent est le plus proche,
            }
//on sort de la boucle
            insertIndex++; //sinon on incr�mente insertIndex, et on reboucle
        }
        events.insertElementAt(newEvent, insertIndex); //on insert l'�venement newEvent au bon endroit
    }
}
