/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'api' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.test.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import poja.core.annotation.Api;
import poja.core.annotation.ApiOperation;

@RestController
@Api(name = "poja", value = "/poja", description = "POJA Annotation Test Endpoint")
public class PojaEndpoint {

    @ApiOperation(path = "/info", methods = { "GET" }, produces = { "test/plain" } )
    @RequestMapping(value = "/info", method = { RequestMethod.GET }, produces = { "test/plain" })
    public String info() {
        return "poja endpoint";
    }

    @ApiOperation(path = "/submit", methods = { "POST" } )
    @RequestMapping(value = "/submit", method = { RequestMethod.POST })
    public void submit() {
        // do something
    }
}
