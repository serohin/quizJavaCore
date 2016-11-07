<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@ include file="/WEB-INF/jspf/css.jspf"%>
</head>
<body>
<div class = "inner-wrap">
    <div id="container">

        <header class = "header">
            <div class = "row">
                <div class="leftColumnInHeader">
                    <div class="innerLeftColumnInHeader">
                        <p class ="addPadding">JAVA CORE QUIZ</p>
                    </div>
                </div>
                <div class="loginForm">
                    <div class="innerLogoutInHeader">
                        <%@ include file="/WEB-INF/jspf/User.jspf"%>
                    </div>
                </div>
            </div>
        </header>

        <div class="content row">
            <div class="innerWrapContent">
                <div class="insideContent">

                        <div class="listInsideContent row">
                            <h3>Выберите раздел Java Core :</h3>
                            <ul>
                                <c:forEach var="section" items="${sectionList}" varStatus="loop">
                                    <li><a href="./theme.do?sectionId=${section.id}">${section.caption}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                </div>
            </div>
        </div>
        <footer class="footer"><div class="footer-bg"></div></footer>
    </div>
</div>
</body>
</html>
