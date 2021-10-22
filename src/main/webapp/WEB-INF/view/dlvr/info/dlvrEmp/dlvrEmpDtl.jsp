<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup control="wjDlvrEmpDtlPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="dlvrEmpDtlCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dlvrEmp.dlvrEmpInfo"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 배달사원번호 --%>
                    <th><s:message code="dlvrEmp.empNo"/></th>
                    <td>
                        {{dlvrEmpInfo.dlvrEmpNo}}
                    </td>
                    <%-- 배달사원명 --%>
                    <th><s:message code="dlvrEmp.empNm"/></th>
                    <td>
                        {{dlvrEmpInfo.dlvrEmpNm}}
                    </td>
                </tr>
                <tr>
                    <%-- 휴대폰번호 --%>
                    <th><s:message code="dlvrEmp.hpNo"/></th>
                    <td>
                        {{dlvrEmpInfo.hpNo}}
                    </td>
                    <%-- SMS수신여부 --%>
                    <th><s:message code="dlvrEmp.smsRecvYn"/></th>
                    <td>
                        {{dlvrEmpInfo.smsRecvYn}}
                    </td>
                </tr>
                <tr>
                    <%-- 사용여부 --%>
                    <th><s:message code="dlvrEmp.useYn"/></th>
                    <td>
                        {{dlvrEmpInfo.useYn}}
                    </td>
                    <th></th>
                    <td></td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th><s:message code="dlvrEmp.remark"/></th>
                    <td colspan="3">
                        {{dlvrEmpInfo.remark}}
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 버튼 영역 --%>
            <div class="wj-dialog-footer">
                <%-- 저장 --%>
                <button class="btn btn_blue" ng-click="modDtl()"><s:message code="cmm.edit"/></button>
                <%-- 닫기 --%>
                <button class="btn btn_blue" ng-click="closeDtl()"><s:message code="cmm.close"/></button>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/dlvrEmp/dlvrEmpDtl.js?ver=20211020.02" charset="utf-8"></script>