package vn.flearn.app.card.utils;

import android.view.ViewGroup;

public class Constant {

    public static final boolean isDebug = false;
    public static final String APP_PACKAGE_NAME = "vn.flearn.app.card";

    public static final String NETWORK_CRYPT_KEY = "bdcc45fba7d9865d";
    public static final String NETWORK_CRYPT_IV = "ccfd810a95af4d9051acc0136b331500";

    public static final int FP = ViewGroup.LayoutParams.FILL_PARENT;
    public static final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    /*
     * Identify screen id
     */
    public static final int IDENTIFY_SCREEN_SPLASH = 1;
    public static final int IDENTIFY_SCREEN_DASHBOARD = 2;
    public static final int IDENTIFY_SCREEN_SUBCOURSE = 3;
    public static final int IDENTIFY_SCREEN_PAYMENT_METHOD = 4;
    public static final int IDENTIFY_SCREEN_WORD_SLIDE = 5;
    public static final int IDENTIFY_SCREEN_PROMOTION = 6;
    public static final int IDENTIFY_SCREEN_PAYMENT_DETAIL = 7;

    /*
     * API
     */
    public static final String API_DOMAIN = "http://128.199.129.42:8014";
    public static final String DOMAIN = "128.199.129.42:8014";
    public static final String API_USER_REGISTER =
            API_DOMAIN + "/index.php/api/authenticate/userRegist/format/json";
    public static final String API_MSERVICE_GET_COURSE_INIT =
            API_DOMAIN + "/index.php/api/authenticate/courseInit/format/json";
    public static final String PAGE_BAOKIM =
            API_DOMAIN + "/index.php/page/baokim?uuid=";
    public static final String PAGE_ONEPAY =
            API_DOMAIN + "/index.php/onepay/index?uuid=";
    public static final String PAGE_CONTACTUS =
            API_DOMAIN + "/index.php/page/contact";
    public static final String API_MSERVICE_GET_WORD =
            API_DOMAIN + "/index.php/api/authenticate/word/format/json";
    public static final String API_MSERVICE_GET_UCOURSE =
            API_DOMAIN + "/index.php/api/authenticate/ucourse/format/json";
    public static final String API_MSERVICE_ACTIVE_PC =
            API_DOMAIN + "/index.php/api/promotion/takePromotionCode/format/json";
    public static final String API_MSERVICE_ACTIVE_PACKAGE =
            API_DOMAIN + "/index.php/api/promotion/activePackage/format/json";
    public static final String FB_FAN_PAGE =
            "https://www.facebook.com/pages/FCard-H%E1%BB%8Dc-t%E1%BB%AB-v%E1%BB%B1ng-ti%E1%BA%BFng-Anh/301410736687582?ref=hl";

    /**
     * Json tag
     */
    public static final String JSON_TAG_COURSE_ITEMS = "Courses";
    public static final String JSON_TAG_WORD_ITEMS = "Words";
    public static final String JSON_TAG_CODE_SUCCESS = "is_successful";
    public static final String JSON_TAG_ALREADY_ACTIVE = "already_active";
    public static final String JSON_TAG_CODE_DATA = "data";
    public static final String JSON_TAG_CODE_ACTIVATED = "IsActivated";
    public static final String JSON_TAG_ERROR_MESSAGE = "ErrorMessage";

    public static final String SQL_NEWLINE = "\n";

    public static final String BUNDLE_EXTRA_COURSE_NAME = "course_name";
    public static final String BUNDLE_EXTRA_COURSE_DESCRIPTION = "course_description";
    public static final String BUNDLE_EXTRA_COURSE_ID = "course_id";
    public static final String BUNDLE_EXTRA_COURSE_PRICE = "course_price";
    public static final String BUNDLE_EXTRA_METHOD_ID = "method_id";
    public static final String BUNDLE_EXTRA_METHOD_NAME = "method_name";
    public static final String BUNDLE_EXTRA_SUB_COURSE_POS = "sub_course_pos";
    public static final String BUNDLE_EXTRA_SUB_NUM_LIMIT = "sub_num_limit";
    public static final String BUNDLE_EXTRA_WORD_TYPE = "word_type";

    public static final int INTENT_REQUEST_CODE_COURSE = 100;
    public static final int INTENT_REQUEST_WORD_REVIEW = 101;
    public static final int MAX_SUB_COURSE_LIMIT = 100;
    public static final String APP_SETTING_NAME = "appSetting";
    public static final long FLIP_TRANSITION_DURATION = 200;
    public static final long WAITING_DURATION = 100;

    public static final String WORD_COLOR_DONE = "done";
    public static final String WORD_COLOR_DIFFICULT = "difficult";

    public static final String JSON_TAG_ERROR = "error";
    public static final String JSON_TAG_UCOURSE = "ucourse";
    public static final String JSON_TAG_UWORDS = "uwords";

    public static final String FIRST_RUN = "first_run";

    public static final int MENU_PACKAGES = 0;
    public static final int MENU_LEARNED = 1;
    public static final int MENU_HARD = 2;

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String SOUND = "setting_sound";
    public static final String SUBCOURSE_PACKAGE = "subcourse_package";

    public enum ActivityType {
        PACKAGES, LEARNED, HARD, SHARE, VOTE, FEEDBACK, RESET, HELP, COMMUNITY, SETTINGS, ERROR, SUBCOURSE
    }
}
