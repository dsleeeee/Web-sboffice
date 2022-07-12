<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="memberTermsCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('memberTermsCtrl',1)">
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
            <!-- 사용일자 -->
            <th>
                <s:message code="memberTerms.useDate" />
            </th>
            <td >
                <div class="sb-select">
                    <span class="txtIn w130px">
                        <input id="srchTimeStartDate" ng-model="startDate" class="w110px">
                    </span>
                    <span class="chk ml10">
                        <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt">
                            <s:message code="cmm.all.day" />
                        </label>
                    </span>
                </div>
            </td>
            <%-- 사용여부 --%>
            <th>
                <s:message code="memberTerms.useYn" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="useYnCombo"
                            ng-model="useYn"
                            items-source="_getComboData('useYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr style="display: none;">
            <%-- 파일번호 --%>
            <th>
                <s:message code="memberTerms.verSerNo" />
            </th>
            <td>
                <input type="text" id="srchVerSerNo" class="sb-input w100" ng-model="verSerNo" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 파일명 --%>
            <th>
                <s:message code="memberTerms.verSerNm" />
            </th>
            <td>
                <input type="text" id="srchVerSerNm" class="sb-input w100" ng-model="verSerNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr style="display: none;">
            <%-- 파일타입 --%>
            <th>
                <s:message code="memberTerms.fileType" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchFileType"
                            ng-model="fileType"
                            items-source="_getComboData('fileType')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 원본파일명 --%>
            <th>
                <s:message code="memberTerms.fileNm" />
            </th>
            <td>
                <input type="text" id="fileOrgNm" class="sb-input w100" ng-model="fileOrgNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <p class="tl s14 mt5 lh15">- [회원약관등록] : 신규 회원약관 등록</p>
        <p class="tl s14 mt5 lh15">- 신규 회원약관 등록 시 이전 회원약관은 미사용 처리됨</p>
    </div>

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
        <%-- 회원약관등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnRegist" ng-click="registVersion()">
            <s:message code="memberTerms.regist" />
        </button>
    </div>

    <%-- 그리드 --%>
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
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="90" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberTerms.verSerNo"/>" binding="verSerNo" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberTerms.verSerNm"/>" binding="verSerNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberTerms.useDate"/>" binding="useDate" align="center" width="160" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberTerms.fileNm"/>" binding="fileOrgNm" data-map="fileTypeDataMap" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberTerms.fileType"/>" binding="fileUseType" data-map="fileTypeDataMap" width="140" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberTerms.fileSize"/>" binding="fileSize"  width="80" align="right" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="memberTerms.useYn"/>" binding="useYn" data-map="useYnDataMap" align="center" width="80"  is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="memberTermsCtrl" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnComboData = ${ccu.getCommCode("067")};

    var clsFg       = ${ccu.getCommCodeExcpAll("059")};
    var sysStatFg   = ${ccu.getCommCodeExcpAll("005")};
    var hqList      = ${ccu.getHqOfficeList()};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/memberTerms/memberTerms.js?ver=20220708.01" charset="utf-8"></script>

<%-- 회원약관관리 신규등록 팝업 --%>
<c:import url="/WEB-INF/view/base/store/memberTerms/memberTermsRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원약관관리 상세 팝업 --%>
<c:import url="/WEB-INF/view/base/store/memberTerms/memberTermsDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 회원약관관리 매장추가 레이어 --%>
<c:import url="/WEB-INF/view/base/store/memberTerms/memberTermsStoreAdd.jsp">
</c:import>