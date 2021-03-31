<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true"  %>

<html>
    <style>
         body {background-color: <%= session.getAttribute("pickedBgCol") %>}
         table {text-align: center; background-color: lightgray; font-size: 30px; font-family: Arial; border-collapse: collapse; width: 100%;}
         div {text-align: center; width: 100%;position: absolute;}
    
    </style>

    <body>
        <div>
            <table border='1'>
                <tr><td>Angle</td><td>COS(x)</td><td>SIN(x)</td></tr>
                <c:forEach var="element" items="${cosValues}" varStatus="status">
                    <tr><td>${angles[status.index]}</td><td>${element}</td><td>${sinValues[status.index]}</td></tr>
                </c:forEach>
            </table>
        </div>
    </body>

</html>