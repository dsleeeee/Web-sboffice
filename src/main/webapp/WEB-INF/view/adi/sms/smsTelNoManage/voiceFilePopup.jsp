<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 음성파일 등록 팝업 --%>
<wj-popup control="wjVoiceFileLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:620px;height:520px;" fade-in="false" fade-out="false">
    <div ng-controller="voiceFileCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span class="ml20"><s:message code="smsGeneralNoManage2.voiceFileTitle"/></span>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="height:470px;">
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w80"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 첨부파일 --%>
                    <th><s:message code="smsGeneralNoManage2.voiceFileAtch"/></th>
                    <td>
                        <f:form id="voiceFileForm" name="voiceFileForm" method="post" enctype="multipart/form-data">
                            <input multiple="multiple" type="file" id="voiceFile" name="file" onchange="voiceFilePreview()"/>
                        </f:form>
                        <span class="ml10" style="color:#888; font-size:12px;"><s:message code="smsGeneralNoManage2.voiceFileMaxDesc"/></span>
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 첨부파일 그리드 --%>
            <div class="w100 mt10">
                <div class="wj-gridWrap" style="height:250px; overflow-y:hidden; overflow-x:hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            is-read-only="true">

                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.voiceFileAtch"/>" binding="orginlFileNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.voiceFileDownload"/>" binding="download" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.voiceFileDel"/>" binding="del" width="70" is-read-only="true" align="center"></wj-flex-grid-column>

                        <wj-flex-grid-column header="" binding="fileNm" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="fileExt" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="idx" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <%-- 저장 버튼 --%>
            <div class="tc mt10">
                <button id="funcSaveVoiceFile" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsTelNoManage/voiceFilePopup.js?ver=20260824.01" charset="utf-8"></script>
