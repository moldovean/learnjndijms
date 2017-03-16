package com.endava.Servlets;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by avrabie on 3/15/2017.
 */
@WebServlet("/start")
public class StarterServlet extends HttpServlet{
    @Resource(lookup = "jms/IMQ")
    ConnectionFactory connectionFactory;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        pw.println("This is the Servlet talking!");
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("MyQueue");
            MessageProducer messageProducer = session.createProducer(queue);

            TextMessage textMessage = session.createTextMessage("Privet, Victor! :D ");
            messageProducer.send(textMessage);
            System.out.println("Hopefully the MQ received it");
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {

        }


    }
}
