<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/store/hq/hqmanage/hqmanage/"/>


<div class="subCon">
               
  
  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a><!--하단 검색테이블 열기 .open, 하단 검색테이블 닫기 .close-->
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
        <th>매장코드</th>
        <td><input type="text" class="sb-input w100" /></td>
        <th>매장명</th>
        <td><input type="text" class="sb-input w100" /></td>
      </tr> 
    </tbody>
  </table>
  
  
  <div class="mt10 oh">
    <%-- 조회 --%>
    <button class="btn_blue fr"><s:message code="cmm.search" /></button>
  </div>
  
  <div class="mt20 tr">
    <%-- 본사신규등록 --%>
    <button class="btn_skyblue"><s:message code="hqManage.newHq" /></button>
    <%-- sms 전송 --%>
    <button class="btn_skyblue"><s:message code="hqManage.sendSMS" /></button>
    <%-- 엑셀다운로드 --%>
    <button class="btn_skyblue"><s:message code="cmm.excel.down" /></button>
  </div>
  
  <%-- 위즈모 테이블 --%>
  <div id="theGrid" class="mt10"></div>

</div>