package Oberflaeche;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservierungsPanel extends JPanel {

    private GUIFrame frame;
    private Date start, ende;
    private String[][] ausstattung;
    private String raumKennung;

    String datumStr;
    Calendar c1, c2;
    SimpleDateFormat sdf;
    JComboBox stundenVon, minutenVon, stundenBis, minutenBis;
    JLabel verfuegbarkeit;

    public ReservierungsPanel(GUIFrame parent) {
        frame = parent;
        raumKennung="Empty";
        start = new Date (System.currentTimeMillis());
        ende = new Date (System.currentTimeMillis()+3600000);	// default: eine Stunde sp�ter
    }

    public void setup (String raumKennung, Date start, Date ende, String[][] a){
        this.raumKennung=raumKennung;
        this.start = start;
        this.ende = ende;
        ausstattung=a;
        
        c1 = Calendar.getInstance();
        c1.setTime(start);
        c2 = Calendar.getInstance();
        c2.setTime(ende);
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        datumStr = sdf.format(c1.getTime());
        
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBorder (new EmptyBorder (10,10,5,5));

        JLabel titelLabel = new JLabel ("Raum " + raumKennung);
        titelLabel.setFont(new Font(titelLabel.getFont().getName(), Font.BOLD, 15));
        titelLabel.setBorder(new EmptyBorder(0,5,5,0));
        add(titelLabel, BorderLayout.NORTH);

        JPanel body = new JPanel(new GridLayout(1,2));
            JPanel ausstPanel = new JPanel(new GridLayout(ausstattung[0].length, 2));
                ausstPanel.setBorder(BorderFactory.createTitledBorder("Ausstattung"));
                for (int i = 0; i<ausstattung[0].length; i++){
                	JLabel temp = new JLabel("  " + ausstattung[0][i]);
                	ausstPanel.add(temp);
                	temp = new JLabel(ausstattung[1][i]);
                	ausstPanel.add(temp);
                }
            body.add(ausstPanel);
            JPanel zeitPanel = new JPanel(new GridLayout(5,1, 5,5));
                zeitPanel.setBorder(BorderFactory.createTitledBorder("Verf�gbarkeit pr�fen"));
                	JPanel panelDatum = new JPanel (new GridLayout (1,2,5,5));
                    JLabel lblDatum1 = new JLabel("Datum:");
                    panelDatum.add(lblDatum1);
                    JLabel lblDatum2 = new JLabel(" " + datumStr);
                    lblDatum2.setBorder(new LineBorder(Color.black, 1));
                    panelDatum.add(lblDatum2);
                    zeitPanel.add(panelDatum);
                    JPanel datumsWechselPnl = new JPanel (new GridLayout(1,2));
                        JButton prevDay = new JButton("<<");
                        prevDay.addActionListener(new ActionListener() {
                                                  public void actionPerformed(ActionEvent e) {
                                                      c1.add(Calendar.DATE, -1);
                                                      c2.add(Calendar.DATE, -1);
                                                      aktualisiereZeitraum();
                                                      datumStr = sdf.format(c1.getTime());
                                                      lblDatum2.setText(" " + datumStr);
                                                      setVerfuegbarkeitLabel();
                                                  }
                                              });
                        datumsWechselPnl.add(prevDay);
                        JButton nextDay = new JButton(">>");
                        nextDay.addActionListener(new ActionListener() {
                                                      public void actionPerformed(ActionEvent e) {
                                                          c1.add(Calendar.DATE, 1);
                                                          c2.add(Calendar.DATE, 1);
                                                          aktualisiereZeitraum();
                                                          datumStr = sdf.format(c1.getTime());
                                                          lblDatum2.setText(" " + datumStr);
                                                          setVerfuegbarkeitLabel();
                                                      }
                                                });
                        datumsWechselPnl.add(nextDay);
                    zeitPanel.add(datumsWechselPnl);
                    
                    JPanel vonPanel = new JPanel(new GridLayout(1,5,0,2));
                    JLabel lblVon = new JLabel("Beginn:");
                    vonPanel.add(lblVon);
                        stundenVon = new JComboBox(GUIFrame.hour);
                            stundenVon.setSelectedItem(Integer.toString(c1.get(Calendar.HOUR_OF_DAY)));
                        vonPanel.add(stundenVon);
                        JLabel labelDPkt1 = new JLabel(":");
                        labelDPkt1.setHorizontalAlignment(JLabel.CENTER);
                        vonPanel.add(labelDPkt1);
                        minutenVon = new JComboBox(GUIFrame.min);
                            minutenVon.setSelectedItem(Integer.toString(c1.get((Calendar.MINUTE / 15)*15)));
                        vonPanel.add(minutenVon);
                        JLabel lblUhr1 = new JLabel("Uhr");
                        lblUhr1.setHorizontalAlignment(JLabel.CENTER);
                        vonPanel.add(lblUhr1);
                    zeitPanel.add(vonPanel);
                    JPanel bisPanel = new JPanel(new GridLayout(1,5,0,2));
                    JLabel lblBis = new JLabel("Ende");
                    bisPanel.add(lblBis);
                        stundenBis = new JComboBox(GUIFrame.hour);
                            stundenBis.setSelectedItem(Integer.toString(c2.get(Calendar.HOUR_OF_DAY)));
                        bisPanel.add(stundenBis);
                        JLabel labelDPkt2 = new JLabel(":");
                        labelDPkt2.setHorizontalAlignment(JLabel.CENTER);
                        bisPanel.add(labelDPkt2);
                        minutenBis = new JComboBox(GUIFrame.min);
                            minutenBis.setSelectedItem(Integer.toString(c2.get((Calendar.MINUTE / 15)*15)));
                        bisPanel.add(minutenBis);
                        JLabel lblUhr2 = new JLabel("Uhr");
                        lblUhr2.setHorizontalAlignment(JLabel.CENTER);
                        bisPanel.add(lblUhr2);
                    zeitPanel.add(bisPanel);
                    verfuegbarkeit = new JLabel("");
                        verfuegbarkeit.setFont(new Font(verfuegbarkeit.getFont().getFontName(), verfuegbarkeit.getFont().BOLD, verfuegbarkeit.getFont().getSize()));
                        setVerfuegbarkeitLabel();
                    zeitPanel.add(verfuegbarkeit);
            body.add(zeitPanel);
        add(body, BorderLayout.CENTER);

        stundenVon.addActionListener(new VerfuegbarkeitsPruefer());
        minutenVon.addActionListener(new VerfuegbarkeitsPruefer());
        stundenBis.addActionListener(new VerfuegbarkeitsPruefer());
        minutenBis.addActionListener(new VerfuegbarkeitsPruefer());

        if (!frame.aktiverNutzerIsAdmin()){
        	JPanel footer = new JPanel(new BorderLayout());
            JButton resButton = new JButton("Reservieren");
            resButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.reservieren(raumKennung, start, ende);
                }
            });
            footer.add(resButton, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);
        }
        
        setVisible(true);
    }

    private void aktualisiereZeitraum(){
    	c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt((String)stundenVon.getSelectedItem()));
    	c1.set(Calendar.MINUTE, Integer.parseInt((String)minutenVon.getSelectedItem()));
    	start=c1.getTime();
    	
    	c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt((String)stundenBis.getSelectedItem()));
    	c2.set(Calendar.MINUTE, Integer.parseInt((String)minutenBis.getSelectedItem()));
        ende=c2.getTime();
    }

    private void setVerfuegbarkeitLabel(){
    	if (frame.raumBuchbar(raumKennung)){
    		boolean frei = frame.pruefeVerfuegbarkeitRaum(raumKennung,start, ende);
            if (frei){
                verfuegbarkeit.setText("Raum verf�gbar!");
                verfuegbarkeit.setForeground(Color.GREEN);
                //verfuegbarkeit.setBorder(new LineBorder(Color.green, 5));
            } else {
                verfuegbarkeit.setText("Raum belegt!");
                verfuegbarkeit.setForeground(Color.red);
                //verfuegbarkeit.setBorder(new LineBorder(Color.red, 5));
            }
    	} else {
    		verfuegbarkeit.setText("Raum nicht buchbar!");
            verfuegbarkeit.setForeground(Color.red);
    	}
    }

    class VerfuegbarkeitsPruefer implements ActionListener {
        public void actionPerformed( ActionEvent e ) {
            aktualisiereZeitraum();
            setVerfuegbarkeitLabel();
        }
    }
}