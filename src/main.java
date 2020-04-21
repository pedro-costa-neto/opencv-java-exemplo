import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.awt.image.*;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.imgproc.Imgproc.*;

/**
 * main
 */
public class main {

    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //exemplo00();
        //exemplo01();
        //exemplo02();
        //exemplo03();
        //exemplo04();
        //exemplo05();
        exemplo06();
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
     * Deteccao de faces
     */
    public static void exemplo01() {
        Mat colorfulImage = imread("src\\images\\pessoas\\pessoas3.jpg");
        Mat greyImage = new Mat();
        Imgproc.cvtColor(colorfulImage, greyImage, COLOR_BGR2GRAY);
        
        CascadeClassifier cc = new CascadeClassifier("src\\cascades\\haarcascade_frontalface_default.xml");
        MatOfRect facesDetectadas = new MatOfRect();
        cc.detectMultiScale(
            greyImage, 
            facesDetectadas,
            //(Valor variavel de acordo com o tamanho da imagem)
            1.19, // Scale factor 

            /*
            minNeighbors (Quantos vizinos cada retangulo candidato deve ter para mante-lo).
            Valores altos = Menos detecções, porem, apresenta maior qualidade.
            */
            3, 

            /*
            Semelhante a uma heuristica
            Rejeita alguma regioes de imagens ue contem muitas ou poucas
            bordas (que nao contem o objeto procurado)
            Utilizado em cascades "antigos"

            HaarCascade
            Traw_Cascade (Novo, tem melhor desempenho)

            Recomentado deixar o valor zerado (funciona apenas nos HaarCascade antigos).
            */
            0, // flag

            /*
            Tamanho minimo e maximo das faces que devem ser detectadas.
            Valores definido por pixels.
            */
            new Size(30, 30), // minSize
            new Size(500, 500) // mazSize
        );

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

    /**
     * Exemplo 02
     * Deteccao de faces e olhos
     */
    public static void exemplo02() {
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

        CascadeClassifier ccOlho = new CascadeClassifier("src\\cascades\\haarcascade_eye.xml");
        MatOfRect olhosDetectadas = new MatOfRect();
        ccOlho.detectMultiScale(greyImage, olhosDetectadas);

        System.out.println("Quantidade de olhos detectadas: " + olhosDetectadas.toArray().length);
        for(Rect rect : olhosDetectadas.toArray()) {
            System.out.println("Localizacao do olho na imagem: " + rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
            Imgproc.rectangle(
                colorfulImage, // Imagem que sera adicionada os retangulos
                new Point(rect.x, rect.y), // Pontos iniciais do retangulo
                new Point(rect.x + rect.width, rect.y + rect.height), // Pontos finais do retangulo
                new Scalar(0, 255, 00), // Cor da borda do retangulo em BGR (0, 0, 255) Vermelha
                2 // Tamanho da borda em pexels
            );
        }

        mostraImagem(convertMatToImage(colorfulImage));
    }

    /**
     * Exemplo 03
     * Acessando a Webcan com o OpenCV
     */
    public static void exemplo03() {

        // Configurando janela
        JFrame janela = new JFrame();
        janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janela.setSize(600, 500);
        janela.setVisible(true);

        // Configurando painel
        JPanel jPanel1 = new JPanel();
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGap(0, 653, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
    
        GroupLayout layout = new GroupLayout(janela.getContentPane());
        janela.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    
        janela.pack();

        Mat video = new Mat();
        VideoCapture captura = new VideoCapture(0);

        if(captura.isOpened()) {
            while (true) {
                captura.read(video);

                if(!video.empty()) {
                    janela.setSize(video.width() + 50, video.height() + 70);
                    BufferedImage image = convertMatToImage(video);
                    Graphics g = jPanel1.getGraphics();
                    g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), null);
                }
            }
        }
    }

    
    /**
     * Exemplo 04
     * Acessando a Webcan com o OpenCV e detectando as faces
     */
    public static void exemplo04() {

        // Configurando janela
        JFrame janela = new JFrame();
        janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janela.setSize(600, 500);
        janela.setVisible(true);

        // Configurando painel
        JPanel jPanel1 = new JPanel();
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGap(0, 653, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
    
        GroupLayout layout = new GroupLayout(janela.getContentPane());
        janela.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    
        janela.pack();

        Mat video = new Mat();
        VideoCapture captura = new VideoCapture(0);

        if(captura.isOpened()) {
            while (true) {
                captura.read(video);

                if(!video.empty()) {
                    janela.setSize(video.width() + 50, video.height() + 70);

                    Mat colorfulImage = video;
                    Mat greyImage = new Mat();
                    Imgproc.cvtColor(colorfulImage, greyImage, COLOR_BGR2GRAY);

                    MatOfRect facesDetectadas = new MatOfRect();
                    CascadeClassifier cc = new CascadeClassifier("src\\cascades\\haarcascade_frontalface_default.xml");
                    cc.detectMultiScale(
                        greyImage, 
                        facesDetectadas,
                        1.1,
                        1,
                        0,
                        new Size(100, 100),
                        new Size(500, 500)
                    );
                
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

                    CascadeClassifier ccOlho = new CascadeClassifier("src\\cascades\\haarcascade_eye.xml");
                    MatOfRect olhosDetectadas = new MatOfRect();
                    ccOlho.detectMultiScale(
                        greyImage, 
                        olhosDetectadas,
                        1.1,
                        1,
                        0,
                        new Size(30, 30),
                        new Size(70, 70)
                    );

                    System.out.println("Quantidade de olhos detectadas: " + olhosDetectadas.toArray().length);
                    for(Rect rect : olhosDetectadas.toArray()) {
                        System.out.println("Localizacao do olho na imagem: " + rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
                        Imgproc.rectangle(
                            colorfulImage, // Imagem que sera adicionada os retangulos
                            new Point(rect.x, rect.y), // Pontos iniciais do retangulo
                            new Point(rect.x + rect.width, rect.y + rect.height), // Pontos finais do retangulo
                            new Scalar(0, 255, 00), // Cor da borda do retangulo em BGR (0, 0, 255) Vermelha
                            2 // Tamanho da borda em pexels
                        );
                    }

                    BufferedImage image = convertMatToImage(colorfulImage);
                    Graphics g = jPanel1.getGraphics();
                    g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), null);
                }
            }
        }
    }

    /**
     * Exemplo 05
     * Deteccao de gatos
     */
    public static void exemplo05() {
        Mat colorfulImage = imread("src\\images\\outros\\gato3.jpg");
        Mat greyImage = new Mat();
        Imgproc.cvtColor(colorfulImage, greyImage, COLOR_BGR2GRAY);
        
        CascadeClassifier cc = new CascadeClassifier("src\\cascades\\haarcascade_frontalcatface.xml");
        MatOfRect facesDetectadas = new MatOfRect();
        cc.detectMultiScale(
            greyImage, 
            facesDetectadas,
            1.06,
            5, 
            0,
            new Size(30, 30),
            new Size(500, 500)
        );

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

    

    /**
     * Exemplo 06
     * Deteccao de relogios
     */
    public static void exemplo06() {
        Mat colorfulImage = imread("src\\images\\outros\\relogio3.jpg");
        Mat greyImage = new Mat();
        Imgproc.cvtColor(colorfulImage, greyImage, COLOR_BGR2GRAY);
        
        CascadeClassifier cc = new CascadeClassifier("src\\cascades\\relogios.xml");
        MatOfRect relogioDetectadas = new MatOfRect();
        cc.detectMultiScale(
            greyImage, 
            relogioDetectadas,
            1.02,
            2, 
            0,
            new Size(45, 45),
            new Size(80, 80)
        );

        System.out.println("Quantidade de relogios detectadas: " + relogioDetectadas.toArray().length);

        for(Rect rect : relogioDetectadas.toArray()) {
            System.out.println("Localizacao do relogio na imagem: " + rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
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