package pl.edu.pw.mini.cadcam.pusn;

import com.formdev.flatlaf.FlatDarkLaf;
import com.hermant.swing.WindowBuilder;
import pl.edu.pw.mini.cadcam.pusn.controller.MainController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatDarkLaf.setup();
            MainController controller = new MainController();
            JFrame window = new WindowBuilder()
                    .setContentPane(controller.getMainPane())
                    .setSize(1280, 720)
                    .setMinimumSize(320, 240)
                    .setTitle("GModeling")
                    .setExitOnClose()
                    .buildFrame();
        });
    }


}
