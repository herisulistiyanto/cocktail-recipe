object BuildAndroidConfig {
    const val COMPILE_SDK = 31
    const val MIN_SDK = 21
    const val TARGET_SDK = 31

    private const val MAJOR = 0
    private const val MINOR = 0
    private const val PATCH = 1

    val VERSION_CODE get() = generateVersionCode()
    val VERSION_NAME get() = "$MAJOR.$MINOR.$PATCH"

    private fun generateVersionCode(): Int {
        val biggest = MIN_SDK * 1000000
        val major = MAJOR * 10000
        val minor = MINOR * 100
        return biggest + major + minor + PATCH
    }
}

