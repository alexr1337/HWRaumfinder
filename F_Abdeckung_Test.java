package Test;

import Verarbeitung.*;
import Persistenz.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


/**
 * <strong>Zweck:</strong> Codeabdeckung Funktionsabdeckung Raumfinder Klasse
 *  @author Maria Wolff
 */
public class F_Abdeckung_Test {
    static private Raumfinder testFunc;

    public static void main (String [] args) throws UnzulaessigerZeitraumException {
        testFunc = Raumfinder.getInstance();
        //gespeicherten Raumfinder laden
        /*testFunc.load();
        testFunc = Raumfinder.getInstance();*/
        testFunc.addRaum(new Raum("WIL1",new Ausstattung(true,true,true,true,true,true,25), true));
        testFunc.addRaum(new Raum("6B375",new Ausstattung(false,true,true,false,false,false,30), true));
        testFunc.onlineEinlesen();
        testFunc = Raumfinder.getInstance();

        System.out.println("Folgende Räume sind im Test-Raumfinder: " +testFunc.getRaeume() +"\n");
        testFunc.loescheRaum(testFunc.getRaeume().get(0));
        System.out.println("Folgende Räume sind im Test-Raumfinder: "+testFunc.getRaeume()+"\n");

        testFunc.addNutzer(new StandardNutzer("Anna","gurke","Was ist der Sinn des Lebens?","42"));
        System.out.println("Folgende Nutzer sind im Test-Raumfinder: "+Arrays.toString(testFunc.getNutzerString())+"\n");
        System.out.println("Der Nutzer Anna wird geloescht: "+testFunc.loescheNutzer(testFunc.getNutzer().get(0)));
        System.out.println("Folgende Nutzer sind im Test-Raumfinder: "+testFunc.getNutzer()+"\n");
        testFunc.legeNutzerAn("Lara","kirsche","Wie viel ist 2+2?","5", true);
        System.out.println("Folgende Nutzer sind im Test-Raumfinder: "+Arrays.toString(testFunc.getNutzerString())+"\n");


        System.out.println("Der ausgwählte Raum 6B375 ist zu der ausgewählten Zeit 12.08.2016 9:00 - 12.08.2016 9:30 frei:"+testFunc.pruefeVerfuegbarkeitRaum("6B375",new Zeitraum(new Date(2016,8,12,9,0),new Date(2016,8,12,9,30)))+"\n");
        System.out.println("Der ausgewählte Raum ist buchbar: "+testFunc.pruefeBuchbarkeitRaum("6B375")+"\n");

        System.out.println("Diese Raeume entsprechen den Kriterien (frei 12.08.2016 8:15 - 12.08.2016 9:00, keine zusätzliche Ausstattung, Kapazität 2):" +testFunc.suche(new Zeitraum(new Date(2016,8,12,8,15),new Date(2016,8,12,9,0)),new Ausstattung(false,false,false,false,false,false,2))+"\n");
        System.out.println("Raum über Kennung finden:"+testFunc.sucheKennung("6B375")+"\n");

        testFunc.legeNutzerAn("Anna","gurke","Was ist der Sinn des Lebens?","42", false);
        System.out.println("Nutzer Anna finden: "+ testFunc.sucheNutzer("Anna").getName() +"\n");
        System.out.println("Nutzer Anna authentifizieren mit Benutzername Anna und Passwort gurke: " +testFunc.authentifiziereNutzer("Anna", "gurke").getName()+"\n");
        System.out.println("Der Raum wird vom Nutzer Anna vom 12.08.2016 8:15 - 12.08.2016 9:30 reserviert: " +testFunc.reservieren(testFunc.getRaeume().get(0),(StandardNutzer) testFunc.getNutzer().get(1), new Zeitraum(new Date(2016,8,12,8,30), new Date(2016,8,12,9,15)))+"\n");
        System.out.println("Anna kann folgende Daten zur Reservierung sehen: ");
        System.out.println(((StandardNutzer) testFunc.getNutzer().get(1)).getReservierungen().get(0).toString(false));
        System.out.println("Die erste Reservierung im Raumfinder ist: ");
        System.out.println(testFunc.sucheReservierung(0).toString(true)+"\n");
        testFunc.stornieren(testFunc.sucheReservierung(0));
        System.out.println("Die Admin sieht diese Infos zur Reservierung: ");
        System.out.println(testFunc.sucheReservierung(0).toString(true)+"\n");
        //System.out.println("Anna kann folgende Daten zur Reservierung sehen: ");
        // exception, da Anna keine Reservierungen besitzt, die sie sehen kann
        // System.out.println(((StandardNutzer) testFunc.getNutzer().get(1)).getReservierungen().get(0).toString(false));

        testFunc.save();

    }
}
