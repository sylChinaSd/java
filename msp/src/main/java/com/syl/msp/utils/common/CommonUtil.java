package com.syl.msp.utils.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.syl.msp.login.entity.MspResource;
import com.syl.msp.login.entity.MspUserDetail;

public class CommonUtil {
	private static final Font CODE_FONT = new Font("Arial", Font.ITALIC, 32);
	private static final int CODE_WIDTH = 100;
	private static final int CODE_HEIGHT = 40;
	private static final BasicStroke CODE_STROKE = new BasicStroke(1.8f);

	private static final SimpleDateFormat orderSdf = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/**
	 * 生成response的map，用于返回json数据
	 * 
	 * @return
	 */
	public static Map<String, Object> getResponseMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		return map;
	}

	public static Map<String, Object> getSqlMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	/**
	 * 根据code生成图片
	 * 
	 * @param code
	 * @return
	 */
	public static BufferedImage generateCodeImage(String code) {
		BufferedImage img = new BufferedImage(CODE_WIDTH, CODE_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		// 字体样式设置
		graphics.setFont(CODE_FONT);
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, CODE_WIDTH, CODE_HEIGHT);
		graphics.setColor(Color.BLUE);
		graphics.drawString(code, 15, 30);
		// 结果图片
		BufferedImage img2 = new BufferedImage(CODE_WIDTH, CODE_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2 = img2.createGraphics();
		graphics2.setColor(Color.WHITE);
		graphics2.fillRect(0, 0, CODE_WIDTH, CODE_HEIGHT);

		// 图形变换 img->img2
		Random r = new Random();
		double tx = 0f, ty = 0f;
		double sx = r.nextFloat() / 10f + 0.9f, sy = r.nextFloat() / 10f + 0.9f;
		double shx = r.nextFloat() / 5, shy = r.nextFloat() / 5;
		for (int x = 0; x < CODE_WIDTH / 2; ++x) {
			for (int y = 0; y < CODE_HEIGHT; ++y) {
				int x2 = (int) (sx * x + shx * y + tx);
				int y2 = (int) (shy * x + sy * y + ty);
				if (x2 >= 0 && y2 >= 0 && x2 < CODE_WIDTH && y2 < CODE_HEIGHT) {
					img2.setRGB(x2, y2, img.getRGB(x, y));
				}
			}
		}

		shx = r.nextFloat() / 6;
		shy = r.nextFloat() / 6;
		for (int x = CODE_WIDTH / 2; x < CODE_WIDTH; ++x) {
			for (int y = 0; y < CODE_HEIGHT; ++y) {
				int x2 = (int) (sx * x + shx * y + tx);
				int y2 = (int) (shy * x + sy * y + ty);
				if (x2 >= 0 && y2 >= 0 && x2 < CODE_WIDTH && y2 < CODE_HEIGHT) {
					img2.setRGB(x2, y2, img.getRGB(x, y));
				}
			}
		}
		// 旋转 img2->img
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, CODE_WIDTH, CODE_HEIGHT);
		double a = r.nextInt(17) / 100.0 * (r.nextBoolean() ? -1 : 1);
		sx = Math.cos(a);
		sy = Math.cos(a);
		shx = -Math.sin(a);
		shy = Math.sin(a);
		for (int x = 0; x < CODE_WIDTH; ++x) {
			for (int y = 0; y < CODE_HEIGHT; ++y) {
				int x2 = (int) (sx * x + shx * y + tx);
				int y2 = (int) (shy * x + sy * y + ty);
				if (x2 >= 0 && y2 >= 0 && x2 < CODE_WIDTH && y2 < CODE_HEIGHT) {
					img.setRGB(x2, y2, img2.getRGB(x, y));
				}
			}
		}
		// 添加干扰
		graphics.setColor(Color.BLUE);
		graphics.setStroke(CODE_STROKE);
		for (int i = 0; i < 5; ++i) {
			graphics.drawLine(r.nextInt(CODE_WIDTH / 10),
					r.nextInt(CODE_HEIGHT), r.nextInt(CODE_WIDTH),
					r.nextInt(CODE_HEIGHT));
		}
		return img;
	}

	/**
	 * 设置异常返回信息
	 * 
	 * @param response
	 * @param e2
	 * @throws IOException
	 */
	public static void setExceptionResponse(HttpServletResponse response,
			Exception e2) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		System.out.println("MSP--setExceptionResponse");
		try {
			out = response.getWriter();
			if (e2 != null) {
				out.append("{\"success\":false,\"msg\":\"" + e2.getMessage()
						+ "\",\"code\":\"" + e2.getClass().getSimpleName() + "\"}");
			} else {
				out.append("{\"success\":true,\"msg\":\"操作成功\"}");
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 获取当前登录用户名
	 * 
	 * @return
	 */
	public static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			// username = principal.toString();
		}
		return username;
	}

	/**
	 * 获取当前动力用户的凭证
	 * 
	 * @return
	 */
	public static UserDetails getCurrentPrincipal() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		UserDetails ud = null;
		if (principal instanceof UserDetails) {
			ud = (UserDetails) principal;
		} else {
			// username = principal.toString();
		}
		return ud;
	}

	/**
	 * 获取当前登录用户的权限
	 * 
	 * @return
	 */
	public static List<MspResource> getCurrentResources() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof MspUserDetail) {
			return ((MspUserDetail) principal).getResources();
		} else {
			return null;
		}
	}

	/**
	 * 格式化订单号
	 * 
	 * @param orderNoSuffix
	 * @return
	 */
	public static String formatOrderNo(Integer orderNoSuffix) {
		String orderNo = orderSdf.format(new Date());
		orderNoSuffix = Math.abs(orderNoSuffix % 1000);
		orderNo = String.format("%s%03d", orderNo, orderNoSuffix);
		return orderNo;
	}

	/**
	 * 获取0点时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBreak(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取下一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
}
