package com.library.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.library.entities.Produit;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ZxingQrcodeHelper {

    private static final String BARCODE_PATH = "C:\\Users\\Folio9470m\\Desktop\\QRCODE_SERVER\\BarCode\\";

    private static final Map hintMap = new HashMap<>();

   /* public static String readProductQrCode(String qrCode) throws Exception {

        BufferedImage bf = ImageIO.read(new FileInputStream(BARCODE_PATH));

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(bf)
        ));

        Result result = new MultiFormatReader().decode(binaryBitmap);

        return result.getText();

    }
*/
    public static String generateCodeCommand() {
        final String FORMAT = "yyyyMMddHHmmss";
        return (DateTimeFormat.forPattern(FORMAT).print(LocalDateTime.now()));
    }

    public static String generatedQrCodeWithProduct(Produit produit) throws Exception {

        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        hintMap.put(EncodeHintType.MARGIN, 1);

        hintMap.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        hintMap.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        hintMap.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));

        String qrCode = BARCODE_PATH + generateCodeCommand() + ".jpg";


        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(generateCodeCommand() + "\n" +
                        produit.getReference() + "\n" + produit.getDesignation() + "\n" +
                        produit.getPrixDetail() + "\n" + produit.getPrixVente() + "\n" +
                        produit.getQtestock() + "\n" + produit.getScategorie().getLibelle(),
                BarcodeFormat.QR_CODE, 350, 350, hintMap);

        Path path = FileSystems.getDefault().getPath(qrCode);

        MatrixToImageWriter.writeToPath(bitMatrix, "jpg", path);

        return qrCode;

    }
}
