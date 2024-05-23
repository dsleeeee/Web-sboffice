/****************************************************************
 *
 * 파일명 : prodImgBarrierFreeStoreRegist.js
 * 설  명 : 베리어프리-이미지관리 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.05.17     김설아      1.0
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
 *  베리어프리-이미지관리 매장적용 팝업 조회 그리드 생성
 */
app.controller('prodImgBarrierFreeStoreRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodImgBarrierFreeStoreRegistCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("sysStatFgCombo", sysStatFgComboData); // 매장상태구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgComboData, 'value', 'name'); // 매장상태구분
    };

    // <-- 검색 호출 -->
    $scope.$on("prodImgBarrierFreeStoreRegistCtrl", function(event, data) {

        // 이미지구분 체크박스 기본 체크로 설정
        $("input:checkbox[id='chkKioskGreen']").prop("checked", true);
        $("input:checkbox[id='chkKioskYellow']").prop("checked", true);
        $("input:checkbox[id='chkKioskWhite']").prop("checked", true);

        $scope.searchProdImgBarrierFreeStoreRegist();
        event.preventDefault();
    });

    $scope.searchProdImgBarrierFreeStoreRegist = function(){
        var params = {};

        $scope._inquiryMain("/base/prod/prodImgBarrierFree/prodImgBarrierFreeStoreRegist/getProdImgBarrierFreeStoreRegistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

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
            $scope._popMsg(messages["cmm.require.selectStore"]); // 매장을 선택해 주세요.
            return false;
        }

        // 선택한 이미지구분이 있는지 체크
        var chkImgFgCount = 0;
        var chkImgFgCd = "";

        if($("#chkKioskGreen").is(":checked")){
            chkImgFgCount++;
            chkImgFgCd += $("#chkKioskGreen").val() + ",";
        }
        if($("#chkKioskYellow").is(":checked")){
            chkImgFgCount++;
            chkImgFgCd += $("#chkKioskYellow").val() + ",";
        }
        if($("#chkKioskWhite").is(":checked")){
            chkImgFgCount++;
            chkImgFgCd += $("#chkKioskWhite").val() + ",";
        }

        if(chkImgFgCount === 0){
            $scope._popMsg(messages["prodImgBarrierFreeStoreRegist.chkImgFg.item"]); // 이미지구분을 선택하세요.
            return false;
        }

        if(chkImgFgCd != "" && chkImgFgCd != null){
            chkImgFgCd = chkImgFgCd.substr(0, chkImgFgCd.length - 1);
        }

        // 본사상품이미지를 매장에 적용하시겠습니까?(기존의 매장상품이미지는 삭제됩니다.)
        $scope._popConfirm(messages["prodImgBarrierFreeStoreRegist.delStoreProdImg.msg"], function () {

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
            $scope._save("/base/prod/prodImgBarrierFree/prodImgBarrierFreeStoreRegist/getProdImgBarrierFreeStoreRegistSave.sb", params, function () {
                // 팝업 닫기
                $scope.close();
            });
        });
    };

    // 닫기
    $scope.close = function(){
        $scope.storeCd = "";
        $scope.storeNm = "";
        $scope.srchSysStatFgCombo.selectedIndex = 0;

        $scope.wjProdImgBarrierFreeStoreRegistLayer.hide();
    };

}]);