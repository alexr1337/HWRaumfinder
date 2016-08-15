package Oberflaeche;

import Verarbeitung.Raumfinder;
import VerarbeitungInterfaces.RaumfinderIF;
import Verarbeitung.Admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <strong> Zweck: </strong> Leitet den Start des Raumfinders mittels Konsolenabfragen ein und initialisiert das System
 * <h2>Aenderungshistorie:</h2>
 * <ol>
 * 		<li>
 * 			<ul>
 * 				<li> <strong> Version: </strong> 1.0 </li>
 *				<li> <strong> Datum: </strong> 07.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Erstellung </li>
 *			 </ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 1.1 </li>
 *				<li> <strong> Datum: </strong> 10.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Implementierung von starteHWRaumfinder() und der main-Methode </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 1.2 </li>
 *				<li> <strong> Datum: </strong> 11.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Verkürzung Wartezeit</li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 1.3 </li>
 *				<li> <strong> Datum: </strong> 11.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Fehler behoben (Reihenfolge Erstellung Raumfinder - Erstellung GUIFrame geändert) </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 1.4 </li>
 *				<li> <strong> Datum: </strong> 12.08.16 </li>
 *				<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 				<li> <strong> Beschreibung: </strong> Unit-Spezifikation </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 1.5 </li>
 *				<li> <strong> Datum: </strong> 14.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Fehler behoben (OnlineEinleser) </li>
 *			</ul>
 *		</li>
 *
 * </ol>
 * @version 1.5
 * @author Alexander Reichenbach
 *
 */

public class Starter {
    static private BufferedReader din = new BufferedReader(new InputStreamReader(System.in));
    static private RaumfinderIF rf;
    static private GUIFrame gui;
    static private Admin admin;

    static private boolean firstStart=false;
    static private boolean einlesen=false;
    static private String[] eingabe = {"","","",""};

    public static void main(String[] Args) throws IOException, InterruptedException {

        System.out.println("Ist dies der erste Programmstart? (Y|N)");
        while (!(eingabe[0].equalsIgnoreCase("y")||eingabe[0].equalsIgnoreCase("n"))) eingabe[0] = din.readLine();
        if (eingabe[0].equalsIgnoreCase("y")){
            firstStart = true;
            System.out.println("Willkommen beim HWRaumfinder!");
            Thread.sleep(500);
            System.out.println("Bevor der HWRaumfinder gestartet werden kann, muss ein Admin-Konto erstellt werden.");
            Thread.sleep(500);
            System.out.println("Achtung: Dieses Nutzerkonto ist nicht löschbar.");
            Thread.sleep(500);
            System.out.println("Bitte den gewünschten Nutzernamen eingeben:");
            eingabe[0] = din.readLine();
            while (eingabe[1].length()<8){
                System.out.println("Bitte ein mindestens 8-stelliges Passwort eingeben:");
                eingabe[1] = din.readLine();
            }
            System.out.println("Bitte eine Sicherheitsfrage eingeben:");
            eingabe[2] = din.readLine();
            System.out.println("Bitte eine Antwort auf die Sicherheitsfrage eingeben:");
            eingabe[3] = din.readLine();

            admin = new Admin(eingabe[0],eingabe[1],eingabe[2], eingabe[3], false);

            System.out.println("\nVielen Dank!");
            Thread.sleep(500);
            System.out.println("Soll der Raumfinder vor dem Start die bestehende Raumbelegung seitens der HWR-Verwaltung\naus dem Internet herunterladen? (Y|N)");
            while (!(eingabe[0].equalsIgnoreCase("y")||eingabe[0].equalsIgnoreCase("n"))) eingabe[0] = din.readLine();
            if (eingabe[0].equalsIgnoreCase("y")) einlesen = true;
        } else {
            firstStart = false;
            System.out.println("Erneut Online-Einlesen?");
            eingabe[1] = din.readLine();
            while (!(eingabe[1].equalsIgnoreCase("y")||eingabe[0].equalsIgnoreCase("n"))) eingabe[0] = din.readLine();
            if (eingabe[1].equalsIgnoreCase("y")) einlesen = true;
        }

        starteHWRaumfinder();
    }

    private static void starteHWRaumfinder(){
        System.out.println("\nHWRaumfinder wird gestartet.");
        
        rf = Raumfinder.getInstance();
        
        if (firstStart) rf.getNutzer().add(admin);
        else {
            rf.load();
            rf= Raumfinder.getInstance();
        }

        if (einlesen) {
            System.out.println("Daten werden eingelesen...");
            rf.onlineEinlesen();
        }

        gui = GUIFrame.getInstance();
        
        System.out.println("Programm gestartet.");
    }
}
