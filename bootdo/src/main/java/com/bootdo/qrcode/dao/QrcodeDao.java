package com.bootdo.qrcode.dao;

import com.bootdo.qrcode.domain.QrcodeDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author adorablexx
 * @email 1992lcg@163.com
 * @date 2019-07-08 16:03:27
 */
@Mapper
public interface QrcodeDao {

	QrcodeDO get(Integer id);
	
	List<QrcodeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);

	int save(QrcodeDO qrcode);

	int update(QrcodeDO qrcode);
	
	int remove(Integer Id);
	
	int batchRemove(Integer[] ids);
}
