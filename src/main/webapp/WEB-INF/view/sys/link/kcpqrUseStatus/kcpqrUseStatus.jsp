<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="kcpqrStatusCtrl">
    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('kcpqrStatusCtrl',1)">
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
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
                <%-- 구분 --%>
                <th><s:message code="kcpqrUseStatus.srchFg" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchFg"
                                ng-model="srchFg"
                                items-source="_getComboData('srchFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchFgCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    id="wjGridList"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.hqOfficeCd"/>"     binding="hqOfficeCd"        width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.hqOfficeNm"/>"     binding="hqOfficeNm"        width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.storeCd"/>"        binding="storeCd"           width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.storeNm"/>"        binding="storeNm"           width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.agencyUseYn"/>"    binding="agencyUseYn"       width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.lastResponse"/>"   binding="lastResponse"      width="*"   is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.lastResponseDt"/>" binding="lastResponseDt"    width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.saleCnt"/>"        binding="saleCnt"           width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.lMenuNm"/>"        binding="lMenuNm"           width="80"  is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.mMenuNm"/>"        binding="mMenuNm"           width="80"  is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.sMenuNm"/>"        binding="sMenuNm"           width="120" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.hqOfficeCd"/>"     binding="hqOfficeCd"        width="80"  is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.hqOfficeNm"/>"     binding="hqOfficeNm"        width="120" is-read-only="true" align="left"   visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.storeCd"/>"        binding="storeCd"           width="80"  is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.storeNm"/>"        binding="storeNm"           width="120" is-read-only="true" align="left"   visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.orgnFg"/>"         binding="orgnFg"            width="70"  is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.userId"/>"         binding="userId"            width="70"  is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.userNm"/>"         binding="userNm"            width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kcpqrUseStatus.useCnt"/>"         binding="useCnt"            width="70"  is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="kcpqrStatusCtrl"/>
            </jsp:include>
        </div>
    </div>
</div>


<script type="text/javascript" src="/resource/solbipos/js/sys/link/kcpqrUseStatus/kcpqrUseStatus.js?ver=20260121.01" charset="utf-8"></script>