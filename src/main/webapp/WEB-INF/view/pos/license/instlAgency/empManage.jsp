<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="empManageView" class="subCon" ng-controller="empManageCtrl"  style="display:none;">
    <%--============================================= 탭 =============================================--%>
    <ul class="subTab">
        <%-- 설치업체관리 업체정보 --%>
        <li><a id="agencyInfoTab" href="#" ng-click="changeTabInstlAgency('agency');"><s:message code="instlAgency.agencyInfo" /></a></li>
        <%-- 설치업체관리 사원관리 --%>
        <li><a id="empManageTab"  href="#" class="on"><s:message code="instlAgency.empManage" /></a></li>
        <%-- 설치업체관리 인증관리 --%>
        <li><a id="authManageTab"  href="#" ng-click="changeTabInstlAgency('auth');"><s:message code="instlAgency.authManage" /></a></li>
    </ul>
    <div style="padding:10px; height:50px;">
        <%-- 사원등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnNewRegEmp" onclick="callEmpManageRegist();">
            <s:message code="instlAgency.empReg"/>
            <input type="hidden" id="agencyCd"/>
        </button>
    </div>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <%-- 그리드 --%>
            <div id="empManageGrid"></div>
        </div>
    </div>
</div>

<script>

    <%-- 재직여부 코드 --%>
    var serviceFg = ${ccu.getCommCodeExcpAll("007")};
    var serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');

    <%-- header --%>
    var empManageHeader =
        [
            {binding:"agencyCd", header:"<s:message code='instlAgency.agencyCd' />", width:100, align:"center"},
            {binding:"empNo", header:"<s:message code='instlAgency.empNo' />", width:100, align:"center"},
            {binding:"empNm", header:"<s:message code='instlAgency.empNm' />", width:"*", align:"center"},
            {binding:"userId", header:"<s:message code='instlAgency.userId' />", width:"*", align:"center"},
            {binding:"serviceFg", header:"<s:message code='instlAgency.serviceFg' />", width:100, align:"center", dataMap : serviceFgDataMap},
            {binding:"mpNo", header:"<s:message code='instlAgency.mpNo' />", align:"center", width:"*"}
        ];

    <%-- 그리드 생성 --%>
    var empManageGrid = wgrid.genGrid("#empManageGrid", empManageHeader);
    empManageGrid.isReadOnly = true;

    <%-- 그리드 포맷 --%>
    empManageGrid.formatItem.addHandler(function(s, e) {
        if (e.panel === s.cells) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if( col.binding === "empNo" ) {
                wijmo.addClass(e.cell, 'wijLink');
            }
        }
    });

    <%-- 그리드 선택 이벤트 --%>
    empManageGrid.addEventListener(empManageGrid.hostElement, 'click', function(e) {
        var ht = empManageGrid.hitTest(e);
        if ( ht.cellType === wijmo.grid.CellType.Cell ) {
            var col = ht.panel.columns[ht.col];
            if( col.binding === "empNo" ) {
                var selectedRow  = empManageGrid.rows[ht.row].dataItem;

                 // 사원 상세화면 호출
                 getEmpManageDtl(selectedRow.agencyCd, selectedRow.empNo);
            }
        }
    });

    <%-- 포스기능 인증 목록 조회 --%>
    function getEmpManageList(data) {

        $("#agencyCd").val(data);

        var param = {};
        param.agencyCd = data;

        $.postJSON("/pos/license/instlAgency/getAgencyEmp.sb", param,
            function(result) {

                var list = result.data.list;
                empManageGrid.itemsSource = new wijmo.collections.CollectionView(list);
                empManageGrid.collectionView.trackChanges = true;
                selectedRow = "";
            },
            function (result) {
                s_alert.pop(result.message);
                return;
            }
        );
    }

    function callEmpManageRegist(){
        empManageRegist('REG', $("#agencyCd").val(), '');
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlAgency/empManage.js?ver=20191023.12" charset="utf-8"></script>



