package kr.co.solbipos.utils.excel;

import org.apache.poi.hssf.util.HSSFColor;

/**
 * 엑셀파일 생성용 상수 모음
 */
public final class ExcelConst {
    public static final int CELL_FORMAT_IDX_KEY = 0;
    public static final int CELL_FORMAT_IDX_TYPE = 1;
    public static final int CELL_FORMAT_IDX_TITLE = 2;
    public static final int CELL_FORMAT_IDX_COLOR = 3;
    public static final int CELL_FORMAT_IDX_WIDTH = 4;
    public static final int CELL_FORMAT_IDX_GUBN = 5;

    public static final short CELL_HEIGHT_TITLE = 0x200;
    public static final short CELL_HEIGHT_NORMAL = 0x150;

    public static final short CELL_TYPE_SEQ = 00;
    public static final short CELL_TYPE_STRING = 10;
    public static final short CELL_TYPE_COMMA_NUMBER = 20; // ###,###,###
    public static final short CELL_TYPE_NUMBER = 30; // #########
    public static final short CELL_TYPE_DOUBLE = 40; // #,##0.##
    public static final short CELL_TYPE_PERCENT = 50; // 0.001 로 넣으면 0.1% 로 표현
    public static final short CELL_TYPE_FORMULA = 60; // 일반형으로 처리하기
    public static final short CELL_TYPE_FORMULA_RIGHT = 90; // 우측정렬 일반형
    public static final short CELL_TYPE_DATE = 130; // yyyyMMdd
    public static final short CELL_TYPE_DATETIME = 140; // yyyyMMddHHmmss

    // TODO 2007버전(xlsx)에서 지원여부 확인
    public static final short CELL_TYPE_COMMA_NUMBER_TEXT = 70; // ###,###,###
    public static final short CELL_TYPE_NUMBER_RIGHT = 80; // 소수점 ###,###.00 숫자형일때 ###,###,###
    public static final short CELL_TYPE_FORMULA_TOTAL = 100; // 원달러 구분하여 합계 - 일반값이지만 원합계 달러합계까지 처리
    public static final short CELL_TYPE_NUMBER_TOTAL = 110; // 원달러 구분하여 합계 - 일반값이지만 원화카운트합계 달러카운트합계
                                                            // 처리
    public static final short CELL_TYPE_SUM = 120; // 일련번호 없는 합계일 경우 공란으로 비워둠

    public static final short COLOR_OLIVE_GREEN = HSSFColor.OLIVE_GREEN.index;
    public static final short COLOR_AQUA = HSSFColor.AQUA.index;
    public static final short COLOR_BLACK = HSSFColor.BLACK.index;
    public static final short COLOR_BLUE = HSSFColor.BLUE.index;
    public static final short COLOR_BLUE_GREY = HSSFColor.BLUE_GREY.index;
    public static final short COLOR_BRIGHT_GREEN = HSSFColor.BRIGHT_GREEN.index;
    public static final short COLOR_BROWN = HSSFColor.BROWN.index;
    public static final short COLOR_CORAL = HSSFColor.CORAL.index;
    public static final short COLOR_CORNFLOWER_BLUE = HSSFColor.CORNFLOWER_BLUE.index;
    public static final short COLOR_DARK_BLUE = HSSFColor.DARK_BLUE.index;
    public static final short COLOR_DARK_GREEN = HSSFColor.DARK_GREEN.index;
    public static final short COLOR_DARK_RED = HSSFColor.DARK_RED.index;
    public static final short COLOR_DARK_TEAL = HSSFColor.DARK_TEAL.index;
    public static final short COLOR_DARK_YELLOW = HSSFColor.DARK_YELLOW.index;
    public static final short COLOR_GOLD = HSSFColor.GOLD.index;
    public static final short COLOR_GREEN = HSSFColor.GREEN.index;
    public static final short COLOR_GREY_25_PERCENT = HSSFColor.GREY_25_PERCENT.index;
    public static final short COLOR_GREY_40_PERCENT = HSSFColor.GREY_40_PERCENT.index;
    public static final short COLOR_GREY_50_PERCENT = HSSFColor.GREY_50_PERCENT.index;
    public static final short COLOR_GREY_80_PERCENT = HSSFColor.GREY_80_PERCENT.index;
    public static final short COLOR_INDIGO = HSSFColor.INDIGO.index;
    public static final short COLOR_LAVENDER = HSSFColor.LAVENDER.index;
    public static final short COLOR_LEMON_CHIFFON = HSSFColor.LEMON_CHIFFON.index;
    public static final short COLOR_LIGHT_BLUE = HSSFColor.LIGHT_BLUE.index;
    public static final short COLOR_LIGHT_CORNFLOWER_BLUE = HSSFColor.LIGHT_CORNFLOWER_BLUE.index;
    public static final short COLOR_LIGHT_GREEN = HSSFColor.LIGHT_GREEN.index;
    public static final short COLOR_LIGHT_ORANGE = HSSFColor.LIGHT_ORANGE.index;
    public static final short COLOR_LIGHT_TURQUOI_SE = HSSFColor.LIGHT_TURQUOISE.index;
    public static final short COLOR_LIGHT_YELLOW = HSSFColor.LIGHT_YELLOW.index;
    public static final short COLOR_LIME = HSSFColor.LIME.index;
    public static final short COLOR_MAROON = HSSFColor.MAROON.index;
    public static final short COLOR_ORANGE = HSSFColor.ORANGE.index;
    public static final short COLOR_ORCHID = HSSFColor.ORCHID.index;
    public static final short COLOR_PALE_BLUE = HSSFColor.PALE_BLUE.index;
    public static final short COLOR_PINK = HSSFColor.PINK.index;
    public static final short COLOR_PLUM = HSSFColor.PLUM.index;
    public static final short COLOR_RED = HSSFColor.RED.index;
    public static final short COLOR_ROSE = HSSFColor.ROSE.index;
    public static final short COLOR_ROYAL_BLUE = HSSFColor.ROYAL_BLUE.index;
    public static final short COLOR_SEA_GREEN = HSSFColor.SEA_GREEN.index;
    public static final short COLOR_SKY_BLUE = HSSFColor.SKY_BLUE.index;
    public static final short COLOR_TAN = HSSFColor.TAN.index;
    public static final short COLOR_TEAL = HSSFColor.TEAL.index;
    public static final short COLOR_TURQUOISE = HSSFColor.TURQUOISE.index;
    public static final short COLOR_VIOLET = HSSFColor.VIOLET.index;
    public static final short COLOR_WHITE = HSSFColor.WHITE.index;
    public static final short COLOR_YELLOW = HSSFColor.YELLOW.index;
}
