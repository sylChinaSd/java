package com.syl.msp.shoppingcart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syl.msp.shoppingcart.entity.ShoppingCart;
import com.syl.msp.shoppingcart.service.ShoppingCartService;
import com.syl.msp.utils.common.CommonUtil;

@Controller
public class ShoppingCartController {

	@Autowired
	ShoppingCartService scService;

	/**
	 * 查询当前用户的所有购物车记录的数量
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/shoppingcart/query/all")
	@ResponseBody
	public Map<String, Object> queryTotalCount() throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		if (username != null) {
			Long totalCount = 0L;
			totalCount = scService.queryTotalCount(username);
			map.put("totalCount", totalCount);
			map.put("success", true);
		}
		return map;
	}

	@RequestMapping("/shoppingcart/query/page")
	@ResponseBody
	public Map<String, Object> queryPage(ShoppingCart sc) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		if (username != null) {
			Long totalCount = 0L;
			totalCount = scService.queryTotalCount(username);
			sc.setUsername(username);
			List<ShoppingCart> items = scService.queryPage(sc);
			map.put("totalCount", totalCount);
			map.put("items", items);
			map.put("success", true);
		}
		return map;
	}

	@RequestMapping("/shoppingcart/delete")
	@ResponseBody
	public Map<String, Object> deleteShoppingCart(@RequestBody List<Integer> ids)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		String username = CommonUtil.getCurrentUsername();
		if (username != null) {
			scService.deleteShoppingCart(ids, username);
			map.put("success", true);
		}
		return map;
	}

	/**
	 * 根据数据，添加一条新纪录至购物车
	 * 
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/shoppingcart/add")
	@ResponseBody
	public Map<String, Object> addToShoppingCart(ShoppingCart sc)
			throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		map.put("totalCount", 0);
		String username = CommonUtil.getCurrentUsername();
		if (username != null) {
			map.put("success", true);
			// 如果该用户不存在该种商品的记录
			sc.setUsername(username);
			sc.setId(null);
			sc.setcTime(null);
			if (scService.findByUsernameAndPid(sc) == null) {
				// 添加数据
				scService.save(sc);
			}

			Long totalCount = scService.queryTotalCount(username);
			map.put("totalCount", totalCount);
		}
		return map;
	}
}
