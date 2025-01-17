/****************************************************************
 *
 * 파일명 : kioskKeyMapClsTypeNm.js
 * 설  명 : 키맵그룹명 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.08     권지현      1.0
 *
 * **************************************************************/

// 팝업 그리드 생성
app.controller('clsTypeNmCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('clsTypeNmCtrl', $scope, $http, false));

  $scope._setComboData("clsTypeNmYnCombo", useYnA);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
  };

  // 팝업 그리드 조회
  $scope.$on("clsTypeNmCtrl", function(event, data) {
    $scope._setComboData("tuClsTypeCombo", kioskTuClsTypeListAll);
    $scope.getClsTypeNmList(data);
    event.preventDefault();
  });

  $scope.getClsTypeNmList = function (data){

    // 파라미터
    var params = {};
    params.posNo = data;
    if(pageFg === '1'){
      params.pageFg = '1';
      params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
    }
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getClsTypeList.sb", params, function() {});
  };

  $scope.saveClsTypeNm = function () {

    $scope._popConfirm(messages["cmm.choo.save"], function() {

      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        if(pageFg === '1'){
          $scope.flex.collectionView.itemsEdited[i].pageFg = '1';
          $scope.flex.collectionView.itemsEdited[i].storeCd = $("#kioskKeyMapSelectStoreCd").val();
        }
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveClsType.sb", params, function(){

        $scope.clsTypeNmLayer.hide();

        var scope = agrid.getScope("kioskKeyMapRegistCtrl");
        scope.setTuClsDropdownList();
      });
    });
  }

  // 엑셀다운로드 -->
  $scope.excelDownload = function(){
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function()	{
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
          {
            includeColumnHeaders: 	true,
            includeCellStyles	: 	false,
            includeColumns      :	function (column) {
              // return column.visible;
              return column.binding != 'gChk';
            }
          },messages["kioskKeyMap.tuClsTypeNm"]+ '_'+ getCurDateTime() +'.xlsx',
          function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
            }, 10);
          }
      );
    }, 10);
  };
}]);