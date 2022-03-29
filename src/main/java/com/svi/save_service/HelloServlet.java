package com.svi.save_service;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.svi.data.*;

public class HelloServlet extends HttpServlet {
    public HelloServlet() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Cache-Control", "no-cache");
        String reqNm = req.getParameter("name");
        PrintWriter out = resp.getWriter();
        
        out.print("Hello save game");
        if (reqNm == null) {
            out.print("Hello <NULL>!");
        } else {
            out.print("Hello " + reqNm + "!");
        }
    }
}
