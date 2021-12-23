<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="instlAgencyView" class="subCon">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr <%--<c:if test="${orgnFg == 'AGENCY'}">style="display: none;"</c:if>--%>>
            <%-- 업체코드 --%>
            <th><s:message code="instlAgency.agencyCd" /></th>
            <td><input type="text" id="srchAgencyCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/></td>
            <%-- 업체명 --%>
            <th><s:message code="instlAgency.agencyNm" /></th>
            <td><input type="text" id="srchAgencyNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/></td>
        </tr>
        <tr>
            <%-- 사업자번호 --%>
            <th><s:message code="instlAgency.bizNo" /></th>
            <td><input type="text" id="srchBizNo" class="sb-input w100" maxlength="15" onkeyup="fnNxBtnSearch();"/></td>
            <th></th>
            <td></td>
            <%-- 업체구분
            <th><s:message code="instlAgency.agencyFg" /></th>
            <td><input type="text" id="srchAgencyFg" class="sb-input w100"/></td>--%>
        </tr>
        <%--<tr>
            사용여부
            <th><s:message code="instlAgency.useYn" /></th>
            <td><input type="text" id="srchUseYn" class="sb-input w100" maxlength="7"/></td>
            <th></th>
            <td></td>
        </tr>--%>
        </tbody>
    </table>

    <div class="wj-TblWrap mt40">

        <!-- Left List -->
        <div class="w25 fl" style="overflow-x: visible">
            <div class="wj-TblWrapBr mr10 pd20" style="height:650px;">
                <div class="sb-select dkbr mb10 oh">
                    <div class="fr">
                    </div>
                </div>
                <div id="agencyGrid" style="height: 550px; overflow-x: hidden;"></div>
            </div>
        </div>

        <!-- Right List -->
        <div class="w75 fr">
            <div class="wj-TblWrapBr ml10 pd20" style="height:650px;">
                <%-- 업체정보--%>
                <c:import url="/WEB-INF/view/pos/license/instlAgency/agencyInfo.jsp">
                    <c:param name="menuCd" value="${menuCd}"/>
                    <c:param name="menuNm" value="${menuNm}"/>
                </c:import>

                <%-- 사원관리--%>
                <c:import url="/WEB-INF/view/pos/license/instlAgency/empManage.jsp">
                    <c:param name="menuCd" value="${menuCd}"/>
                    <c:param name="menuNm" value="${menuNm}"/>
                </c:import>

                <%-- 인증관리--%>
                <c:import url="/WEB-INF/view/pos/license/instlAgency/authManage.jsp">
                    <c:param name="menuCd" value="${menuCd}"/>
                    <c:param name="menuNm" value="${menuNm}"/>
                </c:import>
            </div>
        </div>
        <%-- 저장타입 지정 --%>
        <input type="hidden" id="rowAgencyCd" name="rowAgencyCd">
        <input type="hidden" id="rowPAgencyCd" name="rowPAgencyCd">
    </div>
</div>

<script>

    <%--javascript에서 사용할 설치업체 json 데이터 생성--%>
    var agColList = [];
    <c:forEach var="agCol" items="${agencyList}">
    var agParam = {};
    agParam.agencyCd = "${agCol.agencyCd}";
    agParam.agencyNm = "${agCol.agencyNm}";
    agColList.push(agParam);
    </c:forEach>

    $(document).ready(function(){

        // 업체정보 탭 활성화
        $("#agencyInfoView").show();
        // 사원관리, 인증관리 탭 숨기기(등록 된 업체정보가 있을경우에 보여야 함, 업체 정보 클릭 시 다시 보임)
        $("#empManageTab").hide();
        $("#authManageTab").hide();
        // 신규등록으로 셋팅
        newRegAgency();

        <%-- 설치업체 목록 header --%>
        var agencyData =
            [
                {binding:"agencyCd", header:"<s:message code='instlAgency.agencyCd' />", width:'*', align: 'center', isRequired: true, isReadOnly:true},
                {binding:"agencyNm", header:"<s:message code='instlAgency.agencyNm' />", isReadOnly: true},
                {binding:"pAgencyCd", header:"", isReadOnly: true, visible: false},
                {binding:"agencyGrp", header:"<s:message code='instlAgency.agencyNm' />", isReadOnly: true, visible: false}
            ];

        var agencyGrid = wgrid.genGrid("#agencyGrid", agencyData);
        agencyGrid.isReadOnly = true;
        agencyGrid.allowMerging = "Cells";

        <%-- 그리드 포맷 --%>
        agencyGrid.formatItem.addHandler(function(s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if(col.binding === "agencyNm" && item.agencyCd != null) {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        <%-- 조회 버튼 클릭 --%>
        $("#btnSearch").click(function(){
            search();
        });

        <%-- 설치업체 목록 조회 --%>
        function search(){

            var params = {};

            params.srchAgencyCd = $("#srchAgencyCd").val();
            params.srchAgencyNm = $("#srchAgencyNm").val();
            params.bizNo = $("#srchBizNo").val();

            $.postJSON("/pos/license/instlAgency/getInstlAgency.sb", params,

                function(result) {

                    var list = result.data.list;

                    if(list.length === undefined || list.length === 0) {
                        s_alert.pop(result.message);
                        agencyGrid.itemsSource = new wijmo.collections.CollectionView([]);
                        return;
                    }

                    agencyGrid.itemsSource = new wijmo.collections.CollectionView(list, {
                        groupDescriptions : ['agencyGrp']
                    });

                    agencyGrid.collapseGroupsToLevel(1);

                    <%-- 그리드 선택 이벤트 --%>
                    agencyGrid.addEventListener(agencyGrid.hostElement, 'mousedown', function(e) {
                        var ht = agencyGrid.hitTest(e);
                        if(ht.panel === agencyGrid.cells) {
                            if(!(agencyGrid.rows[ht.row] instanceof wijmo.grid.GroupRow)) {
                                var col = ht.panel.columns[ht.col];
                                var selectedRow = agencyGrid.rows[ht.row].dataItem;

                                if (col.binding === "agencyNm") {
                                    var params      = {};
                                    params.agencyCd = selectedRow.agencyCd;

                                    // 업체 상세 정보 조회
                                    getAgencyInfo(params);

                                    // 사원관리, 인증관리 탭 보이기
                                    $("#empManageTab").show();
                                    $("#authManageTab").show();

                                    // 선택한 업체 코드 값 hidden 으로 갖고 있기(사원관리, 인증관리 호출 시 사용)
                                    $("#rowAgencyCd").val(selectedRow.agencyCd);
                                    $("#rowPAgencyCd").val(selectedRow.pAgencyCd);

                                    // 업체정보 탭 활성화
                                    $("#agencyInfoView").show();
                                    $("#empManageView").hide();
                                    //$("#authManageView").hide();
                                }
                            }
                        }
                    });

                },
                function (result) {
                    s_alert.pop(result.message);
                }
            );
        }
    });

    <%--  탭변경 --%>
    function changeTabInstlAgency(data){

        if(data === "agency"){
            $("#agencyInfoView").show();
            $("#empManageView").hide();
            //$("#authManageView").hide();

        }else if(data === "emp"){
            $("#agencyInfoView").hide();
            $("#empManageView").show();
            //$("#authManageView").hide();

            // 사원관리 조회
            getEmpManageList($("#rowAgencyCd").val(), $("#rowPAgencyCd").val());

        }else if(data === "auth"){
            $("#agencyInfoView").hide();
            $("#empManageView").hide();
            //$("#authManageView").show();
        }
    };

</script>

<%-- 설치업체 사원상세 팝업 --%>
<c:import url="/WEB-INF/view/pos/license/instlAgency/empManageDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 설치업체 사원 등록/수정 팝업 --%>
<c:import url="/WEB-INF/view/pos/license/instlAgency/empManageRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
