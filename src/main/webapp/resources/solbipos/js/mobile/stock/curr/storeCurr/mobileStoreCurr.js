/****************************************************************
 *
 * 파일명 : mobileStoreCurr.js
 * 설  명 : (모바일) 재고현황 > 매장현재고 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.07.23     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 현재고현황 그리드 controller */
app.controller('mobileStoreCurrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileStoreCurrCtrl', $scope, $http, true));

    $scope._setComboData("srchUnitFg", [
        {"name": messages["storeCurr.unitStockFg"], "value": "0"},
        {"name": messages["storeCurr.unitOrderFg"], "value": "1"}
    ]);

    $scope._setComboData("srchWeightFg", [
        {"name": messages["storeCurr.weightFg0"], "value": "0"},
        {"name": messages["storeCurr.weightFg1"], "value": "1"}
    ]);

    $scope._setComboData("srchSafeStockFg", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["storeCurr.safeStockFg0"], "value": "0"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        var comboParams         = {};
        comboParams.nmcodeGrpCd = "093";
        var url                 = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
        // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
        $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("mobileStoreCurrCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mobileStoreCurrCtrl", function (event, data) {
        $scope.searchMobileStoreCurrList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 현재고현황 리스트 조회
    $scope.searchMobileStoreCurrList = function () {
        // 파라미터
        var params     = {};
        params.prodCd       = $scope.prodCd;
        params.prodNm       = $scope.prodNm;
        params.barcdCd      = $scope.barcdCd;
        params.unitFg       = $scope.unitFg;
        params.prodClassCd  = $scope.prodClassCd;
        params.safeStockFg	= $scope.safeStockFg;
        params.vendrCd      = $("#mobileStoreCurrSelectVendrCd").val();
        params.storeCd      = $("#mobileStoreCurrSelectStoreCd").val(); // 매장코드
        params.storageCd    = $("#mobileStoreCurrSelectStorageCd").val(); // 창고코드

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/mobile/stock/curr/storeCurr/mobileStoreCurr/list.sb", params, function (){
            gridShowMsgNoData("mobileStoreCurr", $scope.flex, "Y");
        }, true);
    };


    // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
    // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
    // comboId : combo 생성할 ID
    // gridMapId : grid 에서 사용할 Map ID
    // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
    // params : 데이터 조회할 url에 보낼 파라미터
    // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
    // callback : queryCombo 후 callback 할 함수
    $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
        var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
        if (url) {
            comboUrl = url;
        }

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : comboUrl, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list       = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
                        comboArray = [];
                        if (option === "A") {
                            comboData.name  = messages["cmm.all"];
                            comboData.value = "";
                            comboArray.push(comboData);
                        } else if (option === "S") {
                            comboData.name  = messages["cmm.select"];
                            comboData.value = "";
                            comboArray.push(comboData);
                        }

                        for (var i = 0; i < list.length; i++) {
                            comboData       = {};
                            comboData.name  = list[i].nmcodeNm;
                            comboData.value = list[i].nmcodeCd;
                            comboArray.push(comboData);
                        }
                        $scope._setComboData(comboId, comboArray);
                    }

                    if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
                        comboArray = [];
                        for (var i = 0; i < list.length; i++) {
                            comboData      = {};
                            comboData.id   = list[i].nmcodeCd;
                            comboData.name = list[i].nmcodeNm;
                            comboArray.push(comboData);
                        }
                        $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
                    }
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            if (typeof callback === 'function') {
                $timeout(function () {
                    callback();
                }, 10);
            }
        });
    };


    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope          = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd    = scope.getSelectedClass();
                var params         = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function (response) {
                        $scope.prodClassCd   = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    //상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
    $scope.isChkProdClassDisplay = function(){
        var columns = $scope.flex.columns;

        for(var i=0; i<columns.length; i++){
            if(columns[i].binding === 'prodClassNm'){
                $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
            }
        }
    };

    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileStoreCurrSelectVendrShow = function () {
        $scope._broadcast('mobileStoreCurrSelectVendrCtrl');
    };

//창고선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileStorageCurrSelectStorageShow = function () {
        $scope._broadcast('mobileStorageCurrSelectStorageCtrl');
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileStoreCurrSelectStoreShow = function () {
        $scope._broadcast('mobileStoreCurrSelectStoreCtrl');
    };

}]);
