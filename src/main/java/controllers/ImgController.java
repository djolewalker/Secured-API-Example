/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import io.swagger.annotations.Api;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import model.ImgPojo;
import org.apache.directory.api.util.FileUtils;
import security.Authorize;

/**
 *
 * @author dimitrije
 */
@Authorize
@Path("/images")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Api(value = "images", tags = {"images"})
public class ImgController {

    public ArrayList<ImgPojo> images = new ArrayList();
    public String imgFolder = "/home/dimitrije/Works/Secured-API-Example/images";

    @GET
    public Response getImages() {
        try {
            images.clear();

            File folder = new File(imgFolder);
            ArrayList<File> files = new ArrayList(Arrays.asList(folder.listFiles()));
            for (File file : files) {
                byte[] content = FileUtils.readFileToByteArray(file);
                ImgPojo img = new ImgPojo(file.getName(), Base64.getEncoder().encodeToString(content));
                images.add(img);
            }
            return Response.ok(images).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
