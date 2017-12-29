<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.activation {
	font-weight: bold;
	color: red;
}
</style>

<body>
  <c:choose>

    <c:when test="${isPopPage}">
      <div class="pop_wrap">
        <tiles:insertAttribute name="content" />
      </div>
    </c:when>

    <c:otherwise>
      <div id="wrapper">
        <tiles:insertAttribute name="header" />
        <%--Content page--%>
        <div class="content_page">
          <div class="content">

            <c:set var="hist" value="${sessionScope.sessionInfo.histMenu}" />
            <c:set var="fix" value="${sessionScope.sessionInfo.fixMenu}" />
            <c:set var="histSize" value="${fn:length(hist)}" />


            <c:if test="${view != 'Main'}">
              <%--Location--%>

              <%-- 고정 메뉴 --%>
              <div class="location">
                <i class="fa fa-home i_home_s"></i>
                <c:forEach var="item" items="${fix}" varStatus="status">
                  <span class="${status.last ? 'angle last' : 'angle'}" id="${item.resrceCd}">
                    <a class="${item.activation == true ? 'activation' : ''}" href="${item.url}">${item.resrceNm}</a>&nbsp;
                    <c:if test="${item.activation == false}">
                      <a onclick="deleteFixMenu('${item.resrceCd}');">X</a>
                    </c:if>
                  </span>
                </c:forEach>
              </div>

              <%-- 히스토리 메뉴 --%>
              <div class="location" id="histMenuList">
                <i class="fa fa-home i_home_s"></i>
                <c:forEach var="item" items="${hist}" varStatus="status">
                  <span class="${status.last ? 'angle last' : 'angle'}" id="${item.resrceCd}">
                    <a class="${item.activation == true ? 'activation' : ''}" href="${item.url}">${item.resrceNm}</a>&nbsp;
                    <c:if test="${item.activation == false}">
                      <a onclick="deleteHistMenu('${item.resrceCd}');">X</a>
                    </c:if>
                  </span>
                </c:forEach>

              </div>

              <%--//Location--%>
              <div class="page_title">
                <%-- addMenuName : 메뉴명에 추가할 내용이 있을경우 사용 --%>

                <h2>${sessionScope.sessionInfo.currentMenu.resrceNm}</h2>
              </div>

              <div id="loading" style="display: none;">
                <img alt="loading" style="height: 10%; width: 10%; overflow: visible;" src="/resource/solbipos/img/loading.gif">
              </div>

              <tiles:insertAttribute name="content" />
              <tiles:insertAttribute name="footer" />
            </c:if>

          </div>

        </div>
        <%--Content page--%>
      </div>
    </c:otherwise>
  </c:choose>
</body>

<script>
function deleteHistMenu(menuId) {
  var url = "/menu/delHistMenu.sb";
  callPostJson(url, menuId);
}

function deleteFixMenu(menuId) {
  var url = "/menu/delFixMenu.sb";
  callPostJson(url, menuId);
}

function callPostJson(url, menuId) {
  var param = {};
  param.menuId = menuId;
  $.postJSON( url, param, function( result ){
    $("#"+menuId).remove();
  })
  .fail(function(){
    alert("Ajax Fail");
  });
}
</script>












