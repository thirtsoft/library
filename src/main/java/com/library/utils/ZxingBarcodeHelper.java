package com.library.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.library.entities.Produit;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ZxingBarcodeHelper {

    private static final String BARCODE_PATH = "C:\\Users\\Folio9470m\\Desktop\\QRCODE_SERVER\\BarCode\\";

    public static String generatedBarCodeWithProduct(Produit produit) throws Exception {

       /* hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        hintMap.put(EncodeHintType.MARGIN, 1);

        hintMap.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        hintMap.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        hintMap.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));*/

        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        String barCode = BARCODE_PATH + generateCodeCommand() + ".png";


        Code128Writer writer = new Code128Writer();

        BitMatrix bitMatrix = writer.encode(generateCodeCommand() + "\n" +
                produit.getReference() + "\n" + produit.getDesignation() + "\n" +
                produit.getPrixDetail() + "\n" + produit.getQtestock(), BarcodeFormat.CODE_128, 250, 200, hintMap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToPath(bitMatrix, "png", Paths.get(barCode));

        return "Barcode created...";
    }

    public static String readProductBarCode(String barcode) throws Exception {

        BufferedImage bf = ImageIO.read(new FileInputStream(BARCODE_PATH));

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(bf)
        ));

        Result result = new MultiFormatReader().decode(binaryBitmap);

        return result.getText();

    }

    public static String generateCodeCommand() {
        final String FORMAT = "yyyyMddHHmmss";
        return (DateTimeFormat.forPattern(FORMAT).print(LocalDateTime.now()));
    }

}
