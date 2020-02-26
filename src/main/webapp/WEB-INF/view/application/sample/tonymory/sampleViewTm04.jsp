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

.searchTbl {width:100%; border-top:1px solid #ccc; min-width:auto}
.searchTbl th,.searchTbl td {padding:2px}
.searchTbl th {text-align:center}
.searchTbl .btn_grayS {padding:0 7px; font-size:13px}

.menuControl {display:none}
.userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#ff9d39}

.type_Green .userInfo.t2 {position:fixed; top:0; right:0; left:0; z-index:98; background-color:#00ba8b}
.type_Green .userId {border-left-color:#00a279}

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
							<a href="/sample/tonymory/sample43.sb" class="on">주문등록</a>
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



			<div class="wj-TblWrap">
				<!-- left -->
				<div class="w45 fl">

					<div class="wj-TblWrapBr mr10 pd20">
						<p class="bk lh30">주문등록</p>
						<p class="s12 gr mb10">※ 매장파일등록: [매장코드][상품코드][수량][비고] 순으로 등록.</p>
						<p class="s12 gr mb10">※ 파일등록: [상품코드][수량]순으로 입력된 파일을 등록.</p>	

						<table class="searchTbl">
							<colgroup>
							<col class="w10" />
							<col class="w50" />
							<col class="w10" />
							<col class="w30" />
							</colgroup>
							<tbody>
								<tr>
									<!-- 배송일자 -->
									<th>배송일자</th>
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
										<span class="fl">
											<a href="#" class="btn_grayS ml5" id="">조회</a>
										</span>
									</td>
									<!-- 매장형태 -->
									<th>매장형태</th>
									<td>
										<div class="sb-select">
											<span class="txtIn w90px">
												<wj-combo-box
													items-source="combobox2"
													is-editable="false">
												</wj-combo-box>
											</span>
										</div>
									</td>						
								</tr>
							</tbody>
						</table>
						<div class="updownSet oh mb10 mt10">
							<button id="" class="btn_skyblue">전체조회</button>
							<button id="" class="btn_skyblue">신규등록</button>
							<button id="" class="btn_skyblue">전표삭제</button>
							<button id="" class="btn_skyblue">매장복사</button>
							<button id="" class="btn_skyblue">확정요청</button>
							<button id="" class="btn_skyblue">매장파일등록</button>
						</div>	

						<div class="wj-gridWrap mt10" style="height:400px; overflow-y: hidden;">
							<div class="row">
								<wj-flex-grid
									initialized="initGrid(s,e)"
									sticky-headers="true"
									items-source="data">

									<wj-flex-grid-column header="선택" binding="chk" width="50"></wj-flex-grid-column>
									<wj-flex-grid-column header="No" binding="id" width="50" align="center"></wj-flex-grid-column>
									<wj-flex-grid-column header="매장명" binding="str2" width="100"></wj-flex-grid-column>
									<wj-flex-grid-column header="배송일자" binding="date"  width="150"align="center"></wj-flex-grid-column>
									<wj-flex-grid-column header="전표번호" binding="amount" width="120"></wj-flex-grid-column>
									<wj-flex-grid-column header="주문가능수량" binding="num" width="120"></wj-flex-grid-column>
									<wj-flex-grid-column header="수량" binding="num" width="120" aggregate="Sum"></wj-flex-grid-column>
								</wj-flex-grid>
							</div>
						</div>

					</div>
				</div>
			
				<!-- right -->
				<div class="w55 fr">

					<div class="wj-TblWrapBr ml10 pd20">
						<p class="s12 red mb10">※ 저장버튼을 눌러야 수정이 됩니다.</p>				
						<span class="bk lh30">전표상세</span><button id="" class="btn_gray fr mb10">닫기</button>	

						<table class="searchTbl">
							<colgroup>
							<col class="w15" />
							<col class="w35" />
							<col class="w15" />
							<col class="w35" />
							</colgroup>
							<tbody>
								<tr>
									<!-- 매장명 -->
									<th>매장명</th>
									<td>
										<input type="text" id="" name="" ng-model="" class="sb-input" placeholder="(신촌점)"/>
									</td>
									<!-- 배송일 -->
									<th>배송일</th>
									<td>
										<div class="sb-select">
											<span class="txtIn">
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
								</tr>
								<tr>
									<!-- 전표번호 -->
									<th>전표번호</th>
									<td>
										<input type="text" id="" name="" ng-model="" class="sb-input" placeholder=""/>
									</td>
									<!-- 배송지 -->
									<th>배송지</th>
									<td>
										<input type="text" id="" name="" ng-model="" class="sb-input w100px fl" placeholder=""/>
										<span class="fl">
											<a href="#" class="btn_grayS ml5" id="">조회</a>
										</span>
									</td>
								</tr>
								<tr>
									<!-- 수령인 -->
									<th>수령인</th>
									<td>
										<input type="text" id="" name="" ng-model="" class="sb-input" placeholder=""/>
									</td>
									<!-- 휴대폰번호 -->
									<th>휴대폰번호</th>
									<td>
										<input type="text" id="" name="" ng-model="" class="sb-input" placeholder=""/>
									</td>
								</tr>
								<tr>
									<!-- 별도배송여부 -->
									<th>별도배송여부</th>
									<td>
										<span class="chk ml10">
											<input type="checkbox" id="chkDt"/>
											<label for="chkDt">별도배송</label>
										</span>
									</td>
									<!-- 비고 -->
									<th>비고</th>
									<td>
										<input type="text" id="" name="" ng-model="" class="sb-input" placeholder=""/>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="updownSet oh mb10 mt10">
							<button id="" class="btn_skyblue">상품추가</button>
							<button id="" class="btn_skyblue">상품삭제</button>
							<button id="" class="btn_skyblue">저장</button>
							<button id="" class="btn_skyblue">파일등록</button>
						</div>		

						<div class="wj-gridWrap mt10" style="height:370px; overflow-y: hidden;">
							<div class="row">
								<wj-flex-grid 
									initialized="initGrid(s,e)"
									sticky-headers="true"
									items-source="data">

									<wj-flex-grid-column header="선택" binding="chk" width="50"></wj-flex-grid-column>
									<wj-flex-grid-column header="No" binding="id" width="50" align="center"></wj-flex-grid-column>
									<wj-flex-grid-column header="상품명" binding="str1" width="100"></wj-flex-grid-column>
									<wj-flex-grid-column header="수량" binding="num"  width="100" aggregate="Sum"></wj-flex-grid-column>
									<wj-flex-grid-column header="입수" binding="amount" width="100" aggregate="Sum"></wj-flex-grid-column>
									<wj-flex-grid-column header="주문가능수량" binding="num" width="100" aggregate="Sum"></wj-flex-grid-column>
									<wj-flex-grid-column header="최대가능수량" binding="amount" width="100" aggregate="Sum"></wj-flex-grid-column>
									<wj-flex-grid-column header="공급가" binding="amount" width="100" aggregate="Sum"></wj-flex-grid-column>
									<wj-flex-grid-column header="금액" binding="num" width="100"></wj-flex-grid-column>
									<wj-flex-grid-column header="할당잔량" binding="num" width="100" aggregate="Sum"></wj-flex-grid-column>
								</wj-flex-grid>
							</div>
						</div>

					</div>
				</div>	
				
				<div class="clearfix"></div>
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


	// generate some random data
    var txt1 = '퍼펙트립스 핑크쇼킹,퍼펙트립스 레드쇼킹,퍼펙트립스 루비쇼킹,퍼펙트립스 오렌지쇼킹'.split(','),
    	txt2 = '신촌점, 이대점,강남점,역삼점,홍대점,잠실점'.split(','),
        data = [];
    for (var i = 0; i < 200; i++) {
        data.push({
            id: i,
            str1: txt1[i % txt1.length],
            str2: txt2[i % txt2.length],
            date: new Date(2019, i % 12, i % 28),
            amount: Math.random() * 1000,
            num: Math.random() * 100,
            chk: i % 0 == 0
        });
    }

	$scope.data = data;

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {

		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');

	}	

	
  }

  ]);
</script>