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
var wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      "use strict";

      function i(e) {
        return arguments.length > 1 && (e = Array.prototype.slice.call(arguments)), t.asArray(e, !1), e.reduce(function(e, i) {
          return e + t.asNumber(i)
        }, 0)
      }

      function n(e) {
        return arguments.length > 1 && (e = Array.prototype.slice.call(arguments)), t.asArray(e, !1), i(e) / e.length
      }

      function a(e) {
        arguments.length > 1 && (e = Array.prototype.slice.call(arguments)), t.asArray(e, !1);
        var i = n(e);
        return n(e.map(function(t) {
          return Math.pow(t - i, 2)
        }))
      }

      function r(e, i, n, a) {
        void 0 === a && (a = 14), t.asArray(e, !1), t.asArray(i, !1), t.asArray(n, !1), t.asInt(a, !1, !0);
        var r = Math.min(e.length, i.length, n.length),
          s = [];
        t.assert(r > a && a > 1, "True Range period must be an integer less than the length of the data and greater than one.");
        for (var o = 0; o < r; o++) t.asNumber(e[o], !1), t.asNumber(i[o], !1), t.asNumber(n[o], !1), 0 === o ? s.push(e[o] - i[o]) : s.push(Math.max(e[o] - i[o], Math.abs(e[o] - n[o - 1]), Math.abs(i[o] - n[o - 1])));
        return s
      }

      function s(e, i) {
        t.asArray(e, !1), t.asNumber(i, !1, !0), t.assert(e.length > i && i > 1, "Simple Moving Average period must be an integer less than the length of the data and greater than one.");
        for (var a = [], r = i; r <= e.length; r++) a.push(n(e.slice(r - i, r)));
        return a
      }
      e._trunc = function(e) {
        return t.asNumber(e, !0, !1), e > 0 ? Math.floor(e) : Math.ceil(e)
      }, e._sum = i, e._average = n, e._minimum = function(e) {
        return arguments.length > 1 && (e = Array.prototype.slice.call(arguments)), t.asArray(e, !1), Math.min.apply(null, e)
      }, e._maximum = function(e) {
        return arguments.length > 1 && (e = Array.prototype.slice.call(arguments)), t.asArray(e, !1), Math.max.apply(null, e)
      }, e._variance = a, e._stdDeviation = function(e) {
        return arguments.length > 1 && (e = Array.prototype.slice.call(arguments)), t.asArray(e, !1), Math.sqrt(a(e))
      }, e._avgTrueRng = function(e, i, a, s) {
        void 0 === s && (s = 14), t.asArray(e, !1), t.asArray(i, !1), t.asArray(a, !1), t.asInt(s, !1, !0);
        var o = r(e, i, a, s),
          l = Math.min(e.length, i.length, a.length, o.length),
          h = [];
        t.assert(l > s && s > 1, "Average True Range period must be an integer less than the length of the data and greater than one.");
        for (var c = 0; c < l; c++) t.asNumber(e[c], !1), t.asNumber(i[c], !1), t.asNumber(a[c], !1), t.asNumber(o[c], !1), c + 1 === s ? h.push(n(o.slice(0, s))) : c + 1 > s && h.push(((s - 1) * h[h.length - 1] + o[c]) / s);
        return h
      }, e._trueRng = r, e._sma = s, e._ema = function(e, i) {
        t.asArray(e, !1), t.asNumber(i, !1, !0), t.assert(e.length > i && i > 1, "Exponential Moving Average period must be an integer less than the length of the data and greater than one.");
        var n = [],
          a = 2 / (i + 1),
          r = s(e, i);
        e = e.slice(i - 1);
        for (var o = 0; o < e.length; o++) 0 === o ? n.push(r[0]) : n.push((e[o] - n[o - 1]) * a + n[o - 1]);
        return n
      }, e._range = function(e, i, n) {
        void 0 === n && (n = 1), t.asNumber(e, !1), t.asNumber(i, !1), t.asNumber(n, !1), t.assert(e < i, "begin argument must be less than end argument.");
        for (var a = [], r = e; r <= i; r += n) a.push(r);
        return a
      }
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n;
      ! function(t) {
        t[t.Column = 0] = "Column", t[t.Scatter = 1] = "Scatter", t[t.Line = 2] = "Line", t[t.LineSymbols = 3] = "LineSymbols", t[t.Area = 4] = "Area", t[t.Candlestick = 5] = "Candlestick", t[t.HighLowOpenClose = 6] = "HighLowOpenClose", t[t.HeikinAshi = 7] = "HeikinAshi", t[t.LineBreak = 8] = "LineBreak", t[t.Renko = 9] = "Renko", t[t.Kagi = 10] = "Kagi", t[t.ColumnVolume = 11] = "ColumnVolume", t[t.EquiVolume = 12] = "EquiVolume", t[t.CandleVolume = 13] = "CandleVolume", t[t.ArmsCandleVolume = 14] = "ArmsCandleVolume", t[t.PointAndFigure = 15] = "PointAndFigure"
      }(n = i.FinancialChartType || (i.FinancialChartType = {}));
      var a = function(a) {
        function r(t, e) {
          var i = a.call(this, t, null) || this;
          return i._chartType = n.Line, i.__heikinAshiPlotter = null, i.__lineBreakPlotter = null, i.__renkoPlotter = null, i.__kagiPlotter = null, i.__pfPlotter = null, i.initialize(e), i
        }
        return __extends(r, a), r.prototype._getProductInfo = function() {
          return "A78U,FinancialChart"
        }, Object.defineProperty(r.prototype, "chartType", {
          get: function() {
            return this._chartType
          },
          set: function(e) {
            (e = t.asEnum(e, n)) != this._chartType && (this._chartType = e, this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "options", {
          get: function() {
            return this._options
          },
          set: function(t) {
            t != this._options && (this._options = t, this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "_heikinAshiPlotter", {
          get: function() {
            return null === this.__heikinAshiPlotter && (this.__heikinAshiPlotter = new i._HeikinAshiPlotter, this._initPlotter(this.__heikinAshiPlotter)), this.__heikinAshiPlotter
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "_lineBreakPlotter", {
          get: function() {
            return null === this.__lineBreakPlotter && (this.__lineBreakPlotter = new i._LineBreakPlotter, this._initPlotter(this.__lineBreakPlotter)), this.__lineBreakPlotter
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "_renkoPlotter", {
          get: function() {
            return null === this.__renkoPlotter && (this.__renkoPlotter = new i._RenkoPlotter, this._initPlotter(this.__renkoPlotter)), this.__renkoPlotter
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "_kagiPlotter", {
          get: function() {
            return null === this.__kagiPlotter && (this.__kagiPlotter = new i._KagiPlotter, this._initPlotter(this.__kagiPlotter)), this.__kagiPlotter
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "_pfPlotter", {
          get: function() {
            return null === this.__pfPlotter && (this.__pfPlotter = new i._PointAndFigurePlotter, this._initPlotter(this.__pfPlotter)), this.__pfPlotter
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype._getChartType = function() {
          var t = null;
          switch (this.chartType) {
            case n.Area:
              t = e.ChartType.Area;
              break;
            case n.Line:
            case n.Kagi:
            case n.PointAndFigure:
              t = e.ChartType.Line;
              break;
            case n.Column:
            case n.ColumnVolume:
              t = e.ChartType.Column;
              break;
            case n.LineSymbols:
              t = e.ChartType.LineSymbols;
              break;
            case n.Scatter:
              t = e.ChartType.Scatter;
              break;
            case n.Candlestick:
            case n.Renko:
            case n.HeikinAshi:
            case n.LineBreak:
            case n.EquiVolume:
            case n.CandleVolume:
            case n.ArmsCandleVolume:
              t = e.ChartType.Candlestick;
              break;
            case n.HighLowOpenClose:
              t = e.ChartType.HighLowOpenClose
          }
          return t
        }, r.prototype._getPlotter = function(e) {
          var i = this.chartType,
            r = null;
          if (e) {
            var s = e.chartType;
            s && !t.isUndefined(s) && s != i && (i = s, !0)
          }
          switch (i) {
            case n.HeikinAshi:
              r = this._heikinAshiPlotter;
              break;
            case n.LineBreak:
              r = this._lineBreakPlotter;
              break;
            case n.Renko:
              r = this._renkoPlotter;
              break;
            case n.Kagi:
              r = this._kagiPlotter;
              break;
            case n.ColumnVolume:
              (r = a.prototype._getPlotter.call(this, e)).isVolume = !0, r.width = 1;
              break;
            case n.EquiVolume:
              (r = a.prototype._getPlotter.call(this, e)).isEqui = !0, r.isCandle = !1, r.isArms = !1, r.isVolume = !0, r.symbolWidth = "100%";
              break;
            case n.CandleVolume:
              (r = a.prototype._getPlotter.call(this, e)).isEqui = !1, r.isCandle = !0, r.isArms = !1, r.isVolume = !0, r.symbolWidth = "100%";
              break;
            case n.ArmsCandleVolume:
              (r = a.prototype._getPlotter.call(this, e)).isEqui = !1, r.isCandle = !1, r.isArms = !0, r.isVolume = !0, r.symbolWidth = "100%";
              break;
            case n.PointAndFigure:
              r = this._pfPlotter;
              break;
            default:
              r = a.prototype._getPlotter.call(this, e)
          }
          return r
        }, r.prototype._createSeries = function() {
          return new i.FinancialSeries
        }, r
      }(e.FlexChartCore);
      i.FinancialChart = a
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n = function(n) {
        function a() {
          return null !== n && n.apply(this, arguments) || this
        }
        return __extends(a, n), Object.defineProperty(a.prototype, "chartType", {
          get: function() {
            return this._finChartType
          },
          set: function(e) {
            (e = t.asEnum(e, i.FinancialChartType, !0)) != this._finChartType && (this._finChartType = e, this._invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), a.prototype._getChartType = function() {
          var t = null;
          switch (this.chartType) {
            case i.FinancialChartType.Area:
              t = e.ChartType.Area;
              break;
            case i.FinancialChartType.Line:
            case i.FinancialChartType.Kagi:
            case i.FinancialChartType.PointAndFigure:
              t = e.ChartType.Line;
              break;
            case i.FinancialChartType.Column:
            case i.FinancialChartType.ColumnVolume:
              t = e.ChartType.Column;
              break;
            case i.FinancialChartType.LineSymbols:
              t = e.ChartType.LineSymbols;
              break;
            case i.FinancialChartType.Scatter:
              t = e.ChartType.Scatter;
              break;
            case i.FinancialChartType.Candlestick:
            case i.FinancialChartType.Renko:
            case i.FinancialChartType.HeikinAshi:
            case i.FinancialChartType.LineBreak:
            case i.FinancialChartType.EquiVolume:
            case i.FinancialChartType.CandleVolume:
            case i.FinancialChartType.ArmsCandleVolume:
              t = e.ChartType.Candlestick;
              break;
            case i.FinancialChartType.HighLowOpenClose:
              t = e.ChartType.HighLowOpenClose
          }
          return t
        }, a.prototype.getDataRect = function(i, n) {
          if (n) return n;
          var a = this.getValues(0),
            r = this.getValues(1) || (this.chart._xvals && this.chart._xvals.length ? this.chart._xvals : null),
            s = this._getBinding(0),
            o = this._getBinding(1),
            l = this._getBinding(2),
            h = this._getBinding(3),
            c = (this._plotter, this._getChartType() || this.chart._getChartType());
          if (c !== e.ChartType.HighLowOpenClose && c !== e.ChartType.Candlestick || s === o) return null;
          if (a) {
            for (var u = NaN, _ = NaN, p = NaN, g = NaN, f = a.length, d = 0; d < f; d++) {
              var m = this._getItem(d),
                y = a[d];
              if (isFinite(y) && [y, o ? m[o] : null, l ? m[l] : null, h ? m[h] : null].forEach(function(t) {
                e._DataInfo.isValid(t) && null !== t && ((isNaN(_) || t < _) && (_ = t), (isNaN(g) || t > g) && (g = t))
              }), r) {
                var v = r[d];
                isFinite(v) && (isNaN(u) ? u = p = v : v < u ? u = v : y > g && (p = v))
              }
            }
            if (r || (u = 0, p = f - 1), !isNaN(_)) return new t.Rect(u, _, p - u, g - _)
          }
          return null
        }, a
      }(e.SeriesBase);
      i.FinancialSeries = n
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(t) {
    ! function(t) {
      "use strict";
      var e = function() {
        function t(t, e, i, n) {
          this.highs = t, this.lows = e, this.opens = i, this.closes = n
        }
        return t.prototype.calculate = function() {}, t
      }();
      t._BaseCalculator = e;
      var i = function(e) {
        function i(t, i, n, a) {
          return e.call(this, t, i, n, a) || this
        }
        return __extends(i, e), i.prototype.calculate = function() {
          var e, i, n, a, r = Math.min(this.highs.length, this.lows.length, this.opens.length, this.closes.length),
            s = [];
          if (r <= 0) return s;
          for (var o = 0; o < r; o++) a = t._average(this.highs[o], this.lows[o], this.opens[o], this.closes[o]), 0 === o ? (n = t._average(this.opens[o], this.closes[o]), e = this.highs[o], i = this.lows[o]) : (n = t._average(s[o - 1].open, s[o - 1].close), e = Math.max(this.highs[o], n, a), i = Math.min(this.lows[o], n, a)), s.push({
            high: e,
            low: i,
            close: a,
            open: n,
            pointIndex: o,
            x: null
          });
          return s
        }, i
      }(e);
      t._HeikinAshiCalculator = i;
      var n = function(e) {
        function i(t, i, n, a, r, s, o, l) {
          var h = e.call(this, t, i, n, a) || this;
          return h.xs = r, h.size = s, h.unit = o, h.fields = l, h
        }
        return __extends(i, e), i.prototype._getValues = function() {
          var e, i = [],
            n = Math.min(this.highs.length, this.lows.length, this.opens.length, this.closes.length);
          switch (this.fields) {
            case t.DataFields.High:
              i = this.highs;
              break;
            case t.DataFields.Low:
              i = this.lows;
              break;
            case t.DataFields.Open:
              i = this.opens;
              break;
            case t.DataFields.HL2:
              for (e = 0; e < n; e++) i.push(t._average(this.highs[e], this.lows[e]));
              break;
            case t.DataFields.HLC3:
              for (e = 0; e < n; e++) i.push(t._average(this.highs[e], this.lows[e], this.closes[e]));
              break;
            case t.DataFields.HLOC4:
              for (e = 0; e < n; e++) i.push(t._average(this.highs[e], this.lows[e], this.opens[e], this.closes[e]));
              break;
            case t.DataFields.Close:
            default:
              i = this.closes
          }
          return i
        }, i.prototype._getSize = function() {
          var e = this.unit === t.RangeMode.ATR ? t._avgTrueRng(this.highs, this.lows, this.closes, this.size) : null;
          return this.unit === t.RangeMode.ATR ? e[e.length - 1] : this.size
        }, i
      }(e);
      t._BaseRangeCalculator = n;
      var a = function(t) {
        function e(e, i, n, a, r, s) {
          return t.call(this, e, i, n, a, r, s) || this
        }
        return __extends(e, t), e.prototype.calculate = function() {
          var t = null !== this.xs && this.xs.length > 0,
            e = this.closes.length,
            i = [],
            n = [
              [],
              []
            ];
          if (e <= 0) return i;
          for (var a, r, s, o, l, h, c, u = [], _ = 1; _ < e; _++) {
            if (o = i.length, l = o - 1, r = t ? this.xs[_] : _, s = this.closes[_], -1 === l) {
              if ((a = this.closes[0]) === s) continue
            } else if (u = this._trendExists(n) || 1 === this.size ? n[0].slice(-this.size).concat(n[1].slice(-this.size)) : n[0].slice(1 - this.size).concat(n[1].slice(1 - this.size)), h = Math.max.apply(null, u), c = Math.min.apply(null, u), s > h) a = Math.max(n[0][l], n[1][l]);
            else {
              if (!(s < c)) continue;
              a = Math.min(n[0][l], n[1][l])
            }
            n[0].push(a), n[1].push(s), i.push({
              high: Math.max(a, s),
              low: Math.min(a, s),
              open: a,
              close: s,
              x: r,
              pointIndex: _
            })
          }
          return i
        }, e.prototype._trendExists = function(t) {
          if (t[1].length < this.size) return !1;
          var e, i = !1,
            n = t[1].slice(-this.size);
          for (e = 1; e < this.size && (i = n[e] > n[e - 1]); e++);
          if (!i)
            for (e = 1; e < this.size && (i = n[e] < n[e - 1]); e++);
          return i
        }, e
      }(n);
      t._LineBreakCalculator = a;
      var r = function(e) {
        function i(t, i, n, a, r, s, o, l) {
          return e.call(this, t, i, n, a, r, s, o, l) || this
        }
        return __extends(i, e), i.prototype.calculate = function() {
          var e = this._getSize(),
            i = Math.min(this.highs.length, this.lows.length, this.opens.length, this.closes.length),
            n = this._getValues(),
            a = null !== this.xs && this.xs.length > 0,
            r = [],
            s = [
              [],
              []
            ];
          if (i <= 0) return r;
          for (var o, l, h, c, u, _, p, g, f, d, m = 1; m < i; m++) {
            if (c = r.length, u = c - 1, l = a ? this.xs[m] : m, d = m, f = !1, this.fields === t.DataFields.HighLow)
              if (-1 === u)
                if (this.highs[m] > this.highs[0]) h = this.highs[m];
                else {
                  if (!(this.lows[m] < this.lows[0])) continue;
                  h = this.lows[m]
                } else if ((g = s[1][u] - s[0][u]) > 0)
                if (this.highs[m] > s[1][u]) h = this.highs[m];
                else {
                  if (!(this.lows[m] < s[1][u])) continue;
                  h = this.lows[m]
                } else if (this.lows[m] < s[1][u]) h = this.lows[m];
              else {
                if (!(this.highs[m] > s[1][u])) continue;
                h = this.highs[m]
              } else h = n[m];
            if (this.unit === t.RangeMode.Percentage && (e = h * this.size), -1 === u) {
              if (l = a ? this.xs[0] : 0, d = 0, o = this.fields === t.DataFields.HighLow ? null == this.highs[0] ? this.highs[this.highs.length - 1] : this.highs[0] : null == n[0] ? n[n.length - 1] : n[0], (g = Math.abs(o - h) || 0) < e) continue
            } else if (g = s[1][u] - s[0][u], p = Math.max(s[0][u], s[1][u]), _ = Math.min(s[0][u], s[1][u]), g > 0)
              if (h > p) f = !0;
              else {
                if (!((g = p - h) >= e)) continue;
                o = p
              } else if (h < _) f = !0;
            else {
              if (!((g = h - _) >= e)) continue;
              o = _
            }
            f ? (s[1][u] = h, r[u].close = h, r[u].high = Math.max(r[u].open, r[u].close), r[u].low = Math.min(r[u].open, r[u].close)) : (s[0].push(o), s[1].push(h), r.push({
              high: Math.max(o, h),
              low: Math.min(o, h),
              open: o,
              close: h,
              x: l,
              pointIndex: d
            }))
          }
          return r
        }, i
      }(n);
      t._KagiCalculator = r;
      var s = function(e) {
        function i(t, i, n, a, r, s, o, l, h) {
          void 0 === h && (h = !1);
          var c = e.call(this, t, i, n, a, r, s, o, l) || this;
          return c.rounding = h, c
        }
        return __extends(i, e), i.prototype.calculate = function() {
          var e = this._getSize(),
            i = Math.min(this.highs.length, this.lows.length, this.opens.length, this.closes.length),
            n = null !== this.xs && this.xs.length > 0,
            a = this._getValues(),
            r = [],
            s = [
              [],
              []
            ];
          if (i <= 0) return r;
          for (var o, l, h, c, u, _, p, g, f = 1; f < i; f++) {
            if (c = r.length, u = c - 1, l = n ? this.xs[f] : f, this.fields === t.DataFields.HighLow)
              if (-1 === u)
                if (this.highs[f] - this.highs[0] > e) o = this.highs[0], h = this.highs[f];
                else {
                  if (!(this.lows[0] - this.lows[f] > e)) continue;
                  o = this.lows[0], h = this.lows[f]
                } else if (_ = Math.min(s[0][u], s[1][u]), p = Math.max(s[0][u], s[1][u]), this.highs[f] - p > e) o = p, h = this.highs[f];
              else {
                if (!(_ - this.lows[f] > e)) continue;
                o = _, h = this.lows[f]
              } else if (h = a[f], -1 === u) o = a[0];
            else if (_ = Math.min(s[0][u], s[1][u]), p = Math.max(s[0][u], s[1][u]), h > p) o = p;
            else {
              if (!(h < _)) continue;
              o = _
            }
            if (g = h - o, !(Math.abs(g) < e)) {
              g = t._trunc(g / e);
              for (var d = 0; d < Math.abs(g); d++) {
                var m = {};
                this.rounding && (o = this._round(o, e)), s[0].push(o), m.open = o, o = g > 0 ? o + e : o - e, s[1].push(o), m.close = o, m.x = l, m.pointIndex = f, m.high = Math.max(m.open, m.close), m.low = Math.min(m.open, m.close), r.push(m)
              }
            }
          }
          return r
        }, i.prototype._round = function(t, e) {
          return Math.round(t / e) * e
        }, i
      }(n);
      t._RenkoCalculator = s
    }(t.finance || (t.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n = function(n) {
        function a() {
          var t = n.call(this) || this;
          return t._symFactor = .7, t.clear(), t
        }
        return __extends(a, n), a.prototype.clear = function() {
          n.prototype.clear.call(this), this._haValues = null, this._calculator = null
        }, a.prototype.plotSeries = function(i, n, a, r, s, o, l) {
          var h = this;
          this._calculate(r);
          var c = t.asType(r, e.SeriesBase),
            u = this.chart.series.indexOf(r),
            _ = r.getValues(1),
            p = this._symFactor,
            g = this._haValues.length,
            f = !0;
          if (_) {
            var d = this.dataInfo.getDeltaX();
            d > 0 && (p *= d)
          } else _ = this.dataInfo.getXVals();
          _ ? g = Math.min(g, _.length) : (f = !1, _ = new Array(g));
          var m = this._DEFAULT_WIDTH,
            y = c._getSymbolFill(u),
            v = c._getAltSymbolFill(u) || "transparent",
            x = c._getSymbolStroke(u),
            w = c._getAltSymbolStroke(u) || x,
            b = p,
            k = r.getDataType(1) || r.chart._xDataType;
          i.strokeWidth = m;
          for (var P, C, V, A, F, M, T, L, D = n.actualMin, S = n.actualMax, O = 0, B = 0; B < g; B++)
            if (V = f ? _[B] : B, e._DataInfo.isValid(V) && D <= V && V <= S) {
              if (F = this._haValues[B].high, M = this._haValues[B].low, T = this._haValues[B].open, L = this._haValues[B].close, P = T < L ? v : y, C = T < L ? w : x, i.fill = P, i.stroke = C, i.startGroup(), A = this._getDataPoint(u, B, V, r), this.chart.itemFormatter) {
                var j = new e.HitTestInfo(this.chart, new t.Point(n.convert(V), a.convert(F)), e.ChartElement.SeriesSymbol);
                j._setData(c, B), j._setDataPoint(A), this.chart.itemFormatter(i, j, function() {
                  h._drawSymbol(i, n, a, u, B, P, b, V, F, M, T, L, A, k)
                })
              } else this._drawSymbol(i, n, a, u, B, P, b, V, F, M, T, L, A, k);
              i.endGroup(), r._setPointIndex(B, O), O++
            }
        }, a.prototype._drawSymbol = function(i, n, a, r, s, o, l, h, c, u, _, p, g, f) {
          var d, m = null,
            y = null,
            v = null,
            x = null,
            w = f === t.DataType.Date ? 432e5 : .5;
          if (v = n.convert(h - w * l), x = n.convert(h + w * l), v > x) {
            var b = v;
            v = x, x = b
          }
          h = n.convert(h), e._DataInfo.isValid(_) && e._DataInfo.isValid(p) && (_ = a.convert(_), p = a.convert(p), y = (m = Math.min(_, p)) + Math.abs(_ - p), i.drawRect(v, m, x - v, y - m), (d = new e._RectArea(new t.Rect(v, m, x - v, y - m))).tag = g, this.hitTester.add(d, r)), e._DataInfo.isValid(c) && (c = a.convert(c), null !== m && (i.drawLine(h, m, h, c), d.rect.top = c, d.rect.height = d.rect.height + c)), e._DataInfo.isValid(u) && (u = a.convert(u), null !== y && (i.drawLine(h, y, h, u), d.rect.height = d.rect.height + u))
        }, a.prototype._getDataPoint = function(t, i, n, a) {
          var r = new e._DataPoint(t, i, n, this._haValues[i].high),
            s = a._getItem(i),
            o = a._getBinding(0),
            l = a._getBinding(1),
            h = a._getBinding(2),
            c = a._getBinding(3),
            u = a._getAxisY();
          return null != s && (r.item = e._BasePlotter.cloneStyle(s, []), r.item[o] = this._haValues[i].high, r.item[l] = this._haValues[i].low, r.item[h] = this._haValues[i].open, r.item[c] = this._haValues[i].close), r.y = this._haValues[i].high, r.yfmt = u._formatValue(this._haValues[i].high), r
        }, a.prototype._calculate = function(e) {
          var n = e._getBindingValues(0),
            a = e._getBindingValues(1),
            r = e._getBindingValues(2),
            s = e._getBindingValues(3);
          this._calculator = new i._HeikinAshiCalculator(n, a, r, s), this._haValues = this._calculator.calculate(), (null === this._haValues || t.isUndefined(this._haValues)) && this._init()
        }, a.prototype._init = function() {
          this._haValues = []
        }, a
      }(e._FinancePlotter);
      i._HeikinAshiPlotter = n
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n = function(i) {
        function n() {
          var t = i.call(this) || this;
          return t._symFactor = .7, t.clear(), t
        }
        return __extends(n, i), n.prototype.clear = function() {
          i.prototype.clear.call(this), this._rangeValues = null, this._rangeXLabels = null, this._calculator = null
        }, n.prototype.unload = function() {
          i.prototype.unload.call(this);
          for (var t, e, n = 0; n < this.chart.series.length; n++)(t = this.chart.series[n]) && (e = t._getAxisX()) && e.itemsSource && (e.itemsSource = null)
        }, n.prototype.adjustLimits = function(e, i) {
          var n, a, r, s = 0,
            o = 0,
            l = 0,
            h = 0,
            c = this.chart._xDataType === t.DataType.Date ? .5 : 0;
          t.assert(this.chart.series.length <= 1, "Current FinancialChartType only supports a single series");
          for (var u = 0; u < this.chart.series.length; u++) n = this.chart.series[u], this._calculate(n), this._rangeValues.length <= 0 || this._rangeXLabels.length <= 0 || ((a = this._rangeValues.map(function(t) {
            return t.open
          })).push.apply(a, this._rangeValues.map(function(t) {
            return t.close
          })), r = this._rangeXLabels.map(function(t) {
            return t.value
          }), l = Math.min.apply(null, a), h = Math.max.apply(null, a), s = Math.min.apply(null, r), o = Math.max.apply(null, r), n._getAxisX().itemsSource = this._rangeXLabels);
          return s -= c, new t.Rect(s, l, o - s + c, h - l)
        }, n.prototype.plotSeries = function(i, n, a, r, s, o, l) {
          var h = this;
          this._calculate(r);
          var c = this.chart.series.indexOf(r),
            u = this._rangeValues.length,
            _ = n.actualMin,
            p = n.actualMax,
            g = this._DEFAULT_WIDTH,
            f = this._symFactor,
            d = r._getSymbolFill(c),
            m = r._getAltSymbolFill(c) || "transparent",
            y = r._getSymbolStroke(c),
            v = r._getAltSymbolStroke(c) || y;
          i.strokeWidth = g;
          for (var x, w, b, k, P = 0, C = 0; C < u; C++)
            if (x = C, e._DataInfo.isValid(x) && _ <= x && x <= p) {
              if (w = this._rangeValues[C].open, b = this._rangeValues[C].close, i.fill = w > b ? d : m, i.stroke = w > b ? y : v, k = this._getDataPoint(c, C, r, Math.max(w, b)), i.startGroup(), this.chart.itemFormatter) {
                var V = new e.HitTestInfo(this.chart, new t.Point(n.convert(x), a.convert(b)), e.ChartElement.SeriesSymbol);
                V._setData(r, C), V._setDataPoint(k), this.chart.itemFormatter(i, V, function() {
                  h._drawSymbol(i, n, a, c, P, f, x, w, b, k)
                })
              } else this._drawSymbol(i, n, a, c, P, f, x, w, b, k);
              i.endGroup(), r._setPointIndex(C, P), P++
            }
        }, n.prototype._drawSymbol = function(i, n, a, r, s, o, l, h, c, u) {
          var _, p, g, f, d;
          if (g = n.convert(l - .5 * o), f = n.convert(l + .5 * o), g > f) {
            var m = g;
            g = f, f = m
          }
          e._DataInfo.isValid(h) && e._DataInfo.isValid(c) && (h = a.convert(h), c = a.convert(c), p = (_ = Math.min(h, c)) + Math.abs(h - c), i.drawRect(g, _, f - g, p - _), (d = new e._RectArea(new t.Rect(g, _, f - g, p - _))).tag = u, this.hitTester.add(d, r))
        }, n.prototype._getDataPoint = function(t, i, n, a) {
          var r = i,
            s = new e._DataPoint(t, i, r, a),
            o = n._getItem(this._rangeValues[i].pointIndex),
            l = n.bindingX || this.chart.bindingX,
            h = n._getBinding(0),
            c = n._getBinding(1),
            u = n._getBinding(2),
            _ = n._getBinding(3),
            p = n._getAxisY();
          return s.item = e._BasePlotter.cloneStyle(o, []), s.item[h] = this._rangeValues[i].high, s.item[c] = this._rangeValues[i].low, s.item[u] = this._rangeValues[i].open, s.item[_] = this._rangeValues[i].close, s.y = this._rangeValues[i].close, s.yfmt = p._formatValue(this._rangeValues[i].close), s.x = s.item[l], s.xfmt = this._rangeXLabels[i]._text, s
        }, n.prototype._init = function() {
          this._rangeValues = [], this._rangeXLabels = []
        }, n.prototype._calculate = function(t) {}, n.prototype._generateXLabels = function(i) {
          var n, a = this,
            r = i._getAxisX(),
            s = i.getDataType(1) || this.chart._xDataType;
          this._rangeValues.forEach(function(i, o) {
            var l = i.x;
            n = s === t.DataType.Date ? t.Globalize.format(e.FlexChart._fromOADate(l), r.format || "d") : s === t.DataType.Number ? r._formatValue(l) : null !== s && s !== t.DataType.String || !a.chart._xlabels ? l.toString() : a.chart._xlabels[l], a._rangeXLabels.push({
              value: o,
              text: n,
              _text: n
            })
          }, this)
        }, n
      }(e._BasePlotter);
      i._BaseRangePlotter = n;
      ! function(t) {
        t[t.Close = 0] = "Close", t[t.High = 1] = "High", t[t.Low = 2] = "Low", t[t.Open = 3] = "Open", t[t.HighLow = 4] = "HighLow", t[t.HL2 = 5] = "HL2", t[t.HLC3 = 6] = "HLC3", t[t.HLOC4 = 7] = "HLOC4"
      }(i.DataFields || (i.DataFields = {}));
      ! function(t) {
        t[t.Fixed = 0] = "Fixed", t[t.ATR = 1] = "ATR", t[t.Percentage = 2] = "Percentage"
      }(i.RangeMode || (i.RangeMode = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      "use strict";
      var i = function(i) {
        function n() {
          return i.call(this) || this
        }
        return __extends(n, i), n.prototype.clear = function() {
          i.prototype.clear.call(this), this._newLineBreaks = null
        }, n.prototype._calculate = function(i) {
          this._init();
          var n = i._getBindingValues(3),
            a = i.getValues(1) || this.chart._xvals;
          this._calculator = new e._LineBreakCalculator(null, null, null, n, a, this._newLineBreaks), this._rangeValues = this._calculator.calculate(), (null === this._rangeValues || t.isUndefined(this._rangeValues)) && (this._rangeValues = []), this._generateXLabels(i)
        }, n.prototype._init = function() {
          i.prototype._init.call(this), this._newLineBreaks = t.asInt(this.getNumOption("newLineBreaks", "lineBreak"), !0, !0) || 3, t.assert(this._newLineBreaks >= 1, "Value must be greater than 1")
        }, n
      }(e._BaseRangePlotter);
      e._LineBreakPlotter = i
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      "use strict";
      var i = function(i) {
        function n() {
          return i.call(this) || this
        }
        return __extends(n, i), n.prototype.clear = function() {
          i.prototype.clear.call(this), this._boxSize = null, this._rangeMode = null
        }, n.prototype._calculate = function(i) {
          this._init();
          var n = i._getBindingValues(0),
            a = i._getBindingValues(1),
            r = i._getBindingValues(2),
            s = i._getBindingValues(3),
            o = i.getValues(1) || this.chart._xvals;
          this._calculator = new e._RenkoCalculator(n, a, r, s, o, this._boxSize, this._rangeMode, this._fields, this._rounding), this._rangeValues = this._calculator.calculate(), (null === this._rangeValues || t.isUndefined(this._rangeValues)) && (this._rangeValues = []), this._generateXLabels(i)
        }, n.prototype._init = function() {
          i.prototype._init.call(this), this._boxSize = this.getNumOption("boxSize", "renko") || 14, this._rangeMode = this.getOption("rangeMode", "renko") || e.RangeMode.Fixed, this._rangeMode = t.asEnum(this._rangeMode, e.RangeMode, !0), t.assert(this._rangeMode !== e.RangeMode.Percentage, "RangeMode.Percentage is not supported"), this._fields = this.getOption("fields", "renko") || e.DataFields.Close, this._fields = t.asEnum(this._fields, e.DataFields, !0), t.assert(this._fields !== e.DataFields.HighLow, "DataFields.HighLow is not supported"), this._rounding = t.asBoolean(this.getOption("rounding", "renko"), !0)
        }, n.prototype._generateXLabels = function(t) {
          var e = this;
          i.prototype._generateXLabels.call(this, t), this._rangeXLabels.forEach(function(t, i) {
            i > 0 && e._rangeXLabels[i - 1]._text === t.text && (t.text = "")
          }, this)
        }, n
      }(e._BaseRangePlotter);
      e._RenkoPlotter = i
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n = function(n) {
        function a() {
          return n.call(this) || this
        }
        return __extends(a, n), a.prototype._calculate = function(e) {
          this._init();
          var n = e._getBindingValues(0),
            a = e._getBindingValues(1),
            r = e._getBindingValues(2),
            s = e._getBindingValues(3),
            o = e.getValues(1) || this.chart._xvals;
          this._calculator = new i._KagiCalculator(n, a, r, s, o, this._reversalAmount, this._rangeMode, this._fields), this._rangeValues = this._calculator.calculate(), (null === this._rangeValues || t.isUndefined(this._rangeValues)) && (this._rangeValues = []), this._generateXLabels(e)
        }, a.prototype.plotSeries = function(i, n, a, r, s, o, l) {
          this._calculate(r);
          var h = this.chart.series.indexOf(r),
            c = this._rangeValues.length,
            u = n.actualMin,
            _ = n.actualMax,
            p = this._DEFAULT_WIDTH,
            g = r._getSymbolStroke(h),
            f = r._getAltSymbolStroke(h) || g,
            d = [],
            m = [];
          i.stroke = g, i.strokeWidth = p;
          var y, v, x, w, b, k, P, C = 0;
          i.startGroup();
          for (var V = 0; V < c; V++) y = V, e._DataInfo.isValid(y) && u <= y && y <= _ && (v = this._rangeValues[V].open, x = this._rangeValues[V].close, 0 === V ? (w = Math.min(v, x), b = Math.max(v, x), i.strokeWidth = v > x ? p : 2 * p, i.stroke = v > x ? g : f, i.drawLine(n.convert(y), a.convert(v), n.convert(y), a.convert(x)), i.drawLine(n.convert(y - 1) - i.strokeWidth / 2, a.convert(v), n.convert(y) + i.strokeWidth / 2, a.convert(v))) : i.strokeWidth === p ? x > v ? (x > b ? (i.drawLine(n.convert(y), a.convert(v), n.convert(y), a.convert(b)), i.strokeWidth = 2 * p, i.stroke = f, i.drawLine(n.convert(y), a.convert(b), n.convert(y), a.convert(x)), w = v) : i.drawLine(n.convert(y), a.convert(v), n.convert(y), a.convert(x)), b = x) : i.drawLine(n.convert(y), a.convert(v), n.convert(y), a.convert(x)) : i.strokeWidth / 2 === p && (x < v ? (x < w ? (i.drawLine(n.convert(y), a.convert(v), n.convert(y), a.convert(w)), i.strokeWidth = p, i.stroke = g, i.drawLine(n.convert(y), a.convert(w), n.convert(y), a.convert(x)), b = v) : i.drawLine(n.convert(y), a.convert(v), n.convert(y), a.convert(x)), w = x) : i.drawLine(n.convert(y), a.convert(v), n.convert(y), a.convert(x))), V < c - 1 && i.drawLine(n.convert(y) - i.strokeWidth / 2, a.convert(x), n.convert(y + 1) + i.strokeWidth / 2, a.convert(x)), P = this._getDataPoint(h, V, r, x), (k = new e._CircleArea(new t.Point(n.convert(y), a.convert(x)), .5 * i.strokeWidth)).tag = P, this.hitTester.add(k, h), r._setPointIndex(V, C), C++, d.push(n.convert(y)), m.push(a.convert(v)), d.push(n.convert(y)), m.push(a.convert(x)));
          i.endGroup(), this.hitTester.add(new e._LinesArea(d, m), h)
        }, a.prototype._init = function() {
          n.prototype._init.call(this), this._reversalAmount = this.getNumOption("reversalAmount", "kagi") || 14, this._rangeMode = this.getOption("rangeMode", "kagi") || i.RangeMode.Fixed, this._rangeMode = t.asEnum(this._rangeMode, i.RangeMode, !0), this._fields = this.getOption("fields", "kagi") || i.DataFields.Close, this._fields = t.asEnum(this._fields, i.DataFields, !0)
        }, a.prototype.clear = function() {
          n.prototype.clear.call(this), this._reversalAmount = null, this._rangeMode = null
        }, a
      }(i._BaseRangePlotter);
      i._KagiPlotter = n
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n;
      ! function(t) {
        t[t.Traditional = 0] = "Traditional", t[t.Fixed = 1] = "Fixed", t[t.Dynamic = 2] = "Dynamic"
      }(n = i.PointAndFigureScaling || (i.PointAndFigureScaling = {}));
      var a = function(a) {
        function r() {
          return a.call(this) || this
        }
        return __extends(r, a), r.prototype.clear = function() {
          a.prototype.clear.call(this), this._boxSize = null, this._fields = null, this._reversal = null, this._scaling = null
        }, r.prototype.unload = function() {
          a.prototype.unload.call(this)
        }, r.prototype._init = function() {
          this._boxSize = this.getNumOption("boxSize", "pointAndFigure") || 1, this._reversal = this.getNumOption("reversal", "pointAndFigure") || 3, this._period = this.getNumOption("period", "pointAndFigure") || 20, this._fields = this.getOption("fields", "pointAndFigure") || i.DataFields.Close, this._fields = t.asEnum(this._fields, i.DataFields, !0), t.assert(this._fields == i.DataFields.Close || this._fields == i.DataFields.HighLow, "Only DataFields.Close and DataFields.HighLow are supported"), this._scaling = this.getOption("scaling", "pointAndFigure") || n.Traditional, this._scaling = t.asEnum(this._scaling, n, !0), this._xlbls = []
        }, r.prototype.adjustLimits = function(e, n) {
          this._init(), this.hitTester.clear();
          var a = new t.Rect(0, 0, 0, 0),
            r = this.chart.series.length;
          if (t.assert(r <= 1, "Current FinancialChartType only supports a single series"), r > 0) {
            var s = this.chart.series[0],
              o = this._reversal,
              l = s.collectionView ? s.collectionView : this.chart.collectionView,
              h = l ? l.items : null;
            if (h && h.length > 0) {
              var c = s._getBinding(0),
                u = s._getBinding(1),
                _ = s._getBinding(2),
                p = s._getBinding(3);
              this._fields == i.DataFields.Close && (p ? c = p : _ && (c = _), u = c);
              var g = s.bindingX ? s.bindingX : this.chart.bindingX,
                f = this._actualBoxSize = this.calcBoxSize(h, c, u);
              if (this._pfdata = this.calcPFHiLo2(h, c, u, g, f, o), this._pfdata && this._pfdata.length > 0) {
                var d = this._pfdata.reduce(function(t, e) {
                    return Math.max(t, e.max)
                  }, this._pfdata[0].max),
                  m = this._pfdata.reduce(function(t, e) {
                    return Math.min(t, e.min)
                  }, this._pfdata[0].min);
                a = new t.Rect(-.5, m - .5 * f, this._pfdata.length, d - m + f);
                for (var y = 1; y < this._pfdata.length; y++) {
                  var v = this._pfdata[y - 1],
                    x = this._pfdata[y];
                  t.isDate(x.date) && t.isDate(v.date) && x.date.getYear() != v.date.getYear() && this._xlbls.push({
                    value: y,
                    text: t.Globalize.formatNumber(x.date.getFullYear() % 100, "d2")
                  })
                }
              }
            }
          }
          return 0 == this._xlbls.length && this._xlbls.push({
            value: 0
          }), this.chart.axisX._beginUpdate(), this.chart.axisY._beginUpdate(), this.axisYMajorGrid = this.chart.axisY.majorGrid, this.axisXItemsSource = this.chart.axisX.itemsSource, this.chart.axisY.majorGrid = !1, this.chart.axisX.itemsSource = this._xlbls, a
        }, r.prototype.plotSeries = function(t, e, i, n, a, r, s) {
          if (this._pfdata && this._pfdata.length > 0) {
            var o = this._actualBoxSize;
            this.renderGrid(t, this._pfdata, o), this.renderData(this.chart, t, this._pfdata, o)
          }
          this.chart.axisY.majorGrid = this.axisYMajorGrid, this.chart.axisX.itemsSource = this.axisXItemsSource, this.chart.axisX._cancelUpdate(), this.chart.axisY._cancelUpdate()
        }, r.prototype.calcBoxSize = function(t, e, a) {
          var r = t.reduce(function(t, i) {
              return Math.max(t, i[e])
            }, t[0][e]),
            s = t.reduce(function(t, e) {
              return Math.min(t, e[a])
            }, t[0][a]),
            o = this._boxSize,
            l = r - s;
          switch (this._scaling) {
            case n.Traditional:
              l < .25 ? o = .0625 : l >= .25 && l < 1 ? o = .125 : l >= 1 && l < 5 ? o = .25 : l >= 5 && l < 20 ? o = .5 : l >= 20 && l < 100 ? o = 1 : l >= 100 && l < 200 ? o = 2 : l >= 200 && l < 500 ? o = 4 : l >= 500 && l < 1e3 ? o = 5 : l >= 1e3 && l < 25e3 ? o = 50 : l > -25e3 && (o = 500);
              break;
            case n.Dynamic:
              var h = this.chart.series[0],
                c = h._getBindingValues(0),
                u = h._getBindingValues(1),
                _ = (h._getBindingValues(2), h._getBindingValues(3)),
                p = i._avgTrueRng(c, u, _, this._period);
              o = p[p.length - 1]
          }
          return o
        }, r.prototype.calcPFHiLo2 = function(e, i, n, a, r, s) {
          for (var o = [], l = 0; l < e.length; l++) {
            var h = e[l][i],
              c = e[l][n];
            t.assert(h >= c, "'High' value must be larger than 'low' value.");
            var u = e[l][a];
            if (0 == o.length) o.push({
              min: this.roundDown(c, r),
              max: this.roundDown(h, r),
              rise: !1,
              date: u
            });
            else {
              var _ = o[o.length - 1];
              if (_.rise) {
                var p = _.max + r,
                  g = _.max - s * r;
                this.roundUp(h, r) >= p ? _.max = this.roundUp(h, r) : c <= g && o.push({
                  min: this.roundDown(c, r),
                  max: _.max - r,
                  rise: !1,
                  date: u
                })
              } else {
                var p = _.min - r,
                  g = _.min + s * r;
                this.roundDown(c, r) <= p ? _.min = this.roundDown(c, r) : h >= g && o.push({
                  min: _.min + r,
                  max: this.roundUp(h, r),
                  rise: !0,
                  date: u
                })
              }
            }
          }
          if (o.length > 0) {
            var f = o[0];
            f.min == f.max && o.splice(0, 1)
          }
          return o
        }, r.prototype.roundUp = function(t, e) {
          return Math.ceil(t / e - .999999) * e
        }, r.prototype.roundDown = function(t, e) {
          return Math.floor(t / e + .999999) * e
        }, r.prototype.renderGrid = function(i, n, a) {
          if (this._pfdata) {
            for (var r = this._pfdata.reduce(function(t, e) {
              return Math.max(t, e.max)
            }, this._pfdata[0].max), s = this._pfdata.reduce(function(t, e) {
              return Math.min(t, e.min)
            }, this._pfdata[0].min), o = this.chart, l = this._pfdata.length, h = s - .5 * a; h <= r + a; h += a) {
              u = new t.Point(-.5, h);
              u = o.dataToPoint(u);
              _ = new t.Point(l, h);
              _ = o.dataToPoint(_), i.stroke = e.FlexChart._FG, i.strokeWidth = 1, i.drawLine(u.x, u.y, _.x, _.y, e.FlexChart._CSS_GRIDLINE)
            }
            for (var c = -.5; c <= l; c += 1) {
              var u = new t.Point(c, this.chart.axisY.actualMin);
              u = o.dataToPoint(u);
              var _ = new t.Point(c, this.chart.axisY.actualMax);
              _ = o.dataToPoint(_), i.stroke = e.FlexChart._FG, i.strokeWidth = 1, i.drawLine(u.x, u.y, _.x, _.y, e.FlexChart._CSS_GRIDLINE)
            }
          }
        }, r.prototype.renderData = function(i, n, a, r) {
          for (var s = i.series[0], o = s._getSymbolStroke(0), l = s._getAltSymbolStroke(0) || o, h = 0; h < a.length; h++) {
            var c = a[h],
              u = (a[h].max - a[h].min) / r;
            if (0 != u) {
              var _ = new t.Point(h - .5, c.min);
              _ = i.dataToPoint(_);
              var p = new t.Point(h + .5, c.max);
              p = i.dataToPoint(p), n.fill = "transparent";
              for (var g = (p.y - _.y) / u, f = 0; f < u + 1; f++)
                if (n.strokeWidth = 1.5, c.rise ? (n.stroke = o, n.drawLine(_.x, _.y + (f - .5) * g, p.x, _.y + (f + .5) * g), n.drawLine(p.x, _.y + (f - .5) * g, _.x, _.y + (f + .5) * g)) : (n.stroke = l, n.drawEllipse(.5 * (_.x + p.x), _.y + f * g, .5 * Math.abs(_.x - p.x), .5 * Math.abs(g))), this.hitTester) {
                  var d = c.min + f * r,
                    m = new e._DataPoint(0, h, c.date, d);
                  m.y = d, m.yfmt = this.chart.axisY._formatValue(d), t.isDate(c.date) && (m.x = c.date, m.xfmt = t.Globalize.formatDate(c.date, "d"));
                  var y = new t.Rect(Math.min(_.x, p.x), _.y + f * g - .5 * g, Math.abs(p.x - _.x), g);
                  y.height < 0 && (y.top += g, y.height = -y.height);
                  var v = new e._RectArea(y);
                  v.tag = m, this.hitTester.add(v, 0)
                }
            }
          }
        }, r
      }(e._BasePlotter);
      i._PointAndFigurePlotter = a
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
