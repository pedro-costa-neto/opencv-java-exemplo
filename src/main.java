import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.awt.image.*;
import java.awt.FlowLayout;

import javax.swing.*;

import static org.opencv.imgcodecs.Imgcodecs.*;

/**
 * main
 */
public class main {

    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.VERSION);

        Mat colorfulImage = imread("src\\images\\OpenCVJava.jpg", CV_LOAD_IMAGE_COLOR);

        BufferedImage image = convertMatToImage(colorfulImage);
        mostraImagem(image);
    }

    public static BufferedImage convertMatToImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] bytes = new byte[bufferSize];
        mat.get(0, 0, bytes);
        BufferedImage imagem = new BufferedImage(mat.cols(), mat.rows(), type);
        byte[] targetPixels = ((DataBufferByte) imagem.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);
        return imagem;
    }
    
    public static void mostraImagem(BufferedImage imagem) {
        ImageIcon icon = new ImageIcon(imagem);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(imagem.getWidth() + 50, imagem.getHeight() + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}