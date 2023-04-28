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
app.controller('popUpApplyStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
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
  $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹

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

  // 적용버튼 클릭
  $scope.btnTouchKeyApplyStore = function(){

    $scope.stepCnt = 10;    // 한번에 DB에 저장할 숫자 세팅
    $scope.progressCnt = 0; // 처리된 숫자

    // 저장 파라미터 설정
    var params = new Array();

    for (var i = 0; i < $scope.flexLayer.collectionView.items.length; i++) {
      if ($scope.flexLayer.itemsSource.items[i].gChk === true ) {
        $scope.flexLayer.itemsSource.items[i].tukeyGrpCd = $scope.applyTouchKeyGrp;
        params.push($scope.flexLayer.itemsSource.items[i]);
      }
    }

    // 적용할 대상이 없습니다.
    if (params.length <= 0) {
      s_alert.pop(messages["touchKey.msg.select"]);
      return false;
    }

    // 터치키 매장적용을 하시겠습니까?(선택매장이 많은경우, 오래걸릴수 있습니다.)
      $scope._popConfirm(messages["touchKey.touchKeyApplyStore.msg"], function() {
          $timeout(function () {
              setTimeout(function () {
                  // 터치키 매장적용 저장
                  $scope.save(params);
              }, 500);
          }, 10);
      });
  };
  
  // 터치키 매장적용 저장
  $scope.save = function(orgParams){

    $scope.totalRows = orgParams.length;    // 체크 매장수
    var params = [];

    // 저장 시작이면 작업내역 로딩 팝업 오픈
    if ($scope.progressCnt === 0) {
        $timeout(function () {
            $scope.excelUploadingPopup(true);
            $("#progressCnt").html($scope.progressCnt);
            $("#totalRows").html($scope.totalRows);
        }, 10);
    }

    // stepCnt 만큼 데이터 DB에 저장
    var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
    for (var i = $scope.progressCnt; i < loopCnt; i++) {
        params.push(orgParams[i]);
    }

    console.log("총 갯수 :" + $scope.totalRows);
    console.log("진행 갯수 :" + $scope.progressCnt + "~" + (loopCnt - 1));
    console.log("---------------------------------------------------------------------");

    //가상로그인 session 설정
    var sParam = {};
    if(document.getElementsByName('sessionId')[0]){
        sParam['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // ajax 통신 설정
    $http({
        method : 'POST', //방식
        url    : '/base/prod/touchKey/touchKey/applyStore.sb', /* 통신할 URL */
        data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
        params : sParam,
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
            if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                // 작업내역 로딩 팝업 닫기
                $scope.excelUploadingPopup(false);
                // 터치키 매장적용 팝업 닫기
                $scope.popUpApplyStoreLayer.hide(true);
                // 저장되었습니다.
                $scope._popMsg(messages["cmm.saveSucc"]);
            }
        }
    }, function errorCallback(response) {
        $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
        if (response.data.message) {
            $scope._popMsg(response.data.message);
        } else {
            $scope._popMsg(messages['cmm.saveFail']);
        }
        return false;
    }).then(function () {
        // 'complete' code here
        // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
        if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
            // 처리된 숫자 변경
            $scope.progressCnt = loopCnt;
            // 팝업의 progressCnt 값 변경
            $("#progressCnt").html($scope.progressCnt);
            $scope.save(orgParams);
        }
    });
  };

  // 작업내역 로딩 팝업
  $scope.excelUploadingPopup = function (showFg) {
      if (showFg) {
          // 팝업내용 동적 생성
          var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['touchKey.loading.msg'] + '</p>';
          innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 진행 중...</div>';
          innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
          // html 적용
          $scope._loadingPopup.content.innerHTML = innerHtml;
          // 팝업 show
          $scope._loadingPopup.show(true);
      } else {
          $scope._loadingPopup.hide(true);
      }
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

