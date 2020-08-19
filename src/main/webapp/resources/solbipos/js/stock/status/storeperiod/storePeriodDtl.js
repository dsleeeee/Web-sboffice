/**
 * get application
 */

var app = agrid.getApp();

app.controller('storePeriodDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	  // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('storePeriodDtlCtrl', $scope, $http, true));

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("storePeriodDtlCtrl");

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

	  };


	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("storePeriodDtlCtrl", function (event, data) {

		$scope.slipNo			= data.slipNo;
	    $scope.startDate 		= data.startDate;
	    $scope.endDate			= data.endDate;
	    $scope.prodClassCd    	= data.prodClassCd;
	    $scope.storeCd  		= data.storeCd;
	    $scope.prodCd    		= data.prodCd;
	    $scope.prodNm 			= data.prodNm;
	    $scope.orgnFg     		= data.orgnFg;

	    $scope.storePeriodDtlLayer.show(true);

	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });
}]);