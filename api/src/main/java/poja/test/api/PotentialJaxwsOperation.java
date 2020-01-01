/*
 * Copyright (c) 2020. thinh ho
 * This file is part of 'poja-doc' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.test.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Contains JAXRS method-level annotation.
 */
public class PotentialJaxwsOperation {

    @GET @Path("info")
    public String info() {
        return "potential endpoint";
    }
}
