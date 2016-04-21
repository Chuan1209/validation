package com.keruyun.gateway.validation.utils;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Gson 封装
 * Created by youngtan99@163.com on 16/3/18.
 */
public class GsonWrapper {
    /**
     *
     */
    public static final GsonBuilder GSON_BUILDER;

    public static final Gson GSON;

    static {
        GSON_BUILDER = new GsonBuilder()
                .registerTypeAdapter(LinkedTreeMap.class, new LinkedTreeMapDeserializer())
                .registerTypeAdapter(TreeMap.class, new TreeMapDeserializer())
                .registerTypeAdapter(HashMap.class, new HashMapDeserializer())
                .registerTypeAdapter(Map.class, new MapDeserializer())
//                .registerTypeAdapter(List.class,new ListDeserializer())
                .disableHtmlEscaping();
        GSON = GSON_BUILDER.create();
    }

    /**
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     * @param json
     * @param typeToken
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(String json, TypeToken<T> typeToken) throws JsonSyntaxException {
        return GSON.fromJson(json, typeToken.getType());
    }

    /**
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return GSON.fromJson(json, classOfT);
    }

    /**
     *
     */
    static class LinkedTreeMapDeserializer implements JsonDeserializer<LinkedTreeMap> {
        @Override
        public LinkedTreeMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            LinkedTreeMap resultMap = new LinkedTreeMap();
            JsonObject jsonObject = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
            return resultMap;
        }
    }

    /**
     *
     */
    static class TreeMapDeserializer implements JsonDeserializer<TreeMap> {
        public TreeMap deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            TreeMap resultMap = new TreeMap();
            JsonObject jsonObject = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
            return resultMap;
        }
    }

    /**
     *
     */
    static class HashMapDeserializer implements JsonDeserializer<HashMap> {
        public HashMap deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            HashMap resultMap = new HashMap();
            JsonObject jsonObject = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
            return resultMap;
        }
    }

    /**
     *
     */
    static class MapDeserializer implements JsonDeserializer<Map> {
        public Map deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map resultMap = new LinkedTreeMap<>();
            JsonObject jsonObject = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
            return resultMap;
        }
    }

    static class ListDeserializer implements JsonDeserializer<List> {
        public List deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List list = new ArrayList<>();
            JsonArray jsonArray = json.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                list.add(jsonElement);
            }
            return list;
        }
    }


    public static void main(String[] args) {
        String s = "{\n" +
                "  \"body\": {\n" +
                "    \"source\": \"65487\",\n" +
                "    \"shop\": {\n" +
                "      \"id\": \"247900001\",\n" +
                "      \"name\": \"客如云测试\",\n" +
                "      \"baidu_shop_id\": \"1486061032\"\n" +
                "    },\n" +
                "    \"order\": {\n" +
                "      \"order_id\": \"14582819487103\",\n" +
                "      \"send_immediately\": 1,\n" +
                "      \"send_time\": \"1\",\n" +
                "      \"send_fee\": 0,\n" +
                "      \"package_fee\": 0,\n" +
                "      \"discount_fee\": 0,\n" +
                "      \"total_fee\": 3,\n" +
                "      \"shop_fee\": 3,\n" +
                "      \"user_fee\": 3,\n" +
                "      \"pay_type\": 1,\n" +
                "      \"pay_status\": 1,\n" +
                "      \"need_invoice\": 1,\n" +
                "      \"invoice_title\": \"发票信息0001\",\n" +
                "      \"remark\": \"请提供餐具,辣一点\",\n" +
                "      \"delivery_party\": 2,\n" +
                "      \"create_time\": \"1458281948\"\n" +
                "    },\n" +
                "    \"user\": {\n" +
                "      \"name\": \"pan\",\n" +
                "      \"phone\": \"13608050511\",\n" +
                "      \"gender\": 1,\n" +
                "      \"address\": \"首都体育馆南路\",\n" +
                "      \"coord\": {\n" +
                "        \"longitude\": 116.332561,\n" +
                "        \"latitude\": 39.929333\n" +
                "      }\n" +
                "    },\n" +
                "    \"products\": [\n" +
                "      {\n" +
                "        \"product_id\": \"55\",\n" +
                "        \"upc\": \"\",\n" +
                "        \"product_name\": \"火爆肥肠\",\n" +
                "        \"product_price\": 2,\n" +
                "        \"product_amount\": 1,\n" +
                "        \"product_fee\": 2,\n" +
                "        \"package_price\": 0,\n" +
                "        \"package_amount\": \"1\",\n" +
                "        \"package_fee\": 0,\n" +
                "        \"total_fee\": 2\n" +
                "      },\n" +
                "      {\n" +
                "        \"product_id\": \"13934\",\n" +
                "        \"upc\": \"\",\n" +
                "        \"product_name\": \"麻婆豆腐\",\n" +
                "        \"product_price\": 1,\n" +
                "        \"product_amount\": 1,\n" +
                "        \"product_fee\": 1,\n" +
                "        \"package_price\": 0,\n" +
                "        \"package_amount\": \"1\",\n" +
                "        \"package_fee\": 0,\n" +
                "        \"total_fee\": 1\n" +
                "      }\n" +
                "    ],\n" +
                "    \"discount\": []\n" +
                "  },\n" +
                "  \"cmd\": \"order.create\",\n" +
                "  \"sign\": \"00C5DE3CC5BBD0C9081387A24E5279F5\",\n" +
                "  \"source\": \"65487\",\n" +
                "  \"ticket\": \"EEA42F5A-4098-F99B-6DF1-A58BE1C35A3E\",\n" +
                "  \"timestamp\": 1458281951,\n" +
                "  \"version\": \"2.0\"\n" +
                "}";
        System.out.println(GsonWrapper.fromJson(s, HashMap.class));
    }

}
