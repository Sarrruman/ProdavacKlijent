package prodavac;

import frames.ApartmanUnos;
import frames.ApartmaniPanel;
import frames.HomePanel;
import frames.LoginPanel;
import frames.PromenaKorisnickihPodataka;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

public class Prodavac {

    public static ApartmanUnos apartmanUnos;
    public static ApartmaniPanel apartmaniPanel;
    public static HomePanel homePanel;
    public static LoginPanel loginPanel;
    public static PromenaKorisnickihPodataka promenaPodataka;

    @Resource(lookup = "Zahtevi")
    public static Queue zahtevi;
    @Resource(lookup = "Odgovori")
    public static Topic odgovori;
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public static ConnectionFactory connectionFactory;

    public static beans.Prodavac prodavac;

    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                loginPanel = new LoginPanel();
                loginPanel.setVisible(true);
            }
        });

        System.out.println("Prodavac pokrenut");
    }

}
