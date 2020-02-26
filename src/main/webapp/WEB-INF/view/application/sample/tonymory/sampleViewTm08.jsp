<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.tonyAdd {position:absolute; top:0; left:-220px; bottom:0; right:220px; width:220px; height:100%; z-index:99; background-color:#2e333f}

.tonyMainlogo {position:fixed; top:0; width:200px; height:49px; z-index:100; background-color:#2e333f}
.tonyMainlogo span {display:block; text-align:center}

.tonySnb {position:fixed; top:101px; width:220px; z-index:100; background-color:#2e333f}
.tonySnb ul {padding:20px} 
.tonySnb ul li {}
.tonySnb ul li a {display:block; width:100%; height:45px; font-size:14px; }
.tonySnb ul li a:hover,.tonySnb ul li a:active {background:url(/resource/solbipos/css/img/menu_bullet.png) 170px 12px no-repeat; background-size:6px; color:#fff}
.tonySnb ul li a .ico {display:inline-block; position:relative; top:6px; width:20px; height:22px; margin-right:15px; background:url(/resource/solbipos/css/img/menu.png) 0 0 no-repeat}
.tonySnb ul li a.menu1 .ico {background-position:0 0}
.tonySnb ul li a.menu2 .ico {background-position:-60px 0}
.tonySnb ul li a.menu3 .ico {background-position:-120px 0}
.tonySnb ul li a.menu4 .ico {background-position:-160px 0}
.tonySnb ul li a.menu5 .ico {background-position:-100px 0}
.tonySnb ul li a.menu6 .ico {background-position:-80px 0}
.tonySnb ul li a.menu7 .ico {background-position:-140px 0}
.tonySnb ul li a.menu8 .ico {background-position:-200px 0}

.tonytab {position:fixed; top:50px; width:220px; border-top:1px solid #3e4452; border-bottom:1px solid #3e4452}
.tonytab span {display:block; float:left; width:110px; height:49px; line-height:49px; text-align:center; font-size:14px; color:#888fa4}
.tonytab span:first-child {border-right:1px solid #3e4452; background-color:#212633}
.tonytab span em {display:inline-block; width:40px; padding-bottom:16px; border-bottom:2px solid #fff; font-size:14px; color:#fff}

.menuControl {display:none}
.userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#ff9d39}
.fixedMenu.t2 {position:fixed; top:50px; left:220px; right:0; z-index:97; box-shadow:0 0 0 0 #ff9d39}
.fixedMenu.t2 ul li a {padding:0 35px 0 10px !important}
.fixedMenu.t2 ul li a.btn_close {padding-right:0 !important; top:16px !important; right:10px !important}
</style>

		<div class="subCon" ng-controller="spTonyCtrl">

			<div class="tonyAdd">
				<a href="#" class="tonyMainlogo">
					<span><img src="/resource/solbipos/css/img/test/tony_main_logo.png" alt="" /></span>
				</a>

				<div class="tonytab">
					<span><em>전체</em></span>
					<span>즐겨찾기</span>
				</div>

				<div class="tonySnb">
					<ul>
						<li><a href="#" class="menu1"><i class="ico"></i>운영관리</a></li>
						<li><a href="#" class="menu2"><i class="ico"></i>마스터관리</a></li>
						<li><a href="#" class="menu3"><i class="ico"></i>매출분석</a></li>
						<li><a href="#" class="menu4"><i class="ico"></i>수주관리</a></li>
						<li><a href="#" class="menu5"><i class="ico"></i>매입관리</a></li>
						<li><a href="#" class="menu6"><i class="ico"></i>재고관리</a></li>
						<li><a href="#" class="menu7"><i class="ico"></i>도우미관리</a></li>
						<li><a href="#" class="menu8"><i class="ico"></i>커뮤니케이션</a></li>
					</ul>
				</div>
			</div>

			<div class="userInfo t2">
				<a href="#" class="userId">
					<span>토니모리 관리자</span>
				</a>

				<div class="userLayer userLayer2" style="display:none">
					<p>
						<span>토니모리 관리자</span>
						<span> 
							<em>토니모리</em> 
						</span> 
						<span>개발중</span>
					</p>
					<ul>
						<li><a href="#">내 정보 변경</a></li>
						<li><a href="#" id="pwchg">비밀번호 변경</a></li>
						<li><a href="#">로그아웃</a></li>
					</ul>
				</div>
			</div>
			
			<!-- 고정메뉴 -->
			<div class="fixedMenu t2">
				<nav>
					<ul>
						<li>			
							<a href="/sample/tonymory/sample41.sb">메인화면</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample51.sb">제품등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample42.sb">제품별수주현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample43.sb">주문등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample44.sb">판촉마일리지 관리</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample45.sb">판촉마일리지 현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample46.sb">판촉마일리지 내역조회</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample47.sb" class="on">당일 매출 현황</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample48.sb">매출 업로드 등록</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample49.sb">매출 업로드 확정</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
						<li>			
							<a href="/sample/tonymory/sample50.sb">일별-제품판매실적조회</a>
							<a href="#" class="btn_close histClose"></a>
						</li>
					</ul>
				</nav>
				<!-- 고정메뉴 있는경우 -->
			</div>
			<!-- //고정메뉴 -->
			
			<div class="searchBar flddUnfld">
				<a href="#" class="open fl">당일 매출 현황</a>
				<!-- 조회 -->
				<div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
					<button class="btn_blue" id="" ng-click="">조회</button>
					<button class="btn_gray" id="" ng-click="">엑셀</button>
					<button class="btn_gray" id="" ng-click="">닫기</button>
				</div>
			</div>

			<table class="searchTbl">
				<colgroup>
				<col class="w15" />
				<col class="w35" />
				<col class="w15" />
				<col class="w35" />
				</colgroup>
				<tbody>
					<tr>
						<!-- 조회일자 -->
						<th>조회일자</th>
						<td>
							<div class="sb-select fl">
								<span class="txtIn w110px">
									<wj-input-date
										value="startDate"
										ng-model="startDate"
										control="startDateCombo"
										min="2000-01-01"
										max="2099-12-31"
										initialized="_initDateBox(s)">
									</wj-input-date>
								</span>
							</div>
						</td>
						<!-- 매장형태 -->
						<th>매장형태</th>
						<td>
							<div class="sb-select">
								<span class="txtIn w120px">
									<wj-combo-box
										items-source="combobox1"
										is-editable="false">
									</wj-combo-box>
								</span>
							</div>
						</td>						
					</tr>
					<tr>
						<!-- 매장 -->
						<th>매장</th>
						<td colspan="3">
							<span class="txtIn fl">
								<input type="text" id="" name="" ng-model="" class="sb-input w100px" placeholder="[ALL]"/>
								<input type="text" id="" name="" ng-model="" class="sb-input w50px"/> 개
							</span>
							<span class="fl">
								<a href="#" class="btn_grayS ml5" id="">추가</a>
								<a href="#" class="btn_grayS" id="">지움</a>
							</span>
						</td>	
					</tr>
				</tbody>
			</table>


			<div id="grid" class="w100">
			
				<div class="wj-gridWrap mt30" style="height:420px; overflow-y: hidden;">
				  <div class="row">
					<wj-flex-grid 
						id="flexGrid"
						sticky-headers="true"
						items-source="data">

						<wj-flex-grid-column header="id" binding="id"></wj-flex-grid-column>
						<wj-flex-grid-column header="str1" binding="str1"></wj-flex-grid-column>
						<wj-flex-grid-column header="str2" binding="str2"></wj-flex-grid-column>
						<wj-flex-grid-column header="str3" binding="str3"></wj-flex-grid-column>
						<wj-flex-grid-column header="str4" binding="str4"></wj-flex-grid-column>
						<wj-flex-grid-column header="num1" binding="num1"></wj-flex-grid-column>
						<wj-flex-grid-column header="num2" binding="num2"></wj-flex-grid-column>
						<wj-flex-grid-column header="num3" binding="num3"></wj-flex-grid-column>
						<wj-flex-grid-column header="num4" binding="num4"></wj-flex-grid-column>
						<wj-flex-grid-column header="num5" binding="num5"></wj-flex-grid-column>
						<wj-flex-grid-column header="num6" binding="num6"></wj-flex-grid-column>
					</wj-flex-grid>

				  </div>
				</div>
			</div>


		</div>
		<!-- //subCon end-->
   
<script>
$(document).ready(function() {

	$(document).on('click', '.userId', function() {
		$("div.userLayer2").toggle();
	});
});

</script>
      
<script>
  // js 추가
  var app = agrid.getApp();

  /**********************************************************************
   *  토니모리 테스트 페이지
   **********************************************************************/
  app.controller('spTonyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('spTonyCtrl', $scope, $http, true));

	$scope.combobox1 = ['전체', '분류1', '분류2'];

	//1. 랜덤 데이터 생성
    var txt1 = 'C0010090,C0010220,C0011050,C0010020,C0010070,C0021090,C0030020'.split(','),
        txt2 = '(2호선당산역2호점),(5호선마포역점),(5호선을지로4가역점),(6호선새절역점),(6호선응암역점)'.split(','),
        txt3 = '영업본부,유통본부,기획본부'.split(',')
        txt4 = '손상지,서영주,윤성재,이준상,이주현,정상준,김은구,이지용,이민재'.split(',')
    
	var data = [];
	for (var i = 0; i < 50; i++) {
		data.push({
			id: i,
			str1: txt1[i % txt1.length],
			str2: txt2[i % txt2.length],
			str3: txt3[i % txt3.length],
			str4: txt4[i % txt4.length],
			num1: Math.random() * 1000,
			num2: Math.random() * 100,
			num3: Math.random() * 1000,
			num4: Math.random() * 10000,
			num5: Math.random() * 100,
			num6: Math.random() * 1000
		});
	}

	//2. 그리드 초기화 및 데이터 바인딩
	var grid = new wijmo.grid.FlexGrid('#flexGrid');
		grid.itemsSource = data;
		grid.columns[0].width = 50;
		grid.columns[1].width = 160;
		grid.columns[2].width = 160;
		grid.columns[3].width = 160;
		grid.columns[4].width = 160;
		grid.columns[5].width = 160;
		grid.columns[6].width = 160;
		grid.columns[7].width = 160;
		grid.columns[8].width = 160;
		grid.columns[9].width = 160;
		grid.columns[10].width = 160;

	//3. 다중 헤더 추가
	for (var i = 0; i < 1; i++) {
		var hr = new wijmo.grid.Row();
		grid.columnHeaders.rows.push(hr);
	}

	//Grid 전체에 대해서 병합(Merge) 허용
	grid.allowMerging = wijmo.grid.AllowMerging.All;

	// 컬럼 헤더에 데이터 추가 및 컬럼 헤더 병합
	for (var r = 0; r < grid.columnHeaders.rows.length; r++) {
		if (r != 1) {
			grid.columnHeaders.rows[r].allowMerging = true;
		}
		for (var c = 0; c < grid.columns.length; c++) {
			if (r == 0 && c == 0 || r == 1 && c == 0) {
				grid.columnHeaders.setCellData(r, c, "No");
			}
			else if ((r == 0 && c == 1) || r == 1 && c == 1) {
				grid.columnHeaders.setCellData(r, c, "매장코드");
			}
			else if ((r == 0 && c == 2) || r == 1 && c == 2) {
				grid.columnHeaders.setCellData(r, c, "매장");
			}
			else if ((r == 0 && c == 3) || r == 1 && c == 3) {
				grid.columnHeaders.setCellData(r, c, "영업부서");
			}
			else if ((r == 0 && c == 4) || r == 1 && c == 4) {
				grid.columnHeaders.setCellData(r, c, "담당");
			}
			else if ((r == 0 && c == 5) || r == 0 && c == 6 || r == 0 && c == 7) {
				grid.columnHeaders.setCellData(r, c, "조회일");
			}
			else if ((r == 1 && c == 5)) {
				grid.columnHeaders.setCellData(r, c, "순매출액 [원]");
			}
			else if ((r == 1 && c == 6)) {
				grid.columnHeaders.setCellData(r, c, "내장객 [명]");
			}
			else if ((r == 1 && c == 7)) {
				grid.columnHeaders.setCellData(r, c, "객단가[원/명]");
			}
			else if ((r == 0 && c == 8) || r == 0 && c == 9 || r == 0 && c == 10) {
				grid.columnHeaders.setCellData(r, c, "조회월");
			}
			else if ((r == 1 && c == 8)) {
				grid.columnHeaders.setCellData(r, c, "순매출액 [원]");
			}
			else if ((r == 1 && c == 9)) {
				grid.columnHeaders.setCellData(r, c, "내장객 [명]");
			}
			else if ((r == 1 && c == 10)) {
				grid.columnHeaders.setCellData(r, c, "객단가[원/명]");
			}
			else {
				grid.columnHeaders.setCellData(r, c, "1 Header");
			}
			grid.columns[c].allowMerging = true;
			grid.columns[c].align = "center";
		}
	}

	// 해더의 vertical align 설정
	grid.formatItem.addHandler(function (s, e) {
		if (e.panel.cellType === wijmo.grid.CellType.ColumnHeader) {
			e.cell.innerHTML = '<div>' + e.cell.innerHTML + '</div>';
			wijmo.setCss(e.cell.children[0], {
				position: 'relative',
				top: '50%',
				transform: 'translateY(-50%)',
				fontSize:'12px',
				color:'#222',
				fontWeight:'bold'
			});
		}
	});

    // add data array to scope
	$scope.data = data;

  }

  ]);
</script>