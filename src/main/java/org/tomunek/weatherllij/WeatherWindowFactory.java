package org.tomunek.weatherllij;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;


class WeatherWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        WeatherWindowContent weatherWindowContent = WeatherWindowContent.getInstance();
        Content content = ContentFactory.getInstance().createContent(weatherWindowContent.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}