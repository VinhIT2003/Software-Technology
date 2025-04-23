package com.ComponentandDatabase.Components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MyTreeview extends JTree {
    private final Map<String, Font> customFonts = new HashMap<>();
    private final Map<String, Color> customColors = new HashMap<>();

    public MyTreeview(DefaultMutableTreeNode root) {
        super(new DefaultTreeModel(root));
        this.setRootVisible(true);
        this.setFont(new Font("Arial", Font.PLAIN, 13));
        this.setCellRenderer(new CustomTreeCellRenderer());
        this.setBackground(Color.WHITE); // Màu nền mặc định
    }

    // Phương thức đặt border tùy chỉnh
    public void setCustomBorder(Border border) {
        this.setBorder(border);
    }

    // Phương thức đặt màu nền
    public void setCustomBackground(Color color) {
        this.setBackground(color);
    }

    // Đặt font và màu sắc riêng cho từng node
    public void setFontForNode(String nodeName, Font font, Color color) {
        customFonts.put(nodeName, font);
        customColors.put(nodeName, color);
    }

    // Custom renderer để đặt font riêng cho từng node
    private class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            String nodeName = node.getUserObject().toString();

            if (customFonts.containsKey(nodeName)) {
                comp.setFont(customFonts.get(nodeName));
            }
            if (customColors.containsKey(nodeName)) {
                comp.setForeground(customColors.get(nodeName));
            }

            return comp;
        }
    }
}
