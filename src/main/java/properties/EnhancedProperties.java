/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnhancedProperties extends Properties {

    private static final Pattern rxPLACEHOLDERS = Pattern.compile("\\$\\{(?<placeholder>[A-Za-z0-9\\.\\-_]+)\\}");

    @Override
    public String getProperty(String key, String defaultValue) {
        String value = super.getProperty(key, null);
        if (value == null) {
            if (defaultValue != null) {
                return this.replacePlaceholders(defaultValue);
            }

            return null;
        }

        return this.replacePlaceholders(value);
    }

    @Override
    public String getProperty(String key) {
        String value = super.getProperty(key);
        if (value != null) {
            return this.replacePlaceholders(value);
        }

        return null;
    }

    public void load(String propertiesFile, boolean optional) throws IOException {

        InputStream is = null;
        try {
            String propFile = "/home/dimitrije/Works/Secured-API-Example/src/main/java/conf" + propertiesFile;
            is = new FileInputStream(new File(propFile));
        } catch (FileNotFoundException e) {
        }

        if (is == null) {
            // If property file could not be found, as a fallback try it from resources folder
            String propFile = propertiesFile;
            is = EnhancedProperties.class.getResourceAsStream(propFile);
        }

        if (is == null) {
            if (!optional) {
                throw new RuntimeException("## FATAL: Unable to find a property file!!!");
            }
            return;
        }

        try {
            this.load(is);
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    private String replacePlaceholders(String value) {

        if (value == null) {
            return null;
        }

        String result = value;

        Matcher matcher = rxPLACEHOLDERS.matcher(value);

        while (matcher.find()) {
            String placeholder = matcher.group("placeholder");
            String placeholderValue;

            if (matcher.group(0).startsWith("${env.")) {
                String envar = placeholder.substring(4, placeholder.length());
                placeholderValue = System.getenv(envar);
            } else {
                placeholderValue = this.getProperty(placeholder, null);
            }

            if (placeholderValue == null) {
                continue;
            }

            result = result.replaceAll("\\$\\{" + placeholder + "\\}", placeholderValue);
        }

        return result;
    }

    public String[] getList(String key) {
        String value = this.getProperty(key);
        if (value == null) {
            return null;
        }

        return value.split(",");
    }

}
