/****************************************************************
 *
 * 파일명 : prodImgStoreReg.js
 * 설  명 : 본사상품 이미지 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.16     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('prodImgStoreRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('prodImgStoreRegCtrl', $scope, $http, false));

    $scope._setComboData("srchSysStatFg", sysStatFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("prodImgStoreRegCtrl", function(event, data) {

        // 이미지구분 체크박스 기본 체크로 설정
        $("input:checkbox[id='chkProd']").prop("checked", true);
        $("input:checkbox[id='chkKiosk']").prop("checked", true);
        $("input:checkbox[id='chkDid']").prop("checked", true);
        $("input:checkbox[id='chkHash']").prop("checked", true);

        // 매장조회
        $scope.searchStore();
        event.preventDefault();

    });

    // 매장조회
    $scope.searchStore = function () {

        var params = {};
        params.storeCd = $("#srchStoreCd").val();
        params.storeNm = $("#srchStoreNm").val();
        params.sysStatFg = $scope.srchSysStatFgCombo.selectedValue;

        $scope._inquirySub("/base/prod/prodImg/prodImg/getStoreList.sb", params, function () {});
    };

    // 조회
    $scope.btnSearchStore = function(){

        // 매장조회
        $scope.searchStore();
    };

    // 적용
    $scope.btnRegImgStore = function(){

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["prodImg.chkStore.item"]); // 매장을 선택하세요.
            return false;
        }

        // 선택한 이미지구분이 있는지 체크
        var chkImgFgCount = 0;
        var chkImgFgCd = "";

        if($("#chkProd").is(":checked")){
            chkImgFgCount++;
            chkImgFgCd += $("#chkProd").val() + ",";
        }
        if($("#chkKiosk").is(":checked")){
            chkImgFgCount++;
            chkImgFgCd += $("#chkKiosk").val() + ",";
        }
        if($("#chkDid").is(":checked")){
            chkImgFgCount++;
            chkImgFgCd += $("#chkDid").val() + ",";
        }
        if($("#chkHash").is(":checked")){
            chkImgFgCount++;
            chkImgFgCd += $("#chkHash").val() + ",";
        }

        if(chkImgFgCount === 0){
            $scope._popMsg(messages["prodImg.chkImgFg.item"]); // 이미지구분을 선택하세요.
            return false;
        }

        if(chkImgFgCd != "" && chkImgFgCd != null){
            chkImgFgCd = chkImgFgCd.substr(0, chkImgFgCd.length - 1);
        }

        // 본사상품이미지를 매장에 적용하시겠습니까?(기존의 매장상품이미지는 삭제됩니다.)
        $scope._popConfirm(messages["prodImg.delStoreProdImg.msg"], function () {

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var item = $scope.flex.collectionView.items[i];

                if (item.gChk === true) {
                    var obj = {};
                    obj.storeCd = item.storeCd;
                    obj.imgFg = chkImgFgCd;

                    params.push(obj);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodImg/prodImg/prodImgToStore.sb", params, function () {

                $scope.prodImgStoreRegLayer.hide(true);

            });
        });
    };

    // 닫기
    $scope.close = function () {

        $("#srchStoreCd").val("");
        $("#srchStoreNm").val("");
        $scope.srchSysStatFgCombo.selectedIndex = 0;
    };

}]);