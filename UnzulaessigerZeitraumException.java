package Verarbeitung;

/**
* <strong> Zweck: </strong> Ausnahmebehandlung eines unzulaessigen Zeitraumes (Ende liegt vor Start)
* <h2>Aenderungshistorie:</h2> 
*	<ol>
*   <li>
*     <ul>
*          <li> <strong> Version: </strong> 1.0 </li>
*          <li> <strong> Datum: </strong> 06.08.16 </li>
*          <li> <strong> Autor: </strong> Alexander Reichenbach </li>
* 		      	<li> <strong> Beschreibung: </strong> Initiale Befüllung </li>
*		   </ul>
*   </li>
*   <li>
*     <ul>
*          <li> <strong> Version: </strong> 1.1 </li>
*          <li> <strong> Datum: </strong> 11.08.16 </li>
*          <li> <strong> Autor: </strong> Alexander Reichenbach </li>
* 		      	<li> <strong> Beschreibung: </strong> Interfaces implementiert </li>
*		   </ul>
*   </li>
*   <li>
*     <ul>
*          <li> <strong> Version: </strong> 1.2 </li>
*          <li> <strong> Datum: </strong> 12.08.16 </li>
*          <li> <strong> Autor: </strong> Hanna Behnke </li>
* 		      	<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikation </li>
*		   </ul>
*   </li>
*   <li>
*     <ul>
*          <li> <strong> Version: </strong> 1.3 </li>
*          <li> <strong> Datum: </strong> 12.08.16 </li>
*          <li> <strong> Autor: </strong> Alexander Reichenbach </li>
* 		      	<li> <strong> Beschreibung: </strong> Bug Fixes </li>
*		   </ul>
*	  </li>
* </ol>
* 
* @author Alexander Reichenbach
* @version 1.3
* 
*/

public class UnzulaessigerZeitraumException extends Exception {
    UnzulaessigerZeitraumException() {
        super ("Ende muss nach Anfang des Zeitraumes liegen");
    }
}
