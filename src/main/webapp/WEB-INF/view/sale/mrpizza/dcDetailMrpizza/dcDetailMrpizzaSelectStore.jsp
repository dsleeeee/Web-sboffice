<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="dcDetailMrpizzaSelectStoreView" name="dcDetailMrpizzaSelectStoreView" class="subCon" style="display: none;">

     <div ng-controller="dcDetailMrpizzaSelectStoreCtrl">
         <%-- 조회조건 --%>
         <div class="searchBar">
             <a href="#" class="open fl"> <s:message code="dcDetailMrpizza.selectStore"/></a>
             <%-- 조회 --%>
             <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                 <button class="btn_blue fr" ng-click="_broadcast('dcDetailMrpizzaSelectStoreCtrl',1)">
                     <s:message code="cmm.search"/>
                 </button>
             </div>
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
                 <td colspan="3">
                     <div class="sb-select">
                         <span class="txtIn"> <input id="startDateDcDetailMrpizzaSelectStore" name="startDate" class="w110px"/></span>
                         <span class="rg">~</span>
                         <span class="txtIn"> <input id="endDateDcDetailMrpizzaSelectStore" name="endDate" class="w110px"/></span>
                     </div>
                 </td>
             </tr>
             <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                 <tr>
                         <%-- 매장선택 --%>
                     <th><s:message code="cmm.store.select"/></th>
                     <td colspan="3">
                             <%-- 매장선택 모듈 사용시 include --%>
                         <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                             <jsp:param name="targetTypeFg" value="S"/>
                             <jsp:param name="targetId" value="dcDetailMrpizzaSelStore"/>
                         </jsp:include>
                             <%--// 매장선택 모듈 사용시 include --%>
                     </td>
                 </tr>
             </c:if>
             <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                 <input type="hidden" id="dcDetailMrpizzaSelStoreCd" value="${sessionInfo.storeCd}"/>
             </c:if>
             </tbody>
         </table>

         <div class="mt10 oh sb-select dkbr">
             <%-- 엑셀다운로드 --%>
             <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
         </div>

         <div class="w100 mt10">
             <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
                 <wj-flex-grid
                         id="wjGridList2"
                         autoGenerateColumns="false"
                         selection-mode="Row"
                         items-source="data"
                         control="flex"
                         initialized="initGrid(s,e)"
                         is-read-only="true"
                         item-formatter="_itemFormatter">

                     <!-- define columns -->
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcCode"/>" binding="coupnCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcType"/>" binding="coupnNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.totDcCnt"/>" binding="totDcCnt" width="85" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.totSaleAmt"/>" binding="totSaleAmt" width="85" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcAmt"/>" binding="dcAmt" width="85" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.realSaleAmt"/>" binding="realSaleAmt" width="85" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                 </wj-flex-grid>
             </div>
         </div>
     </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaSelectStore.js?ver=20250806.01" charset="utf-8"></script>
