package com.updis.rest;

import com.updis.entity.Message;

import javax.ws.rs.*;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/27/13
 * Time: 9:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/{uuid}/messages")
public class MessageResource {
    @GET
    @Produces("application/json")
    @Path("/{categoryName}")
    public Message getMessages(@PathParam("uuid") String uuid, @PathParam("categoryName") String categoryName) {
        return new Message(uuid, categoryName);
    }
}
