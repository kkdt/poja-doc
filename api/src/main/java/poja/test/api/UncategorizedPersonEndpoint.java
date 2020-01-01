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

@RestController
public class UncategorizedPersonEndpoint {

    @ApiOperation(path = "/submit/{person}", methods = { "POST" }, url = "http://javadoc/poja-sumbit.html", parameters = {
       @ApiParam(name = "userid", location = "url", required = true)
    })
    @RequestMapping(value = "/submit/{person}", method = { RequestMethod.POST })
    public void person(@RequestParam String person) {
        // do something
    }
}
