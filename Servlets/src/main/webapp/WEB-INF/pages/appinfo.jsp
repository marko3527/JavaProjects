<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>

    <style>
          body {background-color: <%= session.getAttribute("pickedBgCol") %>}
          div {text-align: center;height: 95%;}
          h1 {font-size: 50px; color: gray;}
          p {font-size: 80px;}
          div1 {font-family: Arial; font-size: 30px; width: 100%;bottom: 0;height: 60px; text-align: center;}
          div2 {position: absolute; left: 0;}
          div3 {position: absolute; right: 0}
          a {text-decoration: none; font-size: 30px; color: gray;}
    </style>

    <body>
        <div>
            <h1>Application is active for :</h1>
            <%
            ServletContext sc = request.getServletContext();
            long startingMiliseconds = (long) sc.getAttribute("miliseconds");
            long currentMiliseconds = new Date().getTime();

            long milisecondsPassed = currentMiliseconds - startingMiliseconds;

            long second = (milisecondsPassed / 1000) % 60;
            long minute = (milisecondsPassed / (1000 * 60)) % 60;
            long hour = (milisecondsPassed / (1000 * 60 * 60)) % 24;

            String time = String.format("%02d:%02d:%02d", hour, minute, second);
            out.write("<p>" + time + "</p>");
            
        %>
        </div>
        <div1>
            <div2><a href="chooseColor">Background color chooser</a></div2>
            <div3><a href="info">Info</a></div3>
        </div1>
        
        
    </body>

</html>