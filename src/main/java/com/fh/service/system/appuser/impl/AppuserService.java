package com.fh.service.system.appuser.impl;

import java.util.List;

import javax.annotation.Resource;

import com.fh.entity.system.AppUser;
import com.fh.entity.system.Doll;
import com.fh.util.DateUtil;
import com.fh.util.wwjUtil.AppUserNameUtil;
import com.fh.util.wwjUtil.MyUUID;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.PageData;


/**类名称：AppuserService
 * @author FH Q313596790
 * 修改时间：2015年11月6日
 */
@Service("appuserService")
public class AppuserService implements AppuserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列出某角色下的所有会员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllAppuserByRorlid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppuserMapper.listAllAppuserByRorlid", pd);
	}
	
	/**会员列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPdPageUser(Page page)throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.userlistPage", page);
	}
	
	/**通过用户名获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByUsername", pd);
	}
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByEmail(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByEmail", pd);
	}
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByNumber(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByNumber", pd);
	}
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("AppuserMapper.saveU", pd);
	}
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("AppuserMapper.deleteU", pd);
	}
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("AppuserMapper.editU", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUiId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByUiId", pd);
	}
	
	/**全部会员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUser(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.listAllUser", pd);
	}
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS)throws Exception{
		dao.delete("AppuserMapper.deleteAllU", USER_IDS);
	}
	
	/**获取总数
	 * @param value
	 * @throws Exception
	 */
	public PageData getAppUserCount(String value)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.getAppUserCount", value);
	}

	/**
	 * 注册用户
	 * @param phone
	 * @return
	 * @throws Exception
	 */

	public int reg(String phone) throws Exception {
		return (int)dao.save("AppuserMapper.reg",new AppUser(MyUUID.createSessionId(),phone,null,phone,phone));
	}

	/**
	 * 通过手机号码查询用户信息
	 * @param phone
	 * @return
	 * @throws Exception
	 */

	public AppUser getUserByPhone(String phone) throws Exception {
		return (AppUser) dao.findForObject("AppuserMapper.getUserByPhone",phone);
	}

	/**
	 * 通过ID查询用户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */

	public AppUser getUserByID(String id) throws Exception {
		return (AppUser) dao.findForObject("AppuserMapper.getUserByID",id);
	}

	/**
	 * 更改用户头像
	 * @param appUser
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateAppUserImage(AppUser appUser) throws Exception {
		return (int)dao.update("AppuserMapper.updateAppUserImage",appUser);
	}

	/**
	 * 更改用户昵称
	 * @param appUser
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateAppUserName(AppUser appUser) throws Exception {
		return (int)dao.update("AppuserMapper.updateAppUserName",appUser);
	}

	/**
	 * 根据用户名获取用户信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Override
	public AppUser getAppUserByUserName(String name) throws Exception {
		return (AppUser) dao.findForObject("AppuserMapper.getAppUserByNickName",name);
	}

	/**
	 * 根据昵称获取用户信息
	 * @param nickName
	 * @return
	 * @throws Exception
	 */
	@Override
	public AppUser getAppUserByNickName(String nickName) throws Exception {
		return (AppUser)dao.findForObject("AppuserMapper.getAppUserByNickName",nickName);
	}

	/**
	 * 获取用户更新的的账户余额
	 * @param appUser
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateAppUserBalanceByPhone(AppUser appUser) throws Exception {
		return (int) dao.update("AppuserMapper.updateAppUserBalanceByPhone",appUser);
	}

	/**
	 * rank榜单
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<AppUser> rankList() throws Exception {
		return (List<AppUser>) dao.findForList("AppuserMapper.rankList",null);
	}

	/**
	 * 更新用户的抓娃娃数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateAppUserDollTotalByName(AppUser appUser) throws Exception {
		return (int) dao.update("AppuserMapper.updateAppUserDollTotalByName",appUser);
	}

	@Override
	public int updateAppUserBalanceById(AppUser appUser) throws Exception {
		return (int)dao.update("AppuserMapper.updateAppUserBalanceById",appUser);
	}

	@Override
	public int updateAppUsernickName(AppUser appUser) throws Exception {
		return (int) dao.update("AppuserMapper.updateNickName",appUser);
	}

	@Override
	public int updateAppUserCnee(AppUser appUser) throws Exception {
		return (int) dao.update("AppuserMapper.updateAppUserCnee",appUser);
	}
}

