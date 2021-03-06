package pl.edu.pw.mini.cadcam.pusn.controller;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import pl.edu.pw.mini.cadcam.pusn.graphics.Interpolation;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static com.jogamp.opengl.math.FloatUtil.PI;

public class InterpolationController {
    private JComboBox<String> leftInterpolationCombo;
    private JComboBox<String> rightInterpolationCombo;
    private JButton startPositionButton;
    private JButton startRotationButton;
    private JButton endPositionButton;
    private JButton endRotationButton;
    private JButton startTimeButton;
    private JButton endTimeButton;
    private JButton interpolateButton;
    private JCheckBox showKeyframesCheckbox;
    private JSpinner keyframesSpinner;
    private JPanel mainPane;
    private JLabel startTimeLabel;
    private JLabel startPositionLabel;
    private JLabel startRotationLabel;
    private JLabel endTimeLabel;
    private JLabel endPositionLabel;
    private JLabel endRotationLabel;

    private final Vector3f startRotation = new Vector3f();
    private final Vector3f startPosition = new Vector3f();
    private float startTime;
    private final Vector3f endRotation = new Vector3f();
    private final Vector3f endPosition = new Vector3f();
    private float endTime;

    public InterpolationController(Consumer<Interpolation> setAnimation, Consumer<BiFunction<Interpolation, Float, Matrix4f>> setRightInterpolation, Consumer<BiFunction<Interpolation, Float, Matrix4f>> setLeftInterpolation, BiConsumer<Boolean, Integer> setShowKeyframes) {
        $$$setupUI$$$();
        keyframesSpinner.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        startRotationButton.addActionListener(e -> new RotationController(startRotation::set, startRotationLabel::setText));
        startPositionButton.addActionListener(e -> new PositionController(startPosition::set, startPositionLabel::setText));
        startTimeButton.addActionListener(e -> new TimeController(t -> startTime = t, startTimeLabel::setText));
        endRotationButton.addActionListener(e -> new RotationController(endRotation::set, endRotationLabel::setText));
        endPositionButton.addActionListener(e -> new PositionController(endPosition::set, endPositionLabel::setText));
        endTimeButton.addActionListener(e -> new TimeController(t -> endTime = t, endTimeLabel::setText));
        interpolateButton.addActionListener(e -> {
            if (setAnimation != null) {
                if (startRotation.x < 0) startRotation.x += 2 * PI;
                if (endRotation.x < 0) endRotation.x += 2 * PI;
                if (endRotation.x - startRotation.x > PI) endRotation.x -= 2 * PI;
                if (startRotation.x - endRotation.x > PI) startRotation.x -= 2 * PI;
                if (startRotation.y < 0) startRotation.y += 2 * PI;
                if (endRotation.y < 0) endRotation.y += 2 * PI;
                if (endRotation.y - startRotation.y > PI) endRotation.y -= 2 * PI;
                if (startRotation.y - endRotation.y > PI) startRotation.y -= 2 * PI;
                if (startRotation.z < 0) startRotation.z += 2 * PI;
                if (endRotation.z < 0) endRotation.z += 2 * PI;
                if (endRotation.z - startRotation.z > PI) endRotation.z -= 2 * PI;
                if (startRotation.z - endRotation.z > PI) startRotation.z -= 2 * PI;
                setAnimation.accept(new Interpolation(startTime, endTime, new Vector3f(startRotation), new Vector3f(endRotation), new Vector3f(startPosition), new Vector3f(endPosition)));
            }
        });
        leftInterpolationCombo.addActionListener(e -> {
            switch (Objects.requireNonNull(leftInterpolationCombo.getSelectedItem()).toString()) {
                case "Linear euler" -> setLeftInterpolation.accept(Interpolation::interpolateEuler);
                case "Linear quat" -> setLeftInterpolation.accept(Interpolation::interpolateQuaternion);
                case "Spherical quat" -> setLeftInterpolation.accept(Interpolation::interpolateSpherical);
            }
        });
        rightInterpolationCombo.addActionListener(e -> {
            switch (Objects.requireNonNull(rightInterpolationCombo.getSelectedItem()).toString()) {
                case "Linear euler" -> setRightInterpolation.accept(Interpolation::interpolateEuler);
                case "Linear quat" -> setRightInterpolation.accept(Interpolation::interpolateQuaternion);
                case "Spherical quat" -> setRightInterpolation.accept(Interpolation::interpolateSpherical);
            }
        });
        rightInterpolationCombo.setSelectedIndex(2);
        keyframesSpinner.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        showKeyframesCheckbox.addActionListener(e -> setShowKeyframes.accept(showKeyframesCheckbox.isSelected(), ((Number) keyframesSpinner.getValue()).intValue()));
        keyframesSpinner.addChangeListener(e -> setShowKeyframes.accept(showKeyframesCheckbox.isSelected(), ((Number) keyframesSpinner.getValue()).intValue()));
    }

    public Container getMainPane() {
        return mainPane;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPane = new JPanel();
        mainPane.setLayout(new GridLayoutManager(9, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Left Interpolation");
        mainPane.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainPane.add(spacer1, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        leftInterpolationCombo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Linear euler");
        defaultComboBoxModel1.addElement("Linear quat");
        defaultComboBoxModel1.addElement("Spherical quat");
        leftInterpolationCombo.setModel(defaultComboBoxModel1);
        mainPane.add(leftInterpolationCombo, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Right Interpolation");
        mainPane.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rightInterpolationCombo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Linear euler");
        defaultComboBoxModel2.addElement("Linear quat");
        defaultComboBoxModel2.addElement("Spherical quat");
        rightInterpolationCombo.setModel(defaultComboBoxModel2);
        mainPane.add(rightInterpolationCombo, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPane.add(panel1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(null, "start", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        startPositionButton = new JButton();
        startPositionButton.setText("Position");
        panel1.add(startPositionButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startPositionLabel = new JLabel();
        startPositionLabel.setText("[0, 0, 0]");
        panel1.add(startPositionLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startRotationButton = new JButton();
        startRotationButton.setText("Rotation");
        panel1.add(startRotationButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startRotationLabel = new JLabel();
        startRotationLabel.setText("[0, 0, 0]");
        panel1.add(startRotationLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startTimeButton = new JButton();
        startTimeButton.setText("Time");
        panel1.add(startTimeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startTimeLabel = new JLabel();
        startTimeLabel.setText("0");
        panel1.add(startTimeLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPane.add(panel2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "end", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        endPositionButton = new JButton();
        endPositionButton.setText("Position");
        panel2.add(endPositionButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endRotationButton = new JButton();
        endRotationButton.setText("Rotation");
        panel2.add(endRotationButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endPositionLabel = new JLabel();
        endPositionLabel.setText("[0, 0, 0]");
        panel2.add(endPositionLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endRotationLabel = new JLabel();
        endRotationLabel.setText("[0, 0, 0]");
        panel2.add(endRotationLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimeLabel = new JLabel();
        endTimeLabel.setText("0");
        panel2.add(endTimeLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimeButton = new JButton();
        endTimeButton.setText("Time");
        panel2.add(endTimeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPane.add(panel3, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(null, "keyframes", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        keyframesSpinner = new JSpinner();
        panel3.add(keyframesSpinner, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showKeyframesCheckbox = new JCheckBox();
        showKeyframesCheckbox.setText("show keyframes");
        panel3.add(showKeyframesCheckbox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        interpolateButton = new JButton();
        interpolateButton.setText("Interpolate");
        mainPane.add(interpolateButton, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPane;
    }

}
