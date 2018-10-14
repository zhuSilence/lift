package com.silence.mini.program.life.controller;

import com.alibaba.fastjson.JSONArray;
import com.silence.mini.program.life.model.PicModel;
import com.silence.mini.program.life.model.ResultMsg;
import com.silence.mini.program.life.util.*;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>Function：</b><br>
 * <b>Author：</b>@author Silence<br>
 * <b>Date：</b>2018-10-07 16:00<br>
 * <b>Desc：</b>无<br>
 */
@Controller
@RequestMapping(value = "file")
public class FileController {

    @Value("${upload.root}")
    private String uploadRoot;
    @Value("${upload.url}")
    private String uploadUrl;

    @RequestMapping(value = "/getPic", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public ResultMsg dataOne(@RequestBody PicModel model) throws Exception{
        Map<String, Object> map = new HashMap<>(16);
        String msg = "生成图片失败。参数为：" + JsonUtils.obj2Json(model);
        boolean flag = false;
        if (StringUtils.isNotBlank(model.getCode()) && StringUtils.isNotBlank(model.getAvatarUrl())) {
            List<String> filePathList = new ArrayList<>();
            String oriAvaPath = uploadRoot + "avatar_" + model.getCode() + ".png";
            String oriAvaPath2 = uploadRoot + "avatar_" + model.getCode() + "2.png";
            ThumbnailsImageUtils.saveToFile(model.getAvatarUrl(), oriAvaPath);
            File oriFile = new File(oriAvaPath);
            if (oriFile.exists()) {
                oriAvaPath2 = uploadRoot + "avatar_" + model.getCode() + "2.png";
                //将头像按160px比例缩小
                ThumbnailsImageUtils.resize(oriAvaPath, oriAvaPath2, 220, 220);

                //将头像处理成圆形
                ThumbnailsImageUtils.makeRoundedCorner(new File(oriAvaPath2), new File(oriAvaPath), 220);
            }else {
                oriAvaPath = uploadRoot + "template/default.png";
            }

            String background = uploadRoot + "template/background.png";
            String background2 = uploadRoot + model.getCode() + "background2.png";
            try {
                ThumbnailsImageUtils.watermark(background, 1242, 2208, Positions.TOP_CENTER, oriAvaPath, 1f, 1f, background2);

            } catch (IOException e) {
                e.printStackTrace();
            }

            String RED_PIC = uploadRoot + "template/red.png";
            String WHITE_TOTAL = uploadRoot + "template/white.png";
            int redFileLength = model.getSize() <= 0 ? 1 : model.getSize();
            List<File> redFileList = new ArrayList<>();
            for (int i = 1; i <= redFileLength; i++) {
                redFileList.add(new File(RED_PIC));
            }
            try {
                String targetFile = uploadRoot + model.getCode() + ".png";
                String targetFile2 = uploadRoot + model.getCode() + "_finish.png";
                BufferedImage bufferedImage = ThumbnailsImageUtils.watermarkList(null, 0, redFileList.toArray(new File[redFileLength]));
                ImageIO.write(bufferedImage, "png", new File(targetFile));
                ThumbnailsImageUtils.watermark(WHITE_TOTAL, 1200, 1200, Positions.TOP_LEFT, targetFile, 0.8f, 1f, targetFile2);
                ThumbnailsImageUtils.watermark(background2, 1242, 2208, Positions.CENTER, targetFile2, 0.8f, 1f, background2);
                String content = genDesc(model.getSize());
                System.out.println(content);
                ThumbnailsImageUtils.addWaterMark(background2, background2, content);
                filePathList.add(targetFile);
                filePathList.add(targetFile2);
                filePathList.add(oriAvaPath);
                filePathList.add(oriAvaPath2);
                //删除所有临时文件
                deleteFiles(filePathList);
                map.put("pictureUrl", uploadUrl + model.getCode() + "background2.png");
                msg = "生成图片成功。";
                flag = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResultMsg(flag, map, msg);
    }

    private void deleteFiles(List<String> filePathList) {
        filePathList.forEach(filePath -> {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        });
    }

    private String genDesc(int age) {
        File file = new File(uploadRoot + "template/desc.json");
        int month = DateUtils.getCurDate().getMonth();
        int year = (age - month) / 12;
        try {
            String content= FileUtils.readFileToString(file,"UTF-8");
            Map<String, String> map = JsonUtils.json2Map(content);
            Object object;
            if (year <= 18) {
                object = map.get("age00");
            }else if (year <= 28) {
                object = map.get("age90");
            }else if (year <= 38) {
                object = map.get("age80");
            }else if (year <= 48) {
                object = map.get("age70");
            }else if (year <= 58) {
                object = map.get("age60");
            }else if (year <= 68) {
                object = map.get("age50");
            }else if (year <= 78) {
                object = map.get("age40");
            }else {
                object = map.get("age30");
            }
            JSONArray jsonArray = (JSONArray)object;
            return jsonArray.get(RandomUtils.generateLtInt(jsonArray.size())).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "优秀";
    }
}
