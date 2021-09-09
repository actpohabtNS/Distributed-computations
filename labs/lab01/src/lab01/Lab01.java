package lab01;

import javax.swing.*;
import java.awt.*;

public class Lab01 {
    static Thread th1;
    static Thread th2;
    private static int th1P = 5;
    private static int th2P = 5;
    private static int slPos = 50;
    private static final int delay = 50;

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(750, 180);

        JPanel panel = getjPanel();

        win.add(panel);
        win.setTitle("Lab 01");
        win.setVisible(true);
    }

    private static JPanel getjPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        Font bigFont = new Font("sans-serif", Font.PLAIN, 22);

        // Thread 1
        JLabel lTh1 = new JLabel("Thread 1", SwingConstants.CENTER);
        lTh1.setFont(bigFont);
        lTh1.setBounds(20, 20, 200, 30);
        panel.add(lTh1);

        JLabel lTh1P = new JLabel("5", SwingConstants.CENTER);
        lTh1P.setFont(bigFont);
        lTh1P.setBounds(70, 80, 100, 20);
        panel.add(lTh1P);

        JButton bMinus1 = new JButton("-");
        bMinus1.setBounds(20, 70, 50, 50);
        bMinus1.addActionListener(e -> th1P = decrementPrior(th1P, th1, lTh1P));
        panel.add(bMinus1);

        JButton bPlus1 = new JButton("+");
        bPlus1.setBounds(170, 70, 50, 50);
        bPlus1.addActionListener(e -> th1P = incrementPrior(th1P, th1, lTh1P));
        panel.add(bPlus1);

        // Middle section
        JLabel slVal = new JLabel("50", SwingConstants.CENTER);
        slVal.setFont(bigFont);
        slVal.setBounds(319, 20, 100, 20);
        panel.add(slVal);

        JSlider slider = new JSlider(0, 100);
        slider.setBounds(270, 40, 200, 20);
        panel.add(slider);

        JButton bRun = new JButton("Run");
        bRun.setBounds(320, 70, 100, 50);
        bRun.addActionListener(e -> sliderBattle(slVal, slider));
        panel.add(bRun);

        // Thread 2
        JLabel lTh2 = new JLabel("Thread 2", SwingConstants.CENTER);
        lTh2.setFont(bigFont);
        lTh2.setBounds(520, 20, 200, 30);
        panel.add(lTh2);

        JLabel lTh2P = new JLabel("5", SwingConstants.CENTER);
        lTh2P.setFont(bigFont);
        lTh2P.setBounds(570, 80, 100, 20);
        panel.add(lTh2P);

        JButton bMinus2 = new JButton("-");
        bMinus2.setBounds(520, 70, 50, 50);
        bMinus2.addActionListener(e -> th2P = decrementPrior(th2P, th2,lTh2P));
        panel.add(bMinus2);

        JButton bPlus2 = new JButton("+");
        bPlus2.setBounds(670, 70, 50, 50);
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

    private static void sliderBattle(JLabel slVal, JSlider slider) {
        th1 = new Thread(
                () -> {
                    while (true) {
                        synchronized (Lab01.class) {
                            if (10 < slPos) {
                                slPos--;
                                slider.setValue(slPos);
                                slVal.setText(String.valueOf(slPos));
                            }
                            try {
                                Thread.sleep(delay);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                });
        th1.setPriority(th1P);
        th1.start();

        th2 = new Thread(
                () -> {
                    while (true) {
                        synchronized (Lab01.class) {
                            if (slPos < 90) {
                                slPos++;
                                slider.setValue(slPos);
                                slVal.setText(String.valueOf(slPos));
                            }
                            try {
                                Thread.sleep(delay);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                });
        th2.setPriority(th2P);
        th2.start();
    }
}