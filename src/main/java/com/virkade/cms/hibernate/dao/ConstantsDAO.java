package com.virkade.cms.hibernate.dao;

public class ConstantsDAO {
	
	//common constants
	public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S"; // "2020-06-20 23:00:00"
	public static final String SYSTEM = "system";
	public static final String TYPE_FIELD = "type";
	public static final String NAME_FIELD = "name";
	public static final String CODE_FIELD = "code";
	public static final String ENABLED_FIELD = "enabled";
	
	//statusDAO
	public static final String ACTIVE_CODE = "ACTV";
	public static final String INACTIVE_CODE = "INACTV";
	public static final String ACTIVE_NAME = "Active";
	public static final String INACTIVE_NAME = "InActive";
	
	//userDAO
	public static final String FIRST_NAME_FIELD = "firstName";
	public static final String LAST_NAME_FIELD = "lastName";
	public static final String USER_FIELD = "user";
	public static final String USER_NAME_FIELD = "username";
	public static final String USERID_FIELD = "userId";
	public static final String EMAIL_ADDRESS_FIELD = "emailAddress";
	public static final String GUEST_USER_NAME = "guest";
	public static final String OWNER_USER_NAME = "VirkadeExperience";
	public static final String GUEST_EMAIL_ADDRESS = "virkadeexperience@gmail.com";
	public static final String DEFAULT_OWNER_PASSWORD = "Reid.Zac36";

	//typeDAO
	public static final String ADMIN_CODE = "ADMN";
	public static final String PROSPECT_CODE = "PRSP";
	public static final String CUSTOMER_CODE = "CSTMR";
	public static final String GUEST_CODE = "GST";
	public static final String PHYSICAL_ADDRESS = "PHYSCL_ADRS";
	public static final String MOBILE_PHONE = "MBLE_PHNE";
	public static final String HOME_PHONE = "HME_PHNE";
	public static final String TYPEID_FIELD = "typeId";
	public static final String GENERAL_COMMENT = "GNRL_CMNT";
	public static final String CONDITION_COMMENT = "CNDTN_CMNT";
	public static final String TERMS_CONDITIONS = "TRMS_CNDTN";
	public static final String LIMITED_LIABLE = "LTD_LBLE";
	
	//stateDAO
	public static final String CODE_ARKANSAS = "AR";
	public static final String NAME_ARKANSAS = "Arkansas";
	public static final String STATE_CODE_FIELD = "stateCode";
	public static final String STATEID_FIELD = "stateId";
	
	//regionDAO
	public static final String NAME_AMERICAS = "Americas";
	public static final String CODE_AMERICAS = "AMS";
	public static final String REGION_CODE_FIELD = "regionCode";
	
	//locationDAO
	public static final String ORIGINAL_LOCATION_NAME = "VirKade Prime";
	public static final String LOCATION_ID_FIELD = "locationId";
	
	//countryDAO
	public static final String NAME_UNITEDSTATES = "United States";
	public static final String A2_UNITEDSTATES = "US";
	public static final String A3_UNITEDSTATES = "USA";
	public static final String A2_FIELD = "a2";
	
	//addressDAO
	public static final String STREET_FIELD = "street";
	public static final String CITY_FIELD = "city";
	public static final String APT_FIELD = "apt";
	public static final String UNIT_FIELD = "unit";
	public static final String STATE_FIELD = "state";
	public static final String ZIP_FIELD = "postalCode";
	public static final String ADDRESS_ID = "addressId";
	public static final String ADDRESS = "address";
	
	//phoneDAO
	public static final String PHONE_COUNTRY_FIELD = "countryCode";
	public static final String NUMBER_FIELD = "number";
	public static final String COMMENT_CONTENT_FIELD = "commentContent";
	
	//activityDAO
	public static final String DEFAULT_ACTIVITY_NAME = "Viveport";
	public static final String ACTIVITY_ID_FIELD = "activityId";
	
	//operatingHoursDAO
	public static final String START_AT_FIELD = "startAt";
	public static final String END_AT_FIELD = "endAt";
	public static final String OPERATING_DATE_FIELD = "operatingDate";
	
	//sessionDAO
	public static final String START_DATE_FIELD = "startDate";
	public static final String END_DATE_FIELD = "endDate";
	public static final String LOCATION_FIELD = "location";
	public static final String ACTIVITY_FIELD = "activity";
	public static final String ACTIVITYID_FIELD = "activityid";
	public static final String LOCATIONID_FIELD = "locationid";
	public static final String PAYED_FIELD = "payed";
	
	//documentDAO
	public static final String DEFAULT_TC_TITLE = "tandc";
	public static final String DEFAULT_LIAB_TITLE = "liabilitywaiver";
	public static final float DEFAULT_DOC_VERSION = 1.0f;
	public static final String DEFAULT_DOC_SEP = "-v";
	public static final String DEFAULT_DOC_EXT = ".txt";
	public static final String TITLE_FIELD = "title";
	public static final String VERSION_FIELD = "version";

	//legalDAO
	public static final String EXPIRE_DATE = "expireDate";
}
