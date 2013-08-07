/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcertif.domotique.services;

import com.jcertif.domotique.entities.Equipement;
import java.io.File;
import java.io.FileWriter;
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
    @Produces({"application/json"})
    public List<Equipement> findByPiece(@PathParam("piece_id") Integer piece_id) {
        javax.persistence.Query cq = getEntityManager().createNamedQuery("Equipement.findByPiece").setParameter("piece_id", piece_id);
        return (List<Equipement>) cq.getResultList();
    }

    @POST
    @Path("action/{id}/{etat}")
    @Produces({"application/json"})
    public void edit(@PathParam("id") Integer id, @PathParam("etat") String etat) {
        
        Equipement equipement = super.find(id);
        if(action(equipement.getRelay(), etat)){
            equipement.setEtat(Boolean.valueOf(etat));
            super.edit(equipement);
        }
    }

    public boolean action(String GpioChannels, String state) {
       
        final String GPIO_OUT = "out";
        final String GPIO_ON = "1";
        final String GPIO_OFF = "0";
        
        boolean result = false;
    
        try {
            // Open file handles to GPIO port unexport and export controls  
            FileWriter unexportFile = new FileWriter("/sys/class/gpio/unexport");
            FileWriter exportFile = new FileWriter("/sys/class/gpio/export");

            // Loop through all ports if more than 1  
            // Reset the port, if needed  
            File exportFileCheck = new File("/sys/class/gpio/gpio" + GpioChannels);
            if (exportFileCheck.exists()) {
                unexportFile.write(GpioChannels);
                unexportFile.flush();
            }

            // Set the port for use  
            exportFile.write(GpioChannels);
            exportFile.flush();

            // Open file handle to port input/output control  
            FileWriter directionFile = new FileWriter("/sys/class/gpio/gpio" + GpioChannels + "/direction");

            // Set port for output  
            directionFile.write(GPIO_OUT);
            directionFile.flush();
            // Set up a GPIO port as a command channel  
            FileWriter commandChannel = new FileWriter("/sys/class/gpio/gpio" + GpioChannels + "/value");
            
            if(state.equals("0")){
                // HIGH: Set GPIO port ON  
                commandChannel.write(GPIO_ON);
                commandChannel.flush();
            }else{
                // LOW: Set GPIO port OFF  
                commandChannel.write(GPIO_OFF);
                commandChannel.flush();
            }
            
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        return result;
    }
}
