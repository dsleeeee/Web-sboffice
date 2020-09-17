<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="apprMpayView" class="subCon3"  ng-controller="apprMpayCtrl" style="display: none;">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="dailyReport.apprMpay"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnApprMpaySearch" ng-click="_broadcast('apprMpayCtrlSrch')">
        <s:message code="cmm.search"/>
      </button>
    </div>
    <table class="searchTbl">
      <colgroup>
        <col class="w15"/>
        <col class="w35"/>
        <col class="w15"/>
        <col class="w35"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 조회일자 --%>
        <th><s:message code="cmm.search.date"/></th>
        <td <c:if test="${sessionInfo.orgnFg == 'STORE'}">colspan="3"</c:if> >
        <div class="sb-select">
            <span class="txtIn"><input id="srchApprMpayStartDate" class="w120px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchApprMpayEndDate" class="w120px"></span>
            <span class="chk ml10" style="display: none;">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>
        </div>
        </td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="apprMpaySelectStoreCd" value=""/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="apprMpaySelectStore"/>
                <jsp:param name="targetPosId" value="apprMpaySelectPos"/>
                <jsp:param name="targetCornerId" value="apprMpaySelectCorner"/>
                <jsp:param name="closeFunc" value="getCornerNmList,getPosNmList"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      </c:if>
      </tr>

      <tr>
        <%-- 포스선택 --%>
        <th><s:message code="pos.pos" /></th>
        <td>
            <%-- 포스선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/sale/status/pos/cmm/selectPosM.jsp" flush="true">
                <jsp:param name="targetId" value="apprMpaySelectPos"/>
                <jsp:param name="targetStoreId" value="apprMpaySelectStore"/>
                <jsp:param name="closeFunc" value="getPosNmList"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>

        <%-- 코너표시 --%>
        <th><s:message code="corner.cornrNm" /></th>
        <td>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectCornerM.jsp" flush="true">
                <jsp:param name="targetId" value="apprMpaySelectCorner"/>
                <jsp:param name="targetStoreId" value="apprMpaySelectStore"/>
                <jsp:param name="closeFunc" value="getCornerNmList"/>
            </jsp:include>
        </td>
      </tr>

      <tr>
        <%-- 승인구분 --%>
        <th><s:message code="dayMcoupn.apprProcFg" /></th>
        <td>
          <div class="sb-select">
              <span class="txtIn">
                    <wj-combo-box
                      id="srchMpaySaleFgDisplay"
                      ng-model="saleFgModel"
                      items-source="_getComboData('srchMpaySaleFgDisplay')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                </span>
          </div>
        </td>
       <%-- 승인처리 --%>
        <th><s:message code="storeStatus.apprProcFg" /></th>
        <td>
          <div class="sb-select">
              <span class="txtIn">
                    <wj-combo-box
                      id="srchMpayApprProcFgDisplay"
                      ng-model="apprProcFgModel"
                      items-source="_getComboData('srchMpayApprProcFgDisplay')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                </span>
          </div>
        </td>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="apprMpaySelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
        <input type="hidden" id="posMpaySelectPosCd" value=""/>
        <input type="hidden" id="posMpaySelectPosName" value=""/>
        <input type="hidden" id="apprMpaySelectCornerCd" value=""/>
        <input type="hidden" id="apprMpaySelectCornrName" value=""/>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="apprMpayListScaleBox"
            ng-model="apprMpayListScale"
            items-source="_getComboData('apprMpayListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            initialized="initComboBox(s)"
			control="conListScale"
			is-editable="true"
			text-changed="_checkValidation(s)">

    </wj-combo-box>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="apprMpaySelectStoreStoreNum" ng-model="storeNum">
    </c:if>
    <%-- 엑셀 다운로드 //TODO --%>
    <button class="btn_skyblue fr" ng-click="excelDownloadMpay()"><s:message code="cmm.excel.down" />
    </button>
  </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap2">
        <wj-flex-grid
          id="apprMpayGrid"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">
          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="rtnStatus.storeCd"            />"     binding="storeCd"             width="100" is-read-only="true" align="center" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="rtnStatus.storeNm"            />"     binding="storeNm"             width="200" is-read-only="true" align="center" ></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMpay"      />"     binding="cnt"                 width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApMpay"       />"     binding="apprAmt"             width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMpay"      />"     binding="cntA"                 width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApMpay"       />"     binding="apprAmtA"             width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMpay"      />"     binding="cntB"                 width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApMpay"       />"     binding="apprAmtB"             width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="apprMpayCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
   <%-- id --%>
    <ul id="apprMpayCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%-- 엑셀 리스트 --%>
    <div class="w100 mt10" id="wjWrapType3" style="display:none;" ng-controller="apprMpayExcelCtrl">
      <div class="wj-gridWrap">
            <wj-flex-grid
                id="apprMpayExcelGrid"
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="excelFlex"
                initialized="initGrid(s,e)"
                loaded-rows="loadedRows(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">
                <!-- define columns -->
	            <wj-flex-grid-column header="<s:message code="rtnStatus.storeCd"            />"     binding="storeCd"             width="100" is-read-only="true" align="center" ></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="rtnStatus.storeNm"            />"     binding="storeNm"             width="200" is-read-only="true" align="center" ></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMpay"      />"     binding="cnt"                 width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="dailyReport.apprApMpay"       />"     binding="apprAmt"             width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMpay"      />"     binding="cntA"                 width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="dailyReport.apprApMpay"       />"     binding="apprAmtA"             width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMpay"      />"     binding="cntB"                 width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
	            <wj-flex-grid-column header="<s:message code="dailyReport.apprApMpay"       />"     binding="apprAmtB"             width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/appr/payMethod/mpay/mpay.js" charset="utf-8"></script>

