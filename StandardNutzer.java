package Verarbeitung;

import VerarbeitungInterfaces.StandardNutzerIF;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <strong/>Zweck:</strong> StandardNutzer-Objekten können im Gegensatz zu Objekten der Mutterklasse "Nutzer" Reservierungen zugeorndet werden. Diese werden als Liste gespeichert.
 * <p><strong>Änderungshistorie:</strong> </p>
 * @version 2.3
 * @author Alexander Reichenbach
 *
 */

public class StandardNutzer extends Nutzer implements VerarbeitungInterfaces.StandardNutzerIF, Reservierer, Serializable {

    private ArrayList<Reservierung> reservierungen = new ArrayList<Reservierung>();

    public StandardNutzer (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
        super (name, password, sicherheitsFrage, sicherheitsAntwort);
    }

    public ArrayList<Reservierung> getReservierungen() {
        return reservierungen;
    }

    public boolean addReservierung (Reservierung r) {
        if (!reservierungen.contains(r)) {
            GlobaleMethoden.addReservierungtoArrayList(reservierungen, r);
            return true;
        } else {
            return false;
        }
    }

    public void removeReservierung (Reservierung weg) {
    	reservierungen.remove(weg);
    }
    
    public void setReservierungen(ArrayList<Reservierung> reservierungen) {
        this.reservierungen = reservierungen;
    }

    public boolean istFrei(Zeitraum zr) {
        if (findeKollision(zr)==null) return true;
        else return false;
    }

    public Reservierung findeKollision (Zeitraum zr) {
        return GlobaleMethoden.findeKollisioninArrayList(reservierungen, zr);
    }

    /*
    // Serializable-Implementierung

    private void writeObject(ObjectOutputStream oos) throws IOException {
        // default serialization
        oos.defaultWriteObject();
        // write the object
        //Reservierung[] resSave = new Reservierung [reservierungen.size()];
        //reservierungen.toArray(resSave);
        //oos.writeObject(resSave);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();
        // read the objects
        //Reservierung[] resLoad = (Reservierung[])ois.readObject();
        //reservierungen = new ArrayList<Reservierung> (Arrays.asList(resLoad));
    }
    */
}