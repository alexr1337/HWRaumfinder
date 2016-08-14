package Persistenz;

import Verarbeitung.Raumfinder;
import VerarbeitungInterfaces.RaumfinderIF;

import java.io.*;
import java.util.Scanner;

/**
 * <strong> Zweck: </strong> Realisiert das Speichern und Laden des Programmes mit allen angelegten Raeumen, Nutzern und Reservierungen
 * <h2>Aenderungshistorie:</h2>
 * <ol>
 * 	<li>
 * 		<ul>
 * 			<li> <strong> Version: </strong> 1.0 </li>
 *			<li> <strong> Datum: </strong> 07.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Erstellung Persistenzschicht </li>
 *		 </ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.1 </li>
 *			<li> <strong> Datum: </strong> 07.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Speicherung des statischen ResCounters hinzugefuegt </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.0 </li>
 *			<li> <strong> Datum: </strong> 08.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Major Update </li>
 *		</ul>
 *	</li>
 * 	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.1 </li>
 *			<li> <strong> Datum: </strong> 11.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Interfaces implementiert </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.2 </li>
 *			<li> <strong> Datum: </strong> 11.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Bug Fixes </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 2.3 </li>
 *			<li> <strong> Datum: </strong> 13.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikationen </li>
 *		</ul>
 *	</li>
 * </ol>
 * @version 2.3
 * @author Hanna Behnke, Alexander Reichenbach
 *
 */
 
public class RaumfinderFileAdapter implements RaumfinderFileAdapterIF {

	// Singleton-Implementierung
	private static RaumfinderFileAdapter ourInstance = new RaumfinderFileAdapter();

	private File datei, rescounterdatei;

	// Singleton-Konstruktor
	private  RaumfinderFileAdapter () {
		new File ("FileSaver/").mkdir();
		datei = new File ("FileSaver/persistenz.raumfind");
		rescounterdatei = new File ("FileSaver/rescounter.raumfind");
	}
	
	// Singleton-getInstance
    public static RaumfinderFileAdapter getInstance() {
    	return ourInstance;
    }

    /**
	 * <p><strong>Vorbedingungen:</strong> Es muss einen Raumfinder geben, auf den die Methode angewendet wird.</p>
	 * <p><strong>Effekt:</strong> Der Raumfinder wird serialisiert und somit im Ganzen als ein Objekt in einer Datei gespeichert. 
	 * Der statische "resCounter" wird in einer gesonderten Datei gespeichert, da er nicht serialisierbar ist.</p>
	 *
	 */
	@SuppressWarnings({ "resource", "static-access" })
	public void save(){
		
		//Speichern des Raumfinders
		try {
			ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream(datei));
			out.writeObject((RaumfinderIF)Raumfinder.getInstance());
		} catch(IOException e) {
			System.err.println("Es gab ein Problem beim Speichern: IOException");
			return;
		}

		// Speichern des statischen resCounters in einer gesonderten Datei 
		long resCounter=Raumfinder.getInstance().getResCounter();
		try {
			FileWriter fileout = new FileWriter(rescounterdatei);
			fileout.write(String.valueOf(resCounter));
			fileout.close();
		} catch(IOException e){
			System.err.println("Es gab ein Problem beim Speichern des ResCounters: IOException");
		}
	}

	/**
	 * <p><strong>Vorbedingungen:</strong> Um Fehlermeldungen zu vermeiden, sollte eine einlesbare Datei mit dem serialisierten Raumfinder existieren.</p>
	 * <p><strong>Effekt:</strong> Liest den Raumfinder (der das Raumfinder-Interfaces implementieren muss) und den resCounter  aus den entsprechenden Dateien ein.</p>
	 * @return gibt <strong>null</strong> zurück, wenn ein Fehler beim Einlesen auftritt, sonst wird ein Objekt vom Typ RaumfinderIF zurückgegeben
	 */
	@SuppressWarnings({ "resource", "static-access" })
	public RaumfinderIF load() {

		try {
			// Einlesen des resCounters
			Scanner sc = new Scanner(rescounterdatei);
			long resCounter = sc.nextLong();
			sc.close();
			
			// Einlesen des Raumfinder-Interfaces
			ObjectInputStream in = new ObjectInputStream (new FileInputStream(datei));
			RaumfinderIF rfif = (RaumfinderIF) in.readObject();
			rfif.setResCounter(resCounter);
			return rfif;
		} catch (IOException e) {
			System.err.println("Es gab ein Problem beim Einlesen des ResCounters: IOException");
		} catch (ClassNotFoundException t){
			System.err.println("Es gab ein Problem beim Laden: ClassNotFound");
		}
		return null;
	}
}
