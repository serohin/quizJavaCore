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

        <header class = "header delMargin">
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
                <div class="quizResultContainer">
                    <a class="button delMargin" href="./endquiz.do">« вернуться в квизы</a>
                    <h2>Вы ответили правильно на <b style="color:green"><c:out value="${sessionScope.countCorrectAnswer}"/></b> из <b style="color:tomato"><c:out
                            value="${sessionScope.question.size()}"/></b>
                        вопросов </h2>
                    <ol type="1">
                        <c:forEach var="questionList" items="${sessionScope.question}">
                        <li>
                            <c:out value="${questionList.caption}"/>
                            <p>${questionList.question}</p></br>
                            <b>Ответы:</b></br>
                            <ul type="disc">
                                <c:forEach var="answer" items="${questionList.answerList}">
                                    <c:choose>
                                        <c:when test="${answer.correct == '1'}">
                                            <li><b style="background-color: chartreuse"> <c:out value="${answer.answer}"/></b> - правильный
                                                ответ
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><c:out value="${answer.answer}"/></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </ul>

                            <p><b>Ответ пользователя :</b>
                                <c:choose>
                                    <c:when test="${questionList.userAnswer.correct == '1'}">
                                        <b style="background-color:chartreuse"> <c:out
                                                value="${questionList.userAnswer.answer}"/></b> - верно
                                    </c:when>
                                    <c:otherwise>
                                        <b style="background-color:tomato"> <c:out value="${questionList.userAnswer.answer}"/></b> - неверно
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            <hr>
                            </c:forEach>
                        </li>
                    </ol>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
