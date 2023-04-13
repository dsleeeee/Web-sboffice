/****************************************************************
 *
 * 파일명 : storePosTemplate.js
 * 설  명 : 상품적용 매장등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.15     권지현      1.0
 *
 * **************************************************************/

/**
 *  상품적용매장 그리드 생성
 */
app.controller('storePosTemplateCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storePosTemplateCtrl', $scope, $http, true));
  // 브랜드 콤보박스 셋팅
  $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드
  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    $scope.searchRegStore();
  };

  // 콤보박스 데이터 Set
  $scope._setComboData("srchSysStatFg", sysStatFg);

  // 등록 매장 그리드 조회
  $scope.$on("storePosTemplateCtrl", function(event, data) {
    $scope.searchRegStore();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 선택 매장 전송
  $scope.storeApply = function() {

    // 템플릿을 매장의 실제출력물에 적용하시겠습니까?
    $scope._popConfirm(messages["posTemplate.applyTemplt.chk.msg"], function () {

      var templateScope = agrid.getScope("templateCtrl");
      var params = [];

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          item.storeCd = item.storeCd;
          item.prtClassCd = $("#hdPrtClassCd").val();
          item.templtRegFg = $("#hdApplyTempltRegFg").val();
          item.templtCd = $("#hdTempltCd").val();
          params.push(item);
        }
      }

      $scope._postJSONSave.withPopUp("/base/output/posTemplate/template/applyToStoreReal.sb", params, function (response) {

        var result = response.data.data;

        if(result === ""){
          $scope._popMsg(messages["cmm.registFail"]);
        }else{
          $scope._popMsg(messages["cmm.saveSucc"]);
          $scope.storePosTemplateLayer.hide();
        }
      });
    });
  };

  // 매장 조회
  $scope.searchRegStore = function(){
    var params        = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd    = $scope.storeCd;
    params.storeNm    = $scope.storeNm;
    params.sysStatFg  = $scope.sysStatFg;
    if(momsEnvstVal === "1") {
      params.momsTeam = $scope.momsTeam;
      params.momsAcShop = $scope.momsAcShop;
      params.momsAreaFg = $scope.momsAreaFg;
      params.momsCommercial = $scope.momsCommercial;
      params.momsShopType = $scope.momsShopType;
      params.momsStoreManageType = $scope.momsStoreManageType;
      params.branchCd = $scope.branchCd;
      params.storeHqBrandCd = $scope.storeHqBrandCd;
      // '전체' 일때
      if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
        var momsHqBrandCd = "";
        for (var i = 0; i < userHqBrandCdComboList.length; i++) {
          if (userHqBrandCdComboList[i].value !== null) {
            momsHqBrandCd += userHqBrandCdComboList[i].value + ","
          }
        }
        params.userBrands = momsHqBrandCd;
      }
    }
    $scope._inquirySub("/base/output/posTemplate/template/getRegStoreList.sb", params, function() {}, false);
  };

  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
    if( $("#tblSearchAddShow").css("display") === 'none') {
      $("#tblSearchAddShow").show();
    } else {
      $("#tblSearchAddShow").hide();
    }
  };
}]);
