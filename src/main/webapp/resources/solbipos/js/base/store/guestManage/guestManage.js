/****************************************************************
 *
 * 파일명 : guestManage.js
 * 설  명 : 객층관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.05     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  객층관리 조회 그리드 생성
 */
app.controller('guestManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('guestManageCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 검색
        $scope.searchGuestManageGrid();
    };

    // <-- 검색 호출 -->
    $scope.$on("guestManageCtrl", function(event, data) {
        // 그리드 검색
        $scope.searchGuestManageGrid();
        event.searchGuestManage();
    });

    $scope.searchGuestManage = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp("/base/store/guestManage/guestManage/getGuestManageList.sb", params, function(response) {
            var list = response.data.data.list;

            if(list.length > 0) {
                $("#srchGuestNm1").val(list[0].guestNm);
                $("#srchGuestNm2").val(list[1].guestNm);
                $("#srchGuestNm3").val(list[2].guestNm);
                $("#srchGuestNm4").val(list[3].guestNm);
                $("#srchGuestNm5").val(list[4].guestNm);
                $("#srchGuestNm6").val(list[5].guestNm);
            }
        }, false);
    };

    // 그리드 검색(바로 검색 및 저장 때문에)
    $scope.searchGuestManageGrid = function() {
        var params = {};

        $scope._inquiryMain("/base/store/guestManage/guestManage/getGuestManageList.sb", params, function() {
            // 조회
            $scope.searchGuestManage();
        }, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function () {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var guestNmVal = "";
                if(i == 0) {
                    guestNmVal = $("#srchGuestNm1").val();
                } else if(i == 1) {
                    guestNmVal = $("#srchGuestNm2").val();
                } else if(i == 2) {
                    guestNmVal = $("#srchGuestNm3").val();
                } else if(i == 3) {
                    guestNmVal = $("#srchGuestNm4").val();
                } else if(i == 4) {
                    guestNmVal = $("#srchGuestNm5").val();
                } else if(i == 5) {
                    guestNmVal = $("#srchGuestNm6").val();
                }

                var status = "";
                if(guestNmVal == "" || guestNmVal == null) {
                    status = "D"
                } else {
                    status = "I"
                }

                $scope.flex.collectionView.items[i].status = status;
                $scope.flex.collectionView.items[i].guestCd = $scope.flex.collectionView.items[i].guestCd;
                $scope.flex.collectionView.items[i].guestNm = guestNmVal;

                params.push($scope.flex.collectionView.items[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/base/store/guestManage/guestManage/getGuestManageSave.sb", params, function(){
                // 그리드 검색
                $scope.searchGuestManageGrid();
            });
        });
    };

    // 매장적용
    $scope.storeApply = function () {
        $scope.wjGuestManageStoreRegistLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 객층관리 매장적용 팝업 핸들러 추가
        $scope.wjGuestManageStoreRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('guestManageStoreRegistCtrl', null);
            }, 50)
        });
    });
}]);