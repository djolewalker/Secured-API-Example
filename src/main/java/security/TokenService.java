package security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.User;

public class TokenService {

    private static ObjectMapper mapper = new ObjectMapper();
    private static Map tokens;


    public static void readTokens() throws IOException{
        tokens = mapper.readValue(new File("/home/dimitrije/NetBeansProjects/securedService/securedAppExample/src/main/java/data/tokens.json"), HashMap.class);
    }
    
    public static void writeToken() throws IOException{
        mapper.writeValue(new File("/home/dimitrije/NetBeansProjects/securedService/securedAppExample/src/main/java/data/tokens.json"), tokens);
    }
    
    public static void addUserToken(User us, String token){
        tokens.put(us.getUsername(), token);
    }
    
    public static void removeUserToken(User us){
        tokens.remove(us.getUsername());
    }
    
    public static String getToken(User us){
        return tokens.get(us.getUsername()).toString();
    }
    
    public static boolean checkUser(User us){
        return tokens.containsKey(us.getUsername());
    }
        
}
