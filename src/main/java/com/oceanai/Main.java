package com.oceanai;

import com.oceanai.servlet.GenerateServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * 入口.
 *
 * @author Xiong Raorao
 * @since 2018-06-25-14:54
 */
public class Main {

  public static void main(String[] args) throws Exception {
    Server server = new Server();
    ServerConnector connector = new ServerConnector(server);
    connector.setPort(8888);
    server.setConnectors(new Connector[]{connector});

    ServletContextHandler contextHandler = new ServletContextHandler();
    contextHandler.setContextPath("/");
    contextHandler.addServlet(GenerateServlet.class, "/generate");

    ResourceHandler handler = new ResourceHandler();  //静态资源处理的handler
    handler.setDirectoriesListed(true);  //会显示一个列表
    handler.setWelcomeFiles(new String[]{"index.html"});
    handler.setResourceBase("src/main/webapp");

    HandlerCollection handlerCollection = new HandlerCollection();
    //handlerCollection.setHandlers(new Handler[]{contextHandler, new DefaultHandler()});
    handlerCollection.setHandlers(new Handler[]{handler,contextHandler});
    server.setHandler(handlerCollection);
    //server.setHandler(contextHandler);
    //server.setHandler(handler);
    server.start();
    server.join();

  }
}
