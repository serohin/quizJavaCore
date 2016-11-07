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
            </div>
        </header>

        <div class="content row">
            <div class="innerWrapContent">

                <form action="/question.do" method="post">

                    <h2><b><c:out value="${quizIndex+1}"/></b>. Вопрос номер <b><c:out value="${quizIndex+1}"/></b> из <b><c:out value="${question.size()}"/></b>:</h2><a class="button buttonInQuizRezult" href="./endquiz.do">« выйти в квизы</a>
                    <p><c:out value="${question[quizIndex].caption}"/></p>
                    ${question[quizIndex].question}

                    <p><b>Выберите ответ:</b></br>

                        <c:forEach var="answer" items="${question[quizIndex].answerList}">
                            <input  type="radio" name="userAnswerId" value="${answer.idAnswer}"/><c:out value="${answer.answer}"/></br>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${empty errorInput}"></c:when>
                            <c:otherwise>
                                <span style = "color:tomato"><c:out value="${errorInput}"></c:out></span></br>
                            </c:otherwise>
                        </c:choose>
                        <input class="button buttonQuestionForm" type="submit" value="cледующий »"/>
                    </p>
                </form>


                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
