/****************************************************************
 *
 * 파일명 : dlvrAgencyLink.js
 * 설  명 : 배달관리 - 배달정보 - 배달대행사 연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.14     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('dlvrAgencyLinkCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrAgencyLinkCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {

    };

    $scope.$on("dlvrAgencyLinkCtrl", function (event, data) {
        
        // 배달대행사 연동 현황 조회
        $scope.searchStatus();
        event.preventDefault();
    });
    
    // 배달대행사 연동 현황 조회
    $scope.searchStatus = function () {

        var params = {};

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getAgencyLink.sb", params, function (response) {

        });
    };

    // 배달대행사 연동 팝업 호출
    $scope.btnAdd = function () {
        $scope.wjDlvrAgencyRegLayer.show(true);
        $scope._broadcast('dlvrAgencyRegCtrl');
    };

    // 해제
    $scope.btnClear = function () {

        /*var chkCnt = 0;

        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                chkCnt++;
            }
        }

        if (1 > chkCnt) {
            $scope._popMsg(messages["dlvrAgencyLink.link.del.chk.msg1"]);
            return false;
        }


        if (chkCnt > 1) {
            $scope._popMsg(messages["dlvrAgencyLink.link.del.chk.msg2"]);
            return false;
        }

        // 파라미터 설정
        var params = {};
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                params.linkType = "006";
                //params.posShopId = "omspos1";
                params.mappingSequence = "20250619153940";
            }
        }*/

        var params = {};
        params.linkType = "006";
        //params.posShopId = "omspos1";
        params.mappingSequence = "20251105497150";

        // 과<br>POS 연동을 해제하시겠습니까?
        $scope._popConfirm(messages["dlvrAgencyLink.link.clear.msg"], function () {

            $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/deleteAgencyLink.sb", params, function (response) {

                var data = response.data.data.list;

                if (data.code === "0000") {

                    $scope._popMsg(data.message);

                    // 배달대행사 연동 현황 재조회
                    $scope.searchStatus();

                } else {
                    $scope._popMsg(data.message + "</br>" + data.data);
                    return;
                }
            });

        });
    };


}]);