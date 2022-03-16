<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div id="offAddMonthView" name="offAddMonthView" class="subCon" style="display: none;">

     <div ng-controller="offAddMonthCtrl">
          <%-- 조회조건 --%>
          <div class="searchBar flddUnfld">
               <a href="#" class="open fl"> <s:message code="offAdd.month" /></a>
               <%-- 조회 --%>
               <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_broadcast('offAddMonthCtrl',1)">
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
                         <th>
                              <s:message code="offAdd.month.date" />
                         </th>
                         <td colspan="3">
                              <div class="sb-select">
                                   <span class="txtIn"> <input id="startDateOffAddMonth" name="startDate" class="w110px" /></span>
                                   <span class="rg">~</span>
                                   <span class="txtIn"> <input id="endDateOffAddMonth" name="endDate" class="w110px" /></span>
                              </div>
                         </td>
                    </tr>
                    <c:if test="${orgnFg == 'HQ'}">
                         <tr>
                              <%-- 매장코드 --%>
                              <th><s:message code="offAdd.month.store"/></th>
                              <td colspan="3">
                                   <%-- 매장선택 모듈 싱글 선택 사용시 include
                                        param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                                     displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                                     modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                                     closeFunc - 팝업 닫기시 호출할 함수
                                   --%>
                                   <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                                        <jsp:param name="targetId" value="offAddMonthStore"/>
                                   </jsp:include>
                                   <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                              </td>
                         </tr>
                    </c:if>
                    <c:if test="${orgnFg == 'STORE'}">
                         <input type="hidden" id="offAddMonthStoreCd" value="${storeCd}" />
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
                                   <wj-flex-grid-column header="<s:message code="offAdd.month.saleDate"/>" binding="saleDate" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.month.storeCnt"/>" binding="storeCnt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.month.saleCnt"/>" binding="saleCnt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.month.totSaleAmt"/>" binding="totSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.month.totDcAmt"/>" binding="totDcAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.month.realSaleAmt"/>" binding="realSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                                   <wj-flex-grid-column header="<s:message code="offAdd.month.totOffaddAmt"/>" binding="totOffaddAmt" width="120" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                              </wj-flex-grid>

                              <%-- ColumnPicker 사용시 include --%>
                              <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                                   <jsp:param name="pickerTarget" value="offAddMonthCtrl"/>
                              </jsp:include>
                              <%--// ColumnPicker 사용시 include --%>

                         </div>
                    </div>
               </div>
          </div>
          <%--left--%>
     </div>

     <%--right--%>
     <div class="wj-TblWrap mt20 mb20 w50 fr" ng-controller="offAddMonthDetailCtrl">
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
                              <wj-flex-grid-column header="<s:message code="offAdd.month.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="offAdd.month.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="offAdd.month.saleQty"/>" binding="saleQty" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="offAdd.month.realSaleAmt"/>" binding="realSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                              <wj-flex-grid-column header="<s:message code="offAdd.month.orderAddFg"/>" binding="orderAddFg" width="120" is-read-only="true" align="center" data-map="orderAddFgDataMap"></wj-flex-grid-column>

                         </wj-flex-grid>

                         <%-- ColumnPicker 사용시 include --%>
                         <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                              <jsp:param name="pickerTarget" value="offAddMonthDetailCtrl"/>
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

<script type="text/javascript" src="/resource/solbipos/js/sale/status/offAdd/offAddMonth.js?ver=20220314.01" charset="utf-8"></script>