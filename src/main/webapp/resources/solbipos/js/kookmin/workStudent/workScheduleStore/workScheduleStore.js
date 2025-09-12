/****************************************************************
 *
 * 파일명 : workScheduleStore.js
 * 설  명 : 국민대 > 근로학생관리 > 매장별 근무테이블 등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.09     김유승      1.0
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
// 업무구분
var workFgComboData = [
    {"name": "판매", "value": '1'},
    {"name": "진열", "value": '2'},
    {"name": "제조", "value": '3'}
];
/** 근무테이블 그리드 controller */
app.controller('workScheduleStoreCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workScheduleStoreCtrl', $scope, $http, true));

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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("workScheduleStoreCtrl");

        // 그리드 DataMap 설정
        $scope.workFgComboDataMap = new wijmo.grid.DataMap(workFgComboData, 'value', 'name'); // 열람구분


        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                // if (col.binding === "storeCd") {
                //   wijmo.addClass(e.cell, 'wijLink');
                //   wijmo.addClass(e.cell, 'wj-custom-readonly');
                // }
            }

        });

        // 값 변경시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "sun" || col.binding === "mon" || col.binding === "tue"
                    || col.binding === "wed" || col.binding === "thu" || col.binding === "fri"
                    || col.binding === "sat" || col.binding === "startTime" || col.binding === "endTime"
                    || col.binding === "hourPay" || col.binding === "workFg" || col.binding === "remark") {
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

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("workScheduleStoreCtrl", function (event, data) {
        $scope.getWorkScheduleStoreList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    $scope.getWorkScheduleStoreList = function () {
        // 파라미터
        var params  = {};
        params.srchDate = $scope.srchDate.selectedValue;
        params.termFg   = $("input[name=termFg]:checked").val();

        $scope.termYear = params.srchDate;
        $scope.termFg = params.termFg;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/workStudent/workScheduleStore/workScheduleStore/getWorkScheduleStoreList.sb", params);
    };

    // 추가 클릭 시 근무코드 조회
    // $scope.add = function () {
    //     if($("#workScheduleStoreStoreCd").val() === ""){
    //         $scope._popMsg("매장을 선택해주세요");
    //         return false;
    //     }
    //
    //     var params = {};
    //     params.storeCd  = $("#workScheduleStoreStoreCd").val();
    //     params.termYear = $scope.termYear;
    //     params.termFg   = $scope.termFg;
    //
    //     $scope._postJSONQuery.withPopUp("/kookmin/workStudent/workScheduleStore/workScheduleStore/addRowWorkScheduleStore.sb", params, function(response) {
    //         var list = response.data.data.list;
    //
    //         // 파라미터 설정
    //         var params2 = {};
    //
    //         if(list.length > 0) {
    //             for (var i = 0; i < list.length; i++) {
    //                 params2.gChk = true;
    //                 params2.termYear = $scope.termYear;
    //                 params2.termFg = $scope.termFg;
    //                 params2.workSchCode = list[i].workSchCode;
    //                 params2.storeCd = list[i].storeCd;
    //                 params2.storeNm = list[i].storeNm;
    //                 params2.sun = false;
    //                 params2.mon = false;
    //                 params2.tue = false;
    //                 params2.wed = false;
    //                 params2.thu = false;
    //                 params2.fri = false;
    //                 params2.sat = false;
    //                 params2.status = "I";
    //                 $scope._addRow(params2);
    //             }
    //         }
    //     });
    // }

    // 추가
    $scope.add = function () {
        if($("#workScheduleStoreStoreCd").val() === ""){
            $scope._popMsg("매장을 선택해주세요");
            return false;
        }

        var params = {};

        var arr = $("#workScheduleStoreStoreCd").val().split(",");

        // 선택한 매장 추가
        for (var i = 0; i < arr.length; i++) {
            params.gChk = true;
            params.termYear = $scope.termYear;
            params.termFg = $scope.termFg;
            params.storeCd = arr[i];
            params.sun = false;
            params.mon = false;
            params.tue = false;
            params.wed = false;
            params.thu = false;
            params.fri = false;
            params.sat = false;
            params.status = "I";
            params.workFg = '1';
            $scope._addRow(params);
        }
    }

    // 근무테이블 저장
    $scope.save = function () {
        var params = [];

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            // 입력값 체크
            if($scope.flex.collectionView.itemsAdded[i].startTime == "" || $scope.flex.collectionView.itemsAdded[i].startTime == null){
                $scope._popMsg(messages["workScheduleStore.startTime"] + messages["workScheduleStore.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].endTime == "" || $scope.flex.collectionView.itemsAdded[i].endTime == null){
                $scope._popMsg(messages["workScheduleStore.endTime"] + messages["workScheduleStore.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].hourPay == "" || $scope.flex.collectionView.itemsAdded[i].hourPay == null){
                $scope._popMsg(messages["workScheduleStore.hourPay"] + messages["workScheduleStore.inputEnv"]);
                return false;
            }
            if($scope.flex.collectionView.itemsAdded[i].workFg == "" || $scope.flex.collectionView.itemsAdded[i].workFg == null){
                $scope._popMsg(messages["workScheduleStore.workFg"] + messages["workScheduleStore.inputEnv"]);
                return false;
            }
            $scope.flex.collectionView.itemsAdded[i].startTime = $scope.flex.collectionView.itemsAdded[i].startTime.replaceAll(":","");
            $scope.flex.collectionView.itemsAdded[i].endTime = $scope.flex.collectionView.itemsAdded[i].endTime.replaceAll(":","");
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].gChk) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                // 입력값 체크
                if ($scope.flex.collectionView.itemsEdited[i].startTime == "" || $scope.flex.collectionView.itemsEdited[i].startTime == null) {
                    $scope._popMsg(messages["workScheduleStore.startTime"] + messages["workScheduleStore.inputEnv"]);
                    return false;
                }
                if ($scope.flex.collectionView.itemsEdited[i].endTime == "" || $scope.flex.collectionView.itemsEdited[i].endTime == null) {
                    $scope._popMsg(messages["workScheduleStore.endTime"] + messages["workScheduleStore.inputEnv"]);
                    return false;
                }
                if ($scope.flex.collectionView.itemsEdited[i].hourPay == "" || $scope.flex.collectionView.itemsEdited[i].hourPay == null) {
                    $scope._popMsg(messages["workScheduleStore.hourPay"] + messages["workScheduleStore.inputEnv"]);
                    return false;
                }
                if ($scope.flex.collectionView.itemsEdited[i].workFg == "" || $scope.flex.collectionView.itemsEdited[i].workFg == null) {
                    $scope._popMsg(messages["workScheduleStore.workFg"] + messages["workScheduleStore.inputEnv"]);
                    return false;
                }
                $scope.flex.collectionView.itemsEdited[i].startTime = $scope.flex.collectionView.itemsEdited[i].startTime.replaceAll(":","");
                $scope.flex.collectionView.itemsEdited[i].endTime = $scope.flex.collectionView.itemsEdited[i].endTime.replaceAll(":","");
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        $scope._save("/kookmin/workStudent/workScheduleStore/workScheduleStore/saveWorkScheduleStore.sb", params, function () {
            $scope.getWorkScheduleStoreList()
        });
    };

    // 근무테이블 그리드 행 삭제
    $scope.delete = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };

}]);
