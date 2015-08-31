package com.xinxin.everyxday.global;

/**
 * 全局静态常量
 */
public class Globe {
	
	//请求时间戳
	public static long REQUEST_TIMESTAMP = 0;

	public static final String DB_NAME = "like";

	public static final String WX_APP_ID = "wxcf223f10cc5cc73d";

	public static final String QQ_APP_ID = "1104759471";

	public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}

	public static final String APP_TARGET_URL = "http://www.taoxiaoxian.com";

	public static final String SINA_APP_KEY = "3778651605";
	public static final String SINA_REDIRECT_URL = "http://www.taoxiaoxian.com";
	public static final String SINA_SCOPE = "email,direct_messages_read,direct_messages_write,"
		+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

}
