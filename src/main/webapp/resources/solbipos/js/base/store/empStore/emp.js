/****************************************************************
 *
 * 파일명 : emp.js
 * 설  명 : 사원별매장관리 사원별탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.12     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  사원정보 조회 그리드 생성
 */
app.controller('empStoreEmpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empStoreEmpCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name'); //재직구분

        // 조회
        $scope.searchEmpStoreEmpList();

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "empNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 사원번호 클릭시 상세정보 조회
                if ( col.binding === "empNo") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.empNo = selectedRow.empNo;

                    $("#empTitle").html("[" + selectedRow.empNo + "]" + selectedRow.empNm);
                    var storeScope = agrid.getScope('empManageStoreCtrl');
                    storeScope._broadcast('empManageStoreCtrl', params);

                    var storeScope2 = agrid.getScope('empNoManageStoreCtrl');
                    storeScope2._broadcast('empNoManageStoreCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("empStoreEmpCtrl", function(event, data) {
        $("#empTitle").html("");
        $scope.searchEmpStoreEmpList();
        event.preventDefault();
    });

    $scope.searchEmpStoreEmpList = function() {
        var params = {};

        $scope._inquiryMain("/base/store/empStore/emp/getEmpStoreEmpList.sb", params, function() {
            $scope.$apply(function() {
                if($("#empTitle").html() === ""){
                    var storeScope2 = agrid.getScope('empManageStoreCtrl');
                    storeScope2._gridDataInit();

                    var storeScope23 = agrid.getScope('empNoManageStoreCtrl');
                    storeScope23._gridDataInit();
                }
            });
        }, false);
    };
    // <-- //검색 호출 -->

}]);

/**
 *  관리매장 조회 그리드 생성
 */
app.controller('empManageStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empManageStoreCtrl', $scope, $http, true));
    var empNo;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태
    };

    // <-- 검색 호출 -->
    $scope.$on("empManageStoreCtrl", function(event, data) {
        empNo = data.empNo;
        $scope.searchEmpManageStoreList(data);
        event.preventDefault();
    });

    $scope.searchEmpManageStoreList = function(data) {
        var params = {};
        params.empNo = empNo;

        $scope._inquirySub("/base/store/empStore/emp/getEmpManageStoreList.sb", params, function() {}, false);
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
        $scope._save("/base/store/empStore/emp/getEmpManageStoreDelete.sb", params, function(){
            var storeScope = agrid.getScope('empStoreEmpCtrl');
            storeScope.searchEmpStoreEmpList();
            $scope.searchEmpManageStoreList(empNo);
            var storeScope3 = agrid.getScope('empNoManageStoreCtrl');
            storeScope3.searchEmpNoManageStoreList(empNo);
        });
    };

}]);

/**
 *  미관리매장 조회 그리드 생성
 */
app.controller('empNoManageStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empNoManageStoreCtrl', $scope, $http, true));
    var empNo;
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태
    };

    // <-- 검색 호출 -->
    $scope.$on("empNoManageStoreCtrl", function(event, data) {
        empNo = data.empNo;
        $scope.searchEmpNoManageStoreList(data);
        event.preventDefault();
    });

    $scope.searchEmpNoManageStoreList = function(data) {
        var params = {};
        params.empNo = empNo;

        $scope._inquirySub("/base/store/empStore/emp/getEmpNoManageStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 추가버튼
    $scope.add = function() {
        var params = new Array();
        var all = false;

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                if($scope.flex.collectionView.items[i].storeCd === "ALL"){
                    all = true;
                }
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(all) {
            params.length = 0;
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/empStore/emp/getEmpManageStoreSave.sb", params, function(){
            var storeScope = agrid.getScope('empStoreEmpCtrl');
            storeScope.searchEmpStoreEmpList();
            var storeScope2 = agrid.getScope('empManageStoreCtrl');
            storeScope2.searchEmpManageStoreList(empNo);
            $scope.searchEmpNoManageStoreList(empNo);
        });
    };

}]);