/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.domotique.services;

import com.jcertif.domotique.entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.codehaus.jackson.annotate.JsonAutoDetect;


@Stateless
@Path("users")
@JsonAutoDetect
public class UserFacadeREST extends AbstractFacade<User> {

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Path("add")
    @Override
    @Consumes({"application/xml", "application/json"})
    public String create(User entity) {
        return super.create(entity);
    }

    @POST
    @Path("update")
    @Override
    @Consumes({"application/xml", "application/json"})
    public String edit(User entity) {
        return super.edit(entity);
    }

    @POST
    @Path("delete/{id}")
    public String remove(@PathParam("id") Integer id) {
        return super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("getAllUsers")
    @Override
    @Produces({"application/json"})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    @POST
    @Path("auth/{username}/{password}")
    @Produces({"application/json"})
    public User authentificate(@PathParam("username") String username, @PathParam("password") String password) {
       
            javax.persistence.Query cq = getEntityManager().createNamedQuery("User.findByLoginPassword").setParameter("login", username).setParameter("password", password);
            return ((User) cq.getSingleResult()).getUser();
       
    }
    
}
