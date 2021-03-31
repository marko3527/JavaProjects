<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>

    <style>
          body {text-align:center; background-color:  <%= session.getAttribute("pickedBgCol") %>}
          table {text-align: center; border-collapse: collapse; font-size: 30px; font-family: Arial; background-color: lightgray;
                 width:50%; margin-left: 25%; margin-right: 25%}
          div {text-align: center;height: 100%;}
          h2 {font-family: Arial;}
          a {text-decoration: none; color: gray; font-size: 30px;font-family: Arial;}
          div1 {font-family: Arial; font-size: 30px; width: 100%;bottom: 0;height: 60px; text-align: center;}
          div2 {position: absolute; left: 0;}
          div3 {position: absolute; right: 0}
    </style>

    <body>
        <div>
            <table border = '1'>
                <tr><td>Band</td><td>Broj glasova</td></tr>
                <c:forEach var="name" items="${names}" varStatus="status">
                    <tr><td>${name}</td><td> ${results[status.index]}</td></tr>
                </c:forEach>
            </table>
    
            <h2>Grafički prikaz rezultata</h2>
            <img alt="Pie chart" src=http://localhost:8080/webapp2/glasanje-grafika width="600" height = "600" />
    
            <h2>Rezultati u XLS formatu</h2>
            <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>


            <h2>Primjeri pjesama pobjedničkih bendova</h2>
            <c:forEach var="band" items="${best}" varStatus="status">
                <a href="${links[status.index]}">${band}</a><br>
            </c:forEach>


            <div1>
                <div2><a href="chooseColor">Background color chooser</a></div2>
                <div3><a href="info">Info</a></div3>
            </div1>
        </div>
    </body>