<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@ include file="/WEB-INF/jspf/css.jspf" %>
</head>
<body>

<div class="inner-wrap">
    <div id="container">

        <header class="header">
            <div class="row">
                <div class="leftColumnInHeader">
                    <div class="innerLeftColumnInHeader">
                        <p class="addPadding">JAVA CORE QUIZ</p>
                    </div>
                </div>
                <div class="loginForm">
                    <div class="innerLogoutInHeader">
                        <%@ include file="/WEB-INF/jspf/User.jspf" %>
                    </div>
                </div>
            </div>
        </header>

        <div class="content row">
            <div class="innerWrapContent">
                <div class="insideContent">

                    <c:forEach var="section" items="${sessionScope.sectionList}">
                        <c:choose>
                            <c:when test="${section.id eq sectionId}">
                                <h2>Раздел : <b>${section.caption}</b></h2>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    <div class="listInsideContent row">
                        <h3>Темы раздела :</h3>

                        <form action="/quiz.do" method="post">
                            <ul>
                                <c:forEach var="theme" items="${sessionScope.themeListBySectionId}">
                                    <li>
                                        <button type="submit" name="themeId" value="${theme.id}" class="btnLink">
                                            <c:out value="${theme.caption}"/>
                                        </button>
                                    </li>
                                </c:forEach>
                            </ul>
                        </form>
                        <form action="/section.do" method="post">
                            <button type="submit" class="button">
                                « вернуться в разделы
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <footer class="footer">
            <div class="footer-bg">
                <div class="row">
                    <p class="lineFooter">Учебное java приложение: Серегин О.В.</p>
                </div>
            </div>
        </footer>
    </div>
</div>


</body>
</html>
