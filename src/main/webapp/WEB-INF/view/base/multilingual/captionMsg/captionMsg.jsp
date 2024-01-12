<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="captionMsgCtrl" id="captionMsgView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="captionMsg.captionMsg"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('captionMsgCtrl', 1)">
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
            <th><s:message code="captionMsg.captionMsgGrp2"/></th>
            <td colspan="3">
                <div class="sb-select w30">
                    <wj-combo-box
                        id="srchCaptionMsgGrp"
                        ng-model="captionMsgGrp"
                        items-source="_getComboData('captionMsgGrp')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        control="srchCaptionMsgGrpCombo"
                        selected-index-changed="getCaptionMsgList()">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- left--%>
    <div class="wj-TblWrap mt10 mb20 w50 fl">
        <div class="wj-TblWrapBr pd10" style="height:470px; overflow-y: hidden;">
            <div class="updownSet oh">
                <%-- 추가 --%>
                <button class="btn_skyblue" id="btnAddRow" ng-click="addRow()" >
                    <s:message code="cmm.add" />
                </button>
                <%-- 삭제 --%>
                <button class="btn_skyblue" id="btnDelRow" ng-click="delRow()" >
                    <s:message code="cmm.delete" />
                </button>
                <%-- 저장 --%>
                <button class="btn_skyblue" id="btnSaveRow" ng-click="saveRow()" >
                    <s:message code="cmm.save" />
                </button>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
                <%-- 양식다운로드 --%>
                <button class="btn_skyblue" ng-click="sampleDownload()">
                    <s:message code="cmm.excel.sampleDown" />
                </button>
                <%-- 엑셀업로드 --%>
                <button class="btn_skyblue" ng-click="excelUpload()">
                    <s:message code="cmm.excel.excelUpload" />
                </button>
            </div>

            <%-- 위즈모 테이블 --%>
            <div class="wj-TblWrap mt5">
                <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        control="flex"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        initialized="initGrid(s,e)"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="captionMsg.captionMsgId"/>" binding="captionMsgId" align="left" width="200" max-length="33"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="captionMsg.captionMsgGb"/>" binding="captionMsgGb" data-map="captionMsgGbDataMap" align="center" width="100"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="captionMsg.captionMsgNm"/>" binding="captionMsgNm" align="left" width="200" max-length="160"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="captionMsg.en"/>" binding="captionMsgEnNm" align="left" width="150" max-length="160"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="captionMsg.cn"/>" binding="captionMsgCnNm" align="left" width="150" max-length="160"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="captionMsg.jp"/>" binding="captionMsgJpNm" align="left" width="150" max-length="160"></wj-flex-grid-column>

                        <%-- 저장시 필요 --%>
                        <wj-flex-grid-column header="<s:message code="captionMsg.captionImgCd"/>" binding="captionImgCd" width="0" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <%-- right --%>
    <div class="wj-TblWrap mt10 mt10 w50 fr">
        <div class="wj-TblWrapBr" style="height: 470px; overflow: auto;">
            <%-- 이미지 --%>
            <img id="imgCaptionMsgGrpView" style="width:100%;"/>
        </div>
    </div>
</div>

<%-- 양식다운로드/엑셀업로드 관련 --%>
<div style="display: none;" ng-controller="captionMsgExcelCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
           <wj-flex-grid-column header="<s:message code="captionMsg.captionMsgId"/>" binding="captionMsgId" align="left" width="200" max-length="33"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="captionMsg.captionMsgGb"/>" binding="captionMsgGb" data-map="captionMsgGbDataMap" align="center" width="100"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="captionMsg.captionMsgNm"/>" binding="captionMsgNm" align="left" width="200" max-length="160"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="captionMsg.en"/>" binding="captionMsgEnNm" align="left" width="150" max-length="160"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="captionMsg.cn"/>" binding="captionMsgCnNm" align="left" width="150" max-length="160"></wj-flex-grid-column>
           <wj-flex-grid-column header="<s:message code="captionMsg.jp"/>" binding="captionMsgJpNm" align="left" width="150" max-length="160"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>

    <input type="file" class="form-control" id="excelUpFile"
            ng-model="excelUpFile"
            onchange="angular.element(this).scope().excelFileChanged()"
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/captionMsg/captionMsg.js?ver=20240111.01" charset="utf-8"></script>