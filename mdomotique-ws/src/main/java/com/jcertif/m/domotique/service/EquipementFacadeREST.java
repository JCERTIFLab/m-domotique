
package com.jcertif.m.domotique.service;

import com.jcertif.m.domotique.entities.Equipement;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;



@Path("equipement")
public class EquipementFacadeREST extends AbstractFacade<Equipement> {
   
    public EquipementFacadeREST() {
        super(Equipement.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Equipement entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Equipement entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Equipement find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Equipement> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
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
    @Path("{piece_id}")
    @Produces({"application/xml", "application/json"})
    public List<Equipement> findByPiece(@PathParam("piece_id") Integer piece_id) {
       javax.persistence.Query cq = getEntityManager().createNamedQuery("Equipement.findByPiece").setParameter("piece_id", piece_id);
        return (List<Equipement>)cq.getResultList();
    }
    
}
