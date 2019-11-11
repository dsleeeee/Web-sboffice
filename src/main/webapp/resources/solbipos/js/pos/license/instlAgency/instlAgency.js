/****************************************************************
 *
 * 파일명 : instlAgency.js
 * 설  명 : 설치업체관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.17     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  설치업체관리 그리드
 **********************************************************************/
app.controller('instlAgencyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('instlAgencyCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "agencyNm") {
                    //wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "agencyNm") {
                    var params      = {};
                    params.agencyCd = selectedRow.agencyCd;

                    $scope._broadcast('agencyInfoCtrl', params);

                    // 선택한 업체 코드 값 hidden 으로 갖고 있기(사원관리, 인증관리 호출 시 사용)
                    $scope.rowAgencyCd = selectedRow.agencyCd;
                    
                    // 업체정보 탭 활성화
                    $("#agencyInfoView").show();
                    $("#empManageView").hide();
                    //$("#authManageView").hide();
                }
            }
        });
    };

    $scope.$on("instlAgencyCtrl", function(event, data) {
        $scope.getInstlAgency();
        event.preventDefault();
    });

    // 설치요청목록 조회
    $scope.getInstlAgency = function(){
        var params = {};

        params.agencyCd = $("#srchAgencyCd").val();
        params.agencyNm = $("#srchAgencyNm").val();
        params.bizNo = $("#srchBizNo").val();

        $scope._inquiryMain("/pos/license/instlAgency/getInstlAgency.sb", params, function() {});

    };

    // 탭변경
    $scope.changeTabInstlAgency = function(data){

        if(data === "agency"){
            $("#agencyInfoView").show();
            $("#empManageView").hide();
            //$("#authManageView").hide();

        }else if(data === "emp"){
            $("#agencyInfoView").hide();
            $("#empManageView").show();
            //$("#authManageView").hide();
            
            // 사원관리 조회
            getEmpManageList($scope.rowAgencyCd);

        }else if(data === "auth"){
            $("#agencyInfoView").hide();
            $("#empManageView").hide();
            //$("#authManageView").show();
        }
    };

}]);