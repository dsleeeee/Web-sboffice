/****************************************************************
 *
 * 파일명 : prodBatchChange2.js
 * 설  명 : 상품정보일괄변경2 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.16     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품정보일괄변경 그리드 생성
 */
app.controller('prodBatchChange2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodBatchChange2Ctrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox2', gvListScaleBoxData);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchHqBrand", brandList);
    $scope._setComboData("srchHqBrandChg", brandList.slice(1, brandList.length));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.regFgDataMap = new wijmo.grid.DataMap(regFgData, 'value', 'name'); // 상품등록구분
        $scope.brandDataMap = new wijmo.grid.DataMap(brandList.slice(1, brandList.length), 'value', 'name'); // 브랜드

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    $scope.$on("prodBatchChange2Ctrl", function(event, data) {
        // 상품목록조회
        $scope.searchProd();
        event.preventDefault();
    });
    
    // 상품목록조회
    $scope.searchProd = function(){
        var params = {};
        params.hqBrandCd = $scope.hqBrandCd;
        params.listScale = $scope.listScale2;

        $scope._inquiryMain("/base/prod/prodBatchChange/prodBatchChange/getProdBatchChangeList.sb", params, function() {

            // 프랜차이즈매장은 본사에서 등록한 상품 선택 불가
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {

                var grid = wijmo.Control.getControl("#wjGridProdBatchChange2");
                var rows = grid.rows;

                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    var item = $scope.flex.collectionView.items[i];
                    if (item.regFg === "H") {
                        item.gChk = false;
                        rows[i].isReadOnly = true;
                    }
                }
            }
        }, false);
    };

    // 상품분류정보 팝업
    $scope.popProdClass = function(type) {
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
                        if(type === "srch") {
                            $scope.prodClassCd = prodClassCd;
                            $scope.prodClassCdNm = response.data.data;
                        }else{
                            $scope.prodClassCdChg = prodClassCd;
                            $scope.prodClassCdNmChg = response.data.data;
                        }
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.deleteProdClass = function(type){
        if(type === "srch") {
            $scope.prodClassCd = "";
            $scope.prodClassCdNm = "";
        }else{
            $scope.prodClassCdChg = "";
            $scope.prodClassCdNmChg = "";
        }
    };

    // 일괄적용
    $scope.prodBatchChange = function(chgGubun) {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 일괄적용할 상품분류를 선택하세요.
        if(chgGubun === "srchProdClassChg"){
            if($scope.prodClassCdChg === null || $scope.prodClassCdChg === undefined ||  $scope.prodClassCdChg === ""){
                s_alert.pop(messages["prodBatchChange.prodClassChk"]);
                return;
            }
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // 브랜드
                if(chgGubun == "srchHqBrandChg") {
                    $scope.flex.collectionView.items[i].hqBrandCd = $scope.hqBrandCdChg;
                }
                // 상품분류
                else if(chgGubun == "srchProdClassChg") {
                    $scope.flex.collectionView.items[i].pathNm = $scope.prodClassCdNmChg;
                    $scope.flex.collectionView.items[i].prodClassCd = $scope.prodClassCdChg;
                }
            }
        }
        $scope.flex.refresh();
    };

    // 저장
    $scope.prodBatchSave = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 프랜 매장일때만
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    if($scope.flex.collectionView.items[i].gChk) {
                        // REG_FG 상품등록구분 S인 상품만 수정가능
                        if ($scope.flex.collectionView.items[i].regFg === "H") {
                            $scope._popMsg(messages["prodBatchChange.regFgHqBlank"]); // 상품등록구분이 '본사'인 상품은 수정할 수 없습니다.
                            return false;
                        }
                    }
                }
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = "U";
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prodBatchChange/prodBatchChange/getProdBatchChange2Save.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        // 상품목록조회
        $scope.searchProd();
    };

}]);