/****************************************************************
 *
 * 파일명 : captionMsgGrp.js
 * 설  명 : 다국어관리(기능키/메시지) - 화면구분등록 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.11.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('captionMsgGrpCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('captionMsgGrpCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 선택한 화면구분
    $scope.selectVersion;
        $scope.setSelectVersion = function(ver){
        $scope.selectVersion = ver;
    };
    $scope.getSelectVersion = function(){
        return $scope.selectVersion;
    };

    //
    $scope.initGrid = function (s, e) {
        
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "captionImgCd" || col.binding === "preview" || col.binding === "downLoad") {
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
                    e.cell.innerHTML = "<td><a href=\"/base/multilingual/captionMsg/download.sb?fileNm=" + item.fileNm + "&fileOrgNm=" + item.fileOrgNm + "&fileExt=" + item.fileExt + "\">다운로드</a></td>";
                }
                
                // 파일사이즈 변환
                if (col.binding === "fileSize") {
                    if(e.cell.innerText !== null && e.cell.innerText !== undefined && e.cell.innerText !== ""){
                        e.cell.innerHTML = getfileSize(e.cell.innerText.replaceAll(',',''));
                    }
                }
            }
        });

        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedData = s.rows[ht.row].dataItem;

                // 화면구분코드 선택시 상세 팝업 open
                if ( col.binding === "captionImgCd") {
                    $scope.setSelectVersion(selectedData);
                    $scope.captionMsgGrpDtlLayer.show(true);
                    event.preventDefault();
                }

                // 미리보기 선택시 이미지 팝업 open
                if ( col.binding === "preview") {
                    if(selectedData.fileExt === "png" || selectedData.fileExt === "PNG" ||
                        selectedData.fileExt === "jpg" || selectedData.fileExt === "JPG" ||
                        selectedData.fileExt === "jpeg" || selectedData.fileExt === "JPEG" ||
                        selectedData.fileExt === "gif" || selectedData.fileExt === "GIF") {

                        $scope._broadcast('captionMsgGrpImgCtrl', selectedData.captionImgCd);
                        $scope.captionMsgGrpImgLayer.show(true);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    // 조회 클릭
    $scope.$on("captionMsgGrpCtrl", function (event, data) {
        $scope.getCaptionMsgGrpList();
        event.preventDefault();
    });

    // 조회
    $scope.getCaptionMsgGrpList = function () {
        // 파라미터
        var params = {};
        params.captionImgCd = $scope.captionImgCd;
        params.captionImgNm = $scope.captionImgNm;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/multilingual/captionMsg/getCaptionMsgGrpList.sb", params);
    };

    // 신규등록 팝업
    $scope.captionMsgGrpReg = function () {
        
        // 선택한 화면구분 정보 초기화(신규등록이기 때문에)
        $scope.setSelectVersion(null);
        
        // 팝업 닫힐 때
        $scope.captionMsgGrpRegLayer.show(true, function(){

          // 기존 입력값 초기화
          var scope = agrid.getScope('captionMsgGrpRegCtrl');
          scope.version = {};

          $('#file').val(null);
          //$scope._pageView('captionMsgGrpCtrl', 1);
        });
    };

    // 삭제
    $scope.captionMsgGrpDel = function () {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.not.select"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {

                    // 기능키/메시지가 등록되어 있는 화면구분은 삭제 불가
                    if($scope.flex.collectionView.items[i].msgCnt > 0){
                        //'' 은(는) 기능키/메시지가 등록되어있는 화면구분입니다.<br/> 기능키/메시지 먼저 삭제후 화면구분을 삭제하세요.
                        $scope._popMsg($scope.flex.collectionView.items[i].captionImgNm + messages["captionMsg.captionMsgGrp.del.chk.msg"]);
                        return false;
                    }

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/multilingual/captionMsg/deleteCaptionMsgGrp.sb", params, function(){
                // 재조회
                $scope.getCaptionMsgGrpList();

                // 기능키/메시지 탭 화면구분 검색조건 콤보박스 재조회
                var vScope = agrid.getScope('captionMsgCtrl');
                vScope.setCaptionMsgGrpCombo();
            });
        });
    };

    // 팝업 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 신규등록 팝업 화면
        $scope.captionMsgGrpRegLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('captionMsgGrpRegCtrl',  $scope.getSelectVersion());
            }, 50)
        });

        // 상세 팝업 화면
        $scope.captionMsgGrpDtlLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('captionMsgGrpDtlCtrl',  $scope.getSelectVersion());
            }, 50)
        });
    });

}]);