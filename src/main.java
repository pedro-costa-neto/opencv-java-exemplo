import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.*;
import java.awt.FlowLayout;

import javax.swing.*;

import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.imgproc.Imgproc.*;

/**
 * main
 */
public class main {

    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //exemplo00();
        exemplo01();
    }

    /**
     * Exemplo 00
     */
    public static void exemplo00() {
        System.out.println(Core.VERSION);

        Mat colorfulImage = imread("src\\images\\OpenCVJava.jpg", CV_LOAD_IMAGE_COLOR);

        BufferedImage image = convertMatToImage(colorfulImage);
        mostraImagem(image);

        // Convertendo a imagem para escala de cinza
        Mat greyImage = new Mat();
        Imgproc.cvtColor(colorfulImage, greyImage, COLOR_BGR2GRAY);
        mostraImagem(convertMatToImage(greyImage));
    }
    
    /**
     * Exemplo 01
     */
    public static void exemplo01() {
        Mat colorfulImage = imread("src\\images\\pessoas\\beatles.jpg");
        Mat greyImage = new Mat();
        Imgproc.cvtColor(colorfulImage, greyImage, COLOR_BGR2GRAY);
        
        CascadeClassifier cc = new CascadeClassifier("src\\cascades\\haarcascade_frontalface_default.xml");
        MatOfRect facesDetectadas = new MatOfRect();
        cc.detectMultiScale(greyImage, facesDetectadas);

        System.out.println("Quantidade de faces detectadas: " + facesDetectadas.toArray().length);

        for(Rect rect : facesDetectadas.toArray()) {
            System.out.println("Localizacao da face na imagem: " + rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
            Imgproc.rectangle(
                colorfulImage, // Imagem que sera adicionada os retangulos
                new Point(rect.x, rect.y), // Pontos iniciais do retangulo
                new Point(rect.x + rect.width, rect.y + rect.height), // Pontos finais do retangulo
                new Scalar(0, 0, 255), // Cor da borda do retangulo em BGR (0, 0, 255) Vermelha
                2 // Tamanho da borda em pexels
            );
        }

        mostraImagem(convertMatToImage(colorfulImage));
    }


    

    /*/ +---------------+
        | Metodos uteis |
        +---------------+
    /*/ 

    /**
     * Função para converter objeto Mat em imagem
     * @param mat
     * @return
     */
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
    
    /**
     * Metodo para abrir um janela com a imagem (JFrame).
     * @param imagem
     */
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