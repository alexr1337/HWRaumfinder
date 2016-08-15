package Verarbeitung;

import Persistenz.*;
import VerarbeitungInterfaces.RaumfinderIF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;

/** 
12  * <strong> Zweck:</strong>  
13  *  <ul>  
14  *      <li>Verwaltet Gesamtlisten der Nutzer, Raeume und Reservierungen und koordiniert Änderungen an diesen </li> 
15  *      <li>Zugriff auf Persistenzschicht </li> 
16  *  </ul> 
17  * <h2>Aenderungshistorie:</h2> 
18  *  
19  * <ol> 
20  * 		<li> 
21  * 			<ul> 
22  * 				<li> <strong> Version: </strong> 1.0 </li> 
23  *				<li> <strong> Datum: </strong> 25.06.16 </li> 
24  *				<li> <strong> Autor: </strong> Alexander Reichenbach </li> 
25  * 				<li> <strong> Beschreibung: </strong> Initiale Befuellung </li> 
26  *			 </ul> 
27  *		</li> 
28  *		<li> 
29  *			<ul> 
30  *				<li> <strong> Version: </strong> 1.1 </li> 
31  *				<li> <strong> Datum: </strong> 11.07.16 </li> 
32  *				<li> <strong> Autor: </strong> Alexander Reichenbach </li> 
33  * 				<li> <strong> Beschreibung: </strong> Update: Raumfinder-Suche </li> 
34  *			</ul> 
35  *		</li> 
36  *		<li> 
37  *			<ul> 
38  *				<li> <strong> Version: </strong> 1.2 </li> 
39  *				<li> <strong> Datum: </strong> 21.07.16 </li> 
40  *				<li> <strong> Autor: </strong> Alexander Reichenbach </li> 
41  * 				<li> <strong> Beschreibung: </strong> reservieren und addReservierung hinzugefuegt </li> 
42  *			</ul> 
43  *		</li> 
44  *		<li> 
45  *			<ul> 
46  *				<li> <strong> Version: </strong> 2.0 </li> 
47  *				<li> <strong> Datum: </strong> 13.07.16 </li> 
48  *				<li> <strong> Autor: </strong> Alexander Reichenbach </li> 
49  * 				<li> <strong> Beschreibung: </strong> Update: Major Update </li> 
50  *			</ul> 
51  *		</li> 
52  *		<li> 
53  *			<ul> 
54  *				<li> <strong> Version: </strong> 2.1 </li> 
55  *				<li> <strong> Datum: </strong> 02.08.16 </li> 
56  *				<li> <strong> Autor: </strong> Hanna Behnke </li> 
57  * 				<li> <strong> Beschreibung: </strong> save()- und load()-Methoden hinzugefuegt </li> 
58  *			</ul> 
59  *		</li> 
60  *		<li> 
61  *			<ul> 
62  *				<li> <strong> Version: </strong> 2.2 </li> 
63  *				<li> <strong> Datum: </strong> 11.08.16 </li> 
64  *				<li> <strong> Autor: </strong> Hanna Behnke </li> 
65  * 				<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikationen </li> 
66  *			</ul> 
67  *		</li> 
68  *		<li> 
69  *			<ul> 
70  *				<li> <strong> Version: </strong> 2.3 </li> 
71  *				<li> <strong> Datum: </strong> 07.08.16 </li> 
72  *				<li> <strong> Autor: </strong> Hanna Behnke </li> 
73  * 				<li> <strong> Beschreibung: </strong> Getter und Setter ResCounter hinzugefuegt </li> 
74  *			</ul> 
75  *		</li> 
76  *  	<li> 
77  *			<ul> 
78  *				<li> <strong> Version: </strong> 3.0 </li> 
79  *				<li> <strong> Datum: </strong> 10.08.16 </li> 
80  *				<li> <strong> Autor: </strong> Alexander Reichenbach </li> 
81  * 				<li> <strong> Beschreibung: </strong> Finales Update </li> 
82  *			</ul> 
83  *		</li> 
84  *		<li> 
85  *			<ul> 
86  *				<li> <strong> Version: </strong> 3.1 </li> 
87  *				<li> <strong> Datum: </strong> 11.08.16 </li> 
88  *				<li> <strong> Autor: </strong> Alexander Reichenbach </li> 
89  * 				<li> <strong> Beschreibung: </strong> Bug Fixes </li> 
90  *			</ul> 
91  *		</li> 
92  *  *		<li> 
93  *			<ul> 
94  *				<li> <strong> Version: </strong> 3.2 </li> 
95  *				<li> <strong> Datum: </strong> 12.08.16 </li> 
96  *				<li> <strong> Autor: </strong> Hanna Behnke </li> 
97  * 				<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikation </li> 
98  *			</ul> 
99  *		</li>  
100  *		<li> 
101  *			<ul> 
102  *				<li> <strong> Version: </strong> 3.3 </li> 
103  *				<li> <strong> Datum: </strong> 14.08.16 </li> 
104  *				<li> <strong> Autor: </strong> Alexander Reichenbach </li> 
105  * 				<li> <strong> Beschreibung: </strong> Bug Fixes </li> 
106  *			</ul> 
107  *		</li> 
108  * 
109  * </ol> 
110  * @version 3.3 
111  * @author Alexander Reichenbach 
112  * 
113  */ 



public class Raumfinder implements VerarbeitungInterfaces.RaumfinderIF, Serializable {

    // Singleton-Implementierung
    private static Raumfinder ourInstance = new Raumfinder();

    //Attribute
    private ArrayList<Raum> raeume;
    private ArrayList<Reservierung> reservierungen;
    private ArrayList<Nutzer> nutzer;
    transient private OnlineEinleser onEinleser;
    transient private RaumfinderFileAdapterIF fileAdapter;


    // Singleton-Konstruktor
    private Raumfinder () {

        raeume = new ArrayList<Raum>();
        reservierungen = new ArrayList<Reservierung>();
        nutzer = new ArrayList<Nutzer>();
        onEinleser = new OnlineEinleser(this);
        fileAdapter = RaumfinderFileAdapter.getInstance();
    }

    // Singleton-getInstance
    public static Raumfinder getInstance() {
        return ourInstance;
    }



    // --------------------------------------------------
    //              Abschnitt Suche
    // --------------------------------------------------

    /**
     * <strong>Vorbedingungen:</strong> Es müssen ein Zeitraum- und ein Ausstattungs-Objekt übergeben werden
     * <p><strong>Effekt:</strong> Ermöglicht die Raumsauche anhand der Kriterien Zeitraum und Ausstattung</p>
     * @param s Zeitraum, in dem ein Raum frei sein muss, um als Suchergebnis ausgegeben zu werden
     * @param a Austattungs-Objekt, dass die Anforderungen an die Ausstattung darstellt
     * @return die auf die Suche passenden Räume werden in absteigender Reihenfolge zurückgegeben
     */
    public ArrayList<String> suche (Zeitraum s, Ausstattung a){

        // Variableninitialisierung
        int offset = 0;
        ConcurrentSkipListMap<Integer,String> erg = new ConcurrentSkipListMap<>();

        // Durchlaufen aller Räume
        for (int i=0; i<raeume.size(); i++) {
            Raum r = raeume.get(i);
            int score = r.hatMindestausstattung(a);     // Bewertung der Relevanz des Suchergebnisses
            if (score > 0 && r.istFrei(s)) {            // bei erfülten Suchkriterien
                erg.put((score*1000)+(offset++) , r.getRaumBezeichnung());   // als Ergebnis abspeichern (invers geordnet nach Relevanz)
            }
        }
        return (new ArrayList<String>(erg.descendingMap().values()));     // Konstruktion und Reversion der Werte
    }

    // Raumsuche anhand von Kriterien ohne Zeitraum/Aussattungs-Objekte
    public ArrayList<String> suche (Date start, Date ende, boolean beamer, boolean ohp, boolean tafel, boolean smartb, boolean whiteb, boolean computerr, int kapazitaet) throws UnzulaessigerZeitraumException {
        return suche (new Zeitraum(start, ende), new Ausstattung(beamer, ohp, tafel, smartb, whiteb, computerr, kapazitaet));
    }

    /**
     * <strong>Vorbedingungen:</strong> Es muss eine Raumkennung vom Typ String übergeben werden
     * <p><strong>Effekt:</strong> Ermöglicht die Raumsauche anhand der Raumkennung</p>
     * @param raumKennung String, der unabhängig von Groß- und Kleinschreibung auf Übereinstimmung mit den Kennungen der vorhandenen Räume geprüft wird
     * @return <strong>null</strong>, falls die gesuchte Kennung nicht vergeben ist, bei Übereinstimmung einer Raumkennung und der gesuchten Bezeichnung wird das entsprechende <strong>Raumobjekt</strong> zurückgegeben
     */
    public Raum sucheKennung(String raumKennung){
        for(int k = 0; k<raeume.size(); k++){
            if(raumKennung.equalsIgnoreCase(raeume.get(k).getRaumBezeichnung())){
                return raeume.get(k);
            }
        }
        return null;
    }



    // --------------------------------------------------
    //              Abschnitt Reservierungen
    // --------------------------------------------------

    // automatische Erstellung einer Reservierung ohne Kommentar
    public boolean reservieren (Raum r, Reservierer n, Zeitraum s) {
        Reservierung neu = new Reservierung (r, n, s);
        return reservieren (neu,false);
    }

    // automatische Erstellung einer Reservierung mit Kommentar
    public boolean reservieren (Raum r, Reservierer n, Zeitraum s, String kommentar) {
        Reservierung neu = new Reservierung (r, n, s, kommentar);
        return reservieren (neu, false);
    }

    /**
     * <strong>Vorbedingungen:</strong> Es müssen ein Reservierungs-Objekt und ein boolean-Wert, der definiert, ob bestehende reservierungen überschrieben werden soollen, übergeben werden.
     * <p><strong>Effekt:</strong> Interne beziehungsweise manuelle Erstellung einer Reservierung</p>
     * @param neu Reservierungs-Objekt, welches der Reservierungsliste hinzugefügt werden soll
     * @param overwrite ist boolean overwrite als "true" übergeben, werden bei Kollisionen die betroffene(n) Reservierung(en) gelöscht und durch die neue Reservierung ersetzt
     * (einsetzbar z.B. bei Stundenplanänderungen, die Reservierungen von Studenten überschreiben sollen)
     * @return <strong>true</strong> wenn die Reservierung der Reservierungsliste hinzugefügt wurde, <strong>false</strong>, wenn die Reservierung verworfen wurde
     */
    public boolean reservieren (Reservierung neu, boolean overwrite) {

        // Variableninitialisierung
        boolean kollisionRaum = false, kollisionInh = false;
        Raum raum = neu.getRaum();
        Zeitraum zr = neu.getZeitraum();
        StandardNutzer sn = null;
        if  (neu.getInhaber() instanceof StandardNutzer) sn = (StandardNutzer)neu.getInhaber();

        // mögliche Kollisionen suchen
        if (!raum.istFrei(zr)) kollisionRaum = true;
        if (sn != null) if (!sn.istFrei(zr))  kollisionInh = true;

        // keine Kollisionen:
        // Reservierung einstellen
        if (!kollisionRaum && !kollisionInh && raum.isBuchbar()) {
            this.addReservierung(neu);
            raum.addReservierung(neu);
            if (sn != null) sn.addReservierung(neu);
            return true;
        }

        // Kollisionen existieren
        else {
            // Im overwrite-Modus:
            // Kollisionen beseitigen (durch Errors) und Reservierung einstellen
            if (overwrite) {
                if (kollisionRaum) {
                    raum.findeKollision(zr).setError(true);
                }
                if (kollisionInh) {
                    sn.findeKollision(zr).setError(true);
                }
                this.addReservierung(neu);
                raum.addReservierung(neu);
                if (sn != null) sn.addReservierung(neu);
                return true;
            }
            // Nicht im overwrite-Modus:
            // Reservierung verwerfen
            else return false;
        }
    }

    public void stornieren (Reservierung r) {
        r.setStorniert(true);
        r.getRaum().removeReservierung(r);
        if (r.getInhaber() instanceof StandardNutzer) ((StandardNutzer) r.getInhaber()).removeReservierung(r);
    }

    public Reservierung sucheReservierung(long reservierungsNummer){ //sucht Res nach Nr
        for(int i = 0; i<reservierungen.size(); i++){
            if(reservierungsNummer==reservierungen.get(i).getReservierungsNr()){
                return reservierungen.get(i);
            }
        }
        return null;
    }

    /**
     * <p><strong>Vorbedingungen:</strong> Es muss eine Reservierung übergeben werden.</p>
     * <p><strong>Effekt:</strong> Fügt die Reservierung sortiert in die Reservierungsliste ein, falls sie nicht ohnehin schon in der Liste vorhanden ist.</p>
     * @param neu Reservierung, die der Reservierungsliste des Raumes hinzugefügt werden soll
     *
     */
    private void addReservierung(Reservierung neu){
        GlobaleMethoden.addReservierungtoArrayList(reservierungen, neu);
    }

    public ArrayList<Reservierung> getReservierungen() {
        return reservierungen;
    }

    public void setReservierungen (ArrayList<Reservierung> reservierungen) {
        this. reservierungen = reservierungen;
    }

    public long getResCounter(){
        return Reservierung.getResCounter();
    }

    public void setResCounter(long resCount){
        Reservierung.setResCounter(resCount);
    }



    // --------------------------------------------------
    //                  Abschnitt Räume
    // --------------------------------------------------

    /**
     * <p><strong>Vorbedingungen:</strong> Es müssen ein Zeitraum und eine Raumkennung übergeben werden.</p>
     * <p><strong>Effekt:</strong> Prüft, ob der Raum im übergebenen Zeitraum belegt ist.</p>
     * @param raumKennung Name des Raumes, dessen Verfügbarkeit geprüft wird
     * @param zr Zeitraum, der auf Kollision mit der Belegung des Raumes geprüft wird
     * @return <strong>true</strong> wenn Zeitraum und Belegung/Reservierungen nicht kollidieren, <strong>false</strong> wenn sie kollidieren
     */
    public boolean pruefeVerfuegbarkeitRaum (String raumKennung, Zeitraum zr){
        return sucheKennung(raumKennung).istFrei(zr);
    }

    public boolean pruefeBuchbarkeitRaum (String raumKennung){
        return sucheKennung(raumKennung).isBuchbar();
    }

    // Raumverwaltung

    public void addRaum(Raum a){
        if (raeume.size()==0) { 					//Wenn die Reservierungsliste leer ist, wird das Reservierungsobjekt sofort hinzugefügt
            raeume.add(a);
            return;
        }
        else { 									    //Sonst wird die Reservierung nach Startpunkt des Zeitraumes sortiert in die Liste eingefügt
            for (int i=0; i<raeume.size(); i++) {
                if (raeume.get(i).getRaumBezeichnung().compareToIgnoreCase(a.getRaumBezeichnung()) > 0) {
                    raeume.add(i, a);
                    return;
                }
            }
            raeume.add(a);                          // Wenn bisher nicht hinzugefügt, dann ans Ende der ArrayList
        }
    }

    public void addRaum (String raumKennung, boolean beamer, boolean ohp, boolean tafel, boolean smartb, boolean whiteb, boolean computerr, int kapazitaet, boolean admin){
        addRaum (new Raum (raumKennung, new Ausstattung(beamer, ohp, tafel, smartb, whiteb, computerr, kapazitaet), admin));
    }

    public void sortiereRaumNeuEin (Raum a){
        raeume.remove(a);
        addRaum(a);
    }

    public void loescheRaum (Raum a) {
        raeume.remove(a);
        ArrayList<Reservierung> raumReses = a.getBelegung();
        for (int j=0; j<raumReses.size(); j++){
            raumReses.get(j).setError(true);
        }
    }

    public ArrayList<Raum> getRaeume() {
        return raeume;
    }

    public void setRaeume (ArrayList<Raum> raeume) {
        this.raeume = raeume;
    }



    // --------------------------------------------------
    //                  Abschnitt Nutzer
    // --------------------------------------------------

    public Nutzer sucheNutzer(String nutzerName){
        for(int i = 0; i<nutzer.size(); i++){
            if(nutzerName.equalsIgnoreCase(nutzer.get(i).getName())){
                return nutzer.get(i);
            }
        }
        return null;
    }

    public Nutzer authentifiziereNutzer (String name, String password) {
        Nutzer erg = sucheNutzer(name);
        if(erg!=null && !erg.checkPw(password)) erg=null;
        return erg;
    }

    public void legeNutzerAn (String name, String password, String sicherheitsFrage, String sicherheitsAntwort, boolean admin) {
        if(admin){
            addNutzer(new Admin (name,password,sicherheitsFrage,sicherheitsAntwort));       // deletable-Erweiterung?
        } else {
            addNutzer(new StandardNutzer(name,password,sicherheitsFrage,sicherheitsAntwort));
        }
    }

    /**
     * <p><strong>Vorbedingungen:</strong> Der zu löschende Nutzer darf nicht null sein und muss im Raumfinder gespeichert sein.</p>
     * <p><strong>Effekt:</strong> Die save()-Methode auf dem RaumfinderFileAdapter - Persistenzschicht - aufgerufen.
     * @param n Zu löschender Nutzer
     * @return true bei Erfolg, false bei Misserfolg (wenn Nutzer nicht-löschbarer Admin)
     */
    public boolean loescheNutzer (Nutzer n) {
        if (n.isAdmin()) if (!((Admin) n).isDeletable()) return false;
        if (!n.isAdmin()) {
            ArrayList<Reservierung> nutzerReses = ((StandardNutzer)n).getReservierungen();
            for (int j=0; j<nutzerReses.size(); j++){
                nutzerReses.get(j).setError(true);
            }
        }
        nutzer.remove(n);
        return true;
    }

    public void sortiereNutzerNeuEin (Nutzer n){
        nutzer.remove(n);
        addNutzer(n);
    }

    public void addNutzer(Nutzer n){
        if (nutzer.size()==0) { 					//Wenn die Reservierungsliste leer ist, wird das Reservierungsobjekt sofort hinzugefügt
            nutzer.add(n);
            return;
        }
        else { 									    //Sonst wird die Reservierung nach Startpunkt des Zeitraumes sortiert in die Liste eingefügt
            for (int i=0; i<nutzer.size(); i++) {
                if (nutzer.get(i).getName().compareToIgnoreCase(n.getName()) > 0) {
                    nutzer.add(i, n);
                    return;
                }
            }
            nutzer.add(n);                          // Wenn bisher nicht hinzugefügt, dann ans Ende der ArrayList
        }
    }

    public ArrayList<Nutzer> getNutzer() {
        return nutzer;
    }

    public void setNutzer (ArrayList<Nutzer> nutzer) {
        this.nutzer = nutzer;
    }

    public String[] getNutzerString() {
        String[] erg = new String[nutzer.size()];
        for (int i=0; i<erg.length; i++) {
            Nutzer temp = nutzer.get(i);
            erg[i] = temp.getName();
            if (temp.isAdmin())  erg[i] += " <Admin>";
        }
        return erg;
    }



    // --------------------------------------------------
    //         Abschnitt Einlesen und Persistenz
    // --------------------------------------------------

    public void onlineEinlesen(){
        onEinleser.einlesen();
    }

    /**
     * <p><strong>Vorbedingungen:</strong> Es muss einen Raumfinder geben, auf den die Methode angewendet wird.</p>
     * <p><strong>Effekt:</strong> Die save()-Methode auf dem RaumfinderFileAdapter - Persistenzschicht - aufgerufen.
     */
    public void save(){
        RaumfinderFileAdapter.getInstance().save();
    }

    /**
     * <p><strong>Vorbedingungen:</strong> Um Fehlermeldungen zu vermeiden, sollte eine einlesbare Datei mit dem serialisierten Raumfinder existieren.</p>
     * <p><strong>Effekt:</strong> Liest das Raumfinder-Interface und den resCounter ein bis auf die nicht seralisierbaren Attribute des raumfinders (OnlineEinlese und RaumfinderFileAdapter) über die load()-Methode des RaumfinderFileAdapters - Persistezschicht - ein.</p>
     */
    public void load(){
        ourInstance = (Raumfinder)fileAdapter.load();
        onEinleser = new OnlineEinleser(ourInstance);
        fileAdapter = RaumfinderFileAdapter.getInstance();
    }
}
