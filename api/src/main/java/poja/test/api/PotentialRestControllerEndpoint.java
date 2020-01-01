/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-doc' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.test.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PotentialRestControllerEndpoint {

    @RequestMapping(value = "/potential/info", method = { RequestMethod.GET }, produces = { "test/plain" })
    public String info() {
        return "potential endpoint";
    }

    @RequestMapping(value = "/potential/submit", method = { RequestMethod.POST })
    public void submit(PojaModel m) {
        // do something
    }
}
