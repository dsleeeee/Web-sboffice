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
</script>
