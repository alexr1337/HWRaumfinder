package Verarbeitung;

/**
 * <strong>Zweck:</strong> Gibt Administratoren zusätzlich zu den regulären Nutzer-Attributen die Eigenschaft, löschbar oder nicht löschbar zu sein
 * <p><strong>Änderungshistorie:</strong></p>
 * <ol>
 * 	<li>
 * 		<ul>
 * 			<li> <strong> Version: </strong> 1.0 </li>
 *			<li> <strong> Datum: </strong> 11.07.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Erstellung </li>
 *		 </ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.1 </li>
 *			<li> <strong> Datum: </strong> 28.07.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Package: Verarbeitung </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.5 </li>
 *			<li> <strong> Datum: </strong> 11.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikationen </li>
 *		</ul>
 *	</li>
 *
 * </ol>
 * @version 1.5
 * @author Alexander Reichenbach
 *
 */

public class Admin extends Nutzer {

	
    private boolean deletable;

    // Konstruktor: Admin ist löschbar
    public Admin (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
        super (name, password, sicherheitsFrage, sicherheitsAntwort);
        this.deletable = true;
    }

    // erweiterter Konstruktor: dem Admin wird der Parameter deletable übergeben, der bestimmt, ob der Admin löschbar ist oder nicht
    public Admin (String name, String password, String sicherheitsFrage, String sicherheitsAntwort, boolean deletable) {
        super (name, password, sicherheitsFrage, sicherheitsAntwort);
        this.deletable = deletable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
}
