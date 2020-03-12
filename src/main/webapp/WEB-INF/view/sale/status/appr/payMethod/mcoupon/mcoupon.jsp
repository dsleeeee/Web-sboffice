<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="apprMcouponView" class="subCon3"  ng-controller="apprMcouponCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="dailyReport.apprMcoupn"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnApprMcouponSearch" ng-click="_broadcast('apprMcouponCtrlSrch')">
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
            <span class="txtIn"><input id="srchApprMcouponStartDate" class="w120px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchApprMcouponEndDate" class="w120px"></span>
            <span class="chk ml10">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>
        </div>
        </td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="apprMcouponSelectStoreCd" value=""/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="apprMcouponSelectStore"/>
                <jsp:param name="targetPosId" value="apprMcouponSelectPos"/>
                <jsp:param name="targetCornerId" value="apprMcouponSelectCorner"/>
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
                <jsp:param name="targetId" value="apprMcouponSelectPos"/>
                <jsp:param name="targetStoreId" value="apprMcouponSelectStore"/>
                <jsp:param name="closeFunc" value="getPosNmList"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      
        <%-- 코너표시 --%>
        <th><s:message code="corner.cornrNm" /></th>
        <td>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectCornerM.jsp" flush="true">
                <jsp:param name="targetId" value="apprMcouponSelectCorner"/>
                <jsp:param name="targetStoreId" value="apprMcouponSelectStore"/>
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
                      id="srchMcouponSaleFgDisplay"
                      ng-model="saleFg"
                      items-source="_getComboData('srchMcouponSaleFgDisplay')"
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
                      id="srchMcouponApprProcFgDisplay"
                      ng-model="cashBillApprProcFg"
                      items-source="_getComboData('srchMcouponApprProcFgDisplay')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                </span>
          </div>
        </td>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">  
            <input type="hidden" id="apprMcouponSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
        <input type="hidden" id="posMcouponSelectPosCd" value=""/>
        <input type="hidden" id="posMcouponSelectPosName" value=""/>
        <input type="hidden" id="apprMcouponSelectCornerCd" value=""/>
        <input type="hidden" id="apprMcouponSelectCornrName" value=""/>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>
    
    <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="apprMcouponListScaleBox"
            ng-model="apprMcouponListScale"
            items-source="_getComboData('apprMcouponListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="apprMcouponSelectStoreStoreNum" ng-model="storeNum">
    </c:if>
    <%-- 엑셀 다운로드 //TODO --%>
    <button class="btn_skyblue fr" ng-click="excelDownloadMcoupon()"><s:message code="cmm.excel.down" />
    </button>
  </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap2">
        <wj-flex-grid
          id="apprMcouponGrid"
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
          
          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMcoupn"    />"     binding="cnt"                 width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApMcoupn"     />"     binding="apprAmt"             width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          
          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMcoupn"    />"     binding="cntA"                width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApMcoupn"     />"     binding="apprAmtA"            width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          
          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntMcoupn"    />"     binding="cntB"                width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApMcoupn"     />"     binding="apprAmtB"            width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="apprMcouponCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
   <%-- id --%>
    <ul id="apprMcouponCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/appr/payMethod/mcoupon/mcoupon.js" charset="utf-8"></script>
