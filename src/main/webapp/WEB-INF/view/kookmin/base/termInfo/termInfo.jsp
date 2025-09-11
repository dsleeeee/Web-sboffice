<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="termInfoCtrl" >
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('termInfoCtrl',1)">
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
                <%-- 년도 --%>
                <th>
                    <s:message code="termInfo.srchYear"/>
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchDate" ng-model="srchDate" class="w110px"></span>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.save" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10">
        <div id="theGrid" style="height: 350px;">
            <wj-flex-grid
                    id="wjGridTermInfo"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="termInfo.termYear"/>"     binding="termYear"  width="60" align="center"  ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="termInfo.termFg"/>"       binding="termFg"    width="80"  align="center"  data-map="termFgComboDataMap" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="termInfo.startDate"/>"    binding="startDate" width="150" align="center"  >
                        <wj-flex-grid-cell-template cell-type="CellEdit">
                            <div class="sb-select">
                                    <span class="txtIn w100px">
                                        <wj-input-date
                                                value="$value"
                                                placeholder="미입력"
                                                is-required="false"
                                                is-editable="true"
                                                format="yyyy-MM-dd"
                                                initialized="initInputDate(s)">
                                        </wj-input-date>
                                    </span>
                            </div>
                        </wj-flex-grid-cell-template>
                    </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="termInfo.endDate"/>"       binding="endDate"  width="150" align="center"  >
                        <wj-flex-grid-cell-template cell-type="CellEdit">
                            <div class="sb-select">
                                    <span class="txtIn w100px">
                                        <wj-input-date
                                                value="$value"
                                                placeholder="미입력"
                                                is-required="false"
                                                is-editable="true"
                                                format="yyyy-MM-dd"
                                                initialized="initInputDate(s)">
                                        </wj-input-date>
                                    </span>
                            </div>
                        </wj-flex-grid-cell-template>
                    </wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/base/termInfo/termInfo.js?ver=20250905.01" charset="utf-8"></script>


