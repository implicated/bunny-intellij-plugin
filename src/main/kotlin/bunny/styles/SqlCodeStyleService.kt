package bunny.styles

import com.intellij.application.options.CodeStyle
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.sql.formatter.settings.SqlCodeStyleConst.*
import com.intellij.sql.formatter.settings.SqlCodeStyleSettings
import com.intellij.sql.psi.SqlLanguage

@Service(Service.Level.PROJECT)
class SqlCodeStyleService {
    companion object {
        const val PLUGIN_ID = "com.intellij.database"
    }


    fun customStyle(project: Project) {
        val codeStyleSettings = CodeStyle.getSettings(project)
        val customSettings = codeStyleSettings.getCustomSettings(SqlCodeStyleSettings::class.java)
        val settings = codeStyleSettings.getCommonSettings(SqlLanguage.INSTANCE)

        customSettings.USE_GENERAL_STYLE = true

        // case -> word case -> keyword
        customSettings.KEYWORD_CASE = TO_LOWER

        // queries -> INSERT statement and VALUES clause -> Place INTO on the new line=no
        customSettings.INSERT_INTO_NL = REMOVE
        // queries -> UPDATE statement -> Wrap elements=chop if long
        customSettings.SET_EL_WRAP = EL_CHOP_LONG
        // queries -> WITH clause -> Wrap subqueries=chop if long
        customSettings.WITH_EL_WRAP = EL_CHOP_LONG
        // queries -> SELECT clause -> Wrap elements=chop if long
        customSettings.SELECT_EL_WRAP = EL_CHOP_LONG
        // queries -> SELECT clause -> Treat asterisk as a regular element=true
        customSettings.SELECT_ASTERISK_REGULAR = true
        // queries -> FROM clause -> Wrap elements=chop if long
        customSettings.FROM_EL_WRAP = EL_CHOP_LONG
        // queries -> WHERE and HAVING clause -> Wrap elements=chop if long
        customSettings.WHERE_EL_WRAP = EL_CHOP_LONG

        // ddl -> create table -> Place the opening parenthesis=Indented
        customSettings.TABLE_OPENING = OPENING_INDENT
        customSettings.TABLE_CONTENT = CONTENT_WRAPPED_ALIGNED
        // ddl -> views -> indent query=true
        customSettings.VIEW_INDENT_QUERY = true
        // ddl -> postfix options -> Wrap first option=true
        customSettings.POST_OPT_WRAP_1 = true

        // Wrapping -> Keep when reformatting -> Line breaks=false
        settings.KEEP_LINE_BREAKS = false
        // Wrapping -> Keep when reformatting -> Comment at first column=true

        // customSettings.ALIGN_AS_IN_SELECT_STATEMENT = false
        // customSettings.ALIGN_TYPE_IN_CREATE_STATEMENT = false
        // customSettings.ALIGN_TYPE_IN_BLOCK_STATEMENT = false
        // customSettings.ALIGN_TYPE_IN_ARGUMENT_DEFINITION = false
        // customSettings.ALIGN_INSIDE_BINARY_EXPRESSION = false
        // customSettings.ALIGN_INSIDE_QUERY_EXPRESSION = false
        // customSettings.ALIGN_EQ_INSIDE_SET_CLAUSE = false
        // customSettings.NEW_LINE_BEFORE_FROM = false
        // customSettings.NEW_LINE_BEFORE_JOIN = false
        // customSettings.NEW_LINE_BEFORE_WHERE = false
        // customSettings.NEW_LINE_BEFORE_GROUP_BY = false
        // customSettings.NEW_LINE_BEFORE_ORDER_BY = false
        // customSettings.NEW_LINE_BEFORE_HAVING = false
        // customSettings.NEW_LINE_BEFORE_THEN = false
        // customSettings.NEW_LINE_BEFORE_ELSE = false
        // customSettings.NEW_LINE_BEFORE_OTHER_CLAUSES = false
        // customSettings.INDENT_JOIN = false
        // customSettings.INDENT_JOIN_CONDITION = false
        // customSettings.WRAP_INSIDE_CREATE_TABLE = 0
        // customSettings.WRAP_INSIDE_SELECT = 0
        // customSettings.WRAP_INSIDE_JOIN_EXPRESSION = 0
        // customSettings.WRAP_INSIDE_GROUP_BY = 0
        // customSettings.WRAP_INSIDE_WHERE = 0
        // customSettings.WRAP_INSIDE_ORDER_BY = 0
        // customSettings.WRAP_INSIDE_SET = 0
        // customSettings.WRAP_INSIDE_ARGUMENT_DEFINITION = 0
        // customSettings.WRAP_INSIDE_CALL_EXPRESSION = 0
        // customSettings.WRAP_INSIDE_VALUES_EXPRESSION = 0
        // customSettings.WRAP_VALUES_EXPRESSION = 0
        // customSettings.WRAP_PARENTHESIZED_EXPRESSION_INSIDE_VALUES = 0
        // customSettings.NEW_LINE_AFTER_SELECT_ITEM = false
    }
}
