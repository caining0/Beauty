(function($, owner) {
	/**
	 * ajax请求错误码
	 */
	owner.AJAX_ERROR_CODE = {
		NETSTATUS_NOT_OK: 1
		, API_NOT_OK: 2
		, CANCEL_REQUEST: 3
		, TIMEOUT: 4
		, NETWORK: 5
	};
	/**
	 * ajax请求错误描述
	 */
	owner.AJAX_ERROR_MSG  = {
		NETSTATUS_NOT_OK: "网络状态非200"
		, API_NOT_OK: "api返回状态非ok"
		, CANCEL_REQUEST: "取消网络请求"
		, TIMEOUT: "请求超时"
		, NETWORK: "网络错误"
	};
	
	
	var api_prefix = "http://mapi.onlylady.com/beauty/v100/";
	owner.post = function (api_url, request_params, successcb, errorcb, options) {
		options = options || {};
		options.type = "post";
		request(api_url, request_params, successcb, errorcb, options);
	};
	owner.get = function (api_url, request_params, successcb, errorcb, options) {
		options = options || {};
		options.type = "get";
		request(api_url, request_params, successcb, errorcb, options);
	};
	
	var request = function (api_url, request_params, successcb, errorcb, options) {
		request_params = request_params || {};
		options = options || {};
		successcb = successcb || $.noop;
		errorcb = errorcb || $.noop;
		
		var default_options = {
			dataType: 'json'
			, type: 'get'
			, timeout: 20000// 超时10秒
		};
		if (request_params) {
			default_options.data = request_params;
		}
		
		$.extend(default_options, options);
		$.extend(default_options, {
			success: function (data) {
				successcb(data);
			}
			, error: function (xhr, type, errorThrown) {
				var error_code = owner.AJAX_ERROR_CODE.NETWORK;
				// type：错误描述，可取值："timeout", "error", "abort", "parsererror"、"null"
				switch (type) {
					case 'timeout':
						error_code = owner.AJAX_ERROR_CODE.TIMEOUT;
						break;
					case 'abort':
						error_code = owner.AJAX_ERROR_CODE.CANCEL_REQUEST;
						break;
				}
				
				errorcb(error_code, {errorThrown: errorThrown});
			}
		});
		
		$.ajax(api_url, default_options);
	};
	
	owner.print = function (data) {
		console.log(JSON.stringify(data));
	};
	
	/**
	 * 包装创建webview以及触发自定义事件的方法，目的是修复$.fire方法在webview未完成loaded事件时
	 * 无法触发的bug
	 */
	owner.webview = (function () {
		function getStoreKey(id) {
			if (typeof(id) != "string") {
				throw "无效的id: 需字符串";
			}
			if (id.length < 1) {
				throw "无效的id: 长度需大于1";
			}
			return "APP-webview-xxx-"+id;
		}
		var $false = "0";
		var $true = "1";
		
		function create (url, id, styles, extras) {
			var storageKey = getStoreKey(id);
			plus.storage.setItem(storageKey, $false);
			var page = plus.webview.create(url, id,  styles, extras);
			page.addEventListener("loaded", function () {
				plus.storage.setItem(storageKey, $true);
			});
			
			return page;
		}
		
		/**
		 * 
		 * @param {String} page_id 需传值的目标webview的id
		 * @param {String} event 自定义事件名称
		 * @param {JSON} data json格式的数据
		 */
		function fire(page_id, event, data) {
			var storageKey = getStoreKey(page_id);
			var target = plus.webview.getWebviewById(page_id);
			if (plus.storage.getItem(storageKey) == $true) {
				$.fire(target, event, data);
			} else {
				target.addEventListener("loaded", function () {
					plus.storage.setItem(storageKey, $true);
					$.fire(target, event, data);
				});
			}
		}
		
		function preload(conf) {
			var storageKey = getStoreKey(conf.id);
			plus.storage.setItem(storageKey, $false);
			var page = mui.preload(conf);
			page.addEventListener("loaded", function () {
				plus.storage.setItem(storageKey, $true);
			});
			
			return page;
		}
		
		function openWindow(conf) {
			var storageKey = getStoreKey(conf.id);
			plus.storage.setItem(storageKey, $false);
			var page = mui.openWindow(conf);
			page.addEventListener("loaded", function () {
				plus.storage.setItem(storageKey, $true);
			});
			
			return page;
		}
		
		return {
			create: create
			, fire: fire
			, preload: preload
			, openWindow: openWindow
		};
	})();
	
}(mui, window.App = {}));