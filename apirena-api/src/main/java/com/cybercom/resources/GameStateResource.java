package com.cybercom.resources;

import com.cybercom.core.GameState;
import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/game")
@Produces(MediaType.APPLICATION_JSON)
@Api
public class GameStateResource {

    GameState state = new GameState();

    @Path("/state")
    @GET
    public GameState sayHello() {
        return state;
    }
}
