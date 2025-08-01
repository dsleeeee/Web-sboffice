<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="dcDetailMrpizzaAllStoreView" name="dcDetailMrpizzaAllStoreView" class="subCon" style="display: none;">

    <div ng-controller="dcDetailMrpizzaAllStoreCtrl">
         <%-- 조회조건 --%>
         <div class="searchBar">
             <a href="#" class="open fl"> <s:message code="dcDetailMrpizza.allStore"/></a>
             <%-- 조회 --%>
             <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                 <button class="btn_blue fr" ng-click="_broadcast('dcDetailMrpizzaAllStoreCtrl',1)">
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
                         <span class="txtIn"> <input id="startDateDcDetailMrpizzaAllStore" name="startDate" class="w110px"/></span>
                         <span class="rg">~</span>
                         <span class="txtIn"> <input id="endDateDcDetailMrpizzaAllStore" name="endDate" class="w110px"/></span>
                     </div>
                 </td>
             </tr>
         </table>

         <div class="mt10 oh sb-select dkbr">
             <%-- 조회조건 엑셀다운로드 --%>
             <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
         </div>

         <div class="w100 mt10">
             <div class="wj-gridWrap" style="height: 280px; overflow-x: hidden; overflow-y: hidden;">
                 <wj-flex-grid
                         id="wjGridList"
                         autoGenerateColumns="false"
                         selection-mode="Row"
                         items-source="data"
                         control="flex"
                         initialized="initGrid(s,e)"
                         is-read-only="true"
                         item-formatter="_itemFormatter">

                     <!-- define columns -->
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcCode"/>" binding="dcCode" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcType"/>" binding="dcType" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcCnt"/>" binding="dcCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.payAmt"/>" binding="payAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcAmt"/>" binding="dcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcCnt"/>" binding="dcCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.payAmt"/>" binding="payAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcAmt"/>" binding="dcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcCnt"/>" binding="dcCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.payAmt"/>" binding="payAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                     <wj-flex-grid-column header="<s:message code="dcDetailMrpizza.dcAmt"/>" binding="dcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>



                 </wj-flex-grid>
             </div>
         </div>

         <%-- 페이지 리스트 --%>
         <div class="pageNum mt20">
             <%-- id --%>
             <ul id="dcDetailMrpizzaAllStoreCtrlPager" data-size="10">
             </ul>
         </div>
         <%-- //페이지 리스트 --%>

     </div>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="dcDetailMrpizzaAllStoreExcelCtrl">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridExcelList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaAllStore.js?ver=20250730.01" charset="utf-8"></script>
