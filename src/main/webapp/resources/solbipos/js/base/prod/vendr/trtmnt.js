/****************************************************************
 *
 * 파일명 : trtmnt.js
 * 설  명 : 거래처 취급상품 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.04.23     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 닫기
function closePop(){
    var scope = agrid.getScope('vendrTrtmntCtrl');
    scope.close();
}

// 상품분류 검색조건 팝업
function popUpProdClass(){
    var scope = agrid.getScope('vendrTrtmntCtrl');
    scope.popUpProdClass();
}

// 상품분류정보 선택취소
function delProdClass(){
    var scope = agrid.getScope('vendrTrtmntCtrl');
    scope.delProdClass();
}

// 탭변경
function changeTab(val){
    var scope = agrid.getScope('vendrTrtmntCtrl');
    scope.changeTab(val);
}

/**
 *  거래처 취급상품
 */
app.controller('vendrTrtmntCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrTrtmntCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    // 해당 scope 호출
    $scope.$on("vendrTrtmntCtrl", function(event, vendrCd) {;
        $scope.vendrTrtmntList();
        event.preventDefault();
    });

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        //$scope.prodClassCd = prodClassCd;
                        //$scope.prodClassCdNm = response.data.data;_prodClassCd
                        $("#sProdClassCd").val(prodClassCd);
                        $("#sProdClassNm").val(response.data.data);
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $("#sProdClassCd").val("");
        $("#sProdClassNm").val("");
    }

    // 취급상품조회
    $scope.vendrTrtmntList = function() {

        var vendrScope = agrid.getScope("vendrCtrl");
        var param = {};

        param.vendrCd = vendrScope.getVendrInfo().vendrCd;
        param.prodCd  = $("#sProdCd").val();
        param.prodNm  = $("#sProdNm").val();
        param.trtmntYn = 'Y';

        $scope._inquirySub("/base/prod/vendr/trtMnt/list.sb", param, function() {

            // 미취급상품 조회
            var noVendrTrtmntGrid = agrid.getScope("noVendrTrtmntCtrl");
            noVendrTrtmntGrid._pageView('noVendrTrtmntCtrl', 1);

        }, false);
    };

    // 삭제버튼
    $scope.del = function() {

        var vendrScope = agrid.getScope("vendrCtrl");
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].vendrCd = vendrScope.getVendrInfo().vendrCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        $scope._save("/base/prod/vendr/trtMnt/delete.sb", params, function(){
            $scope.vendrTrtmntList();
        });

    };

    // 닫기
    $scope.close = function () {
        $scope.wjVendrTrtmntLayer.hide();

        // 재조회
        $scope._broadcast('vendrCtrl');
    };

    // 탭변경
    $scope.changeTab = function(val) {

        var vendrScope = agrid.getScope("vendrCtrl");

        if(val ==  "1"){ // 거래처 등록 Tab 클릭 시

            if(vendrScope.getVendrInfo().vendrCd == ""){
                s_alert.pop(messages["vendr.request.regist.vendr"]);
                return false;

            }else{
                $scope.wjVendrTrtmntLayer.hide();
                $scope.wjVendrInfoLayer.show();

                // 상세화면 조회
                var data = {};
                data.vendrCd = vendrScope.getVendrInfo().vendrCd;

                $scope._broadcast('vendrInfoCtrl', data);
                event.preventDefault();

            }

        }else{ // 취급상품 Tab 클릭 시

        }
    };


}]);

/**
 *  거래처 미취급상품
 */
app.controller('noVendrTrtmntCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noVendrTrtmntCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    // 해당 scope 호출
    $scope.$on("noVendrTrtmntCtrl", function(event, vendrCd) {
        $scope.noVendrTrtmntList();
        event.preventDefault();
    });

    // 미취급상품 조회
    $scope.noVendrTrtmntList = function() {

        var vendrScope = agrid.getScope("vendrCtrl");
        var param = {};

        param.vendrCd = vendrScope.getVendrInfo().vendrCd;
        param.prodCd  = $("#sProdCd").val();
        param.prodNm  = $("#sProdNm").val();
        param.prodClassCd = $("#sProdClassCd").val();
        param.trtmntYn = 'N';

        $scope._inquirySub("/base/prod/vendr/trtMnt/list.sb", param, function(result) {});

    };

    // 등록버튼
    $scope.reg = function() {

        var vendrScope = agrid.getScope("vendrCtrl");
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].vendrCd = vendrScope.getVendrInfo().vendrCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        $scope._save("/base/prod/vendr/trtMnt/save.sb", params, function(){
            var vendrTrtmntGrid = agrid.getScope("vendrTrtmntCtrl");
            vendrTrtmntGrid.vendrTrtmntList();
        });
    };

}]);
