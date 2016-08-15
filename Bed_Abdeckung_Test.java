package Test;

import Verarbeitung.*;

import java.util.Arrays;
import java.util.Date;

/**
 * <strong>Zweck:</strong> Codeabdeckung der Bedingungen der Raumfinder Klasse
 * @author Maria Wolff
 */
public class Bed_Abdeckung_Test {
    static private Raumfinder testBed;

    public static void main (String[] args) throws UnzulaessigerZeitraumException {
        testBed = Raumfinder.getInstance();

        //mit Räumen füllen, ohne Online einlesen
        testBed.addRaum(new Raum("Wohnzimmer",new Ausstattung(true,true,true,true,true,false,25), true));
        testBed.addRaum(new Raum("Küche",new Ausstattung(false,true,true,false,false,false,30), true));
        testBed.addRaum(new Raum("Bar",new Ausstattung(false,true,true,false,true,false,50), true));
        testBed.addRaum(new Raum("Hörsaal",new Ausstattung(false,true,true,false,true,false,1000), false));

        Dozent doz = new Dozent("Prof. M");

        //mit Nutzern füllen
        testBed.legeNutzerAn("Anna", "erdbeere","Was ist grün?","Gurken", false);
        testBed.legeNutzerAn("Otto", "hamsterrad","Weshalb?","deshalb", false);
        testBed.legeNutzerAn("Mimi", "pflaumenmus", "Was ist 2+2?", "5",true);
        testBed.legeNutzerAn("Hans", "kirschkuchen", "Wieso?", "weil", false);

        //nicht löschbaren Admin erstellen
        testBed.addNutzer(new Admin("Lara", "bananenmus", "Warum?", "darum", false));

        //Überpfrüfen ob legeNutzerAn Bed. erfüllt:
        //Es kann ein Standard-Nutzer erstellt werden.
        System.out.println("Anna ist ein Admin: "+testBed.getNutzer().get(0).isAdmin());
        //Es kann ein Administrator erstellt werden.
        System.out.println("Mimi ist ein Admin: "+testBed.getNutzer().get(2).isAdmin()+"\n");

        //Nutzer löschen
        //Standard-Nutzer löschen
        System.out.println("Otto wird gelöscht: "+testBed.loescheNutzer(testBed.getNutzer().get(1)));
        //Admin löschen
        System.out.println("Mimi wird gelöscht: "+testBed.loescheNutzer(testBed.getNutzer().get(1)));
        //nicht löschbaren Admin löschen
        System.out.println("Lara wird gelöscht: "+testBed.loescheNutzer(testBed.getNutzer().get(2))+"\n");

        //Nutzer suchen
        //Nutzer existiert, getName() für Darstellung von Nutzer-Objekt
        System.out.println("Dieser Nutzer kann gefunden werden: " +testBed.sucheNutzer("Anna").getName());
        //Nutzer existiert nicht, (getName() wirft exception)
        System.out.println("Dieser Nutzer kann gefunden werden: " +testBed.sucheNutzer("Otto")+"\n");

        //Nutzer authentifizieren
        //Nutzer existiert, Passwort ist korrekt
        System.out.println(testBed.authentifiziereNutzer("Anna","erdbeere").getName()+" kann sich mit dem Passwort 'erdbeere' anmelden.");
        //Nutzer existiert nicht
        System.out.println("Otto kann sich mit dem Passwort 'hamsterrad' anmelden: " +testBed.authentifiziereNutzer("Otto","hamsterrad"));
        //falsches Passwort
        System.out.println("Lara kann sich mit dem Passwort 'hamsterrad' anmelden: " +testBed.authentifiziereNutzer("Lara","hamsterrad")+"\n");


        //Nutzer ausgeben
        System.out.println("Folgende Nutzer sind im Raumfinder: "+Arrays.toString(testBed.getNutzerString())+"\n");


        //Reservierungen erstellen
        //Anna reserviert die Küche am 20.08 von 14:15 - 15:30
        Reservierung b = new Reservierung(testBed.getRaeume().get(1), (Reservierer) testBed.getNutzer().get(0), new Zeitraum (new Date(116,7,20, 14,15), new Date(116,7,20,15,30)));
        //Hans reserviert die Küche am 20.08 von 14:15 - 15:30
        Reservierung d = new Reservierung(testBed.getRaeume().get(1), (Reservierer) testBed.getNutzer().get(1), new Zeitraum (new Date(116,7,20, 14,15), new Date(116,7,20,15,30)));
        //Anna reserviert das Wohnzimmer am 20.08 von 14:15 - 15:30
        Reservierung e = new Reservierung(testBed.getRaeume().get(0), (Reservierer) testBed.getNutzer().get(0), new Zeitraum (new Date(116,7,20, 14,15), new Date(116,7,20,15,30)));
        //Anna reserviert den Hörsaal am 20.08 von 16:15 - 17:30
        Reservierung f = new Reservierung(testBed.getRaeume().get(3), (Reservierer) testBed.getNutzer().get(0), new Zeitraum (new Date(116, 7, 20, 16,15), new Date(116, 7, 20,17,30)));
        // Hans reserviert das Wohnzimmer am 20.08 von 14:15 - 15:30
        Reservierung g = new Reservierung(testBed.getRaeume().get(0), (Reservierer) testBed.getNutzer().get(1), new Zeitraum (new Date(116, 7, 20, 14,15), new Date(116, 7, 20, 15,30)));
        // Anna reserviert das Wohnzimmer am 20.08 von 14:15 - 15:30
        Reservierung h = new Reservierung(testBed.getRaeume().get(0), (Reservierer) testBed.getNutzer().get(0), new Zeitraum (new Date(116, 7, 20, 14,15), new Date(116, 7, 20, 15,30)));
        //Anna reserviert die Küche am 20.08 von 08:00 - 09:00
        Reservierung i = new Reservierung(testBed.getRaeume().get(1), (Reservierer) testBed.getNutzer().get(0), new Zeitraum (new Date(116, 7, 20, 8,0), new Date(116, 7, 20, 9,0)));
        testBed.reservieren(i,false);
        //Anna reserviert die Bar am 20.08 von 8:00 - 9:00
        Reservierung j = new Reservierung(testBed.getRaeume().get(2), (Reservierer) testBed.getNutzer().get(0), new Zeitraum (new Date(116, 7, 20, 8,0), new Date(116, 7, 20, 9,0)));
        // Dozent reserviert die Bar am 20.08 von 12:00 - 14:00
        Reservierung k = new Reservierung(testBed.getRaeume().get(2), (Reservierer) doz, new Zeitraum (new Date(116, 7, 20, 12,0), new Date(116, 7, 20, 14,0)));
        //Hans resrerviert die Bar am 20.08 von 8:00 - 9:00
        Reservierung l = new Reservierung(testBed.getRaeume().get(2), (Reservierer) testBed.getNutzer().get(1), new Zeitraum (new Date(116, 7, 20, 8,0), new Date(116, 7, 20, 9,0)));
        //nicht-existenter Nutzer
        //Reservierung m = new Reservierung(testBed.getRaeume().get(2), (Reservierer) testBed.getNutzer().get(4), new Zeitraum (new Date(116, 7, 20, 8,0), new Date(116, 7, 20, 9,0)));

        //Raum nach Kriterien suchen
        //Raum entspricht den Kriterien und ist frei
        System.out.println("Folgende Räume treffen auf die Suchkriterien zu: "+testBed.suche(new Zeitraum(new Date(116, 7, 21, 8,0),new Date(116, 7, 21, 9, 0)), new Ausstattung(true, true, false,false,true,false,5)));
        //Es gibt keinen Raum der den Kriterien entspricht
        System.out.println("Folgende Räume treffen auf die Suchkriterien zu: "+testBed.suche(new Zeitraum(new Date(116, 7, 21, 8,0),new Date(116, 7, 21, 9, 0)), new Ausstattung(true, true, true,true,true,true,5)));
        //Die Küche entspricht eigentlich den Kriterien ist aber belegt
        System.out.println("Folgende Räume treffen auf die Suchkriterien zu: "+testBed.suche(new Zeitraum(new Date(116, 7, 20, 14,0),new Date(116, 7, 20, 15, 0)), new Ausstattung(false, true, true,true,false,false,26))+"\n");

        //Raum nach Kennung suchen
        //Raum existiert
        System.out.println("Dieser Raum kann gefunden werden: " +testBed.sucheKennung("Wohnzimmer"));
        //Raum existiert nicht
        System.out.println("Dieser Raum kann gefunden werden: " +testBed.sucheKennung("Schlafzimmer")+"\n");


        //Reservierungen anlegen um Überschreibe-Funktion zu testen
        //Der Admin Lara reserviert die Küche am 20.08 von 13:15 - 13:30
        //Reservierung a = new Reservierung(testBed.getRaeume().get(1), (Reservierer) testBed.getNutzer().get(2), new Zeitraum (new Date(116,7,20, 13,15), new Date(116,7,20,13,30)));
        //Es gibt keinen 4. Nutzer
        //Reservierung c = new Reservierung(testBed.getRaeume().get(1), (Reservierer) testBed.getNutzer().get(3), new Zeitraum (new Date(116,7,20, 14,15), new Date(2016, 20,8,15,30)));


        //Reservierung durch Standard-Nutzer, Raum ist frei
        System.out.println("Anna reserviert die Küche am 20.08 von 14:15 - 15:30: "+testBed.reservieren(b,false));
        //Reservierung durch Admin
        //System.out.println(testBed.reservieren(a,false));
        //Reservierer existiert nicht
        //System.out.println(testBed.reservieren(c,false));
        //Raum zu Zeitraum belegt
        System.out.println("Hans reserviert die Küche am 20.08 von 14:15 - 15:30: "+testBed.reservieren(d,false));
        //Nutzer hat schon Raum zu dieser Zeit gebucht
        System.out.println("Anna reserviert das Wohnzimmer am 20.08.von 14:15 - 15:30: "+testBed.reservieren(e,false));
        //Reservierung hinzufügen um Kollision zu erzeugen
        System.out.println("Hans reserviert das Wohnzimmer am 20.08 von 14:15 - 15:30: "+testBed.reservieren(g,false));
        //Raum ist nicht buchbar
        System.out.println("Anna reserviert den Hörsaal am 20.08 von 16:15 - 17:30: "+testBed.reservieren(f,false));

        //Überschreiben der Reservierung, Anna reserviert in dem Zeitraum,  belegt
        System.out.println("Anna kann ihre Reservierung der Küche und Hans Reservierung des Wohnzimmers überschreiben: "+testBed.reservieren(h,true)+"\n");
        System.out.println("Annas alte Reservierung: "+"\n"+b.toString(true)+"\n");
        System.out.println("Hans alte Reservierung: "+"\n"+g.toString(true)+"\n");
        System.out.println("Annas neue Reservierung: "+"\n"+h.toString(true)+"\n");

        //Überschreiben der Reservierung, Anna reserviert die Küche zu dem Zeitraum
        System.out.println("Anna kann ihre alte Reservierung der Küche überschreiben: "+testBed.reservieren(j,true)+"\n");
        System.out.println("Annas alte Reservierung: "+"\n"+i.toString(true)+"\n");
        System.out.println("Annas neue Reservierung: "+"\n"+j.toString(true)+"\n");

        //Überschreiben der Reservierung, Hans reserviert statt Anna die Bar
        System.out.println("Hans kann Annas Reservierung der Bar überschreiben: "+testBed.reservieren(l,true)+"\n");
        System.out.println("Annas alte Reservierung: "+"\n"+j.toString(true)+"\n");
        System.out.println("Hans neue Reservierung: "+"\n"+l.toString(true)+"\n");

        //Überschreiben der Reservierung, aber Nutzer existiert nicht
        //System.out.println("Der Nutzer kann die Reservierung überschreiben: "+testBed.reservieren(m,true));




        //Reservierungen suchen
        System.out.println(testBed.sucheReservierung(0).toString(false));
        System.out.println(testBed.sucheReservierung(4).toString(true)+"\n");

        //Prüfung auskommentiert, wirft nullpointerexception
        //System.out.println(testBed.sucheReservierung(2).toString(true));

        //Reservierung stornieren
        testBed.stornieren(g);
        System.out.println("Hans Resevierung des Wohnzimmers ist storniert: \n" +g.toString(true)+"\n");
        System.out.println(((StandardNutzer)testBed.getNutzer().get(1)).getReservierungen().get(0).toString(false));
        




    }
}
