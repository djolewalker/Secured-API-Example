package controllers;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("webapi")
public class JAXRSConfiguration extends Application {

    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(ExampleDataController.class);
        classes.add(UserController.class);
        return classes;
    }
}
