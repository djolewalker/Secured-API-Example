/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dimitrije
 */
public class Properties {

    private Map<String, String> storage;
    private static ObjectMapper mapper = new ObjectMapper();

    public Properties() {
        try {
            storage = mapper.readValue(new File("/home/dimitrije/NetBeansProjects/securedService/securedAppExample/src/main/java/data/properties.json"), HashMap.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getProperty(String propetrty) {
        String value = storage.get(propetrty);
        return value;
    }

}
