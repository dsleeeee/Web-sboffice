/****************************************************************
 *
 * 파일명 : storeProdOptionPrintYn.js
 * 설  명 : 매장별출력여부관리 - 옵션관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.24    김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 출력여부
var printYnData = [
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];
var printYnAllData = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

/**
 *  옵션관리 조회 그리드 생성
 */
app.controller('storeProdOptionPrintYnCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdOptionPrintYnCtrl', $scope, $http, false));

    // 콤보박스 셋팅
    $scope._setComboData("printYnCombo", printYnAllData); // 출력여부
    $scope._setComboData("printYnChgCombo", printYnData); // 일괄변경 - 출력여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); // 사용여부
        $scope.hqPrintYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 본사출력여부
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
    $scope.$on("storeProdOptionPrintYnCtrl", function(event, data) {
        $scope.searchStoreProdOptionPrintYn();
        event.preventDefault();
    });

    $scope.searchStoreProdOptionPrintYn = function() {
        if($("#storeProdOptionPrintYnStoreCd").val() == ""){
            $scope._popMsg(messages["cmm.require.selectStore"]); // 매장을 선택해 주세요.
            return false;
        }

        var params = {};
        params.storeCd = $("#storeProdOptionPrintYnStoreCd").val();
        params.printYn = $scope.printYn;

        $scope._inquiryMain("/base/prod/storeProdPrintYn/storeProdOptionPrintYn/getStoreProdOptionPrintYnList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeProdOptionPrintYnStoreShow = function () {
        $scope._broadcast('storeProdOptionPrintYnStoreCtrl');
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
            $scope._save("/base/prod/storeProdPrintYn/storeProdOptionPrintYn/getStoreProdOptionPrintYnSave.sb", params, function(){
                $scope.searchStoreProdOptionPrintYn();
            });
        });
    };

}]);