/****************************************************************
 *
 * 파일명 : popUpGrpNm.js
 * 설  명 : 터치키분류명 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.07     권지현      1.0
 *
 * **************************************************************/

// 팝업 그리드 생성
app.controller('popUpGrpNmCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('popUpGrpNmCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 팝업 그리드 조회
  $scope.$on("popUpGrpNmCtrl", function(event, data) {
    $scope.getGrpNmList();
    event.preventDefault();
  });

  $scope.getGrpNmList = function (){
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/touchKey/touchKey/getGrpList.sb", params, function() {});
  };

  $scope.saveGrpNm = function () {

    $scope._popConfirm(messages["cmm.choo.save"], function() {
      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/prod/touchKey/touchKey/saveGrpNm.sb", params, function(){
        $scope.popUpGrpNmLayer.hide();

        // 저장 파라미터 설정
        var params = {};
        // 가상로그인 대응
        if (document.getElementsByName('sessionId')[0]) {
          params.sid = document.getElementsByName('sessionId')[0].value;
        }

        // 터치키 그룹 코드 재조회
        $.ajax({
          type: 'POST',
          async: false,
          cache: false,
          dataType: 'json',
          data: params,
          url: '/base/prod/touchKey/touchKey/getTouchKeyGrp.sb',
          success: function(data){

            var scope = agrid.getScope("touchKeyCtrl");

            var list = data.data.list;
            var comboArray = [];
            var comboArrayAll = [];
            var comboData  = {};

            comboArrayAll.unshift({name: "전체", value: ""});

            for (var i = 0; i < list.length; i++) {
              comboData       = {};
              comboData.name  = list[i].name;
              comboData.value = list[i].value;
              comboArray.push(comboData);
              comboArrayAll.push(comboData);
            }

            touchKeyGrpData = comboArray;
            tukeyGrpData = comboArrayAll;
            scope._setComboData("touchKeyGrpCombo", touchKeyGrpData);
            scope._setComboData("applyTouchKeyGrpCombo", touchKeyGrpData);
            scope._setComboData("copyTouchKeyGrpCombo", touchKeyGrpData);

          }
        });

        document.getElementById('btnSrchTouchKey').click();
        
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
          },messages["touchKey.keyList"]+ '_'+ getCurDateTime() +'.xlsx',
          function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
            }, 10);
          }
      );
    }, 10);
  };

}]);