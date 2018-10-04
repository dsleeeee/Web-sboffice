<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 매장사원정보관리 --%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/base/store/emp/store/" />

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="javascript:;" class="open">${menuNm}</a>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15">
      <col class="w35">
      <col class="w15">
      <col class="w35">
    </colgroup>
    <tbody>
      <tr>
        <%-- 등록일자 --%>
        <th><s:message code="storeEmp.regDt" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"><div id="srchStartDt" class="w200"></div></span>
            <span class="rg">~</span>
            <span class="txtIn"><div id="srchEndDt" class="w200"></div></span>
            <span class="chk ml10">
              <input type="checkbox" name="chkDt" id="chkDt" value="Y"/>
              <label for="chkDt" ><s:message code="cmm.all.day" /></label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 사원번호 --%>
        <th><s:message code="storeEmp.empNo" /></th>
        <td>
          <div class="sb-select">
            <div id="srchEmpNo"></div>
          </div>
        </td>
        <%-- 사원명 --%>
        <th><s:message code="storeEmp.empNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchEmpNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 사원ID --%>
        <th><s:message code="storeEmp.userId" /></th>
        <td>
          <div class="sb-select">
            <div id="srchUserId"></div>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
      <tr>
        <%-- 재직여부 --%>
        <th><s:message code="storeEmp.serviceFg" /></th>
        <td>
          <div class="sb-select">
            <div id="srchServiceFg"></div>
          </div>
        </td>
        <%-- 휴대폰번호 --%>
        <th><s:message code="storeEmp.mpNo" /></th>
        <td>
          <div class="sb-select">
            <div id="srchMpNo"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 웹사용여부 --%>
        <th><s:message code="storeEmp.webUseYn" /></th>
        <td>
          <div class="sb-select">
            <div id="srchWebUseYn"></div>
          </div>
        </td>
        <%-- SMS수신여부 --%>
        <th><s:message code="storeEmp.smsRecvYn" /></th>
        <td>
          <div class="sb-select">
            <div id="srchSmsRecvYn"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
  <!--//searchTbl-->

  <div class="mt10 pdb20 oh">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="mt40 oh sb-select dkbr">
    <%--페이지 스케일 --%>
    <div id="listScaleBox" class="w130 fl"></div>
    <div class="tr">
      <%-- 사원신규등록 --%>
      <button class="btn_skyblue" id="btnRegist"><s:message code="storeEmp.empRegist" /></button>
      <%-- 엑셀다운로드 --%>
      <%--<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>--%>
    </div>
  </div>

  <!--위즈모 테이블-->
  <div id="storeEmpGrid" class="mt20 "></div>
  <!--//위즈모 테이블-->

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page" data-size="10">
    </ul>
  </div>

</div>
<script>
var serviceFg = ${ccu.getCommCodeSelect("007")};
var useYn = ${ccu.getCommCodeSelect("067")};
var RecvYn = ${ccu.getCommCodeSelect("072")};
var listScaleBoxData = ${ccu.getListScale()};
var baseUrl = "${baseUrl}";
</script>

<%-- 매장사원 상세정보 레이어 --%>
<c:import url="/WEB-INF/view/base/store/emp/storeEmpDetail.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장사원 등록 레이어 --%>
<c:import url="/WEB-INF/view/base/store/emp/storeEmpSave.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 패스워드 변경 레이어 --%>
<c:import url="/WEB-INF/view/base/store/emp/storeEmpModifyPwd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/storeEmp.js?ver=20180829" charset="utf-8"></script>
