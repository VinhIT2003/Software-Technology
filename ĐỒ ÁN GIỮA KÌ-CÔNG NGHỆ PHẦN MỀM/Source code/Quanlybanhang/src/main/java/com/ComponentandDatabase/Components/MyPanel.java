package com.ComponentandDatabase.Components;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    public static final int SOLID_COLOR = 0;
    public static final int VERTICAL_GRADIENT = 1;
    public static final int HORIZONTAL_GRADIENT = 2;

    private int mode = SOLID_COLOR;
    private Color solidColor = Color.WHITE;
    private Color gradientStart = Color.decode("#1CB5E0");
    private Color gradientEnd = Color.decode("#000046");

    // ===== CONSTRUCTORS =====

    // Constructor mặc định
    public MyPanel() {
        super();
    }

    // Constructor với Layout
    public MyPanel(LayoutManager layout) {
        super(layout);
    }

    // Constructor cho màu đơn sắc
    public MyPanel(Color solidColor) {
        super();
        this.solidColor = solidColor;
        this.mode = SOLID_COLOR;
    }

    // Constructor cho màu đơn sắc + Layout
    public MyPanel(Color solidColor, LayoutManager layout) {
        super(layout);
        this.solidColor = solidColor;
        this.mode = SOLID_COLOR;
    }

    // Constructor cho gradient
    public MyPanel(Color start, Color end, int mode) {
        super();
        this.gradientStart = start;
        this.gradientEnd = end;
        this.mode = mode;
    }

    // Constructor cho gradient + Layout
    public MyPanel(Color start, Color end, int mode, LayoutManager layout) {
        super(layout);
        this.gradientStart = start;
        this.gradientEnd = end;
        this.mode = mode;
    }

    // ===== SETTERS =====

    public void setSolidColor(Color color) {
        this.solidColor = color;
        this.mode = SOLID_COLOR;
        repaint();
    }

    public void setGradientColors(Color start, Color end, int mode) {
        this.gradientStart = start;
        this.gradientEnd = end;
        this.mode = mode;
        repaint();
    }

    // ===== PAINTING =====

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        switch (mode) {
            case SOLID_COLOR -> {
                g2.setColor(solidColor);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
            case VERTICAL_GRADIENT -> {
                GradientPaint gp = new GradientPaint(0, 0, gradientStart, 0, getHeight(), gradientEnd);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
            case HORIZONTAL_GRADIENT -> {
                GradientPaint gp = new GradientPaint(0, 0, gradientStart, getWidth(), 0, gradientEnd);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        }

        g2.dispose();
    }
}