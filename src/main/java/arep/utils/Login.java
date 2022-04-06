package arep.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Login {

    private Map<String, String> credenciales;
    private static Login instance;

    private Login() {
        credenciales = new HashMap<>();
        //arep1234
        credenciales.put("admin", "52d1ba9b898c6925d25012f2b77ce854d5219160adc644ceed22c060ebaa3965");
        //eci1234
        credenciales.put("usuario1", "24468c88c48124a26c362580994aa39238b529bed2f17200d9b97e03aeb6ef23");
        //ingsistemas
        credenciales.put("usuario2", "dd21726da3cebd24c535c7baaf18d810ce38c46f58dd833f05a63403990742e6");
    }

    private byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private String toHexString(byte[] input) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, input);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public boolean authenticate(String username, String password){
        boolean result = false;
        try {
            String passwordSHA = toHexString(getSHA(password));
            if(credenciales.get(username).equals(passwordSHA)){
                result = true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

}
