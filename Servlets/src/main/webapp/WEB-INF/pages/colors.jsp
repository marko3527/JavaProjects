<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true"  %>

<html>
    <style>
         body {text-align:center; background-color: <%= session.getAttribute("pickedBgCol") %>}
         a {text-decoration: none; font-size: 30px; color: black;}
         h1 {font-size: 30px; font-family: Arial; color: gray}
         
    </style>

    <body>
        <h1>Select a background color</h1>
        <a href="/webapp2/setcolor?color=salmon">
            <h1>RED</h1>
        </a>
        
        <a href="/webapp2/setcolor?color=whitesmoke">
           <h1>   WHITE</h1> 
        </a>
         
        <a href="/webapp2/setcolor?color=goldenrod">
            <h1>  YELLOW</h1>
        </a>
        
        <a href="/webapp2/setcolor?color=powderblue">
            <h1>  BLUE</h1>
        </a>
    </body>
</html>



