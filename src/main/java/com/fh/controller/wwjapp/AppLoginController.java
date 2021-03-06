package com.fh.controller.wwjapp;


import com.fh.entity.system.AppUser;
import com.fh.entity.system.Doll;
import com.fh.service.system.appuser.AppuserManager;

import com.fh.service.system.doll.DollManager;
import com.fh.util.MD5;
import com.fh.util.wwjUtil.*;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 短信接口类
 *
 * @author wjy
 */
@Controller
@RequestMapping("/sms")
public class AppLoginController {

    @Resource(name = "appuserService")
    private AppuserManager appuserService;

    @Resource(name = "dollService")
    private DollManager dollService;

    /**
     * 个人信息
     *
     * @param id
     * @return
     */

    public JSONObject getAppUserInfo(String id) {
        try {
            AppUser appUser = appuserService.getUserByID(id);
            return JSONObject.fromObject(appUser);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 娃娃机信息
     */
    public JSONObject getDollInfo(String id) {
        try {
            Doll doll = dollService.getDollByID(id);
            return JSONObject.fromObject(doll);
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 登陆短信验证码
     *
     * @param aPhone
     * @return
     */
    @RequestMapping(value = "/getRegSMSCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getRegSMSCode(@RequestParam("phone") String aPhone) {
        try {
            String phone = new String(Base64Util.decryptBASE64(aPhone));
            if (phone == null || phone.trim().length() <= 0) {
                return RespStatus.fail("手机号码不能为空！");
            } else {
                if (!phone.matches("^1(3|4|5|7|8)\\d{9}$")) {
                    return RespStatus.fail("手机号码格式错误！");
                }
            }
            Random r = new Random();
            String code = "";
            for (int i = 0; i <= 5; i++) {
                code += String.valueOf(r.nextInt(10));
            }
            SMSUtil.veriCode1(phone, code);
            RedisUtil.getRu().setex("SMSCode:" + phone, code, 300);
            return RespStatus.successs();
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.exception();
        }
    }

    /**
     * 登陆/注册
     *
     * @param aPhone
     * @param aCode
     * @return
     */
    @RequestMapping(value = "/getSMSCodeLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getSMSCodeLogin(@RequestParam("phone") String aPhone, @RequestParam("code") String aCode) {

        try {
            String phone = new String(Base64Util.decryptBASE64(aPhone));
            String code = new String(Base64Util.decryptBASE64(aCode));
            if (phone == null || phone.trim().length() <= 0) {
                return RespStatus.fail("手机号不能为空！");
            } else if (!phone.matches("^1(3|4|5|7|8)\\d{9}$")) {
                return RespStatus.fail("手机号码格式错误！");
            } else if (code == null || code.trim().length() <= 0) {
                return RespStatus.fail("验证码不能为空！");
            }
            if (!RedisUtil.getRu().exists("SMSCode:" + phone)) {
                return RespStatus.fail("此手机号尚未请求验证码！");
            }
            String exitCode = RedisUtil.getRu().get("SMSCode:" + phone);
            if (!exitCode.equals(code)) {
                return RespStatus.fail("验证码无效！");
            }
            RedisUtil.getRu().del("SMSCode:" + phone);
            AppUser appUser = appuserService.getUserByPhone(phone);
            if (appUser != null) {
               /* boolean b = RedisUtil.getRu().exists("sessionId:appUser:" + phone);
                if (b){
                    return RespStatus.fail("该用户已经登录");
                }*/
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                String token = MD5.md5("");


                String accessToken = "";
                if (RedisUtil.getRu().exists("accessToken")) {
                    accessToken = RedisUtil.getRu().get("accessToken");
                } else {
                    accessToken = CameraUtils.getAccessToken();
                }

                String sessionID = MyUUID.createSessionId();
                List<Doll> doll = dollService.getAllDoll();
                RedisUtil.getRu().set("sessionId:appUser:" + phone, sessionID);
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("accessToken", accessToken);
                map.put("sessionID", sessionID);
                map.put("appUser", getAppUserInfo(appUser.getUSER_ID()));
                map.put("dollList", doll);
                return RespStatus.successs().element("data", map);
            } else {
                int a1 = appuserService.reg(phone);
                if (a1 != 1) {
                    return RespStatus.fail("注册失败");

                }
                AppUser appUserNew = appuserService.getUserByPhone(phone);
                String accessToken = "";
                if (RedisUtil.getRu().exists("accessToken")) {
                    accessToken = RedisUtil.getRu().get("accessToken");
                } else {
                    accessToken = CameraUtils.getAccessToken();
                }
                String sessionID = MyUUID.createSessionId();
                List<Doll> doll = dollService.getAllDoll();
                RedisUtil.getRu().set("sessionId:appUser:" + phone, sessionID);
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("accessToken", accessToken);
                map.put("sessionID", sessionID);
                map.put("appUser", getAppUserInfo(appUserNew.getUSER_ID()));
                map.put("dollList", doll);
                return RespStatus.successs().element("data", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.exception();
        }

    }

    /**
     * 手机号码直登获取娃娃机信息
     *
     * @param aPhone
     * @return
     */
    @RequestMapping(value = "/getDoll" ,method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getDoll(@RequestParam("phone") String aPhone) {
        try {
            String phone = new String(Base64Util.decryptBASE64(aPhone));
            AppUser appUser = appuserService.getUserByPhone(phone);
            if (appUser != null) {
                String accessToken = "";
                if (RedisUtil.getRu().exists("accessToken")) {
                    accessToken = RedisUtil.getRu().get("accessToken");
                } else {
                    accessToken = CameraUtils.getAccessToken();
                }
                String sessionID = MyUUID.createSessionId();
                RedisUtil.getRu().set("sessionId:appUser:" + phone, sessionID);
                List<Doll> doll = dollService.getAllDoll();
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("accessToken", accessToken);
                map.put("sessionID", sessionID);
                map.put("appUser", getAppUserInfo(appUser.getUSER_ID()));
                map.put("dollList", doll);
                return RespStatus.successs().element("data", map);
            } else {
                return RespStatus.fail("此用户尚未注册！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.exception();

        }

    }

    /**
     * 访客模式，只允许查看房间
     * @return
     */

    @RequestMapping(value = "/autoLogin",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject autoLogin (){
        try{
            String accessToken = "";
            if (RedisUtil.getRu().exists("accessToken")) {
                accessToken = RedisUtil.getRu().get("accessToken");
            } else {
                accessToken = CameraUtils.getAccessToken();
            }
            String sessionID = MyUUID.createSessionId();
            RedisUtil.getRu().setex("sessionId:autoVistor:" + sessionID, sessionID,3600);
            List<Doll> doll = dollService.getAllDoll();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("accessToken", accessToken);
            map.put("sessionID", sessionID);
            map.put("dollList", doll);
            return RespStatus.successs().element("data", map);
        }catch (Exception e){
            e.printStackTrace();
            return RespStatus.fail();
        }
    }


    /**
     * 采用账号密码的方式登陆（待测试未使用）
     *
     * @param phone
     * @param pw
     * @return
     */
    @RequestMapping(value = "/userPassLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject userPassLogin(@Param("phone") String phone, @Param("password") String pw) {
        try {
            AppUser appUser = appuserService.getUserByPhone(phone);
            if (appUser != null) {
                String password = appUser.getPASSWORD();
                if (password.equals(pw) == true) {
                    String uuid = appUser.getUSER_ID();
                    String token = TokenUtil.getToken(uuid);
                    RedisUtil.getRu().setex("token" + phone, token, 86400);
                    return RespStatus.successs().element("token", token).element("appUser", appUser);
                } else {
                    return RespStatus.fail("账号密码错误");
                }
            } else {
                return RespStatus.fail("无此用户");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.exception();

        }
    }


}



