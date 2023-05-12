import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

public class ScreenCapture {

    public static void main(String[] args) {
        while (true) {
            try {
                // 等待10秒
                Thread.sleep(10000);

                // 获取屏幕尺寸
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Rectangle screenRectangle = new Rectangle(screenSize);

                // 截屏
                Robot robot = new Robot();
                BufferedImage image = robot.createScreenCapture(screenRectangle);

                // 生成文件名
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String fileName = "screenshot_" + dateFormat.format(new Date()) + ".png";

                // 保存截图为PNG文件
                ImageIO.write(image, "png", new File(fileName));

                System.out.println("截图已保存: " + fileName);
            } catch (InterruptedException | AWTException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
