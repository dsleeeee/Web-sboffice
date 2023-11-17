<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>
<input type="text"
       id="<c:out value="${param.targetId}Nm"/>"
       class="sb-input fl mr5"
       style="cursor:pointer; width:200px;"
       value=<s:message code="cmm.all"/>
       ng-click="<c:out value="${param.targetId}"/>Show()"
       readonly/>

<wj-popup id="wj<c:out value="${param.targetId}"/>Layer" control="wj<c:out value="${param.targetId}"/>Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:630px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            > <s:message code="cmm.store.select"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
            <div class="w100">
                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장코드 --%>
                        <th><s:message code="selectStore.storeCd" /></th>
                        <td>
                            <input type="text" id="srchStoreCd" ng-model="srchStoreCd"/>
                        </td>
                        <%-- 매장명 --%>
                        <th><s:message code="selectStore.storeNm" /></th>
                        <td>
                            <input type="text" id="srchStoreNm" ng-model="srchStoreNm"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 구분(판매가변경제한매장) --%>
                        <th><s:message code="selectStore.gubun"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchPopStoreChgNotCombo"
                                        ng-model="popStoreChgNot"
                                        items-source="_getComboData('popStoreChgNotCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchPopStoreChgNotCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
                <%-- 조회조건 --%>
                <table class="tblType01" id="tblSearch1${param.targetId}" style="display: none;">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장브랜드 --%>
                        <th><s:message code="cmm.moms.storeHqBrand" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchPopStoreHqBrandCdCombo"
                                        ng-model="popStoreHqBrandCd"
                                        items-source="_getComboData('popStoreHqBrandCdCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchPopStoreHqBrandCdCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 그룹 --%>
                        <th><s:message code="cmm.moms.branch" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchPopBranchCdCombo"
                                        ng-model="popBranchCd"
                                        items-source="_getComboData('popBranchCdCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchPopBranchCdCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <tr>
                            <%-- 팀별 --%>
                            <th><s:message code="cmm.moms.momsTeam"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchPopMomsTeamCombo"
                                            ng-model="popMomsTeam"
                                            items-source="_getComboData('popMomsTeamCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchPopMomsTeamCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <%-- AC점포별 --%>
                            <th><s:message code="cmm.moms.momsAcShop"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchPopMomsAcShopCombo"
                                            ng-model="popMomsAcShop"
                                            items-source="_getComboData('popMomsAcShopCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchPopMomsAcShopCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 지역구분 --%>
                            <th><s:message code="cmm.moms.momsAreaFg"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchPopMomsAreaFgCombo"
                                            ng-model="popMomsAreaFg"
                                            items-source="_getComboData('popMomsAreaFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchPopMomsAreaFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <%-- 상권 --%>
                            <th><s:message code="cmm.moms.momsCommercial"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchPopMomsCommercialCombo"
                                            ng-model="popMomsCommercial"
                                            items-source="_getComboData('popMomsCommercialCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchPopMomsCommercialCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 점포유형 --%>
                            <th><s:message code="cmm.moms.momsShopType"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchPopMomsShopTypeCombo"
                                            ng-model="popMomsShopType"
                                            items-source="_getComboData('popMomsShopTypeCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchPopMomsShopTypeCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <%-- 매장관리타입 --%>
                            <th><s:message code="cmm.moms.momsStoreManageType"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchPopMomsStoreManageTypeCombo"
                                            ng-model="popMomsStoreManageType"
                                            items-source="_getComboData('popMomsStoreManageTypeCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchPopMomsStoreManageTypeCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                        <tr>
                            <%-- 매장그룹 --%>
                            <th><s:message code="cmm.moms.momsStoreFg01"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchPopMomsStoreFg01Combo"
                                            ng-model="popMomsStoreFg01"
                                            items-source="_getComboData('popMomsStoreFg01Combo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchPopMomsStoreFg01Combo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장명 --%>
                        <th><s:message code="selectStore.storeNm" /></th>
                        <td>
                            <input type="text" id="filterStoreNm" ng-model="filterStoreNm"/>
                        </td>
                        <td colspan="2">
                            <div class="tr">
                                <%-- 필터 --%>
                                <button class="btn_skyblue fl" ng-click="searchFilter()"><s:message code="selectStore.filter" /></button>
                            </div>
                        </td>
                    </tr>
                    <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                        <tr>
                            <%-- 업로드매장 --%>
                            <th><s:message code="selectStore.uploadStore" /></th>
                            <td>
                                <input type="text" id="popUploadStore" ng-model="popUploadStore" readonly/>
                            </td>
                            <td colspan="2">
                                <jsp:include page="/WEB-INF/view/common/popup/selectUploadStore.jsp" flush="true">
                                    <jsp:param name="targetId" value="${param.targetId}SelectUpload"/>
                                </jsp:include>
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>

                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <td colspan="4">
                            <%-- 버튼영역 --%>
                            <div class="tr">
                                <div>
                                    <%-- 조회 --%>
                                    <button class="btn_skyblue ml5 fr" ng-click="searchStore()"><s:message code="cmm.search" /></button>
                                </div>
                                <div id="divStoreSelected${param.targetId}" style="display: none;">
                                    <%-- 선택 --%>
                                    <button class="btn_skyblue ml5 fr" ng-click="storeSelected()"><s:message code="cmm.chk"/></button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%--위즈모 테이블--%>
                <div class="theGrid mt10" style="height: 400px;">
                    <wj-flex-grid
                            id="wjGridStore${param.targetId}"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectStore.storeCd"/>" binding="storeCd" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectStore.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectStore.storeChgNot"/>" binding="storeChgNot" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>


<script type="text/javascript">
    /**
     * get application
     */
    var app = agrid.getApp();

    // 매장브랜드 콤보박스 항목 저장시 쓰려고
    var momsHqBrandCdComboList;

    // 구분(판매가변경제한매장)
    var popStoreChgNotComboData = [
        {"name": "전체", "value": ""},
        {"name": "판매가변경제한매장", "value": "1"},
        {"name": "판매가변경제한매장외", "value": "2"}
    ];

    /** 매장선택 controller */
    app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {

        var targetId = '${param.targetId}';
        var targetCornerId = '${param.targetCornerId}';
        var targetTableId = '${param.targetTableId}';
        var targetPosId = '${param.targetPosId}';
        var targetTypeFg = '${param.targetTypeFg}'; // 매장선택 (S:싱글, M:멀티)

        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, false));

        // 회사 구분 (COMMON:공통, MOMS:맘스터치)
        var companyFg = "COMMON";

        // 조회조건 콤보박스 데이터 Set
        $scope._setComboData("popStoreChgNotCombo", popStoreChgNotComboData); // 구분(판매가변경제한매장)

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridStore${param.targetId}");
            var columns = grid.columns;

            // 매장선택 (S:싱글, M:멀티)
            if(targetTypeFg == "M") {
                // 선택 버튼
                $("#divStoreSelected${param.targetId}").css("display", "");

                // 그리드 체크박스
                columns[0].visible = true;

            } else if(targetTypeFg == "S") {
                // 선택 버튼
                $("#divStoreSelected${param.targetId}").css("display", "none");

                // 그리드 체크박스
                columns[0].visible = false;

                // 그리드 링크 효과
                s.formatItem.addHandler(function (s, e) {
                    if (e.panel == s.cells) {
                        var col = s.columns[e.col];
                        if (col.binding === "storeCd") {
                            var item = s.rows[e.row].dataItem;
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                        }
                    }
                });

                // 그리드 클릭 이벤트
                s.addEventListener(s.hostElement, 'mousedown', function (e) {
                    var ht = s.hitTest(e);
                    if (ht.cellType === wijmo.grid.CellType.Cell) {
                        var col         = ht.panel.columns[ht.col];
                        var selectedRow = s.rows[ht.row].dataItem;
                        if (col.binding === "storeCd") {
                            $("#" + targetId + "Cd").val(selectedRow.storeCd);
                            $("#" + targetId + "Nm").val("[" + selectedRow.storeCd + "] " + selectedRow.storeNm);
                            eval('$scope.wj' + targetId + 'Layer.hide(true)');
                        }
                    }
                });
            }

            // 회사 구분 (COMMON:공통, MOMS:맘스터치)
            var params = {};
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectStoreCompanyFg.sb', params, function (response) {
                var companyList = response.data.data.result;
                $scope.companyList = companyList;

                if(companyList.companyFg < 1) {
                    companyFg = "COMMON";
                } else {
                    companyFg = companyList.companyFg;
                }

                // 회사 구분 (COMMON:공통, MOMS:맘스터치)
                if(companyFg == "MOMS") {
                    // 검색조건
                    $("#tblSearch1${param.targetId}").css("display", "");

                } else if(targetTypeFg == "COMMON") {
                    // 검색조건
                    $("#tblSearch1${param.targetId}").css("display", "none");
                }
            });

            // 매장브랜드
            var params = {};
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectBrandMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popStoreHqBrandCdCombo", list);
                    // 매장브랜드 콤보박스 항목 저장시 쓰려고
                    momsHqBrandCdComboList = list;
                }
            });

            // 팀별
            var params = {};
            params.nmcodeGrpCd = "151";
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectHqNmcodeMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popMomsTeamCombo", list);
                    // 팀별
                    if(list.length <= 1) {
                        $("#srchPopMomsTeamCombo").css('background-color', '#F0F0F0');
                        $("#srchPopMomsTeamCombo").attr("disabled", true);
                    } else {
                        $("#srchPopMomsTeamCombo").css('background-color', '#FFFFFF');
                        $("#srchPopMomsTeamCombo").attr("disabled", false);
                    }
                }
            });

            // AC점포별
            var params = {};
            params.nmcodeGrpCd = "152";
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectHqNmcodeMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popMomsAcShopCombo", list);
                    // AC점포별
                    if(list.length <= 1) {
                        $("#srchPopMomsAcShopCombo").css('background-color', '#F0F0F0');
                        $("#srchPopMomsAcShopCombo").attr("disabled", true);
                    } else {
                        $("#srchPopMomsAcShopCombo").css('background-color', '#FFFFFF');
                        $("#srchPopMomsAcShopCombo").attr("disabled", false);
                    }
                }
            });

            // 지역구분
            var params = {};
            params.nmcodeGrpCd = "153";
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectHqNmcodeMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popMomsAreaFgCombo", list);
                    // 지역구분
                    if(list.length <= 1) {
                        $("#srchPopMomsAreaFgCombo").css('background-color', '#F0F0F0');
                        $("#srchPopMomsAreaFgCombo").attr("disabled", true);
                    } else {
                        $("#srchPopMomsAreaFgCombo").css('background-color', '#FFFFFF');
                        $("#srchPopMomsAreaFgCombo").attr("disabled", false);
                    }
                }
            });

            // 상권
            var params = {};
            params.nmcodeGrpCd = "154";
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectHqNmcodeMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popMomsCommercialCombo", list);
                    // 상권
                    if(list.length <= 1) {
                        $("#srchPopMomsCommercialCombo").css('background-color', '#F0F0F0');
                        $("#srchPopMomsCommercialCombo").attr("disabled", true);
                    } else {
                        $("#srchPopMomsCommercialCombo").css('background-color', '#FFFFFF');
                        $("#srchPopMomsCommercialCombo").attr("disabled", false);
                    }
                }
            });

            // 점포유형
            var params = {};
            params.nmcodeGrpCd = "155";
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectHqNmcodeMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popMomsShopTypeCombo", list);
                    // 점포유형
                    if(list.length <= 1) {
                        $("#srchPopMomsShopTypeCombo").css('background-color', '#F0F0F0');
                        $("#srchPopMomsShopTypeCombo").attr("disabled", true);
                    } else {
                        $("#srchPopMomsShopTypeCombo").css('background-color', '#FFFFFF');
                        $("#srchPopMomsShopTypeCombo").attr("disabled", false);
                    }
                }
            });

            // 매장관리타입
            var params = {};
            params.nmcodeGrpCd = "156";
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectHqNmcodeMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popMomsStoreManageTypeCombo", list);
                    // 매장관리타입
                    if(list.length <= 1) {
                        $("#srchPopMomsStoreManageTypeCombo").css('background-color', '#F0F0F0');
                        $("#srchPopMomsStoreManageTypeCombo").attr("disabled", true);
                    } else {
                        $("#srchPopMomsStoreManageTypeCombo").css('background-color', '#FFFFFF');
                        $("#srchPopMomsStoreManageTypeCombo").attr("disabled", false);
                    }
                }
            });

            // 그룹
            var params = {};
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectBranchMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popBranchCdCombo", list);
                    // 그룹
                    if(list.length <= 1) {
                        $("#srchPopBranchCdCombo").css('background-color', '#F0F0F0');
                        $("#srchPopBranchCdCombo").attr("disabled", true);
                    } else {
                        $("#srchPopBranchCdCombo").css('background-color', '#FFFFFF');
                        $("#srchPopBranchCdCombo").attr("disabled", false);
                    }
                }
            });

            // 매장그룹
            var params = {};
            params.nmcodeGrpCd = "167";
            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectHqNmcodeMomsList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var list = response.data.data.list;
                    $scope._setComboData("popMomsStoreFg01Combo", list);
                    // 매장그룹
                    if(list.length <= 1) {
                        $("#srchPopMomsStoreFg01Combo").css('background-color', '#F0F0F0');
                        $("#srchPopMomsStoreFg01Combo").attr("disabled", true);
                    } else {
                        $("#srchPopMomsStoreFg01Combo").css('background-color', '#FFFFFF');
                        $("#srchPopMomsStoreFg01Combo").attr("disabled", false);
                    }
                }
            });
        };

        $scope.searchFg = "N";

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on(targetId + 'Ctrl', function (event, paramObj) {
            // 매장선택 팝업 오픈
            eval('$scope.wj' + targetId + 'Layer.show(true)');

            // 팝업 닫힐시 이벤트
            eval('$scope.wj' + targetId + 'Layer').hidden.addHandler(function () {
                if ('${param.closeFunc}' !== '') {
                    if('${param.closeFunc}'.indexOf(',')>-1){
                        var closeFunc = ('${param.closeFunc}').split(",");
                        for(var i=0; i<closeFunc.length; i++){
                            eval('$scope.'+closeFunc[i]+'()');
                        }
                    }else{
                        eval('$scope.${param.closeFunc}()');
                    }
                }
                $scope.srchPopStoreHqBrandCdCombo.selectedIndex = 0;
                $scope.srchPopMomsTeamCombo.selectedIndex = 0;
                $scope.srchPopMomsAcShopCombo.selectedIndex = 0;
                $scope.srchPopMomsAreaFgCombo.selectedIndex = 0;
                $scope.srchPopMomsCommercialCombo.selectedIndex = 0;
                $scope.srchPopMomsShopTypeCombo.selectedIndex = 0;
                $scope.srchPopMomsStoreManageTypeCombo.selectedIndex = 0;
                $scope.srchPopBranchCdCombo.selectedIndex = 0;
                $scope.srchPopStoreChgNotCombo.selectedIndex = 0;
                $scope.srchPopMomsStoreFg01Combo.selectedIndex = 0;
            });

            // 업로드매장 텍스트박스 조회
            $scope.selectUploadStoreText();

            if ($scope.searchFg == "N") {
                $scope.searchStore();
            }
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchFilter = function (){
            var grid = wijmo.Control.getControl("#wjGridStore" + targetId);

            if(grid.rows.length > 0){

                for (var i = 0; i < grid.rows.length; i++) {
                    grid.rows[i].visible = true;
                }
                if($scope.filterStoreNm !== undefined && $scope.filterStoreNm !== null && $scope.filterStoreNm !== ""){
                    for (var i = 0; i < grid.rows.length; i++) {
                        if (grid.rows[i].dataItem.storeNm.indexOf($scope.filterStoreNm) === -1) {
                            grid.rows[i].visible = false;
                        }
                    }
                }
                grid.refresh();
            }
        };

        $scope.searchStore = function () {
            // 파라미터
            var params = {};
            params.storeCd = $scope.srchStoreCd;
            params.storeNm = $scope.srchStoreNm;
            params.storeHqBrandCd = $scope.popStoreHqBrandCd;
            params.momsTeam = $scope.popMomsTeam;
            params.momsAcShop = $scope.popMomsAcShop;
            params.momsAreaFg = $scope.popMomsAreaFg;
            params.momsCommercial = $scope.popMomsCommercial;
            params.momsShopType = $scope.popMomsShopType;
            params.momsStoreManageType = $scope.popMomsStoreManageType;
            params.branchCd = $scope.popBranchCd;
            // '전체' 일때
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var momsHqBrandCd = "";
                for(var i=0; i < momsHqBrandCdComboList.length; i++){
                    if(momsHqBrandCdComboList[i].value !== null) {
                        momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            }
            params.storeChgNot = $scope.popStoreChgNot;
            params.momsStoreFg01 = $scope.popMomsStoreFg01;

            $scope._inquirySub("/common/popup/selectStore/getSelectStoreList.sb", params, function () {
                $scope.searchFg = "Y";
            });
        };

        $scope.storeSelected = function () {
            var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
            // var flex = $scope.storeGridM;
            var arrStoreCd = [];
            var strStoreCd = "";
            var strStoreNm = "";
            var cnt        = 0;

            for (var i = 0; i < flex.length; i++) {
                if (flex[i].gChk) {
                    if (cnt == 0) {
                        strStoreCd = flex[i].storeCd;
                        // strStoreNm = "["+flex[i].storeCd+"] "+flex[i].storeNm;
                        strStoreNm = flex[i].storeNm;
                    }
                    arrStoreCd.push(flex[i].storeCd);
                    cnt++;
                }
            }

            $("#" + targetId + "Cd").val(arrStoreCd.join());
            $("#" + targetPosId + "Cd").val("");
            $("#" + targetPosId + "Name").val(messages["cmm.all"]);

            $("#" + targetCornerId + "Cd").val("");
            $("#" + targetCornerId + "Name").val(messages["cmm.all"]);

            $("#" + targetTableId + "Cd").val("");
            $("#" + targetTableId + "Name").val(messages["cmm.all"]);

            if (cnt == 0) {
                $("#" + targetId + "Nm").val(messages["cmm.all"]);
                $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
                $("#" + targetId +"StoreNum").val("");
            }
            else if (cnt == 1) {
                $("#" + targetId + "Nm").val("[" + strStoreCd + "] " + strStoreNm);
                $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
                $("#" + targetId +"StoreNum").val(" 영업매장 : "+cnt+" 개");
            }
            else if (cnt > 1) {
                $("#" + targetId + "Nm").val(strStoreNm + " "+messages["cmm.except"]+" " + (cnt - 1) + messages["cmm.cntStore"]);
                $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
                $("#" + targetId +"StoreNum").val(" 영업매장 : "+cnt+" 개");
            }
            eval('$scope.wj' + targetId + 'Layer.hide(true)');
        };

        // 업로드매장 팝업
        $scope.selectUploadStoreShow = function () {
            $scope._broadcast(targetId + 'SelectUploadCtrl');
        };

        // 업로드매장 텍스트박스 조회
        $scope.selectUploadStoreText = function () {
            var params = {};

            $scope._postJSONQuery.withOutPopUp('/common/popup/selectStore/getSelectUploadStoreText.sb', params, function (response) {
                var textList = response.data.data.result;
                $scope.textList = textList;

                if(textList.storeCnt < 1) {
                    $scope.popUploadStore = "업로드된 매장 없음";
                } else {
                    $scope.popUploadStore = "매장 " + textList.storeCnt + "건";
                }
            });
        };

    }]);
</script>