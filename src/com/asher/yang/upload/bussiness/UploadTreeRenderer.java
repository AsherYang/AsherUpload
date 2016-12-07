package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.util.GuiUtil;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import it.sauronsoftware.ftp4j.FTPFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by AsherYang on 2016/12/7.
 * email: ouyangfan1991@gmail.com
 * <p>
 * BrowserPanel tree renderer
 */
public class UploadTreeRenderer extends ColoredTreeCellRenderer{

    public static final Icon TITLE_ICON = GuiUtil.loadIcon("title_icon.png");

    @Override
    public void customizeCellRenderer(@NotNull JTree jTree, Object value, boolean selected, boolean expanded,
                                      boolean leaf, int row, boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object userObject = node.getUserObject();
        if (userObject instanceof String) {
            append(String.valueOf(userObject), SimpleTextAttributes.REGULAR_ITALIC_ATTRIBUTES);
            setToolTipText(String.valueOf(userObject));
            setIcon(TITLE_ICON);
        } else if (userObject instanceof FTPFile) {
            FTPFile file = (FTPFile) userObject;
            append(file.getName(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
        }
    }
}
