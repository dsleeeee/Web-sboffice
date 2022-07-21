/****************************************************************
 *
 * 파일명 : memberTerms.js
 * 설  명 : 이벤트등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.07.06     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  이벤트등록 조회 그리드 생성
 */
app.controller('specificDayMemoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('specificDayMemoCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    // 이벤트 일자
    var specificDay = wcombo.genDateVal("#specificDay", gvStartDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "specificDayMemo") {
                    if(orgnFg === "STORE" && item.specificNo < '8000'){
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
                if (col.binding === "specificDay" || col.binding === "yoil") {
                    if(item.yoil === "토") {
                        wijmo.addClass(e.cell, 'blue');
                    } else if(item.yoil === "일") {
                        wijmo.addClass(e.cell, 'red');
                    }
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "specificDayMemo") {
                    item.gChk = true;
                }
            }
            s.collectionView.commitEdit();
        });

        $scope.searchSpecificDayMemo();
    };

    // <-- 검색 호출 -->
    $scope.$on("specificDayMemoCtrl", function(event, data) {
        $scope.searchSpecificDayMemo();
        event.preventDefault();
    });

    $scope.searchSpecificDayMemo = function() {
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/base/store/specificDayMemo/specificDayMemo/getSpecificDayMemoList.sb", params, function() {

            var grid = wijmo.Control.getControl("#wjGrid");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];

                if(orgnFg === "STORE" && item.specificNo < '8000'){
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }
        });
    };
    // <-- //검색 호출 -->

    $scope.registEvt = function (event) {
        if (event.keyCode === 13) { // 이벤트가 enter 이면
            $scope.regist();
        }
    }

    // 신규등록
    $scope.regist = function (){
        if($scope.specificDayMemo === undefined || $scope.specificDayMemo === ""){
            $scope._popMsg(messages["specificDayMemo.specificDayMemo.null"]);
            return false;
        }
        var params = {};
        params.specificDay = wijmo.Globalize.format(specificDay.value, 'yyyyMMdd');
        params.specificDayMemo = $scope.specificDayMemo;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/specificDayMemo/specificDayMemo/getSpecificDayMemoRegist.sb", params, function(){
            $scope.searchSpecificDayMemo();
            $scope.specificDayMemo = "";
        });
    };

    // 삭제
    $scope.delete = function (){
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            // 파라미터 설정
            var params = [];
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }
            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }
            // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/store/specificDayMemo/specificDayMemo/getSpecificDayMemoDelete.sb', params, function() {
                $scope.searchSpecificDayMemo();
            });
        });
    };

    // 저장
    $scope.save = function (){
        // 파라미터 설정
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                if(item.specificDayMemo === undefined || item.specificDayMemo === ""){
                    $scope._popMsg(messages["specificDayMemo.specificDayMemo.null"]);
                    return false;
                } else {
                    params.push(item);
                }
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/specificDayMemo/specificDayMemo/getSpecificDayMemoSave.sb', params, function() {
            $scope.searchSpecificDayMemo();
        });
    }
}]);