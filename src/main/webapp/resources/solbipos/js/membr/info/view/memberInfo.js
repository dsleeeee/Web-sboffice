/****************************************************************
 *
 * 파일명 : memberInfo.js
 * 설  명 : 회원정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.08     김지은      1.0
 * 2020.06.25     이재영
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('memberCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberCtrl', $scope, $http, $timeout, true));

  // 기본 회원등급
  // if (membrClassList.length == 0) {
  //   membrClassList = [{value: "", name: "선택"}, {value: "001", name: "기본등급"}];
  // }

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  $scope._getComboDataQuery('072', 'emailRecvYn', 'A');
  $scope._getComboDataQuery('072', 'smsRecvYn', 'A');
  $scope._getComboDataQuery('032', 'anvType', 'A');
  $scope._getComboDataQuery('077', 'periodType', 'A');
  $scope._getComboDataQuery('076', 'weddingYn', 'A');
  $scope._getComboDataQuery('055', 'gendrFg', 'A');
  $scope._getComboDataQuery('067', 'useYn', 'A');

  // 선택 회원
  $scope.selectedMember;
  $scope.setSelectedMember = function(member) {
    $scope.selectedMember = member;
  };
  $scope.getSelectedMember = function(){
    return $scope.selectedMember;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 회원번호, 회원명 클릭시 상세정보 팝업
        if (col.binding === "membrNo" || col.binding === "membrNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
        // 후불적용매장등록 클릭시 매장선택 팝업
        if(col.binding === "postpaidStore") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 회원선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        // 회원번호, 회원명 클릭시 상세정보 팝업
        if ( col.binding === "membrNo" ||  col.binding === "membrNm") {
          var selectedData = s.rows[ht.row].dataItem;
          $scope.setSelectedMember(selectedData);
          $scope._broadcast('responseGet', selectedData);
          $scope.memberRegistLayer.show(true);
          // $scope.memberInfoDetailLayer.show(true);
          event.preventDefault();
        }

        // 후불적용매장등록 클릭시 매장선택 팝업
        if (col.binding === "postpaidStore" ) {
          var selectedData = s.rows[ht.row].dataItem;
          // 해당 매장의 등록매장이 본사의 디폴트 매장과 동일할 경우에만 후불적용 매장을 등록할 수 있다.
          $scope.setSelectedMember(selectedData);
          $scope.postpaidStoreRegistLayer.show(true);
          event.preventDefault();
        }
      }
    });

    // <-- 그리드 헤더3줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.gChk = messages["cmm.chk"]
    dataItem.membrNo = messages["regist.membr.no"]
    dataItem.membrNm = messages["regist.membr.nm"]
    dataItem.membrClassCd = messages["regist.class.cd"]
    dataItem.membrClassNm = messages["regist.class.nm"]
    dataItem.membrCardNo = messages["regist.card.no"]
    dataItem.birthday = messages["regist.brthd"]
    dataItem.phoneNo = messages["regist.tel"]
    dataItem.telNo = messages["regist.phone.no"]
    dataItem.shortNo = messages["regist.membr.stortNo"]
    // dataItem.regStoreCd = messages["regist.membr.regStore"]
    // dataItem.regStoreNm = messages["regist.membr.regStore"]
    dataItem.emailRecvYn = messages["regist.email.recv"]
    dataItem.smsRecvYn = messages["regist.sms.recv"]
    dataItem.useYn = messages["regist.useYn"]
    dataItem.postpaidStore = messages["regist.membr.store"]

    dataItem.point = messages["regist.membr.total"]
    dataItem.totSavePoint = messages["regist.membr.total"]
    dataItem.totUsePoint = messages["regist.membr.total"]
    dataItem.totAdjPoint = messages["regist.membr.total"]
    dataItem.avablPoint = messages["regist.membr.total"]

    dataItem.save = messages["regist.membr.total"]
    dataItem.saveCnt = messages["regist.membr.total"]
    dataItem.saveAmt = messages["regist.membr.total"]

    dataItem.visit = messages["regist.membr.total"]
    dataItem.firstSaleDate = messages["regist.membr.total"]
    dataItem.lastSaleDate = messages["regist.membr.total"]
    dataItem.regDt = messages["regist.membr.total"]
    s.columnHeaders.rows[0].dataItem = dataItem;

    //둘째줄 헤더
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    var dataItem1         = {};
    dataItem1.gChk = messages["cmm.chk"]
    dataItem1.membrNo = messages["regist.membr.no"]
    dataItem1.membrNm = messages["regist.membr.nm"]
    dataItem1.membrClassCd = messages["regist.class.cd"]
    dataItem1.membrClassNm = messages["regist.class.nm"]
    dataItem1.membrCardNo = messages["regist.card.no"]
    dataItem1.birthday = messages["regist.brthd"]
    dataItem1.phoneNo = messages["regist.tel"]
    dataItem1.telNo = messages["regist.phone.no"]
    dataItem1.shortNo = messages["regist.membr.stortNo"]
    dataItem1.regStoreCd = messages["regist.membr.regStore"]
    dataItem1.regStoreNm = messages["regist.membr.regStore"]
    dataItem1.emailRecvYn = messages["regist.email.recv"]
    dataItem1.smsRecvYn = messages["regist.sms.recv"]
    dataItem1.useYn = messages["regist.useYn"]
    dataItem1.postpaidStore = messages["regist.membr.store"]

    dataItem1.point = messages["regist.membr.point"]
    dataItem1.totSavePoint = messages["regist.membr.point"]
    dataItem1.totUsePoint = messages["regist.membr.point"]
    dataItem1.totAdjPoint = messages["regist.membr.point"]
    dataItem1.avablPoint = messages["regist.membr.point"]

    dataItem1.save = messages["regist.membr.save"]
    dataItem1.saveCnt = messages["regist.membr.save"]
    dataItem1.saveAmt = messages["regist.membr.save"]

    dataItem1.visit = messages["regist.membr.visit"]
    dataItem1.firstSaleDate = messages["regist.membr.visit"]
    dataItem1.lastSaleDate = messages["regist.membr.visit"]

    dataItem1.regDt = messages["regist.membr.day"]

    s.columnHeaders.rows[1].dataItem = dataItem1;

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
        panel.rows[r].allowMerging    = true;
        panel.columns[c].allowMerging = true;
        wijmo.setCss(cell, {
          display    : 'table',
          tableLayout: 'fixed'
        });
        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
        wijmo.setCss(cell.children[0], {
          display      : 'table-cell',
          verticalAlign: 'middle',
          textAlign    : 'center'
        });
      }
      // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
        // GroupRow 인 경우에는 표시하지 않는다.
        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
          cell.textContent = '';
        } else {
          if (!isEmpty(panel._rows[r]._data.rnum)) {
            cell.textContent = (panel._rows[r]._data.rnum).toString();
          } else {
            cell.textContent = (r + 1).toString();
          }
        }
      }
      // readOnly 배경색 표시
      else if (panel.cellType === wijmo.grid.CellType.Cell) {
        var col = panel.columns[c];
        if (col.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }
    // <-- //그리드 헤더3줄 -->

  };

  // 조회 버튼 클릭
  $scope.$on("memberCtrl", function(event, data) {
    // 이출, 이입매장 초기화
    /*$("#regStoreCd").val("");
    $("#regStoreNm").val(messages["cmm.select"]);*/
    $scope.getMemberList();
    event.preventDefault();
  });

  // 회원 목록 조회
  $scope.getMemberList = function(){
    var params = {};

    params.periodType = $scope.periodType;
    params.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
    params.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
    params.anvType = $scope.anvType;
    params.anvStartDate = dateToDaystring($scope.anvStartDate).replaceAll('-', '');
    params.anvEndDate = dateToDaystring($scope.anvEndDate).replaceAll('-', '');

    params.startSaveSale = $scope.startSaveSale;
    params.endSaveSale = $scope.endSaveSale;
    params.startAvablPoint = $scope.startAvablPoint;
    params.endAvablPoint = $scope.endAvablPoint
    params.stortNo = $scope.stortNo;
    params.weddingYn = $scope.weddingYn;
    params.memberClass = $scope.memberClass;
    params.phoneNo = $scope.phoneNo;

    params.listScale = $scope.listScale;

    params.membrNo = $("#memberNo").val();
    params.membrNm = $("#memberNm").val();
    params.membrEngNm = $("#memberEngNm").val();
    params.regStoreCd = $("#regStoreCd").val();
    params.telNo = $("#telNo").val();
    params.membrCardNo = $("#membrCardNo").val();
    params.emailAddr = $("#emailAddr").val();
    params.emailRecvYn = $scope.emailRecvYn;
    params.smsRecvYn = $scope.smsRecvYn;
    params.gendrFg = $scope.gendrFg;
    // params.useYn = $scope.useYn;
    params.useYn = 'Y';

    // console.log('params ', params);


    $scope._inquiryMain("/membr/info/view/view/getMemberlist.sb", params, function() {});
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.regStoreShow = function () {
    $scope._broadcast('regStoreCtrl');
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 회원조회 팝업 핸들러 추가
    $scope.memberInfoDetailLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('memberInfoDetailCtrl', $scope.getSelectedMember());
      }, 50)
    });

    // 회원 등록 및 수정 팝업 핸들러 추가
    $scope.memberRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('memberRegistInfo', $scope.getSelectedMember());
      }, 50)
    });

    // 후불회원등록 팝업 핸들러 추가
    $scope.postpaidStoreRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('postpaidStoreRegistCtrl', $scope.getSelectedMember());
      }, 50)
    });
  });

  // 신규회원 등록
  $scope.registMember = function(){
    $scope.setSelectedMember(null);
    $scope._broadcast('responseGet', {});
    $scope.memberRegistLayer.show(true);
  };

  // 회원 삭제
  $scope.deleteMember = function(){

    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 회원 사용여부 '미사용'으로 변경 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/membr/info/view/base/remove.sb", params, function(){ $scope.getMemberList() });

  };

  // 엑셀 다운로드
  $scope.excelDownloadInfo = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : true,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, '회원정보_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

  // 회원 거래처 매핑
  $scope.memberVendorMapping = function(){
    var params = {};

    $scope._broadcast('memberVendorMappingCtrl', params);
  };

}]);