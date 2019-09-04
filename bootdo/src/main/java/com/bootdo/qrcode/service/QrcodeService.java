package com.bootdo.qrcode.service;

import com.bootdo.qrcode.domain.QrcodeDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author adorablexx
 * @email 1992lcg@163.com
 * @date 2019-07-08 16:03:27
 */
public interface QrcodeService {
	
	QrcodeDO get(Integer id);
	
	List<QrcodeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(QrcodeDO qrcode);


	int update(QrcodeDO qrcode);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	/**
	 * @param file 传来的文件列表
	 * @result path 路径映射后的图片URL 如果有多个文件 就用分号隔开多个url
	 * */
	String uploadfile(MultipartFile file);


}
