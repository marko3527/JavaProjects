<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>

    <style>
          body {background-color: <%= session.getAttribute("pickedBgCol") %>;}
          div1 {font-family: Arial; font-size: 30px; width: 100%;bottom: 0;height: 60px; text-align: center;}
          div2 {position: absolute; left: 0;}
          div3 {position: absolute; right: 0}
          div {font-family: Arial; text-align: center; font-size: 30px; color: black; height: 95%;}
          a {text-decoration: none; font-size: 30px; color: gray;}

    </style>
   
    <body>
        <div>
            <a href="trigonometric?a=0&b=90">Angle from 0 to 90</a><br>
            <a href="story">Prica</a><br>
            <a href="powers?a=1&b=100&n=3">Tablica.XLS</a><br><br><br><br>
            
            <form action="trigonometric" method="GET">  
                Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
                Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>  
                <input type="submit" value="Tabeliraj"><input type="reset" value="Reset"></form>
        </div>
        <div1>
            <div2><a href="chooseColor">Background color chooser</a></div2>
            <div3><a href="info">Info</a></div3>
        </div1>
    </body>
</html>