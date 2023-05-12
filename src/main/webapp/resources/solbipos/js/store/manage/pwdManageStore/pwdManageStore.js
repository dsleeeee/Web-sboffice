/****************************************************************
 *
 * 파일명 : pwdManageStore.js
 * 설  명 : 비밀번호 임의변경(매장) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 조회조건 DropBoxDataMap
var empOrgnFgData = [
    {"name":"전체","value":""},
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];

/**
 *  비밀번호 임의변경(매장) 그리드 생성
 */
app.controller('pwdManageStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pwdManageStoreCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("empOrgnFg", empOrgnFgData);
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 내 콤보박스 데이터 set
        $scope.empOrgnFgDataMap = new wijmo.grid.DataMap(empOrgnFgData, 'value', 'name');
        $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');
        $scope.webUseYnDataMap = new wijmo.grid.DataMap(webUseYn, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 사용자ID
                if (col.binding === "userId") {
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 사용자명
                if(col.binding === "userNm"){
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 사원선택
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedData = s.rows[ht.row].dataItem;
                // 사용자ID 클릭시 비밀번호 변경 팝업
                if ( col.binding === "userId" ) {
                    $scope.setEmp(selectedData);
                    $scope.pwdChangePopupLayer.show(true, function(){
                        var changeScope = agrid.getScope('pwdChangeCtrl');
                        $scope.$apply(function() {
                            changeScope.pwdChange = null;
                        });
                    });
                    event.preventDefault();
                }

                // 사용자명 클릭시 로그인 잠금해제 팝업
                if ( col.binding === "userNm" ) {
                    $scope.pwdUnlockPopupLayer.show(true);
                    $scope._broadcast('pwdUnlockCtrl', selectedData);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("pwdManageStoreCtrl", function(event, data) {
        $scope.searchPwdManageStore();
        event.preventDefault();
    });

    $scope.searchPwdManageStore = function(){
        // 파라미터
        var params = {};
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/store/manage/pwdManageStore/pwdManageStore/getPwdManageStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 사원
    $scope.emp;
    $scope.setEmp = function(emp) {
        $scope.emp = emp;
    };
    $scope.getEmp = function(){
        return $scope.emp;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 비밀번호 변경 팝업 핸들러 추가
        $scope.pwdChangePopupLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('pwdChangeCtrl', $scope.getEmp());
            }, 50)
        });
    });

}]);