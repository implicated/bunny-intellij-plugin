package bunny.ban;

import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.impl.TemplateSettings;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BanLiveTemplate implements StartupActivity {
    private static final Logger LOG = Logger.getInstance(BanLiveTemplate.class);
    private static final List<String> BAN = List.of("AsciiDoc", "PlantUml");

    @Override
    public void runActivity(@NotNull Project project) {
        LOG.info("zylog ==> register" + this.getClass().getSimpleName());
        ApplicationManager.getApplication().invokeLater(() -> {
            TemplateSettings templateSettings = TemplateSettings.getInstance();
            for (TemplateImpl template : templateSettings.getTemplates()) {
                if (BAN.contains(template.getGroupName())) {
                    templateSettings.removeTemplate(template);
                }
            }
        });
    }
}
