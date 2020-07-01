/****************************************************************
 *
 * 파일명 : storeEmpWebMenu.js
 * 설  명 : 기초관리 > 매장관리 > 매장사원정보관리 > 사원메뉴권한 > 웹사이트 메뉴 Tab JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.22     이다솜      1.0
 *
 * **************************************************************/

// 모바일 메뉴 탭 클릭
function changeMobileTab(){
    var scope = agrid.getScope('storeEmpWebMenuCtrl');
    scope.changeMobileTab();
}

// 선택한 사원정보 데이터를 담기위해
var selData = [];

app.controller('storeEmpWebMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeEmpWebMenuCtrl', $scope, $http, false));

    // 팝업 오픈시 사원메뉴권한 조회
    $scope.$on("storeEmpWebMenuCtrl", function(event, data) {

        // 선택한 사원정보 데이터
        selData = data;

        // 매장마스터 계정(사원번호 0000)은 수정불가(관리자에서 해야함)
        if(selData.empNo == "0000"){
            $("#btnCopyAuth").css("display", "none");
            $("#btnRemoveMenu").css("display", "none");
            $("#btnAddMenu").css("display", "none");
        }else{
            $("#btnCopyAuth").css("display", "");
            $("#btnRemoveMenu").css("display", "");
            $("#btnAddMenu").css("display", "");
        }

        // 메뉴권한복사 콤보박스 데이터 조회 및 셋팅
        var params= {};
        params.empNo = selData.empNo;

        $.postJSON("/base/store/emp/store/authStoreEmpList.sb", params, function(result) {
                $scope._setComboData("empNoCombo", result.data.list);
            },
            function(result){
                s_alert.pop(result.message);
            }
        );

        // 메뉴 리스트 조회
        $scope.menuList(selData);
        event.preventDefault();

    });

    // 메뉴 리스트 조회
    $scope.menuList = function(data){

        // 사용메뉴 조회
        var params = [];
        params.empNo = data.empNo

        $scope._inquirySub("/base/store/emp/store/avlblMenu.sb", params, function() {

            // 미사용메뉴 조회
            var beUseMenuGrid = agrid.getScope("notUseStoreEmpWebMenuCtrl");
            beUseMenuGrid._pageView('notUseStoreEmpWebMenuCtrl', 1);

        });
    };

    // 권한복사 버튼 클릭
    $scope.copyAuth = function(){

        var param = {};
        param.empNo      = selData.empNo;
        param.copyEmpNo  = $scope.empNo;

        console.log(param);

        $.postJSONSave("/base/store/emp/store/copyAuth.sb", param, function(result) {
                var res = result.data;
                if(res > 0) {
                    $scope._popMsg(messages["cmm.copySucc"]);
                    $scope._broadcast('storeEmpWebMenuCtrl', selData);
                }
            },
            function (result) {
                s_alert.pop(result.message);
            }
        );
    }

    // 미사용등록
    $scope.notUseReg = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].empNo = selData.empNo;
                $scope.flex.collectionView.items[i].resrceCd = $scope.flex.collectionView.items[i].resrceCdSmall;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["storeEmp.require.chkNotUseMenu"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/emp/store/removeAuth.sb', params, function() {
            // 저장 후 재조회
            $scope._broadcast('storeEmpWebMenuCtrl', selData);
            $scope._broadcast('notUseStoreEmpWebMenuCtrl', selData);
        });
    };

    // 모바일 메뉴 탭 클릭
    $scope.changeMobileTab = function(){
        s_alert.pop("준비중인 메뉴입니다.");
        return;
    }

}]);

app.controller('notUseStoreEmpWebMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('notUseStoreEmpWebMenuCtrl', $scope, $http, false));

    // 팝업 오픈시 매장정보 조회
    $scope.$on("notUseStoreEmpWebMenuCtrl", function (event, data) {

        // 미사용메뉴 리스트 조회
        $scope.notUsemenuList();

    });

    // 미사용메뉴 리스트 조회
    $scope.notUsemenuList = function () {

        // 사용메뉴 조회
        var params = [];
        params.empNo = selData.empNo;
        $scope._inquirySub("/base/store/emp/store/beUseMenu.sb", params, function() {

        });
    };

    // 사용등록
    $scope.useReg = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].empNo = selData.empNo;
                $scope.flex.collectionView.items[i].resrceCd = $scope.flex.collectionView.items[i].resrceCdSmall;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["storeEmp.require.chkUseMenu"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/emp/store/addAuth.sb', params, function() {
            // 저장 후 재조회
            $scope._broadcast('notUseStoreEmpWebMenuCtrl', selData);
            $scope._broadcast('storeEmpWebMenuCtrl', selData);
        });
    };

}]);