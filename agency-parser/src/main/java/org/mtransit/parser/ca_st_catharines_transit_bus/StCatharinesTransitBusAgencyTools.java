package org.mtransit.parser.ca_st_catharines_transit_bus;

import static org.mtransit.commons.RegexUtils.DIGITS;
import static org.mtransit.parser.StringUtils.EMPTY;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mtransit.commons.CharUtils;
import org.mtransit.commons.CleanUtils;
import org.mtransit.commons.StringUtils;
import org.mtransit.parser.DefaultAgencyTools;
import org.mtransit.parser.MTLog;
import org.mtransit.parser.gtfs.data.GAgency;
import org.mtransit.parser.gtfs.data.GRoute;
import org.mtransit.parser.gtfs.data.GStop;
import org.mtransit.parser.mt.data.MAgency;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// https://niagaraopendata.ca/dataset/niagara-region-transit-gtfs
// https://maps.niagararegion.ca/googletransit/NiagaraRegionTransit.zip
// https://niagaraopendata.ca/dataset/1a1b885e-1a86-415d-99aa-6803a2d8f178/resource/f7dbcaed-f31a-435e-8146-b0efff0b8eb8/download/gtfs.zip
public class StCatharinesTransitBusAgencyTools extends DefaultAgencyTools {

	public static void main(@NotNull String[] args) {
		new StCatharinesTransitBusAgencyTools().start(args);
	}

	@Nullable
	@Override
	public List<Locale> getSupportedLanguages() {
		return LANG_EN;
	}

	@Override
	public boolean defaultExcludeEnabled() {
		return true;
	}

	@NotNull
	@Override
	public String getAgencyName() {
		return "St Catharines Transit";
	}

	private static final String ST_CATHARINES_TRANSIT_COMMISSION = "St. Catharines Transit Commission";

	@Override
	public boolean excludeAgency(@NotNull GAgency gAgency) {
		//noinspection deprecation
		final String agencyId = gAgency.getAgencyId();
		if (!agencyId.contains(ST_CATHARINES_TRANSIT_COMMISSION)
				&& !agencyId.contains("AllNRT_")
				&& !agencyId.equals("1")) {
			return EXCLUDE;
		}
		return super.excludeAgency(gAgency);
	}

	@Override
	public boolean excludeRoute(@NotNull GRoute gRoute) {
		//noinspection deprecation
		final String agencyId = gRoute.getAgencyIdOrDefault();
		if (!agencyId.contains(ST_CATHARINES_TRANSIT_COMMISSION)
				&& !agencyId.contains("AllNRT_")
				&& !agencyId.equals("1")) {
			return EXCLUDE;
		}
		if (agencyId.contains("AllNRT_")
				|| agencyId.equals("1")) {
			final String rsnS = gRoute.getRouteShortName();
			if (!CharUtils.isDigitsOnly(rsnS)) {
				return EXCLUDE;
			}
			final int rsn = Integer.parseInt(rsnS);
			if (rsn < 300 || rsn > 499) {
				return EXCLUDE;
			}
		}
		if (gRoute.getRouteLongNameOrDefault().startsWith("IMT - ")) {
			return EXCLUDE; // Niagara Region Transit
		}
		return super.excludeRoute(gRoute);
	}

	@Override
	public boolean excludeStop(@NotNull GStop gStop) {
		//noinspection deprecation
		if (IGNORE_STOP_ID.matcher(gStop.getStopId()).find()) {
			return true; // other agency
		}
		return super.excludeStop(gStop);
	}

	@NotNull
	@Override
	public Integer getAgencyRouteType() {
		return MAgency.ROUTE_TYPE_BUS;
	}

	@Override
	public boolean defaultRouteIdEnabled() {
		return true;
	}

	@Override
	public boolean useRouteShortNameForRouteId() {
		return true;
	}

	@Override
	public boolean defaultRouteLongNameEnabled() {
		return true;
	}

	@NotNull
	@Override
	public String cleanRouteLongName(@NotNull String routeLongName) {
		routeLongName = CleanUtils.cleanStreetTypes(routeLongName);
		return CleanUtils.cleanLabel(routeLongName);
	}

	private static final String AGENCY_COLOR_GREEN = "008E1A"; // GREEN (from web site CSS)

	private static final String AGENCY_COLOR = AGENCY_COLOR_GREEN;

	@Override
	public boolean defaultAgencyColorEnabled() {
		return true;
	}

	@NotNull
	@Override
	public String getAgencyColor() {
		return AGENCY_COLOR;
	}

	@SuppressWarnings("DuplicateBranchesInSwitch")
	@Nullable
	@Override
	public String provideMissingRouteColor(@NotNull GRoute gRoute) {
		final int rsn = Integer.parseInt(gRoute.getRouteShortName());
		switch (rsn) {
		// @formatter:off
		case 26: return "ED1B24";
		case 27: return "ED1B24";
		case 301: return "ED1B24";
		case 302: return "00A650";
		case 303: return "ED008C";
		case 304: return "F68713";
		case 305: return "8E1890";
		case 306: return "ED1B24";
		case 307: return "4CC6F5";
		case 308: return "48A1AF";
		case 309: return "48A1AF";
		case 310: return "24528E";
		case 311: return "0A8ED8";
		case 312: return "00A650";
		case 314: return "C81070";
		case 315: return "00823C";
		case 316: return "ED1B24";
		case 317: return "8E1890";
		case 318: return "00823C";
		case 320: return "485683";
		case 321: return "486762";
		case 322: return "F25373";
		case 323: return "8E1890";
		case 324: return "0060AD"; // BLUE
		case 325: return "ED1B24";
		case 326: return "ED1B24";
		case 327: return "ED1B24";
		case 328: return "92D050";
		case 329: return "3A9CB9";
		case 330: return "005FAC";
		case 331: return "00A551";
		case 332: return "166FC1";
		case 333: return "166FC1";
		case 335: return "4CA392";
		case 336: return "E24E26";
		case 337: return "F58345"; // FLAMENCO
		case 401: return "EE1C25"; // RED
		case 402: return "0072BB"; // BLUE
		case 404: return "00ADEF"; // LIGHT BLUE
		case 406: return "EE1C25"; // RED
		case 408: return "00A652"; // GREEN
		case 409: return "A88B6B"; // LIGHT BROWN
		case 410: return "05558A"; // DRAK BLUE
		case 412: return "0072BB"; // BLUE
		case 414: return "C81c6E"; // PURPLE
		case 415: return "008744"; // GREEN
		case 416: return "EE1C25"; // RED
		case 417: return "A88B6B"; // LIGHT BROWN
		case 418: return "008744"; // GREEN
		case 420: return "485E87"; // BLUE-ISH
		case 421: return "486F6E"; // GREEN-ISH
		case 423: return "7570B3"; // LIGHT PURPLE // ?
		case 424: return "0060AD"; // BLUE
		case 425: return "B3B3B3"; // WHITE
		case 428: return "A3CE62"; // LIGHT GREEN
		case 431: return "00A652"; // GREEN
		case 432: return "ED008E"; // PINK
		case 435: return "4FA491";
		case 436: return "F58345"; // ORANGE
		case 437: return "F58345"; // FLAMENCO
		// @formatter:on
		default:
			throw new MTLog.Fatal("Unexpected route color for %s!", gRoute);
		}
	}

	private static final Pattern STARTS_WITH_STC_A00_ = Pattern.compile( //
			"((^)((allnrt|stc)_[a-z]{1,3}\\d{2,4}(_)?([A-Z]{3}(stop))?(stop)?))", //
			Pattern.CASE_INSENSITIVE);

	@NotNull
	@Override
	public String cleanStopOriginalId(@NotNull String gStopId) {
		gStopId = STARTS_WITH_STC_A00_.matcher(gStopId).replaceAll(EMPTY);
		return gStopId;
	}

	@Override
	public boolean directionFinderEnabled() {
		return true;
	}

	private static final Pattern STARTS_WITH_RSN_RLN = Pattern.compile("(^[0-9]{1,3}[A-Z]? ((\\w+[.]? )+- )*)", Pattern.CASE_INSENSITIVE);

	private static final Pattern STARTS_WITH_RLN_DASH = Pattern.compile("(^([^\\-]+- )+)", Pattern.CASE_INSENSITIVE);

	private static final Pattern CENTER_ = CleanUtils.cleanWords("cent[r]?");
	private static final String CENTER_REPLACEMENT = CleanUtils.cleanWordsReplacement("Center");

	@NotNull
	@Override
	public String cleanTripHeadsign(@NotNull String tripHeadsign) {
		tripHeadsign = CleanUtils.toLowerCaseUpperCaseWords(getFirstLanguageNN(), tripHeadsign, getIgnoredWords());
		tripHeadsign = STARTS_WITH_RSN_RLN.matcher(tripHeadsign).replaceAll(EMPTY);
		tripHeadsign = STARTS_WITH_RLN_DASH.matcher(tripHeadsign).replaceAll(EMPTY);
		tripHeadsign = CleanUtils.keepTo(tripHeadsign);
		tripHeadsign = CENTER_.matcher(tripHeadsign).replaceAll(CENTER_REPLACEMENT);
		tripHeadsign = CleanUtils.cleanBounds(tripHeadsign);
		tripHeadsign = CleanUtils.cleanStreetTypes(tripHeadsign);
		return CleanUtils.cleanLabel(tripHeadsign);
	}

	private static final Pattern AND_NOT = Pattern.compile("(&)", Pattern.CASE_INSENSITIVE);
	private static final String AND_NOT_REPLACEMENT = "and";

	private static final Pattern AT = CleanUtils.cleanWords(
			"across fr[.]?", "after", "at",
			"before", "between both", "between",
			"east of", "in front of", "north of",
			"opp", "south of", "west of");
	private static final String AT_REPLACEMENT = CleanUtils.cleanWordsReplacement("/");

	private static final Pattern ENDS_WITH = Pattern.compile("(([&/\\-])\\W*$)", Pattern.CASE_INSENSITIVE);

	private String[] getIgnoredWords() {
		return new String[]{
				"DSBN", "GO", "NHS", "TRW", "YMCA",
		};
	}

	@NotNull
	@Override
	public String cleanStopName(@NotNull String gStopName) {
		gStopName = CleanUtils.toLowerCaseUpperCaseWords(getFirstLanguageNN(), gStopName, getIgnoredWords());
		gStopName = AND_NOT.matcher(gStopName).replaceAll(AND_NOT_REPLACEMENT); // fix Alex&ra
		gStopName = CleanUtils.CLEAN_AND.matcher(gStopName).replaceAll(CleanUtils.CLEAN_AND_REPLACEMENT);
		gStopName = AT.matcher(gStopName).replaceAll(AT_REPLACEMENT);
		gStopName = CleanUtils.cleanBounds(gStopName);
		gStopName = CleanUtils.cleanNumbers(gStopName);
		gStopName = CleanUtils.cleanStreetTypes(gStopName);
		gStopName = ENDS_WITH.matcher(gStopName).replaceAll(EMPTY);
		return CleanUtils.cleanLabel(gStopName);
	}

	private static final String ZERO_0 = "0";

	@NotNull
	@Override
	public String getStopCode(@NotNull GStop gStop) { // used by REAL-TIME API
		String stopCode = gStop.getStopCode();
		if (stopCode.isEmpty()
				|| ZERO_0.equals(stopCode)) {
			//noinspection deprecation
			stopCode = gStop.getStopId();
		}
		stopCode = STARTS_WITH_STC_A00_.matcher(stopCode).replaceAll(EMPTY);
		if (StringUtils.isEmpty(stopCode)) {
			throw new MTLog.Fatal("Unexpected stop code for %s!", gStop);
		}
		if (CharUtils.isDigitsOnly(stopCode)) {
			stopCode = Integer.valueOf(stopCode).toString(); // remove leading 0s
		}
		return stopCode;
	}

	private static final Pattern IGNORE_STOP_ID = Pattern.compile("(^(S_FE|NF|PC|WE))", Pattern.CASE_INSENSITIVE);

	private static final String ABBY = "Abby";
	private static final String ALL = "All";
	private static final String ALNBG = "Alnbg";
	private static final String BRCK = "Brck";
	private static final String CLRK = "Clrk";
	private static final String CMGS = "Cmgs";
	private static final String CNFD = "Cnfd";
	private static final String CRMT = "Crmt";
	private static final String DNKL = "Dnkl";
	private static final String DNTN = "Dntn";
	private static final String FARV = "Farv";
	private static final String FRTH = "Frth";
	private static final String GEN = "Gen";
	private static final String GLND = "Glnd";
	private static final String GRDG = "Grdg";
	private static final String HAIG = "Haig";
	private static final String HRTZ = "Hrtz";
	private static final String KEFR = "Kefr";
	private static final String LOCK = "Lock";
	private static final String LSHR = "Lshr";
	private static final String MAC_T = "MacT";
	private static final String NI_FLS = "NiFls";
	private static final String NW_GN = "NwGn";
	private static final String ONTARIO_ST = "Ontario St";
	private static final String ORMD = "Ormd";
	private static final String PEN = "Pen";
	private static final String QRVW = "Qrvw";
	private static final String RKWD = "Rkwd";
	private static final String SCMN = "Scmn";
	private static final String SCOT = "Scot";
	private static final String SRNG = "Srng";
	private static final String ST_P = "StP";
	private static final String ST_PW = "StPW";
	private static final String SULV = "Sulv";
	private static final String WAL = "Wal";
	private static final String WCTR = "Wctr";
	private static final String WEST = "West";
	private static final String WLDW = "Wldw";
	private static final String ARTH = "Arth";
	private static final String BCHN = "Bchn";
	private static final String BNTG = "Bntg";
	private static final String BRHL = "Brhl";
	private static final String CAMP = "Camp";
	private static final String CHUR = "Chur";
	private static final String CLR = "Clr";
	private static final String CMPS = "Cmps";
	private static final String CNTR = "Cntr";
	private static final String COLL = "Coll";
	private static final String COLR = "Colr";
	private static final String CONF = "Conf";
	private static final String CRLT = "Crlt";
	private static final String CUGA = "Cuga";
	private static final String ECHR = "Echr";
	private static final String FACR = "Facr";
	private static final String GENV = "Genv";
	private static final String GLMR = "Glmr";
	private static final String GLNR = "Glnr";
	private static final String GNDL = "Gndl";
	private static final String GRNT = "Grnt";
	private static final String HOSP = "Hosp";
	private static final String HP = "Hp";
	private static final String LAKE = "Lake";
	private static final String LINW = "Linw";
	private static final String LOUT = "Lout";
	private static final String LNHVN = "Lnhvn";
	private static final String LYN = "Lyn";
	private static final String MAIN = "Main";
	private static final String MALL = "Mall";
	private static final String MART = "Mart";
	private static final String MC_TB = "McTb";
	private static final String MERT = "Mert";
	private static final String MRDL = "Mrdl";
	private static final String MRTV = "Mrtv";
	private static final String NIAG = "Niag";
	private static final String OAKD = "Oakd";
	private static final String ONT = "Ont";
	private static final String PARK = "Park";
	private static final String PELM = "Pelm";
	private static final String QUEN = "Quen";
	private static final String QUNS = "Quns";
	private static final String RES = "Res";
	private static final String RICH = "Rich";
	private static final String ST_D = "StD";
	private static final String TERM = "Term";
	private static final String TOWP = "Towp";
	private static final String TWNL = "Twnl";
	private static final String TUPP = "Tupp";
	private static final String UNIV = "Univ";
	private static final String VINE = "Vine";
	private static final String VSKL = "Vskl";
	private static final String WDRW = "Wdrw";
	private static final String WLND = "Wlnd";
	private static final String WMBL = "Wmbl";

	private static final String CD = "CD";
	private static final String CRL = "CRL";
	private static final String GLI = "GLI";
	private static final String LKV = "LKV";
	private static final String LLI = "LLI";
	private static final String NOTL = "NOTL";
	private static final String PGL = "PGL";
	private static final String SCWE = "SCWE";

	private static final String WEL = "WEL";
	private static final String SWM = "SWM";
	private static final String PEN2 = "PEN";
	private static final String NFT = "NFT";
	private static final String DTT = "DTT";
	private static final String BAS = "BAS";
	private static final String BRU = "BRU";
	private static final String DAS = "DAS";
	private static final String FVM = "FVM";
	private static final String GLW = "GLW";
	private static final String LIG = "LIG";
	private static final String MIW = "MIW";
	private static final String QUP = "QUP";
	private static final String WSM = "WSM";

	@Override
	public int getStopId(@NotNull GStop gStop) {
		//noinspection deprecation
		final String stopId = gStop.getStopId();
		if (IGNORE_STOP_ID.matcher(stopId).find()) {
			return -1; // other agency
		}
		String stopCode = gStop.getStopCode();
		if (stopCode.isEmpty() || ZERO_0.equals(stopCode)) {
			stopCode = stopId;
		}
		stopCode = STARTS_WITH_STC_A00_.matcher(stopCode).replaceAll(EMPTY);
		if (stopCode.isEmpty()) {
			throw new MTLog.Fatal("Unexpected stop ID '%s' (%s)!", stopCode, gStop);
		}
		if (CharUtils.isDigitsOnly(stopCode)) {
			return Integer.parseInt(stopCode); // using stop code as stop ID
		}
		//noinspection IfCanBeSwitch // TO DO?
		if (stopCode.equals(DTT)) {
			return 100000;
		} else if (stopCode.equals(NFT)) {
			return 100001;
		} else if (stopCode.equals(PEN2)) {
			return 100002;
		} else if (stopCode.equals(SWM)) {
			return 100003;
		} else if (stopCode.equals(WEL)) {
			return 100004;
		} else if (stopCode.equals(BAS)) {
			return 100005;
		} else if (stopCode.equals(BRU)) {
			return 100006;
		} else if (stopCode.equals(DAS) || stopCode.equals(DAS + "0174")) {
			return 174;
		} else if (stopCode.equals(FVM)) {
			return 100008;
		} else if (stopCode.equals(GLW)) {
			return 100009;
		} else if (stopCode.equals(LIG)) {
			return 100010;
		} else if (stopCode.equals(QUP)) {
			return 100011;
		} else if (stopCode.equals(WSM)) {
			return 100012;
		} else if (stopCode.equals(MIW)) {
			return 100013;
		} else if (stopCode.equals("BIS")) {
			return 100014;
		} else if (stopCode.equals("BRR")) {
			return 100015;
		} else if (stopCode.equals("CER")) {
			return 100016;
		} else if (stopCode.equals("VIL")) {
			return 100017;
		} else if (stopCode.equals("STK")) {
			return 100018;
		} else if (stopCode.equals("MCS")) {
			return 100019;
		} else if (stopCode.equals("GDC")) {
			return 100020;
		} else if (stopCode.equals("WLC")) {
			return 100021;
		} else if (stopCode.equals("WAL")) {
			return 100022;
		} else if (stopCode.equals("RIC")) {
			return 100023;
		} else if (stopCode.equals("HOS")) {
			return 100024;
		} else if (stopCode.equals("LKO")) {
			return 100025;
		} else if (stopCode.equals("LKV")) {
			return 100026;
		} else if (stopCode.equals("GBH")) {
			return 100027;
		} else if (stopCode.equals("LLI")) {
			return 100028;
		} else if (stopCode.equals("LKN")) {
			return 100029;
		} else if (stopCode.equals("GLI")) {
			return 100030;
		} else if (stopCode.equals("SIR")) {
			return 100031;
		} else if (stopCode.equals("OSD")) {
			return 100032;
		} else if (stopCode.equals("DCA") || stopCode.equals("DCA0724")) {
			return 724;
		} else if (stopCode.equals("TLQ")) {
			return 100034;
		} else if (stopCode.equals("CTO")) {
			return 100035;
		} else if (stopCode.equals("MCL") || stopCode.equals("MCL0767")) {
			return 767;
		} else if (stopCode.equals("LKL")) {
			return 100037;
		} else if (stopCode.equals("LKG")) {
			return 100038;
		} else if (stopCode.equals("PGL")) {
			return 100039;
		} else if (stopCode.equals("CRL")) {
			return 100040;
		} else if (stopCode.equals("WIA") || stopCode.equals("WIA0421")) {
			return 421;
		} else if (stopCode.equals("CVI")) {
			return 100042;
		} else if (stopCode.equals("KAB")) {
			return 100043;
		} else if (stopCode.equals("OUT")) {
			return 100_044;
		} else if (stopCode.equals("ERM")) {
			return 100_045;
		} else if (stopCode.equals("WEN")) {
			return 100_046;
		} else if (stopCode.equals("WEC")) {
			return 100_047;
		} else if (stopCode.equals("PAP")) {
			return 100_048;
		} else if (stopCode.equals("SIP") || stopCode.equals("SIP1372")) {
			return 1372;
		} else if (stopCode.equals("CKE") || stopCode.equals("CKE1072")) {
			return 1072;
		} else if (stopCode.equals("GOT") || stopCode.equals("GOT1399")) {
			return 1399;
		}
		try {
			final Matcher matcher = DIGITS.matcher(stopCode);
			if (matcher.find()) {
				int digits = Integer.parseInt(matcher.group());
				if (stopCode.startsWith(CD)) {
					digits += 30000;
				} else if (stopCode.startsWith(CRL)) {
					digits += 40000;
				} else if (stopCode.startsWith(GLI)) {
					digits += 70000;
				} else if (stopCode.startsWith(LKV)) {
					digits += 120000;
				} else if (stopCode.startsWith(LLI)) {
					digits += 130000;
				} else if (stopCode.startsWith(NOTL)) {
					digits += 140000;
				} else if (stopCode.startsWith(PGL)) {
					digits += 160000;
				} else if (stopCode.startsWith(SCWE)) {
					digits += 190000;
				} else {
					throw new MTLog.Fatal("Unexpected stop ID (starts with digits) '%s' (%s)!", stopCode, gStop);
				}
				return digits;
			}
		} catch (Exception e) {
			throw new MTLog.Fatal(e, "Error while finding stop ID for '%s' (%s)!", stopCode, gStop);
		}
		int digits;
		if (stopCode.startsWith(ALNBG)) {
			digits = 100000;
		} else if (stopCode.startsWith(ARTH)) {
			digits = 110000;
		} else if (stopCode.startsWith(BNTG)) {
			digits = 200000;
		} else if (stopCode.startsWith(BRCK)) {
			digits = 210000;
		} else if (stopCode.startsWith(CLRK)) {
			digits = 300000;
		} else if (stopCode.startsWith(CMGS)) {
			digits = 310000;
		} else if (stopCode.startsWith(CNFD)) {
			digits = 320000;
		} else if (stopCode.startsWith(CRLT)) {
			digits = 330000;
		} else if (stopCode.startsWith(CRMT)) {
			digits = 340000;
		} else if (stopCode.startsWith(DNKL)) {
			digits = 400000;
		} else if (stopCode.startsWith(DNTN)) {
			digits = 410000;
		} else if (stopCode.startsWith(FARV)) {
			digits = 600000;
		} else if (stopCode.startsWith(FRTH)) {
			digits = 610000;
		} else if (stopCode.startsWith(GEN)) {
			digits = 700000;
		} else if (stopCode.startsWith(GENV)) {
			digits = 710000;
		} else if (stopCode.startsWith(GLND)) {
			digits = 720000;
		} else if (stopCode.startsWith(GNDL)) {
			digits = 7300000;
		} else if (stopCode.startsWith(GRDG)) {
			digits = 740000;
		} else if (stopCode.startsWith(GRNT)) {
			digits = 750000;
		} else if (stopCode.startsWith(HAIG)) {
			digits = 800000;
		} else if (stopCode.startsWith(HRTZ)) {
			digits = 810000;
		} else if (stopCode.startsWith(KEFR)) {
			digits = 1100000;
		} else if (stopCode.startsWith(LAKE)) {
			digits = 1200000;
		} else if (stopCode.startsWith(LOCK)) {
			digits = 1210000;
		} else if (stopCode.startsWith(LSHR)) {
			digits = 1220000;
		} else if (stopCode.startsWith(MAC_T)) {
			digits = 1300000;
		} else if (stopCode.startsWith(MERT)) {
			digits = 1310000;
		} else if (stopCode.startsWith(MRDL)) {
			digits = 1320000;
		} else if (stopCode.startsWith(NIAG)) {
			digits = 1400000;
		} else if (stopCode.startsWith(NI_FLS)) {
			digits = 1410000;
		} else if (stopCode.startsWith(NW_GN)) {
			digits = 1420000;
		} else if (stopCode.startsWith(ONT) || gStop.getStopName().startsWith(ONTARIO_ST)) {
			digits = 1500000;
		} else if (stopCode.startsWith(ORMD)) {
			digits = 1510000;
		} else if (stopCode.startsWith(PELM)) {
			digits = 1600000;
		} else if (stopCode.startsWith(PEN)) {
			digits = 1610000;
		} else if (stopCode.startsWith(QRVW)) {
			digits = 1700000;
		} else if (stopCode.startsWith(RICH)) {
			digits = 1800000;
		} else if (stopCode.startsWith(RKWD)) {
			digits = 1810000;
		} else if (stopCode.startsWith(SCMN)) {
			digits = 1900000;
		} else if (stopCode.startsWith(SCOT)) {
			digits = 1910000;
		} else if (stopCode.startsWith(SRNG)) {
			digits = 1920000;
		} else if (stopCode.startsWith(ST_D)) {
			digits = 1930000;
		} else if (stopCode.startsWith(ST_P)) {
			digits = 1940000;
		} else if (stopCode.startsWith(ST_PW)) {
			digits = 1950000;
		} else if (stopCode.startsWith(SULV)) {
			digits = 1960000;
		} else if (stopCode.startsWith(TWNL)) {
			digits = 2000000;
		} else if (stopCode.startsWith(VINE)) {
			digits = 2200000;
		} else if (stopCode.startsWith(VSKL)) {
			digits = 2210000;
		} else if (stopCode.startsWith(WAL)) {
			digits = 2300000;
		} else if (stopCode.startsWith(WCTR)) {
			digits = 2310000;
		} else if (stopCode.startsWith(WEST)) {
			digits = 2320000;
		} else if (stopCode.startsWith(WLDW)) {
			digits = 2330000;
		} else if (stopCode.startsWith(WLND)) {
			digits = 2340000;
		} else {
			throw new MTLog.Fatal("Unexpected stop ID (starts with) '%s' (%s)!", stopCode, gStop);
		}
		if (stopCode.endsWith(ABBY)) {
			digits += 100;
		} else if (stopCode.endsWith(ALL)) {
			digits += 101;
		} else if (stopCode.endsWith(ARTH)) {
			digits += 102;
		} else if (stopCode.endsWith(BCHN)) {
			digits += 200;
		} else if (stopCode.endsWith(BNTG)) {
			digits += 201;
		} else if (stopCode.endsWith(BRHL)) {
			digits += 202;
		} else if (stopCode.endsWith(CAMP)) {
			digits += 300;
		} else if (stopCode.endsWith(CHUR)) {
			digits += 301;
		} else if (stopCode.endsWith(CLR)) {
			digits += 302;
		} else if (stopCode.endsWith(CMPS)) {
			digits += 303;
		} else if (stopCode.endsWith(CNTR)) {
			digits += 304;
		} else if (stopCode.endsWith(COLL)) {
			digits += 305;
		} else if (stopCode.endsWith(COLR)) {
			digits += 306;
		} else if (stopCode.endsWith(CONF)) {
			digits += 307;
		} else if (stopCode.endsWith(CRLT)) {
			digits += 308;
		} else if (stopCode.endsWith(CUGA)) {
			digits += 309;
		} else if (stopCode.endsWith(ECHR)) {
			digits += 500;
		} else if (stopCode.endsWith(FACR)) {
			digits += 600;
		} else if (stopCode.endsWith(GENV)) {
			digits += 700;
		} else if (stopCode.endsWith(GLMR)) {
			digits += 701;
		} else if (stopCode.endsWith(GLNR)) {
			digits += 702;
		} else if (stopCode.endsWith(GNDL)) {
			digits += 703;
		} else if (stopCode.endsWith(GRNT)) {
			digits += 704;
		} else if (stopCode.endsWith(HOSP)) {
			digits += 800;
		} else if (stopCode.endsWith(HP)) {
			digits += 801;
		} else if (stopCode.endsWith(LAKE)) {
			digits += 1200;
		} else if (stopCode.endsWith(LINW)) {
			digits += 1201;
		} else if (stopCode.endsWith(LOUT)) {
			digits += 1203;
		} else if (stopCode.endsWith(LNHVN)) {
			digits += 1204;
		} else if (stopCode.endsWith(LYN)) {
			digits += 1205;
		} else if (stopCode.endsWith(MAIN)) {
			digits += 1300;
		} else if (stopCode.endsWith(MALL)) {
			digits += 1301;
		} else if (stopCode.endsWith(MART)) {
			digits += 1302;
		} else if (stopCode.endsWith(MC_TB)) {
			digits += 1303;
		} else if (stopCode.endsWith(MERT)) {
			digits += 1304;
		} else if (stopCode.endsWith(MRDL)) {
			digits += 1305;
		} else if (stopCode.endsWith(MRTV)) {
			digits += 1306;
		} else if (stopCode.endsWith(NIAG)) {
			digits += 1400;
		} else if (stopCode.endsWith(OAKD)) {
			digits += 1500;
		} else if (stopCode.endsWith(ONT)) {
			digits += 1501;
		} else if (stopCode.endsWith(PARK)) {
			digits += 1600;
		} else if (stopCode.endsWith(PELM)) {
			digits += 1601;
		} else if (stopCode.endsWith(QUEN)) {
			digits += 1700;
		} else if (stopCode.endsWith(QUNS)) {
			digits += 1701;
		} else if (stopCode.endsWith(RES)) {
			digits += 1800;
		} else if (stopCode.endsWith(RICH)) {
			digits += 1801;
		} else if (stopCode.endsWith(ST_D)) {
			digits += 1900;
		} else if (stopCode.endsWith(TERM)) {
			digits += 2000;
		} else if (stopCode.endsWith(TOWP)) {
			digits += 2001;
		} else if (stopCode.endsWith(TWNL)) {
			digits += 2002;
		} else if (stopCode.endsWith(TUPP)) {
			digits += 2003;
		} else if (stopCode.endsWith(UNIV)) {
			digits += 2100;
		} else if (stopCode.endsWith(VINE)) {
			digits += 2200;
		} else if (stopCode.endsWith(VSKL)) {
			digits += 2201;
		} else if (stopCode.endsWith(WDRW)) {
			digits += 2300;
		} else if (stopCode.endsWith(WLND)) {
			digits += 2301;
		} else if (stopCode.endsWith(WMBL)) {
			digits += 2302;
		} else {
			throw new MTLog.Fatal("Unexpected stop ID (ends with) '%s' (%s)!", stopCode, gStop);
		}
		return digits;
	}
}
