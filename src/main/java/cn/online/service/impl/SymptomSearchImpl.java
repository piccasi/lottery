package cn.online.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import cn.online.pojo.BaseSearch;

import com.tcl.ListMapBafVariantsImpl;
import com.tcl.MapBafVariantsImpl;
import com.tcl.SelfTest;
import com.tcl.StandBafVariants;
import common.ServiceUtil;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-5-9 下午11:09:31 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service("symptomSearch")
public class SymptomSearchImpl extends AbstractSearch {
	public List<String> search(BaseSearch baseSearch){
		String ret = callBackend(baseSearch);
		
		List<String> ll = null;
		try {
			ll = convertTclToObject(String.class,  ret);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		int count = 1;
		for( String map : ll){
			System.out.println("---------第 " + count + " 条------------" );
			System.out.println(map);
/*			for(Map.Entry<String, String> entry : map.entrySet()){
				System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
			}*/
			++count;
		}
		return ll;
	}


	public String searchby(BaseSearch baseSearch) {

		//BaseSearch baseSearch = new BaseSearch();
		//baseSearch.setKeyWords(keyWord);
		//baseSearch.setType("2");
		SelfTest manager = ServiceUtil.getSelfTest();
		manager.removeAllAction();
		manager.addAction(baseSearch);
		try {
			manager.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String ret = manager.commonResult.getMessage();
		System.out.println("ret: " + ret);
		//ret = "{{ name :'蒲地蓝消炎口服液',desc :'清热解毒，抗炎消肿。用于疖肿、腮腺炎、咽炎、扁桃体炎等。   ',img :'http://img.39.net/yp/360/s/622325.jpg' }{ name :'蒲地蓝消炎片',desc :'清热解毒，消炎止痛，舒筋活络。用于流行性感冒，咽喉炎，肺炎，菌痢，急性胃肠炎，阑尾炎，烧伤，疮疡脓肿，蜂窝织炎。   ',img :'http://img.39.net/yp/360/2nd/S/5/509700.jpg' }{ name :'蒲地蓝消炎胶囊',desc :'用于治疗病毒性感染和自身免疫性疾病：1.皮肤病；扁平疣，带状疱疹，单纯疱疹，两周为一个疗程；银痟病，尖锐湿疣等，三个月为一个疗程；2.呼吸道疾病；反复感冒，支气管炎，哮喘，慢性支气管炎，过敏性鼻...  ',img :'http://img.39.net/ypk/images/nopic.gif' }}";
		//System.out.println("ret: " + ret);
		StandBafVariants result = MapBafVariantsImpl.getInstance(ret);
		
		ret = result.getParameter("RESULT");
		//ret = "{{{JDK_NAME {急性乙肝}} {JDK_JS {我国HBV感染者为数众多，临床乙型肝炎病人要确定其为急性或慢性需作全面分析。少数急性病例病程迁延转为慢性，或发展为肝硬化甚至肝癌;重者病情进展迅猛可发展为重型肝炎;另一些感染者则成为无症状的病毒携带者。目前乙型肝炎虽是我国最重要的肝炎，但在急性病毒性肝炎中，急性乙型肝炎已远较甲型肝炎和戊型肝炎为少，临床初步诊断的急性乙型肝炎，大部分是慢性无症状HBV携带者(AsC)的急性活动。因此急性乙型肝炎已不很多见。包括}} {JDK_BODY {肝}} {JDK_INFECT {有}} {JDK_HUMAN {中年多发}} {JDK_METHOD {}} {JDK_PERIOD {}} {JDK_CURE {}} {JDK_FEE {}} {JDK_KESHI {肝炎传染科}} {JDK_YIBAO {非医保疾病}} {JDK_CHECK {血清r-谷氨酰基转移酶健康体检丙氨酸氨基转移酶(ALT）血氨肝功能检查血常规尿常规肝脏疾病超声诊断甲型肝炎抗原淋巴细胞转换试验（LTT）}} {JDK_DRUG {甘草甜素片苦参素片碧云砂乙肝颗粒}}} {{JDK_NAME {慢性乙肝}} {JDK_JS {慢性乙型肝炎为乙肝常见的类型。乙肝的特点为起病较缓，以亚临床型及慢性型较常见。急性无黄疸型HBsAg持续阳性者易慢性化。慢性肝炎病程常超过半年，可隐匿发病，常在体检时发现。症状多种多样，反复发作或迁延不愈。急性乙型肝炎转变为慢性肝炎者估计有5%～10%。急性乙型肝炎的慢性化主要取决于初次感染的年龄、免疫状态及病毒水平。婴幼儿期感染易发展为慢性，应用免疫抑制剂和细胞毒药物的病人、血透的慢性肾衰竭病人，常缺乏明显的急性期表现，病情迁延。病毒复制标志(HBeAg、HBVDNA)的血清水平很高的病人，较易发展为慢性肝炎。慢性乙肝}} {JDK_BODY {肝}} {JDK_INFECT {有}} {JDK_HUMAN {所有人群}} {JDK_METHOD {}} {JDK_PERIOD {}} {JDK_CURE {}} {JDK_FEE {}} {JDK_KESHI {肝炎消化内科}} {JDK_YIBAO {非医保疾病}} {JDK_CHECK {丙氨酸氨基转移酶(ALT）}} {JDK_DRUG {拉米夫定片阿德福韦酯胶囊华蟾素胶囊}}} {{JDK_NAME {重型乙肝}} {JDK_JS {重型乙型肝炎的发病率约占乙型肝炎的1%左右。以青年居多。劳累、酗酒、感染、营养不良、末期妊娠、病毒重叠感染等均可促使急、慢性肝炎的病情转重。重型肝炎的病情重，变化快，病死率高，要注意早诊早治，全力以赴进行抢救。重型乙肝}} {JDK_BODY {肝}} {JDK_INFECT {有}} {JDK_HUMAN {有肝炎病毒史}} {JDK_METHOD {}} {JDK_PERIOD {}} {JDK_CURE {}} {JDK_FEE {}} {JDK_KESHI {消化内科传染科}} {JDK_YIBAO {非医保疾病}} {JDK_CHECK {}} {JDK_DRUG {}}} {{JDK_NAME {乙肝}} {JDK_JS {乙肝(viralhepatitistypeB，又称乙型病毒性肝炎)系由乙肝病毒(HBV)引起，临床表现为}} {JDK_BODY {肝}} {JDK_INFECT {有}} {JDK_HUMAN {所有人群，主要见于青少年，绝大多数为10~30岁}} {JDK_METHOD {药物治疗、饮食疗法}} {JDK_PERIOD {3-6月}} {JDK_CURE {0.0001%}} {JDK_FEE {市三甲医院约（3000——8000元）;}} {JDK_KESHI {传染科肝病}} {JDK_YIBAO {非医保疾病}} {JDK_CHECK {病毒学指标凝血酶原时间（PT）五项乙型肝炎抗原抗体检测（HBcAb）血清白蛋白与球蛋白比值（AG）HBsAg免疫复合物血清前S1蛋白血清前S2蛋白血清抗前S2蛋白免疫球蛋白M}} {JDK_DRUG {恩替卡韦分散片碧云砂颗粒注射用盐酸精氨...}}}}";
		System.out.println("ret1: " + ret);
		//ret = ret.substring(1, ret.length() - 1);
		//System.out.println("ret2: " + ret);
		//StandBafVariants result =  MapBafVariantsImpl.getInstance(ret);
		
		List<Map<String, String>> parameter = ListMapBafVariantsImpl.initParameterByTclStr("{" + ret + "}");
		
		int count = 1;
		for(Map<String, String> map : parameter){
		//Map map = result.getMapValue();
			System.out.println("-----------第 " + count + " 项------------");
			Set mappings = map.entrySet();
	        for (Iterator i = mappings.iterator(); i.hasNext();) {
	            Map.Entry me = (Map.Entry) i.next();
	            //tclMsg.append((String) me.getKey(), (String) me.getValue());
	            System.out.println((String) me.getKey() + " , " + (String) me.getValue());
	        }
	        ++count;
		}

		
		return result.getParameter("RESULT_LIST");
	
	}

	public static void main(String[] args){
		BaseSearch baseSearch = new BaseSearch();
		baseSearch.setKeyWords("感冒、流鼻涕");
		baseSearch.setType("3");
		SymptomSearchImpl symptomSearchImpl = new SymptomSearchImpl();
		symptomSearchImpl.search(baseSearch);

	}
}
