package com.jhipster.itprogress.pfe.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClientForwardController {

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code index.html}.
     * This is required for Angular/HTML5 routing in a single-page application.
     *
     * @param path the requested path (ignored but required to bind the wildcard)
     * @return forward to client index.html
     */
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String forward(@PathVariable String path) {
        return "forward:/";
    }
}
