package Verarbeitung;

import VerarbeitungInterfaces.RaumIF;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <strong> Zweck: </strong> Ermoeglicht die Erstellung von Raum-Objekten, denen ein Name, eine bestimmte Ausstattung,
 * sowie Informationen ueber Buchbarkeit und Belegung zugeordnet sind. 
 * <h2>Aenderungshistorie:</h2>
 * 
 * <ol>
 * 	<li>
 * 		<ul>
 * 			<li> <strong> Version: </strong> 1.0 </li>
 *			<li> <strong> Datum: </strong> 25.06.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Initiale Befuellung </li>
 *		 </ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.1 </li>
 *			<li> <strong> Datum: </strong> 11.07.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Kapazitaet entfernt und zu Ausstattung bewegt </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.2 </li>
 *			<li> <strong> Datum: </strong> 12.07.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> istFrei und Kapazitaet- Fixes </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.3 </li>
 *			<li> <strong> Datum: </strong> 28.07.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Package Verarbeitung hinzugefuegt </li>
 *		</ul>
 *	</li>
 *  *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.4 </li>
 *			<li> <strong> Datum: </strong> 02.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Raum implements Serializable hinzugefuegt </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.0 </li>
 *			<li> <strong> Datum: </strong> 06.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Finales Update </li>
 *		</ul>
 *	</li>
 *  *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.1 </li>
 *			<li> <strong> Datum: </strong> 11.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Implementieren der Interfaces </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.2 </li>
 *			<li> <strong> Datum: </strong> 12.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikationen </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.3 </li>
 *			<li> <strong> Datum: </strong> 14.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Bug Fixes </li>
 *		</ul>
 *	</li>
 * </ol>
 * @version 3.2
 * @author Alexander Reichenbach
 *
 */
public class Raum implements VerarbeitungInterfaces.RaumIF, Serializable {

    // Attribute
	
	private static final long serialVersionUID = -1970365805954633424L;
	
	private String raumBezeichnung;
    private ArrayList<Reservierung> belegung;
    private Ausstattung ausstattung;
    private boolean buchbar;


    // Konstruktor
    
    public Raum(String raumBezeichnung, Ausstattung ausstattung, boolean buchbar){
        this.raumBezeichnung = raumBezeichnung;
        this.belegung = new ArrayList<Reservierung>();
        this.ausstattung=ausstattung;
        this.buchbar = buchbar;
    }

    // Getter und Setter
    
    public String getRaumBezeichnung() {
        return raumBezeichnung;
    }

    public void setRaumBezeichnung(String raumBezeichnung) {
        this.raumBezeichnung = raumBezeichnung;
    }

    public ArrayList<Reservierung> getBelegung() {
        return belegung;
    }

    public Ausstattung getAusstattung() {
        return ausstattung;
    }

    public void setAusstattung(Ausstattung ausstattung) {
        this.ausstattung = ausstattung;
    }

    public void setAusstattung (boolean beamer, boolean ohp, boolean tafel, boolean smartb, boolean whiteb, boolean computerr, int kapazitaet){
        setAusstattung(new Ausstattung(beamer, ohp, tafel, smartb, whiteb, computerr, kapazitaet));
    }

    public boolean isBuchbar() {
        return buchbar;
    }

    public void setBuchbar (boolean buchbar) { this.buchbar = buchbar; }


    /**
	 * <p><strong>Vorbedingungen:</strong> Es muss ein Raum-Objekt vorhanden sein, auf dem die Methode aufgerufen werden kann 
	 * und ein Ausstattungs-Objekt, welches als "anforderung" übergeben wird.</p>
	 * <p><strong>Effekt:</strong> Berechnet einen Integer-Wert, der Auskunft darüber gibt, ob die Ausstattung des Raumes die Anforderungen erfüllt und wenn ja, wie umfangreich der Raum ausgestattet ist. </p>
	 * @param a ein Ausstattungs-Objekt, das beschreibt, welche Ausstattungsgegenstände gefordert werden
	 * @return zurückgegeben wird ein Integer-Wert, der =0 ist, wenn die Anforderungen nicht erfüllt sind; ansonsten gilt: je höher der Wert, desto besser ist der Raum ausgestattet
	 */

    public int hatMindestausstattung (Ausstattung a) {
    	return ausstattung.hatMindestens(a);
    }


    /**
     * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum übergeben werden und es muss einen Raum geben, auf den die Methode angewendet wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob der Raum im übergebenen Zeitraum belegt ist.</p>
     * @param zr Zeitraum, der auf Kollision mit der Belegung des Raumes geprüft wird
     * @return <strong>true</strong> wenn Zeitraum und Belegung/Reservierungen nicht kollidieren, <strong>false</strong> wenn sie kollidieren
     */
    public boolean istFrei(Zeitraum zr) {
    	if (findeKollision(zr)==null) return true;
    	else return false;
    }


    /**
     * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum übergeben werden und es muss einen Raum geben, auf den die Methode angewendet wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob der Raum im übergebenen Zeitraum belegt ist.</p>
     * @param zr Zeitraum, der auf Kollision mit der Belegung des Raumes geprüft wird
     * @return das Reservierungsobjekt, mit dem der Zeitraum kollidiert, wenn eine Kollision vorliegt und "null", wenn keine Kollision vorliegt
     */

    public Reservierung findeKollision (Zeitraum zr) {
        return GlobaleMethoden.findeKollisioninArrayList(belegung, zr);
    }


    /**
     * <p><strong>Vorbedingungen:</strong> Es muss eine Reservierung übergeben werden und es muss einen Raum geben, auf den die Methode angewendet werden kann.</p>
	 * <p><strong>Effekt:</strong> Fügt das Reservierung sortiert in die Reservierungs-/Belegungsiste ein, falls es nicht ohnehin schon in der Liste vorhanden ist.</p>
     * @param neu Reservierung, die der Belegungs-/Reservierungsliste des Raumes hinzugefügt werden soll
     * @return <strong>true</strong> wenn die Reservierung der Liste hinzugefügt wurde, <strong>false</strong> wenn die Reservierung nicht hinzugefügt wurde, da sie bereits in der Liste vorhanden war
     */
    
    public boolean addReservierung(Reservierung neu){
        if (!belegung.contains(neu)) {
            GlobaleMethoden.addReservierungtoArrayList(belegung, neu);
            return true;
        } else {
            return false;
        }
    }


   
    //entfernt eine Reservierung aus der Liste
    public void removeReservierung (Reservierung weg) {
    	belegung.remove(weg);
    }
    
    public String toString(){
    	return raumBezeichnung;
    }
}
