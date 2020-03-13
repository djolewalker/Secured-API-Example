package controllers;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
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
        classes.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        classes.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        classes.add(SwaggerConfigurator.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        final Set<Object> classes = new HashSet<>();
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowCredentials(true);
        corsFilter.setAllowedHeaders("Origin, Content-Type, Accept, Authorization, "
                + "x-requested-with, csrf_nonce, Access-Control-Allow-Origin, "
                + "admin_auth_token, auth_token, base_uuid , api_key, withCredentials, "
                + "sec-websocket-key, sec-websocket-extensions, connection, upgrade, sec-websocket-version, jwt-token, Set-Cookie"
        );
        corsFilter.setAllowedMethods("GET, POST, PUT, DELETE, OPTIONS, HEAD");
        corsFilter.setCorsMaxAge(86400);
        classes.add(corsFilter);
        return classes;
    }
}
