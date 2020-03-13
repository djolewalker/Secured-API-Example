package security;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@NameBinding
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = false, dataType = "string", paramType = "header")})
public @interface Authorize {
}
