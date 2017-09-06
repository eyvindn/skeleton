package controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/netid")
@Produces(MediaType.APPLICATION_JSON)
public class NetIdController {

    @GET
    public String getNetId() {
        return "een7";
    }
}
