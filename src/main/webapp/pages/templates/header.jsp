<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Main</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" type="text/css"
          media="all"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.6.2.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery.jcarousel.min.js"></script>
    <!--[if IE 6]>
    <script type="text/javascript" src="/resources/js/png-fix.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/functions.js"></script>
</head>
<body>
<!-- Header -->
<div id="header" class="shell">
    <div id="logo"><h1><a href="main.jsp">Java Library</a></h1><span><a href="#"></a></span></div>

    <!-- Navigation -->
    <div id="navigation">
        <ul>
            <li><a href="#" class="active">Home</a></li>
            <li><a href="#">Products</a></li>
            <li><a href="#">Promotions</a></li>
            <li><a href="#">Profile</a></li>
            <li><a href="#">About Us</a></li>
            <li><a href="#">Contacts</a></li>
        </ul>
    </div>
    <!-- End Navigation -->
    <div class="cl">&nbsp;</div>
    <!-- Login-details -->
    <div id="login-details">
        <p>Welcome, <a href="#" id="user"><%=request.getParameter("username")%>
        </a>!</p>
    </div>
    <!-- End Login-details -->
</div>
<!-- End Header -->

<!-- Main -->
<div id="main" class="shell">

    <div class="search">
        <form class="search-form" action="#">
            <input class="search-field" type="text">
            <input class="search-btn" type="submit" value="Search">
        </form>
    </div>
