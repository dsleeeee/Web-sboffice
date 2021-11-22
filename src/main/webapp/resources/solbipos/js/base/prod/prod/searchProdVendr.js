/****************************************************************
 *
 * 파일명 : searchProdVendr.js
 * 설  명 : 상품 거래처 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.12.16     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  적용 상품 거래처 조회 그리드 생성
 */
app.controller('searchProdVendrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchProdVendrCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("searchProdVendrCtrl", function(event, data) {
        if(data.chkVendrCd !== "") {
            $scope.searchProdVendr(data);
        }
        event.preventDefault();
    });

    $scope.searchProdVendr = function(data){
        var arrVendrCd = data.chkVendrCd.split(",");
        var arrVendrNm = data.chkVendrNm.split(",");

        for (var i = 0; i < arrVendrCd.length; i++) {
            // 파라미터 설정
            var params = {};
            params.gChk = false;
            params.vendrCd = arrVendrCd[i];
            params.vendrNm = arrVendrNm[i];

            // 추가기능 수행 : 파라미터
            $scope._addRow(params);
        }

        var chkVendrCd = "";
        var chkVendrNm = "";

        // 등록후 그리드에 거래처값 가져오기
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            chkVendrCd += $scope.flex.collectionView.items[i].vendrCd + ",";
            chkVendrNm += $scope.flex.collectionView.items[i].vendrNm + ",";
        }
        chkVendrCd = chkVendrCd.substring(0, chkVendrCd.length-1);
        chkVendrNm = chkVendrNm.substring(0, chkVendrNm.length-1);
        $("#lblChkVendrCd").text(chkVendrCd);
        $("#lblChkVendrNm").text(chkVendrNm);
    };
    // <-- //검색 호출 -->

    // 미적용 상품 이동(삭제)
    $scope.deleteVendr = function(){
        var paramsChk = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                paramsChk.push($scope.flex.collectionView.items[i]);
            }
        }
        if(paramsChk.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        // 삭제
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];

            if(item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }

        var chkVendrCd = "";
        var chkVendrNm = "";

        // 삭제하고 남은 그리드에 거래처값 가져오기
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            chkVendrCd += $scope.flex.collectionView.items[i].vendrCd + ",";
            chkVendrNm += $scope.flex.collectionView.items[i].vendrNm + ",";
        }
        chkVendrCd = chkVendrCd.substring(0, chkVendrCd.length-1);
        chkVendrNm = chkVendrNm.substring(0, chkVendrNm.length-1);
        $("#lblChkVendrCd").text(chkVendrCd);
        $("#lblChkVendrNm").text(chkVendrNm);

        // 선택한값 추가하기
        var storeScope = agrid.getScope('searchNoProdVendrCtrl');
        storeScope._broadcast('searchNoProdVendrCtrl', null);
    };

}]);


/**
 *  미적용 상품 거래처 조회 그리드 생성
 */
app.controller('searchNoProdVendrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchNoProdVendrCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 호출시 searchNoProdVendrCtrl 로 안쓰고 searchNoProdVendrTotalCtrl 로 따로 쓴 이유
    // 맨처음에 창띄울때 값을 1번만 담으려고
    // searchNoProdVendrCtrl 로 같이쓰게되면 미적용상품이동 할때 수정한 값에 최초 값이 엎어쳐짐으로 오른쪽 그리드가 수정이 안됨
    $scope.$on("searchNoProdVendrTotalCtrl", function(event, data) {
        if(data.prodCd === "" || data.prodCd === undefined || data.prodCd === null) {
            $("#lblTitle").text(" [신규]");
            $("#lblChkVendrCd").text("");
            $("#lblChkVendrNm").text("");
            $("#lblProdCd").text("");
        } else {
            $("#lblTitle").text(" [" + data.prodCd + "]");
            $("#lblChkVendrCd").text(data.vendrCd);
            $("#lblChkVendrNm").text(data.vendrNm);
            $("#lblProdCd").text(data.prodCd);
        }

        // 미적용 상품 거래처
        var storeScope = agrid.getScope('searchNoProdVendrCtrl');
        storeScope.$apply(function() {
            storeScope._gridDataInit();
        });
        storeScope._broadcast('searchNoProdVendrCtrl', null);

        // 적용 상품 거래처
        var params = {};
        params.chkVendrCd = $("#lblChkVendrCd").text();
        params.chkVendrNm = $("#lblChkVendrNm").text();

        var storeScope2 = agrid.getScope('searchProdVendrCtrl');
        storeScope2.$apply(function() {
            storeScope2._gridDataInit();
        });
        storeScope2._broadcast('searchProdVendrCtrl', params);

        event.preventDefault();
    });

    // <-- 검색 호출 -->
    $scope.$on("searchNoProdVendrCtrl", function(event, data) {
        // 미적용 상품 거래처
        $scope.searchNoProdVendr();
        event.preventDefault();
    });

    $scope.searchNoProdVendr = function(){
        var params = {};
        params.prodCd = $("#lblProdCd").text();
        params.chkVendrCd = $("#lblChkVendrCd").text();

        $scope._inquirySub("/base/prod/prod/prod/getSearchNoProdVendrList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 적용 상품 이동(등록)
    $scope.registVendr = function(){
        var paramsChk = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                paramsChk.push($scope.flex.collectionView.items[i]);
            }
        }
        if(paramsChk.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        var chkVendrCd = "";
        var chkVendrNm = "";

        // 선택한값 가져오기
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                chkVendrCd += $scope.flex.collectionView.items[i].vendrCd + ",";
                chkVendrNm += $scope.flex.collectionView.items[i].vendrNm + ",";
            }
        }

        // 삭제
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];

            if(item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }

        var params = {};
        params.chkVendrCd = chkVendrCd.substring(0, chkVendrCd.length-1);
        params.chkVendrNm = chkVendrNm.substring(0, chkVendrNm.length-1);

        // 선택한값 추가하기
        var storeScope = agrid.getScope('searchProdVendrCtrl');
        storeScope._broadcast('searchProdVendrCtrl', params);
    };

    //  적용
    $("#funcApply").click(function(e){
        var scope = agrid.getScope('prodModifyCtrl');
        scope.prodModifyInfo.vendrCd = $("#lblChkVendrCd").text();
        scope.prodModifyInfo.vendrNm = $("#lblChkVendrNm").text();

        $scope.wjSearchProdVendrLayer.hide();
    });

}]);