<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>

    <style>
          body {text-align: center; background-color: <%= session.getAttribute("pickedBgCol") %>}
          h1 {color: gray; font-size: 30px;font-family: Arial;}
          p {font-size: 20px; font-family: Arial;}
          div {text-align: center;height: 95%;}
          a {text-decoration: none; color: gray; font-size: 30px;font-family: Arial;}
          div1 {font-family: Arial; font-size: 30px; width: 100%;bottom: 0;height: 60px; text-align: center;}
          div2 {position: absolute; left: 0;}
          div3 {position: absolute; right: 0}
    </style>

    <body>
        <div>
            <h1>Glasanje za omiljeni bend:</h1>
            <p>Od sljedećih bendova, koji Vam je najdraži? Kliknite na linke kako biste glasali!</p>
            <c:forEach var = "id" items = "${ids}" varStatus = "status">
                <a href="glasanje-glasaj?id=${id}">${names[status.index]}</a><br><br><br><br><br>
            </c:forEach>
        </div>
        <div1>
            <div2><a href="chooseColor">Background color chooser</a></div2>
            <div3><a href="info">Info</a></div3>
        </div1>
        
    </body>
</html>