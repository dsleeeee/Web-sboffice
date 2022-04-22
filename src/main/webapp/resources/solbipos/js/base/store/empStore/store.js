/****************************************************************
 *
 * 파일명 : store.js
 * 설  명 : 사원별매장관리 매장별탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.13     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매장 조회 그리드 생성
 */
app.controller('empStoreStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empStoreStoreCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태

        // 조회
        $scope.searchEmpStoreStoreList();

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "storeCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 매장코드 클릭시 상세정보 조회
                if ( col.binding === "storeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.storeCd = selectedRow.storeCd;
                    $("#storeTitle").html("[" + selectedRow.storeCd + "]" + selectedRow.storeNm);
                    var storeScope = agrid.getScope('storeManageEmpCtrl');
                    storeScope._broadcast('storeManageEmpCtrl', params);

                    var storeScope2 = agrid.getScope('storeNoManageEmpCtrl');
                    storeScope2._broadcast('storeNoManageEmpCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("empStoreStoreCtrl", function(event, data) {
        $("#storeTitle").html("");
        $scope.searchEmpStoreStoreList();
        event.preventDefault();
    });

    $scope.searchEmpStoreStoreList = function() {
        var params = {};

        $scope._inquiryMain("/base/store/empStore/store/getEmpStoreStoreList.sb", params, function() {
            $scope.$apply(function() {
                if($("#storeTitle").html() === ""){
                    var storeScope = agrid.getScope('storeManageEmpCtrl');
                    storeScope._gridDataInit();

                    var storeScope2 = agrid.getScope('storeNoManageEmpCtrl');
                    storeScope2._gridDataInit();
                }
            });
        }, false);
    };
    // <-- //검색 호출 -->

}]);

/**
 *  관리사원 조회 그리드 생성
 */
app.controller('storeManageEmpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeManageEmpCtrl', $scope, $http, true));
    var storeCd;
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name'); //재직구분
    };

    // <-- 검색 호출 -->
    $scope.$on("storeManageEmpCtrl", function(event, data) {
        storeCd = data.storeCd;
        $scope.searchStoreManageEmpList(data);
        event.preventDefault();
    });

    $scope.searchStoreManageEmpList = function(data) {
        var params = {};
        params.storeCd = storeCd;

        $scope._inquirySub("/base/store/empStore/store/getStoreManageEmpList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 삭제버튼
    $scope.del = function() {
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

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/empStore/store/getStoreManageEmpDelete.sb", params, function(){
            var storeScope = agrid.getScope('empStoreStoreCtrl');
            storeScope.searchEmpStoreStoreList();
            $scope.searchStoreManageEmpList(storeCd);
            var storeScope3 = agrid.getScope('storeNoManageEmpCtrl');
            storeScope3.searchStoreNoManageEmpList(storeCd);
        });
    };

}]);

/**
 *  미관리사원 조회 그리드 생성
 */
app.controller('storeNoManageEmpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeNoManageEmpCtrl', $scope, $http, true));
    var storeCd;
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name'); //재직구분
    };

    // <-- 검색 호출 -->
    $scope.$on("storeNoManageEmpCtrl", function(event, data) {
        storeCd = data.storeCd;
        $scope.searchStoreNoManageEmpList(data);
        event.preventDefault();
    });

    $scope.searchStoreNoManageEmpList = function(data) {
        var params = {};
        params.storeCd = storeCd;

        $scope._inquirySub("/base/store/empStore/store/getStoreNoManageEmpList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 추가버튼
    $scope.add = function() {
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

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/empStore/store/getStoreManageEmpSave.sb", params, function(){
            var storeScope = agrid.getScope('empStoreStoreCtrl');
            storeScope.searchEmpStoreStoreList();
            var storeScope2 = agrid.getScope('storeManageEmpCtrl');
            storeScope2.searchStoreManageEmpList(storeCd);
            $scope.searchStoreNoManageEmpList(storeCd);
        });
    };

}]);