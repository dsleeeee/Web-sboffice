/****************************************************************
 *
 * 파일명 : view.js
 * 설  명 : 매장정보조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.20     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  매장 목록 그리드
 **********************************************************************/
app.controller('storeListCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeListCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);
  $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList);

  // 선택 매장
  $scope.selectedStore;
  $scope.setSelectedStore = function(store) {
    $scope.selectedStore = store;
  };
  $scope.getSelectedStore = function(){
    return $scope.selectedStore;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;

        // 매장 판매터치키 변경 버튼 set
        if( col.binding === "tukeyButtons" || col.binding === "keyMapButtons"){
          e.cell.innerHTML = "변경";
          e.cell.dataItem = item;
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        // 지도보기 팝업 버튼 set
        if( col.binding === "storeLocation"){
            e.cell.innerHTML = messages["storeView.storeLocation"];
            wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.binding === "storeCd" || col.binding === "storeNm" || col.binding === "ownerNm" || col.binding === "storeLocation") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 매장 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;

        // 매장명과 매장코드 클릭시 매장 상세정보 조회
        if ( col.binding === "storeCd" ||  col.binding === "storeNm") {
          $scope.setSelectedStore(s.rows[ht.row].dataItem);
          $scope.storeInfoViewLayer.show(true);
          event.preventDefault();
        }

        // 사업자번호 클릭시 해당 벤사 정보 조회
        if ( col.binding === "ownerNm") {
          $scope.setSelectedStore(s.rows[ht.row].dataItem);
          $scope.vanConfigLayer.show(true);
          event.preventDefault();
        }

        // 매장 판매터치키 변경 버튼 클릭
        if ( col.binding === "tukeyButtons") {
            $scope.vLoginProcess(selectedRow.msUserId, "/base/prod/touchKey/touchKey/view.sb", "000135"); // 판매터치키등록 리소스코드(개발/운영 DB 동일)
        }

        // 매장 키오스크키맵 변경 버튼 클릭
        if(col.binding === "keyMapButtons"){

            var resrceCd = "001639"; // 키오스크키맵 리소스코드(운영 DB)
            if(window.location.href.indexOf("://192") > 0 || window.location.href.indexOf("://localhost") > 0){
                resrceCd = "001858"; // 키오스크키맵 리소스코드(개발 DB)
            }

            $scope.vLoginProcess(selectedRow.msUserId, "/base/prod/kioskKeyMap/kioskKeyMap/view.sb", resrceCd);
        }

        // 지도보기 팝업
        if(col.binding === "storeLocation") {

            // 위도,경도 또는 주소가 있는지 체크
            if(selectedRow.latitude === "" || selectedRow.longitude === "") {
                if(selectedRow.addr === ""){
                  $scope._popMsg(messages["storeView.mapOpen.msg"]); // 정확한 주소가 없어 지도를 조회할 수 없습니다.
                  return;
                }
            }

            var params = {};
            params.title = messages["storeView.storeLocation"];   // 지도 팝업 title
            params.markerNm = selectedRow.storeNm;                // 지도 위치 마커명
            params.addr = selectedRow.addr;                       // 주소
            params.latitude = selectedRow.latitude;               // 위도
            params.longitude = selectedRow.longitude;             // 경도

            $scope.mapPopLayer.show(true);
            $scope._broadcast('mapPopCtrl', params);
        }
      }
    });
  };

  // 조회 버튼 클릭 (_broadcast)
  $scope.$on("storeListCtrl", function(event, data) {
    $scope.getStoreList();
    event.preventDefault();
  });

  // 매장목록 조회
  $scope.getStoreList = function(){
    var params = {};
    params.listScale = $scope.listScale;

    if(brandUseFg === "1" && orgnFg === "HQ"){

        // 선택한 매장브랜드가 있을 때
        params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

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

    $scope._inquiryMain("/base/store/view/view/list.sb", params, function() {});
  };

  // 매장환경복사 팝업
  $scope.copyStoreEnv = function(){
    $scope.copyStoreEnvLayer.show(true);
  };

  // 매장 판매터치키복사 팝업
  $scope.copyStoreTouchKey = function(){
    $scope.copyStoreTouchKeyLayer.show(true);
    $scope._broadcast('copyStoreTouchKeyCtrl');
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 매장상세정보 팝업 핸들러 추가
    $scope.storeInfoViewLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('storeInfoViewCtrl', $scope.getSelectedStore());
      }, 50)
    });

    // 벤사조회 팝업 핸들러 추가
    $scope.vanConfigLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('posTerminalCtrl', $scope.getSelectedStore());
      }, 50)
    });

    // 매장환경복사 팝업 핸들러 추가
    $scope.copyStoreEnvLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('copyStoreEnvCtrl', $scope.getSelectedStore());
      }, 50)
    });
  });

  // <-- 엑셀다운로드 호출 -->
  $scope.excelDownload = function(){
    var params       = {};

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope._broadcast('storeListExcelCtrl', params);
  };
  // <-- //엑셀다운로드 호출 -->

    // 가상로그인 수행
    // 최초 가상로그인으로 로그인시에는 vLoginId 가 아닌 vUserId 파라미터로 로그인 후 vLoginId로 사용한다.
    $scope.vLoginProcess = function(value, url, resrceCd) {

        if (isEmpty(value)) {
            $scope.$apply(function() {
                $scope._popMsg(messages["virtualLogin.vLogin.fail"]);
            });
            return false;
        } else {

            /* post */
            $scope.popupCnt = $scope.popupCnt + 1;

            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "/store/manage/virtualLogin/virtualLogin/vLogin.sb");
            form.setAttribute("target", value);

            var formField = document.createElement("input");
            formField.setAttribute("type", "hidden");
            formField.setAttribute("name", "vUserId");
            formField.setAttribute("value", value);
            form.appendChild(formField);

            // 가상로그인시, 사용자가 따로 지정한 ULR이 있으면 해당 URL로 이동
            formField = document.createElement("input");
            formField.setAttribute("type", "hidden");
            formField.setAttribute("name", "optUrl");
            formField.setAttribute("value", url);
            form.appendChild(formField);

            // 가상로그인시, 해당 메뉴(사용자가 따로 지정한 ULR)는 사용자 메뉴권한에 상관없이 접근가능
            formField = document.createElement("input");
            formField.setAttribute("type", "hidden");
            formField.setAttribute("name", "optResrceCd");
            formField.setAttribute("value", resrceCd);
            form.appendChild(formField);

            document.body.appendChild(form);

            var popup = window.open("", value, "width=1024,height=768,resizable=yes,scrollbars=yes");
            var crono = window.setInterval(function () {
                if (popup.closed !== false) { // !== opera compatibility reasons
                    window.clearInterval(crono);
                    var params = {};
                    params.vUserId = value;
                    if ( popup.document.getElementsByName("sessionId") ) {
                        params.sid = popup.document.getElementsByName("sessionId")[0].value;
                    }

                    $http({
                        method: 'POST',
                        url: "/store/manage/virtualLogin/virtualLogin/vLogout.sb",
                        params: params,
                        headers: {'Content-Type': 'application/json; charset=utf-8'}
                    }).then(function successCallback(response) {

                    }, function errorCallback(response) {
                        $scope._popMsg(response.message);
                        return false;
                    });

                }
            }, 250);
            form.submit();
            document.body.removeChild(form);
        }
    };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('storeListExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeListExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // <-- 검색 호출 -->
  $scope.$on("storeListExcelCtrl", function(event, data) {
    $scope.getStoreListExcel();
    event.preventDefault();
  });

  $scope.getStoreListExcel = function(){
    var params = {};

    if(brandUseFg === "1" && orgnFg === "HQ"){

      // 선택한 매장브랜드가 있을 때
      params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

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

    $scope._inquiryMain("/base/store/view/view/getStoreListExcel.sb", params, function() {

      if ($scope.excelFlex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
        return false;
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
      $timeout(function()	{
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.excelFlex,
            {
              includeColumnHeaders: 	true,
              includeCellStyles	: 	false,
              includeColumns      :	function (column) {
                return column.visible;
              }
            },
            '매장정보_'+getToday()+'.xlsx',
            function () {
              $timeout(function () {
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
              }, 10);
            }
        );
      }, 10);

    });
  };
  // <-- //검색 호출 -->

}]);
