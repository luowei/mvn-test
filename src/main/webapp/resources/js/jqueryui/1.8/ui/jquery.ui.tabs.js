/*
 * jQuery UI Tabs 1.8.17
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Tabs
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 */
(function (a, b) {
    function f() {
        return++d
    }

    function e() {
        return++c
    }

    var c = 0, d = 0;
    a.widget("ui.tabs", {options:{add:null, ajaxOptions:null, cache:!1, cookie:null, collapsible:!1, disable:null, disabled:[], enable:null, event:"click", fx:null, idPrefix:"ui-tabs-", load:null, panelTemplate:"<div></div>", remove:null, select:null, show:null, spinner:"<em>Loading&#8230;</em>", tabTemplate:"<li><a href='#{href}'><span>#{label}</span></a></li>"}, _create:function () {
        this._tabify(!0)
    }, _setOption:function (a, b) {
        if (a == "selected") {
            if (this.options.collapsible && b == this.options.selected)return;
            this.select(b)
        } else this.options[a] = b, this._tabify()
    }, _tabId:function (a) {
        return a.title && a.title.replace(/\s/g, "_").replace(/[^\w\u00c0-\uFFFF-]/g, "") || this.options.idPrefix + e()
    }, _sanitizeSelector:function (a) {
        return a.replace(/:/g, "\\:")
    }, _cookie:function () {
        var b = this.cookie || (this.cookie = this.options.cookie.name || "ui-tabs-" + f());
        return a.cookie.apply(null, [b].concat(a.makeArray(arguments)))
    }, _ui:function (a, b) {
        return{tab:a, panel:b, index:this.anchors.index(a)}
    }, _cleanup:function () {
        this.lis.filter(".ui-state-processing").removeClass("ui-state-processing").find("span:data(label.tabs)").each(function () {
            var b = a(this);
            b.html(b.data("label.tabs")).removeData("label.tabs")
        })
    }, _tabify:function (c) {
        function m(b, c) {
            b.css("display", ""), !a.support.opacity && c.opacity && b[0].style.removeAttribute("filter")
        }

        var d = this, e = this.options, f = /^#.+/;
        this.list = this.element.find("ol,ul").eq(0), this.lis = a(" > li:has(a[href])", this.list), this.anchors = this.lis.map(function () {
            return a("a", this)[0]
        }), this.panels = a([]), this.anchors.each(function (b, c) {
            var g = a(c).attr("href"), h = g.split("#")[0], i;
            h && (h === location.toString().split("#")[0] || (i = a("base")[0]) && h === i.href) && (g = c.hash, c.href = g);
            if (f.test(g))d.panels = d.panels.add(d.element.find(d._sanitizeSelector(g))); else if (g && g !== "#") {
                a.data(c, "href.tabs", g), a.data(c, "load.tabs", g.replace(/#.*$/, ""));
                var j = d._tabId(c);
                c.href = "#" + j;
                var k = d.element.find("#" + j);
                k.length || (k = a(e.panelTemplate).attr("id", j).addClass("ui-tabs-panel ui-widget-content ui-corner-bottom").insertAfter(d.panels[b - 1] || d.list), k.data("destroy.tabs", !0)), d.panels = d.panels.add(k)
            } else e.disabled.push(b)
        }), c ? (this.element.addClass("ui-tabs ui-widget ui-widget-content ui-corner-all"), this.list.addClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all"), this.lis.addClass("ui-state-default ui-corner-top"), this.panels.addClass("ui-tabs-panel ui-widget-content ui-corner-bottom"), e.selected === b ? (location.hash && this.anchors.each(function (a, b) {
            if (b.hash == location.hash) {
                e.selected = a;
                return!1
            }
        }), typeof e.selected != "number" && e.cookie && (e.selected = parseInt(d._cookie(), 10)), typeof e.selected != "number" && this.lis.filter(".ui-tabs-selected").length && (e.selected = this.lis.index(this.lis.filter(".ui-tabs-selected"))), e.selected = e.selected || (this.lis.length ? 0 : -1)) : e.selected === null && (e.selected = -1), e.selected = e.selected >= 0 && this.anchors[e.selected] || e.selected < 0 ? e.selected : 0, e.disabled = a.unique(e.disabled.concat(a.map(this.lis.filter(".ui-state-disabled"), function (a, b) {
            return d.lis.index(a)
        }))).sort(), a.inArray(e.selected, e.disabled) != -1 && e.disabled.splice(a.inArray(e.selected, e.disabled), 1), this.panels.addClass("ui-tabs-hide"), this.lis.removeClass("ui-tabs-selected ui-state-active"), e.selected >= 0 && this.anchors.length && (d.element.find(d._sanitizeSelector(d.anchors[e.selected].hash)).removeClass("ui-tabs-hide"), this.lis.eq(e.selected).addClass("ui-tabs-selected ui-state-active"), d.element.queue("tabs", function () {
            d._trigger("show", null, d._ui(d.anchors[e.selected], d.element.find(d._sanitizeSelector(d.anchors[e.selected].hash))[0]))
        }), this.load(e.selected)), a(window).bind("unload", function () {
            d.lis.add(d.anchors).unbind(".tabs"), d.lis = d.anchors = d.panels = null
        })) : e.selected = this.lis.index(this.lis.filter(".ui-tabs-selected")), this.element[e.collapsible ? "addClass" : "removeClass"]("ui-tabs-collapsible"), e.cookie && this._cookie(e.selected, e.cookie);
        for (var g = 0, h; h = this.lis[g]; g++)a(h)[a.inArray(g, e.disabled) != -1 && !a(h).hasClass("ui-tabs-selected") ? "addClass" : "removeClass"]("ui-state-disabled");
        e.cache === !1 && this.anchors.removeData("cache.tabs"), this.lis.add(this.anchors).unbind(".tabs");
        if (e.event !== "mouseover") {
            var i = function (a, b) {
                b.is(":not(.ui-state-disabled)") && b.addClass("ui-state-" + a)
            }, j = function (a, b) {
                b.removeClass("ui-state-" + a)
            };
            this.lis.bind("mouseover.tabs", function () {
                i("hover", a(this))
            }), this.lis.bind("mouseout.tabs", function () {
                j("hover", a(this))
            }), this.anchors.bind("focus.tabs", function () {
                i("focus", a(this).closest("li"))
            }), this.anchors.bind("blur.tabs", function () {
                j("focus", a(this).closest("li"))
            })
        }
        var k, l;
        e.fx && (a.isArray(e.fx) ? (k = e.fx[0], l = e.fx[1]) : k = l = e.fx);
        var n = l ? function (b, c) {
            a(b).closest("li").addClass("ui-tabs-selected ui-state-active"), c.hide().removeClass("ui-tabs-hide").animate(l, l.duration || "normal", function () {
                m(c, l), d._trigger("show", null, d._ui(b, c[0]))
            })
        } : function (b, c) {
            a(b).closest("li").addClass("ui-tabs-selected ui-state-active"), c.removeClass("ui-tabs-hide"), d._trigger("show", null, d._ui(b, c[0]))
        }, o = k ? function (a, b) {
            b.animate(k, k.duration || "normal", function () {
                d.lis.removeClass("ui-tabs-selected ui-state-active"), b.addClass("ui-tabs-hide"), m(b, k), d.element.dequeue("tabs")
            })
        } : function (a, b, c) {
            d.lis.removeClass("ui-tabs-selected ui-state-active"), b.addClass("ui-tabs-hide"), d.element.dequeue("tabs")
        };
        this.anchors.bind(e.event + ".tabs", function () {
            var b = this, c = a(b).closest("li"), f = d.panels.filter(":not(.ui-tabs-hide)"), g = d.element.find(d._sanitizeSelector(b.hash));
            if (c.hasClass("ui-tabs-selected") && !e.collapsible || c.hasClass("ui-state-disabled") || c.hasClass("ui-state-processing") || d.panels.filter(":animated").length || d._trigger("select", null, d._ui(this, g[0])) === !1) {
                this.blur();
                return!1
            }
            e.selected = d.anchors.index(this), d.abort();
            if (e.collapsible) {
                if (c.hasClass("ui-tabs-selected")) {
                    e.selected = -1, e.cookie && d._cookie(e.selected, e.cookie), d.element.queue("tabs",
                        function () {
                            o(b, f)
                        }).dequeue("tabs"), this.blur();
                    return!1
                }
                if (!f.length) {
                    e.cookie && d._cookie(e.selected, e.cookie), d.element.queue("tabs", function () {
                        n(b, g)
                    }), d.load(d.anchors.index(this)), this.blur();
                    return!1
                }
            }
            e.cookie && d._cookie(e.selected, e.cookie);
            if (g.length)f.length && d.element.queue("tabs", function () {
                o(b, f)
            }), d.element.queue("tabs", function () {
                n(b, g)
            }), d.load(d.anchors.index(this)); else throw"jQuery UI Tabs: Mismatching fragment identifier.";
            a.browser.msie && this.blur()
        }), this.anchors.bind("click.tabs", function () {
            return!1
        })
    }, _getIndex:function (a) {
        typeof a == "string" && (a = this.anchors.index(this.anchors.filter("[href$=" + a + "]")));
        return a
    }, destroy:function () {
        var b = this.options;
        this.abort(), this.element.unbind(".tabs").removeClass("ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible").removeData("tabs"), this.list.removeClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all"), this.anchors.each(function () {
            var b = a.data(this, "href.tabs");
            b && (this.href = b);
            var c = a(this).unbind(".tabs");
            a.each(["href", "load", "cache"], function (a, b) {
                c.removeData(b + ".tabs")
            })
        }), this.lis.unbind(".tabs").add(this.panels).each(function () {
            a.data(this, "destroy.tabs") ? a(this).remove() : a(this).removeClass(["ui-state-default", "ui-corner-top", "ui-tabs-selected", "ui-state-active", "ui-state-hover", "ui-state-focus", "ui-state-disabled", "ui-tabs-panel", "ui-widget-content", "ui-corner-bottom", "ui-tabs-hide"].join(" "))
        }), b.cookie && this._cookie(null, b.cookie);
        return this
    }, add:function (c, d, e) {
        e === b && (e = this.anchors.length);
        var f = this, g = this.options, h = a(g.tabTemplate.replace(/#\{href\}/g, c).replace(/#\{label\}/g, d)), i = c.indexOf("#") ? this._tabId(a("a", h)[0]) : c.replace("#", "");
        h.addClass("ui-state-default ui-corner-top").data("destroy.tabs", !0);
        var j = f.element.find("#" + i);
        j.length || (j = a(g.panelTemplate).attr("id", i).data("destroy.tabs", !0)), j.addClass("ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide"), e >= this.lis.length ? (h.appendTo(this.list), j.appendTo(this.list[0].parentNode)) : (h.insertBefore(this.lis[e]), j.insertBefore(this.panels[e])), g.disabled = a.map(g.disabled, function (a, b) {
            return a >= e ? ++a : a
        }), this._tabify(), this.anchors.length == 1 && (g.selected = 0, h.addClass("ui-tabs-selected ui-state-active"), j.removeClass("ui-tabs-hide"), this.element.queue("tabs", function () {
            f._trigger("show", null, f._ui(f.anchors[0], f.panels[0]))
        }), this.load(0)), this._trigger("add", null, this._ui(this.anchors[e], this.panels[e]));
        return this
    }, remove:function (b) {
        b = this._getIndex(b);
        var c = this.options, d = this.lis.eq(b).remove(), e = this.panels.eq(b).remove();
        d.hasClass("ui-tabs-selected") && this.anchors.length > 1 && this.select(b + (b + 1 < this.anchors.length ? 1 : -1)), c.disabled = a.map(a.grep(c.disabled, function (a, c) {
            return a != b
        }), function (a, c) {
            return a >= b ? --a : a
        }), this._tabify(), this._trigger("remove", null, this._ui(d.find("a")[0], e[0]));
        return this
    }, enable:function (b) {
        b = this._getIndex(b);
        var c = this.options;
        if (a.inArray(b, c.disabled) != -1) {
            this.lis.eq(b).removeClass("ui-state-disabled"), c.disabled = a.grep(c.disabled, function (a, c) {
                return a != b
            }), this._trigger("enable", null, this._ui(this.anchors[b], this.panels[b]));
            return this
        }
    }, disable:function (a) {
        a = this._getIndex(a);
        var b = this, c = this.options;
        a != c.selected && (this.lis.eq(a).addClass("ui-state-disabled"), c.disabled.push(a), c.disabled.sort(), this._trigger("disable", null, this._ui(this.anchors[a], this.panels[a])));
        return this
    }, select:function (a) {
        a = this._getIndex(a);
        if (a == -1)if (this.options.collapsible && this.options.selected != -1)a = this.options.selected; else return this;
        this.anchors.eq(a).trigger(this.options.event + ".tabs");
        return this
    }, load:function (b) {
        b = this._getIndex(b);
        var c = this, d = this.options, e = this.anchors.eq(b)[0], f = a.data(e, "load.tabs");
        this.abort();
        if (!f || this.element.queue("tabs").length !== 0 && a.data(e, "cache.tabs"))this.element.dequeue("tabs"); else {
            this.lis.eq(b).addClass("ui-state-processing");
            if (d.spinner) {
                var g = a("span", e);
                g.data("label.tabs", g.html()).html(d.spinner)
            }
            this.xhr = a.ajax(a.extend({}, d.ajaxOptions, {url:f, success:function (f, g) {
                c.element.find(c._sanitizeSelector(e.hash)).html(f), c._cleanup(), d.cache && a.data(e, "cache.tabs", !0), c._trigger("load", null, c._ui(c.anchors[b], c.panels[b]));
                try {
                    d.ajaxOptions.success(f, g)
                } catch (h) {
                }
            }, error:function (a, f, g) {
                c._cleanup(), c._trigger("load", null, c._ui(c.anchors[b], c.panels[b]));
                try {
                    d.ajaxOptions.error(a, f, b, e)
                } catch (g) {
                }
            }})), c.element.dequeue("tabs");
            return this
        }
    }, abort:function () {
        this.element.queue([]), this.panels.stop(!1, !0), this.element.queue("tabs", this.element.queue("tabs").splice(-2, 2)), this.xhr && (this.xhr.abort(), delete this.xhr), this._cleanup();
        return this
    }, url:function (a, b) {
        this.anchors.eq(a).removeData("cache.tabs").data("load.tabs", b);
        return this
    }, length:function () {
        return this.anchors.length
    }}), a.extend(a.ui.tabs, {version:"1.8.17"}), a.extend(a.ui.tabs.prototype, {rotation:null, rotate:function (a, b) {
        var c = this, d = this.options, e = c._rotate || (c._rotate = function (b) {
            clearTimeout(c.rotation), c.rotation = setTimeout(function () {
                var a = d.selected;
                c.select(++a < c.anchors.length ? a : 0)
            }, a), b && b.stopPropagation()
        }), f = c._unrotate || (c._unrotate = b ? function (a) {
            t = d.selected, e()
        } : function (a) {
            a.clientX && c.rotate(null)
        });
        a ? (this.element.bind("tabsshow", e), this.anchors.bind(d.event + ".tabs", f), e()) : (clearTimeout(c.rotation), this.element.unbind("tabsshow", e), this.anchors.unbind(d.event + ".tabs", f), delete this._rotate, delete this._unrotate);
        return this
    }})
})(jQuery)