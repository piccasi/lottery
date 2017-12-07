package api.gateway.enums;

/**
 * @描述: . <br/>
 * @作者: zhaozm. <br/>
 * @创建时间: 2017-12-05 11:42 . <br/>
 * @版本号: V1.0.0 . <br/>
 */
public enum AppChannelEnums {

	
	/** 170 **/
	partner_170("10014", "7b9d31aa17b849b238ab79cef0733041");

	private String appId;
	private String appKey;

	private AppChannelEnums(String appId, String appKey) {
		this.appId = appId;
		this.appKey = appKey;
	}

	public static AppChannelEnums typeOf(String appId) {
		for (final AppChannelEnums option : AppChannelEnums.values()) {
			if (option.getAppId().equals(appId)) {
				return option;
			}
		}
		return null;
	}

	public String getAppId() {
		return this.appId;
	}

	public String getAppKey() {
		return this.appKey;
	}

}
