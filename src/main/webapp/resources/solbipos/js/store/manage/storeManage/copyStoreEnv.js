/****************************************************************
 *
 * 파일명 : copyStoreEnv.js
 * 설  명 : 매장환경복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.28     김지은      1.0
 *
 * **************************************************************/

app.controller('copyStoreEnvCtrl', ['$scope', '$http', function ($scope, $http) {

  angular.extend(this, new RootController('copyStoreEnvCtrl', $scope, $http, false));

  $scope.isPosReadonly = false;

  // 콤보박스 생성 및 데이터 초기화
  $scope.comboDt = { originalPosCombo:null, targetPosCombo:null };

  $scope.originalPosArr = [];
  $scope.targetPosArr = [];

  $scope.originalPosArr.push({posNo:"", posCdNm:"선택"});
  $scope.targetPosArr.push({posNo:"", posCdNm:"선택"})

  $scope.resetCombobox = function(){

    $scope.originalPosArr = [];
    $scope.targetPosArr = [];

    $scope.originalPosArr.push({posNo:"", posCdNm:"선택"});
    $scope.targetPosArr.push({posNo:"", posCdNm:"선택"})
    $scope.isPosReadonly = true;

    $scope.setComboReadonly();
  };

  $scope.setComboReadonly = function(){
    console.log('setComboReadonly');
    $scope.comboDt.originalPosCombo.isReadOnly = $scope.isPosReadonly;
    $scope.comboDt.targetPosCombo.isReadOnly = $scope.isPosReadonly;
  };


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        // if(selectedRow.nmcodeCd === '03') {
        //   if (col.binding === 'gChk' ) { //포스환경 선택시
        //     $scope.isPosReadonly = false;
        //   } else {
        //     $scope.isPosReadonly = true;
        //   }
        // }
        // $scope.setComboReadonly();
        $scope.posEnvCheck();
      }
    });
  };

  $scope.posEnvCheck = function(){

    $scope.flex.collectionView.commitEdit();

    console.log('$scope.flex.collectionView.items',$scope.flex.collectionView.items);

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].nmcodeCd === '03') {
        if($scope.flex.collectionView.items[i].gChk) {
          $scope.isPosReadonly = false;
        } else {
          $scope.isPosReadonly = true;
        }
        $scope.setComboReadonly();
      }
    }


  };



  // 팝업 오픈시 테이블 그룹정보 조회
  $scope.$on("copyStoreEnvCtrl", function(event, data) {
    // $scope.getPosNmList();
    $scope.resetCombobox();
    event.preventDefault();
  });

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.originalStoreShow = function () {
    console.log('searchStoreShow...');
    $scope._pageView('originalStoreCtrl', 1);
  };

  $scope.targetStoreShow = function () {
    console.log('targetStoreShow...')
    $scope._pageView('targetStoreCtrl', 1);
  };

  $scope.searchEnvList = function(){


    if( isEmptyObject($("#originalStoreCd").val()) ) {
      $scope._popMsg("원매장을 선택해주세요.");
      return false;
    }

    if( isEmptyObject($("#targetStoreCd").val()) ) {
      $scope._popMsg("복사대상매장을 선택해주세요.");
      return false;
    }

    var params             = {};
    params.originalStoreCd = $("#originalStoreCd").val();
    params.targetStoreCd   = $("#targetStoreCd").val();

    console.log('params', params);

    $scope._postJSONQuery.withPopUp( "/base/store/view/copyStoreEnv/getStoreEnvInfo.sb", params,
      function(response){

        console.log('response',response);

        var data            = response.data.data;
        var envList         = data.envList;
        var originalPosList = data.originalPosList;
        var targetPosList   = data.targetPosList;


        // $scope.setOriginalPosList(data.originalPosList);
        // $scope.setTargetPosList(data.targetPosList);
        //
        // console.log('getOriginalPosList : ' + $scope.getOriginalPosList());
        // console.log('getTargetPosList : ' + $scope.getTargetPosList());

        $scope.data = new wijmo.collections.CollectionView(envList);

        if(originalPosList.length > 0) {
          for(var i=0; i<=originalPosList.length; i++){
            $scope.originalPosArr.push(originalPosList[i]);
          }
        }
        if(targetPosList.length > 0) {
          for(var i=0; i<=targetPosList.length; i++){
            $scope.targetPosArr.push(targetPosList[i]);
          }
        }
        $scope.comboDt.originalPosCombo.itemsSource = new wijmo.collections.CollectionView( $scope.originalPosArr);
        $scope.comboDt.targetPosCombo.itemsSource = new wijmo.collections.CollectionView( $scope.targetPosArr);

      });
  };


  //
  // // 포스명칭 목록 조회
  // $scope.getPosNmList = function(){
  //
  //   var params        = {};
  //   var storeScope    = agrid.getScope('storeManageCtrl');
  //   var posEnvScope   = agrid.getScope('posEnvCtrl');
  //   var posList       = posEnvScope.getPosList();
  //
  //   params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
  //   params.storeCd    = storeScope.getSelectedStore().storeCd;
  //
  //   $scope.$broadcast('loadingPopupActive');
  //
  //   $scope._postJSONQuery.withOutPopUp( '/store/manage/storeManage/storeManage/getPosList.sb', params, function(response){
  //
  //     if($.isEmptyObject(response.data) ) {
  //       $scope._popMsg(messages["cmm.empty.data"]);
  //       $scope.tableGroupLayer.hide();
  //       return false;
  //     }
  //     $scope.$broadcast('loadingPopupInactive');
  //
  //     var list = response.data.data.list;
  //
  //     var posEnvScope = agrid.getScope('posEnvCtrl');
  //     posEnvScope.setPosList(list);
  //
  //     var innerHtml = "";
  //
  //     for(var i=0; i<posList.length; i++) {
  //
  //       innerHtml += "<tr>";
  //       innerHtml += "<td class='tc'>"+list[i].rownum+"</td>";
  //       innerHtml += "<td class='tc'>"+list[i].posNo+"</td>";
  //       innerHtml += "<td class='tc'>";
  //       innerHtml += "<input name='pos'id='pos"+list[i].posNo+"' value='"+list[i].posNm+"'>";
  //       innerHtml += "</td>";
  //       innerHtml += "</tr>";
  //     }
  //
  //     $("#posNmContent").html(innerHtml);
  //   });
  // };

  // // 저장
  // $scope.savePosNm = function(){
  //
  //   var params     = new Array();
  //   var storeScope = agrid.getScope('storeManageCtrl');
  //
  //   $("#posNmContent input").each(function(index){
  //
  //     var obj     = {};
  //     var id      = $(this).attr("id");
  //
  //     obj.storeCd = storeScope.getSelectedStore().storeCd;
  //     obj.posNo   = id.substring(3, id.length);
  //     obj.posNm   = $("#"+ id).val();
  //
  //     params.push(obj);
  //   });
  //
  //   // console.log(params);
  //
  //   $scope.$broadcast('loadingPopupActive');
  //   $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/savePosNm.sb", params, function(response){
  //     $scope.$broadcast('loadingPopupInactive');
  //     $scope._popMsg(messages["cmm.saveSucc"]);
  //     $scope.posNmLayer.hide();
  //   });
  // };

  // 팝업 닫기
  $scope.close = function(){
    $scope.copyStoreEnvLayer.hide();
  };
}]);
