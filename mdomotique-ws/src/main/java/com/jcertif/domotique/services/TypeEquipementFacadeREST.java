/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.domotique.services;

import com.jcertif.domotique.entities.TypeEquipement;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Stateless
@Path("typeequipements")
public class TypeEquipementFacadeREST extends AbstractFacade<TypeEquipement> {


    public TypeEquipementFacadeREST() {
        super(TypeEquipement.class);
    }

    @POST
    @Path("add")
    @Override
    @Consumes({"application/xml", "application/json"})
    public String create(TypeEquipement entity) {
        return super.create(entity);
    }

    @POST
    @Path("update")
    @Override
    @Consumes({"application/xml", "application/json"})
    public String edit(TypeEquipement entity) {
        return super.edit(entity);
    }

    @POST
    @Path("delete/{id}")
    public String remove(@PathParam("id") Integer id) {
        return super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({ "application/json"})
    public TypeEquipement find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Path("getAllTypesEquipements")
    @Produces({ "application/xml"})
    public List<TypeEquipement> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({ "application/json"})
    public List<TypeEquipement> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    
}
