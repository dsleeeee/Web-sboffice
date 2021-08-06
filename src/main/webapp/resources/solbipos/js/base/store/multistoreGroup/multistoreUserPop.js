/****************************************************************
 *
 * 파일명 : multistoreUserPop.js
 * 설  명 : 다중매장그룹관리 - 기능사용자 선택 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.03     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();
var vRowNo = "";
var vMultistoreUserId = "";

app.controller('multistoreUserPopCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('multistoreUserPopCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 팝업 오픈 시, 기능사용자 조회
    $scope.$on("multistoreUserPopCtrl", function(event, data) {

        $scope.multistoreUserPopLayer.show(true);

        // 선택한 다중매장그룹정보 보여주기
        $("#lblInfo").text("[" + data.multistoreCd + "] " + data.multistoreNm);

        // 선택한 rowNo 갖고 있기(부모창에 Set 할때 사용)
        vRowNo = data.rowNo;
        vMultistoreUserId = data.multistoreUserId;

        // 기능사용자조회
        $scope.searchUser(data);
        event.preventDefault();

    });

    // 기능사용자 조회
    $scope.searchUser = function (data) {

        var params = data;

        $scope._inquirySub("/base/store/multistoreGroup/multistoreGroup/getMultiStoreUserList.sb", params, function () {

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(vMultistoreUserId.indexOf(item.userId) != -1){
                    item.gChk = true;
                }
            }

        });
    };

    // 기능사용자 선택
    $scope.btnSelectUser = function () {

        // 선택한 기능사용자 ','로 이어 붙이기
        var strUserId = "";
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) {
                strUserId += item.userId + ','
            }
        }

        // 부모창에 기능사용자 id set
        var params = new Array();
        params.multistoreUserId = strUserId.substr(0, strUserId.length-1);
        params.rowNo = vRowNo;

        var multistoreGroupGrid = agrid.getScope("multistoreGroupCtrl");
        multistoreGroupGrid.setMultistoreUserId(params);

        $scope.multistoreUserPopLayer.hide(true);

    }

}]);
