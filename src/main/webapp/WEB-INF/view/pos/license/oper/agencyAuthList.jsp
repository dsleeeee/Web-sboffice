<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="agencyAuthListView" class="subCon" style="display: none;" ng-controller="agencyAuthListCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="oper.agencyAuthList" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('agencyAuthListCtrl',1)">
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
                    <s:message code="oper.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
                    </div>
                </td>
            </tr>
            <tr <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">style="display: none;"</c:if> >
                <%-- 업체코드 --%>
                <th>
                    <s:message code="oper.agencyCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchAgencyCd" ng-model="agencyCdAuth" />
                </td>
                <%-- 업체명 --%>
                <th>
                    <s:message code="oper.agencyNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchAgencyNm" ng-model="agencyNmAuth" />
                </td>
            </tr>
            <tr>
                <%-- 업체구분 --%>
                <th>
                    <s:message code="oper.agencyFg" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                            id="srchAgencyFg"
                            ng-model="agencyFg"
                            items-source="_getComboData('agencyFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
        </tbody>
    </table>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
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
                    <wj-flex-grid-column header="<s:message code="oper.agencyCd"/>" binding="agencyCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.agencyNm"/>" binding="agencyNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.agencyFg"/>" binding="agencyFg" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.ownerNm"/>" binding="ownerNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.buyCnt"/>" binding="buyCnt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.saleCnt"/>" binding="saleCnt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.instCnt"/>" binding="instCnt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.restCnt"/>" binding="restCnt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.regDt"/>" binding="regDt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.regId"/>" binding="regId" width="115" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnFgData = ${ccu.getCommCodeExcpAll("067")};

    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/oper/agencyAuthList.js?ver=2019052801.09" charset="utf-8"></script>