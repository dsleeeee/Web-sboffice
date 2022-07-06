<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="membrClassManageFg" value="${membrClassManageFg}" />
<%--<c:set var="baseUrl" value="/membr/anals/postpaid/" />--%>

<div class="subCon" ng-controller="postpaidCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" ng-click="_pageView('postpaidCtrl',1)" id="nxBtnSearch">
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
    <tr ng-show="orgnFg=='H'">
      <%-- 매장 --%>
      <th><s:message code="postpaid.srchStore" /></th>
      <td>
        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
        <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
          <jsp:param name="targetId" value="store"/>
        </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <%-- 회원번호 --%>
      <th><s:message code="postpaid.membrNo" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srcgMembrNo" ng-model="membrNo" onkeyup="fnNxBtnSearch();"/>
      </td>
      <%-- 회원명 --%>
      <th><s:message code="postpaid.membrNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" onkeyup="fnNxBtnSearch();"/>
      </td>
    </tr>
    <tr>
      <th><s:message code="postpaid.postpaidYn"/></th>
      <td>
        <div class="sb-select">
          <wj-combo-box
                  id="srchUseYn"
                  ng-model="useYn"
                  items-source="_getComboData('useYn')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
      </td>
      <th <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>><s:message code="postpaid.postpaidAmt2"/></th>
      <td <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>>
        <input type="text" class="sb-input w50" id="postpaidAmt" ng-model="postpaidAmt" numberOnly>
        <a href="#" class="btn_grayS ml10" ng-click="batchChange()"><s:message code="prodBatchChange.batchChange" /></a>
      </td>
    </tr>
    </tbody>
  </table>


  <div class="mt40 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
    <%--// 페이지 스케일  --%>

    <div>
      <div <c:if test="${orgnFg == 'HQ' and membrClassManageFg == '0'}">style="display: none;"</c:if>>
      <%-- 외상입금 --%>
      <button class="btn_skyblue ml5 fr"  ng-click="deposit()">
        <s:message code="postpaid.deposit" />
      </button>
      <%-- 세금계산서 발행입금 --%>
      <button class="btn_skyblue ml5 fr"  ng-click="taxBillDeposit()">
        <s:message code="postpaid.taxBill.deposit" />
      </button>
      </div>
      <%-- 엑셀 다운로드 //TODO --%>
      <button class="btn_skyblue fr" ng-click="excelDownload()">
        <s:message code="cmm.excel.down" />
      </button>
    </div>
  </div>

  <%-- 그리드 --%>
  <div class="w100 mt10 mb20">
    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
      <div class="row">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.hqOfficeCd"/>" binding="hqOfficeCd" width="70" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.storeNm"/>" binding="storeNm" width="140" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.membrNo"/>" binding="membrNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.membrNm"/>" binding="membrNm" width="90" is-read-only="true" align="left"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.postpaidAmt"/>" binding="postpaidAmt" width="90" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.postpaidInAmt"/>" binding="postpaidInAmt" width="90" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.postpaidBalAmt"/>" binding="postpaidBalAmt" width="90" is-read-only="true" align="right"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="postpaid.postpaidAmt2"/>" binding="postpaidAmt2" width="90" data-type="Number" align="right"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="postpaidCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<%-- 엑셀 리스트 --%>
<div class="w100 mt10 mb20">
  <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; display: none;" ng-controller="postpaidExcelCtrl">
    <div class="row">
      <wj-flex-grid
              autoGenerateColumns="false"
              control="excelFlex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter"
              is-read-only="true">


        <wj-flex-grid-column header="<s:message code="postpaid.hqOfficeCd"/>" binding="hqOfficeCd" width="70" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.storeNm"/>" binding="storeNm" width="140" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.membrNo"/>" binding="membrNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.membrNm"/>" binding="membrNm" width="90" is-read-only="true" align="left"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.postpaidAmt"/>" binding="postpaidAmt" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.postpaidInAmt"/>" binding="postpaidInAmt" width="90" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="postpaid.postpaidBalAmt"/>" binding="postpaidBalAmt" width="90" is-read-only="true" ></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
</div>
<script type="text/javascript">
  var postpaidFgData = ${ccu.getCommCodeExcpAll("073")};
  var postpaidPayFgData = ${ccu.getCommCodeExcpAll("074")};
  var baseUrl = "${baseUrl}";

  // 회원등급 관리구분[1237]
  var membrClassManageFg = ${membrClassManageFg};

  $(function(){
    $("input:text[numberOnly]").on("keyup", function() {
      $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
    });
  });
</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/postpaid/postpaid.js?ver=2018090302" charset="utf-8"></script>

<%-- 외상입금 팝업 --%>
<%--<c:import url="/WEB-INF/view/membr/anals/postpaid/deposit.jsp">--%>
<%--</c:import>--%>

<%--&lt;%&ndash; 입금 등록 팝업 &ndash;%&gt;--%>
<%--<c:import url="/WEB-INF/view/membr/anals/postpaid/depositRegist.jsp">--%>
<%--</c:import>--%>

<%-- 세금계산서 발행 입금 팝업 --%>
<c:import url="/WEB-INF/view/membr/anals/postpaid/taxBillDeposit.jsp">
</c:import>

<%-- 세금계산서 선택 팝업 --%>
<c:import url="/WEB-INF/view/membr/anals/postpaid/searchTaxBill.jsp">
</c:import>
