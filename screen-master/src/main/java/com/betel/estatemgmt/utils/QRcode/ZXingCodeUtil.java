package com.betel.estatemgmt.utils.QRcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 二维码工具类
 * </p>
 *
 * @className: ZXingCodeUtil <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/30 17:24 <br/>
 * @version: 1.0
 */
public class ZXingCodeUtil {
    /**
     * 打印日志工具类
     */
    private static final Logger LOG = LoggerFactory.getLogger(ZXingCodeUtil.class);
    /**
     * logo占二维码图片百分比
     */
    private static final float LOGO_PERCENTAGE = 0.2F;


    /**
     * 生成二维码工具方法
     *
     * @param photoProDto 素材
     * @return 生成后的图片
     */
    public static BufferedImage buildQRCode(PhotoProDto photoProDto) {
        BufferedImage image = buildQRCode(photoProDto.getContent(), photoProDto.getWidth(), photoProDto.getHeight());
        if (photoProDto.isHasLogo()) {
            addLogo(image, photoProDto.getLogoFile());
        }
        return image;
    }

    /**
     * 构建二维码图片
     *
     * @param content 二维码内容(文字)
     * @param width   二维码宽度
     * @param height  二维码高度
     * @return 二维码图片
     */
    private static BufferedImage buildQRCode(String content, int width, int height) {
        BufferedImage image = null;
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height,
                    getDecodeHintType());
            bitMatrix =deleteWhite(bitMatrix);
            int w = bitMatrix.getWidth();
            int h = bitMatrix.getHeight();
            System.out.println("==============================w="+w+"=========h="+h);
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            // 开始利用二维码数据创建Bitmap图片，分别设为黑白两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
        } catch (WriterException e) {
            try {
                LOG.error("------------二维码生成失败:",e);
                throw new Exception("二维码生成失败!", e);
            } catch (Exception e1) {
                LOG.error("------------二维码生成失败:",e1);
            }
        }
        return image;
    }

    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    /**
     * 二维码图片添加Logo
     *
     * @param image    二维码图片
     * @param logoFile logo文件
     */
    private static void addLogo(BufferedImage image, File logoFile) {
        try {
            Graphics2D g = image.createGraphics();
            BufferedImage logo = ImageIO.read(logoFile);

            // 设置logo的大小,为二维码图片的20%
            int widthLogo = (int) (logo.getWidth(null) > image.getWidth() * LOGO_PERCENTAGE
                    ? (image.getWidth() * LOGO_PERCENTAGE) : logo.getWidth(null));
            int heightLogo = (int) (logo.getHeight(null) > image.getHeight() * LOGO_PERCENTAGE
                    ? (image.getHeight() * LOGO_PERCENTAGE) : logo.getWidth(null));

            // logo放在中心位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.dispose();
            image.flush();
        } catch (IOException e) {
            try {
                throw new Exception("添加logo失败!", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 设置二维码的格式参数
     *
     * @return 二维码的格式参数
     */
    private static Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        return hints;
    }

    /**
     * 图片对象转化为流对象
     *
     * @param bufferedImage
     * @return
     */
    public static InputStream transforInputStream(BufferedImage bufferedImage) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            ImageIO.write(bufferedImage, "jpg", os);
            is = new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            LOG.error("----------转化为流失败-------", e);
        }
        return is;
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgFilePath
     * @return
     */
    public static String GetImageStr(String imgFilePath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }
}