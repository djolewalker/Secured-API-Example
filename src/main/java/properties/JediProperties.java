package properties;

import java.io.IOException;

import javax.inject.Singleton;

@Singleton
public class JediProperties implements IJediProperties {
    
    private EnhancedProperties properties;
    private volatile boolean initialized = false;

    private String PFILE = "/jedi.properties";

    public JediProperties() {
        load();
    }

    public void load() {
        if (this.initialized) {
            return;
        }

        this.properties = new EnhancedProperties();
        try {
            this.properties.load(PFILE, false);
        } catch (IOException ex) {
            throw new IllegalStateException("## Fatal: Unable to load properties files");
        }

        this.initialized = true;
    }
    
    @Override
    public String getString(String key, String def) {
        String v = this.properties.getProperty(key, null);
        if (v == null) {
            return def;
        }
        return v;
    }
}
