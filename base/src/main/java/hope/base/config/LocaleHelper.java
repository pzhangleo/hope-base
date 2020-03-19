package hope.base.config;

import android.text.TextUtils;

import java.util.Locale;

import hope.base.Constants;

/**
 * 多语言工具类
 */
public class LocaleHelper {

//    public static void onCreate(Activity context) {
//        setLocale(context, getPersistedData());
//    }
//
//    public static Locale getLocale() {
//        return getPersistedData();
//    }

    public static String getLanguage() {
        return Locale.getDefault().toString();
    }

    public static String getCountry() {
        if (TextUtils.isEmpty(Constants.NETWORKCOUNTRYISO)) {
            return Locale.getDefault().getCountry();
        } else {
            return Constants.NETWORKCOUNTRYISO;
        }
    }

    /**
     * 获取Sim卡或手机设置的国家代码
     *
     * @return
     */
    public static String getSimOrTelephoneCountry() {
        if (TextUtils.isEmpty(Constants.SIMCOUNTRYISO)) {
            return getCountry();
        } else {
            return Constants.SIMCOUNTRYISO;
        }
    }

//    public static void followSystem(Activity context) {
//    ConfigUtils.remove(PrefConstants.PREF_SELECTED_LANGUAGE_KEY);
//    ConfigUtils.remove(PrefConstants.PREF_SELECTED_COUNTRY_KEY);
//    updateResources(context, getLocale());
//}
//
//    /**
//     * 选择语言时，使用该方法设置语言
//     * @param context
//     * @param language 需要设置的语言
//     */
//    public static void setLocale(Activity context, Locale locale) {
//        if (locale == null) {
//            return;
//        }
//        persist(locale.getLanguage(), locale.getCountry());
//        updateResources(context, locale);
//    }
//
//    private static Locale getPersistedData() {
//        String language = ConfigUtils.getString(PrefConstants.PREF_SELECTED_LANGUAGE_KEY, Locale.getDefault().getLanguage());
//        String country = ConfigUtils.getString(PrefConstants.PREF_SELECTED_COUNTRY_KEY, Locale.getDefault().getCountry());
//        return new Locale(language, country);
//    }
//
//    private static void persist(String language, String country) {
//        ConfigUtils.putString(PrefConstants.PREF_SELECTED_LANGUAGE_KEY, language);
//        ConfigUtils.putString(PrefConstants.PREF_SELECTED_COUNTRY_KEY, country);
//    }
//
//    private static void updateResources(Activity context, Locale locale) {
////        Locale.setDefault(locale);//如果调用setDefault，则会改变默认语言，造成无法切换回跟随系统
//
//        Resources resources = context.getResources();
//
//        Configuration configuration = new Configuration(resources.getConfiguration());
//        configuration.locale = locale;
//
//        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//    }
}
