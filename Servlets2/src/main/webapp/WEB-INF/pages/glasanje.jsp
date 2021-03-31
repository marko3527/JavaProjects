<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="hr.fer.zemris.java.voting.Option" %>
<%@ page import="hr.fer.zemris.java.voting.Poll" %>
<%@ page import="java.util.List" %>
<%
  List<Option> options = (List<Option>)request.getAttribute("options");
  Poll poll = (Poll)request.getAttribute("poll");
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
        body {text-align: center;}
        a {text-decoration: none; color: gray; font-size: 50px; font-family: Arial;}
        div {text-align: center;}
    </style>
    <body>
        <div class="jumbotron text-center">
            <h1><%= poll.getTitle() %></h1>
            <p><%= poll.getMessage() %></p>
        </div>
        <ul>
            <% for(Option option : options){ %>
                <a href = "<%= "/voting-app/servleti/glasanje-glasaj?id=" + option.getId() %>"> <%= option %> </a><br><br>
            <% } %>
            </ul>
    </body>
</html>