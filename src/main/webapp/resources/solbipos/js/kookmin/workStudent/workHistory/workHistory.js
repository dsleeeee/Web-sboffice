/****************************************************************
 *
 * 파일명 : workHistory.js
 * 설  명 : 국민대 > 근로학생관리 > 근무내역 조회/수정 JavaScript
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

// 업무구분
var workStatusComboData = [
    {"name": "결근", "value": 'N'},
    {"name": "출근", "value": 'Y'}
];

/** 근무내역 그리드 controller */
app.controller('workHistoryCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workHistoryCtrl', $scope, $http, false));

    // 근무일자 셋팅
    $scope.workDate = wcombo.genDateVal("#srchWorkDate", gvStartDate);

    // 근로시작, 근로종료 셋팅
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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 값 변경시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "realStartTime" || col.binding === "realEndTime" || col.binding === "modReason") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("workHistoryCtrl", function (event, data) {
        $scope.getWorkHistoryList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 근로학생 배치 조회
    $scope.getWorkHistoryList = function () {
        // 파라미터
        var params  = {};
        params.workDate = wijmo.Globalize.format($scope.workDate.value, 'yyyyMMdd'); // 조회기간

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/workStudent/workHistory/workHistory/getWorkHistoryList.sb", params, function () {
        });
    };

    // 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    // 근무내역 저장
    $scope.save = function () {
        var params = [];

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].gChk) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                if($scope.flex.collectionView.itemsEdited[i].realStartTime !== null && $scope.flex.collectionView.itemsEdited[i].realStartTime !== ""
                   && $scope.flex.collectionView.itemsEdited[i].realEndTime !== null && $scope.flex.collectionView.itemsEdited[i].realEndTime !== ""){
                    // 근무 시작시간이 종료시간보다 빠른지 확인
                    var [startHours, startMin] = $scope.flex.collectionView.itemsEdited[i].realStartTime.split(":").map(Number);
                    var startTime = new Date();
                    startTime.setHours(startHours, startMin, 0, 0); // 시, 분, 초, 밀리초 설정

                    var [endHours, endMin] = $scope.flex.collectionView.itemsEdited[i].realEndTime.split(":").map(Number);
                    var endTime = new Date();
                    endTime.setHours(endHours, endMin, 0, 0); // 시, 분, 초, 밀리초 설정

                    if (startTime > endTime) {
                        // 근무시작시간과 근무종료시간을 확인하세요.
                        $scope._popMsg(messages['workScheduleStore.timeChk.error']);
                        return false;
                    }
                }
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].gChk) {
                $scope.flex.collectionView.itemsEdited[i].workDate = $scope.flex.collectionView.itemsEdited[i].workDate.replaceAll("-","");
                if($scope.flex.collectionView.itemsEdited[i].realStartTime !== null && $scope.flex.collectionView.itemsEdited[i].realStartTime !== "") {
                    $scope.flex.collectionView.itemsEdited[i].realStartTime = $scope.flex.collectionView.itemsEdited[i].realStartTime.replaceAll(":", "");
                }
                if($scope.flex.collectionView.itemsEdited[i].realEndTime !== null && $scope.flex.collectionView.itemsEdited[i].realEndTime !== "") {
                    $scope.flex.collectionView.itemsEdited[i].realEndTime = $scope.flex.collectionView.itemsEdited[i].realEndTime.replaceAll(":", "");
                }
                $scope.flex.collectionView.itemsEdited[i].baseStartTime = $scope.flex.collectionView.itemsEdited[i].baseStartTime.replaceAll(":", "");
                $scope.flex.collectionView.itemsEdited[i].baseEndTime = $scope.flex.collectionView.itemsEdited[i].baseEndTime.replaceAll(":", "");
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
        }


        $scope._save("/kookmin/workStudent/workHistory/workHistory/saveWorkHistory.sb", params, function () {
            //재조회
            $scope.getWorkHistoryList();
        });
    };

    // 근무내역 일괄등록
    $scope.regCommute = function () {
        // 근무내역 일괄등록하시겠습니까?
        $scope._popConfirm(messages['workHistory.msg.missCommuteReg'], function () {


            if($scope.flex.rows.length <= 0){
                $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
                return false;
            }

            var params = new Array();
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
                var item = $scope.flex.collectionView.items[i];
                if(item.realStartTime === null || item.realStartTime === "" || item.realEndTime === null || item.realEndTime === ""){
                    item.workDate = item.workDate.replaceAll("-","");
                    item.baseStartTime = item.baseStartTime.replaceAll(":","");
                    item.baseEndTime = item.baseEndTime.replaceAll(":","");
                    if(item.realStartTime !== null && item.realStartTime !== ""){
                        item.realStartTime = item.realStartTime.replaceAll(":","");
                    }
                    if(item.realEndTime !== null && item.realEndTime !== ""){
                        item.realEndTime = item.realEndTime.replaceAll(":","");
                    }
                    params.push(item);
                }

            }
            $scope._save("/kookmin/workStudent/workHistory/workHistory/saveRegCommuteAll.sb", params, function () {
                $scope.getWorkHistoryList();
            });
        });
    }

}]);
