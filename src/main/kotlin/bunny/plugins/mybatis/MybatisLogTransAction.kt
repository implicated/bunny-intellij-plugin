package bunny.plugins.mybatis

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project

class MybatisLogTransAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        // 获取编辑器对象
        val editor = checkNotNull(e.dataContext.getData(CommonDataKeys.EDITOR))
        val currentCaret = editor.caretModel.currentCaret
        val selectedText = currentCaret.selectedText

        if (!currentCaret.hasSelection() || selectedText.isNullOrEmpty()) {
            showMessage(project, "请先用光标选定mybatis的sql日志和参数日志")
            return
        }

        val strNum = ConvertUtils.strNum(selectedText, SQL_START_STR) + ConvertUtils.strNum(
            selectedText,
            PARAMS_START_STR
        )
        if (strNum != 2) {
            val message = if (strNum < 2) {
                "光标选定mybatis的sql日志和参数日志时，尽量包含“Preparing: ”和“Parameters: ” "
            } else {
                "选定内容格式不对，请检查选择的内容是否为一对对应的sql日志和参数日志"
            }
            showMessage(project, message)
            return
        }

        try {
            // 截取sql
            val sqlStart = selectedText.indexOf(SQL_START_STR)
            val paramsStart = selectedText.indexOf(PARAMS_START_STR)
            val subSql = selectedText.substring(sqlStart + SQL_START_STR.length, paramsStart)
            val sql = subSql.substring(0, subSql.lastIndexOf(CHANGE_LINE))
            // 截取参数
            val subParams = selectedText.substring(paramsStart + PARAMS_START_STR.length)
            val params = if (subParams.contains(CHANGE_LINE)) subParams.substring(
                0,
                subParams.lastIndexOf(CHANGE_LINE)
            ) else subParams
            // 转换
            val convert = ConvertUtils.convert(sql, params)
            if (convert.isNullOrEmpty()) {
                showMessage(project, "转换失败，请检查参数是否正确")
                return
            }
            ClipboardUtils.content = convert
            showMessage(project, "转换成功！已将结果复制到剪切板，直接粘贴即可")
        } catch (exception: Exception) {
            showMessage(project, "转换失败！" + exception.message)
        }
    }


    companion object {
        const val SQL_START_STR: String = "Preparing: "
        const val PARAMS_START_STR: String = "Parameters: "
        const val CHANGE_LINE: String = "\n"

        @JvmStatic
        fun showMessage(project: Project?, message: String?) {
            val groupId = "ConvertLog.NotificationGroup"
            val notification = Notification(
                groupId, "", message!!,
                NotificationType.INFORMATION
            )
            Notifications.Bus.notify(notification, project)
        }
    }
}
