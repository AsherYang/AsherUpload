package com.asher.yang.upload.form;

import com.asher.yang.upload.UploadWindowManager;
import com.asher.yang.upload.action.OpenPluginSettingsAction;
import com.asher.yang.upload.action.RefreshNodeAction;
import com.asher.yang.upload.action.SelectViewAction;
import com.asher.yang.upload.action.UploadFileAction;
import com.asher.yang.upload.bean.View;
import com.asher.yang.upload.bussiness.UploadTreeRenderer;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.BrowserHyperlinkListener;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.TreeSpeedSearch;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.containers.Convertor;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.List;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class BrowserPanel extends SimpleToolWindowPanel implements Disposable {

    private Project project;
    private JPanel rootPanel;
    private JPanel filePanel;
    private final Tree fileTree;
    private static final String LOADING = "Loading...";
    private View currentSelectedView;

    public static BrowserPanel getInstance(Project project) {
        return ServiceManager.getService(project, BrowserPanel.class);
    }

    private BrowserPanel(Project project) {
        super(true);
        this.project = project;
        setProvideQuickActions(false);
        fileTree = createTree();
        filePanel.setLayout(new BorderLayout());
        filePanel.add(ScrollPaneFactory.createScrollPane(fileTree), BorderLayout.CENTER);
        setContent(rootPanel);
    }

    public void init() {
        initGui();
    }

    private void initGui() {
        installActionsInToolbar();
        installActionsInPopupMenu();
    }

    private void installActionsInToolbar() {
        DefaultActionGroup actionGroup = new DefaultActionGroup("AsherToolbarGroup", false);
        actionGroup.add(new SelectViewAction(this));
        actionGroup.addSeparator();
        actionGroup.add(new RefreshNodeAction(this, project));
        actionGroup.add(new OpenPluginSettingsAction());
        GuiUtil.installActionGroupInToolBar(actionGroup, this, ActionManager.getInstance(), "AsherBrowserActions");
    }

    private void installActionsInPopupMenu() {
        DefaultActionGroup popupGroup = new DefaultActionGroup("AsherPopupAction", true);

        popupGroup.add(new UploadFileAction(this));

        PopupHandler.installPopupHandler(fileTree, popupGroup, "POPUP", ActionManager.getInstance());
    }

    public FTPFile getSelectedFile() {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
        if (treeNode != null) {
            Object userObject = treeNode.getUserObject();
            if (userObject instanceof FTPFile) {
                return (FTPFile) userObject;
            }
        }
        return null;
    }

    private Tree createTree() {
        SimpleTree tree = new SimpleTree();
        tree.getEmptyText().setText(LOADING);
        tree.setCellRenderer(new UploadTreeRenderer());
        tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("AsherUpload")));

        new TreeSpeedSearch(tree, new Convertor<TreePath, String>() {
            @Override
            public String convert(TreePath treePath) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                Object userObject = node.getUserObject();
                if (userObject instanceof FTPFile) {
                    return ((FTPFile) userObject).getName();
                }
                return "<empty>";
            }
        });
        return tree;
    }

    public void refreshFileTree(List<FTPFile> ftpFiles) {
        if (null == ftpFiles || ftpFiles.isEmpty()) {
            return;
        }
        DefaultTreeModel model = (DefaultTreeModel) fileTree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
        rootNode.removeAllChildren();
        for (FTPFile ftpFile : ftpFiles) {
            DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(ftpFile);
            rootNode.add(fileNode);
        }
        GuiUtil.runInSwingThread(new Runnable() {
            @Override
            public void run() {
                model.nodeStructureChanged((TreeNode) model.getRoot());
            }
        });
    }

    @Override
    public void dispose() {
        ToolWindowManager.getInstance(project).unregisterToolWindow(UploadWindowManager.ASHER_BROWSER);
    }

    public void notifyInfoToolWindow(final String htmlLinkMessage) {
        GuiUtil.runInSwingThread(new Runnable() {
            @Override
            public void run() {
                ToolWindowManager.getInstance(project).notifyByBalloon(UploadWindowManager.ASHER_BROWSER,
                        MessageType.INFO,
                        htmlLinkMessage,
                        null,
                        new BrowserHyperlinkListener());
            }
        });
    }

    public void notifyErrorToolWindow(final String htmlLinkMessage) {
        GuiUtil.runInSwingThread(new Runnable() {
            @Override
            public void run() {
                ToolWindowManager.getInstance(project).notifyByBalloon(UploadWindowManager.ASHER_BROWSER,
                        MessageType.ERROR,
                        htmlLinkMessage,
                        null,
                        new BrowserHyperlinkListener());
            }
        });
    }

    public void notifySuccessMessage(String msg) {
        Notifications.Bus.notify(new Notification("INFO", "UPLOAD SUCCESS . ", msg, NotificationType.INFORMATION));
    }

}
