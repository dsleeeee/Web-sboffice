/**
 * get application
 */
var app = agrid.getApp();

/** 세트재고조정 그리드 controller */
app.controller('saleAnalsMonthlyCtrl', ['$scope', '$http', '$sce', function ($scope, $http, $sce) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('saleAnalsMonthlyCtrl', $scope, $http, true));

	//$scope.reqYearMonth = wcombo.genDateVal("#reqYearMonth", gvStartDate);
	//검색조건에 조회기간
	var startMonth = new wijmo.input.InputDate('#reqYearMonth', {
		format       : "yyyy-MM",
		selectionMode: "2" // 달력 선택 모드(1:day 2:month)
	});

	//초기화
	$scope.init = function () {
		$scope.searchSaleAnalsMonthlyList();
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("saleAnalsMonthlyCtrl", function (event, data) {
		$scope.searchSaleAnalsMonthlyList();
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});

	// 매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.saleAnalsMonthlySelectStoreShow = function () {
		$scope._broadcast('saleAnalsMonthlySelectStoreCtrl');
	};

	// 리스트 조회
	$scope.searchSaleAnalsMonthlyList = function () {

		Number.prototype.format = function(){
			if(this==0) return 0;
			var reg = /(^[+-]?\d+)(\d{3})/;
			var n = (this + '');
			while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
			return n;
		};

		String.prototype.format = function(){
			var num = parseFloat(this);
			if( isNaN(num) ) return "0";
			return num.format();
		};

		$scope.changeDate = function changeDate(day) {
			if (day.substr(0,1) == "0") {
				day = day.substr(1,1);
			}
			return day;
		};

		// 파라미터

		var params = {};
		params.reqYearMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
		params.storeCd = $("#saleAnalsMonthlySelectStoreCd").val();

		var url = "/sale/anals/monthly/SaleAnalsMonthly/list.sb";

		$http({
			method: 'POST', //방식
			url: url, /* 통신할 URL */
			params: params, /* 파라메터로 보낼 데이터 */
			headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
		}).then(function successCallback(response) {
			// 로딩바 hide
			$scope.$broadcast('loadingPopupInactive');
			if ($scope._httpStatusCheck(response, true)) {
				// this callback will be called asynchronously
				// when the response is available
				var list = response.data.data.list;
				if (list.length === undefined || list.length === 0) {
					$scope.data = new wijmo.collections.CollectionView([]);
					if (isView && response.data.message) {
						$scope._popMsg(response.data.message);
					}

					return false;
				}

				var selectedYearMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');

				$scope._selectedYear = selectedYearMonth.substr(0,4);
				$scope._selectedMonth = $scope.changeDate(selectedYearMonth.substr(4,2));

				var sunSum = 0;
				var monSum = 0;
				var tueSum = 0;
				var wedSum = 0;
				var thuSum = 0;
				var friSum = 0;
				var satSum = 0;
				var totalSaleSum = 0;
				var htmlTxt = '';

				for (var i = 0; i < list.length; i++) {

					var sunData = list[i].sun;
					var monData = list[i].mon;
					var tueData = list[i].tue;
					var wedData = list[i].wed;
					var thuData = list[i].thu;
					var friData = list[i].fri;
					var satData = list[i].sat;

					if (sunData != null && sunData != "") {
						var sunSplit = sunData.split('||');
						totalSaleSum += parseInt(sunSplit[1]);
					}

					if (monData != null && monData != "") {
						var monSplit = monData.split('||');
						totalSaleSum += parseInt(monSplit[1]);
					}

					if (tueData != null && tueData != "") {
						var tueSplit = tueData.split('||');
						totalSaleSum += parseInt(tueSplit[1]);
					}

					if (wedData != null && wedData != "") {
						var wedSplit = wedData.split('||');
						totalSaleSum += parseInt(wedSplit[1]);
					}

					if (thuData != null && thuData != "") {
						var thuSplit = thuData.split('||');
						totalSaleSum += parseInt(thuSplit[1]);
					}

					if (friData != null && friData != "") {
						var friSplit = friData.split('||');
						totalSaleSum += parseInt(friSplit[1]);
					}

					if (satData != null && satData != "") {
						var satSplit = satData.split('||');
						totalSaleSum += parseInt(satSplit[1]);
					}
				}

				for (i = 0; i < list.length; i++) {

					cnt = i * 7;
					cnt2 = i + 1;

					htmlTxt += '<tr class="cal-date">';

					sunData = list[i].sun;
					monData = list[i].mon;
					tueData = list[i].tue;
					wedData = list[i].wed;
					thuData = list[i].thu;
					friData = list[i].fri;
					satData = list[i].sat;
					var weekSum = 0;

					if (sunData != null && sunData != "") {
						var sunSplit = sunData.split('||');
						htmlTxt += '	<td class="red">' + $scope.changeDate(sunSplit[0].substr(6,2)) + '</td>';
						sunSum += parseInt(sunSplit[1]);
						weekSum += parseInt(sunSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (monData != null && monData != "") {
						var monSplit = monData.split('||');
						if (monSplit[2] == "Y") {
							htmlTxt += '	<td class="red">' + $scope.changeDate(monSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td>' + $scope.changeDate(monSplit[0].substr(6,2)) + '</td>';
						}
						monSum += parseInt(monSplit[1]);
						weekSum += parseInt(monSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (tueData != null && tueData != "") {
						var tueSplit = tueData.split('||');
						if (tueSplit[2] == "Y") {
							htmlTxt += '	<td class="red">' + $scope.changeDate(tueSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td>' + $scope.changeDate(tueSplit[0].substr(6,2)) + '</td>';
						}
						tueSum += parseInt(tueSplit[1]);
						weekSum += parseInt(tueSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (wedData != null && wedData != "") {
						var wedSplit = wedData.split('||');
						if (wedSplit[2] == "Y") {
							htmlTxt += '	<td class="red">' + $scope.changeDate(wedSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td>' + $scope.changeDate(wedSplit[0].substr(6,2)) + '</td>';
						}
						wedSum += parseInt(wedSplit[1]);
						weekSum += parseInt(wedSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (thuData != null && thuData != "") {
						var thuSplit = thuData.split('||');
						if (thuSplit[2] == "Y") {
							htmlTxt += '	<td class="red">' + $scope.changeDate(thuSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td>' + $scope.changeDate(thuSplit[0].substr(6,2)) + '</td>';
						}
						thuSum += parseInt(thuSplit[1]);
						weekSum += parseInt(thuSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (friData != null && friData != "") {
						var friSplit = friData.split('||');
						if (friSplit[2] == "Y") {
							htmlTxt += '	<td class="red">' + $scope.changeDate(friSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td>' + $scope.changeDate(friSplit[0].substr(6,2)) + '</td>';
						}
						friSum += parseInt(friSplit[1]);
						weekSum += parseInt(friSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (satData != null && satData != "") {
						var satSplit = satData.split('||');
						if (satSplit[2] == "Y") {
							htmlTxt += '	<td class="red">' + $scope.changeDate(satSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td class="blue">' + $scope.changeDate(satSplit[0].substr(6,2)) + '</td>';
						}
						satSum += parseInt(satSplit[1]);
						weekSum += parseInt(satSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					htmlTxt += '	<td class="bg-total">주간합계</td>'
							+ '	<td class="bg-total">비율</td>'
							+ '</tr>'
							+ '<tr class="cal-txt">';

					if (sunData != null && sunData != "") {
						var sunSplit = sunData.split('||');
						htmlTxt += '	<td>' + sunSplit[1].format() + '</td>';
					} else {
						htmlTxt += '	<td></td>';
					}

					if (monData != null && monData != "") {
						var monSplit = monData.split('||');
						htmlTxt += '	<td>' + monSplit[1].format() + '</td>';
					} else {
						htmlTxt += '	<td></td>';
					}

					if (tueData != null && tueData != "") {
						var tueSplit = tueData.split('||');
						htmlTxt += '	<td>' + tueSplit[1].format() + '</td>';
					} else {
						htmlTxt += '	<td></td>';
					}

					if (wedData != null && wedData != "") {
						var wedSplit = wedData.split('||');
						htmlTxt += '	<td>' + wedSplit[1].format() + '</td>';
					} else {
						htmlTxt += '	<td></td>';
					}

					if (thuData != null && thuData != "") {
						var thuSplit = thuData.split('||');
						htmlTxt += '	<td>' + thuSplit[1].format() + '</td>';
					} else {
						htmlTxt += '	<td></td>';
					}

					if (friData != null && friData != "") {
						var friSplit = friData.split('||');
						htmlTxt += '	<td>' + friSplit[1].format() + '</td>';
					} else {
						htmlTxt += '	<td></td>';
					}

					if (satData != null && satData != "") {
						var satSplit = satData.split('||');
						htmlTxt += '	<td>' + satSplit[1].format() + '</td>';
					} else {
						htmlTxt += '	<td></td>';
					}

					var ratio = (weekSum / totalSaleSum) * 100;

					if (isNaN(ratio)) {
						ratio = 0;
					}

					htmlTxt += '	<td>' + weekSum.format() + '</td>'
							+'	<td>' + String(ratio.toFixed(2)) + '%' + '</td>'
							+ '</tr>'
				}

				//요일별 비율
				var sunRatio = (sunSum / totalSaleSum) * 100;
				var monRatio = (monSum / totalSaleSum) * 100;
				var tueRatio = (tueSum / totalSaleSum) * 100;
				var wedRatio = (wedSum / totalSaleSum) * 100;
				var thuRatio = (thuSum / totalSaleSum) * 100;
				var friRatio = (friSum / totalSaleSum) * 100;
				var satRatio = (satSum / totalSaleSum) * 100;

				if (isNaN(sunRatio)) {
					sunRatio = 0;
				}

				if (isNaN(monRatio)) {
					monRatio = 0;
				}

				if (isNaN(tueRatio)) {
					tueRatio = 0;
				}

				if (isNaN(wedRatio)) {
					wedRatio = 0;
				}

				if (isNaN(thuRatio)) {
					thuRatio = 0;
				}

				if (isNaN(friRatio)) {
					friRatio = 0;
				}

				if (isNaN(satRatio)) {
					satRatio = 0;
				}


				htmlTxt += '<tr class="bg-total">'
						+ '  <td class="red">일요일합계</td>'
						+ '  <td>월요일합계</td>'
						+ '  <td>화요일합계</td>'
						+ '  <td>수요일합계</td>'
						+ '  <td>목요일합계</td>'
						+ '  <td>금요일합계</td>'
						+ '  <td class="blue">토요일합계</th>'
						+ '  <td colspan="2">총매출합계</td>'
						+ '</tr>'
						+ '<tr class="cal-txt">'
						+ '  <td>' + sunSum.format() + '</td>'
						+ '  <td>' + monSum.format() + '</td>'
						+ '  <td>' + tueSum.format() + '</td>'
						+ '  <td>' + wedSum.format() + '</td>'
						+ '  <td>' + thuSum.format() + '</td>'
						+ '  <td>' + friSum.format() + '</td>'
						+ '  <td>' + satSum.format() + '</td>'
						+ '  <td rowspan="3" colspan="2" class="tr red s20">' + totalSaleSum.format() + '</td>'
						+ '</tr>'
						+ '<tr class="bg-total">'
						+ '  <td>비율</td>'
						+ '  <td>비율</td>'
						+ '  <td>비율</td>'
						+ '  <td>비율</td>'
						+ '  <td>비율</td>'
						+ '  <td>비율</td>'
						+ '  <td>비율</td>'
						+ '</tr>'
						+ '<tr class="cal-txt">'
						+ '  <td>' + String(sunRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(monRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(tueRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(wedRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(thuRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(friRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(satRatio.toFixed(2)) + '%' + '</td>'
						+ '</tr>';

				$scope.saleAnalsMonthlyBody = $sce.trustAsHtml(htmlTxt);
			}

      }, function errorCallback(response) {
    	  // 로딩바 hide
    	  $scope.$broadcast('loadingPopupInactive');
    	  // called asynchronously if an error occurs
    	  // or server returns response with an error status.
    	  if (response.data.message) {
    		  $scope._popMsg(response.data.message);
    	  } else {
    		  $scope._popMsg(messages['cmm.error']);
    	  }
    	  return false;
      }).then(function () {
    	  // 'complete' code here
    	  if (typeof callback === 'function') {
    		  setTimeout(function () {
    			  callback();
    		  }, 10);
    	  }
      });
	};

}]);
