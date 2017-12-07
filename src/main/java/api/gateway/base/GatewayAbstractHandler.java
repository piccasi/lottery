package api.gateway.base;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;




import api.gateway.common.CommonResponse;
import api.gateway.enums.AppChannelEnums;
import api.gateway.util.MD5;



/**
 * API接口处理器基类
 *
 * @author zhaozm
 * @date 2017年12月05日上午11:16:39
 * @since 1.0.0
 *
 */
public abstract class GatewayAbstractHandler {

	private static final Logger logger = Logger.getLogger(GatewayAbstractHandler.class);

	/**
	 * 输入字符编码格式
	 */
	public static final String INPUT_CHARSET = "utf-8";

	/**
	 * 验证参数
	 *
	 * @param requireds
	 * @param params
	 * @throws Exception
	 */
	public void validParams(String[] requireds, Map<String, String> params) throws Exception {
		if (requireds != null && requireds.length > 0) {
			for (final String param : requireds) {
				final String paramValue = params.get(param);
				if (StringUtils.isEmpty(paramValue)) {
					throw new Exception(String.format("参数[%s]为空", param));
				}

				// 特殊处理时间戳
				if (param.equals("timestamp")) {
					try {
						new SimpleDateFormat("yyyyMMddHHmmss").parse(paramValue);
					} catch (final Exception e) {
						throw new Exception(String.format("时间戳[%s]格式不合法", param), e);
					}
				}
			}
		}
	}

	/**
	 * 验签
	 *
	 * @param signs
	 * @param params
	 * @throws Exception
	 */
	public void checkSign(String[] signs, Map<String, String> params) throws Exception {
		if (signs == null || signs.length == 0) {
			throw new Exception("参与签名的参数列表为空");
		}

		// 根据appid获取私钥
		final AppChannelEnums channelEnum = AppChannelEnums.typeOf(params.get("appid"));
		if (channelEnum == null) {
			throw new Exception("接入方不合法");
		}
		final String appKey = channelEnum.getAppKey();

		// 此处固定签名格式[param=paramValue],准备待签名串
		final StringBuffer signFormat = new StringBuffer();
		for (final String signParam : signs) {
			signFormat.append("[").append(signParam).append("=").append(params.get(signParam)).append("]");
		}
		final String waitSign = signFormat.toString();

		GatewayAbstractHandler.logger.info(String.format("等待签名的串为:%s,私钥为:%s", waitSign, appKey));

		// 生成签名
		final String signValue = MD5.sign(waitSign, appKey, GatewayAbstractHandler.INPUT_CHARSET);

		// 验证签名
		final String clientSignValue = params.get("signValue");

		GatewayAbstractHandler.logger.info(String.format("客户端签名为:%s,服务端签名为:%s", clientSignValue, signValue));

		if (StringUtils.isEmpty(clientSignValue) || !clientSignValue.equals(signValue)) {
			throw new Exception("验签失败");
		}
	}

	/**
	 * 业务处理
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public abstract CommonResponse handler(Map<String, String> params,String [] resStrs,String [] reqStrs) throws Exception;

	

}
