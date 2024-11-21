<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjSmsGeneralNoManage2Layer" control="wjSmsGeneralNoManage2Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:850px;height:500px;" fade-in="false" fade-out="false">
    <div ng-controller="smsGeneralNoManage2Ctrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsGeneralNoManage2.info"/>
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
                        <s:message code="smsGeneralNoManage2.orgnCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgnCd" ng-model="orgnCd" />
                    </td>
                    <%-- 소속명 --%>
                    <th>
                        <s:message code="smsGeneralNoManage2.orgnNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchOrgnNm" ng-model="orgnNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 처리구분 --%>
                    <th>
                        <s:message code="smsGeneralNoManage2.addProcFg" />
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

            <div class="mt10 oh">
                <%--조회--%>
                <button class="btn_skyblue fr" id="btnSearch" ng-click="_broadcast('smsGeneralNoManage2Ctrl', 1)"><s:message code="cmm.search" /></button>
                <p class="tl s14 mt5 lh15">- 통화일시는 녹취확인용으로 반드시 년/월/일 시간 정보로 필수 입력해주세요.</p>
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
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.orgnCd"/>" binding="orgnCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.orgnNm"/>" binding="orgnNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.orgnFg"/>" binding="orgnFg" data-map="orgnFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.vfYn"/>" binding="vfYn" data-map="vfYnDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.preview1"/>" binding="preview1" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.preview2"/>" binding="preview2" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.preview3"/>" binding="preview3" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.download1"/>" binding="download1" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.download2"/>" binding="download2" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.download3"/>" binding="download3" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.addProcFg"/>" binding="addProcFg" data-map="addProcFgDataMap" width="70" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.telNo"/>" binding="telNo" width="90" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.addSmsUserNm"/>" binding="addSmsUserNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.addSmsTelNo"/>" binding="addSmsTelNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.telDt"/>" binding="telDt" width="95" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.returnRemark"/>" binding="returnRemark" width="95" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.remark"/>" binding="remark" width="95" align="center"></wj-flex-grid-column>

                        <%--저장시 필요--%>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.certId"/>" binding="certId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.fileName1"/>" binding="fileName1" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.fileName2"/>" binding="fileName2" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.backAddProcFg"/>" binding="backAddProcFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.backTelNo"/>" binding="backTelNo" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.backTelDt"/>" binding="backTelDt" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.backReturnRemark"/>" binding="backReturnRemark" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="smsGeneralNoManage2.backRemark"/>" binding="backRemark" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSaveSmsGeneralNoManage2" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>
        </div>

    </div>
</wj-popup>

<form id="smsGeneralNo_info2" name="smsGeneralNo_info2" method="post" action="/adi/sms/smsTelNoManage/smsGeneralNoManage/getSmsGeneralNoManageDownload.sb" target="smsGeneralNoFrm2">
    <iframe name="smsGeneralNoFrm2" style="display:none;"></iframe>

    <input type="hidden" name="fileName" value="" /> <%--파일명--%>
</form>

<script type="text/javascript">
    function smsGeneralNo_download2(fileName)
    {
        document.smsGeneralNo_info2.fileName.value = fileName;
        document.smsGeneralNo_info2.submit();
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsTelNoManage/smsGeneralNoManage2.js?ver=20241121.01" charset="utf-8"></script>

<%-- SMS 발신번호 서류인증 미리보기 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsTelNoManage/smsPreview.jsp">
</c:import>