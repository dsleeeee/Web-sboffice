/****************************************************************
 *
 * 파일명 : popUpTouchKeyApplyStore.js
 * 설  명 : 출력물샘플 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.28     노현수      1.0
 *
 * **************************************************************/
// 팝업 그리드 생성
app.controller('popUpApplyStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('popUpApplyStoreCtrl', $scope, $http, false));
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchSysStatFgCombo", sysStatFgComboData);
  $scope._setComboData("srchClsFgCombo", clsFgComboData);

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("storeHqBrandCdCombo", brandList); // 매장브랜드
  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // // ReadOnly 효과설정 : checkbox disabled
    // s.formatItem.addHandler(function (s, e) {
    //   // 전체선택 사용불가 설정
    //   if (e.panel.cellType === wijmo.grid.CellType.ColumnHeader) {
    //     var col = s.columns[e.col];
    //     if (col.binding === 'gChk' || col.format === 'checkBox' || col.format === 'checkBoxText') {
    //       e.cell.children[0].disabled = true;
    //     }
    //   }
    // });
    // // grid 수정 이벤트
    // s.beginningEdit.addHandler(function (s, e) {
    //   var col = s.columns[e.col];
    //   if (col.binding === "gChk") {
    //     var dataItem = s.rows[e.row].dataItem;
    //     setTimeout(function () {
    //       if ( dataItem.gChk === true ) {
    //         var chk = 0;
    //         for (var i = 0; i < s.itemsSource.items.length; i++) {
    //           if ( s.itemsSource.items[i].gChk === true ) {
    //             chk++;
    //           }
    //         }
    //         if ( chk > 10 ) {
    //           $scope._popMsg("매장적용은 10개 매장까지만 선택 가능합니다.");
    //           s.setCellData(e.row, "gChk", false);
    //         }
    //       }
    //     }, 10);
    //   }
    // });
  };
  // 팝업 그리드 조회
  $scope.$on("popUpApplyStoreCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.momsEnvstVal = momsEnvstVal;
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
        for (var i = 0; i < brandList.length; i++) {
          if (brandList[i].value !== null) {
            momsHqBrandCd += brandList[i].value + ","
          }
        }
        params.userBrands = momsHqBrandCd;
      }
    } else if(brandUseFg === "1"){
      params.storeHqBrandCd = $scope.storeHqBrandCd;
    }
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/touchKey/touchKey/storeList.sb", params, function() {

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
    if( $("#tblSearchAddShow").css("display") === 'none') {
      $("#tblSearchAddShow").show();
    } else {
      $("#tblSearchAddShow").hide();
    }
  };
}]);

