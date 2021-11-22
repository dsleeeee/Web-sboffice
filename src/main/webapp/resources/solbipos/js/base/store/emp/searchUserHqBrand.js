/****************************************************************
 *
 * 파일명 : searchUserHqBrand.js
 * 설  명 : 관리브랜드 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.11.22     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  적용 관리브랜드 조회 그리드 생성
 */
app.controller('searchUserHqBrandCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchUserHqBrandCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("searchUserHqBrandCtrl", function(event, data) {
        if(data.chkHqBrandCd !== "") {
            $scope.searcUserHqBrand(data);
        }
        event.preventDefault();
    });

    $scope.searcUserHqBrand = function(data){
        var arrHqBrandCd = data.chkHqBrandCd.split(",");
        var arrHqBrandNm = data.chkHqBrandNm.split(",");

        for (var i = 0; i < arrHqBrandCd.length; i++) {
            // 파라미터 설정
            var params = {};
            params.gChk = false;
            params.hqBrandCd = arrHqBrandCd[i];
            params.hqBrandNm = arrHqBrandNm[i];

            // 추가기능 수행 : 파라미터
            $scope._addRow(params);
        }

        var chkHqBrandCd = "";
        var chkHqBrandNm = "";

        // 등록후 그리드에 브랜드 값 가져오기
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            chkHqBrandCd += $scope.flex.collectionView.items[i].hqBrandCd + ",";
            chkHqBrandNm += $scope.flex.collectionView.items[i].hqBrandNm + ",";
        }
        chkHqBrandCd = chkHqBrandCd.substring(0, chkHqBrandCd.length-1);
        chkHqBrandNm = chkHqBrandNm.substring(0, chkHqBrandNm.length-1);
        $("#lblChkHqBrandCd").text(chkHqBrandCd);
        $("#lblChkHqBrandNm").text(chkHqBrandNm);
    };
    // <-- //검색 호출 -->

    // 미적용 브랜드 이동(삭제)
    $scope.deleteHqBrand = function(){
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

        var chkHqBrandCd = "";
        var chkHqBrandNm = "";

        // 삭제하고 남은 그리드에 브랜드값 가져오기
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            chkHqBrandCd += $scope.flex.collectionView.items[i].hqBrandCd + ",";
            chkHqBrandNm += $scope.flex.collectionView.items[i].hqBrandNm + ",";
        }
        chkHqBrandCd = chkHqBrandCd.substring(0, chkHqBrandCd.length-1);
        chkHqBrandNm = chkHqBrandNm.substring(0, chkHqBrandNm.length-1);
        $("#lblChkHqBrandCd").text(chkHqBrandCd);
        $("#lblChkHqBrandNm").text(chkHqBrandNm);

        // 선택한값 추가하기
        var storeScope = agrid.getScope('searchNoUserHqBrandCtrl');
        storeScope._broadcast('searchNoUserHqBrandCtrl', null);
    };

}]);


/**
 *  미적용 관리브랜드 조회 그리드 생성
 */
app.controller('searchNoUserHqBrandCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchNoUserHqBrandCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 호출시 searchNoUserHqBrandCtrl 로 안쓰고 searchNoUserHqBrandTotalCtrl 로 따로 쓴 이유
    // 맨처음에 창띄울때 값을 1번만 담으려고
    // searchNoUserHqBrandCtrl 로 같이쓰게되면 미적용상품이동 할때 수정한 값에 최초 값이 엎어쳐짐으로 오른쪽 그리드가 수정이 안됨
    $scope.$on("searchNoUserHqBrandTotalCtrl", function(event, data) {
        if(data.empNo === "" || data.empNo === undefined || data.empNo === null) {
            $("#lblTitle").text(" [신규]");
            $("#lblChkHqBrandCd").text("");
            $("#lblChkHqBrandNm").text("");
        } else {
            $("#lblTitle").text(" [" + data.empNo + " " + data.empNm +"]");
            $("#lblChkHqBrandCd").text(data.hqBrandCd);
            $("#lblChkHqBrandNm").text(data.hqBrandNm);
        }

        // 미적용 브랜드
        var storeScope = agrid.getScope('searchNoUserHqBrandCtrl');
        storeScope.$apply(function() {
            storeScope._gridDataInit();
        });
        storeScope._broadcast('searchNoUserHqBrandCtrl', null);

        // 적용 브랜드
        var params = {};
        params.chkHqBrandCd = $("#lblChkHqBrandCd").text();
        params.chkHqBrandNm = $("#lblChkHqBrandNm").text();

        var storeScope2 = agrid.getScope('searchUserHqBrandCtrl');
        storeScope2.$apply(function() {
            storeScope2._gridDataInit();
        });
        storeScope2._broadcast('searchUserHqBrandCtrl', params);

        event.preventDefault();
    });

    // <-- 검색 호출 -->
    $scope.$on("searchNoUserHqBrandCtrl", function(event, data) {
        // 미적용 브랜드
        $scope.searchNoUserHqBrand();
        event.preventDefault();
    });

    $scope.searchNoUserHqBrand = function(){
        var params = {};
        params.chkHqBrandCd = $("#lblChkHqBrandCd").text();

        $scope._inquirySub("/base/store/emp/hq/getSearchNoUserHqBrandList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 적용 브랜드 이동(등록)
    $scope.registHqBrand = function(){
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

        var chkHqBrandCd = "";
        var chkHqBrandNm = "";

        // 선택한값 가져오기
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                chkHqBrandCd += $scope.flex.collectionView.items[i].hqBrandCd + ",";
                chkHqBrandNm += $scope.flex.collectionView.items[i].hqBrandNm + ",";
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
        params.chkHqBrandCd = chkHqBrandCd.substring(0, chkHqBrandCd.length-1);
        params.chkHqBrandNm = chkHqBrandNm.substring(0, chkHqBrandNm.length-1);

        // 선택한값 추가하기
        var storeScope = agrid.getScope('searchUserHqBrandCtrl');
        storeScope._broadcast('searchUserHqBrandCtrl', params);
    };

    //  적용
    $("#funcApply").click(function(e){
        var scope = agrid.getScope('hqEmpRegistCtrl');
        scope.hqEmpRegistInfo.hqBrandCd = $("#lblChkHqBrandCd").text();
        scope.hqEmpRegistInfo.hqBrandNm = $("#lblChkHqBrandNm").text();

        $scope.wjSearchUserHqBrandLayer.hide();
    });

}]);