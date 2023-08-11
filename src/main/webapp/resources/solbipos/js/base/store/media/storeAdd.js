/****************************************************************
 *
 * 파일명 : storeAdd.js
 * 설  명 : 미디어관리 > 매장추가 상세보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.09    권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 탭 변경
function changeTab(){
  var scope = agrid.getScope("addStoreCtrl");
  scope.changeTab();
}

// 조회
function search(){
  var scope = agrid.getScope("addStoreCtrl");
  scope._pageView('addStoreCtrl', 1);
}

/**********************************************************************
 *  적용매장 그리드
 **********************************************************************/
app.controller('addStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('addStoreCtrl', $scope, $http, false));

  $scope.hqOfficeCd = gvHqOfficeCd;
  // 조회조건
  $scope._setComboData("hqOffice", hqList);
  $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드
  $scope._setComboData("tuClsType", tuClsTypeDataAll); // 예약키맵그룹

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("addStoreCtrl", function(event, data) {

    if($("#srchHqOffice").val() != '' || $("#srchHqOffice").val() != null || $("#srchHqOffice").val() != undefined){
      $scope.selectedHqOffice = $("#srchHqOffice").val();
    }
    $scope.addStoreSearch();
    event.preventDefault();
  });

  // 선택본사
  $scope.selectedHqOffice;
  $scope.setSelectedHqOffice = function(s) {
    $scope.selectedHqOffice = s.selectedValue;
  };
  $scope.getSelectedHqOffice = function(){
    return $scope.selectedHqOffice;
  };

  // 선택본사
  $scope.selectedSysStatFg;
  $scope.setSelectedSysStatFg = function(s) {
    $scope.selectedSysStatFg = s.selectedValue;
  };
  $scope.getSelectedSysStatFg = function(){
    return $scope.selectedSysStatFg;
  };

  // 적용매장 목록 조회
  $scope.addStoreSearch = function(){

    var params = {};
    var scope  = agrid.getScope('mediaCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    if( !isEmptyObject($scope.store)){
      params = $scope.store;
    }

    params.verSerNo    = ver;
    params.searchSatus = 'Y';
    params.hqOfficeCd  = $scope.hqOfficeCd;
    params.sysStatFg = $scope.sysStatFg;
    params.storeCd = $("#addStoreChoiceCd").val();

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
    params.tuClsType = $scope.tuClsType;

    $scope._inquiryMain("/base/store/media/applcStore/srchStoreList.sb", params, function() {
      // 적용매장 조회 후, 미적용 매장 조회
      var allStoreScope = agrid.getScope("allStoreCtrl");
      allStoreScope.allStoreSearch($scope.tuClsType, brandUseFg === "1" && orgnFg === "HQ" ? $scope.srchStoreHqBrandCdCombo.selectedValue : ""); // 미적용매장 조회시, 본사권한은 검색조건 매장브랜드 값 넘기기
      //allStoreScope._pageView('allStoreCtrl', 1);

    }, false);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.addStoreChoiceShow = function () {
    $scope._broadcast('addStoreChoiceCtrl');
  };


  // 삭제
  $scope.delete = function(){

    var params = new Array();
    var scope  = agrid.getScope('mediaCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].verSerNo = ver;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/store/media/applcStore/removeStore.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("addStoreCtrl");
      addStoreScope._broadcast('addStoreCtrl');
    });
  };

  //탭변경
  $scope.changeTab = function(){
    $scope.storeAddLayer.hide();
    $scope.versionInfoDetailLayer.show();
  };

    // 양식 다운로드
    $scope.sampleDownload = function () {

        var vScope = agrid.getScope('addStoreExcelFileUploadCtrl');
        vScope.sampleDownload();
    };

    // 엑셀업로드
    $scope.excelUpload = function () {

      // 정상업로드 된 데이터는 적용매장으로 등록됩니다. 업로드 하시겠습니까?
      $scope._popConfirm(messages["media.excel.confmMsg"], function() {

          $("#excelUpFile").val('');
          $("#excelUpFile").trigger('click');
      });
    };

}]);


/**********************************************************************
 *  미적용매장 그리드
 **********************************************************************/
app.controller('allStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('allStoreCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("sysStatFg", sysStatFgTotal);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("allStoreCtrl", function(event, data) {
    $scope.allStoreSearch(data);
    event.preventDefault();
  });

  // 미적용매장 목록 조회
  $scope.allStoreSearch = function(tuClsType, vStoreHqBrandCd){

    var params = {};
    var scope  = agrid.getScope('mediaCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    var addStoreScope = agrid.getScope('addStoreCtrl');

    params.verSerNo    = ver;
    params.searchSatus = 'N';
    params.hqOfficeCd  = addStoreScope.hqOfficeCd;
    params.sysStatFg = addStoreScope.sysStatFg;
    params.storeCd = $("#addStoreChoiceCd").val();

    // 복수검색 기능 사용여부
    if ($("#chkMulti").prop("checked")) {
      params.chkMulti = "Y";
    }else{
      params.chkMulti = "N";
    }

    if(brandUseFg === "1" && orgnFg === "HQ"){

        // 선택한 매장브랜드가 있을 때
        params.storeHqBrandCd = vStoreHqBrandCd;

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
    params.tuClsType = tuClsType;

    $scope._inquiryMain("/base/store/media/applcStore/srchStoreList.sb", params, function() {
    }, false);
  };


  // 저장
  $scope.save = function(){

    var params = new Array();
    var scope  = agrid.getScope('mediaCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].verSerNo = ver;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/store/media/applcStore/regist.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("addStoreCtrl");
      addStoreScope._broadcast('addStoreCtrl');
    });
  };
}]);








/**********************************************************************
 *  엑셀업로드 그리드
 **********************************************************************/
app.controller('addStoreExcelFileUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('addStoreExcelFileUploadCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.flex.columns.length; i++) {
            var col = $scope.flex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
    $scope.progressCnt = 0; // 처리된 숫자

    $scope.$on("addStoreExcelFileUploadCtrl", function(event, data) {

    });

    // 양식 다운로드
    $scope.sampleDownload = function () {

        // 샘플데이터
        $scope.addRow();

        $timeout(function () {
           wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
               includeColumnHeaders : true,
               includeCellStyles : true,
               includeColumns : function (column) {
                   return column.visible;
               }
           }, '미적용매장_엑셀업로드_양식.xlsx');
         }, 10);
    };

    // 샘플데이터
    $scope.addRow = function(){

        // 그리드 초기화
        var flex = $scope.flex;
        flex.itemsSource = new wijmo.collections.CollectionView();
        flex.collectionView.trackChanges = true;

        // 샘플양식에 값 넣기
        var params = {};
        params.storeCd = "0000001";
        params.storeNm = "○○매장";

        var newRow = flex.collectionView.addNew();
        for (var prop in params) {
            newRow[prop] = params[prop];
        }
        flex.collectionView.commitNew();
    };

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        if ($('#excelUpFile')[0].files[0]) {
            // 엑셀 업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀파일 업로드
    $scope.excelUpload = function () {
        $scope.progressCnt = 0; // 처리된 숫자(초기화)

        // 선택한 파일이 있으면
        if ($('#excelUpFile')[0].files[0]) {
            var file = $('#excelUpFile')[0].files[0];
            var fileName = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                $timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#excelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function (workbook) {
                            $timeout(function () {
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);
            } else {
                $("#excelUpFile").val('');
                $scope._popMsg(messages['empCardInfo.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.excelUploadToJsonConvert = function () {
        var jsonData  = [];
        var item      = {};
        var rowLength = $scope.flex.rows.length;

        if (rowLength === 0) {
            $scope._popMsg(messages['empCardInfo.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};
            for (var c = 0; c < $scope.flex.columns.length; c++) {
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header.replaceAll('\'', '').replaceAll(' ', '')];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';
                    item[colBinding] = cellValue.replaceAll('\'', '');
                }
            }
            jsonData.push(item);
        }
        $timeout(function () {

            setTimeout(function() {
                // 새 데이터 등록
                $scope.save(jsonData);
            }, 500);

        }, 10);
    };

    // 데이터 저장
    $scope.save = function (jsonData) {
        $scope.totalRows = jsonData.length;
        var params = [];
        var msg = '';

        // 저장 시작이면 업로드 중 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // 버전정보
        var scope  = agrid.getScope('mediaCtrl');
        var ver    = scope.getSelectVersion().verSerNo;

        // 사용자 브랜드
        var userHqBrandCd = "";
        for(var i=0; i < userHqBrandCdComboList.length; i++){
            if(userHqBrandCdComboList[i].value !== null) {
                userHqBrandCd += userHqBrandCdComboList[i].value + ","
            }
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            var item = jsonData[i];
            item.verSerNo = ver;

            if(brandUseFg === "1" && orgnFg === "HQ"){
                item.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }

            params.push(item);
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/pos/confg/verAddr/applcStore/storeExcelUpload.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    var parentScope = agrid.getScope('addStoreCtrl');
                    parentScope.addStoreSearch();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
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
                $scope.save(jsonData);
            }
        });

    };

    // 업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['empCardInfo.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 적용 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

}]);
