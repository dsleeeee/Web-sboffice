/****************************************************************
 *
 * 파일명 : media.js
 * 설  명 : 미디어관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.09     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  미디어관리 그리드
 **********************************************************************/
app.controller('mediaCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('mediaCtrl', $scope, $http, true));

  // 전체기간 체크박스
  $scope.isChecked = true;
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("useYn", useYn);
  $scope._setComboData("fileType", fileTypeComboListAll);

  // 등록일자 셋팅
  $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);

  // 선택 버전
  $scope.selectVersion;
  $scope.setSelectVersion = function(ver){
    $scope.selectVersion = ver;
  };
  $scope.getSelectVersion = function(){
    return $scope.selectVersion;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.fileTypeDataMap = new wijmo.grid.DataMap(fileTypeComboList, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "verSerNo" || col.binding === "preview" || col.binding === "downLoad") {
          wijmo.addClass(e.cell, 'wijLink');
        }

        // 미리보기
        if(col.binding === "preview"){
            if(item.fileExt === "png" || item.fileExt === "PNG" ||
                item.fileExt === "jpg" || item.fileExt === "JPG" ||
                item.fileExt === "jpeg" || item.fileExt === "JPEG" ||
                item.fileExt === "gif" || item.fileExt === "GIF") {

                e.cell.innerHTML = "미리보기";
            }
        }

        // 다운로드
        if(col.binding === "downLoad"){
          e.cell.innerHTML = "<td><a href=\"/base/store/media/media/download.sb?fileNm=" + item.fileNmExt + "&orginlFileNm=" + item.realFileOrgNm + "&fileExt=" + item.fileExt + "\">다운로드</a></td>";
        }
      }
    });

    // 버전 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedData = s.rows[ht.row].dataItem;

        if ( col.binding === "verSerNo") {
            $scope.setSelectVersion(selectedData);
            $scope.versionInfoDetailLayer.show(true);
            event.preventDefault();
        }

        if ( col.binding === "preview") {
            if(selectedData.fileExt === "png" || selectedData.fileExt === "PNG" ||
                selectedData.fileExt === "jpg" || selectedData.fileExt === "JPG" ||
                selectedData.fileExt === "jpeg" || selectedData.fileExt === "JPEG" ||
                selectedData.fileExt === "gif" || selectedData.fileExt === "GIF") {

                $scope._broadcast('mediaPreviewCtrl', selectedData.verSerNo);
                $scope.mediaPreviewLayer.show(true);
                event.preventDefault();
            }
        }
      }
    });

    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
  };

  // 조회 버튼 클릭
  $scope.$on("mediaCtrl", function(event, data) {
    $scope.getVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionList = function(){
    var params = {};
    params.listScale = $scope.listScaleVer;
    params.curr = $scope._getPagingInfo('curr');
    params.hqOfficeCd = hqOfficeCd;
    params.useYn = $scope.useYn;
    params.fileType = $scope.fileType;
    params.fileOrgNm = $("#fileOrgNm").text();

    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    }

    $scope._inquiryMain("/base/store/media/media/list.sb", params, function() {
    });
  };

  // 신규버전 등록
  $scope.registVersion = function(){
    $scope.setSelectVersion(null);
    $scope.versionRegistLayer.show(true, function(){
      var scope = agrid.getScope('verRegistCtrl');
      scope.version = null;
      scope.useYn = 'Y';

      $('#file').val(null);
      $scope._pageView('mediaCtrl', 1);
    });
  };

  // 삭제
  $scope.del = function() {
    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].gChk) {
          params.push($scope.flex.collectionView.items[i]);
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/store/media/media/getMediaDelete.sb", params, function(){
        $scope.getVersionList()
      });
    });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 버전상세보기 팝업 핸들러 추가
    $scope.versionInfoDetailLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('verDetailCtrl');
      }, 50)
    });

    // 매장등록 팝업 핸들러 추가
    $scope.storeAddLayer.shown.addHandler(function (s) {});

    // 버전신규등록 팝업 핸들러 추가
    $scope.versionRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('verRegistCtrl', $scope.getSelectVersion() );
      }, 20)
    });
  });

}]);