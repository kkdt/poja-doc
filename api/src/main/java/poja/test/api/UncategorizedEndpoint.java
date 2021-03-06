/*
 * Copyright (c) 2020. thinh ho
 * This file is part of 'poja-doc' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.test.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import poja.core.annotation.Api;
import poja.core.annotation.ApiOperation;
import poja.core.annotation.ApiParam;

/**
 * Scenario where only operations are documented.
 */
@RestController
public class UncategorizedEndpoint {

    @ApiOperation(path = "/submit/{userid}", methods = { "POST" }, url = "http://javadoc/poja-sumbit.html", parameters = {
       @ApiParam(name = "userid", location = "url", required = true)
    })
    @RequestMapping(value = "/submit/{userid}", method = { RequestMethod.POST })
    public void submit(@RequestParam String userid) {
        // do something
    }

    @ApiOperation(path = "/submit/{userid}/{ssn}", methods = { "POST" }, url = "http://javadoc/poja-sumbit.html", parameters = {
        @ApiParam(name = "userid", location = "url", required = true),
        @ApiParam(name = "ssn", location = "url", required = true)
    })
    @RequestMapping(value = "/submit/{userid}/{ssn}", method = { RequestMethod.POST })
    public void updateSocial(@RequestParam String userid, @RequestParam String ssn) {
        // do something
    }
}
