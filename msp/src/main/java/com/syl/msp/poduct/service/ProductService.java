package com.syl.msp.poduct.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.syl.msp.poduct.dao.ProductDao;
import com.syl.msp.poduct.entity.Product;

@Service
public class ProductService {

	@Autowired
	ProductDao productDao;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Product findById(int i) throws Exception {
		return productDao.findById(i);
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveById(Product pro, String basePath) throws Exception {
		productDao.saveById(pro);
		// 并且把图片从临时路径复制到正确路径
		copyProductImage(pro, basePath);
		productDao.updateById(pro);

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Product> queryPage(Product p) throws Exception {
		if (p.getPname() != null) {
			p.setPname("%" + p.getPname() + "%");
		}
		return productDao.queryPage(p);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Long getTotalCount(Product p) throws Exception {
		if (p.getPname() != null) {
			p.setPname("%" + p.getPname() + "%");
		}
		return productDao.getTotalCount(p);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateById(Product tmp, String basePath) throws Exception {
		copyProductImage(tmp, basePath);
		productDao.updateById(tmp);
	}

	/**
	 * 将临时文件移动到真是路径并更改bean的值
	 * 
	 * @param tmp
	 * @param basePath
	 * @throws Exception
	 */
	private void copyProductImage(Product tmp, String basePath)
			throws Exception {
		File f = new File(basePath + "public-resources/images/products/"
				+ tmp.getId());
		if (!f.exists()) {
			f.mkdirs();
		}
		String dest = basePath + "public-resources/images/products/"
				+ tmp.getId() + "/mPic.jpg";
		String source = basePath
				+ tmp.getmPic().replaceFirst("resources", "public-resources");
		f = new File(source);
		if (f.exists() && f.isFile()) {
			Files.move(Paths.get(source), Paths.get(dest),
					StandardCopyOption.REPLACE_EXISTING);
			System.out.println(source + "," + dest);
			tmp.setmPic("resources/images/products/" + tmp.getId()
					+ "/mPic.jpg");
		}
	}
}
