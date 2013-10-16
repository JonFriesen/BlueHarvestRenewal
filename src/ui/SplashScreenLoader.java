package ui;

import javax.swing.UIManager;
import javax.swing.ImageIcon;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import resources.Commons;
import resources.ImageCache;

public class SplashScreenLoader {

  SplashScreen screen;

  public SplashScreenLoader() {
    // initialize the splash screen
    splashScreenInit();
    // do something here to simulate the program doing something that
    // is time consuming
    for (int i = 0; i <= 100; i++)
    {
      for (long j=0; j<50000; ++j)
      {
        String counter = " " + (j + i);
      }
      // run either of these two -- not both
      screen.setProgress("Loading " + i, i);  // progress bar with a message
      //screen.setProgress(i);           // progress bar with no message
    }
    splashScreenDestruct();
    //System.exit(0);
  }

  private void splashScreenDestruct() {
    screen.setScreenVisible(false);
  }
  private int randomNum(int n){
	  Random generator = new Random();
	  return generator.nextInt(4);
  }
  public static BufferedImage resizeImage(final Image image) {
      final BufferedImage bufferedImage = new BufferedImage(Commons.WIDTH, Commons.HEIGHT, BufferedImage.TYPE_INT_RGB);
      final Graphics2D graphics2D = bufferedImage.createGraphics();
      graphics2D.setComposite(AlphaComposite.Src);
      graphics2D.drawImage(image, 0, 0, Commons.WIDTH, Commons.HEIGHT, null);
      graphics2D.dispose();
      
      return bufferedImage;
  }
  private void splashScreenInit() {
    //ImageIcon myImage = new ImageIcon(com.devdaily.splashscreen.SplashScreenMain.class.getResource("SplashImage.gif"));
	  ImageIcon myImage = new ImageIcon(resizeImage(ImageCache.getImage(ImageCache.backgrounds[randomNum(ImageCache.backgrounds.length)])));
    screen = new SplashScreen(myImage);
    screen.setLocationRelativeTo(null);
    screen.setProgressMax(100);
    screen.setScreenVisible(true);
  }

  public static void Loader()
  {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    new SplashScreenLoader();
  }

}

