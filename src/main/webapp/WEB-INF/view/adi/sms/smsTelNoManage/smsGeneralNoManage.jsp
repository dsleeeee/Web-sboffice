<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjSmsGeneralNoManageLayer" control="wjSmsGeneralNoManageLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:850px;height:500px;" fade-in="false" fade-out="false">
    <div ng-controller="smsGeneralNoManageCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsGeneralNoManage.info"/>
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w20"/>
                    <col class="w30"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 소속코드 --%>
                    <th>
                        <s:message code="smsGeneralNoManage.orgnCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgnCd" ng-model="orgnCd" />
                    </td>
                    <%-- 소속명 --%>
                    <th>
                        <s:message code="smsGeneralNoManage.orgnNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgnNm" ng-model="orgnNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 처리구분 --%>
                    <th>
                        <s:message code="smsGeneralNoManage.addProcFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchAddProcFgCombo"
                                    ng-model="addProcFg"
                                    items-source="_getComboData('addProcFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchAddProcFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('smsGeneralNoManageCtrl', 1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:250px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.orgnCd"/>" binding="orgnCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.orgnNm"/>" binding="orgnNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.orgnFg"/>" binding="orgnFg" data-map="orgnFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.addFg"/>" binding="addFg" data-map="addFgDataMap" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.download"/>" binding="download" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.addProcFg"/>" binding="addProcFg" data-map="addProcFgDataMap" width="70" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.telNo"/>" binding="telNo" width="90" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.returnRemark"/>" binding="returnRemark" width="95" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.remark"/>" binding="remark" width="95" align="center"></wj-flex-grid-column>

                        <%--저장시 필요--%>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.certId"/>" binding="certId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.fileName"/>" binding="fileName" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.backAddProcFg"/>" binding="backAddProcFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.backTelNo"/>" binding="backTelNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.backReturnRemark"/>" binding="backReturnRemark" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage.backRemark"/>" binding="backRemark" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSaveSmsGeneralNoManage" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>
        </div>

    </div>
</wj-popup>

<form id="smsGeneralNo_info" name="smsGeneralNo_info" method="post" action="/adi/sms/smsTelNoManage/smsGeneralNoManage/getSmsGeneralNoManageDownload.sb" target="smsGeneralNoFrm">
    <iframe name="smsGeneralNoFrm" style="display:none;"></iframe>

    <input type="hidden" name="fileName" value="" /> <%--파일명--%>
</form>

<script type="text/javascript">
    function smsGeneralNo_download(fileName)
    {
        document.smsGeneralNo_info.fileName.value = fileName;
        document.smsGeneralNo_info.submit();
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsTelNoManage/smsGeneralNoManage.js?ver=20220203.02" charset="utf-8"></script>