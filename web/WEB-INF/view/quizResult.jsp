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

        <header class="header delMargin">
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
                <div class="quizResultContainer">
                    <a class="button delMargin" href="./endquiz.do">« вернуться в квизы</a>

                    <h2>Вы ответили правильно на <b style="color:green"><c:out
                            value="${sessionScope.countCorrectAnswer}"/></b> из <b style="color:tomato"><c:out
                            value="${sessionScope.question.size()}"/></b> вопросов: </h2>

                    <ol>
                        <c:forEach var="questionList" items="${sessionScope.question}">
                            <li>
                                <c:choose>
                                    <c:when test="${questionList.userAnswer.correct == '1'}">
                                        <div class="correctAnswer">
                                            <p><span>✔</span>${questionList.caption}</p>

                                            <p>${questionList.question}</p>
                                            <b>Ответы:</b>

                                            <c:forEach var="answer" items="${questionList.answerList}">
                                                <c:choose>
                                                    <c:when test="${answer.correct == '1'}">
                                                        </br><input type="checkbox" name="userAnswerId" value="" checked disabled>
                                                        <c:out value="${answer.answer}"/><b style="color:green"> ✔ </b>
                                                        <span class="pointerCorrectAnswer">ответ пользователя - правильный ответ</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        </br><input type="checkbox" name="userAnswerId" value="" disabled>
                                                        <c:out value="${answer.answer}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </c:when>

                                    <c:otherwise>
                                        <div class="inCorrectAnswer">
                                            <p><span>✘</span> ${questionList.caption}</p>

                                            <p>${questionList.question}</p>
                                            <b>Ответы:</b>

                                            <c:forEach var="answer" items="${questionList.answerList}">
                                                <c:choose>
                                                    <c:when test="${answer.correct == '1'}">
                                                        </br><input type="checkbox" name="userAnswerId" value="" disabled>
                                                        <c:out value="${answer.answer}"/><b style="color:green">
                                                        ✓ </b><span
                                                            class="pointerCorrectAnswer"> правильный ответ</span>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${answer.idAnswer == questionList.userAnswer.idAnswer }">
                                                                </br><input type="checkbox" name="userAnswerId" value="" checked disabled>
                                                                <c:out value="${answer.answer}"/><b style="color:red">
                                                                ✘ </b><span class="pointerCorrectAnswer"> ответ пользователя</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                </br><input type="checkbox" name="userAnswerId" value="" disabled>
                                                                <c:out value="${answer.answer}"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <hr>
                            </li>
                        </c:forEach>
                    </ol>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
