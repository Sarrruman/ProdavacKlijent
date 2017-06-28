
package utils;
import prodavac.Prodavac;
/**
 *
 * @author malenicn
 */
public class Helpers {
    public static String getId() {
        return "id = '" + Prodavac.prodavac.getUsername() + Prodavac.prodavac.getPassword() + "'";
    }
    
    public static String getId(String username, String password) {
        return "id = '" + username + password + "'";
    }
    
}
