package com.ibb.bean;
//package com.igp.bean;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
///**
// * 2012-11-30 author:wanlingzhi
// */
//public abstract class TransmissionUtil
//{
//    /**
//     * 根据JSON参数生产requestbean
//     * 
//     * @param json
//     *            json对象
//     * @return 内部传输的requestbean对象
//     * */
//    public static RequestBean jsonToBean(final JSONObject json)
//    {
//        RequestBean request = new RequestBean();
//        if (json == null || json.isEmpty())
//        {
//            return request;
//        }
//
//        Set<String> keySet = json.keySet();
//        Object value;
//        for (String key : keySet)
//        {
//            value = json.get(key);
//            if (value != null)
//            {
//                setBean(request, key, value);
//            }
//        }
//        return request;
//    }
//
//    /**
//     * 根据string参数生产requestbean
//     * 
//     * @param str
//     *            string对象
//     * @param delimiter
//     *            字符串中属性的分隔符
//     * @return 内部传输的requestbean对象
//     * */
//    public static RequestBean stringToBean(final String str,
//            final String delimiter)
//    {
//        RequestBean request = new RequestBean();
//        if (str == null || str.trim().isEmpty())
//        {
//            return request;
//        }
//        String regex;
//        if (delimiter == null || delimiter.trim().isEmpty())
//        {
//            regex = "&";
//        }
//        else
//        {
//            regex = delimiter;
//        }
//        String[] strs = str.split(regex.trim());
//        String key, value, content;
//        int index;
//        for (int i = 0, size = strs.length; i < size; i++)
//        {
//            content = strs[i];
//            if (content != null && !content.trim().isEmpty())
//            {
//                index = content.indexOf("=");
//                if (index > 0)
//                {
//                    key = content.trim().substring(0, index);
//                    value = content.trim().substring(index + 1);
//                    setBean(request, key.trim(), value.trim());
//                }
//            }
//        }
//        return request;
//    }
//
//    private static void setBean(RequestBean bean, String key, Object value)
//    {
//        // if (key != null && key.equals("reqType"))
//        // {
//        // bean.setReqType(Integer.valueOf(value.toString()).intValue());
//        // }
//        // else
//        // {
//        bean.addParam(key, value);
//        // }
//    }
//
//    /**
//     * 根据返回的bean生产json字符串
//     * 
//     * @param response
//     *            ResponseBean对象
//     * @return json字符串对象
//     * */
//    @SuppressWarnings("unchecked")
//    public static String responseToJson(final ResponseBean response)
//    {
//        JSONObject json = new JSONObject();
//        json.put("result", response.getResult());
//        String key;
//        String temkey;
//        Object value;
//        List<Map<String, Object>> list;
//        Iterator<String> it;
//        Set<String> set;
//        set = response.getBodyKeySet();
//        // 添加鍵值對
//        if (set != null)
//        {
//            it = set.iterator();
//            while (it.hasNext())
//            {
//                key = it.next();
//                // json.put(key,
//                // string2Json(getValue(response.getResponseValue(key))));
//                // 如果是数据集则用jsonarray否则用json
//                value = response.getValue(key);
//                if (value != null && value instanceof List)
//                {
//                    list = (List<Map<String, Object>>) value;
//                    JSONArray jArray = new JSONArray();
//                    JSONObject jobj = null;
//                    for (int i = 0; i < list.size(); i++)
//                    {
//                        Map<String, Object> hm = list.get(i);
//                        if (hm != null && !hm.isEmpty())
//                        {
//                            jobj = new JSONObject();
//                            Iterator<String> temit = hm.keySet().iterator();
//                            while (temit.hasNext())
//                            {
//                                temkey = temit.next();
//                                jobj.put(temkey, getValue(hm.get(temkey)));
//                            }
//                            jArray.add(jobj);
//                        }
//                    }
//                    json.put(key, jArray);
//                }
//                else
//                {
//                    json.put(key, getValue(response.getValue(key)));
//                }
//                // json.put(key, getValue(response.getValue(key)));
//            }
//        }
//        set = null;
//        set = response.getDataSetKeySet();
//        // 添加返回的数据集
//        if (set != null)
//        {
//            it = set.iterator();
//            while (it.hasNext())
//            {
//                key = it.next();
//                value = response.getDataSetValue(key);
//                // 如果是数据集则用jsonarray否则用json
//                if (value != null && value instanceof List)
//                {
//                    list = (List<Map<String, Object>>) value;
//                    JSONArray jArray = new JSONArray();
//                    JSONObject jobj = null;
//                    for (int i = 0; i < list.size(); i++)
//                    {
//                        Map<String, Object> hm = list.get(i);
//                        if (hm != null && !hm.isEmpty())
//                        {
//                            jobj = new JSONObject();
//                            Iterator<String> temit = hm.keySet().iterator();
//                            while (temit.hasNext())
//                            {
//                                temkey = temit.next();
//                                jobj.put(temkey, getValue(hm.get(temkey)));
//                            }
//                            jArray.add(jobj);
//                        }
//                    }
//                    json.put(key, jArray);
//                }
//                else
//                {
//                    json.put(key, getValue(response.getValue(key)));
//                }
//            }
//        }
//        if (response.getPager() != null)
//        {
//            json.put("totalrows", response.getPager().getTotalRows());
//            json.put("currentpage", response.getPager().getCurrentPage());
//            json.put("pagesize", response.getPager().getPageSize());
//        }
//        return json.toString();
//    }
//
//    @SuppressWarnings("unchecked")
//    private static JSONArray getJsonArrayValue(Object value)
//    {
//        if (value != null && value instanceof List)
//        {
//            JSONArray jArray = new JSONArray();
//            JSONObject jobj;
//            String key;
//            Object obj;
//            HashMap<String, Object> hm;
//            List list = (List) value;
//            for (int i = 0; i < list.size(); i++)
//            {
//                obj = list.get(i);
//                if (obj instanceof Map)
//                {
//                    hm = (HashMap) obj;
//                    if (hm != null && !hm.isEmpty())
//                    {
//                        jobj = new JSONObject();
//                        Iterator<String> temit = hm.keySet().iterator();
//                        while (temit.hasNext())
//                        {
//                            key = temit.next();
//                            jobj.put(key, getValue(hm.get(key)));
//                        }
//                        jArray.add(jobj);
//                    }
//                }
//                else
//                {
//                    jArray.add(obj.toString());
//                }
//            }
//            return jArray;
//        }
//        return null;
//    }
//
//    @SuppressWarnings("unchecked")
//    private static JSONObject getJsonObjectValue(Object value)
//    {
//        if (value != null && value instanceof Map<?, ?>)
//        {
//            JSONObject json = new JSONObject();
//            String key;
//            HashMap<String, Object> hm = (HashMap<String, Object>) value;
//            if (hm != null && !hm.isEmpty())
//            {
//                Iterator<String> temit = hm.keySet().iterator();
//                while (temit.hasNext())
//                {
//                    key = temit.next();
//                    json.put(key, getValue(hm.get(key)));
//                }
//            }
//            return json;
//        }
//        return null;
//    }
//
//    @SuppressWarnings("unchecked")
//    private static Object getValue(Object value)
//    {
//        if (value != null)
//        {
//            if (value instanceof Boolean)
//            {
//                if ((Boolean) value)
//                {
//                    value = "1";
//                }
//                else
//                {
//                    value = "0";
//                }
//            }
//            else if (value instanceof Map<?, ?>)
//            {
//                return getJsonObjectValue(value);
//            }
//            else if (value instanceof List)
//            {
//                return getJsonArrayValue(value);
//            }
//        }
//        else
//        {
//            value = "";
//        }
//        return value;
//    }
//
//    public static String string2Json(String s)
//    {
//        return s;
//        /*
//         * StringBuffer sb = new StringBuffer(); for (int i = 0; i < s.length();
//         * i++) {
//         * 
//         * char c = s.charAt(i); switch (c) { case '\"': sb.append("\\\"");
//         * break; case '\\': sb.append("\\\\"); break; // case
//         * '/':sb.append("\\/");break; case '\b': sb.append("\\b"); break; case
//         * '\f': sb.append("\\f"); break; case '\n': sb.append("\\n"); break;
//         * case '\r': sb.append("\\r"); break; case '\t': sb.append("\\t");
//         * break; case '<': sb.append("&lt;"); break; case '>':
//         * sb.append("&gt;"); break; default: sb.append(c); } } return
//         * sb.toString();
//         */
//    }
//
//    /**
//     * 根据返回的bean生产拼接的url字符串
//     * 
//     * @param response
//     *            ResponseBean对象
//     * @return 拼接后的字符串
//     * */
//    public static String responseToString(final ResponseBean response)
//    {
//        StringBuilder sb = new StringBuilder();
//        String key;
//        Object value;
//        Iterator<String> it;
//        Set<String> set;
//        set = response.getBodyKeySet();
//        // 添加鍵值對
//        if (set != null)
//        {
//            it = set.iterator();
//            while (it.hasNext())
//            {
//                key = it.next();
//                if (sb.length() > 0)
//                {
//                    sb.append("&");
//                }
//                value = getValue(response.getValue(key));
//                if (!"".equals(key.trim()))
//                {
//                    sb.append(key + "=");
//                }
//                if (value != null && !"".equals(value.toString().trim()))
//                {
//                    sb.append(value.toString().trim());
//                }
//            }
//        }
//        return sb.toString();
//    }
//
//    // /**
//    // * 根据JSON参数生产requestbean
//    // *
//    // * @param json
//    // * json对象
//    // * @return 内部传输的requestbean对象
//    // * */
//    // public static RequestBean xmlToBean(final XMLParser parser,
//    // final String parent) {
//    // RequestBean request = new RequestBean();
//    // if (parser != null) {
//    // List<Element> elements = parser.getChildren(parent);
//    // Element element;
//    // for (int i = 0, size = elements.size(); i < size; i++) {
//    // element = elements.get(i);
//    // request.addParam(element.getName(), element.getText());
//    // }
//    // }
//    // return request;
//    // }
//}
