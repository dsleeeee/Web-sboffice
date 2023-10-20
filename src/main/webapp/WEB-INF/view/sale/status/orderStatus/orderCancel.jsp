<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div id="orderCancelView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="orderCancelSrch1Ctrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="orderStatus.orderCancel"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('orderCancelSrch1Ctrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>
        <%--//searchTbl--%>
        <table class="searchTbl">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 조회 일자 --%>
                <th><s:message code="orderStatus.srchDate" /></th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"><input id="ocStartDate" ng-model="ocStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="ocEndDate" ng-model="ocEndDate" class="w110px"></span>
                    </div>
                </td>
            </tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="S"/>
                            <jsp:param name="targetId" value="orderCancelSelectStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="orderCancelSelectStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
           </tbody>
          </table>
        <%--//searchTbl--%>

        <div class="wj-TblWrap w20 pdr5 fl">
            <%-- 기간내 그리드 --%>
            <div>
                <div class="w100">
                    <div class="updownSet mt5">
                        <span class="fl bk lh30"><s:message code="orderStatus.orderCancelSrch1"/></span>
                    </div>
                    <div class="wj-gridWrap" style="height:100px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                              control="flex"
                              autoGenerateColumns="false"
                              selection-mode="Row"
                              initialized="initGrid(s,e)"
                              items-source="data"
                              item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="orderStatus.totCancelCnt"/>" binding="cancelCnt" width="*" align="right" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
            <%-- 일자별 그리드 --%>
            <div ng-controller="orderCancelSrch2Ctrl">
                <div class="w100">
                    <div class="updownSet">
                        <span class="fl bk lh30"><s:message code="orderStatus.orderCancelSrch2"/></span>
                    </div>
                    <div class="wj-gridWrap" style="height:150px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                              control="flex"
                              autoGenerateColumns="false"
                              selection-mode="Row"
                              initialized="initGrid(s,e)"
                              items-source="data"
                              item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="orderStatus.saleDate"/>" binding="saleDate" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="orderStatus.cnt"/>" binding="cancelCnt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
            <%-- 캐셔별 그리드 --%>
            <div ng-controller="orderCancelSrch3Ctrl">
                <div class="w100">
                    <div class="updownSet">
                        <span class="fl bk lh30"><s:message code="orderStatus.orderCancelSrch3"/></span>
                    </div>
                    <div class="wj-gridWrap" style="height:150px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                              control="flex"
                              autoGenerateColumns="false"
                              selection-mode="Row"
                              initialized="initGrid(s,e)"
                              items-source="data"
                              item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="orderStatus.cashier"/>" binding="empNo" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="orderStatus.cashier"/>" binding="empNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="orderStatus.cnt"/>" binding="cancelCnt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%-- 그리드 --%>
    <div class="wj-TblWrap w80 fr" ng-controller="orderCancelCtrl">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue fr mt5 mb5" ng-click="excelDownload()" ><s:message code="cmm.excel.down"/></button>
        <div class="w100" style="height:460px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                  control="flex"
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  initialized="initGrid(s,e)"
                  items-source="data"
                  item-formatter="_itemFormatter"
                  id="wjGridOrderCancel">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="orderStatus.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.orderNo"/>" binding="orderNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.tblGrp"/>" binding="tblGrpNm" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.tblNm"/>" binding="tblNm" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.cancelFg"/>" binding="orderFg" width="70" align="center" is-read-only="true" data-map="orderFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.orderRegDt"/>" binding="orderRegDt" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.orderSeq"/>" binding="orderSeq" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.fg"/>" binding="orderDtlFg" width="70" align="center" is-read-only="true" data-map="orderFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.qty"/>" binding="saleQty" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.dc"/>" binding="dcAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.cashier"/>" binding="empNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.moveTbl"/>" binding="moveTblNo" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderStatus.moveTbl"/>" binding="moveTblCd" width="70" align="center" is-read-only="true"  visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="saleYn" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="webReg" width="70" align="center" is-read-only="true"  visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
</div>

<script type="text/javascript">
  var payColList = [];
  var dcColList  = [];
  <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
  <c:forEach var="payCol" items="${payColList}">
  var payParam       = {};
  payParam.payCd     = "${payCol.payCd}";
  payParam.payMethod = "${payCol.payMethod}";
  payColList.push(payParam);
  </c:forEach>

  <%--javascript에서 사용할 할인 json 데이터 생성--%>
  <c:forEach var="dcCol" items="${dcColList}">
  var dcParam      = {};
  dcParam.dcCd     = "${dcCol.dcCd}";
  dcParam.dcMethod = "${dcCol.dcMethod}";
  dcColList.push(dcParam);
  </c:forEach>

  var payCol      = '${payCol}';
  var dcCol       = '${dcCol}';
  var guestCol    = '${guestCol}';
  var arrPayCol   = payCol.split(',');
  var arrDcCol    = dcCol.split(',');
  var arrGuestCol = guestCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/orderStatus/orderCancel.js?ver=20230608.01" charset="utf-8"></script>

<%-- 영수증 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/cmmSalePopup/billInfo/billInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="payColList" value="${payColList}"/>
  <c:param name="guestColList" value="${guestColList}"/>
</c:import>