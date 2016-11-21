<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
  <title>login</title>
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

      </div>
    </header>

    <div class="content row">
      <div class="innerWrapContent">
        <form class ="registerUserForm" action="/registerNewUser.do" method="post">

          <div class="registerUserDiv"><label for="userName"> Username <span class="indicates">*</span></label>
            <input class="textField" type="text" name="userName" id ="userName" value="${InputUserNameValue}" size="20" />
            <c:choose>
              <c:when test="${empty errorMap.userName}"></c:when>
              <c:otherwise>
                <span class="messageError"><c:out value="${errorMap.userName}"></c:out></span></br>
              </c:otherwise>
            </c:choose>
          </div>

          <div class="registerUserDiv"><label for="password"> Password <span class="indicates">*</span></label>
            <input class="textField" name ="password" type="password"  id ="password" value="" size="20" />
            <c:choose>
              <c:when test="${empty errorMap.password}"></c:when>
              <c:otherwise>
                <span class="messageError"><c:out value="${errorMap.password}"></c:out></span>
              </c:otherwise>
            </c:choose>
            <c:choose>
              <c:when test="${empty errorMap.noEntity}"></c:when>
              <c:otherwise>
                <span class="messageError"><c:out value="${errorMap.noEntity}"></c:out></span>
              </c:otherwise>
            </c:choose>
          </div>

          <div class="registerUserDiv"><label for="invite"> Invite <span class="indicates">*</span></label>
            <input class="textField" type="text" name="invite" id ="invite" value="${InputInviteValue}" size="20" />
            <c:choose>
              <c:when test="${empty errorMap.falseInvite}"></c:when>
              <c:otherwise>
                <span class="messageError"><c:out value="${errorMap.falseInvite}"></c:out></span></br>
              </c:otherwise>
            </c:choose>
          </div>
          <div >
            <input class="submitButton registerFormButton" type="submit" value="зарегистрироваться"  name="submit" />
          </div>
          <p class="linkInregisterForm"><a href="./"><b>« залогиниться</b></a></p>
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
