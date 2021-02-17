<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjDclzDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:750px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="dclzDetailCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dclzManage.dtl.nm" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div>
                <table class="tblType01">
                    <colgroup>
                        <col class="w25" />
                        <col class="w75" />
                    </colgroup>
                    <tbody>
                    <%-- 영업일자 --%>
                    <tr>
                        <th><s:message code="dclzManage.sale.date" /></th>
                        <td>
                            <label id="lblSaleDate"></label>
                        </td>
                    </tr>
                    <%-- 사원 --%>
                    <tr>
                        <th><s:message code="dclzManage.empNm" /></th>
                        <td>
                            <label id="lblEmpNm"></label>
                        </td>
                    </tr>
                    <%-- 출근일시 --%>
                    <tr>
                        <th><s:message code="dclzManage.empin" /></th>
                        <td>
                            <label id="lblEmpInDt"></label>
                        </td>
                    </tr>
                    <%-- 퇴근일시 --%>
                    <tr>
                        <th><s:message code="dclzManage.empout" /></th>
                        <td>
                            <label id="lblEmpOutDt"></label>
                        </td>
                    </tr>
                    <%-- 비고 --%>
                    <tr>
                        <th><s:message code="dclzManage.remark" /></th>
                        <td>
                            <label id="lblRemark"></label>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 버튼 영역 --%>
            <div class="btnSet2">
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <span><a href="#" class="btn_blue" id="btnMod" ng-click="modify()"><s:message code="cmm.edit" /></a></span>
                </c:if>
                <span><a href="#" class="btn_blue" id="btnClose" ng-click="close()"><s:message code="cmm.close" /></a></span>
            </div>
            <%-- hidden value 영역 --%>
            <input type="hidden" id="hdStoreCd" />
            <input type="hidden" id="hdEmpNo" />
            <input type="hidden" id="hdEmpInDate" />
            <input type="hidden" id="hdInFg" />
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/dclz/dclzmanage/dclzDetail.js?ver=20210216.04" charset="utf-8"></script>