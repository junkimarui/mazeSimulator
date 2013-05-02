package problemSet1;

import java.io.IOException;

public class Controller {
    public static void main(String[] args) {
        try {
            Map map1 = new Map("maze-9-9.txt");
            Robot robot = new Robot(map1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
