package security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class SecretUserReader {
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    public static byte[] readSecret() throws IOException{
        return Files.readAllBytes(Paths.get("/home/dimitrije/NetBeansProjects/securedService/securedAppExample/src/main/java/data/secret.json"));
    }
    
    public static HashMap readUsers() throws IOException{
        return mapper.readValue(new File("/home/dimitrije/NetBeansProjects/securedService/securedAppExample/src/main/java/data/users.json"), HashMap.class);
    }
}
