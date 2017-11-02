<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Sidebar -->
<div id="sidebar">
    <ul class="categories">
        <li>
            <h4>Categories</h4>
            <ul>
                <jsp:useBean id="categories" class="com.skorobahatko.library.bean.CategoryList" scope="application"/>
                <c:forEach var="category" items="${categories.getCategories()}">
                    <li><a href="books.jsp?categoryId=${category.getId()}">${category.getName()}</a></li>
                </c:forEach>
            </ul>
        </li>
        <li>
            <h4>Authors</h4>
            <ul>
                <jsp:useBean id="authors" class="com.skorobahatko.library.bean.AuthorList" scope="application"/>
                <c:forEach var="author" items="${authors.getAuthors()}">
                    <li><a href="books.jsp?authorId=${author.getId()}">${author.getName()}</a></li>
                </c:forEach>
            </ul>
        </li>
    </ul>
</div>
<!-- End Sidebar -->
