<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/store/manage/virtualLogin/virtualLogin/"/>

<div class="subCon">

  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
  </div>
  <table class="searchTbl">
    <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
    </colgroup>
    <tbody>
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="virtualLogin.hqOfficeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeCd"></div>
          </div>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="virtualLogin.hqOfficeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="virtualLogin.storeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchStoreCd"></div>
          </div>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="virtualLogin.storeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchStoreNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 용도 --%>
        <th><s:message code="virtualLogin.clsFgNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchClsFg"></div>
          </div>
        </td>
        <%-- 상태 --%>
        <th><s:message code="virtualLogin.sysStatFgNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchSysStatFg"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh bb">
      <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>
    <%-- 엑셀 다운로드 --%>
    <button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down" /></button>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div id="theGrid" style="width:100%;height:393px;"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  
  var menuCd = ${menuCd};
  var popupCnt = 0;
  var clsFg = ${ccu.getCommCodeSelect("003")};
  var sysStatFg = ${ccu.getCommCodeSelect("005")};
  var columnLayout1 = ${clo.getColumnLayout(1)};
  
  var ldata = ${ccu.getListScale()};
  var rdata =
    [
      {"binding":"hqOfficeCd","header":"<s:message code='virtualLogin.hqOfficeCd' />", "width":"*"},
      {"binding":"hqOfficeNm","header":"<s:message code='virtualLogin.hqOfficeNm' />", "width":"*"},
      {"binding":"storeCd","header":"<s:message code='virtualLogin.storeCd' />", "width":"*"},
      {"binding":"storeNm","header":"<s:message code='virtualLogin.storeNm' />", "width":"*"},
      {"binding":"clsFgNm","header":"<s:message code='virtualLogin.clsFgNm' />", "width":"*"},
      {"binding":"sysStatFgNm","header":"<s:message code='virtualLogin.sysStatFgNm' />", "width":"*"},
      {"binding":"ownerNm","header":"<s:message code='virtualLogin.ownerNm' />", "width":"*"},
      {"binding":"telNo","header":"<s:message code='virtualLogin.telNo' />", "width":"*"},
      {"binding":"mpNo","header":"<s:message code='virtualLogin.mpNo' />", "width":"*"},
      {"binding":"agencyNm","header":"<s:message code='virtualLogin.agencyNm' />", "width":"*"},
      {"binding":"sysOpenDate","header":"<s:message code='virtualLogin.sysOpenDate' />", "width":"*"},
      {"binding":"sysClosureDate","header":"<s:message code='virtualLogin.sysClosureDate' />", "width":"*"}
    ];
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/virtualLogin/virtualLogin.js"></script>
  
  