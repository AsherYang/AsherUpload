package com.asher.yang.upload.action;

import com.asher.yang.upload.bean.View;
import com.asher.yang.upload.form.BrowserPanel;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;

import javax.swing.*;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class SelectViewAction extends AnAction implements CustomComponentAction {

    private static final Icon ARROWS_ICON = GuiUtil.loadIcon("/ide/", "statusbar_arrows.png");

    private BrowserPanel browserPanel;
    protected final JLabel myLabel;
    protected final JPanel myPanel;

    public SelectViewAction(BrowserPanel browserPanel) {
        this.browserPanel = browserPanel;
        myLabel = new JLabel();
        myPanel = new JPanel();

        BoxLayout layout = new BoxLayout(myPanel, BoxLayout.X_AXIS);
        myPanel.setLayout(layout);
        final JLabel show = new JLabel("View:");
        show.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
        myPanel.add(show);
        myPanel.add(myLabel);
        myPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 3));
        final JLabel iconLabel = new JLabel(ARROWS_ICON);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
        myPanel.add(iconLabel, myLabel);

    }

    @Override
    public void update(AnActionEvent e) {
        View currentSelectedView = browserPanel.getCurrentSelectedView();
        if (currentSelectedView != null) {
            myLabel.setText(currentSelectedView.getName());
        }
        myLabel.setText("");
    }


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

    }

    @Override
    public JComponent createCustomComponent(Presentation presentation) {
        return myPanel;
    }
}
