package org.lobo.java.webapps;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class DataSourceHttpServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DatabaseLogic.init();
    }

    @Override
    public void destroy() {
        super.destroy();
        DatabaseLogic.destroy();
    }
}
