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
        <li><a id="agencyInfoTab" href="#" onClick="changeTabInstlAgency('agency');"><s:message code="instlAgency.agencyInfo" /></a></li>
        <%-- 설치업체관리 사원관리 --%>
        <li><a id="empManageTab"  href="#" class="on"><s:message code="instlAgency.empManage" /></a></li>
        <%-- 설치업체관리 인증관리 --%>
        <li><a id="authManageTab"  href="#" onClick="changeTabInstlAgency('auth');"><s:message code="instlAgency.authManage" /></a></li>
    </ul>
    <%--============================================= //탭 =============================================--%>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 재직여부 --%>
            <th><s:message code="instlAgency.serviceFg" /></th>
            <td>
                <select class="sb-select" id="empManage_serviceFg">
                </select>
            </td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <div style="padding:10px; height:50px;">
        <%-- 사원등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnNewRegEmp" onclick="callEmpManageRegist();">
            <s:message code="instlAgency.empReg"/>
            <input type="hidden" id="agencyCd"/>
            <input type="hidden" id="pAgencyCd"/>
        </button>
        <%-- 조회 --%>
        <button class="btn_skyblue ml5 fr" id="btnSearch" onclick="searchEmpManageList();">
            <s:message code="cmm.search" />
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

    // 재직여부 SelectBox 데이터 바인딩
    var ele = document.getElementById('empManage_serviceFg');
    for (var i = 0; i < serviceFg.length; i++) {
        if(i == 0) {
            ele.innerHTML = ele.innerHTML +'<option value="">전체</option>';
        }
        ele.innerHTML = ele.innerHTML +'<option value="' + serviceFg[i]['value'] + '">' + serviceFg[i]['name'] + '</option>';
    }

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
                 getEmpManageDtl(selectedRow.agencyCd, selectedRow.empNo, $("#pAgencyCd").val());
            }
        }
    });

    <%-- 사원목록 조회 --%>
    function getEmpManageList(agCd, pagCd) {

        $("#agencyCd").val(agCd);
        $("#pAgencyCd").val(pagCd);

        var params = {};
        params.agencyCd = agCd;
        params.serviceFg = $("#empManage_serviceFg").val();

        $.postJSON("/pos/license/instlAgency/getAgencyEmp.sb", params,
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

    // 사원등록
    function callEmpManageRegist(){
        empManageRegist('1', $("#agencyCd").val(), '', $("#pAgencyCd").val());  // 신규등록
    }

    // 조회
    function searchEmpManageList(){
        getEmpManageList($("#agencyCd").val(), $("#pAgencyCd").val());  // 조회
    }
</script>



