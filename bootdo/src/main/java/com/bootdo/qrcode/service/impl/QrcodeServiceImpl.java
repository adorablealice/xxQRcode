package com.bootdo.qrcode.service.impl;

import com.bootdo.common.utils.FileUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.qrcode.dao.QrcodeDao;
import com.bootdo.qrcode.domain.QrcodeDO;
import com.bootdo.qrcode.service.QrcodeService;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;


@Service
public class QrcodeServiceImpl implements QrcodeService {
    @Autowired
    private QrcodeDao qrcodeDao;

    @Override
    public QrcodeDO get(Integer id) {
        return qrcodeDao.get(id);
    }

    @Override
    public List<QrcodeDO> list(Map<String, Object> map) {
        return qrcodeDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return qrcodeDao.count(map);
    }

    @Override
    public int save(QrcodeDO qrcode) {
        return qrcodeDao.save(qrcode);
    }


    @Override
    public int update(QrcodeDO qrcode) {
        return qrcodeDao.update(qrcode);
    }

    @Override
    public int remove(Integer id) {
        return qrcodeDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return qrcodeDao.batchRemove(ids);
    }

    @Override
    public String uploadfile(MultipartFile file) {
        String fileName = null;
        String path = null;
        String result = "";
        if (!file.isEmpty()) {
            fileName = file.getOriginalFilename();
            path = System.getProperties().getProperty("user.dir") + "\\bootdo\\src\\main\\resources\\static\\img\\qrcode\\";
            String savepath = path + fileName;
            File is_exist = new File(savepath);
            try {
                if (!is_exist.exists())
                    FileUtil.uploadFile(file.getBytes(), path, fileName);
                result = ReadQRCode(savepath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * QRCode读取
     */
//    public String ReadQRCode(String savepath) throws IOException {
//        String s = "";
//        File file = new File(savepath);
//        BufferedImage image = ImageIO.read(file);
//        QRCodeDecoder codeDecoder = new QRCodeDecoder();
//        try {
//            s = new String(codeDecoder.decode(new MYQRCodeImage(image)), "gb2312");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return s;
//    }
    public String ReadQRCode(String savepath) throws IOException, com.google.zxing.NotFoundException {
        File file = new File(savepath);
        MultiFormatReader formatReader = new MultiFormatReader();
        //读取指定的二维码文件
        BufferedImage bufferedImage =ImageIO.read(file);
        BinaryBitmap binaryBitmap= new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        //定义二维码参数
        Map  hints= new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);
        //输出相关的二维码信息\
        bufferedImage.flush();
        return result.toString();
    }


}
