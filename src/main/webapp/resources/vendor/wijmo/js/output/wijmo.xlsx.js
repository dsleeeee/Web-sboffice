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
var JSZip = window.JSZip;
void 0 !== JSZip && JSZip || "function" != typeof window.require || (JSZip = window.require("node-zip"));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    t.useJSZip = function(e) {
      JSZip = e
    };
    var s = function() {
      function s() {}
      return s.load = function(t) {
        var s = new JSZip;
        e.assert(null == s.loadAsync, "Please use JSZip 2.5 to load excel files synchronously.");
        var l, r = new i(s);
        return this._loadImpl(r, t).then(function(e) {
          return l = e
        }), l
      }, s.loadAsync = function(t) {
        var s = new JSZip;
        return e.assert(null != s.loadAsync, "Please use JSZip 3.0 to load excel files asynchrounously."), this._loadImpl(s, t)
      }, s.save = function(e) {
        var t = Date.now(),
          s = this._saveWorkbookToZip(e);
        t = Date.now() - t;
        var l = "";
        l = this._macroEnabled ? "application/vnd.ms-excel.sheet.macroEnabled.12;" : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;";
        var i = Date.now(),
          r = s.generate({
            compression: "DEFLATE"
          });
        return {
          base64: r,
          zipTime: Date.now() - i,
          processTime: t,
          href: function() {
            return "data:" + l + "base64," + r
          }
        }
      }, s.saveAsync = function(e, t) {
        var s = this._saveWorkbookToZip(e, !0).generateAsync({
          type: "base64",
          compression: "DEFLATE"
        });
        return t && s.catch(t), s
      }, s._loadImpl = function(e, t) {
        var s, l = this,
          i = Date.now(),
          r = {
            sheets: []
          },
          n = e.loadAsync(t, {
            base64: !0
          }).then(function(e) {
            return s = e
          });
        t = null;
        var o = n.constructor;
        return n = n.then(function() {
          var e = s.file("xl/theme/theme1.xml");
          if (e) return e.async("string").then(function(e) {
            l._getTheme(e), r.colorThemes = l._colorThemes
          }).then(function() {
            var e = o.resolve();
            return e = l._getZipStyle(s, e, r), e = l._getZipSharedString(s, e, r)
          });
          var t = o.resolve();
          return t = l._getZipStyle(s, t, r), t = l._getZipSharedString(s, t, r)
        }), n = n.then(function() {
          var e = s.file("xl/workbook.xml");
          if (e) return e.async("string").then(function(e) {
            l._getWorkbook(e, r)
          })
        }), n = n.then(function() {
          l._tables = null;
          var e = o.resolve();
          return s.folder("xl/tables").forEach(function(t, s) {
            null == l._tables && (l._tables = []), e = e.then(function() {
              return s.async("string").then(function(e) {
                var t = l._getTable(e);
                t.fileName = s.name.substring(s.name.lastIndexOf("/") + 1), l._tables.push(t)
              })
            })
          }), e
        }), n = n.then(function() {
          var e = s.file("docProps/core.xml");
          if (e) return e.async("string").then(function(e) {
            l._getCoreSetting(e, r)
          })
        }), n = n.then(function() {
          var e = s.file("xl/vbaProject.bin");
          if (e) return e.async("uint8array").then(function(e) {
            null == r.reservedContent && (r.reservedContent = {}), r.reservedContent.macros = e
          })
        }), (n = n.then(function() {
          var e = o.resolve();
          return s.folder("xl/worksheets").forEach(function(t, i) {
            if (t && -1 === t.indexOf("/")) {
              var n = l._getSheetIndex(i.name);
              isNaN(n) || (e = e.then(function() {
                return i.async("string").then(function(e) {
                  if (l._getSheet(e, n - 1, r), null != r.sheets[n - 1].tableRIds || null != r.sheets[n - 1].hyperlinkRIds) {
                    var t = s.file("xl/worksheets/_rels/sheet" + n + ".xml.rels");
                    if (t) return t.async("string").then(function(e) {
                      for (var t = e.split("<Relationship "), s = t.length; --s;) {
                        var i = t[s],
                          o = l._getAttr(i, "Id");
                        r.sheets[n - 1].tableRIds && -1 !== r.sheets[n - 1].tableRIds.indexOf(o) ? (null == r.sheets[n - 1].tables && (r.sheets[n - 1].tables = []), r.sheets[n - 1].tables.push(l._getSheetRelatedTable(i))) : r.sheets[n - 1].hyperlinkRIds && l._getSheetRelatedHyperlink(i, o, r.sheets[n - 1])
                      }
                    })
                  }
                })
              }))
            }
          }), e
        })).then(function() {
          return r.processTime = Date.now() - i, s = null, r
        })
      }, s._getZipStyle = function(e, t, s) {
        var l = this,
          i = e.file("xl/styles.xml");
        return i && (t = t.then(function() {
          return i.async("string").then(function(e) {
            l._getStyle(e), s.styles = l._styles, null != l._tableStyles && (s.tableStyles = l._tableStyles)
          })
        })), t
      }, s._getZipSharedString = function(e, t, s) {
        var l = this,
          i = e.file("xl/sharedStrings.xml");
        return i && t.then(function() {
          return i.async("string").then(function(e) {
            l._getSharedString(e)
          })
        }), t
      }, s._saveWorkbookToZip = function(t, s) {
        void 0 === s && (s = !1);
        Date.now();
        var l = new JSZip;
        s ? e.assert(null != l.generateAsync, "Please use JSZip 3.0 to save excel files asynchrounously.") : e.assert(null == l.generateAsync, "Please use JSZip 2.5 to save excel files synchronously."), l.folder("_rels").file(".rels", this._xmlDescription + this._generateRelsDoc());
        var i = l.folder("docProps"),
          r = l.folder("xl");
        this._colorThemes = t.colorThemes, r.folder("theme").file("theme1.xml", this._xmlDescription + this._generateThemeDoc()), this._macroEnabled = !(!t.reservedContent || !t.reservedContent.macros), this._macroEnabled && r.file("vbaProject.bin", t.reservedContent.macros);
        var n = r.folder("worksheets");
        i.file("core.xml", this._xmlDescription + this._generateCoreDoc(t)), this._sharedStrings = [
          [], 0
        ], this._styles = new Array(1), this._borders = new Array(1), this._fonts = new Array(1), this._fills = new Array(2), this._tableStyles = new Array, this._dxfs = new Array, this._contentTypes = [], this._props = [], this._xlRels = [], this._worksheets = [], this._tables = [], this._tableStyles = [];
        for (var o, a = t.sheets.length; a--;)
          if (this._generateWorkSheet(a, t, n), t.sheets[a] && (t.sheets[a].tables && t.sheets[a].tables.length > 0 || t.sheets[a].externalLinks && t.sheets[a].externalLinks.length > 0)) {
            var h = 0,
              u = '<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">';
            if (t.sheets[a].externalLinks && t.sheets[a].externalLinks.length > 0 && (u += this._generateHyperlinkRel(t.sheets[a].externalLinks), h = t.sheets[a].externalLinks.length), t.sheets[a].tables && t.sheets[a].tables.length > 0) {
              null == o && (o = r.folder("tables"));
              for (var c = 0; c < t.sheets[a].tables.length; c++) {
                var f = t.sheets[a].tables[c];
                this._generateTable(f, o), u += '<Relationship Target="../tables/' + f.fileName + '" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/table" Id="rId' + (c + 1 + h) + '"/>'
              }
            }
            u += "</Relationships>", n.folder("_rels").file("sheet" + (a + 1) + ".xml.rels", this._xmlDescription + u)
          }
        r.file("styles.xml", this._xmlDescription + this._generateStyleDoc()), l.file("[Content_Types].xml", this._xmlDescription + this._generateContentTypesDoc()), i.file("app.xml", this._xmlDescription + this._generateAppDoc(t)), r.folder("_rels").file("workbook.xml.rels", this._xmlDescription + this._generateWorkbookRels());
        var d = this._xmlDescription + this._generateSharedStringsDoc();
        return this._sharedStrings = [
          [], 0
        ], r.file("sharedStrings.xml", d), d = null, r.file("workbook.xml", this._xmlDescription + this._generateWorkbook(t)), l
      }, s._getSharedString = function(e) {
        var s, l, i, r, n, o, a, h = e.split(/<si.*?>/g),
          u = h.length;
        for (this._sharedStrings = []; --u;)
          for (s = 1, r = !1, h[u].search(/<r>/gi) > -1 ? (r = !0, l = h[u].split(/<r>/g)) : (h[u] = h[u].substring(0, h[u].indexOf("</t>")), l = h[u].split(/<t.*?>/g)); s < l.length;) o = null, r ? (-1 !== l[s].indexOf("<rPr>") && (o = this._getTextRunFont(l[s])), (i = l[s].match(/(<t.*?>)(.*)/)) && 3 === i.length && null != i[2] && (a = i[2].substring(0, i[2].indexOf("</t>"))), n = {
            font: o,
            text: t.Workbook._unescapeXML(a)
          }, null == this._sharedStrings[u - 1] ? this._sharedStrings[u - 1] = [n] : this._sharedStrings[u - 1].push(n)) : this._sharedStrings[u - 1] = t.Workbook._unescapeXML(l[s]), s++
      }, s._getInlineString = function(e) {
        for (var t = e.split("<t>"), s = t.length, l = ""; --s;) l += t[s].substring(0, t[s].indexOf("</t>"));
        return l
      }, s._getCoreSetting = function(e, t) {
        var s, l;
        (l = e.indexOf("<dc:creator>")) >= 0 && (s = e.substr(l + 12), t.creator = s.substring(0, s.indexOf("</dc:creator>"))), (l = e.indexOf("<cp:lastModifiedBy>")) >= 0 && (s = e.substr(l + 19), t.lastModifiedBy = s.substring(0, s.indexOf("</cp:lastModifiedBy>"))), (l = e.indexOf('<dcterms:created xsi:type="dcterms:W3CDTF">')) >= 0 && (s = e.substr(l + 43), t.created = new Date(s.substring(0, s.indexOf("</dcterms:created>")))), (l = e.indexOf('<dcterms:modified xsi:type="dcterms:W3CDTF">')) >= 0 && (s = e.substr(l + 44), t.modified = new Date(s.substring(0, s.indexOf("</dcterms:modified>"))))
      }, s._getWorkbook = function(e, t) {
        var s, l, i, r, n, o, a, h, u = e.substring(e.indexOf("<bookViews>"), e.indexOf("</bookViews>")),
          c = "",
          f = e.indexOf("<definedNames>");
        for (u && (c = this._getAttr(u, "activeTab")), t.activeWorksheet = +c, o = (n = e.split("<sheet ")).length; --o;) a = this._getAttr(n[o], "name"), h = "hidden" !== this._getAttr(n[o], "state"), t.sheets.unshift({
          name: a,
          visible: h,
          columns: [],
          rows: []
        });
        if (f > -1)
          for (t.definedNames = [], o = (n = e.substring(f, e.indexOf("</definedNames>")).split("<definedName ")).length; --o;) a = this._getAttr(n[o], "name"), (l = n[o].match(/.*>.+(?=<\/definedName>)/)) && (l = l[0].replace(/(.*>)(.+)/, "$2"), l = isNaN(+l) ? l : +l), s = {
            name: a,
            value: l
          }, "" !== (i = this._getAttr(n[o], "localSheetId")) && (r = t.sheets[+i]) && (s.sheetName = r.name), t.definedNames.unshift(s)
      }, s._getTheme = function(e) {
        e = e.substring(e.indexOf("<a:clrScheme"), e.indexOf("</a:clrScheme>")), this._colorThemes = this._defaultColorThemes.slice(), this._colorThemes[0] = this._getAttr(e.substring(e.indexOf("a:lt1"), e.indexOf("</a:lt1>")), "lastClr") || this._getAttr(e.substring(e.indexOf("a:lt1"), e.indexOf("</a:lt1>")), "val"), this._colorThemes[1] = this._getAttr(e.substring(e.indexOf("a:dk1"), e.indexOf("</a:dk1>")), "lastClr") || this._getAttr(e.substring(e.indexOf("a:dk1"), e.indexOf("</a:dk1>")), "val"), this._colorThemes[2] = this._getAttr(e.substring(e.indexOf("a:lt2"), e.indexOf("</a:lt2>")), "val"), this._colorThemes[3] = this._getAttr(e.substring(e.indexOf("a:dk2"), e.indexOf("</a:dk2>")), "val");
        for (var t = e.substring(e.indexOf("<a:accent1"), e.indexOf("</a:accent6>")).split("<a:accent"), s = t.length; --s;) this._colorThemes[s + 3] = this._getAttr(t[s], "val")
      }, s._getStyle = function(e) {
        var s, l, i, r, n, o = [],
          a = [],
          h = [],
          u = this._numFmts.slice();
        if (this._styles = [], (i = e.indexOf("<numFmts")) >= 0) {
          var c = e.substring(i + 8, e.indexOf("</numFmts>")).split("<numFmt");
          for (s = c.length; --s;) l = c[s], u[+this._getAttr(l, "numFmtId")] = this._getAttr(l, "formatCode")
        }
        if ((i = e.indexOf("<fonts")) >= 0) {
          var f = e.substring(i, e.indexOf("</fonts>")).split("<font>");
          for (s = f.length; --s;) l = f[s], r = this._getChildNodeValue(l, "sz"), o[s - 1] = {
            bold: l.indexOf("<b/>") >= 0,
            italic: l.indexOf("<i/>") >= 0,
            underline: l.indexOf("<u/>") >= 0,
            size: Math.round(r ? 96 * +r / 72 : 14),
            family: this._getChildNodeValue(l, "name"),
            color: this._getColor(l, !1)
          }, r = null
        }
        if ((i = e.indexOf("<fills")) >= 0) {
          var d = e.substring(i, e.indexOf("</fills>")).split("<fill>");
          for (s = d.length; --s;) a[s - 1] = this._getColor(d[s], !0)
        }
        if ((i = e.indexOf("<borders")) >= 0) {
          var p = e.substring(i, e.indexOf("</borders>")).split("<border>");
          for (s = p.length; --s;) l = p[s], h[s - 1] = {
            left: this._getEdgeBorder(l, "left"),
            right: this._getEdgeBorder(l, "right"),
            top: this._getEdgeBorder(l, "top"),
            bottom: this._getEdgeBorder(l, "bottom")
          }
        }
        if ((i = e.indexOf("<cellXfs")) >= 0) {
          var m = e.substring(i, e.indexOf("</cellXfs>")).split("<xf");
          s = m.length;
          for (var g, _, y, b, v, S; --s;) l = m[s], _ = (g = u[y = +this._getAttr(l, "numFmtId")]) ? /[hsmy\:]/i.test(g) ? "date" : g.indexOf("0") > -1 ? "number" : "@" === g ? "string" : "unknown" : "unknown", b = (y = +this._getAttr(l, "fontId")) > 0 ? o[y] : null, v = (y = +this._getAttr(l, "fillId")) > 1 ? a[y] : null, S = (y = +this._getAttr(l, "borderId")) > 0 ? h[y] : null, i = l.indexOf("<alignment"), n = +this._getAttr(l, "quotePrefix"), this._styles.unshift({
            formatCode: g,
            type: _,
            font: b,
            fillColor: v,
            borders: S,
            hAlign: i >= 0 ? t.Workbook._parseStringToHAlign(this._getAttr(l, "horizontal")) : null,
            vAlign: i >= 0 ? t.Workbook._parseStringToVAlign(this._getAttr(l, "vertical")) : null,
            wordWrap: i >= 0 ? "1" === this._getAttr(l, "wrapText") : null,
            quotePrefix: 1 === n
          })
        }
        if (e.indexOf("<tableStyle ") > -1) {
          this._tableStyles = [];
          var w = e.substring(e.indexOf("<tableStyles "), e.indexOf("</tableStyles>")),
            x = e.substring(e.indexOf("<dxfs "), e.indexOf("</dxfs>"));
          this._getTableStyles(w, x.split("<dxf>"))
        }
      }, s._getEdgeBorder = function(e, s) {
        var l, i, r, n, o = e.indexOf("<" + s),
          a = e.indexOf("</" + s + ">");
        if (o >= 0) {
          i = e.substring(o), i = a >= 0 ? i.substring(0, a) : i.substring(0, i.indexOf("/>"));
          var h = this._getAttr(i, "style");
          h && (r = t.Workbook._parseStringToBorderType(h), n = this._getColor(i, !1), r === t.BorderStyle.Thin && n && "#c6c6c6" === n.toLowerCase() || ((l = {}).style = r, l.color = n))
        }
        return l
      }, s._getSheet = function(s, l, i) {
        var r, n = [];
        if (s.indexOf("<mergeCells") > -1)
          for (var o = s.substring(s.indexOf("<mergeCells"), s.indexOf("</mergeCells>")).split("<mergeCell "), a = o.length; --a;) 2 === (r = this._getAttr(o[a], "ref").split(":")).length && n.unshift({
            topRow: +r[0].match(/\d*/g).join("") - 1,
            leftCol: this._alphaNum(r[0].match(/[a-zA-Z]*/g)[0]),
            bottomRow: +r[1].match(/\d*/g).join("") - 1,
            rightCol: this._alphaNum(r[1].match(/[a-zA-Z]*/g)[0])
          });
        this._getsBaseSharedFormulas(s);
        var h = s.split("<row "),
          u = i.sheets[l];
        if (h[0].indexOf("<dimension") >= 0) {
          var c = this._getAttr(h[0].substr(h[0].indexOf("<dimension")), "ref");
          c && (c = c.substr(c.indexOf(":") + 1), u.maxCol = this._alphaNum(c.match(/[a-zA-Z]*/g)[0]) + 1, u.maxRow = +c.match(/\d*/g).join(""))
        }
        var f = s.indexOf("<tableParts");
        if (f > -1) {
          var d = s.substring(f, s.indexOf("</tableParts>")).split("<tablePart "),
            p = d.length;
          for (u.tableRIds = []; --p;) u.tableRIds.unshift(this._getAttr(d[p], "r:id"))
        }
        if (h.length > 0 && h[0].indexOf("<pane") > -1 && "frozen" === this._getAttr(h[0].substr(h[0].indexOf("<pane")), "state")) {
          var m = this._getAttr(h[0].substr(h[0].indexOf("<pane")), "ySplit"),
            g = this._getAttr(h[0].substr(h[0].indexOf("<pane")), "xSplit");
          u.frozenPane = {
            rows: m ? +m : NaN,
            columns: g ? +g : NaN
          }
        }
        u.summaryBelow = "0" !== this._getAttr(h[0], "summaryBelow"), a = h.length, h.length <= 1 ? (u.maxCol = 20, u.maxRow = 200) : u.maxRow < a - 1 && (u.maxRow = a - 1);
        for (var _, y = null, b = null; --a;) {
          var v = u.rows[+this._getAttr(h[a], "r") - 1] = {
            visible: !0,
            groupLevel: NaN,
            cells: []
          };
          if (h[a].substring(0, h[a].indexOf(">")).indexOf("hidden") > -1 && "1" === this._getAttr(h[a], "hidden") && (v.visible = !1), "1" === this._getAttr(h[a], "customHeight")) {
            var S = +this._getAttr(h[a].substring(0, h[a].indexOf(">")).replace("customHeight", ""), "ht");
            v.height = 96 * S / 72
          }
          y = null, b = null, "1" === this._getAttr(h[a], "customFormat") && (y = (b = this._styles[+this._getAttr(h[a].substring(h[a].indexOf(" s=")), "s")] || {
            type: "General",
            formatCode: null
          }).font || b.fillColor || b.hAlign || b.vAlign || b.wordWrap || b.borders || b.formatCode && "General" !== b.formatCode ? {
            format: b.formatCode && "General" !== b.formatCode ? b.formatCode : null,
            font: b.font,
            fill: {
              color: b.fillColor
            },
            borders: b.borders,
            hAlign: b.hAlign,
            vAlign: b.vAlign,
            wordWrap: b.wordWrap
          } : null), v.style = y;
          var w = this._getAttr(h[a], "outlineLevel");
          v.groupLevel = w && "" !== w ? +w : NaN, v.collapsed = "1" === this._getAttr(h[a], "collapsed");
          var x = h[a].split("<c "),
            C = x.length;
          for ((null == u.maxCol || u.maxCol < C - 1) && (u.maxCol = C - 1); --C;) {
            var k = x[C];
            y = (b = this._styles[+this._getAttr(k, "s")] || {
              type: "General",
              formatCode: null
            }).font || b.fillColor || b.hAlign || b.vAlign || b.wordWrap || b.borders || b.formatCode && "General" !== b.formatCode ? {
              format: b.formatCode && "General" !== b.formatCode ? b.formatCode : null,
              font: b.font,
              fill: {
                color: b.fillColor
              },
              borders: b.borders,
              hAlign: b.hAlign,
              vAlign: b.vAlign,
              wordWrap: b.wordWrap
            } : null;
            var T = this._getAttr(k.substring(0, k.indexOf(">")), "t") || b.type,
              F = null,
              z = "inlineStr" === T || k.indexOf("<is>") >= 0;
            z ? F = this._getInlineString(k) : k.indexOf("<v>") > -1 && (F = k.substring(k.indexOf("<v>") + 3, k.indexOf("</v>")));
            var A = null,
              O = null,
              R = null;
            k.indexOf("<f") > -1 && (k.indexOf("</f>") > -1 ? (A = k.match(/<f.*>.+(?=<\/f>)/)) && (A = A[0].replace(/(\<f.*>)(.+)/, "$2")) : (O = this._getAttr(k, "si")) && (R = this._getAttr(k, "r"), A = this._getSharedFormula(O, R))), null != A && (A = A.replace(/\[\#This Row\]\s*,\s*/gi, "@")), "str" === T || "e" === T || z || (F = F ? +F : ""), _ = this._alphaNum(this._getAttr(k, "r").match(/[a-zA-Z]*/g)[0]);
            var D = null;
            switch (T) {
              case "s":
                null != (F = this._sharedStrings[F]) && (e.isString(F) ? b && b.quotePrefix && "'" !== F[0] && (F = "'" + F) : (D = F.slice(), F = this._getTextOfTextRuns(D)));
                break;
              case "b":
                F = 1 === F;
                break;
              case "date":
                F = F ? this._convertDate(F) : ""
            }
            e.isNumber(F) && (null == y && (y = {
              format: ""
            }), e.isInt(F) ? y.format = y.format || "#,##0" : y.format = y.format || "#,##0.00"), v.cells[_] = {
              value: F,
              textRuns: D,
              isDate: "date" === T,
              formula: t.Workbook._unescapeXML(A),
              style: y
            }
          }
        }
        var N = [],
          B = [];
        if (y = null, b = null, h.length > 0 && h[0].indexOf("<cols>") > -1)
          for (var W = (N = h[0].substring(h[0].indexOf("<cols>") + 6, h[0].indexOf("</cols>")).split("<col ")).length - 1; W > 0; W--) {
            var M = this._parseCharWidthToPixel(+this._getAttr(N[W], "width"));
            for (b = null, N[W].indexOf("style") > -1 && (b = this._styles[+this._getAttr(N[W], "style")] || {
              type: "General",
              formatCode: null
            }), y = null, b && (b.font || b.fillColor || b.hAlign || b.vAlign || b.wordWrap || b.borders || b.formatCode && "General" !== b.formatCode) && (y = {
              format: b.formatCode && "General" !== b.formatCode ? b.formatCode : null,
              font: b.font,
              fill: {
                color: b.fillColor
              },
              borders: b.borders,
              hAlign: b.hAlign,
              vAlign: b.vAlign,
              wordWrap: b.wordWrap
            }), _ = +this._getAttr(N[W], "min") - 1; _ < +this._getAttr(N[W], "max") && _ < u.maxCol; _++) B[_] = {
              visible: "1" !== this._getAttr(N[W], "hidden"),
              autoWidth: "1" === this._getAttr(N[W], "bestFit"),
              width: M,
              style: y
            }
          }
        u.columns = B;
        var P = s.indexOf("<hyperlinks");
        if (P > -1)
          for (var L = s.substring(P, s.indexOf("</hyperlinks>")).split("<hyperlink "), E = L.length; --E;) this._getHyperlink(u, L[E]);
        if (u.frozenPane) {
          if (!isNaN(u.frozenPane.rows))
            for (a = 0; a < u.rows.length && a < u.frozenPane.rows; a++) u.rows[a] && !u.rows[a].visible && u.frozenPane.rows++;
          if (!isNaN(u.frozenPane.columns))
            for (a = 0; a < B.length && a < u.frozenPane.columns; a++) B[a] && !B[a].visible && u.frozenPane.columns++
        }
        var I;
        for (C = 0; C < n.length; C++) I = n[C], u.rows[I.topRow] || (u.rows[I.topRow] = {
          cells: []
        }), u.rows[I.topRow].cells || (u.rows[I.topRow].cells = []), u.rows[I.topRow].cells[I.leftCol] || (u.rows[I.topRow].cells[I.leftCol] = {}), u.rows[I.topRow].cells[I.leftCol].rowSpan = I.bottomRow < u.maxRow ? I.bottomRow - I.topRow + 1 : 200, u.rows[I.topRow].cells[I.leftCol].colSpan = I.rightCol < u.maxCol ? I.rightCol - I.leftCol + 1 : 20
      }, s._getTable = function(e) {
        var t = {};
        t.name = this._getAttr(e, "name"), t.range = this._getAttr(e, "ref");
        var s = this._getAttr(e, "headerRowCount");
        t.showHeaderRow = "" == s || "1" === s;
        var l = this._getAttr(e, "totalsRowCount");
        t.showTotalRow = "1" === l;
        var i = e.substring(e.indexOf("<tableStyleInfo")),
          r = this._getAttr(i, "name");
        this._isBuiltInStyleName(r) ? t.style = {
          name: r
        } : t.style = this._getTableStyleByName(r), t.showBandedColumns = "1" === this._getAttr(i, "showColumnStripes"), t.showBandedRows = "1" === this._getAttr(i, "showRowStripes"), t.alterFirstColumn = "1" === this._getAttr(i, "showFirstColumn"), t.alterLastColumn = "1" === this._getAttr(i, "showLastColumn");
        var n = e.split("<tableColumn ");
        t.columns = [];
        for (var o = 1; o < n.length; o++) {
          var a = n[o];
          t.columns.push(this._getTableColumn(a))
        }
        if (e.indexOf("filterColumn") > -1)
          for (var h = e.substring(e.indexOf("<autoFilter"), e.indexOf("</autoFilter>")).split("<filterColumn"), u = 1; u < h.length; u++) {
            var c = h[u],
              f = +this._getAttr(c, "colId");
            t.columns[f].showFilterButton = "1" !== this._getAttr(c, "hiddenButton")
          }
        return t
      }, s._getTableColumn = function(e) {
        var t = {};
        t.name = this._getAttr(e, "name");
        var s = this._getAttr(e, "totalsRowLabel");
        if (s) t.totalRowLabel = s;
        else {
          var l = this._getAttr(e, "totalsRowFunction");
          "custom" === l && (l = e.substring(e.indexOf("<totalsRowFormula>") + 2 + "totalsRowFormula".length, e.indexOf("</totalsRowFormula>"))), t.totalRowFunction = l
        }
        return t
      }, s._getSheetRelatedTable = function(e) {
        var t = this._getAttr(e, "Target");
        t = t.substring(t.lastIndexOf("/") + 1);
        for (var s = 0; s < this._tables.length; s++) {
          var l = this._tables[s];
          if (t === l.fileName) return l
        }
        return null
      }, s._getSheetRelatedHyperlink = function(e, t, s) {
        for (var l = 0; l < s.hyperlinkRIds.length; l++) {
          var i = s.hyperlinkRIds[l];
          if (i.rId === t) {
            var r = this._getAttr(e, "Target");
            s.rows[i.ref.row] && s.rows[i.ref.row].cells[i.ref.col] && (s.rows[i.ref.row].cells[i.ref.col].link = r)
          }
        }
      }, s._getTableStyles = function(e, t) {
        for (var s = e.split("<tableStyle "), l = s.length; --l;) {
          var i = {},
            r = s[l];
          i.name = this._getAttr(r, "name");
          for (var n = r.split("<tableStyleElement "), o = n.length; --o;) {
            var a = n[o],
              h = this._getAttr(a, "type");
            switch (h) {
              case "firstRowStripe":
                h = "firstBandedRowStyle";
                break;
              case "secondRowStripe":
                h = "secondBandedRowStyle";
                break;
              case "firstColumnStripe":
                h = "firstBandedColumnStyle";
                break;
              case "secondColumnStripe":
                h = "secondBandedColumnStyle";
                break;
              default:
                h += "Style"
            }
            var u = this._getAttr(a, "dxfId");
            "" !== u && (i[h] = this._getTableStyleElement(t[+u + 1]));
            var c = this._getAttr(a, "size");
            c && (null == i[h] && (i[h] = {}), i[h].size = +c)
          }
          this._tableStyles.push(i)
        }
      }, s._getTableStyleElement = function(e) {
        var t = null,
          s = null,
          l = null,
          i = null,
          r = e.indexOf("<font>");
        if (r >= 0) {
          t = e.substring(r, e.indexOf("</font>"));
          var n = this._getChildNodeValue(t, "sz");
          s = {
            bold: "1" === this._getChildNodeValue(t, "b"),
            italic: "1" === this._getChildNodeValue(t, "i"),
            underline: "1" === this._getChildNodeValue(t, "u"),
            size: Math.round(n ? 96 * +n / 72 : 14),
            family: this._getChildNodeValue(t, "name"),
            color: this._getColor(t, !1)
          }
        }
        return t = null, (r = e.indexOf("<fill>")) >= 0 && (t = e.substring(r, e.indexOf("</fill>")), l = {
          color: this._getColor(t, !0)
        }), t = null, (r = e.indexOf("<border>")) >= 0 && (t = e.substring(r, e.indexOf("</border>")), i = {
          left: this._getEdgeBorder(t, "left"),
          right: this._getEdgeBorder(t, "right"),
          top: this._getEdgeBorder(t, "top"),
          bottom: this._getEdgeBorder(t, "bottom"),
          vertical: this._getEdgeBorder(t, "vertical"),
          horizontal: this._getEdgeBorder(t, "horizontal")
        }), {
          font: s,
          fill: l,
          borders: i
        }
      }, s._getTableStyleByName = function(e) {
        var t, s;
        if (null == this._tableStyles || 0 === this._tableStyles.length) return null;
        for (t = 0; t < this._tableStyles.length; t++)
          if ((s = this._tableStyles[t]) && s.name.toLowerCase() === e.toLowerCase()) return s;
        return null
      }, s._getHyperlink = function(e, s) {
        var l, i, r, n, o, a;
        if (null != (l = this._getAttr(s, "ref"))) {
          r = l.split(":"), null == (o = this._getAttr(s, "r:id")) && (a = this._getAttr(s, "location"));
          for (var h = 0; h < r.length; h++) i = r[h], n = t.Workbook.tableAddress(i), o ? (null == e.hyperlinkRIds && (e.hyperlinkRIds = []), e.hyperlinkRIds.push({
            ref: n,
            rId: o
          })) : a && e.rows[n.row] && e.rows[n.row].cells[n.col] && (e.rows[n.row].cells[n.col].link = a)
        }
      }, s._getTextRunFont = function(e) {
        var t = this._getChildNodeValue(e, "sz");
        return {
          bold: e.indexOf("<b/>") >= 0,
          italic: e.indexOf("<i/>") >= 0,
          underline: e.indexOf("<u/>") >= 0,
          size: Math.round(t ? 96 * +t / 72 : 14),
          family: this._getChildNodeValue(e, "name"),
          color: this._getColor(e, !1)
        }
      }, s._getTextOfTextRuns = function(e) {
        var t, s, l = "";
        for (t = 0; t < e.length; t++)(s = e[t]) && (l += s.text);
        return l
      }, s._isBuiltInStyleName = function(e) {
        var t;
        if (0 === e.search(/TableStyleLight/i)) {
          if (t = +e.substring(15), !isNaN(t) && t >= 1 && t <= 21) return !0
        } else if (0 === e.search(/TableStyleMedium/i)) {
          if (t = +e.substring(16), !isNaN(t) && t >= 1 && t <= 28) return !0
        } else if (0 === e.search(/TableStyleDark/i) && (t = +e.substring(14), !isNaN(t) && t >= 1 && t <= 11)) return !0;
        return !1
      }, s._generateRelsDoc = function() {
        return '<Relationships xmlns="' + this._relationshipsNS + '"><Relationship Target="docProps/app.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Id="rId3"/><Relationship Target="docProps/core.xml" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Id="rId2"/><Relationship Target="xl/workbook.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Id="rId1"/></Relationships>'
      }, s._generateThemeDoc = function() {
        return '<a:theme name="Office Theme" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"><a:themeElements>' + this._generateClrScheme() + this._generateFontScheme() + this._generateFmtScheme() + "</a:themeElements><a:objectDefaults/><a:extraClrSchemeLst/></a:theme>"
      }, s._generateClrScheme = function() {
        return null === this._colorThemes && (this._colorThemes = []), '<a:clrScheme name="Office"><a:dk1><a:sysClr lastClr="' + (this._colorThemes[1] || "000000") + '" val="windowText"/></a:dk1><a:lt1><a:sysClr lastClr="' + (this._colorThemes[0] || "FFFFFF") + '" val="window"/></a:lt1><a:dk2><a:srgbClr val="' + (this._colorThemes[3] || "1F497D") + '"/></a:dk2><a:lt2><a:srgbClr val="' + (this._colorThemes[2] || "EEECE1") + '"/></a:lt2><a:accent1><a:srgbClr val="' + (this._colorThemes[4] || "4F81BD") + '"/></a:accent1><a:accent2><a:srgbClr val="' + (this._colorThemes[5] || "C0504D") + '"/></a:accent2><a:accent3><a:srgbClr val="' + (this._colorThemes[6] || "9BBB59") + '"/></a:accent3><a:accent4><a:srgbClr val="' + (this._colorThemes[7] || "8064A2") + '"/></a:accent4><a:accent5><a:srgbClr val="' + (this._colorThemes[8] || "4BACC6") + '"/></a:accent5><a:accent6><a:srgbClr val="' + (this._colorThemes[9] || "F79646") + '"/></a:accent6><a:hlink><a:srgbClr val="0000FF"/></a:hlink><a:folHlink><a:srgbClr val="800080"/></a:folHlink></a:clrScheme>'
      }, s._generateFontScheme = function() {
        return '<a:fontScheme name="Office"><a:majorFont><a:latin typeface="Cambria"/><a:ea typeface=""/><a:cs typeface=""/><a:font typeface="ＭＳ Ｐゴシック" script="Jpan"/><a:font typeface="맑은 고딕" script="Hang"/><a:font typeface="宋体" script="Hans"/><a:font typeface="新細明體" script="Hant"/><a:font typeface="Times New Roman" script="Arab"/><a:font typeface="Times New Roman" script="Hebr"/><a:font typeface="Tahoma" script="Thai"/><a:font typeface="Nyala" script="Ethi"/><a:font typeface="Vrinda" script="Beng"/><a:font typeface="Shruti" script="Gujr"/><a:font typeface="MoolBoran" script="Khmr"/><a:font typeface="Tunga" script="Knda"/><a:font typeface="Raavi" script="Guru"/><a:font typeface="Euphemia" script="Cans"/><a:font typeface="Plantagenet Cherokee" script="Cher"/><a:font typeface="Microsoft Yi Baiti" script="Yiii"/><a:font typeface="Microsoft Himalaya" script="Tibt"/><a:font typeface="MV Boli" script="Thaa"/><a:font typeface="Mangal" script="Deva"/><a:font typeface="Gautami" script="Telu"/><a:font typeface="Latha" script="Taml"/><a:font typeface="Estrangelo Edessa" script="Syrc"/><a:font typeface="Kalinga" script="Orya"/><a:font typeface="Kartika" script="Mlym"/><a:font typeface="DokChampa" script="Laoo"/><a:font typeface="Iskoola Pota" script="Sinh"/><a:font typeface="Mongolian Baiti" script="Mong"/><a:font typeface="Times New Roman" script="Viet"/><a:font typeface="Microsoft Uighur" script="Uigh"/><a:font typeface="Sylfaen" script="Geor"/></a:majorFont><a:minorFont><a:latin typeface="Calibri"/><a:ea typeface=""/><a:cs typeface=""/><a:font typeface="ＭＳ Ｐゴシック" script="Jpan"/><a:font typeface="맑은 고딕" script="Hang"/><a:font typeface="宋体" script="Hans"/><a:font typeface="新細明體" script="Hant"/><a:font typeface="Arial" script="Arab"/><a:font typeface="Arial" script="Hebr"/><a:font typeface="Tahoma" script="Thai"/><a:font typeface="Nyala" script="Ethi"/><a:font typeface="Vrinda" script="Beng"/><a:font typeface="Shruti" script="Gujr"/><a:font typeface="DaunPenh" script="Khmr"/><a:font typeface="Tunga" script="Knda"/><a:font typeface="Raavi" script="Guru"/><a:font typeface="Euphemia" script="Cans"/><a:font typeface="Plantagenet Cherokee" script="Cher"/><a:font typeface="Microsoft Yi Baiti" script="Yiii"/><a:font typeface="Microsoft Himalaya" script="Tibt"/><a:font typeface="MV Boli" script="Thaa"/><a:font typeface="Mangal" script="Deva"/><a:font typeface="Gautami" script="Telu"/><a:font typeface="Latha" script="Taml"/><a:font typeface="Estrangelo Edessa" script="Syrc"/><a:font typeface="Kalinga" script="Orya"/><a:font typeface="Kartika" script="Mlym"/><a:font typeface="DokChampa" script="Laoo"/><a:font typeface="Iskoola Pota" script="Sinh"/><a:font typeface="Mongolian Baiti" script="Mong"/><a:font typeface="Arial" script="Viet"/><a:font typeface="Microsoft Uighur" script="Uigh"/><a:font typeface="Sylfaen" script="Geor"/></a:minorFont></a:fontScheme>'
      }, s._generateFmtScheme = function() {
        return '<a:fmtScheme name="Office">' + this._generateFillScheme() + this._generateLineStyles() + this._generateEffectScheme() + this._generateBgFillScheme() + "</a:fmtScheme>"
      }, s._generateFillScheme = function() {
        return '<a:fillStyleLst><a:solidFill><a:schemeClr val="phClr"/></a:solidFill><a:gradFill rotWithShape="1"><a:gsLst><a:gs pos="0"><a:schemeClr val="phClr"><a:tint val="50000"/><a:satMod val="300000"/></a:schemeClr></a:gs><a:gs pos="35000"><a:schemeClr val="phClr"><a:tint val="37000"/><a:satMod val="300000"/></a:schemeClr></a:gs><a:gs pos="100000"><a:schemeClr val="phClr"><a:satMod val="350000"/></a:schemeClr></a:gs></a:gsLst><a:lin scaled="1" ang="16200000"/></a:gradFill><a:gradFill rotWithShape="1"><a:gsLst><a:gs pos="0"><a:schemeClr val="phClr"><a:tint val="51000"/><a:satMod val="130000"/></a:schemeClr></a:gs><a:gs pos="80000"><a:schemeClr val="phClr"><a:tint val="15000"/><a:satMod val="130000"/></a:schemeClr></a:gs><a:gs pos="100000"><a:schemeClr val="phClr"><a:tint val="94000"/><a:satMod val="135000"/></a:schemeClr></a:gs></a:gsLst><a:lin scaled="1" ang="16200000"/></a:gradFill></a:fillStyleLst>'
      }, s._generateLineStyles = function() {
        return '<a:lnStyleLst><a:ln algn="ctr" cmpd="sng" cap="flat" w="9525"><a:solidFill><a:schemeClr val="phClr"><a:shade val="9500"/><a:satMod val="105000"/></a:schemeClr></a:solidFill><a:prstDash val="solid"/></a:ln><a:ln algn="ctr" cmpd="sng" cap="flat" w="25400"><a:solidFill><a:schemeClr val="phClr"/></a:solidFill><a:prstDash val="solid"/></a:ln><a:ln algn="ctr" cmpd="sng" cap="flat" w="38100"><a:solidFill><a:schemeClr val="phClr"/></a:solidFill><a:prstDash val="solid"/></a:ln></a:lnStyleLst>'
      }, s._generateEffectScheme = function() {
        return '<a:effectStyleLst><a:effectStyle><a:effectLst><a:outerShdw dir="5400000" rotWithShape="0" dist="23000" blurRad="40000"><a:srgbClr val="000000"><a:alpha val="38000"/></a:srgbClr></a:outerShdw></a:effectLst></a:effectStyle><a:effectStyle><a:effectLst><a:outerShdw dir="5400000" rotWithShape="0" dist="23000" blurRad="40000"><a:srgbClr val="000000"><a:alpha val="35000"/></a:srgbClr></a:outerShdw></a:effectLst></a:effectStyle><a:effectStyle><a:effectLst><a:outerShdw dir="5400000" rotWithShape="0" dist="23000" blurRad="40000"><a:srgbClr val="000000"><a:alpha val="35000"/></a:srgbClr></a:outerShdw></a:effectLst><a:scene3d><a:camera prst="orthographicFront"><a:rot rev="0" lon="0" lat="0"/></a:camera><a:lightRig dir="t" rig="threePt"><a:rot rev="1200000" lon="0" lat="0"/></a:lightRig></a:scene3d><a:sp3d><a:bevelT w="63500" h="25400"/></a:sp3d></a:effectStyle></a:effectStyleLst>'
      }, s._generateBgFillScheme = function() {
        return '<a:bgFillStyleLst><a:solidFill><a:schemeClr val="phClr"/></a:solidFill><a:gradFill rotWithShape="1"><a:gsLst><a:gs pos="0"><a:schemeClr val="phClr"><a:tint val="40000"/><a:satMod val="350000"/></a:schemeClr></a:gs><a:gs pos="40000"><a:schemeClr val="phClr"><a:tint val="45000"/><a:shade val="99000"/><a:satMod val="350000"/></a:schemeClr></a:gs><a:gs pos="100000"><a:schemeClr val="phClr"><a:tint val="20000"/><a:satMod val="255000"/></a:schemeClr></a:gs></a:gsLst><a:path path="circle"><a:fillToRect l="50000" t="-80000" r="50000" b="180000"/></a:path></a:gradFill><a:gradFill rotWithShape="1"><a:gsLst><a:gs pos="0"><a:schemeClr val="phClr"><a:satMod val="300000"/></a:schemeClr></a:gs><a:gs pos="100000"><a:schemeClr val="phClr"><a:tint val="80000"/><a:satMod val="200000"/></a:schemeClr></a:gs></a:gsLst><a:path path="circle"><a:fillToRect l="50000" t="50000" r="50000" b="50000"/></a:path></a:gradFill></a:bgFillStyleLst>'
      }, s._generateCoreDoc = function(e) {
        var t = '<cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:dcmitype="http://purl.org/dc/dcmitype/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">';
        return e.creator ? t += "<dc:creator>" + e.creator + "</dc:creator>" : t += "<dc:creator/>", e.lastModifiedBy ? t += "<cp:lastModifiedBy>" + e.lastModifiedBy + "</cp:lastModifiedBy>" : t += "<cp:lastModifiedBy/>", t += '<dcterms:created xsi:type="dcterms:W3CDTF">' + (e.created || new Date).toISOString() + '</dcterms:created><dcterms:modified xsi:type="dcterms:W3CDTF">' + (e.modified || new Date).toISOString() + "</dcterms:modified></cp:coreProperties>"
      }, s._generateSheetGlobalSetting = function(e, t, s) {
        var l = t.rows && t.rows[0] && t.rows[0].cells ? t.rows[0].cells.length : 0,
          i = ' xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" mc:Ignorable="x14ac" xmlns:x14ac="http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac">';
        return i += '<sheetPr><outlinePr summaryBelow="0"/></sheetPr>', i += '<dimension ref="A1' + (l > 0 ? ":" + this._numAlpha(l - 1) + t.rows.length : "") + '"/>', i += '<sheetViews><sheetView workbookViewId="0"', e === s.activeWorksheet && (i += ' tabSelected="1"'), !t.frozenPane || 0 === t.frozenPane.rows && 0 === t.frozenPane.columns ? i += "/>" : (i += ">", i += '<pane state="frozen" activePane="' + (0 !== t.frozenPane.rows && 0 !== t.frozenPane.columns ? "bottomRight" : 0 !== t.frozenPane.rows ? "bottomLeft" : "topRight") + '" topLeftCell="' + (this._numAlpha(t.frozenPane.columns) + (t.frozenPane.rows + 1)) + '" ySplit="' + t.frozenPane.rows.toString() + '" xSplit="' + t.frozenPane.columns.toString() + '"/>', i += "</sheetView>"), i += "</sheetViews>", i += '<sheetFormatPr defaultRowHeight="15" x14ac:dyDescent="0.25"/>'
      }, s._generateCell = function(e, s, l, i, r, n) {
        var o = '<c r="' + this._numAlpha(s) + (e + 1) + '" s="' + l.toString() + '"';
        i && (o += ' t="' + i + '"');
        var a = "";
        return n && ("=" === n[0] && (n = n.substring(1)), n = n.replace(/\@\s*/gi, "[#This Row], "), a += "<f>" + t.Workbook._escapeXML(n) + "</f>"), null != r && "" !== r && (a += "<v>" + r + "</v>"), o + (a ? ">" + a + "</c>" : "/>")
      }, s._generateMergeSetting = function(e) {
        for (var t = '<mergeCells count="' + e.length.toString() + '">', s = 0; s < e.length; s++) t += '<mergeCell ref="' + e[s].join(":") + '"/>';
        return t + "</mergeCells>"
      }, s._generateStyleDoc = function() {
        var e = '<styleSheet xmlns="' + this._workbookNS + '" xmlns:x14ac="http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" mc:Ignorable="x14ac">',
          t = "",
          s = "",
          l = [],
          i = 0,
          r = 0,
          n = "",
          o = "";
        o = this._generateFontStyle({}, !0);
        var a = "",
          h = "";
        h += this._generateFillStyle("none", null), h += this._generateFillStyle("gray125", null);
        var u = "",
          c = "";
        c += this._generateBorderStyle({});
        var f = "",
          d = "";
        for (d += this._generateCellXfs(0, 0, 0, 0, {}); i < this._styles.length;) {
          var p = this._styles[i];
          if (p) {
            var m = 0;
            if ((p = JSON.parse(p)).format && "General" !== p.format && (m = this._numFmts.indexOf(p.format)) < 0) {
              var g = l.indexOf(p.format); - 1 === g ? (l.push(p.format), s += '<numFmt numFmtId="' + (m = 164 + r).toString() + '" formatCode="' + p.format + '"/>', r++) : m = 164 + g
            }
            var _ = 0;
            if (p.borders) {
              var y = JSON.stringify(p.borders);
              (_ = this._borders.indexOf(y)) < 0 && (_ = this._borders.push(y) - 1, c += this._generateBorderStyle(p.borders))
            }
            var b = 0;
            if (p.font) {
              var v = JSON.stringify(p.font);
              (b = this._fonts.indexOf(v)) < 0 && (b = this._fonts.push(v) - 1, o += this._generateFontStyle(p.font))
            }
            var S = 0;
            if (p.fill && p.fill.color) {
              var w = JSON.stringify(p.fill);
              (S = this._fills.indexOf(w)) < 0 && (S = this._fills.push(w) - 1, h += this._generateFillStyle("solid", p.fill.color))
            }
            d += this._generateCellXfs(m, _, b, S, p)
          }
          i++
        }
        l = null, r > 0 ? (t = '<numFmts count="' + r + '">', t += s, t += "</numFmts>") : t = '<numFmts count="0"/>', e += t, n = '<fonts count="' + this._fonts.length + '" x14ac:knownFonts="1">', n += o, e += n += "</fonts>", a = '<fills count="' + this._fills.length + '">', a += h, e += a += "</fills>", u = '<borders count="' + this._borders.length + '">', u += c, e += u += "</borders>", e += '<cellStyleXfs count="1"><xf numFmtId="0" fontId="0" fillId="0" borderId="0"/></cellStyleXfs>', f = '<cellXfs count="' + this._styles.length + '">', f += d, f += "</cellXfs>";
        var x = "",
          C = "";
        return this._tableStyles.length > 0 && (this._getDxfs(), this._dxfs.length > 0 && (x = this._generateDxfs()), C = this._generateTableStyles()), e += f + '<cellStyles count="1"><cellStyle xfId="0" builtinId="0" name="Normal"/></cellStyles>' + ("" === x ? '<dxfs count="0"/>' : x) + ("" === C ? '<tableStyles count="0" defaultTableStyle="TableStyleMedium9" defaultPivotStyle="PivotStyleLight16"/>' : C) + '<extLst><ext uri="{EB79DEF2-80B8-43e5-95BD-54CBDDF9020C}"><x14ac:slicerStyles defaultSlicerStyle="SlicerStyleLight1" xmlns:x14="http://schemas.microsoft.com/office/spreadsheetml/2009/9/main"/></ext></extLst></styleSheet>'
      }, s._generateBorderStyle = function(e, t) {
        void 0 === t && (t = !1);
        var s, l, i = "<border>";
        for (var r in {
          left: 0,
          right: 0,
          top: 0,
          bottom: 0,
          diagonal: 0,
          vertical: 0,
          horizontal: 0
        })(t || "vertical" !== r && "horizontal" !== r) && (e[r] ? (s = "<" + r + ' style="' + e[r].style + '">', "", 6 === (l = (l = e[r].color) ? "#" === l[0] ? l.substring(1) : l : "").length && (l = "FF" + l), l || (l = "FF000000"), s += '<color rgb="' + l + '"/>', s += "</" + r + ">") : s = "<" + r + "/>", i += s);
        return i += "</border>"
      }, s._generateFontStyle = function(e, t) {
        void 0 === t && (t = !1);
        var s = "<font>";
        return e.bold && (s += "<b/>"), e.italic && (s += "<i/>"), e.underline && (s += "<u/>"), s += '<sz val="' + (e.size ? Math.round(72 * e.size / 96) : this._defaultFontSize) + '"/>', e.color ? s += '<color rgb="FF' + ("#" === e.color[0] ? e.color.substring(1) : e.color) + '"/>' : s += '<color theme="1"/>', s += '<name val="' + (e.family || this._defaultFontName) + '"/>', s += '<family val="2"/>', t && (s += '<scheme val="minor"/>'), s += "</font>"
      }, s._generateFillStyle = function(e, t, s) {
        void 0 === s && (s = !1);
        var l, i = '<fill><patternFill patternType="' + e + '">';
        return t && (l = s ? "<bgColor " : "<fgColor ", i += l += 'rgb="FF' + ("#" === t[0] ? t.substring(1) : t) + '"/>'), i += "</patternFill></fill>"
      }, s._generateCellXfs = function(e, t, s, l, i) {
        var r = '<xf xfId="0" ';
        if (r += 'numFmtId="' + e.toString() + '" ', e > 0 && (r += 'applyNumberFormat="1" '), r += 'borderId="' + t.toString() + '" ', t > 0 && (r += 'applyBorder="1" '), r += 'fontId="' + s.toString() + '" ', s > 0 && (r += 'applyFont="1" '), r += 'fillId="' + l.toString() + '" ', l > 0 && (r += 'applyFill="1" '), i.quotePrefix && (r += 'quotePrefix="1" '), i.hAlign || i.vAlign || i.indent || i.wordWrap) {
          r += 'applyAlignment="1">';
          var n = "<alignment ";
          i.hAlign && (n += 'horizontal="' + i.hAlign + '" '), i.vAlign && (n += 'vertical="' + i.vAlign + '" '), i.indent && (n += 'indent="' + i.indent + '" '), i.wordWrap && (n += 'wrapText="1"'), r += n += "/>", r += "</xf>"
        } else r += "/>";
        return r
      }, s._generateContentTypesDoc = function() {
        var e, t = '<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">';
        for (this._macroEnabled && (t += '<Default ContentType="application/vnd.ms-office.vbaProject" Extension="bin"/>'), t += '<Default ContentType="application/vnd.openxmlformats-package.relationships+xml" Extension="rels"/><Default ContentType="application/xml" Extension="xml"/><Override ContentType="' + (this._macroEnabled ? "application/vnd.ms-excel.sheet.macroEnabled.main+xml" : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml") + '" PartName="/xl/workbook.xml"/>', e = 0; e < this._contentTypes.length; e++) t += this._contentTypes[e];
        for (t += '<Override ContentType="application/vnd.openxmlformats-officedocument.theme+xml" PartName="/xl/theme/theme1.xml"/><Override ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml" PartName="/xl/styles.xml"/><Override ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml" PartName="/xl/sharedStrings.xml"/><Override ContentType="application/vnd.openxmlformats-package.core-properties+xml" PartName="/docProps/core.xml"/><Override ContentType="application/vnd.openxmlformats-officedocument.extended-properties+xml" PartName="/docProps/app.xml"/>', e = 0; e < this._tables.length; e++) t += '<Override ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.table+xml" PartName="/xl/tables/' + this._tables[e] + '"/>';
        return t += "</Types>"
      }, s._generateAppDoc = function(e) {
        for (var t = '<Properties xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes" xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"><Application>' + (e.application || "wijmo.xlsx") + '</Application><DocSecurity>0</DocSecurity><ScaleCrop>false</ScaleCrop><HeadingPairs><vt:vector baseType="variant" size="2"><vt:variant><vt:lpstr>Worksheets</vt:lpstr></vt:variant><vt:variant><vt:i4>' + this._props.length + '</vt:i4></vt:variant></vt:vector></HeadingPairs><TitlesOfParts><vt:vector baseType="lpstr" size="' + this._props.length + '">', s = 0; s < this._props.length; s++) t += "<vt:lpstr>" + this._props[s] + "</vt:lpstr>";
        return t += "</vt:vector></TitlesOfParts><Manager/><Company>" + (e.company || "GrapeCity, Inc.") + "</Company><LinksUpToDate>false</LinksUpToDate><SharedDoc>false</SharedDoc><HyperlinksChanged>false</HyperlinksChanged><AppVersion>1.0</AppVersion></Properties>"
      }, s._generateWorkbookRels = function() {
        for (var e = '<Relationships xmlns="' + this._relationshipsNS + '">', t = 0; t < this._xlRels.length; t++) e += this._xlRels[t];
        return e += '<Relationship Target="sharedStrings.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings" Id="rId' + (this._xlRels.length + 1) + '"/><Relationship Target="styles.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Id="rId' + (this._xlRels.length + 2) + '"/><Relationship Target="theme/theme1.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme" Id="rId' + (this._xlRels.length + 3) + '"/>', this._macroEnabled && (e += '<Relationship Target="vbaProject.bin" Type="http://schemas.microsoft.com/office/2006/relationships/vbaProject" Id="rId' + (this._xlRels.length + 4) + '"/>'), e += "</Relationships>"
      }, s._generateWorkbook = function(e) {
        for (var t = '<workbook xmlns="' + this._workbookNS + '" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"><fileVersion rupBuild="9303" lowestEdited="5" lastEdited="5" appName="xl"/><workbookPr/><bookViews><workbookView xWindow="480" yWindow="60" windowWidth="18195" windowHeight="8505"' + (null != e.activeWorksheet ? ' activeTab="' + e.activeWorksheet.toString() + '"' : "") + "/></bookViews><sheets>", s = 0; s < this._worksheets.length; s++) t += this._worksheets[s];
        if (t += "</sheets>", e.definedNames && e.definedNames.length > 0) {
          for (t += "<definedNames>", s = 0; s < e.definedNames.length; s++) {
            var l = -1;
            e.definedNames[s].sheetName && (l = this._getSheetIndexBySheetName(e, e.definedNames[s].sheetName)), t += '<definedName name="' + e.definedNames[s].name + '" ' + (l > -1 ? 'localSheetId="' + l + '"' : "") + ">" + e.definedNames[s].value + "</definedName>"
          }
          t += "</definedNames>"
        }
        return t += '<calcPr fullCalcOnLoad="1"/></workbook>'
      }, s._generateWorkSheet = function(s, l, i) {
        var r, n, o, a, h, u, c, f, d, p, m, g, _, y, b, v, S, w, x, C, k, T, F, z = 1;
        if (r = s + 1, n = l.sheets[s], o = n.columns, a = this._cloneColumnsStyle(o), !n) throw "Worksheet should not be empty!";
        var A = '<worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"';
        A += this._generateSheetGlobalSetting(s, n, l);
        var O = "<sheetData>";
        for (h = n.style ? this._cloneStyle(n.style) : null, "", [], c = [], f = -1, d = (u = n.rows) ? u.length : 0; ++f < d;) {
          for (p = -1, m = u[f] && u[f].cells ? u[f].cells.length : 0, g = null, O += '<row x14ac:dyDescent="0.25" r="' + (f + 1).toString() + '"', u[f] && (u[f].height && (O += ' customHeight="1" ht="' + (72 * +u[f].height / 96).toString() + '"'), u[f].groupLevel && (O += ' outlineLevel="' + u[f].groupLevel.toString() + '"'), (g = u[f].style ? this._cloneStyle(u[f].style) : null) && ((g = this._resolveStyleInheritance(g)).font && g.font.color && (g.font.color = this._parseColor(g.font.color)), g.fill && g.fill.color && (g.fill.color = this._parseColor(g.fill.color)), null == g.hAlign || e.isString(g.hAlign) || (g.hAlign = t.Workbook._parseHAlignToString(e.asEnum(g.hAlign, t.HAlign))), null == g.vAlign || e.isString(g.vAlign) || (g.vAlign = t.Workbook._parseVAlignToString(e.asEnum(g.vAlign, t.VAlign))), _ = JSON.stringify(g), (y = this._styles.indexOf(_)) < 0 && (y = this._styles.push(_) - 1), O += ' customFormat="1" s="' + y.toString() + '"')), u[f] && !1 === u[f].visible && (O += ' hidden="1"'), u[f] && !0 === u[f].collapsed && (O += ' collapsed="1"'), O += ">"; ++p < m;) {
            if (b = u[f].cells[p], v = null, S = null, w = "", x = -1, F = b ? b.textRuns : null, v = b && b.hasOwnProperty("value") ? b.value : b, S = b && b.style ? this._cloneStyle(b.style) : {}, S = this._resolveStyleInheritance(S), (C = a[p]) && (C = this._resolveStyleInheritance(C), S = this._extend(S, C)), g && (S = this._extend(S, g)), h && (h = this._resolveStyleInheritance(h), S = this._extend(S, h)), null == S.hAlign || e.isString(S.hAlign) || (S.hAlign = t.Workbook._parseHAlignToString(e.asEnum(S.hAlign, t.HAlign))), null == S.vAlign || e.isString(S.vAlign) || (S.vAlign = t.Workbook._parseVAlignToString(e.asEnum(S.vAlign, t.VAlign))), S.font && S.font.color && (S.font.color = this._parseColor(S.font.color)), S.fill && S.fill.color && (S.fill.color = this._parseColor(S.fill.color)), this._applyDefaultBorder(S), S.borders && (S.borders = this._extend({}, S.borders), this._parseBorder(S.borders, !!S.fill && !!S.fill.color)), b && b.isDate && !e.isDate(v)) {
              var R = new Date(v);
              e.isDate(R) && (v = R)
            }!e.isNumber(v) || !isNaN(v) && isFinite(v) || (v = v.toString()), F || v && e.isString(v) && ("@" === S.format || (+v).toString() !== v || !isFinite(+v)) ? (this._sharedStrings[1]++, "'" === (v = F ? "{RichTextMark}" + JSON.stringify(F) : t.Workbook._unescapeXML(v))[0] && (S.quotePrefix = !0, v = v.substring(1)), (x = this._sharedStrings[0].indexOf(v)) < 0 && (x = this._sharedStrings[0].push(v) - 1), v = x, w = "s") : e.isBoolean(v) ? (v = v ? 1 : 0, w = "b") : e.isDate(v) ? (v = this._convertDate(v), S.format = S.format || "mm-dd-yy") : e.isObject(v) && (v = null), S = JSON.stringify(S), (y = this._styles.indexOf(S)) < 0 && (y = this._styles.push(S) - 1), b && (null != b.colSpan && b.colSpan > 1 || null != b.rowSpan && b.rowSpan > 1) && (b.colSpan = b.colSpan || 1, b.rowSpan = b.rowSpan || 1, this._checkValidMergeCell(c, f, b.rowSpan, p, b.colSpan) && c.push([this._numAlpha(p) + (f + 1), this._numAlpha(p + b.colSpan - 1) + (f + b.rowSpan)])), b && b.link && (null == T && (T = []), T.push({
              ref: t.Workbook.xlsxAddress(f, p),
              value: v,
              href: b.link
            })), S = null, O += this._generateCell(f, p, y, w, v, b && b.formula ? b.formula : null)
          }
          O += "</row>"
        }
        if (O += "</sheetData>", o && o.length > 0) {
          for (A += "<cols>", f = 0; f < o.length; f++)
            if (y = -1, this._isEmpty(a[f]) || (C = a[f]) && ((C = this._resolveStyleInheritance(C)).font && C.font.color && (C.font.color = this._parseColor(C.font.color)), C.fill && C.fill.color && (C.fill.color = this._parseColor(C.fill.color)), null == C.hAlign || e.isString(C.hAlign) || (C.hAlign = t.Workbook._parseHAlignToString(e.asEnum(C.hAlign, t.HAlign))), null == C.vAlign || e.isString(C.vAlign) || (C.vAlign = t.Workbook._parseVAlignToString(e.asEnum(C.vAlign, t.VAlign))), C = JSON.stringify(C), (y = this._styles.indexOf(C)) < 0 && (y = this._styles.push(C) - 1)), !this._isEmpty(o[f])) {
              k = null != (k = o[f].width) ? "string" == typeof k && k.indexOf("ch") > -1 ? this._parseCharCountToCharWidth(k.substring(0, k.indexOf("ch"))) : this._parsePixelToCharWidth(k) : 8.43;
              var D = (f + 1).toString();
              A += '<col min="' + D + '" max="' + D + '"', y >= 0 && (A += ' style="' + y.toString() + '"'), k && (A += ' width="' + k + '" customWidth="1"'), !1 !== o[f].autoWidth && (A += ' bestFit="1"'), !1 === o[f].visible && (A += ' hidden="1"'), A += "/>"
            }
          A += "</cols>"
        }
        if (O = A + O, A = O, O = null, c.length > 0 && (A += this._generateMergeSetting(c)), T && T.length > 0) {
          for (A += "<hyperlinks>", f = 0; f < T.length; f++) /\'?(\w+)\'?\!\$?[A-Za-z]{1,2}\$?\d+(:\$?[A-Za-z]{1,2}\$?\d+)?/.test(T[f].href) || /^\$?[A-Za-z]{1,2}\$?\d+(:\$?[A-Za-z]{1,2}\$?\d+)?$/.test(T[f].href) ? A += '<hyperlink ref="' + T[f].ref + '" display="' + T[f].value + '" location="' + T[f].href + '"/>' : (null == n.externalLinks && (n.externalLinks = []), n.externalLinks.push(T[f].href), A += '<hyperlink ref="' + T[f].ref + '" r:id="rId' + z + '"/>', z++);
          A += "</hyperlinks>"
        }
        if (A += '<pageMargins left="0.7" right="0.7" top="0.75" bottom="0.75" header="0.3" footer="0.3"/>', n.tables && n.tables.length > 0) {
          for (A += '<tableParts count="' + n.tables.length + '">', f = 0; f < n.tables.length; f++) A += '<tablePart r:id="rId' + z + '"/>', z++;
          A += "</tableParts>"
        }
        A += "</worksheet>", i.file("sheet" + r + ".xml", this._xmlDescription + A), A = null;
        var N = '<Override ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml" PartName="/xl/worksheets/sheet' + r + '.xml"/>';
        this._contentTypes.unshift(N), this._props.unshift(t.Workbook._escapeXML(n.name) || "Sheet" + r);
        var B = '<Relationship Target="worksheets/sheet' + r + '.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet" Id="rId' + r + '"/>';
        this._xlRels.unshift(B);
        var W = '<sheet r:id="rId' + r + '" sheetId="' + r + '" name="' + (t.Workbook._escapeXML(n.name) || "Sheet" + r) + '"' + (!1 === n.visible ? ' state="hidden"' : "") + "/>";
        this._worksheets.unshift(W)
      }, s._generateSharedStringsDoc = function() {
        var e, t = '<sst xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" count="' + this._sharedStrings[1] + '" uniqueCount="' + this._sharedStrings[0].length + '">';
        for (e = 0; e < this._sharedStrings[0].length; e++) {
          t += "<si>";
          var s = this._sharedStrings[0][e];
          if (s && 0 === s.indexOf("{RichTextMark}")) try {
            var l = JSON.parse(s.substring(14));
            if (l && l.length > 0) {
              for (var i = 0; i < l.length; i++) {
                t += "<r>";
                var r = l[i];
                r.font && (t += this._generateFontStyle(r.font, !0).replace(/font/g, "rPr")), t += this._generatePlainText(r.text), t += "</r>"
              }
              t += "</si>"
            }
          } catch (e) {
            t += this._generatePlainText(s) + "</si>"
          } else t += this._generatePlainText(s) + "</si>"
        }
        return t + "</sst>"
      }, s._generatePlainText = function(s) {
        var l = "<t";
        return (e.isNullOrWhiteSpace(s) || /^\s+\w*|\w*\s+$/.test(s)) && (l += ' xml:space="preserve"'), l += ">" + t.Workbook._escapeXML(s) + "</t>"
      }, s._generateTable = function(e, t) {
        var s = this._tables.length + 1,
          l = "table" + s + ".xml";
        e.fileName = l, this._tables.push(l);
        var i = '<table ref="' + e.range + '" displayName="' + e.name + '" name="' + e.name + '" id="' + s + '" ' + (!1 === e.showHeaderRow ? 'headerRowCount="0" ' : "") + (!0 === e.showTotalRow ? 'totalsRowCount="1" ' : 'totalsRowShown="0" ') + ' xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">';
        !1 !== e.showHeaderRow && (i += this._generateTableFilterSetting(e.range, e.showTotalRow, e.columns)), i += '<tableColumns count="' + e.columns.length + '">';
        for (var r = "", n = 0; n < e.columns.length; n++) {
          var o = e.columns[n];
          r += '<tableColumn name="' + o.name + '" id="' + (n + 1) + '" ', o.totalRowFunction ? this._tableColumnFunctions.indexOf(o.totalRowFunction) > -1 ? r += 'totalsRowFunction="' + o.totalRowFunction + '"/>' : r += 'totalsRowFunction="custom"><totalsRowFormula>' + o.totalRowFunction + "</totalsRowFormula></tableColumn>" : r += (o.totalRowLabel ? 'totalsRowLabel="' + o.totalRowLabel + '"' : "") + "/>"
        }
        if (i += r + "</tableColumns>", i += '<tableStyleInfo name="' + e.style.name + '" showColumnStripes="' + (e.showBandedColumns ? "1" : "0") + '" showRowStripes="' + (e.showBandedRows ? "1" : "0") + '" showLastColumn="' + (e.alterLastColumn ? "1" : "0") + '" showFirstColumn="' + (e.alterFirstColumn ? "1" : "0") + '"/></table>', !this._isBuiltInStyleName(e.style.name)) {
          var a = JSON.stringify(e.style); - 1 === this._tableStyles.indexOf(a) && this._tableStyles.push(a)
        }
        t.file(l, this._xmlDescription + i), i = null
      }, s._generateTableFilterSetting = function(e, s, l) {
        var i = e;
        if (s) {
          var r = i.indexOf(":") + 1,
            n = t.Workbook.tableAddress(i.substring(i.indexOf(":") + 1));
          n.row -= 1, i = i.substring(0, r) + t.Workbook.xlsxAddress(n.row, n.col)
        }
        for (var o = '<autoFilter ref="' + i + '"', a = "", h = 0; h < l.length; h++) !1 === l[h].showFilterButton && (a += '<filterColumn hiddenButton="1" colId="' + h + '"/>');
        return o += "" === a ? "/>" : ">" + a + "</autoFilter>"
      }, s._generateHyperlinkRel = function(e) {
        for (var t = "", s = 0; s < e.length; s++) t += '<Relationship TargetMode="External" Target="' + e[s] + '" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/hyperlink" Id="rId' + (s + 1) + '"/>';
        return t
      }, s._getDxfs = function() {
        for (var t, s = this, l = 0; l < this._tableStyles.length; l++) t = JSON.parse(this._tableStyles[l]), Object.keys(t).forEach(function(l) {
          var i, r, n = t[l];
          n && !e.isString(n) && (s._isEmptyStyleEle(n) || (i = JSON.stringify(n), -1 === (r = s._dxfs.indexOf(i)) && (r = s._dxfs.push(i) - 1, n.styleIndex = r)))
        }), this._tableStyles[l] = t
      }, s._generateDxfs = function() {
        for (var e, t = '<dxfs count="' + this._dxfs.length + '">', s = 0; s < this._dxfs.length; s++) t += "<dxf>", (e = JSON.parse(this._dxfs[s])).font && (t += this._generateFontStyle(e.font)), e.fill && e.fill.color && (t += this._generateFillStyle("solid", e.fill.color, !0)), e.borders && !this._isEmpty(e.borders) && (e.borders = this._extend({}, e.borders), this._parseBorder(e.borders, !1), t += this._generateBorderStyle(e.borders, !0)), t += "</dxf>";
        return t += "</dxfs>"
      }, s._generateTableStyles = function() {
        for (var t, s, l, i, r, n, o = '<tableStyles count="' + this._tableStyles.length + '" defaultTableStyle="TableStyleMedium9" defaultPivotStyle="PivotStyleLight16">', a = 0; a < this._tableStyles.length; a++) {
          t = this._tableStyles[a], s = Object.keys(t), r = "", n = 0;
          for (var h = 0; h < s.length; h++)
            if (l = s[h], i = t[l], !e.isString(i)) {
              switch (n++, r += "<tableStyleElement", null != i.styleIndex && (r += ' dxfId="' + i.styleIndex + '"'), l) {
                case "firstBandedColumnStyle":
                  r += ' type="firstColumnStripe"', null != i.size && (r += ' size="' + i.size + '"');
                  break;
                case "secondBandedColumnStyle":
                  r += ' type="secondColumnStripe"', null != i.size && (r += ' size="' + i.size + '"');
                  break;
                case "firstBandedRowStyle":
                  r += ' type="firstRowStripe"', null != i.size && (r += ' size="' + i.size + '"');
                  break;
                case "secondBandedRowStyle":
                  r += ' type="secondRowStripe"', null != i.size && (r += ' size="' + i.size + '"');
                  break;
                default:
                  r += ' type="' + l.substring(0, l.length - 5) + '"'
              }
              r += "/>"
            }
          n > 0 && (o += '<tableStyle count="' + n + '" name="' + t.name + '" pivot="0">', o += r + "</tableStyle>")
        }
        return o += "</tableStyles>"
      }, s._isEmptyStyleEle = function(t) {
        return this._isEmpty(t.borders) && (this._isEmpty(t.fill) || e.isNullOrWhiteSpace(t.fill.color)) && (this._isEmpty(t.font) || !0 !== t.font.bold && e.isNullOrWhiteSpace(t.font.color) && e.isNullOrWhiteSpace(t.font.family) && !0 !== t.font.italic && null == t.font.size && !0 !== t.font.underline)
      }, s._getTableFileName = function(e, t) {
        for (var s = "", l = 0; l < e.length; l++) {
          var i = e[l];
          if (i.name === t) {
            s = i.fileName;
            break
          }
        }
        return s
      }, s._getColor = function(e, t) {
        var s, l, i, r, n;
        return -1 === e.search(/fgcolor/i) && -1 === e.search(/bgcolor/i) && t || -1 === e.search(/color/i) && !t ? null : (-1 === (s = e.indexOf("<fgColor")) && (s = e.indexOf("<bgColor")), -1 !== (e = t ? e.substring(s, e.indexOf("/>")) : e.substring(e.indexOf("<color"))).indexOf("rgb=") ? (r = this._getAttr(e, "rgb")) && 8 === r.length && (r = r.substring(2)) : -1 !== e.indexOf("indexed") ? (i = +this._getAttr(e, "indexed"), r = this._indexedColors[i] || "") : (l = +this._getAttr(e, "theme"), -1 !== e.indexOf("tint") && (n = +this._getAttr(e, "tint")), r = this._getThemeColor(l, n)), r && "#" === r[0] ? r : "#" + r)
      }, s._getThemeColor = function(t, s) {
        var l, i, r = this._colorThemes[t];
        return null != s ? (l = new e.Color("#" + r), i = l.getHsl(), i[2] = s < 0 ? i[2] * (1 + s) : i[2] * (1 - s) + (1 - 1 * (1 - s)), (l = e.Color.fromHsl(i[0], i[1], i[2])).toString().substring(1)) : r
      }, s._parseColor = function(t) {
        var s = new e.Color(t);
        return s.a < 1 && (s = e.Color.toOpaque(s)), s.toString()
      }, s._getsBaseSharedFormulas = function(e) {
        var t, s, l, i = e.match(/\<f[^<]*ref[^<]*>[^<]+(?=\<\/f>)/g);
        if (this._sharedFormulas = [], i && i.length > 0)
          for (var r = 0; r < i.length; r++) t = i[r], s = this._getAttr(t, "si"), l = (l = this._getAttr(t, "ref")) ? l.substring(0, l.indexOf(":")) : "", t = t.replace(/(\<f.*>)(.+)/, "$2"), this._sharedFormulas[+s] = this._parseSharedFormulaInfo(l, t)
      }, s._parseSharedFormulaInfo = function(e, s) {
        var l, i, r, n, o, a = s.match(/(\'?\w+\'?\!)?(\$?[A-Za-z]+)(\$?\d+)/g);
        if (n = t.Workbook.tableAddress(e), a && a.length > 0) {
          o = [];
          for (var h = 0; h < a.length; h++) l = a[h], s = s.replace(l, "{" + h + "}"), (i = l.indexOf("!")) > 0 && (r = l.substring(0, i), l = l.substring(i + 1)), o[h] = {
            cellAddress: t.Workbook.tableAddress(l),
            sheetRef: r
          }
        }
        return {
          cellRef: n,
          formula: s,
          formulaRefs: o
        }
      }, s._getSharedFormula = function(e, s) {
        var l, i, r, n, o, a, h, u, c, f, d, p;
        if (this._sharedFormulas && this._sharedFormulas.length > 0 && (l = this._sharedFormulas[+e])) {
          if (c = l.formula, (f = l.formulaRefs) && f.length > 0) {
            i = t.Workbook.tableAddress(s), h = l.cellRef ? l.cellRef.row : 0, u = l.cellRef ? l.cellRef.col : 0, r = i.row - h, n = i.col - u;
            for (var m = 0; m < f.length; m++) o = (d = f[m]).cellAddress.row + (d.cellAddress.absRow ? 0 : r), a = d.cellAddress.col + (d.cellAddress.absCol ? 0 : n), p = t.Workbook.xlsxAddress(o, a, d.cellAddress.absRow, d.cellAddress.absCol), null != d.sheetRef && "" !== d.sheetRef && (p = d.sheetRef + "!" + p), c = c.replace("{" + m + "}", p)
          }
          return c
        }
        return ""
      }, s._convertDate = function(t) {
        var s, l, i = new Date(1900, 0, 0),
          r = Date.UTC(1900, 0, 0),
          n = "[object Date]" === Object.prototype.toString.call(t),
          o = 6e4 * ((n ? t.getTimezoneOffset() : (new Date).getTimezoneOffset()) - i.getTimezoneOffset()),
          a = i.getTime() - r - 6e4 * i.getTimezoneOffset();
        return n ? (t.getTime() - i.getTime() - o + a) / 864e5 + 1 : e.isNumber(t) ? (s = t > 59 ? 1 : 0, l = new Date(1e3 * Math.round((+i + 864e5 * (t - s)) / 1e3)), 0 !== (o = 6e4 * (l.getTimezoneOffset() - i.getTimezoneOffset())) ? new Date(1e3 * Math.round((+i + o - a + 864e5 * (t - s)) / 1e3)) : l) : null
      }, s._parseBorder = function(s, l) {
        for (var i in {
          left: 0,
          right: 0,
          top: 0,
          bottom: 0,
          diagonal: 0
        }) {
          var r = s[i];
          r && (e.isString(r.color) && (r.color = this._parseColor(r.color)), null == r.style || e.isString(r.style) || (r.style = t.Workbook._parseBorderTypeToString(e.asEnum(r.style, t.BorderStyle, !1))), !l && r.color && "#c6c6c6" === r.color.toLowerCase() && "thin" === r.style && (s[i] = null))
        }
      }, s._applyDefaultBorder = function(t) {
        if (t.fill && t.fill.color) {
          null == t.borders && (t.borders = {});
          for (var s in {
            left: 0,
            right: 0,
            top: 0,
            bottom: 0
          }) null == t.borders[s] && (t.borders[s] = {
            style: e.xlsx.BorderStyle.Thin,
            color: "#C6C6C6"
          })
        }
      }, s._resolveStyleInheritance = function(e) {
        var t;
        if (!e.basedOn) return e;
        for (var s in e.basedOn)
          if ("basedOn" === s) {
            t = this._resolveStyleInheritance(e.basedOn);
            for (s in t) {
              l = t[s];
              e[s] = null == e[s] ? l : this._extend(e[s], l)
            }
          } else {
            var l = e.basedOn[s];
            e[s] = null == e[s] ? l : this._extend(e[s], l)
          }
        return delete e.basedOn, e
      }, s._parsePixelToCharWidth = function(e) {
        return null == e || isNaN(+e) ? null : ((+e - 5) / 7 * 100 + .5) / 100
      }, s._parseCharWidthToPixel = function(e) {
        return null == e || isNaN(+e) ? null : Math.floor((256 * +e + 128 / 7) / 256 * 7) + 5
      }, s._parseCharCountToCharWidth = function(e) {
        return null == e || isNaN(+e) ? null : (7 * +e + 5) / 7 * 256 / 256
      }, s._numAlpha = function(e) {
        var t = Math.floor(e / 26) - 1;
        return (t > -1 ? this._numAlpha(t) : "") + this._alphabet.charAt(e % 26)
      }, s._alphaNum = function(e) {
        return e.length > 1 ? (this._alphabet.indexOf(e[0]) + 1) * Math.pow(26, e.length - 1) + this._alphaNum(e.substring(1)) : this._alphabet.indexOf(e)
      }, s._typeOf = function(e) {
        return {}.toString.call(e).match(/\s([a-zA-Z]+)/)[1].toLowerCase()
      }, s._extend = function(t, s) {
        if (e.isObject(t) && e.isObject(s)) {
          for (var l in s) {
            var i = s[l];
            e.isObject(i) && null != t[l] ? this._extend(t[l], i) : null != i && null == t[l] && (t[l] = i)
          }
          return t
        }
        return s
      }, s._isEmpty = function(e) {
        var t = Object.prototype.hasOwnProperty;
        if (null == e) return !0;
        if (e.length > 0) return !1;
        if (0 === e.length) return !0;
        for (var s in e)
          if (t.call(e, s)) return !1;
        return !0
      }, s._cloneStyle = function(e) {
        var t;
        if (null == e || "object" != typeof e) return e;
        t = {};
        for (var s in e) e.hasOwnProperty(s) && (t[s] = this._cloneStyle(e[s]));
        return t
      }, s._cloneColumnsStyle = function(e) {
        for (var t, s = [], l = 0; l < e.length; l++)(t = e[l]) && t.style && (s[l] = this._cloneStyle(t.style));
        return s
      }, s._getSheetIndex = function(e) {
        return e = e.substring(0, e.lastIndexOf(".xml")), +e.substring(e.lastIndexOf("sheet") + 5)
      }, s._checkValidMergeCell = function(e, t, s, l, i) {
        for (var r = 0; r < e.length; r++) {
          var n = e[r],
            o = +n[0].match(/\d*/g).join("") - 1,
            a = this._alphaNum(n[0].match(/[a-zA-Z]*/g)[0]),
            h = +n[1].match(/\d*/g).join("") - 1,
            u = this._alphaNum(n[1].match(/[a-zA-Z]*/g)[0]);
          if (!(t > h || t + s - 1 < o || l > u || l + i - 1 < a)) return !1
        }
        return !0
      }, s._getAttr = function(e, t) {
        var s = e.indexOf(t + '="');
        return s >= 0 ? (e = e.substr(s + t.length + 2)).substring(0, e.indexOf('"')) : ""
      }, s._getChildNodeValue = function(e, t) {
        var s = e.indexOf(t + ' val="');
        return s >= 0 ? (e = e.substr(s + t.length + 6)).substring(0, e.indexOf('"')) : ""
      }, s._getSheetIndexBySheetName = function(e, t) {
        for (var s = 0; s < e.sheets.length; s++)
          if (e.sheets[s].name === t) return s;
        return -1
      }, s._alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", s._indexedColors = ["000000", "FFFFFF", "FF0000", "00FF00", "0000FF", "FFFF00", "FF00FF", "00FFFF", "000000", "FFFFFF", "FF0000", "00FF00", "0000FF", "FFFF00", "FF00FF", "00FFFF", "800000", "008000", "000080", "808000", "800080", "008080", "C0C0C0", "808080", "9999FF", "993366", "FFFFCC", "CCFFFF", "660066", "FF8080", "0066CC", "CCCCFF", "000080", "FF00FF", "FFFF00", "00FFFF", "800080", "800000", "008080", "0000FF", "00CCFF", "CCFFFF", "CCFFCC", "FFFF99", "99CCFF", "FF99CC", "CC99FF", "FFCC99", "3366FF", "33CCCC", "99CC00", "FFCC00", "FF9900", "FF6600", "666699", "969696", "003366", "339966", "003300", "333300", "993300", "993366", "333399", "333333", "000000", "FFFFFF"], s._numFmts = ["General", "0", "0.00", "#,##0", "#,##0.00", , , "$#,##0.00_);($#,##0.00)", , "0%", "0.00%", "0.00E+00", "# ?/?", "# ??/??", "m/d/yyyy", "d-mmm-yy", "d-mmm", "mmm-yy", "h:mm AM/PM", "h:mm:ss AM/PM", "h:mm", "h:mm:ss", "m/d/yy h:mm", , , , , , , , , , , , , , , "#,##0 ;(#,##0)", "#,##0 ;[Red](#,##0)", "#,##0.00;(#,##0.00)", "#,##0.00;[Red](#,##0.00)", , , , , "mm:ss", "[h]:mm:ss", "mmss.0", "##0.0E+0", "@"], s._tableColumnFunctions = "average, count, countNums, max, min, stdDev, sum, var", s._xmlDescription = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>', s._workbookNS = "http://schemas.openxmlformats.org/spreadsheetml/2006/main", s._relationshipsNS = "http://schemas.openxmlformats.org/package/2006/relationships", s._defaultFontName = "Calibri", s._defaultFontSize = 11, s._macroEnabled = !1, s._defaultColorThemes = ["FFFFFF", "000000", "EEECE1", "1F497D", "4F818D", "C0504D", "9BBB59", "8064A2", "4BACC6", "F79646"], s
    }();
    t._xlsx = s;
    var l = function() {
        function e(e) {
          this.value = e
        }
        return e.prototype.then = function(t, s) {
          return new e(t ? t(this.value) : null)
        }, e.prototype.catch = function(e) {
          return this.then(null, e)
        }, e.resolve = function() {
          return new e
        }, e
      }(),
      i = function() {
        function e(e) {
          this._obj = e
        }
        return e.prototype.loadAsync = function() {
          for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
          var s = (i = this._obj).load.apply(i, e);
          return new l(this._wrapOrNull(s));
          var i
        }, e.prototype.file = function() {
          for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
          var s = (l = this._obj).file.apply(l, e);
          return this._wrapOrNull(s);
          var l
        }, Object.defineProperty(e.prototype, "name", {
          get: function() {
            return this._obj.name
          },
          enumerable: !0,
          configurable: !0
        }), e.prototype.async = function() {
          for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
          var s;
          return s = e && e.length > 0 && "uint8array" === e[0].toLowerCase() ? this._obj.asUint8Array() : this._obj.asText(), new l(s)
        }, e.prototype.folder = function(e) {
          var t = this._obj.folder(e);
          return this._wrapOrNull(t)
        }, e.prototype.forEach = function(t) {
          var s = this._obj.root,
            l = s.length,
            i = this._obj.files;
          for (var r in i) r.length > l && r.substr(0, l).toLowerCase() === s && t(r.substr(l), new e(i[r]))
        }, e.prototype._wrapOrNull = function(t) {
          return null != t ? new e(t) : null
        }, e
      }()
  }(e.xlsx || (e.xlsx = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var s in t) t.hasOwnProperty(s) && (e[s] = t[s])
      };
    return function(t, s) {
      function l() {
        this.constructor = t
      }
      e(t, s), t.prototype = null === s ? Object.create(s) : (l.prototype = s.prototype, new l)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var s = function() {
      function s() {}
      return Object.defineProperty(s.prototype, "sheets", {
        get: function() {
          return null == this._sheets && (this._sheets = []), this._sheets
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "styles", {
        get: function() {
          return null == this._styles && (this._styles = []), this._styles
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "definedNames", {
        get: function() {
          return null == this._definedNames && (this._definedNames = []), this._definedNames
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "colorThemes", {
        get: function() {
          return null == this._colorThemes && (this._colorThemes = []), this._colorThemes
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "reservedContent", {
        get: function() {
          return null == this._reservedContent && (this._reservedContent = {}), this._reservedContent
        },
        set: function(e) {
          this._reservedContent = e
        },
        enumerable: !0,
        configurable: !0
      }), s.prototype.save = function(e) {
        var l = t._xlsx.save(this),
          i = !(!this._reservedContent || !this._reservedContent.macros);
        return e && s._saveToFile(l.base64, e, i), l.base64
      }, s.prototype.saveAsync = function(e, l, i) {
        var r = this;
        t._xlsx.saveAsync(r, i).then(function(t) {
          if (e) {
            var i = !(!r._reservedContent || !r._reservedContent.macros);
            s._saveToFile(t, e, i)
          }
          l && l(t)
        })
      }, s.prototype.load = function(e) {
        var s = t._xlsx.load(this._getBase64String(e));
        this._deserialize(s), s = null
      }, s.prototype.loadAsync = function(e, s, l) {
        var i = this;
        t._xlsx.loadAsync(i._getBase64String(e)).then(function(e) {
          i._deserialize(e), e = null, s(i)
        }).catch(l)
      }, s.prototype._serialize = function() {
        var e = {
          sheets: []
        };
        return e.sheets = this._serializeWorkSheets(), this._styles && this._styles.length > 0 && (e.styles = this._serializeWorkbookStyles()), this._reservedContent && (e.reservedContent = this._reservedContent), null != this.activeWorksheet && !isNaN(this.activeWorksheet) && this.activeWorksheet >= 0 && (e.activeWorksheet = this.activeWorksheet), this.application && (e.application = this.application), this.company && (e.company = this.company), null != this.created && (e.created = this.created), this.creator && (e.creator = this.creator), this.lastModifiedBy && (e.lastModifiedBy = this.lastModifiedBy), null != this.modified && (e.modified = this.modified), this._definedNames && this._definedNames.length > 0 && (e.definedNames = this._serializeDefinedNames()), this._colorThemes && this._colorThemes.length > 0 && (e.colorThemes = this._colorThemes.slice()), e
      }, s.prototype._deserialize = function(e) {
        this._deserializeWorkSheets(e.sheets), e.styles && e.styles.length > 0 && this._deserializeWorkbookStyles(e.styles), this.activeWorksheet = e.activeWorksheet, this.application = e.application, this.company = e.company, this.created = e.created, this.creator = e.creator, this.lastModifiedBy = e.lastModifiedBy, this.modified = e.modified, this.reservedContent = e.reservedContent, e.definedNames && e.definedNames.length > 0 && this._deserializeDefinedNames(e.definedNames), e.colorThemes && e.colorThemes.length > 0 && (this._colorThemes = e.colorThemes.slice())
      }, s.prototype._addWorkSheet = function(e, t) {
        null == this._sheets && (this._sheets = []), null == t || isNaN(t) ? this._sheets.push(e) : this._sheets[t] = e
      }, s._saveToFile = function(e, t, l) {
        var i, r, n, o, a, h, u = window.document,
          c = l ? "xlsm" : "xlsx",
          f = "xlsm" === c ? "application/vnd.ms-excel.sheet.macroEnabled.12" : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if ((r = t.lastIndexOf(".")) < 0) t += "." + c;
        else {
          if (0 === r) throw "Invalid file name.";
          "" === (i = t.substring(r + 1)) ? t += "." + c: i !== c && (t += "." + c)
        }
        n = new Blob([s._base64DecToArr(e)], {
          type: f
        }), navigator.msSaveBlob ? navigator.msSaveBlob(n, t) : URL.createObjectURL ? (a = u.createElement("a"), h = function(e) {
          var t = u.createEvent("MouseEvents");
          t.initMouseEvent("click", !0, !1, window, 0, 0, 0, 0, 0, !1, !1, !1, !1, 0, null), e.dispatchEvent(t)
        }, a.download = t, a.href = URL.createObjectURL(n), h(a), a = null) : (o = new FileReader, a = u.createElement("a"), h = function(e) {
          var t = u.createEvent("MouseEvents");
          t.initMouseEvent("click", !0, !1, window, 0, 0, 0, 0, 0, !1, !1, !1, !1, 0, null), e.dispatchEvent(t)
        }, o.onload = function() {
          a.download = t, a.href = o.result, h(a), a = null
        }, o.readAsDataURL(n))
      }, s.prototype._getBase64String = function(e) {
        var t;
        if (null == e || 0 === e.length) throw "Invalid xlsx file content.";
        return -1 !== (t = e.search(/base64,/i)) && (e = e.substring(t + 7)), e
      }, s.toXlsxDateFormat = function(t) {
        var s;
        if (1 === t.length) switch (t) {
          case "r":
          case "R":
            return "ddd, dd MMM yyyy HH:mm:ss &quot;GMT&quot;";
          case "u":
            return "yyyy-MM-dd&quot;T&quot;HH:mm:ss&quot;Z&quot;";
          case "o":
          case "O":
            s = (s = e.culture.Globalize.calendar.patterns[t]).replace(/f+k/gi, "000");
            break;
          default:
            s = e.culture.Globalize.calendar.patterns[t]
        }
        return s || (s = t), s = s.replace(/"/g, "").replace(/tt/, "AM/PM").replace(/t/, "A/P").replace(/M+/gi, function(e) {
          return e.toLowerCase()
        }).replace(/g+y+/gi, function(e) {
          return e.substring(0, e.indexOf("y")) + "e"
        }), /FY|Q/i.test(s) ? "General" : s
      }, s.toXlsxNumberFormat = function(t) {
        var s, l, i, r, n, o, a, h = -1,
          u = t ? t.toLowerCase() : "",
          c = [];
        if (/^[ncpfdg]\d*.*,*$/.test(u) && (s = u[0], l = this._formatMap[s]), l) {
          if (r = u.split(","), "c" === s && ("​" === (i = (a = t ? t.match(/([a-z])(\d*)(,*)(.*)/i) : null) && a[4] ? a[4] : e.culture.Globalize.numberFormat.currency.symbol) && (i = ""), l = l.replace(/\{1\}/g, i)), u.length > 1 ? h = parseInt(r[0].substr(1)) : "d" !== s && (h = 2), !isNaN(h))
            for (o = 0; o < h; o++) c.push(0);
          for (o = 0; o < r.length - 1; o++) c.push(",");
          n = c.length > 0 ? "d" === s ? l.replace(/\{0\}/g, c.join("")) : l.replace(/\{0\}/g, (!isNaN(h) && h > 0 ? "." : "") + c.join("")) : "d" === s ? l.replace(/\{0\}/g, "0") : l.replace(/\{0\}/g, "")
        } else n = u;
        return n
      }, s.fromXlsxFormat = function(t) {
        var s, l, i, r, n, o, a, h, u, c = [],
          f = e.culture.Globalize.numberFormat.currency.symbol;
        if (!t || "General" === t) return [""];
        for (l = (t = t.replace(/;@/g, "").replace(/&quot;?/g, "")).split(";"), r = 0; r < l.length; r++) {
          if (i = l[r], /[hsmy\:]/i.test(i)) s = i.replace(/\[\$\-.+\]/g, "").replace(/(\\)(.)/g, "$2").replace(/H+/g, function(e) {
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
          else if (o = i.lastIndexOf("."), a = i.lastIndexOf("0"), h = i.lastIndexOf(","), s = i.search(/\[\$([^\-\]]+)[^\]]*\]/) > -1 || i.indexOf(f) > -1 && -1 === i.search(/\[\$([\-\]]+)[^\]]*\]/) ? "c" : "%" === i[t.length - 1] ? "p" : "n", s += o > -1 && o < a ? i.substring(o, a).length : "0", /^0+,*$/.test(i) && (s = "d" + ((a = i.lastIndexOf("0")) + 1)), h > -1 && a > -1 && a < h)
            for (u = i.substring(a + 1, h + 1).split(""), n = 0; n < u.length; n++) s += ",";
          c.push(s)
        }
        return c
      }, s._parseCellFormat = function(e, t) {
        return t ? this.toXlsxDateFormat(e) : this.toXlsxNumberFormat(e)
      }, s._parseExcelFormat = function(t) {
        if (void 0 !== t && null !== t && void 0 !== t.value && null !== t.value && !isNaN(t.value)) {
          var s = t.style && t.style.format ? t.style.format : "";
          return t.isDate || e.isDate(t.value) ? this.fromXlsxFormat(s)[0] : !e.isNumber(t.value) || s && "General" !== s ? e.isNumber(t.value) || "" === t.value ? this.fromXlsxFormat(s)[0] : s : e.isInt(t.value) ? "d" : "f2"
        }
      }, s.xlsxAddress = function(e, t, s, l) {
        var i = s ? "$" : "",
          r = null == l ? i : l ? "$" : "";
        return (isNaN(t) ? "" : r + this._numAlpha(t)) + (isNaN(e) ? "" : i + (e + 1).toString())
      }, s.tableAddress = function(e) {
        var t = /^((\$?)([A-Za-z]+))?((\$?)(\d+))?$/,
          s = e && t.exec(e),
          l = {};
        return s ? (s[3] && (l.col = this._alphaNum(s[3]), l.absCol = !!s[2]), s[6] && (l.row = +s[6] - 1, l.absRow = !!s[5]), l) : null
      }, s._parseHAlignToString = function(e) {
        switch (e) {
          case S.Left:
            return "left";
          case S.Center:
            return "center";
          case S.Right:
            return "right";
          case S.Justify:
            return "justify";
          default:
            return "general"
        }
      }, s._parseStringToHAlign = function(e) {
        var t = e ? e.toLowerCase() : "";
        return "left" === t ? S.Left : "center" === t ? S.Center : "right" === t ? S.Right : "justify" === t ? S.Justify : S.General
      }, s._parseVAlignToString = function(e) {
        switch (e) {
          case w.Bottom:
            return "bottom";
          case w.Center:
            return "center";
          case w.Top:
            return "top";
          default:
            return null
        }
      }, s._parseStringToVAlign = function(e) {
        var t = e ? e.toLowerCase() : "";
        return "top" === t ? w.Top : "center" === t ? w.Center : "bottom" === t ? w.Bottom : null
      }, s._parseBorderTypeToString = function(e) {
        switch (e) {
          case x.Dashed:
            return "dashed";
          case x.Dotted:
            return "dotted";
          case x.Double:
            return "double";
          case x.Hair:
            return "hair";
          case x.Medium:
            return "medium";
          case x.MediumDashDotDotted:
            return "mediumDashDotDot";
          case x.MediumDashDotted:
            return "mediumDashDot";
          case x.MediumDashed:
            return "mediumDashed";
          case x.SlantedMediumDashDotted:
            return "slantDashDot";
          case x.Thick:
            return "thick";
          case x.Thin:
            return "thin";
          case x.ThinDashDotDotted:
            return "dashDotDot";
          case x.ThinDashDotted:
            return "dashDot";
          case x.None:
          default:
            return "none"
        }
      }, s._parseStringToBorderType = function(e) {
        return "dashed" === e ? x.Dashed : "dotted" === e ? x.Dotted : "double" === e ? x.Double : "hair" === e ? x.Hair : "medium" === e ? x.Medium : "mediumDashDotDot" === e ? x.MediumDashDotDotted : "mediumDashDot" === e ? x.MediumDashDotted : "mediumDashed" === e ? x.MediumDashed : "slantDashDot" === e ? x.SlantedMediumDashDotted : "thick" === e ? x.Thick : "thin" === e ? x.Thin : "dashDotDot" === e ? x.ThinDashDotDotted : "dashDot" === e ? x.ThinDashDotted : null
      }, s._escapeXML = function(e) {
        return "string" == typeof e ? e.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;") : ""
      }, s._unescapeXML = function(e) {
        return "string" == typeof e ? e.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&quot;/g, '"').replace(/&#x27;/g, "'").replace(/&#x2F;/g, "/") : ""
      }, s._numAlpha = function(e) {
        var t = Math.floor(e / 26) - 1;
        return (t > -1 ? this._numAlpha(t) : "") + this._alphabet.charAt(e % 26)
      }, s._alphaNum = function(e) {
        var t = 0;
        return e && (e = e.toUpperCase()), 2 === e.length && (t = this._alphaNum(e.charAt(0)) + 1), 26 * t + this._alphabet.indexOf(e.substr(-1))
      }, s._b64ToUint6 = function(e) {
        return e > 64 && e < 91 ? e - 65 : e > 96 && e < 123 ? e - 71 : e > 47 && e < 58 ? e + 4 : 43 === e ? 62 : 47 === e ? 63 : 0
      }, s._base64DecToArr = function(e, t) {
        for (var s, l, i = e.replace(/[^A-Za-z0-9\+\/]/g, ""), r = i.length, n = t ? Math.ceil((3 * r + 1 >> 2) / t) * t : 3 * r + 1 >> 2, o = new Uint8Array(n), a = 0, h = 0, u = 0; u < r; u++)
          if (l = 3 & u, a |= this._b64ToUint6(i.charCodeAt(u)) << 18 - 6 * l, 3 === l || r - u == 1) {
            for (s = 0; s < 3 && h < n; s++, h++) o[h] = a >>> (16 >>> s & 24) & 255;
            a = 0
          }
        return o
      }, s._uint6ToB64 = function(e) {
        return e < 26 ? e + 65 : e < 52 ? e + 71 : e < 62 ? e - 4 : 62 === e ? 43 : 63 === e ? 47 : 65
      }, s._base64EncArr = function(e) {
        for (var t = 2, s = "", l = e.length, i = 0, r = 0; r < l; r++) t = r % 3, r > 0 && 4 * r / 3 % 76 == 0 && (s += "\r\n"), i |= e[r] << (16 >>> t & 24), 2 !== t && e.length - r != 1 || (s += String.fromCharCode(this._uint6ToB64(i >>> 18 & 63), this._uint6ToB64(i >>> 12 & 63), this._uint6ToB64(i >>> 6 & 63), this._uint6ToB64(63 & i)), i = 0);
        return s.substr(0, s.length - 2 + t) + (2 === t ? "" : 1 === t ? "=" : "==")
      }, s.prototype._serializeWorkSheets = function() {
        var e, t, s = [];
        for (t = 0; t < this._sheets.length; t++)(e = this._sheets[t]) && (s[t] = e._serialize());
        return s
      }, s.prototype._serializeWorkbookStyles = function() {
        var e, t, s = [];
        for (t = 0; t < this._styles.length; t++)(e = this._styles[t]) && (s[t] = e._serialize());
        return s
      }, s.prototype._serializeDefinedNames = function() {
        var e, t, s = [];
        for (t = 0; t < this._definedNames.length; t++)(e = this._definedNames[t]) && (s[t] = e._serialize());
        return s
      }, s.prototype._serializeTableStyles = function() {
        var e, t, s = [];
        for (t = 0; t < this._tableStyles.length; t++)(e = this._tableStyles[t]) && (s[t] = e._serialize());
        return s
      }, s.prototype._deserializeWorkSheets = function(t) {
        var s, i, r;
        for (this._sheets = [], e.assert(null != t, "workSheets should not be null."), r = 0; r < t.length; r++)(i = t[r]) && ((s = new l)._deserialize(i), this._sheets[r] = s)
      }, s.prototype._deserializeWorkbookStyles = function(e) {
        var t, s, l;
        for (this._styles = [], l = 0; l < e.length; l++)(s = e[l]) && ((t = new a)._deserialize(s), this._styles[l] = t)
      }, s.prototype._deserializeDefinedNames = function(e) {
        var t, s, l;
        for (this._definedNames = [], l = 0; l < e.length; l++)(s = e[l]) && ((t = new d)._deserialize(s), this._definedNames[l] = t)
      }, s.prototype._deserializeTableStyles = function(e) {
        var t, s, l;
        for (this._tableStyles = [], l = 0; l < e.length; l++)(s = e[l]) && ((t = new g)._deserialize(s), this._tableStyles[l] = t)
      }, s._alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", s._formatMap = {
        n: "#,##0{0}",
        c: "{1}#,##0{0}_);({1}#,##0{0})",
        p: "#,##0{0}%",
        f: "0{0}",
        d: "{0}",
        g: "0{0}"
      }, s
    }();
    t.Workbook = s;
    var l = function() {
      function e() {}
      return Object.defineProperty(e.prototype, "columns", {
        get: function() {
          return null == this._columns && (this._columns = []), this._columns
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "rows", {
        get: function() {
          return null == this._rows && (this._rows = []), this._rows
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "tables", {
        get: function() {
          return null == this._tables && (this._tables = []), this._tables
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype._serialize = function() {
        var e;
        return this._checkEmptyWorkSheet() ? null : (e = {}, this.style && (e.style = this.style._serialize()), this._columns && this._columns.length > 0 && (e.columns = this._serializeWorkbookColumns()), this._rows && this._rows.length > 0 && (e.rows = this._serializeWorkbookRows()), this.frozenPane && (e.frozenPane = this.frozenPane._serialize()), this.name && (e.name = this.name), null != this.summaryBelow && (e.summaryBelow = this.summaryBelow), null != this.visible && (e.visible = this.visible), this._tables && this._tables.length > 0 && (e.tables = this._serializeTables()), e)
      }, e.prototype._deserialize = function(e) {
        var t, s;
        e.style && ((s = new a)._deserialize(e.style), this.style = s), e.columns && e.columns.length > 0 && this._deserializeWorkbookColumns(e.columns), e.rows && e.rows.length > 0 && this._deserializeWorkbookRows(e.rows), e.frozenPane && ((t = new o)._deserialize(e.frozenPane), this.frozenPane = t), this.name = e.name, this.summaryBelow = e.summaryBelow, this.visible = e.visible, e.tables && e.tables.length > 0 && this._deserializeTables(e.tables)
      }, e.prototype._addWorkbookColumn = function(e, t) {
        null == this._columns && (this._columns = []), null == t || isNaN(t) ? this._columns.push(e) : this._columns[t] = e
      }, e.prototype._addWorkbookRow = function(e, t) {
        null == this._rows && (this._rows = []), null == t || isNaN(t) ? this._rows.push(e) : this._rows[t] = e
      }, e.prototype._serializeWorkbookColumns = function() {
        var e, t, s = [];
        for (t = 0; t < this._columns.length; t++)(e = this._columns[t]) && (s[t] = e._serialize());
        return s
      }, e.prototype._serializeWorkbookRows = function() {
        var e, t, s = [];
        for (t = 0; t < this._rows.length; t++)(e = this._rows[t]) && (s[t] = e._serialize());
        return s
      }, e.prototype._serializeTables = function() {
        var e, t, s = [];
        for (t = 0; t < this._tables.length; t++)(e = this._tables[t]) && (s[t] = e._serialize());
        return s
      }, e.prototype._deserializeWorkbookColumns = function(e) {
        var t, s, l;
        for (this._columns = [], l = 0; l < e.length; l++)(t = e[l]) && ((s = new i)._deserialize(t), this._columns[l] = s)
      }, e.prototype._deserializeWorkbookRows = function(e) {
        var t, s, l;
        for (this._rows = [], l = 0; l < e.length; l++)(t = e[l]) && ((s = new r)._deserialize(t), this._rows[l] = s)
      }, e.prototype._deserializeTables = function(e) {
        var t, s, l;
        for (this._tables = [], l = 0; l < e.length; l++)(s = e[l]) && ((t = new p)._deserialize(s), this._tables[l] = t)
      }, e.prototype._checkEmptyWorkSheet = function() {
        return !(null != this._rows || null != this._columns || null != this.visible || null != this.summaryBelow || null != this.frozenPane || null != this.style || null != this.name && "" !== this.name || null != this._tables && 0 != this._tables.length)
      }, e
    }();
    t.WorkSheet = l;
    var i = function() {
      function e() {}
      return e.prototype._serialize = function() {
        var e;
        return this._checkEmptyWorkbookColumn() ? null : (e = {}, this.style && (e.style = this.style._serialize()), null != this.autoWidth && (e.autoWidth = this.autoWidth), null != this.width && (e.width = this.width), null != this.visible && (e.visible = this.visible), e)
      }, e.prototype._deserialize = function(e) {
        var t;
        e.style && ((t = new a)._deserialize(e.style), this.style = t), this.autoWidth = e.autoWidth, this.visible = e.visible, this.width = e.width
      }, e.prototype._checkEmptyWorkbookColumn = function() {
        return null == this.style && null == this.width && null == this.autoWidth && null == this.visible
      }, e
    }();
    t.WorkbookColumn = i;
    var r = function() {
      function e() {}
      return Object.defineProperty(e.prototype, "cells", {
        get: function() {
          return null == this._cells && (this._cells = []), this._cells
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype._serialize = function() {
        var e;
        return this._checkEmptyWorkbookRow() ? null : (e = {}, this._cells && this._cells.length > 0 && (e.cells = this._serializeWorkbookCells()), this.style && (e.style = this.style._serialize()), null != this.collapsed && (e.collapsed = this.collapsed), null == this.groupLevel || isNaN(this.groupLevel) || (e.groupLevel = this.groupLevel), null == this.height || isNaN(this.height) || (e.height = this.height), null != this.visible && (e.visible = this.visible), e)
      }, e.prototype._deserialize = function(e) {
        var t;
        e.cells && e.cells.length > 0 && this._deserializeWorkbookCells(e.cells), e.style && ((t = new a)._deserialize(e.style), this.style = t), this.collapsed = e.collapsed, this.groupLevel = e.groupLevel, this.height = e.height, this.visible = e.visible
      }, e.prototype._addWorkbookCell = function(e, t) {
        null == this._cells && (this._cells = []), null == t || isNaN(t) ? this._cells.push(e) : this._cells[t] = e
      }, e.prototype._serializeWorkbookCells = function() {
        var e, t, s = [];
        for (t = 0; t < this._cells.length; t++)(e = this._cells[t]) && (s[t] = e._serialize());
        return s
      }, e.prototype._deserializeWorkbookCells = function(e) {
        var t, s, l;
        for (this._cells = [], l = 0; l < e.length; l++)(t = e[l]) && ((s = new n)._deserialize(t), this._cells[l] = s)
      }, e.prototype._checkEmptyWorkbookRow = function() {
        return null == this._cells && null == this.style && null == this.collapsed && null == this.visible && (null == this.height || isNaN(this.height)) && (null == this.groupLevel || isNaN(this.groupLevel))
      }, e
    }();
    t.WorkbookRow = r;
    var n = function() {
      function e() {}
      return e.prototype._serialize = function() {
        var e;
        return this._checkEmptyWorkbookCell() ? null : (e = {}, this.style && (e.style = this.style._serialize()), null != this.value && (e.value = this.value), this.formula && (e.formula = this.formula), null != this.isDate && (e.isDate = this.isDate), null != this.colSpan && !isNaN(this.colSpan) && this.colSpan > 1 && (e.colSpan = this.colSpan), null != this.rowSpan && !isNaN(this.rowSpan) && this.rowSpan > 1 && (e.rowSpan = this.rowSpan), this.link && (e.link = this.link), this.textRuns && this.textRuns.length > 0 && (e.textRuns = this._serializeTextRuns()), e)
      }, e.prototype._deserialize = function(e) {
        var t;
        e.style && ((t = new a)._deserialize(e.style), this.style = t), this.value = e.value, this.formula = e.formula, this.isDate = e.isDate, this.colSpan = e.colSpan, this.rowSpan = e.rowSpan, this.link = e.link, e.textRuns && e.textRuns.length > 0 && this._deserializeTextRuns(e.textRuns)
      }, e.prototype._serializeTextRuns = function() {
        var e, t, s = [];
        for (t = 0; t < this.textRuns.length; t++) e = this.textRuns[t], s.push(e._serialize());
        return s
      }, e.prototype._deserializeTextRuns = function(e) {
        var t, s, l;
        for (this.textRuns = [], l = 0; l < e.length; l++) s = new v, t = e[l], s._deserialize(t), this.textRuns.push(s)
      }, e.prototype._checkEmptyWorkbookCell = function() {
        return null == this.style && null == this.value && null == this.isDate && (null == this.formula || "" === this.formula) && (null == this.colSpan || isNaN(this.colSpan) || this.colSpan <= 1) && (null == this.rowSpan || isNaN(this.rowSpan) || this.rowSpan <= 1) && (null == this.link || "" === this.link)
      }, e
    }();
    t.WorkbookCell = n;
    var o = function() {
      function e() {}
      return e.prototype._serialize = function() {
        return null != this.columns && !isNaN(this.columns) && 0 !== this.columns || null != this.rows && !isNaN(this.rows) && 0 !== this.rows ? {
          columns: this.columns,
          rows: this.rows
        } : null
      }, e.prototype._deserialize = function(e) {
        this.columns = e.columns, this.rows = e.rows
      }, e
    }();
    t.WorkbookFrozenPane = o;
    var a = function() {
      function t() {}
      return t.prototype._serialize = function() {
        var t;
        return this._checkEmptyWorkbookStyle() ? null : (t = {}, this.basedOn && (t.basedOn = this.basedOn._serialize()), this.fill && (t.fill = this.fill._serialize()), this.font && (t.font = this.font._serialize()), this.borders && (t.borders = this.borders._serialize()), this.format && (t.format = this.format), null != this.hAlign && (t.hAlign = e.asEnum(this.hAlign, S, !1)), null != this.vAlign && (t.vAlign = e.asEnum(this.vAlign, w, !1)), null == this.indent || isNaN(this.indent) || (t.indent = this.indent), this.wordWrap && (t.wordWrap = this.wordWrap), t)
      }, t.prototype._deserialize = function(s) {
        var l, i, r, n;
        s.basedOn && ((l = new t)._deserialize(s.basedOn), this.basedOn = l), s.fill && ((i = new u)._deserialize(s.fill), this.fill = i), s.font && ((r = new h)._deserialize(s.font), this.font = r), s.borders && ((n = new c)._deserialize(s.borders), this.borders = n), this.format = s.format, null != s.hAlign && (this.hAlign = e.asEnum(s.hAlign, S, !1)), null != s.vAlign && (this.vAlign = e.asEnum(s.vAlign, w, !1)), null == s.indent || isNaN(s.indent) || (this.indent = s.indent), s.wordWrap && (this.wordWrap = s.wordWrap)
      }, t.prototype._checkEmptyWorkbookStyle = function() {
        return null == this.basedOn && null == this.fill && null == this.font && null == this.borders && (null == this.format || "" === this.format) && null == this.hAlign && null == this.vAlign && null == this.wordWrap
      }, t
    }();
    t.WorkbookStyle = a;
    var h = function() {
      function e() {}
      return e.prototype._serialize = function() {
        var e;
        return this._checkEmptyWorkbookFont() ? null : (e = {}, null != this.bold && (e.bold = this.bold), null != this.italic && (e.italic = this.italic), null != this.underline && (e.underline = this.underline), this.color && (e.color = this.color), this.family && (e.family = this.family), null == this.size || isNaN(this.size) || (e.size = this.size), e)
      }, e.prototype._deserialize = function(e) {
        this.bold = e.bold, null != e.color && (this.color = e.color), this.family = e.family, this.italic = e.italic, this.size = e.size, this.underline = e.underline
      }, e.prototype._checkEmptyWorkbookFont = function() {
        return null == this.bold && null == this.italic && null == this.underline && (null == this.color || "" === this.color) && (null == this.family || "" === this.family) && (null == this.size || isNaN(this.size))
      }, e
    }();
    t.WorkbookFont = h;
    var u = function() {
      function e() {}
      return e.prototype._serialize = function() {
        return this.color ? {
          color: this.color
        } : null
      }, e.prototype._deserialize = function(e) {
        null != e.color && (this.color = e.color)
      }, e
    }();
    t.WorkbookFill = u;
    var c = function() {
      function e() {}
      return e.prototype._serialize = function() {
        var e;
        return this._checkEmptyWorkbookBorder() ? null : (e = {}, this.top && (e.top = this.top._serialize()), this.bottom && (e.bottom = this.bottom._serialize()), this.left && (e.left = this.left._serialize()), this.right && (e.right = this.right._serialize()), this.diagonal && (e.diagonal = this.diagonal._serialize()), e)
      }, e.prototype._deserialize = function(e) {
        var t, s, l, i, r;
        e.top && ((t = new f)._deserialize(e.top), this.top = t), e.bottom && ((s = new f)._deserialize(e.bottom), this.bottom = s), e.left && ((l = new f)._deserialize(e.left), this.left = l), e.right && ((i = new f)._deserialize(e.right), this.right = i), e.diagonal && ((r = new f)._deserialize(e.diagonal), this.diagonal = r)
      }, e.prototype._checkEmptyWorkbookBorder = function() {
        return null == this.top && null == this.bottom && null == this.left && null == this.right && null == this.diagonal
      }, e
    }();
    t.WorkbookBorder = c;
    var f = function() {
      function t() {}
      return t.prototype._serialize = function() {
        var t;
        return null != this.color && "" !== this.color || null != this.style ? (t = {}, this.color && (t.color = this.color), null != this.style && (t.style = e.asEnum(this.style, x, !1)), t) : null
      }, t.prototype._deserialize = function(t) {
        null != t.color && (this.color = t.color, null != t.style && (this.style = e.asEnum(t.style, x, !1)))
      }, t
    }();
    t.WorkbookBorderSetting = f;
    var d = function() {
      function e() {}
      return e.prototype._serialize = function() {
        var e;
        return null == this.name ? null : (e = {
          name: this.name,
          value: this.value
        }, null != this.sheetName && (e.sheetName = this.sheetName), e)
      }, e.prototype._deserialize = function(e) {
        this.name = e.name, this.value = e.value, this.sheetName = e.sheetName
      }, e
    }();
    t.DefinedName = d;
    var p = function() {
      function t() {}
      return Object.defineProperty(t.prototype, "columns", {
        get: function() {
          return null == this._columns && (this._columns = []), this._columns
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype._serialize = function() {
        var e, t;
        return null == this.name || null == this.range || null == this._columns ? null : (null != this.style && (t = this.style._serialize()), e = {
          name: this.name,
          range: this.range,
          showHeaderRow: this.showHeaderRow,
          showTotalRow: this.showTotalRow,
          style: t,
          showBandedColumns: this.showBandedColumns,
          showBandedRows: this.showBandedRows,
          alterFirstColumn: this.alterFirstColumn,
          alterLastColumn: this.alterLastColumn,
          columns: []
        }, e.columns = this._serializeTableColumns(), e)
      }, t.prototype._deserialize = function(e) {
        var t;
        this.name = e.name, this.range = e.range, this.showHeaderRow = e.showHeaderRow, this.showTotalRow = e.showTotalRow, null != e.style && ((t = new g)._deserialize(e.style), this.style = t), this.showBandedColumns = e.showBandedColumns, this.showBandedRows = e.showBandedRows, this.alterFirstColumn = e.alterFirstColumn, this.alterLastColumn = e.alterLastColumn, this._deserializeTableColumns(e.columns)
      }, t.prototype._serializeTableColumns = function() {
        var e, t, s = [];
        for (t = 0; t < this._columns.length; t++)(e = this._columns[t]) && (s[t] = e._serialize());
        return s
      }, t.prototype._deserializeTableColumns = function(t) {
        var s, l, i;
        for (e.assert(null != t, "table Columns should not be null."), this._columns = [], i = 0; i < t.length; i++)(l = t[i]) && ((s = new m)._deserialize(l), this._columns[i] = s)
      }, t
    }();
    t.WorkbookTable = p;
    var m = function() {
      function e() {}
      return e.prototype._serialize = function() {
        return null == this.name ? null : {
          name: this.name,
          totalRowLabel: this.totalRowLabel,
          totalRowFunction: this.totalRowFunction,
          showFilterButton: this.showFilterButton
        }
      }, e.prototype._deserialize = function(e) {
        this.name = e.name, this.totalRowLabel = e.totalRowLabel, this.totalRowFunction = e.totalRowFunction, this.showFilterButton = e.showFilterButton
      }, e
    }();
    t.WorkbookTableColumn = m;
    var g = function() {
      function e() {}
      return e.prototype._serialize = function() {
        var e;
        return this._checkEmptyWorkbookTableStyle() ? null : (e = {
          name: this.name
        }, this.wholeTableStyle && (e.wholeTableStyle = this.wholeTableStyle._serialize()), this.firstBandedColumnStyle && (e.firstBandedColumnStyle = this.firstBandedColumnStyle._serialize()), this.firstBandedRowStyle && (e.firstBandedRowStyle = this.firstBandedRowStyle._serialize()), this.secondBandedColumnStyle && (e.secondBandedColumnStyle = this.secondBandedColumnStyle._serialize()), this.secondBandedRowStyle && (e.secondBandedRowStyle = this.secondBandedRowStyle._serialize()), this.headerRowStyle && (e.headerRowStyle = this.headerRowStyle._serialize()), this.totalRowStyle && (e.totalRowStyle = this.totalRowStyle._serialize()), this.firstColumnStyle && (e.firstColumnStyle = this.firstColumnStyle._serialize()), this.lastColumnStyle && (e.lastColumnStyle = this.lastColumnStyle._serialize()), this.firstHeaderCellStyle && (e.firstHeaderCellStyle = this.firstHeaderCellStyle._serialize()), this.lastHeaderCellStyle && (e.lastHeaderCellStyle = this.lastHeaderCellStyle._serialize()), this.firstTotalCellStyle && (e.firstTotalCellStyle = this.firstTotalCellStyle._serialize()), this.lastTotalCellStyle && (e.lastTotalCellStyle = this.lastTotalCellStyle._serialize()), e)
      }, e.prototype._deserialize = function(e) {
        this.name = e.name, e.wholeTableStyle && (this.wholeTableStyle = new _, this.wholeTableStyle._deserialize(e.wholeTableStyle)), e.firstBandedColumnStyle && (this.firstBandedColumnStyle = new y, this.firstBandedColumnStyle._deserialize(e.firstBandedColumnStyle)), e.firstBandedRowStyle && (this.firstBandedRowStyle = new y, this.firstBandedRowStyle._deserialize(e.firstBandedRowStyle)), e.secondBandedColumnStyle && (this.secondBandedColumnStyle = new y, this.secondBandedColumnStyle._deserialize(e.secondBandedColumnStyle)), e.secondBandedRowStyle && (this.secondBandedRowStyle = new y, this.secondBandedRowStyle._deserialize(e.secondBandedRowStyle)), e.headerRowStyle && (this.headerRowStyle = new _, this.headerRowStyle._deserialize(e.headerRowStyle)), e.totalRowStyle && (this.totalRowStyle = new _, this.totalRowStyle._deserialize(e.totalRowStyle)), e.firstColumnStyle && (this.firstColumnStyle = new _, this.firstColumnStyle._deserialize(e.firstColumnStyle)), e.lastColumnStyle && (this.lastColumnStyle = new _, this.lastColumnStyle._deserialize(e.lastColumnStyle)), e.firstHeaderCellStyle && (this.firstHeaderCellStyle = new _, this.firstHeaderCellStyle._deserialize(e.firstHeaderCellStyle)), e.lastHeaderCellStyle && (this.lastHeaderCellStyle = new _, this.lastHeaderCellStyle._deserialize(e.lastHeaderCellStyle)), e.firstTotalCellStyle && (this.firstTotalCellStyle = new _, this.firstTotalCellStyle._deserialize(e.firstTotalCellStyle)), e.lastTotalCellStyle && (this.lastTotalCellStyle = new _, this.lastTotalCellStyle._deserialize(e.lastTotalCellStyle))
      }, e.prototype._checkEmptyWorkbookTableStyle = function() {
        return null == this.name || null == this.wholeTableStyle && null == this.firstBandedColumnStyle && null == this.firstBandedRowStyle && null == this.secondBandedColumnStyle && null == this.secondBandedRowStyle && null == this.headerRowStyle && null == this.totalRowStyle && null == this.firstColumnStyle && null == this.lastColumnStyle && null == this.firstHeaderCellStyle && null == this.lastHeaderCellStyle && null == this.firstTotalCellStyle && null == this.lastTotalCellStyle
      }, e
    }();
    t.WorkbookTableStyle = g;
    var _ = function(e) {
      function t() {
        return e.call(this) || this
      }
      return __extends(t, e), t.prototype._deserialize = function(t) {
        var s;
        e.prototype._deserialize.call(this, t), t.borders && ((s = new b)._deserialize(t.borders), this.borders = s)
      }, t
    }(a);
    t.WorkbookTableCommonStyle = _;
    var y = function(e) {
      function t() {
        return e.call(this) || this
      }
      return __extends(t, e), t.prototype._serialize = function() {
        var t;
        return t = e.prototype._serialize.call(this), t.size = this.size, t
      }, t.prototype._deserialize = function(t) {
        e.prototype._deserialize.call(this, t), null != t.size && (this.size = t.size)
      }, t
    }(_);
    t.WorkbookTableBandedStyle = y;
    var b = function(e) {
      function t() {
        return e.call(this) || this
      }
      return __extends(t, e), t.prototype._serialize = function() {
        var t;
        return null != (t = e.prototype._serialize.call(this)) || this.vertical && this.horizontal || (t = {}), this.vertical && (t.vertical = this.vertical._serialize()), this.horizontal && (t.horizontal = this.horizontal._serialize()), t
      }, t.prototype._deserialize = function(t) {
        var s, l;
        e.prototype._deserialize.call(this, t), t.vertical && ((s = new f)._deserialize(t.vertical), this.vertical = s), t.horizontal && ((l = new f)._deserialize(t.horizontal), this.horizontal = l)
      }, t
    }(c);
    t.WorkbookTableBorder = b;
    var v = function() {
      function e() {}
      return e.prototype._serialize = function() {
        var e = {
          text: this.text
        };
        return this.font && (e.font = this.font._serialize()), e
      }, e.prototype._deserialize = function(e) {
        e.font && (this.font = new h, this.font._deserialize(e.font)), this.text = e.text
      }, e
    }();
    t.WorkbookTextRun = v;
    var S;
    ! function(e) {
      e[e.General = 0] = "General", e[e.Left = 1] = "Left", e[e.Center = 2] = "Center", e[e.Right = 3] = "Right", e[e.Fill = 4] = "Fill", e[e.Justify = 5] = "Justify"
    }(S = t.HAlign || (t.HAlign = {}));
    var w;
    ! function(e) {
      e[e.Top = 0] = "Top", e[e.Center = 1] = "Center", e[e.Bottom = 2] = "Bottom", e[e.Justify = 3] = "Justify"
    }(w = t.VAlign || (t.VAlign = {}));
    var x;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Thin = 1] = "Thin", e[e.Medium = 2] = "Medium", e[e.Dashed = 3] = "Dashed", e[e.Dotted = 4] = "Dotted", e[e.Thick = 5] = "Thick", e[e.Double = 6] = "Double", e[e.Hair = 7] = "Hair", e[e.MediumDashed = 8] = "MediumDashed", e[e.ThinDashDotted = 9] = "ThinDashDotted", e[e.MediumDashDotted = 10] = "MediumDashDotted", e[e.ThinDashDotDotted = 11] = "ThinDashDotDotted", e[e.MediumDashDotDotted = 12] = "MediumDashDotDotted", e[e.SlantedMediumDashDotted = 13] = "SlantedMediumDashDotted"
    }(x = t.BorderStyle || (t.BorderStyle = {}))
  }(e.xlsx || (e.xlsx = {}))
}(wijmo || (wijmo = {}));
