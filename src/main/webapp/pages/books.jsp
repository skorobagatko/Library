<%@ page import="com.skorobahatko.library.entity.Book" %>
<%@ page import="com.skorobahatko.library.entity.QueryType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="templates/header.jsp" %>
<%@ include file="templates/sidebar.jsp" %>

<jsp:useBean id="bookList" class="com.skorobahatko.library.bean.BookList" scope="page"/>


<% /*request.setCharacterEncoding("UTF-8");*/

    List<Book> books = null;

    if (request.getParameter("categoryId") != null) {
        String categoryId = request.getParameter("categoryId");
        books = bookList.getBooksByCategory(categoryId);
    } else if (request.getParameter("authorId") != null) {
        String authorId = request.getParameter("authorId");
        books = bookList.getBooksByAuthor(authorId);
    } else if (request.getParameter("query") != null
            && !request.getParameter("query").isEmpty()) {
        String q = request.getParameter("query");
        QueryType type;
        if (request.getParameter("query_type").equalsIgnoreCase("Name")) {
            type = QueryType.NAME;
        } else {
            type = QueryType.AUTHOR;
        }
        books = bookList.getBooksByQuery(q, type);
    }
%>

<!-- Content -->
<div id="content">
    <!-- Products -->
    <div class="products">
        <%--<h3><%=categories.getCategoryById(categoryId).getName()%></h3>--%>

        <% if (books == null || books.isEmpty()) { %>
            <p><b>No books found</b></p>
        <% } else { %>

        <ul>
            <c:forEach var="book" items="<%=books%>">
                <li>
                    <div class="product">
                        <a href="#" class="info">
								<span class="holder">
									<img src="${pageContext.request.contextPath}/resources/images/books/${book.image}" alt=""/>
									<span class="book-name">${book.name}</span>
									<span class="author">${book.author.name}</span>
                                    <%--<span class="isbn">${book.isbn}</span>--%>
								</span>
                        </a>
                        <a href="book.jsp?book_id=${book.id}" class="buy-btn">READ</a>
                    </div>
                </li>
            </c:forEach>
        </ul>

        <% } %>
        <!-- End Products -->
    </div>
    <div class="cl">&nbsp;</div>
</div>
<!-- End Content -->

<%@ include file="templates/footer.jsp" %>
