﻿/*
    *
    * Wijmo Library 5.20183.550
    * http://wijmo.com/
    *
    * Copyright(c) GrapeCity, Inc.  All rights reserved.
    *
    * Licensed under the GrapeCity Commercial License.
    * sales@wijmo.com
    * wijmo.com/products/wijmo-5/license/
    *
    */
/*
 * Wijmo culture file: ko (Korean)
 */
var wijmo;
(function (wijmo) {
    // process the scenario where "wijmo" !== "window['wijmo']", for example when culture file is loaded
    // using 'import' statement in a WebPack bundled app, where "wijmo" will be local to this module.
    if (!window['wijmo']) {
        window['wijmo'] = wijmo;
    }
    wijmo.culture = window['wijmo'].culture = {
        Globalize: {
            name: 'ko',
            displayName: 'Korean',
            numberFormat: {
                '.': '.',
                ',': ',',
                '-': '-',
                '+': '+',
                '%': '%',
                percent: { pattern: ['-n%', 'n%'] },
                currency: { decimals: 0, symbol: '₩', pattern: ['-$n', '$n'] }
            },
            calendar: {
                '/': '-',
                ':': ':',
                firstDay: 0,
                days: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
                daysAbbr: ['일', '월', '화', '수', '목', '금', '토'],
                months: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                monthsAbbr: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
                am: ['오전', '오전'],
                pm: ['오후', '오후'],
                eras: ['서기'],
                patterns: {
                    d: 'yyyy-MM-dd', D: 'yyyy"년" M"월" d"일" dddd',
                    f: 'yyyy"년" M"월" d"일" dddd tt h:mm', F: 'yyyy"년" M"월" d"일" dddd tt h:mm:ss',
                    t: 'tt h:mm', T: 'tt h:mm:ss',
                    m: 'M"월" d"일"', M: 'M"월" d"일"',
                    y: 'yyyy"년" M"월"', Y: 'yyyy"년" M"월"',
                    g: 'yyyy-MM-dd tt h:mm', G: 'yyyy-MM-dd tt h:mm:ss',
                    s: 'yyyy"-"MM"-"dd"T"HH":"mm":"ss'
                },
            }
        },
        Licensing: {
            cls: '닫기',
            ctc: 'Please contact GrapeCity Korea: <a href="mailto:sales-kor@grapecity.com">sales-kor@grapecity.com</a><br/>전화:1670-0583',
            dmn: 'The Wijmo license in this application is not valid for the current domain. The license domain is <b>{licDomain}</b>; the current domain is <b>{domain}</b>.',
            evl: 'Wijmo 평가 버전 ({})',
            exp: '이 응용 프로그램에서 Wijmo 라이센스 만료 되었습니다. 라이센스 만료 날짜는  <b> {expDate:d}</b>.',
            hdr: 'Wijmo 라이센스',
            lic: '이 응용 프로그램에서 Wijmo 라이센스 유효 하지 않습니다.',
            mss: '이 응용 프로그램에서 Wijmo 라이센스 설정 되지 않았습니다.',
            prd: '이 응용 프로그램에서 Wijmo 라이센스  <b> {control} </b>  컨트롤에 대 한 유효 하지 않습니다.',
            ver: '이 응용 프로그램에서 Wijmo 라이센스 사용 중인 버전에 대 한 유효 하지 않습니다. 라이센스 버전  <b> {licVer}</b>이며 제품 버전은  <b> {version}</b>.'
        },
        Calendar: {
            ariaLabels: {
                calendar: '일정',
                monthView: '월 보기',
                yearView: '년 보기',
                prvMo: '이전 달',
                today: '오늘',
                nxtMo: '다음 달',
                prvYr: '이전 연도',
                currMo: '현재 달',
                nxtYr: '내년',
            }
        },
        DropDown: {
            ariaLabels: {
                tgl: '토글 메뉴'
            }
        },
        FlexGrid: {
            groupHeaderFormat: '{name}: <b>{value}</b> ({count:n0} 항목)',
            ariaLabels: {
                toggleDropDown: '토글 메뉴',
                toggleGroup: '전환 그룹'
            }
        },
        FlexGridDetailProvider: {
            ariaLabels: {
                toggleDetail: '전환 행 세부 사항'
            }
        },
        FlexGridFilter: {
            // aria labels
            ariaLabels: {
                edit: '열에 대 한 필터를 편집',
                dialog: '열에 대 한 필터 편집기',
                asc: '오름차순 정렬 열',
                dsc: '내림차순 정렬 열',
                search: '검색 항목 목록',
                op1: '첫 번째 조건 연산자',
                val1: '첫 번째 조건 값',
                and: '두 조건 필요',
                or: '필요 조건 중 하나가',
                op2: '두 번째 조건 연산자',
                val2: '두 번째 조건 값'
            },
            // filter
            ascending: '\u2191 상승',
            descending: '\u2193 하락',
            apply: '적용',
            cancel: '취소',
            clear: '지움',
            conditions: '조건에 따른 필터',
            values: '값에 따른 필터',
            // value filter
            search: '검색',
            selectAll: '모두 선택',
            null: '(없음)',
            // condition filter
            header: '항목 표시: 값이',
            and: '및',
            or: '또는',
            stringOperators: [
                { name: '(설정 되지 않음)', op: null },
                { name: '다음과 같을 경우', op: 0 },
                { name: '다음과 같지 않을 경우', op: 1 },
                { name: '다음의 값으로 시작하는 경우', op: 6 },
                { name: '다음의 값으로 끝나는 경우', op: 7 },
                { name: '다음의 값을 포함하는 경우', op: 8 },
                { name: '다음의 값을 포함하지 않는 경우', op: 9 }
            ],
            numberOperators: [
                { name: '(설정 되지 않음)', op: null },
                { name: '다음과 같을 경우', op: 0 },
                { name: '다음과 같지 않을 경우', op: 1 },
                { name: '다음의 값보다 큰 경우', op: 2 },
                { name: '다음의 값보다 크거나 같은 경우', op: 3 },
                { name: '다음의 값보다 작은 경우', op: 4 },
                { name: '다음의 값보다 작거나 같은 경우', op: 5 }
            ],
            dateOperators: [
                { name: '(설정 되지 않음)', op: null },
                { name: '다음과 같을 경우', op: 0 },
                { name: '다음의 값보다 앞에 있는 경우', op: 4 },
                { name: '다음의 값보다 뒤에 있는 경우', op: 2 }
            ],
            booleanOperators: [
                { name: '(설정 되지 않음)', op: null },
                { name: '다음과 같을 경우', op: 0 },
                { name: '다음과 같지 않을 경우', op: 1 }
            ]
        },
        InputDateTime: {
            ariaLabels: {
                tglDate: '토글 일정',
                tglTime: '전환 시간 목록'
            }
        },
        InputNumber: {
            ariaLabels: {
                incVal: '증가 값',
                decVal: '값을 감소'
            }
        },
        MultiSelect: {
            itemsSelected: '{count:n0} 항목 선택',
            selectAll: '모두 선택'
        },
        olap: {
            PivotFieldEditor: {
                dialogHeader: '필드 설정:',
                header: '헤더:',
                summary: '요약:',
                showAs: '표시:',
                weighBy: '여 무게:',
                sort: '정렬:',
                filter: '필터:',
                format: '형식:',
                sample: '미리 보기:',
                edit: '편집…',
                clear: '지우기',
                ok: '확인',
                cancel: '취소',
                none: '(없음)',
                sorts: {
                    asc: '오름차순',
                    desc: '내림차순'
                },
                aggs: {
                    sum: '합계',
                    cnt: '개수',
                    avg: '평균',
                    max: 'MAX',
                    min: 'MIN',
                    rng: '범위',
                    std: '표본_표준_편차',
                    var: 'VAR',
                    stdp: 'StdDevPop',
                    varp: 'VarPop',
                    first: '첫째',
                    last: '마지막'
                },
                calcs: {
                    noCalc: '계산 없음',
                    dRow: '이전 행에서 차이',
                    dRowPct: '% 이전 행에서 차이',
                    dCol: '이전 열에서 차이',
                    dColPct: '% 이전 열에서 차이',
                    dPctGrand: '그랜드 합계의 %',
                    dPctRow: '행 합계의 %',
                    dPctCol: '열 합계의 %',
                    dRunTot: '누적 합계',
                    dRunTotPct: '% 누적 합계'
                },
                formats: {
                    n0: '정수 (n0)',
                    n2: '진수 (n2)',
                    c: '통화 (c)',
                    p0: '비율 (p0)',
                    p2: '비율 (p2)',
                    n2c: '수천 (n2,)',
                    n2cc: '수백만 (n2,,)',
                    n2ccc: '수십억 (n2,,,)',
                    d: '날짜 (d)',
                    MMMMddyyyy: '월 일 년 (MMMM dd, yyyy)',
                    dMyy: '일 월 년 (d/M/yy)',
                    ddMyy: '일 월 년 (dd/M/yy)',
                    dMyyyy: '일 월 년 (M/dd/yyyy)',
                    MMMyyyy: '달 년 (MMM yyyy)',
                    MMMMyyyy: '달 년 (MMMM yyyy)',
                    yyyyQq: '올해 분기 (yyyy "Q" q)',
                    FYEEEEQU: '회계 연도 분기 ("년도" EEEE "Q" U)'
                }
            },
            PivotEngine: {
                grandTotal: '총합계',
                subTotal: '부분합'
            },
            PivotPanel: {
                fields: '보고서에 추가할 필드 선택:',
                drag: '아래 영역 사이에 필드를 끌어 놓으십시오:',
                filters: '필터',
                cols: '열',
                rows: '행',
                vals: '값',
                defer: '업데이트 지연',
                update: '업데이트'
            },
            _ListContextMenu: {
                up: '위로 이동',
                down: '아래로 이동',
                first: '처음으로 이동',
                last: '끝으로 이동',
                filter: '보고서 필터로 이동',
                rows: '행 레이블로 이동',
                cols: '열 레이블로 이동',
                vals: '값으로 이동',
                remove: '필드 제거',
                edit: '필드 설정…',
                detail: '자세히 보기…'
            },
            PivotChart: {
                by: '기준',
                and: 'and'
            },
            DetailDialog: {
                header: '세부 정보 보기:',
                ok: '확인',
                items: '{cnt:n0} 항목',
                item: '{cnt} 항목',
                row: 'Row',
                col: '세로 막대형'
            },
            Slicer: {
                multiSelect: '다중 선택',
                clearFilter: '필터 지우기'
            }
        },
        Viewer: {
            cancel: '취소',
            ok: '확인',
            bottom: '아래쪽:',
            top: '위쪽:',
            right: '오른쪽:',
            left: '왼쪽:',
            margins: '여백(인치)',
            orientation: '방향:',
            paperKind: '종이 종류:',
            pageSetup: '설정 페이지',
            landscape: '가로',
            portrait: '세로',
            pageNumber: '페이지 번호',
            zoomFactor: '확대/축소 비율',
            paginated: '인쇄 레이아웃',
            print: '인쇄',
            search: '검색',
            matchCase: '대/소문자 구분',
            wholeWord: '단어 단위로',
            searchResults: '검색 결과',
            previousPage: '이전 페이지',
            nextPage: '다음 페이지',
            firstPage: '첫 페이지',
            lastPage: '마지막 페이지',
            backwardHistory: '뒤로',
            forwardHistory: '앞으로',
            pageCount: '페이지 수',
            selectTool: '도구 선택',
            moveTool: '이동 도구',
            continuousMode: '연속 페이지 보기',
            singleMode: '단일 페이지 보기',
            wholePage: '전체 페이지를 맞춤된',
            pageWidth: '페이지 너비에 맞게',
            zoomOut: '축소',
            zoomIn: '확대',
            rubberbandTool: '선택 영역 확대',
            magnifierTool: '돋보기',
            rotatePage: '페이지 회전',
            rotateDocument: '문서 회전',
            exports: '내보내기',
            fullScreen: '전체 화면',
            exitFullScreen: '전체 화면 끝내기',
            hamburgerMenu: '도구',
            showSearchBar: '검색 창 표시',
            viewMenu: '레이아웃 옵션',
            searchOptions: '검색 옵션',
            matchCaseMenuItem: '대/소문자 구분',
            wholeWordMenuItem: '단어 단위로',
            thumbnails: '페이지 축소판',
            outlines: '문서 구조',
            loading: '로드 중입니다…',
            pdfExportName: 'Adobe PDF',
            docxExportName: 'Open XML 워드',
            xlsxExportName: 'Open XML Excel',
            docExportName: 'Microsoft Word',
            xlsExportName: 'Microsoft Excel',
            mhtmlExportName: '웹 보관 파일 (MHTML)',
            htmlExportName: 'HTML 문서',
            rtfExportName: 'RTF 문서',
            metafileExportName: '압축 된 메타 파일',
            csvExportName: 'CSV',
            tiffExportName: 'Tiff 이미지',
            bmpExportName: 'BMP 이미지',
            emfExportName: '향상 된 메타 파일',
            gifExportName: 'GIF 이미지',
            jpgExportName: 'JPEG 이미지',
            jpegExportName: 'JPEG 이미지',
            pngExportName: 'PNG 이미지',
            abstractMethodException: '이 추상 메서드는, 그것을 구현 하시기 바랍니다.',
            cannotRenderPageNoViewPage: '문서 소스 없이 페이지 및 보기 페이지 렌더링 수 없습니다.',
            cannotRenderPageNoDoc: '문서 소스 없이 페이지 및 보기 페이지 렌더링 수 없습니다.',
            exportFormat: '내보내기 형식:',
            exportOptionTitle: '내보내기 옵션',
            documentRestrictionsGroup: '문서 제한',
            passwordSecurityGroup: '암호 보안',
            outputRangeGroup: '출력 범위',
            documentInfoGroup: '문서 정보',
            generalGroup: '일반',
            docInfoTitle: '제목이',
            docInfoAuthor: '만든 이',
            docInfoManager: '관리자',
            docInfoOperator: '연산자',
            docInfoCompany: '회사',
            docInfoSubject: '주제',
            docInfoComment: '보고 사항',
            docInfoCreator: '만든 이',
            docInfoProducer: '제작자',
            docInfoCreationTime: '만든 시간',
            docInfoRevisionTime: '수정 시간',
            docInfoKeywords: '키워드가',
            embedFonts: '트루타입 글꼴 포함',
            pdfACompatible: 'PDF/A 호환 (레벨 2B)',
            useCompression: '압축을 사용 하 여',
            useOutlines: '아웃 라인을 생성',
            allowCopyContent: '내용 복사 또는 추출 허용',
            allowEditAnnotations: '주석 편집 허용',
            allowEditContent: '콘텐츠 편집을 허용합니다',
            allowPrint: '인쇄 허용',
            ownerPassword: '권한 (소유자) 암호:',
            userPassword: '문서 열기 (사용자) 비밀 번호:',
            encryptionType: '암호화 수준:',
            paged: '페이지됨',
            showNavigator: '탐색 창 표시',
            navigatorPosition: '네비게이터 위치',
            singleFile: '단일 파일',
            tolerance: '허용 오차 텍스트 범위 (포인트)를 감지 하는 경우:',
            pictureLayer: '별도 이미지 레이어를 사용 하 여',
            metafileType: '메타 파일 형식:',
            monochrome: '흑백',
            resolution: '해상도:',
            outputRange: '페이지 범위:',
            outputRangeInverted: '반전',
            showZoomBar: '확대/축소 막대',
            searchPrev: '이전 검색',
            searchNext: '다음 검색',
            checkMark: '\u2713',
            exportOk: '수출…',
            cannotSearch: '검색을 위해서는 문서 소스를 지정해야합니다.',
            parameters: '매개 변수',
            requiringParameters: '매개 변수를 입력 하십시오.',
            nullParameterError: '값은 null일 수 없습니다.',
            invalidParameterError: '잘못된 입력입니다.',
            parameterNoneItemsSelected: '(없음)',
            parameterAllItemsSelected: '(모두)',
            parameterSelectAllItemText: '(모두 선택)',
            selectParameterValue: '(값을 선택)',
            apply: '적용',
            errorOccured: '오류가 발생했습니다.'
        },
        FlexSheet: {
            insertRow: '행 삽입',
            deleteRow: '행 삭제',
            insertCol: '열 삽입',
            deleteCol: 'Delete Column',
            convertTable: '표 변환',
            copyCells: '셀 복사',
            fillSeries: '연속 데이터 채우기',
            fillFormat: '서식만 채우기',
            fillWithoutFormat: '서식 없이 채우기'
        }
    };
    var updc = window['wijmo']._updateCulture;
    if (updc) {
        updc();
    }
})(wijmo || (wijmo = {}));
;

