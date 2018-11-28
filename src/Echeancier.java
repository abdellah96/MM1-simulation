import java.util.Vector;

public class Echeancier {
    // cette classe modélise la liste des évenements futurs 

    Vector<Event> events;

    public Echeancier() {
        events = new Vector<Event>();
    }
    
    public void addEvent(Event newEvent) {
        int insertIndex = 0; //sert Ã  repérer l'enndroit ou l'evenement newEvent doit être insérer 
        //on compare l'instant de newEvent avec l'instant des autres evenements déjà  dans la liste
        while (insertIndex < events.size()) {
            Event e = (Event) events.elementAt(insertIndex); //on extrait le insertIndex
            if (e.getTime() > newEvent.getTime()) {
                break; //si l'instant de newEvent est le plus proche,
            }
//on sort de la boucle
            insertIndex++; //sinon on incrémente insertIndex, et on reboucle
        }
        events.insertElementAt(newEvent, insertIndex); //on insert l'évenement newEvent au bon endroit
    }
}
