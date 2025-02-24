<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div id="offAddDayView" name="offAddDayView" class="subCon" style="display: none;">

     <div ng-controller="offAddDayCtrl">
          <%-- 조회조건 --%>
          <div class="searchBar">
               <a href="#" class="open fl"> <s:message code="offAdd.day" /></a>
               <%-- 조회 --%>
               <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_broadcast('offAddDayCtrl',1)">
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
                    <tr>
                         <%-- 조회일자 --%>
                         <th><s:message code="cmm.search.date" /></th>
                         <td colspan="3">
                              <div class="sb-select">
                                   <span class="txtIn"> <input id="startDateOffAddDay" name="startDate" class="w110px" /></span>
                                   <span class="rg">~</span>
                                   <span class="txtIn"> <input id="endDateOffAddDay" name="endDate" class="w110px" /></span>
                              </div>
                         </td>
                    </tr>
                    <c:if test="${orgnFg == 'HQ'}">
                         <tr>
                              <%-- 매장선택 --%>
                              <th><s:message code="cmm.store.select"/></th>
                              <td colspan="3">
                                   <%-- 매장선택 모듈 사용시 include --%>
                                   <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                                        <jsp:param name="targetTypeFg" value="M"/>
                                        <jsp:param name="targetId" value="offAddDayStore"/>
                                   </jsp:include>
                                   <%--// 매장선택 모듈 사용시 include --%>
                              </td>
                         </tr>
                    </c:if>
                    <c:if test="${orgnFg == 'STORE'}">
                         <input type="hidden" id="offAddDayStoreCd" value="${storeCd}" />
                    </c:if>
               </tbody>
          </table>

          <%--left--%>
          <div class="wj-TblWrap mt20 mb20 w50 fl">
               <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                    <div class="updownSet oh mb10">
                         <%-- 시간대별 엑셀다운로드 --%>
                         <button class="btn_skyblue ml5 fr" ng-click="excelDownloadPeriodSaleTime()"><s:message code="cmm.excel.down"/></button>
                    </div>
                    <div class="w100 mt10 mb20">
                         <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                              <wj-flex-grid
                                   autoGenerateColumns="false"
                                   control="flex"
                                   initialized="initGrid(s,e)"
                                   selection-mode="Row"
                                   items-source="data"
                                   item-formatter="_itemFormatter">

                                   <!-- define columns -->
                                   <wj-flex-grid-column header="<s:message code="offAdd.day.saleDate"/>" binding="saleDate" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.day.storeCnt"/>" binding="storeCnt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.day.totSaleAmt"/>" binding="totSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.day.totDcAmt"/>" binding="totDcAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.day.realSaleAmt"/>" binding="realSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.day.totOffaddAmt"/>" binding="totOffaddAmt" width="120" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                              </wj-flex-grid>

                              <%-- ColumnPicker 사용시 include --%>
                              <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                                   <jsp:param name="pickerTarget" value="offAddDayCtrl"/>
                              </jsp:include>
                              <%--// ColumnPicker 사용시 include --%>

                         </div>
                    </div>
               </div>
          </div>
          <%--left--%>
     </div>

     <%--right--%>
     <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="offAddDayDetailCtrl">
          <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
               <div class="updownSet oh mb10">
                    <%-- 매출상세 엑셀다운로드 --%>
                    <button class="btn_skyblue ml5 fr" ng-click="excelDownloadPeriodSaleDtl()"><s:message code="cmm.excel.down"/></button>
               </div>
               <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                         <wj-flex-grid
                              autoGenerateColumns="false"
                              control="flex"
                              initialized="initGrid(s,e)"
                              selection-mode="Row"
                              items-source="data"
                              item-formatter="_itemFormatter">

                              <!-- define columns -->
                              <wj-flex-grid-column header="<s:message code="offAdd.day.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="offAdd.day.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="offAdd.day.saleQty"/>" binding="saleQty" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="offAdd.day.realSaleAmt"/>" binding="realSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="offAdd.day.orderAddFg"/>" binding="orderAddFg" width="120" is-read-only="true" align="center" data-map="orderAddFgDataMap"></wj-flex-grid-column>

                         </wj-flex-grid>

                         <%-- ColumnPicker 사용시 include --%>
                         <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                              <jsp:param name="pickerTarget" value="offAddDayDetailCtrl"/>
                         </jsp:include>
                         <%--// ColumnPicker 사용시 include --%>

                    </div>
               </div>
          </div>
     </div>
     <%--right--%>

</div>

<script type="text/javascript">
     var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/offAdd/offAddDay.js?ver=20240605.01" charset="utf-8"></script>