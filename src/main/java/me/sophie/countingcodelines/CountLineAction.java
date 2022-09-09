package me.sophie.countingcodelines;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class CountLineAction extends AnAction {
    private ArrayList<FileStats> list;
    private ArrayList<String> supportedExtensions = new ArrayList<>(List.of("java", "txt", "json", "gradle"));
    private ArrayList<String> blacklist = new ArrayList<>(List.of(".gradle", ".idea", ".run", "build", "gradle"));
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        VirtualFile file = ProjectUtil.guessProjectDir(event.getProject());
        list = new ArrayList<>();

        if (file.isDirectory()) {
            VirtualFile[] files = file.getChildren();
            try {
                traverse(files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int lines = 0;
        int codeLines = 0;
        int commentLines = 0;
        int blankLines = 0;

        for (int i = 0; i < list.size(); i++) {
            FileStats entry = list.get(i);
            lines = lines + entry.getLines();
            codeLines = codeLines+ + entry.getCodeLines();
            commentLines = commentLines + entry.getCommentLines();
            blankLines = blankLines + entry.getBlankLines();
        }
        StringBuilder message = new StringBuilder();
        message.append("You have: \n").append(lines).append(" lines, \n").append(codeLines).append(" code lines, \n")
                        .append(commentLines).append(" comment lines and \n").append(blankLines).append(" blank Lines. :)");
        Messages.showMessageDialog(event.getProject(), message.toString(), "Project Lines", null);
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        if (event.getProject() != null) {
            event.getPresentation().setEnabledAndVisible(true);
        } else {
            event.getPresentation().setEnabledAndVisible(false);
        }
    }
}
