<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>
<input type="text"
       id="<c:out value="${param.targetId}Nm"/>"
       class="sb-input fl"
       style="cursor:pointer; width:200px;"
       value=<c:choose><c:when test="${param.targetTypeFg == 'M'}"><s:message code="cmm.all"/></c:when><c:otherwise><s:message code="cmm.select"/></c:otherwise></c:choose>
               ng-click="_pageView('<c:out value="${param.targetId}"/>Ctrl', 1)"
readonly/>
<input type="hidden" id="<c:out value="${param.targetId}Check"/>"/>
<button type="button" class="btn_skyblue fl" id="<c:out value="${param.targetId}"/>btnCancelStoreCd" style="margin-left: 5px;" onclick="delStore('<c:out value="${param.targetId}"/>', '<c:out value="${param.targetTypeFg}"/>')"><s:message code="cmm.selectCancel"/></button>

<wj-popup id="wj<c:out value="${param.targetId}"/>Layer" control="wj<c:out value="${param.targetId}"/>Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:630px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="cmm.store.select"/>
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
                            <%-- 매장상태구분 --%>
                            <th>
                                <s:message code="selectStore.sysStatFg" />
                            </th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchPopSysStatFgCombo"
                                            ng-model="popSysStatFg"
                                            items-source="_getComboData('popSysStatFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchPopSysStatFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
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
                        </tr>
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
                        <wj-flex-grid-column header="<s:message code="selectStore.sysStatFg2"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
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

    <%-- 매장상태구분 --%>
    var popSysStatFgComboData = ${ccu.getCommCode("005")};

    // 구분(판매가변경제한매장)
    var popStoreChgNotComboData = [
        {"name": "전체", "value": ""},
        {"name": "판매가변경제한매장", "value": "1"},
        {"name": "판매가변경제한매장외", "value": "2"}
    ];

    // 매장선택 초기화
    function delStore(targetId, targetTypeFg){
        $("#" + targetId+ "Cd").val("");

        if(targetTypeFg == "M") {
            $("#" + targetId + "Nm").val(messages["cmm.all"]);
            // 선택취소 클릭 시 값 저장
            $("#" + targetId + "Check").val('Cancel');
        }else{
            $("#" + targetId + "Nm").val(messages["cmm.select"]);
        }
    }

    /** 매장선택 controller */
    app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {

        var targetId = '${param.targetId}';
        var targetVendrCd = "${param.targetVendrCd}";
        <%--var targetCornerId = '${param.targetCornerId}';--%>
        <%--var targetTableId = '${param.targetTableId}';--%>
        <%--var targetPosId = '${param.targetPosId}';--%>
        var targetTypeFg = '${param.targetTypeFg}'; // 매장선택 (S:싱글, M:멀티)

        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, false));

        // 회사 구분 (COMMON:공통, MOMS:맘스터치)
        var companyFg = "COMMON";

        // 조회조건 콤보박스 데이터 Set
        $scope._setComboData("popSysStatFgCombo", popSysStatFgComboData); // 매장상태구분
        $scope._setComboData("popStoreChgNotCombo", popStoreChgNotComboData); // 구분(판매가변경제한매장)

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // 그리드 DataMap 설정
            $scope.sysStatFgDataMap = new wijmo.grid.DataMap(popSysStatFgComboData, 'value', 'name'); // 매장상태구분

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
                            if(targetId === 'kioskKeyMapSelectStore'){
                                var scope = agrid.getScope('kioskKeyMapRegistCtrl');
                                scope.kioskKeyMapSelectStore(selectedRow.storeCd);
                            }
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
                    $("#tblSearch2${param.targetId}").css("display", "none");
                } else if(companyFg == "MRPIZZA") {
                    $("#tblSearch2${param.targetId}").css("display", "");
                    $("#tblSearch1${param.targetId}").css("display", "none");
                }else if(companyFg == "COMMON") {
                    // 검색조건
                    $("#tblSearch1${param.targetId}").css("display", "none");
                    $("#tblSearch2${param.targetId}").css("display", "none");
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
                $scope.srchPopSysStatFgCombo.selectedIndex = 0;
                $scope.srchPopStoreChgNotCombo.selectedIndex = 0;
            });

            // 업로드매장 텍스트박스 조회
            $scope.selectUploadStoreText();

            $scope.searchStore();

            // 화면에서 선택취소 클릭 후 진입 시
            if($("#" + targetId + "Check").val() === 'Cancel'){
                var grid = wijmo.Control.getControl("#wjGridStore" + targetId);

                if(grid.rows.length > 0){

                    for (var i = 0; i < grid.rows.length; i++) {
                        grid.rows[i].dataItem.gChk = false;
                    }
                    grid.refresh();
                }
                $("#" + targetId + "Check").val('');
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
            params.storeChgNot = $scope.popStoreChgNot;
            params.sysStatFg = $scope.popSysStatFg;

            var vendrCd = $("#"+targetVendrCd).val();

            if(vendrCd !== null && vendrCd !== undefined && vendrCd != ""){
                params.vendrCd = vendrCd;
            }

            $scope._inquirySub("/kookmin/acquire/acquireSlipRegist/acquireSelectStore/getAcquireSelectStoreList.sb", params, function () {
                $scope.searchFg = "Y";
            });
        };

        $scope.storeSelected = function () {
            var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
            // var flex = $scope.storeGridM;
            var arrStoreCd = [];
            var arrStoreCdNm = [];
            var strStoreCd = "";
            var strStoreNm = "";
            var cnt        = 0;

            for (var i = 0; i < flex.length; i++) {
                if (flex[i].gChk) {
                    if (cnt == 0) {
                        strStoreCd = flex[i].storeCd;
                        strStoreNm = flex[i].storeNm;
                    }
                    arrStoreCd.push(flex[i].storeCd);
                    arrStoreCdNm.push("["+flex[i].storeCd+"] "+flex[i].storeNm);
                    cnt++;
                }
            }

            $("#" + targetId + "Cd").val(arrStoreCd.join());
            $("#" + targetId + "CdNm").val(arrStoreCdNm.join());
            // $("#" + targetCornerId + "Cd").val("");
            // $("#" + targetCornerId + "Name").val(messages["cmm.all"]);
            // $("#" + targetTableId + "Cd").val("");
            // $("#" + targetTableId + "Name").val(messages["cmm.all"]);
            // $("#" + targetPosId + "Cd").val("");
            // $("#" + targetPosId + "Name").val(messages["cmm.all"]);

            if (cnt == 0) {
                $("#" + targetId + "Nm").val(messages["cmm.all"]);
                // $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                // $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                // $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
                $("#" + targetId +"StoreNum").val("");
            }
            else if (cnt == 1) {
                $("#" + targetId + "Nm").val("[" + strStoreCd + "] " + strStoreNm);
                $("#" + targetId +"StoreNum").val(" 영업매장 : "+cnt+" 개");
                // $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                // $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                // $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
            }
            else if (cnt > 1) {
                $("#" + targetId + "Nm").val(strStoreNm + " "+messages["cmm.except"]+" " + (cnt - 1) + messages["cmm.cntStore"]);
                $("#" + targetId +"StoreNum").val(" 영업매장 : "+cnt+" 개");
                // $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                // $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                // $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
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