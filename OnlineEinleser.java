package Verarbeitung;

import VerarbeitungInterfaces.RaumfinderIF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Date;

/**
 * <strong>Zweck:</strong> Liest offiziellen Stundenplan aller Kurse des FB2 der HWR Berlin (im Internet verfügbar) herunter und konvertiert diese zu Reservierungen im HWRaumfinder
 * <p><strong>Änderungshistorie:</strong></p>
 * 
 * <ol>
 * 		<li>
 * 			<ul>
 * 				<li> <strong> Version: </strong> 1.0 </li>
 *				<li> <strong> Datum: </strong> 25.06.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Initiale Befuellung </li>
 *			 </ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 1.1 </li>
 *				<li> <strong> Datum: </strong> 28.07.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Package Verarbeitung hinzugefuegt </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 2.0 </li>
 *				<li> <strong> Datum: </strong> 06.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Major Update </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 3.0 </li>
 *				<li> <strong> Datum: </strong> 10.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Major Update </li>
 *			</ul>
 *		</li>
 *  *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 3.1 </li>
 *				<li> <strong> Datum: </strong> 11.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Interfaces implementiert </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 3.2 </li>
 *				<li> <strong> Datum: </strong> 12.08.16 </li>
 *				<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 				<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikationen </li>
 *			</ul>
 *		</li>

 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 3.3 </li>
 *				<li> <strong> Datum: </strong> 13.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Bug Fixes </li>
 *			</ul>
 *		</li>
 *
 * </ol>
 * @version 3.3
 * @author Alexander Reichenbach
 *
 */

public class OnlineEinleser {

    private int fileCounter, resCounter, errCounter;
    private RaumfinderIF raumfinder;

    private final String SPEICHERORT = "StundenplanFiles/";


    public OnlineEinleser (RaumfinderIF raumfinder){
        this.raumfinder=raumfinder;
        new File(SPEICHERORT).mkdir();
    }

    public void einlesen() {

        ArrayList<Reservierung> resses = new ArrayList<Reservierung>();

        download();
        resCounter=0;

        for (int i=0; i<fileCounter; i++) {
            resses.addAll(readEvents(SPEICHERORT + "events_" + i + ".ics"));
        }

        System.out.println("OnlineEinleser: "+resCounter+" Reservierungen eingelesen.");
    }

    private void download(){

        URL tempURL;
        String tempString;
        ReadableByteChannel rbc = null;
        FileOutputStream fos = null;

        final String URLSTART = "http://moodle.hwr-berlin.de/fb2-stundenplan/download.php?doctype=.ics&url=./fb2-stundenplaene/";

        String[] url1 = {        // normal: 1-6, abc      //"bank/semester1/kursa",
                "bank/",
                "handel/",
                "industrie/",
                "wi/"
        };
        String[] url2 = {                       // nur ein Kurs je Semester
                "bauwesen/",
                "dl/",
                "elektrotechnik/",
                "fm/",
                "iba/",
                "immobilien/",
                "informatik/",
                "maschinenbau/",
                "steuern/"
        };
        String[] url3 = {
                "ppm/semester1/kurs",	//sem 1-4
                "ppm/semester2/kurs",
                "ppm/semester3/kurs",
                "ppm/semester4/kurs",

                "spedition/semester1/kursa", //kurs a,b
                "spedition/semester1/kursb",
                "spedition/semester2/kursa",
                "spedition/semester2/kursb",
                "spedition/semester3/kursa",
                "spedition/semester3/kursb",
                "spedition/semester4/kursa",
                "spedition/semester4/kursb",
                "spedition/semester5/kursa",
                "spedition/semester5/kursb",
                "spedition/semester6/kursa",
                "spedition/semester6/kursb",

                "versicherung/semester1/kursa", //kurs a,b
                "versicherung/semester1/kursb",
                "versicherung/semester2/kursa",
                "versicherung/semester2/kursb",
                "versicherung/semester3/kursa",
                "versicherung/semester3/kursb",
                "versicherung/semester4/kursa",
                "versicherung/semester4/kursb",
                "versicherung/semester5/kursa",
                "versicherung/semester5/kursb",
                "versicherung/semester6/kursa",
                "versicherung/semester6/kursb",

                "tourismus/semester1/kurs",	// kein System
                "tourismus/semester1/kursa",
                "tourismus/semester1/kursb",
                "tourismus/semester2/kurs",
                "tourismus/semester2/kursa",
                "tourismus/semester2/kursb",
                "tourismus/semester3/kurs",
                "tourismus/semester4/kurs",
                "tourismus/semester5/kurs",
                "tourismus/semester5/kursa",
                "tourismus/semester5/kursb",
                "tourismus/semester6/kurs",
                "tourismus/semester6/kursa",
                "tourismus/semester6/kursb"

                /*"6B_151_153",
                "Gapp",
                "Jurscha",
                "Krause_Andreas",
                "Tchepki",
                "Wagner_Ralf",
                "Wittmuess",
                "wannemacher"*/
        };

        try {

            fileCounter = 0;

            // url1
            for (int i = 0; i < url1.length; i++) {
                for (int j = 1; j <= 6; j++) {
                    for (int k = 97; k <= 99; k++) {        // k repräsentiert char-Werte für a,b,c
                        tempString = url1[i] + "semester" + j + "/kurs" + (char) k;
                        tempURL = new URL(URLSTART + tempString);
                        download (tempURL, new File (SPEICHERORT + "events_" + fileCounter + ".ics"), rbc, fos);
                    }
                }
            }

            // url2
            for (int i = 0; i < url2.length; i++) {
                for (int j = 1; j <= 6; j++) {
                    tempString = url2[i] + "semester" + j + "/kurs";
                    tempURL = new URL(URLSTART + tempString);
                    download (tempURL, new File (SPEICHERORT + "events_" + fileCounter + ".ics"), rbc, fos);
                }
            }

            //url3
            for (int i = 0; i < url3.length; i++) {
                tempString = url3[i];
                tempURL = new URL (URLSTART + tempString);
                download (tempURL, new File (SPEICHERORT + "events_" + fileCounter + ".ics"), rbc, fos);
            }

            if (rbc != null) rbc.close();   //funktioniert so nicht, muss anders  -> close in download (..)?
            if (fos!= null) fos.close();
        }
        catch (MalformedURLException e) {
            System.err.println("Interner Fehler: Fehlerhafte URL [download]");
        }
        catch (IOException e) {
            System.err.println("Interner Fehler: rbc/fos konnte nicht geschlossen werden [download]");
            e.printStackTrace();
        }
    }

    private void download (URL website, File ziel, ReadableByteChannel rbc, FileOutputStream fos) throws IOException{
        rbc = Channels.newChannel(website.openStream());
        fos = new FileOutputStream(ziel);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fileCounter++;
    }

    private ArrayList<Reservierung> readEvents (String filepath) {

        ArrayList<Reservierung> reservierungen =new ArrayList<Reservierung>();

        String strName, strBesitzer, strStart, strEnde, strRaum, tempStr;
        String tempDatei = readICS(filepath);

        Raum tempRaum;
        Reservierer tempBesitzer;
        Zeitraum tempZeitraum;

        try {
            while (tempDatei.indexOf("SUMMARY")>0){
               /*tempDatei = substringAfter(tempDatei, "SUMMARY:");
                tempStr = tempDatei.substring(0,tempDatei.indexOf("LOCATION")-2);
                strName = tempStr.substring(0, tempStr.indexOf("\\")) + " " + tempStr.substring(tempStr.indexOf("\\")+3, tempStr.lastIndexOf("\\"));     //ERROR HERE
                strBesitzer = tempStr.substring(tempStr.lastIndexOf("\\")+2, tempStr.length());
                    strBesitzer = strBesitzer.replaceAll("\\\\", "");
*/
                tempDatei = substringAfter(tempDatei, "LOCATION:");
                strRaum = tempDatei.substring(0,tempDatei.indexOf("DESCRIPTION")-2);
                    strRaum = strRaum.replaceAll("\\\\n", "");
                    strRaum = strRaum.replaceAll("\\\\", "");
                    strRaum = strRaum.replaceAll("CL: ", "");
                //neu ab hier:
                tempDatei = substringAfter(tempDatei, "DESCRIPTION:Art: ");
                tempStr = tempDatei.substring(0,tempDatei.indexOf("DTSTART")-2);
                    tempStr = tempStr.replaceAll("\\n", "");
                    tempStr = tempStr.replaceAll("\\r", "");
                    tempStr = tempStr.replaceAll("\\t", "");
                strName = tempStr.substring(0, tempStr.indexOf("\\")) + ": ";
                tempStr = substringAfter(tempStr, "Veranstaltung: ");
                if (tempStr.indexOf("Veran")>=0) strName += tempStr.substring(0, tempStr.indexOf("Veran"));
                else strName += tempStr.substring(0, tempStr.indexOf("Dozent"));
                    strName = strName.replaceAll("\\\\n", "");
                    strName = strName.replaceAll("\\\\", "");
                tempStr = substringAfter(tempStr, "Dozent: ");
                strBesitzer = tempStr.substring(0, tempStr.indexOf("Raum"));
                    strBesitzer = strBesitzer.replaceAll("\\\\n", "");
                    strBesitzer = strBesitzer.replaceAll("\\\\", "");
                tempStr = substringAfter(tempStr, "Raum: ");
                //strRaum = tempStr; //.substring(0,tempStr.indexOf("DTSTART"));
                    //strRaum = strRaum.replaceAll("\\\\n", "");

                tempDatei = substringAfter(tempDatei,"DTSTART;TZID=Europe/Berlin:");
                strStart = tempDatei.substring(0,tempDatei.indexOf("DTEND")-2);
                tempDatei = substringAfter(tempDatei, "DTEND;TZID=Europe/Berlin:");
                strEnde = tempDatei.substring(0,tempDatei.indexOf("PRIORITY")-2);

                /*
                System.out.println(strName);
                System.out.println(strBesitzer);
                System.out.println(strRaum);
                System.out.println(strStart);
                System.out.println(strEnde);
                System.out.println("-----------");
                */

                // Test:
//                if (strRaum.length()>50){
 //                   System.out.println("Fuck.");
  //              }

                if (strRaum.isEmpty()) strRaum = "Ohne Raum";
                tempRaum = raumfinder.sucheKennung(strRaum);
                    if (tempRaum == null) {
                        if(!strRaum.equals("Ohne Raum")){
                            tempRaum = new Raum (
                                    strRaum,
                                    new Ausstattung(false, false, false, false, false, false, 0),       // Ausstattung null
                                    true        // buchbar true
                            );
                        } else {
                            tempRaum = new Raum (
                                    strRaum,
                                    new Ausstattung(false, false, false, false, false, false, 0),       // Ausstattung null
                                    false        // buchbar false
                            );
                        }
                        raumfinder.addRaum(tempRaum);
                    }

                tempBesitzer = new Dozent (strBesitzer);
                tempZeitraum = new Zeitraum(icsStringToDate(strStart), icsStringToDate(strEnde));
/*
                reservierungen.add(
                        new Reservierung(
                                tempRaum,
                                tempBesitzer,
                                tempZeitraum,
                                strName
                        )
                );*/
                raumfinder.reservieren(new Reservierung(tempRaum, tempBesitzer, tempZeitraum, strName), true);
                resCounter++;
            }

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Interner Fehler: Substring out of Bounds [readEvents]");
            //e.printStackTrace();
        } catch (UnzulaessigerZeitraumException e) {
            System.err.println("Error: Fehlerhafter Zeitraum.");
        } finally {
            errCounter++;
        }

        return reservierungen;
    }

    private String substringAfter (String text, String breakpoint) {
        int startIndex=text.indexOf(breakpoint);
        return text.substring((startIndex+breakpoint.length()));
    }

    private String readICS (String filepath){
        String erg="";
        int c;
        try{
            FileReader f = new FileReader(filepath);
            while ((c = f.read()) != -1){
                erg = erg + (char)c;
            }
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return erg;
    }

    public Date icsStringToDate (String s) {        // eigentlich private stat public -> aendern!

        int year,month,day,hrs,mins,secs;

        year    = Integer.parseInt(s.substring(0,4));
        month   = Integer.parseInt(s.substring(4,6));
        day     = Integer.parseInt(s.substring(6,8));
        hrs     = Integer.parseInt(s.substring(9,11));
        mins    = Integer.parseInt(s.substring(11,13));
        secs    = Integer.parseInt(s.substring(13,15));

        return new Date(year-1900, month-1, day, hrs, mins, secs);
    }

}
