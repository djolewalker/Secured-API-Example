package controllers;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import security.JwtFilter;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@ApplicationPath("webapi")
public class JAXRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(ExampleDataController.class);
        classes.add(UserController.class);
        classes.add(JwtFilter.class);
        classes.add(ImgController.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        final Set<Object> classes = new HashSet<>();
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowCredentials(true);
        corsFilter.setAllowedHeaders("Origin, content-type, accept, Authorization, "
                + "x-requested-with, csrf_nonce, Access-Control-Allow-Origin, "
                + "admin_auth_token, auth_token, base_uuid"
                + "sec-websocket-key, sec-websocket-extensions, connection, upgrade, sec-websocket-version"
        );
        corsFilter.setAllowedMethods("GET, POST, PUT, DELETE, OPTIONS, HEAD");
        corsFilter.setCorsMaxAge(86400);
        classes.add(corsFilter);
        return classes;
    }
}
