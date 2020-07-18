object Versions {
    val versionName = "7.0.15" // X.Y.Z; X = Major, Y = minor, Z = Patch level
    private val versionCodeBase = 70150 // XYYZZM; M = Module (tv, mobile)
    val versionCodeMobile = versionCodeBase + 3

    const val COMPILE_SDK = 29
    const val TARGET_SDK = 29
    const val MIN_SDK = 19

    const val ANDROID_GRADLE_PLUGIN = "4.0.1"
    const val GOOGLE_SERVICES = "4.3.3"
    const val KOTLIN = "1.3.72"
    const val ROOM = "2.2.5"
    const val DAGGER = "2.28.1"

    const val OKHTTP = "4.8.0"

}