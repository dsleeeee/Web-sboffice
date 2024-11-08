/****************************************************************
 *
 * 파일명 : prodBatchChange.js
 * 설  명 : 상품정보일괄변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.28     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 상품등록구분
var regFgData = [
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];
// 상품등록구분
var regFgTotData = [
    {"name":"전체","value":""},
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];
// 판매상품여부
var saleProdYnTotData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"N"},
    {"name":"사용","value":"Y"}
];
// 포인트적립여부
var pointSaveYnTotData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"N"},
    {"name":"사용","value":"Y"}
];
// 가격관리구분
var prcCtrlFgTotData = [
    {"name":"전체","value":""},
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];

/**
 *  상품정보일괄변경 그리드 생성
 */
app.controller('prodBatchChangeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodBatchChangeCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("saleProdYnCombo", saleProdYnTotData); // 판매상품여부
    $scope._setComboData("pointSaveYnCombo", pointSaveYnTotData); // 포인트적립여부
    $scope._setComboData("prcCtrlFgCombo", prcCtrlFgTotData); // 가격관리구분
    $scope._setComboData("regFgCombo", regFgTotData); // 상품등록구분
    $scope._setComboData("vatFgCombo", vatFgData); // 과세여부

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("saleProdYnChgCombo", saleProdYnData); // 판매상품여부
    $scope._setComboData("pointSaveYnChgCombo", pointSaveYnData); // 포인트적립여부
    $scope._setComboData("prcCtrlFgChgCombo", prcCtrlFgData); // 가격관리구분
    $scope._setComboData("vatFgChgCombo", vatFgData2); // 과세여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.saleProdYnDataMap = new wijmo.grid.DataMap(saleProdYnData, 'value', 'name'); // 판매상품여부
        $scope.pointSaveYnDataMap = new wijmo.grid.DataMap(pointSaveYnData, 'value', 'name'); // 포인트적립여부
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
        $scope.regFgDataMap = new wijmo.grid.DataMap(regFgData, 'value', 'name'); // 상품등록구분
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData2, 'value', 'name'); // 과제여부구분

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });


        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "saleProdYn" || col.binding === "pointSaveYn" || col.binding === "mapProdCd" || col.binding === "prcCtrlFg" || col.binding === "vatFg") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    //값 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    // <-- 검색 호출 -->
    $scope.$on("prodBatchChangeCtrl", function(event, data) {
        $scope.searchProdBatchChange();
        event.preventDefault();
    });

    $scope.searchProdBatchChange = function(){
        var params = {};
        params.listScale = $scope.listScaleCombo.text;

        $scope._inquiryMain("/base/prod/prodBatchChange/prodBatchChange/getProdBatchChangeList.sb", params, function() {

            // 프랜차이즈매장은 본사에서 등록한 상품 선택 불가
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {

                var grid = wijmo.Control.getControl("#wjGridProdBatchChange");
                var rows = grid.rows;

                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    var item = $scope.flex.collectionView.items[i];
                    if (item.regFg === "H") {
                        item.gChk = false;
                        rows[i].isReadOnly = true;
                    }
                }
            }

        }, false);
    };
    // <-- //검색 호출 -->

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 일괄적용
    $scope.batchChange = function(chgGubun) {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // 판매상품여부
                if(chgGubun == "saleProdYnChg") {
                    $scope.flex.collectionView.items[i].saleProdYn = $scope.saleProdYnChg;
                }
                // 포인트적립여부
                else if(chgGubun == "pointSaveYnChg") {
                    $scope.flex.collectionView.items[i].pointSaveYn = $scope.pointSaveYnChg;
                }
                // 가격관리구분
                else if(chgGubun == "prcCtrlFgChg") {
                    $scope.flex.collectionView.items[i].prcCtrlFg = $scope.prcCtrlFgChg;
                }
                // 매핑상품코드
                else if(chgGubun == "mapProdCdChg") {
                    $scope.flex.collectionView.items[i].mapProdCd = $scope.mapProdCdChg;
                }
                // 과세여부
                else if(chgGubun == "vatFgChg") {
                    $scope.flex.collectionView.items[i].vatFg = $scope.vatFgChg;
                }
            }
        }
        $scope.flex.refresh();
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 판매상품여부 변경으로 인한 배민 주문 차단
        var saleProdYnCntChk = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                if($scope.flex.collectionView.items[i].oldSaleProdYn == "Y" && $scope.flex.collectionView.items[i].saleProdYn == "N") {
                    saleProdYnCntChk = saleProdYnCntChk + 1;
                }
            }
        }
        if(saleProdYnCntChk > 0) {
            if (!confirm("판매상품여부 미사용으로 변경 시 주문 앱에서 주문이 불가능해 집니다.\n그래도 변경 하시겠습니까?")) {
                return false;
            }
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 프랜 매장일때만
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    if($scope.flex.collectionView.items[i].gChk) {
                        // REG_FG 상품등록구분 S인 상품만 수정가능
                        if ($scope.flex.collectionView.items[i].regFg === "H") {
                            $scope._popMsg(messages["prodBatchChange.regFgHqBlank"]); // 상품등록구분이 '본사'인 상품은 수정할 수 없습니다.
                            return false;
                        }
                    }
                }
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status  = "U";

                    // 매핑상품코드 앞뒤 공백 및 엔터값 제거
                    if ($scope.flex.collectionView.items[i].mapProdCd !== "" && $scope.flex.collectionView.items[i].mapProdCd !== null) {
                        $scope.flex.collectionView.items[i].mapProdCd = $scope.flex.collectionView.items[i].mapProdCd.trim().removeEnter();
                        if ($scope.flex.collectionView.items[i].mapProdCd.length <= 0) {
                            $scope.flex.collectionView.items[i].mapProdCd = null;
                        }
                    }else{
                        $scope.flex.collectionView.items[i].mapProdCd = null;
                    }

                    if(nvl($scope.flex.collectionView.items[i].saleProdYn,'') !== nvl($scope.flex.collectionView.items[i].oldSaleProdYn,'')
                        || nvl($scope.flex.collectionView.items[i].pointSaveYn,'') !==nvl($scope.flex.collectionView.items[i].oldPointSaveYn,'')
                        || nvl($scope.flex.collectionView.items[i].mapProdCd,'') !== nvl($scope.flex.collectionView.items[i].oldMapProdCd,'')
                        || nvl($scope.flex.collectionView.items[i].prcCtrlFg,'') !== nvl($scope.flex.collectionView.items[i].oldPrcCtrlFg,'')
                        || nvl($scope.flex.collectionView.items[i].vatFg,'') !== nvl($scope.flex.collectionView.items[i].oldVatFg,'')) {

                        params.push($scope.flex.collectionView.items[i]);

                    }
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodBatchChange/prodBatchChange/getProdBatchChangeSave.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchProdBatchChange();
    };
    // <-- //그리드 저장 -->

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.prodCd = $("#srchProdCd").val();
        params.prodNm = $("#srchProdNm").val();
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $("#srchBarCd").val();
        params.saleProdYn = $scope.saleProdYn;
        params.pointSaveYn = $scope.pointSaveYn;
        params.mapProdCd = $("#srchMapProdCd").val();

        if(orgnFg === "HQ"){
            params.prcCtrlFg = $scope.prcCtrlFg;
        }else if (orgnFg === "STORE" && hqOfficeCd !== "00000"){
            params.regFg = $scope.regFg;
        }
        params.vatFg = $scope.vatFg;

        $scope._broadcast('prodBatchChangeExcelCtrl', params);
    };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('prodBatchChangeExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodBatchChangeExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.saleProdYnDataMap = new wijmo.grid.DataMap(saleProdYnData, 'value', 'name'); // 판매상품여부
        $scope.pointSaveYnDataMap = new wijmo.grid.DataMap(pointSaveYnData, 'value', 'name'); // 포인트적립여부
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
        $scope.regFgDataMap = new wijmo.grid.DataMap(regFgData, 'value', 'name'); // 상품등록구분
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData2, 'value', 'name'); // 과제여부구분

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodBatchChangeExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/prod/prodBatchChange/prodBatchChange/getProdBatchChangeExcelList.sb", params, function() {

            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, messages["prodBatchChange.prodBatchChange"] + "_(" + messages["prodBatchChange.tab1"] + ")_" + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);