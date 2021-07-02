package com.library.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.library.entities.Produit;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.file.Paths;

public class ZxingBarcodeHelper {

    private static final String BARCODE_PATH = "C:\\Users\\Folio9470m\\Desktop\\QRCODE_SERVER\\BarCode\\";


    public static String generatedBarCodeWithProduct(Produit produit) throws Exception {
        String barCode = BARCODE_PATH + generateCodeCommand() + ".jpg";

        Code128Writer writer = new Code128Writer();

        BitMatrix bitMatrix = writer.encode(generateCodeCommand() + "\n" +
                produit.getReference() + "\n" + produit.getDesignation() + "\n" +
                produit.getPrixDetail() + "\n" + produit.getQtestock(), BarcodeFormat.CODE_128, 500, 300);

        MatrixToImageWriter.writeToPath(bitMatrix, "jpg", Paths.get(barCode));

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
        final String FORMAT = "yyyyMMddHHmmss";
        return (DateTimeFormat.forPattern(FORMAT).print(LocalDateTime.now()));
    }
}
