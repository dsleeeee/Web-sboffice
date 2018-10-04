<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/etc/vanCard/vanCard/" />

<div class="subCon">

  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>
  
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>
    
  <div class="w70 fl" style="width: 60%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr pd20" style="height: 300px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridVanNm' /></span>
        <button class="btn_skyblue" id="btnAddVan" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelVan" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveVan" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridVan" style="height:210px"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div class="w30 fr" style="width: 40%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 610px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridVanCardNm' /></span>
        <button class="btn_skyblue" id="btnAddMapng" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelMapng" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveMapng" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridMapping" style="height:510px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  <div class="w70 fl" style="width: 60%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10 pd20 mb10" style="height: 300px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='vanCard.gridCardNm' /></span>
        <button class="btn_skyblue" id="btnAddCard" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelCard" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveCard" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridCard" style="height:210px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  
  
</div>

<script>
  var cardCmpnyList = ${cardCmpnyList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/etc/vanCard/vanCard.js?ver=2018081301" charset="utf-8"></script>
