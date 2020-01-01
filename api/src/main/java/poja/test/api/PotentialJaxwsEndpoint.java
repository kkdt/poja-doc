/*
 * Copyright (c) 2020. thinh ho
 * This file is part of 'poja-doc' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.test.api;

import javax.ws.rs.Path;

/**
 * Contains JAXRS class-level annotation.
 */
@Path("/potential/jaxws")
public class PotentialJaxwsEndpoint {

    public String info() {
        return "potential endpoint";
    }
}
