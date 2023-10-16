<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="storeVerDelCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeVerDel.storeVerDel"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('storeVerDelCtrl')"><s:message code="cmm.search"/></button>
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
                <%-- 본사코드 --%>
                <th><s:message code="storeVerDel.hqOfficeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 본사명 --%>
                <th><s:message code="storeVerDel.hqOfficeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="storeVerDel.storeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
                </td>
                <%-- 매장명 --%>
                <th><s:message code="storeVerDel.storeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
                </td>
            </tr>
            <tr>
                <%-- 버전일련번호 --%>
                <th><s:message code="storeVerDel.verSerNo" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchVerSerNo" ng-model="verSerNo" onkeyup="fnNxBtnSearch();" />
                </td>
                <%-- 포스버전구분 --%>
                <th><s:message code="storeVerDel.progFg" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                            id="srchprogFg"
                            ng-model="progFg"
                            items-source="_getComboData('progFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchProgFgCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
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

        <%-- 삭제 --%>
        <button class="btn_skyblue ml5 fr" ng-click="del()"><s:message code="cmm.del" /></button>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
    </div>

    <%-- 매장 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                control="flex"
                autoGenerateColumns="false"
                selection-mode="Row"
                initialized="initGrid(s,e)"
                items-source="data"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.storeCd"/>" binding="storeCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.storeNm"/>" binding="storeNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.curProg"/>" binding="curProgFg" data-map="progFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.verSerNo"/>" binding="verSerNo" align="center" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.verSerNm"/>" binding="verSerNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.progFg"/>" binding="progFg" data-map="progFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="storeVerDelCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<%-- 엑셀다운로드 그리드 --%>
<div class="w100 mt10 mb20" style="display:none;" ng-controller="storeVerDelExcelCtrl">
    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
            control="excelFlex"
            autoGenerateColumns="false"
            selection-mode="Row"
            initialized="initGrid(s,e)"
            items-source="data"
            item-formatter="_itemFormatter">

            <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.storeCd"/>" binding="storeCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.storeNm"/>" binding="storeNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.curProg"/>" binding="curProgFg" data-map="progFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.verSerNo"/>" binding="verSerNo" align="center" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.verSerNm"/>" binding="verSerNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerDel.progFg"/>" binding="progFg" data-map="progFgDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
    </div>
</div>

<script type="text/javascript">
    var progFg = ${ccu.getCommCode("059")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/confg/storeVerDel/storeVerDel.js?ver=202231013.01" charset="utf-8"></script>

