/****************************************************************
 *
 * 파일명 : smsTelNoStop.js
 * 설  명 : 발신번호차단 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.08     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  발신번호차단 조회 그리드 생성
 */
app.controller('smsTelNoStopCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsTelNoStopCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부
    };

    // <-- 검색 호출 -->
    $scope.$on("smsTelNoStopCtrl", function(event, data) {
        $scope.searchSmsTelNoStop();
        event.preventDefault();
    });

    $scope.searchSmsTelNoStop = function() {
        var params = {};

        $scope._inquiryMain("/adi/sms/smsTelNoManage/smsTelNoStop/getSmsTelNoStopList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].telNo = $scope.flex.collectionView.itemsEdited[i].telNo.replaceAll("-", "");
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/adi/sms/smsTelNoManage/smsTelNoStop/getSmsTelNoStopSaveUpdate.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchSmsTelNoStop();
    };

    // 일반번호 인증요청 처리
    $scope.smsGeneralNoManage = function() {
        $scope.wjSmsGeneralNoManageLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 일반번호 인증요청 처리 팝업 핸들러 추가
        $scope.wjSmsGeneralNoManageLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsGeneralNoManageCtrl', null);
            }, 50)
        });
    });
}]);