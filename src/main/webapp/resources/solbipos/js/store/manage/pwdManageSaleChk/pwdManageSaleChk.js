/****************************************************************
 *
 * 파일명 : pwdManage.js
 * 설  명 : 매출조회 비밀번호 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.05     김유승      1.0
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

/**********************************************************************
 *  사원목록 그리드
 **********************************************************************/
app.controller('pwdManageSaleChkCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('pwdManageSaleChkCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("empOrgnFg", empOrgnFgData);
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 선택 사원
    $scope.emp;
    $scope.setEmp = function(emp) {
        $scope.emp = emp;
    };
    $scope.getEmp = function(){
        return $scope.emp;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 내 콤보박스 데이터 set
        $scope.salePwYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 사원 선택 가능
                if (col.binding === "userId") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 사원선택
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                // 선택 사원 비밀번호 변경 팝업
                if ( col.binding === "userId" ) {
                    var selectedData = s.rows[ht.row].dataItem;
                    selectedData.currPage = $scope._getPagingInfo('curr');
                    $scope.setEmp(selectedData);
                    $scope.pwdChangeSaleChkPopupLayer.show(true, function(){
                        var changeScope = agrid.getScope('pwdChangeSaleChkCtrl');
                        $scope.$apply(function() {
                            changeScope.pwdChange = null;
                        });
                    });
                    event.preventDefault();
                }
            }
        });
    };

    // 조회 버튼 클릭
    $scope.$on("pwdManageSaleChkCtrl", function(event, data) {
        $scope.getPwdManageSaleChkList();
        event.preventDefault();
    });

    // 회원 목록 조회
    $scope.getPwdManageSaleChkList = function(){
        var params = {};
        params.listScale = $scope.listScale;
        params.empOrgnFg = $scope.empOrgnFgCombo.selectedValue;
        console.log(params.empOrgnFg);

        $scope._inquiryMain("/store/manage/pwdManageSaleChk/pwdManageSaleChk/getPwdManageSaleChkList.sb", params, function() {
        });
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 비밀번호 변경 팝업 핸들러 추가
        $scope.pwdChangeSaleChkPopupLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('pwdChangeSaleChkCtrl', $scope.getEmp());
            }, 50)
        });
    });

}]);