package lab01;

import javax.swing.*;
import java.awt.*;

public class Lab01 {
    static Thread th1;
    static Thread th2;
    private static int th1P = 5;
    private static int th2P = 5;
    private static int slVal = 50;
    private static final int delay = 50;
    private static boolean isBusy = false;

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(750, 210);

        JPanel panel = getjPanel();

        win.add(panel);
        win.setTitle("Lab 01");
        win.setVisible(true);
    }

    private static JPanel getjPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font bigFont = new Font("sans-serif", Font.PLAIN, 22);

        // Declarations
        JLabel lTh1 = new JLabel("Thread 1", SwingConstants.CENTER);
        JLabel lTh1P = new JLabel("5", SwingConstants.CENTER);
        JButton bMinus1 = new JButton("-");
        JButton bPlus1 = new JButton("+");
        JLabel slVal = new JLabel("50", SwingConstants.CENTER);
        JSlider slider = new JSlider();
        JLabel lBusy = new JLabel("free", SwingConstants.CENTER);
        JButton bRun1 = new JButton("Run 1");
        JButton bStop1 = new JButton("Stop 1");
        JButton bRun2 = new JButton("Run 2");
        JButton bStop2 = new JButton("Stop 2");
        JLabel lTh2 = new JLabel("Thread 2", SwingConstants.CENTER);
        JLabel lTh2P = new JLabel("5", SwingConstants.CENTER);
        JButton bMinus2 = new JButton("-");
        JButton bPlus2 = new JButton("+");

        // Thread 1
        lTh1.setFont(bigFont);
        lTh1.setBounds(20, 35, 200, 30);
        panel.add(lTh1);

        lTh1P.setFont(bigFont);
        lTh1P.setBounds(70, 95, 100, 20);
        panel.add(lTh1P);

        bMinus1.setBounds(20, 85, 50, 50);
        bMinus1.addActionListener(e -> th1P = decrementPrior(th1P, th1, lTh1P));
        panel.add(bMinus1);

        bPlus1.setBounds(170, 85, 50, 50);
        bPlus1.addActionListener(e -> th1P = incrementPrior(th1P, th1, lTh1P));
        panel.add(bPlus1);

        // Middle section
        slVal.setFont(bigFont);
        slVal.setBounds(319, 20, 100, 20);
        panel.add(slVal);

        slider.setBounds(270, 40, 200, 20);
        panel.add(slider);

        lBusy.setBounds(340, 100, 60, 20);
        panel.add(lBusy);

        bRun1.setBounds(270, 70, 70, 40);
        bRun1.setBackground(new Color(187, 255, 158));
        bRun1.addActionListener(e -> {
            startTh1(slVal, slider);
            isBusy = true;
            lBusy.setText("busy");
            setButtonsEnabled(new JButton[]{bRun1, bRun2, bMinus2, bPlus2}, false);
            setButtonsEnabled(new JButton[]{bStop1}, true);
        });
        panel.add(bRun1);

        bStop1.setBounds(270, 120, 70, 40);
        bStop1.setBackground(new Color(255, 158, 158));
        bStop1.setEnabled(false);
        bStop1.addActionListener(e -> {
            th1.interrupt();
            isBusy = false;
            lBusy.setText("free");
            setButtonsEnabled(new JButton[]{bRun1, bRun2, bMinus2, bPlus2}, true);
            setButtonsEnabled(new JButton[]{bStop1}, false);
        });
        panel.add(bStop1);

        bRun2.setBounds(400, 70, 70, 40);
        bRun2.setBackground(new Color(187, 255, 158));
        bStop2.setEnabled(false);
        bRun2.addActionListener(e -> {
            startTh2(slVal, slider);
            isBusy = true;
            lBusy.setText("busy");
            setButtonsEnabled(new JButton[]{bRun1, bRun2, bMinus1, bPlus1}, false);
            setButtonsEnabled(new JButton[]{bStop2}, true);
        });
        panel.add(bRun2);

        bStop2.setBounds(400, 120, 70, 40);
        bStop2.setBackground(new Color(255, 158, 158));
        bStop2.addActionListener(e -> {
            th2.interrupt();
            isBusy = false;
            lBusy.setText("free");
            setButtonsEnabled(new JButton[]{bRun1, bRun2, bMinus1, bPlus1}, true);
            setButtonsEnabled(new JButton[]{bStop2}, false);
        });
        panel.add(bStop2);

        // Thread 2
        lTh2.setFont(bigFont);
        lTh2.setBounds(520, 35, 200, 30);
        panel.add(lTh2);

        lTh2P.setFont(bigFont);
        lTh2P.setBounds(570, 95, 100, 20);
        panel.add(lTh2P);

        bMinus2.setBounds(520, 85, 50, 50);
        bMinus2.addActionListener(e -> th2P = decrementPrior(th2P, th2,lTh2P));
        panel.add(bMinus2);

        bPlus2.setBounds(670, 85, 50, 50);
        bPlus2.addActionListener(e -> th2P = incrementPrior(th2P, th2, lTh2P));
        panel.add(bPlus2);

        return panel;
    }

    private static int incrementPrior(int prior, Thread th, JLabel label) {
        if (prior < 10) {
            prior++;
            if (th != null) {
                th.setPriority(prior);
            }
            label.setText(String.valueOf(prior));
        }

        return prior;
    }

    private static int decrementPrior(int prior, Thread th, JLabel label) {
        if (1 < prior) {
            prior--;
            if (th != null) {
                th.setPriority(prior);
            }
            label.setText(String.valueOf(prior));
        }

        return prior;
    }

    private static void startTh1(JLabel label, JSlider slider) {
        if (isBusy) {
            return;
        }

        th1 = new Thread(
                () -> {
                    while (true) {
                        if (10 < slVal) {
                            slVal--;
                            slider.setValue(slVal);
                            label.setText(String.valueOf(slVal));
                        }
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException interruptedException) {
                            break;
                        }
                    }
                });
        th1.setPriority(th1P);
        th1.setDaemon(true);
        th1.start();
    }

    private static void startTh2(JLabel label, JSlider slider) {
        if (isBusy) {
            return;
        }

        th2 = new Thread(
                () -> {
                    while (true) {
                        if (slVal < 90) {
                            slVal++;
                            slider.setValue(slVal);
                            label.setText(String.valueOf(slVal));
                        }
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException interruptedException) {
                            break;
                        }
                    }
                });
        th2.setPriority(th2P);
        th1.setDaemon(true);
        th2.start();
    }

    private static void setButtonsEnabled(JButton[] buttons, boolean enable) {
        for (JButton button : buttons) {
            button.setEnabled(enable);
        }
    }
}