/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.domotique.services;

import com.jcertif.domotique.entities.Equipement;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Stateless
@Path("equipements")
public class EquipementFacadeREST extends AbstractFacade<Equipement> {

    public EquipementFacadeREST() {
        super(Equipement.class);
    }

    @POST
    @Path("add")
    @Override
    @Consumes({"application/xml", "application/json"})
    public String create(Equipement entity) {
        return super.create(entity);
    }

    @POST
    @Path("update")
    @Override
    @Consumes({"application/xml", "application/json"})
    public String edit(Equipement entity) {
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
    public Equipement find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Path("getAllEquipements")
    @Produces({"application/json"})
    public List<Equipement> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({ "application/json"})
    public List<Equipement> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    @POST
    @Path("getEquipementsByRoom/{piece_id}")
    @Produces({"application/json"})
    public List<Equipement> findByPiece(@PathParam("piece_id") Integer piece_id) {
       javax.persistence.Query cq = getEntityManager().createNamedQuery("Equipement.findByPiece").setParameter("piece_id", piece_id);
        return (List<Equipement>)cq.getResultList();
    }
    
    @POST
    @Path("action/{id}/{etat}")
    @Produces({"application/json"})
    public void edit(@PathParam("id") Integer id, @PathParam("etat") String etat) {
        Equipement equipement = super.find(id);
        equipement.setEtat(Boolean.valueOf(etat));
        super.edit(equipement);
    }
}
