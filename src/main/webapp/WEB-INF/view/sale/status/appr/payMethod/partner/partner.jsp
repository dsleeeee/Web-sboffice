<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="apprPartnerView" class="subCon3"  ng-controller="apprPartnerCtrl">
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl"><s:message code="dailyReport.apprPartner"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnApprPartnerSearch" ng-click="_broadcast('apprPartnerCtrlSrch')">
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
            <span class="txtIn"><input id="srchApprPartnerStartDate" class="w120px"></span>
                <span class="rg">~</span>
            <span class="txtIn"><input id="srchApprPartnerEndDate" class="w120px"></span>
            <span class="chk ml10">
                <input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
                <label for="chkDt">
                    <s:message code="cmm.all.day" />
                </label>
            </span>
        </div>
        </td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <input type="hidden" id="apprPartnerSelectStoreCd" value=""/>
        <%-- 매장코드 --%>
        <th><s:message code="todayBillSaleDtl.store"/></th>
        <td>
            <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
            --%>
<%--             <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true"> --%>
<%--                 <jsp:param name="targetId" value="apprPartnerSelectStore"/> --%>
<%--                 <jsp:param name="closeFunc" value="getCornerNmList"/> --%>
<%--             </jsp:include> --%>
            <%-- //매장선택 모듈 싱글 선택 사용시 include --%>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreM.jsp" flush="true">
                <jsp:param name="targetId" value="apprPartnerSelectStore"/>
                <jsp:param name="targetPosId" value="apprPartnerSelectPos"/>
                <jsp:param name="targetCornerId" value="apprPartnerSelectCorner"/>
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
                <jsp:param name="targetId" value="apprPartnerSelectPos"/>
                <jsp:param name="targetStoreId" value="apprPartnerSelectStore"/>
                <jsp:param name="closeFunc" value="getPosNmList"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </td>
      
        <%-- 코너표시 --%>
        <th><s:message code="corner.cornrNm" /></th>
        <td>
            <jsp:include page="/WEB-INF/view/sale/com/popup/selectCornerM.jsp" flush="true">
                <jsp:param name="targetId" value="apprPartnerSelectCorner"/>
                <jsp:param name="targetStoreId" value="apprPartnerSelectStore"/>
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
                      id="srchPartnerSaleFgDisplay"
                      ng-model="saleFg"
                      items-source="_getComboData('srchPartnerSaleFgDisplay')"
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
                      id="srchPartnerApprProcFgDisplay"
                      ng-model="apprProcFg"
                      items-source="_getComboData('srchPartnerApprProcFgDisplay')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                </span>
          </div>
        </td>
      <c:if test="${sessionInfo.orgnFg == 'STORE'}">  
            <input type="hidden" id="apprPartnerSelectStoreCd" value="${sessionInfo.storeCd}"/>
      </c:if>
        <input type="hidden" id="posPartnerSelectPosCd" value=""/>
        <input type="hidden" id="posPartnerSelectPosName" value=""/>
        <input type="hidden" id="apprPartnerSelectCornerCd" value=""/>
        <input type="hidden" id="apprPartnerSelectCornrName" value=""/>
      </tr>
      </tbody>
    </table>
    <div style="clear: both;"></div>
    
    <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="apprPartnerListScaleBox"
            ng-model="apprPartnerListScale"
            items-source="_getComboData('apprPartnerListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="hidden" id="apprPartnerSelectStoreStoreNum" ng-model="storeNum">
        </c:if>
    <%-- 엑셀 다운로드 //TODO --%>
    <button class="btn_skyblue fr" ng-click="excelDownloadPartner()"><s:message code="cmm.excel.down" />
    </button>
  </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap2">
        <wj-flex-grid
          id="apprPartnerGrid"
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
          
          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntPartner"   />"     binding="cnt"                 width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprDcPartner"    />"     binding="dcAmt"               width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApPartner"    />"     binding="apprAmt"             width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          
          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntPartner"   />"     binding="cntA"                width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprDcPartner"    />"     binding="dcAmtA"              width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApPartner"    />"     binding="apprAmtA"            width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          
          <wj-flex-grid-column header="<s:message code="dailyReport.apprCntPartner"   />"     binding="cntB"                width="100" is-read-only="true" align="center"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprDcPartner"    />"     binding="dcAmtB"              width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="dailyReport.apprApPartner"    />"     binding="apprAmtB"            width="150" is-read-only="true" align="right"   aggregate="Sum" ></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="apprPartnerCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
    
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
   <%-- id --%>
    <ul id="apprPartnerCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/appr/payMethod/partner/partner.js" charset="utf-8"></script>

<%-- 매장현황 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/appr/apprPartner.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>