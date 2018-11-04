package com.bootdo.common.service.impl;

import com.bootdo.common.domain.CustomerTask;
import com.bootdo.common.domain.CustomerTaskDto;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.bootdo.common.dao.DictDao;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;


@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictDao dictDao;

    @Override
    public CustomerTask get(Long id) {
        return dictDao.get(id);
    }

    @Override
    public List<CustomerTask> list(Map<String, Object> map) {
        return dictDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return dictDao.count(map);
    }

    @Override
    public int save(DictDO dict) {
        return dictDao.save(dict);
    }

    @Override
    public int update(CustomerTaskDto dict) {
        return dictDao.update(dict);
    }

    @Override
    public int remove(Long id) {
        return dictDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return dictDao.batchRemove(ids);
    }

    @Override

    public List<DictDO> listType() {
        return dictDao.listType();
    }

    @Override
    public String getName(String type, String value) {
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("type", type);
        param.put("value", value);
        String rString = dictDao.list(param).get(0).getStoreName();
        return rString;
    }

    @Override
    public List<CustomerTask> getHobbyList(UserDO userDO) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "hobby");
        List<CustomerTask> hobbyList = dictDao.list(param);

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
//                for (DictDO hobby : hobbyList) {
//                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
//                        continue;
//                    }
//                    hobby.setRemarks("true");
//                    break;
//                }
            }
        }

        return hobbyList;
    }

    @Override
    public List<CustomerTask> getSexList() {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "sex");
        return dictDao.list(param);
    }

    @Override
    public List<CustomerTask> listByType(String type) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", type);
        return dictDao.list(param);
    }

}
