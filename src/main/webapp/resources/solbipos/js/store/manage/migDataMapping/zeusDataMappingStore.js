/****************************************************************
 *
 * 파일명 : zeusDataMappingStore.js
 * 설  명 : 제우스 매장 연동 신청 팝업
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.02.04    김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 검색조건1
var zeusFgData = [
    {"name":"제우스","value":"1"},
    {"name":"링크","value":"2"}
];

// 검색조건2
var hqStoreFgData = [
    {"name":"본사코드","value":"1"},
    {"name":"매장코드","value":"2"},
    {"name":"매장명","value":"3"}
];
/**
 *  제우스 매장 연동 신청 팝업
 */
app.controller('zeusDataMappingStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('zeusDataMappingStoreCtrl', $scope, $http, true));

    $scope._setComboData("zeusFgCombo", zeusFgData); // 검색조건1
    $scope._setComboData("hqStoreFgCombo", hqStoreFgData); // 검색조건2

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("zeusDataMappingStoreCtrl", function(event, data) {
        if(data === "clear") {
            $scope.$apply(function() {
                var dtlScope = agrid.getScope('zeusDataMappingStoreCtrl');
                dtlScope._gridDataInit();
                var dtlScope2 = agrid.getScope('zeusDataMappingSelectStoreCtrl');
                dtlScope2._gridDataInit();
                var dtlScope3 = agrid.getScope('lynkDataMappingSelectStoreCtrl');
                dtlScope3._gridDataInit();
                $scope.srchZeusFgCombo.selectedIndex = 0;
                $scope.srchHqStoreFgCombo.selectedIndex = 0;
                $scope.hqStoreCd = '';
            });
            return false;
        }

        // 제우스/링크 매장 조회
        $scope.getSearchZeusStoreList();
        event.preventDefault();
    });

    // 제우스/링크 매장 조회
    $scope.getSearchZeusStoreList = function(){

        var params = {};

        params.zeusFg       = $scope.srchZeusFgCombo.selectedValue;
        params.hqStoreFg    = $scope.srchHqStoreFgCombo.selectedValue;
        params.hqStoreCd    = $scope.hqStoreCd;

        $scope.zeusFg       = params.zeusFg;

        $scope._inquiryMain("/store/manage/migDataMapping/migDataMapping/getSearchZeusStoreList.sb", params, function() {
            $scope.setGrid(params.zeusFg);
        }, false);
    };
    // <-- //검색 호출 -->

    $scope.setGrid = function(zeusFg) {
        var grid = wijmo.Control.getControl("#noRegStoreGrid");
        var zeusGrid = wijmo.Control.getControl("#zeusRegStoreGrid");
        var lynkGrid = wijmo.Control.getControl("#lynkRegStoreGrid");

        if(grid.rows.length > 0){
            if(zeusFg === '1') {
                for (var i = 0; i < grid.rows.length; i++) {
                    grid.rows[i].visible = true;
                    for (var j = 0; j < zeusGrid.rows.length; j++) {
                        var gridInfo = grid.rows[i].dataItem.hqOfficeCd + grid.rows[i].dataItem.storeCd
                        var regGridInfo = zeusGrid.rows[j].dataItem.hqOfficeCd + zeusGrid.rows[j].dataItem.storeCd
                        if (gridInfo.indexOf(regGridInfo) !== -1) {
                            grid.rows[i].visible = false;
                        }
                    }
                }
            }else{
                for (var i = 0; i < grid.rows.length; i++) {
                    grid.rows[i].visible = true;
                    for (var j = 0; j < lynkGrid.rows.length; j++) {
                        var gridInfo = grid.rows[i].dataItem.hqOfficeCd + grid.rows[i].dataItem.storeCd
                        var regGridInfo = lynkGrid.rows[j].dataItem.hqOfficeCd + lynkGrid.rows[j].dataItem.storeCd
                        if (gridInfo.indexOf(regGridInfo) !== -1) {
                            grid.rows[i].visible = false;
                        }
                    }
                }
            }
            grid.refresh();
        }
    };

    // <-- 저장 -->
    $scope.regStoreMapping = function (){

        var params = new Array();

        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
                params.push(item);
            }
        }

        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        if($scope.zeusFg === "1"){
            var scope = agrid.getScope('zeusDataMappingSelectStoreCtrl');
            scope.addRow(params);
        }else{
            var scope = agrid.getScope('lynkDataMappingSelectStoreCtrl');
            scope.addRow(params);
        }

    };


    // 팝업 닫기
    $scope.close = function(){
        $scope.zeusDataMappingStoreLayer.hide();

        // 저장기능 수행후 재조회
        $scope._broadcast('zeusDataMappingCtrl');
    };
}]);


app.controller('zeusDataMappingSelectStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('zeusDataMappingSelectStoreCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 비고
                if (col.binding === "remark") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                // 비고 클릭 시 삭제
                if (col.binding === "remark") {
                    $scope.delRow(s.rows[ht.row]._idx);
                }
            }
        });
    };

    $scope.$on("zeusDataMappingSelectStoreCtrl", function(event, data) {

    });

    $scope.addRow = function (data){

        for(var i=0; i<data.length; i++){
            var params = {};
            params.hqOfficeCd   = data[i].hqOfficeCd;
            params.storeCd      = data[i].storeCd;
            params.storeNm      = data[i].storeNm
            params.remark       = '선택취소'
            $scope._addRow(params);
        }

    };

    $scope.delRow = function (rowNum){
        $scope.flex.collectionView.removeAt(rowNum);
    };

}]);

app.controller('lynkDataMappingSelectStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('lynkDataMappingSelectStoreCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 비고
                if (col.binding === "remark") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                // 비고 클릭 시 삭제
                if (col.binding === "remark") {
                    $scope.delRow(s.rows[ht.row]._idx);
                }
            }
        });
    };

    $scope.$on("lynkDataMappingSelectStoreCtrl", function(event, data) {

    });

    $scope.addRow = function (data){

        for(var i=0; i<data.length; i++){
            var params = {};
            params.hqOfficeCd   = data[i].hqOfficeCd;
            params.storeCd      = data[i].storeCd;
            params.storeNm      = data[i].storeNm
            params.remark       = '선택취소'
            $scope._addRow(params);
        }

    }

    $scope.delRow = function (rowNum){
        $scope.flex.collectionView.removeAt(rowNum);
    }

    $scope.storeMappingReg = function() {
        var zeusGrid = wijmo.Control.getControl("#zeusRegStoreGrid");
        var lynkGrid = wijmo.Control.getControl("#lynkRegStoreGrid");

        if(zeusGrid.rows.length !== lynkGrid.rows.length){
            $scope._popMsg(messages["migDataMapping.zeus.notMatchMsg"]);
            return false;
        }

        var params = new Array();

        for(var i=0; i <zeusGrid.rows.length; i++){
            var data = {};
            data.cocd       = zeusGrid.rows[i].dataItem.hqOfficeCd;
            data.buut       = zeusGrid.rows[i].dataItem.storeCd;
            data.hqOfficeCd = lynkGrid.rows[i].dataItem.hqOfficeCd;
            data.storeCd    = lynkGrid.rows[i].dataItem.storeCd;
            params.push(data);
        }

        $scope._save("/store/manage/migDataMapping/migDataMapping/getStoreMappingReg.sb", params, function(){
            var scope = agrid.getScope('zeusDataMappingStoreCtrl');
            scope.close();
        });

    };



}]);