<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.m-tbl {height:185px; margin:10px 0 30px; border-bottom:1px solid #ddd; overflow:hidden; overflow-y:auto}
.m-tbl table {width:100%}
.m-tbl table th {font-size:13px; padding:10px; text-align:center; border:1px solid #ddd; background-color:#e8e8e8; color:#222}
.m-tbl table td {font-size:13px; padding:8px; text-align:center; border:1px solid #ddd; color:#222}
.m-tbl table tr:nth-child(even) {background-color:#f7f7f7}
.m-tbl table tr:last-child td {border-bottom:0}
.m-tbl.t2 {height:auto; border-bottom:0}
.m-tbl.t2 table td {text-align:right}
.m-tbl.t2 table tr:nth-child(even) {background-color:#fff}
.m-tbl.t2 table tr:last-child td {height:auto; border:1px solid #ddd}

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
.type_Green .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#00ba8b}
.type_Green .userInfo.t2 .userId {border-left-color:#00a279}
.type_Orange .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#ff9d39}
.type_Orange .userInfo.t2 .userId {border-left-color:#ee8418}
.type_Blue .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#1e88e5}
.type_Blue .userInfo.t2 .userId {border-left-color:#127bd8}
.type_Purple .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#ae63ff}
.type_Purple .userInfo.t2 .userId {border-left-color:#8c38e6}

.fixedMenu.t2 {position:fixed; top:50px; left:220px; right:0; z-index:97; box-shadow:0 0 0 0 #ff9d39}
.fixedMenu.t2 ul li a {padding:0 35px 0 10px !important}
.fixedMenu.t2 ul li a.btn_close {padding-right:0 !important; top:16px !important; right:10px !important}

</style>

		<div class="subCon" ng-controller="spTonyCtrl">

			<div class="tonyAdd">
				<a href="/sample/tonymory/sample41.sb" class="tonyMainlogo">
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
							<a href="/sample/tonymory/sample41.sb" class="on">메인화면</a>
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
							<a href="/sample/tonymory/sample47.sb">당일 매출 현황</a>
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

      <span class="s18 fl bk lh30">공지사항</span>
      <div class="fr">
          <button type="button" class="btn_blue">재조회</button>
      </div>
      <div class="clearfix"></div>

      <div class="m-tbl">
          <table>
              <colgroup>
              <col class="w10" />
              <col class="w10" />
              <col class="w40" />
              <col class="w10" />
              <col class="w10" />
              <col class="w20" />
              </colgroup>
              <thead>
                  <tr>
                      <th>No.</th>
                      <th>부서</th>
                      <th>제목</th>
                      <th>이름</th>
                      <th>조회수</th>
                      <th>공지기간</th>
                  </tr>
              </thead>
              <tbody>
                  <tr>
                      <td>1</td>
                      <td>물류</td>
                      <td class="tl">[물류공지] 3월 25일 상품입고 내역</td>
                      <td>오문영</td>
                      <td>711</td>
                      <td>2019-03-25 ~ 2019-04-01</td>
                  </tr>
                  <tr>
                      <td>2</td>
                      <td>본사</td>
                      <td class="tl">[공지] 점간이동게시판 화면 추가</td>
                      <td>정병수</td>
                      <td>935</td>
                      <td>2019-03-25 ~ 2019-04-25</td>
                  </tr>
                  <tr>
                      <td>3</td>
                      <td>본사</td>
                      <td class="tl">[신제품공지] 더촉촉그린티폼클렌저2</td>
                      <td>김형조</td>
                      <td>142</td>
                      <td>2019-03-25 ~ 2019-05-31</td>
                  </tr>
                  <tr>
                      <td>4</td>
                      <td>본사</td>
                      <td class="tl">[구매팀] 차주 주요제품 입고리스트</td>
                      <td>백종혁</td>
                      <td>1350</td>
                      <td>2019-03-22 ~ 2019-03-29</td>
                  </tr>
                  <tr>
                      <td>5</td>
                      <td>물류</td>
                      <td class="tl">[물류공지] 3월 22일 신상품 입고내역(오후공지)</td>
                      <td>오문영</td>
                      <td>142</td>
                      <td>2019-03-22 ~ 2019-03-29</td>
                  </tr>
                  <tr>
                      <td>6</td>
                      <td>물류</td>
                      <td class="tl">[물류공지] 3월 25일 상품입고 내역</td>
                      <td>오문영</td>
                      <td>711</td>
                      <td>2019-03-25 ~ 2019-04-01</td>
                  </tr>
                  <tr>
                      <td>7</td>
                      <td>본사</td>
                      <td class="tl">[공지] 점간이동게시판 화면 추가</td>
                      <td>정병수</td>
                      <td>935</td>
                      <td>2019-03-25 ~ 2019-04-25</td>
                  </tr>
                  <tr>
                      <td>8</td>
                      <td>본사</td>
                      <td class="tl">[신제품공지] 더촉촉그린티폼클렌저2</td>
                      <td>김형조</td>
                      <td>142</td>
                      <td>2019-03-25 ~ 2019-05-31</td>
                  </tr>
                  <tr>
                      <td>9</td>
                      <td>본사</td>
                      <td class="tl">[구매팀] 차주 주요제품 입고리스트</td>
                      <td>백종혁</td>
                      <td>1350</td>
                      <td>2019-03-22 ~ 2019-03-29</td>
                  </tr>
                  <tr>
                      <td>10</td>
                      <td>물류</td>
                      <td class="tl">[물류공지] 3월 22일 신상품 입고내역(오후공지)</td>
                      <td>오문영</td>
                      <td>142</td>
                      <td>2019-03-22 ~ 2019-03-29</td>
                  </tr>
              </tbody>
          </table>
      </div>

      <span class="s18 fl bk lh30">전주, 전일대비 당일 매출실적</span>
      <div class="fr">
          <button type="button" class="btn_blue">엑셀</button>
      </div>
      <div class="clearfix"></div>

			<div id="grid" class="w100 mb30">
			
				<div class="wj-gridWrap mt10" style="height:420px; overflow-y: hidden;">
				  <div class="row">
					<wj-flex-grid 
						id="flexGrid"
						sticky-headers="true"
						items-source="data">

						<wj-flex-grid-column header="id" binding="id"></wj-flex-grid-column>
						<wj-flex-grid-column header="영업지역" binding="country"></wj-flex-grid-column>
						<wj-flex-grid-column header="str1" binding="str1" ></wj-flex-grid-column>
						<wj-flex-grid-column header="str2" binding="str2"></wj-flex-grid-column>
						<wj-flex-grid-column header="str3" binding="str3"></wj-flex-grid-column>
						<wj-flex-grid-column header="str4" binding="str4"></wj-flex-grid-column>
						<wj-flex-grid-column header="num1" binding="num1"></wj-flex-grid-column>
						<wj-flex-grid-column header="num2" binding="num2"></wj-flex-grid-column>
						<wj-flex-grid-column header="num3" binding="num3"></wj-flex-grid-column>
						<wj-flex-grid-column header="num4" binding="num4"></wj-flex-grid-column>
						<wj-flex-grid-column header="num5" binding="num5"></wj-flex-grid-column>
						<wj-flex-grid-column header="num6" binding="num6"></wj-flex-grid-column>
						<wj-flex-grid-column header="num7" binding="num7"></wj-flex-grid-column>
						<wj-flex-grid-column header="num8" binding="num8"></wj-flex-grid-column>
						<wj-flex-grid-column header="num9" binding="num9"></wj-flex-grid-column>
					</wj-flex-grid>

				  </div>
				</div>
			</div>
            
       <span class="s18 fl bk lh30">지난 일주일간 매출추이 [로드유통]</span>
       <div class="fr">
           <button type="button" class="btn_skyblue">로드유통</button>
           <button type="button" class="btn_skyblue">로드</button>
           <button type="button" class="btn_skyblue">유통</button>
           <button type="button" class="btn_skyblue">디지털</button>
           <button type="button" class="btn_skyblue">전체</button>
       </div>
       <div class="clearfix"></div>              

       <div class="m-tbl t2">
           <table>
               <colgroup>
               <col />
               </colgroup>
               <thead>
                   <tr>
                       <th>19/03/19 (화)</th>
                       <th>19/03/20 (수)</th>
                       <th>19/03/21 (목)</th>
                       <th>19/03/22 (금)</th>
                       <th>19/03/23 (토)</th>
                       <th>19/03/24 (일)</th>
                       <th>19/03/25 (월)</th>
                       <th>19/03/26 (화)</th>
                   </tr>
               </thead>
               <tbody>
                   <tr>
                       <td>291,989,950</td>
                       <td>263,503,100</td>
                       <td>304,301,730</td>
                       <td>291,989,950</td>
                       <td>263,503,100</td>
                       <td>304,301,730</td>
                       <td>291,989,950</td>
                       <td>263,503,100</td>
                   </tr>
                   <tr>
                       <td>13.07%</td>
                       <td>11.8%</td>
                       <td>13.63</td>
                       <td>13.07%</td>
                       <td>11.8%</td>
                       <td>13.63</td>
                       <td>13.07%</td>
                       <td>11.8%</td>
                   </tr>
               </tbody>
           </table>
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

	$scope.combobox1 = ['주문일자', '주문자', '주문번호'];
	$scope.combobox2 = ['전체', '분류1', '분류2'];

	//1. 랜덤 데이터 생성
    var txt1 = '로드1팀-이민재,로드1팀-이지용'.split(','),
        txt2 = '이민재,이지용'.split(','),
        txt3 = '서울특별시,경기도'.split(','),
        txt4 = '(동대문점),명동1호점NEW,명동2호점NEW,명동3호점,명동5호점,명동6호점,명동충무로점'.split(',')
    
	var data = [];
	for (var i = 0; i < 50; i++) {
		data.push({
			id: i,
			str1: txt1[i % txt1.length],
			str2: txt2[i % txt2.length],
			str3: txt3[i % txt3.length],
			str4: txt4[i % txt4.length],
			num1: Math.random() * 10000,
			num2: Math.random() * 100,
			num3: Math.random() * 1000,
			num4: Math.random() * 10000,
			num5: Math.random() * 100,
			num6: Math.random() * 1000,
			num7: Math.random() * 10000,
			num8: Math.random() * 100,
			num9: Math.random() * 1000
		});
	}

	//2. 그리드 초기화 및 데이터 바인딩
	var grid = new wijmo.grid.FlexGrid('#flexGrid');
		grid.itemsSource = data;
		grid.columns[0].width = 50;
		grid.columns[1].width = 130;
		grid.columns[2].width = 130;
		grid.columns[3].width = 130;
		grid.columns[4].width = 130;
		grid.columns[5].width = 130;
		grid.columns[6].width = 130;
		grid.columns[7].width = 130;
		grid.columns[8].width = 130;
		grid.columns[9].width = 130;
		grid.columns[10].width = 130;
		grid.columns[11].width = 130;
		grid.columns[12].width = 130;
		grid.columns[13].width = 130;


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
				grid.columnHeaders.setCellData(r, c, "영업지역");
			}
			else if ((r == 0 && c == 2) || r == 1 && c == 2) {
				grid.columnHeaders.setCellData(r, c, "담당자");
			}
			else if ((r == 0 && c == 3) || r == 1 && c == 3) {
				grid.columnHeaders.setCellData(r, c, "지역구분");
			}
			else if ((r == 0 && c == 4) || r == 1 && c == 4) {
				grid.columnHeaders.setCellData(r, c, "점포명");
			}
			else if ((r == 0 && c == 5) || r == 0 && c == 6 || r == 0 && c == 7) {
				grid.columnHeaders.setCellData(r, c, "전주(2019/03/19)");
			}
			else if ((r == 1 && c == 5)) {
				grid.columnHeaders.setCellData(r, c, "순매출액");
			}
			else if ((r == 1 && c == 6)) {
				grid.columnHeaders.setCellData(r, c, "영수건수");
			}
			else if ((r == 1 && c == 7)) {
				grid.columnHeaders.setCellData(r, c, "객단가");
			}
			else if ((r == 0 && c == 8) || r == 0 && c == 9 || r == 0 && c == 10) {
				grid.columnHeaders.setCellData(r, c, "전일(2019/03/25)");
			}
			else if ((r == 1 && c == 8)) {
				grid.columnHeaders.setCellData(r, c, "순매출액");
			}
			else if ((r == 1 && c == 9)) {
				grid.columnHeaders.setCellData(r, c, "영수건수");
			}
			else if ((r == 1 && c == 10)) {
				grid.columnHeaders.setCellData(r, c, "객단가");
			}
			else if ((r == 0 && c == 11) || r == 0 && c == 12 || r == 0 && c == 13) {
				grid.columnHeaders.setCellData(r, c, "당일(2019/03/25)");
			}
			else if ((r == 1 && c == 11)) {
				grid.columnHeaders.setCellData(r, c, "순매출액");
			}
			else if ((r == 1 && c == 12)) {
				grid.columnHeaders.setCellData(r, c, "영수건수");
			}
			else if ((r == 1 && c == 13)) {
				grid.columnHeaders.setCellData(r, c, "객단가");
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