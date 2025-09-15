/****************************************************************
 *
 * 파일명 : workScheduleStudent.js
 * 설  명 : 국민대 > 근로학생관리 > 근로학생 배치 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.11     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var currentYear = new Date().getFullYear() + 2;
var yearsData = [];
for (let y = 2000; y <= currentYear; y++) {
    yearsData.push(y.toString());
}

// 근무배치여부
var regFgComboData = [
    {"name": "전체", "value": ''},
    {"name": "등록", "value": 'Y'},
    {"name": "미등록", "value": 'N'}
];

// 업무구분
var workFgComboData = [
    {"name": "판매", "value": '1'},
    {"name": "진열", "value": '2'},
    {"name": "제조", "value": '3'}
];
/** 근로학생 배치 그리드 controller */
app.controller('workScheduleStudentCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workScheduleStudentCtrl', $scope, $http, false));

    // 조회일자 설정
    $scope.srchDate = new wijmo.input.ComboBox('#srchDate', {
        itemsSource: yearsData,
        selectedValue: new Date().getFullYear().toString(), // 기본 선택값: 올해
        isEditable: false // 사용자가 직접 입력하지 못하게 설정 (선택 전용)
    });

    $scope.timeList = [];

    for (let h = 0; h < 24; h++) {
        for (let m = 0; m < 60; m += 5) {
            var hh = h.toString().padStart(2, '0');
            var mm = m.toString().padStart(2, '0');
            var time = `${hh}:${mm}`;
            $scope.timeList.push({ id: time, name: time });
        }
    }

    // DataMap으로 생성
    $scope.timeDataMap = new wijmo.grid.DataMap($scope.timeList, 'id', 'name');

    $scope._setComboData("regFgAllComboData", regFgComboData); // 근무배치여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함

        // 그리드 DataMap 설정
        $scope.workFgComboDataMap = new wijmo.grid.DataMap(workFgComboData, 'value', 'name'); // 업무구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "studentNm") {
                  wijmo.addClass(e.cell, 'wijLink');
                  wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 값 변경시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "startDay" || col.binding === "endDay" || col.binding === "remark") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "studentNm") { // 근로학생
                    $scope.wjWorkScheduleStudentRegLayer.show();
                    // 그리드 row값
                    var grid  = $scope.flex;
                    var sel = grid.selection.row;
                    $scope._broadcast('workScheduleStudentRegCtrl',sel);
                }
            }
        });

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("workScheduleStudentCtrl", function (event, data) {
        $scope.getWorkScheduleStudentList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 근로학생 배치 조회
    $scope.getWorkScheduleStudentList = function () {
        // 파라미터
        var params  = {};
        params.srchDate = $scope.srchDate.selectedValue;
        params.termFg   = $("input[name=termFg]:checked").val();
        params.regFg    = $scope.regFgAllCombo.selectedValue;

        $scope.termYear = params.srchDate;
        $scope.termFg = params.termFg;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/workStudent/workScheduleStudent/workScheduleStudent/getWorkScheduleStudentList.sb", params, function () {
            var grid = wijmo.Control.getControl("#wjGridWorkScheduleStudent");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];

                // 행간격 고정
                rows[i].height = 25;
            }
            $scope.flex.refresh();
        });
    };

    // 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    // 팝업에서 선택한 값 그리드에 적용
    $scope.applyWorkStudent = function(data){

        if (data !== null && data !== undefined) {
            var grid = $scope.flex;
            var item = grid.collectionView.items[data.sel];

            grid.collectionView.editItem(item);

            item.studentNo = data.studentNo;
            item.studentNm = data.studentNm;

            $scope.checked(item);

            grid.collectionView.commitEdit();
            grid.refresh();
        }
    }

    // 달력 모듈 값 없으면 null 처리
    $scope.initInputDate = function(s) {
        s.valueChanged.addHandler(function(sender) {
            // 셀 위치 찾기
            var grid = $scope.flex;
            var cell = sender.hostElement;
            var ht = grid.hitTest(cell);
            var row = ht.row;
            var col = ht.col;


            if (row != null && col != null) {
                // 셀 데이터 가져오기
                var item = grid.rows[row].dataItem;
                var colName = grid.columns[col].binding;

                // 값이 없으면 null 처리
                if (!sender.value) {
                    item[colName] = null;
                    grid.invalidate();
                }
            }
        });
    };



    // 근로학생 저장
    $scope.save = function () {
        var params = [];

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].gChk) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                // 입력값 체크
                if ($scope.flex.collectionView.itemsEdited[i].studentNo == "" || $scope.flex.collectionView.itemsEdited[i].studentNo == null) {
                    $scope._popMsg(messages["workScheduleStudent.studentNo"] + messages["cmm.inputEnv"]);
                    return false;
                }
                if ($scope.flex.collectionView.itemsEdited[i].startDay == "" || $scope.flex.collectionView.itemsEdited[i].startDay == null) {
                    $scope._popMsg(messages["workScheduleStudent.startDay"] + messages["cmm.inputEnv"]);
                    return false;
                }
                if ($scope.flex.collectionView.itemsEdited[i].endDay == "" || $scope.flex.collectionView.itemsEdited[i].endDay == null) {
                    $scope._popMsg(messages["workScheduleStudent.endDay"] + messages["cmm.inputEnv"]);
                    return false;
                }
                var startDay = wijmo.Globalize.format($scope.flex.collectionView.itemsEdited[i].startDay, 'yyyy-MM-dd');
                var endDay = wijmo.Globalize.format($scope.flex.collectionView.itemsEdited[i].endDay, 'yyyy-MM-dd');

                var startDt = new Date(startDay);
                var endDt = new Date(endDay);
                // 시작일자가 종료일자보다 빠른지 확인
                if(startDt.getTime() > endDt.getTime()){
                    $scope._popMsg(messages['workScheduleStudent.dateChk.error'] + " (" + $scope.flex.collectionView.itemsEdited[i].workSchCode + ")");
                    return false;
                }

                if ($scope.flex.collectionView.itemsEdited[i].startDay !== null && $scope.flex.collectionView.itemsEdited[i].startDay !== '') {
                    $scope.flex.collectionView.itemsEdited[i].startDay = startDay.replaceAll('-', '');
                }
                if ($scope.flex.collectionView.itemsEdited[i].endDay !== null && $scope.flex.collectionView.itemsEdited[i].endDay !== '') {
                    $scope.flex.collectionView.itemsEdited[i].endDay = endDay.replaceAll('-', '');
                }
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
        }

        $scope._save("/kookmin/workStudent/workScheduleStudent/workScheduleStudent/saveWorkScheduleStudent.sb", params, function () {
            $scope.getWorkScheduleStudentList()
        });
    };

    // 근로학생 그리드 행 삭제
    $scope.delete = function(){
        var params = [];
        $scope._popConfirm(messages["workScheduleStudent.studentNm"] + '을 ' + messages["cmm.choo.delete"], function() {
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                var item = $scope.flex.collectionView.itemsEdited[i];
                if (item.gChk) {
                    $scope.flex.collectionView.itemsEdited[i].status = "D";
                    $scope.flex.collectionView.itemsEdited[i].studentNo = '';
                    $scope.flex.collectionView.itemsEdited[i].startDay = '';
                    $scope.flex.collectionView.itemsEdited[i].endDay = '';
                    params.push($scope.flex.collectionView.itemsEdited[i]);
                }
            }
            $scope._save("/kookmin/workStudent/workScheduleStudent/workScheduleStudent/saveWorkScheduleStudent.sb", params, function () {
                $scope.getWorkScheduleStudentList()
            });
        });
    };

}]);
