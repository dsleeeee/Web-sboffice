<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="mediaPlaySeqView" class="subCon" style="display: none;">
    <div ng-controller="mediaPlaySeqCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="mediaPlaySeq.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('mediaPlaySeqCtrl',1)">
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
                <%-- 파일타입 --%>
                <th><s:message code="mediaPlaySeq.fileType" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchFileType"
                                ng-model="fileType"
                                items-source="_getComboData('fileTypeMediaPlaySeq')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="updownSet oh mb10">
                <%-- UP --%>
                <button class="btn_up" id="btnUp" ng-click="rowMoveUp()"><s:message code="cmm.up" /></button>
                <%-- DOWN --%>
                <button class="btn_down" id="btnDown" ng-click="rowMoveDown()"><s:message code="cmm.down" /></button>
                <%-- 저장 --%>
                <button class="btn_skyblue" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
            </div>
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mediaPlaySeq.verSerNo"/>" binding="verSerNo" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mediaPlaySeq.verSerNm"/>" binding="verSerNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mediaPlaySeq.useDate"/>" binding="useDate" align="center" width="160" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mediaPlaySeq.fileNm"/>" binding="fileOrgNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mediaPlaySeq.fileType"/>" binding="fileUseType" data-map="fileTypeDataMap" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mediaPlaySeq.fileSize"/>" binding="fileSize" width="80" align="right" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mediaPlaySeq.useYn"/>" binding="useYn" data-map="useYnDataMap" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mediaPlaySeq.dispTime"/>" binding="dispTime" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script>
    var useYn = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/media/mediaPlaySeq.js?ver=20221017.06" charset="utf-8"></script>