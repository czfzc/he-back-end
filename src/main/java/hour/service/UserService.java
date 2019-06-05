package hour.service;

import hour.model.User;

public interface UserService {
    /**
     * 微信初始登录 无法获取手机号
     * @param code 微信传过来的登录码
     * @return
     */
    public String wxLogin(String code);

    /**
     * 微信二次登陆 传过来加密了的手机号码
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return
     * 返回json
     * {
     *    status:true,
     *    mysession:7ad4xa87a4d88a
     * }
     */
    public String registWithPhoneNum(String encryptedData,String iv,String code);

    boolean gzhRegister(String gzh_open_id);

    boolean gzhCheckRegisted(String gzh_open_id);

    /**
     * 用session换取用户名
     * @param mysession
     * @return
     */
    String getUserId(String mysession);

    User getUser(String mysession);

    String setUserInfo(String data, String iv,String mysession);
}
