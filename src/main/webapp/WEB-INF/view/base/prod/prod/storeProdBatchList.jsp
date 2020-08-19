<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="storeProdBatchListLayer" control="storeProdBatchListLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">

    <div ng-controller="storeProdBatchListCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.title.storeList"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"><s:message code="prod.title.storeList"/></a>
                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_pageView('storeProdBatchListCtrl',1)">
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
                    <%-- 매장코드 --%>
                    <th><s:message code="prod.storeCd" /></th>
                    <td>
                        <input type="text" id="srchBatStoreCd" class="sb-input w100" ng-model="storeCd" />
                    </td>
                    <%-- 매장명 --%>
                    <th><s:message code="prod.storeNm" /></th>
                    <td>
                        <input type="text" id="srchBatStoreNm" class="sb-input w100" ng-model="storeNm" />
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            is-read-only="true">

                       <!-- define columns -->
                       <wj-flex-grid-column header="<s:message code="prod.hqOfficeCd"/>" binding="hqOfficeCd" is-read-only="true" align="center"></wj-flex-grid-column>
                       <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                       <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="350"is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
               </div>
           </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/storeProdBatchList.js?ver=20200213.15" charset="utf-8"></script>