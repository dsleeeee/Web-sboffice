/****************************************************************
 *
 * 파일명 : vendrStoreRegist.js
 * 설  명 : 상품 팝업 - 매입처/매장 추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.11.13     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 구분 데이터
var tradeFgComboData = [
    {"name": "위탁", "value": "0"},
    {"name": "외상", "value": "1"},
    {"name": "현매(월)", "value": "2"},
    {"name": "현매(거)", "value": "3"}
];

// 구분 데이터
var tradeFormComboData = [
    {"name": "일반", "value": "1"}
];

/**
 *  등록 거래처 조회 그리드 생성
 */
app.controller('prodVendrRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodVendrRegistCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.tradeFgComboDataMap = new wijmo.grid.DataMap(tradeFgComboData, 'value', 'name'); // 구분
        $scope.tradeFormComboDataMap = new wijmo.grid.DataMap(tradeFormComboData, 'value', 'name'); // 형태

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "vendrCd"){ // 거래처코드
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {

            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "vendrCd") { // 거래처코드 클릭
                    if (selectedRow.status !== "I") {
                        selectedRow.prodCd = $scope.paramData.prodCd;
                        $scope._broadcast('prodStoreRegistCtrl', selectedRow);
                    }
                }
            }
        });

    };

    // <-- 검색 호출 -->
    $scope.$on("prodVendrRegistCtrl", function(event, data) {
        $scope.wjVendrStoreRegistLayer.show(true);
        $scope.paramData = data;
        $scope.getSearchRegistVendr(data);

        var emptyParams = {};
        $scope._broadcast('prodStoreRegistCtrl', emptyParams);
        event.preventDefault();
    });

    // 거래처 조회
    $scope.getSearchRegistVendr = function(data){

        var params = {};
        params.prodCd = data.prodCd;

        $scope._inquiryMain("/base/prod/prod/prod/getSearchRegistVendr.sb", params, function() {
        });
    };
    // <-- //검색 호출 -->

    // 거래처 추가 팝업 호출
    $scope.addVendr = function (){
        $scope._broadcast('searchVendrCtrl');
        event.preventDefault();
    }

    // 거래처 추가
    $scope.addRow = function(data) {
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.vendrCd = data.vendrCd;
        params.vendrNm = data.vendrNm;
        params.tradeFg = "";
        params.tradeForm = "";
        params.acquireUprc = "";
        params.acquireRate = "";
        $scope._addRow(params);
    };

    // 거래처 저장
    $scope.saveProdVendr = function () {

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].tradeFg === null || $scope.flex.collectionView.itemsEdited[i].tradeFg === "" ){
                $scope._popMsg(messages["prod.vendrStoreRegist.tradeFg"] + " " + messages["cmm.require.text"]); // 구분을 입력하세요.
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].tradeForm === null || $scope.flex.collectionView.itemsEdited[i].tradeForm === "" ){
                $scope._popMsg(messages["prod.vendrStoreRegist.tradeForm"] + " " + messages["cmm.require.text"]); // 형태를 입력하세요.
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].acquireUprc === null || $scope.flex.collectionView.itemsEdited[i].acquireUprc === "" ){
                $scope._popMsg(messages["prod.vendrStoreRegist.acquireUprc"] + " " + messages["cmm.require.text"]); // 매입가를 입력하세요.
                return false;
            }
            if($scope.flex.collectionView.itemsEdited[i].acquireRate === null || $scope.flex.collectionView.itemsEdited[i].acquireRate === "" ){
                $scope._popMsg(messages["prod.vendrStoreRegist.acquireRate"] + " " + messages["cmm.require.text"]); // 매입율을 입력하세요.
                return false;
            }
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            $scope.flex.collectionView.itemsEdited[i].prodCd = $scope.paramData.prodCd;
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if($scope.flex.collectionView.itemsAdded[i].tradeFg === null || $scope.flex.collectionView.itemsAdded[i].tradeFg === "" ){
                $scope._popMsg(messages["prod.vendrStoreRegist.tradeFg"] + " " + messages["cmm.require.text"]); // 구분을 입력하세요.
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].tradeForm === null || $scope.flex.collectionView.itemsAdded[i].tradeForm === "" ){
                $scope._popMsg(messages["prod.vendrStoreRegist.tradeForm"] + " " + messages["cmm.require.text"]); // 형태를 입력하세요.
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].acquireUprc === null || $scope.flex.collectionView.itemsAdded[i].acquireUprc === "" ){
                $scope._popMsg(messages["prod.vendrStoreRegist.acquireUprc"] + " " + messages["cmm.require.text"]); // 매입가를 입력하세요.
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].acquireRate === null || $scope.flex.collectionView.itemsAdded[i].acquireRate === "" ){
                $scope._popMsg(messages["prod.vendrStoreRegist.acquireRate"] + " " + messages["cmm.require.text"]); // 매입율을 입력하세요.
                return false;
            }
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            $scope.flex.collectionView.itemsAdded[i].prodCd = $scope.paramData.prodCd;
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/prod/prod/saveProdVendr.sb", params, function(){
            $scope.getSearchRegistVendr($scope.paramData);
        });
    }
}]);


/**
 *  매장 조회 그리드 생성
 */
app.controller('prodStoreRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodStoreRegistCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("prodStoreRegistCtrl", function(event, data) {

        $scope.paramData = data;

        // 거래처별 매장 조회
        $scope.getSearchRegistStore(data);
        event.preventDefault();
    });

    // 거래처별 매장조회
    $scope.getSearchRegistStore = function(data){

        var params = data;

        $scope._inquirySub("/base/prod/prod/prod/getSearchRegistStore.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장 저장
    $scope.saveProdVendrStore = function () {

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].prodCd = $scope.paramData.prodCd;
            $scope.flex.collectionView.itemsEdited[i].vendrCd = $scope.paramData.vendrCd;
            $scope.flex.collectionView.itemsEdited[i].seq = $scope.paramData.seq;
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/prod/prod/saveProdVendrStore.sb", params, function(){});
    }

}]);