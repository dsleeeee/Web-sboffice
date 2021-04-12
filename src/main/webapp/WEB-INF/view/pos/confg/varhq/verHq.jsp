<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="con">
  <div class="tabType1" ng-controller="verHqCtrl" ng-init="init()">
    <ul>
      <%-- POS 버전 관리 --%>
      <li>
        <a id="verManageTab" href="#" class="on" ng-click="verManageShow()"><s:message code="verHq.verManage"/></a>
      </li>
      <%-- 버전별 수신현황 --%>
      <li>
        <a id="verRecvTab" href="#" ng-click="verRecvShow()"><s:message code="verHq.verRecv"/></a>
      </li>
      <%-- 매장별 수신현황--%>
      <li>
        <a id="storeRecvTab" href="#" ng-click="storeRecvShow()"><s:message code="verHq.storeRecv"/></a>
      </li>
      <%-- 버전별 매장현황--%>
      <li>
        <a id="verStoreTab" href="#" ng-click="verStoreShow()"><s:message code="verHq.verStore"/></a>
      </li>
    </ul>
  </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verhq/verHq.js?ver=20200805.08" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- POS버전관리 --%>
<c:import url="/WEB-INF/view/pos/confg/vermanage/verManage.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 버전별수신현황 --%>
<c:import url="/WEB-INF/view/pos/confg/verrecv/verrecv.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 매장별수신현황 --%>
<c:import url="/WEB-INF/view/pos/confg/verrecv/storerecv.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 버전별매장현황 --%>
<c:import url="/WEB-INF/view/pos/confg/verrecv/verstore.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>