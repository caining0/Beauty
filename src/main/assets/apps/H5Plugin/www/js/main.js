var reloadWeb;
var closeWeb;
var requestExitVideoFullscreen;
(function($, doc) {
	//启用双击监听
	$.init({
		gestureConfig:{
			doubletap:true
		},
		statusBarBackground: '#00bcd4'
	});
	
	closeWeb = function () {
		var self = plus.webview.currentWebview();
		var subs = self.children();
		for (var i=0;i<subs.length;i++) {
			subs[i].close();
		}
	}
	
	reloadWeb = function (data_id, data_type) {
		reload(data_id, data_type);
	}
	
	function reload(data_id, data_type) {
		var article_template;
		if (data_type == 1) {
			article_template = "imgtext.html";
		} else {
			article_template = "live.html";
		}
		var hashparam = "#data_id="+data_id+"&data_type="+data_type;
		var imgtextpage = App.webview.create(article_template + hashparam, article_template,  {
				top: '0px',
				bottom: '0px'
				, hardwareAccelerated:true
				, softinputMode: "adjustResize"
		});
		imgtextpage.addEventListener("loaded", function() {
			imgtextpage.nativeInstanceObject().plusSetAttribute("allowsInlineMediaPlayback", true);
//			imgtextpage.nativeInstanceObject().plusSetAttribute("mediaPlaybackRequiresUserAction", false);ß
		});
		
		var self = plus.webview.currentWebview();
		self.append(imgtextpage);
	}
	
	$.plusReady(function () {
		// 获取传递过来的参数
		var passdata = location.hash;
	    var hash_params = passdata.split("&");
	    var data_id;
	    var data_type = 0;
	    if (passdata.indexOf('&') != -1 && hash_params.length == 2){
	    		data_id = hash_params[0].split("=")[1];
	        data_type = hash_params[1].split("=")[1];
	    }
	    
		if (plus.os.name == "Android") {
			if (plus.runtime.arguments) {
				var args = JSON.parse(plus.runtime.arguments);
				data_id = args.id;
				data_type = args.type;
			}
		}
		
		(function () {
			var fullscreen = false;
			/**
			 * 该方法由android端调用
			 */
			requestExitVideoFullscreen = function () {
				if (fullscreen) {
					// 通知子页面退出全屏
					App.webview.fire("imgtextlivepage", "requestExitVideoFullscreen");
					return "donothing";
				} else {
					return "back";
				}
			};
			window.addEventListener("ooo", function (event) {
				fullscreen = event.detail.fullscreen;
				requestExitVideoFullscreen();
			});
		})();
		
		data_id = data_id || 156;
	    data_type = data_type || 2;
		reload(data_id, data_type);
	});
}(mui, document));
