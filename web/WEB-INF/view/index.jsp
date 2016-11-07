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
      <div class = "indexHeader">
        <div class = "row">
          <div class="leftColumnInHeader">
            <div class="innerLeftColumnInHeader">
                <p>JAVA CORE QUIZ</p>
                <form action="./registerNewUser.do" >
                  <input class="submitButton" type="submit" value="зарегистрироваться »"  name="submit" />
                </form>
            </div>
          </div>

          <div class="loginForm">
            <div class ="innerLoginForm">
            <form action="/login.do" method="post">
              <div class="inputForm"><label for="userName"> Username <span class="indicates">*</span><span>  you may be: kamazz</span> </label>
                <input type="text" name="userName" id ="userName" value="" size="20" />
                <c:choose>
                  <c:when test="${empty errorMap.userName}"></c:when>
                  <c:otherwise>
                    <span class="messageError"><c:out value="${errorMap.userName}"></c:out></span></br>
                  </c:otherwise>
                </c:choose>
              </div>


              <div class="inputForm"><label for="password"> Password <span class="indicates">*</span><span>  kamazz</span> </label>
                <input name ="password" type="password"  id ="password" value="" size="20" />
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
              <div >
                <input class="submitButton" type="submit" value="войти"  name="submit" />
              </div>
            </form>
          </div>
          </div>
        </div>
    </div>
      <div class="topContent">
        <div class="widget">
          <h1>Стек технологий: Spring Framework, Servlet Api, JDBC API, JSP, JSTL, MySQL, Apache Tomcat.
          </h1>
        </div>
      </div>
      <div class="content row">
        <div class="contentWrap">
          <p>
            Java Enterprise проект с регистрацией/авторизацией. Пользователь может проверить свои знания по  java core, выбрать и пройти квиз из представленных тем и разделов. После пройденного квиза можно посмотреть свой результатт с правельными ответами.</div>
      </div>
    </div>

  </div>
  </body>
</html>
