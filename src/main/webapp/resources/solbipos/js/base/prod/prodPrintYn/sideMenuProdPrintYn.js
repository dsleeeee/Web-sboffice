/****************************************************************
 *
 * 파일명 : prodOptionPrintYn.js
 * 설  명 : 출력여부관리 - 사이드메뉴관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.06.28    김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 고정상품구분
var fixProdFgData = [
    {"name":"선택","value":"0"},
    {"name":"고정","value":"1"}
];
var fixProdFgAllData = [
    {"name":"전체","value":""},
    {"name":"선택","value":"0"},
    {"name":"고정","value":"1"}
];

// 세트구분
var sdselTypeFgData = [
    {"name":"세트","value":"C"},
    {"name":"싱글세트","value":"S"}
];

// 필수선택여부
var requireYnData = [
    {"name":"선택안함","value":"N"},
    {"name":"필수선택","value":"Y"}
];

/**
 *  사이드메뉴관리 조회 그리드 생성
 */
app.controller('sideMenuProdPrintYnCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sideMenuProdPrintYnCtrl', $scope, $http, false));

    // 콤보박스 셋팅
    $scope._setComboData("fixProdFgCombo", fixProdFgAllData); // 구분
    $scope._setComboData("printYnCombo", printYnAllData); // 출력여부
    $scope._setComboData("printYnChgCombo", printYnData); // 일괄변경 - 출력여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.sdselGrpFixProdFgDataMap = new wijmo.grid.DataMap(fixProdFgData, 'value', 'name'); // 구분
        $scope.sdselTypeFgDataMap = new wijmo.grid.DataMap(sdselTypeFgData, 'value', 'name'); // 세트구분
        $scope.requireYnDataMap = new wijmo.grid.DataMap(requireYnData, 'value', 'name'); // 필수선택여부
        $scope.fixProdFgDataMap = new wijmo.grid.DataMap(fixProdFgData, 'value', 'name'); // 구분
        $scope.printYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 출력여부

        // 그리드 값 변경 시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "printYn") {
                    item.gChk = true;
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("sideMenuProdPrintYnCtrl", function(event, data) {
        $scope.searchSideMenuProdPrintYn();
        event.preventDefault();
    });

    $scope.searchSideMenuProdPrintYn = function() {
        var params = {};
        params.printYn = $scope.printYn;
        params.listScale = 500;

        $scope._inquiryMain("/base/prod/prodPrintYn/sideMenuProdPrintYn/getSideMenuProdPrintYnList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

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
                // 출력여부
                if(chgGubun == "printYnChg") {
                    $scope.flex.collectionView.items[i].printYn = $scope.printYnChg;
                }
            }
        }
        $scope.flex.refresh();
    };

    // 저장
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 저장하시겠습니까?
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodPrintYn/sideMenuProdPrintYn/getSideMenuProdPrintYnSave.sb", params, function(){
                $scope.searchSideMenuProdPrintYn();
            });
        });
    };

}]);