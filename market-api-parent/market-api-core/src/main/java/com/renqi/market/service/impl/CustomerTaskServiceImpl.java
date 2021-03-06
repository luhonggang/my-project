package com.renqi.market.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.renqi.market.common.*;
import com.renqi.market.dao.*;
import com.renqi.market.entity.CustomerState;
import com.renqi.market.entity.CustomerTask;
import com.renqi.market.entity.CustomerTaskInfo;
import com.renqi.market.exception.CheckBaseException;
import com.renqi.market.service.CustomerTaskService;
import com.renqi.market.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author luhonggang @date 2018/9/15 @since 1.0
 */
@SuppressWarnings("all")
@Service
public class CustomerTaskServiceImpl implements CustomerTaskService {
    private final static Logger logger = LoggerFactory.getLogger(CustomerTaskServiceImpl.class);
    @Autowired
    CustomerStateMapper customerStateMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CustomerTaskMapper customerTaskMapper;
    @Autowired
    TaskWordMapper taskWordMapper;
    @Autowired
    TaskTemplateMapper templateMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 统计用户的任务发布的详情及个人信息
     * @param customerId  客户主键ID
     * @return            BaseResultMsg
     */
    @Override
    public BaseResultMsg countCustomerTask(String customerId) {
        BaseResultMsg msg = new BaseResultMsg(SystemCode.SYSTEM_SUCCESS.getCode(), SystemCode.SYSTEM_SUCCESS.getMsg());
        try {
            CustomerTaskInfo customer = customerMapper.queryCustomerMsg(customerId);
            int taskInCom = 0;
            int taskCom = 0;
            int taskContinue = 0;
            int taskException = 0;
            int totalTask = 1;
            if (null != customer) {
                customer.setTotalTask(customer.getCustomerState().getTotalTask()); /* 缓存总的任务数*/
                redisTemplate.opsForValue().set("TOTAL_TASK" + customerId, customer.getCustomerState().getTotalTask() + "", 1 * 60 * 60, TimeUnit.SECONDS);
                List<CustomerTask> taskList = customer.getTaskList();
                if (null != taskList && taskList.size() > 0) {
                    totalTask = taskList.size();
                    customer.setTotalTask(totalTask);
                    for (CustomerTask task : taskList) {
                        if (CustomerTask.TaskState.TASK_INCOMPLETED.getState().equals(task.getTaskState())){
                            taskInCom++;
                        }else if (CustomerTask.TaskState.TASK_COMPLETED.getState().equals(task.getTaskState())) {
                            taskCom++;
                        } else if (CustomerTask.TaskState.TASK_CONTINUE.getState().equals(task.getTaskState())) {
                            taskContinue++;
                        } else {
                            taskException++;
                        }
                     }
                }
                customer.setTaskIsComplete(taskCom);
                customer.setTaskIsDoing(taskContinue);
                customer.setTaskIsException(taskException);
                // 设置完成率的百分比
                int isRate = taskCom;
                int rate = isRate/totalTask;
                customer.setTaskRate(rate+"%");
            }
            msg.setData(customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), "++++++++++++ /task/queryCustomerMsg +++++++++++");
            return ResultMsgUtil.setCodeMsg(msg,SystemCode.SYSTEM_ERROR.getCode(),SystemCode.SYSTEM_ERROR.getMsg());
        }
        return msg;
    }

    /**
     * 用户发布任务保存业务 @param task 参数 @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult saveCustomerTask(CustomerTaskDto task) {
        BaseResult msg = new BaseResult(SystemCode.SYSTEM_SUCCESS.getCode(), SystemCode.SYSTEM_SUCCESS.getMsg());
        String taskType = task.getTaskType(); /* 获取到 总的任务数*/
        Integer totalTask = 0; /* 校验任务类型*/
        if (null == TaskType.values()[Integer.parseInt(taskType) - 1] || StringUtils.isEmpty(TaskType.values()[Integer.parseInt(taskType) - 1].getValue())) {
            throw new CheckBaseException(msg, SystemCode.TASK_TYPE_EXCEPTION.getCode(), SystemCode.TASK_TYPE_EXCEPTION.getMsg()); /* return ResultMsgUtil.setCodeMsg(msg,SystemCode.TASK_TYPE_EXCEPTION.getCode(),SystemCode.TASK_TYPE_EXCEPTION.getMsg());*/
        }
//        String[] mobileType = new String[]{"5", "6", "7"}; /* 手机端 发布任务设置宝贝ID*/
//        if (StringHandleUtils.containsStr(taskType, mobileType)) { /* 需要获取旺旺名称 httpClient 调用*/
//            String wwName = ""; /*手机端流量任务发布 就需要获取宝贝的ID 手机端宝贝链接 如 ： http://item.taobao.com/item.htm?id=535560353891*/
//            if (task.getGoodLinkUrl().indexOf("?") != -1) {
//                String goodId = task.getGoodLinkUrl().split("=")[1];
//                logger.info("+++++++++++ 手机端宝贝ID ： {} ",goodId);
//                task.setGoodId(Long.parseLong(goodId));
//            } else {
//                throw new CheckBaseException(msg, SystemCode.GOOD_LINKURL_ERROR.getCode(), SystemCode.GOOD_LINKURL_ERROR.getMsg());
//                //return ResultMsgUtil.setCodeMsg(msg,SystemCode.GOOD_LINKURL_ERROR.getCode(),SystemCode.GOOD_LINKURL_ERROR.getMsg());
//            }
//            if (StringUtils.isEmpty(task.getStoreName())) {
//                task.setStoreName(wwName);
//            }
//        }
        // 此时设置任务编号
        task.setOrderNo((System.currentTimeMillis() + 1) + "");
        task.setCreateTime(new Date());
        // 任务默认是 等待中的 状态
        task.setTaskState(CustomerTask.TaskState.TASK_CONTINUE.getState());
        String[] wordList = null;
        String[] vistorList = null;
        String[] numberList = null;
        // 依据关键词来新增 任务
        if ("no".equals(task.getWordList())) {
            // PC端 聚划算开团提醒
            task.setWordList(null);
            if("0".equals(task.getTemplateId())){
                task.setTemplateId(null);
            }
        } else {
            wordList = StringHandleUtils.getStrArray(task.getWordList());
            vistorList = StringHandleUtils.getStrArray(task.getTotalVisitor());
            numberList = StringHandleUtils.getStrArray(task.getTotalNumber());
            if (null == wordList || vistorList == null || numberList == null) {
                throw new CheckBaseException(msg, SystemCode.PARAM_VALID_EXCEPTION.getCode(), SystemCode.PARAM_VALID_EXCEPTION.getMsg());
                //return ResultMsgUtil.setCodeMsg(msg,SystemCode.PARAM_VALID_EXCEPTION.getCode(),SystemCode.PARAM_VALID_EXCEPTION.getMsg());
            }
            // 总的访客数和总的浏览量
            task.setTotalVisitor(StringHandleUtils.calculateTotalVal(task.getTotalVisitor()));
            task.setTotalNumber(StringHandleUtils.calculateTotalVal(task.getTotalNumber()));
            // 设置模板组合ID
            String tId = task.getTemplateId();
            if("0".equals(tId) || "no".equalsIgnoreCase(tId)){
                task.setTemplateId(null);
            }else {
                task.setTemplateId(tId.substring(0, tId.length()-1));
            }
        }

        //
        task.setUseDiscount(task.getUseDiscount());
        task.setUseMoney(task.getTotalMoney());

        String cacheTotalTask = (String) redisTemplate.opsForValue().get("TOTAL_TASK" + task.getTaskId());
        if (StringUtils.isNotEmpty(cacheTotalTask)) {
            totalTask += Integer.parseInt(cacheTotalTask);
        }
        totalTask += 1;

        Double totalMoney = 0.0d;
        // accountMoney 当前账户余额
        // 查询出 当前用户账户总的金额
        Optional<CustomerState> stateVo = Optional.ofNullable(customerStateMapper.queryTotalMoneyByCustomerId(task.getCustomerStateId()));
        if (stateVo.isPresent()) {
            totalMoney = stateVo.get().getTotalMoney();
        } else {
            throw new CheckBaseException(msg, SystemCode.SYSTEM_ERROR.getCode(), SystemCode.SYSTEM_ERROR.getMsg());
        //  return ResultMsgUtil.setCodeMsg(msg,SystemCode.SYSTEM_ERROR.getCode(),SystemCode.SYSTEM_ERROR.getMsg());
        }

        // 当前用户发布所用的金额
        Double currentMoney = task.getTotalMoney();
        if(currentMoney > totalMoney){
            msg.setCode(SystemCode.MONEY_NOT_ENOUGH.getCode());
            msg.setMsg(SystemCode.MONEY_NOT_ENOUGH.getMsg());
            throw new CheckBaseException(msg,SystemCode.MONEY_NOT_ENOUGH.getCode(),SystemCode.MONEY_NOT_ENOUGH.getMsg());
            //return ResultMsgUtil.setCodeMsg(msg,SystemCode.MONEY_NOT_ENOUGH.getCode(),SystemCode.MONEY_NOT_ENOUGH.getMsg());
        }
        logger.info("当前用户发布任务扣除的金额 ; "+ BigDecMathUtil.subtract(totalMoney,currentMoney));
        // 发布任务后扣除用户充值的总金额
        customerStateMapper.updateByCustomerStateId(totalTask+"",task.getCustomerStateId()+"",(BigDecMathUtil.subtract(totalMoney,currentMoney))+"");

        // 新增任务记录
        customerTaskMapper.insertCustomerTask(task);
        int taskId = task.getTaskId();
        logger.info("++++++++++++ /task/saveCustomerTask +++++++++++ : taskId :{}", taskId);

        // 获取的每一个关键词对应的 访客数 和 浏览量 并组装成对象保存
        /**
         *  正常的数据格式     关键词： 淘宝,天猫
         *                  访客量： 100,200
         *                  浏览量： 200，200
         */
        if (null != wordList && wordList.length > 0) {
            for (int i = 0; i < wordList.length; i++) {
                TaskWordDto dto = new TaskWordDto();
                dto.setTaskId(taskId);
                dto.setWordName(wordList[i]);
                dto.setTaskVisitor(Integer.parseInt(StringUtils.isNotEmpty(vistorList[i]) ? vistorList[i] : "0"));
                dto.setShowNumber(Integer.parseInt(StringUtils.isNotEmpty(numberList[i]) ? numberList[i] : "0"));
                dto.setCreateTime(new Date());
                taskWordMapper.insertTaskWord(dto);
            }
        }
            /**
             * TODO 此时用户发布任务成功 后需要 发送验证码通知管理人员
             */
            String typeName = "";
            switch (taskType){
                case "1":typeName = "PC端匿名访问";break;
                case "2":typeName = "PC端实名访问";break;
                case "3":typeName = "PC直通车流量";break;
                case "4":typeName = "PC聚划算开团提醒";break;
                case "5":typeName = "手机端匿名访问";break;
                case "6":typeName = "手机端实名访问";break;
                case "7":typeName = "手机端天猫App流量";break;
                case "9":typeName = "其他京东App流量";break;
                case "10":typeName = "猜你喜欢流量";break;
                case "11":typeName = "手机直通车流量";break;
                case "12":typeName = "PC端收藏加购";break;
                case "13":typeName = "手机端收藏加购";break;
                default:typeName = "PC端匿名访问";break;
            }
            logger.info("++++++++++ phone ++++++++++ : {}",task.getPhoneNum());
            Map<String,String> mapPram = new HashMap<>();
//            mapPram.put("phone","15812346666");
            mapPram.put("ownPhone",redisTemplate.opsForValue().get("phone"));
            mapPram.put("templateId","SMS_150575604");
            mapPram.put("phone",task.getPhoneNum());
        try {
            MobileCodeUtil.sendSmsTask(mapPram);
        } catch (ClientException e) {
            logger.info("++++++++++ 发送通知短信失败 +++++++++ customerId : {}",task.getCustomerId());
            e.printStackTrace();
            throw new CheckBaseException(msg, SystemCode.SYSTEM_ERROR.getCode(), SystemCode.TASK_SEND_CODE_ERROR.getMsg());
        }
        return msg;
    }

    /**
     * 查询任务的详情记录
     *
     * @param dto
     * @return
     */
    @Override
    public BaseResultMsg queryCustomerTask(CustomerParamDto dto) {
        BaseResultMsg msg = new BaseResultMsg(SystemCode.SYSTEM_SUCCESS.getCode(), SystemCode.SYSTEM_SUCCESS.getMsg());
        try {
            List<CustomerTaskVo> taskList = customerTaskMapper.queryCustomerTask(dto);
            if (!StringHandleUtils.isEmpty(taskList)) {
                for (CustomerTaskVo vo : taskList) {
                    if (vo != null) {
                        String arrayStr = StringHandleUtils.concatArrayStr(vo.getTemplateId());
                        List<TaskTemplateVo> list = templateMapper.queryTemplate(vo.getTaskType(), arrayStr);
                        if (!StringHandleUtils.isEmpty(list)) {
//                           for (int i = 0; i < list.size(); i++) {
                            TaskTemplateVo tVo = list.get(0);
                            // 查询出的第一个数组下标的数据 为总的浏览时长
                            vo.setBrowerTotalTime(tVo.getTaskBeginBrowse() + "-" + tVo.getTaskEndBrowse());
//                           }
                        }
                    }
                }
            }
            msg.setData(taskList);
        } catch (Exception e) {
            logger.error("++++++++++++ /task/queryCustomerTask +++++++++++", e.getMessage());
            msg.setMsg(SystemCode.SYSTEM_ERROR.getCode());
            msg.setMsg(SystemCode.SYSTEM_ERROR.getMsg());
        }
        return msg;
    }


}
