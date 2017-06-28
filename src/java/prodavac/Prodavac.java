package prodavac;

import frames.LoginPanel;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

public class Prodavac {

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
                new LoginPanel().setVisible(true);
            }
        });

        System.out.println("Prodavac pokrenut");
    }

}
