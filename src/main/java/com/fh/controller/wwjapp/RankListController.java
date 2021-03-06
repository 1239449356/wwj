package com.fh.controller.wwjapp;

import com.fh.entity.system.AppUser;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 抓娃娃排行榜
 */
@Controller
@RequestMapping(value = "/rank")
public class RankListController {

    @Resource(name = "appuserService")
    private AppuserManager appuserService;

    /**
     * 个人信息
     *
     * @param phone
     * @return
     */

    public JSONObject getAppUserInfo(String phone) {
        try {
            AppUser appUser = appuserService.getUserByPhone(phone);
            return JSONObject.fromObject(appUser);
        } catch (Exception e) {
            return null;
        }

    }

    @RequestMapping(value = "/rankList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject rankList() {
        try {
            List<AppUser> list = appuserService.rankList();
            Map<String, Object> map = new HashMap<>();
            map.put("appUser", list);
            return RespStatus.successs().element("data", map);

        } catch (Exception e) {
            return RespStatus.fail();
        }

    }



}
