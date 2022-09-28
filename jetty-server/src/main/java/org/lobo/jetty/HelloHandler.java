//
// ========================================================================
// Copyright (c) 1995-2021 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under the
// terms of the Eclipse Public License v. 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
// which is available at https://www.apache.org/licenses/LICENSE-2.0.
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

//package org.eclipse.jetty.demos;
package org.lobo.jetty;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloHandler extends AbstractHandler {
    private final static Logger LOGGER = Logger.getLogger("hello-handler");
    final String greeting;

    public HelloHandler(String greeting) {
        this.greeting = greeting;
    }

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        LOGGER.info("Received [" + request.getRemoteHost() + ":" + request.getRemotePort() + "] : " +
                request.getRequestURI());
        String name = request.getRequestURI().substring(1);
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();

        out.println("~~~ " + greeting + " " + name + " ~~~");

        baseRequest.setHandled(true);
    }
}
