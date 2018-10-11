<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="storeNm" value="${sessionScope.sessionInfo.storeNm}" />
<c:set var="baseUrl" value="/base/store/posfunc/"/>

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>

  <div class="wj-TblWrap mt40">
    <%--<div class="w100">--%>
      <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
        <%-- 포스기능 사용관리--%>
        <c:import url="/WEB-INF/view/base/store/posFunc/posFuncUseManage.jsp">
          <c:param name="menuCd" value="${menuCd}"/>
          <c:param name="menuNm" value="${menuNm}"/>
          <c:param name="baseUrl" value="${baseUrl}"/>
        </c:import>

        <%-- 포스기능 인증관리--%>
        <c:import url="/WEB-INF/view/base/store/posFunc/posFuncAuth.jsp">
          <c:param name="menuCd" value="${menuCd}"/>
          <c:param name="menuNm" value="${menuNm}"/>
          <c:param name="baseUrl" value="${baseUrl}"/>
        </c:import>
      </div>
    <%--</div>--%>

  </div>
</div>

<script>

  var posList;
  var selectedStore = {};

  selectedStore.hqOfficeCd = "${hqOfficeCd}";
  selectedStore.storeCd = "${storeCd}";
  selectedStore.storeNm = "${storeNm}";

  $(document).ready(function(){
    showPosFuncList();
  });

</script>

<%-- 인증허용대상 설정 팝업 --%>
<c:import url="/WEB-INF/view/base/store/posFunc/posFuncAuthSetting.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 기능복사 팝업 --%>
<c:import url="/WEB-INF/view/base/store/posFunc/posFuncCopy.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

