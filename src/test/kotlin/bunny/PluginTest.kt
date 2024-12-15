package bunny

import com.intellij.application.options.CodeStyle
import com.intellij.lang.Language
import com.intellij.lang.java.JavaLanguage
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import com.intellij.psi.codeStyle.JavaCodeStyleSettings
import com.intellij.sql.formatter.settings.SqlCodeStyleSettings
import com.intellij.sql.psi.SqlLanguage
import com.intellij.testFramework.HeavyPlatformTestCase
import com.intellij.testFramework.LightPlatformTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.core.formatter.KotlinCodeStyleSettings

class PluginTest :
// BasePlatformTestCase
//     LightPlatformTestCase
    HeavyPlatformTestCase
        () {

    fun testCodeStyleOption() {
        val defaultSettings = CodeStyle.getDefaultSettings()
        printCodeStyleSettings(defaultSettings, SqlLanguage.INSTANCE, SqlCodeStyleSettings::class.java)
        printCodeStyleSettings(defaultSettings, KotlinLanguage.INSTANCE, KotlinCodeStyleSettings::class.java)
        printCodeStyleSettings(defaultSettings, JavaLanguage.INSTANCE, JavaCodeStyleSettings::class.java)
    }

    // 使用反射打印所有字段和值
    private fun <T : CustomCodeStyleSettings> printCodeStyleSettings(
        settings: CodeStyleSettings,
        language: Language,
        any: Class<T>
    ) {
        println("${language.displayName} ${any.javaClass.simpleName}")

        val commonSettings = settings.getCommonSettings(language)
        val customSettings = settings.getCustomSettings(any)

        // listOf(commonSettings, customSettings).forEach {
        //     it.javaClass.declaredFields.forEach { field ->
        //         field.isAccessible = true
        //         try {
        //             println("${field.name} = ${field.get(it)}")
        //         } catch (e: Exception) {
        //             println("error ==> :{${e}}")
        //         }
        //     }
        // }
    }
}