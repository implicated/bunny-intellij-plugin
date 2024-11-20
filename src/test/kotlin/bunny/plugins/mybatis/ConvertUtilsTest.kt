package bunny.plugins.mybatis


class ConvertUtilsTest {

    fun convert() {
        ConvertUtils.main()
    }

    // Main 方法用于简单测试
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val test = ConvertUtilsTest()
            test.convert()  // 调用 convert 方法进行测试
        }
    }
}