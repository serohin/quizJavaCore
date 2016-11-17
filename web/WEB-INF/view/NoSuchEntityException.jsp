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
                <h2>Нет такой сущности!</h2>
                </br>
                <form action="/section.do" method="post" >
                    <button type="submit" class="button">
                        вернуться в разделы
                    </button>
                </form>
            </div>
        </div>
        <footer class="footer">
            <div class="footer-bg">
                <div class = "row">
                    <p class="lineFooter">Учебное java приложение: Серегин О.В.</p>
                </div>
            </div>
        </footer>
    </div>
</div>

</body>
</html>