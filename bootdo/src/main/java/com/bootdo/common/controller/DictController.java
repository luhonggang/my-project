package com.bootdo.common.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.config.Constant;
import com.bootdo.common.domain.CustomerTask;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.system.common.TaskState;
import com.bootdo.system.common.TaskType;
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
 * 字典表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-29 18:28:07
 */

@Controller
@RequestMapping("/common/dict")
public class DictController extends BaseController {
	@Autowired
	private DictService dictService;

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
		List<CustomerTask> dictList = dictService.list(query);
		int total = dictService.count(query);
		PageUtils pageUtils = new PageUtils(dictList, total);
		return pageUtils;
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
	public R update(DictDO dict) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		dictService.update(dict);
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
		return dictList;
	}
}
