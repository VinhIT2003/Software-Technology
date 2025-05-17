package com.ComponentandDatabase.Components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MyTable extends JTable {

    public MyTable(DefaultTableModel model, Color backgroundColor, Color foregroundColor,
                   Color selectionBackgroundColor, Color selectionForegroundColor,
                   Color headerBackgroundColor, Color headerForegroundColor,
                   Font contentFont, Font headerFont) {
        super(model);
        initTable(backgroundColor, foregroundColor, selectionBackgroundColor, selectionForegroundColor,
                  headerBackgroundColor, headerForegroundColor, contentFont, headerFont);
    }

    private void initTable(Color backgroundColor, Color foregroundColor,
                           Color selectionBackgroundColor, Color selectionForegroundColor,
                           Color headerBackgroundColor, Color headerForegroundColor,
                           Font contentFont, Font headerFont) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cài đặt nội dung bảng
        setFont(contentFont);
        setRowHeight(35);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setSelectionBackground(selectionBackgroundColor);
        setSelectionForeground(selectionForegroundColor);
        setGridColor(Color.GRAY);
        setShowGrid(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Căn giữa nội dung bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        setDefaultRenderer(Object.class, centerRenderer);

        // ✅ Tùy chỉnh JTableHeader để loại bỏ hover hoàn toàn
        JTableHeader header = new JTableHeader(getColumnModel()) {
            @Override
            public void updateUI() {
                setUI(new javax.swing.plaf.basic.BasicTableHeaderUI());
            }
        };
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setOpaque(true);
                label.setBackground(headerBackgroundColor);
                label.setForeground(headerForegroundColor);
                label.setFont(headerFont);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(230, 230, 230)), // viền mỏng đáy
                        BorderFactory.createEmptyBorder(2, 4, 2, 4) // padding top-bottom
                ));
                return label;
            }
        });

     setTableHeader(header);
     header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));

    }

    public void adjustColumnWidths() {
        TableColumnModel columnModel = getColumnModel();
        JTableHeader header = getTableHeader();

        for (int col = 0; col < getColumnCount(); col++) {
            TableColumn column = columnModel.getColumn(col);

            TableCellRenderer headerRenderer = column.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = header.getDefaultRenderer();
            }

            Component headerComp = headerRenderer.getTableCellRendererComponent(this, column.getHeaderValue(), false, false, -1, col);
            int maxWidth = headerComp.getPreferredSize().width + 15;

            for (int row = 0; row < getRowCount(); row++) {
                TableCellRenderer cellRenderer = getCellRenderer(row, col);
                Component comp = prepareRenderer(cellRenderer, row, col);
                maxWidth = Math.max(maxWidth, comp.getPreferredSize().width + 15);
            }

            column.setPreferredWidth(maxWidth);
        }
    }

    public static JScrollPane createScrollPane(MyTable table, int x, int y, int width, int height) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(x, y, width, height);
        scrollPane.setViewportView(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return scrollPane;
    }
}
