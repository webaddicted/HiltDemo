package com.webaddicted.newhiltproject.utils.constant

object AppConstant {

    //    val SUB_DEALER_PICKLIST:
    const val SPLASH_DELAY: Long = 3000

    //menu options
    enum class MenuItem(val value: String) {
        HOME("Home"),
        NEW_OUTLET("New Outlet"),
        VISIT("Visit"),
        PRICE_VISIBILITY("Price List"),
        E_CATALOGUE("E-Catalogue"),
    }

    enum class HomeItem(val value: String) {
        MTD_DASHBOARD("MTD Dashboard"),
        RETAILERS_L3M("Retailers L3M"),
        RETAILER_CREDIT("Retailer Credit"),
        L3M_TOP_RETAILERS("L3M Top Retailers"),
        CATEGORY_WISE_QUANTITY("Category Wise Quantity"),
        RETAILERS_MONTHLY_SALES("Retailers Monthly Sales"),
        DAY_SUMMARY("Day Summary")
    }

    enum class VisitDetailsItem(val value: String) {
        ORDER_BOOKING("Order Booking"),
        SALES_RETURN("Sales Return"),
        COLLECTION("Collection"),
        SURVEY("Survey"),
        OUTLET_DASHBOARD("Outlet Dashboard"),
        UPDATE_LOCATION("Update Location"),
        CHECK_OUT("Checkin Without Capturing Order"),
        FEEDBACK_REMARK("Remarks/Feedback"),
        NAVIGATION("Navigation"),
        BADGE("Badge"),
        ORDER_HISTORY("Order History")
    }

    //  <item>Select a language</item>
//  <item>English</item>
//  <item>Polish</item>
//  <item>Spanish</item>
//  <item>Italian</item>
    enum class SelectableLanguage(val value: String) {
        ENGLISH("English"),
        POLISH("Polish"),
        SPANISH("Spanish"),
        ITALIAN("Italian")
    }

    enum class AccountType(val value: String) {
        MCHANNIC("Mechanic"),
        SUBDEALER("Sub-Dealer"),
    }

    enum class OrderCategory(val value: String) {
        ALL("ALL"),
        MC_TT("MC TT"),
        MC_TL("MC TL"),
        SC_TT("SC TT"),
        SC_TL("SC TL"),
        PCR("PCR"),
        UVR("UVR"),
        LM("LM"),
        LCV("LCV"),
        PKD_TUBE("PKD Tube"),
        TBB("TBB"),
        TBR("TBR"),
    }

    enum class VisitActionTab(val value: String) {
        PENDING("Pending"),
        COMPLETE("Complete")
    }

    enum class PriceVisiblityTab(val value: String) {
        POST_TAX("Post-Tax"),
        PRE_TAX("Pre-Tax")
    }

    enum class ECatalogueTab(val value: String) {
        PASSENGER("Passenger"),
        COMMERCE("Commerce")
    }

    enum class OutletDashboardTab(val value: String) {
        STORE_SUMMARY("Store Summary"),
        ORDER_SUMMARY("Order Summary"),
        TYPICAL_ADDRESS("Typical Orders"),
    }

    enum class DialogType(val value: Int) {
        PRICE_CATEGORY(2000),
        SKU(2001),
        STATE(2002),
        DISTRICT(2003),
        TOWN(2004),
        TCS_RATE(2005),
        TDS_RATE(2006),
        PARENT(2007),
        CREDIT_SALES(2008),
        SALESMAN_NAME(2009),
        SALES_ROUTE(2010),
        DELIVERY_ROUTE(2011),
        DELIVERY_BOY(2012),
        CATEGORY(2013),
        CHANNEL(2014),
        GROUP(2015),
        CLASS(2016),
        PHOTO_ID_1(2017),
        PHOTO_ID_2(2018),
        PREFERRED_LANGUAGE(2019),
        OTHER_BUSINESS(2020),
        WEEKLY_OFF(2021)
    }

    enum class WeekOff(var value: String) {
        SUNDAY("Sunday"),
        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday"),
        SATURDAY("Saturday")
    }

    enum class UploadImageType(var value: String) {
        SHOP_IMAGE("Shop Image"),
        DOC_IMG1("Doc Img1"),
        DOC_IMG2("Doc Img2"),
    }

    enum class DialogCategoryType(val value: String) {
        PRICE_CATEGORY("priceCategory"),
        SKU("SKU")
    }

    enum class ECatalogueVehicleType(val value: String) {
        MOTORCYCLE("MotorCycle"),
        SCOOTER("Scooter"),
        CAR("Car"),
        SUV("SUV"),
        TRUCK_BUS("TruckAndBus"),
        LAST_MILE("LastMile"),
        LCV("LCV"),
        FARM("Farm"),
    }

    enum class PhotoIdType(val value: String) {
        VOTER_ID("Voter Id"),
        AADHAR_CARD("Aadhar Card"),
        DRIVING_LICENSE("Driving License"),
        PASSPORT("Passport"),
    }

    const val BACK_STACK_ROOT_TAG = "root_fragment"
    const val DISPLAY_DATE_FORMAT = "dd-MMM-yyyy"
    const val SERVER_DATE_FORMAT = "yyyy-MM-dd"
    const val TIME_AM_PM_FORMAT = "hh:mm aaa"
    const val TIME_FORMAT = "hh:mm"
    const val DATE_TIME_FORMAT = "dd-MMM-yyyy hh:mm"
    const val IMG_FILE_NAME_FORMAT = "yyyyMMdd_HHmmss"
    const val IMG_FILE_EXT = "jpeg"
    const val IMGS_DIR = "app_imgs_dir"


}
