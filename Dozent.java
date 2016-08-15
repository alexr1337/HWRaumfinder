
package Verarbeitung;

import java.io.Serializable;

/**
 * <p><strong>Zweck:</strong> Der Dozent kann als Reservierer angelegt werden, ist selber jedoch kein aktiver Nutzer des Systems. Dadurch ist es möglich, bei Reservierungen, die Vorlesungen und Seminare repräsentieren, den Dozenten als Reservierer anzugeben.</p> 
 * <p><strong>Änderungshistorie:</strong></p>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.0 </li>
 *			<li> <strong> Datum: </strong> 25.06.2016 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Fehler behoben: Wertberechnung in hatMindestanforerung() </li>
 *		</ul>
 * @version 1.0
 * @author Hanna Behnke
 *
 */

public class Dozent implements Reservierer, Serializable {
	
	// Attribut
    private String name;

    // Kontruktor
    public Dozent (String name) {
        this.name = name;
    }

    // Getter und Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
