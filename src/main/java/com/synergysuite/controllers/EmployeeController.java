package com.synergysuite.controllers;

import com.synergysuite.jpa.Employee;
import com.synergysuite.services.EmployeeEJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/employees")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class EmployeeController {

    @Inject
    private EmployeeEJB ejb;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getAllEmployees(@QueryParam("name") String fName) {
        List<Employee> allEmployees = ejb.findEmployeeByName(fName);
        return Response.ok(allEmployees, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") Long id) {
        Employee employee = ejb.findEmployeeById(id);

        return Response.ok(employee, MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addEmployee(Employee employee) {

        ejb.addEmployee(employee);

        URI employeeUri = uriInfo.getAbsolutePathBuilder().path(employee.getId().toString()).build();
        return Response.ok(employeeUri).build();
    }

    @PUT
    public Response updateEmployee(Employee employee) {
        ejb.updateEmployee(employee);

        URI employeeUri = uriInfo.getAbsolutePathBuilder().path(employee.getId().toString()).build();
        return Response.ok(employeeUri).build();
    }

    @DELETE
    public Response deleteEmployee(Employee employee) {
        ejb.deleteEmployee(employee);

        URI employeeUri = uriInfo.getAbsolutePathBuilder().build();
        return Response.ok(employeeUri).build();
    }
}
