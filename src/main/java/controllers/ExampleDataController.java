/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.Authorize;
import model.*;

/**
 * REST Web Service
 *
 * @author dimitrije
 */
@Path("examples")
@RequestScoped
public class ExampleDataController {

    private ObjectMapper mapper = new ObjectMapper();
    private HashMap<String, ExampleData> base;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public ExampleDataController() {
        base = new HashMap<String, ExampleData>();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getText() {
        try {
            read();
            Collection conList = new ArrayList();
            for (String key : base.keySet()) {
                conList.add(base.get(key));
            }
            return Response.ok(conList).build();
        } catch (IOException ex) {
            System.out.println(ex);
            return Response.serverError().build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Authorize
    public Response getTextById(@PathParam("id") int id) {
        try {
            read();
            return Response.ok(base.get(String.valueOf(id))).build();
        } catch (IOException ex) {
            System.out.println(ex);
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Response postText(String content) {
        try {
            read();
            ExampleData exam = mapper.readValue(content, ExampleData.class);
            base.put(String.valueOf(exam.getId()), exam);
            save();
            return Response.ok(exam).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.notModified(e.getMessage()).build();
        }

    }

    private void save() throws IOException {
        mapper.writeValue(new File("/home/dimitrije/NetBeansProjects/securedService/securedAppExample/src/main/java/data/exampleData.json"), base);
    }

    private void read() throws IOException {
        base = mapper.readValue(new File("/home/dimitrije/NetBeansProjects/securedService/securedAppExample/src/main/java/data/exampleData.json"), HashMap.class);
    }
}
