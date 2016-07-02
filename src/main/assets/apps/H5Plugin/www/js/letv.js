
var LETV_Callback;
var LETV = (function () {
	var uu = "cfdce463f4";
	var cur_eleid;// 当前播放视频所处html播放容器的id
	var cur_ele_oldwidth;// 存放播放容器宽度，以便从全屏恢复时使用
	var cur_ele_oldheight;// 存放播放容器的高度
	var resolutionHeight;// 设备屏幕高度分辨率
	var resolutionWidth;// 设备屏幕宽度分辨率
	var player;// 有潜在的问题
	
    LETV_Callback = function (type) {
        switch (type) {
        		case "fullscreen":
        			var setting = player.sdk.getVideoSetting();
        			//alert("全屏："+setting.fullscreen);
        			// if (setting.fullscreen) {
        			// 	plus.screen.lockOrientation("landscape-primary");
        			// } else {
        			// 	plus.screen.lockOrientation("portrait-primary");
        			// }
        			if (setting.fullscreen) {
        				plus.pgpassport.changeStatusBarOrientation('landscape-primary');
        			} else {
        				plus.pgpassport.changeStatusBarOrientation('portrait-primary');
        			}
        			break;
        	}
        console.log("typeis: "+ type);
    }
    
	/**
	 * 
	 * @param {String} vu 乐视视频id，如：c472e261b4
	 * @param {String} eleid html用于展示视频的元素id
	 * @param {Object} conf 播放器配置
	 * @return {Object} 播放器的引用
	 */
	function vod(vu, eleid, conf) {
		conf = conf || {};
		cur_eleid = eleid;
		
		var ele_player = document.getElementById(cur_eleid);
		ele_player.style.display = "block";
		cur_ele_oldwidth = ele_player.style.width;
		cur_ele_oldheight = ele_player.style.height;
        			
		player = new CloudVodPlayer();
		var target_conf = {
        		autoplay: 0
        		, controls: 1
        		, dfull: 0
        		, fullscreen: 0
        		, definition: 1
        		, skinnable: 0
        		, callbackJs: "LETV_Callback"
        	};
        	conf.uu = uu;
        	conf.vu = vu;
        	mui.extend(target_conf, conf);
        player.init(target_conf, cur_eleid);
        	
        	return player;
	}
	
	/**
	 * 
	 * @param {String} activityId 直播活动id
	 * @param {String} eleid html用于展示视频的元素id
	 * @param {Object} conf 播放器配置
	 * @return {Object} 播放器的引用
	 */
	function live(activityId, eleid, conf) {
		conf = conf || {};
		cur_eleid = eleid;
		
		var ele_player = document.getElementById(cur_eleid);
		ele_player.style.display = "block";
		cur_ele_oldwidth = ele_player.style.width;
		cur_ele_oldheight = ele_player.style.height;
		
		player = new CloudLivePlayer();
		var target_conf = {
        		autoplay: 0
        		, controls: 1
        		, dfull: 0
        		, fullscreen: 0
        		, definition: 1
        		, skinnable: 1
        		, callbackJs: "LETV_Callback"
        	};
        	conf.activityId = activityId;
        	mui.extend(target_conf, conf);
        player.init(target_conf, eleid);
        	
        	return player;
	}
	
	return {
		vod: vod	
		, live: live
	};
})();
