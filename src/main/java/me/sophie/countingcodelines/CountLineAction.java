package me.sophie.countingcodelines;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class CountLineAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Messages.showMessageDialog(event.getProject(), "Hi", "Hi", null);
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        if(event.getProject() != null) {
            event.getPresentation().setEnabledAndVisible(true);
        } else {
            event.getPresentation().setEnabledAndVisible(false);
        }
    }
}
