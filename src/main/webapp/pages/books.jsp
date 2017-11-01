<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="templates/header.jsp" %>
<%@ include file="templates/sidebar.jsp" %>

<% request.setCharacterEncoding("UTF-8");
    String categoryId = request.getParameter("category");
    if (categoryId == null) {
        request.getRequestDispatcher("main.jsp").forward(request, response);
    }
%>

<jsp:useBean id="bookList" class="com.skorobahatko.library.bean.BookList" scope="page"/>

<!-- Content -->
<div id="content">
    <!-- Products -->
    <div class="products">
        <h3><%=categories.getCategoryById(categoryId).getName()%></h3>

        <ul>
            <c:forEach var="book" items="${bookList.getBooksByCategory(param.get('category'))}">
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
                        <a href="#" class="buy-btn">READ</a>
                    </div>
                </li>
            </c:forEach>
        </ul>

        <!-- End Products -->
    </div>
    <div class="cl">&nbsp;</div>
</div>
<!-- End Content -->


<%@ include file="templates/footer.jsp" %>
