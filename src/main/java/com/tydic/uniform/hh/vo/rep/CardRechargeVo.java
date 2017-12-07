package com.tydic.uniform.hh.vo.rep;
/**
 * 
 * <p></p>
 * @author Administrator 2015年10月18日 下午12:25:47
 * @ClassName CardRechargeVo
 * @Description 充值卡充值
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月18日
 * @modify by reason:{方法名}:{原因}
 */
public class CardRechargeVo {
	private String request_id;
	private String request_user;
	private String request_time;
	private String card_pin;
	private String destination_id;
	private String access_type;
	private String channel_code;
	
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getRequest_user() {
		return request_user;
	}
	public void setRequest_user(String request_user) {
		this.request_user = request_user;
	}
	public String getRequest_time() {
		return request_time;
	}
	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}
	public String getCard_pin() {
		return card_pin;
	}
	public void setCard_pin(String card_pin) {
		this.card_pin = card_pin;
	}
	public String getDestination_id() {
		return destination_id;
	}
	public void setDestination_id(String destination_id) {
		this.destination_id = destination_id;
	}
	public String getAccess_type() {
		return access_type;
	}
	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}
	public String getChannel_code() {
		return channel_code;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	
}
