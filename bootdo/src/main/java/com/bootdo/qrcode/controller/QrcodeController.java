package com.bootdo.qrcode.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.qrcode.domain.QrcodeDO;
import com.bootdo.qrcode.service.QrcodeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author adorablexx
 * @email 1992lcg@163.com
 * @date 2019-07-08 16:03:27
 */

@CrossOrigin
@Controller
@RequestMapping("/qrcode/qrcode")
public class QrcodeController {
	@Autowired
	private QrcodeService qrcodeService;
	private QrcodeDO qrcodeDO;
	
	@GetMapping()
	@RequiresPermissions("qrcode:qrcode:qrcode")
	String Qrcode(){
	    return "qrcode/qrcode/qrcode";
	}
	
	@ResponseBody
	@GetMapping("/list")
	//@RequiresPermissions("qrcode:qrcode:qrcode")
	//用户使用过程中必经流程 不能设权限
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<QrcodeDO> qrcodeList = qrcodeService.list(query);
		int total = qrcodeService.count(query);
		PageUtils pageUtils = new PageUtils(qrcodeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
			// 用户无需登录就可以提交信息 但是编辑需要后台登录
	//@RequiresPermissions("qrcode:qrcode:add")
	String add(){
	    return "qrcode/qrcode/add";
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/redirect")
		// test html
	String redirect(){
		return "qrcode/qrcode/redirect";
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/generate")
		// test html
	String generate(){
		return "qrcode/qrcode/generate";
	}


	@GetMapping("/edit/{id}")
	@RequiresPermissions("qrcode:qrcode:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		QrcodeDO qrcode = qrcodeService.get(id);
		model.addAttribute("qrcode", qrcode);
	    return "qrcode/qrcode/edit";
	}
	
	/**
	 * 保存
	 */
	//@ResponseBody
	@PostMapping("/save")
	//@RequiresPermissions("qrcode:qrcode:add")
	public String save( QrcodeDO qrcode,@RequestParam(name="uploads_alipay") MultipartFile file_ali,@RequestParam(name="uploads_wechat") MultipartFile file_wechat,@RequestParam(name="uploads_qq") MultipartFile file_qq,Model model) {
		String result_ali = qrcodeService.uploadfile(file_ali);
		qrcode.setAlipayurl(result_ali);
        String result_wechat = qrcodeService.uploadfile(file_wechat);
        qrcode.setWechaturl(result_wechat);
		String result_qq = qrcodeService.uploadfile(file_qq);
		qrcode.setQqurl(result_qq);
		String md5 = DigestUtils.md5DigestAsHex((qrcode.getAlipayurl() + qrcode.getWechaturl() + qrcode.getQqurl()).getBytes());
		qrcode.setResulturl(md5);
		model.addAttribute("platform","md5");
		model.addAttribute("md5",md5);
		if(qrcodeService.save(qrcode)>0){
			return "/qrcode/qrcode/redirect";
		}
		return "error";
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("qrcode:qrcode:edit")
	public R update( QrcodeDO qrcode){
		qrcodeService.update(qrcode);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("qrcode:qrcode:remove")
	public R remove( Integer id){
		if(qrcodeService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("qrcode:qrcode:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		qrcodeService.batchRemove(ids);
		return R.ok();
	}


//	@PostMapping("/generate")
//	@ResponseBody
//	public R generate(QrcodeDO bean){
//		bean.setResulturl("http://127.0.0.1:8889/qrcode/qrcode/getResult?resulturl="+DigestUtils.md5DigestAsHex((bean.getAlipayurl() + bean.getWechaturl()).getBytes()));
//		qrcodeService.save(bean);
//		return R.ok().put("result",bean.getResulturl());
//	}


	@GetMapping("/getResult")
	//@ResponseBody
	public String getResult(QrcodeDO bean,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
		String query="";
		String result = "";
		String resulturl = bean.getResulturl();
		Map<String, Object> mp = new HashMap<String, Object>();
		mp = bean2map(bean);
		List<QrcodeDO> queryResult = new ArrayList<>();
		queryResult = qrcodeService.list(mp);
		String agent= request.getHeader("user-agent");
		if(agent.contains("Ali")){
			query = queryResult.get(0).getAlipayurl();
			response.sendRedirect(query);
			model.addAttribute("platform","Ali");
		} else if(agent.contains("Micro")) {
			query =queryResult.get(0).getWechaturl();
			model.addAttribute("platform","Micro");
        //TODO: https://blog.csdn.net/qq_41790332/article/details/82937155 独立完成作业：在spring boot 项目中使用thymeleaf模板，将后台数据传递给前台界面。
		} else if(agent.contains("QQ")){
			query =queryResult.get(0).getQqurl();
			model.addAttribute("platform","qq");
		}else {
			model.addAttribute("platform","unsupported");
			query = "maybe PC unsupported";
			//return query;
		}
		model.addAttribute("query",query);
		return "/qrcode/qrcode/redirect";

		//return R.ok().put("queryResult",query);
	}



	public static Map<String,Object> bean2map(Object bean) throws Exception{
		Map<String,Object> map = new HashMap<>();
		//获取JavaBean的描述器
		BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
		//获取属性描述器
		PropertyDescriptor[] pds = b.getPropertyDescriptors();
		//对属性迭代
		for (PropertyDescriptor pd : pds) {
			//属性名称
			String propertyName = pd.getName();
			//属性值,用getter方法获取
			Method m = pd.getReadMethod();
			Object properValue = m.invoke(bean);//用对象执行getter方法获得属性值

			//把属性名-属性值 存到Map中
			map.put(propertyName, properValue);
		}
		return map;
	}

}
