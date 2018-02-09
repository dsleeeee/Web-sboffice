<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<body>

<%--   <tiles:insertAttribute name="header" /> --%>

  <div class="wrap type_Blue">
    
    <%-- 왼쪽 메뉴 --%>
    <nav id="_nav" class="menuOpen">
    
      <!--로고영역-->
      <h1><a href="#" class="on"><span><img src="/resource/solbipos/img/main/logo_main.png" alt="" /></span></a></h1><!-- 활성화 : class="on" -->
      <!--//로고영역-->
      
      
      <!--전체,즐겨찾기-->
      <div class="menuTab">
          <p class="all"><a href="#" id="_all" class="on"><span>전체</span></a></p><!-- 활성화 : class="on" -->
          <p class="favorite"><a href="#" id="_favorite"><span>즐겨찾기</span></a></p>
          
      </div>
      <!--//전체,즐겨찾기-->
      
      
      
      
      <div class="menuTree">
               
          <!--위즈모 메뉴-->
          <div>
              <div id="theTree">
              </div>
          </div>
          <!--//위즈모 메뉴-->
          
      </div>    
      
    </nav>
    
    
    
    
    
    
    
    
    
    
    
    
    <%-- 오른쪽 메인 부분 --%>
    <div class="contents">
    
      <!--사용자정보영역-->   
      <div class="topBar">
          <div class="menuControl">
              <a href="#" id="_arrow" class="arrowOpen"><span></span></a>
          </div>
          <div class="userInfo">
              <a href="#" class="userNotice"><span>2</span></a><!--새로운 공지 있는경우 span추가-->
              <a href="#" class="userId"><span>kcpmaster</span></a>
          </div>
      </div>
      <!--//사용자정보영역-->
    
      
      <!--고정메뉴-->
      <div class="fixedMenu">
          <!--고정메뉴 없는경우-->
          <p class="empty" style="display:none;">즐겨찾기에서 고정메뉴를 등록하여 편리하게 사용하세요!</p>
          <!--//고정메뉴 없는경우-->
          
          <!--고정메뉴 있는경우-->
          <nav>                
              <ul>
                  <li><a href="#" class="on">POS 로그인현황</a><a href="#" class="btn_close"></a></li>
                  <li><a href="#">설치업체관리</a><a href="#" class="btn_close"></a></li>
                  <li><a href="#">미사용 라이센스 조회</a><a href="#" class="btn_close"></a></li>
              </ul>
              <div class="moveBtn">
                  <a href="#" class="mL" title="왼쪽으로 메뉴 이동"></a>
                  <a href="#" class="mR" title="오른쪽으로 메뉴 이동"></a>
              </div>
          </nav>
          <!--고정메뉴 있는경우-->    
      </div>
      <!--//고정메뉴-->
    
    
    </div>
  
  </div>
  
<%--     <tiles:insertAttribute name="content" /> --%>

</body>

<script type="text/javascript">

onload = function() {

	// create the tree
  var tree = new wijmo.nav.TreeView('#theTree', {
  	itemsSource: getData(),
		displayMemberPath: 'header',
    childItemsPath: 'items',
    loadedItems: function(s, e) {
    	s.collapseToLevel(0);
    }  
	});

  
  // get the tree data
  function getData() { 
		return [
    	{ header: '포스관리', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴 프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드 임의변경' }]
				}]
			},
      { header: '가맹점관리', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드 임의변경' }]
				}]
			},
			{ header: '시스템관리', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드 임의변경' }]
				}]
			},
			{ header: '기초관리', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드 임의변경' }]
				}]
			},
			{ header: '매출관리', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드 임의변경' }]
				}]
			},
			{ header: '회원관리', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드 임의변경' }]
				}]
			}, 
			{ header: '수불관리', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드 임의변경' }]
				}]
			},
			{ header: '정산관리', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드' }]
				}]
			},
			{ header: '부가서비스', items: [
        { header: 'POS 버전관리', items: [
				 { header: '버전관리' },
          { header: '수신현황', newItem: true },
          { header: '버전제한' }]
				},
        { header: '프로그램 메뉴', items: [
				 { header: '프로그램 메뉴관리' },
          { header: '저장메뉴 템플릿', newItem: true },
          { header: '모바일 메뉴관리' }]
				},  
        { header: '본사/매장 마스터', items: [
				 { header: '본사정보관리' },
          { header: '본사정보조회', newItem: true },
          { header: '매장현황' },
          { header: '패스워드 임의변경 패스워드 임의변경 패스워드' }]
				}]
			},
		];
	}
}
</script>












