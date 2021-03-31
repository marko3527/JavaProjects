<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true"  %>


<html>
    <style>
         body {background-color: <%= session.getAttribute("pickedBgCol") %>}
         div {text-align: center;height: 95%; font-size: 60px}
         h1 {font-family: Arial; font-size: 30px;color: gray;}
         p {font-family: Arial; font-size: 20px;color: black;}
         div1 {font-family: Arial; font-size: 30px; width: 100%;bottom: 0;height: 60px; text-align: center;}
         div2 {position: absolute; left: 0;}
         div3 {position: absolute; right: 0}
         a {text-decoration: none; font-size: 30px; color: gray;}
        
    </style>

    <body>
        <div>
            <h1>OS usage</h1>
            <p>Here are the results of OS usage in survey that we completed.</p>
            <img src = http://localhost:8080/webapp2/reportImage></img>
        </div>
        
        <div1>
            <div2><a href="chooseColor">Background color chooser</a></div2>
            <div3><a href="info">Info</a></div3>
        </div1>
    </body>

</html>