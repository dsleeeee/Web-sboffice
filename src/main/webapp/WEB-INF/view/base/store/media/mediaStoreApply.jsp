<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" ng-controller="mediaStoreApplyCtrl" id="mediaStoreApplyView" style="display: none;padding: 10px 20px 40px">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="mediaTab.mediaStoreApply" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch3" ng-click="_pageView('mediaStoreApplyCtrl', 1)">
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
            <th><s:message code="media.useDate" /></th>
            <td >
                <div class="sb-select">
            <span class="txtIn w130px">
                <input id="srchTimeStartDate2" ng-model="startDate" class="w110px">
            </span>
                    <span class="chk ml10">
              <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
              <label for="chkDt">
                <s:message code="cmm.all.day" />
              </label>
            </span>
                </div>
            </td>
            <%-- 원본파일명 --%>
            <th><s:message code="media.fileNm" /></th>
            <td>
                <input type="text" id="fileOrgNm" class="sb-input w100" ng-model="fileOrgNm" onkeyup="fnNxBtnSearch('3');"/>
            </td>
        </tr>
        <tr>
            <%-- 버전일련번호 --%>
            <th><s:message code="media.verSerNo" /></th>
            <td>
                <input type="text" id="srchVerSerNo" class="sb-input w100" ng-model="verSerNo" onkeyup="fnNxBtnSearch('3');"/>
            </td>
            <%-- 버전적용명 --%>
            <th><s:message code="media.verSerNm" /></th>
            <td>
                <input type="text" id="srchVerSerNm" class="sb-input w100" ng-model="verSerNm" onkeyup="fnNxBtnSearch('3');"/>
            </td>
        </tr>
        <tr>
            <%-- 파일타입 --%>
            <th><s:message code="media.fileType" /></th>
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
            <%-- 사용여부 --%>
            <th><s:message code="media.useYn" /></th>
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
        <tr>
            <%-- 언어구분 --%>
            <th><s:message code="media.langFg" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="langFgCombo"
                            ng-model="langFg"
                            items-source="_getComboData('langFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td colspan="3">
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="mediaStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
                    <input type="hidden" id="mediaStoreCd" value="${sessionInfo.storeCd}"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr"ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>

    <%-- 버전관리 그리드 --%>
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
                <wj-flex-grid-column header="<s:message code="media.store.storeCd"/>" binding="storeCd" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.store.storeNm"/>" binding="storeNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.verSerNo"/>" binding="verSerNo" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.verSerNm"/>" binding="verSerNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.useDate"/>" binding="useDate" align="center" width="160" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.fileNm"/>" binding="fileOrgNm" data-map="fileTypeDataMap" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.fileType"/>" binding="fileUseType" data-map="fileTypeDataMap" width="135" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.langFg"/>" binding="langFg" data-map="langFgDataMap" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.fileSize"/>" binding="fileSize"  width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                <%--        <wj-flex-grid-column header="<s:message code="media.regCnt"/>" binding="regCnt" align="center" width="80"  is-read-only="true"></wj-flex-grid-column>--%>
                <%--        <wj-flex-grid-column header="<s:message code="media.recvCnt"/>" binding="recvCnt" align="center" width="80"  is-read-only="true"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="media.useYn"/>" binding="useYn" data-map="useYnDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.preview"/>" binding="preview" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.download"/>" binding="downLoad" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.dispTime"/>" binding="dispTime" align="center" width="100" is-read-only="true"></wj-flex-grid-column>

                <%-- 저장시 필요 --%>
                <wj-flex-grid-column header="<s:message code="media.fileNmExt"/>" binding="fileNmExt" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.fileNm"/>" binding="realFileOrgNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="media.fileExt"/>" binding="fileExt" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="mediaStoreApplyCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script>
    // List 형식("" 안붙임)
    // 파일타입 콤보박스
    var fileTypeComboList = ${fileTypeComboList};
    var fileTypeComboListAll = ${fileTypeComboListAll};

    <%--var fileType    = ${ccu.getCommCode("303")};--%>
    var useYn       = ${ccu.getCommCode("067")};
    var clsFg       = ${ccu.getCommCodeExcpAll("059")};
    var sysStatFg   = ${ccu.getCommCodeExcpAll("005")};
    var hqList      = ${ccu.getHqOfficeList()};
    var hqOfficeCd  = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/media/mediaStoreApply.js?ver=20240605.01" charset="utf-8"></script>