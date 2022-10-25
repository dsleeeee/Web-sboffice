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

  <div class="wj-TblWrap mt20">
    <c:import url="/WEB-INF/view/base/store/posFunc/posFuncUseManage.jsp">
      <c:param name="menuCd" value="${menuCd}"/>
      <c:param name="menuNm" value="${menuNm}"/>
      <c:param name="baseUrl" value="${baseUrl}"/>
    </c:import>
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

  // POS에서 해당 WEB 화면 최초 접속한 경우(접속하면서 session 생성), 왼쪽 메뉴영역은 접어두기.
  // 최초 접속시에는 이전 URL 인자값으로 판별가능
  var referrer = document.referrer;
  if(referrer.indexOf("userId") > 0 && referrer.indexOf("resrceCd") > 0 && referrer.indexOf("accessCd") > 30 ){
      $(".menuControl").trigger("click");
  }

  // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값 판단하여 왼쪽 메뉴영역은 접어두기.
  // 재접속시에는 이전 URL 인자값이 없어, 로그인 여부 판별시에 특정 parameter 값을 보내 처리.
  if("${posLoginReconnect}" === "Y"){ // 직접입력한경우
      $(".menuControl").trigger("click");
  }
</script>
