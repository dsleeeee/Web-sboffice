/****************************************************************
 *
 * 파일명 : recpOriginReg.js
 * 설  명 : 원산지신규등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.27     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품조회 그리드 생성
 */
app.controller('recpOriginRegCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('recpOriginRegCtrl', $scope, $http, true));

    $scope._setComboData("srchRorProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        if(brandUseFg === "1") {
            $scope.hqBrandFgMap = new wijmo.grid.DataMap(userHqBrandCdComboList2, 'value', 'name');
        }else{
            var url                = '/base/prod/recpOrigin/recpOrigin/getBrandComboList.sb';
            var comboParams        = {};
            comboParams.hqOfficeCd = hqOfficeCd;
            $scope._queryCombo("map", null, "hqBrandFgMap", url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
        }
    };

    $scope.$on("recpOriginRegCtrl", function(event, data) {
        // 조회
        $scope.searchRecpOrigin();
        event.preventDefault();
    });

    // 조회 버튼
    $scope.search = function(){
        $scope.searchRecpOrigin();
    }
    
    // 조회
    $scope.searchRecpOrigin = function(){
        var params = {};
        params.recipesNm = $scope.recipesNm;
        params.orgplceNm = $scope.orgplceNm;

        if(brandUseFg === "1" && orgnFg === "HQ"){
          // 선택한 상품브랜드가 있을 때
          params.prodHqBrandCd = $scope.srchRorProdHqBrandCdCombo.selectedValue;

          // 선택한 상품브랜드가 없을 때('전체' 일때)
          if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
              var userHqBrandCd = "";
              for(var i=0; i < userHqBrandCdComboList.length; i++){
                  if(userHqBrandCdComboList[i].value !== null) {
                      userHqBrandCd += userHqBrandCdComboList[i].value + ","
                  }
              }
              params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
          }
        }

        $scope._inquirySub("/base/prod/recpOrigin/recpOrigin/getRecpOriginList.sb", params, function() {}, false);
    };

    // 추가
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.recipesCd="자동채번";
        params.recipesNm = "";
        params.orgplceNm = "";
        params.hqBrandCd = "선택";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 저장
    $scope.save = function() {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].recipesNm === "") {
                $scope._popMsg(messages["recpOrigin.recipesNmBlank"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].orgplceNm === "") {
                $scope._popMsg(messages["recpOrigin.orgplceNmBlank"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].recipesNm.length > 100) {
                $scope._popMsg(messages["recpOrigin.recipesNmMax"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].orgplceNm.length > 133) {
                $scope._popMsg(messages["recpOrigin.orgplceNmMax"]);
                return false;
            }
            if($scope.flex.collectionView.items[i].hqBrandCd == '0' || $scope.flex.collectionView.items[i].hqBrandCd == '선택') {
                $scope.flex.collectionView.items[i].hqBrandCd = null;
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/recpOrigin/getRecpOriginSave.sb", params, function(){
            
            // 리스트 재조회
            $scope.searchRecpOrigin();

            // 부모창 재조회
            var scopeMid = agrid.getScope('prodRecpOriginDetailCtrl');

            if(scopeMid.getSelectedProd() !== null){
                scopeMid._broadcast('prodRecpOriginDetailCtrl', scopeMid.getSelectedProd());
            }

            // 원산지 관리 tab > 재료 및 원산지 등록 리스트 재조회
            var scopeRegOrigin = agrid.getScope('recpOriginCtrl');
            scopeRegOrigin._broadcast('recpOriginCtrl');
        });
    };

    // 삭제
    $scope.del = function(){
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 저장
            $scope.save();
        });
    };

    //
    $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
        var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
        if (url) {
            comboUrl = url;
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
                            comboData.id    = "0";
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
                        comboData      = {};
                        comboData.id   = "0";
                        comboData.name = "선택";
                        comboArray.push(comboData);

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

}]);