/****************************************************************
 *
 * 파일명 : memberExcelUpload.js
 * 설  명 : 회원포인트조정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.23    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  세금계산서 요청목록 그리드 생성
 */
app.controller('memberExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 성공내역, 실페내역
  $scope.statuList = [
    {value: '1', name: '전체'},
    {value: '2', name: '성공내역'},
    {value: '3', name: '오류내역'}
  ]
  $scope.statu = $scope.statuList[0]

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberExcelUploadCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.statusFgDataMap = new wijmo.grid.DataMap(statusDataFg, 'value', 'name');
  };

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

// grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  $scope.$on("memberExcelUploadCtrl", function (event, data) {
    $scope.searchMemberExcelList();
    event.preventDefault();
  });

  $scope.searchMemberExcelList = function () {
    var params = {};
    $scope._inquiryMain("/membr/info/upload/excel/getMemberExcelList.sb", params, function () {
    }, false);
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: false,
        includeColumns: function (column) {
          return column.visible;
        }
      }, 'excel.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };


  /** 엑셀업로드 관련 공통 함수 */
  $scope.excelTextUpload = function (prcsFg) {

    var excelUploadScope = agrid.getScope('memberExcelUploadCtrl');
    /** 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐. */
    var uploadFg = 'memberExcel';

    // 엑셀 양식다운로드
    if (prcsFg === 'excelFormDown') {
      excelUploadScope.excelFormDownload(uploadFg);
    } else {
      var msg = messages["excelUpload.confmMsg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
      s_alert.popConf(msg, function () {
        excelUploadScope.uploadFg = uploadFg;
        /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
        excelUploadScope.parentCtrl = 'memberExcelUploadCtrl';
        // 엑셀 업로드
        $("#memberExcelUpload").val('');
        $("#memberExcelUpload").trigger('click')
      });
    }
  };

  // 엑셀 업로드
  $scope.excelUpload = function () {
    $scope.excelTextFg = 'excel';
    // 업로드 progress 관련 기본값 세팅
    $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
    $scope.progressCnt = 0;   // 처리된 숫자

    // 선택한 파일이 있으면
    if ($('#memberExcelUpload')[0].files[0]) {
      var file = $('#memberExcelUpload')[0].files[0];
      var fileName = file.name;
      var fileExtension = fileName.substring(fileName.lastIndexOf('.'));


      // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
      if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
          var flex = $scope.flex;
          wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#memberExcelUpload')[0].files[0], {includeColumnHeaders: true}
              , function (workbook) {
                $timeout(function () {
                  $scope.excelUploadToJsonConvert();
                }, 10);
              }
          );
        }, 10);
      } else {
        $("#memberExcelUpload").val('');
        $scope._popMsg(messages['excelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
        return false;
      }
    }
  };

  // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
  $scope.excelUploadToJsonConvert = function () {
    var jsonData = [];
    var item = {};
    var rowLength = $scope.flex.rows.length;

    if (rowLength === 0) {
      $scope._popMsg(messages['excelUpload.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
      return false;
    }

    // 업로드 된 데이터 JSON 형태로 생성
    for (var r = 0; r < rowLength; r++) {
      item = {};
      for (var c = 0; c < $scope.flex.columns.length; c++) {
        if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
          var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header];
          var cellValue = $scope.flex.getCellData(r, c, false) + '';

          item[colBinding] = cellValue;
        }
      }

      // item.uploadFg = $scope.uploadFg;
      jsonData.push(item);
    }

    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
    let data = new wijmo.collections.CollectionView(jsonData);
    data.trackChanges = true;
    $scope.data = data;
    console.log('data', $scope.data)
  };

  /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
  $scope.save = function () {
    var params = {};
    params.date = $scope.acinsDate;
    params.seqNo = $scope.seqNo;
    params.title = $scope.acinsTitle;
    params.addQtyFg = $scope.addQtyFg;

    var excelUploadScope = agrid.getScope('excelUploadCtrl');

    $http({
      method: 'POST', //방식
      url: '/stock/acins/acins/acinsRegist/excelUpload.sb', /* 통신할 URL */
      params: params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 엑셀 에러내역 팝업 호출
        $scope.excelUploadErrInfo();

        // 등록 그리드, 부모 그리드 조회
        $scope.saveRegistCallback();
      }
    }, function errorCallback(response) {
      $scope._popMsg(response.data.message);
      return false;
    }).then(function () {
      excelUploadScope.excelUploadingPopup(false); // 업로딩 팝업 닫기
    });
  };

  // 에러내역 팝업 호출
  $scope.excelUploadErrInfo = function () {
    var params = {};
    params.uploadFg = 'acins';
    $scope._broadcast('excelUploadErrInfoCtrl', params);
  };
}]);