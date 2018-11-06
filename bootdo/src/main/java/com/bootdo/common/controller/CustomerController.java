package com.bootdo.common.controller;

import com.bootdo.common.config.Constant;
import com.bootdo.common.dao.CustomerMapper;
import com.bootdo.common.domain.CustomerInfoDto;
import com.bootdo.common.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 用户管理 修改用户的信息
 */
@Controller
@RequestMapping("/common/userlist")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerMapper customerMapper;

	@GetMapping()
	@RequiresPermissions("common:userlist:userlist")
	String userList() {
		return "common/userlist/userlist";
	}

	/**
	 * 查询出所有的用户的信息
	 * @param params
	 * @return
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:userlist:userlist")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<CustomerInfoDto> customerList = customerMapper.queryListCustomerMsg(query);
		int total = customerMapper.count(query);
		PageUtils pageUtils = new PageUtils(customerList, total);
		return pageUtils;
	}

	@GetMapping("/edit/{customerId}")
	@RequiresPermissions("common:userlist:edit")
	String editO(@PathVariable("customerId") Long customerId, Model model) {
		CustomerInfoDto dict = customerMapper.queryCustomerById(customerId);
		model.addAttribute("dict", dict);
		return "common/userlist/edit";
	}

	/**
	 * 查询出所有的手机号码
	 * @return
	 */
	@GetMapping("/phoneList")
	@ResponseBody
	public List<CustomerInfoDto> phoneList() {
		return customerMapper.queryListPhone();
	};

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:userlist:edit")
	public R update(CustomerInfoDto customerInfoDto) {
		// 测试账户不允许修改
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		// 修改用户的会员等级
		customerMapper.updateLevelById(customerInfoDto);

		// 查询出当前用户 绑定的 状态id
		CustomerInfoDto infoDto = customerMapper.selectStateId(customerInfoDto.getCustomerId());
		// 修改用户的折扣 总的账户余额 是否充值
		CustomerInfoDto taskVo = customerMapper.selectCurrentMoney(customerInfoDto);
		Double totalMoney = customerInfoDto.getTotalMoney();
		Double cuTotalMoney = taskVo.getTotalMoney() == null ? 0.0D : taskVo.getTotalMoney();
		if(taskVo != null){
			if(totalMoney < cuTotalMoney){
				return R.error();
			}
			customerMapper.updateTotalMoney(customerInfoDto);
		}
		return R.ok();
	}

}
