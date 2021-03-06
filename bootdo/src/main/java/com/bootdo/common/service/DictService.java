package com.bootdo.common.service;

import com.bootdo.common.domain.CustomerTask;
import com.bootdo.common.domain.CustomerTaskDto;
import com.bootdo.common.domain.DictDO;
import com.bootdo.system.domain.UserDO;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-29 18:28:07
 */
public interface DictService {

	CustomerTask get(Long id);
	
	List<CustomerTask> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DictDO dict);
	
	int update(CustomerTaskDto dict);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<DictDO> listType();
	
	String getName(String type,String value);

	/**
	 * 获取爱好列表
	 * @return
     * @param userDO
	 */
	List<CustomerTask> getHobbyList(UserDO userDO);

	/**
	 * 获取性别列表
 	 * @return
	 */
	List<CustomerTask> getSexList();

	/**
	 * 根据type获取数据
	 * @param map
	 * @return
	 */
	List<CustomerTask> listByType(String type);

}
