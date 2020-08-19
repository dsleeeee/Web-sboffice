<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div id="empCornerEmpView" class="subCon" style="display: none;">

    <div ng-controller="empCornerEmpCtrl">
        <%--사원정보--%>
        <div class="wj-TblWrap mt20 mb20 w40 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px; overflow-y: hidden;" >
                <h3 class="lh30" style="font-size:0.75em; border:1px solid #ccc; background:#e8e8e8; padding:5px 15px; color:#222; min-width:150px; position:relative;">
                    <s:message code="empCorner.emp.emp" />
                </h3>
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="empCorner.emp.empNo"/>" binding="empNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empCorner.emp.empNm"/>" binding="empNm" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empCorner.emp.serviceFg"/>" binding="serviceFg" data-map="serviceFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empCorner.emp.cornrCnt"/>" binding="cornrCnt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%--사원정보--%>
    </div>

    <%--관리코너--%>
    <div class="wj-TblWrap mt20 mb20 w30 fl" ng-controller="empManageCornerCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <h3 class="lh30" style="font-size:0.75em; border:1px solid #ccc; background:#e8e8e8; padding:5px 15px; color:#222; min-width:150px; position:relative;">
                <s:message code="empCorner.emp.manage" />
                <span class="fr" style="font-size:1em;" id="btnDelete">
                    <a href="#" class="btn_grayS" ng-click="del()"><s:message code="cmm.delete" /></a>
                </span>
            </h3>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="func.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empCorner.emp.cornrCd"/>" binding="cornrCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empCorner.emp.cornrNm"/>" binding="cornrNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--상세 조회시 필요--%>
                    <wj-flex-grid-column header="<s:message code="empCorner.emp.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empCorner.emp.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
    <%--관리코너--%>

    <%--미관리코너--%>
    <div class="wj-TblWrap mt20 mb20 w30 fr" ng-controller="empNoManageCornerCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <h3 class="lh30" style="font-size:0.75em; border:1px solid #ccc; background:#e8e8e8; padding:5px 15px; color:#222; min-width:150px; position:relative;">
                <s:message code="empCorner.emp.noManage" />
                <span class="fr" style="font-size:1em;" id="btnAdd">
                    <a href="#" class="btn_grayS" ng-click="add()"><s:message code="cmm.add" /></a>
                </span>
            </h3>
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="func.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empCorner.emp.cornrCd"/>" binding="cornrCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empCorner.emp.cornrNm"/>" binding="cornrNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--상세 조회시 필요--%>
                    <wj-flex-grid-column header="<s:message code="empCorner.emp.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empCorner.emp.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
    <%--미관리코너--%>

</div>

<script type="text/javascript">
    <%-- 재직구분 --%>
    var serviceFgData = ${ccu.getCommCodeExcpAll("007")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/empCorner/emp.js?ver=20200514.02" charset="utf-8"></script>