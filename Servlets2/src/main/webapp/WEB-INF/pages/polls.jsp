<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="hr.fer.zemris.java.voting.Poll"%>
<%@ page import="java.util.List"%>
<%
  List<Poll> polls = (List<Poll>)request.getAttribute("polls");
%>

<html lang="en">
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
        h1 {text-align: center;}
    </style>
    <body>
        <div class="jumbotron text-center">
            <h1>Ankete</h1>
        </div>
        <ul>
            <% for(Poll poll : polls){ %>
                <a href= "<%= "/voting-app/servleti/glasanje?pollID=" + poll.getId() %>"> <%= poll.getTitle() %> </a><br>
            <% } %>
        </ul>
    </body>
</html>