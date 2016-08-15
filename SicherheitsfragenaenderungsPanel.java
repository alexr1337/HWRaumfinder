package Oberflaeche;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <strong> Zweck: </strong> Definiert Design und Funktionalitäten des Sicherheitsfragenaenderungs-Panels
 * <h2>Aenderungshistorie:</h2>
 * <ol>
 * 	<li>
 * 		<ul>
 * 			<li> <strong> Version: </strong> 1.0 </li>
 *			<li> <strong> Datum: </strong> 06.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Erstellung und Design</li>
 *		 </ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.1 </li>
 *			<li> <strong> Datum: </strong> 10.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Impplementierung ActionListener </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.2 </li>
 *			<li> <strong> Datum: </strong> 12.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Unit-Spezifikation </li>
 *		</ul>
 *	</li>
 * </ol>
 * 
 * @version 1.2
 * @author Alexander Reichenbach
 *
 */
public class SicherheitsfragenaenderungsPanel extends GUIPanel {
    private GUIFrame frame;
    private String aktuelleFrage;

    public SicherheitsfragenaenderungsPanel(String[] frage) {
        aktuelleFrage=frage[0];
        frame = GUIFrame.getInstance();
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0,40,10,40));

        JPanel fields = new JPanel(new GridLayout(6,1));
        fields.setBorder(new EmptyBorder(0,0,20,0));
        JLabel altFrage = new JLabel(aktuelleFrage);
        altFrage.setToolTipText("Bitte aktuelle Sicherheitsfrage beantworten!");
        fields.add(altFrage);
        JTextField altAW = new JTextField();
        fields.add(altAW);
        JLabel neuFrageLbl = new JLabel("Neue Sicherheitsfrage eingeben:");
        fields.add(neuFrageLbl);
        JTextField neuFrage = new JTextField();
        fields.add(neuFrage);
        JLabel neuAWLbl = new JLabel("Neue Antwort eingeben:");
        fields.add(neuAWLbl);
        JTextField neuAW = new JTextField();
        fields.add(neuAW);
        add(fields, BorderLayout.CENTER);

        JButton okBtn = new JButton("Änderung durchführen");
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (altAW.getText().isEmpty() || neuFrage.getText().isEmpty() || neuAW.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Alle Felder müssen ausgefüllt werden!",
                            "Änderung fehlgeschlagen",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    frame.sicherheitsfrageAendern(altAW.getText(), neuFrage.getText(), neuAW.getText());
                }
            }
        });
        add(okBtn,BorderLayout.SOUTH);
    }
}
