<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/bill/kind/" />

<div class="subCon">
  
  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
  </div>
  
  <%-- 조회 --%>
  <div class="mt10 pdb10 oh">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="wj-TblWrap">
    <div class="w50 fl" style="width: 40%">
      <%--위즈모 테이블--%>
      <div class="wj-TblWrapBr mr10 pd20" style="height: 490px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='kind.gridNm' /></span>
          <button class="btn_skyblue" id="btnAddPrint" style="display: none;">
            <s:message code="cmm.add" />
          </button>
          <button class="btn_skyblue" id="btnDelPrint" style="display: none;">
            <s:message code="cmm.delete" />
          </button>
          <button class="btn_skyblue" id="btnSavePrint" style="display: none;">
            <s:message code="cmm.save" />
          </button>
        </div>
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div id="gridPrint" style="height:405px"></div>
      </div>
      <%--//위즈모 테이블--%>
    </div>

    <div class="w50 fl" style="width: 60%">
      <%--위즈모 테이블--%>
      <div class="wj-TblWrapBr ml10 pd20" style="height: 490px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code='kind.gridMapngNm' /></span>
          <button class="btn_up" id="btnUpMapng" style="display: none;">
            <s:message code="cmm.up" />
          </button>
          <button class="btn_down" id="btnDownMapng" style="display: none;">
            <s:message code="cmm.down" />
          </button>
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
        <div id="gridMapng" style="height:405px;"></div>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    <input type="hidden" id="prtClassCd" />
  </div>
</div>

<%-- 출력물코드 선택 레이어 --%>
<div id="itemSelTent" class="fullDimmed" style="display: none;"></div>
<div id="itemSelLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w800">
      <p class="tit"><s:message code="kind.layer.gridNm" /></p>
      <a href="javascript:;" class="btn_close itemSelClose"></a>
      <div class="con">
          <%--위즈모 테이블--%>
          <div class="wj-TblWrapBr mt10" style="height: 400px;">
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div id="gridPrintCode" style="width:100%;height:393px;"></div>
          </div>
      </div>
      <%-- 저장 --%>
      <div class="btnSet">
        <span><a href="javascript:;" id="btnSaveItem" class="btn_blue"><s:message code="cmm.chk" /></a></span>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/sys/bill/kind/kind.js?ver=2018081302" charset="utf-8"></script>
