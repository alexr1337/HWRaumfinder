package Verarbeitung;

import VerarbeitungInterfaces.ReservierungIF;

import java.io.Serializable;
import java.util.Date;

/**
 * <strong> Zweck:</strong> Ermoeglicht die Erstellung von Reservierungsobjekten, denen eine eindeutige Reservierungsnummer 
 * sowie ein Zeitraum, ein Reservierer und ein Raum zugeordnet sind.
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
 *			<li> <strong> Beschreibung: </strong> Update Reservierungsnummer </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.2 </li>
 *			<li> <strong> Datum: </strong> 28.07.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Package Verarbeitung hinzugefuegt </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.3 </li>
 *			<li> <strong> Datum: </strong> 02.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Implements Serializable </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.0 </li>
 *			<li> <strong> Datum: </strong> 06.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Major Update </li>
 *		</ul>
 *	</li>
 * 	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.1 </li>
 *			<li> <strong> Datum: </strong> 07.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Update Getter/Setter für resCounter </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.2 </li>
 *			<li> <strong> Datum: </strong> 11.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Interfaces implementiert </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.3 </li>
 *			<li> <strong> Datum: </strong> 12.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikationen </li>
 *		</ul>
 *	</li>

 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.4 </li>
 *			<li> <strong> Datum: </strong> 13.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Bug Fixes </li>
 *		</ul>
 *	</li>
 * </ol>
 * @version 2.4
 * @author Alexander Reichenbach
 *
 */


public class Reservierung implements VerarbeitungInterfaces.ReservierungIF, Serializable {

    // Attribute
	private static final long serialVersionUID = 8584156051140726334L;

	private static long resCounter = 0;

    private long reservierungsNr;
    private Raum raum;
    private Reservierer inhaber;
    private Zeitraum zeitraum;
    private Date reservierungsZeitpunkt;
    private String kommentar;
    private boolean error;
    private boolean storniert;


    // Konstruktoren
    //  ->  reservierungsNr und reservierungsZeitpunkt werden automatisch gesetzt
    //  ->  error und storniert sind bei Objektkonstruktion false

    // mit Kommentar
    public Reservierung(Raum raum, Reservierer inhaber, Zeitraum zeitraum, String kommentar) {

        this.reservierungsNr = resCounter++;
        this.raum = raum;
        this.inhaber = inhaber;
        this.zeitraum = zeitraum;
        this.reservierungsZeitpunkt = new Date (System.currentTimeMillis());
        this.kommentar = kommentar;
        this.error = false;
        this.storniert = false;
    }

    // ohne Kommentar
    public Reservierung(Raum raum, Reservierer inhaber, Zeitraum zeitraum) {

        this.reservierungsNr = resCounter++;
        this.raum = raum;
        this.inhaber = inhaber;
        this.zeitraum = zeitraum;
        this.reservierungsZeitpunkt = new Date (System.currentTimeMillis());
        this.kommentar = "";
        this.error = false;
        this.storniert = false;
    }

    // mit manuell gesetztem Zeitpunkt und Kommentar, für Verarbeitung.OnlineEinleser
    public Reservierung(Raum raum, Reservierer inhaber, Zeitraum zeitraum, Date zeitpunkt) {

        this.reservierungsNr = resCounter++;
        this.raum = raum;
        this.inhaber = inhaber;
        this.zeitraum = zeitraum;
        this.reservierungsZeitpunkt = zeitpunkt;
        this.kommentar = "";
        this.error = false;
        this.storniert = false;
    }

    //Getter und Setter

    public long getReservierungsNr() {
        return reservierungsNr;
    }
    public void setReservierungsNr(long reservierungsNr) {
        this.reservierungsNr = reservierungsNr;
    }
    public Raum getRaum() {
        return raum;
    }
    public void setRaum(Raum raum) {
        this.raum = raum;
    }
    public Reservierer getInhaber() {
        return inhaber;
    }
    public void setInhaber(Reservierer inhaber) {
        this.inhaber = inhaber;
    }
    public Zeitraum getZeitraum() {
        return zeitraum;
    }
    public void setZeitraum(Zeitraum zeitraum) {
        this.zeitraum = zeitraum;
    }
    public Date getReservierungsZeitpunkt() {
        return reservierungsZeitpunkt;
    }
    public void setReservierungsZeitpunkt(Date reservierungsZeitpunkt) {
        this.reservierungsZeitpunkt = reservierungsZeitpunkt;
    }
    public String getKommentar() {
        return kommentar;
    }
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
    public boolean isError() {
        return error;
    }
    public void setError(boolean error) {
        this.error = error;
    }
    public boolean isStorniert() {
        return storniert;
    }
    public void setStorniert(boolean storniert) {
        this.storniert = storniert;
    }

    public static long getResCounter(){
        return resCounter;
    }

    public static void setResCounter(long resCount){
        Reservierung.resCounter=resCount;
    }

    /**
     * <p><strong>Vorbedingungen:</strong> Es muss ein Zeitraum übergeben werden und es muss einen Reservierung geben, auf den die Methode angewendet wird.</p>
	 * <p><strong>Effekt:</strong> Prüft, ob die Reservierung mit übergebenen Zeitraum kollidiert</p>
     * @param zr übergebener Zeitraum
     * @return <strong>false </strong> wenn die Reservierung storniert oder fehlerhaft ist oder keine Kollision vorliegt; <strong>true</strong>, wenn Zeitraum und Reservierung kollidieren
     */
    public boolean kollidiert (Zeitraum zr) {
        if (error || storniert) return false;
        else return this.zeitraum.kollidiert(zr);
    }

    public String toString (boolean admin) {
        String erg;
        erg =	"Nummer:     "  + reservierungsNr                       + "\n" +
                "Raum:       "  + raum.getRaumBezeichnung()             + "\n" +
                "Zeitraum:   "  + zeitraum.toString()      				+ "\n" +
                "Kommentar:  ";

        if (!kommentar.isEmpty()) erg += kommentar;
        else erg += "Kein Kommentar";

        erg +=  "\n" +
                "reserviert am " + reservierungsZeitpunkt.toString() + "\n";

        if (admin){
            erg +=	"Inhaber:    "  + inhaber.getName()                     + "\n" +
                    "Error:      "  + error                                 + "\n" +
                    "Storniert:  "  + storniert;
        }

        return erg;
    }
}
