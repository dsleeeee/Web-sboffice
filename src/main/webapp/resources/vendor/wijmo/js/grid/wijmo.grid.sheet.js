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
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function() {
        function n(t) {
          this._expressionCache = {}, this._idChars = "$:!", this._functionTable = {}, this._cacheSize = 0, this._tableRefStart = !1, this.unknownFunction = new e.Event, this._owner = t, this._buildSymbolTable(), this._registerAggregateFunction(), this._registerMathFunction(), this._registerLogicalFunction(), this._registerTextFunction(), this._registerDateFunction(), this._registLookUpReferenceFunction(), this._registFinacialFunction(), this._registAddressRelatedFunction()
        }
        return n.prototype.onUnknownFunction = function(e, t) {
          var n, i;
          if (t && t.length > 0) {
            n = [];
            for (var l = 0; l < t.length; l++) n[l] = t[l].evaluate(this._rowIndex, this._columnIndex)
          }
          if (i = new o.UnknownFunctionEventArgs(e, n), this.unknownFunction.raise(this, i), null != i.value) return new o._Expression(i.value);
          throw 'The function "' + e + '" has not supported in FlexSheet yet.'
        }, n.prototype.evaluate = function(t, n, i, l, s) {
          var r;
          if (!this._owner.enableFormulas) return t;
          try {
            if (t && t.length > 1 && "=" === t[0]) {
              for (this._containsCellRef = !1, this._rowIndex = l, this._columnIndex = s, this._sheet = i || this._owner.selectedSheet, r = this._checkCache(t).evaluate(l, s, i); r instanceof o._Expression;) r = r.evaluate(l, s, i);
              return n && e.isPrimitive(r) ? e.Globalize.format(r, n) : r
            }
            return t || ""
          } catch (e) {
            return "Error: " + e
          }
        }, n.prototype.addCustomFunction = function(e, t, n, i) {
          var s = this;
          e = e.toLowerCase(), this._functionTable[e] = new l(function(e) {
            var n, i = [];
            if (e && e.length > 0)
              for (var l = 0; l < e.length; l++)(n = e[l]) instanceof o._CellRangeExpression ? i[l] = n.cells : i[l] = n.evaluate(s._rowIndex, s._columnIndex);
            return t.apply(s, i)
          }, i, n)
        }, n.prototype.addFunction = function(e, t, n, i) {
          var s = this;
          e = e.toLowerCase(), this._functionTable[e] = new l(function(e) {
            var n, i = [];
            if (e && e.length > 0)
              for (var l = 0; l < e.length; l++)(n = e[l]) instanceof o._CellRangeExpression ? i[l] = n.getValuseWithTwoDimensions() : i[l] = [
                [n.evaluate(s._rowIndex, s._columnIndex)]
              ];
            return t.apply(s, i)
          }, i, n)
        }, n.prototype._clearExpressionCache = function() {
          this._expressionCache = null, this._expressionCache = {}, this._cacheSize = 0
        }, n.prototype._parse = function(e) {
          return this._expression = e, this._expressLength = e ? e.length : 0, this._pointer = 0, this._expressLength > 0 && "=" === this._expression[0] && this._pointer++, this._parseExpression()
        }, n.prototype._buildSymbolTable = function() {
          this._tokenTable || (this._tokenTable = {}, this._addToken("+", r.ADD, s.ADDSUB), this._addToken("-", r.SUB, s.ADDSUB), this._addToken("(", r.OPEN, s.GROUP), this._addToken(")", r.CLOSE, s.GROUP), this._addToken("*", r.MUL, s.MULDIV), this._addToken(",", r.COMMA, s.GROUP), this._addToken(".", r.PERIOD, s.GROUP), this._addToken("/", r.DIV, s.MULDIV), this._addToken("\\", r.DIVINT, s.MULDIV), this._addToken("=", r.EQ, s.COMPARE), this._addToken(">", r.GT, s.COMPARE), this._addToken("<", r.LT, s.COMPARE), this._addToken("^", r.POWER, s.POWER), this._addToken("<>", r.NE, s.COMPARE), this._addToken(">=", r.GE, s.COMPARE), this._addToken("<=", r.LE, s.COMPARE), this._addToken("&", r.CONCAT, s.CONCAT), this._addToken("[", r.OPEN, s.SQUAREBRACKETS), this._addToken("]", r.CLOSE, s.SQUAREBRACKETS))
        }, n.prototype._registerAggregateFunction = function() {
          var t = this;
          t._functionTable.sum = new l(function(o, n) {
            return t._getAggregateResult(e.Aggregate.Sum, o, n)
          }), t._functionTable.average = new l(function(o, n) {
            return t._getAggregateResult(e.Aggregate.Avg, o, n)
          }), t._functionTable.max = new l(function(o, n) {
            return t._getAggregateResult(e.Aggregate.Max, o, n)
          }), t._functionTable.min = new l(function(o, n) {
            return t._getAggregateResult(e.Aggregate.Min, o, n)
          }), t._functionTable.var = new l(function(o, n) {
            return t._getAggregateResult(e.Aggregate.Var, o, n)
          }), t._functionTable.varp = new l(function(o, n) {
            return t._getAggregateResult(e.Aggregate.VarPop, o, n)
          }), t._functionTable.stdev = new l(function(o, n) {
            return t._getAggregateResult(e.Aggregate.Std, o, n)
          }), t._functionTable.stdevp = new l(function(o, n) {
            return t._getAggregateResult(e.Aggregate.StdPop, o, n)
          }), t._functionTable.count = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.Count, e, o)
          }), t._functionTable.counta = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.CountA, e, o)
          }), t._functionTable.countblank = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.ConutBlank, e, o)
          }), t._functionTable.countif = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.CountIf, e, o)
          }, 2, 2), t._functionTable.countifs = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.CountIfs, e, o)
          }, 254, 2), t._functionTable.sumif = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.SumIf, e, o)
          }, 3, 2), t._functionTable.sumifs = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.SumIfs, e, o)
          }, 255, 2), t._functionTable.rank = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.Rank, e, o)
          }, 3, 2), t._functionTable.product = new l(function(e, o) {
            return t._getFlexSheetAggregateResult(a.Product, e, o)
          }, 255, 1), t._functionTable.subtotal = new l(function(e, o) {
            return t._handleSubtotal(e, o)
          }, 255, 2), t._functionTable.dcount = new l(function(e, o) {
            return t._handleDCount(e, o)
          }, 3, 3), t._functionTable.sumproduct = new l(function(e, o) {
            return t._getSumProduct(e, o)
          }, 255, 1)
        }, n.prototype._registerMathFunction = function() {
          var e = this,
            t = ["abs", "acos", "asin", "atan", "cos", "exp", "ln", "sin", "sqrt", "tan"],
            n = ["ceiling", "floor"],
            i = ["round", "rounddown", "roundup"];
          e._functionTable.pi = new l(function() {
            return Math.PI
          }, 0, 0), e._functionTable.rand = new l(function() {
            return Math.random()
          }, 0, 0), e._functionTable.power = new l(function(t, n) {
            return Math.pow(o._Expression.toNumber(t[0], e._rowIndex, e._columnIndex, n), o._Expression.toNumber(t[1], e._rowIndex, e._columnIndex, n))
          }, 2, 2), e._functionTable.atan2 = new l(function(t, n) {
            var i = o._Expression.toNumber(t[0], e._rowIndex, e._columnIndex, n),
              l = o._Expression.toNumber(t[1], e._rowIndex, e._columnIndex, n);
            if (0 === i && 0 === l) throw "The x number and y number can't both be zero for the atan2 function";
            return Math.atan2(l, i)
          }, 2, 2), e._functionTable.mod = new l(function(t, n) {
            var i = o._Expression.toNumber(t[0], e._rowIndex, e._columnIndex, n),
              l = o._Expression.toNumber(t[1], e._rowIndex, e._columnIndex, n),
              s = Math.abs(i) % Math.abs(l);
            return l < 0 && (s = -s), s
          }, 2, 2), e._functionTable.trunc = new l(function(t, n) {
            var i, l, s, r = o._Expression.toNumber(t[0], e._rowIndex, e._columnIndex, n),
              a = 2 === t.length ? o._Expression.toNumber(t[1], e._rowIndex, e._columnIndex, n) : 0;
            if (0 === a ? (s = "n0", l = r >= 0 ? Math.floor(r) : Math.ceil(r)) : (s = "n" + ((a = a > 0 ? Math.floor(a) : Math.ceil(a)) > 0 ? a : 0), i = Math.pow(10, a), l = r >= 0 ? Math.floor(r * i) / i : Math.ceil(r * i) / i), null != l) return {
              value: l,
              format: s
            }
          }, 2, 1), n.forEach(function(t) {
            e._functionTable[t] = new l(function(n, i) {
              var l, s, r, a = o._Expression.toNumber(n[0], e._rowIndex, e._columnIndex, i),
                h = 2 === n.length ? o._Expression.toNumber(n[1], e._rowIndex, e._columnIndex, i) : 1;
              if (isNaN(a)) throw "Invalid Number!";
              if (isNaN(h)) throw "Invalid Significance!";
              if (a > 0 && h < 0) throw "The significance has to be positive, if the number is positive.";
              if (0 === a || 0 === h) return 0;
              if (l = h - Math.floor(h), s = 1, 0 !== l)
                for (; l < 1;) s *= 10, l *= 10;
              return r = "ceiling" === t ? Math.ceil(a / h) : Math.floor(a / h), h * s * r / s
            }, 2, 1)
          }), i.forEach(function(t) {
            e._functionTable[t] = new l(function(n, i) {
              var l, s, r, a, h = o._Expression.toNumber(n[0], e._rowIndex, e._columnIndex, i),
                c = o._Expression.toNumber(n[1], e._rowIndex, e._columnIndex, i);
              if (0 === c) {
                switch (t) {
                  case "rounddown":
                    l = h >= 0 ? Math.floor(h) : Math.ceil(h);
                    break;
                  case "roundup":
                    l = h >= 0 ? Math.ceil(h) : Math.floor(h);
                    break;
                  case "round":
                    a = h >= 0 ? 0 : .1, l = Math.round(h - a);
                    break;
                  default:
                    l = Math.floor(h)
                }
                s = "n0"
              } else {
                switch (c = c > 0 ? Math.floor(c) : Math.ceil(c), r = Math.pow(10, c), t) {
                  case "rounddown":
                    l = h >= 0 ? Math.floor(h * r) / r : Math.ceil(h * r) / r;
                    break;
                  case "roundup":
                    l = h >= 0 ? Math.ceil(h * r) / r : Math.floor(h * r) / r;
                    break;
                  case "round":
                    a = h >= 0 ? 0 : 1 * r / 10, l = Math.round(h * r - a) / r
                }
                s = "n" + (c > 0 ? c : 0)
              }
              if (null != l) return {
                value: l,
                format: s
              }
            }, 2, 2)
          }), t.forEach(function(t) {
            e._functionTable[t] = new l(function(n, i) {
              return "ln" === t ? Math.log(o._Expression.toNumber(n[0], e._rowIndex, e._columnIndex, i)) : Math[t](o._Expression.toNumber(n[0], e._rowIndex, e._columnIndex, i))
            }, 1, 1)
          })
        }, n.prototype._registerLogicalFunction = function() {
          var e = this;
          e._functionTable.and = new l(function(t, n) {
            var i, l = !0;
            for (i = 0; i < t.length && (l = l && o._Expression.toBoolean(t[i], e._rowIndex, e._columnIndex, n)); i++);
            return l
          }, Number.MAX_VALUE, 1), e._functionTable.or = new l(function(t, n) {
            var i, l = !1;
            for (i = 0; i < t.length && !(l = l || o._Expression.toBoolean(t[i], e._rowIndex, e._columnIndex, n)); i++);
            return l
          }, Number.MAX_VALUE, 1), e._functionTable.not = new l(function(t, n) {
            return !o._Expression.toBoolean(t[0], e._rowIndex, e._columnIndex, n)
          }, 1, 1), e._functionTable.if = new l(function(t, n) {
            return 3 === t.length ? o._Expression.toBoolean(t[0], e._rowIndex, e._columnIndex, n) ? t[1].evaluate(e._rowIndex, e._columnIndex, n) : t[2].evaluate(e._rowIndex, e._columnIndex, n) : !!o._Expression.toBoolean(t[0], e._rowIndex, e._columnIndex, n) && t[1].evaluate(e._rowIndex, e._columnIndex, n)
          }, 3, 2), e._functionTable.true = new l(function() {
            return !0
          }, 0, 0), e._functionTable.false = new l(function() {
            return !1
          }, 0, 0)
        }, n.prototype._registerTextFunction = function() {
          var t = this;
          t._functionTable.char = new l(function(e, n) {
            var i, l = "";
            for (i = 0; i < e.length; i++) l += String.fromCharCode(o._Expression.toNumber(e[i], t._rowIndex, t._columnIndex, n));
            return l
          }, Number.MAX_VALUE, 1), t._functionTable.code = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n);
            return i && i.length > 0 ? i.charCodeAt(0) : -1
          }, 1, 1), t._functionTable.concatenate = new l(function(e, n) {
            var i, l = "";
            for (i = 0; i < e.length; i++) l = l.concat(o._Expression.toString(e[i], t._rowIndex, t._columnIndex, n));
            return l
          }, Number.MAX_VALUE, 1), t._functionTable.left = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n),
              l = null == e[1] ? 1 : Math.floor(o._Expression.toNumber(e[1], t._rowIndex, t._columnIndex, n));
            return i && i.length > 0 ? i.slice(0, l) : null
          }, 2, 1), t._functionTable.right = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n),
              l = null == e[1] ? 1 : Math.floor(o._Expression.toNumber(e[1], t._rowIndex, t._columnIndex, n));
            return i && i.length > 0 ? i.slice(-l) : null
          }, 2, 1), t._functionTable.find = new l(function(n, i) {
            var l, s = o._Expression.toString(n[0], t._rowIndex, t._columnIndex, i),
              r = o._Expression.toString(n[1], t._rowIndex, t._columnIndex, i),
              a = null != n[2] ? e.asInt(o._Expression.toNumber(n[2], t._rowIndex, t._columnIndex, i)) : 0;
            return null != r && null != s && (l = !isNaN(a) && a > 0 && a < r.length ? r.indexOf(s, a) : r.indexOf(s)) > -1 ? l + 1 : -1
          }, 3, 2), t._functionTable.search = new l(function(n, i) {
            var l, s, r, a = o._Expression.toString(n[0], t._rowIndex, t._columnIndex, i),
              h = o._Expression.toString(n[1], t._rowIndex, t._columnIndex, i),
              c = null != n[2] ? e.asInt(o._Expression.toNumber(n[2], t._rowIndex, t._columnIndex, i)) : 0;
            return null != h && null != a && (s = new RegExp(a, "i"), !isNaN(c) && c > 0 && c < h.length ? (h = h.substring(c), l = c + 1) : l = 1, (r = h.search(s)) > -1) ? r + l : -1
          }, 3, 2), t._functionTable.len = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n);
            return i ? i.length : -1
          }, 1, 1), t._functionTable.mid = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n),
              l = Math.floor(o._Expression.toNumber(e[1], t._rowIndex, t._columnIndex, n)),
              s = Math.floor(o._Expression.toNumber(e[2], t._rowIndex, t._columnIndex, n));
            return i && i.length > 0 && l > 0 ? i.substr(l - 1, s) : null
          }, 3, 3), t._functionTable.lower = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n);
            return i && i.length > 0 ? i.toLowerCase() : null
          }, 1, 1), t._functionTable.upper = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n);
            return i && i.length > 0 ? i.toUpperCase() : null
          }, 1, 1), t._functionTable.proper = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n);
            return i && i.length > 0 ? i.replace(/\b[\w']+\b/g, function(e) {
              return e && e.length > 0 ? e[0].toUpperCase() + e.substring(1).toLowerCase() : e
            }) : null
          }, 1, 1), t._functionTable.trim = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n);
            return i && i.length > 0 ? i.trim() : null
          }, 1, 1), t._functionTable.replace = new l(function(e, n) {
            var i = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n),
              l = Math.floor(o._Expression.toNumber(e[1], t._rowIndex, t._columnIndex, n)),
              s = Math.floor(o._Expression.toNumber(e[2], t._rowIndex, t._columnIndex, n)),
              r = o._Expression.toString(e[3], t._rowIndex, t._columnIndex, n);
            return i && i.length > 0 && l > 0 ? i.substring(0, l - 1) + r + i.slice(l - 1 + s) : null
          }, 4, 4), t._functionTable.substitute = new l(function(e, n) {
            var i, l = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n),
              s = o._Expression.toString(e[1], t._rowIndex, t._columnIndex, n),
              r = o._Expression.toString(e[2], t._rowIndex, t._columnIndex, n),
              a = 4 === e.length ? o._Expression.toNumber(e[3], t._rowIndex, t._columnIndex, n) : null,
              h = 0;
            if (null != a && a < 1 || isNaN(a)) throw "Invalid instance number.";
            return l && l.length > 0 && s && s.length > 0 ? (i = new RegExp(s, "g"), l.replace(i, function(e) {
              return h++, null != a ? (a = Math.floor(a), h === a ? r : e) : r
            })) : null
          }, 4, 3), t._functionTable.rept = new l(function(e, n) {
            var i, l = o._Expression.toString(e[0], t._rowIndex, t._columnIndex, n),
              s = Math.floor(o._Expression.toNumber(e[1], t._rowIndex, t._columnIndex, n)),
              r = "";
            if (l && l.length > 0 && s > 0)
              for (i = 0; i < s; i++) r = r.concat(l);
            return r
          }, 2, 2), t._functionTable.text = new l(function(n, i) {
            var l, s = n[0].evaluate(t._rowIndex, t._columnIndex, i),
              r = o._Expression.toString(n[1], t._rowIndex, t._columnIndex, i);
            if (!e.isPrimitive(s) && s && (s = s.value), e.isDate(s)) r = r.replace(/\[\$\-.+\]/g, "").replace(/(\\)(.)/g, "$2").replace(/H+/g, function(e) {
              return e.toLowerCase()
            }).replace(/m+/g, function(e) {
              return e.toUpperCase()
            }).replace(/S+/g, function(e) {
              return e.toLowerCase()
            }).replace(/AM\/PM/gi, "tt").replace(/A\/P/gi, "t").replace(/\.000/g, ".fff").replace(/\.00/g, ".ff").replace(/\.0/g, ".f").replace(/\\[\-\s,]/g, function(e) {
              return e.substring(1)
            }).replace(/Y+/g, function(e) {
              return e.toLowerCase()
            }).replace(/D+/g, function(e) {
              return e.toLowerCase()
            }).replace(/M+:?|:?M+/gi, function(e) {
              return e.indexOf(":") > -1 ? e.toLowerCase() : e
            }).replace(/g+e/gi, function(e) {
              return e.substring(0, e.length - 1) + "yy"
            });
            else {
              if (!e.isNumber(s)) return s;
              if ((l = r.match(/^(\d+)(\.\d+)?\E\+(\d+)(\.\d+)?$/)) && 5 === l.length) return t._parseToScientificValue(s, l[1], l[2], l[3], l[4]);
              /M{1,4}|d{1,4}|y{1,4}/.test(r) ? s = new Date(1900, 0, s - 1) : r && (r = e.xlsx.Workbook.fromXlsxFormat(r)[0])
            }
            if (e.isDate(s)) switch (r) {
              case "d":
                return s.getDate();
              case "M":
                return s.getMonth() + 1;
              case "y":
                r = "yy";
                break;
              case "yyy":
                r = "yyyy"
            }
            return e.Globalize.format(s, r)
          }, 2, 2), t._functionTable.value = new l(function(n, i) {
            var l, s = o._Expression.toString(n[0], t._rowIndex, t._columnIndex, i);
            if ((s = s.replace(/(\,\d{3})/g, function(e) {
              return e.substring(1)
            })).length > 0) {
              if (s[0] === e.culture.Globalize.numberFormat.currency.symbol) return +(s = s.substring(1));
              if ("%" === s[s.length - 1]) return +(s = s.substring(0, s.length - 1)) / 100
            }
            return l = +s, isNaN(l) ? o._Expression.toNumber(n[0], t._rowIndex, t._columnIndex, i) : l
          }, 1, 1)
        }, n.prototype._registerDateFunction = function() {
          var t = this;
          t._functionTable.now = new l(function() {
            return {
              value: new Date,
              format: "M/d/yyyy h:mm"
            }
          }, 0, 0), t._functionTable.today = new l(function() {
            var e = new Date;
            return {
              value: new Date(e.getFullYear(), e.getMonth(), e.getDate()),
              format: "M/d/yyyy"
            }
          }, 0, 0), t._functionTable.year = new l(function(n, i) {
            var l = o._Expression.toDate(n[0], t._rowIndex, t._columnIndex, i);
            return !e.isPrimitive(l) && l ? l.value : e.isDate(l) ? l.getFullYear() : 1900
          }, 1, 1), t._functionTable.month = new l(function(n, i) {
            var l = o._Expression.toDate(n[0], t._rowIndex, t._columnIndex, i);
            return !e.isPrimitive(l) && l ? l.value : e.isDate(l) ? l.getMonth() + 1 : 1
          }, 1, 1), t._functionTable.day = new l(function(n, i) {
            var l = o._Expression.toDate(n[0], t._rowIndex, t._columnIndex, i);
            return !e.isPrimitive(l) && l ? l.value : e.isDate(l) ? l.getDate() : 0
          }, 1, 1), t._functionTable.hour = new l(function(n, i) {
            var l = n[0].evaluate(t._rowIndex, t._columnIndex, i);
            if (e.isNumber(l) && !isNaN(l)) return Math.floor(24 * (l - Math.floor(l)));
            if (e.isDate(l)) return l.getHours();
            if (l = o._Expression.toDate(n[0], t._rowIndex, t._columnIndex, i), !e.isPrimitive(l) && l && (l = l.value), e.isDate(l)) return l.getHours();
            throw "Invalid parameter."
          }, 1, 1), t._functionTable.time = new l(function(o, n) {
            var i = o[0].evaluate(t._rowIndex, t._columnIndex, n),
              l = o[1].evaluate(t._rowIndex, t._columnIndex, n),
              s = o[2].evaluate(t._rowIndex, t._columnIndex, n);
            if (e.isNumber(i) && e.isNumber(l) && e.isNumber(s)) return i %= 24, l %= 60, s %= 60, {
              value: new Date(0, 0, 0, i, l, s),
              format: "h:mm tt"
            };
            throw "Invalid parameters."
          }, 3, 3), t._functionTable.date = new l(function(o, n) {
            var i = o[0].evaluate(t._rowIndex, t._columnIndex, n),
              l = o[1].evaluate(t._rowIndex, t._columnIndex, n),
              s = o[2].evaluate(t._rowIndex, t._columnIndex, n);
            if (e.isNumber(i) && e.isNumber(l) && e.isNumber(s)) return {
              value: new Date(i, l - 1, s),
              format: "M/d/yyyy"
            };
            throw "Invalid parameters."
          }, 3, 3), t._functionTable.datedif = new l(function(n, i) {
            var l, s, r, a, h, c = o._Expression.toDate(n[0], t._rowIndex, t._columnIndex, i),
              d = o._Expression.toDate(n[1], t._rowIndex, t._columnIndex, i),
              u = n[2].evaluate(t._rowIndex, t._columnIndex, i);
            if (!e.isPrimitive(c) && c && (c = c.value), !e.isPrimitive(d) && d && (d = d.value), e.isDate(c) && e.isDate(d) && e.isString(u)) {
              if (l = c.getTime(), s = d.getTime(), l > s) throw "Start date is later than end date.";
              switch (r = d.getDate() - c.getDate(), a = d.getMonth() - c.getMonth(), h = d.getFullYear() - c.getFullYear(), u.toUpperCase()) {
                case "Y":
                  return a > 0 ? h : a < 0 ? h - 1 : r >= 0 ? h : h - 1;
                case "M":
                  return r >= 0 ? 12 * h + a : 12 * h + a - 1;
                case "D":
                  return (s - l) / 864e5;
                case "YM":
                  return (a = r >= 0 ? 12 * h + a : 12 * h + a - 1) % 12;
                case "YD":
                  return a > 0 ? (new Date(c.getFullYear(), d.getMonth(), d.getDate()).getTime() - c.getTime()) / 864e5 : a < 0 ? (new Date(c.getFullYear() + 1, d.getMonth(), d.getDate()).getTime() - c.getTime()) / 864e5 : r >= 0 ? r : (new Date(c.getFullYear() + 1, d.getMonth(), d.getDate()).getTime() - c.getTime()) / 864e5;
                case "MD":
                  return r >= 0 ? r : r = new Date(d.getFullYear(), d.getMonth(), 0).getDate() - new Date(d.getFullYear(), d.getMonth() - 1, 1).getDate() + 1 + r;
                default:
                  throw "Invalid unit."
              }
            }
            throw "Invalid parameters."
          }, 3, 3)
        }, n.prototype._registLookUpReferenceFunction = function() {
          var e = this;
          e._functionTable.column = new l(function(t, n, i, l) {
            var s;
            if (null == t) return l + 1;
            if (s = t[0], (s = e._ensureNonFunctionExpression(s, n)) instanceof o._CellRangeExpression) return s.cells.col + 1;
            throw "Invalid Cell Reference."
          }, 1, 0), e._functionTable.columns = new l(function(t, n) {
            var i = t[0];
            if ((i = e._ensureNonFunctionExpression(i, n)) instanceof o._CellRangeExpression) return i.cells.columnSpan;
            throw "Invalid Cell Reference."
          }, 1, 1), e._functionTable.row = new l(function(t, n, i, l) {
            var s;
            if (null == t) return i + 1;
            if (s = t[0], (s = e._ensureNonFunctionExpression(s, n)) instanceof o._CellRangeExpression) return s.cells.row + 1;
            throw "Invalid Cell Reference."
          }, 1, 0), e._functionTable.rows = new l(function(t, n) {
            var i = t[0];
            if ((i = e._ensureNonFunctionExpression(i, n)) instanceof o._CellRangeExpression) return i.cells.rowSpan;
            throw "Invalid Cell Reference."
          }, 1, 1), e._functionTable.choose = new l(function(t, n) {
            var i = o._Expression.toNumber(t[0], e._rowIndex, e._columnIndex, n);
            if (isNaN(i)) throw "Invalid index number.";
            if (i < 1 || i >= t.length) throw "The index number is out of the list range.";
            return i = Math.floor(i), t[i].evaluate(e._rowIndex, e._columnIndex, n)
          }, 255, 2), e._functionTable.index = new l(function(n, i) {
            var l, s = n[0],
              r = o._Expression.toNumber(n[1], e._rowIndex, e._columnIndex, i),
              a = null != n[2] ? o._Expression.toNumber(n[2], e._rowIndex, e._columnIndex, i) : 0;
            if (isNaN(r) || r < 0) throw "Invalid Row Number.";
            if (isNaN(a) || a < 0) throw "Invalid Column Number.";
            if ((s = e._ensureNonFunctionExpression(s, i)) instanceof o._CellRangeExpression) {
              if (l = s.cells, r > l.rowSpan || a > l.columnSpan) throw "Index is out of the cell range.";
              if (r > 0 && a > 0) return e._owner.getCellValue(l.topRow + r - 1, l.leftCol + a - 1, !0, i);
              if (0 === r && 0 === a) return s;
              if (0 === r) return new o._CellRangeExpression(new t.CellRange(l.topRow, l.leftCol + a - 1, l.bottomRow, l.leftCol + a - 1), s.sheetRef, e._owner);
              if (0 === a) return new o._CellRangeExpression(new t.CellRange(l.topRow + r - 1, l.leftCol, l.topRow + r - 1, l.rightCol), s.sheetRef, e._owner)
            }
            throw "Invalid Cell Reference."
          }, 4, 2), e._functionTable.hlookup = new l(function(t, o) {
            return e._handleHLookup(t, o)
          }, 4, 3)
        }, n.prototype._registFinacialFunction = function() {
          var e = this;
          e._functionTable.rate = new l(function(t, o) {
            return {
              value: e._calculateRate(t, o),
              format: "p2"
            }
          }, 6, 3)
        }, n.prototype._registAddressRelatedFunction = function() {
          var t = this;
          t._functionTable.indirect = new l(function(n, i) {
            var l, s, r, a, h, c, d, u, _ = t._ensureNonFunctionExpression(n[0], i);
            if (_ instanceof o._CellRangeExpression ? (l = _.cells, s = _.evaluate(l.row, l.col, i)) : s = _.evaluate(t._rowIndex, t._columnIndex, i), e.isString(s)) {
              if (2 === (r = s.split("!")).length ? (a = r[0].toLowerCase(), h = r[1].toLowerCase()) : h = r[0].toLowerCase(), c = t._getDefinedName(h, a || t._sheet.name.toLowerCase()), i = i || t._owner.selectedSheet, c) {
                if (c.sheetName && c.sheetName.toLowerCase() !== i.name.toLowerCase()) throw "Invalid Cell Reference.";
                s = c.value
              }
              if (d = t._getCellRange(s)) {
                if (d.sheetRef) {
                  for (var g = 0; g < t._owner.sheets.length; g++)
                    if (t._owner.sheets[g].name.toLowerCase() === d.sheetRef.toLowerCase()) {
                      u = t._owner.sheets[g];
                      break
                    }
                } else u = t._owner.selectedSheet;
                if (u) return t._owner.getCellValue(d.cellRef.cellRange.row, d.cellRef.cellRange.col, !0, i)
              }
            }
            throw "Invalid Cell Reference."
          }, 2, 1), t._functionTable.address = new l(function(n, i) {
            var l, s, r = o._Expression.toNumber(n[0], t._rowIndex, t._columnIndex, i),
              a = o._Expression.toNumber(n[1], t._rowIndex, t._columnIndex, i),
              h = !1,
              c = !1;
            if (isNaN(r) || !e.isInt(r) || r < 1 || r > 1048576) throw "Invalid row number.";
            if (isNaN(a) || !e.isInt(a) || a < 1 || a > 16384) throw "Invalid column number.";
            if (null != n[2] && (l = o._Expression.toNumber(n[2], t._rowIndex, t._columnIndex, i), isNaN(l) || !e.isInt(l) || l < 1 || l > 4)) throw "Invalid absolute type.";
            return null != n[4] && (s = o._Expression.toString(n[4], t._rowIndex, t._columnIndex, i)), null == l || 1 === l ? (h = !0, c = !0) : 2 === l ? h = !0 : 3 === l && (c = !0), (null != s ? s + "!" : "") + (c ? "$" : "") + t._numAlpha(a) + (h ? "$" : "") + r.toString()
          }, 5, 2)
        }, n.prototype._addToken = function(e, t, o) {
          var n = new i(e, t, o);
          this._tokenTable[e] = n
        }, n.prototype._parseExpression = function() {
          return this._getToken(), this._parseCompareOrConcat()
        }, n.prototype._parseCompareOrConcat = function() {
          for (var e, t, n = this._parseAddSub(); this._token.tokenType === s.COMPARE || this._token.tokenType === s.CONCAT;) e = this._token, this._getToken(), t = this._parseAddSub(), n = new o._BinaryExpression(e, n, t);
          return n
        }, n.prototype._parseAddSub = function() {
          for (var e, t, n = this._parseMulDiv(); this._token.tokenType === s.ADDSUB;) e = this._token, this._getToken(), t = this._parseMulDiv(), n = new o._BinaryExpression(e, n, t);
          return n
        }, n.prototype._parseMulDiv = function() {
          for (var e, t, n = this._parsePower(); this._token.tokenType === s.MULDIV;) e = this._token, this._getToken(), t = this._parsePower(), n = new o._BinaryExpression(e, n, t);
          return n
        }, n.prototype._parsePower = function() {
          for (var e, t, n = this._parseUnary(); this._token.tokenType === s.POWER;) e = this._token, this._getToken(), t = this._parseUnary(), n = new o._BinaryExpression(e, n, t);
          return n
        }, n.prototype._parseUnary = function() {
          var e, t;
          return this._token.tokenID === r.ADD || this._token.tokenID === r.SUB ? (e = this._token, this._getToken(), t = this._parseAtom(), new o._UnaryExpression(e, t)) : this._parseAtom()
        }, n.prototype._parseAtom = function() {
          var e, t, n, i, l, a, h, c, d, u, _, g, f, p = null;
          switch (this._token.tokenType) {
            case s.LITERAL:
              p = new o._Expression(this._token);
              break;
            case s.IDENTIFIER:
              if (e = this._token.value.toString().toLowerCase(), t = this._functionTable[e]) {
                if (n = this._getParameters(), this._token.tokenType === s.GROUP && this._token.tokenID === r.CLOSE) {
                  if (i = n ? n.length : 0, -1 !== t.paramMin && i < t.paramMin) throw "Too few parameters.";
                  if (-1 !== t.paramMax && i > t.paramMax) throw "Too many parameters.";
                  "rand" === e && (this._containsCellRef = !0), p = new o._FunctionExpression(e, t, n);
                  break
                }
                if ("true" === e || "false" === e) {
                  p = new o._FunctionExpression(e, t, n, !1);
                  break
                }
              }
              if (2 === (u = e.split("!")).length ? (g = u[0], _ = u[1]) : _ = u[0], a = this._getDefinedName(_, g || this._sheet.name.toLowerCase())) {
                if (a.sheetName && a.sheetName !== this._sheet.name && a.sheetName.toLowerCase() !== g) throw "The defined name item works in " + a.sheetName + ".  It does not work in current sheet.";
                d = this._pointer, c = this._expressLength, h = this._expression, this._pointer = 0, p = this._checkCache(a.value), this._pointer = d, this._expressLength = c, this._expression = h;
                break
              }
              if (g = "", null != (f = this._owner._getTable(e))) {
                if ("" === (g = f.sheet.name)) throw "The Table(" + e + ") is not located in any sheet.";
                if (this._getToken(), this._token.tokenType !== s.SQUAREBRACKETS || this._token.tokenID !== r.OPEN) throw "Invalid Table Reference.";
                this._tableRefStart = !0, p = this._getTableReference(f, g);
                break
              }
              if (l = this._getCellRange(e)) {
                this._containsCellRef = !0, p = new o._CellRangeExpression(l.cellRef.cellRange, l.sheetRef, this._owner, e.indexOf(":") > -1, l.cellRef.absRow, l.cellRef.absCol, l.cellRef.absRow2, l.cellRef.absCol2, l.isWholeRow);
                break
              }
              n = this._getParameters(), p = this.onUnknownFunction(e, n);
              break;
            case s.GROUP:
              if (this._token.tokenID !== r.OPEN) throw "Expression expected.";
              if (this._getToken(), p = this._parseCompareOrConcat(), this._token.tokenID !== r.CLOSE) throw "Unbalanced parenthesis.";
              break;
            case s.SQUAREBRACKETS:
              if (this._token.tokenID !== r.OPEN) throw "Table References expected.";
              null != (f = this._sheet.findTable(this._rowIndex, this._columnIndex)) && (g = f.sheet.name, this._tableRefStart = !0, p = this._getTableReference(f, g, !1))
          }
          if (null === p) throw "";
          return this._getToken(), p
        }, n.prototype._getToken = function() {
          for (var e, t, o, n, l, a, h = "", c = "", d = new RegExp("[　-〿぀-ゟ゠-ヿ＀-ﾟ一-龯㐀-䶿]"); this._pointer < this._expressLength && " " === this._expression[this._pointer];) this._pointer++;
          if (this._pointer >= this._expressLength) this._token = new i(null, r.END, s.GROUP);
          else {
            if (t = this._expression[this._pointer], l = t >= "a" && t <= "z" || t >= "A" && t <= "Z" || d.test(t), a = t >= "0" && t <= "9" || "." == t, !l && !a) {
              var u = this._tokenTable[t];
              if (u) return this._token = u, this._pointer++, void(this._pointer < this._expressLength && (">" === t || "<" === t) && (u = this._tokenTable[this._expression.substring(this._pointer - 1, this._pointer + 1)]) && (this._token = u, this._pointer++))
            }
            if (a) {
              if (n = this._pointer, this._parseDigit(), ":" !== this._expression[this._pointer]) return;
              this._pointer = n
            }
            if ('"' !== t) {
              if ("'" !== t || (c = this._parseSheetRef()))
                if ("#" !== t) {
                  if (!l && !a && "_" !== t && this._idChars.indexOf(t) < 0 && !c) throw "Identifier expected.";
                  for (e = 1; e + this._pointer < this._expressLength; e++)
                    if (t = this._expression[this._pointer + e], l = t >= "a" && t <= "z" || t >= "A" && t <= "Z" || d.test(t), a = t >= "0" && t <= "9", "'" !== t || ":" !== o) {
                      if (o = t, !l && !a && "_" !== t && this._idChars.indexOf(t) < 0) break
                    } else h = c + this._expression.substring(this._pointer, this._pointer + e), this._pointer += e, c = this._parseSheetRef(), e = 0;
                  h += c + this._expression.substring(this._pointer, this._pointer + e), this._pointer += e, this._token = new i(h, r.ATOM, s.IDENTIFIER)
                } else this._parseDate()
            } else this._parseString()
          }
        }, n.prototype._getTableToken = function() {
          for (var e, t, o, n = "", l = !1; this._pointer < this._expressLength && " " === this._expression[this._pointer];) this._pointer++;
          for ("@" === (t = this._expression[this._pointer]) && (n = t, this._pointer++), "[" === (t = this._expression[this._pointer]) && (l = !0), e = 1; e + this._pointer < this._expressLength; e++) {
            if (t = this._expression[this._pointer + e], l && "," === t) throw "Invalid table reference.";
            if ("]" === t) break
          }
          o = n + this._expression.substring(this._pointer + (l ? 1 : 0), this._pointer + e), this._pointer += e + (l ? 1 : 0), this._token = new i(o, r.ATOM, s.IDENTIFIER)
        }, n.prototype._parseDigit = function() {
          var e, t, o = -1,
            n = !1,
            l = !1,
            a = 0;
          for (e = 0; e + this._pointer < this._expressLength; e++)
            if ((t = this._expression[this._pointer + e]) >= "0" && t <= "9") a = 10 * a + (+t - 0), o > -1 && (o *= 10);
            else if ("." === t && o < 0) o = 1;
            else {
              if ("E" !== t && "e" !== t || n) {
                if ("%" === t) {
                  l = !0, e++;
                  break
                }
                break
              }
              n = !0, "+" !== (t = this._expression[this._pointer + e + 1]) && "-" !== t || e++
            }
          n ? a = +this._expression.substring(this._pointer, this._pointer + e) : (o > 1 && (a /= o), l && (a /= 100)), this._token = new i(a, r.ATOM, s.LITERAL), this._pointer += e
        }, n.prototype._parseString = function() {
          var e, t, o;
          for (e = 1; e + this._pointer < this._expressLength; e++)
            if ('"' === (t = this._expression[this._pointer + e])) {
              if ('"' !== (e + this._pointer < this._expressLength - 1 ? this._expression[this._pointer + e + 1] : " ")) break;
              e++
            }
          if ('"' !== t) throw "Can't find final quote.";
          if (o = this._expression.substring(this._pointer + 1, this._pointer + e), this._pointer += e + 1, "!" === this._expression[this._pointer]) throw "Illegal cross sheet reference.";
          this._token = new i(o.replace('""', '"'), r.ATOM, s.LITERAL)
        }, n.prototype._parseDate = function() {
          var e, t, o;
          for (e = 1; e + this._pointer < this._expressLength && "#" !== (t = this._expression[this._pointer + e]); e++);
          if ("#" !== t) throw 'Can\'t find final date delimiter ("#").';
          o = this._expression.substring(this._pointer + 1, this._pointer + e), this._pointer += e + 1, this._token = new i(Date.parse(o), r.ATOM, s.LITERAL)
        }, n.prototype._parseSheetRef = function() {
          var e, t, o;
          for (e = 1; e + this._pointer < this._expressLength; e++)
            if ("'" === (t = this._expression[this._pointer + e])) {
              if ("'" !== (e + this._pointer < this._expressLength - 1 ? this._expression[this._pointer + e + 1] : " ")) break;
              e++
            }
          if ("'" !== t) throw "Can't find final quote.";
          return o = this._expression.substring(this._pointer + 1, this._pointer + e), this._pointer += e + 1, "!" === this._expression[this._pointer] ? o.replace(/\'\'/g, "'") : ""
        }, n.prototype._getCellRange = function(e) {
          var t, o, n, i, l;
          if (e && (t = e.split(":")).length > 0 && t.length < 3) {
            if (null == (o = this._parseCell(t[0])).cellRef) return null;
            if (i = o.cellRef.cellRange, null != o.cellRef.isWholeRow && 1 === t.length) return null;
            if (i && 2 === t.length) {
              if (n = this._parseCell(t[1]), l = n.cellRef.cellRange, null != o.cellRef.isWholeRow && o.cellRef.isWholeRow !== n.cellRef.isWholeRow) return null;
              if (o.sheetRef && !n.sheetRef && (n.sheetRef = o.sheetRef), o.sheetRef !== n.sheetRef) throw "The cell reference must be in the same sheet!";
              l ? (i.col2 = l.col, i.row2 = l.row) : i = null
            }
          }
          return null == i ? null : {
            cellRef: {
              cellRange: i,
              absRow: o.cellRef.absRow,
              absCol: o.cellRef.absCol,
              absRow2: n ? n.cellRef.absRow : null,
              absCol2: n ? n.cellRef.absCol : null
            },
            sheetRef: o.sheetRef,
            isWholeRow: o.cellRef.isWholeRow
          }
        }, n.prototype._parseCellRange = function(e) {
          var o, n, i, l, s = -1,
            r = -1,
            a = !1,
            h = !1;
          for (o = 0; o < e.length; o++)
            if ("$" !== (i = e[o]) || a) {
              if (!(i >= "a" && i <= "z" || i >= "A" && i <= "Z")) break;
              s < 0 && (s = 0), s = 26 * s + (i.toUpperCase().charCodeAt(0) - "A".charCodeAt(0) + 1)
            } else a = !0;
          for (; o < e.length; o++)
            if ("$" !== (i = e[o]) || h) {
              if (!(i >= "0" && i <= "9")) break;
              r < 0 && (r = 0), r = 10 * r + (+i - 0)
            } else h = !0;
          return o < e.length ? null : (r > -1 && s > -1 ? n = new t.CellRange(r - 1, s - 1) : -1 === s ? (l = !0, n = new t.CellRange(r - 1, 0)) : -1 === r && (l = !1, n = new t.CellRange(0, s - 1)), {
            cellRange: n,
            absRow: h,
            absCol: a,
            isWholeRow: l
          })
        }, n.prototype._parseCell = function(e) {
          var t, o, n, i;
          if ((o = e.lastIndexOf("!")) > 0 && o < e.length - 1) i = e.substring(0, o), n = e.substring(o + 1);
          else {
            if (!(o <= 0)) return null;
            n = e
          }
          return t = this._parseCellRange(n), {
            cellRef: t,
            sheetRef: i
          }
        }, n.prototype._getParameters = function() {
          var e, t, o = this._pointer,
            n = this._token;
          if (this._getToken(), this._token.tokenType !== s.SQUAREBRACKETS || this._token.tokenID !== r.OPEN) {
            if (this._token.tokenID !== r.OPEN) return this._pointer = o, this._token = n, null;
            if (o = this._pointer, this._getToken(), this._token.tokenID === r.CLOSE) return null;
            for (this._pointer = o, e = new Array, t = this._parseExpression(), e.push(t); this._token.tokenID === r.COMMA;) t = this._parseExpression(), e.push(t);
            if (this._token.tokenID !== r.CLOSE) throw "Syntax error.";
            return e
          }
        }, n.prototype._getTableReference = function(e, t, n) {
          void 0 === n && (n = !0);
          var i, l, a = [],
            h = this._getTableParameter(),
            c = !1;
          if (null == h) throw "Invalid table reference.";
          for (a.push(h); this._tableRefStart && (this._token.tokenID === r.COMMA || ":" === this._token.value);) {
            if (":" === this._token.value && (c = !0), null == (h = this._getTableParameter())) throw "Invalid table reference.";
            c ? (a[a.length - 1] += ":" + h, c = !1) : a.push(h)
          }
          if (this._token.tokenType !== s.SQUAREBRACKETS || this._token.tokenID !== r.CLOSE) throw "Unbalanced square brackets.";
          return this._tableRefStart = !1, i = this._getTableRange(e, a), this._containsCellRef = !0, l = new o._CellRangeExpression(i, t.toLowerCase(), this._owner), l._tableParams = a, n && (l._tableName = e.name), l
        }, n.prototype._getTableParameter = function() {
          var e;
          for (this._pointer; this._pointer < this._expressLength && " " === this._expression[this._pointer];) this._pointer++;
          return "]" === this._expression[this._pointer] ? (this._tableRefStart = !1, null) : (this._getTableToken(), e = this._token.value, this._getToken(), e)
        }, n.prototype._getTableRange = function(e, n) {
          for (var i, l, s, r, a, h, c = 0; c < n.length; c++) {
            if (l) throw "Invalid Table Refernce.";
            if (i = n[c].toLowerCase(), a = null, "#" === i[0]) {
              switch (i) {
                case "#all":
                  a = e.getRange();
                  break;
                case "#data":
                  a = e.getRange(o.TableSection.Data);
                  break;
                case "#headers":
                  a = e.getRange(o.TableSection.Header);
                  break;
                case "#totals":
                  a = e.getRange(o.TableSection.Footer);
                  break;
                case "#this row":
                  if (r = e.getRange(), !(this._rowIndex >= r.topRow && this._rowIndex <= r.bottomRow)) throw "The row is out of the table (" + e.name + ") range.";
                  a = new t.CellRange(this._rowIndex, r.leftCol, this._rowIndex, r.rightCol);
                  break;
                default:
                  throw "Invalid Table Refernce."
              }
              if (null == a) throw "Invalid Table Refernce.";
              null == s ? s = a : (s.row = s.topRow < a.topRow ? s.topRow : a.topRow, s.row2 = s.bottomRow > a.bottomRow ? s.bottomRow : a.bottomRow)
            } else {
              l = !0, h = i.split(":");
              for (var d = 0; d < h.length; d++) {
                if (h.length > 2) throw "Invalid Table Column Refernce.";
                if ("@" === (i = h[d])[0]) {
                  if (null == (a = e.getRange(o.TableSection.Data, i.substring(1)))) throw "Invalid Table Refernce.";
                  if (!(this._rowIndex >= a.topRow && this._rowIndex <= a.bottomRow)) throw "The row is out of the table (" + e.name + ") range.";
                  a.row = this._rowIndex, a.row2 = this._rowIndex
                } else if (null == (a = e.getRange(o.TableSection.Data, i))) throw "Invalid Table Refernce.";
                0 === d && (null == s ? s = a : s.col = a.col), s.col2 = a.col, a = null
              }
            }
          }
          return s
        }, n.prototype._getAggregateResult = function(t, o, n) {
          var i, l = this._getItemList(o, n);
          return i = e.getAggregate(t, l.items), l.isDate && (i = {
            value: i = new Date(i),
            format: l.format || "M/d/yyyy"
          }), i
        }, n.prototype._getFlexSheetAggregateResult = function(e, t, n) {
          var i, l, s, r;
          switch (e) {
            case a.Count:
              return i = this._getItemList(t, n, !0, !1), this._countNumberCells(i.items);
            case a.CountA:
              return (i = this._getItemList(t, n, !1, !1)).items.length;
            case a.ConutBlank:
              return i = this._getItemList(t, n, !1, !0), this._countBlankCells(i.items);
            case a.Rank:
              if (s = o._Expression.toNumber(t[0], this._rowIndex, this._columnIndex, n), r = t[2] ? o._Expression.toNumber(t[2], this._rowIndex, this._columnIndex, n) : 0, isNaN(s)) throw "Invalid number.";
              if (isNaN(r)) throw "Invalid order.";
              if (t[1] = this._ensureNonFunctionExpression(t[1], n), t[1] instanceof o._CellRangeExpression) return i = this._getItemList([t[1]], n), this._getRankOfCellRange(s, i.items, r);
              throw "Invalid Cell Reference.";
            case a.CountIf:
              if (t[0] = this._ensureNonFunctionExpression(t[0], n), t[0] instanceof o._CellRangeExpression) return i = this._getItemList([t[0]], n, !1), this._countCellsByCriterias([i.items], [t[1]], n);
              throw "Invalid Cell Reference.";
            case a.CountIfs:
              return this._handleCountIfs(t, n);
            case a.SumIf:
              if (t[0] = this._ensureNonFunctionExpression(t[0], n), t[0] instanceof o._CellRangeExpression) return i = this._getItemList([t[0]], n, !1), t[2] = this._ensureNonFunctionExpression(t[2], n), null != t[2] && t[2] instanceof o._CellRangeExpression && (l = this._getItemList([t[2]], n)), this._sumCellsByCriterias([i.items], [t[1]], l ? l.items : null, n);
              throw "Invalid Cell Reference.";
            case a.SumIfs:
              return this._handleSumIfs(t, n);
            case a.Product:
              return i = this._getItemList(t, n), this._getProductOfNumbers(i.items)
          }
          throw "Invalid aggregate type."
        }, n.prototype._getItemList = function(t, n, i, l, s, r) {
          void 0 === i && (i = !0), void 0 === l && (l = !1), void 0 === s && (s = !0);
          var a, h, c, d, u, _, g, f, p, w, m, C = new Array;
          for (h = 0; h < t.length; h++)
            if (u = t[h], (u = this._ensureNonFunctionExpression(u, n)) instanceof o._CellRangeExpression) {
              d = u.getValues(s, r, n), 0 === h && ((p = (f = u._getSheet() || n || this._sheet).getCellStyle(u.cells.topRow, u.cells.leftCol)) && (g = p.format), g || (w = f.grid.columns[u.cells.leftCol]) && (g = w.format));
              e: for (c = 0; c < d.length; c++)
                if (a = d[c], null == _ && null != a && !e.isString(a)) {
                  _ = e.isDate(a);
                  break e
                }
              for (c = 0; c < d.length; c++)
                if (a = d[c], l || null != a && "" !== a) {
                  if (e.isString(a) && 0 === a.indexOf("Error: ")) {
                    C = [0];
                    break
                  }
                  m = e.isString(a) ? _ ? e.changeType(a, e.DataType.Date, "") : e.changeType(a, e.DataType.Number, "") : a, i && !e.isBoolean(m) ? isNaN(+m) || C.push(+m) : C.push(a)
                }
            } else {
              if (a = u instanceof o._Expression ? u.evaluate(this._rowIndex, this._columnIndex, n) : u, e.isPrimitive(a) || (a = a.value), !l && (null == a || "" === a)) continue;
              if (e.isString(a) && 0 === a.indexOf("Error: ")) {
                C = [0];
                break
              }
              i ? isNaN(+a) || C.push(+a) : C.push(a), null == _ && C.length > 0 && (_ = a instanceof Date)
            }
          return 0 === C.length && (_ = !1), {
            isDate: _,
            items: C,
            format: g
          }
        }, n.prototype._countBlankCells = function(t) {
          for (var o, n = 0, i = 0; n < t.length; n++)(null == (o = t[n]) || e.isString(o) && "" === o || e.isNumber(o) && isNaN(o)) && i++;
          return i
        }, n.prototype._countNumberCells = function(t) {
          for (var o, n = 0, i = 0; n < t.length; n++) null != (o = t[n]) && e.isNumber(o) && !isNaN(o) && i++;
          return i
        }, n.prototype._getRankOfCellRange = function(e, t, o) {
          void 0 === o && (o = 0);
          var n, i = 0,
            l = 0;
          for (o ? t.sort(function(e, t) {
            return isNaN(e) || isNaN(t) ? -1 : e - t
          }) : t.sort(function(e, t) {
            return isNaN(e) || isNaN(t) ? 1 : t - e
          }); i < t.length; i++)
            if (n = t[i], !isNaN(n) && (l++, e === n)) return l;
          throw e + " is not in the cell range."
        }, n.prototype._handleCountIfs = function(e, t) {
          var n, i, l, s, r = 0,
            a = [],
            h = [];
          if (e.length % 2 != 0) throw "Invalid params.";
          for (; r < e.length / 2; r++) {
            if (i = e[2 * r], !((i = this._ensureNonFunctionExpression(i, t)) instanceof o._CellRangeExpression)) throw "Invalid Cell Reference.";
            if (0 === r) {
              if (!i.cells) throw "Invalid Cell Reference.";
              l = i.cells.rowSpan, s = i.cells.columnSpan
            } else {
              if (!i.cells) throw "Invalid Cell Reference.";
              if (i.cells.rowSpan !== l || i.cells.columnSpan !== s) throw "The row span and column span of each cell range has to be same with each other."
            }
            n = this._getItemList([i], t, !1), a[r] = n.items, h[r] = e[2 * r + 1]
          }
          return this._countCellsByCriterias(a, h, t)
        }, n.prototype._countCellsByCriterias = function(t, n, i, l) {
          for (var s, r, a, h, c, d = 0, u = 0, _ = 0, g = t[0].length, f = []; u < n.length; u++) "*" === (c = o._Expression.toString(n[u], this._rowIndex, this._columnIndex, i)) ? f.push(c) : f.push(this._parseRightExpr(c));
          for (; d < g; d++) {
            s = !1;
            e: for (u = 0; u < t.length; u++)
              if (a = t[u], h = a[d], "string" == typeof(c = f[u])) {
                if ("*" !== c && (null == h || "" === h)) {
                  s = !1;
                  break e
                }
                if (!(s = "*" === c || this.evaluate(this._combineExpr(h, c), null, i, this._rowIndex, this._columnIndex))) break e
              } else
              if (!(s = s = c.reg.test(h.toString()) === c.checkMathces)) break e;
            s && (l ? null != (r = l[d]) && e.isNumber(r) && !isNaN(r) && _++ : _++)
          }
          return _
        }, n.prototype._handleSumIfs = function(e, t) {
          var n, i, l, s, r, a, h = 1,
            c = [],
            d = [];
          if (e.length % 2 != 1) throw "Invalid params.";
          if (l = e[0], !((l = this._ensureNonFunctionExpression(l, t)) instanceof o._CellRangeExpression)) throw "Invalid Sum Cell Reference.";
          if (!l.cells) throw "Invalid Sum Cell Reference.";
          for (r = l.cells.rowSpan, a = l.cells.columnSpan, i = this._getItemList([l], t); h < (e.length + 1) / 2; h++) {
            if (s = e[2 * h - 1], !((s = this._ensureNonFunctionExpression(s, t)) instanceof o._CellRangeExpression)) throw "Invalid Criteria Cell Reference.";
            if (!s.cells) throw "Invalid Criteria Cell Reference.";
            if (s.cells.rowSpan !== r || s.cells.columnSpan !== a) throw "The row span and column span of each cell range has to be same with each other.";
            n = this._getItemList([s], t, !1), c[h - 1] = n.items, d[h - 1] = e[2 * h]
          }
          return this._sumCellsByCriterias(c, d, i.items, t)
        }, n.prototype._sumCellsByCriterias = function(t, n, i, l) {
          var s, r, a, h, c, d = 0,
            u = 0,
            _ = 0,
            g = t[0].length,
            f = [];
          for (null == i && (i = t[0]); u < n.length; u++) "*" === (c = o._Expression.toString(n[u], this._rowIndex, this._columnIndex, l)) ? f.push(c) : f.push(this._parseRightExpr(c));
          for (; d < g; d++) {
            r = !1, s = i[d];
            e: for (u = 0; u < t.length; u++)
              if (a = t[u], h = a[d], "string" == typeof(c = f[u])) {
                if ("*" !== c && (null == h || "" === h)) {
                  r = !1;
                  break e
                }
                if (!(r = "*" === c || this.evaluate(this._combineExpr(h, c), null, l, this._rowIndex, this._columnIndex))) break e
              } else
              if (!(r = c.reg.test(h.toString()) === c.checkMathces)) break e;
            r && e.isNumber(s) && !isNaN(s) && (_ += s)
          }
          return _
        }, n.prototype._getProductOfNumbers = function(t) {
          var o, n = 0,
            i = 1,
            l = !1;
          if (t)
            for (; n < t.length; n++) o = t[n], e.isNumber(o) && !isNaN(o) && (i *= o, l = !0);
          return l ? i : 0
        }, n.prototype._handleSubtotal = function(t, n) {
          var i, l, s, r, a = !0,
            c = !0;
          if ((i = o._Expression.toNumber(t[0], this._rowIndex, this._columnIndex, n)) >= 1 && i <= 11 || i >= 101 && i <= 111) {
            switch (i >= 101 && i <= 111 && (a = !1), (i = e.asEnum(i, h)) !== h.CountA && i !== h.CountAWithoutHidden || (c = !1), l = this._getItemList(t.slice(1), n, c, !1, a), i) {
              case h.Count:
              case h.CountWithoutHidden:
                return this._countNumberCells(l.items);
              case h.CountA:
              case h.CountAWithoutHidden:
                return l.items.length;
              case h.Product:
              case h.ProductWithoutHidden:
                return this._getProductOfNumbers(l.items);
              case h.Average:
              case h.AverageWithoutHidden:
                s = e.Aggregate.Avg;
                break;
              case h.Max:
              case h.MaxWithoutHidden:
                s = e.Aggregate.Max;
                break;
              case h.Min:
              case h.MinWithoutHidden:
                s = e.Aggregate.Min;
                break;
              case h.Std:
              case h.StdWithoutHidden:
                s = e.Aggregate.Std;
                break;
              case h.StdPop:
              case h.StdPopWithoutHidden:
                s = e.Aggregate.StdPop;
                break;
              case h.Sum:
              case h.SumWithoutHidden:
                s = e.Aggregate.Sum;
                break;
              case h.Var:
              case h.VarWithoutHidden:
                s = e.Aggregate.Var;
                break;
              case h.VarPop:
              case h.VarPopWithoutHidden:
                s = e.Aggregate.VarPop
            }
            return r = e.getAggregate(s, l.items), l.isDate && (r = new Date(r)), r
          }
          throw "Invalid Subtotal function."
        }, n.prototype._handleDCount = function(e, t) {
          var n, i, l, s = e[0],
            r = e[2];
          if (s = this._ensureNonFunctionExpression(s, t), r = this._ensureNonFunctionExpression(r, t), s instanceof o._CellRangeExpression && r instanceof o._CellRangeExpression && (n = e[1].evaluate(this._rowIndex, this._columnIndex, t), i = this._getColumnIndexByField(s, n), (l = this._getItemList([s], t, !0, !1, !0, i)).items && l.items.length > 1)) return this._DCountWithCriterias(l.items.slice(1), s, r);
          throw "Invalid Count Cell Reference."
        }, n.prototype._DCountWithCriterias = function(e, t, n) {
          var i, l, s, r, a, h, c, d, u, _, g, f = n.cells,
            p = 0;
          if (i = this._getSheet(t.sheetRef), l = this._getSheet(n.sheetRef), f.rowSpan > 1) {
            for (s = f.topRow, r = f.bottomRow; r > f.topRow; r--) {
              for (_ = [], g = [], a = f.leftCol; a <= f.rightCol; a++)
                if (null != (c = this._owner.getCellValue(r, a, !1, l)) && "" !== c) {
                  if (g.push(new o._Expression(c)), d = this._owner.getCellValue(s, a, !1, l), h = this._getColumnIndexByField(t, d), !(null != (u = this._getItemList([t], i, !1, !1, !0, h)).items && u.items.length > 1)) throw "Invalid Count Cell Reference.";
                  _.push(u.items.slice(1))
                }
              p += this._countCellsByCriterias(_, g, i, e)
            }
            return p
          }
          throw "Invalid Criteria Cell Reference."
        }, n.prototype._getColumnIndexByField = function(t, o) {
          var n, i, l, s, r;
          if (n = t.cells, -1 === (r = n.topRow)) throw "Invalid Count Cell Reference.";
          if (e.isInt(o) && !isNaN(o)) {
            if (o >= 1 && o <= n.columnSpan) return l = n.leftCol + o - 1
          } else
            for (i = this._getSheet(t.sheetRef), l = n.leftCol; l <= n.rightCol; l++)
              if (s = this._owner.getCellValue(r, l, !1, i), o = e.isString(o) ? o.toLowerCase() : o, s = e.isString(s) ? s.toLowerCase() : s, o === s) return l; throw "Invalid field."
        }, n.prototype._getSumProduct = function(e, t) {
          var o, n, i, l = 0,
            s = this._getItemListForSumProduct(e, t);
          if (s.length > 0) {
            n = s[0].length, i = s.length;
            for (var r = 0; r < n; r++) {
              o = 1;
              for (var a = 0; a < i; a++) o *= s[a][r];
              l += o
            }
          }
          return l
        }, n.prototype._getItemListForSumProduct = function(e, t) {
          var n, i, l, s, r, a, h = [new Array];
          for (l = 0; l < e.length; l++) {
            if (a = e[l], n = new Array, (a = this._ensureNonFunctionExpression(a, t)) instanceof o._CellRangeExpression)
              for (r = a.getValues(!0, null, t), s = 0; s < r.length; s++) i = r[s], n.push(+i);
            else i = a instanceof o._Expression ? a.evaluate(this._rowIndex, this._columnIndex, t) : a, n.push(+i);
            if (l > 0 && n.length !== h[0].length) throw "The cell ranges of the sumProduct formula must have the same dimensions.";
            h[l] = n
          }
          return h
        }, n.prototype._getSheet = function(e) {
          var t, o = 0;
          if (e)
            for (; o < this._owner.sheets.length && (t = this._owner.sheets[o]).name !== e; o++);
          return t
        }, n.prototype._parseRightExpr = function(e) {
          var t, o, n = !1;
          if (e.indexOf("?") > -1 || e.indexOf("*") > -1) {
            if (null == (t = e.match(/=?([\?\*]*)(\w*)([\?\*]*)(\w*)([\?\*]*)/)) || 6 !== t.length) throw "Invalid Criteria.";
            return o = new RegExp("^" + (t[1].length > 0 ? this._parseRegCriteria(t[1]) : "") + t[2] + (t[3].length > 0 ? this._parseRegCriteria(t[3]) : "") + t[4] + (t[5].length > 0 ? this._parseRegCriteria(t[5]) : "") + "$", "i"), /^[<>=]/.test(e) ? "=" === e.trim()[0] && (n = !0) : n = !0, {
              reg: o,
              checkMathces: n
            }
          }
          if (isNaN(+e))
            if (/^\w/.test(e)) e = '="' + e + '"';
            else {
              if (!/^[<>=]{1,2}\s*-?.+$/.test(e)) throw "Invalid Criteria.";
              e = e.replace(/([<>=]{1,2})\s*(-?.+)/, '$1"$2"')
            } else e = "=" + +e;
          return e
        }, n.prototype._combineExpr = function(t, o) {
          return (e.isString(t) || e.isDate(t)) && (t = '"' + t + '"'), (t = "=" + t) + o
        }, n.prototype._parseRegCriteria = function(e) {
          for (var t = 0, o = 0, n = ""; t < e.length; t++) "*" === e[t] ? (o > 0 && (n += "\\w{" + o + "}", o = 0), n += "\\w*") : "?" === e[t] && o++;
          return o > 0 && (n += "\\w{" + o + "}"), n
        }, n.prototype._calculateRate = function(e, t) {
          var n, i, l, s, r, a, h, c, d, u, _ = 0,
            g = 0;
          for (l = o._Expression.toNumber(e[0], this._rowIndex, this._columnIndex, t), s = o._Expression.toNumber(e[1], this._rowIndex, this._columnIndex, t), r = o._Expression.toNumber(e[2], this._rowIndex, this._columnIndex, t), a = null != e[3] ? o._Expression.toNumber(e[3], this._rowIndex, this._columnIndex, t) : 0, h = null != e[4] ? o._Expression.toNumber(e[4], this._rowIndex, this._columnIndex, t) : 0, i = null != e[5] ? o._Expression.toNumber(e[5], this._rowIndex, this._columnIndex, t) : .1, Math.abs(i) < 1e-7 ? r * (1 + l * i) + s * (1 + i * h) * l + a : r * (c = Math.exp(l * Math.log(1 + i))) + s * (1 / i + h) * (c - 1) + a, d = r + s * l + a, u = r * c + s * (1 / i + h) * (c - 1) + a, n = i; Math.abs(d - u) > 1e-7 && _ < 20;) i = (u * g - d * n) / (u - d), g = n, n = i, d = u, u = Math.abs(i) < 1e-7 ? r * (1 + l * i) + s * (1 + i * h) * l + a : r * (c = Math.exp(l * Math.log(1 + i))) + s * (1 / i + h) * (c - 1) + a, ++_;
          if (Math.abs(d - u) > 1e-7 && 20 === _) throw "It is not able to calculate the rate with current parameters.";
          return i
        }, n.prototype._handleHLookup = function(e, t) {
          var n, i, l = e[0].evaluate(this._rowIndex, this._columnIndex, t),
            s = e[1],
            r = o._Expression.toNumber(e[2], this._rowIndex, this._columnIndex, t),
            a = null == e[3] || o._Expression.toBoolean(e[3], this._rowIndex, this._columnIndex, t);
          if (null == l || "" == l) throw "Invalid lookup value.";
          if (isNaN(r) || r < 0) throw "Invalid row index.";
          if ((s = this._ensureNonFunctionExpression(s, t)) instanceof o._CellRangeExpression) {
            if (n = s.cells, r > n.rowSpan) throw "Row index is out of the cell range.";
            if (a ? -1 === (i = this._exactMatch(l, n, t, !1)) && (i = this._approximateMatch(l, n, t)) : i = this._exactMatch(l, n, t), -1 === i) throw "Lookup Value is not found.";
            return this._owner.getCellValue(n.topRow + r - 1, i, !1, t)
          }
          throw "Invalid Cell Reference."
        }, n.prototype._exactMatch = function(t, o, n, i) {
          void 0 === i && (i = !0);
          var l, s, r, a, h = o.topRow;
          if (e.isString(t) && (t = t.toLowerCase()), i && e.isString(t) && (t.indexOf("?") > -1 || t.indexOf("*") > -1)) {
            if (null == (r = t.match(/([\?\*]*)(\w+)([\?\*]*)(\w+)([\?\*]*)/)) || 6 !== r.length) throw "Invalid lookup value.";
            a = new RegExp("^" + (r[1].length > 0 ? this._parseRegCriteria(r[1]) : "") + r[2] + (r[3].length > 0 ? this._parseRegCriteria(r[3]) : "") + r[4] + (r[5].length > 0 ? this._parseRegCriteria(r[5]) : "") + "$", "i")
          }
          for (l = o.leftCol; l <= o.rightCol; l++)
            if (s = this._owner.getCellValue(h, l, !1, n), null != a) {
              if (a.test(s)) return l
            } else if (e.isString(s) && (s = s.toLowerCase()), t === s) return l;
          return -1
        }, n.prototype._approximateMatch = function(t, o, n) {
          var i, l, s = o.topRow,
            r = [],
            a = 0;
          for (e.isString(t) && (t = t.toLowerCase()), l = o.leftCol; l <= o.rightCol; l++) i = this._owner.getCellValue(s, l, !1, n), i = isNaN(+i) ? i : +i, r.push({
            value: i,
            index: l
          });
          for (r.sort(function(t, o) {
            return e.isString(t.value) && (t.value = t.value.toLowerCase()), e.isString(o.value) && (o.value = o.value.toLowerCase()), t.value > o.value ? -1 : t.value === o.value ? o.index - t.index : 1
          }); a < r.length; a++)
            if (i = r[a], e.isString(i.value) && (i.value = i.value.toLowerCase()), t > i.value) return i.index;
          throw "Lookup Value is not found."
        }, n.prototype._parseToScientificValue = function(t, o, n, i, l) {
          var s, r, a, h, c = 0;
          if (Math.abs(t) >= 1)
            for (r = "+", s = Math.pow(10, o.length); t > s;) t /= s, c += o.length;
          else
            for (r = "-", s = Math.pow(10, o.length); t * s < s;) t *= s, c += o.length;
          if (a = e.Globalize.format(t, "D" + o.length), n)
            for ((a += e.Globalize.format(t - Math.floor(t), o + n).substring(1)).indexOf(".") > -1 ? h = a.length - 1 - a.indexOf(".") : (a += ".", h = 0); h < n.length - 1;) a += "0", h++;
          if (a += "E" + r + e.Globalize.format(c, "D" + i.length), l) {
            a += ".";
            for (var d = 1; d < l.length; d++) a += "0"
          }
          return a
        }, n.prototype._checkCache = function(e, t, o, n) {
          null != t && (this._sheet = this._owner.sheets[t]), null != o && (this._rowIndex = o), null != n && (this._columnIndex = n);
          var i = this._expressionCache[e] || this._expressionCache[this._rowIndex + "_" + this._columnIndex + ":" + e];
          if (i) return i;
          if (i = this._parse(e), this._token.tokenID !== r.END || this._token.tokenType !== s.GROUP) throw "Invalid Expression: " + e;
          return this._cacheSize > 1e4 && this._clearExpressionCache(), this._expressionCache[(this._containsCellRef ? this._rowIndex + "_" + this._columnIndex + ":" : "") + e] = i, this._cacheSize++, i
        }, n.prototype._ensureNonFunctionExpression = function(e, t) {
          for (; e instanceof o._FunctionExpression;) e = e.evaluate(this._rowIndex, this._columnIndex, t);
          return e
        }, n.prototype._getDefinedName = function(e, t) {
          for (var o, n, i = 0; i < this._owner.definedNames.length; i++)
            if ((o = this._owner.definedNames[i]).name.toLowerCase() === e)
              if (o.sheetName) {
                if (o.sheetName.toLowerCase() === t) return o
              } else n = o;
          return n
        }, n.prototype._numAlpha = function(e) {
          var t = Math.floor((e - 1) / 26);
          return (t > 0 ? this._numAlpha(t) : "") + String.fromCharCode((e - 1) % 26 + 65)
        }, n
      }();
      o._CalcEngine = n;
      var i = function() {
        function e(e, t, o) {
          this._value = e, this._tokenID = t, this._tokenType = o
        }
        return Object.defineProperty(e.prototype, "value", {
          get: function() {
            return this._value
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "tokenID", {
          get: function() {
            return this._tokenID
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "tokenType", {
          get: function() {
            return this._tokenType
          },
          enumerable: !0,
          configurable: !0
        }), e
      }();
      o._Token = i;
      var l = function() {
        function t(t, o, n) {
          this._paramMax = Number.MAX_VALUE, this._paramMin = Number.MIN_VALUE, this._func = t, e.isNumber(o) && !isNaN(o) && (this._paramMax = o), e.isNumber(n) && !isNaN(n) && (this._paramMin = n)
        }
        return Object.defineProperty(t.prototype, "paramMax", {
          get: function() {
            return this._paramMax
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "paramMin", {
          get: function() {
            return this._paramMin
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "func", {
          get: function() {
            return this._func
          },
          enumerable: !0,
          configurable: !0
        }), t
      }();
      o._FunctionDefinition = l;
      var s;
      ! function(e) {
        e[e.COMPARE = 0] = "COMPARE", e[e.ADDSUB = 1] = "ADDSUB", e[e.MULDIV = 2] = "MULDIV", e[e.POWER = 3] = "POWER", e[e.CONCAT = 4] = "CONCAT", e[e.GROUP = 5] = "GROUP", e[e.LITERAL = 6] = "LITERAL", e[e.IDENTIFIER = 7] = "IDENTIFIER", e[e.SQUAREBRACKETS = 8] = "SQUAREBRACKETS"
      }(s = o._TokenType || (o._TokenType = {}));
      var r;
      ! function(e) {
        e[e.GT = 0] = "GT", e[e.LT = 1] = "LT", e[e.GE = 2] = "GE", e[e.LE = 3] = "LE", e[e.EQ = 4] = "EQ", e[e.NE = 5] = "NE", e[e.ADD = 6] = "ADD", e[e.SUB = 7] = "SUB", e[e.MUL = 8] = "MUL", e[e.DIV = 9] = "DIV", e[e.DIVINT = 10] = "DIVINT", e[e.MOD = 11] = "MOD", e[e.POWER = 12] = "POWER", e[e.CONCAT = 13] = "CONCAT", e[e.OPEN = 14] = "OPEN", e[e.CLOSE = 15] = "CLOSE", e[e.END = 16] = "END", e[e.COMMA = 17] = "COMMA", e[e.PERIOD = 18] = "PERIOD", e[e.ATOM = 19] = "ATOM"
      }(r = o._TokenID || (o._TokenID = {}));
      var a;
      ! function(e) {
        e[e.Count = 0] = "Count", e[e.CountA = 1] = "CountA", e[e.ConutBlank = 2] = "ConutBlank", e[e.CountIf = 3] = "CountIf", e[e.CountIfs = 4] = "CountIfs", e[e.Rank = 5] = "Rank", e[e.SumIf = 6] = "SumIf", e[e.SumIfs = 7] = "SumIfs", e[e.Product = 8] = "Product"
      }(a || (a = {}));
      var h;
      ! function(e) {
        e[e.Average = 1] = "Average", e[e.Count = 2] = "Count", e[e.CountA = 3] = "CountA", e[e.Max = 4] = "Max", e[e.Min = 5] = "Min", e[e.Product = 6] = "Product", e[e.Std = 7] = "Std", e[e.StdPop = 8] = "StdPop", e[e.Sum = 9] = "Sum", e[e.Var = 10] = "Var", e[e.VarPop = 11] = "VarPop", e[e.AverageWithoutHidden = 101] = "AverageWithoutHidden", e[e.CountWithoutHidden = 102] = "CountWithoutHidden", e[e.CountAWithoutHidden = 103] = "CountAWithoutHidden", e[e.MaxWithoutHidden = 104] = "MaxWithoutHidden", e[e.MinWithoutHidden = 105] = "MinWithoutHidden", e[e.ProductWithoutHidden = 106] = "ProductWithoutHidden", e[e.StdWithoutHidden = 107] = "StdWithoutHidden", e[e.StdPopWithoutHidden = 108] = "StdPopWithoutHidden", e[e.SumWithoutHidden = 109] = "SumWithoutHidden", e[e.VarWithoutHidden = 110] = "VarWithoutHidden", e[e.VarPopWithoutHidden = 111] = "VarPopWithoutHidden"
      }(h || (h = {}))
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function() {
        function t(e) {
          e ? e instanceof o._Token ? this._token = e : this._token = new o._Token(e, o._TokenID.ATOM, o._TokenType.LITERAL) : this._token = new o._Token(null, o._TokenID.ATOM, o._TokenType.IDENTIFIER)
        }
        return Object.defineProperty(t.prototype, "token", {
          get: function() {
            return this._token
          },
          enumerable: !0,
          configurable: !0
        }), t.prototype.evaluate = function(e, t, n) {
          if (this._token.tokenType !== o._TokenType.LITERAL) throw "Bad expression.";
          return this._token.value
        }, t.toString = function(t, o, n, i) {
          var l = t.evaluate(o, n, i);
          return e.isPrimitive(l) || (l = l.value), null != l ? l.toString() : ""
        }, t.toNumber = function(t, n, i, l) {
          var s = t.evaluate(n, i, l);
          return e.isPrimitive(s) || (s = s.value), e.isNumber(s) ? s : e.isBoolean(s) ? s ? 1 : 0 : e.isDate(s) ? o.FlexSheet._toOADate(s) : e.isString(s) ? s ? isNaN(+s) ? this.isDateValue(s) ? o.FlexSheet._toOADate(new Date(s)) : e.changeType(s, e.DataType.Number, "") : +s : 0 : e.changeType(s, e.DataType.Number, "")
        }, t.toBoolean = function(t, o, n, i) {
          var l = t.evaluate(o, n, i);
          return e.isPrimitive(l) || (l = l.value), e.isBoolean(l) ? l : e.isNumber(l) ? 0 !== l : e.changeType(l, e.DataType.Boolean, "")
        }, t.toDate = function(t, n, i, l) {
          var s = t.evaluate(n, i, l);
          return e.isPrimitive(s) || (s = s.value), e.isDate(s) ? s : e.isNumber(s) ? o.FlexSheet._fromOADate(s) : e.changeType(s, e.DataType.Date, "")
        }, t.isDateValue = function(t) {
          var o, n = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
          if (e.isPrimitive(t) || (t = t.value), !(o = e.isDate(t)) && e.isString(t))
            for (var i in n)
              if (t.indexOf(n[i]) > -1) {
                o = !0;
                break
              }
          return o
        }, t.prototype._updateCellRangeExp = function(e, t, o, n, i, l) {
          return !1
        }, t.prototype._moveCellRangeExp = function(e, t, o, n, i) {
          return void 0 === n && (n = !1), void 0 === i && (i = !1), !1
        }, t.prototype._updateCellRangeExpForReorderingRows = function(e) {
          return !1
        }, t.prototype._updateCellBoundary = function(e, t) {
          return !1
        }, t.prototype._getStringExpression = function() {
          return null == this._token.value ? "" : e.isString(this._token.value) ? '"' + this._token.value + '"' : this._token.value.toString()
        }, t
      }();
      o._Expression = n;
      var i = function(e) {
        function t(t, o) {
          var n = e.call(this, t) || this;
          return n._expr = o, n
        }
        return __extends(t, e), t.prototype.evaluate = function(e, t, i) {
          if (this.token.tokenID === o._TokenID.SUB) return null == this._evaluatedValue && (this._evaluatedValue = -n.toNumber(this._expr, e, t, i)), this._evaluatedValue;
          if (this.token.tokenID === o._TokenID.ADD) return null == this._evaluatedValue && (this._evaluatedValue = +n.toNumber(this._expr, e, t, i)), this._evaluatedValue;
          throw "Bad expression."
        }, t.prototype._updateCellRangeExp = function(e, t, o, n, i, l) {
          return this._expr._updateCellRangeExp(e, t, o, n, i, l)
        }, t.prototype._moveCellRangeExp = function(e, t, o, n, i) {
          return void 0 === n && (n = !1), void 0 === i && (i = !1), this._expr._moveCellRangeExp(e, t, o, n, i)
        }, t.prototype._updateCellRangeExpForReorderingRows = function(e) {
          return this._expr._updateCellRangeExpForReorderingRows(e)
        }, t.prototype._getStringExpression = function() {
          return this.token.tokenID === o._TokenID.SUB ? "-" + this._expr._getStringExpression() : this.token.tokenID === o._TokenID.ADD ? "+" + this._expr._getStringExpression() : ""
        }, t
      }(n);
      o._UnaryExpression = i;
      var l = function(t) {
        function i(e, o, n) {
          var i = t.call(this, e) || this;
          return i._leftExpr = o, i._rightExpr = n, i
        }
        return __extends(i, t), i.prototype.evaluate = function(t, i, l) {
          var s, r, a, h, c, d, u, _, g, f = !1;
          if (null != this._evaluatedValue) return this._evaluatedValue;
          if (s = n.toString(this._leftExpr, t, i, l), r = n.toString(this._rightExpr, t, i, l), this.token.tokenType === o._TokenType.CONCAT) return this._evaluatedValue = s + r, this._evaluatedValue;
          if (a = this._leftExpr.evaluate(t, i, l), e.isPrimitive(a) || (c = a.format, a = a.value), h = this._rightExpr.evaluate(t, i, l), e.isPrimitive(h) || (d = h.format, h = h.value), (f = n.isDateValue(a) || n.isDateValue(h)) ? (u = e.isDate(a) ? o.FlexSheet._toOADate(a) : e.isNumber(a) ? a : o.FlexSheet._toOADate(new Date(a)), _ = e.isDate(h) ? o.FlexSheet._toOADate(h) : e.isNumber(h) ? h : o.FlexSheet._toOADate(new Date(h))) : (u = n.toNumber(this._leftExpr, t, i, l), _ = n.toNumber(this._rightExpr, t, i, l)), g = u - _, this.token.tokenType === o._TokenType.COMPARE) switch (this.token.tokenID) {
            case o._TokenID.GT:
              return g > 0;
            case o._TokenID.LT:
              return g < 0;
            case o._TokenID.GE:
              return g >= 0;
            case o._TokenID.LE:
              return g <= 0;
            case o._TokenID.EQ:
              return isNaN(g) ? (this._evaluatedValue = s.toLowerCase() === r.toLowerCase(), this._evaluatedValue) : (this._evaluatedValue = 0 === g, this._evaluatedValue);
            case o._TokenID.NE:
              return isNaN(g) ? (this._evaluatedValue = s.toLowerCase() !== r.toLowerCase(), this._evaluatedValue) : (this._evaluatedValue = 0 !== g, this._evaluatedValue)
          }
          switch (this.token.tokenID) {
            case o._TokenID.ADD:
              this._evaluatedValue = u + _;
              break;
            case o._TokenID.SUB:
              this._evaluatedValue = u - _;
              break;
            case o._TokenID.MUL:
              this._evaluatedValue = u * _;
              break;
            case o._TokenID.DIV:
              this._evaluatedValue = u / _;
              break;
            case o._TokenID.DIVINT:
              this._evaluatedValue = Math.floor(u / _);
              break;
            case o._TokenID.MOD:
              this._evaluatedValue = Math.floor(u % _);
              break;
            case o._TokenID.POWER:
              0 === _ && (this._evaluatedValue = 1), .5 === _ && (this._evaluatedValue = Math.sqrt(u)), 1 === _ && (this._evaluatedValue = u), 2 === _ && (this._evaluatedValue = u * u), 3 === _ && (this._evaluatedValue = u * u * u), 4 === _ && (this._evaluatedValue = u * u * u * u), this._evaluatedValue = Math.pow(u, _);
              break;
            default:
              this._evaluatedValue = NaN
          }
          if (!isNaN(this._evaluatedValue)) return f && (this._evaluatedValue = {
            value: o.FlexSheet._fromOADate(this._evaluatedValue),
            format: c || d
          }), this._evaluatedValue;
          throw "Bad expression."
        }, i.prototype._updateCellRangeExp = function(e, t, o, n, i, l) {
          var s, r = !1;
          return s = this._leftExpr._updateCellRangeExp(e, t, o, n, i, l), r = r || s, s = this._rightExpr._updateCellRangeExp(e, t, o, n, i, l), r || s
        }, i.prototype._moveCellRangeExp = function(e, t, o, n, i) {
          void 0 === n && (n = !1), void 0 === i && (i = !1);
          var l, s = !1;
          return l = this._leftExpr._moveCellRangeExp(e, t, o, n, i), s = s || l, l = this._rightExpr._moveCellRangeExp(e, t, o, n, i), s || l
        }, i.prototype._updateCellRangeExpForReorderingRows = function(e) {
          var t, o = !1;
          return t = this._leftExpr._updateCellRangeExpForReorderingRows(e), o = o || t, t = this._rightExpr._updateCellRangeExpForReorderingRows(e), o || t
        }, i.prototype._getStringExpression = function() {
          var e;
          switch (this.token.tokenID) {
            case o._TokenID.GT:
              e = ">";
              break;
            case o._TokenID.LT:
              e = "<";
              break;
            case o._TokenID.GE:
              e = ">=";
              break;
            case o._TokenID.LE:
              e = "<=";
              break;
            case o._TokenID.EQ:
              e = "=";
              break;
            case o._TokenID.NE:
              e = "<>";
              break;
            case o._TokenID.ADD:
              e = "+";
              break;
            case o._TokenID.SUB:
              e = "-";
              break;
            case o._TokenID.MUL:
              e = "*";
              break;
            case o._TokenID.DIV:
              e = "/";
              break;
            case o._TokenID.DIVINT:
              e = "\\";
              break;
            case o._TokenID.POWER:
              e = "^";
              break;
            default:
              return ""
          }
          return this._leftExpr._getStringExpression() + e + this._rightExpr._getStringExpression()
        }, i
      }(n);
      o._BinaryExpression = l;
      var s = function(o) {
        function n(e, n, i, l, s, r, a, h, c) {
          void 0 === l && (l = !0), void 0 === s && (s = !1), void 0 === r && (r = !1), void 0 === a && (a = !1), void 0 === h && (h = !1);
          var d = o.call(this) || this;
          return d._cells = new t.CellRange(e.topRow, e.leftCol, e.bottomRow, e.rightCol), d._sheetRef = n, d._flex = i, d._evalutingRange = {}, d._isCellRange = l, d._absRow = s, d._absCol = r, d._absRow2 = a, d._absCol2 = h, d._isWholeRow = c, d
        }
        return __extends(n, o), n.prototype.evaluate = function(e, t, o) {
          var n = this._cells.clone();
          return null == this._evaluatedValue && (null != this._isWholeRow && (this._isWholeRow ? (n.col = 0, n.col2 = o ? o.grid.columns.length - 1 : this._flex.columns.length - 1) : (n.row = 0, n.row2 = o ? o.grid.rows.length - 1 : this._flex.rows.length - 1)), this._evaluatedValue = this._getCellValue(n, o, e, t)), this._evaluatedValue
        }, n.prototype.getValues = function(o, n, i) {
          void 0 === o && (o = !0);
          var l, s, n, r, a, h, c = [],
            d = 0;
          if (!(i = this._getSheet() || i || this._flex.selectedSheet)) return null;
          for (h = this._cells.clone(), null != this._isWholeRow && (this._isWholeRow ? (h.col = 0, h.col2 = i.grid.columns.length - 1) : (h.row = 0, h.row2 = i.grid.rows.length - 1)), r = null == n || isNaN(+n) ? h.leftCol : n, a = null == n || isNaN(+n) ? h.rightCol : n, s = h.topRow; s <= h.bottomRow && s < i.grid.rows.length; s++)
            if (o || !1 !== i.grid.rows[s].isVisible)
              for (n = r; n <= a && n < i.grid.columns.length; n++) l = this._getCellValue(new t.CellRange(s, n), i), e.isPrimitive(l) || (l = l.value), c[d] = l, d++;
          return c
        }, n.prototype.getValuseWithTwoDimensions = function(o, n) {
          void 0 === o && (o = !0);
          var i, l, s, r, a, h = [],
            c = 0,
            d = 0;
          if (!(n = this._getSheet() || n || this._flex.selectedSheet)) return null;
          for (a = this._cells.clone(), null != this._isWholeRow && (this._isWholeRow ? (a.col = 0, a.col2 = n.grid.columns.length - 1) : (a.row = 0, a.row2 = n.grid.rows.length - 1)), s = a.topRow; s <= a.bottomRow && s < n.grid.rows.length; s++)
            if (o || !1 !== n.grid.rows[s].isVisible) {
              for (l = [], d = 0, r = a.leftCol; r <= a.rightCol && r < n.grid.columns.length; r++) o || !1 !== n.grid.columns[r].isVisible ? (i = this._getCellValue(new t.CellRange(s, r), n), e.isPrimitive(i) || (i = i.value), l[d] = i, d++) : d++;
              h[c] = l, c++
            } else c++;
          return h
        }, Object.defineProperty(n.prototype, "cells", {
          get: function() {
            return this._cells
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "sheetRef", {
          get: function() {
            return this._sheetRef
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._getCellValue = function(e, t, o, n) {
          var t, i, l, s, r, a;
          if (!(t = this._getSheet() || t || this._flex.selectedSheet)) return null;
          if (e.isSingleCell ? (l = e.row, s = e.col) : (null != o && o >= e.topRow && o <= e.bottomRow && e.col === e.col2 && (l = o, s = e.col), null != n && n >= e.leftCol && n <= e.rightCol && e.row === e.row2 && (l = e.row, s = n)), null == l || null == s) throw "Invalid Cell Reference.";
          if (i = t.name + ":" + l + "," + s + "-" + l + "," + s, this._evalutingRange[i]) throw "Circular Reference";
          try {
            if (this._flex) return this._evalutingRange[i] = !0, l < t.rowCount && s < t.columnCount ? (r = t.grid.columns[s], a = this._flex.getCellValue(l, s, !1, t), r.dataMap && (a = r.dataMap.getDisplayValue(a)), a) : 0
          } finally {
            delete this._evalutingRange[i]
          }
        }, n.prototype._getSheet = function() {
          var e, t = 0;
          if (!this._sheetRef) return null;
          for (; t < this._flex.sheets.length; t++)
            if ((e = this._flex.sheets[t]).name.toLowerCase() === this._sheetRef) return e;
          throw "Invalid sheet reference"
        }, n.prototype._updateCellRangeExp = function(e, t, o, n, i, l) {
          var s = this._cells,
            r = !1;
          if (this._tableParams && this._tableParams.length > 0) return !1;
          if (e === this._flex.selectedSheetIndex) {
            if (this._sheetRef && this._sheetRef.toLowerCase() !== this._flex.selectedSheet.name.toLowerCase()) return !1
          } else if (!this._sheetRef || this._sheetRef.toLowerCase() !== this._flex.selectedSheet.name.toLowerCase()) return !1;
          if (i) {
            if (l && (s.leftCol > l.rightCol || s.rightCol < l.leftCol)) return !1;
            n ? s.topRow >= t ? (s.row += o, s.row2 += o, r = !0) : s.topRow < t && s.bottomRow >= t && (s.row2 += o, r = !0) : s.topRow > t ? (s.row -= o, s.row2 -= o, r = !0) : s.isSingleCell ? s.row >= t - o + 1 && (s.row = t - o + 1, s.row2 = t - o + 1, r = !0) : s.topRow >= t - o + 1 ? (s.row = t - o + 1, s.row2 -= o, r = !0) : s.topRow < t - o + 1 && s.bottomRow >= t - o + 1 && (s.bottomRow > t ? s.row2 -= o : s.row2 = t - o, r = !0)
          } else {
            if (l && (s.topRow > l.bottomRow || s.bottomRow < l.topRow)) return !1;
            n ? s.leftCol >= t ? (s.col += o, s.col2 += o, r = !0) : s.leftCol < t && s.rightCol >= t && (s.col2 += o, r = !0) : s.leftCol > t ? (s.col -= o, s.col2 -= o, r = !0) : s.isSingleCell ? s.col >= t - o + 1 && (s.col = t - o + 1, s.col2 = t - o + 1, r = !0) : s.leftCol >= t - o + 1 ? (s.col = t - o + 1, s.col2 -= o, r = !0) : s.leftCol < t - o + 1 && s.rightCol >= t - o + 1 && (s.rightCol > t ? s.col2 -= o : s.col2 = t - o, r = !0)
          }
          return r
        }, n.prototype._moveCellRangeExp = function(e, t, o, n, i) {
          void 0 === n && (n = !1), void 0 === i && (i = !1);
          var l, s, r = this._cells,
            a = !1;
          return !(this._tableParams && this._tableParams.length > 0) && (l = o.topRow - t.topRow, s = o.leftCol - t.leftCol, i ? (0 !== l && (r.row += i && this._absRow ? 0 : l, r.row2 += this._isCellRange ? i && this._absRow2 ? 0 : l : i && this._absRow ? 0 : l, a = !0), 0 !== s && (r.col += i && this._absCol ? 0 : s, r.col2 += this._isCellRange ? i && this._absCol2 ? 0 : s : i && this._absCol ? 0 : s, a = !0), a) : (t.contains(r) ? (0 !== l && (r.row += l, r.row2 += l, a = !0), 0 !== s && (r.col += s, r.col2 += s, a = !0)) : t.intersects(r) ? (t.intersectsRow(r) && 0 !== l && (t.topRow <= r.topRow ? l < 0 ? (r.row += l, a = !0) : l > 0 && n && (t.topRow < r.topRow && (r.row -= r.topRow - t.topRow), r.row2 -= t.rowSpan, a = !0) : t.bottomRow >= r.bottomRow ? l > 0 ? (r.row2 += l, a = !0) : l < 0 && n && (t.bottomRow > r.bottomRow && (r.row2 += t.bottomRow - r.bottomRow), r.row += t.rowSpan, a = !0) : n && (l < 0 && o.topRow < r.topRow && (r.row += t.rowSpan), l > 0 && o.bottomRow > r.bottomRow && (r.row2 -= t.rowSpan), a = !0)), t.intersectsColumn(r) && 0 !== s && (t.leftCol <= r.leftCol ? s < 0 ? (r.col += s, a = !0) : s > 0 && n && (t.leftCol < r.leftCol && (r.col -= r.leftCol - t.leftCol), r.col2 -= t.columnSpan, a = !0) : t.rightCol >= r.rightCol ? s > 0 ? (r.col2 += s, a = !0) : s < 0 && n && (t.rightCol > r.rightCol && (r.col2 += t.rightCol - r.rightCol), r.col += t.columnSpan, a = !0) : n && (s < 0 ? r.col += t.columnSpan : r.col2 -= t.columnSpan, a = !0))) : n && (r.topRow >= o.topRow && r.topRow < t.topRow && l < 0 && (r.row += o.rowSpan, r.row2 += o.rowSpan, a = !0), r.topRow > t.bottomRow && r.bottomRow <= o.bottomRow && l > 0 && (r.row -= o.rowSpan, r.row2 -= o.rowSpan, a = !0), r.leftCol >= o.leftCol && r.leftCol < t.leftCol && s < 0 && (r.col += o.columnSpan, r.col2 += o.columnSpan, a = !0), r.leftCol > t.rightCol && r.rightCol <= o.rightCol && s > 0 && (r.col -= o.columnSpan, r.col2 -= o.columnSpan, a = !0)), a))
        }, n.prototype._updateCellRangeExpForReorderingRows = function(e) {
          var t, o = this._cells;
          return (t = this._cells.row + e) < 0 ? t = 0 : t >= this._flex.rows.length && (t = this._flex.rows.length - 1), o.row = t, this._isCellRange && ((t = this._cells.row2 + e) < 0 ? t = 0 : t >= this._flex.rows.length && (t = this._flex.rows.length - 1)), o.row2 = t, !0
        }, n.prototype._updateCellBoundary = function(e, t) {
          var o = this._cells;
          return (!this._sheetRef || this._sheetRef.toLowerCase() === this._flex.selectedSheet.name.toLowerCase()) && (o.row === o.row2 && o.row === e && t === o.col2 + 1 ? (o.col2 += 1, !0) : o.col === o.col2 && o.col === t && e === o.row2 + 1 && (o.row2 += 1, !0))
        }, n.prototype._getStringExpression = function() {
          return this._tableParams && this._tableParams.length > 0 ? this._getTableParamsStringExpression() : (this._sheetRef ? this._sheetRef + "!" : "") + e.xlsx.Workbook.xlsxAddress(this._cells.row, this._cells.col, this._absRow, this._absCol) + (this._isCellRange ? ":" + e.xlsx.Workbook.xlsxAddress(this._cells.row2, this._cells.col2, this._absRow2, this._absCol2) : "")
        }, n.prototype._getTableParamsStringExpression = function() {
          for (var e, t = 0, o = "", n = this._tableParams.length, i = !1; t < n; t++) "#" === (e = this._tableParams[t])[0] ? (o += "[" + e + "]", t < n - 1 && (o += ", ")) : i ? o += ": [" + e + "]" : (o += "[" + e + "]", i = !0);
          return n > 1 && (o = "[" + o + "]"), this._tableName && (o = this._tableName + o), o
        }, n
      }(n);
      o._CellRangeExpression = s;
      var r = function(e) {
        function t(t, o, n, i) {
          void 0 === i && (i = !0);
          var l = e.call(this) || this;
          return l._funcId = t, l._funcDefinition = o, l._params = n, l._needCacheEvaluatedVal = i, l
        }
        return __extends(t, e), t.prototype.evaluate = function(e, t, o) {
          return this._needCacheEvaluatedVal ? (null == this._evaluatedValue && (this._evaluatedValue = this._funcDefinition.func(this._params, o, e, t)), this._evaluatedValue) : this._funcDefinition.func(this._params, o, e, t)
        }, t.prototype._updateCellRangeExp = function(e, t, o, n, i, l) {
          var s, r, a = !1;
          if (this._params && this._params.length > 0)
            for (s = 0; s < this._params.length; s++) r = this._params[s]._updateCellRangeExp(e, t, o, n, i, l), a || (a = a || r);
          return a
        }, t.prototype._moveCellRangeExp = function(e, t, o, n, i) {
          void 0 === n && (n = !1), void 0 === i && (i = !1);
          var l, s, r = !1;
          if (this._params && this._params.length > 0)
            for (l = 0; l < this._params.length; l++) s = this._params[l]._moveCellRangeExp(e, t, o, n, i), r || (r = r || s);
          return r
        }, t.prototype._updateCellRangeExpForReorderingRows = function(e) {
          var t, o, n = !1;
          if (this._params && this._params.length > 0)
            for (t = 0; t < this._params.length; t++) o = this._params[t]._updateCellRangeExpForReorderingRows(e), n || (n = n || o);
          return n
        }, t.prototype._updateCellBoundary = function(e, t) {
          var o;
          return !!(this._params && 1 === this._params.length && (o = this._params[0]) instanceof s) && o._updateCellBoundary(e, t)
        }, t.prototype._getStringExpression = function() {
          return this._funcId + this._parseParamsExpToString()
        }, t.prototype._parseParamsExpToString = function() {
          var e, t, o;
          if (this._params && this._params.length > 0) {
            for (o = "", e = 0; e < this._params.length; e++) t = this._params[e], 0 === e && (o += "("), o += t._getStringExpression(), e < this._params.length - 1 ? o += ", " : o += ")";
            return o
          }
          return "()"
        }, t
      }(n);
      o._FunctionExpression = r
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function() {
        function e(e) {
          this._owner = e, this._sheetIndex = e.selectedSheetIndex
        }
        return Object.defineProperty(e.prototype, "sheetIndex", {
          get: function() {
            return this._sheetIndex
          },
          enumerable: !0,
          configurable: !0
        }), e.prototype.undo = function() {
          throw "This abstract method must be overridden."
        }, e.prototype.redo = function() {
          throw "This abstract method must be overridden."
        }, e.prototype.saveNewState = function() {
          throw "This abstract method must be overridden."
        }, e
      }();
      o._UndoAction = n;
      var i = function(e) {
        function t(t, o) {
          var n = e.call(this, t) || this;
          return n._isPaste = !1, n._selections = o ? [o] : t.selectedSheet.selectionRanges.length > 0 ? t.selectedSheet.selectionRanges.slice() : [t.selection.clone()], n._mergeAction = new c(t), n._cellStyleAction = new h(t), n._saveValues(!0), n
        }
        return __extends(t, e), Object.defineProperty(t.prototype, "isPaste", {
          get: function() {
            return this._isPaste
          },
          enumerable: !0,
          configurable: !0
        }), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype.saveNewState = function() {
          return !!this._saveValues(!1) && (this._mergeAction.saveNewState(), this._cellStyleAction.saveNewState(), this._checkActionState())
        }, t.prototype.markIsPaste = function() {
          this._isPaste = !0
        }, t.prototype.updateForPasting = function(e) {
          var t = this._selections[this._selections.length - 1],
            o = this._owner.getCellData(e.row, e.col, !!this._owner.columns[e.col].dataMap);
          t || (t = this._owner.selection, this._selections = [t]), o = null == o ? "" : o, this._oldValues["r" + e.row + "_c" + e.col] = {
            row: e.row,
            col: e.col,
            value: o
          }, t.row = Math.min(t.topRow, e.topRow), t.row2 = Math.max(t.bottomRow, e.bottomRow), t.col = Math.min(t.leftCol, e.leftCol), t.col2 = Math.max(t.rightCol, e.rightCol)
        }, t.prototype._storeDeletedTables = function(e) {
          null == this._deletedTables && (this._deletedTables = []), this._deletedTables.push(e)
        }, t.prototype._checkActionState = function() {
          var e = this,
            t = !1;
          return Object.keys(e._oldValues).forEach(function(o) {
            var n, i;
            t || (n = e._oldValues[o], i = e._newValues[o], n && i && n.value !== i.value && (t = !0))
          }), t || e._mergeAction._checkActionState() || e._cellStyleAction._checkActionState()
        }, t.prototype._saveValues = function(e) {
          var t, o, n, i, l, s, r = {};
          for (t = 0; t < this._selections.length; t++)
            for (i = (o = this._selections[t]).topRow; i <= o.bottomRow; i++)
              for (l = o.leftCol; l <= o.rightCol; l++) {
                if (!(n = this._owner.columns[l])) return !1;
                s = null == (s = this._owner.getCellData(i, l, !!n.dataMap)) ? "" : s, r["r" + i + "_c" + l] = {
                  row: i,
                  col: l,
                  value: s
                }
              }
          return e ? this._oldValues = r : this._newValues = r, !0
        }, t.prototype._handleUndoRedo = function(e) {
          var t = this;
          t._owner._clearCalcEngine(), t._owner.selectedSheet.selectionRanges.clear(), t._owner.deferUpdate(function() {
            var o, n, i, l, s, r, a, h = e ? t._oldValues : t._newValues;
            if (t._deletedTables && t._deletedTables.length > 0)
              for (o = 0; o < t._deletedTables.length; o++) n = t._deletedTables[o], e ? t._owner.selectedSheet.tables.push(n) : t._owner.selectedSheet.tables.remove(n);
            for (i = 0; i < t._selections.length; i++) l = t._selections[i], t._owner.selectedSheet.selectionRanges.push(l);
            if (Object.keys(h).forEach(function(e) {
              var o = h[e];
              t._owner.setCellData(o.row, o.col, o.value)
            }), t._affectedFormulas && (s = e ? t._affectedFormulas.oldFormulas : t._affectedFormulas.newFormulas), s && s.length > 0)
              for (a = 0; a < s.length; a++) r = s[a], t._owner.setCellData(r.point.x, r.point.y, r.formula);
            e ? (t._mergeAction.undo(), t._cellStyleAction.undo()) : (t._mergeAction.redo(), t._cellStyleAction.redo()), t._owner.refresh(!1)
          })
        }, t
      }(n);
      o._EditAction = i;
      var l = function(e) {
        function t(t, o, n) {
          var i = e.call(this, t) || this;
          return i._panel = o, i._colIndex = n, i._oldColWidth = o.columns[n].width, i
        }
        return __extends(t, e), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype.saveNewState = function() {
          return this._newColWidth = this._panel.columns[this._colIndex].width, this._oldColWidth !== this._newColWidth
        }, t.prototype._handleUndoRedo = function(e) {
          var t = this._panel.columns[this._colIndex];
          t && (t.width = e ? this._oldColWidth : this._newColWidth)
        }, t
      }(n);
      o._ColumnResizeAction = l;
      var s = function(e) {
        function t(t, o, n) {
          var i = e.call(this, t) || this;
          return i._panel = o, i._rowIndex = n, i._oldRowHeight = o.rows[n].height, i
        }
        return __extends(t, e), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype.saveNewState = function() {
          return this._newRowHeight = this._panel.rows[this._rowIndex].height, this._oldRowHeight !== this._newRowHeight
        }, t.prototype._handleUndoRedo = function(e) {
          var t = this._panel.rows[this._rowIndex];
          t && (t.height = e ? this._oldRowHeight : this._newRowHeight)
        }, t
      }(n);
      o._RowResizeAction = s;
      var r = function(e) {
        function t(t, o, n, i) {
          var l = e.call(this, t) || this;
          return l._columnIndex = o, l._count = n, l._isAdding = i, l._saveValues(!0), l
        }
        return __extends(t, e), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype.saveNewState = function() {
          return this._saveValues(!1), !0
        }, t.prototype._saveValues = function(e) {
          var t, o, n, i, l = [],
            s = [];
          if (null == this._isAdding)
            for (t = 0; t < this._owner.columns.length; t++) l.push(this._owner.columns[t]);
          else if (e && !this._isAdding || !e && this._isAdding)
            for (t = this._columnIndex; t < this._columnIndex + this._count && t < this._owner.columns.length; t++) l.push(this._owner.columns[t]);
          if (this._owner.selectedSheet.tables && this._owner.selectedSheet.tables.length > 0)
            for (o = 0; o < this._owner.selectedSheet.tables.length; o++)(n = this._owner.selectedSheet.tables[o]) && n.sheet.name === this._owner.selectedSheet.name && s.push({
              name: n.name,
              range: n.getRange(),
              columns: n.getColumns()
            });
          i = {
            columns: l,
            sortList: this._owner.sortManager._cloneSortList(this._owner.sortManager._committedList),
            styledCells: this._owner.selectedSheet ? this._owner._cloneObject(this._owner.selectedSheet._styledCells) : null,
            mergedCells: this._owner.selectedSheet ? this._owner.selectedSheet._cloneMergedCells() : null,
            tableRanges: s,
            selection: this._owner.selection,
            filterDef: this._owner.selectedSheet._filterDefinition
          }, e ? this._oldValue = i : this._newValue = i
        }, t.prototype._handleUndoRedo = function(e) {
          var t, o, n, i, l, s, r, a, h, c, d, u, _ = this,
            g = e ? _._oldValue : _._newValue;
          _._owner.selectedSheet && (_._owner.deferUpdate(function() {
            var f;
            if (_._owner._isUndoing = !0, _._owner._clearCalcEngine(), _._owner.finishEditing(), null == _._isAdding && (_._owner.columns.clear(), _._columnIndex = 0), _._owner.selectedSheet._styledCells = null, _._owner.selectedSheet._mergedRanges.length = 0, _._owner.columns.beginUpdate(), g.columns && g.columns.length > 0)
              for (t = 0; t < g.columns.length; t++)(o = g.columns[t]) && o.isVisible && (_._owner.columns.insert(_._columnIndex + t, o), _._owner.itemsSource && (f = _._owner.allowAddNew && _._owner.newRowAtTop ? _._owner.rows[1] : _._owner.rows[0]) && (f._ubv || (f._ubv = {}), f._ubv[o._hash] = o.header));
            else
              for (t = _._columnIndex + _._count - 1; t >= _._columnIndex; t--)(o = _._owner.columns[t]) && o.isVisible && _._owner.columns.removeAt(t);
            for (_._owner.columns.endUpdate(), _._owner.selectedSheet._styledCells = _._owner._cloneObject(g.styledCells), u = 0; u < g.mergedCells.length; u++) _._owner.selectedSheet._mergedRanges[u] = g.mergedCells[u];
            for (i = 0; i < g.tableRanges.length; i++) l = g.tableRanges[i], (s = _._owner._getTable(l.name)) && s._setTableRange(l.range, l.columns);
            if (_._affectedFormulas && (a = e ? _._affectedFormulas.oldFormulas : _._affectedFormulas.newFormulas), a && a.length > 0)
              for (n = 0; n < a.length; n++) null != (r = a[n]).point ? r.sheet.name === _._owner.selectedSheet.name ? _._owner.setCellData(r.point.x, r.point.y, r.formula) : r.sheet.grid.setCellData(r.point.x, r.point.y, r.formula) : r.row._ubv[r.column._hash] = r.formula;
            if (_._deletedTables && _._deletedTables.length > 0)
              for (i = 0; i < _._deletedTables.length; i++) s = _._deletedTables[i], e ? _._owner.selectedSheet.tables.push(s) : _._owner.selectedSheet.tables.remove(s);
            if (_._affectedDefinedNameVals && (h = e ? _._affectedDefinedNameVals.oldDefinedNameVals : _._affectedDefinedNameVals.newDefinedNameVals), h && h.length > 0)
              for (n = 0; n < h.length; n++) c = h[n], (d = _._owner._getDefinedNameIndexByName(c.name)) > -1 && (_._owner.definedNames[d].value = c.value);
            _._owner.selectedSheet.grid.wj_sheetInfo.styledCells = _._owner.selectedSheet._styledCells, _._owner.selectedSheet.grid.wj_sheetInfo.mergedRanges = _._owner.selectedSheet._mergedRanges, _._owner._isUndoing = !1
          }), _._owner._copyColumnsToSelectedSheet(), _._owner.sortManager.sortDescriptions.sourceCollection = g.sortList.slice(), _._owner.selectedSheet._filterDefinition = g.filterDef, _._owner.sortManager.commitSort(!1), _._owner.selection = g.selection)
        }, t
      }(n);
      o._ColumnsChangedAction = r;
      var a = function(t) {
        function o(e, o, n, i) {
          var l = t.call(this, e) || this;
          return l._rowIndex = o, l._count = n, l._isAdding = i, l._saveValues(!0), l
        }
        return __extends(o, t), o.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, o.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, o.prototype.saveNewState = function() {
          return this._saveValues(!1), !0
        }, o.prototype._saveValues = function(t) {
          var o, n, i, l, s, r, a, h, c = [],
            d = [],
            u = [];
          if (null == this._isAdding)
            if (this._owner.itemsSource)
              for (r = this._owner.itemsSource instanceof e.collections.CollectionView ? this._owner.itemsSource.sourceCollection.slice() : this._owner.itemsSource.slice(), i = 0; i < this._owner.columns.length; i++) d.push(this._owner.columns[i]);
            else
              for (o = 0; o < this._owner.rows.length; o++) c.push(this._owner.rows[o]);
          else {
            if (t && !this._isAdding || !t && this._isAdding)
              for (o = this._rowIndex; o < this._rowIndex + this._count && o < this._owner.rows.length; o++)(n = this._owner.rows[o]).isVisible || this._isAdding ? c.push(n) : c.push(null);
            this._owner.collectionView && this._owner.collectionView.sortDescriptions.length > 0 && (a = this._owner.collectionView._view.slice())
          }
          if (this._owner.selectedSheet.tables && this._owner.selectedSheet.tables.length > 0)
            for (l = 0; l < this._owner.selectedSheet.tables.length; l++)(s = this._owner.selectedSheet.tables[l]) && s.sheet.name === this._owner.selectedSheet.name && u.push({
              name: s.name,
              range: s.getRange(),
              setting: {
                showHeaderRow: s.showHeaderRow,
                showTotalRow: s.showTotalRow
              }
            });
          h = {
            rows: c,
            columns: d,
            itemsSource: r,
            styledCells: this._owner.selectedSheet ? this._owner._cloneObject(this._owner.selectedSheet._styledCells) : null,
            mergedCells: this._owner.selectedSheet ? this._owner.selectedSheet._cloneMergedCells() : null,
            tableSettings: u,
            selection: this._owner.selection,
            dataView: a,
            scrollPosition: this._owner.scrollPosition
          }, t ? this._oldValue = h : this._newValue = h
        }, o.prototype._handleUndoRedo = function(t) {
          var o, n, i, l, s, r, a, h, c, d, u, _, g, f, p, w, m, C = this,
            b = this,
            y = !!b._owner.itemsSource,
            S = t ? b._oldValue : b._newValue;
          b._owner.selectedSheet && (b._owner.deferUpdate(function() {
            if (b._owner._isUndoing = !0, b._owner._clearCalcEngine(), b._owner.finishEditing(), null == b._isAdding && (b._owner.itemsSource && b._owner.columns.clear(), b._owner.rows.clear(), b._rowIndex = 0), b._owner.selectedSheet._styledCells = null, b._owner.selectedSheet._mergedRanges.length = 0, null == C._isAdding && y) {
              for (p = b._owner.autoGenerateColumns, b._owner.autoGenerateColumns = !1, b._owner.collectionView.beginUpdate(), b._owner.itemsSource instanceof e.collections.CollectionView ? (f = [0, b._owner.itemsSource.sourceCollection.length].concat(S.itemsSource), Array.prototype.splice.apply(b._owner.itemsSource.sourceCollection, f)) : b._owner.itemsSource = S.itemsSource.slice(), b._owner.columns.beginUpdate(), i = 0; i < S.columns.length; i++) b._owner.columns.push(S.columns[i]);
              b._owner.columns.endUpdate(), b._owner.collectionView.endUpdate(), b._owner.autoGenerateColumns = p
            } else {
              if (y ? (p = b._owner.autoGenerateColumns, b._owner.autoGenerateColumns = !1, w = b._rowIndex - (b._owner.allowAddNew && b._owner.newRowAtTop ? 2 : 1), b._owner.collectionView.beginUpdate()) : b._owner.rows.beginUpdate(), S.rows && S.rows.length > 0)
                for (o = 0; o < S.rows.length; o++)(n = S.rows[o]) && (y && n.dataItem ? (m = null != n.dataItem._itemIdx ? n.dataItem._itemIdx : w + o, b._owner.itemsSource instanceof e.collections.CollectionView ? (b._owner._updateItemIndexForInsertingRow(b._owner.itemsSource.sourceCollection, m, 1), b._owner.itemsSource.sourceCollection.splice(m, 0, n.dataItem)) : (b._owner._updateItemIndexForInsertingRow(b._owner.itemsSource, m, 1), b._owner.itemsSource.splice(m, 0, n.dataItem))) : b._owner.rows.insert(b._rowIndex + o, n));
              else
                for (o = b._rowIndex + b._count - 1; o >= b._rowIndex; o--)(n = b._owner.rows[o]) && (y && n.dataItem ? (m = null != n.dataItem._itemIdx ? n.dataItem._itemIdx : o - (b._owner.allowAddNew && b._owner.newRowAtTop ? 2 : 1), b._owner.itemsSource instanceof e.collections.CollectionView ? (b._owner.itemsSource.sourceCollection.splice(m, 1), b._owner._updateItemIndexForRemovingRow(b._owner.itemsSource.sourceCollection, m)) : (b._owner.itemsSource.splice(m, 1), b._owner._updateItemIndexForRemovingRow(b._owner.itemsSource, m))) : b._owner.rows.removeAt(o));
              y ? (b._owner._lastCount = b._owner.collectionView.itemCount, b._owner.collectionView.endUpdate(), S.dataView && S.dataView.length > 0 && (b._owner.collectionView._view = S.dataView, b._owner.collectionView._pgView = b._owner.collectionView._getPageView(), b._owner.itemsSource instanceof e.collections.CollectionView || (b._owner.selectedSheet.grid.collectionView._view = S.dataView, b._owner.selectedSheet.grid.collectionView._pgView = b._owner.selectedSheet.grid.collectionView._getPageView()), b._owner._bindGrid(!1), b._owner.selectedSheet.grid._bindGrid(!1)), b._owner.autoGenerateColumns = p) : b._owner.rows.endUpdate()
            }
            for (b._owner.selectedSheet._styledCells = b._owner._cloneObject(S.styledCells), g = 0; g < S.mergedCells.length; g++) b._owner.selectedSheet._mergedRanges[g] = S.mergedCells[g];
            for (s = 0; s < S.tableSettings.length; s++) r = S.tableSettings[s], (a = b._owner._getTable(r.name)) && (a._showHeaderRow = r.setting.showHeaderRow, a._showTotalRow = r.setting.showTotalRow, a._setTableRange(r.range));
            if (b._affectedFormulas && (c = t ? b._affectedFormulas.oldFormulas : b._affectedFormulas.newFormulas), c && c.length > 0)
              for (l = 0; l < c.length; l++) null != (h = c[l]).point ? h.sheet.name === b._owner.selectedSheet.name ? b._owner.setCellData(h.point.x, h.point.y, h.formula) : h.sheet.grid.setCellData(h.point.x, h.point.y, h.formula) : h.row._ubv[h.column._hash] = h.formula;
            if (b._deletedTables && b._deletedTables.length > 0)
              for (s = 0; s < b._deletedTables.length; s++) a = b._deletedTables[s], t ? b._owner.selectedSheet.tables.push(a) : b._owner.selectedSheet.tables.remove(a);
            if (b._affectedDefinedNameVals && (d = t ? b._affectedDefinedNameVals.oldDefinedNameVals : b._affectedDefinedNameVals.newDefinedNameVals), d && d.length > 0)
              for (l = 0; l < d.length; l++) u = d[l], (_ = b._owner._getDefinedNameIndexByName(u.name)) > -1 && (b._owner.definedNames[_].value = u.value);
            b._owner.selectedSheet.grid.wj_sheetInfo.styledCells = b._owner.selectedSheet._styledCells, b._owner.selectedSheet.grid.wj_sheetInfo.mergedRanges = b._owner.selectedSheet._mergedRanges, b._owner.selection = S.selection, b._owner.scrollPosition = S.scrollPosition, b._owner._isUndoing = !1
          }), b._owner._copyRowsToSelectedSheet())
        }, o
      }(n);
      o._RowsChangedAction = a;
      var h = function(e) {
        function t(t, o) {
          var n = e.call(this, t) || this;
          return n._oldStyledCells = o ? t._cloneObject(o) : t.selectedSheet ? t._cloneObject(t.selectedSheet._styledCells) : null, n
        }
        return __extends(t, e), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype.saveNewState = function() {
          return this._newStyledCells = this._owner.selectedSheet ? this._owner._cloneObject(this._owner.selectedSheet._styledCells) : null, !0
        }, t.prototype._checkActionState = function() {
          return null != this._oldStyledCells && null == this._newStyledCells || null == this._oldStyledCells && null != this._newStyledCells || null != this._oldStyledCells && null != this._newStyledCells && this._oldStyledCells.length !== this._newStyledCells.length
        }, t.prototype._handleUndoRedo = function(e) {
          var t;
          this._owner.selectedSheet && (t = e ? this._oldStyledCells : this._newStyledCells, this._owner.selectedSheet._styledCells = this._owner._cloneObject(t), this._owner.selectedSheet.grid.wj_sheetInfo.styledCells = this._owner.selectedSheet._styledCells, this._owner.refresh(!1))
        }, t
      }(n);
      o._CellStyleAction = h;
      var c = function(e) {
        function t(t) {
          var o = e.call(this, t) || this;
          return o._oldMergedCells = t.selectedSheet ? t.selectedSheet._cloneMergedCells() : null, o
        }
        return __extends(t, e), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype.saveNewState = function() {
          return this._newMergedCells = this._owner.selectedSheet ? this._owner.selectedSheet._cloneMergedCells() : null, !0
        }, t.prototype._handleUndoRedo = function(e) {
          var t, o;
          if (this._owner.selectedSheet) {
            if (t = e ? this._oldMergedCells : this._newMergedCells, this._owner._clearCalcEngine(), t)
              for (this._owner.selectedSheet._mergedRanges.length = 0, o = 0; o < t.length; o++) this._owner.selectedSheet._mergedRanges[o] = t[o];
            this._owner.selectedSheet.grid.wj_sheetInfo.mergedRanges = this._owner.selectedSheet._mergedRanges, this._owner.refresh(!0)
          }
        }, t.prototype._checkActionState = function() {
          var e, t, o;
          if (null != this._oldMergedCells && null == this._newMergedCells || null == this._oldMergedCells && null != this._newMergedCells) return !0;
          if (null != this._oldMergedCells && null != this._newMergedCells && this._oldMergedCells.length !== this._newMergedCells.length) return !0;
          if (null != this._oldMergedCells && null != this._newMergedCells && this._oldMergedCells.length === this._newMergedCells.length)
            for (e = 0; e < this._oldMergedCells.length; e++)
              if (o = this._oldMergedCells[e], t = this._oldMergedCells[e], !o.equals(t)) return !0;
          return !1
        }, t
      }(n);
      o._CellMergeAction = c;
      var d = function(t) {
        function o(e) {
          var o = t.call(this, e) || this;
          return o._saveValues(!0), o
        }
        return __extends(o, t), o.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, o.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, o.prototype.saveNewState = function() {
          return this._saveValues(!1), !0
        }, o.prototype._saveValues = function(e) {
          var t, o, n, i, l, s, r, a, h, c = [];
          for (o = 0; o < this._owner.columns.length; o++) c.push(this._owner.columns[o]);
          if (this._owner.itemsSource) {
            i = this._owner.allowAddNew && this._owner.newRowAtTop ? 2 : 1, l = [];
            for (var d = 0; d < this._owner.rows.length - i; d++)(r = (s = this._owner.rows[d + i]).dataItem) && (l[null != r._itemIdx ? r._itemIdx : d] = s.visible);
            e && (h = this._owner.collectionView._view.slice())
          } else
            for (n = [], t = 0; t < this._owner.rows.length; t++) n.push(this._owner.rows[t]);
          a = {
            sortList: this._owner.sortManager._committedList.slice(),
            rows: n,
            columns: c,
            selection: this._owner.selection.clone(),
            formulas: this._owner._scanFormulas(),
            styledCells: this._owner.selectedSheet ? this._owner._cloneObject(this._owner.selectedSheet._styledCells) : null,
            rowsVisible: l,
            dataView: h
          }, e ? this._oldValue = a : this._newValue = a
        }, o.prototype._handleUndoRedo = function(t) {
          var o, n, i, l, s = this,
            r = t ? s._oldValue : s._newValue,
            a = !!s._owner.itemsSource,
            h = this._owner.itemsSource instanceof e.collections.CollectionView;
          s._owner.selectedSheet && (s._owner._isUndoing = !0, s._owner.deferUpdate(function() {
            if (s._owner._clearCalcEngine(), s._owner.sortManager.sortDescriptions.sourceCollection = r.sortList.slice(), s._owner.sortManager.commitSort(!1), s._owner.selectedSheet._styledCells = s._owner._cloneObject(r.styledCells), a) r.dataView && r.dataView.length > 0 && (s._owner.collectionView._view = r.dataView, s._owner.collectionView._pgView = s._owner.collectionView._getPageView(), h || (s._owner.selectedSheet.grid.collectionView._view = r.dataView, s._owner.selectedSheet.grid.collectionView._pgView = s._owner.selectedSheet.grid.collectionView._getPageView()), s._owner._bindGrid(!1), s._owner.selectedSheet.grid._bindGrid(!1));
            else {
              for (s._owner.rows.clear(), s._owner.selectedSheet.grid.rows.clear(), o = 0; o < r.rows.length; o++) i = r.rows[o], s._owner.selectedSheet.grid.rows.push(i), s._owner.rows.push(i);
              for (s._owner.columns.clear(), n = 0; n < r.columns.length; n++) l = r.columns[n], s._owner.columns.push(l);
              s._owner._resetFormulas(r.formulas)
            }
            s._owner._isUndoing = !1
          }))
        }, o
      }(n);
      o._SortColumnAction = d;
      var u = function(t) {
        function o(e, o, n, i) {
          var l = t.call(this, e) || this;
          return e.selectedSheet ? (0 === o.topRow && o.bottomRow === e.rows.length - 1 ? l._isDraggingColumns = !0 : l._isDraggingColumns = !1, l._isCopyCells = i, l._dragRange = o, l._dropRange = n, l._saveValues(!0), l) : l
        }
        return __extends(o, t), o.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, o.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, o.prototype.saveNewState = function() {
          return !!this._owner.selectedSheet && (!!this._dropRange && (this._saveValues(!1), !0))
        }, o.prototype._saveValues = function(e) {
          var t, o, n, i, l, s, r, a, h, c, d, u;
          for (d = [], u = {}, t = this._dropRange.topRow; t <= this._dropRange.bottomRow; t++)
            for (o = this._dropRange.leftCol; o <= this._dropRange.rightCol; o++) this._isDraggingColumns && (u[o] || (u[o] = {
              dataType: this._owner.columns[o].dataType,
              align: this._owner.columns[o].align,
              format: this._owner.columns[o].format
            })), n = t * this._owner.columns.length + o, l = this._owner.selectedSheet._styledCells[n] ? this._owner._cloneObject(this._owner.selectedSheet._styledCells[n]) : null, i = this._owner.getCellData(t, o, !1), d.push({
              rowIndex: t,
              columnIndex: o,
              cellContent: i,
              cellStyle: l
            });
          if (e ? (this._oldDroppingCells = d, this._oldDroppingColumnSetting = u) : (this._newDroppingCells = d, this._newDroppingColumnSetting = u), this._isCopyCells) {
            if (this._isDraggingColumns)
              if (e)
                for (t = this._dragRange.topRow; t <= this._dragRange.bottomRow; t++)
                  for (o = this._dragRange.leftCol; o <= this._dragRange.rightCol; o++)(a = this._owner.selectedSheet.findTable(t, o)) && (h = a.getRange(), c = a.getColumns(), a.showHeaderRow && t === h.topRow && (this._draggingTableColumns || (this._draggingTableColumns = []), this._draggingTableColumns.push({
                    rowIndex: t,
                    columnIndex: o,
                    cellContent: c[o - h.leftCol].name
                  })));
              else if (this._draggingTableColumns && this._draggingTableColumns.length > 0 && this._draggingTableColumns && this._draggingTableColumns.length > 0)
                for (s = 0; s < this._draggingTableColumns.length; s++) r = this._draggingTableColumns[s], (a = this._owner.selectedSheet.findTable(r.rowIndex, r.columnIndex)) && (h = a.getRange(), c = a.getColumns(), a.showHeaderRow && r.rowIndex === h.topRow && (r.updatedCellContent = c[r.columnIndex - h.leftCol].name))
          } else if (e)
            for (this._draggingCells = [], this._draggingColumnSetting = {}, t = this._dragRange.topRow; t <= this._dragRange.bottomRow; t++)
              for (o = this._dragRange.leftCol; o <= this._dragRange.rightCol; o++) this._isDraggingColumns && (this._draggingColumnSetting[o] || (this._draggingColumnSetting[o] = {
                dataType: this._owner.columns[o].dataType,
                align: this._owner.columns[o].align,
                format: this._owner.columns[o].format
              })), n = t * this._owner.columns.length + o, l = this._owner.selectedSheet._styledCells[n] ? this._owner._cloneObject(this._owner.selectedSheet._styledCells[n]) : null, i = this._owner.getCellData(t, o, !1), this._draggingCells.push({
                rowIndex: t,
                columnIndex: o,
                cellContent: i,
                cellStyle: l
              });
          else if (this._isDraggingColumns && this._draggingCells && this._draggingCells.length > 0)
            for (s = 0; s < this._draggingCells.length; s++) r = this._draggingCells[s], (a = this._owner.selectedSheet.findTable(r.rowIndex, r.columnIndex)) && (h = a.getRange(), c = a.getColumns(), a.showHeaderRow && r.rowIndex === h.topRow && (r.updatedCellContent = c[r.columnIndex - h.leftCol].name))
        }, o.prototype._handleUndoRedo = function(t) {
          var o, n, i, l, s, r, a, h, c, d, u = this,
            _ = t ? u._oldDroppingCells : u._newDroppingCells,
            g = t ? u._oldDroppingColumnSetting : u._newDroppingColumnSetting;
          u._owner.selectedSheet && (u._owner._clearCalcEngine(), u._owner.deferUpdate(function() {
            var f, p, w;
            if (u._affectedFormulas && (r = t ? u._affectedFormulas.oldFormulas : u._affectedFormulas.newFormulas), r && r.length > 0)
              for (o = 0; o < r.length; o++)(a = r[o]).sheet.name === u._owner.selectedSheet.name ? u._owner.setCellData(a.point.x, a.point.y, a.formula) : a.sheet.grid.setCellData(a.point.x, a.point.y, a.formula);
            if (u._affectedDefinedNameVals && (h = t ? u._affectedDefinedNameVals.oldDefinedNameVals : u._affectedDefinedNameVals.newDefinedNameVals), h && h.length > 0)
              for (o = 0; o < h.length; o++) c = h[o], (d = u._owner._getDefinedNameIndexByName(c.name)) > -1 && (u._owner.definedNames[d].value = c.value);
            for (o = 0; o < _.length; o++) n = _[o], u._owner.setCellData(n.rowIndex, n.columnIndex, n.cellContent), (f = u._owner.selectedSheet.findTable(n.rowIndex, n.columnIndex)) && (p = f.getRange(), w = f.getColumns(), f.showHeaderRow && n.rowIndex === p.topRow && (w[n.columnIndex - p.leftCol].name = n.cellContent)), i = n.rowIndex * u._owner.columns.length + n.columnIndex, n.cellStyle ? u._owner.selectedSheet._styledCells[i] = n.cellStyle : delete u._owner.selectedSheet._styledCells[i];
            if (u._isDraggingColumns && g && Object.keys(g).forEach(function(t) {
              u._owner.columns[+t].dataType = g[+t].dataType ? g[+t].dataType : e.DataType.Object, u._owner.columns[+t].align = g[+t].align, u._owner.columns[+t].format = g[+t].format
            }), u._isCopyCells) {
              if (u._draggingTableColumns && u._draggingTableColumns.length > 0)
                for (o = 0; o < u._draggingTableColumns.length; o++) n = u._draggingTableColumns[o], (f = u._owner.selectedSheet.findTable(n.rowIndex, n.columnIndex)) && (p = f.getRange(), w = f.getColumns(), f.showHeaderRow && n.rowIndex === p.topRow && (u._owner.setCellData(n.rowIndex, n.columnIndex, t ? n.cellContent : n.updatedCellContent), w[n.columnIndex - p.leftCol].name = t ? n.cellContent : n.updatedCellContent))
            } else {
              for (o = 0; o < u._draggingCells.length; o++) n = u._draggingCells[o], (f = u._owner.selectedSheet.findTable(n.rowIndex, n.columnIndex)) ? (p = f.getRange(), w = f.getColumns(), f.showHeaderRow && n.rowIndex === p.topRow ? (u._owner.setCellData(n.rowIndex, n.columnIndex, t ? n.cellContent : n.updatedCellContent), w[n.columnIndex - p.leftCol].name = t ? n.cellContent : n.updatedCellContent) : u._owner.setCellData(n.rowIndex, n.columnIndex, t ? n.cellContent : null)) : u._owner.setCellData(n.rowIndex, n.columnIndex, t ? n.cellContent : null), i = n.rowIndex * u._owner.columns.length + n.columnIndex, t ? n.cellStyle && (u._owner.selectedSheet._styledCells[i] = n.cellStyle) : u._owner.selectedSheet._styledCells[i] && delete u._owner.selectedSheet._styledCells[i];
              if (u._isDraggingColumns && u._draggingColumnSetting && Object.keys(u._draggingColumnSetting).forEach(function(o) {
                u._owner.columns[+o].dataType = t && u._draggingColumnSetting[+o].dataType ? u._draggingColumnSetting[+o].dataType : e.DataType.Object, u._owner.columns[+o].align = t ? u._draggingColumnSetting[+o].align : null, u._owner.columns[+o].format = t ? u._draggingColumnSetting[+o].format : null
              }), t) {
                if (u._isDraggingColumns)
                  if (u._dragRange.leftCol < u._dropRange.leftCol)
                    for (s = u._dragRange.leftCol, l = u._dropRange.leftCol; l <= u._dropRange.rightCol; l++) u._owner._updateColumnFiler(l, s), s++;
                  else
                    for (s = u._dragRange.rightCol, l = u._dropRange.rightCol; l >= u._dropRange.leftCol; l--) u._owner._updateColumnFiler(l, s), s--
              } else if (u._isDraggingColumns)
                if (u._dragRange.leftCol > u._dropRange.leftCol)
                  for (s = u._dropRange.leftCol, l = u._dragRange.leftCol; l <= u._dragRange.rightCol; l++) u._owner._updateColumnFiler(l, s), s++;
                else
                  for (s = u._dropRange.rightCol, l = u._dragRange.rightCol; l >= u._dragRange.leftCol; l--) u._owner._updateColumnFiler(l, s), s--
            }
          }))
        }, o
      }(n);
      o._MoveCellsAction = u;
      var _ = function(e) {
        function o(o) {
          var n = e.call(this, o) || this;
          return n._oldValues = [], n._mergeAction = new c(o), n._celltyleAction = new h(o), n._cutSheet = o._copiedSheet, n._selection = o.selection, n._cutSelection = o.selectionMode === t.SelectionMode.ListBox ? o._getSelectionForListBoxMode(n._cutSheet.grid) : o._copiedRanges[0], n._saveCutValues(!0), n
        }
        return __extends(o, e), o.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, o.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, o.prototype.saveNewState = function() {
          var e, t, o;
          this._cutSheet === this._owner.selectedSheet ? this._owner : this._cutSheet.grid;
          for (this._saveCutValues(!1), this._newValues = [], e = this._selection.topRow; e <= this._selection.bottomRow; e++)
            for (t = this._selection.leftCol; t <= this._selection.rightCol; t++) {
              if (!this._owner.columns[t]) return !1;
              o = null == (o = this._owner.getCellData(e, t, !!this._owner.columns[t].dataMap)) ? "" : o, this._newValues.push({
                row: e,
                col: t,
                value: o
              })
            }
          return this._mergeAction.saveNewState(), this._celltyleAction.saveNewState(), !0
        }, o.prototype.updateForPasting = function(e) {
          var t = this._owner.getCellData(e.row, e.col, !!this._owner.columns[e.col].dataMap);
          t = null == t ? "" : t, this._oldValues.push({
            row: e.row,
            col: e.col,
            value: t
          }), this._selection.row = Math.min(this._selection.topRow, e.topRow), this._selection.row2 = Math.max(this._selection.bottomRow, e.bottomRow), this._selection.col = Math.min(this._selection.leftCol, e.leftCol), this._selection.col2 = Math.max(this._selection.rightCol, e.rightCol)
        }, o.prototype._saveCutValues = function(e) {
          var t, o, n, i = this._cutSheet === this._owner.selectedSheet ? this._owner : this._cutSheet.grid,
            l = [];
          for (t = this._cutSelection.topRow; t <= this._cutSelection.bottomRow; t++)
            if (null != i.rows[t])
              for (o = this._cutSelection.leftCol; o <= this._cutSelection.rightCol; o++) n = null == (n = i.getCellData(t, o, !!i.columns[o].dataMap)) ? "" : n, l.push({
                row: t,
                col: o,
                value: n
              });
          e ? this._oldCutValues = l : this._newCutValues = l
        }, o.prototype._handleUndoRedo = function(e) {
          var t = this,
            o = e ? t._oldCutValues : t._newCutValues,
            n = e ? t._oldValues : t._newValues;
          t._owner._clearCalcEngine(), t._owner.selectedSheet.selectionRanges.clear(), t._owner.deferUpdate(function() {
            var i, l, s = t._cutSheet === t._owner.selectedSheet ? t._owner : t._cutSheet.grid;
            for (t._owner.selectedSheet.selectionRanges.push(t._selection), i = 0; i < o.length; i++) l = o[i], s.setCellData(l.row, l.col, l.value);
            for (i = 0; i < n.length; i++) l = n[i], t._owner.setCellData(l.row, l.col, l.value);
            e ? (t._mergeAction.undo(), t._celltyleAction.undo()) : (t._mergeAction.redo(), t._celltyleAction.redo()), t._owner.refresh(!1)
          })
        }, o
      }(n);
      o._CutAction = _;
      var g = function(e) {
        function t(t, o) {
          var n = e.call(this, t) || this;
          return n._table = o, n._saveValues(!0), n
        }
        return __extends(t, e), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype.saveNewState = function() {
          return this._saveValues(!1), !0
        }, t.prototype._saveValues = function(e) {
          var t = {
            name: this._table.name,
            style: this._table.style,
            showHeaderRow: this._table.showHeaderRow,
            showTotalRow: this._table.showTotalRow,
            showbandedRows: this._table.showBandedRows,
            showBandedColumns: this._table.showBandedColumns,
            alterFirstColumn: this._table.alterFirstColumn,
            alterLastColumn: this._table.alterLastColumn
          };
          e ? this._oldTableSetting = t : this._newTableSetting = t
        }, t.prototype._handleUndoRedo = function(e) {
          var t = e ? this._oldTableSetting : this._newTableSetting;
          this._owner.beginUpdate(), this._owner._isUndoing = !0, this._table.name = t.name, this._table.style = t.style, this._table.showHeaderRow = t.showHeaderRow, this._table.showTotalRow = t.showTotalRow, this._table.showBandedRows = t.showbandedRows, this._table.showBandedColumns = t.showBandedColumns, this._table.alterFirstColumn = t.alterFirstColumn, this._table.alterLastColumn = t.alterLastColumn, this._owner._isUndoing = !1, this._owner.endUpdate()
        }, t
      }(n);
      o._TableSettingAction = g;
      var f = function(e) {
        function t(t, o) {
          var n = e.call(this, t) || this;
          return n._addedTable = o, o.showHeaderRow && (n._orgHeaderCellsContent = o._orgHeaderCellsContent.slice()), n
        }
        return __extends(t, e), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype._handleUndoRedo = function(e) {
          var t, o, n;
          if (e ? this._owner.selectedSheet.tables.remove(this._addedTable) : this._owner.selectedSheet.tables.push(this._addedTable), this._addedTable.showHeaderRow)
            for (o = this._addedTable.getRange(), n = this._addedTable.getColumns(), t = 0; t < o.columnSpan; t++) this._owner.setCellData(o.topRow, o.leftCol + t, e ? this._orgHeaderCellsContent[t] : n[t].name);
          this._owner.refresh()
        }, t
      }(n);
      o._TableAction = f;
      var p = function(e) {
        function t(t) {
          var o = e.call(this, t) || this;
          return o._oldFilterDefinition = t.filter.filterDefinition, o._oldRowsVisible = o._getRowsVisible(), o
        }
        return __extends(t, e), t.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, t.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, t.prototype.saveNewState = function() {
          return this._newFilterDefinition = this._owner.filter.filterDefinition, this._newRowsVisible = this._getRowsVisible(), !0
        }, t.prototype._handleUndoRedo = function(e) {
          var t = e ? this._oldFilterDefinition : this._newFilterDefinition;
          this._owner.filter.filterDefinition !== t ? (this._owner._isUndoing = !0, this._owner.selectedSheet._filterDefinition = t, this._owner.selectedSheet._applyFilterSetting(), this._owner._isUndoing = !1, this._owner.filter.apply()) : this._setRowVisible(e)
        }, t.prototype._getRowsVisible = function() {
          for (var e, t = [], o = 0; o < this._owner.rows.length; o++)(e = this._owner.rows[o]) && t.push(e.visible);
          return t
        }, t.prototype._setRowVisible = function(e) {
          for (var t, o = e ? this._oldRowsVisible : this._newRowsVisible, n = 0; n < o.length; n++)(t = this._owner.rows[n]) && (t.visible = o[n])
        }, t
      }(n);
      o._FilteringAction = p;
      var w = function(e) {
        function o(t, o) {
          var n = e.call(this, t) || this;
          return n._fillSource = o.clone(), n._oldCellSettings = t._orgCellSettings, n
        }
        return __extends(o, e), o.prototype.undo = function() {
          this._handleUndoRedo(!0)
        }, o.prototype.redo = function() {
          this._handleUndoRedo(!1)
        }, o.prototype.saveNewState = function() {
          return this._fillRange = this._owner.selection.clone(), this._newCellSettings = this._owner._getCellSettingsForFill(this._fillSource, this._fillRange), !0
        }, o.prototype._handleUndoRedo = function(e) {
          var o, n, i, l, s, r, a = e ? this._oldCellSettings : this._newCellSettings,
            h = e ? this._fillSource : this._fillRange;
          if (this._owner.beginUpdate(), r = this._fillRange.topRow < this._fillSource.topRow ? new t.CellRange(this._fillRange.topRow, this._fillRange.col, this._fillSource.topRow - 1, this._fillRange.col2) : this._fillRange.leftCol === this._fillSource.leftCol && this._fillRange.rightCol === this._fillSource.rightCol ? new t.CellRange(this._fillSource.bottomRow + 1, this._fillRange.col, this._fillRange.bottomRow, this._fillRange.col2) : this._fillRange.leftCol < this._fillSource.leftCol ? new t.CellRange(this._fillRange.row, this._fillRange.leftCol, this._fillRange.row2, this._fillSource.leftCol - 1) : new t.CellRange(this._fillRange.row, this._fillSource.rightCol + 1, this._fillRange.row2, this._fillRange.rightCol), this._owner._resetMergedRange(r), a && a.length > 0)
            for (o = 0; o < a.length; o++) i = (n = a[o]).row, l = n.col, s = i * this._owner.columns.length + l, this._owner.selectedSheet._styledCells[s] = n.style, this._owner.setCellData(i, l, n.value), n.mergedCell && this._owner.mergeRange(n.mergedCell);
          this._owner.selection = h, this._owner.endUpdate()
        }, o
      }(n);
      o._FillAction = w
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(t) {
      "use strict";
      var o = function(o) {
        function n(e, t) {
          var n = o.call(this, e) || this;
          return n._idx = -1, n._isDisableDelRow = !1, n._isDisableConvertTable = !1, n._owner = t, n.applyTemplate("", n.getTemplate(), {
            _insRows: "insert-rows",
            _delRows: "delete-rows",
            _insCols: "insert-columns",
            _delCols: "delete-columns",
            _splitter: "splitter",
            _convertTable: "convert-table"
          }), n._init(), n
        }
        return __extends(n, o), Object.defineProperty(n.prototype, "visible", {
          get: function() {
            return "none" !== this.hostElement.style.display
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.show = function(t, o) {
          if (this._owner.selectedSheet) {
            var n = (o ? o.x : t.clientX) + (t ? window.pageXOffset : 0),
              i = (o ? o.y : t.clientY) + (t ? window.pageYOffset : 0);
            this.hostElement.style.position = "absolute", this.hostElement.style.display = "inline", this._owner._isDisableDeleteRow(this._owner.selection.topRow, this._owner.selection.bottomRow) && (this._isDisableDelRow = !0, e.addClass(this._delRows, "wj-state-disabled")), this._showTableOperation(), this._owner.selection.isValid || (this._isDisableConvertTable = !0, e.addClass(this._convertTable, "wj-state-disabled")), i + this.hostElement.clientHeight > window.innerHeight + (t ? window.pageYOffset : 0) && (i -= this.hostElement.clientHeight), n + this.hostElement.clientWidth > window.innerWidth + (t ? window.pageXOffset : 0) && (n -= this.hostElement.clientWidth), this.hostElement.style.top = i + "px", this.hostElement.style.left = n + "px"
          }
        }, n.prototype.hide = function() {
          this._idx = -1;
          var t = this.hostElement.querySelectorAll(".wj-context-menu-item");
          this._removeSelectedState(t), this.hostElement.style.display = "none", this._isDisableDelRow = !1, this._isDisableConvertTable = !1, e.removeClass(this._delRows, "wj-state-disabled"), e.removeClass(this._convertTable, "wj-state-disabled")
        }, n.prototype.moveToNext = function() {
          var t = this.hostElement.querySelectorAll(".wj-context-menu-item");
          for (this._removeSelectedState(t), this._idx++, 1 === this._idx && this._isDisableDelRow && this._idx++; t[this._idx] && "none" === t[this._idx].style.display;) this._idx++;
          this._idx >= t.length && (this._idx = 0), e.addClass(t[this._idx], "wj-context-menu-item-selected")
        }, n.prototype.moveToPrev = function() {
          var t = this.hostElement.querySelectorAll(".wj-context-menu-item");
          for (this._removeSelectedState(t), this._idx--, 1 === this._idx && this._isDisableDelRow && this._idx--; this._idx > 0 && "none" === t[this._idx].style.display;) this._idx--;
          this._idx < 0 && (this._idx = t.length - 1), e.addClass(t[this._idx], "wj-context-menu-item-selected")
        }, n.prototype.moveToFirst = function() {
          var t = this.hostElement.querySelectorAll(".wj-context-menu-item");
          this._removeSelectedState(t), this._idx = 0, e.addClass(t[this._idx], "wj-context-menu-item-selected")
        }, n.prototype.moveToLast = function() {
          var t = this.hostElement.querySelectorAll(".wj-context-menu-item");
          this._removeSelectedState(t), this._idx = t.length - 1, t[this._idx] && "none" === t[this._idx].style.display && this._idx--, e.addClass(t[this._idx], "wj-context-menu-item-selected")
        }, n.prototype.handleContextMenu = function() {
          if (-1 === this._idx) this.moveToNext();
          else {
            switch (this.hostElement.querySelectorAll(".wj-context-menu-item")[this._idx]) {
              case this._insCols:
                this._owner.insertColumns();
                break;
              case this._insRows:
                this._owner.insertRows();
                break;
              case this._delCols:
                this._owner.deleteColumns();
                break;
              case this._delRows:
                this._isDisableDelRow || this._owner.deleteRows();
                break;
              case this._convertTable:
                this._addTable()
            }
            this.hide(), this._owner.hostElement.focus()
          }
        }, n.prototype._init = function() {
          var t = e.culture.FlexSheet,
            o = this,
            n = this.hostElement.querySelectorAll(".wj-context-menu-item");
          o._insRows.textContent = t.insertRow, o._delRows.textContent = t.deleteRow, o._insCols.textContent = t.insertCol, o._delCols.textContent = t.deleteCol, o._convertTable.textContent = t.convertTable, o.hostElement.style.zIndex = "9999", document.querySelector("body").appendChild(o.hostElement), o.addEventListener(document.body, "mousemove", function() {
            o._removeSelectedState(n)
          }), o.addEventListener(o.hostElement, "contextmenu", function(e) {
            e.preventDefault()
          }), o.addEventListener(o._insRows, "click", function(e) {
            o._owner.insertRows(), o.hide(), o._owner.hostElement.focus()
          }), o.addEventListener(o._delRows, "click", function(e) {
            o._isDisableDelRow || o._owner.deleteRows(), o.hide(), o._owner.hostElement.focus()
          }), o.addEventListener(o._insCols, "click", function(e) {
            o._owner.insertColumns(), o.hide(), o._owner.hostElement.focus()
          }), o.addEventListener(o._delCols, "click", function(e) {
            o._owner.deleteColumns(), o.hide(), o._owner.hostElement.focus()
          }), o.addEventListener(o._convertTable, "click", function(e) {
            o._isDisableConvertTable || o._addTable(), o.hide(), o._owner.hostElement.focus()
          })
        }, n.prototype._removeSelectedState = function(t) {
          for (var o = 0; o < t.length; o++) e.removeClass(t[o], "wj-context-menu-item-selected")
        }, n.prototype._showTableOperation = function() {
          var e, t, o = this._owner.selection;
          for (e = o.topRow; e <= o.bottomRow; e++)
            for (t = o.leftCol; t <= o.rightCol; t++)
              if (null != this._owner.selectedSheet.findTable(e, t)) return this._convertTable.style.display = "none", void(this._splitter.style.display = "none");
          o.isSingleCell || this._owner._containsMergedCells(o) ? (this._convertTable.style.display = "none", this._splitter.style.display = "none") : (this._convertTable.style.display = "", this._splitter.style.display = "")
        }, n.prototype._addTable = function() {
          var e, o;
          if (e = this._owner.selectedSheet._addTable(this._owner.selection)) {
            if (0 === this._owner.undoStack.stackSize) return;
            o = new t._TableAction(this._owner, e), this._owner.undoStack._addAction(o)
          }
        }, n.controlTemplate = '<div class="wj-context-menu wj-control wj-flexsheet-context-menu" width="150px"><div class="wj-context-menu-item" wj-part="insert-rows"></div><div class="wj-context-menu-item" wj-part="delete-rows"></div><div class="wj-context-menu-item" wj-part="insert-columns"></div><div class="wj-context-menu-item" wj-part="delete-columns"></div><div class="wj-state-disabled" wj-part="splitter" style="width:100%;height:1px;background-color:lightgray;"></div><div class="wj-context-menu-item" wj-part="convert-table"></div></div>', n
      }(e.Control);
      t._ContextMenu = o
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(t) {
      "use strict";
      var o = function(e) {
        function o(t, o) {
          var n = e.call(this, t) || this;
          return n._splitterMousedownHdl = n._splitterMousedownHandler.bind(n), n._owner = o, n.applyTemplate("", n.getTemplate(), {
            _divSheet: "left",
            _divSplitter: "splitter",
            _divRight: "right"
          }), n._init(), n
        }
        return __extends(o, e), Object.defineProperty(o.prototype, "sheetControl", {
          get: function() {
            return this._sheetControl
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(o.prototype, "visible", {
          get: function() {
            return "none" !== this.hostElement.style.display
          },
          set: function(e) {
            this.hostElement.style.display = e ? "block" : "none", this._divSheet.style.display = e ? "block" : "none"
          },
          enumerable: !0,
          configurable: !0
        }), o.prototype.getSheetBlanketSize = function() {
          return 20
        }, o.prototype.adjustSize = function() {
          var e = this._owner.scrollSize.width - this._owner.clientSize.width,
            t = (this._owner.scrollSize.height, this._owner.clientSize.height, this._divSplitter.parentElement);
          e <= 0 ? (t.style.minWidth = "100px", this._divSplitter.style.display = "none", this._divRight.style.display = "none", this._divSheet.style.width = "100%", this._divSplitter.removeEventListener("mousedown", this._splitterMousedownHdl, !0)) : (t.style.minWidth = "300px", this._divSplitter.style.display = "none", this._divRight.style.display = "none", this._divSheet.style.width = "100%", this._divSplitter.removeEventListener("mousedown", this._splitterMousedownHdl, !0), this._divSplitter.addEventListener("mousedown", this._splitterMousedownHdl, !0)), this._sheetControl._adjustSize()
        }, o.prototype._init = function() {
          var e = this;
          e._funSplitterMousedown = function(t) {
            e._splitterMouseupHandler(t)
          }, e._divSplitter.parentElement.style.height = e.getSheetBlanketSize() + "px", e._sheetControl = new t._SheetTabs(e._divSheet, this._owner)
        }, o.prototype._splitterMousedownHandler = function(e) {
          this._startPos = e.pageX, document.addEventListener("mousemove", this._splitterMousemoveHandler.bind(this), !0), document.addEventListener("mouseup", this._funSplitterMousedown, !0), e.preventDefault()
        }, o.prototype._splitterMousemoveHandler = function(e) {
          null !== this._startPos && void 0 !== this._startPos && this._adjustDis(e.pageX - this._startPos)
        }, o.prototype._splitterMouseupHandler = function(e) {
          document.removeEventListener("mousemove", this._splitterMousemoveHandler, !0), document.removeEventListener("mouseup", this._funSplitterMousedown, !0), this._adjustDis(e.pageX - this._startPos), this._startPos = null
        }, o.prototype._adjustDis = function(e) {
          var t = this._divRight.offsetWidth - e,
            o = this._divSheet.offsetWidth + e;
          t <= 100 ? (t = 100, e = this._divRight.offsetWidth - t, o = this._divSheet.offsetWidth + e) : o <= 100 && (e = (o = 100) - this._divSheet.offsetWidth, t = this._divRight.offsetWidth - e), 0 != e && (this._divRight.style.width = t + "px", this._divSheet.style.width = o + "px", this._startPos = this._startPos + e)
        }, o.controlTemplate = '<div><div wj-part="left" style ="float:left;height:100%;overflow:hidden"></div><div wj-part="splitter" style="float:left;height:100%;width:6px;background-color:#e9eaee;padding:2px;cursor:e-resize"><div style="background-color:#8a9eb2;height:100%"></div></div><div wj-part="right" style="float:left;height:100%;background-color:#e9eaee"></div></div>', o
      }(e.Control);
      t._TabHolder = o
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(n) {
        function i() {
          return null !== n && n.apply(this, arguments) || this
        }
        return __extends(i, n), i.prototype.updateCell = function(i, l, s, r, a) {
          var h, c, d, u, _, g, f, p, w, m, C, b, y, S, R, v = i.grid;
          switch (i.cellType === t.CellType.Cell && this._resetCellStyle(r), n.prototype.updateCell.call(this, i, l, s, r, a), a && !a.isSingleCell && (l = a.row, s = a.col, a.row2, a.col2), R = v.selectedSheet, m = v._getBindingColumn(i, l, i.columns[s]), i.cellType) {
            case t.CellType.RowHeader:
              r.textContent = l + 1 + "";
              break;
            case t.CellType.ColumnHeader:
              h = R && R._showDefaultHeader || e.isNullOrWhiteSpace(m.header) || !e.isNullOrWhiteSpace(m.binding) && (m.header === m.binding || m.header === e.toHeaderCase(m.binding)) ? o.FlexSheet.convertNumberToAlpha(s) : m.header, r.textContent ? r.innerHTML = r.innerHTML.replace(e.escapeHtml(r.textContent.trim()), h).replace(r.textContent, h) : r.innerHTML += h, r.style.textAlign = "center";
              break;
            case t.CellType.Cell:
              if (c = l * v.columns.length + s, f = R && R._styledCells ? R._styledCells[c] : null, a && !a.isSingleCell && (l = (b = this._getFirstVisibleCell(v, a)).row, s = b.col), i.rows[l] instanceof o.HeaderRow) i.columns[s].dataType === e.DataType.Boolean ? 1 === r.childElementCount && r.firstElementChild instanceof HTMLInputElement && "checkbox" === r.firstElementChild.type && (r.innerHTML = e.escapeHtml(v.getCellValue(l, s))) : r.innerHTML || (r.innerHTML = e.escapeHtml(v.getCellValue(l, s))), e.addClass(r, "wj-header-row");
              else if (u = v.getCellValue(l, s, !1), _ = v.getCellData(l, s, !1), g = null != _ && "string" == typeof _ && "=" === _[0], y = i.rows[l] instanceof t.GroupRow, C = (f ? f.format : null) || (y ? null : m.format), v.editRange && v.editRange.contains(l, s) ? !e.isNumber(u) || m.dataMap || g || (C && (u = this._getFormattedValue(u, C)), (w = r.querySelector("input")) && (w.value = u)) : i.columns[s].dataType === e.DataType.Boolean ? (p = r.querySelector('[type="checkbox"]')) && (p.checked = v.getCellValue(l, s), p.disabled = p.disabled || !v.canEditCell(l, s)) : m.dataMap && !y ? (u = v.getCellValue(l, s, !0), (d = r.firstChild) && 3 === d.nodeType && d.nodeValue !== u && (d.nodeValue = u)) : 0 === r.childElementCount && r.textContent === v.getCellData(l, s, !0) && ("" !== (u = v.getCellValue(l, s, !0)) && e.isNumber(+u) && !isNaN(+u) && /[hsmy\:]/i.test(C) && (S = o.FlexSheet._fromOADate(+u), isNaN(S.getTime()) || (u = e.Globalize.formatDate(S, C))), !C && y || (u = e.isString(u) ? u.replace(/^(\')(\s*[\w|=])/, "$2") : u, e.isString(u) ? u && this._isURL(u) ? r.innerHTML = '<a href="' + u + '" target="_blank">' + e.escapeHtml(u) + "</a>" : r.innerHTML = e.escapeHtml(u) : r.innerHTML = u)), f) {
                var x, T = r.style;
                for (var I in f) "className" === I ? f.className && e.addClass(r, f.className) : "format" !== I && (x = f[I]) && (!e.hasClass(r, "wj-state-selected") && !e.hasClass(r, "wj-state-multi-selected") || "color" !== I && "backgroundColor" !== I ? T[I] = "whiteSpace" === I && "normal" === x ? "" : x : T[I] = "")
              }(r.style.backgroundColor || r.style.color) && (f || (f = {}, R && R._styledCells[c]), r.style.backgroundColor && (f.backgroundColor = r.style.backgroundColor), r.style.color && (f.color = r.style.color))
          }
          i.cellType === t.CellType.Cell && (l !== v._lastVisibleFrozenRow || e.hasClass(r, "wj-frozen-row") || e.addClass(r, "wj-frozen-row"), s !== v._lastVisibleFrozenColumn || e.hasClass(r, "wj-frozen-col") || e.addClass(r, "wj-frozen-col"))
        }, i.prototype._resetCellStyle = function(e) {
          ["fontFamily", "fontSize", "fontStyle", "fontWeight", "textDecoration", "textAlign", "verticalAlign", "backgroundColor", "color", "whiteSpace", "borderLeftStyle", "borderLeftColor", "borderLeftWidth", "borderRightStyle", "borderRightColor", "borderRightWidth", "borderTopStyle", "borderTopColor", "borderTopWidth", "borderBottomStyle", "borderBottomColor", "borderBottomWidth"].forEach(function(t) {
            e.style[t] = ""
          })
        }, i.prototype._getFormattedValue = function(t, o) {
          return t !== Math.round(t) && (o = o.replace(/([a-z])(\d*)(.*)/gi, "$0112$3")), e.Globalize.formatNumber(t, o, !0)
        }, i.prototype._getFirstVisibleCell = function(e, o) {
          var n, i;
          for (n = o.topRow; n <= o.bottomRow && !e.rows[n].isVisible; n++);
          for (i = o.leftCol; i <= o.rightCol && !e.columns[i].isVisible; i++);
          return new t.CellRange(n, i)
        }, i.prototype._isURL = function(e) {
          return new RegExp("^(https|http|ftp|rtsp|mms)://(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].[a-z]{2,6})(:[0-9]{1,4})?(/($|[a-zA-Z0-9.,?'\\+&amp;%$#=~_-]+))*").test(e)
        }, i
      }(t.CellFactory);
      o._FlexSheetCellFactory = n
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      e._addCultureInfo("FlexSheet", {
        insertRow: "Insert Row",
        deleteRow: "Delete Rows",
        insertCol: "Insert Column",
        deleteCol: "Delete Columns",
        convertTable: "Convert To Table",
        copyCells: "Copy Cells",
        fillSeries: "Fill Series",
        fillFormat: "Fill Formatting Only",
        fillWithoutFormat: "Fill Without Formatting"
      });
      var n = [{
          name: "abs",
          description: "Returns the absolute value of a number."
        }, {
          name: "acos",
          description: "Returns the arccosine of a number."
        }, {
          name: "address",
          description: "Obtains the address of a cell in a worksheet by given specified row and column numbers."
        }, {
          name: "and",
          description: "Returns TRUE if all of its arguments are TRUE."
        }, {
          name: "asin",
          description: "Returns the arcsine of a number."
        }, {
          name: "atan",
          description: "Returns the arctangent of a number."
        }, {
          name: "atan2",
          description: "Returns the arctangent from x- and y-coordinates."
        }, {
          name: "average",
          description: "Returns the average of its arguments."
        }, {
          name: "ceiling",
          description: "Rounds a number to the nearest integer or to the nearest multiple of significance."
        }, {
          name: "char",
          description: "Returns the character specified by the code number."
        }, {
          name: "choose",
          description: "Chooses a value from a list of values."
        }, {
          name: "code",
          description: "Returns a numeric code for the first character in a text string."
        }, {
          name: "column",
          description: "Returns the column number of a reference."
        }, {
          name: "columns",
          description: "Returns the number of columns in a reference."
        }, {
          name: "concatenate",
          description: "Joins several text items into one text item."
        }, {
          name: "cos",
          description: "Returns the cosine of a number."
        }, {
          name: "count",
          description: "Counts how many numbers are in the list of arguments."
        }, {
          name: "counta",
          description: "Counts how many values are in the list of arguments."
        }, {
          name: "countblank",
          description: "Counts the number of blank cells within a range."
        }, {
          name: "countif",
          description: "Counts the number of cells within a range that meet the given criteria."
        }, {
          name: "countifs",
          description: "Counts the number of cells within a range that meet multiple criteria."
        }, {
          name: "date",
          description: "Returns the serial number of a particular date."
        }, {
          name: "datedif",
          description: "Calculates the number of days, months, or years between two dates."
        }, {
          name: "day",
          description: "Converts a serial number to a day of the month."
        }, {
          name: "dcount",
          description: "Counts the cells that contain numbers in a database."
        }, {
          name: "exp",
          description: "Returns e raised to the power of a given number."
        }, {
          name: "false",
          description: "Returns the logical value FALSE."
        }, {
          name: "find",
          description: "Finds one text value within another (case-sensitive)."
        }, {
          name: "floor",
          description: "Rounds a number down, toward zero."
        }, {
          name: "hlookup",
          description: "Looks in the top row of an array and returns the value of the indicated cell."
        }, {
          name: "hour",
          description: "Converts a serial number to an hour."
        }, {
          name: "if",
          description: "Specifies a logical test to perform."
        }, {
          name: "index",
          description: "Uses an index to choose a value from a reference."
        }, {
          name: "indirect",
          description: "Returns the reference specified by a text string. References are immediately evaluated to display their contents."
        }, {
          name: "left",
          description: "Returns the leftmost characters from a text value."
        }, {
          name: "len",
          description: "Returns the number of characters in a text string."
        }, {
          name: "ln",
          description: "Returns the natural logarithm of a number."
        }, {
          name: "lower",
          description: "Converts text to lowercase."
        }, {
          name: "max",
          description: "Returns the maximum value in a list of arguments."
        }, {
          name: "mid",
          description: "Returns a specific number of characters from a text string starting at the position you specify."
        }, {
          name: "min",
          description: "Returns the minimum value in a list of arguments."
        }, {
          name: "mod",
          description: "Returns the remainder from division."
        }, {
          name: "month",
          description: "Converts a serial number to a month."
        }, {
          name: "not",
          description: "Reverses the logic of its argument."
        }, {
          name: "now",
          description: "Returns the serial number of the current date and time."
        }, {
          name: "or",
          description: "Returns TRUE if any argument is TRUE."
        }, {
          name: "pi",
          description: "Returns the value of pi."
        }, {
          name: "power",
          description: "Returns the result of a number raised to a power."
        }, {
          name: "product",
          description: "Multiplies its arguments."
        }, {
          name: "proper",
          description: "Capitalizes the first letter in each word of a text value."
        }, {
          name: "rand",
          description: "Returns a random number between 0 and 1."
        }, {
          name: "rank",
          description: "Returns the rank of a number in a list of numbers."
        }, {
          name: "rate",
          description: "Returns the interest rate per period of an annuity."
        }, {
          name: "replace",
          description: "Replaces characters within text."
        }, {
          name: "rept",
          description: "Repeats text a given number of times."
        }, {
          name: "right",
          description: "Returns the rightmost characters from a text value."
        }, {
          name: "round",
          description: "Rounds a number to a specified number of digits."
        }, {
          name: "rounddown",
          description: "Rounds a number down, toward zero."
        }, {
          name: "roundup",
          description: "Rounds a number up, away from zero."
        }, {
          name: "row",
          description: "Returns the row number of a reference."
        }, {
          name: "rows",
          description: "Returns the number of rows in a reference."
        }, {
          name: "search",
          description: "Finds one text value within another (not case-sensitive)."
        }, {
          name: "sin",
          description: "Returns the sine of the given angle."
        }, {
          name: "sqrt",
          description: "Returns a positive square root."
        }, {
          name: "stdev",
          description: "Estimates standard deviation based on a sample."
        }, {
          name: "stdevp",
          description: "Calculates standard deviation based on the entire population."
        }, {
          name: "substitute",
          description: "Substitutes new text for old text in a text string."
        }, {
          name: "subtotal",
          description: "Returns a subtotal in a list or database."
        }, {
          name: "sum",
          description: "Adds its arguments."
        }, {
          name: "sumif",
          description: "Adds the cells specified by a given criteria."
        }, {
          name: "sumifs",
          description: "Adds the cells in a range that meet multiple criteria."
        }, {
          name: "sumproduct",
          description: "Multiplies corresponding components in the given arrays, and returns the sum of those products."
        }, {
          name: "tan",
          description: "Returns the tangent of a number."
        }, {
          name: "text",
          description: "Formats a number and converts it to text."
        }, {
          name: "time",
          description: "Returns the serial number of a particular time."
        }, {
          name: "today",
          description: "Returns the serial number of today's date."
        }, {
          name: "trim",
          description: "Removes spaces from text."
        }, {
          name: "true",
          description: "Returns the logical value TRUE."
        }, {
          name: "trunc",
          description: "Truncates a number to an integer."
        }, {
          name: "upper",
          description: "Converts text to uppercase."
        }, {
          name: "value",
          description: "Converts a text argument to a number."
        }, {
          name: "var",
          description: "Estimates variance based on a sample."
        }, {
          name: "varp",
          description: "Calculates variance based on the entire population."
        }, {
          name: "year",
          description: "Converts a serial number to a year."
        }],
        i = function(i) {
          function s(n, l) {
            var s = i.call(this, n, l) || this;
            return s._selectedSheetIndex = -1, s._columnHeaderClicked = !1, s._addingSheet = !1, s._mouseMoveHdl = s._mouseMove.bind(s), s._clickHdl = s._click.bind(s), s._touchStartHdl = s._touchStart.bind(s), s._touchEndHdl = s._touchEnd.bind(s), s._keydownHdl = s._keydown.bind(s), s._isContextMenuKeyDown = !1, s._isClicking = !1, s._definedNames = new e.collections.ObservableArray, s._builtInTableStylesCache = null, s._needCopyToSheet = !0, s._isSorting = !1, s._fillingData = !1, s._endDragFillOperationHdl = s._endDragFillOperation.bind(s), s._enableDragDrop = !0, s._enableFormulas = !0, s.selectedSheetChanged = new e.Event, s.draggingRowColumn = new e.Event, s.droppingRowColumn = new e.Event, s.loaded = new e.Event, s.unknownFunction = new e.Event, s.sheetCleared = new e.Event, s.prepareChangingRow = new e.Event, s.prepareChangingColumn = new e.Event, s.rowChanged = new e.Event, s.columnChanged = new e.Event, s._needCopyToSheet = !1, s._colorThemes = ["FFFFFF", "000000", "EEECE1", "1F497D", "4F818D", "C0504D", "9BBB59", "8064A2", "4BACC6", "F79646"], s._eCt.style.backgroundColor = "white", e.addClass(s.hostElement, "wj-flexsheet"), e.setCss(s.hostElement, {
              fontFamily: "Arial"
            }), s._cf = new o._FlexSheetCellFactory, s._bndSortConverter = s._sheetSortConverter.bind(s), s.quickAutoSize = !1, s._init(), s.showSort = !1, s.allowSorting = !1, s.showGroups = !1, s.showMarquee = !0, s.showSelectedHeaders = t.HeadersVisibility.All, s.allowResizing = t.AllowResizing.Both, s.allowDragging = t.AllowDragging.None, s.keyActionTab = t.KeyAction.CycleOut, s._needCopyToSheet = !0, s
          }
          return __extends(s, i), s.prototype._getProductInfo = function() {
            return "R20I,FlexSheet"
          }, Object.defineProperty(s.prototype, "sheets", {
            get: function() {
              return this._sheets || (this._sheets = new o.SheetCollection, this._sheets.selectedSheetChanged.addHandler(this._selectedSheetChange, this), this._sheets.collectionChanged.addHandler(this._sourceChange, this), this._sheets.sheetVisibleChanged.addHandler(this._sheetVisibleChange, this), this._sheets.sheetCleared.addHandler(this.onSheetCleared, this)), this._sheets
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "selectedSheetIndex", {
            get: function() {
              return this._selectedSheetIndex
            },
            set: function(e) {
              e !== this._selectedSheetIndex && (this._showSheet(e), this._sheets.selectedIndex = e)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "selectedSheet", {
            get: function() {
              return this._sheets[this._selectedSheetIndex]
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "isFunctionListOpen", {
            get: function() {
              return this._functionListHost && "none" !== this._functionListHost.style.display
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "isTabHolderVisible", {
            get: function() {
              return this._tabHolder.visible
            },
            set: function(e) {
              e !== this._tabHolder.visible && (e ? (this._divContainer.style.height = this._divContainer.parentElement.clientHeight - this._tabHolder.getSheetBlanketSize() + "px", this._eFocus.removeAttribute("tabindex")) : (this._divContainer.style.height = this._divContainer.parentElement.clientHeight + "px", this._eFocus.tabIndex = this._orgTabIndex), this._tabHolder.visible = e)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "undoStack", {
            get: function() {
              return this._undoStack
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "sortManager", {
            get: function() {
              return this._sortManager
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "filter", {
            get: function() {
              return this._filter
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "showFilterIcons", {
            get: function() {
              return !!this._filter && this._filter.showFilterIcons
            },
            set: function(e) {
              this._filter && this._filter.showFilterIcons !== e && (this._filter.showFilterIcons = e)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "definedNames", {
            get: function() {
              return this._definedNames
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "enableDragDrop", {
            get: function() {
              return this._enableDragDrop
            },
            set: function(e) {
              this._enableDragDrop = e
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "enableFormulas", {
            get: function() {
              return this._enableFormulas
            },
            set: function(e) {
              this._enableFormulas !== e && (this._enableFormulas = e, this.refresh())
            },
            enumerable: !0,
            configurable: !0
          }), s.prototype.onSelectedSheetChanged = function(e) {
            this.selectedSheetChanged.raise(this, e)
          }, s.prototype.onDraggingRowColumn = function(e) {
            this.draggingRowColumn.raise(this, e)
          }, s.prototype.onDroppingRowColumn = function(t) {
            this.droppingRowColumn.raise(this, new e.EventArgs)
          }, s.prototype.onLoaded = function(t) {
            var o = this;
            o._toRefresh && (clearTimeout(o._toRefresh), o._toRefresh = null), o._toRefresh = setTimeout(function() {
              o._setFlexSheetToDirty(), o.invalidate()
            }, 10), o.loaded.raise(this, new e.EventArgs)
          }, s.prototype.onUnknownFunction = function(e) {
            this.unknownFunction.raise(this, e)
          }, s.prototype.onSheetCleared = function(t) {
            this.sheetCleared.raise(this, new e.EventArgs)
          }, s.prototype.onPrepareChangingRow = function(e) {
            this.prepareChangingRow.raise(this, e)
          }, s.prototype.onPrepareChangingColumn = function(e) {
            this.prepareChangingColumn.raise(this, e)
          }, s.prototype.onRowChanged = function(e) {
            this.rowChanged.raise(this, e)
          }, s.prototype.onColumnChanged = function(e) {
            this.columnChanged.raise(this, e)
          }, s.prototype.refresh = function(e) {
            void 0 === e && (e = !0);
            var t, o, n;
            if (this._divContainer.style.height = this._divContainer.parentElement.clientHeight - (this.isTabHolderVisible ? this._tabHolder.getSheetBlanketSize() : 0) + "px", !this.preserveSelectedState && this.selectedSheet && (this.selectedSheet.selectionRanges.clear(), this.selectedSheet.selectionRanges.push(this.selection)), e && this._clearCalcEngine(), this._lastVisibleFrozenRow = -1, this.frozenRows > 0)
              for (var l = this.frozenRows - 1; l >= 0; l--)
                if (this.rows[l] && this.rows[l].isVisible) {
                  this._lastVisibleFrozenRow = l;
                  break
                }
            if (this._lastVisibleFrozenColumn = -1, this.frozenColumns > 0)
              for (var s = this.frozenColumns - 1; s >= 0; s--)
                if (this.columns[s] && this.columns[s].isVisible) {
                  this._lastVisibleFrozenColumn = s;
                  break
                }
            if (this.selectedSheet) {
              if (this.selectedSheet._freezeHiddenRows && this.selectedSheet._freezeHiddenRows.length > 0)
                for (t = 0; t < this.selectedSheet._freezeHiddenRows.length; t++)(o = this.rows[t]) instanceof h || !this.selectedSheet._freezeHiddenRows[t] || (o.visible = !1);
              if (this.selectedSheet._freezeHiddenCols && this.selectedSheet._freezeHiddenCols.length > 0)
                for (n = 0; n < this.selectedSheet._freezeHiddenCols.length; n++) this.selectedSheet._freezeHiddenCols[n] && (this.columns[n].visible = !1)
            }
            i.prototype.refresh.call(this, e), this._tabHolder.adjustSize()
          }, s.prototype.setCellData = function(t, o, n, i, l) {
            void 0 === i && (i = !1), void 0 === l && (l = !0);
            var s = e.isString(n) && n.length > 1 && "=" === n[0];
            return this._clearCalcEngine(), this.cells.setCellData(t, o, n, i && !s, l)
          }, s.prototype.containsFocus = function() {
            return this.isFunctionListOpen || i.prototype.containsFocus.call(this)
          }, s.prototype.addUnboundSheet = function(e, t, o, n, i) {
            var l = this._addSheet(e, t, o, n, i);
            return 0 === l.selectionRanges.length && l.selectionRanges.push(this.selection), l
          }, s.prototype.addBoundSheet = function(e, t, o, n) {
            var i = this._addSheet(e, 0, 0, o, n);
            return t && (i.itemsSource = t, this.childItemsPath && (i.grid.childItemsPath = this.childItemsPath)), 0 === i.selectionRanges.length && i.selectionRanges.push(this.selection), i
          }, s.prototype.applyCellsStyle = function(e, t, n) {
            void 0 === n && (n = !1);
            var i, l, s, r, a, h = null != t && t.length > 0,
              c = t || [this.selection];
            if (this.selectedSheet) {
              if (!e && this._cloneStyle) return this.selectedSheet._styledCells = this._cloneObject(this._cloneStyle), this._cloneStyle = null, void this.refresh(!1);
              if (c) {
                for (t || n ? n && !this._cloneStyle && (this._cloneStyle = this._cloneObject(this.selectedSheet._styledCells)) : (this.undoStack.stackSize > 0 && (a = new o._CellStyleAction(this, this._cloneStyle)), this._cloneStyle = null), r = 0; r < c.length; r++)
                  for (i = (s = c[r]).topRow; i <= s.bottomRow; i++)
                    for (l = s.leftCol; l <= s.rightCol; l++) this._applyStyleForCell(i, l, e, h);
                a && (a.saveNewState(), this._undoStack._addAction(a))
              }
              t || this.refresh(!1)
            }
          }, s.prototype.freezeAtCursor = function() {
            var e, t, o, n, i, l, s = this;
            if (s.selectedSheet) {
              if (this.selectedSheet._freezeHiddenRows = null, this.selectedSheet._freezeHiddenCols = null, s.selection && 0 === s.frozenRows && 0 === s.frozenColumns) {
                if (s._ptScrl.y < 0)
                  for (this.selectedSheet._freezeHiddenRows = [], e = 0; e < s.selection.topRow - 1; e++)
                    if (!((i = s.rows[e]) instanceof h)) {
                      if (!(i._pos + s._ptScrl.y < 0 && i.visible)) break;
                      i.visible = !1, this.selectedSheet._freezeHiddenRows[e] = !0
                    }
                if (s._ptScrl.x < 0)
                  for (this.selectedSheet._freezeHiddenCols = [], t = 0; t < s.selection.leftCol - 1 && ((l = s.columns[t])._pos + s._ptScrl.x < 0 && l.visible); t++) l.visible = !1, this.selectedSheet._freezeHiddenCols[t] = !0;
                o = s.selection.leftCol > 0 ? s.selection.leftCol : 0, n = s.selection.topRow > 0 ? s.selection.topRow : 0
              } else {
                for (e = 0; e < s.frozenRows - 1; e++) s.rows[e].visible = !0;
                for (t = 0; t < s.frozenColumns - 1; t++) s.columns[t].visible = !0;
                s._filter.apply(), o = 0, n = 0
              }
              s.frozenRows = s.selectedSheet.grid.frozenRows = n, s.frozenColumns = s.selectedSheet.grid.frozenColumns = o, setTimeout(function() {
                s._setFlexSheetToDirty(), s.invalidate(), s.scrollIntoView(s.selection.topRow, s.selection.leftCol)
              }, 10)
            }
          }, s.prototype.showColumnFilter = function() {
            var e = this.selection.col > 0 ? this.selection.col : 0;
            this.columns.length > 0 && this._filter.editColumnFilter(this.columns[e])
          }, s.prototype.clear = function() {
            this.beginUpdate(), this.selection = new t.CellRange, this.sheets.clear(), this._selectedSheetIndex = -1, this.columns.clear(), this.rows.clear(), this.columnHeaders.columns.clear(), this.rowHeaders.rows.clear(), this._undoStack.clear(), this._ptScrl = new e.Point, this._clearCalcEngine(), this._definedNames.clear(), this._builtInTableStylesCache = null, this._copiedRanges = null, this._copiedSheet = null, this._isCutting = !1, this._cutValue = null, this._reservedContent = null, this._lastVisibleFrozenRow = -1, this._lastVisibleFrozenColumn = -1, this.addUnboundSheet(), this.endUpdate()
          }, s.prototype.getSelectionFormatState = function() {
            var e, t, o = this.rows.length,
              n = this.columns.length,
              i = {
                isBold: !1,
                isItalic: !1,
                isUnderline: !1,
                textAlign: "left",
                isMergedCell: !1
              };
            if (0 === o || 0 === n) return i;
            if (this.selection) {
              if (this.selection.row >= o || this.selection.row2 >= o || this.selection.col >= n || this.selection.col2 >= n) return i;
              for (e = this.selection.topRow; e <= this.selection.bottomRow; e++)
                for (t = this.selection.leftCol; t <= this.selection.rightCol; t++) this._checkCellFormat(e, t, i)
            }
            return i
          }, s.prototype.insertRows = function(n, i) {
            var l, s, a, c, d, u, _, g, f, p = this,
              w = e.isNumber(n) && n >= 0 ? n : p.selection && p.selection.topRow > -1 ? p.selection.topRow : 0,
              m = e.isNumber(i) ? i : 1,
              C = p.rows[w],
              b = !1;
            if (p.selectedSheet) {
              if (p.rows.length > 0 ? w >= p.rows.length && (w = p.rows.length - 1) : w = 0, p._clearCalcEngine(), p.finishEditing(), 0 === w && C && C.constructor === h && (w = 1), p.onPrepareChangingRow(new r(w, m, !0)), p.undoStack.stackSize > 0 && (l = new o._RowsChangedAction(p, w, m, !0)), p._updateCellsForUpdatingRow(p.rows.length, w, m), (!p.collectionView || 0 === p.collectionView.sortDescriptions.length) && (s = p._updateAffectedFormula(w, m, !0, !0), a = p._updateAffectedNamedRanges(w, m, !0, !0)), l && (l._affectedFormulas = s, l._affectedDefinedNameVals = a), p.collectionView) {
                for (p.selectedSheet._dataView = p.collectionView._view.slice(), b = !0, d = p.itemsSource instanceof e.collections.CollectionView, g = p.autoGenerateColumns, p.autoGenerateColumns = !1, p.collectionView.beginUpdate(), d || (f = p.selectedSheet.grid.autoGenerateColumns, p.selectedSheet.grid.autoGenerateColumns = !1, p.selectedSheet.grid.collectionView.beginUpdate()), c = 0; c < m; c++) _ = w - (p.allowAddNew && p.newRowAtTop ? 2 : 1), d ? (u = p.itemsSource.newItemCreator ? p.itemsSource.newItemCreator() : {}, p.itemsSource.sourceCollection.splice(_, 0, u)) : (u = {}, p.itemsSource.splice(_, 0, u), p.itemsSource !== p.selectedSheet.grid.itemsSource && p.selectedSheet.grid.itemsSource.splice(_, 0, u)), p.selectedSheet._dataView.splice(_, 0, u);
                p._updateItemIndexForInsertingRow(p.collectionView.sourceCollection, _, m), d || (p.selectedSheet.grid.collectionView.endUpdate(), p.selectedSheet.grid.autoGenerateColumns = f), p.collectionView.endUpdate(), p.collectionView.sortDescriptions.length > 0 && (p.collectionView._view = p.selectedSheet._dataView, p.collectionView._pgView = p.collectionView._getPageView(), d || (p.selectedSheet.grid.collectionView._view = p.selectedSheet._dataView, p.selectedSheet.grid.collectionView._pgView = p.selectedSheet.grid.collectionView._getPageView()), p._bindGrid(!1), p.selectedSheet.grid._bindGrid(!1)), setTimeout(function() {
                  p._filter.apply()
                }), p.autoGenerateColumns = g
              } else {
                for (p.rows.beginUpdate(), c = 0; c < m; c++) p.rows.insert(w, new t.Row);
                p.rows.endUpdate()
              }
              p._updateTablesForUpdatingRow(w, m), p.selection && -1 !== p.selection.row && -1 !== p.selection.col || (p.selection = new t.CellRange(0, 0)), l && (l.saveNewState(), p._undoStack._addAction(l)), p.onRowChanged(new r(w, m, !0)), b && p.refresh()
            }
          }, s.prototype.deleteRows = function(n, i) {
            var l, s, a, c, d, u, _, g, f, p, w, m, C, b = e.isNumber(i) && i >= 0 ? i : this.selection && this.selection.topRow > -1 ? this.selection.bottomRow - this.selection.topRow + 1 : 1,
              y = e.isNumber(n) && n >= 0 ? n : this.selection && this.selection.topRow > -1 ? this.selection.topRow : -1,
              S = e.isNumber(n) && n >= 0 ? n + b - 1 : this.selection && this.selection.topRow > -1 ? this.selection.bottomRow : -1,
              R = !1;
            if (this.selectedSheet && !(y >= this.rows.length) && (S = Math.min(S, this.rows.length - 1), this._clearCalcEngine(), this.finishEditing(), y > -1 && S > -1)) {
              for (this.onPrepareChangingRow(new r(y, b, !1)), this.undoStack.stackSize > 0 && (l = new o._RowsChangedAction(this, y, b, !1)), this._updateCellsForUpdatingRow(this.rows.length, y, b, !0), a = this._updateAffectedFormula(S, S - y + 1, !1, !0), c = this._updateAffectedNamedRanges(S, S - y + 1, !1, !0), d = this._updateTablesForUpdatingRow(y, b, !0), l && (l._affectedFormulas = a, l._affectedDefinedNameVals = c, l._deletedTables = d), s = b === this.rows.length, w = (R = !!this.collectionView) && this.itemsSource instanceof e.collections.CollectionView, this.rows.beginUpdate(), R && (this.selectedSheet._dataView = this.collectionView._view.slice(), m = this.autoGenerateColumns, this.autoGenerateColumns = !1, this.collectionView.beginUpdate(), w || (C = this.selectedSheet.grid.autoGenerateColumns, this.selectedSheet.grid.autoGenerateColumns = !1, this.selectedSheet.grid.collectionView.beginUpdate())); S >= y; S--)(!(u = this.rows[S]) || (u.constructor !== h || s) && u.isVisible) && (u.dataItem && this.collectionView ? (_ = this._getCvIndex(S)) > -1 && (g = this.collectionView.items[_], w ? (f = this.itemsSource.sourceCollection.indexOf(g), this.itemsSource.sourceCollection.splice(f, 1)) : (f = this.itemsSource.indexOf(g), this.itemsSource.splice(f, 1), this.itemsSource !== this.selectedSheet.grid.itemsSource && this.selectedSheet.grid.itemsSource.splice(f, 1)), this.selectedSheet._dataView.splice(this.selectedSheet._dataView.indexOf(g), 1), this._updateItemIndexForRemovingRow(this.collectionView.sourceCollection, f)) : this.rows.removeAt(S));
              R && (w || (this.selectedSheet.grid.collectionView.endUpdate(), this.selectedSheet.grid.autoGenerateColumns = C), this.collectionView.endUpdate(), this.collectionView.sortDescriptions.length > 0 && (this.collectionView._view = this.selectedSheet._dataView, this.collectionView._pgView = this.collectionView._getPageView(), w || (this.selectedSheet.grid.collectionView._view = this.selectedSheet._dataView, this.selectedSheet.grid.collectionView._pgView = this.selectedSheet.grid.collectionView._getPageView()), this._bindGrid(!1), this.selectedSheet.grid._bindGrid(!1)), this.autoGenerateColumns = m), this.rows.endUpdate(), p = this.rows.length, this.selectedSheet.selectionRanges.clear(), 0 === p ? (this.select(new t.CellRange), "move" === this.hostElement.style.cursor && (this.hostElement.style.cursor = "default")) : S === p - 1 ? this.select(new t.CellRange(S, 0, S, this.columns.length - 1)) : this.select(new t.CellRange(this.selection.topRow, this.selection.col, this.selection.topRow, this.selection.col2)), l && (l.saveNewState(), this._undoStack._addAction(l)), this.onRowChanged(new r(y, b, !1)), R && this.refresh()
            }
          }, s.prototype.insertColumns = function(n, i) {
            var l, s, a, h, c, d, u = e.isNumber(n) && n >= 0 ? n : this.selection && this.selection.leftCol > -1 ? this.selection.leftCol : 0,
              _ = e.isNumber(i) ? i : 1;
            if (this.selectedSheet) {
              for (this.columns.length > 0 ? u >= this.columns.length && (u = this.columns.length - 1) : u = 0, this._clearCalcEngine(), this.finishEditing(), this.onPrepareChangingColumn(new r(u, _, !0)), this.undoStack.stackSize > 0 && (l = new o._ColumnsChangedAction(this, u, _, !0)), this._updateCellsForUpdatingColumn(this.columns.length, u, _), c = this._updateAffectedFormula(u, _, !0, !1), d = this._updateAffectedNamedRanges(u, _, !0, !1), l && (l._affectedFormulas = c, l._affectedDefinedNameVals = d), this.columns.beginUpdate(), a = 0; a < _; a++)(s = new t.Column).isRequired = !1, this.itemsSource && (s.binding = this._getUniqueColumnName(), (h = this.allowAddNew && this.newRowAtTop ? this.rows[1] : this.rows[0]) && (h._ubv || (h._ubv = {}), h._ubv[s._hash] = s.header)), this.columns.insert(u, s);
              this.columns.endUpdate(), this.selectedSheet._filterDefinition = this._filter.filterDefinition, this._sortManager._updateSortSortDescription(u, _), this._sortManager.commitSort(!1), this._updateTablesForUpdatingColumn(u, _), this.selection && -1 !== this.selection.row && -1 !== this.selection.col || (this.selection = new t.CellRange(0, 0)), l && (l.saveNewState(), this._undoStack._addAction(l)), this.onColumnChanged(new r(u, _, !0))
            }
          }, s.prototype.deleteColumns = function(n, i) {
            var l, s, a, h, c, d = e.isNumber(i) && i >= 0 ? i : this.selection && this.selection.leftCol > -1 ? this.selection.rightCol - this.selection.leftCol + 1 : 1,
              u = e.isNumber(n) && n >= 0 ? n : this.selection && this.selection.leftCol > -1 ? this.selection.leftCol : -1,
              _ = e.isNumber(n) && n >= 0 ? n + d - 1 : this.selection && this.selection.leftCol > -1 ? this.selection.rightCol : -1;
            if (this.selectedSheet && !(u >= this.columns.length) && (_ = Math.min(_, this.columns.length - 1), this._clearCalcEngine(), this.finishEditing(), u > -1 && _ > -1)) {
              for (this.onPrepareChangingColumn(new r(u, d, !1)), this.undoStack.stackSize > 0 && (s = new o._ColumnsChangedAction(this, u, d, !1)), this._updateCellsForUpdatingColumn(this.columns.length, u, d, !0), a = this._updateAffectedFormula(_, _ - u + 1, !1, !1), h = this._updateAffectedNamedRanges(_, _ - u + 1, !1, !1), c = this._updateTablesForUpdatingColumn(u, d, !0), s && (s._affectedFormulas = a, s._affectedDefinedNameVals = h, s._deletedTables = c), this.columns.beginUpdate(); _ >= u; _--) this.columns[_].isVisible && (this.columns.removeAt(_), this._sortManager.deleteSortLevel(_));
              this.columns.endUpdate(), this.selectedSheet._filterDefinition = this._filter.filterDefinition, this._sortManager._updateSortSortDescription(u, d, !1), this._sortManager.commitSort(!1), l = this.columns.length, this.selectedSheet.selectionRanges.clear(), 0 === l ? (this.select(new t.CellRange), "move" === this.hostElement.style.cursor && (this.hostElement.style.cursor = "default")) : _ === l - 1 ? this.select(new t.CellRange(0, _, this.rows.length - 1, _)) : this.select(new t.CellRange(this.selection.row, this.selection.leftCol, this.selection.row2, this.selection.leftCol)), s && (s.saveNewState(), this._undoStack._addAction(s)), this.onColumnChanged(new r(u, d, !1))
            }
          }, s.prototype.mergeRange = function(e, n) {
            void 0 === n && (n = !1);
            var i, l, s, r, a, h = e || this.selection,
              c = -1,
              d = -1;
            if (this.selectedSheet) {
              if (h) {
                if (1 === h.rowSpan && 1 === h.columnSpan) return;
                for (i = h.topRow; i <= h.bottomRow; i++)
                  for (l = h.leftCol; l <= h.rightCol; l++)
                    if (this.selectedSheet.findTable(i, l)) return;
                if (e || n || (this.undoStack.stackSize > 0 && (a = new o._CellMergeAction(this)), this.hostElement.focus()), !this._resetMergedRange(h)) {
                  for (i = h.topRow; i <= h.bottomRow; i++)
                    if ((s = this.rows[i]) && s.isVisible) {
                      c = i;
                      break
                    }
                  for (l = h.leftCol; l <= h.rightCol; l++)
                    if ((r = this.columns[l]) && r.isVisible) {
                      d = l;
                      break
                    }
                  c > -1 && d > -1 && this.selectedSheet._mergedRanges.push(new t.CellRange(c, d, h.bottomRow, h.rightCol))
                }
                a && (a.saveNewState(), this._undoStack._addAction(a))
              }
              e || this.refresh()
            }
          }, s.prototype.getMergedRange = function(e, o, n, l) {
            void 0 === l && (l = !0);
            var s, r, a, h, c = this.selectedSheet ? this.selectedSheet._getMergedRange(o, n) : null;
            return e === this.cells && c ? !c.isSingleCell && (this.frozenRows > 0 || this.frozenColumns > 0) && (c.topRow < this.frozenRows && c.bottomRow >= this.frozenRows || c.leftCol < this.frozenColumns && c.rightCol >= this.frozenColumns) ? (s = c.topRow, r = c.bottomRow, a = c.leftCol, h = c.rightCol, o >= this.frozenRows && c.topRow < this.frozenRows && (s = this.frozenRows), o < this.frozenRows && c.bottomRow >= this.frozenRows && (r = this.frozenRows - 1), r >= this.rows.length && (r = this.rows.length - 1), n >= this.frozenColumns && c.leftCol < this.frozenColumns && (a = this.frozenColumns), n < this.frozenColumns && c.rightCol >= this.frozenColumns && (h = this.frozenColumns - 1), h >= this.columns.length && (h = this.columns.length - 1), new t.CellRange(s, a, r, h)) : c.bottomRow >= this.rows.length ? new t.CellRange(c.topRow, c.leftCol, this.rows.length - 1, c.rightCol) : c.rightCol >= this.columns.length ? new t.CellRange(c.topRow, c.leftCol, c.bottomRow, this.columns.length - 1) : c.clone() : n >= 0 && this.columns && this.columns.length > n && o >= 0 && this.rows && this.rows.length > o ? i.prototype.getMergedRange.call(this, e, o, n, l) : null
          }, s.prototype.evaluate = function(t, o, n, i) {
            void 0 === i && (i = !0);
            var l = this._evaluate(t, o, n);
            return i && null != l && !e.isPrimitive(l) ? l.value : l
          }, s.prototype.getCellValue = function(t, o, n, i) {
            void 0 === n && (n = !1);
            var l, s, r = i && i !== this.selectedSheet ? i.grid.columns[o] : this.columns[o],
              a = this._getCellStyle(t, o, i);
            return l = a && a.format ? a.format : "", s = i && i !== this.selectedSheet ? i.grid.getCellData(t, o, !1) : this.getCellData(t, o, !1), e.isString(s) && "=" === s[0] && (s = this._evaluate(s, n ? l : "", i, t, o)), n ? (l || null == s || e.isPrimitive(s) || (l = s.format), s = this._formatEvaluatedResult(s, r, l)) : null == s || e.isPrimitive(s) || (s = s.value), null == s ? "" : s
          }, s.prototype.showFunctionList = function(t) {
            var o, n, i = this,
              l = i._cumulativeOffset(t),
              s = i._cumulativeOffset(i._root);
            i._enableFormulas && (i._functionTarget = e.tryCast(t, HTMLInputElement), i._functionTarget && i._functionTarget.value && "=" === i._functionTarget.value[0] ? (i._functionList._cv.filter = function(e) {
              var t, o = e.actualvalue.toLowerCase(),
                n = i._getCurrentFormulaIndex(i._functionTarget.value);
              return -1 === n && (n = 0), (t = i._functionTarget.value.substr(n + 1).trim().toLowerCase()).length > 0 && 0 === o.indexOf(t) || "=" === i._functionTarget.value
            }, i._functionList.selectedIndex = 0, o = l.y + t.clientHeight + 2 + (e.hasClass(t, "wj-grid-editor") ? this._ptScrl.y : 0), n = l.x + (e.hasClass(t, "wj-grid-editor") ? this._ptScrl.x : 0), e.setCss(i._functionListHost, {
              height: i._functionList._cv.items.length > 5 ? "218px" : "auto",
              display: i._functionList._cv.items.length > 0 ? "block" : "none",
              top: "",
              left: ""
            }), i._functionListHost.scrollTop = 0, i._functionListHost.offsetHeight + o > s.y + i._root.offsetHeight ? o = o - t.clientHeight - i._functionListHost.offsetHeight - 5 : o += 5, i._functionListHost.offsetWidth + n > s.x + i._root.offsetWidth && (n = s.x + i._root.offsetWidth - i._functionListHost.offsetWidth), e.setCss(i._functionListHost, {
              top: o,
              left: n
            })) : i.hideFunctionList())
          }, s.prototype.hideFunctionList = function() {
            this._functionListHost.style.display = "none"
          }, s.prototype.selectPreviousFunction = function() {
            this._functionList.selectedIndex > 0 && this._functionList.selectedIndex--
          }, s.prototype.selectNextFunction = function() {
            this._functionList.selectedIndex < this._functionList.itemsSource.length && this._functionList.selectedIndex++
          }, s.prototype.applyFunctionToCell = function() {
            var e, t = this;
            t._functionTarget && (-1 === (e = t._getCurrentFormulaIndex(t._functionTarget.value)) ? e = t._functionTarget.value.indexOf("=") : e += 1, t._functionTarget.value = t._functionTarget.value.substring(0, e) + t._functionList.selectedValue + "(", "=" !== t._functionTarget.value[0] && (t._functionTarget.value = "=" + t._functionTarget.value), t._functionTarget.focus(), t.hideFunctionList())
          }, s.prototype.save = function(e, t) {
            var o = this._saveToWorkbook(t);
            return e && o.save(e), o
          }, s.prototype.saveAsync = function(e, t, o, n) {
            var i = this._saveToWorkbook(n);
            return i.saveAsync(e, t, o), i
          }, s.prototype.saveToWorkbookOM = function(e) {
            return this._saveToWorkbook(e)._serialize()
          }, s.prototype.load = function(t) {
            var o, n, i = this;
            if (t instanceof Blob)(n = new FileReader).onload = function() {
              var t = n.result;
              t = e.xlsx.Workbook._base64EncArr(new Uint8Array(t)), (o = new e.xlsx.Workbook).load(t), i._loadFromWorkbook(o)
            }, n.readAsArrayBuffer(t);
            else if (t instanceof e.xlsx.Workbook) i._loadFromWorkbook(t);
            else {
              if (t instanceof ArrayBuffer) t = e.xlsx.Workbook._base64EncArr(new Uint8Array(t));
              else if (!e.isString(t)) throw "Invalid workbook.";
              (o = new e.xlsx.Workbook).load(t), i._loadFromWorkbook(o)
            }
          }, s.prototype.loadAsync = function(t, o, n) {
            var i, l, s = this;
            if (t instanceof Blob)(l = new FileReader).onload = function() {
              var t = l.result;
              t = e.xlsx.Workbook._base64EncArr(new Uint8Array(t)), (i = new e.xlsx.Workbook).loadAsync(t, function(e) {
                s._loadFromWorkbook(e), o && o(e)
              }, n)
            }, l.readAsArrayBuffer(t);
            else if (t instanceof e.xlsx.Workbook) s._loadFromWorkbook(t), o && o(t);
            else {
              if (t instanceof ArrayBuffer) t = e.xlsx.Workbook._base64EncArr(new Uint8Array(t));
              else if (!e.isString(t)) throw "Invalid workbook.";
              (i = new e.xlsx.Workbook).loadAsync(t, function(e) {
                s._loadFromWorkbook(e), o && o(e)
              }, n)
            }
          }, s.prototype.loadFromWorkbookOM = function(t) {
            var o;
            t instanceof e.xlsx.Workbook ? o = t : (o = new e.xlsx.Workbook)._deserialize(t), this._loadFromWorkbook(o)
          }, s.prototype.undo = function() {
            var e = this;
            setTimeout(function() {
              e._undoStack.undo()
            }, 100)
          }, s.prototype.redo = function() {
            var e = this;
            setTimeout(function() {
              e._undoStack.redo()
            }, 100)
          }, s.prototype.select = function(o, n) {
            void 0 === n && (n = !0);
            var l, s, r;
            if (e.isNumber(o) && e.isNumber(n) && (o = new t.CellRange(o, n), n = !0), o.rowSpan !== this.rows.length && o.columnSpan !== this.columns.length)
              for (s = o.topRow; s <= o.bottomRow; s++)
                for (r = o.leftCol; r <= o.rightCol; r++)(l = this.getMergedRange(this.cells, s, r)) && !o.equals(l) && (o.row <= o.row2 ? (o.row = Math.min(o.topRow, l.topRow), o.row2 = Math.max(o.bottomRow, l.bottomRow)) : (o.row = Math.max(o.bottomRow, l.bottomRow), o.row2 = Math.min(o.topRow, l.topRow)), o.col <= o.col2 ? (o.col = Math.min(o.leftCol, l.leftCol), o.col2 = Math.max(o.rightCol, l.rightCol)) : (o.col = Math.max(o.rightCol, l.rightCol), o.col2 = Math.min(o.leftCol, l.leftCol)));
            this._isPasting && this.collectionView && (this.collectionView._pendingRefresh = !1), i.prototype.select.call(this, o, n)
          }, s.prototype.addCustomFunction = function(t, o, n, i, l) {
            e._deprecated("addCustomFunction", "addFunction"), this._calcEngine.addCustomFunction(t, o, i, l), this._addCustomFunctionDescription(t, n)
          }, s.prototype.addFunction = function(e, t, o, n, i) {
            this._calcEngine.addFunction(e, t, n, i), this._addCustomFunctionDescription(e, o)
          }, s.prototype.dispose = function() {
            var e = window.navigator.userAgent;
            this._needCopyToSheet = !1, this.removeEventListener(document.body, "mousemove", this._mouseMoveHdl), this.removeEventListener(document.body, "keydown", this._keydownHdl), this.removeEventListener(document.body, "click", this._clickHdl), (e.match(/iPad/i) || e.match(/iPhone/i)) && (this.removeEventListener(document.body, "touchstart", this._touchStartHdl), this.removeEventListener(document.body, "touchend", this._touchEndHdl)), this.hideFunctionList(), i.prototype.dispose.call(this)
          }, s.prototype.getClipString = function(o) {
            var n, l, s, r, a, h, c = "",
              d = !0;
            if (!this.selectedSheet) return i.prototype.getClipString.call(this, o);
            if (this._isCutting = !1, !o)
              if (this.selectedSheet.selectionRanges.length > 1) {
                if (this._isMultipleRowsSelected()) {
                  for (c = "", 0 === (n = this.selectedSheet.selectionRanges.slice(0)).length && (n = [this.selection]), n.sort(this._sortByRow), s = 0; s < n.length; s++) c && (c += "\n"), c += this.getClipString(n[s]);
                  return c
                }
                if (this._isMultipleColumnsSelected()) {
                  for (c = "", 0 === (n = this.selectedSheet.selectionRanges.slice(0)).length && (n = [this.selection]), n.sort(this._sortByColumn), l = (r = n[0]).topRow, d = !0; l <= r.bottomRow; l++)
                    for (d || (c += "\n"), d = !1, s = 0, f = !0; s < n.length; s++) a = n[s], f || (c += "\t"), f = !1, c += this.getClipString(new t.CellRange(l, a.col, l, a.col2));
                  return c
                }
                e.assert(!1, "Copy or Cut operation cannot be used on multiple selections.")
              } else switch (o = this.selection, this.selectionMode) {
                case t.SelectionMode.Row:
                case t.SelectionMode.RowRange:
                  o.col = 0, o.col2 = this.columns.length - 1;
                  break;
                case t.SelectionMode.ListBox:
                  o.col = 0, o.col2 = this.columns.length - 1;
                  for (var u = 0; u < this.rows.length; u++) this.rows[u].isSelected && this.rows[u].isVisible && (o.row = o.row2 = u, c && (c += "\n"), c += this.getClipString(o));
                  return c
              }
            if (!(o = e.asType(o, t.CellRange)).isValid) return "";
            for (var _ = o.topRow, d = !0; _ <= o.bottomRow; _++)
              if (this.rows[_].isVisible) {
                d || (c += "\n"), d = !1;
                for (var g = o.leftCol, f = !0; g <= o.rightCol; g++) this.columns[g].isVisible && (f || (c += "\t"), f = !1, (h = (h = this.getCellValue(_, g, !0).toString()).replace(/\t/g, " ")).indexOf("\n") > -1 && (h = '"' + h.replace(/"/g, '""') + '"'), c += h)
              }
            return c
          }, s.prototype.setClipString = function(o, n) {
            var l, s, r, a, h, c, d, u, _, g, f, p, w, m, C, b, y, S, R, v, x, T, I, k = null == n,
              A = !1,
              E = !1;
            if (this.selectedSheet) {
              if (n = n ? e.asType(n, t.CellRange) : this.selection, (o = e.asString(o).replace(/\r\n/g, "\n").replace(/\r/g, "\n")) && "\n" == o[o.length - 1] && (o = o.substring(0, o.length - 1)), x = o, u = this._edtHdl._parseClipString(e.asString(o)), k && !n.isSingleCell && u.length && this._edtHdl._expandClipRows(u, n), E = this._containsMultiLineText(u), (!this._copiedRanges || 0 === this._copiedRanges.length || x.trim() !== this._getRangeString(this._copiedRanges, this._copiedSheet).trim() && !this._containsRandFormula(this._copiedRanges, this._copiedSheet) || this._cutValue) && !E) return x !== this._cutValue && (this._cutValue = null, i.prototype.setClipString.call(this, o)), this._copiedRanges = null, void(this._copiedSheet = null);
              if (T = this._getRangeString(this._copiedRanges, this._copiedSheet, !1), (T = e.asString(T).replace(/\r\n/g, "\n").replace(/\r/g, "\n")) && "\n" == T[T.length - 1] && (T = T.substring(0, T.length - 1)), u = this._edtHdl._parseClipString(e.asString(T)), k && !n.isSingleCell && u.length && this._edtHdl._expandClipRows(u, n), (a = n.topRow + u.length - 1) >= this.rows.length && (a = this.rows.length - 1), (h = n.leftCol + u[0].length - 1) >= this.columns.length && (h = this.columns.length - 1), l = new t.CellRange(n.topRow, n.leftCol, a, h), !this.onPasting(new t.CellRangeEventArgs(this.cells, l))) return this._cutValue = null, this._copiedRanges = null, this._copiedSheet = null, !1;
              if (this.beginUpdate(), E || !this._copiedRanges || this._copiedRanges.length > 1 || 0 === this._copiedRanges.length)
                for (s = n.topRow, c = (S = this._copiedRanges && this._copiedRanges.length > 1 ? this._copiedRanges[0] : new t.CellRange).topRow, f = 0; f < u.length && s < this.rows.length; f++, s++)
                  if (this.rows[s].isVisible) {
                    for (_ = u[f], d = S.leftCol, m = (r = n.leftCol) - d, p = 0; p < _.length && r < this.columns.length; p++, r++) this.columns[r].isVisible ? (g = _[p], this.columns[r].isReadOnly || this.rows[s].isReadOnly || (A = this._postSetClipStringProcess(g, s, r, c, d), l.row2 = Math.max(l.row2, s), l.col2 = Math.max(l.col2, r)), d >= 0 && d++) : p--;
                    c >= 0 && c++
                  } else f--;
              else if (this._copiedRanges && 1 === this._copiedRanges.length)
                for (R = 0, (S = this.selectionMode === t.SelectionMode.ListBox ? this._getSelectionForListBoxMode(this._copiedSheet.grid) : this._copiedRanges[0]).rowSpan > n.rowSpan ? S.rowSpan : n.rowSpan, S.columnSpan > n.columnSpan ? S.columnSpan : n.columnSpan, s = n.topRow, f = 0; f < u.length && s < this.rows.length; f++, s++)
                  if (v = 0, this.rows[s].isVisible) {
                    for (; this._copiedSheet.grid.rows[S.topRow + R] && !this._copiedSheet.grid.rows[S.topRow + R].isVisible;) R++;
                    if (R >= S.rowSpan && (R %= S.rowSpan), !this._copiedSheet.grid.rows[S.topRow + R]) break;
                    for (w = s - S.topRow - R, _ = u[f], r = n.leftCol, p = 0; p < _.length && r < this.columns.length; p++, r++)
                      if (this.columns[r] && this.columns[r].isVisible) {
                        if (v >= S.columnSpan && (v %= S.columnSpan), !this._copiedSheet.grid.columns[S.leftCol + v]) break;
                        if (m = r - S.leftCol - v, !this.columns[r].isReadOnly && !this.rows[s].isReadOnly) {
                          if (null != this._copiedSheet.grid.columns[S.leftCol + v].dataMap, g = _[p], I = this._getCellStyle(S.topRow + R, S.leftCol + v, this._copiedSheet), g && "string" == typeof g && "=" === g[0] && (0 !== w || 0 !== m)) try {
                            C = this._calcEngine._parse(g), b = new t.CellRange(S.topRow + R, S.leftCol + v), y = new t.CellRange(S.topRow + R + w, S.leftCol + v + m), C._moveCellRangeExp(this.selectedSheetIndex, b, y, !1, !0) && (g = "=" + C._getStringExpression())
                          } catch (e) {}
                          this._copiedSheet.grid.columns[S.leftCol + v].format && !this.columns[r].format && (I ? I.format || (I.format = this._copiedSheet.grid.columns[S.leftCol + v].format) : I = {
                            format: this._copiedSheet.grid.columns[S.leftCol + v].format
                          }), A = this._postSetClipStringProcess(g, s, r, S.topRow + R, S.leftCol + v, I), l.row2 = Math.max(l.row2, s), l.col2 = Math.max(l.col2, r)
                        }
                        v++
                      } else p--;
                    R++
                  } else f--;
              this._isCutting && (this._delCutData(u.length, u[0].length), this._isCutting = !1, this._cutValue = x, this._copiedRanges = null, this._copiedSheet = null), this.endUpdate(), this.collectionView && A && this.collectionView.refresh(), this.select(l), this.onPasted(new t.CellRangeEventArgs(this.cells, l))
            } else i.prototype.setClipString.call(this, o, n)
          }, s.prototype.getBuiltInTableStyle = function(e) {
            var t;
            return null == this._builtInTableStylesCache && (this._builtInTableStylesCache = {}), null == (t = this._builtInTableStylesCache[e.toLowerCase()]) && (t = this._createBuiltInTableStyle(e), Object.freeze(t), this._builtInTableStylesCache[e.toLowerCase()] = t), t
          }, s.prototype._getCvIndex = function(e) {
            return e > -1 && this.collectionView ? this.rows[e] instanceof h ? e : i.prototype._getCvIndex.call(this, e) : -1
          }, s.prototype._bindGrid = function(e) {
            this.itemsSource && i.prototype._bindGrid.call(this, e)
          }, s.prototype._init = function() {
            var n = this,
              i = this,
              l = window.navigator.userAgent,
              s = function(e) {
                document.removeEventListener("mouseup", s), i._mouseUp(e)
              };
            i.hostElement.setAttribute("tabindex", "-1"), i._divContainer = i.hostElement.querySelector('[wj-part="container"]'), i._tabHolder = new o._TabHolder(i.hostElement.querySelector('[wj-part="tab-holder"]'), i), i._contextMenu = new o._ContextMenu(i.hostElement.querySelector('[wj-part="context-menu"]'), i), i._gpCells = new a(i, t.CellType.Cell, i.rows, i.columns, i._eCt), i._gpCHdr = new a(i, t.CellType.ColumnHeader, i._hdrRows, i.columns, i._eCHdrCt), i._gpRHdr = new a(i, t.CellType.RowHeader, i.rows, i._hdrCols, i._eRHdrCt), i._gpTL = new a(i, t.CellType.TopLeft, i._hdrRows, i._hdrCols, i._eTLCt), i._syncSelection = i._flexSheetSyncSelection.bind(i), i._sortManager = new o.SortManager(i), i._filter = new o.FlexSheetFilter(i), i._filter.filterApplied.addHandler(function() {
              i.selectedSheet && (i.selectedSheet._filterDefinition = i._filter.filterDefinition, i.selectedSheet.itemsSource && (i.selectedSheet._storeRowSettings(), i.selectedSheet._setRowSettings()))
            }), i._calcEngine = new o._CalcEngine(i), i._calcEngine.unknownFunction.addHandler(function(e, t) {
              i.onUnknownFunction(t)
            }, i), i._initFuncsList(), i._undoStack = new o.UndoStack(i), i.loadedRows.addHandler(function() {
              if (i.collectionView && !(i.rows[0] instanceof h)) {
                var e, o = new h;
                o.isReadOnly = !0;
                for (var n = 0; n < i.columns.length; n++) e = i.columns[n], o._ubv || (o._ubv = {}), o._ubv[e._hash] = e.header;
                i.rows[0] instanceof t._NewRowTemplate && i.newRowAtTop ? i.rows.insert(1, o) : i.rows.insert(0, o)
              }
              i._filter && i._filter.apply()
            }), i.itemsSourceChanged.addHandler(function() {
              var e;
              for (e = 0; e < i.columns.length; e++) i.columns[e].isRequired = !1;
              i.collectionView && i.collectionView.currentChanged.removeHandler(n._cvCurrentChanged, n)
            }), i.copied.addHandler(function(t, o) {
              var n;
              i.selectedSheet && (i._copiedSheet = i.selectedSheet, i._needCopyToSheet = !0, i.selectedSheet.selectionRanges.length > 1 ? i._isMultipleRowsSelected() ? ((n = i.selectedSheet.selectionRanges.slice(0)).sort(i._sortByRow), i._copiedRanges = n) : i._isMultipleColumnsSelected() ? ((n = i.selectedSheet.selectionRanges.slice(0)).sort(i._sortByColumn), i._copiedRanges = n) : e.assert(!1, "Copy operation cannot be used on multiple selections.") : i._copiedRanges = [o.range])
            }), i.rows.collectionChanged.addHandler(function(e, o) {
              i._clearForEmptySheet("rows"), i.itemsSource || !i.selectedSheet || !i._needCopyToSheet || i._isCopying || i._isUndoing || o.item instanceof t._NewRowTemplate || i._copyRowsToSelectedSheet()
            }, i), i.columns.collectionChanged.addHandler(function(e, t) {
              i._clearForEmptySheet("columns"), i.selectedSheet && i._needCopyToSheet && !i._isCopying && !i._isUndoing && i._copyColumnsToSelectedSheet()
            }, i), i.definedNames.collectionChanged.addHandler(function(t, o) {
              if (o.action === e.collections.NotifyCollectionChangedAction.Add || o.action === e.collections.NotifyCollectionChangedAction.Change) {
                var n = o.action === e.collections.NotifyCollectionChangedAction.Add ? "inserted" : "updated";
                if (!(o.item instanceof c)) throw i.definedNames.remove(o.item), "Invalid defined name item object was " + n + ".  The DefinedName instance should be " + n + " in the definedNames array.";
                if (!o.item || !o.item.name || null == o.item.value) throw i.definedNames.remove(o.item), "Invalid defined name was " + n + ".";
                if (null != o.item.sheetName && !i._validateSheetName(o.item.sheetName)) throw i.definedNames.remove(o.item), "The sheet name (" + o.item.sheetName + ") does not exist in FlexSheet.";
                if (i._checkExistDefinedName(o.item.name, o.item.sheetName, o.index)) throw i.definedNames.remove(o.item), "The " + o.item.name + " already existed in definedNames."
              }
            }), i.addEventListener(i.hostElement, "mousedown", function(e) {
              document.addEventListener("mouseup", s), i._isDescendant(i._divContainer, e.target) && i._mouseDown(e)
            }, !0), i.addEventListener(i.hostElement, "drop", function() {
              i._columnHeaderClicked = !1
            }), i.addEventListener(i.hostElement, "contextmenu", function(o) {
              var n, l, s, r, a, h, c, d, u;
              if (!o.defaultPrevented && i.selectedSheet) {
                if (!i.activeEditor && (i._isContextMenuKeyDown && i.selection.row > -1 && i.selection.col > -1 && i.rows.length > 0 && i.columns.length > 0 ? (s = i.columns[i.selection.col], l = i.rows[i.selection.row], c = i._cumulativeOffset(i.hostElement), d = i._cumulativeScrollOffset(i.hostElement), r = s.pos + i._eCt.offsetLeft + c.x + s.renderSize / 2 + i._ptScrl.x, a = l.pos + i._eCt.offsetTop + c.y + l.renderSize / 2 + i._ptScrl.y, h = new e.Point(r - d.x, a - d.y), n = i.hitTest(r, a)) : n = i.hitTest(o), o.preventDefault(), n && n.cellType !== t.CellType.None)) {
                  switch (n.cellType) {
                    case t.CellType.TopLeft:
                      u = new t.CellRange(0, 0, i.rows.length - 1, i.columns.length - 1);
                      break;
                    case t.CellType.RowHeader:
                      u = new t.CellRange(n.row, 0, n.row, i.columns.length - 1);
                      break;
                    case t.CellType.ColumnHeader:
                      u = new t.CellRange(i.itemsSource && i.rows[0] && !i.rows[0].isVisible ? 1 : 0, n.col, i.rows.length - 1, n.col);
                      break;
                    case t.CellType.Cell:
                      u = new t.CellRange(n.row, n.col)
                  }
                  i.selection.contains(u) && (u = null), u && (i.selectedSheet && (i.selectedSheet.selectionRanges.clear(), i.selectedSheet.selectionRanges.push(u)), i.selection = u), i._contextMenu.show(o, h)
                }
                i._isContextMenuKeyDown = !1
              }
            }), i.prepareCellForEdit.addHandler(i._prepareCellForEditHandler, i), i.cellEditEnded.addHandler(function(e, t) {
              (!t.data || 46 !== t.data.keyCode && 8 !== t.data.keyCode) && setTimeout(function() {
                i.hideFunctionList()
              }, 200)
            }), i.cellEditEnding.addHandler(function(e, t) {
              if (!t.data || 46 !== t.data.keyCode && 8 !== t.data.keyCode) {
                var n, l, s;
                n = i.getCellData(t.row, t.col, !1), l = i.cellFactory.getEditorValue(i), null != n && "" !== n || null == l || "" === l || isNaN(+l) || (s = i._updateFormulaBoundaryForEditingCell(t.row, t.col), i._undoStack._pendingAction && i._undoStack._pendingAction instanceof o._EditAction && (i._undoStack._pendingAction._affectedFormulas = s)), i._clearCalcEngine()
              }
            }), i.rowEditEnding.addHandler(function(e, t) {
              var o = i.collectionView;
              o && (i._orgRowVisible = i.rows[t.row].visible, o.sortDescriptions.length > 0 && (i.selectedSheet._dataView = o._view.slice(), i.selectedSheet._scrollPosition = i.scrollPosition))
            }), i.rowEditEnded.addHandler(function(t, o) {
              var n = i.collectionView;
              n && (n.sortDescriptions.length > 0 && (n._view = i.selectedSheet._dataView, n._pgView = n._getPageView(), i.itemsSource instanceof e.collections.CollectionView || (i.selectedSheet.grid.collectionView._view = i.selectedSheet._dataView, i.selectedSheet.grid.collectionView._pgView = i.selectedSheet.grid.collectionView._getPageView()), i._bindGrid(!1), i.selectedSheet.grid._bindGrid(!1), i.scrollPosition = i.selectedSheet._scrollPosition), i.rows[o.row].visible = i._orgRowVisible)
            }), i.pasting.addHandler(function() {
              i._needCopyToSheet = !1, i._isPasting = !0
            }), i.pasted.addHandler(function() {
              i.selection;
              i._needCopyToSheet = !0, i._isPasting = !1, i._clearCalcEngine()
            }), i.resizingColumn.addHandler(function() {
              i._resizing = !0
            }), i.resizingRow.addHandler(function() {
              i._resizing = !0
            }), i.resizedColumn.addHandler(function() {
              i._resizing = !1
            }), i.resizedRow.addHandler(function() {
              i._resizing = !1
            }), i.selectionChanged.addHandler(function() {
              var e = i.selection;
              i._isCopying || !e.isValid || i.containsFocus() || i.hostElement.focus(), e.rightCol >= i.columns.length && (i.selection = new t.CellRange(e.row, Math.min(e.col, i.columns.length - 1), e.row2, Math.min(i.selection.col2, i.columns.length - 1))), i._enableMulSel || i._isCopying || !i.selectedSheet || i.selectedSheet.selectionRanges.clear()
            }), i.addEventListener(i.hostElement, "keydown", function(o) {
              var n, l, s = !1,
                r = !1;
              if (o.ctrlKey) {
                if (89 === o.keyCode && (i.finishEditing(), i.redo(), o.preventDefault()), 90 === o.keyCode && (i.finishEditing(), i.undo(), o.preventDefault()), i.selectedSheet && 65 === o.keyCode && (i.selectedSheet.selectionRanges.clear(), i.selectedSheet.selectionRanges.push(i.selection)), 67 !== o.keyCode && 45 != o.keyCode || (i.activeEditor && (i._copiedRanges = null, i._copiedSheet = null), i._cutValue = null), 88 === o.keyCode && !i.activeEditor) {
                  if (o.stopPropagation(), i.selectionMode === t.SelectionMode.ListBox)
                    for (var a = 0; a < i.rows.length; a++)
                      if (i.rows[a].isSelected)
                        if (s) {
                          if (r) continue;
                          e.assert(!1, "Cut operation cannot be used on multiple selections.")
                        } else s = !0, r = !0;
                      else r && (r = !1);
                  i.selectedSheet.selectionRanges.length > 1 ? e.assert(!1, "Cut operation cannot be used on multiple selections.") : (i.finishEditing(), n = new t.CellRangeEventArgs(i.cells, i.selection), i.onCopying(n) && (i._cutValue = null, l = i.getClipString(), i._isCutting = !0, e.Clipboard.copy(l), i.onCopied(n)))
                }
                o.keyCode === e.Key.Space && i.selection.isValid && i.selectionMode === t.SelectionMode.CellRange && i.select(new t.CellRange(0, i.selection.col, i.rows.length - 1, i.selection.col))
              }
              o.keyCode === e.Key.Escape && i._contextMenu.hide(), (93 === o.keyCode || o.shiftKey && 121 === o.keyCode) && (i._contextMenu.visible && !e.isFirefox() ? i._isContextMenuKeyDown = !1 : i._isContextMenuKeyDown = !0)
            }), i.addEventListener(document.body, "keydown", i._keydownHdl, !0), i.addEventListener(document.body, "click", i._clickHdl), i.addEventListener(document.body, "mousemove", i._mouseMoveHdl), (l.match(/iPad/i) || l.match(/iPhone/i)) && (i.addEventListener(document.body, "touchstart", i._touchStartHdl), i.addEventListener(document.body, "touchend", i._touchEndHdl)), i.addEventListener(i.hostElement, "drop", function() {
              i._htDown = null
            })
          }, s.prototype._flexSheetSyncSelection = function(o) {
            if (this.collectionView && this.selectionMode != t.SelectionMode.None) {
              var n = this.selection,
                i = n.row > -1 && n.row < this.rows.length ? this.rows[n.row] : null,
                l = i ? i.dataItem : null;
              if ((i instanceof h || this.newRowAtTop && i instanceof t._NewRowTemplate || l instanceof e.collections.CollectionViewGroup) && (l = null), (l != this.collectionView.currentItem || o) && (!this.editableCollectionView || !this.editableCollectionView.currentAddItem)) {
                var s = this._getRowIndex(this.collectionView.currentPosition);
                s != n.row && (n.row = n.row2 = s, this.select(n, !1), this.selectionMode && this.scrollIntoView(n.row, -1))
              }
            }
          }, s.prototype._initFuncsList = function() {
            var t = this;
            t._functionListHost = document.createElement("div"), e.addClass(t._functionListHost, "wj-flexsheet-formula-list"), document.querySelector("body").appendChild(t._functionListHost), t._functionListHost.style.display = "none", t._functionListHost.style.position = "absolute", t._functionList = new e.input.ListBox(t._functionListHost), t._functionList.isContentHtml = !0, t._functionList.itemsSource = t._getFunctions(), t._functionList.displayMemberPath = "displayValue", t._functionList.selectedValuePath = "actualvalue", t.addEventListener(t._functionListHost, "click", t.applyFunctionToCell.bind(t)), t.addEventListener(t._functionListHost, "keydown", function(o) {
              o.keyCode === e.Key.Escape && (t.hideFunctionList(), t.hostElement.focus(), o.preventDefault()), o.keyCode === e.Key.Enter && (t.applyFunctionToCell(), t.hostElement.focus(), o.preventDefault(), o.stopPropagation())
            })
          }, s.prototype._getFunctions = function() {
            for (var e, t = [], o = 0; o < n.length; o++) e = n[o], t.push({
              displayValue: '<div class="wj-flexsheet-formula-name">' + e.name + '</div><div class="wj-flexsheet-formula-description">' + e.description + "</div>",
              actualvalue: e.name
            });
            return t
          }, s.prototype._addCustomFunctionDescription = function(e, t) {
            for (var o = {
              displayValue: '<div class="wj-flexsheet-formula-name">' + e + "</div>" + (t ? '<div class="wj-flexsheet-formula-description">' + t + "</div>" : ""),
              actualvalue: e
            }, n = this._functionList.itemsSource, i = -1, l = 0; l < n.length; l++)
              if (n[l].actualvalue === e) {
                i = l;
                break
              }
            i > -1 ? n.splice(i, 1, o) : n.push(o)
          }, s.prototype._getCurrentFormulaIndex = function(e) {
            var t = -1;
            return ["+", "-", "*", "/", "^", "(", "&"].forEach(function(o) {
              var n = e.lastIndexOf(o);
              n > t && (t = n)
            }), t
          }, s.prototype._prepareCellForEditHandler = function() {
            var t = this,
              o = t._edtHdl._edt;
            o && (t.addEventListener(o, "keydown", function(o) {
              if (t.isFunctionListOpen) switch (o.keyCode) {
                case e.Key.Up:
                  t.selectPreviousFunction(), o.preventDefault(), o.stopPropagation();
                  break;
                case e.Key.Down:
                  t.selectNextFunction(), o.preventDefault(), o.stopPropagation();
                  break;
                case e.Key.Tab:
                case e.Key.Enter:
                  t.applyFunctionToCell(), o.preventDefault(), o.stopPropagation();
                  break;
                case e.Key.Escape:
                  t.hideFunctionList(), o.preventDefault(), o.stopPropagation()
              }
            }), t.addEventListener(o, "keyup", function(n) {
              (n.keyCode > 40 || n.keyCode < 32) && n.keyCode !== e.Key.Tab && n.keyCode !== e.Key.Escape && setTimeout(function() {
                t.showFunctionList(o)
              }, 0)
            }))
          }, s.prototype._addSheet = function(e, t, n, i, l) {
            var s = this,
              r = new o.Sheet(s, l, e, t, n);
            return s.sheets.isValidSheetName(r) || r._setValidName(s.sheets.getValidSheetName(r)), r.nameChanged.addHandler(function(e, t) {
              s._updateDefinedNameWithSheetRefUpdating(t.oldValue, t.newValue)
            }), "number" == typeof i ? (i < 0 && (i = 0), i >= s.sheets.length && (i = s.sheets.length)) : i = s.sheets.length, s.sheets.insert(i, r), r
          }, s.prototype._showSheet = function(e) {
            !this.sheets || !this.sheets.length || e >= this.sheets.length || e < 0 || e === this.selectedSheetIndex || this.sheets[e] && !this.sheets[e].visible || (this.finishEditing(), this._clearCalcEngine(), this._isCopying = !0, this.selectedSheetIndex > -1 && this.selectedSheetIndex < this.sheets.length && this._copyTo(this.sheets[this.selectedSheetIndex]), this.sheets[e] && (this._selectedSheetIndex = e, this._copyFrom(this.sheets[e])), this._isCopying = !1, this._filter.closeEditor())
          }, s.prototype._selectedSheetChange = function(e, t) {
            this._showSheet(t.newValue), this.invalidate(!0), this.onSelectedSheetChanged(t)
          }, s.prototype._sourceChange = function(t, o) {
            if (o.action === e.collections.NotifyCollectionChangedAction.Add || o.action === e.collections.NotifyCollectionChangedAction.Change) o.item._attachOwner(this), o.action === e.collections.NotifyCollectionChangedAction.Add ? (this._addingSheet = !0, o.index <= this.selectedSheetIndex && (this._selectedSheetIndex += 1)) : o.index === this.selectedSheetIndex && this._copyFrom(o.item, !0), this.selectedSheetIndex = o.index;
            else if (o.action === e.collections.NotifyCollectionChangedAction.Reset) {
              for (var n = 0; n < this.sheets.length; n++) this.sheets[n]._attachOwner(this);
              this.sheets.length > 0 ? (0 === this.selectedSheetIndex && this._copyFrom(this.selectedSheet, !0), this.selectedSheetIndex = 0) : (this.rows.clear(), this.columns.clear(), this._selectedSheetIndex = -1)
            } else this.sheets.length > 0 ? this.selectedSheetIndex >= this.sheets.length ? this.selectedSheetIndex = 0 : this.selectedSheetIndex > o.index && (this._selectedSheetIndex -= 1) : (this.rows.clear(), this.columns.clear(), this._selectedSheetIndex = -1);
            this.invalidate(!0)
          }, s.prototype._sheetVisibleChange = function(e, t) {
            t.item.visible || t.index === this.selectedSheetIndex && (this.selectedSheetIndex === this.sheets.length - 1 ? this.selectedSheetIndex = t.index - 1 : this.selectedSheetIndex = t.index + 1)
          }, s.prototype._applyStyleForCell = function(e, t, o, n) {
            void 0 === n && (n = !1);
            var i, l, s, r = this,
              a = r.rows[e];
            null == a || a instanceof h || !a.isVisible && !n || (s = e * r.columns.length + t, (l = r.selectedSheet._getMergedRange(e, t)) && (s = l.topRow * r.columns.length + l.leftCol), (i = r.selectedSheet._styledCells[s]) ? (i.className = "normal" === o.className ? "" : o.className || i.className, i.textAlign = o.textAlign || i.textAlign, i.verticalAlign = o.verticalAlign || i.verticalAlign, i.fontFamily = o.fontFamily || i.fontFamily, i.fontSize = o.fontSize || i.fontSize, i.backgroundColor = o.backgroundColor || i.backgroundColor, i.color = o.color || i.color, i.fontStyle = "none" === o.fontStyle ? "" : o.fontStyle || i.fontStyle, i.fontWeight = "none" === o.fontWeight ? "" : o.fontWeight || i.fontWeight, i.textDecoration = "none" === o.textDecoration ? "" : o.textDecoration || i.textDecoration, i.format = o.format || i.format, i.whiteSpace = o.whiteSpace || i.whiteSpace, i.borderTopColor = o.borderTopColor || i.borderTopColor, i.borderTopStyle = o.borderTopStyle || i.borderTopStyle, i.borderTopWidth = o.borderTopWidth || i.borderTopWidth, i.borderBottomColor = o.borderBottomColor || i.borderBottomColor, i.borderBottomStyle = o.borderBottomStyle || i.borderBottomStyle, i.borderBottomWidth = o.borderBottomWidth || i.borderBottomWidth, i.borderLeftColor = o.borderLeftColor || i.borderLeftColor, i.borderLeftStyle = o.borderLeftStyle || i.borderLeftStyle, i.borderLeftWidth = o.borderLeftWidth || i.borderLeftWidth, i.borderRightColor = o.borderRightColor || i.borderRightColor, i.borderRightStyle = o.borderRightStyle || i.borderRightStyle, i.borderRightWidth = o.borderRightWidth || i.borderRightWidth) : r.selectedSheet._styledCells[s] = {
              className: o.className,
              textAlign: o.textAlign,
              verticalAlign: o.verticalAlign,
              fontStyle: o.fontStyle,
              fontWeight: o.fontWeight,
              fontFamily: o.fontFamily,
              fontSize: o.fontSize,
              textDecoration: o.textDecoration,
              backgroundColor: o.backgroundColor,
              color: o.color,
              format: o.format,
              whiteSpace: o.whiteSpace,
              borderTopColor: o.borderTopColor,
              borderTopStyle: o.borderTopStyle,
              borderTopWidth: o.borderTopWidth,
              borderBottomColor: o.borderBottomColor,
              borderBottomStyle: o.borderBottomStyle,
              borderBottomWidth: o.borderBottomWidth,
              borderLeftColor: o.borderLeftColor,
              borderLeftStyle: o.borderLeftStyle,
              borderLeftWidth: o.borderLeftWidth,
              borderRightColor: o.borderRightColor,
              borderRightStyle: o.borderRightStyle,
              borderRightWidth: o.borderRightWidth
            })
          }, s.prototype._checkCellFormat = function(e, t, o) {
            var n, i, l = e * this.columns.length + t;
            this.selectedSheet && ((n = this.selectedSheet._getMergedRange(e, t)) && (o.isMergedCell = !0, l = n.topRow * this.columns.length + n.leftCol), (i = this.selectedSheet._styledCells[l]) && (o.isBold = o.isBold || "bold" === i.fontWeight, o.isItalic = o.isItalic || "italic" === i.fontStyle, o.isUnderline = o.isUnderline || "underline" === i.textDecoration), e === this.selection.row && t === this.selection.col && (i && i.textAlign ? o.textAlign = i.textAlign : t > -1 && (o.textAlign = this.columns[t].getAlignment() || o.textAlign)))
          }, s.prototype._resetMergedRange = function(e) {
            var t, o, n, i, l = !1;
            for (t = e.topRow; t <= e.bottomRow; t++)
              for (o = e.leftCol; o <= e.rightCol; o++)(i = this.selectedSheet._getMergedRange(t, o)) && (l = !0, n = this.selectedSheet._mergedRanges.indexOf(i), this.selectedSheet._mergedRanges.splice(n, 1));
            return l
          }, s.prototype._updateCellsForUpdatingRow = function(e, t, o, n) {
            var i, l, s, r, a, h, c, d = e * this.columns.length;
            if (n)
              for (l = i = t * this.columns.length; l < d; l++) s = l - o * this.columns.length, (r = this.selectedSheet._styledCells[l]) && (l >= (t + o) * this.columns.length && (this.selectedSheet._styledCells[s] = r), delete this.selectedSheet._styledCells[l]);
            else
              for (i = t * this.columns.length - 1, l = d - 1; l > i; l--) s = l + this.columns.length * o, (r = this.selectedSheet._styledCells[l]) && (this.selectedSheet._styledCells[s] = r, delete this.selectedSheet._styledCells[l]);
            if (this.selectedSheet._mergedRanges.length > 0)
              for (h = this.selectedSheet._mergedRanges.length - 1; h >= 0; h--) a = this.selectedSheet._mergedRanges[h], n ? (c = t + o - 1) < a.topRow ? (a.row -= o, a.row2 -= o) : c >= a.topRow && c <= a.bottomRow ? t <= a.topRow && c >= a.bottomRow ? this.selectedSheet._mergedRanges.splice(h, 1) : t < a.topRow ? a.row <= a.row2 ? (a.row += t - a.topRow, a.row2 -= o) : (a.row2 += t - a.topRow, a.row -= o) : t === a.topRow && o === a.rowSpan ? this.selectedSheet._mergedRanges.splice(h, 1) : a.row <= a.row2 ? a.row2 -= o : a.row -= o : t <= a.topRow ? this.selectedSheet._mergedRanges.splice(h, 1) : t <= a.bottomRow && (a.row <= a.row2 ? a.row2 += t - a.bottomRow - 1 : a.row += t - a.bottomRow - 1) : t <= a.topRow ? (a.row += o, a.row2 += o) : t > a.topRow && t <= a.bottomRow && (a.row <= a.row2 ? a.row2 += o : a.row += o)
          }, s.prototype._updateCellsForUpdatingColumn = function(e, t, o, n) {
            var i, l, s, r, a, h, c, d = this.rows.length * e;
            if (n)
              for (i = t; i < d; i++) l = i - o * (Math.floor(i / e) + ((r = i % e) >= t ? 1 : 0)), (s = this.selectedSheet._styledCells[i]) && ((r < t || r >= t + o) && (this.selectedSheet._styledCells[l] = s), delete this.selectedSheet._styledCells[i]);
            else
              for (i = d - 1; i >= t; i--) l = i + Math.floor(i / e) * o + ((r = i % e) >= t ? 1 : 0), (s = this.selectedSheet._styledCells[i]) && (this.selectedSheet._styledCells[l] = s, delete this.selectedSheet._styledCells[i]);
            if (this.selectedSheet._mergedRanges.length > 0)
              for (h = this.selectedSheet._mergedRanges.length - 1; h >= 0; h--) a = this.selectedSheet._mergedRanges[h], n ? (c = t + o - 1) < a.leftCol ? (a.col -= o, a.col2 -= o) : c >= a.leftCol && c <= a.rightCol ? t <= a.leftCol && c >= a.rightCol ? this.selectedSheet._mergedRanges.splice(h, 1) : t < a.leftCol ? a.col <= a.col2 ? (a.col += t - a.leftCol, a.col2 -= o) : (a.col2 += t - a.leftCol, a.col -= o) : t === a.leftCol && o === a.columnSpan ? this.selectedSheet._mergedRanges.splice(h, 1) : a.col <= a.col2 ? a.col2 -= o : a.col -= o : t <= a.leftCol ? this.selectedSheet._mergedRanges.splice(h, 1) : t <= a.rightCol && (a.col <= a.col2 ? a.col2 -= a.rightCol - t + 1 : a.col -= a.rightCol - t + 1) : t <= a.leftCol ? (a.col += o, a.col2 += o) : t > a.leftCol && t <= a.rightCol && (a.col <= a.col2 ? a.col2 += o : a.col += o)
          }, s.prototype._cloneObject = function(t) {
            var o;
            if (null == t || !e.isObject(t)) return t;
            o = {};
            for (var n in t) t.hasOwnProperty(n) && null != t[n] && (t[n].clone ? o[n] = t[n].clone() : o[n] = this._cloneObject(t[n]));
            return o
          }, s.prototype._evaluate = function(e, t, o, n, i) {
            return e && e.length > 1 && this._enableFormulas ? (e = "=" === e[0] ? e : "=" + e, this._calcEngine.evaluate(e, t, o, n, i)) : e
          }, s.prototype._copyTo = function(e) {
            var o, n, i, l = this,
              s = e.grid.autoGenerateColumns;
            if (e._storeRowSettings(), i = this.selection.clone(), e.grid.selection = new t.CellRange, e.grid.rows.clear(), e.grid.columns.clear(), e.grid.columnHeaders.columns.clear(), e.grid.rowHeaders.rows.clear(), l.itemsSource) e.grid.autoGenerateColumns = !1, e.itemsSource = l.itemsSource, l.collectionView.sortDescriptions.length > 0 && (e._dataView = l.collectionView._view.slice()), e.grid.collectionView.beginUpdate();
            else
              for (e.itemsSource = null, n = 0; n < l.rows.length; n++) e.grid.rows.push(l.rows[n]);
            for (e._sortList = l.sortManager._committedList.slice(), e._getFilterSetting(), o = 0; o < l.columns.length; o++) e.grid.columns.push(l.columns[o]);
            e.grid.collectionView && (l._resetMappedColumns(e.grid), e.grid.collectionView.endUpdate()), e.grid.autoGenerateColumns = s, e.grid.frozenRows = l.frozenRows, e.grid.frozenColumns = l.frozenColumns, e.grid.allowAddNew = l.allowAddNew, null != l.newRowAtTop && (e.grid.newRowAtTop = l.newRowAtTop), e.grid.selection = i, e._scrollPosition = l.scrollPosition, setTimeout(function() {
              l._setFlexSheetToDirty(), l.refresh(!0)
            }, 10)
          }, s.prototype._copyFrom = function(o, n) {
            void 0 === n && (n = !0);
            var i, l, s, r, a, c, d, u = this,
              _ = u.autoGenerateColumns;
            if (u._isCopying = !0, u._dragable = !1, u.itemsSource = null, u.rows.clear(), u.columns.clear(), u.columnHeaders.columns.clear(), u.rowHeaders.rows.clear(), c = o.grid.selection.clone(), u.selection = new t.CellRange, o.selectionRanges.length > 1 && u.selectionMode === t.SelectionMode.CellRange && (u._enableMulSel = !0), o.itemsSource) u.autoGenerateColumns = !1, u.itemsSource = o.itemsSource, u.collectionView.beginUpdate();
            else
              for (l = 0; l < o.grid.rows.length; l++) u.rows.push(o.grid.rows[l]);
            for (u.sortManager.sortDescriptions.sourceCollection = o._sortList.slice(), u.sortManager._committedList = o._sortList.slice(), i = 0; i < o.grid.columns.length; i++)(r = o.grid.columns[i]).isRequired = !1, u.columns.push(r);
            for (u.collectionView && (u.collectionView.moveCurrentToPosition(u._getCvIndex(c.row)), u._resetMappedColumns(u), u.collectionView.endUpdate(), o._dataView && (u.collectionView._view = o._dataView, u.collectionView._pgView = u.collectionView._getPageView(), u.itemsSource instanceof e.collections.CollectionView || (o.grid.collectionView._view = o._dataView, o.grid.collectionView._pgView = o.grid.collectionView._getPageView()), u._bindGrid(!1), o.grid._bindGrid(!1)), u.collectionView.collectionChanged.addHandler(function(t, o) {
              o.action === e.collections.NotifyCollectionChangedAction.Reset && setTimeout(function() {
                u.invalidate()
              }, 10)
            }, u)), u.rows.length && u.columns.length && (u.selection = c), o._applyFilterSetting(), l = 0; l < u.rows.length; l++)(s = o._rowSettings[l]) && (d = (a = u.rows[l]) instanceof h, a.height = s.height, a.allowMerging = s.allowMerging, a.visible = s.visible, a instanceof t.GroupRow && (a.isCollapsed = !!s.isCollapsed), a.isSelected = !!s.isSelected, a.isReadOnly = d || !!s.readOnly);
            u.autoGenerateColumns = _, u.frozenRows = o.grid.frozenRows, u.frozenColumns = o.grid.frozenColumns, u._isCopying = !1, u._addingSheet ? (u._toRefresh && (clearTimeout(u._toRefresh), u._toRefresh = null), u._toRefresh = setTimeout(function() {
              u._setFlexSheetToDirty(), u.invalidate()
            }, 10), u._addingSheet = !1) : n && u.refresh(), u.scrollPosition = o._scrollPosition, u._ptScrl = o._scrollPosition
          }, s.prototype._resetMappedColumns = function(e) {
            var t, o, n = 0;
            if (e._mappedColumns = null, e.collectionView)
              for (o = e.collectionView.sortDescriptions; n < o.length; n++)(t = e.columns.getColumn(o[n].property)) && t.dataMap && (e._mappedColumns || (e._mappedColumns = {}), e._mappedColumns[t.binding] = t.dataMap)
          }, s.prototype._loadFromWorkbook = function(t) {
            var o, n, i, l, s = 0,
              r = this;
            if (null != t.sheets && 0 !== t.sheets.length) {
              if (r.beginUpdate(), r.clear(), r._reservedContent = t.reservedContent, o = t.sheets.length, t.colorThemes && t.colorThemes.length > 0)
                for (l = 0; l < this._colorThemes.length; l++) this._colorThemes[l] = t.colorThemes[l];
              for (; s < o; s++) {
                if (s > 0 && r.addUnboundSheet(), r.selectedSheet.grid.wj_sheetInfo = {}, e.grid.xlsx.FlexGridXlsxConverter.load(r.selectedSheet.grid, t, {
                  sheetIndex: s,
                  includeColumnHeaders: !1
                }), r.selectedSheet.name = r.selectedSheet.grid.wj_sheetInfo.name, r.selectedSheet.visible = r.selectedSheet.grid.wj_sheetInfo.visible, r.selectedSheet._styledCells = r.selectedSheet.grid.wj_sheetInfo.styledCells, r.selectedSheet.grid.wj_sheetInfo.mergedRanges)
                  for (i = 0; i < r.selectedSheet.grid.wj_sheetInfo.mergedRanges.length; i++) r.selectedSheet._mergedRanges[i] = r.selectedSheet.grid.wj_sheetInfo.mergedRanges[i];
                if (r._copyFrom(r.selectedSheet, !1), null != r.selectedSheet.grid.wj_sheetInfo.tables && r.selectedSheet.grid.wj_sheetInfo.tables.length > 0)
                  for (l = 0; l < r.selectedSheet.grid.wj_sheetInfo.tables.length; l++) this._parseFromWorkbookTable(r.selectedSheet.grid.wj_sheetInfo.tables[l], r.selectedSheet)
              }
              if (null != t.activeWorksheet && t.activeWorksheet > -1 && t.activeWorksheet < r.sheets.length ? r.selectedSheetIndex = t.activeWorksheet : r.selectedSheetIndex = 0, t.definedNames && t.definedNames.length > 0)
                for (l = 0; l < t.definedNames.length; l++) n = t.definedNames[l], r.definedNames.push(new c(r, n.name, n.value, n.sheetName));
              r.endUpdate(), r.onLoaded()
            }
          }, s.prototype._saveToWorkbook = function(t) {
            var o, n, i, l, s, r, a = this,
              h = !(!t || !t.includeFormulaValues),
              c = function(e) {
                return a.evaluate(e, null, l)
              };
            if (0 === a.sheets.length) throw "The flexsheet is empty.";
            if (l = a.sheets[0], h && (l.grid.wj_sheetInfo.evaluateFormula = c), 0 === a.selectedSheetIndex && (l._storeRowSettings(), l.itemsSource instanceof e.collections.CollectionView || a._copyRowsToSelectedSheet()), l._setRowSettings(), l.tables.length > 0)
              for (l.grid.wj_sheetInfo.tables = [], r = 0; r < l.tables.length; r++) l.grid.wj_sheetInfo.tables.push(a._parseToWorkbookTable(l.tables[r]));
            for (o = e.grid.xlsx.FlexGridXlsxConverter.save(l.grid, {
              sheetName: l.name,
              sheetVisible: l.visible,
              includeColumnHeaders: !1
            }), a._checkTableHeaderRow(l.tables, o), l.grid.wj_sheetInfo.evaluateFormula && (l.grid.wj_sheetInfo.evaluateFormula = null), o.reservedContent = a._reservedContent, s = 1; s < a.sheets.length; s++) {
              if (l = a.sheets[s], h && (l.grid.wj_sheetInfo.evaluateFormula = c), a.selectedSheetIndex === s && (l._storeRowSettings(), l.itemsSource instanceof e.collections.CollectionView || a._copyRowsToSelectedSheet()), l._setRowSettings(), l.tables.length > 0)
                for (l.grid.wj_sheetInfo.tables = [], r = 0; r < l.tables.length; r++) l.grid.wj_sheetInfo.tables.push(a._parseToWorkbookTable(l.tables[r]));
              n = e.grid.xlsx.FlexGridXlsxConverter.save(l.grid, {
                sheetName: l.name,
                sheetVisible: l.visible,
                includeColumnHeaders: !1
              }), a._checkTableHeaderRow(l.tables, n), l.grid.wj_sheetInfo.evaluateFormula && (l.grid.wj_sheetInfo.evaluateFormula = null), o._addWorkSheet(n.sheets[0], s)
            }
            for (o.activeWorksheet = a.selectedSheetIndex, r = 0; r < a.definedNames.length; r++) {
              var d = a.definedNames[r];
              (i = new e.xlsx.DefinedName).name = d.name, i.value = d.value, i.sheetName = d.sheetName, o.definedNames.push(i)
            }
            if (a._colorThemes && a._colorThemes.length > 0)
              for (r = 0; r < a._colorThemes.length; r++) o.colorThemes[r] = a._colorThemes[r];
            return o
          }, s.prototype._mouseDown = function(o) {
            var n, i, s = window.navigator.userAgent,
              r = this.hitTest(o);
            this.columns;
            if (this.selectedSheet && (this.selectedSheet._scrollPosition = this.scrollPosition), r.cellType !== t.CellType.None && (this._isClicking = !0), this._dragable) return this._isDragging = !0, this._draggingMarker = document.createElement("div"), e.setCss(this._draggingMarker, {
              position: "absolute",
              display: "none",
              borderStyle: "dotted",
              cursor: "move"
            }), document.body.appendChild(this._draggingMarker), this._draggingTooltip = new e.Tooltip, this._draggingCells = this.selection, this.selectedSheet && this.selectedSheet.selectionRanges.clear(), this.onDraggingRowColumn(new l(this._draggingRow, o.shiftKey)), void o.preventDefault();
            if ("crosshair" === this.hostElement.style.cursor && null == this._fillingMarker) return this.finishEditing(), this._fillingData = !0, this._fillingPoint = new e.Point(o.clientX - this.scrollPosition.x, o.clientY - this.scrollPosition.y), this._fillingSource = this.selection.clone(), this._fillingMarker = document.createElement("div"), e.setCss(this._fillingMarker, {
              position: "absolute",
              display: "none",
              border: "2px dashed"
            }), this._root.appendChild(this._fillingMarker), this._fillingTooltip = new e.Tooltip, void o.preventDefault();
            if (this.selectionMode === t.SelectionMode.CellRange ? o.ctrlKey ? this._enableMulSel || (this._enableMulSel = !0, 0 === this.selectedSheet.selectionRanges.length && this.selectedSheet.selectionRanges.push(this.selection)) : r.cellType !== t.CellType.None && (this.selectedSheet && this.selectedSheet.selectionRanges.clear(), this._enableMulSel && this.refresh(!1), this._enableMulSel = !1) : (this._enableMulSel = !1, this.selectedSheet && this.selectedSheet.selectionRanges.clear()), this._htDown = r, 0 !== this.rows.length && 0 !== this.columns.length && (s.match(/iPad/i) || s.match(/iPhone/i) || this._contextMenu.hide(), 3 !== o.which && this.selectionMode === t.SelectionMode.CellRange && (r.cellType === t.CellType.ColumnHeader || r.cellType === t.CellType.None) && !(r.col > -1 && this.columns[r.col].isSelected) && e.hasClass(o.target, "wj-cell") && !r.edgeRight)) {
              if (this._columnHeaderClicked = !0, i = this.itemsSource && this.rows[0] && !this.rows[0].isVisible ? 1 : 0, o.shiftKey) this._multiSelectColumns(r);
              else {
                if (n = new t.CellRange(i, r.col, this.rows.length - 1, r.col), 3 === o.which && this.selection.contains(n)) return;
                this.select(n)
              }
              this._eCt.children[i] && this._eCt.children[i].children[r.col] && this._eCt.children[i].children[r.col].focus(), o.preventDefault()
            }
          }, s.prototype._mouseMove = function(e) {
            var o, n = this.hitTest(e),
              i = this.selection.clone(),
              l = this.rows.length,
              s = this.columns.length,
              r = this.hostElement.style.cursor;
            if (0 === this.rows.length || 0 === this.columns.length) return this._dragable = !1, void(n.cellType === t.CellType.Cell && (this.hostElement.style.cursor = "default"));
            if (this._isDragging) return this.hostElement.style.cursor = "move", void this._showDraggingMarker(e);
            if (!this._isClicking && n.edgeBottom && n.edgeRight && n.row === i.bottomRow && n.col === i.rightCol) this.hostElement.style.cursor = "crosshair";
            else {
              if (this._fillingData) return this.hostElement.style.cursor = "crosshair", void this._showFillMarker(e);
              o = this.itemsSource ? 0 === i.topRow || 1 === i.topRow : 0 === i.topRow, this._isClicking || !i || n.cellType === t.CellType.None || this.itemsSource || this.isReadOnly || !this._enableDragDrop || (this._draggingColumn = o && i.bottomRow === l - 1, this._draggingRow = 0 === i.leftCol && i.rightCol === s - 1, n.cellType === t.CellType.Cell ? (this._draggingColumn && ((n.col === i.leftCol - 1 || n.col === i.rightCol) && n.edgeRight || n.row === l - 1 && n.edgeBottom) && (r = "move"), this._draggingRow && !this._containsGroupRows(i) && ((n.row === i.topRow - 1 || n.row === i.bottomRow) && n.edgeBottom || n.col === s - 1 && n.edgeRight) && (r = "move")) : n.cellType === t.CellType.ColumnHeader ? n.edgeBottom && (this._draggingColumn && n.col >= i.leftCol && n.col <= i.rightCol ? r = "move" : this._draggingRow && 0 === i.topRow && (r = "move")) : n.cellType === t.CellType.RowHeader && n.edgeRight && (this._draggingColumn && 0 === i.leftCol ? r = "move" : this._draggingRow && n.row >= i.topRow && n.row <= i.bottomRow && !this._containsGroupRows(i) && (r = "move")), this._dragable = "move" === r, this.hostElement.style.cursor = r), this._htDown && this._htDown.panel && (n = new t.HitTestInfo(this._htDown.panel, e), this._multiSelectColumns(n), n.cellType === t.CellType.Cell && this.scrollIntoView(n.row, n.col))
            }
          }, s.prototype._mouseUp = function(n) {
            try {
              if (this._isDragging) this._draggingCells.equals(this._dropRange) || (this._handleDropping(n), this.onDroppingRowColumn());
              else if (this._fillingData) {
                var i = d.CopyContent,
                  l = this._fillingRange,
                  s = this._fillingSource,
                  r = void 0;
                this._orgCellSettings = null, this._fillingRange && this._fillingRange.isValid && (e.assert(this._canDoFillOperation(), "To do this, all the merged cells need be the same size."), s.leftCol === l.leftCol && s.rightCol === l.rightCol ? s.row !== s.row2 && (i = d.FillSeries) : s.col !== s.col2 && (i = d.FillSeries), this._orgCellSettings = this._getCellSettingsForFill(), r = new o._FillAction(this, this._fillingSource), this._undoStack._addAction(r), i = d.CopyFormat | i, this._fillData(i), this._showFillMenuSmartTag(i))
              }
            } finally {
              this._isDragging && (this._draggingCells = null, this._dropRange = null, document.body.removeChild(this._draggingMarker), this._draggingMarker = null, this._draggingTooltip.hide(), this._draggingTooltip = null, this._isDragging = !1, this._draggingColumn = !1, this._draggingRow = !1), this._htDown && this._htDown.cellType !== t.CellType.None && this.selectedSheet && (this._htDown.cellType === t.CellType.TopLeft && this.selectionMode === t.SelectionMode.CellRange && (this.selection = new t.CellRange(0, 0, this.rows.length - 1, this.columns.length - 1)), this.selection.isValid && this.selectedSheet._addSelection(this.selection), this._enableMulSel = !1), this._fillingData && (this._fillingData = !1, this._fillingPoint = null, this._fillingRange = null, this._root.removeChild(this._fillingMarker), this._fillingMarker = null, this._fillingTooltip.hide(), this._fillingTooltip = null, this.hostElement.style.cursor = "default"), this._isClicking = !1, this._columnHeaderClicked = !1, this._htDown = null
            }
          }, s.prototype._click = function() {
            var e = this,
              t = window.navigator.userAgent;
            t.match(/iPad/i) || t.match(/iPhone/i) || e._contextMenu.hide(), setTimeout(function() {
              e.hideFunctionList()
            }, 200)
          }, s.prototype._touchStart = function(o) {
            var n = this;
            e.hasClass(o.target, "wj-context-menu-item") || n._contextMenu.hide(), n._longClickTimer = setTimeout(function() {
              var i;
              n._isDescendant(n._divContainer, o.target) && (i = n.hitTest(o)) && i.cellType !== t.CellType.None && !n._resizing && (i.cellType === t.CellType.TopLeft && (n.selection = new t.CellRange(0, 0, n.rows.length - 1, n.columns.length - 1), n.selectedSheet && (n.selectedSheet.selectionRanges.clear(), n.selectedSheet.selectionRanges.push(n.selection))), n._contextMenu.show(null, new e.Point(o.pageX + 10, o.pageY + 10)))
            }, 500)
          }, s.prototype._touchEnd = function() {
            clearTimeout(this._longClickTimer)
          }, s.prototype._keydown = function(t) {
            this._endDragFillOperation(t), !this._isDescendant(this.hostElement, t.target) && this.hostElement !== t.target || this._edtHdl.activeEditor || !this.selectedSheet || t.keyCode !== e.Key.Delete && t.keyCode !== e.Key.Back || (this._delSeletionContent(t), t.preventDefault()), this._contextMenu.visible && (t.keyCode === e.Key.Down && this._contextMenu.moveToNext(), t.keyCode === e.Key.Up && this._contextMenu.moveToPrev(), t.keyCode === e.Key.Home && this._contextMenu.moveToFirst(), t.keyCode === e.Key.End && this._contextMenu.moveToLast(), t.keyCode === e.Key.Enter && this._contextMenu.handleContextMenu(), t.preventDefault())
          }, s.prototype._showDraggingMarker = function(o) {
            var n, i, l, r, a, h, c, d, u, _ = new t.HitTestInfo(this.cells, o),
              g = this.selection,
              f = this.columns.length,
              p = this.rows.length,
              w = this._cumulativeScrollOffset(this.hostElement),
              m = this._root.getBoundingClientRect(),
              C = m.left + w.x,
              b = m.top + w.y;
            if (this.scrollIntoView(_.row, _.col), this._draggingColumn) {
              for (i = g.rightCol - g.leftCol + 1, a = 0, ((l = _.col) < 0 || l + i > f) && (l = f - i), n = this.cells.getCellBoundingRect(0, l), h = this._root.offsetHeight - this._eCHdr.offsetHeight, r = (r = this.cells.height) > h ? h : r, c = 0; c < i; c++) a += this.columns[l + c].renderSize;
              d = s.convertNumberToAlpha(l) + " : " + s.convertNumberToAlpha(l + i - 1), this._dropRange ? (this._dropRange.col = l, this._dropRange.col2 = l + i - 1) : this._dropRange = new t.CellRange(0, l, this.rows.length - 1, l + i - 1)
            } else if (this._draggingRow) {
              for (i = g.bottomRow - g.topRow + 1, r = 0, ((l = _.row) < 0 || l + i > p) && (l = p - i), n = this.cells.getCellBoundingRect(l, 0), h = this._root.offsetWidth - this._eRHdr.offsetWidth, c = 0; c < i; c++) r += this.rows[l + c].renderSize;
              a = (a = this.cells.width) > h ? h : a, d = l + 1 + " : " + (l + i), this._dropRange ? (this._dropRange.row = l, this._dropRange.row2 = l + i - 1) : this._dropRange = new t.CellRange(l, 0, l + i - 1, this.columns.length - 1)
            }
            if (n) {
              if (u = {
                display: "inline",
                zIndex: "9999",
                opacity: .5,
                top: n.top - (this._draggingColumn ? this._ptScrl.y : 0) + w.y,
                left: n.left - (this._draggingRow ? this._ptScrl.x : 0) + w.x,
                height: r,
                width: a
              }, n.top = n.top - (this._draggingColumn ? this._ptScrl.y : 0), n.left = n.left - (this._draggingRow ? this._ptScrl.x : 0), this.rightToLeft && this._draggingRow && (u.left = u.left - a + n.width + 2 * this._ptScrl.x, n.left = n.left + 2 * this._ptScrl.x), this._draggingRow) {
                if (C + this._eRHdr.offsetWidth !== u.left || b + this._root.offsetHeight + 1 < u.top + u.height) return
              } else if (b + this._eCHdr.offsetHeight !== u.top || C + this._root.offsetWidth + 1 < u.left + u.width) return;
              e.setCss(this._draggingMarker, u), this._draggingTooltip.show(this.hostElement, d, n)
            }
          }, s.prototype._showFillMarker = function(o) {
            var n, i, l, s, r, a, h, c, d = new t.HitTestInfo(this.cells, o);
            d.row > -1 && d.col > -1 && (n = o.clientX - this.scrollPosition.x - this._fillingPoint.x, i = o.clientY - this.scrollPosition.y - this._fillingPoint.y, Math.abs(n) >= Math.abs(i) ? (l = this._fillingSource.topRow, r = this._fillingSource.bottomRow, s = n >= 0 ? this._fillingSource.leftCol : d.col, a = n >= 0 ? d.col : this._fillingSource.rightCol) : (l = i >= 0 ? this._fillingSource.topRow : d.row, r = i >= 0 ? d.row : this._fillingSource.bottomRow, s = this._fillingSource.leftCol, a = this._fillingSource.rightCol), h = this.cells.getCellBoundingRect(l, s, !0), c = this.cells.getCellBoundingRect(r, a, !0), e.setCss(this._fillingMarker, {
              left: h.left + this.cells.hostElement.offsetLeft - 2,
              top: h.top + this.cells.hostElement.offsetTop - 2,
              width: c.right - h.left + 4,
              height: c.bottom - h.top + 4,
              display: "",
              zIndex: "9999"
            }), this._fillingRange = new t.CellRange(l, s, r, a), this.scrollIntoView(d.row, d.col), this._showFillTooltip())
          }, s.prototype._showFillTooltip = function() {
            var t, o, n, i, l, s, r, a, h, c, d, u, _ = this._fillingSource,
              g = this._fillingRange;
            this._fillingTooltip.hide(), g.equals(_) || _.contains(g) || ((o = _.leftCol === g.leftCol && _.rightCol === g.rightCol) ? (g.bottomRow > _.bottomRow ? ((t = this.cells.getCellBoundingRect(g.bottomRow, g.rightCol)).top = t.bottom + 10, n = g.bottomRow - _.topRow) : ((t = this.cells.getCellBoundingRect(g.topRow, g.rightCol)).top += 10, n = g.topRow - _.topRow), t.left = t.right, (i = n % _.rowSpan) < 0 && (i += _.rowSpan), _.row === _.row2 && (l = !0), h = this.selectedSheet.getCellStyle(_.topRow + i, _.leftCol), c = this.getMergedRange(this.cells, _.topRow + i, _.leftCol), d = this.columns[_.leftCol], u = h && h.format ? h.format : d.format, l ? (a = this.getCellData(c ? c.topRow : _.topRow + i, _.leftCol, !1), a = e.isString(a) && "=" === a[0] ? "" : e.Globalize.format(a, u)) : (s = this.getCellData(_.topRow + i, _.leftCol, !1), e.isNumber(s) || e.isDate(s) ? (r = this._getFillSeries(o, 0, i)) ? (a = this._getFillData(i, n, _, o, r, !0), a = e.Globalize.format(a, u)) : a = "" : (a = s, e.isString(a) && "=" === a[0] && (a = "")))) : (g.rightCol > _.rightCol ? (t = this.cells.getCellBoundingRect(g.bottomRow, g.rightCol), n = g.rightCol - _.leftCol) : (t = this.cells.getCellBoundingRect(g.bottomRow, g.leftCol), n = g.leftCol - _.leftCol), t.top = t.bottom + 10, (i = n % _.columnSpan) < 0 && (i += _.columnSpan), _.col === _.col2 && (l = !0), h = this.selectedSheet.getCellStyle(_.topRow, _.leftCol + i), c = this.getMergedRange(this.cells, _.topRow, _.leftCol + i), d = this.columns[_.leftCol + i], u = h && h.format ? h.format : d.format, l ? (a = this.getCellData(_.topRow, c ? c.leftCol : _.leftCol + i, !1), a = e.isString(a) && "=" === a[0] ? "" : e.Globalize.format(a, u)) : (s = this.getCellData(_.topRow, _.leftCol + i, !1), e.isNumber(s) || e.isDate(s) ? (r = this._getFillSeries(o, 0, i)) ? (a = this._getFillData(i, n, _, o, r, !0), a = e.Globalize.format(a, u)) : a = "" : (a = s, e.isString(a) && "=" === a[0] && (a = "")))), t.top += this.cells.hostElement.offsetTop, this._fillingTooltip.show(this.hostElement, a, t))
          }, s.prototype._showFillMenuSmartTag = function(t) {
            var o = this,
              n = document.createElement("img"),
              i = document.createElement("img"),
              l = o.selection,
              s = o.cells.getCellBoundingRect(l.bottomRow, l.rightCol, !0),
              r = s.right + o.cells.hostElement.offsetLeft,
              a = s.bottom + o.cells.hostElement.offsetTop;
            o.addEventListener(document.body, "mousedown", o._endDragFillOperationHdl, !0), o._fillSmartTagHost = document.createElement("div"), e.addClass(o._fillSmartTagHost, "wj-flexsheet-smart-tag"), r + o._ptScrl.x + 32 + (o.cells.hostElement.offsetHeight > o._root.offsetHeight ? 17 : 0) > o._root.offsetWidth && (r -= 32), a + o._ptScrl.y + 18 + (o.cells.hostElement.offsetWidth > o._root.offsetWidth ? 17 : 0) > o._root.offsetHeight && (a -= 18), e.setCss(o._fillSmartTagHost, {
              left: r,
              top: a
            }), n.setAttribute("src", "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAYAAABWzo5XAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAKT2lDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjanVNnVFPpFj333vRCS4iAlEtvUhUIIFJCi4AUkSYqIQkQSoghodkVUcERRUUEG8igiAOOjoCMFVEsDIoK2AfkIaKOg6OIisr74Xuja9a89+bN/rXXPues852zzwfACAyWSDNRNYAMqUIeEeCDx8TG4eQuQIEKJHAAEAizZCFz/SMBAPh+PDwrIsAHvgABeNMLCADATZvAMByH/w/qQplcAYCEAcB0kThLCIAUAEB6jkKmAEBGAYCdmCZTAKAEAGDLY2LjAFAtAGAnf+bTAICd+Jl7AQBblCEVAaCRACATZYhEAGg7AKzPVopFAFgwABRmS8Q5ANgtADBJV2ZIALC3AMDOEAuyAAgMADBRiIUpAAR7AGDIIyN4AISZABRG8lc88SuuEOcqAAB4mbI8uSQ5RYFbCC1xB1dXLh4ozkkXKxQ2YQJhmkAuwnmZGTKBNA/g88wAAKCRFRHgg/P9eM4Ors7ONo62Dl8t6r8G/yJiYuP+5c+rcEAAAOF0ftH+LC+zGoA7BoBt/qIl7gRoXgugdfeLZrIPQLUAoOnaV/Nw+H48PEWhkLnZ2eXk5NhKxEJbYcpXff5nwl/AV/1s+X48/Pf14L7iJIEyXYFHBPjgwsz0TKUcz5IJhGLc5o9H/LcL//wd0yLESWK5WCoU41EScY5EmozzMqUiiUKSKcUl0v9k4t8s+wM+3zUAsGo+AXuRLahdYwP2SycQWHTA4vcAAPK7b8HUKAgDgGiD4c93/+8//UegJQCAZkmScQAAXkQkLlTKsz/HCAAARKCBKrBBG/TBGCzABhzBBdzBC/xgNoRCJMTCQhBCCmSAHHJgKayCQiiGzbAdKmAv1EAdNMBRaIaTcA4uwlW4Dj1wD/phCJ7BKLyBCQRByAgTYSHaiAFiilgjjggXmYX4IcFIBBKLJCDJiBRRIkuRNUgxUopUIFVIHfI9cgI5h1xGupE7yAAygvyGvEcxlIGyUT3UDLVDuag3GoRGogvQZHQxmo8WoJvQcrQaPYw2oefQq2gP2o8+Q8cwwOgYBzPEbDAuxsNCsTgsCZNjy7EirAyrxhqwVqwDu4n1Y8+xdwQSgUXACTYEd0IgYR5BSFhMWE7YSKggHCQ0EdoJNwkDhFHCJyKTqEu0JroR+cQYYjIxh1hILCPWEo8TLxB7iEPENyQSiUMyJ7mQAkmxpFTSEtJG0m5SI+ksqZs0SBojk8naZGuyBzmULCAryIXkneTD5DPkG+Qh8lsKnWJAcaT4U+IoUspqShnlEOU05QZlmDJBVaOaUt2ooVQRNY9aQq2htlKvUYeoEzR1mjnNgxZJS6WtopXTGmgXaPdpr+h0uhHdlR5Ol9BX0svpR+iX6AP0dwwNhhWDx4hnKBmbGAcYZxl3GK+YTKYZ04sZx1QwNzHrmOeZD5lvVVgqtip8FZHKCpVKlSaVGyovVKmqpqreqgtV81XLVI+pXlN9rkZVM1PjqQnUlqtVqp1Q61MbU2epO6iHqmeob1Q/pH5Z/YkGWcNMw09DpFGgsV/jvMYgC2MZs3gsIWsNq4Z1gTXEJrHN2Xx2KruY/R27iz2qqaE5QzNKM1ezUvOUZj8H45hx+Jx0TgnnKKeX836K3hTvKeIpG6Y0TLkxZVxrqpaXllirSKtRq0frvTau7aedpr1Fu1n7gQ5Bx0onXCdHZ4/OBZ3nU9lT3acKpxZNPTr1ri6qa6UbobtEd79up+6Ynr5egJ5Mb6feeb3n+hx9L/1U/W36p/VHDFgGswwkBtsMzhg8xTVxbzwdL8fb8VFDXcNAQ6VhlWGX4YSRudE8o9VGjUYPjGnGXOMk423GbcajJgYmISZLTepN7ppSTbmmKaY7TDtMx83MzaLN1pk1mz0x1zLnm+eb15vft2BaeFostqi2uGVJsuRaplnutrxuhVo5WaVYVVpds0atna0l1rutu6cRp7lOk06rntZnw7Dxtsm2qbcZsOXYBtuutm22fWFnYhdnt8Wuw+6TvZN9un2N/T0HDYfZDqsdWh1+c7RyFDpWOt6azpzuP33F9JbpL2dYzxDP2DPjthPLKcRpnVOb00dnF2e5c4PziIuJS4LLLpc+Lpsbxt3IveRKdPVxXeF60vWdm7Obwu2o26/uNu5p7ofcn8w0nymeWTNz0MPIQ+BR5dE/C5+VMGvfrH5PQ0+BZ7XnIy9jL5FXrdewt6V3qvdh7xc+9j5yn+M+4zw33jLeWV/MN8C3yLfLT8Nvnl+F30N/I/9k/3r/0QCngCUBZwOJgUGBWwL7+Hp8Ib+OPzrbZfay2e1BjKC5QRVBj4KtguXBrSFoyOyQrSH355jOkc5pDoVQfujW0Adh5mGLw34MJ4WHhVeGP45wiFga0TGXNXfR3ENz30T6RJZE3ptnMU85ry1KNSo+qi5qPNo3ujS6P8YuZlnM1VidWElsSxw5LiquNm5svt/87fOH4p3iC+N7F5gvyF1weaHOwvSFpxapLhIsOpZATIhOOJTwQRAqqBaMJfITdyWOCnnCHcJnIi/RNtGI2ENcKh5O8kgqTXqS7JG8NXkkxTOlLOW5hCepkLxMDUzdmzqeFpp2IG0yPTq9MYOSkZBxQqohTZO2Z+pn5mZ2y6xlhbL+xW6Lty8elQfJa7OQrAVZLQq2QqboVFoo1yoHsmdlV2a/zYnKOZarnivN7cyzytuQN5zvn//tEsIS4ZK2pYZLVy0dWOa9rGo5sjxxedsK4xUFK4ZWBqw8uIq2Km3VT6vtV5eufr0mek1rgV7ByoLBtQFr6wtVCuWFfevc1+1dT1gvWd+1YfqGnRs+FYmKrhTbF5cVf9go3HjlG4dvyr+Z3JS0qavEuWTPZtJm6ebeLZ5bDpaql+aXDm4N2dq0Dd9WtO319kXbL5fNKNu7g7ZDuaO/PLi8ZafJzs07P1SkVPRU+lQ27tLdtWHX+G7R7ht7vPY07NXbW7z3/T7JvttVAVVN1WbVZftJ+7P3P66Jqun4lvttXa1ObXHtxwPSA/0HIw6217nU1R3SPVRSj9Yr60cOxx++/p3vdy0NNg1VjZzG4iNwRHnk6fcJ3/ceDTradox7rOEH0x92HWcdL2pCmvKaRptTmvtbYlu6T8w+0dbq3nr8R9sfD5w0PFl5SvNUyWna6YLTk2fyz4ydlZ19fi753GDborZ752PO32oPb++6EHTh0kX/i+c7vDvOXPK4dPKy2+UTV7hXmq86X23qdOo8/pPTT8e7nLuarrlca7nuer21e2b36RueN87d9L158Rb/1tWeOT3dvfN6b/fF9/XfFt1+cif9zsu72Xcn7q28T7xf9EDtQdlD3YfVP1v+3Njv3H9qwHeg89HcR/cGhYPP/pH1jw9DBY+Zj8uGDYbrnjg+OTniP3L96fynQ89kzyaeF/6i/suuFxYvfvjV69fO0ZjRoZfyl5O/bXyl/erA6xmv28bCxh6+yXgzMV70VvvtwXfcdx3vo98PT+R8IH8o/2j5sfVT0Kf7kxmTk/8EA5jz/GMzLdsAAAAgY0hSTQAAeiUAAICDAAD5/wAAgOkAAHUwAADqYAAAOpgAABdvkl/FRgAAAFJJREFUeNrclEEKACAIBOfp/Xy7i4WSRnRYBIVRVhFJVIhyEAxllQa5E/wBSnsU6Rza2nugqNmASi57C/KKNg/Iqn+iVWzx6M4bOdUEAAD//wMAAYRMfiNaiqEAAAAASUVORK5CYII="), e.setCss(n, {
              float: "left",
              height: 18,
              margin: 0
            }), i.setAttribute("src", "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAcAAAASCAYAAACXScT7AAAACXBIWXMAAA7DAAAOwwHHb6hkAAAKTWlDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjanVN3WJP3Fj7f92UPVkLY8LGXbIEAIiOsCMgQWaIQkgBhhBASQMWFiApWFBURnEhVxILVCkidiOKgKLhnQYqIWotVXDjuH9yntX167+3t+9f7vOec5/zOec8PgBESJpHmomoAOVKFPDrYH49PSMTJvYACFUjgBCAQ5svCZwXFAADwA3l4fnSwP/wBr28AAgBw1S4kEsfh/4O6UCZXACCRAOAiEucLAZBSAMguVMgUAMgYALBTs2QKAJQAAGx5fEIiAKoNAOz0ST4FANipk9wXANiiHKkIAI0BAJkoRyQCQLsAYFWBUiwCwMIAoKxAIi4EwK4BgFm2MkcCgL0FAHaOWJAPQGAAgJlCLMwAIDgCAEMeE80DIEwDoDDSv+CpX3CFuEgBAMDLlc2XS9IzFLiV0Bp38vDg4iHiwmyxQmEXKRBmCeQinJebIxNI5wNMzgwAABr50cH+OD+Q5+bk4eZm52zv9MWi/mvwbyI+IfHf/ryMAgQAEE7P79pf5eXWA3DHAbB1v2upWwDaVgBo3/ldM9sJoFoK0Hr5i3k4/EAenqFQyDwdHAoLC+0lYqG9MOOLPv8z4W/gi372/EAe/tt68ABxmkCZrcCjg/1xYW52rlKO58sEQjFu9+cj/seFf/2OKdHiNLFcLBWK8ViJuFAiTcd5uVKRRCHJleIS6X8y8R+W/QmTdw0ArIZPwE62B7XLbMB+7gECiw5Y0nYAQH7zLYwaC5EAEGc0Mnn3AACTv/mPQCsBAM2XpOMAALzoGFyolBdMxggAAESggSqwQQcMwRSswA6cwR28wBcCYQZEQAwkwDwQQgbkgBwKoRiWQRlUwDrYBLWwAxqgEZrhELTBMTgN5+ASXIHrcBcGYBiewhi8hgkEQcgIE2EhOogRYo7YIs4IF5mOBCJhSDSSgKQg6YgUUSLFyHKkAqlCapFdSCPyLXIUOY1cQPqQ28ggMor8irxHMZSBslED1AJ1QLmoHxqKxqBz0XQ0D12AlqJr0Rq0Hj2AtqKn0UvodXQAfYqOY4DRMQ5mjNlhXIyHRWCJWBomxxZj5Vg1Vo81Yx1YN3YVG8CeYe8IJAKLgBPsCF6EEMJsgpCQR1hMWEOoJewjtBK6CFcJg4Qxwicik6hPtCV6EvnEeGI6sZBYRqwm7iEeIZ4lXicOE1+TSCQOyZLkTgohJZAySQtJa0jbSC2kU6Q+0hBpnEwm65Btyd7kCLKArCCXkbeQD5BPkvvJw+S3FDrFiOJMCaIkUqSUEko1ZT/lBKWfMkKZoKpRzame1AiqiDqfWkltoHZQL1OHqRM0dZolzZsWQ8ukLaPV0JppZ2n3aC/pdLoJ3YMeRZfQl9Jr6Afp5+mD9HcMDYYNg8dIYigZaxl7GacYtxkvmUymBdOXmchUMNcyG5lnmA+Yb1VYKvYqfBWRyhKVOpVWlX6V56pUVXNVP9V5qgtUq1UPq15WfaZGVbNQ46kJ1Bar1akdVbupNq7OUndSj1DPUV+jvl/9gvpjDbKGhUaghkijVGO3xhmNIRbGMmXxWELWclYD6yxrmE1iW7L57Ex2Bfsbdi97TFNDc6pmrGaRZp3mcc0BDsax4PA52ZxKziHODc57LQMtPy2x1mqtZq1+rTfaetq+2mLtcu0W7eva73VwnUCdLJ31Om0693UJuja6UbqFutt1z+o+02PreekJ9cr1Dund0Uf1bfSj9Rfq79bv0R83MDQINpAZbDE4Y/DMkGPoa5hpuNHwhOGoEctoupHEaKPRSaMnuCbuh2fjNXgXPmasbxxirDTeZdxrPGFiaTLbpMSkxeS+Kc2Ua5pmutG003TMzMgs3KzYrMnsjjnVnGueYb7ZvNv8jYWlRZzFSos2i8eW2pZ8ywWWTZb3rJhWPlZ5VvVW16xJ1lzrLOtt1ldsUBtXmwybOpvLtqitm63Edptt3xTiFI8p0in1U27aMez87ArsmuwG7Tn2YfYl9m32zx3MHBId1jt0O3xydHXMdmxwvOuk4TTDqcSpw+lXZxtnoXOd8zUXpkuQyxKXdpcXU22niqdun3rLleUa7rrStdP1o5u7m9yt2W3U3cw9xX2r+00umxvJXcM970H08PdY4nHM452nm6fC85DnL152Xlle+70eT7OcJp7WMG3I28Rb4L3Le2A6Pj1l+s7pAz7GPgKfep+Hvqa+It89viN+1n6Zfgf8nvs7+sv9j/i/4XnyFvFOBWABwQHlAb2BGoGzA2sDHwSZBKUHNQWNBbsGLww+FUIMCQ1ZH3KTb8AX8hv5YzPcZyya0RXKCJ0VWhv6MMwmTB7WEY6GzwjfEH5vpvlM6cy2CIjgR2yIuB9pGZkX+X0UKSoyqi7qUbRTdHF09yzWrORZ+2e9jvGPqYy5O9tqtnJ2Z6xqbFJsY+ybuIC4qriBeIf4RfGXEnQTJAntieTE2MQ9ieNzAudsmjOc5JpUlnRjruXcorkX5unOy553PFk1WZB8OIWYEpeyP+WDIEJQLxhP5aduTR0T8oSbhU9FvqKNolGxt7hKPJLmnVaV9jjdO31D+miGT0Z1xjMJT1IreZEZkrkj801WRNberM/ZcdktOZSclJyjUg1plrQr1zC3KLdPZisrkw3keeZtyhuTh8r35CP5c/PbFWyFTNGjtFKuUA4WTC+oK3hbGFt4uEi9SFrUM99m/ur5IwuCFny9kLBQuLCz2Lh4WfHgIr9FuxYji1MXdy4xXVK6ZHhp8NJ9y2jLspb9UOJYUlXyannc8o5Sg9KlpUMrglc0lamUycturvRauWMVYZVkVe9ql9VbVn8qF5VfrHCsqK74sEa45uJXTl/VfPV5bdra3kq3yu3rSOuk626s91m/r0q9akHV0IbwDa0b8Y3lG19tSt50oXpq9Y7NtM3KzQM1YTXtW8y2rNvyoTaj9nqdf13LVv2tq7e+2Sba1r/dd3vzDoMdFTve75TsvLUreFdrvUV99W7S7oLdjxpiG7q/5n7duEd3T8Wej3ulewf2Re/ranRvbNyvv7+yCW1SNo0eSDpw5ZuAb9qb7Zp3tXBaKg7CQeXBJ9+mfHvjUOihzsPcw83fmX+39QjrSHkr0jq/dawto22gPaG97+iMo50dXh1Hvrf/fu8x42N1xzWPV56gnSg98fnkgpPjp2Snnp1OPz3Umdx590z8mWtdUV29Z0PPnj8XdO5Mt1/3yfPe549d8Lxw9CL3Ytslt0utPa49R35w/eFIr1tv62X3y+1XPK509E3rO9Hv03/6asDVc9f41y5dn3m978bsG7duJt0cuCW69fh29u0XdwruTNxdeo94r/y+2v3qB/oP6n+0/rFlwG3g+GDAYM/DWQ/vDgmHnv6U/9OH4dJHzEfVI0YjjY+dHx8bDRq98mTOk+GnsqcTz8p+Vv9563Or59/94vtLz1j82PAL+YvPv655qfNy76uprzrHI8cfvM55PfGm/K3O233vuO+638e9H5ko/ED+UPPR+mPHp9BP9z7nfP78L/eE8/sl0p8zAAAAIGNIUk0AAHolAACAgwAA+f8AAIDpAAB1MAAA6mAAADqYAAAXb5JfxUYAAAGCSURBVHjafNFPKKRxHAbw5/v7GYdBYUQjLm5SkoNykCQHtcx431/KgdKumJvSrAO7Nwc3RQ5kd3OkHJC/hUJREluonVcToqRh3sRFPA6M1Mbhc3z6Pj1fkMRHQNJL0uPeul731lU37o1y49cqHr8GvvgWQRLBsmpM/P0j4XAXiooKcXl1CZDEzl4EJBEwAZBUwWAQsVgsFSRR11gmM8trimSa3WypzZ31l5v2/vfk/4oAcv9aSGyUSz4gg/AIAOET0YQswIQWaNrnH+2OeSaY0BJN2+wDTi/OpCrwkxX1vW8q63p5cnaaB+Z/09u7x0nFJTVMiEajPsNCQaC6Ryb8THKcw/Tikho6zj//0RGUNV6gMZ1H8fmpH5iTHDlwsiOhO7FrN5RdP6aBIUj/pvJ2bkFbkxAzBzELELNCQQqgrJ5ST1/jqmYOJcHa7dYYGV5TrQ3d+vfUU+b7IfrOIRCGBYD0o1VGmaHaB6DZkqvMD2hUfF1UAISkvE/+yqbCZ89+HgBtwgFOrBUzJgAAAABJRU5ErkJggg=="), e.setCss(i, {
              float: "left",
              margin: 0,
              height: 18,
              display: "none"
            }), o._fillSmartTagHost.appendChild(n), o._fillSmartTagHost.appendChild(i), o._root.appendChild(o._fillSmartTagHost), o.addEventListener(o._fillSmartTagHost, "mouseover", function() {
              o._fillSmartTagHost.style.width = "32px", i.style.display = ""
            }), o.addEventListener(o._fillSmartTagHost, "mouseleave", function() {
              o._fillMenuHost || (o._fillSmartTagHost.style.width = "18px", i.style.display = "none")
            }), o.addEventListener(o._fillSmartTagHost, "mousedown", function(n) {
              e.hasClass(o._fillSmartTagHost, "wj-flexsheet-smart-tag-active") ? (e.removeClass(o._fillSmartTagHost, "wj-flexsheet-smart-tag-active"), o._fillMenuHost.style.display = "none") : (e.addClass(o._fillSmartTagHost, "wj-flexsheet-smart-tag-active"), o._showFillMenu(t), t = null), n.preventDefault()
            }, !0)
          }, s.prototype._showFillMenu = function(t) {
            var o, n, i = e.culture.FlexSheet,
              l = this,
              s = l._fillSmartTagHost.offsetLeft,
              r = l._fillSmartTagHost.offsetTop + 18;
            if (!l._fillMenuHost) {
              l._fillMenuHost = document.createElement("div"), e.setCss(l._fillMenuHost, {
                display: "none",
                zIndex: 100
              }), l._fillMenuHost.innerHTML = '<div class="wj-flexsheet-fill-menu-item">' + i.copyCells + '</div><div class="wj-flexsheet-fill-menu-item">' + i.fillSeries + '</div><div class="wj-flexsheet-fill-menu-item">' + i.fillFormat + '</div><div class="wj-flexsheet-fill-menu-item">' + i.fillWithoutFormat + "</div>", e.addClass(l._fillMenuHost, "wj-flexsheet-fill-menu"), l._root.appendChild(l._fillMenuHost);
              for (var a = function(t) {
                o = l._fillMenuHost.childNodes[t], l.addEventListener(o, "mousedown", function(o) {
                  var n;
                  switch (l._removeActiveStyleForFillMenuItem(), e.addClass(o.target, "wj-flexsheet-fill-menu-item-active"), t) {
                    case 0:
                      n = d.CopyFormat | d.CopyContent;
                      break;
                    case 1:
                      n = d.CopyFormat | d.FillSeries;
                      break;
                    case 2:
                      n = d.CopyFormat;
                      break;
                    case 3:
                      n = d.FillSeries
                  }
                  l._fillData(n), l._fillMenuHost.style.display = "none", e.removeClass(l._fillSmartTagHost, "wj-flexsheet-smart-tag-active"), o.preventDefault()
                }, !0)
              }, h = 0; h < l._fillMenuHost.childElementCount; h++) a(h)
            }
            if (t) {
              switch (l._removeActiveStyleForFillMenuItem(), t) {
                case d.CopyFormat | d.CopyContent:
                  n = 0;
                  break;
                case d.CopyFormat | d.FillSeries:
                  n = 1;
                  break;
                case d.CopyFormat:
                  n = 2;
                  break;
                case d.FillSeries:
                  n = 3
              }
              e.addClass(l._fillMenuHost.childNodes[n], "wj-flexsheet-fill-menu-item-active")
            }
            e.setCss(l._fillMenuHost, {
              display: ""
            }), s + l._ptScrl.x + l._fillMenuHost.offsetWidth + (l.cells.hostElement.offsetHeight > l._root.offsetHeight ? 17 : 0) > l._root.offsetWidth && (s = s - l._fillMenuHost.offsetWidth + l._fillSmartTagHost.offsetWidth), r + l._ptScrl.y + l._fillMenuHost.offsetHeight + +(l.cells.hostElement.offsetWidth > l._root.offsetWidth ? 17 : 0) > l._root.offsetHeight && (r = l._fillSmartTagHost.offsetTop - l._fillMenuHost.offsetHeight), l._fillMenuHost.style.left = s + "px", l._fillMenuHost.style.top = r + "px"
          }, s.prototype._endDragFillOperation = function(e) {
            this._fillSmartTagHost && (e instanceof MouseEvent && (e.target === this._fillSmartTagHost || this._isDescendant(this._fillSmartTagHost, e.target) || e.target === this._fillMenuHost || this._isDescendant(this._fillMenuHost, e.target)) || (this.removeEventListener(document.body, "mousedown", this._endDragFillOperationHdl), this._disposeFillSmartTag(), this._fillMenuHost && this._disposeFillMenu(), this._undoStack._updateCurrentAction(o._FillAction)))
          }, s.prototype._removeActiveStyleForFillMenuItem = function() {
            for (var t, o = 0; o < this._fillMenuHost.childElementCount; o++) t = this._fillMenuHost.childNodes[o], e.removeClass(t, "wj-flexsheet-fill-menu-item-active")
          }, s.prototype._disposeFillSmartTag = function() {
            this.removeEventListener(this._fillSmartTagHost), this._root.removeChild(this._fillSmartTagHost), this._fillSmartTagHost = null, this._orgCellSettings = null
          }, s.prototype._disposeFillMenu = function() {
            for (var e, t = 0; t < this._fillMenuHost.childElementCount; t++) e = this._fillMenuHost.childNodes[t], this.removeEventListener(e);
            this._root.removeChild(this._fillMenuHost), this._fillMenuHost = null
          }, s.prototype._handleDropping = function(t) {
            var n, i, l, s, r, a, h, c = this;
            if (c.selectedSheet && c._draggingCells && c._dropRange && !c._containsMergedCells(c._draggingCells) && !c._containsMergedCells(c._dropRange)) {
              if (c._clearCalcEngine(), c._draggingColumn && c._draggingCells.leftCol > c._dropRange.leftCol || c._draggingRow && c._draggingCells.topRow > c._dropRange.topRow)
                if (t.shiftKey) {
                  if (!c._allowExchangeCells(c._draggingRow, !0)) return void console.warn("Can not complete operation: You are attempting to change a position of table row or column in a way that is not allowed.");
                  if (c._draggingColumn) {
                    for (s = c._dropRange.leftCol, i = c._draggingCells.leftCol; i <= c._draggingCells.rightCol; i++) c.columns.moveElement(i, s), s++;
                    c._exchangeTableColumns(!0)
                  } else if (c._draggingRow)
                    for (l = c._dropRange.topRow, n = c._draggingCells.topRow; n <= c._draggingCells.bottomRow; n++) c.rows.moveElement(n, l), l++;
                  c._exchangeCellStyle(!0)
                } else {
                  for (this.undoStack.stackSize > 0 && (r = new o._MoveCellsAction(c, c._draggingCells, c._dropRange, t.ctrlKey)), l = c._dropRange.topRow, n = c._draggingCells.topRow; n <= c._draggingCells.bottomRow; n++) {
                    for (s = c._dropRange.leftCol, i = c._draggingCells.leftCol; i <= c._draggingCells.rightCol; i++) c._moveCellContent(n, i, l, s, t.ctrlKey), c._draggingColumn && l === c._dropRange.topRow && (c.columns[s].dataType = c.columns[i].dataType ? c.columns[i].dataType : e.DataType.Object, c.columns[s].align = c.columns[i].align, c.columns[s].format = c.columns[i].format, t.ctrlKey || (c.columns[i].dataType = e.DataType.Object, c.columns[i].align = null, c.columns[i].format = null)), s++;
                    l++
                  }
                  if (c._draggingColumn && !t.ctrlKey)
                    for (s = c._dropRange.leftCol, i = c._draggingCells.leftCol; i <= c._draggingCells.rightCol; i++) c._updateColumnFiler(i, s), s++
                } else if (c._draggingColumn && c._draggingCells.leftCol < c._dropRange.leftCol || c._draggingRow && c._draggingCells.topRow < c._dropRange.topRow)
                if (t.shiftKey) {
                  if (!c._allowExchangeCells(c._draggingRow, !1)) return void console.warn("Can not complete operation: You are attempting to change a position of table row or column in a way that is not allowed.");
                  if (c._draggingColumn) {
                    for (s = c._dropRange.rightCol, i = c._draggingCells.rightCol; i >= c._draggingCells.leftCol; i--) c.columns.moveElement(i, s), s--;
                    c._exchangeTableColumns(!1)
                  } else if (c._draggingRow)
                    for (l = c._dropRange.bottomRow, n = c._draggingCells.bottomRow; n >= c._draggingCells.topRow; n--) c.rows.moveElement(n, l), l--;
                  c._exchangeCellStyle(!1)
                } else {
                  for (this.undoStack.stackSize > 0 && (r = new o._MoveCellsAction(c, c._draggingCells, c._dropRange, t.ctrlKey)), l = c._dropRange.bottomRow, n = c._draggingCells.bottomRow; n >= c._draggingCells.topRow; n--) {
                    for (s = c._dropRange.rightCol, i = c._draggingCells.rightCol; i >= c._draggingCells.leftCol; i--) c._moveCellContent(n, i, l, s, t.ctrlKey), c._draggingColumn && l === c._dropRange.bottomRow && (c.columns[s].dataType = c.columns[i].dataType ? c.columns[i].dataType : e.DataType.Object, c.columns[s].align = c.columns[i].align, c.columns[s].format = c.columns[i].format, t.ctrlKey || (c.columns[i].dataType = e.DataType.Object, c.columns[i].align = null, c.columns[i].format = null)), s--;
                    l--
                  }
                  if (c._draggingColumn && !t.ctrlKey)
                    for (s = c._dropRange.rightCol, i = c._draggingCells.rightCol; i >= c._draggingCells.leftCol; i--) c._updateColumnFiler(i, s), s--
                }
              t.ctrlKey || (a = c._updateFormulaForDropping(t.shiftKey), h = c._updateNamedRangesForDropping(t.shiftKey)), r && r.saveNewState() && (r._affectedFormulas = a, r._affectedDefinedNameVals = h, c._undoStack._addAction(r)), c._undoStack._pendingAction && (c._undoStack._pendingAction._affectedFormulas = a, c._undoStack._pendingAction._affectedDefinedNameVals = h), c.select(c._dropRange), c.selectedSheet._addSelection(c.selection), c.hostElement.focus()
            }
          }, s.prototype._moveCellContent = function(e, o, n, i, l) {
            var s, r, a, h, c, d, u, _, g, f = this.getCellData(e, o, !1),
              p = e * this.columns.length + o,
              w = n * this.columns.length + i,
              m = this.selectedSheet._styledCells[p],
              C = !0;
            if ((r = this.selectedSheet.findTable(n, i)) && (u = r.getRange(), _ = r.getColumns(), r.showHeaderRow && n === u.topRow && (c = _[h = i - u.leftCol].name, null == f || "" === f ? C = !1 : _[h].name = f)), C) {
              if (l && f && "string" == typeof f && "=" === f[0] && w - p != 0) try {
                (g = this._calcEngine._parse(f))._moveCellRangeExp(this.selectedSheetIndex, new t.CellRange(e, o), new t.CellRange(n, i), !1, !0) && (f = "=" + g._getStringExpression())
              } catch (e) {}
              this.setCellData(n, i, f)
            } else C = !0;
            if (m ? this.selectedSheet._styledCells[w] = this._cloneObject(m) : delete this.selectedSheet._styledCells[w], l)(s = this.selectedSheet.findTable(e, o)) && (d = s.getRange(), s === r && e === n && s.showHeaderRow && e === d.topRow && (a = o - d.leftCol, s._updateColumnName(a, f)));
            else {
              if (delete this.selectedSheet._styledCells[p], s = this.selectedSheet.findTable(e, o)) {
                if (d = s.getRange(), s === r && e === n && s.showHeaderRow && e === d.topRow) return a = o - d.leftCol, void s._updateColumnName(a, c);
                if (s.showHeaderRow && e === d.topRow) return
              }
              this.setCellData(e, o, null)
            }
          }, s.prototype._allowExchangeCells = function(e, n) {
            var i, l, s, r, a, h = this.selectedSheet.tables;
            for (i = 0; i < h.length; i++)
              if (l = h[i], e ? (s = l.getRange(o.TableSection.Data), this._draggingCells.rowSpan, r = new t.CellRange(this._draggingCells.topRow, s.leftCol, this._draggingCells.bottomRow, s.rightCol), a = new t.CellRange(this._dropRange.topRow, s.leftCol, this._dropRange.bottomRow, s.rightCol)) : (s = l.getRange(), this._draggingCells.columnSpan, r = new t.CellRange(s.topRow, this._draggingCells.leftCol, s.bottomRow, this._draggingCells.rightCol), a = new t.CellRange(s.topRow, this._dropRange.leftCol, s.bottomRow, this._dropRange.rightCol)), s.intersects(r) && !s.contains(r) || s.intersects(a) && !s.contains(a) || s.contains(r) && !s.contains(a) || !s.contains(r) && s.contains(a)) return !1;
            return !0
          }, s.prototype._exchangeTableColumns = function(e) {
            var t, o, n, i, l, s, r = this.selectedSheet.tables;
            for (t = 0; t < r.length; t++)
              if (o = r[t], (n = o.getRange()).leftCol <= this._draggingCells.leftCol && n.rightCol >= this._draggingCells.rightCol)
                for (i = (e ? this._draggingCells.leftCol : this._draggingCells.rightCol) - n.leftCol, l = (e ? this._dropRange.leftCol : this._dropRange.rightCol) - n.leftCol, s = 0; s < this._draggingCells.columnSpan; s++) o._moveColumns(i, l), e ? (i++, l++) : (i--, l--)
          }, s.prototype._exchangeCellStyle = function(e) {
            var t, o, n, i, l, s = 0,
              r = [];
            for (t = this._draggingCells.topRow; t <= this._draggingCells.bottomRow; t++)
              for (o = this._draggingCells.leftCol; o <= this._draggingCells.rightCol; o++) n = t * this.columns.length + o, this.selectedSheet._styledCells[n] ? (r.push(this._cloneObject(this.selectedSheet._styledCells[n])), delete this.selectedSheet._styledCells[n]) : r.push(void 0);
            if (e) {
              if (this._draggingColumn)
                for (l = this._draggingCells.rightCol - this._draggingCells.leftCol + 1, o = this._draggingCells.leftCol - 1; o >= this._dropRange.leftCol; o--)
                  for (t = 0; t < this.rows.length; t++) n = t * this.columns.length + o, i = t * this.columns.length + o + l, this.selectedSheet._styledCells[n] ? (this.selectedSheet._styledCells[i] = this._cloneObject(this.selectedSheet._styledCells[n]), delete this.selectedSheet._styledCells[n]) : delete this.selectedSheet._styledCells[i];
              else if (this._draggingRow)
                for (l = this._draggingCells.bottomRow - this._draggingCells.topRow + 1, t = this._draggingCells.topRow - 1; t >= this._dropRange.topRow; t--)
                  for (o = 0; o < this.columns.length; o++) n = t * this.columns.length + o, i = (t + l) * this.columns.length + o, this.selectedSheet._styledCells[n] ? (this.selectedSheet._styledCells[i] = this._cloneObject(this.selectedSheet._styledCells[n]), delete this.selectedSheet._styledCells[n]) : delete this.selectedSheet._styledCells[i]
            } else if (this._draggingColumn)
              for (l = this._draggingCells.rightCol - this._draggingCells.leftCol + 1, o = this._draggingCells.rightCol + 1; o <= this._dropRange.rightCol; o++)
                for (t = 0; t < this.rows.length; t++) n = t * this.columns.length + o, i = t * this.columns.length + o - l, this.selectedSheet._styledCells[n] ? (this.selectedSheet._styledCells[i] = this._cloneObject(this.selectedSheet._styledCells[n]), delete this.selectedSheet._styledCells[n]) : delete this.selectedSheet._styledCells[i];
            else if (this._draggingRow)
              for (l = this._draggingCells.bottomRow - this._draggingCells.topRow + 1, t = this._draggingCells.bottomRow + 1; t <= this._dropRange.bottomRow; t++)
                for (o = 0; o < this.columns.length; o++) n = t * this.columns.length + o, i = (t - l) * this.columns.length + o, this.selectedSheet._styledCells[n] ? (this.selectedSheet._styledCells[i] = this._cloneObject(this.selectedSheet._styledCells[n]), delete this.selectedSheet._styledCells[n]) : delete this.selectedSheet._styledCells[i];
            for (t = this._dropRange.topRow; t <= this._dropRange.bottomRow; t++)
              for (o = this._dropRange.leftCol; o <= this._dropRange.rightCol; o++) n = t * this.columns.length + o, r[s] ? this.selectedSheet._styledCells[n] = r[s] : delete this.selectedSheet._styledCells[n], s++
          }, s.prototype._containsMergedCells = function(e, t) {
            var o, n;
            if (!(t = t || this.selectedSheet)) return !1;
            for (o = 0; o < t._mergedRanges.length; o++)
              if (n = t._mergedRanges[o], e.intersects(n)) return !0;
            return !1
          }, s.prototype._multiSelectColumns = function(e) {
            var o;
            e && this._columnHeaderClicked && ((o = new t.CellRange(e.row, e.col)).row = this.itemsSource && this.rows[0] && !this.rows[0].isVisible ? 1 : 0, o.row2 = this.rows.length - 1, o.col2 = this.selection.col2, this.select(o))
          }, s.prototype._cumulativeOffset = function(t) {
            var o = 0,
              n = 0;
            do {
              o += t.offsetTop || 0, n += t.offsetLeft || 0, t = t.offsetParent
            } while (t);
            return new e.Point(n, o)
          }, s.prototype._cumulativeScrollOffset = function(t) {
            var o = 0,
              n = 0;
            do {
              o += t.scrollTop || 0, n += t.scrollLeft || 0, t = t.offsetParent
            } while (t && !(t instanceof HTMLBodyElement));
            return o += document.body.scrollTop || document.documentElement.scrollTop, n += document.body.scrollLeft || document.documentElement.scrollLeft, new e.Point(n, o)
          }, s.prototype._checkHitWithinSelection = function(e) {
            var o;
            if (null != e && e.cellType === t.CellType.Cell) {
              if ((o = this.getMergedRange(this.cells, e.row, e.col)) && o.contains(this.selection)) return !0;
              if (this.selection.row === e.row && this.selection.col === e.col) return !0
            }
            return !1
          }, s.prototype._clearForEmptySheet = function(e) {
            this.selectedSheet && 0 === this[e].length && !0 !== this._isCopying && !0 !== this._isUndoing && !0 !== this._isSorting && (this.selectedSheet._mergedRanges.length = 0, this.selectedSheet._styledCells = null, this.select(new t.CellRange))
          }, s.prototype._containsGroupRows = function(e) {
            var o;
            for (o = e.topRow; o <= e.bottomRow; o++)
              if (this.rows[o] instanceof t.GroupRow) return !0;
            return !1
          }, s.prototype._delSeletionContent = function(n) {
            var i, l, s, r, a, h, c, d, u, _, g, f = this.selectedSheet.selectionRanges.slice(),
              p = !1,
              w = new o._EditAction(this),
              m = this.editableCollectionView,
              C = !1,
              b = this.scrollPosition;
            if (!this.isReadOnly)
              if (this.allowDelete && this.selection.isValid && 0 === this.selection.leftCol && this.selection.rightCol === this.columns.length - 1) this.deleteRows(this.selection.topRow, this.selection.rowSpan);
              else {
                for (this.beginUpdate(), this.undoStack.stackSize > 0 && (w = new o._EditAction(this)), null != f && 0 !== f.length || (f = [this.selection]), s = 0; s < f.length; s++) {
                  if (i = f[s], l = new t.CellRange, _ = new t.CellEditEndingEventArgs(this.cells, l, n), (d = this.selectedSheet.tables.length) > 0)
                    for (h = d - 1; h >= 0; h--) c = this.selectedSheet.tables[h], i.contains(c.getRange()) && (w && w._storeDeletedTables(c), this.selectedSheet.tables.remove(c));
                  for (a = i.topRow; a <= i.bottomRow; a++)
                    if ((g = this.rows[a]) && !g.isReadOnly && (g.visible || this.selectedSheet._freezeHiddenRows && this.selectedSheet._freezeHiddenRows[a]))
                      for (r = i.leftCol; r <= i.rightCol; r++)(u = this._getBindingColumn(this.cells, a, this.columns[r])).isReadOnly || !1 !== u.isRequired && (null != u.isRequired || u.dataType != e.DataType.String) || !u.visible && !this.selectedSheet._freezeHiddenCols[r] || this.getCellData(a, r, !0) && (l.setRange(a, r), _.cancel = !1, this.onBeginningEdit(_) && (m && (C || (C = !0, m.beginUpdate()), m.editItem(g.dataItem), this._edtHdl._edItem = m.currentEditItem), this.setCellData(a, r, "", !0), p = !0, this.onCellEditEnding(_), this.onCellEditEnded(_)));
                  C && (m.endUpdate(), m._pendingRefresh = !1, i.rowSpan > 1 && m.commitEdit())
                }
                p && w && (w.saveNewState(), this._undoStack._addAction(w)), this.selection = i, this.scrollPosition = b, this.endUpdate()
              }
          }, s.prototype._updateAffectedFormula = function(t, o, n, i, l) {
            var s, r, a, h, c, d, u, _, g, f = [],
              p = [],
              w = this.selection.clone();
            for (this.selectedSheet._storeRowSettings(), this.beginUpdate(), s = 0; s < this.sheets.length; s++)
              for (a = (r = this.sheets[s]).grid, h = 0; h < a.rows.length; h++)
                for (c = 0; c < a.columns.length; c++)
                  if ((_ = a.getCellData(h, c, !1)) && e.isString(_) && "=" === _[0] && (g = this._updateCellRef(_, s, t, o, n, i, l))) {
                    if (f.push({
                      sheet: r,
                      point: new e.Point(h, c),
                      formula: _
                    }), d = h, u = c, s === this.selectedSheetIndex && (i ? h >= t && (n ? d += o : d -= o) : c >= t && (n ? u += o : u -= o), !n && (i && h <= t && h >= t - o + 1 || !i && c <= t && c >= t - o + 1))) continue;
                    a.setCellData(h, c, g, !1), p.push({
                      sheet: r,
                      point: new e.Point(d, u),
                      formula: g
                    })
                  }
            return this.selection = w, this.endUpdate(), {
              oldFormulas: f,
              newFormulas: p
            }
          }, s.prototype._updateAffectedNamedRanges = function(t, o, n, i) {
            var l, s, r, a, h = [],
              c = [];
            for (l = 0; l < this.definedNames.length; l++)(r = (s = this.definedNames[l]).value) && e.isString(r) && (a = this._updateCellRef(r, this.selectedSheetIndex, t, o, n, i)) && (h.push({
              name: s.name,
              value: r
            }), s.value = a, c.push({
              name: s.name,
              value: a
            }));
            return {
              oldDefinedNameVals: h,
              newDefinedNameVals: c
            }
          }, s.prototype._updateFormulaBoundaryForEditingCell = function(t, o) {
            var n, i, l, s, r = [],
              a = [];
            for (this.beginUpdate(), n = t; n < this.rows.length; n++)(l = this.getCellData(n, o, !1)) && e.isString(l) && "=" === l[0] && (s = this._updateCellBoundary(l, t, o)) && (r.push({
              point: new e.Point(n, o),
              formula: l
            }), this.setCellData(n, o, s, !1), a.push({
              point: new e.Point(n, o),
              formula: s
            }));
            for (i = o; i < this.columns.length; i++)(l = this.getCellData(t, i, !1)) && e.isString(l) && "=" === l[0] && (s = this._updateCellBoundary(l, t, o)) && (r.push({
              point: new e.Point(t, i),
              formula: l
            }), this.setCellData(t, i, s, !1), a.push({
              point: new e.Point(t, i),
              formula: s
            }));
            return this.endUpdate(), {
              oldFormulas: r,
              newFormulas: a
            }
          }, s.prototype._updateColumnFiler = function(e, t) {
            for (var o = JSON.parse(this._filter.filterDefinition), n = 0; n < o.filters.length; n++) {
              var i = o.filters[n];
              if (i.columnIndex === e) {
                i.columnIndex = t;
                break
              }
            }
            this._filter.filterDefinition = JSON.stringify(o)
          }, s.prototype._isDescendant = function(e, t) {
            for (var o = t.parentNode; null != o;) {
              if (o === e) return !0;
              o = o.parentNode
            }
            return !1
          }, s.prototype._clearCalcEngine = function() {
            this._calcEngine._clearExpressionCache()
          }, s.prototype._getRangeString = function(o, n, i) {
            void 0 === i && (i = !0);
            var l, s, r, a, h, c = "",
              d = !0,
              u = n.grid;
            if (e.isArray(o))
              if (o.length > 1) {
                if (this._isMultipleRowsSelected(o, n)) {
                  for (c = "", s = 0; s < o.length; s++) c && (c += "\n"), c += this._getRangeString(o[s], n);
                  return c
                }
                if (!this._isMultipleColumnsSelected(o, n)) return "";
                for (c = "", l = 0, d = !0; l < u.rows.length; l++) {
                  for (d || (c += "\n"), d = !1, s = 0, p = !0; s < o.length; s++)(r = o[s].clone()).row = r.row2 = l, p || (c += "\t"), p = !1, c += this._getRangeString(o[s], n);
                  return c
                }
              } else switch (h = o[0], this.selectionMode) {
                case t.SelectionMode.Row:
                case t.SelectionMode.RowRange:
                  h.col = 0, h.col2 = n.grid.columns.length - 1;
                  break;
                case t.SelectionMode.ListBox:
                  h.col = 0, h.col2 = n.grid.columns.length - 1;
                  for (var _ = 0; _ < n.grid.rows.length; _++) n.grid.rows[_].isSelected && n.grid.rows[_].isVisible && (h.row = h.row2 = _, c && (c += "\n"), c += this._getRangeString(h, n));
                  return c
              }
            for (var g = (h = e.asType(e.isArray(o) ? o[0] : o, t.CellRange)).topRow; g <= h.bottomRow; g++)
              if (u.rows[g] && u.rows[g].isVisible) {
                d || (c += "\n"), d = !1;
                for (var f = h.leftCol, p = !0; f <= h.rightCol; f++)
                  if (u.columns[f] && u.columns[f].isVisible) {
                    p || (c += "\t"), p = !1;
                    var w = this._getCellStyle(g, f, n),
                      m = w ? w.format : "",
                      C = u.columns[f];
                    i ? (a = u.getCellData(g, f, !1), e.isString(a) && "=" === a[0] && (a = this._evaluate(a, m, n, g, f)), a = this._formatEvaluatedResult(a, C, m)) : (a = u.getCellData(g, f, !1), e.isDate(a) && (a = e.Globalize.format(a, C.format)), C.dataMap && (a = C.dataMap.getDisplayValue(a))), (a = (a = null == a ? "" : a.toString()).replace(/\t/g, " ")).indexOf("\n") > -1 && (a = '"' + a.replace(/"/g, '""') + '"'), c += a
                  }
              }
            return c
          }, s.prototype._getSelectionForListBoxMode = function(e) {
            for (var o, n = 0; n < e.rows.length; n++) e.rows[n].isSelected && (o ? o.row2 = n : o = new t.CellRange(n, 0, n, e.columns.length - 1));
            return o
          }, s.prototype._containsRandFormula = function(t, o) {
            for (var n = 0; n < t.length; n++)
              for (var i = t[n], l = i.topRow; l <= i.bottomRow && l < o.grid.rows.length; l++)
                for (var s = i.leftCol; s <= i.rightCol && s < o.grid.columns.length; s++) {
                  var r = o.grid.getCellData(l, s, !1);
                  if (e.isString(r) && "=" === r[0] && -1 !== r.search(/rand/i)) return !0
                }
            return !1
          }, s.prototype._isMultipleRowsSelected = function(e, t) {
            var o, n, i;
            e && e.length > 1 ? i = e : this.selectedSheet.selectionRanges.length > 1 && (i = this.selectedSheet.selectionRanges), o = i[0];
            for (var l = 1; l < i.length; l++)
              if ((n = i[l]).leftCol !== o.leftCol || n.rightCol !== o.rightCol) return !1;
            return !0
          }, s.prototype._isMultipleColumnsSelected = function(e, t) {
            var o, n, i;
            e && e.length > 1 ? i = e : this.selectedSheet.selectionRanges.length > 1 && (i = this.selectedSheet.selectionRanges), o = i[0];
            for (var l = 0; l < i.length; l++)
              if ((n = i[l]).topRow !== o.topRow || n.bottomRow !== o.bottomRow) return !1;
            return !0
          }, s.prototype._postSetClipStringProcess = function(o, n, i, l, s, r) {
            var a, h, c, d, u, _, g, f = !1;
            return d = new t.CellRangeEventArgs(this.cells, new t.CellRange(n, i), o), this.onPastingCell(d) && (l >= 0 && s >= 0 && this._copiedSheet && (a = this._copiedSheet._getMergedRange(l, s)) && a.topRow === l && a.leftCol === s && (h = (h = n + a.rowSpan - 1) < this.rows.length ? h : this.rows.length - 1, c = (c = i + a.columnSpan - 1) < this.columns.length ? c : this.columns.length - 1, this.mergeRange(new t.CellRange(n, i, h, c), !0)), this.cells.setCellData(n, i, d.data) && (e.isString(d.data) && (u = d.data.match(/\n/g)) && u.length > 0 && (r ? r.whiteSpace = "pre" : r = {
              whiteSpace: "pre"
            }, this.rows[n].height = this.rows.defaultSize * (u.length + 1)), _ = n * this.columns.length + i, (a = this.selectedSheet._getMergedRange(n, i)) && (_ = a.topRow * this.columns.length + a.leftCol), r && (g = this._cloneObject(r)), this.selectedSheet._styledCells[_] = g, this.onPastedCell(d), f = !0)), f
          }, s.prototype._delCutData = function(o, n) {
            var i, l, s, r, a = this._copiedSheet === this.selectedSheet ? this : this._copiedSheet.grid;
            for (i = (r = this.selectionMode === t.SelectionMode.ListBox ? this._getSelectionForListBoxMode(a) : this._copiedRanges[0]).topRow; i <= r.bottomRow; i++)
              if (null != a.rows[i])
                for (l = r.leftCol; l <= r.rightCol; l++)(this._copiedSheet !== this.selectedSheet || i < this.selection.topRow || i > this.selection.topRow + o - 1 || l < this.selection.leftCol || l > this.selection.leftCol + n - 1) && (0 == (s = a._getBindingColumn(a.cells, i, a.columns[l])).isRequired || null == s.isRequired && s.dataType == e.DataType.String) && a.getCellData(i, l, !0) && a.setCellData(i, l, "", !0)
          }, s.prototype._containsMultiLineText = function(e) {
            for (var t = 0; t < e.length; t++)
              for (var o = e[t], n = 0; n < o.length; n++)
                if (o[n].indexOf("\n") >= 0) return !0;
            return !1
          }, s.prototype._sortByRow = function(e, t) {
            return e.topRow > t.topRow ? 1 : e.topRow < t.topRow ? -1 : 0
          }, s.prototype._sortByColumn = function(e, t) {
            return e.leftCol > t.leftCol ? 1 : e.leftCol < t.leftCol ? -1 : 0
          }, s.prototype._setFlexSheetToDirty = function() {
            this.columns._dirty = !0, this.rows._dirty = !0, this.rowHeaders.columns._dirty = !0, this.rowHeaders.rows._dirty = !0, this.columnHeaders.columns._dirty = !0, this.columnHeaders.rows._dirty = !0
          }, s.convertNumberToAlpha = function(e) {
            var t, o, n = "";
            if (e >= 0)
              do {
                t = Math.floor(e / 26), o = e % 26, n = String.fromCharCode(o + 65) + n, e = t - 1
              } while (t);
            return n
          }, s.prototype._updateFormulaForReorderingRows = function(e, t, o) {
            void 0 === o && (o = !1);
            var n, i, l = o ? e - t : t - e;
            this.beginUpdate();
            for (var s = 0; s < this.columns.length; s++)
              if ((n = this.getCellData(t, s, !1)) && "string" == typeof n && "=" === n[0] && 0 !== l) {
                try {
                  i = this._calcEngine._parse(n)
                } catch (e) {
                  continue
                }
                i._updateCellRangeExpForReorderingRows(l) && this.setCellData(t, s, "=" + i._getStringExpression())
              }
            this.endUpdate()
          }, s.prototype._updateFormulaForDropping = function(t) {
            var o, n, i, l, s, r, a, h, c, d = [],
              u = [];
            for (this.beginUpdate(), i = 0; i < this.sheets.length; i++)
              for (n = (o = this.sheets[i]).grid, l = 0; l < n.rows.length; l++)
                for (r = 0; r < n.columns.length; r++)(h = n.getCellData(l, r, !1)) && "string" == typeof h && "=" === h[0] && (c = this._updateCellRefForDropping(h, i, t)) && (n.setCellData(l, r, c, !1), t && i === this.selectedSheetIndex ? (s = n.rows[l], a = n.columns[r], d.push({
                  sheet: o,
                  row: s,
                  column: a,
                  formula: h
                }), u.push({
                  sheet: o,
                  row: s,
                  column: a,
                  formula: c
                })) : (d.push({
                  sheet: o,
                  point: new e.Point(l, r),
                  formula: h
                }), u.push({
                  sheet: o,
                  point: new e.Point(l, r),
                  formula: c
                })));
            return this.endUpdate(), {
              oldFormulas: d,
              newFormulas: u
            }
          }, s.prototype._updateNamedRangesForDropping = function(t) {
            var o, n, i, l, s = [],
              r = [];
            for (o = 0; o < this.definedNames.length; o++)(i = (n = this.definedNames[o]).value) && e.isString(i) && (l = this._updateCellRefForDropping(i, this.selectedSheetIndex, t)) && (s.push({
              name: n.name,
              value: i
            }), n.value = l, r.push({
              name: n.name,
              value: l
            }));
            return {
              oldDefinedNameVals: s,
              newDefinedNameVals: r
            }
          }, s.prototype._updateCellRefForDropping = function(e, t, o) {
            void 0 === o && (o = !1);
            var n;
            try {
              n = this._calcEngine._parse(e)
            } catch (e) {
              return null
            }
            return n._moveCellRangeExp(t, this._draggingCells, this._dropRange, o) ? "=" + n._getStringExpression() : null
          }, s.prototype._updateCellStyleForReorderingRows = function(e, t, o) {
            var n, i, l = this.columns.length;
            for (n = 0; n < l; n++)(i = this._getCellStyle(e, n)) && (o[t * l + n] = i)
          }, s.prototype._scanFormulas = function() {
            for (var t = [], o = 0; o < this.rows.length; o++)
              for (var n = 0; n < this.columns.length; n++) {
                var i = this.getCellData(o, n, !1);
                i && e.isString(i) && "=" === i[0] && t.push({
                  row: o,
                  column: n,
                  formula: i
                })
              }
            return t
          }, s.prototype._resetFormulas = function(e) {
            var t = this;
            e && t.deferUpdate(function() {
              for (var o = 0; o < e.length; o++) {
                var n = e[o];
                t.setCellData(n.row, n.column, n.formula)
              }
            })
          }, s.prototype._getCellStyle = function(e, t, o) {
            return (o = o || this.selectedSheet) ? o.getCellStyle(e, t) : null
          }, s.prototype._validateSheetName = function(e) {
            for (var t = 0; t < this.sheets.length; t++)
              if (this.sheets[t].name === e) return !0;
            return !1
          }, s.prototype._checkExistDefinedName = function(e, t, o) {
            void 0 === o && (o = -1);
            for (var n = 0; n < this.definedNames.length; n++)
              if (this.definedNames[n].name === e && this.definedNames[n].sheetName === t && n !== o) return !0;
            return !1
          }, s.prototype._updateDefinedNameWithSheetRefUpdating = function(e, t) {
            for (var o, n, i, l = 0; l < this.definedNames.length; l++)
              if ((o = this.definedNames[l]).sheetName === e && null != o.sheetName && (o._sheetName = t), (n = o.value.match(/(\w+)\!\$?[A-Za-z]+\$?\d+/g)) && n.length > 0)
                for (var s = 0; s < n.length; s++)(i = n[s]).substring(0, i.indexOf("!")) === e && (o.value = o.value.replace(i, i.replace(e, t)))
          }, s.prototype._updateFormulasWithNameUpdating = function(e, t, o) {
            void 0 === o && (o = !1);
            var n, i, l, s, r, a, h, c, d, u, _ = this.selection.clone(),
              g = !1;
            for (u = o ? /\[/ : /\w/, this.selectedSheet._storeRowSettings(), this.beginUpdate(), n = 0; n < this.sheets.length; n++)
              for (s = this.sheets[n].grid, i = 0; i < s.rows.length; i++)
                for (l = 0; l < s.columns.length; l++)
                  if ((r = s.getCellData(i, l, !1)) && "string" == typeof r && "=" === r[0]) {
                    for (g = !1, a = r.indexOf(e), d = new RegExp(e, "g"); a > -1;) h = "", c = "", a > 0 && (h = r[a - 1]), a + e.length < r.length && (c = r[a + e.length]), !/\w/.test(h) && (o && u.test(c) || !o && !u.test(c)) && (r = r.replace(d, function(o, n) {
                      return n === a ? t : e
                    }), g = !0), a = r.indexOf(e, a + e.length);
                    g && s.setCellData(i, l, r, !1)
                  }
            this.selection = _, this.endUpdate()
          }, s.prototype._getDefinedNameIndexByName = function(e) {
            for (var t = 0; t < this.definedNames.length; t++)
              if (this.definedNames[t].name === e) return t;
            return -1
          }, s.prototype._updateTablesForUpdatingRow = function(e, t, o) {
            var n, i, l, s, r;
            if (this.selectedSheet.tables.length > 0)
              for (n = this.selectedSheet.tables.length - 1; n >= 0; n--)
                if (i = this.selectedSheet.tables[n], l = i.getRange(), o)
                  if ((s = e + t - 1) < l.topRow) i._updateTableRange(-t, -t, 0, 0);
                  else if (s >= l.topRow && s <= l.bottomRow) {
                    if (e <= l.topRow && s === l.bottomRow) {
                      null == r && (r = []), r.push(i), this.selectedSheet.tables.remove(i);
                      continue
                    }
                    e < l.topRow ? (i.showHeaderRow && (i._showHeaderRow = !1), i._updateTableRange(e - l.topRow, -t, 0, 0)) : (e === l.topRow && i.showHeaderRow && (i._showHeaderRow = !1), s === l.bottomRow && i.showTotalRow && (i._showTotalRow = !1), i._updateTableRange(0, -t, 0, 0))
                  } else e <= l.topRow ? (null == r && (r = []), r.push(i), this.selectedSheet.tables.remove(i)) : e <= l.bottomRow && (i.showTotalRow && (i._showTotalRow = !1), i._updateTableRange(0, e - l.bottomRow - 1, 0, 0));
                else e <= l.topRow ? i._updateTableRange(t, t, 0, 0) : e > l.topRow && e <= l.bottomRow && i._updateTableRange(0, t, 0, 0);
            return r
          }, s.prototype._updateTablesForUpdatingColumn = function(e, t, o) {
            var n, i, l, s, r, a, h, c;
            if (this.selectedSheet.tables.length > 0)
              for (n = this.selectedSheet.tables.length - 1; n >= 0; n--)
                if (i = this.selectedSheet.tables[n], l = i.getRange(), o)
                  if ((s = e + t - 1) < l.leftCol) i._updateTableRange(0, 0, -t, -t);
                  else if (s >= l.leftCol && s <= l.rightCol) {
                    if (e <= l.leftCol && s === l.rightCol) {
                      null == c && (c = []), c.push(i), this.selectedSheet.tables.remove(i);
                      continue
                    }
                    e < l.leftCol ? (r = t - l.leftCol + e, a = l.leftCol, i._updateTableRange(0, 0, e - l.leftCol, -t)) : (r = t, a = e, i._updateTableRange(0, 0, 0, -r)), i._columns.splice(a - l.leftCol, r)
                  } else e <= l.leftCol ? (null == c && (c = []), c.push(i), this.selectedSheet.tables.remove(i)) : e <= l.rightCol && (r = l.rightCol - e + 1, i._updateTableRange(0, 0, 0, -r), i._columns.splice(e, r));
                else if (e <= l.leftCol) i._updateTableRange(0, 0, t, t);
                else if (e > l.leftCol && e <= l.rightCol)
                  for (i._updateTableRange(0, 0, 0, t), a = e - l.leftCol, h = 0; h < t; h++) i._addColumn(a + h);
            return c
          }, s.prototype._isDisableDeleteRow = function(e, t) {
            var o, n, i;
            if (this.selectedSheet.tables.length > 0)
              for (o = 0; o < this.selectedSheet.tables.length; o++)
                if (n = this.selectedSheet.tables[o], i = n.getRange(), n.showHeaderRow && i.topRow >= e && i.topRow <= t) return !0;
            return !1
          }, s.prototype._copy = function(e, t) {
            var o, n;
            if ("columns" == e) {
              if (i.prototype._copy.call(this, e, t), this.itemsSource && (o = this.rows[0]) instanceof h) {
                o._ubv = null, o._ubv = {};
                for (var l = 0; l < this.columns.length; l++) n = this.columns[l], o._ubv[n._hash] = n.header
              }
              return !0
            }
            return !1
          }, s.prototype._getTableSheetIndex = function(e, t) {
            this.sheets;
            for (var o = 0; o < e.length; o++)
              if (e[o].indexOf(t) > -1) return o;
            return -1
          }, s.prototype._sheetSortConverter = function(t, o, n, l) {
            return n = i.prototype._sortConverter.call(this, t, o, n, l), e.isString(n) && n.length > 0 && "=" === n[0] && (n = this.evaluate(n)), n
          }, s.prototype._formatEvaluatedResult = function(t, o, n) {
            return e.isPrimitive(t) ? (o.dataMap && (t = o.dataMap.getDisplayValue(t)), !e.isInt(t) || o.format || o.dataMap || n ? ((n && "@" !== n || o.format && "@" !== o.format) && e.isString(t) && (+t).toString() === t && (t = +t), t = null != t ? e.Globalize.format(t, n || o.format) : "") : t = t.toString()) : t && (!e.isInt(t.value) || o.format || o.dataMap || n || t.format ? (e.isString(t.value) && (+t.value).toString() === t.value && (t.value = +t.value), t = null != t.value ? e.Globalize.format(t.value, n || t.format || o.format) : "") : t = t.value.toString()), t
          }, s.prototype._updateCellRef = function(e, t, o, n, i, l, s) {
            var r;
            try {
              r = this._calcEngine._parse(e)
            } catch (e) {
              return null
            }
            return r._updateCellRangeExp(t, o, n, i, l, s) ? "=" + r._getStringExpression() : null
          }, s.prototype._updateCellBoundary = function(e, t, n) {
            var i;
            try {
              i = this._calcEngine._parse(e)
            } catch (e) {
              return null
            }
            return i instanceof o._FunctionExpression && i._updateCellBoundary(t, n) ? "=" + i._getStringExpression() : null
          }, s.prototype._fillData = function(o) {
            var n, i, l, s, r, a, h, c, u, _, g, f, p, w, m, C, b, y = this._fillingRange || this.selection,
              S = this._fillingSource,
              R = !o || 0 != (o & d.CopyFormat),
              v = !!o && 0 != (o & d.CopyContent),
              x = !o || 0 != (o & d.FillSeries),
              T = S.leftCol === y.leftCol && S.rightCol === y.rightCol,
              I = [];
            for (this.beginUpdate(), this._resetCellsForFillRange(o), n = 0; n < y.rowSpan; n++)
              for ((l = (c = (r = y.topRow + n) - S.topRow) % S.rowSpan) < 0 && (l += S.rowSpan), i = 0; i < y.columnSpan; i++) u = (a = y.leftCol + i) - S.leftCol, c >= 0 && c < S.rowSpan && u >= 0 && u < S.columnSpan || ((s = u % S.columnSpan) < 0 && (s += S.columnSpan), w = !1, (p = this.getMergedRange(this.cells, S.topRow + l, S.leftCol + s)) && (w = p.topRow === S.topRow + l && p.leftCol === S.leftCol + s) && (this.getMergedRange(this.cells, r, a) || this.mergeRange(new t.CellRange(r, a, r + p.rowSpan - 1, a + p.columnSpan - 1))), R && (f = null, (g = this.selectedSheet.getCellStyle(S.topRow + l, S.leftCol + s)) && (f = this._cloneObject(g)), h = r * this.columns.length + a, this.selectedSheet._styledCells[h] = f), p && !w || (v ? (_ = this.getCellData(S.topRow + l, S.leftCol + s, !1)) && e.isString(_) && "=" === _[0] ? this._fillFormula(_, S.topRow + l, S.leftCol + s, r, a) : this.setCellData(r, a, _) : x && (T ? (m = i, C = l) : (m = n, C = s), null == (_ = this.getCellData(S.topRow + l, S.leftCol + s, !1)) || e.isString(_) ? (_ && e.isString(_) && "=" === _[0] ? this._fillFormula(_, S.topRow + l, S.leftCol + s, r, a) : this.setCellData(r, a, _), I[m] = null) : (I[m] || (I[m] = this._getFillSeries(T, m, C)), (b = I[m]) && (b.items, T ? (_ = this._getFillData(l, c, S, T, b, null != o || 0 === m), b.endIndex === S.topRow + l && (I[m] = null)) : (_ = this._getFillData(s, u, S, T, b, null != o || 0 === m), b.endIndex === S.leftCol + s && (I[m] = null)), this.setCellData(r, a, _))))));
            this._fillingRange && this.select(this._fillingRange, !1), this.endUpdate()
          }, s.prototype._getFillData = function(e, t, o, n, i, l) {
            var r, a, h = i.items;
            return 1 !== h.length || l ? (r = n ? Math.floor(t / o.rowSpan) * i.itemIndexes[i.itemIndexes.length - 1] + o.topRow + e - i.startIndex + 1 : Math.floor(t / o.columnSpan) * i.itemIndexes[i.itemIndexes.length - 1] + o.leftCol + e - i.startIndex + 1, a = this._getLinearBestFitTrendData(h, i.itemIndexes, r), "date" === i.type && (a = s._fromOADate(a)), a) : h[0]
          }, s.prototype._fillFormula = function(e, o, n, i, l) {
            var s, r, a;
            try {
              s = this._calcEngine._parse(e), r = new t.CellRange(o, n), a = new t.CellRange(i, l), s._moveCellRangeExp(this.selectedSheetIndex, r, a, !1, !0) && (e = "=" + s._getStringExpression())
            } catch (e) {} finally {
              this.setCellData(i, l, e)
            }
          }, s.prototype._getFillSeries = function(t, o, n) {
            var i, l, r, a, h, c, d, u, _ = this._fillingSource,
              g = [],
              f = [];
            if (t)
              for (n = _.topRow + n, a = 0, h = _.topRow; h <= _.bottomRow; h++) {
                if (c = this.getMergedRange(this.cells, h, _.leftCol + o))
                  if (d && !c.equals(d)) {
                    if (a += d.rowSpan, d = c, c.leftCol !== _.leftCol + o) continue
                  } else {
                    if (c.topRow !== h || c.leftCol !== _.leftCol + o) continue;
                    a += 1, d = c
                  } else d ? (a += d.rowSpan, d = null) : a += 1;
                if (null != (u = this.getCellData(h, _.leftCol + o, !1)) && "" !== u)
                  if (e.isNumber(u) || e.isDate(u))
                    if (i = e.isNumber(u) ? "number" : "date", e.isDate(u) && (u = s._toOADate(u)), 0 === g.length) r = h, l = i, g.push(u), f.push(a);
                    else if (i === l) g.push(u), f.push(a);
                    else {
                      if (n >= r && n <= h) return {
                        type: l,
                        startIndex: r,
                        endIndex: h - 1,
                        items: g,
                        itemIndexes: f
                      };
                      g.splice(0, g.length), f.splice(0, f.length)
                    } else if (g.length > 0) {
                    if (n >= r && n <= h) return {
                      type: l,
                      startIndex: r,
                      endIndex: h - 1,
                      items: g,
                      itemIndexes: f
                    };
                    g.splice(0, g.length), f.splice(0, f.length)
                  }
              } else
              for (n = _.leftCol + n, a = 0, h = _.leftCol; h <= _.rightCol; h++) {
                if (c = this.getMergedRange(this.cells, _.topRow + o, h))
                  if (d && !c.equals(d)) {
                    if (a += d.columnSpan, d = c, c.topRow !== _.topRow + o) continue
                  } else {
                    if (c.leftCol !== h || c.topRow !== _.topRow + o) continue;
                    a += 1, d = c
                  } else d ? (a += d.columnSpan, d = null) : a += 1;
                if (null != (u = this.getCellData(_.topRow + o, h, !1)) && "" !== u)
                  if (e.isNumber(u) || e.isDate(u))
                    if (i = e.isNumber(u) ? "number" : "date", e.isDate(u) && (u = s._toOADate(u)), 0 === g.length) r = h, l = i, g.push(u), f.push(a);
                    else if (i === l) g.push(u), f.push(a);
                    else {
                      if (n >= r && n <= h) return {
                        type: l,
                        startIndex: r,
                        endIndex: h - 1,
                        items: g,
                        itemIndexes: f
                      };
                      g.splice(0, g.length), f.splice(0, f.length)
                    } else if (g.length > 0) {
                    if (n >= r && n <= h) return {
                      type: l,
                      startIndex: r,
                      endIndex: h - 1,
                      items: g,
                      itemIndexes: f
                    };
                    g.splice(0, g.length), f.splice(0, f.length)
                  }
              }
            return g.length > 0 ? {
              type: l,
              startIndex: r,
              endIndex: h - 1,
              items: g,
              itemIndexes: f
            } : null
          }, s.prototype._getLinearBestFitTrendData = function(e, t, o) {
            var n, i, l, s, r, a, h = 0,
              c = 0,
              d = 0,
              u = 0;
            if (1 === (s = e.length)) return e[0] + o - 1;
            for (n = 0; n < t.length; n++) h += i = t[n], d += i * i, c += l = e[n], u += i * l;
            return r = (s * u - h * c) / (s * d - h * h), a = (c * d - h * u) / (s * d - h * h), o * r + a
          }, s.prototype._getCellSettingsForFill = function(e, t) {
            var o, n, i, l, s, r;
            for (e || (e = this._fillingSource), t || (t = this._fillingRange), s = [], o = t.topRow; o <= t.bottomRow; o++)
              for (n = t.leftCol; n <= t.rightCol; n++) o >= e.topRow && o <= e.bottomRow && n >= e.leftCol && n <= e.rightCol || (i = this.getCellData(o, n, !1), l = this._cloneObject(this.selectedSheet.getCellStyle(o, n)), (r = this.getMergedRange(this.cells, o, n)) && r.topRow === o && r.leftCol === n || (r = null), s.push({
                row: o,
                col: n,
                value: i,
                style: l,
                mergedCell: r ? r.clone() : null
              }));
            return s
          }, s.prototype._resetCellsForFillRange = function(e) {
            var t, o, n, i, l, s = !e || 0 != (e & d.CopyFormat),
              r = !!e && 0 != (e & d.CopyContent),
              a = !e || 0 != (e & d.FillSeries);
            if (this._orgCellSettings && this._orgCellSettings.length > 0)
              for (t = 0; t < this._orgCellSettings.length; t++) n = (o = this._orgCellSettings[t]).row, i = o.col, s || (l = n * this.columns.length + i, this.selectedSheet._styledCells[l] = o.style), r || a || this.setCellData(n, i, o.value)
          }, s.prototype._canDoFillOperation = function() {
            var e, t, o, n, i, l, s, r, a, h, c, d, u, _, g, f = this._fillingSource,
              p = this._fillingRange,
              w = !1;
            for (e = f.topRow; e <= f.bottomRow; e++)
              for (t = f.leftCol; t <= f.rightCol; t++)
                if (h = this.getMergedRange(this.cells, e, t), c) {
                  if (!h || !c.equals(h)) {
                    w = !0;
                    break
                  }
                } else c = h;
            for (o = p.topRow; o <= p.bottomRow; o++)
              for ((n = (i = o - f.topRow) % f.rowSpan) < 0 && (n += f.rowSpan), n += f.topRow, l = p.leftCol; l <= p.rightCol; l++)
                if (r = l - f.leftCol, !(i >= 0 && i < f.rowSpan && r >= 0 && r < f.columnSpan) && ((s = r % f.columnSpan) < 0 && (s += f.columnSpan), s += f.leftCol, h = this.getMergedRange(this.cells, n, s), a = this.getMergedRange(this.cells, o, l))) {
                  if (w || !h) return !1;
                  if (d = a ? o - a.topRow : NaN, u = a ? l - a.leftCol : NaN, _ = h ? n - h.topRow : NaN, g = h ? s - h.leftCol : NaN, d !== _ || u !== g) return !1
                }
            return !0
          }, s.prototype._updateItemIndexForInsertingRow = function(e, t, o) {
            var n, i;
            for (n = 0; n < e.length; n++)(i = e[n]) && null != i._itemIdx && i._itemIdx >= t && (i._itemIdx += o)
          }, s.prototype._updateItemIndexForRemovingRow = function(e, t) {
            var o, n;
            for (o = 0; o < e.length; o++)(n = e[o]) && null != n._itemIdx && n._itemIdx >= t && (n._itemIdx -= 1)
          }, s.prototype._copyRowsToSelectedSheet = function() {
            var e, t = this;
            if (t.selectedSheet) {
              for (t.selectedSheet.grid.rows.clear(), e = 0; e < t.rows.length; e++) t.selectedSheet.grid.rows.push(t.rows[e]);
              setTimeout(function() {
                t._setFlexSheetToDirty(), t.invalidate()
              }, 10)
            }
          }, s.prototype._copyColumnsToSelectedSheet = function() {
            var e, t = this;
            if (t.selectedSheet) {
              for (t.selectedSheet.grid.columns.clear(), e = 0; e < t.columns.length; e++) t.selectedSheet.grid.columns.push(t.columns[e]);
              setTimeout(function() {
                t._setFlexSheetToDirty(), t.invalidate()
              }, 10)
            }
          }, s.prototype._getUniqueColumnName = function() {
            for (var e = 1; null != this.columns.getColumn("col" + e);) e++;
            return "col" + e
          }, s.prototype._parseFromWorkbookTable = function(n, i) {
            var l, s, r, a, h, c, d, u, _, g, f, p = i === this.selectedSheet ? this : i.grid;
            if (s = n.range.split(":"), r = s[0], a = s[1], h = e.xlsx.Workbook.tableAddress(r), l = new t.CellRange(h.row, h.col), c = e.xlsx.Workbook.tableAddress(a), l.row2 = c.row, l.col2 = c.col, l.bottomRow >= i.rowCount)
              for (f = l.bottomRow - i.rowCount + 1, w = 0; w < f; w++) p.rows.push(new t.Row);
            if (l.rightCol >= i.columnCount)
              for (f = l.rightCol - i.columnCount + 1, w = 0; w < f; w++) p.columns.push(new t.Column);
            if (n.columns && n.columns.length > 0) {
              d = [];
              for (var w = 0; w < n.columns.length; w++) u = n.columns[w], d[w] = new o.TableColumn(u.name, u.totalRowLabel, u.totalRowFunction, u.showFilterButton)
            }
            return null != n.style && (_ = this._isBuiltInStyleName(n.style.name) ? this.getBuiltInTableStyle(n.style.name) : this._parseFromWorkbookTableStyle(n.style)), g = new o.Table(n.name, l, _, d, {
              showHeaderRow: n.showHeaderRow,
              showTotalRow: n.showTotalRow,
              showBandedColumns: n.showBandedColumns,
              showBandedRows: n.showBandedRows,
              alterFirstColumn: n.alterFirstColumn,
              alterLastColumn: n.alterLastColumn
            }), i.tables.push(g), g
          }, s.prototype._parseFromWorkbookTableStyle = function(e) {
            var t = new o.TableStyle(e.name);
            return null != e.firstBandedColumnStyle && (t.firstBandedColumnStyle = this._parseFromWorkbookTableStyleElement(e.firstBandedColumnStyle)), null != e.firstBandedRowStyle && (t.firstBandedRowStyle = this._parseFromWorkbookTableStyleElement(e.firstBandedRowStyle)), null != e.firstColumnStyle && (t.firstColumnStyle = this._parseFromWorkbookTableStyleElement(e.firstColumnStyle)), null != e.firstHeaderCellStyle && (t.firstHeaderCellStyle = this._parseFromWorkbookTableStyleElement(e.firstHeaderCellStyle)), null != e.firstTotalCellStyle && (t.firstTotalCellStyle = this._parseFromWorkbookTableStyleElement(e.firstTotalCellStyle)), null != e.headerRowStyle && (t.headerRowStyle = this._parseFromWorkbookTableStyleElement(e.headerRowStyle)), null != e.lastColumnStyle && (t.lastColumnStyle = this._parseFromWorkbookTableStyleElement(e.lastColumnStyle)), null != e.lastHeaderCellStyle && (t.lastHeaderCellStyle = this._parseFromWorkbookTableStyleElement(e.lastHeaderCellStyle)), null != e.lastTotalCellStyle && (t.lastTotalCellStyle = this._parseFromWorkbookTableStyleElement(e.lastTotalCellStyle)), null != e.secondBandedColumnStyle && (t.secondBandedColumnStyle = this._parseFromWorkbookTableStyleElement(e.secondBandedColumnStyle)), null != e.secondBandedRowStyle && (t.secondBandedRowStyle = this._parseFromWorkbookTableStyleElement(e.secondBandedRowStyle)), null != e.totalRowStyle && (t.totalRowStyle = this._parseFromWorkbookTableStyleElement(e.totalRowStyle)), null != e.wholeTableStyle && (t.wholeTableStyle = this._parseFromWorkbookTableStyleElement(e.wholeTableStyle)), t
          }, s.prototype._parseFromWorkbookTableStyleElement = function(e) {
            var o;
            return o = {
              fontWeight: e.font && e.font.bold ? "bold" : "none",
              fontStyle: e.font && e.font.italic ? "italic" : "none",
              textDecoration: e.font && e.font.underline ? "underline" : "none",
              fontFamily: e.font && e.font.family ? e.font.family : "",
              fontSize: e.font && e.font.size ? e.font.size + "px" : "",
              color: e.font && e.font.color ? e.font.color : "",
              backgroundColor: e.fill && e.fill.color ? e.fill.color : ""
            }, e.borders && (e.borders.left && (t.xlsx.FlexGridXlsxConverter._parseBorderStyle(e.borders.left.style, "Left", o), o.borderLeftColor = e.borders.left.color), e.borders.right && (t.xlsx.FlexGridXlsxConverter._parseBorderStyle(e.borders.right.style, "Right", o), o.borderRightColor = e.borders.right.color), e.borders.top && (t.xlsx.FlexGridXlsxConverter._parseBorderStyle(e.borders.top.style, "Top", o), o.borderTopColor = e.borders.top.color), e.borders.bottom && (t.xlsx.FlexGridXlsxConverter._parseBorderStyle(e.borders.bottom.style, "Bottom", o), o.borderBottomColor = e.borders.bottom.color), e.borders.vertical && (t.xlsx.FlexGridXlsxConverter._parseBorderStyle(e.borders.vertical.style, "Vertical", o), o.borderVerticalColor = e.borders.vertical.color), e.borders.horizontal && (t.xlsx.FlexGridXlsxConverter._parseBorderStyle(e.borders.horizontal.style, "Horizontal", o), o.borderHorizontalColor = e.borders.horizontal.color)), null != e.size && (o.size = e.size), o
          }, s.prototype._parseToWorkbookTable = function(t) {
            var o, n, i = new e.xlsx.WorkbookTable,
              l = t.getRange(),
              s = t.getColumns();
            i.name = t.name, o = e.xlsx.Workbook.xlsxAddress(l.topRow, l.leftCol), n = e.xlsx.Workbook.xlsxAddress(l.bottomRow, l.rightCol), i.range = o + ":" + n, null != t.style && (t.style.isBuiltIn ? (i.style = new e.xlsx.WorkbookTableStyle, i.style.name = t.style.name) : i.style = this._parseToWorkbookTableStyle(t.style)), i.showBandedColumns = t.showBandedColumns, i.showBandedRows = t.showBandedRows, i.showHeaderRow = t.showHeaderRow, i.showTotalRow = t.showTotalRow, i.alterFirstColumn = t.alterFirstColumn, i.alterLastColumn = t.alterLastColumn;
            for (var r = 0; r < s.length; r++) {
              var a = s[r],
                h = new e.xlsx.WorkbookTableColumn;
              h.name = a.name, h.totalRowLabel = a.totalRowLabel, h.totalRowFunction = a.totalRowFunction, h.showFilterButton = a.showFilterButton, i.columns.push(h)
            }
            return i
          }, s.prototype._parseToWorkbookTableStyle = function(t) {
            var o = new e.xlsx.WorkbookTableStyle;
            return o.name = t.name, null != t.firstBandedColumnStyle && (o.firstBandedColumnStyle = this._parseToWorkbookTableStyleElement(t.firstBandedColumnStyle, !0)), null != t.firstBandedRowStyle && (o.firstBandedRowStyle = this._parseToWorkbookTableStyleElement(t.firstBandedRowStyle, !0)), null != t.firstColumnStyle && (o.firstColumnStyle = this._parseToWorkbookTableStyleElement(t.firstColumnStyle)), null != t.firstHeaderCellStyle && (o.firstHeaderCellStyle = this._parseToWorkbookTableStyleElement(t.firstHeaderCellStyle)), null != t.firstTotalCellStyle && (o.firstTotalCellStyle = this._parseToWorkbookTableStyleElement(t.firstTotalCellStyle)), null != t.headerRowStyle && (o.headerRowStyle = this._parseToWorkbookTableStyleElement(t.headerRowStyle)), null != t.lastColumnStyle && (o.lastColumnStyle = this._parseToWorkbookTableStyleElement(t.lastColumnStyle)), null != t.lastHeaderCellStyle && (o.lastHeaderCellStyle = this._parseToWorkbookTableStyleElement(t.lastHeaderCellStyle)), null != t.lastTotalCellStyle && (o.lastTotalCellStyle = this._parseToWorkbookTableStyleElement(t.lastTotalCellStyle)), null != t.secondBandedColumnStyle && (o.secondBandedColumnStyle = this._parseToWorkbookTableStyleElement(t.secondBandedColumnStyle, !0)), null != t.secondBandedRowStyle && (o.secondBandedRowStyle = this._parseToWorkbookTableStyleElement(t.secondBandedRowStyle, !0)), null != t.totalRowStyle && (o.totalRowStyle = this._parseToWorkbookTableStyleElement(t.totalRowStyle)), null != t.wholeTableStyle && (o.wholeTableStyle = this._parseToWorkbookTableStyleElement(t.wholeTableStyle)), o
          }, s.prototype._parseToWorkbookTableStyleElement = function(o, n) {
            void 0 === n && (n = !1);
            var i, l = t.xlsx.FlexGridXlsxConverter._parseCellStyle(o, !0);
            return n ? (i = new e.xlsx.WorkbookTableBandedStyle).size = o.size : i = new e.xlsx.WorkbookTableCommonStyle, i._deserialize(l), i
          }, s.prototype._isBuiltInStyleName = function(e) {
            var t;
            if (0 === e.search(/TableStyleLight/i)) {
              if (t = +e.substring(15), !isNaN(t) && t >= 1 && t <= 21) return !0
            } else if (0 === e.search(/TableStyleMedium/i)) {
              if (t = +e.substring(16), !isNaN(t) && t >= 1 && t <= 28) return !0
            } else if (0 === e.search(/TableStyleDark/i) && (t = +e.substring(14), !isNaN(t) && t >= 1 && t <= 11)) return !0;
            return !1
          }, s.prototype._getTable = function(e) {
            for (var t, o, n, i = 0; i < this.sheets.length; i++)
              for (o = this.sheets[i].tables, n = 0; n < o.length; n++)
                if ((t = o[n]).name.toLowerCase() === e.toLowerCase()) return t;
            return null
          }, s.prototype._checkTableHeaderRow = function(e, t) {
            var n, i, l, s, r;
            for (i = 0; i < e.length; i++)
              if ((n = e[i]).showHeaderRow)
                for (l = (r = n.getRange(o.TableSection.Header)).row, s = r.leftCol; s <= r.rightCol; s++) t.sheets[0].rows[l].cells[s].isDate = !1
          }, s.prototype._isTableColumnRef = function(e, t) {
            return new RegExp("\\[(\\s*@)?" + t + "\\]", "i").test(e)
          }, s.prototype._getThemeColor = function(t, o) {
            var n, i, l = this._colorThemes[t];
            return null != o ? (n = new e.Color("#" + l), i = n.getHsl(), i[2] = o < 0 ? i[2] * (1 + o) : i[2] * (1 - o) + (1 - 1 * (1 - o)), (n = e.Color.fromHsl(i[0], i[1], i[2])).toString()) : "#" + l
          }, s.prototype._createBuiltInTableStyle = function(e) {
            var t;
            if (0 === e.search(/TableStyleLight/i)) {
              if (t = +e.substring(15), !isNaN(t) && t >= 1 && t <= 21) return t <= 7 ? this._generateTableLightStyle1(t - 1, e, !0) : t <= 14 ? this._generateTableLightStyle2(t - 8, e) : this._generateTableLightStyle1(t - 15, e, !1)
            } else if (0 === e.search(/TableStyleMedium/i)) {
              if (t = +e.substring(16), !isNaN(t) && t >= 1 && t <= 28) return t <= 7 ? this._generateTableMediumStyle1(t - 1, e) : t <= 14 ? this._generateTableMediumStyle2(t - 8, e) : t <= 21 ? this._generateTableMediumStyle3(t - 15, e) : this._generateTableMediumStyle4(t - 22, e)
            } else if (0 === e.search(/TableStyleDark/i) && (t = +e.substring(14), !isNaN(t) && t >= 1 && t <= 11)) return t <= 7 ? this._generateTableDarkStyle1(t - 1, e) : this._generateTableDarkStyle2(t - 8, e);
            return null
          }, s.prototype._generateTableLightStyle1 = function(e, t, n) {
            var i = new o.TableStyle(t, !0),
              l = 0 === e ? 1 : e + 3,
              s = n ? "1px" : "2px",
              r = n ? "solid" : "double",
              a = n ? "1px" : "3px";
            return i.wholeTableStyle = {
              borderTopColor: this._getThemeColor(l),
              borderTopStyle: "solid",
              borderTopWidth: "1px",
              borderBottomColor: this._getThemeColor(l),
              borderBottomStyle: "solid",
              borderBottomWidth: "1px"
            }, i.firstColumnStyle = {
              fontWeight: "bold"
            }, i.lastColumnStyle = {
              fontWeight: "bold"
            }, i.headerRowStyle = {
              borderBottomColor: this._getThemeColor(l),
              borderBottomStyle: "solid",
              borderBottomWidth: s,
              fontWeight: "bold"
            }, i.totalRowStyle = {
              borderTopColor: this._getThemeColor(l),
              borderTopStyle: r,
              borderTopWidth: a,
              fontWeight: "bold"
            }, 0 === e ? (i.wholeTableStyle.color = this._getThemeColor(l), i.firstColumnStyle.color = this._getThemeColor(l), i.lastColumnStyle.color = this._getThemeColor(l), i.headerRowStyle.color = this._getThemeColor(l), i.totalRowStyle.color = this._getThemeColor(l), i.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(0, -.15)
            }, i.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(0, -.15)
            }) : (n ? (i.wholeTableStyle.color = this._getThemeColor(l, -.25), i.firstColumnStyle.color = this._getThemeColor(l, -.25), i.lastColumnStyle.color = this._getThemeColor(l, -.25), i.headerRowStyle.color = this._getThemeColor(l, -.25), i.totalRowStyle.color = this._getThemeColor(l, -.25)) : (i.wholeTableStyle.color = this._getThemeColor(1), i.firstColumnStyle.color = this._getThemeColor(1), i.lastColumnStyle.color = this._getThemeColor(1), i.headerRowStyle.color = this._getThemeColor(1), i.totalRowStyle.color = this._getThemeColor(1)), i.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(l, .8)
            }, i.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(l, .8)
            }), n || (i.wholeTableStyle.borderLeftColor = this._getThemeColor(l), i.wholeTableStyle.borderLeftStyle = "solid", i.wholeTableStyle.borderLeftWidth = "1px", i.wholeTableStyle.borderRightColor = this._getThemeColor(l), i.wholeTableStyle.borderRightStyle = "solid", i.wholeTableStyle.borderRightWidth = "1px", i.wholeTableStyle.borderHorizontalColor = this._getThemeColor(l), i.wholeTableStyle.borderHorizontalStyle = "solid", i.wholeTableStyle.borderHorizontalWidth = "1px", i.wholeTableStyle.borderVerticalColor = this._getThemeColor(l), i.wholeTableStyle.borderVerticalStyle = "solid", i.wholeTableStyle.borderVerticalWidth = "1px"), i
          }, s.prototype._generateTableLightStyle2 = function(e, t) {
            var n = new o.TableStyle(t, !0),
              i = 0 === e ? 1 : e + 3;
            return n.wholeTableStyle = {
              borderTopColor: this._getThemeColor(i),
              borderTopStyle: "solid",
              borderTopWidth: "1px",
              borderBottomColor: this._getThemeColor(i),
              borderBottomStyle: "solid",
              borderBottomWidth: "1px",
              borderLeftColor: this._getThemeColor(i),
              borderLeftStyle: "solid",
              borderLeftWidth: "1px",
              borderRightColor: this._getThemeColor(i),
              borderRightStyle: "solid",
              borderRightWidth: "1px",
              color: this._getThemeColor(1)
            }, n.firstBandedRowStyle = {
              borderTopColor: this._getThemeColor(i),
              borderTopStyle: "solid",
              borderTopWidth: "1px"
            }, n.secondBandedRowStyle = {
              borderTopColor: this._getThemeColor(i),
              borderTopStyle: "solid",
              borderTopWidth: "1px"
            }, n.firstBandedColumnStyle = {
              borderLeftColor: this._getThemeColor(i),
              borderLeftStyle: "solid",
              borderLeftWidth: "1px"
            }, n.secondBandedColumnStyle = {
              borderLeftColor: this._getThemeColor(i),
              borderLeftStyle: "solid",
              borderLeftWidth: "1px"
            }, n.firstColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.lastColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.headerRowStyle = {
              backgroundColor: this._getThemeColor(i),
              fontWeight: "bold",
              color: this._getThemeColor(0)
            }, n.totalRowStyle = {
              borderTopColor: this._getThemeColor(i),
              borderTopStyle: "double",
              borderTopWidth: "3px",
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n
          }, s.prototype._generateTableMediumStyle1 = function(e, t) {
            var n = new o.TableStyle(t, !0),
              i = 0 === e ? 1 : e + 3;
            return n.wholeTableStyle = {
              borderTopStyle: "solid",
              borderTopWidth: "1px",
              borderBottomStyle: "solid",
              borderBottomWidth: "1px",
              borderLeftStyle: "solid",
              borderLeftWidth: "1px",
              borderRightStyle: "solid",
              borderRightWidth: "1px",
              borderHorizontalStyle: "solid",
              borderHorizontalWidth: "1px",
              color: this._getThemeColor(1)
            }, 0 === e ? (n.wholeTableStyle.borderTopColor = this._getThemeColor(i), n.wholeTableStyle.borderBottomColor = this._getThemeColor(i), n.wholeTableStyle.borderLeftColor = this._getThemeColor(i), n.wholeTableStyle.borderRightColor = this._getThemeColor(i), n.wholeTableStyle.borderHorizontalColor = this._getThemeColor(i), n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(0, -.15)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(0, -.15)
            }) : (n.wholeTableStyle.borderTopColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderBottomColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderLeftColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderRightColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderHorizontalColor = this._getThemeColor(i, .4), n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(i, .8)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(i, .8)
            }), n.firstColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.lastColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.headerRowStyle = {
              backgroundColor: this._getThemeColor(i),
              fontWeight: "bold",
              color: this._getThemeColor(0)
            }, n.totalRowStyle = {
              borderTopColor: this._getThemeColor(i),
              borderTopStyle: "double",
              borderTopWidth: "3px",
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n
          }, s.prototype._generateTableMediumStyle2 = function(e, t) {
            var n = new o.TableStyle(t, !0),
              i = 0 === e ? 1 : e + 3;
            return n.wholeTableStyle = {
              borderVerticalStyle: "solid",
              borderVerticalWidth: "1px",
              borderVerticalColor: this._getThemeColor(0),
              borderHorizontalStyle: "solid",
              borderHorizontalWidth: "1px",
              borderHorizontalColor: this._getThemeColor(0),
              color: this._getThemeColor(1)
            }, 0 === e ? (n.wholeTableStyle.backgroundColor = this._getThemeColor(0, -.15), n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(0, -.35)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(0, -.35)
            }) : (n.wholeTableStyle.backgroundColor = this._getThemeColor(i, .8), n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(i, .6)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(i, .6)
            }), n.firstColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(0),
              backgroundColor: this._getThemeColor(i)
            }, n.lastColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(0),
              backgroundColor: this._getThemeColor(i)
            }, n.headerRowStyle = {
              borderBottomColor: this._getThemeColor(0),
              borderBottomStyle: "solid",
              borderBottomWidth: "3px",
              backgroundColor: this._getThemeColor(i),
              fontWeight: "bold",
              color: this._getThemeColor(0)
            }, n.totalRowStyle = {
              borderTopColor: this._getThemeColor(0),
              borderTopStyle: "solid",
              borderTopWidth: "3px",
              backgroundColor: this._getThemeColor(i),
              fontWeight: "bold",
              color: this._getThemeColor(0)
            }, n
          }, s.prototype._generateTableMediumStyle3 = function(e, t) {
            var n = new o.TableStyle(t, !0),
              i = 0 === e ? 1 : e + 3;
            return n.wholeTableStyle = {
              borderTopStyle: "solid",
              borderTopWidth: "2px",
              borderTopColor: this._getThemeColor(1),
              borderBottomStyle: "solid",
              borderBottomWidth: "2px",
              borderBottomColor: this._getThemeColor(1),
              color: this._getThemeColor(1)
            }, 0 === e && (n.wholeTableStyle.borderLeftColor = this._getThemeColor(1), n.wholeTableStyle.borderLeftStyle = "solid", n.wholeTableStyle.borderLeftWidth = "1px", n.wholeTableStyle.borderRightColor = this._getThemeColor(1), n.wholeTableStyle.borderRightStyle = "solid", n.wholeTableStyle.borderRightWidth = "1px", n.wholeTableStyle.borderVerticalColor = this._getThemeColor(1), n.wholeTableStyle.borderVerticalStyle = "solid", n.wholeTableStyle.borderVerticalWidth = "1px", n.wholeTableStyle.borderHorizontalColor = this._getThemeColor(1), n.wholeTableStyle.borderHorizontalStyle = "solid", n.wholeTableStyle.borderHorizontalWidth = "1px"), n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(0, -.35)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(0, -.35)
            }, n.firstColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(0),
              backgroundColor: this._getThemeColor(i)
            }, n.lastColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(0),
              backgroundColor: this._getThemeColor(i)
            }, n.headerRowStyle = {
              borderBottomColor: this._getThemeColor(1),
              borderBottomStyle: "solid",
              borderBottomWidth: "2px",
              backgroundColor: this._getThemeColor(i),
              fontWeight: "bold",
              color: this._getThemeColor(0)
            }, n.totalRowStyle = {
              borderTopColor: this._getThemeColor(1),
              borderTopStyle: "double",
              borderTopWidth: "3px"
            }, n
          }, s.prototype._generateTableMediumStyle4 = function(e, t) {
            var n = new o.TableStyle(t, !0),
              i = 0 === e ? 1 : e + 3;
            return n.wholeTableStyle = {
              borderTopStyle: "solid",
              borderTopWidth: "1px",
              borderBottomStyle: "solid",
              borderBottomWidth: "1px",
              borderLeftStyle: "solid",
              borderLeftWidth: "1px",
              borderRightStyle: "solid",
              borderRightWidth: "1px",
              borderVerticalStyle: "solid",
              borderVerticalWidth: "1px",
              borderHorizontalStyle: "solid",
              borderHorizontalWidth: "1px",
              color: this._getThemeColor(1)
            }, 0 === e ? (n.wholeTableStyle.borderTopColor = this._getThemeColor(i), n.wholeTableStyle.borderBottomColor = this._getThemeColor(i), n.wholeTableStyle.borderLeftColor = this._getThemeColor(i), n.wholeTableStyle.borderRightColor = this._getThemeColor(i), n.wholeTableStyle.borderVerticalColor = this._getThemeColor(i), n.wholeTableStyle.borderHorizontalColor = this._getThemeColor(i), n.wholeTableStyle.backgroundColor = this._getThemeColor(0, -.15), n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(0, -.35)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(0, -.35)
            }) : (n.wholeTableStyle.borderTopColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderBottomColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderLeftColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderRightColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderVerticalColor = this._getThemeColor(i, .4), n.wholeTableStyle.borderHorizontalColor = this._getThemeColor(i, .4), n.wholeTableStyle.backgroundColor = this._getThemeColor(i, .8), n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(i, .6)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(i, .6)
            }), n.firstColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.lastColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.headerRowStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.totalRowStyle = {
              borderTopColor: this._getThemeColor(i),
              borderTopStyle: "solid",
              borderTopWidth: "2px",
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n
          }, s.prototype._generateTableDarkStyle1 = function(e, t) {
            var n = new o.TableStyle(t, !0),
              i = 0 === e ? 1 : e + 3,
              l = 0 === e ? .25 : -.25;
            return n.wholeTableStyle = {
              color: this._getThemeColor(0)
            }, n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(i, l)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(i, l)
            }, n.firstColumnStyle = {
              borderRightColor: this._getThemeColor(0),
              borderRightStyle: "solid",
              borderRightWidth: "2px",
              fontWeight: "bold",
              color: this._getThemeColor(0),
              backgroundColor: this._getThemeColor(i, l)
            }, n.lastColumnStyle = {
              borderLeftColor: this._getThemeColor(0),
              borderLeftStyle: "solid",
              borderLeftWidth: "2px",
              fontWeight: "bold",
              color: this._getThemeColor(0),
              backgroundColor: this._getThemeColor(i, l)
            }, n.headerRowStyle = {
              borderBottomColor: this._getThemeColor(0),
              borderBottomStyle: "solid",
              borderBottomWidth: "2px",
              fontWeight: "bold",
              color: this._getThemeColor(0),
              backgroundColor: this._getThemeColor(1)
            }, n.totalRowStyle = {
              borderTopColor: this._getThemeColor(0),
              borderTopStyle: "solid",
              borderTopWidth: "2px",
              fontWeight: "bold",
              color: this._getThemeColor(0)
            }, 0 === e ? (n.wholeTableStyle.backgroundColor = this._getThemeColor(i, .5), n.totalRowStyle.backgroundColor = this._getThemeColor(i, .15)) : (n.wholeTableStyle.backgroundColor = this._getThemeColor(i), n.totalRowStyle.backgroundColor = this._getThemeColor(i, -.5)), n
          }, s.prototype._generateTableDarkStyle2 = function(e, t) {
            var n = new o.TableStyle(t, !0),
              i = 0 === e ? 0 : 2 * e + 2,
              l = 0 === e ? 1 : 2 * e + 3,
              s = 0 === e ? -.15 : .8;
            return n.wholeTableStyle = {
              backgroundColor: this._getThemeColor(i, s)
            }, n.firstBandedRowStyle = {
              backgroundColor: this._getThemeColor(i, s - .2)
            }, n.firstBandedColumnStyle = {
              backgroundColor: this._getThemeColor(i, s - .2)
            }, n.firstColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.lastColumnStyle = {
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n.headerRowStyle = {
              color: this._getThemeColor(0),
              backgroundColor: this._getThemeColor(l)
            }, n.totalRowStyle = {
              borderTopColor: this._getThemeColor(1),
              borderTopStyle: "double",
              borderTopWidth: "3px",
              fontWeight: "bold",
              color: this._getThemeColor(1)
            }, n
          }, s._toOADate = function(e) {
            var t, o = new Date(1900, 0, 0),
              n = Date.UTC(1900, 0, 0),
              i = o.getTime() - n - 6e4 * o.getTimezoneOffset(),
              l = 6e4 * (e.getTimezoneOffset() - o.getTimezoneOffset());
            return t = (e.getTime() - o.getTime() - (0 !== l ? l - i : 0)) / 864e5, t += t > 59 ? 1 : 0
          }, s._fromOADate = function(e) {
            var t, o, n, i = new Date(1900, 0, 0),
              l = Date.UTC(1900, 0, 0),
              s = i.getTime() - l - 6e4 * i.getTimezoneOffset();
            return o = e > 59 ? 1 : 0, n = new Date(i.getTime() + 864e5 * (e - o)), 0 !== (t = 6e4 * (n.getTimezoneOffset() - i.getTimezoneOffset())) ? new Date(i.getTime() + t - s + 864e5 * (e - o)) : n
          }, s.controlTemplate = '<div style="width:100%;height:100%"><div wj-part="container" style="width:100%">' + t.FlexGrid.controlTemplate + '</div><div wj-part="tab-holder" class="wj-tabholder" style="width:100%; min-width:100px"></div><div wj-part="context-menu" style="display:none;z-index:100"></div></div>', s
        }(t.FlexGrid);
      o.FlexSheet = i;
      var l = function(e) {
        function t(t, o) {
          var n = e.call(this) || this;
          return n._isDraggingRows = t, n._isShiftKey = o, n
        }
        return __extends(t, e), Object.defineProperty(t.prototype, "isDraggingRows", {
          get: function() {
            return this._isDraggingRows
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "isShiftKey", {
          get: function() {
            return this._isShiftKey
          },
          enumerable: !0,
          configurable: !0
        }), t
      }(e.EventArgs);
      o.DraggingRowColumnEventArgs = l;
      var s = function(e) {
        function t(t, o) {
          var n = e.call(this) || this;
          return n._funcName = t, n._params = o, n
        }
        return __extends(t, e), Object.defineProperty(t.prototype, "funcName", {
          get: function() {
            return this._funcName
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "params", {
          get: function() {
            return this._params
          },
          enumerable: !0,
          configurable: !0
        }), t
      }(e.EventArgs);
      o.UnknownFunctionEventArgs = s;
      var r = function(t) {
        function o(e, o, n) {
          var i = t.call(this) || this;
          return i._index = e, i._count = o, i._added = n, i
        }
        return __extends(o, t), Object.defineProperty(o.prototype, "index", {
          get: function() {
            return this._index
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(o.prototype, "count", {
          get: function() {
            return this._count
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(o.prototype, "added", {
          get: function() {
            return this._added
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(o.prototype, "isAdd", {
          get: function() {
            return e._deprecated("RowColumnChangedEventArgs.isAdd", "RowColumnChangedEventArgs.added"), this._added
          },
          enumerable: !0,
          configurable: !0
        }), o
      }(e.EventArgs);
      o.RowColumnChangedEventArgs = r;
      var a = function(n) {
        function i(e, t, o, i, l) {
          return n.call(this, e, t, o, i, l) || this
        }
        return __extends(i, n), i.prototype.getSelectedState = function(e, o, i) {
          var l, s, r, a, h, c;
          if (this.grid) {
            if (c = this.grid.getMergedRange(this, e, o), l = this.grid.selectedSheet ? this.grid.selectedSheet.selectionRanges : null, h = n.prototype.getSelectedState.call(this, e, o, i), s = l ? l.length : 0, h === t.SelectedState.None && s > 0)
              for (r = 0; r < l.length; r++)
                if ((a = l[r]) && a instanceof t.CellRange) {
                  if (this.cellType === t.CellType.Cell) {
                    if (c) {
                      if (c.contains(a.row, a.col)) return r !== s - 1 || this.grid._isClicking ? t.SelectedState.Selected : this.grid.showMarquee ? t.SelectedState.None : t.SelectedState.Cursor;
                      if (c.intersects(a)) return t.SelectedState.Selected
                    }
                    if (a.row === e && a.col === o) return r !== s - 1 || this.grid._isClicking ? t.SelectedState.Selected : this.grid.showMarquee ? t.SelectedState.None : t.SelectedState.Cursor;
                    if (a.contains(e, o)) return t.SelectedState.Selected
                  }
                  if (this.grid.showSelectedHeaders & t.HeadersVisibility.Row && this.cellType === t.CellType.RowHeader && a.containsRow(e)) return t.SelectedState.Selected;
                  if (this.grid.showSelectedHeaders & t.HeadersVisibility.Column && this.cellType === t.CellType.ColumnHeader && a.containsColumn(o)) return t.SelectedState.Selected
                }
            return h
          }
        }, i.prototype.getCellData = function(t, o, i) {
          var l, s, r, a, h, c;
          if (e.isString(o) && (o = this.columns.indexOf(o)) < 0) throw "Invalid column name or binding.";
          return t >= this.rows.length || e.asNumber(o, !1, !0) >= this.columns.length ? null : (l = n.prototype.getCellData.call(this, t, o, i), s = this.columns[e.asNumber(o, !1, !0)], r = this.grid ? this.grid._getBindingColumn(this, t, s) : s, i && (h = n.prototype.getCellData.call(this, t, o, !1), a = this.grid ? this.grid._getCellStyle(t, o) : null, e.isNumber(h) && 0 !== h && (r && r.dataMap || ("" === (c = a ? a.format : "") && (c = r ? r.format : ""), l = e.Globalize.format(h, c)))), l)
        }, i.prototype.setCellData = function(t, i, l, s, r) {
          if (void 0 === s && (s = !0), void 0 === r && (r = !0), t >= this.rows.length || i >= this.columns.length) return !1;
          var a, c, d, u, _, g = this.getCellData(t, i, !1),
            f = this.grid._isPasting,
            p = this.columns[i],
            w = this.grid ? this.grid._getBindingColumn(this, t, p) : p,
            m = this.rows[t],
            C = this.grid ? this.grid._getCellStyle(t, i) : null,
            b = e.getType(g),
            y = e.culture.Globalize.numberFormat.currency.symbol;
          if (this.grid && (d = this.grid.selectedSheet.findTable(t, i)) && d.showHeaderRow && (u = d.getRange(o.TableSection.Header), t === u.row && i >= u.leftCol && i <= u.rightCol)) return null == l || e.isString(l) && e.isNullOrWhiteSpace(l) ? void 0 : n.prototype.setCellData.call(this, t, i, l.toString(), !1, r);
          if (s && l && e.isString(l) && "'" !== l[0] && "=" !== l[0]) {
            if (this.getCellData(t, i, !0) === l) return;
            var S = C ? C.format : "";
            /[hsmt\:]/.test(S) || (S = ""), (a = e.Globalize.parseDate(l, S)) ? l = a : (_ = new RegExp("[^\\d,\\.\\" + y + "]", "g"), w.dataType === e.DataType.String || e.isNullOrWhiteSpace(l) || null != g && b !== e.DataType.Number || _.test(l) && "%" !== l[l.length - 1] ? w.dataType === e.DataType.Boolean && (l = e.changeType(l, e.DataType.Boolean, null)) : (c = e.changeType(l, e.DataType.Number, ""), e.isNumber(c) ? l = c : s = !1))
          }
          if ((l && e.isString(l) && ("=" === l[0] || "'" === l[0]) || !w.dataType) && (s = !1), !(m instanceof h)) return n.prototype.setCellData.call(this, t, i, l, s && !f, r);
          m._ubv[w._hash] = l
        }, i.prototype._renderCell = function(t, o, i, l, s, r) {
          var a, h, c, d, u = o * this.grid.columns.length + i,
            _ = this.grid.getMergedRange(this, o, i);
          return d = n.prototype._renderCell.call(this, t, o, i, l, s, r), this.cellType !== e.grid.CellType.Cell ? d : _ && u > _.topRow * this.grid.columns.length + _.leftCol ? d : ((a = t.childNodes[r]) && (null != this.grid.editRange && this.grid.editRange.contains(o, i) || this.grid.selectedSheet && (c = this.grid.selectedSheet.findTable(o, i)) && c._updateCell(o, i, a), e.hasClass(a, "wj-state-selected") || e.hasClass(a, "wj-state-multi-selected") ? (a.style.backgroundColor = "", a.style.color = "") : this.grid.selectedSheet && (h = this.grid.selectedSheet._styledCells[u]) && ("" !== h.backgroundColor && (a.style.backgroundColor = h.backgroundColor), "" !== h.color && (a.style.color = h.color))), d)
        }, i
      }(t.GridPanel);
      o.FlexSheetPanel = a;
      var h = function(e) {
        function t() {
          var t = e.call(this) || this;
          return t.isReadOnly = !0, t
        }
        return __extends(t, e), t
      }(t.Row);
      o.HeaderRow = h;
      var c = function() {
        function e(e, t, o, n) {
          if (this._owner = e, this._name = t, this._value = o, null != n) {
            if (!e._validateSheetName(n)) throw "The sheet name:(" + n + ") does not exist in FlexSheet.";
            this._sheetName = n
          }
        }
        return Object.defineProperty(e.prototype, "name", {
          get: function() {
            return this._name
          },
          set: function(e) {
            var t;
            if (this._name !== e) {
              if (this._owner._checkExistDefinedName(name, this._sheetName)) throw "The " + name + " already existed in definedNames.";
              t = this._name, this._name = e, this._owner._updateFormulasWithNameUpdating(t, this._name)
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "value", {
          get: function() {
            return this._value
          },
          set: function(e) {
            this._value !== e && (this._value = e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "sheetName", {
          get: function() {
            return this._sheetName
          },
          enumerable: !0,
          configurable: !0
        }), e
      }();
      o.DefinedName = c;
      var d;
      ! function(e) {
        e[e.CopyFormat = 1] = "CopyFormat", e[e.CopyContent = 2] = "CopyContent", e[e.FillSeries = 4] = "FillSeries"
      }(d || (d = {}))
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function() {
        function n(t, n, i, l, r) {
          this._visible = !0, this._unboundSortDesc = new e.collections.ObservableArray, this._currentStyledCells = {}, this._currentMergedRanges = [], this._isEmptyGrid = !1, this._rowSettings = [], this._scrollPosition = new e.Point, this._showDefaultHeader = !1, this.nameChanged = new e.Event, this.visibleChanged = new e.Event;
          var a = this;
          a._owner = t, a._name = i, a._sortList = [new o.ColumnSortDescription(-1, !0)], e.isNumber(l) && !isNaN(l) && l >= 0 ? a._rowCount = l : a._rowCount = 200, e.isNumber(r) && !isNaN(r) && r >= 0 ? a._columnCount = r : a._columnCount = 20, n ? (a._showDefaultHeader = !0, a._grid = n) : a._grid = this._createGrid(), a._grid.loadedRows.addHandler(function() {
            a._addHeaderRow(), a._setRowSettings()
          }), a._grid.itemsSourceChanged.addHandler(this._gridItemsSourceChanged, this), a._addHeaderRow(), a._unboundSortDesc.collectionChanged.addHandler(function() {
            var t, o, n = a._unboundSortDesc,
              i = {};
            for (t = 0; t < n.length; t++)
              if (!e.tryCast(n[t], s)) throw "sortDescriptions array must contain SortDescription objects.";
            if (a._owner) {
              if (a._owner.rows.beginUpdate(), a._owner.rows.sort(a._compareRows()), !a._owner._isUndoing) {
                for (t = 0; t < a._owner.rows.length; t++) t !== (o = a._owner.rows[t])._idx && a._owner._updateFormulaForReorderingRows(o._idx, t), a._owner._updateCellStyleForReorderingRows(o._idx, t, i);
                a._currentStyledCells = i
              }
              a._owner.rows.endUpdate(), a._owner.rows._dirty = !0, a._owner.rows._update(), a._owner.selectedSheet && (a._owner._copyTo(a._owner.selectedSheet), a._owner._copyFrom(a._owner.selectedSheet))
            }
          }), a.tables.collectionChanged.addHandler(function(t, n) {
            if (null != n.item && !(n.item instanceof o.Table)) throw "The tables only allows to handle Table instance.";
            if (n.action === e.collections.NotifyCollectionChangedAction.Add || n.action === e.collections.NotifyCollectionChangedAction.Change) {
              if (n.item) {
                var i = void 0,
                  l = void 0,
                  s = n.item,
                  r = s.getRange(),
                  h = a === a._owner.selectedSheet ? a._owner : a.grid;
                try {
                  if (r.topRow >= h.rows.length || r.leftCol >= h.columns.length) throw "";
                  if (r.leftCol + r.columnSpan > h.columns.length) throw "";
                  if (r.topRow + r.rowSpan > h.rows.length) throw "";
                  for (i = r.topRow; i <= r.bottomRow; i++)
                    for (l = r.leftCol; l <= r.rightCol; l++)
                      if (a.findTable(i, l) !== s) throw "";
                  s._attachSheet(a)
                } catch (e) {
                  a.tables.removeAt(n.index)
                }
              }
            } else n.action === e.collections.NotifyCollectionChangedAction.Remove && n.item && n.item._detachSheet()
          })
        }
        return Object.defineProperty(n.prototype, "grid", {
          get: function() {
            return null == this._grid.wj_sheetInfo && (this._grid.wj_sheetInfo = {}), this._grid
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "name", {
          get: function() {
            return this._name
          },
          set: function(t) {
            if (!e.isNullOrWhiteSpace(t) && (this._name && this._name.toLowerCase() !== t.toLowerCase() || !this._name)) {
              var o = new e.PropertyChangedEventArgs("sheetName", this._name, t);
              this._name = t, this.grid.wj_sheetInfo.name = t, this.onNameChanged(o)
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "visible", {
          get: function() {
            return this._visible
          },
          set: function(t) {
            this._visible !== t && (this._visible = t, this.grid.wj_sheetInfo.visible = t, this.onVisibleChanged(new e.EventArgs))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "rowCount", {
          get: function() {
            return null != this._grid ? this._grid.rows.length : 0
          },
          set: function(o) {
            var n;
            if (!this.itemsSource && e.isNumber(o) && !isNaN(o) && o >= 0 && this._rowCount !== o) {
              if (this._rowCount < o)
                for (n = 0; n < o - this._rowCount; n++) this.grid.rows.push(new t.Row);
              else this.grid.rows.splice(o, this._rowCount - o);
              this._rowCount = o, this._owner && this._owner.selectedSheet && this._name === this._owner.selectedSheet.name && this._owner._copyFrom(this, !0)
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "columnCount", {
          get: function() {
            return null != this._grid ? this._grid.columns.length : 0
          },
          set: function(o) {
            var n;
            if (!this.itemsSource && e.isNumber(o) && !isNaN(o) && o >= 0 && this._columnCount !== o) {
              if (this._columnCount < o)
                for (n = 0; n < o - this._columnCount; n++) this._grid.columns.push(new t.Column);
              else this._grid.columns.splice(o, this._columnCount - o);
              this._columnCount = o, this._owner && this._owner.selectedSheet && this._name === this._owner.selectedSheet.name && this._owner._copyFrom(this, !0)
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "selectionRanges", {
          get: function() {
            var o = this;
            return this._selectionRanges || (this._selectionRanges = new e.collections.ObservableArray, this._selectionRanges.collectionChanged.addHandler(function() {
              var e, n;
              o._owner && !o._owner._isClicking && ((e = o._selectionRanges.length) > 0 && (n = o._selectionRanges[e - 1]) && n instanceof t.CellRange && (o._owner.selection = n), e > 1 && (o._owner._enableMulSel = !0, o._owner.refresh(!1)), o._owner._enableMulSel = !1)
            }, this)), this._selectionRanges
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "itemsSource", {
          get: function() {
            return null != this._grid ? this._grid.itemsSource : null
          },
          set: function(e) {
            var t = this;
            null == t._grid && (t._createGrid(), t._grid.itemsSourceChanged.addHandler(t._gridItemsSourceChanged, t)), t._isEmptyGrid && t._clearGrid(), t._grid.itemsSource = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "filterSetting", {
          get: function() {
            return this === this._owner.selectedSheet && this._getFilterSetting(), this._filterSetting
          },
          set: function(e) {
            this._filterSetting = e, this === this._owner.selectedSheet && this._applyFilterSetting()
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "tables", {
          get: function() {
            return null == this._tables && (this._tables = new e.collections.ObservableArray), this._tables
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "_styledCells", {
          get: function() {
            return this._currentStyledCells || (this._currentStyledCells = {}), this._currentStyledCells
          },
          set: function(e) {
            this._currentStyledCells = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "_mergedRanges", {
          get: function() {
            return this._currentMergedRanges || (this._currentMergedRanges = []), this._currentMergedRanges
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.onNameChanged = function(e) {
          this.nameChanged.raise(this, e)
        }, n.prototype.onVisibleChanged = function(e) {
          this.visibleChanged.raise(this, e)
        }, n.prototype.dispose = function() {
          if (this._clearGrid(), this._grid.wj_sheetInfo = null, this._grid.dispose(), this._grid = null, null != this._tables) {
            for (var e = 0; e < this._tables.length; e++) this._tables[e] = null;
            this._tables = null
          }
        }, n.prototype.getCellStyle = function(e, t) {
          var o, n = this._grid.rows.length,
            i = this._grid.columns.length;
          return e >= n || t >= i ? null : (o = e * i + t, this._styledCells[o])
        }, n.prototype.addTableFromArray = function(n, i, l, s, r, a, h, c) {
          void 0 === c && (c = !0);
          var d, u, _, g, f, p, w, m, C, b, y, S = !1,
            R = this === this._owner.selectedSheet ? this._owner : this.grid;
          if (null == l || 0 === l.length) throw "Invalid array to load.";
          if (!(null != s && 0 !== s.length || null != (s = Object.keys(l[0])) && 0 !== s.length)) throw "Invalid array to load.";
          if (n >= this.rowCount || i >= this.columnCount) return null;
          var v = null == (h && h.showHeaderRow) || h.showHeaderRow,
            x = !(!h || !h.showTotalRow),
            T = n + (v ? 1 : 0),
            I = T + l.length - 1 + (x ? 1 : 0);
          for (d = [], C = 0; C < s.length; C++) b = s[C], u = new o.TableColumn(b), 0 === C ? u._totalRowLabel = "Total" : C === s.length - 1 && (u._totalRowFunction = "Sum"), d.push(u);
          if (_ = new t.CellRange(n, i, I, i + s.length - 1), 0 !== (g = this._needShiftForTable(_))) {
            if (!c) return null;
            if (!this._canShiftCells(_)) return null;
            if (f = this._needAddRowCountForAddTable(g, _), R.collectionView) {
              for (y = R.itemsSource instanceof e.collections.CollectionView, R.collectionView.beginUpdate(), w = 0; w < f; w++) y ? R.itemsSource.sourceCollection.push({}) : R.itemsSource.push({});
              R.collectionView.endUpdate()
            } else
              for (w = 0; w < f; w++) R.rows.push(new t.Row);
            S = !0
          }
          if (i + s.length >= R.columns.length) {
            if (!c) return null;
            if (p = i + s.length - R.columns.length + 1, this.itemsSource && p > 0) return null;
            for (R.columns.beginUpdate(), w = 0; w < p; w++) R.columns.push(new t.Column);
            R.columns.endUpdate()
          }
          if (R.beginUpdate(), S && this._moveDownCells(g, _), v)
            for (C = 0; C < s.length; C++) b = s[C], R.setCellData(n, i + C, b);
          for (m = 0; m < l.length; m++)
            for (C = 0; C < s.length; C++) b = s[C], R.setCellData(T + m, i + C, l[m][b]);
          return R.endUpdate(), this._addTable(_, r, a, d, h)
        }, n.prototype.findTable = function(e, t) {
          var o, n;
          if (this._tables && this._tables.length > 0)
            for (var i = 0; i < this._tables.length; i++)
              if (o = this._tables[i], n = o.getRange(), e >= n.topRow && e <= n.bottomRow && t >= n.leftCol && t <= n.rightCol) return o;
          return null
        }, n.prototype._attachOwner = function(e) {
          this._owner !== e && (this._owner = e)
        }, n.prototype._setValidName = function(e) {
          this._name = e, this.grid.wj_sheetInfo.name = e
        }, n.prototype._storeRowSettings = function() {
          var e, o = 0;
          for (this._rowSettings = []; o < this._grid.rows.length; o++)(e = this._owner.rows[o]) && (this._rowSettings[o] = {
            height: e.height,
            allowMerging: e.allowMerging,
            isCollapsed: e instanceof t.GroupRow ? e.isCollapsed : null,
            visible: e.visible,
            isSelected: e.isSelected,
            readOnly: e.isReadOnly
          })
        }, n.prototype._setRowSettings = function() {
          for (var e, n, i, l = 0; l < this._rowSettings.length; l++)(n = this._rowSettings[l]) && (e = this._grid.rows[l]) && (i = e instanceof o.HeaderRow, e.height = n.height, e.allowMerging = n.allowMerging, e.visible = n.visible, e instanceof t.GroupRow && (e.isCollapsed = !!n.isCollapsed), e.isSelected = !!n.isSelected, e.isReadOnly = i || !!n.readOnly)
        }, n.prototype._addTable = function(e, t, n, i, l) {
          if (!e.isValid) return null;
          var s;
          return null != t && null == this._owner._getTable(t) || (t = this._getUniqueTableName()), s = new o.Table(t, e, n, i, l), this.tables.push(s), s
        }, n.prototype._addSelection = function(e) {
          var t, o;
          for (this.selectionRanges.beginUpdate(), o = this.selectionRanges.length - 1; o >= 0; o--) t = this.selectionRanges[o], e.contains(t) && this.selectionRanges.removeAt(o);
          e.isValid && this.selectionRanges.push(e), this.selectionRanges.endUpdate()
        }, n.prototype._compareRows = function() {
          var t = this,
            o = this._unboundSortDesc;
          return function(n, i) {
            for (var l = 0; l < o.length; l++) {
              var s = o[l],
                r = n._ubv && s.column ? n._ubv[s.column._hash] : "",
                a = i._ubv && s.column ? i._ubv[s.column._hash] : "";
              if (e.isString(r) && "=" === r[0] && (r = t._owner.evaluate(r)), e.isString(a) && "=" === a[0] && (a = t._owner.evaluate(a)), r !== r && (r = null), a !== a && (a = null), e.isString(r) && (r = r.toLowerCase() + r), e.isString(a) && (a = a.toLowerCase() + a), "" === r || null == r) return 1;
              if ("" === a || null == a) return -1;
              var h = r < a ? -1 : r > a ? 1 : 0;
              if (e.isString(r) && e.isNumber(a) && (h = 1), e.isString(a) && e.isNumber(r) && (h = -1), 0 !== h) return s.ascending ? +h : -h
            }
            return 0
          }
        }, n.prototype._createGrid = function() {
          var e, o, n, i, l = document.createElement("div");
          for (this._isEmptyGrid = !0, l.style.visibility = "hidden", document.body.appendChild(l), e = new t.FlexGrid(l), document.body.removeChild(l), i = 0; i < this._rowCount; i++) e.rows.push(new t.Row);
          for (n = 0; n < this._columnCount; n++)(o = new t.Column).isRequired = !1, e.columns.push(o);
          return e.wj_sheetInfo = {
            name: this.name,
            visible: this.visible,
            styledCells: this._styledCells,
            mergedRanges: this._mergedRanges
          }, e
        }, n.prototype._clearGrid = function() {
          this._grid.rows.clear(), this._grid.columns.clear(), this._grid.columnHeaders.columns.clear(), this._grid.rowHeaders.rows.clear()
        }, n.prototype._gridItemsSourceChanged = function() {
          var e = this;
          e._owner && !e._owner._isCopying && e._owner.selectedSheet && e._name === e._owner.selectedSheet.name && (e._owner.filter.clear(), e._owner._copyFrom(e, !1), setTimeout(function() {
            e._owner._setFlexSheetToDirty(), e._owner.invalidate()
          }, 10))
        }, n.prototype._addHeaderRow = function() {
          var e;
          if (this._grid.collectionView && !this._grid.collectionView.isEmpty && !(this._grid.rows[0] instanceof o.HeaderRow)) {
            var t;
            (e = new o.HeaderRow).isReadOnly = !0;
            for (var n = 0; n < this._grid.columns.length; n++) t = this._grid.columns[n], e._ubv || (e._ubv = {}), e._ubv[t._hash] = t.header;
            this._grid.rows.insert(0, e)
          }
        }, n.prototype._getUniqueTableName = function() {
          for (var e = "Table1", t = 2;;) {
            if (null == this._owner._getTable(e)) break;
            e = "Table" + t, t += 1
          }
          return e
        }, n.prototype._needShiftForTable = function(e) {
          var t, o, n, i = 0,
            l = 0,
            s = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (t = e.topRow; t <= e.bottomRow; t++) {
            if (t >= s.rows.length) return i = e.rowSpan - l;
            for (o = e.leftCol; o <= e.rightCol && o < s.columns.length; o++)
              if (null != (n = s.getCellData(t, o, !1)) && "" !== n) return i = e.rowSpan - l;
            l++
          }
          return i
        }, n.prototype._needAddRowCountForAddTable = function(e, t) {
          var o, n, i, l, s, r = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (o = 1; o <= e; o++)
            for (n = r.rows.length - o, i = t.leftCol; i <= t.rightCol && i < r.columns.length; i++)
              if (l = r.getCellData(n, i, !1), s = this.getCellStyle(n, i), null != l && "" !== l || null != s) return e - o + 1;
          return 0
        }, n.prototype._moveDownTable = function(e) {
          var t, o, n, i, l = e.getRange(),
            s = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (t = l.bottomRow; t >= l.topRow; t--)
            for (o = l.leftCol; o <= l.rightCol; o++) i = null != s.columns[o].dataMap, n = s.getCellData(t, o, i), s.setCellData(t + 1, o, n), s.setCellData(t, o, "");
          e._updateTableRange(1, 1, 0, 0)
        }, n.prototype._moveDownCells = function(e, t) {
          var o, n, i, l, s, r, a, h, c, d, u = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (o = u.rows.length - 1 - e; o >= t.topRow; o--)
            for (n = t.leftCol; n <= t.rightCol && n < u.columns.length; n++) d = null != u.columns[n].dataMap, i = u.getCellData(o, n, d), u.setCellData(o + e, n, i), u.setCellData(o, n, ""), l = this.getCellStyle(o, n), r = (o + e) * u.columns.length + n, s = o * u.columns.length + n, l && (this._styledCells[r] = l, this._styledCells[s] = null), (a = this._mergedRanges[s]) && (a.row += e, a.row2 += e, this._mergedRanges[r] = a, this._mergedRanges[s] = null), (h = this.findTable(o, n)) && (c = h.getRange()).topRow === o && c.leftCol === n && h._updateTableRange(e, e, 0, 0)
        }, n.prototype._moveUpCells = function(e, t) {
          var o, n, i, l, s, r, a, h, c, d, u = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (o = t.topRow; o < u.rows.length; o++)
            for (n = t.leftCol; n <= t.rightCol && n < u.columns.length; n++) d = null != u.columns[n].dataMap, i = u.getCellData(o, n, d), u.setCellData(o - e, n, i), u.setCellData(o, n, ""), l = this.getCellStyle(o, n), r = (o - e) * u.columns.length + n, s = o * u.columns.length + n, l && (this._styledCells[r] = l, this._styledCells[s] = null), (a = this._mergedRanges[s]) && (a.row -= e, a.row2 -= e, this._mergedRanges[r] = a, this._mergedRanges[s] = null), (h = this.findTable(o, n)) && (c = h.getRange()).topRow === o && c.leftCol === n && h._updateTableRange(-e, -e, 0, 0)
        }, n.prototype._moveDownCellsWithinTable = function(e, t, o) {
          var n, i, l, s, r = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (n = o.bottomRow; n > o.topRow + e; n--)
            for (i = o.leftCol; i <= o.rightCol; i++) s = null != r.columns[i].dataMap, l = r.getCellData(n, i, s), r.setCellData(n + t, i, l), r.setCellData(n, i, ""), (n + t) * r.columns.length + i, n * r.columns.length + i
        }, n.prototype._moveUpCellsWithinTable = function(e, t, o) {
          var n, i, l, s, r = o.topRow + e,
            a = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (n = r; n <= o.bottomRow; n++)
            for (i = o.leftCol; i <= o.rightCol; i++) n >= r + t && (s = null != a.columns[i].dataMap, l = a.getCellData(n, i, s), a.setCellData(n - t, i, l)), a.setCellData(n, i, ""), (n - t) * a.columns.length + i, n * a.columns.length + i
        }, n.prototype._canShiftCells = function(e) {
          var t, o, n, i, l, s, r = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (t = e.topRow; t < r.rows.length; t++)
            for (o = e.leftCol; o <= e.rightCol && o < r.columns.length; o++) {
              if ((l = this.findTable(t, o)) && ((s = l.getRange()).leftCol < e.leftCol || s.rightCol > e.rightCol)) return !1;
              if (n = t * r.columns.length + o, (i = this._mergedRanges[n]) && (i.leftCol < e.leftCol || i.rightCol > e.rightCol)) return !1
            }
          return !0
        }, n.prototype._needMoveDownTable = function(e) {
          var t, o, n = e.getRange(),
            i = e.getColumns(),
            l = this === this._owner.selectedSheet ? this._owner : this.grid;
          if (0 === n.topRow) return !0;
          for (t = 0; t < i.length; t++)
            if (null != (o = l.getCellData(n.topRow - 1, n.leftCol + t, !1)) && "" !== o) return !0;
          return !1
        }, n.prototype._needAddRowCountForInsertTableRows = function(e, t) {
          var o, n, i, l, s, r = this === this._owner.selectedSheet ? this._owner : this.grid;
          for (o = 1; o <= e; o++) {
            if ((n = r.rows.length - o) <= t.bottomRow) return e;
            for (i = t.leftCol; i <= t.rightCol && i < r.columns.length; i++)
              if (l = r.getCellData(n, i, !1), s = this.getCellStyle(n, i), null != l && "" !== l || null != s) return e - o + 1
          }
          return 0
        }, n.prototype._getFilterSetting = function() {
          var e, t = 0;
          for (this._filterDefinition = this._owner.filter.filterDefinition, this._filterSetting = {}, this._filterSetting.filterColumns = this._owner.filter.filterColumns, this._filterSetting.columnFilterSettings || (this._filterSetting.columnFilterSettings = []); t < this._owner.filter._filters.length; t++)(e = this._owner.filter._filters[t]) && this._filterSetting.columnFilterSettings.push({
            column: e.column,
            filterType: e.filterType,
            dataMap: e.dataMap,
            valueFilterSetting: {
              maxValues: e.valueFilter.maxValues,
              uniqueValues: e.valueFilter.uniqueValues,
              sortValues: e.valueFilter.sortValues,
              dataMap: e.valueFilter.dataMap
            },
            conditionFilterSetting: {
              dataMap: e.conditionFilter.dataMap
            }
          })
        }, n.prototype._applyFilterSetting = function() {
          var e, t, o, n, i, l, s, r, a, h, c = 0;
          if (null == this._filterSetting) this._clearFilterSetting();
          else if (this._owner.filter.filterColumns = this._filterSetting.filterColumns, this._filterDefinition && (this._owner.filter.filterDefinition = this._filterDefinition), null != (e = this._filterSetting.columnFilterSettings) && e.length > 0)
            for (; c < e.length; c++)(t = e[c]) && null != t.column && (i = this._owner.filter.getColumnFilter(t.column)) && (l = i.valueFilter.showValues, s = i.valueFilter.filterText, r = {
              operator: i.conditionFilter.condition1.operator,
              value: i.conditionFilter.condition1.value
            }, a = {
              operator: i.conditionFilter.condition2.operator,
              value: i.conditionFilter.condition2.value
            }, h = i.conditionFilter.and, null != t.dataMap && (i.dataMap = t.dataMap), null != t.filterType && (i.filterType = t.filterType), (o = t.valueFilterSetting) && (o.dataMap && (i.valueFilter.dataMap = o.dataMap), null != o.maxValues && (i.valueFilter.maxValues = o.maxValues), null != o.sortValues && (i.valueFilter.sortValues = o.sortValues), o.uniqueValues && (i.valueFilter.uniqueValues = o.uniqueValues)), (n = t.conditionFilterSetting) && n.dataMap && (i.conditionFilter.dataMap = n.dataMap), i.valueFilter.showValues = l, i.valueFilter.filterText = s, i.conditionFilter.condition1.operator = r.operator, i.conditionFilter.condition1.value = r.value, i.conditionFilter.condition2.operator = a.operator, i.conditionFilter.condition2.value = a.value, i.conditionFilter.and = h)
        }, n.prototype._clearFilterSetting = function() {
          this._owner.filter._filters = [], this._owner.filter.filterColumns = null, this._filterDefinition && (this._owner.filter.filterDefinition = this._filterDefinition)
        }, n.prototype._cloneMergedCells = function() {
          for (var e, t = 0, o = []; t < this._mergedRanges.length; t++) e = this._mergedRanges[t], o.push(e.clone());
          return o
        }, n.prototype._getMergedRange = function(e, t) {
          var o, n;
          for (o = 0; o < this._mergedRanges.length; o++)
            if (n = this._mergedRanges[o], e >= n.topRow && e <= n.bottomRow && t >= n.leftCol && t <= n.rightCol) return n;
          return null
        }, n
      }();
      o.Sheet = n;
      var i = function(t) {
        function o() {
          var o = null !== t && t.apply(this, arguments) || this;
          return o._current = -1, o.sheetCleared = new e.Event, o.selectedSheetChanged = new e.Event, o.sheetNameChanged = new e.Event, o.sheetVisibleChanged = new e.Event, o
        }
        return __extends(o, t), o.prototype.onSheetCleared = function() {
          this.sheetCleared.raise(this, new e.EventArgs)
        }, Object.defineProperty(o.prototype, "selectedIndex", {
          get: function() {
            return this._current
          },
          set: function(e) {
            this._moveCurrentTo(+e)
          },
          enumerable: !0,
          configurable: !0
        }), o.prototype.onSelectedSheetChanged = function(e) {
          this.selectedSheetChanged.raise(this, e)
        }, o.prototype.insert = function(e, o) {
          var n;
          (n = o.name ? this.getValidSheetName(o) : this._getUniqueName()) !== o.name && (o.name = n), t.prototype.insert.call(this, e, o), this._postprocessSheet(o)
        }, o.prototype.push = function() {
          for (var e = [], o = 0; o < arguments.length; o++) e[o] = arguments[o];
          this.length;
          for (var n, i = 0; i < e.length; i++)(n = e[i].name ? this.getValidSheetName(e[i]) : this._getUniqueName()) !== e[i].name && (e[i].name = n), t.prototype.push.call(this, e[i]), this._postprocessSheet(e[i]);
          return this.length
        }, o.prototype.splice = function(e, o, n) {
          var i;
          return n ? ((i = n.name ? this.getValidSheetName(n) : this._getUniqueName()) !== n.name && (n.name = i), this._postprocessSheet(n), t.prototype.splice.call(this, e, o, n)) : t.prototype.splice.call(this, e, o, n)
        }, o.prototype.removeAt = function(e) {
          this.hide(e) && (t.prototype.removeAt.call(this, e), e < this.selectedIndex && (this._current -= 1))
        }, o.prototype.onSheetNameChanged = function(e) {
          this.sheetNameChanged.raise(this, e)
        }, o.prototype.onSheetVisibleChanged = function(e) {
          this.sheetVisibleChanged.raise(this, e)
        }, o.prototype.selectFirst = function() {
          return this._moveCurrentTo(0)
        }, o.prototype.selectLast = function() {
          return this._moveCurrentTo(this.length - 1)
        }, o.prototype.selectPrevious = function() {
          return this._moveCurrentTo(this._current - 1)
        }, o.prototype.selectNext = function() {
          return this._moveCurrentTo(this._current + 1)
        }, o.prototype.hide = function(e) {
          return !(e < 0 && e >= this.length) && (!!this[e].visible && (this[e].visible = !1, !0))
        }, o.prototype.show = function(e) {
          return !(e < 0 && e >= this.length) && (this[e].visible = !0, this._moveCurrentTo(e), !0)
        }, o.prototype.clear = function() {
          for (var e = 0; e < this.length; e++) this[e].dispose(), null;
          t.prototype.clear.call(this), this._current = -1, this.onSheetCleared()
        }, o.prototype.isValidSheetName = function(e) {
          var t = this._getSheetIndexFrom(e.name),
            o = this.indexOf(e);
          return -1 === t || t === o
        }, o.prototype.getValidSheetName = function(e) {
          for (var t, o = e.name, n = 1, i = this.indexOf(e);;) {
            if (-1 === (t = this._getSheetIndexFrom(o)) || t === i) break;
            o = e.name.concat((n + 1).toString()), n += 1
          }
          return o
        }, o.prototype._moveCurrentTo = function(t) {
          var o, n = t;
          if (t < 0 || t >= this.length) return !1;
          if (this._current < n || 0 === n)
            for (; n < this.length && !this[n].visible;) n++;
          else if (this._current > n)
            for (; n >= 0 && !this[n].visible;) n--;
          if (n === this.length)
            for (n = t; n >= 0 && !this[n].visible;) n--;
          return !(n < 0) && (n !== this._current && (o = new e.PropertyChangedEventArgs("sheetIndex", this._current, n), this._current = n, this.onSelectedSheetChanged(o)), !0)
        }, o.prototype._getSheetIndexFrom = function(e) {
          var t;
          if (!e) return -1;
          e = e.toLowerCase();
          for (var o = 0; o < this.length; o++)
            if (t = this[o], (t.name ? t.name.toLowerCase() : "") === e) return o;
          return -1
        }, o.prototype._postprocessSheet = function(t) {
          var o = this;
          t.nameChanged.addHandler(function() {
            var n, i = o._getSheetIndexFrom(t.name);
            o.isValidSheetName(t) || t._setValidName(o.getValidSheetName(t)), n = new e.collections.NotifyCollectionChangedEventArgs(e.collections.NotifyCollectionChangedAction.Change, t, e.isNumber(i) ? i : o.length - 1), o.onSheetNameChanged(n)
          }), t.visibleChanged.addHandler(function() {
            var n = o._getSheetIndexFrom(t.name),
              i = new e.collections.NotifyCollectionChangedEventArgs(e.collections.NotifyCollectionChangedAction.Change, t, e.isNumber(n) ? n : o.length - 1);
            o.onSheetVisibleChanged(i)
          })
        }, o.prototype._getUniqueName = function() {
          for (var e = "Sheet1", t = 2;;) {
            if (-1 === this._getSheetIndexFrom(e)) break;
            e = "Sheet" + t, t += 1
          }
          return e
        }, o
      }(e.collections.ObservableArray);
      o.SheetCollection = i;
      var l = function(o) {
        function n(e, t, n) {
          var i = o.call(this, e, n) || this;
          i._rtl = !1, i._sheetTabClicked = !1;
          var l = i;
          return l._owner = t, l._sheets = t.sheets, l._rtl = "rtl" == getComputedStyle(l._owner.hostElement).direction, l._initControl(), l.deferUpdate(function() {
            n && l.initialize(n)
          }), i
        }
        return __extends(n, o), n.prototype.refresh = function(e) {
          this._tabContainer.innerHTML = "", this._tabContainer.innerHTML = this._getSheetTabs(), this._rtl && this._adjustSheetsPosition(), this._adjustSize()
        }, n.prototype._sourceChanged = function(t, o) {
          void 0 === o && (o = e.collections.NotifyCollectionChangedEventArgs.reset);
          var n = o;
          switch (n.action) {
            case e.collections.NotifyCollectionChangedAction.Add:
              n.index - 1 < 0 && 0, this._tabContainer.innerHTML = "", this._tabContainer.innerHTML = this._getSheetTabs(), this._rtl && this._adjustSheetsPosition(), this._adjustSize();
              break;
            case e.collections.NotifyCollectionChangedAction.Remove:
              this._tabContainer.removeChild(this._tabContainer.children[n.index]), this._tabContainer.hasChildNodes() && this._updateTabActive(n.index, !0), this._adjustSize();
              break;
            default:
              this.invalidate()
          }
        }, n.prototype._selectedSheetChanged = function(e, t) {
          this._updateTabActive(t.oldValue, !1), this._updateTabActive(t.newValue, !0), this._sheetTabClicked ? this._sheetTabClicked = !1 : this._scrollToActiveSheet(t.newValue, t.oldValue), this._adjustSize()
        }, n.prototype._initControl = function() {
          var e = this;
          e.applyTemplate("", e.getTemplate(), {
            _sheetContainer: "sheet-container",
            _tabContainer: "container",
            _sheetPage: "sheet-page",
            _newSheet: "new-sheet"
          }), e._rtl && (e._sheetPage.style.right = "0px", e._tabContainer.parentElement.style.right = e._sheetPage.clientWidth + "px", e._tabContainer.style.right = "0px", e._tabContainer.style.cssFloat = "right", e._newSheet.style.right = e._sheetPage.clientWidth + e._tabContainer.parentElement.clientWidth + "px"), e._adjustNavigationButtons(e._rtl), e.addEventListener(e.hostElement, "mousedown", function() {
            e._owner._mouseHdl._htDown = null
          }), e.addEventListener(e._newSheet, "click", function(t) {
            var o = e._owner.selectedSheetIndex;
            e._owner.addUnboundSheet(), e._scrollToActiveSheet(e._owner.selectedSheetIndex, o)
          }), e._sheets.collectionChanged.addHandler(e._sourceChanged, e), e._sheets.selectedSheetChanged.addHandler(e._selectedSheetChanged, e), e._sheets.sheetNameChanged.addHandler(e._updateSheetName, e), e._sheets.sheetVisibleChanged.addHandler(e._updateTabShown, e), e._initSheetPage(), e._initSheetTab()
        }, n.prototype._initSheetTab = function() {
          var e = this;
          e.addEventListener(e._tabContainer, "mousedown", function(t) {
            var o, n = t.target;
            n instanceof HTMLLIElement && (e._sheetTabClicked = !0, o = e._getItemIndex(e._tabContainer, n), e._scrollSheetTabContainer(n), o > -1 && (e._sheets.selectedIndex = o))
          })
        }, n.prototype._initSheetPage = function() {
          var e = this;
          e.hostElement.querySelector("div.wj-sheet-page").addEventListener("click", function(t) {
            var o = "[object HTMLButtonElement]" === t.target.toString() ? t.target : t.target.parentElement,
              n = e._getItemIndex(e._sheetPage, o);
            if (0 !== e._sheets.length) switch (n) {
              case 0:
                e._sheets.selectFirst();
                break;
              case 1:
                e._sheets.selectPrevious();
                break;
              case 2:
                e._sheets.selectNext();
                break;
              case 3:
                e._sheets.selectLast()
            }
          })
        }, n.prototype._getSheetTabs = function() {
          var e, t = "";
          for (e = 0; e < this._sheets.length; e++) t += this._getSheetElement(this._sheets[e], this._sheets.selectedIndex === e);
          return t
        }, n.prototype._getSheetElement = function(e, t) {
          void 0 === t && (t = !1);
          var o = "<li";
          return e.visible ? t && (o += ' class="active"') : o += ' class="hidden"', o += ">" + e.name + "</li>"
        }, n.prototype._updateTabActive = function(t, o) {
          t < 0 || t >= this._tabContainer.children.length || (o ? e.addClass(this._tabContainer.children[t], "active") : e.removeClass(this._tabContainer.children[t], "active"))
        }, n.prototype._updateTabShown = function(t, o) {
          o.index < 0 || o.index >= this._tabContainer.children.length || (o.item.visible ? e.removeClass(this._tabContainer.children[o.index], "hidden") : e.addClass(this._tabContainer.children[o.index], "hidden"), this._adjustSize())
        }, n.prototype._adjustSize = function() {
          var e, t, o = this._tabContainer.childElementCount,
            n = 0,
            i = 0;
          if ("none" !== this.hostElement.style.display) {
            for (i = this._tabContainer.parentElement.scrollLeft, this._tabContainer.parentElement.style.width = "", this._tabContainer.style.width = "", this._sheetPage.parentElement.style.width = "", e = 0; e < o; e++) n += this._tabContainer.children[e].offsetWidth + 1;
            t = this.hostElement.offsetWidth - this._sheetPage.offsetWidth - this._newSheet.offsetWidth - 2, this._tabContainer.parentElement.style.width = (n > t ? t : n) + "px", this._tabContainer.style.width = n + "px", this._sheetPage.parentElement.style.width = this._sheetPage.offsetWidth + this._newSheet.offsetWidth + this._tabContainer.parentElement.offsetWidth + 3 + "px", this._tabContainer.parentElement.scrollLeft = i
          }
        }, n.prototype._getItemIndex = function(e, t) {
          for (var o = 0; o < e.children.length; o++)
            if (e.children[o] === t) return o;
          return -1
        }, n.prototype._updateSheetName = function(e, t) {
          this._tabContainer.querySelectorAll("li")[t.index].textContent = t.item.name, this._adjustSize()
        }, n.prototype._scrollSheetTabContainer = function(e) {
          var o, n = this._tabContainer.parentElement.scrollLeft,
            i = this._sheetPage.offsetWidth,
            l = this._newSheet.offsetWidth,
            s = this._tabContainer.parentElement.offsetWidth;
          if (this._rtl) switch (t.FlexGrid._getRtlMode()) {
            case "rev":
              (o = -this._tabContainer.offsetLeft) + e.offsetLeft + e.offsetWidth > s + n ? this._tabContainer.parentElement.scrollLeft += e.offsetWidth : o + e.offsetLeft < n && (this._tabContainer.parentElement.scrollLeft -= e.offsetWidth);
              break;
            case "neg":
              e.offsetLeft < n ? this._tabContainer.parentElement.scrollLeft -= e.offsetWidth : e.offsetLeft + e.offsetWidth > s + n && (this._tabContainer.parentElement.scrollLeft += e.offsetWidth);
              break;
            default:
              e.offsetLeft - l + n < 0 ? this._tabContainer.parentElement.scrollLeft += e.offsetWidth : e.offsetLeft + e.offsetWidth - l + n > s && (this._tabContainer.parentElement.scrollLeft -= e.offsetWidth)
          } else e.offsetLeft + e.offsetWidth - i > s + n ? this._tabContainer.parentElement.scrollLeft += e.offsetWidth : e.offsetLeft - i < n && (this._tabContainer.parentElement.scrollLeft -= e.offsetWidth)
        }, n.prototype._adjustSheetsPosition = function() {
          var e, t, o = this._tabContainer.querySelectorAll("li"),
            n = 0;
          for (t = 0; t < o.length; t++)(e = o[t]).style.cssFloat = "right", e.style.right = n + "px", n += o[t].clientWidth
        }, n.prototype._scrollToActiveSheet = function(e, o) {
          var n, i, l, s = this._tabContainer.querySelectorAll("li");
          if (i = this._tabContainer.clientWidth > this._tabContainer.parentElement.clientWidth ? this._tabContainer.clientWidth - this._tabContainer.parentElement.clientWidth : 0, s.length > 0 && e < s.length && o < s.length) {
            if (0 === e && !this._rtl || e === s.length - 1 && this._rtl) {
              if (this._rtl) switch (t.FlexGrid._getRtlMode()) {
                case "rev":
                  this._tabContainer.parentElement.scrollLeft = 0;
                  break;
                case "neg":
                  this._tabContainer.parentElement.scrollLeft = -i;
                  break;
                default:
                  this._tabContainer.parentElement.scrollLeft = i
              } else this._tabContainer.parentElement.scrollLeft = 0;
              return
            }
            if (0 === e && this._rtl || e === s.length - 1 && !this._rtl) {
              if (this._rtl) switch (t.FlexGrid._getRtlMode()) {
                case "rev":
                  this._tabContainer.parentElement.scrollLeft = i;
                  break;
                case "neg":
                default:
                  this._tabContainer.parentElement.scrollLeft = 0
              } else this._tabContainer.parentElement.scrollLeft = i;
              return
            }
            if (e >= o)
              for (l = o + 1; l <= e; l++) n = s[l], this._scrollSheetTabContainer(n);
            else
              for (l = o - 1; l >= e; l--) n = s[l], this._scrollSheetTabContainer(n)
          }
        }, n.prototype._adjustNavigationButtons = function(t) {
          var o, n = this.hostElement.querySelectorAll(".wj-sheet-page button");
          n && 4 === n.length && (t ? (o = n[0].querySelector("span"), e.removeClass(o, "wj-glyph-step-backward"), e.addClass(o, "wj-glyph-step-forward"), o = n[1].querySelector("span"), e.removeClass(o, "wj-glyph-left"), e.addClass(o, "wj-glyph-right"), o = n[2].querySelector("span"), e.removeClass(o, "wj-glyph-right"), e.addClass(o, "wj-glyph-left"), o = n[3].querySelector("span"), e.removeClass(o, "wj-glyph-step-forward"), e.addClass(o, "wj-glyph-step-backward")) : (o = n[0].querySelector("span"), e.removeClass(o, "wj-glyph-step-forward"), e.addClass(o, "wj-glyph-step-backward"), o = n[1].querySelector("span"), e.removeClass(o, "wj-glyph-right"), e.addClass(o, "wj-glyph-left"), o = n[2].querySelector("span"), e.removeClass(o, "wj-glyph-left"), e.addClass(o, "wj-glyph-right"), o = n[3].querySelector("span"), e.removeClass(o, "wj-glyph-step-backward"), e.addClass(o, "wj-glyph-step-forward")))
        }, n.controlTemplate = '<div wj-part="sheet-container" class="wj-sheet" style="height:100%;position:relative"><div wj-part="sheet-page" class="wj-btn-group wj-sheet-page"><button type="button" class="wj-btn wj-btn-default"><span class="wj-sheet-icon wj-glyph-step-backward"></span></button><button type="button" class="wj-btn wj-btn-default"><span class="wj-sheet-icon wj-glyph-left"></span></button><button type="button" class="wj-btn wj-btn-default"><span class="wj-sheet-icon wj-glyph-right"></span></button><button type="button" class="wj-btn wj-btn-default"><span class="wj-sheet-icon wj-glyph-step-forward"></span></button></div><div class="wj-sheet-tab" style="height:100%;overflow:hidden"><ul wj-part="container"></ul></div><div wj-part="new-sheet" class="wj-new-sheet"><span class="wj-sheet-icon wj-glyph-file"></span></div></div>', n
      }(e.Control);
      o._SheetTabs = l;
      var s = function() {
        function e(e, t) {
          this._column = e, this._ascending = t
        }
        return Object.defineProperty(e.prototype, "column", {
          get: function() {
            return this._column
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "ascending", {
          get: function() {
            return this._ascending
          },
          enumerable: !0,
          configurable: !0
        }), e
      }();
      o._UnboundSortDescription = s
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    ! function(t) {
      "use strict";
      var o = function() {
        function o(t) {
          this._owner = t, this._sortDescriptions = new e.collections.CollectionView, this._committedList = [new n(-1, !0)], this._sortDescriptions.newItemCreator = function() {
            return new n(-1, !0)
          }
        }
        return Object.defineProperty(o.prototype, "sortDescriptions", {
          get: function() {
            return this._sortDescriptions
          },
          set: function(e) {
            this._sortDescriptions = e, this.commitSort()
          },
          enumerable: !0,
          configurable: !0
        }), o.prototype.addSortLevel = function(t, o) {
          void 0 === o && (o = !0);
          var n = this._sortDescriptions.addNew();
          null != t && !isNaN(t) && e.isInt(t) && (n.columnIndex = t), n.ascending = o, this._sortDescriptions.commitNew()
        }, o.prototype.deleteSortLevel = function(e) {
          var t;
          (t = null != e ? this._getSortItem(e) : this._sortDescriptions.currentItem) && this._sortDescriptions.remove(t)
        }, o.prototype.copySortLevel = function() {
          var e = this._sortDescriptions.currentItem;
          if (e) {
            var t = this._sortDescriptions.addNew();
            t.columnIndex = parseInt(e.columnIndex), t.ascending = e.ascending, this._sortDescriptions.commitNew()
          }
        }, o.prototype.editSortLevel = function(e, t) {
          null != e && (this._sortDescriptions.currentItem.columnIndex = e), null != t && (this._sortDescriptions.currentItem.ascending = t)
        }, o.prototype.moveSortLevel = function(e) {
          var t = this._sortDescriptions.currentItem;
          if (t) {
            var o = this._sortDescriptions.sourceCollection,
              n = o.indexOf(t),
              i = n + e;
            n > -1 && i > -1 && (o.splice(n, 1), o.splice(i, 0, t), this._sortDescriptions.refresh(), this._sortDescriptions.moveCurrentTo(t))
          }
        }, o.prototype.checkSortItemExists = function(e) {
          for (var t = 0, o = this._sortDescriptions.itemCount; t < o; t++)
            if (+this._sortDescriptions.items[t].columnIndex === e) return t;
          return -1
        }, o.prototype.commitSort = function(o) {
          void 0 === o && (o = !0);
          var i, l, s, r, a, h, c, d, u, _, g, f, p, w, m, C, b = this,
            y = b._owner,
            S = b._owner.itemsSource instanceof e.collections.CollectionView,
            R = {};
          if (b._owner.selectedSheet && 0 !== b._owner.columns.length) {
            if (y._needCopyToSheet = !1, h = y.selectedSheet._unboundSortDesc, o && y.undoStack.stackSize > 0 && (c = new t._SortColumnAction(y)), b._sortDescriptions.itemCount > 0 ? b._committedList = b._cloneSortList(b._sortDescriptions.items) : b._committedList = [new n(-1, !0)], _ = y.selectedSheet._filterDefinition, y.collectionView) {
              for (y._isSorting = !0, y.beginUpdate(), (d = y.editableCollectionView) && d.currentEditItem && -1 !== d.items.indexOf(d.currentEditItem) && !b._isEmpty(d.currentEditItem) && d.commitEdit(), y.collectionView.beginUpdate(), y.selectedSheet.grid.collectionView.beginUpdate(), (s = y.collectionView.sortDescriptions).clear(), u = y.selection.clone(), C = y.allowAddNew && y.newRowAtTop ? 2 : 1, f = [], a = 0; a < y.rows.length - C; a++)(m = (w = y.rows[a + C]).dataItem) && (f[p = null != m._itemIdx ? m._itemIdx : a] = w.visible);
              for (S || (r = y.selectedSheet.grid.collectionView.sortDescriptions).clear(), y.collectionView.sourceCollection.map(function(e, t) {
                e._itemIdx = t
              }), y.selection = u, navigator.userAgent.toLowerCase().indexOf("chrome") > -1 && (y.collectionView.useStableSort = !0, S || (y.selectedSheet.grid.collectionView.useStableSort = !0)), a = 0; a < b._sortDescriptions.itemCount; a++)(i = b._sortDescriptions.items[a]).columnIndex > -1 && (l = new e.collections.SortDescription(y.columns[i.columnIndex].binding, i.ascending), s.push(l), S || (y.collectionView.sortDescriptions.push(l), r.push(l)));
              for (y.selectedSheet.selectionRanges.clear(), y.collectionView.endUpdate(), y.selectedSheet.grid.collectionView.endUpdate(), u = y.selection.clone(), g = y.collectionView._view.slice(), a = 0; a < g.length; a++) a !== (m = g[a])._itemIdx && y._updateFormulaForReorderingRows(m._itemIdx + C, a + C), y._updateCellStyleForReorderingRows(m._itemIdx + C, a + C, R);
              for (a = 0; a < y.rows.length - C; a++)(m = (w = y.rows[a + C]).dataItem) && (p = null != m._itemIdx ? m._itemIdx : a, w.visible = f[p]);
              y.selectedSheet._styledCells = R, y.selection = u, y.endUpdate(), y._copyColumnsToSelectedSheet(), y._isSorting = !1
            } else {
              for (h.clear(), a = 0; a < b._sortDescriptions.itemCount; a++)(i = b._sortDescriptions.items[a]).columnIndex > -1 && h.push(new t._UnboundSortDescription(y.columns[i.columnIndex], i.ascending));
              setTimeout(function() {
                _ && (y.filter.filterDefinition = _)
              }, 10)
            }
            c && (c.saveNewState(), y.undoStack._addAction(c)), y._copiedRanges = null, y._needCopyToSheet = !0
          }
        }, o.prototype.cancelSort = function() {
          this._sortDescriptions.sourceCollection = this._committedList.slice()
        }, o.prototype.clearSort = function() {
          this._sortDescriptions.sourceCollection = [], this.commitSort()
        }, o.prototype._getColumnIndex = function(e) {
          for (var t = 0, o = this._owner.columns.length; t < o; t++)
            if (this._owner.columns[t].binding === e) return t;
          return -1
        }, o.prototype._getSortItem = function(e) {
          var t = this.checkSortItemExists(e);
          if (t > -1) return this._sortDescriptions.items[t]
        }, o.prototype._cloneSortList = function(e) {
          for (var t = [], o = 0; o < e.length; o++) t[o] = e[o].clone();
          return t
        }, o.prototype._updateSortSortDescription = function(e, t, o) {
          void 0 === o && (o = !0);
          var n, i;
          for (n = this._sortDescriptions.items.length - 1; n >= 0; n--) i = this._sortDescriptions.items[n], o ? i.columnIndex > e && (i.columnIndex += t) : i.columnIndex >= e + t ? i.columnIndex -= t : i.columnIndex >= e && this._sortDescriptions.remove(i)
        }, o.prototype._isEmpty = function(e) {
          var t = Object.prototype.hasOwnProperty;
          if (null == e) return !0;
          if (e.length > 0) return !1;
          if (0 === e.length) return !0;
          for (var o in e)
            if (t.call(e, o)) return !1;
          return !0
        }, o
      }();
      t.SortManager = o;
      var n = function() {
        function e(e, t) {
          this._columnIndex = e, this._ascending = t
        }
        return Object.defineProperty(e.prototype, "columnIndex", {
          get: function() {
            return this._columnIndex
          },
          set: function(e) {
            this._columnIndex = +e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "ascending", {
          get: function() {
            return this._ascending
          },
          set: function(e) {
            this._ascending = e
          },
          enumerable: !0,
          configurable: !0
        }), e.prototype.clone = function() {
          return new e(this._columnIndex, this._ascending)
        }, e
      }();
      t.ColumnSortDescription = n
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    ! function(t) {
      "use strict";
      var o = function() {
        function o(o) {
          this.MAX_STACK_SIZE = 500, this._stack = [], this._pointer = -1, this._resizingTriggered = !1, this.undoStackChanged = new e.Event;
          var n = this;
          n._owner = o, n._owner.prepareCellForEdit.addHandler(n._initCellEditAction, n), n._owner.cellEditEnded.addHandler(function(e, o) {
            n._pendingAction && (!o.data || 46 !== o.data.keyCode && 8 !== o.data.keyCode) && n._pendingAction instanceof t._EditAction && !n._pendingAction.isPaste && n._afterProcessCellEditAction(n)
          }, n), n._owner.pasting.addHandler(n._initCellEditActionForPasting, n), n._owner.pastingCell.addHandler(function(e, o) {
            n._pendingAction && (n._pendingAction instanceof t._EditAction ? n._pendingAction.updateForPasting(o.range) : n._pendingAction instanceof t._CutAction && n._pendingAction.updateForPasting(o.range))
          }, n), n._owner.pasted.addHandler(function() {
            n._pendingAction && (n._pendingAction instanceof t._EditAction && n._pendingAction.isPaste ? n._afterProcessCellEditAction(n) : n._pendingAction instanceof t._CutAction && (n._pendingAction.saveNewState(), n._addAction(n._pendingAction), n._pendingAction = null))
          }, n), n._owner.resizingColumn.addHandler(function(e, o) {
            0 !== n.stackSize && n._owner.selectedSheet && (n._resizingTriggered || (n._pendingAction = new t._ColumnResizeAction(n._owner, o.panel, o.col), n._resizingTriggered = !0))
          }, n), n._owner.resizedColumn.addHandler(function(e, o) {
            n._pendingAction instanceof t._ColumnResizeAction && n._pendingAction.saveNewState() && n._addAction(n._pendingAction), n._pendingAction = null, n._resizingTriggered = !1
          }, n), n._owner.resizingRow.addHandler(function(e, o) {
            0 !== n.stackSize && n._owner.selectedSheet && (n._resizingTriggered || (n._pendingAction = new t._RowResizeAction(n._owner, o.panel, o.row), n._resizingTriggered = !0))
          }, n), n._owner.resizedRow.addHandler(function(e, o) {
            n._pendingAction instanceof t._RowResizeAction && n._pendingAction.saveNewState() && n._addAction(n._pendingAction), n._pendingAction = null, n._resizingTriggered = !1
          }, n), n._owner.draggingRowColumn.addHandler(function(e, o) {
            0 !== n.stackSize && n._owner.selectedSheet && o.isShiftKey && (o.isDraggingRows ? n._pendingAction = new t._RowsChangedAction(n._owner) : n._pendingAction = new t._ColumnsChangedAction(n._owner))
          }, n), n._owner.droppingRowColumn.addHandler(function() {
            n._pendingAction && n._pendingAction.saveNewState() && n._addAction(n._pendingAction), n._pendingAction = null
          }, n)
        }
        return Object.defineProperty(o.prototype, "stackSize", {
          get: function() {
            return null == this._stackSize ? this.MAX_STACK_SIZE : this._stackSize
          },
          set: function(t) {
            e.isNumber(t) && (t < 0 ? this._stackSize = 0 : t > this.MAX_STACK_SIZE ? this._stackSize = this.MAX_STACK_SIZE : this._stackSize = Math.floor(t))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(o.prototype, "canUndo", {
          get: function() {
            return this._pointer > -1 && this._pointer < this._stack.length
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(o.prototype, "canRedo", {
          get: function() {
            return this._pointer + 1 > -1 && this._pointer + 1 < this._stack.length
          },
          enumerable: !0,
          configurable: !0
        }), o.prototype.onUndoStackChanged = function() {
          this.undoStackChanged.raise(this)
        }, o.prototype.undo = function() {
          var e;
          this.canUndo && (e = this._stack[this._pointer], this._beforeUndoRedo(e), e.undo(), this._pointer--, this.onUndoStackChanged())
        }, o.prototype.redo = function() {
          var e;
          this.canRedo && (this._pointer++, e = this._stack[this._pointer], this._beforeUndoRedo(e), e.redo(), this.onUndoStackChanged())
        }, o.prototype.clear = function() {
          this._stack.length = 0
        }, o.prototype._addAction = function(e) {
          this._stack.length > 0 && this._stack.length > this._pointer + 1 && this._stack.splice(this._pointer + 1, this._stack.length - this._pointer - 1), this._stack.length >= this.stackSize && this._stack.splice(0, this._stack.length - this.stackSize + 1), this._pointer = this._stack.length, this._stack.push(e), this.onUndoStackChanged()
        }, o.prototype._pop = function() {
          var e;
          return this._pointer < 0 ? null : (e = this._stack[this._pointer], this._pointer--, e)
        }, o.prototype._updateCurrentAction = function(e) {
          var t;
          this._pointer < 0 || (t = this._stack[this._pointer]) instanceof e && t.saveNewState()
        }, o.prototype._initCellEditAction = function(e, o) {
          0 !== this.stackSize && this._owner.selectedSheet && (this._pendingAction = new t._EditAction(this._owner, o.range))
        }, o.prototype._initCellEditActionForPasting = function() {
          0 !== this.stackSize && this._owner.selectedSheet && (this._owner._isCutting ? this._pendingAction = new t._CutAction(this._owner) : (this._pendingAction = new t._EditAction(this._owner), this._pendingAction.markIsPaste()))
        }, o.prototype._afterProcessCellEditAction = function(e) {
          e._pendingAction && (e._pendingAction instanceof t._EditAction && e._pendingAction.saveNewState() && e._addAction(this._pendingAction), e._pendingAction = null)
        }, o.prototype._beforeUndoRedo = function(e) {
          this._owner.selectedSheetIndex = e.sheetIndex
        }, o
      }();
      t.UndoStack = o
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function() {
        function n(e, t, o, n, i) {
          if (this._showHeaderRow = !0, this._showTotalRow = !1, this._showBandedColumns = !1, this._showBandedRows = !0, this._alterFirstColumn = !1, this._alterLastColumn = !1, this._orgHeaderCellsContent = [], !t.isValid) throw "The range of this table is invalid.";
          this._name = e, this._range = t.clone(), this._style = o, null != n && n.length > 0 && this._pushTableColumns(n), null != i && (null != i.showHeaderRow && (this._showHeaderRow = i.showHeaderRow), null != i.showTotalRow && (this._showTotalRow = i.showTotalRow), null != i.showBandedColumns && (this._showBandedColumns = i.showBandedColumns), null != i.showBandedRows && (this._showBandedRows = i.showBandedRows), null != i.alterFirstColumn && (this._alterFirstColumn = i.alterFirstColumn), null != i.alterLastColumn && (this._alterLastColumn = i.alterLastColumn))
        }
        return Object.defineProperty(n.prototype, "name", {
          get: function() {
            return this._name
          },
          set: function(e) {
            var t, n;
            if (null == e || "" === e) throw "The name of the Table should not be empty.";
            this._name.toLowerCase() !== e.toLowerCase() && (n = this._name, this._name = e, this._owner && (!this._owner._isUndoing && this._owner.undoStack.stackSize > 0 && (t = new o._TableSettingAction(this._owner, this)), this._owner._updateFormulasWithNameUpdating(n, e, !0), t && (t.saveNewState(), this._owner.undoStack._addAction(t), t = null)))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "sheet", {
          get: function() {
            return this._sheet
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "style", {
          get: function() {
            return this._style
          },
          set: function(e) {
            var t;
            e.name !== this._style.name && (this._owner && !this._owner._isUndoing && this._owner.undoStack.stackSize > 0 && (t = new o._TableSettingAction(this._owner, this)), this._style = e, this._owner && (t && (t.saveNewState(), this._owner.undoStack._addAction(t), t = null), this._owner.refresh()))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "showHeaderRow", {
          get: function() {
            return this._showHeaderRow
          },
          set: function(e) {
            var t, n;
            if (e !== this._showHeaderRow && (this._owner && !this._owner._isUndoing && this._owner.undoStack.stackSize > 0 && (t = new o._TableSettingAction(this._owner, this)), n = this._showHeaderRow, this._showHeaderRow = e, this._owner)) try {
              this._owner.beginUpdate(), this._adjustTableRangeWithHeaderRow(), t && (t.saveNewState(), this._owner.undoStack._addAction(t))
            } catch (e) {
              throw this._showHeaderRow = n, e
            } finally {
              t = null, this._owner.endUpdate()
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "showTotalRow", {
          get: function() {
            return this._showTotalRow
          },
          set: function(e) {
            var t, n;
            if (e !== this._showTotalRow && (this._owner && !this._owner._isUndoing && this._owner.undoStack.stackSize > 0 && (t = new o._TableSettingAction(this._owner, this)), n = this._showTotalRow, this._showTotalRow = e, this._owner)) try {
              this._owner.beginUpdate(), this._adjustTableRangeWithTotalRow(), t && (t.saveNewState(), this._owner.undoStack._addAction(t))
            } catch (e) {
              throw this._showTotalRow = n, e
            } finally {
              t = null, this._owner.endUpdate()
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "showBandedColumns", {
          get: function() {
            return this._showBandedColumns
          },
          set: function(e) {
            var t;
            e !== this._showBandedColumns && (this._owner && !this._owner._isUndoing && this._owner.undoStack.stackSize > 0 && (t = new o._TableSettingAction(this._owner, this)), this._showBandedColumns = e, this._owner && (t && (t.saveNewState(), this._owner.undoStack._addAction(t), t = null), this._owner.refresh()))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "showBandedRows", {
          get: function() {
            return this._showBandedRows
          },
          set: function(e) {
            var t;
            e !== this._showBandedRows && (this._owner && !this._owner._isUndoing && this._owner.undoStack.stackSize > 0 && (t = new o._TableSettingAction(this._owner, this)), this._showBandedRows = e, this._owner && (t && (t.saveNewState(), this._owner.undoStack._addAction(t), t = null), this._owner.refresh()))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "alterFirstColumn", {
          get: function() {
            return this._alterFirstColumn
          },
          set: function(e) {
            var t;
            e !== this._alterFirstColumn && (this._owner && !this._owner._isUndoing && this._owner.undoStack.stackSize > 0 && (t = new o._TableSettingAction(this._owner, this)), this._alterFirstColumn = e, this._owner && (t && (t.saveNewState(), this._owner.undoStack._addAction(t), t = null), this._owner.refresh()))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "alterLastColumn", {
          get: function() {
            return this._alterLastColumn
          },
          set: function(e) {
            var t;
            e !== this._alterLastColumn && (this._owner && !this._owner._isUndoing && this._owner.undoStack.stackSize > 0 && (t = new o._TableSettingAction(this._owner, this)), this._alterLastColumn = e, this._owner && (t && (t.saveNewState(), this._owner.undoStack._addAction(t), t = null), this._owner.refresh()))
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.getRange = function(e, t) {
          void 0 === e && (e = s.All);
          var o;
          switch (e) {
            case s.Data:
              o = this._getDataRange();
              break;
            case s.Header:
              o = this._getHeaderRange();
              break;
            case s.Footer:
              o = this._getFooterRange();
              break;
            default:
              o = this._range.clone()
          }
          if (null != t && o) {
            var n = this._getColumnIndex(t);
            if (null == n) return null;
            o.col = o.col2 = o.leftCol + n
          }
          return o
        }, n.prototype.getColumns = function() {
          return this._columns ? this._columns.slice() : []
        }, n.prototype.insertRows = function(o, n, i) {
          void 0 === i && (i = !0);
          var l, s, r, a, h, c = this.getRange(),
            d = !1;
          if (null == n && (n = 1), !e.isInt(n) || n < 1) return !1;
          if (0 === o && this._showHeaderRow || o === this._range.rowSpan - 1 && this._showTotalRow || o < 0 || o >= this._range.rowSpan) return !1;
          if (this._owner) {
            if (this._owner.beginUpdate(), a = this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid, i) {
              if (r = new t.CellRange(c.bottomRow + 1, c.col, c.bottomRow + 1, c.col2), this._sheet._canShiftCells(r)) {
                if (l = this._sheet._needAddRowCountForInsertTableRows(n, c), a.collectionView) {
                  for (h = a.itemsSource instanceof e.collections.CollectionView, a.collectionView.beginUpdate(), s = 0; s < l; s++) h ? a.itemsSource.sourceCollection.push({}) : a.itemsSource.push({});
                  a.collectionView.endUpdate()
                } else
                  for (s = 0; s < l; s++) a.rows.push(new t.Row);
                this._sheet._moveDownCells(n, r), d = !0
              }
            } else this._canInsertRowsWithoutShift(n) && (d = !0);
            d && (this._sheet._moveDownCellsWithinTable(o, n, c), this._updateTableRange(0, n, 0, 0)), this._owner.endUpdate()
          } else this._updateTableRange(0, n, 0, 0), d = !0;
          return d
        }, n.prototype.deleteRows = function(o, n, i) {
          void 0 === i && (i = !0);
          var l, s = !1,
            r = this.getRange();
          null == n && (n = 1), !e.isInt(n) || n < 1 || 0 === o && this._showHeaderRow || o === this._range.rowSpan - 1 && this._showTotalRow || o < 0 || o >= this._range.rowSpan || o + n > this._range.rowSpan - (this._showTotalRow ? 1 : 0) || (this._owner ? (this._owner.beginUpdate(), i ? (l = new t.CellRange(r.bottomRow + 1, r.col, r.bottomRow + 1, r.col2), this._sheet._canShiftCells(l) && (s = !0)) : s = !0, s && (this._sheet._moveUpCellsWithinTable(o, n, r), i && this._sheet._moveUpCells(n, l), this._updateTableRange(0, -n, 0, 0)), this._owner.endUpdate()) : this._updateTableRange(0, -n, 0, 0))
        }, n.prototype._addColumn = function(e, t) {
          var o, n = this.getRange();
          if (e <= 0 || e > this._columns.length) throw "The column index is out of range.";
          t = this._getUniqueColumnName(e, t), (o = new i(t))._attach(this), this._columns.splice(e, 0, o), this.showHeaderRow && this._owner && this._sheet && (this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid).setCellData(n.topRow, n.leftCol + e, t)
        }, n.prototype._updateCell = function(e, t, o) {
          var n, i = e - this._range.topRow,
            l = t - this._range.leftCol;
          null != this._style && (n = this._getTableCellAppliedStyles(i, l), this._applyStylesForCell(n, o))
        }, n.prototype._updateTableRange = function(e, t, o, n) {
          this._range.row <= this._range.row2 ? (this._range.row += e, this._range.row2 += t) : (this._range.row2 += e, this._range.row += t), this._range.col <= this._range.col2 ? (this._range.col += o, this._range.col2 += n) : (this._range.col2 += o, this._range.col += n)
        }, n.prototype._setTableRange = function(e, t) {
          var o;
          if (this._range = e, null != t)
            for (this._columns.splice(0, this._columns.length), o = 0; o < t.length; o++) this._columns.push(t[o])
        }, n.prototype._updateColumnName = function(e, t) {
          var o = this._columns[e];
          t = this._getUniqueColumnName(null, t), o.name = t, this._showHeaderRow && this._owner && this._sheet && (this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid).setCellData(this._range.topRow, this._range.leftCol + e, t)
        }, n.prototype._updateColumnTotalRowContent = function(e, t) {
          var o, n;
          null == t && (t = this._columns.indexOf(e)), t < 0 || (e.totalRowFunction ? n = null != (o = this._getSubtotalFunction(e.totalRowFunction.toLowerCase())) ? "=subtotal(" + o + ",[" + e.name + "])" : "=" === e.totalRowFunction[0] ? e.totalRowFunction : "=" + e.totalRowFunction : 0 === t ? (e.totalRowLabel || (e.totalRowLabel = "Total"), n = e.totalRowLabel) : n = e.totalRowLabel, this._owner && this._sheet && (this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid).setCellData(this._range.bottomRow, this._range.leftCol + t, n, !1))
        }, n.prototype._attachSheet = function(e) {
          var t = this.getRange();
          if (!(this._sheet && this._owner || (this._sheet = e, this._owner = e._owner, null == this._owner))) {
            if (this._owner._containsMergedCells(this._range, e)) throw "Table does not allow the merged cell within the table.";
            null == this._style && (this._style = this._owner.getBuiltInTableStyle("TableStyleMedium9")), null != this._columns && 0 !== this._columns.length || this._generateColumns(this._showHeaderRow);
            try {
              this._owner.beginUpdate(), this._showHeaderRow && 1 === t.rowSpan && this._adjustTableRangeWithHeaderRow(), this._showTotalRow && this._updateTotalRow()
            } finally {
              this._owner.endUpdate()
            }
          }
        }, n.prototype._detachSheet = function() {
          this._sheet = null, this._owner = null
        }, n.prototype._pushTableColumns = function(e) {
          var t, o;
          for (this._columns = [], o = 0; o < e.length; o++)(t = e[o])._attach(this), this._columns.push(t)
        }, n.prototype._generateColumns = function(t) {
          var o, n, l, s, r, a = this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid;
          if (!this._range.isValid) throw "The range of the table is invalid.";
          this._columns = [];
          for (var h = 0; h < this._range.columnSpan; h++) s = this._sheet.grid.columns[this._range.leftCol + h], r = this._range.topRow * this._sheet.grid.columns.length + this._range.leftCol + h, 1 === this._range.rowSpan ? o = this._getUniqueColumnName(h + 1) : (null == (o = this._sheet.grid.getCellData(this._range.topRow, this._range.leftCol + h, !1)) && (o = ""), e.isString(o) && "=" === o[0] && (o = this._owner.evaluate(o, "", this._sheet, !1)), l = this._sheet._styledCells[r], o = this._owner._formatEvaluatedResult(o, s, l ? l.format : ""), o = this._getUniqueColumnName(h + 1, o.toString())), t && this._range.rowSpan > 1 && (this._orgHeaderCellsContent[h] = this._sheet.grid.getCellData(this._range.topRow, this._range.leftCol + h, !1), isNaN(+o) || s.format || l && l.format || (l ? l.format = "@" : this._sheet._styledCells[r] = {
            format: "@"
          }), a.setCellData(this._range.topRow, this._range.leftCol + h, o, !1)), (n = new i(o))._attach(this), 0 === h ? n._totalRowLabel = "Total" : h === this._range.columnSpan - 1 && (n._totalRowFunction = "Sum"), this._columns.push(n)
        }, n.prototype._getTableCellAppliedStyles = function(e, t) {
          var o, n = {},
            i = !1,
            l = !1,
            s = 0;
          return this._showHeaderRow && (s = 1), this._showHeaderRow && 0 === e ? i = !0 : this._showTotalRow && e === this._range.rowSpan - 1 && (l = !0), this._extendStyle(n, this._style.wholeTableStyle, e, t, i, l), i || l || (!this._showBandedColumns || null == this._style.firstBandedColumnStyle && null == this._style.secondBandedColumnStyle || (t % ((o = this._style.firstBandedColumnStyle ? null == this._style.firstBandedColumnStyle.size ? 1 : this._style.firstBandedColumnStyle.size : 1) + (this._style.secondBandedColumnStyle ? null == this._style.secondBandedColumnStyle.size ? 1 : this._style.secondBandedColumnStyle.size : 1)) >= o ? this._style.secondBandedColumnStyle && this._extendStyle(n, this._style.secondBandedRowStyle, e, t, i, l) : this._style.firstBandedColumnStyle && this._extendStyle(n, this._style.firstBandedColumnStyle, e, t, i, l)), !this._showBandedRows || null == this._style.firstBandedRowStyle && null == this._style.secondBandedRowStyle || ((e - s) % ((o = this._style.firstBandedRowStyle ? null == this._style.firstBandedRowStyle.size ? 1 : this._style.firstBandedRowStyle.size : 1) + (this._style.secondBandedRowStyle ? null == this._style.secondBandedRowStyle.size ? 1 : this._style.secondBandedRowStyle.size : 1)) >= o ? this._style.secondBandedRowStyle && this._extendStyle(n, this._style.secondBandedRowStyle, e, t, i, l) : this._style.firstBandedRowStyle && this._extendStyle(n, this._style.firstBandedRowStyle, e, t, i, l))), this._alterLastColumn && t === this._range.columnSpan - 1 && this._style.lastColumnStyle && this._extendStyle(n, this._style.lastColumnStyle, e, t, i, l), this._alterFirstColumn && 0 === t && this._style.firstColumnStyle && this._extendStyle(n, this._style.firstColumnStyle, e, t, i, l), i ? (this._style.headerRowStyle && this._extendStyle(n, this._style.headerRowStyle, e, t, i, l), t === this._range.columnSpan - 1 && this._style.lastHeaderCellStyle && this._extendStyle(n, this._style.lastHeaderCellStyle, e, t, i, l), 0 === t && this._style.firstHeaderCellStyle && this._extendStyle(n, this._style.firstHeaderCellStyle, e, t, i, l)) : l && (this._style.totalRowStyle && this._extendStyle(n, this._style.totalRowStyle, e, t, i, l), t === this._range.columnSpan - 1 && this._style.lastTotalCellStyle && this._extendStyle(n, this._style.lastTotalCellStyle, e, t, i, l), 0 === t && this._style.firstTotalCellStyle && this._extendStyle(n, this._style.firstTotalCellStyle, e, t, i, l)), n
        }, n.prototype._applyStylesForCell = function(e, t) {
          var o, n = t.style;
          for (var i in e)(o = e[i]) && (n[i] = o)
        }, n.prototype._extendStyle = function(e, t, o, n, i, l) {
          var s;
          for (var r in t) null !== (s = t[r]) && (r.indexOf("borderTop") > -1 ? (0 === o || l) && (e[r] = s) : r.indexOf("borderBottom") > -1 ? (o === this._range.rowSpan - 1 || i) && (e[r] = s) : r.indexOf("borderLeft") > -1 ? 0 === n && (e[r] = s) : r.indexOf("borderRight") > -1 ? n === this._range.columnSpan - 1 && (e[r] = s) : r.indexOf("borderHorizontal") > -1 ? o < this._range.rowSpan - 1 && ("borderHorizontalStyle" === r ? e.borderBottomStyle = s : "borderHorizontalWidth" === r ? e.borderBottomWidth = s : e.borderBottomColor = s) : r.indexOf("borderVertical") > -1 ? n < this._range.columnSpan - 1 && ("borderVerticalStyle" === r ? e.borderRightStyle = s : "borderVerticalWidth" === r ? e.borderRightWidth = s : e.borderRightColor = s) : e[r] = s)
        }, n.prototype._getSubtotalFunction = function(e) {
          switch (e) {
            case "average":
              return "101";
            case "countnums":
              return "102";
            case "count":
              return "103";
            case "max":
              return "104";
            case "min":
              return "105";
            case "stddev":
              return "107";
            case "sum":
              return "109";
            case "var":
              return "110"
          }
          return null
        }, n.prototype._checkColumnNameExist = function(e) {
          var t, o = this.getColumns();
          for (t = 0; t < o.length; t++)
            if (o[t].name.toLowerCase() === e.toLowerCase()) return !0;
          return !1
        }, n.prototype._adjustTableRangeWithHeaderRow = function() {
          var o, n, i, l, s = this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid;
          if (this._showHeaderRow) {
            if (this._sheet._needMoveDownTable(this)) {
              if (i = this.getRange(), l = new t.CellRange(i.bottomRow + 1, i.col, i.bottomRow + 1, i.col2), !this._sheet._canShiftCells(l)) throw "The operation is not allowed.  The operation is attempting to shift the cells in a table or a merged cell on the current sheet.";
              1 === this._sheet._needAddRowCountForInsertTableRows(1, i) && (s.collectionView ? (s.collectionView.beginUpdate(), s.itemsSource instanceof e.collections.CollectionView ? s.itemsSource.sourceCollection.push({}) : s.itemsSource.push({}), s.collectionView.endUpdate()) : s.rows.push(new t.Row)), this._sheet._moveDownCells(1, l), this._sheet._moveDownTable(this)
            }
            for (this._range.row <= this._range.row2 ? this._range.row -= 1 : this._range.row2 -= 1, o = 0; o < this._columns.length; o++) n = this._columns[o], this._orgHeaderCellsContent[o] = s.getCellData(this._range.topRow, this._range.leftCol + o, !1), s.setCellData(this._range.topRow, this._range.leftCol + o, n.name)
          } else {
            for (o = this._range.leftCol; o <= this._range.rightCol; o++) s.setCellData(this._range.topRow, o, "");
            this._range.row <= this._range.row2 ? this._range.row += 1 : this._range.row2 += 1
          }
        }, n.prototype._adjustTableRangeWithTotalRow = function() {
          var o, n, i, l = this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid;
          if (n = this.getRange(), i = new t.CellRange(n.bottomRow + 1, n.col, n.bottomRow + 1, n.col2), this._showTotalRow) {
            if (this._sheet._canShiftCells(i)) 1 === this._sheet._needAddRowCountForInsertTableRows(1, n) && (l.collectionView ? (l.collectionView.beginUpdate(), l.itemsSource instanceof e.collections.CollectionView ? l.itemsSource.sourceCollection.push({}) : l.itemsSource.push({}), l.collectionView.endUpdate()) : l.rows.push(new t.Row)), this._sheet._moveDownCells(1, i), this._owner._updateAffectedFormula(this._range.bottomRow + 1, 1, !0, !0, this.getRange());
            else if (!this._beneathRowIsEmpty()) throw "The operation is not allowed.  The operation is attempting to shift the cells in a table or a merged cell on the current sheet.";
            this._range.row <= this._range.row2 ? this._range.row2 += 1 : this._range.row += 1, this._updateTotalRow()
          } else {
            for (o = this._range.leftCol; o <= this._range.rightCol; o++) l.setCellData(this._range.bottomRow, o, "");
            this._sheet._canShiftCells(i) && (this._owner._updateAffectedFormula(this._range.bottomRow, 1, !1, !0, this.getRange()), this._sheet._moveUpCells(1, i)), this._range.row <= this._range.row2 ? this._range.row2 -= 1 : this._range.row -= 1
          }
        }, n.prototype._updateTotalRow = function() {
          var e;
          if (this.showTotalRow)
            for (var t = 0; t < this._columns.length; t++) e = this._columns[t], this._updateColumnTotalRowContent(e, t)
        }, n.prototype._getUniqueColumnName = function(e, t) {
          var o, n = 1;
          if (null != t && "" !== t || (t = "Column" + e), this._checkColumnNameExist(t)) {
            for (o = t + n; this._checkColumnNameExist(o);) o = t + ++n;
            t = o
          }
          return t
        }, n.prototype._moveColumns = function(e, t) {
          if (this._columns) {
            var o = this._columns[e];
            this._columns.splice(e, 1), t < 0 && (t = this._columns.length), this._columns.splice(t, 0, o)
          }
        }, n.prototype._canInsertRowsWithoutShift = function(e) {
          var t, o, n, i, l, s = this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid;
          if (this._range.bottomRow + e >= s.rows.length) return !1;
          for (t = 1; t <= e; t++)
            for (o = this._range.bottomRow + t, n = this._range.leftCol; n <= this._range.rightCol; n++)
              if (i = s.getCellData(o, n, !1), l = this._sheet.getCellStyle(o, n), null != i && "" !== i || null != l) return !1;
          return !0
        }, n.prototype._beneathRowIsEmpty = function() {
          var e, t, o, n = this._range.bottomRow + 1,
            i = this._sheet === this._owner.selectedSheet ? this._owner : this._sheet.grid;
          if (n >= i.rows.length) return !1;
          for (e = this._range.leftCol; e <= this._range.rightCol; e++) {
            if (null != (t = i.getCellData(this._range.bottomRow + 1, e, !1)) && "" !== t) return !1;
            if (null != this._sheet.getCellStyle(this._range.bottomRow + 1, e)) return !1;
            if (null != this._sheet.findTable(n, e)) return null;
            if (o = n * i.columns.length + e, null != this._sheet._mergedRanges[o]) return !1
          }
          return !0
        }, n.prototype._getDataRange = function() {
          var e = this._range.topRow,
            o = this._range.bottomRow;
          return this._showHeaderRow && (e += 1), this._showTotalRow && (o -= 1), new t.CellRange(e, this._range.leftCol, o, this._range.rightCol)
        }, n.prototype._getHeaderRange = function() {
          return this._showHeaderRow ? new t.CellRange(this._range.topRow, this._range.leftCol, this._range.topRow, this._range.rightCol) : null
        }, n.prototype._getFooterRange = function() {
          return this._showTotalRow ? new t.CellRange(this._range.bottomRow, this._range.leftCol, this._range.bottomRow, this._range.rightCol) : null
        }, n.prototype._getColumnIndex = function(t) {
          var o, n, l;
          if (e.isInt(t) && t >= 0 && t < this._columns.length) o = t;
          else {
            t instanceof i ? n = t.name.toLowerCase() : e.isString(t) && "" !== t && (n = t.toLowerCase()), l = this.getColumns();
            for (var s = 0; s < l.length; s++)
              if (l[s].name.toLowerCase() === n) {
                o = s;
                break
              }
          }
          return o
        }, n
      }();
      o.Table = n;
      var i = function() {
        function e(e, t, o, n) {
          void 0 === n && (n = !0), this._name = e, this._totalRowLabel = t, this._totalRowFunction = o, null != n && (this._showFilterButton = n)
        }
        return Object.defineProperty(e.prototype, "table", {
          get: function() {
            return this._table
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "name", {
          get: function() {
            return this._name
          },
          set: function(e) {
            e !== this._name && (this._name = e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "totalRowLabel", {
          get: function() {
            return this._totalRowLabel
          },
          set: function(e) {
            e !== this._totalRowLabel && (this._totalRowLabel = e, this._updateTableTotalInfo())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "totalRowFunction", {
          get: function() {
            return this._totalRowFunction
          },
          set: function(e) {
            e !== this._totalRowFunction && (this._totalRowFunction = e, this._updateTableTotalInfo())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "showFilterButton", {
          get: function() {
            return this._showFilterButton
          },
          set: function(e) {
            this._showFilterButton !== e && (this._showFilterButton = e)
          },
          enumerable: !0,
          configurable: !0
        }), e.prototype._attach = function(e) {
          this._table = e
        }, e.prototype._updateTableTotalInfo = function() {
          this._table && this._table.showTotalRow && (this._table._updateColumnTotalRowContent(this), this._table._owner && this._table._owner.refresh())
        }, e
      }();
      o.TableColumn = i;
      var l = function() {
        function e(e, t) {
          void 0 === t && (t = !1), this._name = e, this._isBuiltIn = t
        }
        return Object.defineProperty(e.prototype, "name", {
          get: function() {
            return this._name
          },
          set: function(e) {
            e !== this._name && (this._name = e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "wholeTableStyle", {
          get: function() {
            return this._wholeTableStyle
          },
          set: function(e) {
            this._wholeTableStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "firstBandedColumnStyle", {
          get: function() {
            return this._firstBandedColumnStyle
          },
          set: function(e) {
            this._firstBandedColumnStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "secondBandedColumnStyle", {
          get: function() {
            return this._secondBandedColumnStyle
          },
          set: function(e) {
            this._secondBandedColumnStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "firstBandedRowStyle", {
          get: function() {
            return this._firstBandedRowStyle
          },
          set: function(e) {
            this._firstBandedRowStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "secondBandedRowStyle", {
          get: function() {
            return this._secondBandedRowStyle
          },
          set: function(e) {
            this._secondBandedRowStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "firstColumnStyle", {
          get: function() {
            return this._firstColumnStyle
          },
          set: function(e) {
            this._firstColumnStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "lastColumnStyle", {
          get: function() {
            return this._lastColumnStyle
          },
          set: function(e) {
            this._lastColumnStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "headerRowStyle", {
          get: function() {
            return this._headerRowStyle
          },
          set: function(e) {
            this._headerRowStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "totalRowStyle", {
          get: function() {
            return this._totalRowStyle
          },
          set: function(e) {
            this._totalRowStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "firstHeaderCellStyle", {
          get: function() {
            return this._firstHeaderCellStyle
          },
          set: function(e) {
            this._firstHeaderCellStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "lastHeaderCellStyle", {
          get: function() {
            return this._lastHeaderCellStyle
          },
          set: function(e) {
            this._lastHeaderCellStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "firstTotalCellStyle", {
          get: function() {
            return this._firstTotalCellStyle
          },
          set: function(e) {
            this._firstTotalCellStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "lastTotalCellStyle", {
          get: function() {
            return this._lastTotalCellStyle
          },
          set: function(e) {
            this._lastTotalCellStyle = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "isBuiltIn", {
          get: function() {
            return this._isBuiltIn
          },
          enumerable: !0,
          configurable: !0
        }), e
      }();
      o.TableStyle = l;
      var s;
      ! function(e) {
        e[e.All = 0] = "All", e[e.Data = 1] = "Data", e[e.Header = 2] = "Header", e[e.Footer = 3] = "Footer"
      }(s = o.TableSection || (o.TableSection = {}))
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(e) {
        function n(t) {
          return e.call(this, t) || this
        }
        return __extends(n, e), n.prototype.apply = function(e) {
          var n, i = this.column.grid;
          return !(i instanceof o.FlexSheet) || (!this.showValues || !Object.keys(this.showValues).length || (i.rows[e] instanceof t._NewRowTemplate || (n = this.dataMap || this.column.dataMap, e = n ? n.getDisplayValue(i.getCellValue(e, this.column.index)) : i.getCellValue(e, this.column.index, !0), void 0 != this.showValues[e])))
        }, n
      }(e.grid.filter.ValueFilter);
      o.FlexSheetValueFilter = n
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(e) {
        function n() {
          return null !== e && e.apply(this, arguments) || this
        }
        return __extends(n, e), n.prototype.updateEditor = function() {
          var n, i, l, s, r, a, h, c, d, u = this.filter.column,
            _ = u.grid,
            g = u.index,
            f = [],
            p = {};
          if (this.filter.uniqueValues || null != _.itemsSource && null != _.childItemsPath) e.prototype.updateEditor.call(this);
          else {
            for (C = 0; C < _.rows.length; C++) r = this.filter.apply(C), s = this.filter.showValues, this.filter.showValues = null, a = _.filter._filter(C), this.filter.showValues = s, (!(i = _.getMergedRange(_.cells, C, g)) || C === i.topRow && g === i.leftCol) && ((n = _.rows[C]) instanceof o.HeaderRow || n instanceof t.GroupRow || n instanceof t._NewRowTemplate || !(n.visible || !r && a) || (l = _.getCellValue(C, g), h = this.filter.dataMap ? this.filter.dataMap.getDisplayValue(l) : _.getCellValue(C, g, !0), l = "" === l ? null : l, c = _.getCellData(C, g, !0), p[h] || (p[h] = !0, d = c && "=" === c[0] ? C + "_" + g : "", f.push({
              value: l,
              text: h,
              cellRef: d
            }))));
            var w = this.filter.showValues;
            if (w && 0 != Object.keys(w).length) {
              for (var m in w)
                for (C = 0; C < f.length; C++)
                  if (w[m] && "" !== f[C].cellRef && f[C].cellRef === w[m].cellRef) w[f[C].text] = {
                    show: !0,
                    cellRef: f[C].cellRef
                  }, f[C].show = !0, delete w[m];
                  else if (f[C].text == m) {
                    f[C].show = !0;
                    break
                  }
            } else
              for (var C = 0; C < f.length; C++) f[C].show = !0;
            this._lbValues.isContentHtml = u.isContentHtml, this._cmbFilter.text = this.filter.filterText, this._filterText = this._cmbFilter.text.toLowerCase(), this._view.pageSize = this.filter.maxValues, this._view.sourceCollection = f, this._view.moveCurrentToPosition(-1)
          }
        }, n.prototype.updateFilter = function() {
          var e = null,
            t = this._view.items;
          if (this._filterText || this._cbSelectAll.indeterminate) {
            e = {};
            for (var o = 0; o < t.length; o++) {
              var n = t[o];
              n.show && (e[n.text] = {
                show: !0,
                cellRef: n.cellRef
              })
            }
          }
          this._filter.showValues = e, this._filter.filterText = this._filterText
        }, n
      }(e.grid.filter.ValueFilterEditor);
      o.FlexSheetValueFilterEditor = n
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(n) {
        function i(e) {
          return n.call(this, e) || this
        }
        return __extends(i, n), i.prototype.apply = function(n) {
          var i, l, s, r, a = this.column,
            h = a.grid,
            c = this.condition1,
            d = this.condition2;
          if (!(h instanceof o.FlexSheet)) return !0;
          if (!this.isActive) return !0;
          if (h.rows[n] instanceof t._NewRowTemplate) return !0;
          "" === (i = h.getCellValue(n, a.index)) && a.dataType !== e.DataType.String && (i = null), l = s = i, (r = this.dataMap || a.dataMap) ? l = s = i = r.getDisplayValue(i) : e.isDate(i) ? (e.isString(c.value) || e.isString(d.value)) && (l = s = i = h.getCellValue(n, a.index, !0)) : e.isNumber(i) ? (l = s = i = e.Globalize.parseFloat(h.getCellValue(n, a.index, !0)), 0 !== i || a.dataType || (c.isActive && "" === c.value && (l = i.toString()), d.isActive && "" === d.value && (s = s.toString()))) : null == i && (c.isActive && e.isNumber(c.value) && (l = NaN), d.isActive && e.isNumber(d.value) && (s = NaN));
          var u = c.apply(l),
            _ = d.apply(s);
          return c.isActive && d.isActive ? this.and ? u && _ : u || _ : c.isActive ? u : !d.isActive || _
        }, i
      }(e.grid.filter.ConditionFilter);
      o.FlexSheetConditionFilter = n
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(t) {
      "use strict";
      var o = function(e) {
        function o(o, n) {
          var i = e.call(this, o, n) || this;
          return i._valueFilter = new t.FlexSheetValueFilter(n), i._conditionFilter = new t.FlexSheetConditionFilter(n), i
        }
        return __extends(o, e), o
      }(e.grid.filter.ColumnFilter);
      t.FlexSheetColumnFilter = o
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(t) {
      "use strict";
      var o = function(o) {
        function n(e, t, n) {
          void 0 === n && (n = !0);
          var i, l, s = o.call(this, e, t, n) || this,
            r = s;
          return n && (s._divSort.style.display = ""), i = s.cloneElement(s._btnAsc), l = s.cloneElement(s._btnDsc), s._btnAsc.parentNode.replaceChild(i, s._btnAsc), s._btnDsc.parentNode.replaceChild(l, s._btnDsc), i.addEventListener("click", function(e) {
            r._sortBtnClick(e, !0)
          }), l.addEventListener("click", function(e) {
            r._sortBtnClick(e, !1)
          }), s
        }
        return __extends(n, o), n.prototype._showFilter = function(n) {
          n == e.grid.filter.FilterType.Value && null == this._edtVal && (this._edtVal = new t.FlexSheetValueFilterEditor(this._divEdtVal, this.filter.valueFilter)), o.prototype._showFilter.call(this, n)
        }, n.prototype._sortBtnClick = function(e, t) {
          var o, n, i = this.filter.column,
            l = i.grid.sortManager;
          e.preventDefault(), e.stopPropagation(), (o = l.checkSortItemExists(i.index)) > -1 ? (l.sortDescriptions.moveCurrentToPosition(o), l.sortDescriptions.currentItem.ascending = t, n = -o) : (l.addSortLevel(i.index, t), n = -(l.sortDescriptions.items.length - 1)), l.moveSortLevel(n), l.commitSort(), this.onButtonClicked()
        }, n.prototype.cloneElement = function(e) {
          for (var t = e.cloneNode(); e.firstChild;) t.appendChild(e.lastChild);
          return t
        }, n
      }(e.grid.filter.ColumnFilterEditor);
      t.FlexSheetColumnFilterEditor = o
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(n) {
        function i() {
          return null !== n && n.apply(this, arguments) || this
        }
        return __extends(i, n), Object.defineProperty(i.prototype, "filterDefinition", {
          get: function() {
            for (var e = {
              filters: []
            }, t = 0; t < this._filters.length; t++) {
              var o = this._filters[t];
              if (o && o.column && this.grid.columns.indexOf(o.column) > -1)
                if (o.conditionFilter.isActive) {
                  var n = o.conditionFilter;
                  e.filters.push({
                    columnIndex: o.column.index,
                    type: "condition",
                    condition1: {
                      operator: n.condition1.operator,
                      value: n.condition1.value
                    },
                    and: n.and,
                    condition2: {
                      operator: n.condition2.operator,
                      value: n.condition2.value
                    }
                  })
                } else if (o.valueFilter.isActive) {
                  var i = o.valueFilter;
                  e.filters.push({
                    columnIndex: o.column.index,
                    type: "value",
                    filterText: i.filterText,
                    showValues: i.showValues
                  })
                }
            }
            return JSON.stringify(e)
          },
          set: function(t) {
            var o = JSON.parse(e.asString(t));
            this.clear();
            for (var n = 0; n < o.filters.length; n++) {
              var i = o.filters[n],
                l = this.grid.columns[i.columnIndex],
                s = this.getColumnFilter(l, !0);
              if (s) switch (i.type) {
                case "condition":
                  var r = s.conditionFilter;
                  r.condition1.value = l.dataType == e.DataType.Date ? e.changeType(i.condition1.value, l.dataType, null) : i.condition1.value, r.condition1.operator = i.condition1.operator, r.and = i.and, r.condition2.value = l.dataType == e.DataType.Date ? e.changeType(i.condition2.value, l.dataType, null) : i.condition2.value, r.condition2.operator = i.condition2.operator;
                  break;
                case "value":
                  var a = s.valueFilter;
                  a.filterText = i.filterText, a.showValues = i.showValues
              }
            }
            this.apply()
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype.apply = function() {
          var n = this;
          n.grid.deferUpdate(function() {
            for (var i, l, s, r = -1, a = 0; a < n.grid.rows.length; a++)
              if (!((i = n.grid.rows[a]) instanceof o.HeaderRow || a <= r))
                if (r = -1, l = n._filter(a), i instanceof t.GroupRow) {
                  if (s = i.getCellRange(), null == i.dataItem || i.dataItem instanceof e.collections.CollectionViewGroup) i.visible = n._checkGroupVisible(s);
                  else if (i.visible = l, r = s.bottomRow, s.isValid)
                    for (var h = s.topRow; h <= s.bottomRow; h++) n.grid.rows[h].visible = l
                } else i.visible = l;
            n.grid._isCopying || n.grid._isUndoing || n.grid._isSorting || n.onFilterApplied()
          })
        }, i.prototype.editColumnFilter = function(n, i) {
          var l = this,
            s = l.grid;
          l.closeEditor(), n = l._asColumn(n);
          var r = document.createElement("div"),
            a = l.getColumnFilter(n),
            h = new o.FlexSheetColumnFilterEditor(r, a, l.showSortButtons);
          e.addClass(r, "wj-dropdown-panel"), l._divEdt = r, l._edtCol = n;
          var c = new t.CellRangeEventArgs(s.cells, new t.CellRange(-1, n.index));
          if (l.onFilterChanging(c), c.cancel) return l._divEdt = null, void(l._edtCol = null);
          c.cancel = !0, s.undoStack.stackSize > 0 && (l._undoAcion = new o._FilteringAction(s)), s.rightToLeft && (r.dir = "rtl"), h.filterChanged.addHandler(function() {
            c.cancel = !1, l._filterChanged = !0, setTimeout(function() {
              c.cancel || (l.apply(), l._undoAcion && (l._undoAcion.saveNewState(), s.undoStack._addAction(l._undoAcion), l._undoAcion = null))
            })
          }), h.buttonClicked.addHandler(function() {
            l.closeEditor(), s.focus(), l.onFilterChanged(c)
          }), h.lostFocus.addHandler(function() {
            setTimeout(function() {
              var t = e.Control.getControl(l._divEdt);
              t && !t.containsFocus() && l.closeEditor()
            }, 10)
          }), s._edtHdl._commitRowEdits(), s.scrollIntoView(-1, n.index, !0);
          var d = l.grid.columnHeaders,
            u = i ? i.row : d.rows.length - 1,
            _ = i ? i.col : n.index,
            g = d.getCellBoundingRect(u, _),
            f = document.elementFromPoint(g.left + g.width / 2, g.top + g.height / 2);
          (f = e.closest(f, ".wj-cell")) ? e.showPopup(r, f, !1, !1, !1): e.showPopup(r, g), l._setAriaExpanded(f, !0), l._setAriaExpanded(s.cells.getCellElement(-1, _), !0);
          for (var p = h.hostElement.querySelectorAll("input"), w = 0; w < p.length; w++) {
            var m = p[w];
            if (m.offsetHeight > 0 && m.tabIndex > -1 && !m.disabled) {
              m.focus();
              break
            }
          }
          h.containsFocus() || h.focus()
        }, i.prototype.closeEditor = function() {
          this._undoAcion && !this._filterChanged && (this._undoAcion = null), n.prototype.closeEditor.call(this)
        }, i.prototype.getColumnFilter = function(n, i) {
          if (void 0 === i && (i = !0), e.isString(n) ? n = this.grid.columns.getColumn(n) : e.isNumber(n) && (n = this.grid.columns[n]), !n) return null;
          n = e.asType(n, t.Column);
          for (var l = 0; l < this._filters.length; l++)
            if (this._filters[l].column == n) return this._filters[l];
          if (i) {
            var s = new o.FlexSheetColumnFilter(this, n);
            return this._filters.push(s), s
          }
          return null
        }, i.prototype._checkGroupVisible = function(e) {
          for (var o, n = !0, i = e.topRow + 1; i <= e.bottomRow; i++)
            if (o = this.grid.rows[i])
              if (o instanceof t.GroupRow) n = this._checkGroupVisible(o.getCellRange());
              else if (n = this._filter(i)) break;
          return n
        }, i
      }(e.grid.filter.FlexGridFilter);
      o.FlexSheetFilter = n
    }(t.sheet || (t.sheet = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
