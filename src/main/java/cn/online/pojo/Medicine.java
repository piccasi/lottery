package cn.online.pojo;
/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-6 下午9:40:17 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class Medicine {
	private String name;
	private String desc;
	private String img;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	@Override
	public String toString() {
		return "Medicine [name=" + name + ", desc=" + desc + ", img=" + img + "]";
	}
	
	
}
