package com.tydic.uniform.hh.constant;


/**
 * 错误编码定义枚举类
 * @author fusionZ
 *
 */
public enum CODE{
	SUCCESS{
		@Override
		public int value() {
			return 0;
		}

		@Override
		public String desc() {
			return "everything goes well";
		}

		@Override
		public String getDisplayMsg() {
			return desc();
		}
	},
	TIMEOUT {
		@Override
		public int value() {
			return -1;
		}

		@Override
		public String desc() {
			return "请求超时";
		}

		@Override
		public String getDisplayMsg() {
			return desc();
		}
	},
	REQERR {
		@Override
		public int value() {
			return -13;
		}

		@Override
		public String desc() {
			return "请求非法";
		}

		@Override
		public String getDisplayMsg() {
			return desc();
		}
	},
	VALIDATE_ERROR{
		@Override
		public int value() {
			return -2;
		}

		@Override
		public String desc() {
			return "验证码错误";
		}

		@Override
		public String getDisplayMsg() {
			return desc();
		}
	},PARAMETER_ERROR{
		public int value(){
			return -3;
		}

		@Override
		public String desc() {
			return "参数错误";
		}

		@Override
		public String getDisplayMsg() {
			return "我要白菜你却给我萝卜";
		};
	},SIGN_ERROR{
		public int value(){
			return -4;
		}

		@Override
		public String desc() {
			return "签名错误";
		}

		@Override
		public String getDisplayMsg() {
			return "你肯定忘记了我们约定的暗语";
		};
	},NOT_LOGGED {
		@Override
		public int value() {
			return -999;
		}

		@Override
		public String desc() {
			return "请重新登录";
		}

		@Override
		public String getDisplayMsg() {
			return desc();
		}
	},PWD_ERROR{
		public int value(){
			return -5;
		}

		@Override
		public String desc() {
			return "密码错误";
		}

		@Override
		public String getDisplayMsg() {
			return "用户名密码错误";
		};
	}, ACCOUNT_NOT_EXSIT {
		public int value(){
			return -6;
		}

		@Override
		public String desc() {
			return "账号不存在";
		}

		@Override
		public String getDisplayMsg() {
			return "用户名或密码错误";
		};
	}, UNKNOW {
		public int value(){
			return -7;
		}

		@Override
		public String desc() {
			return "未知错误";
		}

		@Override
		public String getDisplayMsg() {
			return "抱歉，系统遇到一点问题，请稍候再试";
		};
	}, AGENT_CODE_ERROR {
		public int value(){
			return -8;
		}

		@Override
		public String desc() {
			return "代理商编码错误";
		}

		@Override
		public String getDisplayMsg() {
			return desc();
		};
	}, NOT_AGENT_ACCOUNT {
		public int value(){
			return -9;
		}

		@Override
		public String desc() {
			return "非代理商工号，请验证";
		}

		@Override
		public String getDisplayMsg() {
			return desc();
		};
	}, INTERFACE_ERR {
		public int value(){
			return -10;
		}

		@Override
		public String desc() {
			return "接口错误";
		}

		@Override
		public String getDisplayMsg() {
			return "抱歉，系统遇到一点问题，请稍候再试";
		};
	},  AMBIGUOUS {
		public int value(){
			return -11;
		}

		@Override
		public String desc() {
			return "参数有歧义";
		}

		@Override
		public String getDisplayMsg() {
			return "收到你的消息有点困惑，不知道该怎么办";
		};
	}, MOBILE_ERR {
		public int value(){
			return -12;
		}

		@Override
		public String desc() {
			return "账号不对，找不到对应的手机号码";
		}

		@Override
		public String getDisplayMsg() {
			return "发送验证码错误";
		};
	};
	
	//错误编码
	abstract public int value();
	//后台错误描述
	abstract public String desc();
	//返回给前台的错误提示
	abstract public String getDisplayMsg();
}
