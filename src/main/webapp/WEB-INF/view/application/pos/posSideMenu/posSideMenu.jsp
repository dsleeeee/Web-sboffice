<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<body ng-app="rootApp" ng-controller="rootCtrl">
  <div class="tabType1" ng-controller="posSideMenuCtrl" ng-init="init()">
    <ul>
      <%-- 사이드메뉴관리 --%>
      <li>
        <a id="sideMenuTab" href="#" class="on" ng-click="sideMenuShow()"><s:message code="posSideMenu.sideMenu"/></a>
      </li>
      <%-- 상품정보관리 --%>
      <li>
        <a id="prodTab" href="#" ng-click="prodShow()"><s:message code="posSideMenu.prod"/></a>
      </li>
      <%-- 판매터치키등록--%>
      <li>
        <a id="touchKeyTab" href="#" ng-click="touchKeyShow()"><s:message code="posSideMenu.touchKey"/></a>
      </li>
    </ul>
  </div>
</body>
<script>
  var touchKeyEnvstVal = ${touchKeyEnvstVal};
  var touchKeyGrpData = ${touchKeyGrp};
  var maxClassRow = ${maxClassRow};
  var prodNoEnvFg = "${prodNoEnvFg}";
  var prodAuthEnvstVal = "${prodAuthEnvstVal}";
  var gvStartDate = "${startDate}";
  var gvEndDate = "${endDate}";
  var gvOrgnFg = "${orgnFg}";
  var gvHqOfficeCd = "${hqOfficeCd}";
  var gvStoreCd = "${storeCd}";
  var gvListScaleBoxData = [
    {"name":"10","value":"10"},
    {"name":"30","value":"30"},
    {"name":"50","value":"50"}
  ];
</script>
<script type="text/javascript" src="/resource/solbipos/js/application/pos/posSideMenu/posSideMenu.js?ver=20210527.23" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 사이드메뉴관리 --%>
<c:import url="/WEB-INF/view/base/prod/sideMenu/sideMenu.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="gubun" value="sideMenu"/>
</c:import>
<%-- 상품정보관리 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prod.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="gubun" value="sideMenu"/>
</c:import>
<%-- 판매터치키등록 --%>
<c:import url="/WEB-INF/view/base/prod/touchKey/touchKey.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="gubun" value="sideMenu"/>
</c:import>