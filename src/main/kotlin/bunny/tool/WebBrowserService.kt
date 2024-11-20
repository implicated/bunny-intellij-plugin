package bunny.tool

import com.intellij.ide.browsers.BrowserFamily
import com.intellij.ide.browsers.WebBrowserManager

class WebBrowserService {
    fun setEnableBrowser() {
        val webBrowserManager = WebBrowserManager.getInstance()
        webBrowserManager.findBrowserById(BrowserFamily.SAFARI.name)
    }
}