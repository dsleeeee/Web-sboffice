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

        // 매장에 적용하시겠습니까? 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["storeType.storeApply.msg"], function() {

            // 작업내역 로딩 팝업 오픈
            $scope.loadingPopup(true);

            // 매장적용할 전체 매장수 셋팅
            var totCnt =  params.length;
            $("#totalRows").html(totCnt);

            // 10개씩 나눠 진행시, 총 차수 파악
            var turnCnt= Math.ceil(params.length/10);

            function delay(x, y){
                return new Promise(function(resolve, reject){

                    // 10개씩 나눠 매장적용, 마지막 차수는 남은거 전부 처리
                    var params2 = (y === turnCnt) ? params.slice(x, totCnt) : params.slice(x, y * 10);
                    console.log(JSON.stringify(params2));

                    //가상로그인 session 설정
                    var sParam = {};
                    if(document.getElementsByName('sessionId')[0]){
                        sParam['sid'] = document.getElementsByName('sessionId')[0].value;
                    }

                    // ajax 통신 설정
                    $http({
                        method : 'POST', //방식
                        url    : '/base/store/storeType/storeType/saveStoreTypeApplyStore.sb', /* 통신할 URL */
                        data   : params2, /* 파라메터로 보낼 데이터 : @requestBody */
                        params : sParam,  /* 파라메터로 보낼 데이터 : request.getParameter */
                        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
                    }).then(function successCallback(response) {
                        if ($scope._httpStatusCheck(response, true)) {
                            // this callback will be called asynchronously
                            // when the response is available

                            // 진행 완료된 매장 숫자 변경
                            $("#progressCnt").html(x + 10);

                            // 매장타입 매장적용 다음 차수 진행
                            startStoreTypeApply(x + 10, y + 1);
                        }
                    }, function errorCallback(response) {
                        // 로딩팝업 hide
                        $scope.loadingPopup(false);
                        // called asynchronously if an error occurs
                        // or server returns response with an error status.
                        if (response.data.message) {
                            $scope._popMsg(response.data.message);
                        } else {
                            $scope._popMsg(messages['cmm.error']);
                        }
                        return false;
                    }).then(function () {

                    });
                    resolve();
                });
            };

            async function startStoreTypeApply(x, y) {
                if(totCnt > x){
                    await delay(x, y);
                }else{
                    $scope.loadingPopup(false); // 작업내역 로딩 팝업 닫기
                    $scope.storeTypeApplyStoreLayer.hide(true); // 팝업 닫기
                    $scope.close();
                }
            };

            // 매장타입 매장적용 시작
            startStoreTypeApply(0, 1); // 매장적용 시작배열 수, 차수
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

    // 작업내역 로딩 팝업
    $scope.loadingPopup = function (showFg) {
      if (showFg) {
          // 팝업내용 동적 생성
          var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
          innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 매장 완료...</div>';
          innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
          // html 적용
          $scope._loadingPopup.content.innerHTML = innerHtml;
          // 팝업 show
          $scope._loadingPopup.show(true);
      } else {
          $scope._loadingPopup.hide(true);
      }
    };

}]);