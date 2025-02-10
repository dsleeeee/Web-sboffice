/****************************************************************
 *
 * 파일명 : terminal.js
 * 설  명 : 터미널 설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.06     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// vandorList 에서 name 만을 dataMap으로 사용. (name과 value 동시 사용시 오류) // todo 추후 수정 필요
var allVanList = new Array();
var vanList = new Array();
var paycoList = new Array();
var mpayList = new Array();
var mcoupnList = new Array();
var paperVoucherList = new Array();
var taxRefundList = new Array();
var sktList = new Array();
var cornerFgList = [];

for (var i in vandorList) {
    if (vandorList[i].vanFg === '01') {
        vanList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '02') {
        paycoList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '03') {
        mpayList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '04') {
        mcoupnList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '06') {
        paperVoucherList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '07') {
        taxRefundList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '08') {
        sktList.push(vandorList[i].name);
    }
}
/**********************************************************************
 *  터미널 Ctrl
 **********************************************************************/
app.controller('terminalCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('terminalCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData("srchClsFg", clsFg);
    $scope._setComboData("srchStatFg", sysStatFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name'); // 용도
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name'); // 상태

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 재료코드
                if (col.binding === "storeCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 재료코드 클릭시 상세정보 조회
                if (col.binding === "storeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = {};
                    params.storeCd = selectedRow.storeCd;
                    params.storeNm = selectedRow.storeNm;
                    params.hqOfficeCd = selectedRow.hqOfficeCd;

                    $("#lblStoreInfo").text("[" + params.storeCd + "] " + params.storeNm);
                    $("#lblStoreCd").text(params.storeCd);
                    $("#lblStoreNm").text(params.storeNm);
                    $("#lblHqOfficeCd").text(params.hqOfficeCd);

                    $scope.search();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.searchTerminalManage = function () {
        var params = {};

        $scope._inquiryMain("/store/manage/terminalManage/terminalManage/getTerminalManageList.sb", params, function () {
            $("#lblStoreInfo").text("");
            $("#lblStoreCd").text("");
            $("#lblStoreNm").text("");
            $("#lblHqOfficeCd").text("");

            $scope.$apply(function () {
                // 그리드 초기화
                var posScope = agrid.getScope('posCtrl');
                posScope._gridDataInit();

                var cornerScope = agrid.getScope('cornerCtrl');
                cornerScope._gridDataInit();
            });

            $("#lblToolTip").text("");
            $("#terminalListArea").hide();

            $("#posListArea").hide();
            // $("#posArea").hide();
            $("#posBtnArea").hide();
            $("#cornerListArea").hide();
            $("#cornerArea").hide();
            $("#cornerBtnArea").hide();

            // 다시 초기화해주고
            $scope.resetCombobox();
        }, false);
    };
    // <-- //검색 호출 -->

    // 터미널 환경변수 [2028]
    $scope.terminalEnvVal;
    $scope.setTerminalEnvVal = function (s) {
        $scope.terminalEnvVal = s;
    };
    $scope.getTerminalEnvVal = function () {
        return $scope.terminalEnvVal;
    };

    // 포스 콥보박스 선택값 (포스 콤보박스 선택시, 해당 포스의 터미널 정보 조회)
    $scope.posFgVal = "01";
    $scope.setPosFgVal = function (s, e) {
        if (isNull(s.selectedValue)) {
            return false;
        }

        $scope.posFgVal = s.selectedValue;

        var posScope = agrid.getScope('posCtrl');
        posScope.getPosSetting();
    };
    $scope.getPosFgVal = function () {
        return $scope.posFgVal;
    };

    // 코너 콤보박스 선택값 (코너 콤보박스 선택시, 해당 코너의 터미널 정보 조회)
    $scope.cornerFgVal = "01";
    $scope.setCornerFgVal = function (s, e) {

        $scope.cornerFgVal = s.selectedValue;

        if (!isNull($("#lblStoreCd").text())) {
            setTimeout(function () {
                var cornerScope = agrid.getScope('cornerCtrl');
                cornerScope.getCornerSetting();
            }, 10);
        }
    };
    $scope.getCornerFgVal = function () {
        return $scope.cornerFgVal;
    };

    // 콤보박스 생성 및 데이터 초기화
    $scope.comboDt = {posCombo: null, cornerCombo: null};

    $scope.posFgArr = [];
    $scope.cornerFgArr = [];

    $scope.resetCombobox = function () {
        $scope.posFgArr = [];
        $scope.cornerFgArr = [];

        // $scope.posFgArr.push({value:"", name:"POS 선택"});
        // $scope.cornerFgArr.push({value:"", name:"코너 선택"});
    };

    $scope._setComboData("terminalFg", terminalFg);

    // 매장 선택 후, 조회 (환경변수, 포스목록, 코너목록)
    $scope.$on("terminalCtrl", function (event, data) {
        $scope.searchTerminalManage();
        event.preventDefault();
    });

    // 매장별 터미널 조회시, 먼저 환경변수 조회 수행
    // 해당 매장의 코너목록과 포스목록도 함께 조회
    $scope.search = function () {

        var params = {};
        params.storeCd = $("#lblStoreCd").text();
        params.envstCd = "2028"; //코너사용설정[2028]

        $scope._postJSONQuery.withOutPopUp(baseUrl + "terminalManage/getTerminalEnv.sb", params, function (result) {

            var terminalEnvVal = result.data.data.envstVal;
            var posList = result.data.data.posList;
            var cornerList = result.data.data.cornerList;

            cornerFgList = cornerList;

            // 터미널 정보 set(터미널 콤보박스 변경에 따라 값 변함)
            $scope.setTerminalEnvVal(terminalEnvVal);

            // 터미널 정보 콤보박스에 set
            $scope.terminalFg = terminalEnvVal;

            // 터미널 정보 갖고있기(불변)
            $("#orgTerminalEnvVal").val(terminalEnvVal);

            // 다시 초기화해주고
            $scope.resetCombobox();

            // 조회한 데이터 붙이기
            for (var i = 0; i <= posList.length; i++) {
                if (posList[i] !== undefined) {
                    $scope.posFgArr.push(posList[i]);
                }
            }
            $scope.comboDt.posCombo.itemsSource = new wijmo.collections.CollectionView($scope.posFgArr);

            if(cornerList.length > 0) {
                var allData = {};
                allData.gChk = false;
                allData.name = "-전체-";
                allData.value = "";

                $scope.cornerFgArr.push(allData);
            }

            for (var j = 0; j < cornerList.length; j++) {
                $scope.cornerFgArr.push(cornerList[j]);
            }

            $scope.comboDt.cornerCombo.itemsSource = new wijmo.collections.CollectionView($scope.cornerFgArr);

            // 코너 보여주기
            if ($scope.terminalFg === "1" || $scope.terminalFg === "2") {
                $scope.showCorner();
            }
            // 포스 보여주기
            else if ($scope.terminalFg === "0" || $scope.terminalFg === "3") {
                $scope.showPos();
            }

            // POS 선택하라는 alert
            //$scope._popMsg(messages["terminalManage.require.select.pos"]);

            // 그리드 초기화
            var posScope = agrid.getScope('posCtrl');
            posScope._gridDataInit();

            var cornerScope = agrid.getScope('cornerCtrl');
            cornerScope._gridDataInit();

            $scope.chkVanFix(params);

        });
    };

    $scope.chkVanFix = function(data){
        var params = data;
        // params.agencyCd = orgnCd;

        // VAN사 변경 허용 체크
        $scope._postJSONQuery.withOutPopUp( "/store/manage/terminalManage/terminalManage/chkVanFix.sb", params, function(response){
            var chkVanFix = response.data.data;
            if(chkVanFix === "Y" || chkVanFix === "N") {
                $("#lblVanFixFg").text(chkVanFix);
            }else{
                $("#lblVanFixFg").text("N");
            }
        });
    }


    // 코너 보여주기
    $scope.showCorner = function () {

        if ($scope.terminalFg === "1") {

            $scope.cornerFgArr2 = $scope.cornerFgArr;
            if($scope.cornerFgArr2.length > 0 && $scope.cornerFgArr2[0].name === "-전체-"){
                $scope.cornerFgArr2.splice(0, 1);
                $scope.comboDt.cornerCombo.itemsSource = new wijmo.collections.CollectionView($scope.cornerFgArr2);
            }
        }else if($scope.terminalFg === "2") {

            $scope.cornerFgArr2 = $scope.cornerFgArr;
            if($scope.cornerFgArr2.length > 0 && $scope.cornerFgArr2[0].name !== "-전체-"){
                var allData = {};
                allData.gChk = false;
                allData.name = "-전체-";
                allData.value = "";

                $scope.cornerFgArr2.unshift(allData);
                $scope.comboDt.cornerCombo.itemsSource = new wijmo.collections.CollectionView($scope.cornerFgArr2);
            }
        }
        $scope.comboDt.cornerCombo.itemsSource.selectedIndex = 0;

        var grid = wijmo.Control.getControl("#wjCornerGrid");
        var columns = grid.columns;
        for(var i=0; i<columns.length-1; i++){
            if(columns[i].binding === "cornrNm"){
                if($scope.terminalFg === "2") {
                    columns[i].visible = true;
                }else{
                    columns[i].visible = false;
                }
            }
        }

        $("#lblToolTip").text("* 코너를 선택하세요.");
        $("#terminalListArea").show();

        $("#cornerListArea").show();
        $("#cornerArea").show();
        $("#cornerBtnArea").show();

        $("#posListArea").hide();
        $("#posArea").hide();
        $("#posBtnArea").hide();
    };

    // 포스 보여주기
    $scope.showPos = function () {
        $("#lblToolTip").text("* POS를 선택하세요.");
        $("#terminalListArea").show();

        $("#cornerListArea").hide();
        $("#cornerArea").hide();
        $("#cornerBtnArea").hide();

        $("#posListArea").show();
        $("#posArea").show();
        $("#posBtnArea").show();
    };

    // 터미널 콤보박스 값 변경 이벤트
    $scope.changeTerminalFg = function (s, e) {

        if ($scope.getTerminalEnvVal() === undefined) {
            return false;
        }

        $scope.setTerminalEnvVal(s.selectedValue);
        var selectedTerminalFgVal = $scope.getTerminalEnvVal();

        // 리스트 초기화
        var posScope = agrid.getScope('posCtrl');
        posScope._gridDataInit();

        var cornerScope = agrid.getScope('cornerCtrl');
        cornerScope._gridDataInit();

        // 사용 터미널 콤보박스 변경시, 포스 or 코너 콤보박스 가장 첫번째 값으로 셋팅하고, 리스트 조회
        $scope.comboDt.cornerCombo.selectedIndex = 0;
        $scope.comboDt.posCombo.selectedIndex = 0;
        $scope.setCornerFgVal($scope.comboDt.cornerCombo);
        $scope.setPosFgVal($scope.comboDt.posCombo);

        // 코너 보여주기
        if (selectedTerminalFgVal === "1" || selectedTerminalFgVal === "2") {
            $scope.showCorner();
        }
        // 포스 보여주기
        else if (selectedTerminalFgVal == "0" || selectedTerminalFgVal == "3") {
            $scope.showPos();
        }
    };

    // 터미널정보복사
    $scope.copyTerminalInfo = function () {

        var params = {};
        params.storeCd = $("#lblStoreCd").text();
        params.envstCd = "2028"; //코너사용설정[2028]

        $scope._postJSONQuery.withOutPopUp(baseUrl + "terminalManage/getTerminalEnv.sb", params, function (result) {

            var posList = result.data.data.posList;

            var scope = agrid.getScope("copyTerminalInfoCtrl");
            scope.copyPosNoCombo.itemsSource = new wijmo.collections.CollectionView(posList);
            scope.pastePosNoCombo.itemsSource = new wijmo.collections.CollectionView(posList);
        });
        $scope.copyTerminalInfoLayer.show();
        $scope._broadcast('copyTerminalInfoCtrl', params);
    };

    // 포스 터미널 그리드 행 추가
    $scope.posAddRow = function () {

        if ($scope.comboDt.posCombo.selectedValue === null || $scope.comboDt.posCombo.selectedValue === undefined || $scope.comboDt.posCombo.selectedValue === '') {
            $scope._popMsg("POS를 선택해주세요.");
            return false;
        }

        var posScope = agrid.getScope('posCtrl');
        posScope.addRow();
    };

    // 포스 저장
    $scope.posSave = function () {
        var posScope = agrid.getScope('posCtrl');
        posScope.save();
    };

    // 포스 삭제
    $scope.posDel = function () {
        $scope._popConfirm(messages["cmm.choo.delete"], function () {
            // 포스 저장
            var posScope = agrid.getScope('posCtrl');
            posScope.savePosDel();
        });
    };

    // 코너 터미널 그리드 행 추가
    $scope.cornerAddRow = function () {

        if ($scope.comboDt.cornerCombo.selectedValue === null || $scope.comboDt.cornerCombo.selectedValue === undefined || $scope.comboDt.cornerCombo.selectedValue === '') {
            if($scope.comboDt.cornerCombo.selectedItem === null) {
                $scope._popMsg("코너를 선택해주세요.");
                return false;
            }
        }

        var cornerScope = agrid.getScope('cornerCtrl');
        cornerScope.addRow();
    };

    // 코너 저장
    $scope.cornerSave = function () {
        var cornerScope = agrid.getScope('cornerCtrl');
        cornerScope.save();
    };

    // 코너 추가 등록 팝업
    $scope.cornerAdd = function () {

        $scope.cornerAddLayer.show(true);

        var params = {};
        params.storeCd = $("#lblStoreCd").text();
        params.storeNm = $("#lblStoreNm").text();
        params.vanFixFg = $("#lblVanFixFg").text();

        $scope._broadcast('cornerAddCtrl', params);

    };

    // 코너 추가 후 코너 SelectBox 재조회
    $scope.setCorner = function () {

        // 코너 SelectBox 데이터 초기화
        $scope.cornerFgArr = [];
        // $scope.cornerFgArr.push({value:"", name:"코너 선택"});

        // 코너 조회
        var params = {};
        params.storeCd = $("#lblStoreCd").text();
        params.envstCd = "2028"; //코너사용설정[2028]

        $scope._postJSONQuery.withOutPopUp(baseUrl + "terminalManage/getTerminalEnv.sb", params, function (result) {

            var cornerList = result.data.data.cornerList;
            cornerFgList = cornerList;

            if(cornerList.length > 0) {
                var allData = {};
                allData.gChk = false;
                allData.name = "-전체-";
                allData.value = "";

                $scope.cornerFgArr.push(allData);
            }

            for (var j = 0; j < cornerList.length; j++) {
                $scope.cornerFgArr.push(cornerList[j]);
            }

            $scope.comboDt.cornerCombo.itemsSource = new wijmo.collections.CollectionView($scope.cornerFgArr);

            // 코너 그리드 초기화
            var cornerScope = agrid.getScope('cornerCtrl');
            cornerScope._gridDataInit();

        });
    }

    // 코너 삭제
    $scope.cornerDel = function () {
        $scope._popConfirm(messages["cmm.choo.delete"], function () {
            // 코너 저장
            var cornerScope = agrid.getScope('cornerCtrl');
            cornerScope.saveCornerDel();
        });
    };

}]);

/**********************************************************************
 *  POS 설정 그리드
 **********************************************************************/
app.controller('posCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.vendorFgDataMap = new wijmo.grid.DataMap(vendorFg, 'value', 'name');
        // $scope.vanCdDataMap = new wijmo.grid.DataMap(vandorList, 'value', 'name');
        $scope.vanCdDataMap = allVanList;
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "vendorFg" || col.binding === "vendorCd" || col.binding === "vendorNm" ) {
                    var item = s.rows[e.row].dataItem;
                    if (col.binding === "vendorFg" || col.binding === "vendorCd") {
                        if (item.status !== "I") {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
                if($("#lblVanFixFg").text() == "Y") {
                    if (col.binding === "vendorNm") {
                        if (item.vendorFg === "01") {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            }
        });

        // 벤더구분, 벤더코드 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "vendorFg" || col.binding === "vendorCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if(dataItem.status === "U"){
                    elements.cancel = true;
                }else if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }else if (col.binding === "vendorNm"){
                if($("#lblVanFixFg").text() == "Y") {
                    var dataItem = s.rows[elements.row].dataItem;
                    if(dataItem.vendorFg === "01") {
                        elements.cancel = true;
                    }
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 변경시 체크박스 체크
                if (col.binding === "vendorFg" || col.binding === "vendorFgNm" ||
                    col.binding === "vendorNm" || col.binding === "vendorCd" || col.binding === "vendorTermnlNo" ||
                    col.binding === "vendorSerNo") {
                    $scope.checked(item);
                    if($("#lblVanFixFg").text() == "Y") {
                        if(col.binding === "vendorFg"){
                            if(item.vendorFg === '01') {
                                item.vendorNm = "KOCES";
                            }
                        }
                    }
                }
            }
            s.collectionView.commitEdit();
        });

    };

    // 벤더구분 변경시 벤더 dataMap 변경
    $scope.changeVendorFg = function (s, e) {
        if (e.panel === s.cells) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if(col.binding === "vendorFg"){
                if (item.status === "I") {
                    item.vendorNm = '';
                }
            } else if (col.binding === "vendorNm") {
                var changeVendorFg = s.rows[e.row].dataItem.vendorFg;
                switch (changeVendorFg) {
                    case '01':
                        col.dataMap = vanList;
                        break;
                    case '02':
                        col.dataMap = paycoList;
                        break;
                    case '03':
                        col.dataMap = mpayList;
                        break;
                    case '04':
                        col.dataMap = mcoupnList;
                        break;
                    case '06':
                        col.dataMap = paperVoucherList;
                        break;
                    case '07':
                        col.dataMap = taxRefundList;
                        break;
                    case '08':
                        col.dataMap = sktList;
                        break;
                }
            }
        }
    };

    // 포스설정 터미널 데이터 조회
    $scope.$on("posCtrl", function (event, data) {
        $scope.getPosSetting();
        event.preventDefault();
    });

    // 포스설정 터미널 데이터 조회
    $scope.getPosSetting = function () {

        if (isNull($("#lblStoreCd").text())) {
            $scope._popMsg(messages["terminalManage.request.select.store"]);
            return false;
        }

        var terminalScope = agrid.getScope('terminalCtrl');

        var params = {};
        params.storeCd = $("#lblStoreCd").text();
        params.posNo = terminalScope.getPosFgVal();

        $scope._inquirySub(baseUrl + "pos/getPosTerminalList.sb", params, function () {
            $scope.flex.collectionView.commitEdit();
        }, false);
    };

    // 행 추가
    $scope.addRow = function () {

        var params = {};
        params.status = "I";
        params.vendorFg = "01";
        params.vendorCd = "001";
        if($("#lblVanFixFg").text() == "Y") {
            params.vendorNm = "KOCES";
        }
        params.gChk = true;

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 포스 정보 저장
    $scope.save = function () {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var terminalScope = agrid.getScope('terminalCtrl');

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if($scope.flex.collectionView.itemsEdited[i].gChk) {
                    $scope.flex.collectionView.itemsEdited[i].status = "U";
                    $scope.flex.collectionView.itemsEdited[i].storeCd = $("#lblStoreCd").text();
                    $scope.flex.collectionView.itemsEdited[i].posNo = terminalScope.getPosFgVal();
                    params.push($scope.flex.collectionView.itemsEdited[i]);
                }
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].gChk) {
                    $scope.flex.collectionView.itemsAdded[i].status = "I";
                    $scope.flex.collectionView.itemsAdded[i].storeCd = $("#lblStoreCd").text();
                    $scope.flex.collectionView.itemsAdded[i].posNo = terminalScope.getPosFgVal();
                    params.push($scope.flex.collectionView.itemsAdded[i]);
                }
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                if($scope.flex.collectionView.itemsRemoved[i].gChk) {
                    $scope.flex.collectionView.itemsRemoved[i].status = "D";
                    $scope.flex.collectionView.itemsRemoved[i].storeCd = $("#lblStoreCd").text();
                    $scope.flex.collectionView.itemsRemoved[i].posNo = terminalScope.getPosFgVal();
                    params.push($scope.flex.collectionView.itemsRemoved[i]);
                }
            }

            // 필수값 체크
            for (var i = 0; i < params.length; i++) {

                if (params[i].status !== "D") {

                    if (params[i].vendorFg == "") {
                        $scope._popMsg(messages["terminalManage.vendorFg"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorCd == "") {
                        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorNm == "" || params[i].vendorNm == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorTermnlNo == "" || params[i].vendorTermnlNo == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.input"]);
                        return false;
                    } else {
                        if (params[i].vendorFg === "01" && params[i].vendorNm === "KCP") {
                            if (params[i].vendorTermnlNo.length != 10) {
                                $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + "는 10자리로 입력하세요.");
                                return false;
                            }
                        } else {
                            if (nvl(params[i].vendorTermnlNo.getByteLengthForOracle(),'') > 20) {
                                $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.exact.data"]);
                                return false;
                            }
                        }
                    }

                    if (params[i].vendorSerNo == "" || params[i].vendorSerNo == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.input"]);
                        return false;
                    } else {
                        if (nvl(params[i].vendorSerNo.getByteLengthForOracle(),'') > 20) {
                            $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.exact.data"]);
                            return false;
                        }
                    }

                    // BBQ는 VAN - KCP 만 저장 가능
                    // 개발 DS011
                    // 운영 DS024 H0360
                    if (params[i].status !== "D") {
                        if ($("#lblHqOfficeCd").text() == "DS011" || $("#lblHqOfficeCd").text() == "DS024" || $("#lblHqOfficeCd").text() == "H0360") {
                            if (params[i].vendorFg == "01") {
                                if (params[i].vendorNm == "KCP") {
                                } else {
                                    // BBQ 매장은 VAN - KCP 선택하여 주십시오.
                                    $scope._popMsg(messages["terminalManage.bbqSave.msg"]);
                                    return false;
                                }
                            }
                        }
                    }
                    // 벤더구분 [01]VAN인 경우 벤더코드는 [008]KOCES 만 선택가능합니다
                    if($("#lblVanFixFg").text() == "Y") {
                        if (params[i].vendorFg === '01') {
                            if (params[i].vendorNm !== 'KOCES') {
                                $scope._popMsg(messages["terminalManage.vendorFg"]+"이 [01]VAN인 경우 " + messages["terminalManage.vendorCd"] + "는 [008] KOCES" + messages["terminalManage.require.select"]);
                                return false;
                            }
                        }
                    }
                }
            }

            // 벤더구분-벤더코드 중복체크
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                    var item2 = $scope.flex.collectionView.items[j];
                    if(i !== j){
                        if(item.vendorNm.toString !== null && item.vendorNm.toString !== '' && item.vendorNm.toString !== undefined
                            && item2.vendorNm.toString !== null && item2.vendorNm.toString !== '' && item2.vendorNm.toString !== undefined) {
                            if (item.vendorFg.toString() + item.vendorNm.toString() === item2.vendorFg.toString() + item2.vendorNm.toString()) {
                                $scope._popMsg("벤더구분-벤더코드 " + getVendorFgNm(item.vendorFg.toString()) + "-" + item.vendorNm.toString() + messages["terminalManage.input.duplicate"]);
                                return false;
                            }
                        }
                    }
                }
            }

            var chkChanged = false;
            // 현재 선택한 터미널 콤보값과 기존 터미널 환경설정값이 일치하는지 확인
            if ($("#terminalFgVal").val() !== $("#orgTerminalEnvVal").val()) {
                chkChanged = true;
            }

            if (chkChanged) {
                var params2 = {};
                params2.storeCd = $("#lblStoreCd").text();
                params2.envstCd = "2028"; //코너사용설정[2028]
                params2.envstVal = $("#terminalFgVal").val();

                // 코너사용설정[2028] 환경설정값 변경
                $scope._save("/store/manage/terminalManage/terminalManage/updateTerminalEnvst.sb", params2, function () {

                    // 리스트 변경사항이 있는지 확인
                    if (params.length <= 0) {
                        // 재조회
                        terminalScope.search();
                    } else {
                        params = JSON.stringify(params);
                        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                        $scope._save(baseUrl + "pos/savePosTerminalInfo.sb", params, function () {
                            // 재조회
                            terminalScope.search();
                        });
                    }
                });
            } else {
                // 리스트 변경사항이 있는지 확인
                if (params.length <= 0) {
                    $scope._popMsg(messages["cmm.not.modify"]);
                    return false;
                } else {
                    params = JSON.stringify(params);
                    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                    $scope._save(baseUrl + "pos/savePosTerminalInfo.sb", params, function () {
                        // 재조회
                        terminalScope.search();
                    });
                }
            }
        });
    };

    // 포스 정보 삭제
    $scope.savePosDel = function () {
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];

            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }

        // 포스 정보 저장
        $scope.save();
    };

    // 정보 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };

}]);

/***************************************************************************
 * 코너 설정 그리드
 ***************************************************************************/
app.controller('cornerCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cornerCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.vendorFgDataMap = new wijmo.grid.DataMap(vendorFg, 'value', 'name');
        // $scope.vanCdDataMap = new wijmo.grid.DataMap(vandorList, 'value', 'name');
        $scope.vanCdDataMap = allVanList;
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');


        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "vendorFg" || col.binding === "vendorCd" || col.binding === "cornrNm") {
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }else if(col.binding === "vendorNm") {
                    if ($("#lblVanFixFg").text() == "Y") {
                        if (item.vendorFg === "01") {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            }
        });

        var terminalScope = agrid.getScope('terminalCtrl');

        // 벤더구분, 벤더코드 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "vendorFg" || col.binding === "vendorCd" || col.binding === "cornrNm") {
                var dataItem = s.rows[elements.row].dataItem;
                if(dataItem.status === "U"){
                    elements.cancel = true;
                }else if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }else if (dataItem.status === "I"){
                    if(col.binding === "cornrNm" && !isNull(terminalScope.getCornerFgVal())){
                        elements.cancel = true;
                    }
                }
            }else if (col.binding === "vendorNm"){
                if($("#lblVanFixFg").text() == "Y") {
                    var dataItem = s.rows[elements.row].dataItem;
                    if(dataItem.vendorFg === "01") {
                        elements.cancel = true;
                    }
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 변경시 체크박스 체크
                if (col.binding === "cornrCd" || col.binding === "vendorFg" || col.binding === "vendorFgNm" ||
                    col.binding === "vendorNm" || col.binding === "vendorCd" || col.binding === "vendorTermnlNo" ||
                    col.binding === "vendorSerNo") {
                    $scope.checked(item);
                    if($("#lblVanFixFg").text() == "Y") {
                        if(col.binding === "vendorFg"){
                            if(item.vendorFg === '01') {
                                item.vendorNm = "KOCES";
                            }
                        }
                    }
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 벤더구분 변경시 벤더 dataMap 변경
    $scope.changeVendorFg = function (s, e) {
        if (e.panel === s.cells) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if(col.binding === "vendorFg"){
                if (item.status === "I") {
                    item.vendorNm = '';
                }
            } else if (col.binding === "vendorNm") {
                var changeVendorFg = s.rows[e.row].dataItem.vendorFg;
                switch (changeVendorFg) {
                    case '01':
                        col.dataMap = vanList;
                        break;
                    case '02':
                        col.dataMap = paycoList;
                        break;
                    case '03':
                        col.dataMap = mpayList;
                        break;
                    case '04':
                        col.dataMap = mcoupnList;
                        break;
                    case '06':
                        col.dataMap = paperVoucherList;
                        break;
                    case '07':
                        col.dataMap = taxRefundList;
                        break;
                    case '08':
                        col.dataMap = sktList;
                        break;
                }
            }
        }
    };

    // 조회
    $scope.$on("cornerCtrl", function (event, data) {
        $scope.getCornerSetting();
        event.preventDefault();
    });

    // 코너설정 데이터 조회
    $scope.getCornerSetting = function () {

        if (isNull($("#lblStoreCd").text())) {
            $scope._popMsg(messages["terminalManage.request.select.store"]);
            return false;
        }

        var terminalScope = agrid.getScope('terminalCtrl');

        var params = {};
        params.storeCd = $("#lblStoreCd").text();
        params.cornrCd = terminalScope.getCornerFgVal();

        $scope._inquirySub(baseUrl + "corner/getCornerTerminalList.sb", params, function () {
            $scope.cornerFgDataMap = new wijmo.grid.DataMap(cornerFgList, 'value', 'name');

        }, false);
    };

    // 행 추가
    $scope.addRow = function () {

        var params = {};

        var terminalScope = agrid.getScope('terminalCtrl');

        params.status = "I";
        if(isNull(terminalScope.getCornerFgVal())) {
            if(cornerFgList !== null && cornerFgList !== '') {
                params.cornrNm = cornerFgList[0].name;
            }
        }else{
            params.cornrNm = terminalScope.getCornerFgVal();
        }
        params.vendorFg = "01";
        params.vendorCd = "001";
        if($("#lblVanFixFg").text() == "Y") {
            params.vendorNm = "KOCES";
        }
        params.gChk = true;

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 코너 정보 저장
    $scope.save = function () {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var terminalScope = agrid.getScope('terminalCtrl');

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if($scope.flex.collectionView.itemsEdited[i].gChk) {
                    $scope.flex.collectionView.itemsEdited[i].status = "U";
                    $scope.flex.collectionView.itemsEdited[i].storeCd = $("#lblStoreCd").text();
                    if(!isNull(terminalScope.getCornerFgVal())){
                        $scope.flex.collectionView.itemsEdited[i].cornrCd = terminalScope.getCornerFgVal();
                    }
                    params.push($scope.flex.collectionView.itemsEdited[i]);
                }
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].gChk) {
                    $scope.flex.collectionView.itemsAdded[i].status = "I";
                    $scope.flex.collectionView.itemsAdded[i].storeCd = $("#lblStoreCd").text();
                    if(isNull(terminalScope.getCornerFgVal())){
                        $scope.flex.collectionView.itemsAdded[i].cornrCd = $scope.flex.collectionView.itemsAdded[i].cornrNm;
                    }else {
                        $scope.flex.collectionView.itemsAdded[i].cornrCd = terminalScope.getCornerFgVal();
                    }
                    params.push($scope.flex.collectionView.itemsAdded[i]);
                }
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                if($scope.flex.collectionView.itemsRemoved[i].gChk) {
                    $scope.flex.collectionView.itemsRemoved[i].status = "D";
                    $scope.flex.collectionView.itemsRemoved[i].storeCd = $("#lblStoreCd").text();
                    if(!isNull(terminalScope.getCornerFgVal())){
                        $scope.flex.collectionView.itemsRemoved[i].cornrCd = terminalScope.getCornerFgVal();
                    }
                    params.push($scope.flex.collectionView.itemsRemoved[i]);
                }
            }

            //필수값 체크
            for (var i = 0; i < params.length; i++) {

                if (params[i].status !== "D") {

                    if (params[i].cornrNm == "") {
                        $scope._popMsg(messages["terminalManage.corner"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorFg == "") {
                        $scope._popMsg(messages["terminalManage.vendorFg"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorCd == "") {
                        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorNm == "" || params[i].vendorNm == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorTermnlNo == "" || params[i].vendorTermnlNo == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.input"]);
                        return false;
                    } else {
                        if (params[i].vendorFg === "01" && params[i].vendorNm === "KCP") {
                            if (params[i].vendorTermnlNo.length != 10) {
                                $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + "는 10자리로 입력하세요.");
                                return false;
                            }
                        } else {
                            if (nvl(params[i].vendorTermnlNo.getByteLengthForOracle(),'') > 20) {
                                $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.exact.data"]);
                                return false;
                            }
                        }
                    }

                    if (params[i].vendorSerNo == "" || params[i].vendorSerNo == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.input"]);
                        return false;
                    } else {
                        if (nvl(params[i].vendorSerNo.getByteLengthForOracle(),'') > 20) {
                            $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.exact.data"]);
                            return false;
                        }
                    }

                    // BBQ는 VAN - KCP 만 저장 가능
                    // 개발 DS011
                    // 운영 DS024 H0360
                    if (params[i].status !== "D") {
                        if ($("#lblHqOfficeCd").text() == "DS011" || $("#lblHqOfficeCd").text() == "DS024" || $("#lblHqOfficeCd").text() == "H0360") {
                            if (params[i].vendorFg == "01") {
                                if (params[i].vendorNm == "KCP") {
                                } else {
                                    // BBQ 매장은 VAN - KCP 선택하여 주십시오.
                                    $scope._popMsg(messages["terminalManage.bbqSave.msg"]);
                                    return false;
                                }
                            }
                        }
                    }
                    // KOCES 총판은 벤더코드 KOCES만 저장가능
                    if($("#lblVanFixFg").text() == "Y") {
                        if (params[i].vendorFg === '01') {
                            if (params[i].vendorNm !== 'KOCES') {
                                $scope._popMsg(messages["terminalManage.vendorFg"]+"이 [01]VAN인 경우 " + messages["terminalManage.vendorCd"] + "는 [008] KOCES" + messages["terminalManage.require.select"]);
                                return false;
                            }
                        }
                    }
                }
            }

            // 벤더구분-벤더코드 중복체크
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                    var item2 = $scope.flex.collectionView.items[j];
                    if(i !== j){
                        if(item.vendorNm.toString !== null && item.vendorNm.toString !== '' && item.vendorNm.toString !== undefined
                           && item2.vendorNm.toString !== null && item2.vendorNm.toString !== '' && item2.vendorNm.toString !== undefined) {
                            if (item.cornrCd.toString() + item.vendorFg.toString() + item.vendorNm.toString() === item2.cornrCd.toString() + item2.vendorFg.toString() + item2.vendorNm.toString()) {
                                $scope._popMsg("벤더구분-벤더코드 " + getVendorFgNm(item.vendorFg.toString()) + "-" + item.vendorNm.toString() + messages["terminalManage.input.duplicate"]);
                                return false;
                            }
                        }
                    }
                }
            }

            var chkChanged = false;
            // 현재 선택한 터미널 콤보값과 기존 터미널 환경설정값이 일치하는지 확인
            if ($("#terminalFgVal").val() !== $("#orgTerminalEnvVal").val()) {
                chkChanged = true;
            }

            if (chkChanged) {
                var params2 = {};
                params2.storeCd = $("#lblStoreCd").text();
                params2.envstCd = "2028"; //코너사용설정[2028]
                params2.envstVal = $("#terminalFgVal").val();

                // 코너사용설정[2028] 환경설정값 변경
                $scope._save("/store/manage/terminalManage/terminalManage/updateTerminalEnvst.sb", params2, function () {

                    // 리스트 변경사항이 있는지 확인
                    if (params.length <= 0) {
                        // 재조회
                        terminalScope.search();
                    } else {
                        params = JSON.stringify(params);
                        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                        $scope._save(baseUrl + "corner/saveCornerTerminalInfo.sb", params, function () {
                            // 재조회
                            terminalScope.search();
                        });
                    }
                });
            } else {
                // 리스트 변경사항이 있는지 확인
                if (params.length <= 0) {
                    $scope._popMsg(messages["cmm.not.modify"]);
                    return false;
                } else {
                    params = JSON.stringify(params);
                    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                    $scope._save(baseUrl + "corner/saveCornerTerminalInfo.sb", params, function () {
                        // 재조회
                        terminalScope.search();
                    });
                }
            }
        });
    };

    // 코너 정보 삭제
    $scope.saveCornerDel = function () {
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];

            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }

        // 코너 정보 저장
        $scope.save();
    };

    // 정보 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };

}]);

// 벤더구분 코드값으로 명칭 가져오기
function getVendorFgNm (cd){
    var list = vendorFg;
    for (var i = 0; i < list.length; i++) {
        if(list[i].value === cd){
            return list[i].name;
        }
    }
}