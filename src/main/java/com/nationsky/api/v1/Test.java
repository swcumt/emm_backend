package com.nationsky.api.v1;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.nationsky.utils.Http;
import com.nationsky.webapp.util.Utils;

public class Test {
	
	public static void main(String[] args) {
		String json = "{     \"sdk\": {         \"sdk_list\": [             {                 \"sdk_name\": \"update\",                 \"main_header\": \"NQUpdate.h\",                 \"lib\": [],                 \"web_config\": [                     {                         \"lable_text\": \"更新服务器链接\",                         \"tag\": \"input\",                         \"tag_type\": \"text\",                         \"name\": \"update_url\",                         \"value\": \"http://www.baidu.com\",                         \"value_type\": \"String\",                         \"isDefine\": 1,                         \"define_Key\": \"UPDATE_URL\"                     }                 ],                 \"sdk_path\": \"/Users/andy/Desktop/emm_sdk_ric/service_sdk/1001/update.zip\"             },             {                 \"sdk_name\": \"encrypt\",                 \"main_header\": \"NQEncrypt.h\",                 \"lib\": [                     \"AES256\",                     \"FMDB\",                     \"GTMBase64\"                 ],                 \"web_config\": [                     {                         \"lable_text\": \"更新服务器链接\",                         \"tag\": \"input\",                         \"tag_type\": \"text\",                         \"name\": \"update_url\",                         \"value\": \"\",                         \"value_type\": \"String\",                         \"isDefine\": 0,                         \"define_Key\": \"UPDATE_URL\"                     }                 ],                 \"sdk_path\": \"/Users/andy/Desktop/emm_sdk_ric/service_sdk/1002/encrypt.zip\"             },             {                 \"sdk_name\": \"push\",                 \"main_header\": \"NQPush.h\",                 \"lib\": [],                 \"web_config\": [                     {                         \"lable_text\": \"更新服务器链接\",                         \"tag\": \"input\",                         \"tag_type\": \"text\",                         \"name\": \"update_url\",                         \"value\": \"http://www.google.com\",                         \"value_type\": \"String\",                         \"isDefine\": 1,                         \"define_Key\": \"DEVICE_TOKEN\"                     }                 ],                 \"sdk_path\": \"/Users/andy/Desktop/emm_sdk_ric/service_sdk/1003/push.zip\"             }         ],         \"output_name\": \"NQSDK\",         \"output_path\": \"/Users/andy/desktop/emm_sdk_ric/sdk_zip\",         \"app_version_Id\": \"0001\"     } }";
		System.out.println(json);
//		String str = Http.sendPost("http://172.27.35.11:3012/config", "json="+json);
		String str = Http.sendPost(Utils.getPropertiesValue("APP_PACKAGING_URL"), "json="+json);
		System.out.println(str);
	}
	
	public static void main1(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode root = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();
		ObjectNode serviceNode = null;
		ObjectNode configNode = null;

		root.put("SDK", 1);
		root.put("APP_STORE_ID", 1);
		root.put("APP_VERSION_ID", 1);
		root.put("SDK_PATH", 1);

		// 版本更新
		configNode = mapper.createObjectNode();
		configNode.put("UPDATE_URL", "www.baidu.com");

		serviceNode = mapper.createObjectNode();
		serviceNode.put("ID", 1);
		serviceNode.put("NAME", "update");
		serviceNode.put("CONFIG", configNode);
		arrayNode.add(serviceNode);

		// 加密服务
		configNode = mapper.createObjectNode();
		configNode.put("CONFIG1", "加密服务其他配置");
		configNode.put("CONFIG2", "加密服务其他配置");
		configNode.put("CONFIG3", "加密服务其他配置");

		serviceNode = mapper.createObjectNode();
		serviceNode.put("ID", 2);
		serviceNode.put("NAME", "encrypt");
		serviceNode.put("CONFIG", configNode);
		arrayNode.add(serviceNode);

		// 推送服务
		configNode = mapper.createObjectNode();
		configNode.put("CONFIG1", "推送服务其他配置");
		configNode.put("CONFIG2", "推送服务其他配置");
		configNode.put("CONFIG3", "推送服务其他配置");

		serviceNode = mapper.createObjectNode();
		serviceNode.put("ID", 3);
		serviceNode.put("NAME", "push");
		serviceNode.put("CONFIG", configNode);

		arrayNode.add(serviceNode);
		root.put("SDK", arrayNode);
		try {
//			System.out.println(mapper.defaultPrettyPrintingWriter().writeValueAsString(root));
			mapper.defaultPrettyPrintingWriter().writeValueAsString(root);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readJson2List();
	}

	public static void readJson2List() {

		String APP_SERVICE_SDK_FOLDER_PATH = Utils.getPropertiesValue("APP_SERVICE_SDK_FOLDER_PATH");

		String json = "{\"sdk_name\":\"update\",\"main_header\":\"NQUpdate.h\",\"lib\":[],\"sdk_path\":\"/Users/andy/Desktop/emm_sdk/service_sdk/1001/update.zip\",\"web_config\":[{\"lable_text\":\"更新服务器链接\",\"tag\":\"input\",\"tag_type\":\"text\",\"name\":\"update\",\"value\":\"a\",\"value_type\":\"String\",\"isDefine\":1,\"define_Key\":\"UPDATE_URL\"},{\"lable_text\":\"更新服务器链接2\",\"tag\":\"input\",\"tag_type\":\"text\",\"name\":\"delete\",\"value\":\"b\",\"value_type\":\"String\",\"isDefine\":1,\"define_Key\":\"UPDATE_URL\"},{\"lable_text\":\"更新服务器链接3\",\"tag\":\"input\",\"tag_type\":\"text\",\"name\":\"save\",\"value\":\"c\",\"value_type\":\"String\",\"isDefine\":1,\"define_Key\":\"UPDATE_URL\"}]}";
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode rootNode = mapper.createObjectNode();
			ArrayNode arrayNode = mapper.createArrayNode();
			ObjectNode configNode = mapper.createObjectNode();
			Map<String, Object> objMap = mapper.readValue(json, Map.class);
			System.out.println(objMap.size());

			objMap.put("sdk_path", APP_SERVICE_SDK_FOLDER_PATH + "000//000.zip");

			arrayNode.addPOJO(objMap);
			arrayNode.addPOJO(objMap);
			arrayNode.addPOJO(objMap);

			configNode.put("output_name", "NQSDK");
			configNode.put("output_path", "/Users/andy/desktop/emm_sdk/sdk_zip");
			configNode.put("app_version_id", "1000001");
			configNode.put("app_id", "1000002");
			configNode.put("sdk_list", arrayNode);
			rootNode.put("sdk", configNode);
			String configJsonFilePath = "C:" + File.separator + "SDK" + File.separator + "temp" + File.separator + "json.json";
			mapper.defaultPrettyPrintingWriter().writeValue(new File(configJsonFilePath), rootNode);
			System.out.println(mapper.defaultPrettyPrintingWriter().writeValueAsString(rootNode));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
