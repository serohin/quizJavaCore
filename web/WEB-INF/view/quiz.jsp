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

          <c:forEach var="section" items="${sessionScope.sectionList}">
            <c:choose>
              <c:when test="${section.id eq sectionId}">
                <h2>Раздел : <b>${section.caption}</b></h2>
              </c:when>
            </c:choose>
          </c:forEach>
          <c:forEach var="theme" items="${sessionScope.themeListBySectionId}">
            <c:choose>
              <c:when test="${theme.id eq currentThemeId}">
                <h2>Тема : <b>${theme.caption}</b></h2>
              </c:when>
            </c:choose>
          </c:forEach>

          <div class="listInsideContent row">

            <form action="./question.do" method="post" >
              <h3>Выберите квиз :</h3>
              <ul>
                <c:forEach var="quiz" items="${quizListByThemeId}" >
                  <li>
                    <button type="submit" name="quizId" value="${quiz.id}" class="btnLink">
                      <c:out value="${quiz.caption}"/>
                    </button>
                  </li>
                </c:forEach>
              </ul>
            </form>
            <form class="formLeftButton" action="/theme.do" method="post" >
              <button type="submit" name="sectionId" class="button">
                « вернуться в темы
              </button>
            </form>
            <form action="/section.do" method="post" >
              <button type="submit" class="button rightButton">
                выйти в разделы
              </button>
            </form>
          </div>
        </div>
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
