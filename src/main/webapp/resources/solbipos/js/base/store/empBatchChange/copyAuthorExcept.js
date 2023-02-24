/****************************************************************
 *
 * 파일명 : copyAuthorExcept.js
 * 설  명 : 사원권한 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.02.21     권지현      1.0
 *
 * **************************************************************/

app.controller('copyAuthorExceptCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('copyAuthorExceptCtrl', $scope, $http, false));

    // 브랜드 콤보박스 셋팅
    // $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 사원브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 사원관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "empNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 기준사원 사원코드 클릭
                if ( col.binding === "empNo") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var scope = agrid.getScope('copyAuthorExcept2Ctrl');
                    $("#orgEmpNo").val(selectedRow.empNo);
                    $("#orgEmp").text( messages["empBatchChange.original.emp"] + " : " + "[" + selectedRow.empNo + "]" + selectedRow.empNm);

                    // 적용대상사원 조회
                    scope.searchTargetEmpList();

                    event.preventDefault();
                }
            }
        });
    };

    $scope.$on("copyAuthorExceptCtrl", function (event, data) {

        // 기준사원 조회
        $scope.searchOrgEmpList();
        event.preventDefault();
    });
    
    // 기준사원 조회
    $scope.searchOrgEmpList = function () {

        // 파라미터
        var params = {};
        params.empNo = $("#srchEmpNo1").val();
        params.empNm = $("#srchEmpNm1").val();
        params.originalEmpNo = "";
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        // // 선택한 사원브랜드가 있을 때
        // params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
        // // 선택한 사원브랜드가 없을 때('전체' 일때)
        // if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
        //     var userHqBrandCd = "";
        //     for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
        //         if (momsHqBrandCdComboList[i].value !== null) {
        //             userHqBrandCd += momsHqBrandCdComboList[i].value + ","
        //         }
        //     }
        //     params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
        // }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/store/empBatchChange/copyAuthorExcept/getEmpList2.sb", params, function (){
            $("#orgEmpNo").val("");
            $("#orgEmp").text(messages["empBatchChange.original.emp"] + " : ");

            var grid = agrid.getScope('copyAuthorExcept2Ctrl');
            grid._gridDataInit();
        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShowCopy").css("display") === 'none') {
            $("#tblSearchAddShowCopy").show();
            $("#tblSearchAddShowCopy2").show();
        } else {
            $("#tblSearchAddShowCopy").hide();
            $("#tblSearchAddShowCopy2").hide();
        }
    };
}]);

app.controller('copyAuthorExcept2Ctrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('copyAuthorExcept2Ctrl', $scope, $http, false));

    // 브랜드 콤보박스 셋팅
    // $scope._setComboData("storeHqBrandCdCombo2", momsHqBrandCdComboList); // 사원브랜드
    $scope._setComboData("momsTeamCombo2", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo2", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo2", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo2", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo2", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo2", momsStoreManageTypeComboList); // 사원관리타입
    $scope._setComboData("branchCdCombo2", branchCdComboList); // 지사

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("copyAuthorExcept2Ctrl", function (event, data) {
        // 적용대상사원 조회
        $scope.searchTargetEmpList();
        event.preventDefault();
    });

    // 적용대상사원 조회
    $scope.searchTargetEmpList = function () {

        // 파라미터
        var params = {};
        params.empNo = $("#srchEmpNo2").val();
        params.empNm = $("#srchEmpNm2").val();
        params.originalEmpNo = $("#orgEmpNo").val();
        params.momsTeam = $scope.momsTeam2;
        params.momsAcShop = $scope.momsAcShop2;
        params.momsAreaFg = $scope.momsAreaFg2;
        params.momsCommercial = $scope.momsCommercial2;
        params.momsShopType = $scope.momsShopType2;
        params.momsStoreManageType = $scope.momsStoreManageType2;
        params.branchCd = $scope.branchCd2;

        // 선택한 사원브랜드가 있을 때
        // params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo2.selectedValue;
        //
        // // 선택한 사원브랜드가 없을 때('전체' 일때)
        // if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
        //     var userHqBrandCd = "";
        //     for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
        //         if (momsHqBrandCdComboList[i].value !== null) {
        //             userHqBrandCd += momsHqBrandCdComboList[i].value + ","
        //         }
        //     }
        //     params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
        // }
        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/store/empBatchChange/copyAuthorExcept/getEmpList2.sb", params);
    };

    // 복사
    $scope.saveCopyAuthorExcept = function () {
        
        if($("#orgEmpNo").val() === ""){
            $scope._popMsg(messages["empBatchChange.original.emp"] + messages["cmm.require.select"]); // 기준사원을 선택해주세요.
            return false;
        }

        var chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["empBatchChange.target.emp"] + messages["cmm.require.select"]); // 적용대상사원을(를) 선택하세요.
            return false;
        }

        $scope._popConfirm(messages["empBatchChange.copy.msg1"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if($scope.flex.collectionView.items[i].gChk) {

                    $scope.flex.collectionView.items[i].originalEmpNo = $("#orgEmpNo").val();
                    $scope.flex.collectionView.items[i].targetEmpNo = $scope.flex.collectionView.items[i].empNo;

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/store/empBatchChange/copyAuthorExcept/copyAuthorExcept.sb', params, function() {
                $scope._gridDataInit();

                // 기준사원 그리드 재조회
                var scope = agrid.getScope('copyAuthorExcedlvrProddlvrProdptCtrl');
                scope.searchOrgEmpList();
            });
        });

    };

}]);