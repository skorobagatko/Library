<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="templates/header.jsp"%>
<%@ include file="templates/sidebar.jsp"%>

    <!-- Content -->
    <div id="content">
        <!-- Products -->
        <div class="products">
            <h3>All Books</h3>
            <jsp:useBean id="bookList" class="com.skorobahatko.library.bean.BookList" scope="page"/>
            <ul>
                <c:forEach var="book" items="${bookList.books}">
                    <li>
                        <div class="product">
                            <a href="#" class="info">
								<span class="holder">
									<img src="${pageContext.request.contextPath}/resources/images/books/${book.image}" alt=""/>
									<span class="book-name">${book.name}</span>
									<span class="author">${book.author.name}</span>
								</span>
                            </a>
                            <a href="book.jsp?book_id=${book.id}" class="buy-btn">READ</a>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <!-- End Products -->
        </div>
        <div class="cl">&nbsp;</div>
    </div>
    <!-- End Content -->

<%@ include file="templates/footer.jsp"%>
