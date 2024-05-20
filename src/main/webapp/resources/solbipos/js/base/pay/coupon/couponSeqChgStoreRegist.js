/****************************************************************
 *
 * 파일명 : couponSeqChgStoreRegist.js
 * 설  명 : 쿠폰순서 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.05.14     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매장상태구분 DropBoxDataMap
var sysStatFgComboData = [
    {"name": "전체", "value": ""},
    {"name": "오픈", "value": "1"},
    {"name": "폐점", "value": "2"},
    {"name": "중지", "value": "3"},
    {"name": "데모", "value": "9"}
];

/**
 *  쿠폰순서 매장적용 팝업 조회 그리드 생성
 */
app.controller('couponSeqChgStoreRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponSeqChgStoreRegistCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("sysStatFgCombo", sysStatFgComboData); // 매장상태구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgComboData, 'value', 'name'); // 매장상태구분
    };

    // <-- 검색 호출 -->
    $scope.$on("couponSeqChgStoreRegistCtrl", function(event, data) {
        $("#lblPayClassCd").text(data);
        $scope.searchCouponSeqChgStoreRegist();
        event.preventDefault();
    });

    $scope.searchCouponSeqChgStoreRegist = function(){
        var params = {};

        $scope._inquiryMain("/base/pay/coupon/class/getCouponSeqChgStoreRegistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $("#funcSave").click(function(e){
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].payClassCd = $("#lblPayClassCd").text();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
            if(params.length <= 0) {
                s_alert.pop(messages["cmm.not.select"]);
                return;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/pay/coupon/class/getCouponSeqChgStoreRegistSave.sb", params, function(){ $scope.close() });
        });
    });

    // 팝업 닫기
    $scope.close = function(){
        $scope.storeCd = "";
        $scope.storeNm = "";
        $scope.srchSysStatFgCombo.selectedIndex = 0;

        $scope.wjCouponSeqChgRegistLayer.hide();
    };

}]);