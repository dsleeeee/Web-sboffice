/****************************************************************
 *
 * 파일명 : storeAdd.js
 * 설  명 : 포스버전관리 > 매장추가 상세보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.05    김지은      1.0           Angular방식으로 변경
 *
 * **************************************************************/
/**
 * get application
 */
// 시 VALUE
var Hh = [24];
for(i =0 ; i < 24; i++){
  var timeVal = i.toString();
  if(i>=0 && i<=9){
    timeVal = "0" + timeVal;
  }
  Hh[i] = {"name":timeVal,"value":timeVal}
}

// 분, 초 VALUE
var MmSs = [60];
for(i =0 ; i < 60; i++){
  var timeVal = i.toString();
  if(i>=0 && i<=9){
    timeVal = "0" + timeVal;
  }
  MmSs[i] = {"name":timeVal,"value":timeVal}
}

var app = agrid.getApp();

// 탭 변경
function changeVerTab(){
  var scope = agrid.getScope("addStoreCtrl");
  scope.changeTab();
}

// 조회
function search(){
  var scope = agrid.getScope("addStoreCtrl");
  scope._pageView('addStoreCtrl', 1);
}

// 매장코드 입력양식 값 제어
function setText(){

  if ($("#chkMulti").prop("checked")) {
    var val = $("#srchStoreCd").val();
    var pattern = /[^a-zA-Z0-9]/gi;   // 특수문자, 공백, 한글 제거

    $("#srchStoreCd").val(comma(val.replace(pattern, "")));
  }
}

// 매장코드 자릿수(7자리) 체크하여 콤마 찍기
function comma(num){
  var len, point, str;

  num = num + "";
  point = num.length % 7 ;
  len = num.length;

  str = num.substring(0, point);
  while (point < len) {
    if (str != "") str += ",";
    str += num.substring(point, point + 7);
    point += 7;
  }

  return str;
}

// 대리점관리매장
var agencyStoreYnData = [
    {"name":"미포함","value":"N"},
    {"name":"포함","value":"Y"},
];

/**********************************************************************
 *  적용매장 그리드
 **********************************************************************/
app.controller('addStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('addStoreCtrl', $scope, $http, false));

  $scope.hqOfficeCd = gvHqOfficeCd;

  // 조회조건
  var commuteInDt = wcombo.genDateVal("#commuteInDt", gvStartDate);
  $scope._setComboData("hqOffice", hqList);
  $scope._setComboData("commuteInDtHhCombo", Hh);
  $scope._setComboData("commuteInDtMmCombo", MmSs);
  $scope._setComboData("agencyStoreYn", agencyStoreYnData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    // 대리점관리매장 초기화
    if (orgnFg == "AGENCY") {
        if(orgnCd === "00607" || orgnCd === "00608"){
            $scope.agencyStoreYnCombo.selectedValue = "Y";
        }else{
            $scope.agencyStoreYnCombo.selectedValue = "N";
        }
    } else {
        $scope.agencyStoreYnCombo.selectedValue = "N";
    }
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
    var ver    = scope.getSelectVersion().verSerNo;

    if( !isEmptyObject($scope.store)){
      params = $scope.store;
    }

    params.verSerNo    = ver;
    params.searchSatus = 'Y';
    params.hqOfficeCd  = $scope.hqOfficeCd;
    params.storeCd = $("#srchStoreCd").val();
    params.storeNm = $("#srchStoreNm").val();
    params.sysStatFg = $scope.sysStatFg;
    params.orgnFg = orgnFg;
    params.agencyCd = orgnCd;
    params.progFg = manageVer;
    params.addr = $("#srchAddr").val();
    params.agencyStoreYn = $scope.agencyStoreYnCombo.selectedValue;
    $scope._inquirySub("/pos/confg/verManage/applcStore/srchStoreList.sb", params, function() {
      // 적용매장 조회 후, 미적용 매장 조회
      var allStoreScope = agrid.getScope("allStoreCtrl");
      allStoreScope._pageView('allStoreCtrl', 1);

    }, false);
  };

  // 삭제
  $scope.delete = function(){

    var params = new Array();
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
    $scope._save("/pos/confg/verManage/applcStore/removeStore.sb", params, function(){
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
    var scope = agrid.getScope("verAddStoreExcelFileUploadCtrl");
    scope.sampleDownload2();
  };

  // 엑셀업로드
  $scope.excelUpload = function () {

    // 정상작성 된 데이터만 조회됩니다. 업로드 하시겠습니까?
    $scope._popConfirm(messages["verManage.excel.confmMsg"], function() {

      $("#excelUpFile").val('');
      $("#excelUpFile").trigger('click');
    });
  };

  // 신규버전예약등록
  $scope.resveRegist = function(){

    var params = new Array();
    var ver    = scope.getSelectVersion().verSerNo;

    var sScope = agrid.getScope('allStoreCtrl')

    // NXPOS_V1/V2 외에 적용매장등록된 내역이 있습니다.<br>[포스관리] - [POS 설정관리] - [매장별 POS 버전 삭제] 에서 관리 후 진행하여 주십시오.
    for (var i = 0; i < sScope.flex.collectionView.items.length; i++) {
      if (sScope.flex.collectionView.items[i].gChk) {
          if(sScope.flex.collectionView.items[i].hqOfficeCd !== "A0001") { // A0001보나비 예외처리(키오스크 포스는 다른버전 사용 가능)
              if (sScope.flex.collectionView.items[i].diffVerCnt > 0) {
                  sScope._popMsg("NXPOS_V" + manageVer + messages["verManage.store.diffVerCnt.chk.msg"]);
                  return false;
              }
          }
      }
    }

    var resveDate = wijmo.Globalize.format(commuteInDt.value, 'yyyyMMdd') +$scope.commuteInDtHhCombo.selectedValue + $scope.commuteInDtMmCombo.selectedValue + '00';

    for (var i = 0; i < sScope.flex.collectionView.items.length; i++) {
      if(sScope.flex.collectionView.items[i].gChk) {
        sScope.flex.collectionView.items[i].verSerNo = ver;
        sScope.flex.collectionView.items[i].progFg = manageVer;
        sScope.flex.collectionView.items[i].resveDate = resveDate;
        params.push(sScope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    var now = new Date();
    var resDate = wijmo.Globalize.format(commuteInDt.value, 'yyyy-MM-dd') + ' ' +$scope.commuteInDtHhCombo.selectedValue + ':' + $scope.commuteInDtMmCombo.selectedValue + ':00';
    var resDt = new Date(resDate);

    // 예약일시가 현재 시간보다 빠른지 비교
    if(now.getTime() > resDt.getTime()){
      $scope._popMsg("예약일시를 확인하세요.");
      return false;
    }

    // console.log('save params',params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/pos/confg/verManage/applcStore/regist.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("addStoreCtrl");
      addStoreScope._broadcast('addStoreCtrl');
    });

  }

  // 엑셀다운로드
  $scope.excelDownload = function(){

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          // return column.visible;
          return column.binding != 'gChk';
        }
      }, '포스버전관리_적용매장' + "_" +getCurDateTime() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);


/**********************************************************************
 *  미적용매장 그리드
 **********************************************************************/
app.controller('allStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
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
    $scope.allStoreSearch();
    event.preventDefault();
  });

  // 미적용매장 목록 조회
  $scope.allStoreSearch = function(data){

    var params = {};
    var ver = scope.getSelectVersion().verSerNo;
    if(data !== null && data !== undefined && data !== ""){
      params.chkMulti = "Y"
      params.storeCd  = data;
    }else {
      var addStoreScope = agrid.getScope('addStoreCtrl');

      params.hqOfficeCd = addStoreScope.hqOfficeCd;
      params.storeCd = $("#srchStoreCd").val();
      params.storeNm = $("#srchStoreNm").val();
      params.sysStatFg = addStoreScope.sysStatFg;
      params.orgnFg = orgnFg;
      params.agencyCd = orgnCd;
      params.addr = $("#srchAddr").val();
      params.agencyStoreYn = addStoreScope.agencyStoreYnCombo.selectedValue;

      // 복수검색 기능 사용여부
      if ($("#chkMulti").prop("checked")) {
        params.chkMulti = "Y";
      } else {
        params.chkMulti = "N";
      }
    }

    params.verSerNo = ver;
    params.searchSatus = 'N';
    params.progFg = manageVer;

    $scope._inquirySub("/pos/confg/verManage/applcStore/srchStoreList.sb", params, function() {
    }, false);
  };


  // 저장
  $scope.save = function(){

    var params = new Array();
    var ver    = scope.getSelectVersion().verSerNo;

    // NXPOS_V1/V2 외에 적용매장등록된 내역이 있습니다.<br>[포스관리] - [POS 설정관리] - [매장별 POS 버전 삭제] 에서 관리 후 진행하여 주십시오.
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if ($scope.flex.collectionView.items[i].gChk) {
            if($scope.flex.collectionView.items[i].storeCd !== "DS89117" && $scope.flex.collectionView.items[i].hqOfficeCd !== "A0001") { // DS89117, A0001보나비 예외처리(키오스크 포스는 다른버전 사용 가능)
                if ($scope.flex.collectionView.items[i].diffVerCnt > 0) {
                    $scope._popMsg("NXPOS_V" + manageVer + messages["verManage.store.diffVerCnt.chk.msg"]);
                    return false;
                }
            }
        }
    }

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].verSerNo = ver;
        $scope.flex.collectionView.items[i].progFg = manageVer;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    // console.log('save params',params);

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/pos/confg/verManage/applcStore/regist.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("addStoreCtrl");
      addStoreScope._broadcast('addStoreCtrl');
    });
  };

  // 엑셀다운로드
  $scope.excelDownload = function(){

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          // return column.visible;
          return column.binding != 'gChk';
        }
      }, '포스버전관리_미적용매장' + "_" +getCurDateTime() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);


/**********************************************************************
 *  엑셀업로드 그리드
 **********************************************************************/
app.controller('verAddStoreExcelFileUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verAddStoreExcelFileUploadCtrl', $scope, $http, false));

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

  $scope.$on("verAddStoreExcelFileUploadCtrl", function(event, data) {

  });

  // 양식 다운로드
  $scope.sampleDownload2 = function () {

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
      $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
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
          if(colBinding === "storeCd" && cellValue !== null && cellValue !== undefined && cellValue != ""){
            item[colBinding] = cellValue.replaceAll('\'', '');
            jsonData.push(item);
          }
        }
      }
    }

    var len = jsonData.length;
    if (len === 0) {
      $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
      $scope._popMsg(messages['empCardInfo.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
      return false;
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
    var params = '';
    var msg = '';

    // 저장 시작이면 업로드 중 팝업 오픈
    if ($scope.progressCnt === 0) {
      $timeout(function () {
        $scope.excelUploadingPopup(true);
        $("#progressCnt").html($scope.progressCnt);
        $("#totalRows").html($scope.totalRows);
      }, 10);
    }


    // stepCnt 만큼 데이터 DB에 저장
    var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
    for (var i = $scope.progressCnt; i < $scope.totalRows; i++) {
      var item = jsonData[i].storeCd;
      if(i === $scope.totalRows -1){
        params += item;
      }else {
        params += item + ",";
      }
    }

    //가상로그인 session 설정
    var sParam = {};
    if(document.getElementsByName('sessionId')[0]){
      sParam['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // 엑셀업로드 값 조회
    var scope = agrid.getScope("allStoreCtrl");
    scope.allStoreSearch(params);

    // 적용매장 그리드 초기화
    // var addScope = agrid.getScope("addStoreCtrl");
    // addScope._gridDataInit();

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
