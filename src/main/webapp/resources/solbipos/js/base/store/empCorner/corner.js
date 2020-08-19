/****************************************************************
 *
 * 파일명 : corner.js
 * 설  명 : 사원별코너관리 코너별탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.15     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  코너정보 조회 그리드 생성
 */
app.controller('empCornerCornerCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empCornerCornerCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태

        // 조회
        $scope.searchEmpCornerCornerList();

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "cornrCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 코너코드 클릭시 상세정보 조회
                if ( col.binding === "cornrCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.cornrCd = selectedRow.cornrCd;

                    var storeScope = agrid.getScope('cornerManageEmpCtrl');
                    storeScope._broadcast('cornerManageEmpCtrl', params);

                    var storeScope2 = agrid.getScope('cornerNoManageEmpCtrl');
                    storeScope2._broadcast('cornerNoManageEmpCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("empCornerCornerCtrl", function(event, data) {
        $scope.searchEmpCornerCornerList();
        event.preventDefault();
    });

    $scope.searchEmpCornerCornerList = function() {
        var params = {};

        $scope._inquiryMain("/base/store/empCorner/corner/getEmpCornerCornerList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('cornerManageEmpCtrl');
                storeScope._gridDataInit();

                var storeScope2 = agrid.getScope('cornerNoManageEmpCtrl');
                storeScope2._gridDataInit();
            });
        }, false);
    };
    // <-- //검색 호출 -->

}]);

/**
 *  관리사원 조회 그리드 생성
 */
app.controller('cornerManageEmpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cornerManageEmpCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name'); //재직구분
    };

    // <-- 검색 호출 -->
    $scope.$on("cornerManageEmpCtrl", function(event, data) {
        $scope.searchCornerManageEmpList(data);
        event.preventDefault();
    });

    $scope.searchCornerManageEmpList = function(data) {
        var params = {};
        params.cornrCd = data.cornrCd;

        $scope._inquirySub("/base/store/empCorner/corner/getCornerManageEmpList.sb", params, function() {}, false);
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
        $scope._save("/base/store/empCorner/corner/getCornerManageEmpDelete.sb", params, function(){
            var storeScope = agrid.getScope('empCornerCornerCtrl');
            storeScope.searchEmpCornerCornerList();
        });
    };

}]);

/**
 *  미관리사원 조회 그리드 생성
 */
app.controller('cornerNoManageEmpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cornerNoManageEmpCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name'); //재직구분
    };

    // <-- 검색 호출 -->
    $scope.$on("cornerNoManageEmpCtrl", function(event, data) {
        $scope.searchCornerNoManageEmpList(data);
        event.preventDefault();
    });

    $scope.searchCornerNoManageEmpList = function(data) {
        var params = {};
        params.cornrCd = data.cornrCd;

        $scope._inquirySub("/base/store/empCorner/corner/getCornerNoManageEmpList.sb", params, function() {}, false);
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
        $scope._save("/base/store/empCorner/corner/getCornerManageEmpSave.sb", params, function(){
            var storeScope = agrid.getScope('empCornerCornerCtrl');
            storeScope.searchEmpCornerCornerList();
        });
    };

}]);