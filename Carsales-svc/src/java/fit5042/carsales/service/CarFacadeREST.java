/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.service;

import fit5042.carsales.Car;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author zipv5_000
 */
@Stateless
@Path("Car")
public class CarFacadeREST {
    @PersistenceContext(unitName = "Carsales-svcPU")
    private EntityManager em;
       
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getServiceDescription() {
        return "Smallwood Carsales APIs (v1.0.0)";
    }
    
    @GET
    @Path("/{VIN}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car find(@PathParam("VIN") String VIN) {
        return em.find(Car.class, VIN);
    }
}
