package Oberflaeche;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <strong> Zweck: </strong> Definiert das Design der gelieferten Ergebnisse einer Suche
 * <h2>Aenderungshistorie:</h2>
 * <ol>
 * 	<li>
 * 		<ul>
 * 			<li> <strong> Version: </strong> 1.0 </li>
 *			<li> <strong> Datum: </strong> 06.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Erstellung und Design</li>
 *		 </ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.1 </li>
 *			<li> <strong> Datum: </strong> 10.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Implementierung ActionListener und getRenderer() </li>
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
 * @version 1.2
 * @author Alexander Reichenbach
 *
 */
public class SuchErgebnisPanel extends GUIPanel {

    private GUIFrame frame;
    private String[] ergebnisArray;

    public SuchErgebnisPanel (String[] ergebnisArray){
        frame = GUIFrame.getInstance();
        this.ergebnisArray=ergebnisArray;
        initialize();
    }

    private void initialize(){
        setLayout(new BorderLayout(10,10));
        setBorder (new EmptyBorder (10,10,5,5));

        JPanel header = new JPanel(new GridLayout(2,1,5,5));
            JLabel einleitung1 = new JLabel("geordnet nach Grad der Übereinstimmung mit Suchkriterien.");
            header.add(einleitung1);
            JLabel einleitung2 = new JLabel("Bitte einen Raum zur Reservierung auswählen:");
            header.add(einleitung2);
        add(header, BorderLayout.NORTH);

        JList<String> ergebnisList = new JList<>(ergebnisArray);
            ergebnisList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ergebnisList.setLayoutOrientation(JList.VERTICAL);
            ergebnisList.setVisibleRowCount(-1);
            ergebnisList.setCellRenderer(getRenderer());

        JScrollPane ergebnisPanel = new JScrollPane(ergebnisList);
        add(ergebnisPanel);

        JPanel footer = new JPanel(new BorderLayout());
            JButton auswahlBtn = new JButton ("Weiter");
            footer.add(auswahlBtn, BorderLayout.EAST);
        add (footer, BorderLayout.SOUTH);

        auswahlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String auswahl = ergebnisList.getSelectedValue();
                if (auswahl == null){
                    JOptionPane.showMessageDialog(frame,
                            "Bitte einen Raum auswählen!",
                            "Fehler",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    frame.raumAuswaehlen(auswahl);
                }
            }
        });
    }

    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

                return listCellRendererComponent;
            }
        };
    }
}
