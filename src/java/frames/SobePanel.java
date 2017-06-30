/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import beans.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.Helpers;
import utils.TipZahteva;

/**
 *
 * @author malenicn
 */
public class SobePanel extends javax.swing.JFrame {

    private Apartman apartman;

    public SobePanel(Apartman a) {
        initComponents();

        dohvatiSobe(a);

        List<Soba> sobe = apartman.getSobe();
        this.setLayout(new GridLayout(sobe.size(), 2));
        for (int i = 0; i < sobe.size(); i++) {
            JButton glavno = new JButton("" + sobe.get(i).getRedBr());
            JButton obrisi = new JButton("Obrisi");

            // paneli za resizing
            JPanel gp = new JPanel(), op = new JPanel();
            gp.add(glavno);
            op.add(obrisi);

            glavno.setPreferredSize(new Dimension(200, 40));
            obrisi.setPreferredSize(new Dimension(200, 40));

            glavno.addActionListener(new ActionListener() {
                private Soba s;
                private SobePanel panel;

                public void actionPerformed(ActionEvent e) {
                    new SobeIzmena(s, apartman).setVisible(true);
                    panel.setVisible(false);
                }

                private ActionListener init(Soba s, SobePanel panel) {
                    this.s = s;
                    this.panel = panel;
                    return this;
                }
            }.init(sobe.get(i), this));

            obrisi.addActionListener(new ActionListener() {
                private Soba soba;
                private Apartman apartman;
                private SobePanel panel;

                public void actionPerformed(ActionEvent e) {
                    JMSContext context = prodavac.Prodavac.connectionFactory.createContext();

                    Destination destination = prodavac.Prodavac.zahtevi;
                    String username = prodavac.Prodavac.prodavac.getUsername();
                    String password = prodavac.Prodavac.prodavac.getPassword();

                    JMSConsumer consumer = context.createConsumer(prodavac.Prodavac.odgovori, Helpers.getId(username, password));
                    JMSProducer producer = context.createProducer();

                    ObjectMessage zahtev = context.createObjectMessage();
                    try {
                        zahtev.setStringProperty("id", username + password);

                        zahtev.setObject(soba);
                        zahtev.setIntProperty("tip", TipZahteva.BRISANJE_SOBE.ordinal());

                        producer.send(destination, zahtev);
                    } catch (JMSException ex) {
                        Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Message odgovor = consumer.receive();
                    if (odgovor instanceof ObjectMessage) {
                        try {
                            Object objekat = ((ObjectMessage) odgovor).getObject();
                            if (objekat != null) {
                                List<Soba> slist = apartman.getSobe();
                                for (Soba stek : slist) {
                                    if (stek.getId().equals(soba.getId())) {
                                        slist.remove(stek);
                                        break;
                                    }
                                }
                                panel.revalidate();
                                panel.repaint();
                            } else {

                            }
                        } catch (JMSException ex) {
                            Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                    }
                }

                private ActionListener init(Apartman a, Soba s, SobePanel panel) {
                    this.apartman = a;
                    this.panel = panel;
                    this.soba = s;
                    return this;
                }
            }.init(apartman, sobe.get(i), this));

            this.add(gp);
            this.add(op);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closingHandler(evt);
            }
        });

        jMenu1.setText("Home");

        jMenuItem1.setText("Unos sobe");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 902, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new SobeUnos(apartman).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void closingHandler(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closingHandler
        prodavac.Prodavac.apartmaniPanel.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_closingHandler


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables

    private void dohvatiSobe(Apartman a) {
// dohvatanje soba za apartman
        JMSContext context = prodavac.Prodavac.connectionFactory.createContext();

        Destination destination = prodavac.Prodavac.zahtevi;

        JMSConsumer consumer = context.createConsumer(prodavac.Prodavac.odgovori, Helpers.getId(prodavac.Prodavac.prodavac.getUsername(),
                prodavac.Prodavac.prodavac.getPassword()));
        JMSProducer producer = context.createProducer();

        ObjectMessage zahtev = context.createObjectMessage();
        try {
            zahtev.setObject(a);
            zahtev.setStringProperty("id", prodavac.Prodavac.prodavac.getUsername()
                    + prodavac.Prodavac.prodavac.getPassword());
            zahtev.setStringProperty("username", prodavac.Prodavac.prodavac.getUsername());
            zahtev.setStringProperty("password", prodavac.Prodavac.prodavac.getPassword());

            zahtev.setIntProperty("tip", TipZahteva.DOHVATANJE_SOBA_ZA_APARTMAN.ordinal());

            producer.send(destination, zahtev);
        } catch (JMSException ex) {
            Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        Message odgovor = consumer.receive();
        if (odgovor instanceof ObjectMessage) {

            try {
                Object objekat = ((ObjectMessage) odgovor).getObject();
                if (objekat != null) {
                    this.apartman = (Apartman) objekat;
                } else {
                }
            } catch (JMSException ex) {
                Logger.getLogger(SobeUnos.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
        }
    }
}
