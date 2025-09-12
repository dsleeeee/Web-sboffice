/****************************************************************
 *
 * 파일명 : workScheduleStudentReg.js
 * 설  명 : 근로학생 근무배치 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.12     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**
 *  근로학생 근무배치 팝업 조회 그리드 생성
 */
app.controller('workScheduleStudentRegCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workScheduleStudentRegCtrl', $scope, $http, false));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "studentNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "studentNo") { // 근로학생
                    $scope.wjWorkScheduleStudentRegLayer.hide();
                    var params = {};
                    params.studentNo = selectedRow.studentNo;
                    params.studentNm = selectedRow.studentNm;
                    params.sel       = $scope.sel;
                    var scope = agrid.getScope('workScheduleStudentCtrl');
                    scope.applyWorkStudent(params);

                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("workScheduleStudentRegCtrl", function(event, data) {
        $scope.getWorkStudentList();
        
        // 메인 그리드 row값
        $scope.sel = data;
        event.preventDefault();
    });
    
    // 근로학생 조회
    $scope.getWorkStudentList = function(){
        var params = {};

        $scope._inquiryMain("/kookmin/workStudent/workScheduleStudent/workScheduleStudent/getWorkStudentList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->


    // 팝업 닫기
    $scope.close = function(){
        $scope.studentNo = "";
        $scope.studentNm = "";
        $scope.department = "";

        $scope.wjWorkScheduleStudentRegLayer.hide();
    };

}]);