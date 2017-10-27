package com.syl.msp.poduct.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.syl.msp.poduct.entity.Product;
import com.syl.msp.poduct.service.ProductService;
import com.syl.msp.utils.common.CommonUtil;

@Controller
public class ProductController {

	@Autowired
	ProductService productService;

	/**
	 * 商品图片文件上传
	 * 
	 * @param name
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/product/upload")
	@ResponseBody
	public Map<String, Object> handleFormUpload(Product p,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Map<String, Object> map = CommonUtil.getResponseMap();
		map.put("success", true);
		map.put("msg", "");
		if (!file.isEmpty()) {
			BufferedOutputStream bos = null;
			BufferedInputStream bis = null;
			// 剪裁前后的状态
			/*
			 * String released = p.getPdesc(); boolean croppered =
			 * released.equalsIgnoreCase("croppered");
			 */
			try {
				// 获取实际路径
				String path = request.getSession().getServletContext()
						.getRealPath("/");
				path += "public-resources/images/products/tmp/";
				// 创建文件夹
				File directory = new File(path);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				// 复制并保存文件
				byte[] b = new byte[1024];
				int off = 0, len = 0;
				String suffix = file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf("."));
				File output = new File(path + System.currentTimeMillis()
						+ suffix);
				bos = new BufferedOutputStream(new FileOutputStream(output));
				bis = new BufferedInputStream(file.getInputStream());
				while ((len = bis.read(b, off, 1024)) != -1) {
					bos.write(b, off, len);
				}
				// 设置更新后图片路径
				map.put("path",
						"resources/images/products/tmp/" + output.getName());
			} catch (IOException e) {
				e.printStackTrace();
				map.put("msg", "图片文件不合规");
			} finally {
				// 关闭流
				try {
					if (bos != null) {
						bos.close();
					}
					if (bis != null) {
						bis.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	@RequestMapping(value = "/product/query/page")
	@ResponseBody
	public Map<String, Object> pageQuery(Product p) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		// 查询当页的数据及总条数
		List<Product> items = productService.queryPage(p);
		Long totalCount = productService.getTotalCount(p);

		// 设置返回数据
		map.put("totalCount", totalCount);
		map.put("items", items);
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/product/query")
	@ResponseBody
	public Map<String, Object> productQuery(Product p) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		// 查询当页的数据
		Product item = null;
		if (p.getId() != null) {
			item = productService.findById(p.getId());
		}
		// 设置返回数据
		map.put("item", item);
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/product/update")
	@ResponseBody
	public Map<String, Object> productUpdate(Product p,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		if (p.getId() != null) {
			// 更新数据
			Product item = productService.findById(p.getId());
			// 设置更新数据
			item.setPname(p.getPname());
			item.setRemark(p.getRemark());
			item.setPdesc(p.getPdesc());
			item.setRuleParam(p.getRuleParam());
			item.setPackList(p.getPackList());
			item.setPrice(p.getPrice());
			item.setDiscount(p.getDiscount());
			item.setStock(p.getStock());
			item.setPcondi(p.getPcondi());
			item.setmPic(p.getmPic());
			// 更新
			String path = request.getSession().getServletContext()
					.getRealPath("/");
			productService.updateById(item, path);
		}
		// 设置返回数据
		map.put("success", true);
		return map;
	}

	@RequestMapping(value = "/product/add")
	@ResponseBody
	public Map<String, Object> productAdd(Product p, HttpServletRequest request) throws Exception {
		Map<String, Object> map = CommonUtil.getResponseMap();
		// 保存
		String path = request.getSession().getServletContext().getRealPath("/");
		productService.saveById(p, path);
		map.put("success", true);
		return map;
	}
}
