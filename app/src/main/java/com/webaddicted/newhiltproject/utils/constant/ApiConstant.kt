package com.webaddicted.newhiltproject.utils.constant

/**
* Author : Deepak Sharma(Webaddicted)
* Email : techtamper@gmail.com
* Profile : https://github.com/webaddicted
*/
object ApiConstant {
      const val API_TIME_OUT: Long = 10
      const val AWS_SURVEY_URL =
        "http://ec2-13-127-231-121.ap-south-1.compute.amazonaws.com:5020/CEAT-Image-Analytics"
      const val DB_NAME = "force_tab.db"
      const val DB_VERSION = 1

      enum class DependentPicklistType(val value: String) {
            DISTRICT("District"),
            TOWN("Town"),
            SALES_ROUTE("SalesRoute"),
            DELIVERY_ROUTE("DeliveryRoute"),
          }

      enum class BeatType(val value: String) {
            TODAY_BEAT("Today Beat"),
            OTHER_BEAT("Other Beat"),
          }

      const val ALL_PRODUCT_LIST = "allproductlist"
      const val BEAT = "getVisitBeatDetails"
      const val TTF_MAPPING = "ttfMapping"

      //youtube key
      const val DEVELOPER_KEY = "AIzaSyD8rT6WgGYkHXQokYvnvIYB5ekhq-cPEzI"
      const val SELECT_OPTION = "selectoption"
      const val DATA_TABLE = "dataTable"
      const val SKU_VALUES = "SKUValues"
      const val DEPENDENT_PICKLIST = "dependentPicklist"

      const val BANK_DETAILS = "bankDetails"
      const val SURVEY_REQUEST = "surveyRequest"
      const val CREATE_FEEDBACK = "createFeedback"
      const val UPDATE_LOCATION = "updateLocationDMS"
      const val CHECKIN_DMS = "checkInDMS"
      const val DASHBOARD_DMS = "dashboardDMS"

      const val SUB_DEALER_PICKLIST = "subdealerPicklist"
      const val SUBMIT_ORDER = "submitorder"
      const val CREATE_ORDER = "createorder"
      const val GET_SUBDEALER_MODULE_STATUS = "getSubdealerModuleStatus"
      const val CREATE_COLLECTION = "createCollection"
      const val VALIDATE_OTP = "validateOTP"
      const val GENERATE_OTP = "generateOTP"
      const val RESENT_OTP = "resendOTP"
      const val SUBDEALER_CHANGES = "Subdealer changes"
      const val SUBDEALER_LEAD_CREATION = "subDealerLeadCreation"

      const val TODAY_BEAT_TABLE = "TodayBeats"
      const val MTD_DASHBOARD = "mtdDashboard"
      const val DAY_SUMMARY = "daySummary"
      const val SALESMAN_DASHBOARD_SUMMARY = "salesmanDashboardSummary"
      const val L3M_RETAILER = "l3mRetailer"
      const val RETAILER_CREDIT = "retailerCredit"
      const val ORDER_LIST = "orderList"
      const val ORDER_INFO = "orderInfo"




}