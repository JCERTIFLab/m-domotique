/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.domotique.services;

import com.jcertif.domotique.entities.Equipement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @Produces({"application/json"})
    public Equipement find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Path("getAllEquipements")
    @Produces({"application/xml"})
    public List<Equipement> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
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
    @Produces({"application/xml"})
    public List<Equipement> findByPiece(@PathParam("piece_id") Integer piece_id) {
        javax.persistence.Query cq = getEntityManager().createNamedQuery("Equipement.findByPiece").setParameter("piece_id", piece_id);
        return (List<Equipement>) cq.getResultList();
    }

    @POST
    @Path("action/{id}/{etat}")
    @Produces({"application/json"})
    public String edit(@PathParam("id") Integer id, @PathParam("etat") String etat) {
        
        try {
            Equipement equipement = super.find(id);
            if(executeAction(equipement.getRelay(), etat)){
                equipement.setEtat(Boolean.valueOf(etat));
                return super.edit(equipement);
            }else
                return "{\"state\":\"erreur\"}";
        } catch (Exception ex) {
            return "{\"state\":\"erreur\"}";
        } 

    }
    
    public boolean executeAction(String pin, String etat){
        
        final String GPIO_OUT = "out";
        final String GPIO_ON = "1";
        final String GPIO_OFF = "0";
        
        boolean responce = false;
        
        try {
            // Open file handles to GPIO port unexport and export controls  
            FileWriter unexportFile = new FileWriter("/sys/class/gpio/unexport");
            FileWriter exportFile = new FileWriter("/sys/class/gpio/export");

            // Reset the port, if needed  
            File exportFileCheck = new File("/sys/class/gpio/gpio" + pin);
            if (exportFileCheck.exists()) {
                unexportFile.write(pin);
                unexportFile.flush();
            }

            // Set the port for use  
            exportFile.write(pin);
            exportFile.flush();

            // Open file handle to port input/output control  
            FileWriter directionFile = new FileWriter("/sys/class/gpio/gpio" + pin + "/direction");

            // Set port for output  
            directionFile.write(GPIO_OUT);
            directionFile.flush();

            // Set up a GPIO port as a command channel  
            FileWriter commandChannel = new FileWriter("/sys/class/gpio/gpio" + pin + "/value");
            
            if(etat.equals("1")){
                commandChannel.write(GPIO_ON);
                commandChannel.flush();
            }else{
                commandChannel.write(GPIO_OFF);
                commandChannel.flush();
            }
            
            responce = true;

        } catch (Exception exception) { }
        
        return responce;
        
    }

}
