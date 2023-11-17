<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}" />

<wj-popup control="captionMsgGrpRegLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:290px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="captionMsgGrpRegCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="lblTitle"></span>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div>
                <div  style="height:280px; overflow-y: auto;">
                    <f:form id="regForm" name="regForm" >
                        <table class="tblType01">
                            <colgroup>
                                <col class="w15" />
                                <col class="w35" />
                                <col class="w15" />
                                <col class="w35" />
                            </colgroup>
                            <tbody>
                                <tr>
                                    <%-- 화면구분코드 --%>
                                    <th><s:message code="multilingualCaptionMsg.captionImgCd" /></th>
                                    <td><input type="text" class="sb-input w100" id="regCaptionImgCd" ng-model="version.captionImgCd" readonly></td>
                                    <%-- 화면구분명 --%>
                                    <th><s:message code="multilingualCaptionMsg.captionImgNm" /></th>
                                    <td><input type="text" class="sb-input w100" id="regCaptionImgNm" ng-model="version.captionImgNm"></td>
                                </tr>
                                <tr>
                                    <%-- 첨부파일 --%>
                                    <th><s:message code="multilingualCaptionMsg.file"/></th>
                                    <td id="fileIn">
                                        <input type="file" class="form-control" id="file"
                                               name="file"
                                               ng-model="version.uploadFile"
                                               onchange="angular.element(this).scope().uploadChange()"
                                               accept=".png,.PNG"/>
                                    </td>
                                    <th id="fileOrgH" style="display:none;"><s:message code="multilingualCaptionMsg.fileOrgNm"/></th>
                                    <td id="fileOrgD" style="display:none;">
                                        <input type="text" class="sb-input w100" id="fileOrgNm" ng-model="version.fileOrgNm" readonly/>
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 파일사이즈 --%>
                                    <th><s:message code="multilingualCaptionMsg.fileSize" /></th>
                                    <td colspan="3">
                                        <p class="s12 red mb10" id="fileSizeMax"><s:message code="multilingualCaptionMsg.fileSize.max" /></p>
                                        <p class="s12 red mb10"><label id="lblFileSizeMax"></label></p>
                                        {{version.fileSize}}
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 파일설명 --%>
                                    <th><s:message code="multilingualCaptionMsg.fileDesc" /></th>
                                    <td colspan="3">
                                        <input type="text" class="sb-input w100" id="fileDesc" ng-model="version.fileDesc" maxlength="300">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </f:form>
                    <div class="btnSet2">
                        <%-- 신규 --%>
                        <span><a href="#" class="btn_blue pd20" ng-click="save()" ng-show="!isEdit"><s:message code="cmm.save"/></a></span>
                        <%-- 수정 --%>
                        <span><a href="#" class="btn_blue pd20" ng-click="save()" ng-show="isEdit"><s:message code="cmm.save"/></a></span>
                        <%-- 닫기 --%>
                        <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close"/></a></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    var orgnCd = "${orgnCd}";
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/multilingualCaptionMsg/captionMsgGrpReg.js?ver=20231117.01" charset="utf-8"></script>