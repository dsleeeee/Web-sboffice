<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<wj-popup control="captionMsgGrpDtlLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:290px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="captionMsgGrpDtlCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="captionMsg.captionMsgDtl"/>
            <span id="versionDetailTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div>
                <div  style="height:280px; overflow-y: auto;">
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
                                <th><s:message code="captionMsg.captionImgCd" /></th>
                                <td>{{version.captionImgCd}}</td>
                                <%-- 화면구분명 --%>
                                <th><s:message code="captionMsg.captionImgNm" /></th>
                                <td>{{version.captionImgNm}}</td>
                            </tr>
                            <tr>
                                <%-- 첨부파일 --%>
                                <th><s:message code="captionMsg.file"/></th>
                                <td id="fileIn">
                                    {{version.fileOrgNm}}
                                </td>
                                <th id="fileOrgH" style="display:none;"><s:message code="captionMsg.fileOrgNm"/></th>
                                <td id="fileOrgD" style="display:none;">
                                    <input type="text" class="sb-input w100" id="fileOrgNm" ng-model="version.fileOrgNm" readonly/>
                                </td>
                            </tr>
                            <tr>
                                <%-- 파일사이즈 --%>
                                <th><s:message code="captionMsg.fileSize" /></th>
                                <td colspan="3">
                                    {{version.fileSize}}
                                </td>
                            </tr>
                            <tr>
                                <%-- 파일설명 --%>
                                <th><s:message code="captionMsg.fileDesc" /></th>
                                <td colspan="3">
                                    {{version.fileDesc}}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="btnSet2">
                        <%-- 수정 --%>
                        <span id="btnMod"><a href="#" class="btn_blue pd20" ng-click="modify()"><s:message code="cmm.edit" /></a></span>
                        <%-- 닫기 --%>
                        <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close"/></a></span>
                    </div>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/captionMsg/captionMsgGrpDtl.js?ver=20231120.01" charset="utf-8"></script>

<script type="text/javascript">
    var orgnCd = "${orgnCd}";
</script>
