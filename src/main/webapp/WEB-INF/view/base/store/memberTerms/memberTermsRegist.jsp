<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />

<wj-popup control="wjMemberTermsRegisLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:350px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="memberTermsRegist.verInfo" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" ng-controller="memberTermsRegistCtrl">
            <div>
                <div style="height:200px; overflow-y: auto;">
                    <f:form id="regForm" name="regForm" >
                        <h3 class="h3_tbl"><s:message code="memberTermsRegist.verInfo" /></h3>
                        <table class="searchTbl">
                            <colgroup>
                                <col class="w13" />
                                <col class="w37" />
                                <col class="w13" />
                                <col class="w37" />
                            </colgroup>
                            <tbody>
                            <tr>
                                <%-- 파일번호 --%>
                                <th>
                                    <s:message code="memberTermsRegist.verSerNo" />
                                </th>
                                <td>
                                    <input type="text" class="sb-input w100" id="verSerNo" ng-model="version.verSerNo" readonly>
                                </td>
                                <%-- 파일명 --%>
                                <th>
                                    <s:message code="memberTermsRegist.verSerNm" />
                                </th>
                                <td>
                                    <input type="text" class="sb-input w100" id="verSerNm" ng-model="version.verSerNm">
                                </td>
                            </tr>
                            <tr>
                                <%-- 첨부파일 --%>
                                <th>
                                    <s:message code="memberTermsRegist.file" />
                                </th>
                                <td id="fileIn">
                                    <input type="file" class="form-control" id="file"
                                           name="file"
                                           ng-model="version.uploadFile"
                                           onchange="angular.element(this).scope().uploadChange()"
                                           accept="getFileType()"/>
                                </td>
                                <th id="fileOrgH" style="display:none;"><s:message code="memberTermsRegist.fileNm"/></th>
                                <td id="fileOrgD" style="display:none;">
                                    <input type="text" class="sb-input w100" id="fileOrgNm" ng-model="version.fileOrgNm" readonly/>
                                </td>
                            </tr>
                            <tr>
                                <%-- 파일사이즈 --%>
                                <th>
                                    <s:message code="memberTermsRegist.fileSize" />
                                </th>
                                <td>
                                    {{version.fileSize}}
                                </td>
                            </tr>
                            <tr style="display: none;">
                                <%-- 사용일자 --%>
                                <th>
                                    <s:message code="memberTermsRegist.useDate" />
                                </th>
                                <td colspan="3">
                                    <div class="sb-select">
                                        <span class="txtIn"><input id="startDate" class="w110px"></span>
                                        <span class="rg">~</span>
                                        <span class="txtIn"><input id="endDate" class="w110px"></span>
                                        <span style="background:#ebf5ff; border:1px solid #93cbfc; color:#337dde; border-radius:4px; line-height:23px; padding:5px 10px 5px 10px; font-size:0.75em; transition:all 0.2s;"><a href="#" ng-click="maxDate()"><s:message code="memberTermsRegist.maxDate" /></a></span>
                                    </div>
                                </td>
                            </tr>
                            <tr style="display: none;">
                                <%-- 파일타입 --%>
                                <th>
                                    <s:message code="memberTermsRegist.fileType" />
                                </th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="fileTypeCombo"
                                                ng-model="version.fileType"
                                                control="versionFileTypeCombo"
                                                items-source="_getComboData('fileTypeCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                selected-index-changed="fileTypeChg(s)">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                <%-- 사용여부 --%>
                                <th>
                                    <s:message code="memberTermsRegist.useYn" />
                                </th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="useYn"
                                                ng-model="version.useYn"
                                                control="versionUseYnCombo"
                                                items-source="_getComboData('registUseYnCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </f:form>
                </div>
                <div class="btnSet2">
                    <%-- 신규 --%>
                    <span><a href="#" class="btn_blue pd20" ng-click="mediaSave()" ng-show="!isEdit"><s:message code="cmm.save" /></a></span>
                    <%-- 수정 --%>
                    <span><a href="#" class="btn_blue pd20" ng-click="mediaSave()" ng-show="isEdit"><s:message code="cmm.save" /></a></span>
                    <%-- 닫기 --%>
                    <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    var orgnCd       = "${orgnCd}";
    var orgnFg       = "${orgnFg}";
    var hqOfficeCd   = "${hqOfficeCd}";
    var storeCd      = "${storeCd}";
    var userId       = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/memberTerms/memberTermsRegist.js?ver=20220708.02" charset="utf-8"></script>