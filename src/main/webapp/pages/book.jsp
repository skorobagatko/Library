<%@ page import="com.skorobahatko.library.bean.BookList" %>
<%@ page import="com.skorobahatko.library.entity.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="templates/header.jsp" %>

<div id="book-content">
    <%
        String bookId = request.getParameter("book_id");
        if (bookId != null && !bookId.isEmpty()) {
            long id = Long.parseLong(bookId);
            BookList bookList = new BookList();
            for (Book b : bookList.getBooks()) {
                if (b.getId() == id) {
    %>
    <object type="application/pdf"
            data="${pageContext.request.contextPath}/resources/books/<%=b.getContent()%>"></object>
    <%
                }
            }
        }
    %>
</div>

<%@ include file="templates/footer.jsp" %>
