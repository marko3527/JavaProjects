<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="hr.fer.zemris.java.voting.Option"%>
<%@ page import="java.util.List"%>
<%
  List<Option> options = (List<Option>)request.getAttribute("options");
  List<Option> mostVoted = (List<Option>)request.getAttribute("mostVotes");
%>

<html lang = "en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <style>
        body {text-align:center;}
        table {text-align: center; border-collapse: collapse; font-size: 30px; font-family: Arial;}
        h2 {font-family: Arial;}
        a {text-decoration: none; color: gray; font-size: 30px;font-family: Arial;}
    </style>

    <body>
        <div class="jumbotron text-center">
            <h1>Rezultati</h1>
        </div> 


        <div class="container">
            <div class="panel-body">
                <table class="table">
                    <thead class="thead-dark">
                    <tr>
                        <td><b>Option</b></td>
                        <td><b>Number of votes</b></td>
                    </tr>
                    </thead>
                    <tbody>
                        <% for(Option option : options){ %>
                            <tr>
                                <td><%= option.getName() %></td>
                                <td><%= option.getNumberOfVotes() %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
       
        <h2>Grafički prikaz rezultata</h2>
        <img alt="Pie chart" src=http://localhost:8080/voting-app/servleti/glasanje-grafika width="600" height="600" />

        <h2>Rezultati u XLS formatu</h2>
        <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

        <br><br>
        <% for(Option option : mostVoted){ %>
            <a href="<%= option.getLink() %>"> <%= option %></a><br>
        <% } %>
    </body>
</html>