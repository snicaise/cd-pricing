package com.polaris.cd.pricing.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Strings;
import com.polaris.cd.pricing.api.ErrorMessage;
import com.polaris.cd.pricing.api.Pricing;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 *
 */
@Path("pricing")
@Produces(MediaType.APPLICATION_JSON)
public class PricingResource {

    @GET
    @Path("price")
    @Timed(name = "pricing.price", absolute = true)
    public Response price(@QueryParam("origin") final String origin,
                          @QueryParam("destination") final String destination)
    {
        if (Strings.isNullOrEmpty(origin)) {
            return Response.status(BAD_REQUEST)
                .entity(new ErrorMessage("param 'origin' is mandatory"))
                .build();
        }
        if (Strings.isNullOrEmpty(destination)) {
            return Response.status(BAD_REQUEST)
                .entity(new ErrorMessage("param 'destination' is mandatory"))
                .build();
        }
        if (origin.equalsIgnoreCase(destination)) {
            return Response.status(BAD_REQUEST)
                .entity(new ErrorMessage("'origin' and 'destination' must be different"))
                .build();
        }

        Pricing pricing = new Pricing(origin, destination, new Random().nextInt(600));
        return Response.ok()
            .entity(pricing)
            .build();
    }

}
