﻿/*
    *
    * Wijmo Library 5.20173.405
    * http://wijmo.com/
    *
    * Copyright(c) GrapeCity, Inc.  All rights reserved.
    *
    * Licensed under the GrapeCity Commercial License.
    * sales@wijmo.com
    * wijmo.com/products/wijmo-5/license/
    *
    */
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            "use strict";
            // simplified version of experimental Math.trunc()
            // Math.trunc() on MDN: http://mzl.la/1BY3vHE
            function _trunc(value) {
                wijmo.asNumber(value, true, false);
                return value > 0 ? Math.floor(value) : Math.ceil(value);
            }
            finance._trunc = _trunc;
            function _sum(values) {
                if (arguments.length > 1) {
                    values = Array.prototype.slice.call(arguments);
                }
                wijmo.asArray(values, false);
                return values.reduce(function (prev, curr) { return prev + wijmo.asNumber(curr); }, 0);
            }
            finance._sum = _sum;
            function _average(values) {
                if (arguments.length > 1) {
                    values = Array.prototype.slice.call(arguments);
                }
                wijmo.asArray(values, false);
                return _sum(values) / values.length;
            }
            finance._average = _average;
            function _minimum(values) {
                if (arguments.length > 1) {
                    values = Array.prototype.slice.call(arguments);
                }
                wijmo.asArray(values, false);
                return Math.min.apply(null, values);
            }
            finance._minimum = _minimum;
            function _maximum(values) {
                if (arguments.length > 1) {
                    values = Array.prototype.slice.call(arguments);
                }
                wijmo.asArray(values, false);
                return Math.max.apply(null, values);
            }
            finance._maximum = _maximum;
            function _variance(values) {
                if (arguments.length > 1) {
                    values = Array.prototype.slice.call(arguments);
                }
                wijmo.asArray(values, false);
                var mean = _average(values), diffs = values.map(function (value) { return Math.pow(value - mean, 2); });
                return _average(diffs);
            }
            finance._variance = _variance;
            function _stdDeviation(values) {
                if (arguments.length > 1) {
                    values = Array.prototype.slice.call(arguments);
                }
                wijmo.asArray(values, false);
                return Math.sqrt(_variance(values));
            }
            finance._stdDeviation = _stdDeviation;
            // calculate Average True Range for a set of financial data
            function _avgTrueRng(highs, lows, closes, period) {
                if (period === void 0) { period = 14; }
                wijmo.asArray(highs, false);
                wijmo.asArray(lows, false);
                wijmo.asArray(closes, false);
                wijmo.asInt(period, false, true);
                var trs = _trueRng(highs, lows, closes, period), len = Math.min(highs.length, lows.length, closes.length, trs.length), atrs = [];
                wijmo.assert(len > period && period > 1, "Average True Range period must be an integer less than the length of the data and greater than one.");
                for (var i = 0; i < len; i++) {
                    wijmo.asNumber(highs[i], false);
                    wijmo.asNumber(lows[i], false);
                    wijmo.asNumber(closes[i], false);
                    wijmo.asNumber(trs[i], false);
                    if ((i + 1) === period) {
                        atrs.push(_average(trs.slice(0, period)));
                    }
                    else if ((i + 1) > period) {
                        atrs.push(((period - 1) * atrs[atrs.length - 1] + trs[i]) / period);
                    }
                }
                return atrs;
            }
            finance._avgTrueRng = _avgTrueRng;
            // calculate True Range for a set of financial data
            function _trueRng(highs, lows, closes, period) {
                if (period === void 0) { period = 14; }
                wijmo.asArray(highs, false);
                wijmo.asArray(lows, false);
                wijmo.asArray(closes, false);
                wijmo.asInt(period, false, true);
                var len = Math.min(highs.length, lows.length, closes.length), trs = [];
                wijmo.assert(len > period && period > 1, "True Range period must be an integer less than the length of the data and greater than one.");
                for (var i = 0; i < len; i++) {
                    wijmo.asNumber(highs[i], false);
                    wijmo.asNumber(lows[i], false);
                    wijmo.asNumber(closes[i], false);
                    if (i === 0) {
                        trs.push(highs[i] - lows[i]);
                    }
                    else {
                        trs.push(Math.max(highs[i] - lows[i], Math.abs(highs[i] - closes[i - 1]), Math.abs(lows[i] - closes[i - 1])));
                    }
                }
                return trs;
            }
            finance._trueRng = _trueRng;
            // simple moving average
            function _sma(values, period) {
                wijmo.asArray(values, false);
                wijmo.asNumber(period, false, true);
                wijmo.assert(values.length > period && period > 1, "Simple Moving Average period must be an integer less than the length of the data and greater than one.");
                var retval = [];
                for (var i = period; i <= values.length; i++) {
                    retval.push(_average(values.slice(i - period, i)));
                }
                return retval;
            }
            finance._sma = _sma;
            // exponential moving average
            function _ema(values, period) {
                wijmo.asArray(values, false);
                wijmo.asNumber(period, false, true);
                wijmo.assert(values.length > period && period > 1, "Exponential Moving Average period must be an integer less than the length of the data and greater than one.");
                var retval = [], multiplier = 2 / (period + 1), smas = _sma(values, period);
                values = values.slice(period - 1);
                for (var i = 0; i < values.length; i++) {
                    if (i === 0) {
                        retval.push(smas[0]);
                    }
                    else {
                        retval.push((values[i] - retval[i - 1]) * multiplier + retval[i - 1]);
                    }
                }
                return retval;
            }
            finance._ema = _ema;
            // generate a range of numbers
            function _range(begin, end, step) {
                if (step === void 0) { step = 1; }
                wijmo.asNumber(begin, false);
                wijmo.asNumber(end, false);
                wijmo.asNumber(step, false);
                wijmo.assert(begin < end, "begin argument must be less than end argument.");
                var retval = [];
                for (var i = begin; i <= end; i += step) {
                    retval.push(i);
                }
                return retval;
            }
            finance._range = _range;
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
/**
 * Defines the @see:FinancialChart control and its associated classes.
 *
 */
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            'use strict';
            /**
             * Specifies the type of financial chart.
             */
            var FinancialChartType;
            (function (FinancialChartType) {
                /** Shows vertical bars and allows you to compare values of items across categories. */
                FinancialChartType[FinancialChartType["Column"] = 0] = "Column";
                /** Uses X and Y coordinates to show patterns within the data. */
                FinancialChartType[FinancialChartType["Scatter"] = 1] = "Scatter";
                /** Shows trends over a period of time or across categories. */
                FinancialChartType[FinancialChartType["Line"] = 2] = "Line";
                /** Shows line chart with a symbol on each data point. */
                FinancialChartType[FinancialChartType["LineSymbols"] = 3] = "LineSymbols";
                /** Shows line chart with area below the line filled with color. */
                FinancialChartType[FinancialChartType["Area"] = 4] = "Area";
                /** Presents items with high, low, open, and close values.
                 * The size of the wick line is determined by the High and Low values, while
                 * the size of the bar is determined by the Open and Close values. The bar is
                 * displayed using different colors, depending on whether the close value is
                 * higher or lower than the open value. The data for this chart type can be defined using the
                 *  @see:FinancialChart or @see:FinancialSeries <b>binding</b> property as a comma separated value in the
                 * following format: "highProperty, lowProperty, openProperty, closeProperty".  */
                FinancialChartType[FinancialChartType["Candlestick"] = 5] = "Candlestick";
                /** Displays the same information as a candlestick chart, except that opening
                 * values are displayed using lines to the left, while lines to the right
                 * indicate closing values. The data for this chart type can be defined using the
                 *  @see:FinancialChart or @see:FinancialSeries <b>binding</b> property as a comma separated value in the
                 * following format: "highProperty, lowProperty, openProperty, closeProperty". */
                FinancialChartType[FinancialChartType["HighLowOpenClose"] = 6] = "HighLowOpenClose";
                /** Derived from the candlestick chart and uses information from the current and
                 * prior period in order to filter out the noise. These charts cannot be combined
                 * with any other series objects. The data for this chart type can be defined using the
                 *  @see:FinancialChart or @see:FinancialSeries <b>binding</b> property as a comma separated value in the
                 * following format: "highProperty, lowProperty, openProperty, closeProperty". */
                FinancialChartType[FinancialChartType["HeikinAshi"] = 7] = "HeikinAshi";
                /** Filters out noise by focusing exclusively on price changes. These charts cannot
                 * be combined with any other series objects. The data for this chart type can be defined using the
                 *  @see:FinancialChart or @see:FinancialSeries <b>binding</b> property as a comma separated value in the
                 * following format: "highProperty, lowProperty, openProperty, closeProperty". */
                FinancialChartType[FinancialChartType["LineBreak"] = 8] = "LineBreak";
                /** Ignores time and focuses on price changes that meet a specified amount. These
                 * charts cannot be combined with any other series objects. The data for this chart type can be defined using the
                 *  @see:FinancialChart or @see:FinancialSeries <b>binding</b> property as a comma separated value in the
                 * following format: "highProperty, lowProperty, openProperty, closeProperty". */
                FinancialChartType[FinancialChartType["Renko"] = 9] = "Renko";
                /** Ignores time and focuses on price action. These charts cannot be combined with
                 * any other series objects. The data for this chart type can be defined using the
                 *  @see:FinancialChart or @see:FinancialSeries <b>binding</b> property as a comma separated value in the
                 * following format: "highProperty, lowProperty, openProperty, closeProperty". */
                FinancialChartType[FinancialChartType["Kagi"] = 10] = "Kagi";
                /** Identical to the standard Column chart, except that the width of each bar is
                 * determined by the Volume value. The data for this chart type can be defined using the
                 *  @see:FinancialChart or @see:FinancialSeries <b>binding</b> property as a comma separated value in the
                 * following format: "yProperty, volumeProperty".  This chart type can only be used at
                 * the @see:FinancialChart level, and should not be applied on
                 * @see:FinancialSeries objects. Only one set of volume data is currently supported
                 * per @see:FinancialChart. */
                FinancialChartType[FinancialChartType["ColumnVolume"] = 11] = "ColumnVolume";
                /** Similar to the Candlestick chart, but shows the high and low values only.
                 * In addition, the width of each bar is determined by Volume value. The data for
                 * this chart type can be defined using the  @see:FinancialChart or @see:FinancialSeries
                 * <b>binding</b> property as a comma separated value in the following format:
                 * "highProperty, lowProperty, openProperty, closeProperty, volumeProperty".
                 * This chart type can only be used at the @see:FinancialChart level, and should not
                 * be applied on @see:FinancialSeries objects. Only one set of volume data is currently
                 * supported per @see:FinancialChart. */
                FinancialChartType[FinancialChartType["EquiVolume"] = 12] = "EquiVolume";
                /** Identical to the standard Candlestick chart, except that the width of each
                 * bar is determined by Volume value. The data for
                 * this chart type can be defined using the  @see:FinancialChart or @see:FinancialSeries
                 * <b>binding</b> property as a comma separated value in the following format:
                 * "highProperty, lowProperty, openProperty, closeProperty, volumeProperty".
                 * This chart type can only be used at the @see:FinancialChart level, and should not
                 * be applied on @see:FinancialSeries objects. Only one set of volume data is currently
                 * supported per @see:FinancialChart. */
                FinancialChartType[FinancialChartType["CandleVolume"] = 13] = "CandleVolume";
                /** Created by Richard Arms, this chart is a combination of EquiVolume and
                 * CandleVolume chart types. The data for
                 * this chart type can be defined using the  @see:FinancialChart or @see:FinancialSeries
                 * <b>binding</b> property as a comma separated value in the following format:
                 * "highProperty, lowProperty, openProperty, closeProperty, volumeProperty".
                 * This chart type can only be used at the @see:FinancialChart level, and should not
                 * be applied on @see:FinancialSeries objects. Only one set of volume data is currently
                 * supported per @see:FinancialChart. */
                FinancialChartType[FinancialChartType["ArmsCandleVolume"] = 14] = "ArmsCandleVolume";
                /**
                 * Point and figure financial chart.
                 * The data for this chart type can be defined using the  @see:FinancialChart
                 * or @see:FinancialSeries <b>binding</b> property as a comma separated value in
                 * the following format: "highProperty, lowProperty, closeProperty".
                 * This chart type can only be used at the @see:FinancialChart level, and should not
                 * be applied on @see:FinancialSeries objects. */
                FinancialChartType[FinancialChartType["PointAndFigure"] = 15] = "PointAndFigure";
            })(FinancialChartType = finance.FinancialChartType || (finance.FinancialChartType = {}));
            /**
             * Financial charting control.
             */
            var FinancialChart = /** @class */ (function (_super) {
                __extends(FinancialChart, _super);
                /**
                 * Initializes a new instance of the @see:FlexChart class.
                 *
                 * @param element The DOM element that hosts the control, or a selector for the
                 * host element (e.g. '#theCtrl').
                 * @param options A JavaScript object containing initialization data for the
                 * control.
                 */
                function FinancialChart(element, options) {
                    var _this = _super.call(this, element, null) || this;
                    _this._chartType = FinancialChartType.Line;
                    _this.__heikinAshiPlotter = null;
                    _this.__lineBreakPlotter = null;
                    _this.__renkoPlotter = null;
                    _this.__kagiPlotter = null;
                    _this.__pfPlotter = null;
                    _this.initialize(options);
                    return _this;
                }
                Object.defineProperty(FinancialChart.prototype, "chartType", {
                    /**
                     * Gets or sets the type of financial chart to create.
                     */
                    get: function () {
                        return this._chartType;
                    },
                    set: function (value) {
                        if (value != this._chartType) {
                            this._chartType = wijmo.asEnum(value, FinancialChartType);
                            this.invalidate();
                        }
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FinancialChart.prototype, "options", {
                    /**
                     * Gets or sets various chart options.
                     *
                     * The following options are supported:
                     *
                     * <b>kagi.fields</b>: Specifies the @see:DataFields used for
                     * the Kagi chart. The default value is DataFields.Close.
                     *
                     * <b>kagi.rangeMode</b>: Specifies the @see:RangeMode for
                     * the Kagi chart. The default value is RangeMode.Fixed.
                     *
                     * <b>kagi.reversalAmount</b>: Specifies the reversal amount for
                     * the Kagi chart. The default value is 14.
                     *
                     * <pre>chart.options = {
                     *   kagi: {
                     *      fields: wijmo.chart.finance.DataFields.Close,
                     *      rangeMode: wijmo.chart.finance.RangeMode.Fixed,
                     *      reversalAmount: 14
                     *   }
                     * }</pre>
                     *
                     * <b>lineBreak.newLineBreaks</b>: Gets or sets the number of previous
                     * boxes that must be compared before a new box is drawn in
                     * Line Break charts. The default value is 3.
                     *
                     * <pre>chart.options = {
                     *   lineBreak: { newLineBreaks: 3 }
                     * }</pre>
                     *
                     * <b>renko.fields</b>: Specifies the @see:DataFields used for
                     * the Renko chart. The default value is DataFields.Close.
                     *
                     * <b>renko.rangeMode</b>: Specifies the @see:RangeMode for
                     * the Renko chart. The default value is RangeMode.Fixed.
                     *
                     * <b>renko.boxSize</b>: Specifies the box size for
                     * the Renko chart. The default value is 14.
                     *
                     * <pre>chart.options = {
                     *   renko: {
                     *      fields: wijmo.chart.finance.DataFields.Close,
                     *      rangeMode: wijmo.chart.finance.RangeMode.Fixed,
                     *      boxSize: 14
                     *   }
                     * }</pre>
                     */
                    get: function () {
                        return this._options;
                    },
                    set: function (value) {
                        if (value != this._options) {
                            this._options = value;
                            this.invalidate();
                        }
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FinancialChart.prototype, "_heikinAshiPlotter", {
                    get: function () {
                        if (this.__heikinAshiPlotter === null) {
                            this.__heikinAshiPlotter = new finance._HeikinAshiPlotter();
                            this._initPlotter(this.__heikinAshiPlotter);
                        }
                        return this.__heikinAshiPlotter;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FinancialChart.prototype, "_lineBreakPlotter", {
                    get: function () {
                        if (this.__lineBreakPlotter === null) {
                            this.__lineBreakPlotter = new finance._LineBreakPlotter();
                            this._initPlotter(this.__lineBreakPlotter);
                        }
                        return this.__lineBreakPlotter;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FinancialChart.prototype, "_renkoPlotter", {
                    get: function () {
                        if (this.__renkoPlotter === null) {
                            this.__renkoPlotter = new finance._RenkoPlotter();
                            this._initPlotter(this.__renkoPlotter);
                        }
                        return this.__renkoPlotter;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FinancialChart.prototype, "_kagiPlotter", {
                    get: function () {
                        if (this.__kagiPlotter === null) {
                            this.__kagiPlotter = new finance._KagiPlotter();
                            this._initPlotter(this.__kagiPlotter);
                        }
                        return this.__kagiPlotter;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FinancialChart.prototype, "_pfPlotter", {
                    get: function () {
                        if (this.__pfPlotter === null) {
                            this.__pfPlotter = new finance._PointAndFigurePlotter();
                            this._initPlotter(this.__pfPlotter);
                        }
                        return this.__pfPlotter;
                    },
                    enumerable: true,
                    configurable: true
                });
                FinancialChart.prototype._getChartType = function () {
                    var ct = null;
                    switch (this.chartType) {
                        case FinancialChartType.Area:
                            ct = chart.ChartType.Area;
                            break;
                        case FinancialChartType.Line:
                        case FinancialChartType.Kagi:
                        case FinancialChartType.PointAndFigure:
                            ct = chart.ChartType.Line;
                            break;
                        case FinancialChartType.Column:
                        case FinancialChartType.ColumnVolume:
                            ct = chart.ChartType.Column;
                            break;
                        case FinancialChartType.LineSymbols:
                            ct = chart.ChartType.LineSymbols;
                            break;
                        case FinancialChartType.Scatter:
                            ct = chart.ChartType.Scatter;
                            break;
                        case FinancialChartType.Candlestick:
                        case FinancialChartType.Renko:
                        case FinancialChartType.HeikinAshi:
                        case FinancialChartType.LineBreak:
                        case FinancialChartType.EquiVolume:
                        case FinancialChartType.CandleVolume:
                        case FinancialChartType.ArmsCandleVolume:
                            ct = chart.ChartType.Candlestick;
                            break;
                        case FinancialChartType.HighLowOpenClose:
                            ct = chart.ChartType.HighLowOpenClose;
                            break;
                    }
                    return ct;
                };
                FinancialChart.prototype._getPlotter = function (series) {
                    var chartType = this.chartType, plotter = null, isSeries = false;
                    if (series) {
                        var stype = series.chartType;
                        if (stype && !wijmo.isUndefined(stype) && stype != chartType) {
                            chartType = stype;
                            isSeries = true;
                        }
                    }
                    switch (chartType) {
                        case FinancialChartType.HeikinAshi:
                            plotter = this._heikinAshiPlotter;
                            break;
                        case FinancialChartType.LineBreak:
                            plotter = this._lineBreakPlotter;
                            break;
                        case FinancialChartType.Renko:
                            plotter = this._renkoPlotter;
                            break;
                        case FinancialChartType.Kagi:
                            plotter = this._kagiPlotter;
                            break;
                        case FinancialChartType.ColumnVolume:
                            plotter = _super.prototype._getPlotter.call(this, series);
                            plotter.isVolume = true;
                            plotter.width = 1;
                            break;
                        case FinancialChartType.EquiVolume:
                            plotter = _super.prototype._getPlotter.call(this, series);
                            plotter.isEqui = true;
                            plotter.isCandle = false;
                            plotter.isArms = false;
                            plotter.isVolume = true;
                            plotter.symbolWidth = "100%";
                            break;
                        case FinancialChartType.CandleVolume:
                            plotter = _super.prototype._getPlotter.call(this, series);
                            plotter.isEqui = false;
                            plotter.isCandle = true;
                            plotter.isArms = false;
                            plotter.isVolume = true;
                            plotter.symbolWidth = "100%";
                            break;
                        case FinancialChartType.ArmsCandleVolume:
                            plotter = _super.prototype._getPlotter.call(this, series);
                            plotter.isEqui = false;
                            plotter.isCandle = false;
                            plotter.isArms = true;
                            plotter.isVolume = true;
                            plotter.symbolWidth = "100%";
                            break;
                        case FinancialChartType.PointAndFigure:
                            plotter = this._pfPlotter;
                            break;
                        // no plotter found for FinancialChartType - try based on ChartType
                        default:
                            plotter = _super.prototype._getPlotter.call(this, series);
                            break;
                    }
                    return plotter;
                };
                FinancialChart.prototype._createSeries = function () {
                    return new finance.FinancialSeries();
                };
                return FinancialChart;
            }(chart.FlexChartCore));
            finance.FinancialChart = FinancialChart;
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            'use strict';
            /**
             * Represents a series of data points to display in the chart.
             *
             * The @see:Series class supports all basic chart types. You may define
             * a different chart type on each @see:Series object that you add to the
             * @see:FlexChart series collection. This overrides the @see:chartType
             * property set on the chart that is the default for all @see:Series objects
             * in its collection.
             */
            var FinancialSeries = /** @class */ (function (_super) {
                __extends(FinancialSeries, _super);
                function FinancialSeries() {
                    return _super !== null && _super.apply(this, arguments) || this;
                }
                Object.defineProperty(FinancialSeries.prototype, "chartType", {
                    /**
                     * Gets or sets the chart type for a specific series, overriding the chart type
                     * set on the overall chart. Please note that ColumnVolume, EquiVolume,
                     * CandleVolume and ArmsCandleVolume chart types are not supported and should be
                     * set on the @see:FinancialChart.
                     */
                    get: function () {
                        return this._finChartType;
                    },
                    set: function (value) {
                        if (value != this._finChartType) {
                            this._finChartType = wijmo.asEnum(value, finance.FinancialChartType, true);
                            this._invalidate();
                        }
                    },
                    enumerable: true,
                    configurable: true
                });
                FinancialSeries.prototype._getChartType = function () {
                    var ct = null;
                    switch (this.chartType) {
                        case finance.FinancialChartType.Area:
                            ct = chart.ChartType.Area;
                            break;
                        case finance.FinancialChartType.Line:
                        case finance.FinancialChartType.Kagi:
                        case finance.FinancialChartType.PointAndFigure:
                            ct = chart.ChartType.Line;
                            break;
                        case finance.FinancialChartType.Column:
                        case finance.FinancialChartType.ColumnVolume:
                            ct = chart.ChartType.Column;
                            break;
                        case finance.FinancialChartType.LineSymbols:
                            ct = chart.ChartType.LineSymbols;
                            break;
                        case finance.FinancialChartType.Scatter:
                            ct = chart.ChartType.Scatter;
                            break;
                        case finance.FinancialChartType.Candlestick:
                        case finance.FinancialChartType.Renko:
                        case finance.FinancialChartType.HeikinAshi:
                        case finance.FinancialChartType.LineBreak:
                        case finance.FinancialChartType.EquiVolume:
                        case finance.FinancialChartType.CandleVolume:
                        case finance.FinancialChartType.ArmsCandleVolume:
                            ct = chart.ChartType.Candlestick;
                            break;
                        case finance.FinancialChartType.HighLowOpenClose:
                            ct = chart.ChartType.HighLowOpenClose;
                            break;
                    }
                    return ct;
                };
                return FinancialSeries;
            }(chart.SeriesBase));
            finance.FinancialSeries = FinancialSeries;
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            "use strict";
            // abstract base class for range based calculators
            var _BaseCalculator = /** @class */ (function () {
                function _BaseCalculator(highs, lows, opens, closes) {
                    this.highs = highs;
                    this.lows = lows;
                    this.opens = opens;
                    this.closes = closes;
                }
                _BaseCalculator.prototype.calculate = function () { };
                return _BaseCalculator;
            }());
            finance._BaseCalculator = _BaseCalculator;
            // calculator for Heikin-Ashi plotter - http://bit.ly/1BY55tc
            var _HeikinAshiCalculator = /** @class */ (function (_super) {
                __extends(_HeikinAshiCalculator, _super);
                function _HeikinAshiCalculator(highs, lows, opens, closes) {
                    return _super.call(this, highs, lows, opens, closes) || this;
                }
                _HeikinAshiCalculator.prototype.calculate = function () {
                    var len = Math.min(this.highs.length, this.lows.length, this.opens.length, this.closes.length), haHigh, haLow, haOpen, haClose, retvals = [];
                    if (len <= 0) {
                        return retvals;
                    }
                    for (var i = 0; i < len; i++) {
                        haClose = finance._average(this.highs[i], this.lows[i], this.opens[i], this.closes[i]);
                        if (i === 0) {
                            haOpen = finance._average(this.opens[i], this.closes[i]);
                            haHigh = this.highs[i];
                            haLow = this.lows[i];
                        }
                        else {
                            haOpen = finance._average(retvals[i - 1].open, retvals[i - 1].close);
                            haHigh = Math.max(this.highs[i], haOpen, haClose);
                            haLow = Math.min(this.lows[i], haOpen, haClose);
                        }
                        retvals.push({
                            high: haHigh,
                            low: haLow,
                            close: haClose,
                            open: haOpen,
                            pointIndex: i,
                            x: null
                        });
                    }
                    return retvals;
                };
                return _HeikinAshiCalculator;
            }(_BaseCalculator));
            finance._HeikinAshiCalculator = _HeikinAshiCalculator;
            // abstract base class for range based calculators
            var _BaseRangeCalculator = /** @class */ (function (_super) {
                __extends(_BaseRangeCalculator, _super);
                function _BaseRangeCalculator(highs, lows, opens, closes, xs, size, unit, fields) {
                    var _this = _super.call(this, highs, lows, opens, closes) || this;
                    _this.xs = xs;
                    _this.size = size;
                    _this.unit = unit;
                    _this.fields = fields;
                    return _this;
                }
                // based on "fields" member, return the values to be used for calculations
                //  DataFields.HighLow must be handled in the calculate() method
                _BaseRangeCalculator.prototype._getValues = function () {
                    var values = [], len = Math.min(this.highs.length, this.lows.length, this.opens.length, this.closes.length), i;
                    switch (this.fields) {
                        case finance.DataFields.High: {
                            values = this.highs;
                            break;
                        }
                        case finance.DataFields.Low: {
                            values = this.lows;
                            break;
                        }
                        case finance.DataFields.Open: {
                            values = this.opens;
                            break;
                        }
                        case finance.DataFields.HL2: {
                            for (i = 0; i < len; i++) {
                                values.push(finance._average(this.highs[i], this.lows[i]));
                            }
                            break;
                        }
                        case finance.DataFields.HLC3: {
                            for (i = 0; i < len; i++) {
                                values.push(finance._average(this.highs[i], this.lows[i], this.closes[i]));
                            }
                            break;
                        }
                        case finance.DataFields.HLOC4: {
                            for (i = 0; i < len; i++) {
                                values.push(finance._average(this.highs[i], this.lows[i], this.opens[i], this.closes[i]));
                            }
                            break;
                        }
                        case finance.DataFields.Close:
                        default: {
                            values = this.closes;
                            break;
                        }
                    }
                    return values;
                };
                _BaseRangeCalculator.prototype._getSize = function () {
                    var atrs = this.unit === finance.RangeMode.ATR ? finance._avgTrueRng(this.highs, this.lows, this.closes, this.size) : null;
                    return this.unit === finance.RangeMode.ATR ? atrs[atrs.length - 1] : this.size;
                };
                return _BaseRangeCalculator;
            }(_BaseCalculator));
            finance._BaseRangeCalculator = _BaseRangeCalculator;
            // calculator for Line Break plotter
            var _LineBreakCalculator = /** @class */ (function (_super) {
                __extends(_LineBreakCalculator, _super);
                function _LineBreakCalculator(highs, lows, opens, closes, xs, size) {
                    return _super.call(this, highs, lows, opens, closes, xs, size) || this;
                }
                _LineBreakCalculator.prototype.calculate = function () {
                    var hasXs = this.xs !== null && this.xs.length > 0, len = this.closes.length, retvals = [], rangeValues = [[], []];
                    if (len <= 0) {
                        return retvals;
                    }
                    var tempRngs = [], basePrice, x, close, lbLen, lbIdx, max, min;
                    // start at index of one
                    for (var i = 1; i < len; i++) {
                        lbLen = retvals.length;
                        lbIdx = lbLen - 1;
                        x = hasXs ? this.xs[i] : i;
                        close = this.closes[i];
                        if (lbIdx === -1) {
                            basePrice = this.closes[0];
                            if (basePrice === close) {
                                continue;
                            }
                        }
                        else {
                            if (this._trendExists(rangeValues) || this.size === 1) {
                                tempRngs = rangeValues[0].slice(-this.size).concat(rangeValues[1].slice(-this.size));
                            }
                            else {
                                tempRngs = rangeValues[0].slice(1 - this.size).concat(rangeValues[1].slice(1 - this.size));
                            }
                            max = Math.max.apply(null, tempRngs);
                            min = Math.min.apply(null, tempRngs);
                            if (close > max) {
                                basePrice = Math.max(rangeValues[0][lbIdx], rangeValues[1][lbIdx]);
                            }
                            else if (close < min) {
                                basePrice = Math.min(rangeValues[0][lbIdx], rangeValues[1][lbIdx]);
                            }
                            else {
                                continue;
                            }
                        }
                        rangeValues[0].push(basePrice);
                        rangeValues[1].push(close);
                        retvals.push({
                            high: Math.max(basePrice, close),
                            low: Math.min(basePrice, close),
                            open: basePrice,
                            close: close,
                            x: x,
                            pointIndex: i
                        });
                    }
                    return retvals;
                };
                _LineBreakCalculator.prototype._trendExists = function (vals) {
                    if (vals[1].length < this.size) {
                        return false;
                    }
                    var retval = false, t, temp = vals[1].slice(-this.size); // get subset of "current" values based on _newLineBreaks
                    // detect rising trend
                    for (t = 1; t < this.size; t++) {
                        retval = temp[t] > temp[t - 1];
                        if (!retval) {
                            break;
                        }
                    }
                    // detect falling trend
                    if (!retval) {
                        for (t = 1; t < this.size; t++) {
                            retval = temp[t] < temp[t - 1];
                            if (!retval) {
                                break;
                            }
                        }
                    }
                    return retval;
                };
                return _LineBreakCalculator;
            }(_BaseRangeCalculator));
            finance._LineBreakCalculator = _LineBreakCalculator;
            // calculator for Kagi plotter
            var _KagiCalculator = /** @class */ (function (_super) {
                __extends(_KagiCalculator, _super);
                function _KagiCalculator(highs, lows, opens, closes, xs, size, unit, field) {
                    return _super.call(this, highs, lows, opens, closes, xs, size, unit, field) || this;
                }
                _KagiCalculator.prototype.calculate = function () {
                    var reversal = this._getSize(), len = Math.min(this.highs.length, this.lows.length, this.opens.length, this.closes.length), values = this._getValues(), hasXs = this.xs !== null && this.xs.length > 0, retvals = [], rangeValues = [[], []];
                    if (len <= 0) {
                        return retvals;
                    }
                    var basePrice, x, current, rLen, rIdx, min, max, diff, extend, pointIndex;
                    for (var i = 1; i < len; i++) {
                        rLen = retvals.length;
                        rIdx = rLen - 1;
                        x = hasXs ? this.xs[i] : i;
                        pointIndex = i;
                        extend = false;
                        // set current value
                        if (this.fields === finance.DataFields.HighLow) {
                            if (rIdx === -1) {
                                if (this.highs[i] > this.highs[0]) {
                                    current = this.highs[i];
                                }
                                else if (this.lows[i] < this.lows[0]) {
                                    current = this.lows[i];
                                }
                                else {
                                    continue;
                                }
                            }
                            else {
                                diff = rangeValues[1][rIdx] - rangeValues[0][rIdx];
                                if (diff > 0) {
                                    if (this.highs[i] > rangeValues[1][rIdx]) {
                                        current = this.highs[i];
                                    }
                                    else if (this.lows[i] < rangeValues[1][rIdx]) {
                                        current = this.lows[i];
                                    }
                                    else {
                                        continue;
                                    }
                                }
                                else {
                                    if (this.lows[i] < rangeValues[1][rIdx]) {
                                        current = this.lows[i];
                                    }
                                    else if (this.highs[i] > rangeValues[1][rIdx]) {
                                        current = this.highs[i];
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }
                        else {
                            current = values[i];
                        }
                        // set reversal for percentage-based charts
                        if (this.unit === finance.RangeMode.Percentage) {
                            reversal = current * this.size;
                        }
                        // set base price value
                        if (rIdx === -1) {
                            x = hasXs ? this.xs[0] : 0;
                            pointIndex = 0;
                            if (this.fields === finance.DataFields.HighLow) {
                                basePrice = this.highs[0] == null ? this.highs[this.highs.length - 1] : this.highs[0];
                            }
                            else {
                                basePrice = values[0] == null ? values[values.length - 1] : values[0];
                            }
                            diff = Math.abs(basePrice - current) || 0;
                            if (diff < reversal) {
                                continue;
                            }
                        }
                        else {
                            diff = rangeValues[1][rIdx] - rangeValues[0][rIdx];
                            max = Math.max(rangeValues[0][rIdx], rangeValues[1][rIdx]);
                            min = Math.min(rangeValues[0][rIdx], rangeValues[1][rIdx]);
                            if (diff > 0) {
                                if (current > max) {
                                    extend = true;
                                }
                                else {
                                    diff = max - current;
                                    if (diff >= reversal) {
                                        basePrice = max;
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                            else {
                                if (current < min) {
                                    extend = true;
                                }
                                else {
                                    diff = current - min;
                                    if (diff >= reversal) {
                                        basePrice = min;
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }
                        if (extend) {
                            rangeValues[1][rIdx] = current;
                            retvals[rIdx].close = current;
                            retvals[rIdx].high = Math.max(retvals[rIdx].open, retvals[rIdx].close);
                            retvals[rIdx].low = Math.min(retvals[rIdx].open, retvals[rIdx].close);
                        }
                        else {
                            rangeValues[0].push(basePrice);
                            rangeValues[1].push(current);
                            retvals.push({
                                high: Math.max(basePrice, current),
                                low: Math.min(basePrice, current),
                                open: basePrice,
                                close: current,
                                x: x,
                                pointIndex: pointIndex
                            });
                        }
                    }
                    return retvals;
                };
                return _KagiCalculator;
            }(_BaseRangeCalculator));
            finance._KagiCalculator = _KagiCalculator;
            // calculator for Renko plotter
            var _RenkoCalculator = /** @class */ (function (_super) {
                __extends(_RenkoCalculator, _super);
                function _RenkoCalculator(highs, lows, opens, closes, xs, size, unit, field, rounding) {
                    if (rounding === void 0) { rounding = false; }
                    var _this = _super.call(this, highs, lows, opens, closes, xs, size, unit, field) || this;
                    // internal only
                    _this.rounding = rounding;
                    return _this;
                }
                _RenkoCalculator.prototype.calculate = function () {
                    var size = this._getSize(), len = Math.min(this.highs.length, this.lows.length, this.opens.length, this.closes.length), hasXs = this.xs !== null && this.xs.length > 0, values = this._getValues(), retvals = [], rangeValues = [[], []];
                    if (len <= 0) {
                        return retvals;
                    }
                    var basePrice, x, current, rLen, rIdx, min, max, diff;
                    // start at index of one
                    for (var i = 1; i < len; i++) {
                        rLen = retvals.length;
                        rIdx = rLen - 1;
                        x = hasXs ? this.xs[i] : i;
                        // todo: not working correctly, figure out
                        // set basePrice and current for DataFields == HighLow
                        if (this.fields === finance.DataFields.HighLow) {
                            if (rIdx === -1) {
                                if (this.highs[i] - this.highs[0] > size) {
                                    basePrice = this.highs[0];
                                    current = this.highs[i];
                                }
                                else if (this.lows[0] - this.lows[i] > size) {
                                    basePrice = this.lows[0];
                                    current = this.lows[i];
                                }
                                else {
                                    continue;
                                }
                            }
                            else {
                                min = Math.min(rangeValues[0][rIdx], rangeValues[1][rIdx]);
                                max = Math.max(rangeValues[0][rIdx], rangeValues[1][rIdx]);
                                if ((this.highs[i] - max) > size) {
                                    basePrice = max;
                                    current = this.highs[i];
                                }
                                else if ((min - this.lows[i]) > size) {
                                    basePrice = min;
                                    current = this.lows[i];
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                        else {
                            // DataFields != HighLow
                            // current price
                            current = values[i];
                            // set "base price"
                            if (rIdx === -1) {
                                basePrice = values[0];
                            }
                            else {
                                min = Math.min(rangeValues[0][rIdx], rangeValues[1][rIdx]);
                                max = Math.max(rangeValues[0][rIdx], rangeValues[1][rIdx]);
                                if (current > max) {
                                    basePrice = max;
                                }
                                else if (current < min) {
                                    basePrice = min;
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                        diff = current - basePrice;
                        if (Math.abs(diff) < size) {
                            continue;
                        }
                        // determine number of boxes to add
                        diff = finance._trunc(diff / size);
                        // append ranges and x's
                        for (var j = 0; j < Math.abs(diff); j++) {
                            var rng = {};
                            // note StockCharts adjusts based on size
                            if (this.rounding) {
                                basePrice = this._round(basePrice, size);
                            }
                            rangeValues[0].push(basePrice);
                            rng.open = basePrice;
                            basePrice = diff > 0 ? basePrice + size : basePrice - size;
                            rangeValues[1].push(basePrice);
                            rng.close = basePrice;
                            rng.x = x;
                            rng.pointIndex = i;
                            rng.high = Math.max(rng.open, rng.close);
                            rng.low = Math.min(rng.open, rng.close);
                            retvals.push(rng);
                        }
                    }
                    return retvals;
                };
                // internal only - for StockCharts rounding
                _RenkoCalculator.prototype._round = function (value, size) {
                    return Math.round(value / size) * size;
                };
                return _RenkoCalculator;
            }(_BaseRangeCalculator));
            finance._RenkoCalculator = _RenkoCalculator;
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            "use strict";
            // Plotter for Heikin-Ashi FinancialChartType
            var _HeikinAshiPlotter = /** @class */ (function (_super) {
                __extends(_HeikinAshiPlotter, _super);
                function _HeikinAshiPlotter() {
                    var _this = _super.call(this) || this;
                    _this._symFactor = 0.7;
                    _this.clear();
                    return _this;
                }
                _HeikinAshiPlotter.prototype.clear = function () {
                    _super.prototype.clear.call(this);
                    this._haValues = null;
                    this._calculator = null;
                };
                _HeikinAshiPlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser) {
                    var _this = this;
                    this._calculate(series);
                    var ser = wijmo.asType(series, chart.SeriesBase), si = this.chart.series.indexOf(series), xs = series.getValues(1), sw = this._symFactor;
                    var len = this._haValues.length, hasXs = true;
                    if (!xs) {
                        xs = this.dataInfo.getXVals();
                    }
                    else {
                        // find minimal distance between point and use it as column width
                        var delta = this.dataInfo.getDeltaX();
                        if (delta > 0) {
                            sw *= delta;
                        }
                    }
                    if (!xs) {
                        hasXs = false;
                        xs = new Array(len);
                    }
                    else {
                        len = Math.min(len, xs.length);
                    }
                    var swidth = this._DEFAULT_WIDTH, fill = ser._getSymbolFill(si), altFill = ser._getAltSymbolFill(si) || "transparent", stroke = ser._getSymbolStroke(si), altStroke = ser._getAltSymbolStroke(si) || stroke, 
                    //symSize = ser._getSymbolSize(),
                    //symStyle = series.symbolStyle,
                    symSize = sw, dt = series.getDataType(1) || series.chart._xDataType;
                    engine.strokeWidth = swidth;
                    var xmin = ax.actualMin, xmax = ax.actualMax, itemIndex = 0, currentFill, currentStroke, x, dpt, hi, lo, open, close;
                    for (var i = 0; i < len; i++) {
                        x = hasXs ? xs[i] : i;
                        if (chart._DataInfo.isValid(x) && xmin <= x && x <= xmax) {
                            hi = this._haValues[i].high;
                            lo = this._haValues[i].low;
                            open = this._haValues[i].open;
                            close = this._haValues[i].close;
                            currentFill = open < close ? altFill : fill;
                            currentStroke = open < close ? altStroke : stroke;
                            engine.fill = currentFill;
                            engine.stroke = currentStroke;
                            engine.startGroup();
                            // manually specify values for HitTestInfo
                            dpt = this._getDataPoint(si, i, x, series);
                            if (this.chart.itemFormatter) {
                                var hti = new chart.HitTestInfo(this.chart, new wijmo.Point(ax.convert(x), ay.convert(hi)), chart.ChartElement.SeriesSymbol);
                                hti._setData(ser, i);
                                hti._setDataPoint(dpt);
                                this.chart.itemFormatter(engine, hti, function () {
                                    _this._drawSymbol(engine, ax, ay, si, i, currentFill, symSize, x, hi, lo, open, close, dpt, dt);
                                });
                            }
                            else {
                                this._drawSymbol(engine, ax, ay, si, i, currentFill, symSize, x, hi, lo, open, close, dpt, dt);
                            }
                            engine.endGroup();
                            series._setPointIndex(i, itemIndex);
                            itemIndex++;
                        }
                    }
                };
                // modified variation of FinancialPlotter's implementation - added optional _DataPoint parameter
                _HeikinAshiPlotter.prototype._drawSymbol = function (engine, ax, ay, si, pi, fill, w, x, hi, lo, open, close, dpt, dt) {
                    var area, y0 = null, y1 = null, x1 = null, x2 = null, half = dt === wijmo.DataType.Date ? 43200000 : 0.5; // todo: better way?
                    x1 = ax.convert(x - half * w);
                    x2 = ax.convert(x + half * w);
                    if (x1 > x2) {
                        var tmp = x1;
                        x1 = x2;
                        x2 = tmp;
                    }
                    x = ax.convert(x);
                    if (chart._DataInfo.isValid(open) && chart._DataInfo.isValid(close)) {
                        open = ay.convert(open);
                        close = ay.convert(close);
                        y0 = Math.min(open, close);
                        y1 = y0 + Math.abs(open - close);
                        engine.drawRect(x1, y0, x2 - x1, y1 - y0);
                        area = new chart._RectArea(new wijmo.Rect(x1, y0, x2 - x1, y1 - y0));
                        area.tag = dpt;
                        this.hitTester.add(area, si);
                    }
                    if (chart._DataInfo.isValid(hi)) {
                        hi = ay.convert(hi);
                        if (y0 !== null) {
                            engine.drawLine(x, y0, x, hi);
                        }
                    }
                    if (chart._DataInfo.isValid(lo)) {
                        lo = ay.convert(lo);
                        if (y1 !== null) {
                            engine.drawLine(x, y1, x, lo);
                        }
                    }
                };
                // generates _DataPoint for hit test support
                _HeikinAshiPlotter.prototype._getDataPoint = function (seriesIndex, pointIndex, x, series) {
                    var dpt = new chart._DataPoint(seriesIndex, pointIndex, x, this._haValues[pointIndex].high), item = series._getItem(pointIndex), bndHigh = series._getBinding(0), bndLow = series._getBinding(1), bndOpen = series._getBinding(2), bndClose = series._getBinding(3), ay = series._getAxisY();
                    // set item related data and maintain original binding
                    dpt["item"] = chart._BasePlotter.cloneStyle(item, []);
                    dpt["item"][bndHigh] = this._haValues[pointIndex].high;
                    dpt["item"][bndLow] = this._haValues[pointIndex].low;
                    dpt["item"][bndOpen] = this._haValues[pointIndex].open;
                    dpt["item"][bndClose] = this._haValues[pointIndex].close;
                    // set y related data
                    dpt["y"] = this._haValues[pointIndex].high;
                    dpt["yfmt"] = ay._formatValue(this._haValues[pointIndex].high);
                    // don't set "x" or "xfmt" values - can use default behavior
                    return dpt;
                };
                _HeikinAshiPlotter.prototype._calculate = function (series) {
                    var highs = series._getBindingValues(0), lows = series._getBindingValues(1), opens = series._getBindingValues(2), closes = series._getBindingValues(3);
                    this._calculator = new finance._HeikinAshiCalculator(highs, lows, opens, closes);
                    this._haValues = this._calculator.calculate();
                    if (this._haValues === null || wijmo.isUndefined(this._haValues)) {
                        this._init();
                    }
                };
                _HeikinAshiPlotter.prototype._init = function () {
                    this._haValues = [];
                };
                return _HeikinAshiPlotter;
            }(chart._FinancePlotter));
            finance._HeikinAshiPlotter = _HeikinAshiPlotter;
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            "use strict";
            // Abstract plotter for range based FinancialChartTypes
            var _BaseRangePlotter = /** @class */ (function (_super) {
                __extends(_BaseRangePlotter, _super);
                function _BaseRangePlotter() {
                    var _this = _super.call(this) || this;
                    _this._symFactor = 0.7;
                    _this.clear();
                    return _this;
                }
                _BaseRangePlotter.prototype.clear = function () {
                    _super.prototype.clear.call(this);
                    this._rangeValues = null;
                    this._rangeXLabels = null;
                    this._calculator = null;
                };
                _BaseRangePlotter.prototype.unload = function () {
                    _super.prototype.unload.call(this);
                    var series, ax;
                    for (var i = 0; i < this.chart.series.length; i++) {
                        series = this.chart.series[i];
                        if (!series) {
                            continue;
                        }
                        ax = series._getAxisX();
                        // reset AxisX.itemsSource
                        if (ax && ax.itemsSource) {
                            ax.itemsSource = null;
                        }
                    }
                };
                // todo: possibly add support for multiple series *later* (i.e. overlays/indicators)
                // todo: better way to adjust x limits?
                _BaseRangePlotter.prototype.adjustLimits = function (dataInfo, plotRect) {
                    var series, arrTemp, xTemp, xmin = 0, xmax = 0, ymin = 0, ymax = 0, ax, padding = this.chart._xDataType === wijmo.DataType.Date ? 0.5 : 0;
                    // only one supported at the moment - possibly remove later for overlays & indicators
                    wijmo.assert(this.chart.series.length <= 1, "Current FinancialChartType only supports a single series");
                    // looping for future - will need adjusted (see above)
                    for (var i = 0; i < this.chart.series.length; i++) {
                        series = this.chart.series[i];
                        this._calculate(series);
                        if (this._rangeValues.length <= 0 || this._rangeXLabels.length <= 0) {
                            continue;
                        }
                        // create temporary array for calculating ymin & ymax
                        arrTemp = this._rangeValues.map(function (value) { return value.open; });
                        arrTemp.push.apply(arrTemp, this._rangeValues.map(function (value) { return value.close; }));
                        // create temp array for xmin & xmax
                        xTemp = this._rangeXLabels.map(function (current) { return current.value; });
                        // update y-axis
                        ymin = Math.min.apply(null, arrTemp);
                        ymax = Math.max.apply(null, arrTemp);
                        // update x-axis and set itemsSource
                        xmin = Math.min.apply(null, xTemp);
                        xmax = Math.max.apply(null, xTemp);
                        ax = series._getAxisX();
                        ax.itemsSource = this._rangeXLabels;
                    }
                    xmin -= padding;
                    return new wijmo.Rect(xmin, ymin, xmax - xmin + padding, ymax - ymin);
                };
                _BaseRangePlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser) {
                    var _this = this;
                    this._calculate(series);
                    var si = this.chart.series.indexOf(series), len = this._rangeValues.length, xmin = ax.actualMin, xmax = ax.actualMax, strWidth = this._DEFAULT_WIDTH, symSize = this._symFactor, fill = series._getSymbolFill(si), altFill = series._getAltSymbolFill(si) || "transparent", stroke = series._getSymbolStroke(si), altStroke = series._getAltSymbolStroke(si) || stroke;
                    engine.strokeWidth = strWidth;
                    var itemIndex = 0, x, start, end, dpt;
                    for (var i = 0; i < len; i++) {
                        x = i;
                        if (chart._DataInfo.isValid(x) && xmin <= x && x <= xmax) {
                            start = this._rangeValues[i].open;
                            end = this._rangeValues[i].close;
                            // symbol fill and stroke
                            engine.fill = start > end ? fill : altFill;
                            engine.stroke = start > end ? stroke : altStroke;
                            // manually specify values for HitTestInfo
                            // for Bars - dataY should be the top of the bar
                            dpt = this._getDataPoint(si, i, series, Math.max(start, end));
                            engine.startGroup();
                            if (this.chart.itemFormatter) {
                                var hti = new chart.HitTestInfo(this.chart, new wijmo.Point(ax.convert(x), ay.convert(end)), chart.ChartElement.SeriesSymbol);
                                hti._setData(series, i);
                                hti._setDataPoint(dpt);
                                this.chart.itemFormatter(engine, hti, function () {
                                    _this._drawSymbol(engine, ax, ay, si, itemIndex, symSize, x, start, end, dpt);
                                });
                            }
                            else {
                                this._drawSymbol(engine, ax, ay, si, itemIndex, symSize, x, start, end, dpt);
                            }
                            engine.endGroup();
                            series._setPointIndex(i, itemIndex);
                            itemIndex++;
                        }
                    }
                };
                _BaseRangePlotter.prototype._drawSymbol = function (engine, ax, ay, si, pi, w, x, start, end, dpt) {
                    var y0, y1, x1, x2, area;
                    x1 = ax.convert(x - 0.5 * w);
                    x2 = ax.convert(x + 0.5 * w);
                    if (x1 > x2) {
                        var tmp = x1;
                        x1 = x2;
                        x2 = tmp;
                    }
                    //x = ax.convert(x);
                    if (chart._DataInfo.isValid(start) && chart._DataInfo.isValid(end)) {
                        start = ay.convert(start);
                        end = ay.convert(end);
                        y0 = Math.min(start, end);
                        y1 = y0 + Math.abs(start - end);
                        engine.drawRect(x1, y0, x2 - x1, y1 - y0);
                        area = new chart._RectArea(new wijmo.Rect(x1, y0, x2 - x1, y1 - y0));
                        area.tag = dpt;
                        this.hitTester.add(area, si);
                    }
                };
                // generates _DataPoint for hit test support
                _BaseRangePlotter.prototype._getDataPoint = function (seriesIndex, pointIndex, series, dataY) {
                    var x = pointIndex, dpt = new chart._DataPoint(seriesIndex, pointIndex, x, dataY), item = series._getItem(this._rangeValues[pointIndex].pointIndex), bndX = series.bindingX || this.chart.bindingX, bndHigh = series._getBinding(0), bndLow = series._getBinding(1), bndOpen = series._getBinding(2), bndClose = series._getBinding(3), ay = series._getAxisY();
                    // set item related data and maintain original bindings
                    dpt["item"] = chart._BasePlotter.cloneStyle(item, []);
                    dpt["item"][bndHigh] = this._rangeValues[pointIndex].high;
                    dpt["item"][bndLow] = this._rangeValues[pointIndex].low;
                    dpt["item"][bndOpen] = this._rangeValues[pointIndex].open;
                    dpt["item"][bndClose] = this._rangeValues[pointIndex].close;
                    // set x & y related data
                    dpt["y"] = this._rangeValues[pointIndex].close;
                    dpt["yfmt"] = ay._formatValue(this._rangeValues[pointIndex].close);
                    dpt["x"] = dpt["item"][bndX];
                    dpt["xfmt"] = this._rangeXLabels[pointIndex]._text;
                    return dpt;
                };
                // initialize variables for calculations
                _BaseRangePlotter.prototype._init = function () {
                    this._rangeValues = [];
                    this._rangeXLabels = [];
                };
                // abstract method
                _BaseRangePlotter.prototype._calculate = function (series) { };
                // generates new labels for the x-axis based on derived data
                _BaseRangePlotter.prototype._generateXLabels = function (series) {
                    var _this = this;
                    var textVal, ax = series._getAxisX(), dataType = series.getDataType(1) || this.chart._xDataType;
                    // todo: find a better way and/or separate
                    this._rangeValues.forEach(function (value, index) {
                        var val = value.x;
                        if (dataType === wijmo.DataType.Date) {
                            textVal = wijmo.Globalize.format(chart.FlexChart._fromOADate(val), ax.format || "d");
                        }
                        else if (dataType === wijmo.DataType.Number) {
                            textVal = ax._formatValue(val);
                        }
                        else if ((dataType === null || dataType === wijmo.DataType.String) && _this.chart._xlabels) {
                            textVal = _this.chart._xlabels[val];
                        }
                        else {
                            textVal = val.toString();
                        }
                        // _text property will be used as a backup for the text property
                        // there could be cases, like Renko, where text is cleared
                        _this._rangeXLabels.push({ value: index, text: textVal, _text: textVal });
                    }, this);
                };
                return _BaseRangePlotter;
            }(chart._BasePlotter));
            finance._BaseRangePlotter = _BaseRangePlotter;
            /**
             * Specifies which fields are to be used for calculation. Applies to Renko and Kagi chart types.
             */
            var DataFields;
            (function (DataFields) {
                /** Close values are used for calculations. */
                DataFields[DataFields["Close"] = 0] = "Close";
                /** High values are used for calculations. */
                DataFields[DataFields["High"] = 1] = "High";
                /** Low values are used for calculations. */
                DataFields[DataFields["Low"] = 2] = "Low";
                /** Open values are used for calculations. */
                DataFields[DataFields["Open"] = 3] = "Open";
                /** High-Low method is used for calculations. DataFields.HighLow is currently not
                 * supported with Renko chart types. */
                DataFields[DataFields["HighLow"] = 4] = "HighLow";
                /** Average of high and low values is used for calculations. */
                DataFields[DataFields["HL2"] = 5] = "HL2";
                /** Average of high, low, and close values is used for calculations. */
                DataFields[DataFields["HLC3"] = 6] = "HLC3";
                /** Average of high, low, open, and close values is used for calculations. */
                DataFields[DataFields["HLOC4"] = 7] = "HLOC4";
            })(DataFields = finance.DataFields || (finance.DataFields = {}));
            /**
             * Specifies the unit for Kagi and Renko chart types.
             */
            var RangeMode;
            (function (RangeMode) {
                /** Uses a fixed, positive number for the Kagi chart's reversal amount
                 * or Renko chart's box size. */
                RangeMode[RangeMode["Fixed"] = 0] = "Fixed";
                /** Uses the current Average True Range value for Kagi chart's reversal amount
                 * or Renko chart's box size. When ATR is used, the reversal amount or box size
                 * option of these charts must be an integer and will be used as the period for
                 * the ATR calculation. */
                RangeMode[RangeMode["ATR"] = 1] = "ATR";
                /** Uses a percentage for the Kagi chart's reversal amount. RangeMode.Percentage
                 * is currently not supported with Renko chart types. */
                RangeMode[RangeMode["Percentage"] = 2] = "Percentage";
            })(RangeMode = finance.RangeMode || (finance.RangeMode = {}));
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            "use strict";
            // Plotter for Line Break FinancialChartType
            var _LineBreakPlotter = /** @class */ (function (_super) {
                __extends(_LineBreakPlotter, _super);
                function _LineBreakPlotter() {
                    return _super.call(this) || this;
                }
                _LineBreakPlotter.prototype.clear = function () {
                    _super.prototype.clear.call(this);
                    this._newLineBreaks = null;
                };
                _LineBreakPlotter.prototype._calculate = function (series) {
                    this._init();
                    var closes = series._getBindingValues(3), xs = series.getValues(1) || this.chart._xvals;
                    this._calculator = new finance._LineBreakCalculator(null, null, null, closes, xs, this._newLineBreaks);
                    this._rangeValues = this._calculator.calculate();
                    if (this._rangeValues === null || wijmo.isUndefined(this._rangeValues)) {
                        this._rangeValues = [];
                    }
                    // always regenerate x-axis labels at the end of each calculation cycle
                    this._generateXLabels(series);
                };
                _LineBreakPlotter.prototype._init = function () {
                    _super.prototype._init.call(this);
                    // NewLineBreaks
                    this._newLineBreaks = wijmo.asInt(this.getNumOption("newLineBreaks", "lineBreak"), true, true) || 3;
                    wijmo.assert(this._newLineBreaks >= 1, "Value must be greater than 1");
                };
                return _LineBreakPlotter;
            }(finance._BaseRangePlotter));
            finance._LineBreakPlotter = _LineBreakPlotter;
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            "use strict";
            // Plotter for Renko FinancialChartType
            var _RenkoPlotter = /** @class */ (function (_super) {
                __extends(_RenkoPlotter, _super);
                function _RenkoPlotter() {
                    return _super.call(this) || this;
                }
                _RenkoPlotter.prototype.clear = function () {
                    _super.prototype.clear.call(this);
                    this._boxSize = null;
                    this._rangeMode = null;
                };
                _RenkoPlotter.prototype._calculate = function (series) {
                    this._init();
                    var highs = series._getBindingValues(0), lows = series._getBindingValues(1), opens = series._getBindingValues(2), closes = series._getBindingValues(3), xs = series.getValues(1) || this.chart._xvals;
                    this._calculator = new finance._RenkoCalculator(highs, lows, opens, closes, xs, this._boxSize, this._rangeMode, this._fields, this._rounding);
                    this._rangeValues = this._calculator.calculate();
                    if (this._rangeValues === null || wijmo.isUndefined(this._rangeValues)) {
                        this._rangeValues = [];
                    }
                    // always regenerate x-axis labels at the end of each calculation cycle
                    this._generateXLabels(series);
                };
                _RenkoPlotter.prototype._init = function () {
                    _super.prototype._init.call(this);
                    // BoxSize
                    this._boxSize = this.getNumOption("boxSize", "renko") || 14;
                    // RangeMode
                    this._rangeMode = this.getOption("rangeMode", "renko") || finance.RangeMode.Fixed;
                    this._rangeMode = wijmo.asEnum(this._rangeMode, finance.RangeMode, true);
                    wijmo.assert(this._rangeMode !== finance.RangeMode.Percentage, "RangeMode.Percentage is not supported");
                    // DataFields
                    this._fields = this.getOption("fields", "renko") || finance.DataFields.Close;
                    this._fields = wijmo.asEnum(this._fields, finance.DataFields, true);
                    // todo: figure out HighLow
                    wijmo.assert(this._fields !== finance.DataFields.HighLow, "DataFields.HighLow is not supported");
                    // rounding - internal only
                    this._rounding = wijmo.asBoolean(this.getOption("rounding", "renko"), true);
                };
                _RenkoPlotter.prototype._generateXLabels = function (series) {
                    var _this = this;
                    _super.prototype._generateXLabels.call(this, series);
                    // bricks may have duplicate x-labels - prevent that behavior
                    this._rangeXLabels.forEach(function (value, index) {
                        // compare current item's text property to the previous item's _text property (backup for text)
                        if (index > 0 && _this._rangeXLabels[index - 1]._text === value.text) {
                            value.text = "";
                        }
                    }, this);
                };
                return _RenkoPlotter;
            }(finance._BaseRangePlotter));
            finance._RenkoPlotter = _RenkoPlotter;
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart) {
        var finance;
        (function (finance) {
            "use strict";
            // Plotter for Kagi FinancialChartType
            var _KagiPlotter = /** @class */ (function (_super) {
                __extends(_KagiPlotter, _super);
                function _KagiPlotter() {
                    return _super.call(this) || this;
                }
                _KagiPlotter.prototype._calculate = function (series) {
                    this._init();
                    var highs = series._getBindingValues(0), lows = series._getBindingValues(1), opens = series._getBindingValues(2), closes = series._getBindingValues(3), xs = series.getValues(1) || this.chart._xvals;
                    this._calculator = new finance._KagiCalculator(highs, lows, opens, closes, xs, this._reversalAmount, this._rangeMode, this._fields);
                    this._rangeValues = this._calculator.calculate();
                    if (this._rangeValues === null || wijmo.isUndefined(this._rangeValues)) {
                        this._rangeValues = [];
                    }
                    // always regenerate x-axis labels at the end of each calculation cycle
                    this._generateXLabels(series);
                };
                _KagiPlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser) {
                    this._calculate(series);
                    var si = this.chart.series.indexOf(series), len = this._rangeValues.length, xmin = ax.actualMin, xmax = ax.actualMax, strWidth = this._DEFAULT_WIDTH, stroke = series._getSymbolStroke(si), altStroke = series._getAltSymbolStroke(si) || stroke, dx = [], dy = [];
                    engine.stroke = stroke;
                    engine.strokeWidth = strWidth;
                    var itemIndex = 0, x, start, end, min, max, area, dpt;
                    engine.startGroup();
                    for (var i = 0; i < len; i++) {
                        x = i;
                        if (chart._DataInfo.isValid(x) && xmin <= x && x <= xmax) {
                            start = this._rangeValues[i].open;
                            end = this._rangeValues[i].close;
                            // main (vertical) line
                            if (i === 0) {
                                min = Math.min(start, end);
                                max = Math.max(start, end);
                                // determine thinkness
                                engine.strokeWidth = start > end ? strWidth : strWidth * 2;
                                // determine stroke
                                engine.stroke = start > end ? stroke : altStroke;
                                // main line
                                engine.drawLine(ax.convert(x), ay.convert(start), ax.convert(x), ay.convert(end));
                                // initial inflection line
                                engine.drawLine(ax.convert(x - 1) - (engine.strokeWidth / 2), ay.convert(start), ax.convert(x) + (engine.strokeWidth / 2), ay.convert(start));
                            }
                            else if (engine.strokeWidth === strWidth) {
                                if (end > start) {
                                    if (end > max) {
                                        // change in thickness
                                        engine.drawLine(ax.convert(x), ay.convert(start), ax.convert(x), ay.convert(max));
                                        engine.strokeWidth = strWidth * 2;
                                        engine.stroke = altStroke;
                                        engine.drawLine(ax.convert(x), ay.convert(max), ax.convert(x), ay.convert(end));
                                        // new min
                                        min = start;
                                    }
                                    else {
                                        // maintain current thickness
                                        engine.drawLine(ax.convert(x), ay.convert(start), ax.convert(x), ay.convert(end));
                                    }
                                    // new max
                                    max = end;
                                }
                                else {
                                    engine.drawLine(ax.convert(x), ay.convert(start), ax.convert(x), ay.convert(end));
                                }
                            }
                            else if ((engine.strokeWidth / 2) === strWidth) {
                                if (end < start) {
                                    if (end < min) {
                                        // change in thickness
                                        engine.drawLine(ax.convert(x), ay.convert(start), ax.convert(x), ay.convert(min));
                                        engine.strokeWidth = strWidth;
                                        engine.stroke = stroke;
                                        engine.drawLine(ax.convert(x), ay.convert(min), ax.convert(x), ay.convert(end));
                                        // new max
                                        max = start;
                                    }
                                    else {
                                        // maintain thickness
                                        engine.drawLine(ax.convert(x), ay.convert(start), ax.convert(x), ay.convert(end));
                                    }
                                    // new min
                                    min = end;
                                }
                                else {
                                    engine.drawLine(ax.convert(x), ay.convert(start), ax.convert(x), ay.convert(end));
                                }
                            }
                            // inflection (horizontal) line
                            if (i < (len - 1)) {
                                // x needs to account for engine.strokeWidth, after conversion, to prevent corner gaps
                                // where horizontal and vertical lines meet
                                engine.drawLine(ax.convert(x) - (engine.strokeWidth / 2), ay.convert(end), ax.convert(x + 1) + (engine.strokeWidth / 2), ay.convert(end));
                            }
                            // manually specify values for HitTestInfo
                            dpt = this._getDataPoint(si, i, series, end);
                            // add item to HitTester
                            area = new chart._CircleArea(new wijmo.Point(ax.convert(x), ay.convert(end)), 0.5 * engine.strokeWidth);
                            area.tag = dpt;
                            this.hitTester.add(area, si);
                            // point index
                            series._setPointIndex(i, itemIndex);
                            itemIndex++;
                            // append x/y values to collection for _LinesArea which
                            // is needed for selection
                            dx.push(ax.convert(x));
                            dy.push(ay.convert(start));
                            dx.push(ax.convert(x));
                            dy.push(ay.convert(end));
                        }
                    }
                    engine.endGroup();
                    // add _LinesArea for selection
                    this.hitTester.add(new chart._LinesArea(dx, dy), si);
                };
                _KagiPlotter.prototype._init = function () {
                    _super.prototype._init.call(this);
                    // ReversalAmount
                    this._reversalAmount = this.getNumOption("reversalAmount", "kagi") || 14;
                    // RangeMode
                    this._rangeMode = this.getOption("rangeMode", "kagi") || finance.RangeMode.Fixed;
                    this._rangeMode = wijmo.asEnum(this._rangeMode, finance.RangeMode, true);
                    // DataFields
                    this._fields = this.getOption("fields", "kagi") || finance.DataFields.Close;
                    this._fields = wijmo.asEnum(this._fields, finance.DataFields, true);
                };
                _KagiPlotter.prototype.clear = function () {
                    _super.prototype.clear.call(this);
                    this._reversalAmount = null;
                    this._rangeMode = null;
                };
                return _KagiPlotter;
            }(finance._BaseRangePlotter));
            finance._KagiPlotter = _KagiPlotter;
        })(finance = chart.finance || (chart.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var chart;
    (function (chart_1) {
        var finance;
        (function (finance) {
            "use strict";
            /**
             * Specifies the scaling mode for point and figure chart.
             */
            var PointAndFigureScaling;
            (function (PointAndFigureScaling) {
                /** Traditional scaling. The box size is calculated automatically based on price range.  */
                PointAndFigureScaling[PointAndFigureScaling["Traditional"] = 0] = "Traditional";
                /** Fixed scaling. The box size is defined by boxSize property. */
                PointAndFigureScaling[PointAndFigureScaling["Fixed"] = 1] = "Fixed";
                /** Dynamic(ATR) scaling. The box size is calculated based on ATR. */
                PointAndFigureScaling[PointAndFigureScaling["Dynamic"] = 2] = "Dynamic";
                // TODO: Percentage,
            })(PointAndFigureScaling = finance.PointAndFigureScaling || (finance.PointAndFigureScaling = {}));
            // Plotter for PointAndFigure FinancialChartType
            var _PointAndFigurePlotter = /** @class */ (function (_super) {
                __extends(_PointAndFigurePlotter, _super);
                function _PointAndFigurePlotter() {
                    return _super.call(this) || this;
                }
                _PointAndFigurePlotter.prototype.clear = function () {
                    _super.prototype.clear.call(this);
                    this._boxSize = null;
                    this._fields = null;
                    this._reversal = null;
                    this._scaling = null;
                    // this._pfdata = null;
                };
                _PointAndFigurePlotter.prototype.unload = function () {
                    _super.prototype.unload.call(this);
                    this.chart.axisX.itemsSource = this._xlbls;
                };
                _PointAndFigurePlotter.prototype._init = function () {
                    this._boxSize = this.getNumOption("boxSize", "pointAndFigure") || 1;
                    this._reversal = this.getNumOption("reversal", "pointAndFigure") || 3;
                    this._period = this.getNumOption("period", "pointAndFigure") || 20;
                    // DataFields
                    this._fields = this.getOption("fields", "pointAndFigure") || finance.DataFields.Close;
                    this._fields = wijmo.asEnum(this._fields, finance.DataFields, true);
                    // todo: figure out HighLow
                    wijmo.assert((this._fields == finance.DataFields.Close) || (this._fields == finance.DataFields.HighLow), "Only DataFields.Close and DataFields.HighLow are supported");
                    this._scaling = this.getOption("scaling", "pointAndFigure") || PointAndFigureScaling.Traditional;
                    this._scaling = wijmo.asEnum(this._scaling, PointAndFigureScaling, true);
                    this._xlbls = [];
                };
                _PointAndFigurePlotter.prototype.adjustLimits = function (dataInfo, plotRect) {
                    this._init();
                    this.hitTester.clear();
                    var rect = new wijmo.Rect(0, 0, 0, 0);
                    var len = this.chart.series.length;
                    // only one supported at the moment - possibly remove later for overlays & indicators
                    wijmo.assert(len <= 1, "Current FinancialChartType only supports a single series");
                    if (len > 0) {
                        var series = this.chart.series[0];
                        var reversal = this._reversal;
                        var cv = series.collectionView ? series.collectionView : this.chart.collectionView;
                        var data = cv ? cv.items : null;
                        if (data && data.length > 0) {
                            var bnd0 = series._getBinding(0);
                            var bnd1 = series._getBinding(1);
                            var bnd2 = series._getBinding(2);
                            var bnd3 = series._getBinding(3);
                            if (this._fields == finance.DataFields.Close) {
                                if (bnd3) {
                                    bnd0 = bnd3; // HLOC
                                }
                                else if (bnd2) {
                                    bnd0 = bnd2; // HLC
                                }
                                bnd1 = bnd0;
                            }
                            var xbnd = series.bindingX ? series.bindingX : this.chart.bindingX;
                            var boxSize = this._actualBoxSize = this.calcBoxSize(data, bnd0, bnd1);
                            this._pfdata = this.calcPFHiLo2(data, bnd0, bnd1, xbnd, boxSize, reversal);
                            if (this._pfdata && this._pfdata.length > 0) {
                                var max = this._pfdata.reduce(function (a, b) {
                                    return Math.max(a, b.max);
                                }, this._pfdata[0].max);
                                var min = this._pfdata.reduce(function (a, b) {
                                    return Math.min(a, b.min);
                                }, this._pfdata[0].min);
                                rect = new wijmo.Rect(-0.5, min - 0.5 * boxSize, this._pfdata.length, max - min + boxSize);
                                for (var i = 1; i < this._pfdata.length; i++) {
                                    var item0 = this._pfdata[i - 1];
                                    var item = this._pfdata[i];
                                    if (wijmo.isDate(item.date) && wijmo.isDate(item0.date) && item.date.getYear() != item0.date.getYear()) {
                                        this._xlbls.push({ value: i, text: wijmo.Globalize.formatNumber(item.date.getFullYear() % 100, 'd2') });
                                    }
                                }
                            }
                        }
                    }
                    if (this._xlbls.length == 0) {
                        this._xlbls.push({ value: 0 });
                    }
                    this.chart.axisY.majorGrid = false;
                    this.chart.axisX.itemsSource = this._xlbls;
                    return rect;
                };
                _PointAndFigurePlotter.prototype.plotSeries = function (engine, ax, ay, series, palette, iser, nser) {
                    if (this._pfdata && this._pfdata.length > 0) {
                        var boxSize = this._actualBoxSize;
                        this.renderGrid(engine, this._pfdata, boxSize);
                        this.renderData(this.chart, engine, this._pfdata, boxSize);
                    }
                };
                _PointAndFigurePlotter.prototype.calcBoxSize = function (data, fieldHi, fieldLo) {
                    var high = data.reduce(function (a, b) {
                        return Math.max(a, b[fieldHi]);
                    }, data[0][fieldHi]);
                    var low = data.reduce(function (a, b) {
                        return Math.min(a, b[fieldLo]);
                    }, data[0][fieldLo]);
                    var boxSize = this._boxSize;
                    var range = high - low;
                    switch (this._scaling) {
                        case PointAndFigureScaling.Traditional:
                            if (range < 0.25) {
                                boxSize = 0.0625;
                            }
                            else if (range >= 0.25 && range < 1.00) {
                                boxSize = 0.125;
                            }
                            else if (range >= 1.00 && range < 5.00) {
                                boxSize = 0.25;
                            }
                            else if (range >= 5.00 && range < 20.00) {
                                boxSize = 0.50;
                            }
                            else if (range >= 20.00 && range < 100) {
                                boxSize = 1.00;
                            }
                            else if (range >= 100 && range < 200) {
                                boxSize = 2.00;
                            }
                            else if (range >= 200 && range < 500) {
                                boxSize = 4.00;
                            }
                            else if (range >= 500 && range < 1000) {
                                boxSize = 5.00;
                            }
                            else if (range >= 1000 && range < 25000) {
                                boxSize = 50.00;
                            }
                            else if (range > -25000) {
                                boxSize = 500;
                            }
                            break;
                        case PointAndFigureScaling.Dynamic:
                            var series = this.chart.series[0];
                            var highs = series._getBindingValues(0), lows = series._getBindingValues(1), opens = series._getBindingValues(2), closes = series._getBindingValues(3);
                            var atrs = finance._avgTrueRng(highs, lows, closes, this._period);
                            boxSize = atrs[atrs.length - 1];
                            break;
                        case PointAndFigureScaling.Fixed:
                            break;
                        default:
                            break;
                    }
                    return boxSize;
                };
                _PointAndFigurePlotter.prototype.calcPFHiLo2 = function (data, fieldHi, fieldLo, xbnd, boxSize, reversal) {
                    var result = [];
                    for (var i = 0; i < data.length; i++) {
                        var high = data[i][fieldHi];
                        var low = data[i][fieldLo];
                        wijmo.assert(high >= low, "'High' value must be larger than 'low' value.");
                        var date = data[i][xbnd];
                        if (result.length == 0) {
                            result.push({ min: this.roundDown(low, boxSize), max: this.roundDown(high, boxSize), rise: false, date: date });
                        }
                        else {
                            var cur = result[result.length - 1];
                            if (cur.rise) {
                                var ap1 = cur.max + boxSize;
                                var ap2 = cur.max - reversal * boxSize;
                                // console.log(ap1.toString() + ' ' + ap2.toString());
                                if (this.roundUp(high, boxSize) >= ap1) {
                                    cur.max = this.roundUp(high, boxSize);
                                }
                                else if (low <= ap2) {
                                    result.push({ min: this.roundDown(low, boxSize), max: cur.max - boxSize, rise: false, date: date });
                                }
                            }
                            else {
                                var ap1 = cur.min - boxSize;
                                var ap2 = cur.min + reversal * boxSize;
                                // console.log(ap1.toString() + ' ' + ap2.toString());
                                if (this.roundDown(low, boxSize) <= ap1) {
                                    cur.min = this.roundDown(low, boxSize);
                                }
                                else if (high >= ap2) {
                                    result.push({ min: cur.min + boxSize, max: this.roundUp(high, boxSize), rise: true, date: date });
                                }
                            }
                        }
                    }
                    if (result.length > 0) {
                        var item = result[0];
                        if (item.min == item.max) {
                            result.splice(0, 1);
                        }
                    }
                    return result;
                };
                _PointAndFigurePlotter.prototype.roundUp = function (val, boxSize) {
                    return Math.ceil(val / boxSize - 0.999999) * boxSize;
                };
                _PointAndFigurePlotter.prototype.roundDown = function (val, boxSize) {
                    return Math.floor(val / boxSize + 0.999999) * boxSize;
                };
                _PointAndFigurePlotter.prototype.renderGrid = function (engine, data, boxSize) {
                    if (this._pfdata) {
                        var max = this._pfdata.reduce(function (a, b) {
                            return Math.max(a, b.max);
                        }, this._pfdata[0].max);
                        var min = this._pfdata.reduce(function (a, b) {
                            return Math.min(a, b.min);
                        }, this._pfdata[0].min);
                        var chart = this.chart;
                        var xmin = -0.5; //chart.axisX.actualMin;
                        var xmax = this._pfdata.length; //chart.axisX.actualMax;
                        //if (isDate(xmin)) {
                        //    xmin = asDate(xmin).valueOf();
                        //}
                        //if (isDate(xmax)) {
                        //    xmax = asDate(xmax).valueOf();
                        //}
                        for (var val = min - 0.5 * boxSize; val <= max + boxSize; val += boxSize) {
                            var pt1 = new wijmo.Point(xmin, val);
                            pt1 = chart.dataToPoint(pt1);
                            var pt2 = new wijmo.Point(xmax, val);
                            pt2 = chart.dataToPoint(pt2);
                            engine.stroke = chart_1.FlexChart._FG;
                            engine.strokeWidth = 1;
                            engine.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, chart_1.FlexChart._CSS_GRIDLINE);
                        }
                        for (var x = xmin; x <= xmax; x += 1) {
                            var pt1 = new wijmo.Point(x, this.chart.axisY.actualMin);
                            pt1 = chart.dataToPoint(pt1);
                            var pt2 = new wijmo.Point(x, this.chart.axisY.actualMax);
                            pt2 = chart.dataToPoint(pt2);
                            engine.stroke = chart_1.FlexChart._FG;
                            engine.strokeWidth = 1;
                            engine.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, chart_1.FlexChart._CSS_GRIDLINE);
                        }
                    }
                };
                _PointAndFigurePlotter.prototype.renderData = function (chart, engine, data, boxSize) {
                    var si = 0; // single series
                    var series = chart.series[0];
                    var stroke = series._getSymbolStroke(si), altStroke = series._getAltSymbolStroke(si) || stroke;
                    for (var i = 0; i < data.length; i++) {
                        var item = data[i];
                        var nsym = (data[i].max - data[i].min) / boxSize;
                        if (nsym == 0) {
                            continue;
                        }
                        var pt1 = new wijmo.Point(i - 0.5, item.min); // + 0.5*boxSize);
                        pt1 = chart.dataToPoint(pt1);
                        var pt2 = new wijmo.Point(i + 0.5, item.max); // + 0.5*boxSize);
                        pt2 = chart.dataToPoint(pt2);
                        engine.fill = 'transparent';
                        var h0 = (pt2.y - pt1.y) / nsym;
                        for (var j = 0; j < nsym + 1; j++) {
                            engine.strokeWidth = 1.5;
                            if (item.rise) {
                                engine.stroke = stroke;
                                engine.drawLine(pt1.x, pt1.y + (j - 0.5) * h0, pt2.x, pt1.y + (j + 0.5) * h0);
                                engine.drawLine(pt2.x, pt1.y + (j - 0.5) * h0, pt1.x, pt1.y + (j + 0.5) * h0);
                            }
                            else {
                                engine.stroke = altStroke;
                                engine.drawEllipse(0.5 * (pt1.x + pt2.x), pt1.y + j * h0, 0.5 * Math.abs(pt1.x - pt2.x), 0.5 * Math.abs(h0));
                            }
                            if (this.hitTester) {
                                var y = item.min + j * boxSize;
                                var dpt = new chart_1._DataPoint(si, i, item.date, y);
                                dpt["y"] = y;
                                dpt["yfmt"] = this.chart.axisY._formatValue(y);
                                if (wijmo.isDate(item.date)) {
                                    dpt["x"] = item.date;
                                    dpt["xfmt"] = wijmo.Globalize.formatDate(item.date, 'd');
                                }
                                var rect = new wijmo.Rect(Math.min(pt1.x, pt2.x), pt1.y + j * h0 - 0.5 * h0, Math.abs(pt2.x - pt1.x), h0);
                                if (rect.height < 0) {
                                    rect.top += h0;
                                    rect.height = -rect.height;
                                }
                                var ra = new chart_1._RectArea(rect);
                                ra.tag = dpt;
                                this.hitTester.add(ra, si);
                            }
                        }
                    }
                };
                return _PointAndFigurePlotter;
            }(chart_1._BasePlotter));
            finance._PointAndFigurePlotter = _PointAndFigurePlotter;
        })(finance = chart_1.finance || (chart_1.finance = {}));
    })(chart = wijmo.chart || (wijmo.chart = {}));
})(wijmo || (wijmo = {}));

