package com.bootdo.common.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.config.Constant;
import com.bootdo.common.dao.CustomerMapper;
import com.bootdo.common.domain.CustomerTask;
import com.bootdo.common.domain.CustomerTaskDto;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.domain.TaskWord;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.system.common.TaskState;
import com.bootdo.system.common.TaskType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询出所有 的任务
 */
@Controller
@RequestMapping("/common/dict")
@Slf4j
public class DictController extends BaseController {
	@Autowired
	private DictService dictService;

	@Autowired
	private CustomerMapper customerMapper;
	private static final 	String HOUR = "点";
	private static final	String MIN = "分钟";

	@GetMapping()
	@RequiresPermissions("common:dict:dict")
	String dict() {
		return "common/dict/dict";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:dict:dict")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<CustomerTask> taskList = dictService.list(query);
		int total = dictService.count(query);
		PageUtils pageUtils = new PageUtils(collectCount(taskList), total);
		return pageUtils;
	}

	/**
	 * 组装需要的数据展现
	 * @param taskList List 集合
	 * @return  List<CustomerTask>
	 */
	private List<CustomerTask> collectCount(List<CustomerTask> taskList){
		for (CustomerTask task:taskList) {
			task.setTimeScope(task.getTaskBeginHour()+HOUR+"到"+task.getTaskEndHour()+HOUR);
			task.setMiunteScope(task.getTaskBeginMiunte()+MIN+"到"+task.getTaskEndMiunte()+MIN);
			// 关键词
			String wordList = customerMapper.selectListWord(task.getTaskId());
			task.setWordList(wordList);
			// 浏览时长 todo
			String tId = task.getTemplateId();
			if(StringUtils.isNotEmpty(tId) && tId.contains(",")){
//				String lastElement = tId.substring(tId.lastIndexOf(",")+1);
//
//				task.setBowerTime(StringUtils.getBowerTime(lastElement));
				// 获取的是 搜索的范围
				task.setTemplateName(StringUtils.getTemplateName(tId.split(",")));
			}else{
				// 获取的是 搜索的范围
				task.setTemplateName("");
			}
			// 获取的是 任务类型名称
			log.info("+++++++++++++ taskType ++++++++++++++ ",task.getTaskType() +" task.getTotalNumber "+task.getTotalNumber());
			task.setTaskTypeName(StringUtils.getTaskType(task.getTaskType()));
		}
		return taskList;
	}
	@GetMapping("/add")
	@RequiresPermissions("common:dict:add")
	String add() {
		return "common/dict/add";
	}

	@GetMapping("/edit/{taskId}")
	@RequiresPermissions("common:dict:edit")
	String edit(@PathVariable("taskId") Long id, Model model) {
		CustomerTask dict = dictService.get(id);
		model.addAttribute("dict", dict);
		return "common/dict/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:dict:add")
	public R save(DictDO dict) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (dictService.save(dict) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:dict:edit")
	public R update(CustomerTaskDto taskDto) {
		// 测试账户不允许修改
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		dictService.update(taskDto);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("common:dict:remove")
	public R remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (dictService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:dict:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		dictService.batchRemove(ids);
		return R.ok();
	}

	/**
	 * 查询出任务的类型
	 * @return
	 */
	@GetMapping("/type")
	@ResponseBody
	public /*List<DictDO>*/ JSONObject listType() {
		JSONArray array = new JSONArray();
		JSONObject typeVo = new JSONObject();
		List<JSONObject> typeList = new ArrayList<>();
		List<JSONObject> stateList = new ArrayList<>();
		for (int i = 0; i < TaskType.values().length; i++) {
			JSONObject obj = new JSONObject();
			obj.put("type",TaskType.values()[i].getValue());
			obj.put("description", TaskType.values()[i].getTypeName());
			typeList.add(obj);

		}
		for (int i = 0; i < 4; i++) {
			JSONObject obj = new JSONObject();
			obj.put("state",TaskState.values()[i].getState());
			obj.put("description", TaskState.values()[i].getStateName());
			stateList.add(obj);

		}
		typeVo.put("type",typeList);
		typeVo.put("state",stateList);
		return typeVo;
//		dictService.listType();
	};

	// 类别已经指定增加
	@GetMapping("/add/{type}/{description}")
	@RequiresPermissions("common:dict:add")
	String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
		model.addAttribute("type", type);
		model.addAttribute("description", description);
		return "common/dict/add";
	}

	@ResponseBody
	@GetMapping("/list/{taskType}")
	public List<CustomerTask> listByType(@PathVariable("taskType") String type) {
		// 查询列表数据
		Map<String, Object> map = new HashMap<>(16);
		map.put("taskType", type);
		List<CustomerTask> dictList = dictService.list(map);
		return collectCount(dictList);
	}
}
