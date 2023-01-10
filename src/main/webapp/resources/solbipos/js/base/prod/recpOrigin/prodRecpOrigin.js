/****************************************************************
 *
 * 파일명 : prodRecpOrigin.js
 * 설  명 : 상품-원산지관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.03.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부
var useYnComboData = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

// 사용여부
var recpOriginUseYnComboData = [
    {"name":"전체","value":""},
    {"name":"등록","value":"Y"},
    {"name":"미등록","value":"N"}
];

/**
 *  상품-원산지관리 그리드 생성
 */
app.controller('prodRecpOriginCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodRecpOriginCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnCombo", useYnComboData); // 사용여부
    $scope._setComboData("recpOriginUseYnCombo", recpOriginUseYnComboData); // 원산지등록 사용여부
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    $scope.isChecked = true;

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;

        if(brandUseFg === "1"){
            $scope.hqBrandFgMap = new wijmo.grid.DataMap(userHqBrandCdComboList2, 'value', 'name');
        }else{
            var url                = '/base/prod/recpOrigin/recpOrigin/getBrandComboList.sb';
            var comboParams        = {};
            comboParams.hqOfficeCd = hqOfficeCd;
            $scope._queryCombo("map", null, "hqBrandFgMap", url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
        }

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 재료코드
                if (col.binding === "recipesCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 상품코드
                if (col.binding === "prodCd") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 상품코드 클릭시 상세정보 조회
                if ( col.binding === "prodCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = {};
                    params.prodCd = selectedRow.prodCd;
                    params.prodNm = selectedRow.prodNm;
                    params.hqBrandCd = selectedRow.hqBrandCd;

                    if(selectedRow.hqBrandCd != selectedRow.hqBrandCdCombo) {
                        $scope._popMsg(messages["prodRecpOrigin.hqBrandCdBlank"]); // 브랜드를 변경하셨습니다. 저장 후 다시 선택해주세요.
                        return false;
                    }

                    var storeScope = agrid.getScope('prodRecpOriginDetailCtrl');
                    storeScope._broadcast('prodRecpOriginDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("prodRecpOriginCtrl", function(event, data) {
        $scope.searchProdRecpOrigin();
        event.preventDefault();
    });

    $scope.searchProdRecpOrigin = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
        params.chkDt = $scope.isChecked;
        params.useYn = $scope.useYn;
        params.recpOriginUseYn = $scope.recpOriginUseYn;

        if(brandUseFg === "1" && orgnFg === "HQ"){

          // 선택한 상품브랜드가 있을 때
          params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

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

        $scope._inquiryMain("/base/prod/recpOrigin/prodRecpOrigin/getProdRecpOriginList.sb", params, function() {
            
            // 등록 재료 리스트 초기화
            $scope.$apply(function() {
                var storeScope = agrid.getScope('prodRecpOriginDetailCtrl');
                storeScope._gridDataInit();
                storeScope._broadcast('prodRecpOriginDetailCtrl', null);
            });

            // 미등록 재료 리스트 초기화
            $scope.$apply(function() {
                var regSope = agrid.getScope('prodRecpOriginRegCtrl');
                regSope._gridDataInit();
                regSope._broadcast('prodRecpOriginRegCtrl', null);

                // 재료명, 원사지명 조회조건 textbox 초기화
                regSope.reset();
            });

        }, false);
    };

    $scope.searchProdRecpOrigin2 = function(){

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
        params.chkDt = $scope.isChecked;
        params.useYn = $scope.useYn;
        params.recpOriginUseYn = $scope.recpOriginUseYn;
        $scope._inquiryMain("/base/prod/recpOrigin/prodRecpOrigin/getProdRecpOriginList.sb", params, function() {});
        }
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

    // <-- 저장 -->
    // 현재 A0001 본사만 사용되게 로직 구성됨
    $scope.prodRecpOriginSave = function() {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/recpOrigin/prodRecpOrigin/getProdRecpOriginSave.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchProdRecpOrigin();
    };
    // <-- //저장 -->

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
    
    // 원산지신규등록 팝업
    $scope.openOriginReg = function () {
        $scope.wjRecpOriginRegLayer.show(true);
        $scope._broadcast('recpOriginRegCtrl');
    }

}]);


/**
 *  재료 및 원산지 등록 그리드 생성
 */
app.controller('prodRecpOriginDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodRecpOriginDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("prodRecpOriginDetailCtrl", function(event, data) {
        $scope.setSelectedProd(data);

        if(!$.isEmptyObject($scope.selectedProd) ) {
            $("#lblProd").text("[" + $scope.selectedProd.prodCd + "] " + $scope.selectedProd.prodNm);
            $scope.searchProdRecpOriginDetail();

            // 미등록 재료 조회
            var scope = agrid.getScope("prodRecpOriginRegCtrl");
            scope._broadcast('prodRecpOriginRegCtrl', data);

        }else{
            $("#lblProd").text("");
        }
        event.preventDefault();
    });

    $scope.searchProdRecpOriginDetail = function(){
        var params = {};
        params.prodCd = $scope.selectedProd.prodCd;
        params.hqBrandCd = $scope.selectedProd.hqBrandCd;

        $scope._inquirySub("/base/prod/recpOrigin/prodRecpOrigin/getProdRecpOriginDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedProd;
    $scope.setSelectedProd = function(store) {
        $scope.selectedProd = store;
    };
    $scope.getSelectedProd = function(){
        return $scope.selectedProd;
    };

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){

        if(!$.isEmptyObject($scope.selectedProd) ) {
            $scope._popConfirm(messages["cmm.choo.delete"], function() {
                for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                    var item = $scope.flex.collectionView.items[i];

                    if(item.gChk) {
                        $scope.flex.collectionView.removeAt(i);
                    }
                }
                // 저장
                $scope.delSave();
            });

        }else{
            $scope._popMsg(messages["prodRecpOrigin.prodCdBlank"]); // 상품코드를 선택해주세요.
            return false;
        }
    };

    $scope.delSave = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            $scope.flex.collectionView.itemsRemoved[i].prodCd = $scope.selectedProd.prodCd;
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/prodRecpOriginAdd/getProdRecpOriginAddSave.sb", params, function(){
            // 다시 순번 저장
            $scope.saveSave();

            // 등록 재료 조회
            $scope.searchProdRecpOriginDetail();

            // 미등록 재료 조회
            var scope = agrid.getScope("prodRecpOriginRegCtrl");
            scope._broadcast('prodRecpOriginRegCtrl', $scope.selectedProd);
        });
        var scope = agrid.getScope('prodRecpOriginCtrl');
        scope.searchProdRecpOrigin2();
    };
    // <-- //그리드 행 삭제 -->

    // 위로 옮기기 버튼
    $scope.rowMoveUp = function() {
        var movedRows = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (i > 0 && item.gChk) {
                if (!$scope.flex.collectionView.items[i - 1].gChk) {
                    movedRows = i - 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };

    // 아래로 옮기기 버튼
    $scope.rowMoveDown = function() {
        var movedRows = 0;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
                if (!$scope.flex.collectionView.items[i + 1].gChk) {
                    movedRows = i + 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };

    // <-- 저장 -->
    $scope.save = function() {

        if(!$.isEmptyObject($scope.selectedProd) ) {
            $scope._popConfirm(messages["cmm.choo.save"], function() {
                // 순번 저장
                $scope.saveSave();
            });

        }else{
            $scope._popMsg(messages["prodRecpOrigin.prodCdBlank"]); // 상품코드를 선택해주세요.
            return false;
        }
    };

    $scope.saveSave = function () {
        var recpSeq = 1;

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].status = "U";
            $scope.flex.collectionView.items[i].prodCd = $scope.selectedProd.prodCd;
            $scope.flex.collectionView.items[i].recpSeq = recpSeq;
            params.push($scope.flex.collectionView.items[i]);
            recpSeq++;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/prodRecpOriginAdd/getProdRecpOriginAddSave.sb", params, function(){

            // 등록 재료 조회
            $scope.searchProdRecpOriginDetail();

            // 미등록 재료 조회
            var scope = agrid.getScope("prodRecpOriginRegCtrl");
            scope._broadcast('prodRecpOriginRegCtrl', $scope.selectedProd);
        });
        var scope = agrid.getScope('prodRecpOriginCtrl');
        scope.searchProdRecpOrigin2();
    };
    // <-- //저장 -->

}]);


/**
 * 재료 및 원산지 미등록 그리드 생성
 */
app.controller('prodRecpOriginRegCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodRecpOriginRegCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    $scope.$on("prodRecpOriginRegCtrl", function(event, data) {
        if(data !== null) {
            // 상품 데이터 셋팅
            $scope.setSelectedProd(data);

            // 미등록 재료 리스트 조회
            $scope.searchProdRecpOriginReg();
            event.preventDefault();
        }
    });

    // 선택 매장
    $scope.selectedProd;
    $scope.setSelectedProd = function(store) {
        $scope.selectedProd = store;
    };
    $scope.getSelectedProd = function(){
        return $scope.selectedProd;
    };

    // 미등록 재료 리스트 조회
    $scope.searchProdRecpOriginReg = function(){
        var params = {};
        params.prodCd = $scope.selectedProd.prodCd;
        params.hqBrandCd = $scope.selectedProd.hqBrandCd;
        params.recipesNm = $scope.recipesNm;
        params.orgplceNm = $scope.orgplceNm;

        $scope._inquirySub("/base/prod/recpOrigin/prodRecpOriginAdd/getProdRecpOriginAddList.sb", params, function() {}, false);
    };

    // 미등록 재료 조회
    $scope.btnSearch = function () {

        if(!$.isEmptyObject($scope.selectedProd) ) {

            $scope.searchProdRecpOriginReg();

        }else{
            $scope._popMsg(messages["prodRecpOrigin.prodCdBlank"]); // 상품코드를 선택해주세요.
            return false;
        }

    };

    // 미등록 재료 추가
    $scope.add = function () {

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "I";
                $scope.flex.collectionView.items[i].prodCd = $scope.selectedProd.prodCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/recpOrigin/prodRecpOriginAdd/getProdRecpOriginAddSave.sb", params, function(){

            // 등록 재료 조회
            var scope = agrid.getScope("prodRecpOriginDetailCtrl");
            scope._broadcast('prodRecpOriginDetailCtrl', $scope.selectedProd);
            
            // 미등록 재료 조회
            $scope.searchProdRecpOriginReg();

            var scope = agrid.getScope('prodRecpOriginCtrl');
            scope.searchProdRecpOrigin2();
        });
    };

    // 재료명, 원사지명 조회조건 textbox 초기화
    $scope.reset = function () {
        $scope.recipesNm = "";
        $scope.orgplceNm = "";
    }

}]);