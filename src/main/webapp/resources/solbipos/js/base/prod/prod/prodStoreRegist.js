/****************************************************************
 *
 * 파일명 : prodStoreRegist.js
 * 설  명 : 상품적용 매장등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.04     김지은      1.0
 *
 * **************************************************************/

function closePop(){
  var scope = agrid.getScope("regStoreCtrl");
  scope.close();
}
/**
 *  상품적용매장 그리드 생성
 */
app.controller('regStoreCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('regStoreCtrl', $scope, $http, true));

  $scope._setComboData("srchPsrStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드
  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹

  $scope.prodSaleUprcApply = true;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        // 가격 변경시 체크박스 체크
        if (col.binding === "saleUprc" || col.binding === "stinSaleUprc" || col.binding === "dlvrSaleUprc" || col.binding === "packSaleUprc") {
          item.gChk = true;
        }
        // 판매가 변경시 다른 컬럼값도 변경
        if (col.binding === "saleUprc") {
          if($("#prodSaleUprcApply").prop("checked")){
            item.stinSaleUprc = item.saleUprc;
            item.dlvrSaleUprc = item.saleUprc;
            item.packSaleUprc = item.saleUprc;
          }
        }
      }
      s.collectionView.commitEdit();
    });

    var url             = '/base/prod/prod/prod/getBrandComboList.sb';
    var comboParams     = {};
    comboParams.hqOfficeCd = hqOfficeCd;
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchHqBrand", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
  };

  // 콤보박스 데이터 Set
  $scope._setComboData("srchSysStatFg", sysStatFg);

  // 등록 매장 그리드 조회
  $scope.$on("regStoreCtrl", function(event, data) {
    if(data !== null && data !== "" && data !== undefined) {
      $("#lblCurr").text(data.curr);
    }

    $scope.searchRegStore();
    // 등록상품 조회 후, 미등록상품 조회
    var noRegStoreGrid = agrid.getScope("noRegStoreCtrl");
    noRegStoreGrid._pageView('noRegStoreCtrl', 1);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 등록된 매장 조회
  $scope.searchRegStore = function(){

    var prodScope     = agrid.getScope("prodCtrl");
    var regStoreScope = agrid.getScope("regStoreCtrl");
    var params        = {};

    params.storeCd    = '';
    params.storeNm    = '';
    params.prodCd     = prodScope.getProdInfo().prodCd;
    if(hqOfficeCd == 'A0001') {
        if(orgnFg === "HQ"){
            params.sysStatFg = regStoreScope.srchSysStatFgCombo.selectedValue;
            params.hqBrandCd = regStoreScope.srchHqBrandCombo.selectedValue;
        }
    }
    // params.hqBrandCd     = prodScope.getProdInfo().hqBrandCd;
    params.storeRegFg = 'Y';
    params.momsTeam = $("#hdSrchPsrMomsTeam").val();
    params.momsAcShop = $("#hdSrchPsrMomsAcShop").val();
    params.momsAreaFg = $("#hdSrchPsrMomsAreaFg").val();
    params.momsCommercial = $("#hdSrchPsrMomsCommercial").val();
    params.momsShopType = $("#hdSrchPsrMomsShopType").val();
    params.momsStoreManageType = $("#hdSrchPsrMomsStoreManageType").val();
    params.branchCd = $("#hdSrchPsrBranchCd").val();
    if(hqOfficeCd !== 'A0001'){
        if(brandUseFg === "1" && orgnFg === "HQ"){

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $("#hdSrchPsrStoreHqBrandCd").val();

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
    }
    params.momsStoreFg01 = $("#hdSrchPsrMomsStoreFg01").val();
    params.momsStoreFg02 = $("#hdSrchPsrMomsStoreFg02").val();
    params.momsStoreFg03 = $("#hdSrchPsrMomsStoreFg03").val();
    params.momsStoreFg04 = $("#hdSrchPsrMomsStoreFg04").val();
    params.momsStoreFg05 = $("#hdSrchPsrMomsStoreFg05").val();

    $scope._inquirySub("/base/prod/prod/prod/getRegStoreList.sb", params, function() {}, false);
  };

  // 등록 매장 삭제
  $scope.delete = function(){
    var prodScope = agrid.getScope("prodCtrl");
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].prodCd = prodScope.getProdInfo().prodCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/prod/prod/prod/deleteProdStore.sb", params, function(){ $scope.allSearch() });
  };

  // 판매가 변경
  $scope.changeSaleUprc = function(){

    // 그리드 변경된 건 커밋
    $scope.flex.collectionView.commitEdit();

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        // PRC_CTRL_FG 가격관리구분 H인 상품만 수정가능
        if($scope.flex.collectionView.items[i].prcCtrlFg === "S") {
          $scope._popMsg(messages["prod.prcCtrlFgStoreBlank"]); // 가격관리구분이 '매장'인 상품은 수정할 수 없습니다.
          return false;
        }
      }
    }

    var prodScope = agrid.getScope("prodCtrl");
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        var saleUprcB       = $scope.flex.collectionView.items[i].saleUprcB;        // 기존 판매가
        var saleUprc        = $scope.flex.collectionView.items[i].saleUprc;         // 현재 판매가
        var stinSaleUprcB   = $scope.flex.collectionView.items[i].stinSaleUprcB;    // 기존 내점가
        var stinSaleUprc    = $scope.flex.collectionView.items[i].stinSaleUprc;     // 현재 내점가
        var dlvrSaleUprcB   = $scope.flex.collectionView.items[i].dlvrSaleUprcB;    // 기존 배달가
        var dlvrSaleUprc    = $scope.flex.collectionView.items[i].dlvrSaleUprc;     // 현재 배달가
        var packSaleUprcB   = $scope.flex.collectionView.items[i].packSaleUprcB;    // 기존 포장가
        var packSaleUprc    = $scope.flex.collectionView.items[i].packSaleUprc;     // 현재 포장가

        if(saleUprc !== saleUprcB || stinSaleUprc !== stinSaleUprcB || dlvrSaleUprc !== dlvrSaleUprcB || packSaleUprc !== packSaleUprcB){
          $scope.flex.collectionView.items[i].prodCd = prodScope.getProdInfo().prodCd;
          params.push($scope.flex.collectionView.items[i]);
        }
      }
    }
    $scope._save("/base/prod/prod/prod/updateStoreSaleUprc.sb", params, function(){ $scope.allSearch() });
  };

  // 매장 삭제 완료 후처리
  $scope.allSearch = function () {
    $scope.searchRegStore();
    var noRegStoreGrid = agrid.getScope("noRegStoreCtrl");
    noRegStoreGrid.searchNoRegStore();
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

  // 매장브랜드 선택시, hidden에 선택한 브랜드의 코드 셋팅
  // script 코드에서 $scope.combobox value를 가져오기 어려워, combobox select change시 hidden에 값에 셋팅해 이것으로 사용.
  $scope.setPsrStoreHqBrandCd = function(s) {
    $('#hdSrchPsrStoreHqBrandCd').val(s.selectedValue);
  };
  $scope.setPsrMomsTeam = function(s) {
    $('#hdSrchPsrMomsTeam').val(s.selectedValue);
  };
  $scope.setPsrMomsAcShop = function(s) {
    $('#hdSrchPsrMomsAcShop').val(s.selectedValue);
  };
  $scope.setPsrMomsAreaFg = function(s) {
    $('#hdSrchPsrMomsAreaFg').val(s.selectedValue);
  };
  $scope.setPsrMomsCommercial = function(s) {
    $('#hdSrchPsrMomsCommercial').val(s.selectedValue);
  };
  $scope.setPsrMomsShopType = function(s) {
    $('#hdSrchPsrMomsShopType').val(s.selectedValue);
  };
  $scope.setPsrMomsStoreManageType = function(s) {
    $('#hdSrchPsrMomsStoreManageType').val(s.selectedValue);
  };
  $scope.setPsrBranchCd = function(s) {
    $('#hdSrchPsrBranchCd').val(s.selectedValue);
  };
  $scope.setPsrMomsStoreFg01 = function(s) {
    $('#hdSrchPsrMomsStoreFg01').val(s.selectedValue);
  };
  $scope.setPsrMomsStoreFg02 = function(s) {
    $('#hdSrchPsrMomsStoreFg02').val(s.selectedValue);
  };
  $scope.setPsrMomsStoreFg03 = function(s) {
    $('#hdSrchPsrMomsStoreFg03').val(s.selectedValue);
  };
  $scope.setPsrMomsStoreFg04 = function(s) {
    $('#hdSrchPsrMomsStoreFg04').val(s.selectedValue);
  };
  $scope.setPsrMomsStoreFg05 = function(s) {
    $('#hdSrchPsrMomsStoreFg05').val(s.selectedValue);
  };

  // 확장조회 숨김/보임
  $scope.searchAddShowChangeReg = function(){
    if( $("#tblSearchAddShowReg").css("display") === 'none') {
      $("#tblSearchAddShowReg").show();
    } else {
      $("#tblSearchAddShowReg").hide();
    }
  };

  $scope.close= function () {
    var scope = agrid.getScope("prodCtrl");
    scope._pageView('prodCtrl', $("#lblCurr").text());
  }


}]);

/**
 *  상품 미적용 매장 그리드 생성
 */
app.controller('noRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegStoreCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
  };

  // 미등록 매장 그리드 조회
  $scope.$on("noRegStoreCtrl", function(event, data) {
    $scope.searchNoRegStore();
    event.preventDefault();
  });

  // 미등록 매장 조회
  $scope.searchNoRegStore = function(){

    var prodScope     = agrid.getScope("prodCtrl");
    var regStoreScope = agrid.getScope("regStoreCtrl");
    var params        = {};

    params.storeCd    = $("#srchRegStoreCd").val();
    params.storeNm    = $("#srchRegStoreNm").val();
    params.prodCd     = prodScope.getProdInfo().prodCd;
    if(hqOfficeCd == 'A0001') {
      if (orgnFg === "HQ") {
          params.sysStatFg = regStoreScope.srchSysStatFgCombo.selectedValue;
          params.hqBrandCd = regStoreScope.srchHqBrandCombo.selectedValue;
      }
    }
    // params.hqBrandCd     = prodScope.getProdInfo().hqBrandCd;
    params.storeRegFg = 'N';
    params.momsTeam = $("#hdSrchPsrMomsTeam").val();
    params.momsAcShop = $("#hdSrchPsrMomsAcShop").val();
    params.momsAreaFg = $("#hdSrchPsrMomsAreaFg").val();
    params.momsCommercial = $("#hdSrchPsrMomsCommercial").val();
    params.momsShopType = $("#hdSrchPsrMomsShopType").val();
    params.momsStoreManageType = $("#hdSrchPsrMomsStoreManageType").val();
    params.branchCd = $("#hdSrchPsrBranchCd").val();
    if(hqOfficeCd !== 'A0001') {
      if (brandUseFg === "1" && orgnFg === "HQ") {

          // 선택한 매장브랜드가 있을 때
          params.storeHqBrandCd = $("#hdSrchPsrStoreHqBrandCd").val();

          // 선택한 매장브랜드가 없을 때('전체' 일때)
          if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
              var userHqBrandCd = "";
              for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                  if (userHqBrandCdComboList[i].value !== null) {
                      userHqBrandCd += userHqBrandCdComboList[i].value + ","
                  }
              }
              params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
          }
      }
    }
    params.momsStoreFg01 = $("#hdSrchPsrMomsStoreFg01").val();
    params.momsStoreFg02 = $("#hdSrchPsrMomsStoreFg02").val();
    params.momsStoreFg03 = $("#hdSrchPsrMomsStoreFg03").val();
    params.momsStoreFg04 = $("#hdSrchPsrMomsStoreFg04").val();
    params.momsStoreFg05 = $("#hdSrchPsrMomsStoreFg05").val();
    console.log(params);

    $scope._inquirySub("/base/prod/prod/prod/getRegStoreList.sb", params, function() {}, false);
  };

  // 매장 등록
  $scope.regist = function() {

    var prodScope = agrid.getScope("prodCtrl");
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].prodCd = prodScope.getProdInfo().prodCd;
        $scope.flex.collectionView.items[i].sideProdYn = prodScope.getProdInfo().sideProdYn;
        $scope.flex.collectionView.items[i].sdselGrpCd = prodScope.getProdInfo().sdselGrpCd;
        $scope.flex.collectionView.items[i].prodClassCd  = prodScope.getProdInfo().prodClassCd;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/prod/prod/prod/insertProdStore.sb", params, function(){ $scope.allSearch() });
  };

  // 매장 등록 완료 후처리
  $scope.allSearch = function () {
    $scope.searchNoRegStore();
    var regStoreGrid = agrid.getScope("regStoreCtrl");
    $scope._broadcast('regStoreCtrl');
  };
}]);

