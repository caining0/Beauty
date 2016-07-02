(function($, doc) {
	$.init({});
	
	var letvPlayer;// 对乐视播放器的引用
	
	var pageInfo = {};
	// 获取传递过来的参数
	var passdata = location.hash;
    var hash_params = passdata.split("&");
    if (passdata.indexOf('&') != -1 && hash_params.length == 2){
		pageInfo.id = hash_params[0].split("=")[1];
        pageInfo.type = hash_params[1].split("=")[1];
    }
	
	var pagedata;// 页面数据
	var reviewPage = 1;// 需加载的评论页数
	
	if (pageInfo.type == 1) {// 直播不需要这个效果
		(function ($) {
			$(".box_samil").scroll(function(){
				var sToptow = $(this).scrollTop();
				if(sToptow<50){$("footer").hide(300);$(".box").css("padding-bottom","0px");}
				if(sToptow>=50){$("footer").show(300);$(".box").css("padding-bottom","43px");}
			});
		})(jQuery);
	} else {
		jQuery("footer").show();
		jQuery(".box").css("padding-bottom","43px");
	}
	
	/**
	 * 解析时间戳到友好时间
	 * @param {int} msts 毫秒数
	 */
	function parseMsts(msts) {
		var d = new Date();
		d.setTime(msts);
		var year = d.getFullYear();
		var month = (d.getMonth()+1);
		if (month < 10) {
			month = "0"+month;
		}
		var day = d.getDate();
		if (day < 10) {
			day = "0"+day;
		}
		var hours = d.getHours();
		if (hours < 10) {
			hours = "0"+hours;
		}
		var minutes = d.getMinutes();
		if (minutes < 10) {
			minutes = "0"+minutes;
		}
		
		var today = new Date();
		if (today.getFullYear() != year) {// 非当年
			return year+"-"+month+"-"+day+" "+hours+":"+minutes;
		}
		// 当天
		if (today.getMonth() == d.getMonth() && today.getDate() == d.getDate()) {
			return hours+":"+minutes;
		}
		// 当当天
		return month+"-"+day+" "+hours+":"+minutes;
	}
	
	function pullupRefresh(callback) {
		callback = callback || $.noop;
		/**
		 * status的值：
		 * 0：获了评论成功；
		 * 1：没有评论数据了；
		 * 2：失败
		 */
		loadReviews(function (status) {
			callback(status);
		});
	}
	
	// 处理显示加载更多评论逻辑
	var UpRefresh = (function ($) {
		var startX,
		startY,
		x,
		y;
		
		function touchSatrtFunc(e){
			var touch=e.touches[0];
			startX = touch.pageX; 
			startY = touch.pageY; 
		}
		
		function hideloading() {
			$("#loding_div").css("display","none");
			$("#loding_div").animate({opacity:'0'},400);
		}
		function showloading() {
			$(".box_samil").css("padding-bottom","40px");
			$("#nomore_pl").css("display","none");
			$("#loding_div").css("display","block");
			$("#loding_div").animate({opacity:'1'},300);
		}
		
		function nomore() {
			$("#nomore_pl").css("display","block");
			$("#nomore_pl").animate({opacity:'1'},400);
		}
		
		var flagdisableUpRefresh = false;
		// 强制关闭上拉加载逻辑
		function disableUpRefresh() {
			flagdisableUpRefresh = true;
			unlistenUpRefresh();
		}
		function unlistenUpRefresh() {
			document.getElementById("parent").removeEventListener('touchstart', touchSatrtFunc,false); 
			document.getElementById("parent").removeEventListener('touchmove', touchMoveFunc,false);
		}
		
		function listenUpRefresh() {
			if (flagdisableUpRefresh) {
				return;
			}
			document.getElementById("parent").addEventListener('touchstart', touchSatrtFunc,false); 
			document.getElementById("parent").addEventListener('touchmove', touchMoveFunc,false); 
		}
		
		function touchMoveFunc(e){
			var touch = e.touches[0];
			x = touch.pageX - startX;
			y = touch.pageY - startY;
			var sTop = $(".box_samil").scrollTop();
			var heig2=document.getElementById("parent2").offsetHeight;
			var heig3=document.getElementById("parent3").offsetHeight;
			var heitop=heig3-heig2;
			var heitoptow=heitop-40;
			if (y<0 && sTop>=heitop){
				showloading();
				unlistenUpRefresh();
				
				console.log("here");
				// 满足loading条件
				
				pullupRefresh(function (loadstatus) {
					setTimeout(function () {
						listenUpRefresh();
					}, 1000);
					
					hideloading();
					switch(loadstatus) {
						case 0:// 加载成功
						break;
						
						case 1:// 没有更多数据了
							nomore();
							disableUpRefresh();
						break;
						
						case 2:// 加载失败
						plus.nativeUI.toast("当前网络不可用，请检查你的网络设置");
						// donothing
						break;
					}
				});
			}
			if(y>0 && heitop>=sTop){
				$(".box_samil").css("padding-bottom","0px");
				$("#nomore_pl").css("display","none");
			}
			else{return false;}
		}
		listenUpRefresh();
		
		return {
			disableUpRefresh: disableUpRefresh
		}
	})(jQuery);
	
	function dg(id) {
		return document.getElementById(id);
	}
	
	/**
	 * 获取页面详情数据
	 */
	function loadDetailData() {
		var get_fn;
		if (pageInfo.type == 1) {
			get_fn = App.Api.getArticle;
		} else {
			get_fn = App.Api.getLive;
		}
		
		get_fn(pageInfo.id, function (data) {
			pagedata = data.data;
			if (pageInfo.type == 1) {// 非直播
				var nopic="http://cms4sdoc.onlylady.com/admin/articlepost/images/nopic.jpg";
				dg("bigpic").src = pagedata.iu;
				if(dg("bigpic").src == nopic){
					dg("bigpic").style.display="none";
				}
			}
			dg("article_title").innerText = pagedata.tt;
			dg("article_readnum").innerText = pagedata.cl;
			dg("article_author_pic").src = pagedata.up;
			dg("article_author_name").innerText = pagedata.usr;
			dg("article_content").innerHTML = pagedata.des;
			
			if (pagedata.vu) {
				letvPlayer = LETV.vod(pagedata.vu, "player", {
					fullscreen: 1
				});
			} else if (pageInfo.type == 2) {// 直播
				/*
				if (pagedata.stu == 2) {// 未开始
					jQuery("#player").html("<center>直播未开始</center>").show();
				} else if (pagedata.stu == 3) {// 已结束
					jQuery("#player").html("<center>直播已结束</center>").show();
				}
				*/
			}
			
			// 内容加载后，才判断文本是否超出
			(function ($) {
				setTimeout(function() {
					var p_hei = $(".zb_text .zb_text_samil #article_content").height();
					if (p_hei > $(".zb_text .zb_text_samil").height()) {
						$(".zb_text .more_span").css("display","block");
						$(".zb_text").css("padding-bottom","40px");
						$(".zb_text .more_span").toggle(
							function() {
								$(this).prev(".zb_text_samil").animate({height:p_hei},200);
								$(this).addClass("click");
								$(this).removeClass("click1");
							},
							function(){
								$(this).prev(".zb_text_samil").animate({height:110+"px"},200);
								$(this).removeClass("click");
								$(this).addClass("click1");
							}
						);
					}
					else{
						$(".zb_text .zb_text_samil").css("height",p_hei+"px");
					}
				}, 0);
			})(jQuery);
		}, function (errcode) {
			plus.nativeUI.toast("当前网络不可用，请检查你的网络设置");
		});
	}
	
	function share() {
		var sharedata = {
			tt: pagedata.share.tt
			, iu: pagedata.share.iu
			, shu: pagedata.share.shu
			, desc: pagedata.share.des
		};
		plus.pgpassport.requireShare(sharedata, function (data) {
			plus.nativeUI.toast("分享成功");
		}, function (code) {
//			alert("失败，错误码是："+code);
		});
	}
	
	var template = '<div class="mui-slider-right mui-disabled">'
		+ 	'<a class="mui-btn {color-class} reportordel" authorid="{ud}" reviewid="{rid}">{dowhat}</a>'
		+ '</div>'
		+ '<div class="mui-slider-handle">'
		+ 	'<div class="pl_list">'
		+		'<div>'
		+			'<a href=""><img src="{up}"></a>'
		+			'<p class="name">{un}<span>{pt}</span></p>'
		+		'</div>'
		+		'<p>{con}</p>'
		+	'</div>'
		+ '</div>'
		;
	function loadReviews(callback) {
		callback = callback || $.noop;
		App.Api.getReviews(pageInfo.type, pageInfo.id, reviewPage, function(data) {
			if (data.data === null) {
				if (reviewPage == 1) {
					// 禁止下拉获取数据逻辑
					UpRefresh.disableUpRefresh();
					// 提示暂时无评论
					document.getElementsByClassName("pl_cong_none")[0].style.display = "block";
					jQuery(".pl_cong_none").show();
				}
				callback(1);// 没有更多评论
				return;
			}
			var fragment = document.createDocumentFragment();
			var li;
			var tmpUserInfo = plus.pgpassport.getUserInfo();
			$.each(data.data.review, function(k, review) {
				li = document.createElement('li');
				li.className = "mui-table-view-cell";
				li.id = "review"+review.rid;
				review.pt = parseMsts(review.pt+"000");
				// 判断评论是自己的还是别人的
				var isOwner;
				if (!tmpUserInfo) {
					isOwner = false;
				} else {
					if (tmpUserInfo.id == review.ud) {
						isOwner = true;
					} else {
						isOwner = false;
					}
				}
				if (isOwner) {
					review["color-class"] = 'mui-btn-red';
					review.dowhat = "删除";
				} else {
					review["color-class"] = 'mui-btn-grey';
					review.dowhat = "举报";
				}
				
				var tmpHtml = $.dntools.substitute(template, review)+"";
				li.innerHTML = tmpHtml;
				fragment.appendChild(li);
			});
			var ele_reviews = dg("reviews");
			ele_reviews.appendChild(fragment);
			
			reviewPage++;
			callback(0);// 获取评论成功
		}, function () {
			callback(2);// 获取评论失败
		});
	}
	
	$.plusReady(function () {
		// 获取数据
		loadDetailData();
		loadReviews();
		
		// 监听请求退出全屏的事件
		window.addEventListener("requestExitVideoFullscreen", function () {
//			alert("退出全屏");
			var de = document;
			if (de.exitFullscreen) {
				de.exitFullscreen();
			} else if (de.mozCancelFullScreen) {
				de.mozCancelFullScreen();
			} else if (de.webkitCancelFullScreen) {
				de.webkitCancelFullScreen();
			}
		});
		
		// 进后台
		document.addEventListener("pause", function () {
			if (letvPlayer) {
				letvPlayer.sdk.pauseVideo();
			}
		});
		// 回前台
		document.addEventListener("resume", function () {
			if (letvPlayer) {
				letvPlayer.sdk.resumeVideo();
			}
		});
		
		// 允许内联播放
//		plus.webview.currentWebview().nativeInstanceObject().plusSetAttribute("allowsInlineMediaPlayback", true);
		// ios允许自动播放的前提条件
//		plus.webview.currentWebview().nativeInstanceObject().plusSetAttribute("mediaPlaybackRequiresUserAction", false);
			
		jQuery(".reportordel").live("tap", function() {
			var jq_this = jQuery(this);
			var reviewid = jq_this.attr("reviewid");
			var dowhat = jq_this.text();
			
			var data = {
				id: 	reviewid
				, type: pageInfo.type
			};
			switch (dowhat) {
				case "删除":
					//var btnArray = ['否', '是'];
					mui.confirm('您确定要删除此评论吗？', function(e) {
						if (e.index == 1) {
							setTimeout(function() {
								$.swipeoutClose(document.getElementById("review"+reviewid));
							}, 0);
							return false;
						} else {
							plus.pgpassport.requireLogin(function (userinfo) {
								data.ud = userinfo.id;
								data.token = userinfo.token;
								App.Api.deleteReview(data, function (d) {
									plus.nativeUI.toast("删除成功");
									var jq_review = jQuery("#review"+reviewid);
									jq_review.remove();
									
									// 评论都删光了
									if (jQuery(".reportordel").length < 1) {
										jQuery(".pl_cong_none").show();// 显示无评论提示
									}
								}, function () {
									plus.nativeUI.toast("删除失败");
								});
							});
						}
					});
				break;
				case "举报":
					App.Api.reportReview(data, function (d) {
						setTimeout(function() {
							$.swipeoutClose(document.getElementById("review"+reviewid));
						}, 0);
						
						plus.nativeUI.toast("举报成功");
					}, function () {
						plus.nativeUI.toast("当前网络不可用，请检查你的网络设置");
					});
				break;
			}
		});
		
		(function ($) {
			// 没登录，盖层
			if (!plus.pgpassport.getUserInfo()) {
				$(".input_ceng").css("display","block");
				$(".input_ceng").on("tap", function () {
					if (!plus.pgpassport.getUserInfo()) {
						if (letvPlayer) {
							letvPlayer.sdk.pauseVideo();
						}
						plus.pgpassport.requireLogin(function () {
							// TODO
							$(".input_ceng").css("display","none");
						});
					}
				});
			}
		})(jQuery);
		
		// 点击评论输入框，要求登录
		jQuery(".textbox").on("tap", function () {
			if (!plus.pgpassport.getUserInfo()) {
				if (letvPlayer) {
					letvPlayer.sdk.pauseVideo();
				}
				plus.pgpassport.requireLogin();
			}
		});
		
		jQuery("#reviewsender").on("click", function () {
			var content = jQuery.trim(jQuery(".textbox").val());
			if (content.length < 2) {
				plus.nativeUI.toast("评论需至少2个字");
				return;
			}
			if (content.length > 140) {
				plus.nativeUI.toast("评论字数超过140字");
				return;
			}
			
			plus.pgpassport.requireLogin(function (userinfo) {
				App.Api.postReview({
					ty: pageInfo.type
					, aid: pageInfo.id
					, ud: userinfo.id
					, cnt: content
					, token: userinfo.token
				}, function (data) {
					jQuery(".textbox").val("");// 置空评论内容
					jQuery(".textbox").blur();
					
					jQuery(".pl_cong_none").hide();// 隐藏无评论提示
					
					var li = document.createElement('li');
					li.className = "mui-table-view-cell";
					li.id = "review"+data.data.reviewid;
					var review = {
						up: userinfo.avatar
						, un: userinfo.name
						, pt: parseMsts(Date.parse(new Date()))
						, con: content
						, rid: data.data.reviewid
						, ud: userinfo.id
					};
					var isOwner;
					var tmpUserInfo = plus.pgpassport.getUserInfo();
					if (!tmpUserInfo) {
						isOwner = false;
					} else {
						if (tmpUserInfo.id == review.ud) {
							isOwner = true;
						} else {
							isOwner = false;
						}
					}
					if (isOwner) {
						review["color-class"] = 'mui-btn-red';
						review.dowhat = "删除";
					} else {
						review["color-class"] = 'mui-btn-grey';
						review.dowhat = "举报";
					}
					var tmpHtml = $.dntools.substitute(template, review)+"";
					li.innerHTML = tmpHtml;
					jQuery("#reviews").prepend(li);
					
					var jq_li = jQuery("#"+li.id).find(".pl_list");
					jq_li.css("opacity", 0);
					jq_li.animate({opacity: 1}, 2000);
					
					plus.nativeUI.toast("评论成功");
				}, function(errcode, data) {
					if (typeof(data.errmsg) != "undefined") {
						plus.nativeUI.toast("评论失败: "+data.errmsg);
					} else {
						plus.nativeUI.toast("当前网络不可用，请检查你的网络设置");
					}
				});
			}, function (code) {
				plus.nativeUI.toast("登录失败，错误码是："+code);
			});
		});
		
		document.getElementById("share").addEventListener("click", function () {
			share();
		});
	});
	
}(mui, document));