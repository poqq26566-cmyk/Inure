package app.simple.inure.util

import android.content.res.Resources
import android.os.Build
import android.view.View
import app.simple.inure.models.Locales
import java.util.Locale

object LocaleUtils {

    private var appLocale = Locale.getDefault()

    /**
     * Code for russian locale
     *
     * https://community.appinventor.mit.edu/t/android-localization-code-the-complete-list/7055
     */
    private val russianLocale = arrayOf("ru", "RU", "ru-RU")

    fun getSystemLanguageCode(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0].language
        } else {
            @Suppress("deprecation")
            Resources.getSystem().configuration.locale.language
        }
    }

    fun getSystemLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0]
        } else {
            @Suppress("deprecation")
            Resources.getSystem().configuration.locale
        }
    }

    fun isOneOfTraditionalChinese(): Boolean {
        val locale = getSystemLocale()

        if (locale.language != "zh") {
            return false
        }

        // A bare "zh" language code (which is what most Simplified-Chinese devices/ROMs,
        // mainland China included, actually report) does NOT by itself mean Traditional —
        // only the specific Traditional-using regions/script tag do. Checking just the
        // language prefix here was misclassifying plain Simplified Chinese as Traditional.
        return locale.country in arrayOf("TW", "HK", "MO") || locale.script == "Hant"
    }

    /**
     * List of languages currently supported by
     * the app.
     *
     * Do not include incomplete translations.
     *
     * Use Java 8's [Locale] codes and not Android's:
     * https://www.oracle.com/java/technologies/javase/jdk8-jre8-suported-locales.html
     */
    val localeList = arrayListOf(
            // Auto detect language (default)
            Locales("autoSystemLanguageString" /* Placeholder */, "default"),
            // English (United States)
            Locales("English (US)", "en-US"),
            // Traditional Chinese (Taiwan)
            Locales("繁體中文 (Traditional Chinese)", "zh-TW"),
            // Simplified Chinese (China)
            Locales("简体中文 (Simplified Chinese)", "zh-CN"),
            // Russian
            Locales("Русский (Russian)", "ru-RU"),
            // Italian
            Locales("Italiano (Italian)", "it-IT"),
            // Portuguese (Brazil)
            Locales("Português (Brazil)", "pt-BR"),
            // Spanish (Spain)
            Locales("Español (Spanish)", "es-ES"),
            // Arabic
            Locales("العربية (Arabic)", "ar"),
            // Turkish
            Locales("Türkçe (Turkish)", "tr-TR"),
            // Hindi
            Locales("हिन्दी (Hindi)", "hi-IN"),
            // German
            Locales("Deutsch (German)", "de-DE"),
            // French
            Locales("Français (French)", "fr-FR"),
            // Vietnamese
            Locales("Tiếng Việt (Vietnamese)", "vi-VN"),
            // Indonesian
            Locales("Bahasa Indonesia (Indonesian)", "id-ID"),
            // Japanese
            Locales("日本語 (Japanese)", "ja-JP"),
            // Polish
            Locales("Polski (Polish)", "pl-PL"),
    )

    fun getAppLocale(): Locale {
        return synchronized(this) {
            appLocale
        }
    }

    fun setAppLocale(value: Locale) {
        synchronized(this) {
            appLocale = value
        }
    }

    fun Resources.isRTL(): Boolean {
        return configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

    fun isAppRussianLocale(): Boolean {
        return getSystemLanguageCode() in russianLocale
                || getAppLocale().language in russianLocale
    }
}
