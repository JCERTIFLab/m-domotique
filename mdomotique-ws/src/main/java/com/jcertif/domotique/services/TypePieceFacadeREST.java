/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.domotique.services;

import com.jcertif.domotique.entities.TypePiece;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author FirasGabsi
 */
@Stateless
@Path("typepieces")
public class TypePieceFacadeREST extends AbstractFacade<TypePiece> {

    public TypePieceFacadeREST() {
        super(TypePiece.class);
    }

    @POST
    @Path("add")
    @Override
    @Consumes({ "application/json"})
    public void create(TypePiece entity) {
        super.create(entity);
    }

    @POST
    @Path("update")
    @Override
    @Consumes({ "application/json"})
    public void edit(TypePiece entity) {
        super.edit(entity);
    }

    @POST
    @Path("delete/{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({ "application/json"})
    public TypePiece find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({ "application/json"})
    public List<TypePiece> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({ "application/json"})
    public List<TypePiece> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    
}
