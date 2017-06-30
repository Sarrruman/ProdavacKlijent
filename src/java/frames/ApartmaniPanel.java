package frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JFrame;
import messages.Login;
import prodavac.Prodavac;
import utils.Helpers;
import utils.TipZahteva;
import beans.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ApartmaniPanel extends javax.swing.JFrame {

    private JLabel status;

    public void refresh() {
        if (!this.isVisible()) {
            this.setVisible(true);
        }
        Point point = this.getLocationOnScreen();
        prodavac.Prodavac.apartmaniPanel = new ApartmaniPanel();
        prodavac.Prodavac.apartmaniPanel.setLocation(point);
        prodavac.Prodavac.apartmaniPanel.setVisible(true);

        this.dispose();
    }

    @SuppressWarnings("Convert2Lambda")
    public ApartmaniPanel() {
        initComponents();

        List<Apartman> apartmani = prodavac.Prodavac.prodavac.getApartmani();

        this.setLayout(new GridLayout(apartmani.size() + 1, 3));
        status = new JLabel("");
        this.add(status);
        this.add(new JLabel());
        this.add(new JLabel());

        for (int i = 0; i < apartmani.size(); i++) {
            JButton glavno = new JButton(apartmani.get(i).getIme());
            JButton obrisi = new JButton("Obrisi");
            JButton sobe = new JButton("Sobe");

            // paneli za resizing
            JPanel gp = new JPanel(), op = new JPanel(), sp = new JPanel();
            gp.add(glavno);
            op.add(obrisi);
            sp.add(sobe);

            glavno.setPreferredSize(new Dimension(200, 40));
            obrisi.setPreferredSize(new Dimension(200, 40));
            sobe.setPreferredSize(new Dimension(200, 40));

            glavno.addActionListener(new ActionListener() {
                private Apartman a;
                private ApartmaniPanel panel;

                public void actionPerformed(ActionEvent e) {
                    new ApartmanIzmena(a).setVisible(true);
                    panel.setVisible(false);
                }

                private ActionListener init(Apartman a, ApartmaniPanel panel) {
                    this.a = a;
                    this.panel = panel;
                    return this;
                }
            }.init(apartmani.get(i), this));

            obrisi.addActionListener(new ActionListener() {
                private Apartman a;
                private ApartmaniPanel panel;

                public void actionPerformed(ActionEvent e) {
                    JMSContext context = Prodavac.connectionFactory.createContext();

                    Destination destination = Prodavac.zahtevi;
                    String username = prodavac.Prodavac.prodavac.getUsername();
                    String password = prodavac.Prodavac.prodavac.getPassword();

                    JMSConsumer consumer = context.createConsumer(Prodavac.odgovori, Helpers.getId(username, password));
                    JMSProducer producer = context.createProducer();

                    ObjectMessage zahtev = context.createObjectMessage();
                    try {
                        zahtev.setStringProperty("id", username + password);

                        zahtev.setObject(a);
                        zahtev.setIntProperty("tip", TipZahteva.BRISANJE_APARTMANA.ordinal());

                        producer.send(destination, zahtev);
                    } catch (JMSException ex) {
                        Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Message odgovor = consumer.receive();
                    if (odgovor instanceof ObjectMessage) {
                        try {
                            Object objekat = ((ObjectMessage) odgovor).getObject();
                            if (objekat != null) {
                                status.setText("Uspesno");
                                List<Apartman> aps = prodavac.Prodavac.prodavac.getApartmani();
                                for (Apartman atek : aps) {
                                    if (atek.getId().equals(a.getId())) {
                                        aps.remove(atek);
                                        break;
                                    }
                                }
                                panel.refresh();
                            } else {
                                status.setText("Morate prvo obrisati sve sobe");
                            }
                        } catch (JMSException ex) {
                            Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                    }
                }

                private ActionListener init(Apartman a, ApartmaniPanel panel) {
                    this.a = a;
                    this.panel = panel;
                    return this;
                }
            }.init(apartmani.get(i), this));

            sobe.addActionListener(new ActionListener() {
                private Apartman a;
                private ApartmaniPanel panel;

                public void actionPerformed(ActionEvent e) {
                    prodavac.Prodavac.sobePanel = new SobePanel(a);
                    prodavac.Prodavac.sobePanel.setVisible(true);
                    panel.setVisible(false);
                }

                private ActionListener init(Apartman a, ApartmaniPanel panel) {
                    this.a = a;
                    this.panel = panel;
                    return this;
                }
            }.init(apartmani.get(i), this));

            this.add(gp);
            this.add(op);
            this.add(sp);
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

        jMenuItem1.setText("Unos apartmana");
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
            .addGap(0, 774, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 626, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closingHandler(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closingHandler
        prodavac.Prodavac.homePanel.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_closingHandler

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new ApartmanUnos().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ApartmaniPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApartmaniPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApartmaniPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApartmaniPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApartmaniPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables
}
