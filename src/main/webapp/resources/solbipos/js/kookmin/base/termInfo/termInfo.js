/****************************************************************
 *
 * 파일명 : termInfo.js
 * 설  명 : 국민대 > 기초관리 > 학기정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.05     김유승      1.0
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

// 학기구분
var termFgComboData = [
    {"name": "1학기", "value": '1'},
    {"name": "하계방학", "value": '2'},
    {"name": "2학기", "value": '3'},
    {"name": "동계방학", "value": '4'}
];
/**
 * 학기정보 그리드 생성
 */
app.controller('termInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('termInfoCtrl', $scope, $http, false));

    // 조회일자 설정
    $scope.srchDate = new wijmo.input.ComboBox('#srchDate', {
        itemsSource: yearsData,
        selectedValue: new Date().getFullYear().toString(), // 기본 선택값: 올해
        isEditable: false // 사용자가 직접 입력하지 못하게 설정 (선택 전용)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.termFgComboDataMap = new wijmo.grid.DataMap(termFgComboData, 'value', 'name'); // 열람구분

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "termYear" || col.binding === "termFg") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "termYear" || col.binding === "termFg") {
                var dataItem = s.rows[elements.row].dataItem;
                elements.cancel = true;
            }
        });

        // 값 변경시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "startDate" || col.binding === "endDate") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

    };

    // 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
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

    $scope.$on("termInfoCtrl", function(event, data) {
        $scope.getTermInfoList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 학기정보 그리드 조회
    $scope.getTermInfoList = function(){

        var params = {};
        params.srchDate       = $scope.srchDate.selectedValue;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/kookmin/base/termInfo/termInfo/getTermInfoList.sb', params, function(){
            var grid = wijmo.Control.getControl("#wjGridTermInfo");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];

                // 행간격 고정
                rows[i].height = 25;
            }
        }, false);

    };

    // 학기정보 저장
    $scope.save = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {

            if ($scope.flex.collectionView.itemsEdited[i].gChk) {
                var startDate = wijmo.Globalize.format($scope.flex.collectionView.itemsEdited[i].startDate, 'yyyy-MM-dd');
                var endDate = wijmo.Globalize.format($scope.flex.collectionView.itemsEdited[i].endDate, 'yyyy-MM-dd');

                var startDt = new Date(startDate);
                var endDt = new Date(endDate);
                // 시작일자가 종료일자보다 빠른지 확인
                if(startDt.getTime() > endDt.getTime()){
                    $scope._popMsg(messages['cmm.dateChk.error'] + " (" + $scope.termFgComboDataMap.getDisplayValue($scope.flex.collectionView.itemsEdited[i].termFg) + ")");
                    return false;
                }

                if ($scope.flex.collectionView.itemsEdited[i].startDate !== null && $scope.flex.collectionView.itemsEdited[i].startDate !== '') {
                    $scope.flex.collectionView.itemsEdited[i].startDate = startDate.replaceAll('-', '');
                }
                if ($scope.flex.collectionView.itemsEdited[i].endDate !== null && $scope.flex.collectionView.itemsEdited[i].endDate !== '') {
                    $scope.flex.collectionView.itemsEdited[i].endDate = endDate.replaceAll('-', '');
                }
                // 입력값 체크
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/kookmin/base/termInfo/termInfo/saveTermInfo.sb', params, function(){ $scope.getTermInfoList() });
    }

}]);



