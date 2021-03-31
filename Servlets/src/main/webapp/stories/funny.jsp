<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
    <style>
        p {color: ${fontColor};}
        body {background-color: <%= session.getAttribute("pickedBgCol") %>}
        div {text-align: center;height: 100%; font-size: 60px;}
        div1 {font-family: Arial; font-size: 30px; width: 100%;bottom: 0;height: 60px; text-align: center;}
        div2 {position: absolute; left: 0;}
        div3 {position: absolute; right: 0}
        a {text-decoration: none; font-size: 30px; color: gray;}
    </style>

    <body>
        <div>
            <p>Ovo je smjesna prica!</p>
        </div>
        
        <div1>
            <div2><a href="chooseColor">Background color chooser</a></div2>
            <div3><a href="info">Info</a></div3>
        </div1>
    </body>

</html>