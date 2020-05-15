/****************************************************************
 *
 * 파일명 : emp.js
 * 설  명 : 사원별코너관리 사원별탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.14     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  사원정보 조회 그리드 생성
 */
app.controller('empCornerEmpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empCornerEmpCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFgData, 'value', 'name'); //재직구분

        // 조회
        $scope.searchEmpCornerEmpList();

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

                    var storeScope = agrid.getScope('empManageCornerCtrl');
                    storeScope._broadcast('empManageCornerCtrl', params);

                    var storeScope2 = agrid.getScope('empNoManageCornerCtrl');
                    storeScope2._broadcast('empNoManageCornerCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("empCornerEmpCtrl", function(event, data) {
        $scope.searchEmpCornerEmpList();
        event.preventDefault();
    });

    $scope.searchEmpCornerEmpList = function() {
        var params = {};

        $scope._inquiryMain("/base/store/empCorner/emp/getEmpCornerEmpList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('empManageCornerCtrl');
                storeScope._gridDataInit();

                var storeScope2 = agrid.getScope('empNoManageCornerCtrl');
                storeScope2._gridDataInit();
            });
        }, false);
    };
    // <-- //검색 호출 -->

}]);

/**
 *  관리코너 조회 그리드 생성
 */
app.controller('empManageCornerCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empManageCornerCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("empManageCornerCtrl", function(event, data) {
        $scope.searchEmpManageCornerList(data);
        event.preventDefault();
    });

    $scope.searchEmpManageCornerList = function(data) {
        var params = {};
        params.empNo = data.empNo;

        $scope._inquirySub("/base/store/empCorner/emp/getEmpManageCornerList.sb", params, function() {}, false);
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
        $scope._save("/base/store/empCorner/emp/getEmpManageCornerDelete.sb", params, function(){
            var storeScope = agrid.getScope('empCornerEmpCtrl');
            storeScope.searchEmpCornerEmpList();
        });
    };

}]);

/**
 *  미관리코너 조회 그리드 생성
 */
app.controller('empNoManageCornerCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empNoManageCornerCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("empNoManageCornerCtrl", function(event, data) {
        $scope.searchEmpNoManageCornerList(data);
        event.preventDefault();
    });

    $scope.searchEmpNoManageCornerList = function(data) {
        var params = {};
        params.empNo = data.empNo;

        $scope._inquirySub("/base/store/empCorner/emp/getEmpNoManageCornerList.sb", params, function() {}, false);
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
        $scope._save("/base/store/empCorner/emp/getEmpManageCornerSave.sb", params, function(){
            var storeScope = agrid.getScope('empCornerEmpCtrl');
            storeScope.searchEmpCornerEmpList();
        });
    };

}]);