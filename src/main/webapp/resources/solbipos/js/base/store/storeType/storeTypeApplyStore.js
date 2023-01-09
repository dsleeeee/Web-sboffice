/****************************************************************
 *
 * 파일명 : storeTypeApplyStore.js
 * 설  명 : 매장타입관리 - 매장타입 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.02     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 시 VALUE
var Hh = [24];
for(i =0 ; i < 24; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    Hh[i] = {"name":timeVal,"value":timeVal}
}

// 매장타입관리 - 매장타입 매장적용 팝업
app.controller('storeTypeApplyStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeTypeApplyStoreCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchPopStoreType", storeTypeList);
    $scope._setComboData("srchPopStoreGroup", storeGroupList);
    $scope._setComboData("srchPopSysStatFg", sysStatFg);
    $scope._setComboData("srchPopStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드

    // 적용일시
    var applyDay = wcombo.genDateVal("#applyDay", gvStartDate);
    $scope._setComboData("applyDayHhCombo", Hh);

    // 적용일시 셋팅
    $scope.isCheckedApplyDay = false;
    $("input:checkbox[id='chkApplyDay']").prop("checked", false);
    $("#applyDay").attr("disabled", true);
    $("#applyDay").css('background-color', '#F0F0F0');
    $("#applyDayHh").attr("disabled", true);
    $("#applyDayHh").css('background-color', '#F0F0F0');
    
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "storeGroupNms") {

                    var item = s.rows[e.row].dataItem;
                    if (item.storeGroupNms === "()") {
                        e.cell.innerHTML = "";
                    }
                }
            }
        });
    };

    $scope.$on("storeTypeApplyStoreCtrl", function(event, data) {

        // 매장 조회
        $scope.searchPopStore();
        event.preventDefault();
        
    });

    // 매장 조회
    $scope.searchPopStore = function () {

        var params = {};
        params.storeTypeCd = $scope.srchPopStoreTypeCombo.selectedValue;
        params.storeGroupCd = $scope.srchPopStoreGroupCombo.selectedValue;
        params.storeCd = $("#srchPopStoreCd").val();
        params.storeNm = $("#srchPopStoreNm").val();
        params.sysStatFg = $scope.srchPopSysStatFgCombo.selectedValue;

        if(brandUseFg === "1" && orgnFg === "HQ"){

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchPopStoreHqBrandCdCombo.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        $scope._inquirySub("/base/store/storeType/storeType/getStoreTypeApplyStoreList.sb", params, function () {

        });
    };

    // 매장타입 매장적용
    $scope.storeTypeApply = function () {

        $scope.flex.collectionView.commitEdit();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["storeType.storeChk.msg"]); // 적용할 매장을 선택하세요.
            return false;
        }

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {

            var item =  $scope.flex.collectionView.items[i];

            if(item.gChk === true) {

                var obj = {};
                obj.storeCd = item.storeCd;
                obj.commentRemark = "TB_HQ_STORE_TYPE_APP 에 직접 등록";

                if ($("#chkApplyDay").is(":checked")) {
                    obj.applyDt = wijmo.Globalize.format(applyDay.value, 'yyyyMMdd') + $scope.applyDayHh + "0000";
                    obj.applyProcFg = "0"; // 미처리(스케쥴러처리)
                }else{
                    obj.applyProcFg = "5"; // 미처리(즉시처리)
                }

                /*if(storeTypeApplyEnvstVal === "1") { // 매장타입판매가설정(1107) 미사용시, 기본 applyFg = 0

                    if ($("#chkSaleUprcApply").is(":checked")) {
                        obj.applyFg = "1";
                    } else {
                        obj.applyFg = "0";
                    }
                }else{
                    obj.applyFg = "0";
                }*/

                obj.applyFg = "1"; // 매장타입판매가설정(1107) 기본 사용(applyFg = 1)으로 처리 - 2021.12.27

                params.push(obj);
            }
        }

        $scope._popConfirm(messages["storeType.storeApply.msg"], function() {

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/store/storeType/storeType/saveStoreTypeApplyStore.sb", params, function () {

                $scope.storeTypeApplyStoreLayer.hide(true);
                $scope.close();

            });

        });

    };

    // 닫기
    $scope.close = function () {

        $scope.srchPopStoreTypeCombo.selectedIndex = 0;
        $scope.srchPopStoreGroupCombo.selectedIndex = 0;
        $("#srchPopStoreCd").val("");
        $("#srchPopStoreNm").val("");
        $scope.srchPopSysStatFgCombo.selectedIndex = 0;

        // 적용일시
        $scope.isCheckedApplyDay = false;
        $("input:checkbox[id='chkApplyDay']").prop("checked", false);
        $("#applyDay").attr("disabled", true);
        $("#applyDay").css('background-color', '#F0F0F0');
        $("#applyDayHh").attr("disabled", true);
        $("#applyDayHh").css('background-color', '#F0F0F0');

        // 판매가 같이적용
        $("inpt:checkbox[id='chkSaleUprcApply']").prop("checked", false);

    };

    // 적용일시 입력 사용/미사용 체크박스
    $scope.isChkApplyDay = function () {
        if($scope.isCheckedApplyDay){
            $("#applyDay").attr("disabled", false);
            $("#applyDay").css('background-color', '#FFFFFF');
            $("#applyDayHh").attr("disabled", false);
            $("#applyDayHh").css('background-color', '#FFFFFF');
        }else{
            $("#applyDay").attr("disabled", true);
            $("#applyDay").css('background-color', '#F0F0F0');
            $("#applyDayHh").attr("disabled", true);
            $("#applyDayHh").css('background-color', '#F0F0F0');
        }
    };

}]);