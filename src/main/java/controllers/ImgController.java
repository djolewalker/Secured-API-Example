/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import model.ImgPojo;
import security.Authorize;

/**
 *
 * @author dimitrije
 */
@Authorize
@Path("/images")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ImgController {

    public ArrayList<ImgPojo> images = new ArrayList();
    
    @GET
    public Response getImages() {
        images.clear();
        images.add(new ImgPojo(1, "img/img1.jpg"));
        images.add(new ImgPojo(2, "img/img2.jpg"));
        images.add(new ImgPojo(3, "img/img3.jpg"));
        images.add(new ImgPojo(4, "img/img4.jpg"));
        return Response.ok(images).build();
    }
    
}
