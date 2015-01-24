import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Example extends BasicGame {
  public Example(String gameName) {
    super(gameName);
  }

  @Override
  public void init(GameContainer gc) throws SlickException {}

  @Override
  public void update(GameContainer gc, int i) throws SlickException {}

  @Override
  public void render(GameContainer gc, Graphics g) throws SlickException {
    g.drawString("Hello World!", 50, 50);
  }

  public static void main(String[] args) {
    try {
      AppGameContainer container;
      container = new AppGameContainer(new Example("Example Game"));
      container.setDisplayMode(640, 480, false);
      container.start();
    } catch (SlickException ex) {
      ex.printStackTrace();
    }
  }
}
