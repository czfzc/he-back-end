package hour.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class COSUtil {

    private static final int DEFAULT_DURATION_SECONDS = 1800;

    public static JSONObject getCredential(TreeMap<String, Object> config) throws IOException {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        Parameters parameters = new Parameters();
        parameters.parse(config);

        String policy = parameters.policy;
        if (policy != null) {
            params.put("Policy", policy);
        } else {
            params.put("Policy", getPolicy(parameters).toString());
        }

        params.put("DurationSeconds", parameters.duration);

        params.put("Name", "cos-sts-java");
        params.put("Action", "GetFederationToken");
        params.put("Version", "2018-08-13");
        params.put("Region", parameters.region);

        String host = "sts.tencentcloudapi.com";
        String path = "/";

        String result = null;
        try {
            result = com.tencent.cloud.cos.util.Request.send(params, (String) parameters.secretId,
                    parameters.secretKey,
                    "POST", host, path);
            JSONObject jsonResult = JSON.parseObject(result);
            JSONObject data = jsonResult.getJSONObject("Response");
            if (data == null) {
                data = jsonResult;
            }
            long expiredTime = data.getLong("ExpiredTime");
            data.put("startTime", expiredTime - parameters.duration);
            return downCompat(data);
        } catch (Exception e) {
            throw new IOException("result = " + result, e);
        }
    }

    private String getPolicy(List<Scope> scopes) {
        if(scopes == null || scopes.size() == 0)return null;
        STSPolicy stsPolicy = new STSPolicy();
        stsPolicy.addScope(scopes);
        return stsPolicy.toString();
    }

    // v2接口的key首字母小写，v3改成大写，此处做了向下兼容
    private static JSONObject downCompat(JSONObject resultJson) {
        JSONObject dcJson = new JSONObject();

        for (String key : resultJson.keySet()) {
            Object value = resultJson.get(key);
            if (value instanceof JSONObject) {
                dcJson.put(headerToLowerCase(key), downCompat((JSONObject) value));
            } else {
                String newKey = "Token".equals(key) ? "sessionToken" : headerToLowerCase(key);
                dcJson.put(newKey, resultJson.get(key));
            }
        }

        return dcJson;
    }

    private static String headerToLowerCase(String source) {
        return Character.toLowerCase(source.charAt(0)) + source.substring(1);
    }

    private static JSONObject getPolicy(Parameters parameters) {
        if(parameters.bucket == null) {
            throw new NullPointerException("bucket == null");
        }
        if(parameters.allowPrefix == null) {
            throw new NullPointerException("allowPrefix == null");
        }
        String bucket = parameters.bucket;
        String region = parameters.region;
        String allowPrefix = parameters.allowPrefix;
        if(!allowPrefix.startsWith("/")) {
            allowPrefix = "/" + allowPrefix;
        }
        String[] allowActions = parameters.allowActions;

        JSONObject policy = new JSONObject();
        policy.put("version", "2.0");

        JSONObject statement = new JSONObject();
        statement.put("effect", "allow");
        JSONArray actions = new JSONArray();
        for (String action : allowActions) {
            actions.put(action);
        }
        statement.put("action", actions);

        int lastSplit = bucket.lastIndexOf("-");
        String appId = bucket.substring(lastSplit + 1);
        String resource = String.format("qcs::cos:%s:uid/%s:%s%s",
                region, appId, bucket, allowPrefix);

        statement.put("resource", resource);
        policy.put("statement", statement);
        return policy;
    }


    private static class Parameters{
        String secretId;
        String secretKey;
        int duration = DEFAULT_DURATION_SECONDS;
        String bucket;
        String region;
        String allowPrefix;
        String[] allowActions;
        String policy;

        public void parse(Map<String, Object> config) {
            if(config == null) throw new NullPointerException("config == null");
            for(Map.Entry<String, Object> entry : config.entrySet()) {
                String key = entry.getKey();
                if("SecretId".equalsIgnoreCase(key)) {
                    secretId = (String) entry.getValue();
                }else if("SecretKey".equalsIgnoreCase(key)) {
                    secretKey = (String) entry.getValue();
                }else if("durationSeconds".equalsIgnoreCase(key)) {
                    duration = (Integer) entry.getValue();
                }else if("bucket".equalsIgnoreCase(key)) {
                    bucket = (String) entry.getValue();
                }else if("region".equalsIgnoreCase(key)) {
                    region = (String) entry.getValue();
                }else if("allowPrefix".equalsIgnoreCase(key)) {
                    allowPrefix = (String) entry.getValue();
                }else if("policy".equalsIgnoreCase(key)) {
                    policy = (String) entry.getValue();
                }else if("allowActions".equalsIgnoreCase(key)) {
                    allowActions = (String[]) entry.getValue();
                }
            }
        }
    }

    private class STSPolicy {

        private List<Scope> scopes = new ArrayList<Scope>();

        public STSPolicy() {

        }

        public void addScope(List<Scope> scopes) {
            if(scopes != null) {
                for(Scope scope : scopes) {
                    this.scopes.add(scope);
                }
            }
        }

        public void addScope(Scope scope) {
            this.scopes.add(scope);
        }

        private JSONObject createElement(Scope scope) {
            JSONObject element = new JSONObject();

            JSONArray actions = new JSONArray();
            actions.put(scope.getAction());
            element.put("action", actions);

            element.put("effect", scope.getEffect());

            JSONArray resources = new JSONArray();
            resources.put(scope.getResource());
            element.put("resource", resources);

            return element;
        }

        @Override
        public String toString() {
            JSONObject policy = new JSONObject();
            policy.put("version", "2.0");
            JSONArray statement = new JSONArray();
            if(scopes.size() > 0) {
                for(Scope scope : scopes) {
                    statement.put(createElement(scope));
                }
                policy.put("statement", statement);
            }
            return policy.toString();
        }
    }

    private class Scope {

        private static final String ALLOW = "allow";
        private static final String DENY = "deny";

        private String action;
        private String bucket;
        private String region;
        private String sourcePrefix;
        private String effect = ALLOW;
        private String condition = null;
        /**
         *
         * @param action 操作名称，如 "name/cos:PutObject"
         * @param bucket 存储桶名称，格式：test-1250000000
         * @param region 园区名称，如 ap-guangzhou
         *  prefix 拼接 resource 字段所需的 key 前缀，客户端 SDK 默认传固定文件名如 "dir/1.txt"，支持 * 结尾如 "dir/*, 或 *"
         */
        public Scope(String action, String bucket, String region, String sourcePrefix) {
            this.action = action;
            this.bucket = bucket;
            this.region = region;
            this.sourcePrefix = sourcePrefix;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public void setResourcePrefix(String sourcePrefix) {
            this.sourcePrefix = sourcePrefix;
        }

        /**
         * isAllow is true that means allow, otherwise, deny
         * @param isAllow
         */
        public void isAllow(boolean isAllow) {
            if(isAllow) {
                this.effect = ALLOW;
            }else {
                this.effect = DENY;
            }
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getAction() {
            if(this.action == null) throw new NullPointerException("action == null");
            return this.action;
        }

        public String getEffect() {
            return this.effect;
        }

        /**
         * it format as follows:"qcs::cos:ap-beijing:uid/1250000000:examplebucket-1250000000/*"
         * @return the resource of policy.
         */
        public String getResource() {
            if(bucket == null) throw new NullPointerException("bucket == null");
            if(sourcePrefix == null) throw new NullPointerException("sourcePrefix == null");
            int index = bucket.lastIndexOf('-');
            if(index < 0) throw new IllegalStateException("bucket format is invalid: " + bucket);
            String appid = bucket.substring(index + 1).trim();
            if(!sourcePrefix.startsWith("/")) {
                sourcePrefix = '/' + sourcePrefix;
            }
            StringBuilder resource = new StringBuilder();
            resource.append("qcs::cos")
                    .append(':')
                    .append(region)
                    .append(':')
                    .append("uid/").append(appid)
                    .append(':')
                    .append(bucket)
                    .append(sourcePrefix);
            return resource.toString();
        }

        public String getCondition() {
            return this.condition;
        }

    }
}
