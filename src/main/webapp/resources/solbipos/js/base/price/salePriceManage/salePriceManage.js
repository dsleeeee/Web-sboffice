/****************************************************************
 *
 * 파일명 : salePriceManage.js
 * 설  명 : 판매가관리(매장용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.13     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 판매가 선택 DropBoxDataMap
var saleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"}
];
var saleAmtOptionFgStore = [
    {"name":"매장판매가","value":"S"}
];
// 단위 DropBoxDataMap
var unitFg = [
    {"name":"1원 단위","value":"1"},
    {"name":"100원 단위","value":"100"},
    {"name":"1,000원 단위","value":"1000"}
];
// 반올림 DropBoxDataMap
var modeFg = [
    {"name":"반올림","value":"0"},
    {"name":"절상","value":"1"},
    {"name":"절하","value":"2"}
];

/**
 *  판매가관리(매장용) 그리드 생성
 */
app.controller('salePriceManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('salePriceManageCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 매장일때만
        if(hqOfficeCd != "00000") {
            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];

                    // 체크박스
                    if (col.binding === "gChk" || col.binding === "saleUprc") {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (item[("prcCtrlFg")] === 'H') {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            item[("gChk")] = false; // 전체 체크시 오류

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            });
        }
    };

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);
    if(hqOfficeCd == "00000") {
        $scope._setComboData("saleAmtOption", saleAmtOptionFgStore);
    } else {
        $scope._setComboData("saleAmtOption", saleAmtOptionFg);
    }
    $scope._setComboData("changeUnit", unitFg);
    $scope._setComboData("changeMode", modeFg);

    // <-- 검색 호출 -->
    $scope.$on("salePriceManageCtrl", function(event, data) {
        $scope.searchSalePriceManage();
        event.preventDefault();
    });

    $scope.searchSalePriceManage = function(){
        var params = {};
        params.prodClassCd = $scope.prodClassCd;
        params.listScale = $scope.listScaleCombo.text;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;

        $scope._inquirySub('/base/price/salePriceManage/salePriceManage/getSalePriceManageList.sb', params, function() {}, false);
    };
    // <-- //검색 호출 -->

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
                        $scope.prodClassCd = prodClassCd;
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

    // 상품정보
    $scope.prodInfo;
    $scope.setProdInfo = function(data){
        $scope.prodInfo = data;
    };
    $scope.getProdInfo = function(){
        return $scope.prodInfo;
    };

    // 일괄적용 버튼 클릭
    // 매장판매가 일괄적용시, 입력한 매장판매가를 적용시킴.
    // 본사판매가 일괄적용시, 조회된 본사판매가를 적용시킴.
    $scope.changeAmt = function() {
        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var saleAmtOption = $scope.prodInfo.saleAmtOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputSaleRate").val() === ""){
            $scope._popMsg("변경비율을 입력해 주세요.");
            return false;
        }

        var newSaleAmt = 0;

        // 변경 판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newSaleAmt = 0;
                if(saleAmtOption === "S"){ // 매장
                    newSaleAmt = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputSaleRate").val())/100);
                }else{ // 본사
                    newSaleAmt = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputSaleRate").val())/100);
                }

                $scope.flex.collectionView.items[i].saleUprc = $scope.calChangeAmt(newSaleAmt);
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 변경판매가 계산
    $scope.calChangeAmt = function(amt){
        var ChangeAmt = 0;
        var unit = $scope.prodInfo.changeUnit;
        var mode = $scope.prodInfo.changeMode;

        if(mode === "0"){ // 반올림
            ChangeAmt = Math.round(amt/(unit*10))*(unit*10);
        }else if(mode === "1"){ //절상
            ChangeAmt = Math.ceil(amt/(unit*10))*(unit*10);
        }else if(mode === "2"){ //절하
            ChangeAmt = Math.floor(amt/(unit*10))*(unit*10);
        }

        return ChangeAmt;
    };

    // 저장
    $scope.saveProdPrice = function(){
        // 파라미터 설정
        var params = new Array();
        var saleAmtOption = $scope.prodInfo.saleAmtOption;

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {
                // PRC_CTRL_FG 가격관리구분 S인 상품만 수정가능
                if ($scope.flex.collectionView.items[i].prcCtrlFg === "H") {
                    $scope._popMsg(messages["salePriceManage.prcCtrlFgHqBlank"]); // 가격관리구분이 '본사'인 상품은 수정할 수 없습니다.
                    return false;
                }
            }
        }

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {
                // 변경판매가 숫자만 입력가능하도록
                if($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
                    $scope._popMsg(messages["salePriceManage.saleUprcBlank"]); // 변경판매가를 입력하세요.
                    return false;
                } else {
                    // 소수점 입력안됨
                    var numchkexp3 = /^\d*[.]\d*$/;
                    var numchkexp4 = /^-\d*[.]\d*$/;
                    if(numchkexp3.test($scope.flex.collectionView.items[i].saleUprc) == true) {
                        $scope.flex.collectionView.items[i].saleUprc = "";
                        $scope._popMsg(messages["salePriceManage.saleUprcPointNoInChk"]); // 변경판매가는 소수점을 입력할 수 없습니다. <br/> 숫자만(정수9자리) 입력해주세요.
                        return false;
                    } else if(numchkexp4.test($scope.flex.collectionView.items[i].saleUprc) == true) {
                        $scope.flex.collectionView.items[i].saleUprc = "";
                        $scope._popMsg(messages["salePriceManage.saleUprcPointNoInChk"]); // 변경판매가는 소수점을 입력할 수 없습니다. <br/> 숫자만(정수9자리) 입력해주세요.
                        return false;
                    } else {
                        // 숫자만 입력
                        var numchkexp = /[^0-9]/g;
                        if (numchkexp.test($scope.flex.collectionView.items[i].saleUprc)) { // 음수
                            var numchkexp2 = /^-[0-9]/g;
                            if (numchkexp2.test($scope.flex.collectionView.items[i].saleUprc)) {
                                // $scope.flex.collectionView.items[i].storeCd = $("#searchStoreCd").val();
                                params.push($scope.flex.collectionView.items[i]);
                            } else if((numchkexp2.test($scope.flex.collectionView.items[i].saleUprc) == false)){
                                $scope.flex.collectionView.items[i].saleUprc = "";
                                $scope._popMsg(messages["salePriceManage.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        } else if($scope.flex.collectionView.items[i].saleUprc >= 1000000000){ // 양수 max값
                            $scope.flex.collectionView.items[i].saleUprc = "";
                            $scope._popMsg(messages["salePriceManage.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        } else {
                            // $scope.flex.collectionView.items[i].storeCd = $("#searchStoreCd").val();
                            params.push($scope.flex.collectionView.items[i]);
                        }
                    }
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/price/salePriceManage/salePriceManage/getSalePriceManageSave.sb', params, function(){
            $scope.searchSalePriceManage();
        });
    };

}]);