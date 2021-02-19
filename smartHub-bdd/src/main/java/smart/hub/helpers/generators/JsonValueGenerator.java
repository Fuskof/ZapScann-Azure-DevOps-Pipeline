package smart.hub.helpers.generators;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import net.serenitybdd.core.Serenity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.format;
import static smart.hub.helpers.Constants.DATE_PATTERN_YYYY_MM_DD;
import static smart.hub.helpers.Constants.TIMESTAMP;

public class JsonValueGenerator {

    private final String MATCHING_PATTERN = ".*[{].{3,}[}].*";
    private final String SPECIAL_CHARS_PATTERN = "\\`\\-\\=\\[\\]\\\\\\;\\'\\,\\.\\/\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\{\\}\\|\\:\\\"\\<\\>\\?";
    private String internalTimestampPattern = TIMESTAMP;
    private String internalDatePattern = DATE_PATTERN_YYYY_MM_DD;
    private String datePattern = null;
    private String initialValue;
    private String initialPattern;
    private String pattern;
    private String stringValue = null;
    private Integer integerValue = null;
    private Long longValue = null;
    private Double doubleValue = null;
    private Boolean booleanValue = null;
    private Object[] arrayValue = null;
    private JsonFieldType generatedFieldType = JsonFieldType.NULL;
    private Faker faker = new Faker();

    public String getStringValue() {
        return stringValue;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public Object[] getArrayValue() {
        return arrayValue;
    }

    public JsonFieldType getGeneratedFieldType() {
        return generatedFieldType;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * Method which generates a random or specific value, based on the provided pattern.
     * If no pattern is detected, the input value is returned as is.
     * <p>
     * The input value can be in itself a pattern - like {STRING:5},
     *  or it can contain a pattern - values like USER-000{STRING:4}Z are also accepted,
     *  but only for patterns which generate strings, which means all patterns except:
     *  {INT}, {LONG}, {DOUBLE}, {ARRAY}, {TRUE}, {FALSE}, {NULL}
     * <p>
     * The patterns itself are case insensitive, meaning {INT:3} is the same as {int:3} or {iNt:3}.
     * <p>
     * Can generate arrays for most patterns (Object[]), with the optional prefixes [N] or [N,M].
     * This is just a modifier for patterns, not to be confused with the {ARRAY} pattern.
     *      - {[3]INT:2-8} = fixed list length of 3
     *      - {[1,5]STRING:4} = random list length between 1 and 5
     * <p>
     * Has default timestamp and date formats, which can also be overridden externally,
     *  through the setDatePattern method.
     * <p>
     * Limitations:
     *      - if the received value contains a pattern, then it should not contain delimiter characters ({, })
     *          outside of that pattern (delimiters inside the pattern are allowed):
     *          - values like "val: {REGEX:{[0-9]{4}}" are processed correctly
     *          - but in values like "key: {id}, val: {REGEX:{[0-9]{4}}" the pattern won't be processed
     *      - only one single pattern is expected/supported within the initial value,
     *              in values like {STRING:3}{INT:7}, no pattern will be processed.
     *      - if the input value contains newlines (\n), no pattern will be detected.
     * <p>
     * List of supported patterns:
     *  - {INT:N}                       Generates a random N digits long positive INTEGER.
     *                                  Note: given the lower and upper bounds of the Integer type,
     *                                  using a length greater than 9 will most likely result in an ERROR.
     *                                  Optional modifiers: N,M  = random length, between N and M, inclusive
     *                                                      N..M = generates an int between N and M
     *                                                      S    = generates the int as a string
     *                                                      -    = generates a negative int
     *                                                      =    = saves the specific value as an int
     *                                  Examples:   {INT:6}       = 846309
     *                                              {-INT:4}      = -9570
     *                                              {INT:1,10}    = 207
     *                                              {INT:44..1683} = 524
     *                                              {INT:2,8S}    = "934827"
     *                                              {INT=86400}   = 86400
     * <p>
     *  - {LONG:N}                      Generates a random N digits long positive LONG.
     *                                  Note: given the lower and upper bounds of the Long type,
     *                                  using a length greater than 19 will result in an ERROR.
     *                                  Optional modifiers: N,M  = random length, between N and M, inclusive
     *                                                      N..M = generates a long between N and M
     *                                                      S    = generates the long as a string
     *                                                      -    = generates a negative long
     *                                                      =    = saves the specific value as a long
     *                                  Examples:   {LONG:15}               = 314941616421355
     *                                              {-LONG:6}              = -843522
     *                                              {LONG:4,14}             = 34096571
     *                                              {LONG:-95732..12522362} = -9845
     *                                              {LONG:1,19S}            = "23956"
     *                                              {LONG=2857074282}       = 2857074282
     * <p>
     *  - {DOUBLE:N}                    Generates a random N digits long positive DOUBLE.
     *                                  Optional modifiers: N.U      = whole part length set to N, decimals length set to U
     *                                                      N,M      = random length, between N and M, inclusive
     *                                                      N,M.U,V  = whole part random length, between N and M, inclusive
     *                                                                  decimals random length, between U and V, inclusive
     *                                                      N..M     = generates a double between N and M
     *                                                      N.U..M.V = generates a double between N.U and M.V
     *                                                                  (decimals random length, between U and V, inclusive)
     *                                                      S        = generates the double as a string
     *                                                      -        = generates a negative double
     *                                                      =        = saves the specific value as a double
     *                                  Examples:   {DOUBLE:12}                  = 324905724362.0
     *                                              {DOUBLE:4.6}                 = 4322.004812
     *                                              {-DOUBLE:7}                  = -9845643.0
     *                                              {DOUBLE:6,18}                = 1346981341.0
     *                                              {DOUBLE:3,5.1,4}             = 2783.23
     *                                              {DOUBLE:-118320..438539}     = -97435.0
     *                                              {DOUBLE:2435.8032..33914.11} = 8123.071
     *                                              {DOUBLE:6,12S}               = "49352320.0"
     *                                              {DOUBLE=540.0067}            = 540.0067
     * <p>
     *  - {STRING:N}                   Generates an N characters long STRING,
     *                                  containing random letters(upper and lowercase).
     *                                  Optional modifiers: ,M = random length, between N and M, inclusive
     *                                                      U  = all chars are uppercase
     *                                                      L  = all chars are lowercase
     *                                  Examples:   {STRING:7}    = "sAioDgb"
     *                                              {STRING:2,5U} = "ADNJA"
     *                                              {STRING:1L}   = "x"
     * <p>
     *  - {ALPHANUMERIC:N}              Generates an N characters long STRING, which may contain
     *                                  both digits and letters(upper and lowercase)
     *                                  Optional modifiers: ,M = random length, between N and M, inclusive
     *                                                      U  = all chars are uppercase
     *                                                      L  = all chars are lowercase
     *                                  Examples:   {ALPHANUMERIC:4}     = "2Boz"
     *                                              {ALPHANUMERIC:9U}    = "REL5902UM"
     *                                              {ALPHANUMERIC:3,13L} = "m42"
     * <p>
     *  - {SPECIAL:N}                   Generates an N special characters long STRING.
     *                                  Optional modifiers: ,M = random length, between N and M, inclusive
     *                                  Examples:   {SPECIAL:7}   = ",^.@;--"
     *                                              {SPECIAL:1,4} = "}{"
     * <p>
     *  - {RANDOM:N}                    Generates an N characters long STRING, which may contain
     *                                  digits, letters(upper and lower) and special chars.
     *                                  Optional modifiers: ,M = random length, between N and M, inclusive
     *                                                      U  = all chars are uppercase
     *                                                      L  = all chars are lowercase
     *                                  Examples:   {RANDOM:11,16} = "V%^6e5rgh8b%)"
     *                                              {RANDOM:6U}    = "&NJ4M!"
     *                                              {RANDOM:8L}    = "m%suu0@!"
     * <p>
     *  - {REGEX:S}                     Generates a random STRING based on S, the provided regex pattern
     *                                  Examples:   {REGEX:[A-Z]{4}-[0-9]{5}}     = "DNOV-94783"
     *                                              {REGEX:user[000]{3}[0-9]{7}}  = "user0002746300"
     * <p>
     *  - {SERENITY:K}                  Returns a previously Serenity saved value, as a STRING, based on a key - K.
     *                                  If the saved value is a number or a boolean, it will be converted to String.
     *                                  If the saved value is an array or a custom type,
     *                                      a not so meaningful toString() representation will be returned.
     *                                  If the key doesn't exist in Serenity, then NULL is returned.
     *                                  The key IS case sensitive.
     *                                  Examples:   Considering that we have the following key/value pairs saved in Serenity:
     *                                                  cardNumber : "2568323150523542" (string)
     *                                                  amount     : 500 (int)
     *                                                  isNew      : true (boolean)
     *                                                  productIds : [123, 904, 21] (array)
     *                                              then we should expect the following returned values:
     *                                                  {SERENITY:cardNumber} = "2568323150523542"
     *                                                  {SERENITY:amount} = "500"
     *                                                  {SERENITY:isNew} = "true"
     *                                                  {SERENITY:productIds} = "[Ljava.lang.Integer;@78b1cc93"
     * <p>
     *  - {ENUM:E.F}                    Returns an Enum value, as a STRING, based on E - the Enum's short name
     *                                      and F - the field name.
     *                                  Both the short name and the field name ARE case sensitive.
     *                                  Within the loaded clasess at runtime, there must be only one Enum with
     *                                      the given short name AND with the given field. If there are multiple Enums
     *                                      with the same short name but only one of them has the given field,
     *                                      then the value of that field will be returned.
     *                                  Examples:   Considering the following existing Enums:
     *                                                  com.test.il.DAYSOFF {SUNDAY, MONDAY}
     *                                                  com.test.row.DAYSOFF {SATURDAY, SUNDAY}
     *                                                  com.test.del.il.DAYSOFF {Sunday, MONDAY}
     *                                                  com.test.del.row.DaysOff {SATURDAY, sunday}
     *                                              then we should expect the following outcomes:
     *                                                  {ENUM:DAYSOFF.SATURDAY} = returns "SATURDAY"
     *                                                  {ENUM:DAYSOFF.SUNDAY} = error, 2 Enums with the same name and field
     *                                                  {ENUM:DAYSOFF.Monday} = error, no Enums with the given name and field
     * <p>
     *  - {FAKER:S.R}                   Generates a STRING using the Java Faker library, based on S.R...Z,
     *                                  the chain of Faker methods to be used.
     *                                  Supports only methods without params, and the last method from the chain
     *                                  must return a STRING.
     *                                  For the full list of Faker methods, see
     *                                  https://github.com/DiUS/java-faker
     *                                  and/or
     *                                  http://dius.github.io/java-faker/apidocs/index.html
     *                                  Examples:   {FAKER:NAME.FIRSTNAME} = "Mauro"
     *                                              {FAKER:ADDRESS.STREETADDRESS} = "715 Larson Islands"
     * <p>
     *  - {TRUE}                        Generates a BOOLEAN object, with the value set to true.
     * <p>
     *  - {FALSE}                       Generates a BOOLEAN object, with the value set to false.
     * <p>
     *  - {SPACE}                       Generates a STRING composed of one space character.
     *                                  Optional modifiers: :N   = the string will have a fixed length of N
     *                                                      :N,M = random length, between N and M, inclusive
     *                                  Examples:   {SPACE}     = " "
     *                                              {SPACE:3}   = "   "
     *                                              {SPACE:2,7} = "    "
     * <p>
     *  - {EMPTY}                       Generates an empty STRING
     * <p>
     *  - {NULL}                        Generates a NULL object. The target field will be missing from the json object.
     * <p>
     *  - {UUID}                        Generates a STRING with the value of a random uuid.
     *                                  Examples:   {UUID} = "123e4567-e89b-12d3-a456-556642440000"
     * <p>
     *  - {ARRAY:a,b,...z}              Generates an ARRAY of OBJECTs, populated with the specified values.
     *                                  All items must be of the same type (either string, integer, long, double or boolean).
     *                                  Auto-detects the type of the elements, but builds an array of objects (Objects[]).
     *                                  If all elements are numbers:
     *                                      - if at least one of them is double, then all generated values will be double.
     *                                      - if no elements are double, but one of them is long, then all generated values will be long.
     *                                  Examples:   {ARRAY:visa,mc,AEX}              = ["visa", "mc", "AEX"] - string values
     *                                              {ARRAY:-89,0,4821342,221}        = [-89,0,4821342,221] - int values
     *                                              {ARRAY:257902353474763, -45}     = [257902353474763, -45] - long values
     *                                              {ARRAY:9546745, 0, -903, 13.798} = [9546745.0, 0.0, -903.0, 13.798] - double values
     *                                              {ARRAY:true,false,false}         = [true, false, false] - boolean values
     *                                              {ARRAY:true ,fals,false}         = ["true ", "fals", "false"] - string values
     * <p>
     *  - {NOW}                         Generates a STRING containing the current date and time,
     *                                  formatted as the Constants.TIMESTAMP pattern.
     *                                  Optional modifiers: -/+Nx   = adds or subtracts from the current datetime,
     *                                                                where x can be: y   = years
     *                                                                                m   = months
     *                                                                                d   = days
     *                                                                                h   = hours
     *                                                                                min = minutes
     *                                                                                s   = seconds
     *                                                                                ms  = milliseconds
     *                                                      :UNIX   = returns the current unix time, as LONG
     *                                                      :MILLIS = returns the current time in millis, as LONG
     *                                  Examples:   Given that {NOW} is 2020-10-02T15:34:07.834Z, then:
     *                                              {NOW-912ms}     = "2020-10-02T15:34:06.920Z"
     *                                              {NOW+12s}       = "2020-10-02T15:34:19.834Z"
     *                                              {NOW+44min}     = "2020-10-02T16:18:07.834Z"
     *                                              {NOW-1h}        = "2020-10-02T14:34:07.834Z"
     *                                              {NOW-2d}        = "2020-09-30T15:34:07.834Z"
     *                                              {NOW+11m}       = "2021-09-02T15:34:07.834Z"
     *                                              {NOW-1y}        = "2019-10-02T15:34:07.834Z"
     *                                              {NOW:UNIX}      = 1601642047
     *                                              {NOW:MILLIS}    = 1601642047920
     * <p>
     *  - {TIMESTAMP}                   Generates a STRING containing a random date and time from the last 5 minutes,
     *                                  formatted as the Constants.TIMESTAMP pattern.
     *                                  Optional modifiers:     r = the timestamp will be based on a random millisecond
     *                                                              between {NOW}-Nx and {NOW} or between {NOW} and {NOW}+Nx
     *                                                      -/+Nx = adds or subtracts from the current datetime up to
     *                                                              N time units, defined as x,
     *                                                              where x can be: y   = years
     *                                                                              m   = months
     *                                                                              d   = days
     *                                                                              h   = hours
     *                                                                              min = minutes
     *                                                                              s   = seconds
     *                                                                              ms  = milliseconds
     *                                  Examples:   Given that {NOW} is 2020-10-02T15:34:07.834Z, then:
     *                                              {TIMESTAMP-912ms} = "2020-10-02T15:34:07.239Z"
     *                                              subtracts between 1 and 912 milliseconds from {NOW}
     *                                              {TIMESTAMP+12s}   = "2020-10-02T15:34:10.834Z"
     *                                              adds between 1 and 12 seconds to {NOW}
     *                                              {TIMESTAMP+12sr}  = "2020-10-02T15:34:15.113Z"
     *                                              picks a random millisecond between {NOW} and {NOW+12s}
     *                                              {TIMESTAMP+44min} = "2020-10-02T16:00:07.834Z"
     *                                              {TIMESTAMP-1h}    = "2020-10-02T14:34:07.834Z"
     *                                              {TIMESTAMP-2dr}   = "2020-09-30T23:29:46.305Z"
     *                                              {TIMESTAMP+11m}   = "2021-04-02T15:34:07.834Z"
     *                                              {TIMESTAMP-1y}    = "2019-10-02T15:34:07.834Z"
     * <p>
     *  - {TODAY}                       Generates a STRING containing the current date,
     *                                  formatted as the Constants.DATE_PATTERN_YYYYMMDD pattern.
     *                                  Optional modifiers: -/+Nx = adds or subtracts from the current date,
     *                                                              where x can be: y   = years
     *                                                                              m   = months
     *                                                                              d   = days
     *                                  Examples:   Given that {TODAY} is 2020-10-02, then:
     *                                              {TODAY+11d}    = "2020-10-13"
     *                                              {TODAY-1m}     = "2020-09-02"
     *                                              {TODAY+1y}     = "2021-10-02"
     * <p>
     *  - {DATE}                        Generates a STRING containing a random date from the last week,
     *                                  formatted as the Constants.DATE_PATTERN_YYYYMMDD pattern.
     *                                  Optional modifiers:     r = the date will be based on a random day
     *                                                              between {TODAY}-Nx and {TODAY} or between {TODAY} and {TODAY}+Nx
     *                                                      -/+Nx = adds or subtracts from the current datetime up to
     *                                                              N time units, defined as x,
     *                                                              where x can be: y   = years
     *                                                                              m   = months
     *                                                                              d   = days
     *                                  Examples:   Given that {TODAY} is 2020-10-02, then:
     *                                              {DATE+11d}      = "2020-10-10"
     *                                              adds between 1 and 11 days from {TODAY}
     *                                              {DATE-4m}       = "2020-07-02"
     *                                              subtracts between 1 and 4 months from {TODAY}
     *                                              {DATE-4mr}      = "2020-08-19"
     *                                              picks a random day between {TODAY} and {TODAY-4m}
     *                                              {DATE+3y}       = "2022-10-02"
     *                                              {DATE+3yr}      = "2023-03-27"
     */
    public JsonValueGenerator generateJsonValue(String initialValue) {

        this.initialValue = initialValue;
        if (initialValue.matches(MATCHING_PATTERN)) {
            int startOfPatternIndex = initialValue.indexOf("{");
            int endOfPatternIndex = initialValue.lastIndexOf("}");
            initialPattern = initialValue.substring(startOfPatternIndex + 1, endOfPatternIndex);
            pattern = initialPattern.toLowerCase();
            String patternType = pattern.startsWith("[") ? pattern.split("]")[1] : pattern;
            patternType = patternType.startsWith("-") ? patternType.split("-")[1] : patternType;
            patternType = patternType.split("=")[0].split(":")[0].split("-")[0].split("\\+")[0];

            switch (patternType) {
                case "int":
                    generateInt();
                    break;
                case "long":
                    generateLong();
                    break;
                case "double":
                    generateDouble();
                    break;
                case "string":
                    generateString();
                    break;
                case "alphanumeric":
                    generateAlphanumeric();
                    break;
                case "special":
                    generateSpecialChars();
                    break;
                case "random":
                    generateRandom();
                    break;
                case "regex":
                    generateRegex();
                    break;
                case "serenity":
                    returnSerenityValue();
                    break;
                case "enum":
                    returnEnumValue();
                    break;
                case "faker":
                    generateFakerValue();
                    break;
                case "true":
                case "false":
                    generateBoolean(patternType);
                    break;
                case "space":
                    generateSpace();
                    break;
                case "empty":
                    generateEmptyString();
                    break;
                case "null":
                    break;
                case "uuid":
                    generateUUID();
                    break;
                case "array":
                    generateArray();
                    break;
                case "now":
                    computeTimestamp(pattern);
                    break;
                case "timestamp":
                    generateTimestamp();
                    break;
                case "today":
                    computeSimpleDate(pattern);
                    break;
                case "date":
                    generateDate();
                    break;
                default:
                    this.stringValue = initialValue;
                    generatedFieldType = JsonFieldType.STRING;
                    break;
            }
        } else {
            this.stringValue = initialValue;
            generatedFieldType = JsonFieldType.STRING;
        }
        return this;
    }

    private void generateWholeNumber(JsonFieldType type) {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            long value = 0;
            if (pattern.contains("..")) {
                long lowerBound = Long.parseLong(pattern.split(":")[1].split("\\.\\.")[0]);
                long upperBound = Long.parseLong(pattern.split(":")[1].split("\\.\\.")[1].split("s")[0]);
                value = generateRandomLong(lowerBound, upperBound);
            } else if (pattern.contains("=")) {
                value = Long.parseLong(pattern.split("=")[1].split("s")[0]);
            } else {
                String length = getValueLength((!pattern.startsWith("-") ? pattern : pattern.split("-")[1]).split("s")[0]);
                Long regexLength = Long.parseLong(length) - 1;
                boolean isValueLong = false;
                if (regexLength > 18) {
                    throw new RuntimeException("Invalid length for Long type");
                } else {
                    while (!isValueLong) {
                        try {
                            value = Long.parseLong(faker.regexify(format("[1-9][0-9]{%s}", regexLength)));
                            isValueLong = true;
                        } catch (NumberFormatException e) {

                        }
                    }
                }
                value = !pattern.startsWith("-") ? value : -value;
            }
            if (!pattern.contains("s")) {
                populateValue(value, type);
            } else {
                populateValue(Long.toString(value), JsonFieldType.STRING);
            }
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateWholeNumber(type);
                array[i] = !pattern.contains("s") ? (type.equals(JsonFieldType.INTEGER) ? integerValue : longValue) : stringValue;
            }
            integerValue = null;
            longValue = null;
            stringValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateInt() {
        generateWholeNumber(JsonFieldType.INTEGER);
    }

    private void generateLong() {
        generateWholeNumber(JsonFieldType.LONG);
    }

    private void generateDouble() {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            double value;
            if (pattern.contains("..")) {
                double lowerBound = Double.parseDouble(pattern.split(":")[1].split("\\.\\.")[0]);
                double upperBound = Double.parseDouble(pattern.split(":")[1].split("\\.\\.")[1]);
                value = generateRandomDouble(lowerBound, upperBound, -1);
            } else if (pattern.contains("=")) {
                value = Double.parseDouble(pattern.split("=")[1]);
            } else {
                String length = getValueLength((!pattern.startsWith("-") ? pattern : pattern.split("-")[1]).split("s")[0].split("\\.")[0]);
                Long regexLength = Long.parseLong(length) - 1;
                if (pattern.contains(".")) {
                    String decimalLength = getValueLength("double:" + (!pattern.startsWith("-") ? pattern : pattern.split("-")[1]).split("s")[0].split("\\.")[1]);
                    value = Double.parseDouble(faker.regexify(format("[1-9][0-9]{%s}\\.[0-9]{%s}", regexLength, decimalLength)));
                } else {
                    value = Double.parseDouble(faker.regexify(format("[1-9][0-9]{%s}", regexLength)));
                }
                value = !pattern.startsWith("-") ? value : -value;
            }
            if (!pattern.contains("s")) {
                populateValue(value, JsonFieldType.DOUBLE);
            } else {
                populateValue(Double.toString(value), JsonFieldType.STRING);
            }
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateDouble();
                array[i] = !pattern.contains("s") ? doubleValue : stringValue;
            }
            doubleValue = null;
            stringValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateString() {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            String casePattern = getCasePattern();
            String length = getValueLength(pattern);
            String value = faker.regexify(format("[%s]{%s}", casePattern, length));
            populateValue(value, JsonFieldType.STRING);
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateString();
                array[i] = stringValue;
            }
            stringValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateAlphanumeric() {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            String casePattern = getCasePattern();
            String length = getValueLength(pattern);
            String value = faker.regexify(format("[%s0-9]{%s}", casePattern, length));
            populateValue(value, JsonFieldType.STRING);
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateAlphanumeric();
                array[i] = stringValue;
            }
            stringValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateSpecialChars() {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            String length = getValueLength(pattern);
            String value = faker.regexify(format("[%s]{%s}", SPECIAL_CHARS_PATTERN, length));
            populateValue(value, JsonFieldType.STRING);
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateSpecialChars();
                array[i] = stringValue;
            }
            stringValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateRandom() {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            String casePattern = getCasePattern();
            String length = getValueLength(pattern);
            String value = faker.regexify(format("[%s0-9%s]{%s}", casePattern, SPECIAL_CHARS_PATTERN, length));
            populateValue(value, JsonFieldType.STRING);
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateRandom();
                array[i] = stringValue;
            }
            stringValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateRegex() {
        String value = faker.regexify(pattern.split(":")[1]);
        populateValue(value, JsonFieldType.STRING);
    }

    private void returnSerenityValue() {
        String key = initialPattern.split(":")[1];
        if (Serenity.hasASessionVariableCalled(key)) {
            String value = Serenity.sessionVariableCalled(key).toString();
            if (!Objects.isNull(value)) {
                populateValue(value, JsonFieldType.STRING);
            }
        }
    }

    private void returnEnumValue() {
        String enumName = initialPattern.split(":")[1].split("\\.")[0];
        String enumValue = initialPattern.split(":")[1].split("\\.")[1];
        List<Class> loadedClasses;
        List<Class<Enum>> matchingClasses = new ArrayList<>();
        Field field;

        try {
            field = ClassLoader.class.getDeclaredField("classes");
            field.setAccessible(true);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            loadedClasses = (List<Class>) field.get(classLoader);

            for (Class cls : loadedClasses) {
                String classSimpleName = cls.getName().split("\\.")[cls.getName().split("\\.").length - 1];
                if (classSimpleName.equals(enumName) && cls.isEnum()) {
                    for (Object value : cls.getEnumConstants()) {
                        if (value.toString().equals(enumValue)) {
                            matchingClasses.add(cls);
                        }
                    }
                }
            }

            if (matchingClasses.size() < 1) {
                throw new ClassNotFoundException(String.format("No enum found with short name: %s and field: %s",
                        enumName, enumValue));
            } else if (matchingClasses.size() > 1) {
                throw new RuntimeException(String.format("%d enum classes found with short name: %s and field: %s",
                        matchingClasses.size(), enumName, enumValue));
            } else {
                for (Object value : matchingClasses.get(0).getEnumConstants()) {

                    if (value.toString().equals(enumValue)) {
                        populateValue(value.toString(), JsonFieldType.STRING);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateFakerValue() {
        String value = invokeFakerMethodChain(faker, pattern.split(":")[1]);
        populateValue(value, JsonFieldType.STRING);
    }

    @SneakyThrows
    private <R, P> R invokeFakerMethodChain(P object, String methodChain) {
        String[] methods = methodChain.split("\\.");
        Method method = Arrays.stream(object.getClass().getMethods())
                .filter(m -> m.getName().equalsIgnoreCase(methods[0]))
                .filter(m -> m.getParameterTypes().length == 0)
                .findFirst()
                .get();
        if (methods.length == 1) {
            return (R) method.invoke(object);
        } else {
            return invokeFakerMethodChain(method.invoke(object), methods[1]);
        }
    }

    private void generateBoolean(String value) {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            populateValue(Boolean.valueOf(value), JsonFieldType.BOOLEAN);
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateBoolean(value);
                array[i] = booleanValue;
            }
            booleanValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateSpace() {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            String length = getValueLength(!pattern.contains(":") ? pattern + ":1" : pattern);
            String value = faker.regexify(format("[\040]{%s}", length));
            populateValue(value, JsonFieldType.STRING);
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateSpace();
                array[i] = stringValue;
            }
            stringValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateEmptyString() {
        populateValue("", JsonFieldType.STRING);
    }

    private void generateUUID() {
        int arrayLength = getArrayLength();
        if (arrayLength == 0) {
            String value = UUID.randomUUID().toString();
            populateValue(value, JsonFieldType.STRING);
        } else {
            Object[] array = new Object[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                generateUUID();
                array[i] = stringValue;
            }
            stringValue = null;
            populateValue(array, JsonFieldType.ARRAY);
        }
    }

    private void generateArray() {
        String[] elements = pattern.split(":")[1].split(",");
        Object[] array = new Object[elements.length];
        boolean allElementsAreInt = true;
        boolean allElementsAreLong = true;
        boolean isLongElementPresent = false;
        boolean allElementsAreDouble = true;
        boolean isDoubleElementPresent = false;
        boolean allElementsAreBoolean = true;
        for (String element : elements) {
            int wholePartLength = element.split("\\.")[0].length();
            if (element.matches(format("0|0\\.[0-9]{1,}|[\\-1-9][0-9]{%s}|[\\-1-9][0-9]{%s}\\.[0-9]{1,}",
                    element.length() - 1, wholePartLength - 1))) {
                if (element.contains(".")) {
                    isDoubleElementPresent = true;
                } else {
                    if (Math.abs(Long.parseLong(element)) > Integer.MAX_VALUE) {
                        isLongElementPresent = true;
                    }
                }
                allElementsAreBoolean = false;
            } else if (element.toLowerCase().matches("true|false")) {
                allElementsAreInt = false;
                allElementsAreLong = false;
                allElementsAreDouble = false;
            } else {
                allElementsAreInt = false;
                allElementsAreLong = false;
                allElementsAreDouble = false;
                allElementsAreBoolean = false;
            }
        }
        if (isDoubleElementPresent) {
            allElementsAreLong = false;
            allElementsAreInt = false;
        } else {
            if (isLongElementPresent) {
                allElementsAreDouble = false;
                allElementsAreInt = false;
            } else {
                allElementsAreDouble = false;
                allElementsAreLong = false;
            }
        }
        for (int i = 0; i < elements.length; i++) {
            if (allElementsAreInt) {
                array[i] = Integer.parseInt(elements[i]);
            } else if (allElementsAreLong) {
                array[i] = Long.parseLong(elements[i]);
            } else if (allElementsAreDouble) {
                array[i] = Double.parseDouble(elements[i]);
            } else if (allElementsAreBoolean) {
                array[i] = Boolean.parseBoolean(elements[i]);
            } else {
                array[i] = elements[i];
            }
        }
        populateValue(array, JsonFieldType.ARRAY);
    }

    private void computeTimestamp(String pattern) {
        computeDate(pattern, "now");
    }

    private void generateTimestamp() {
        Calendar now = new GregorianCalendar();
        long modifierValue;
        int modifierUnit;
        String modifierType;
        if (pattern.equals("timestamp")) {
            modifierValue = -generateRandomLong(1, 5 * 60 * 1000);
            modifierUnit = Calendar.MILLISECOND;
            modifierType = "ms";
        } else {
            modifierType = getTimeUnitType(pattern.split("r")[0]);
            modifierUnit = convertTimeUnitTypeToCalendarUnit(modifierType);
            modifierValue = Integer.parseInt(pattern.split("r")[0].split("timestamp")[1].split(modifierType)[0]);
            if (pattern.endsWith("r")) {
                pattern = pattern.split("r")[0];
                modifierUnit = Calendar.MILLISECOND;
                switch (modifierType) {
                    case "y":
                        modifierValue = modifierValue * 365 * 24 * 60 * 60 * 1000;
                        break;
                    case "m":
                        modifierValue = modifierValue * 30 * 24 * 60 * 60 * 1000;
                        break;
                    case "d":
                        modifierValue = modifierValue * 24 * 60 * 60 * 1000;
                        break;
                    case "h":
                        modifierValue = modifierValue * 60 * 60 * 1000;
                        break;
                    case "min":
                        modifierValue = modifierValue * 60 * 1000;
                        break;
                    case "s":
                        modifierValue = modifierValue * 1000;
                        break;
                    default:
                        break;
                }
            }
        }
        if (modifierValue > 0) {
            modifierValue = generateRandomLong(1, modifierValue);
        } else {
            modifierValue = generateRandomLong(modifierValue, 1);
        }
        int intValue;
        if (modifierType.equals("ms") && Math.abs(modifierValue) > Integer.MAX_VALUE) {
            now.add(Calendar.DAY_OF_YEAR, (int) (modifierValue / (24 * 60 * 60 * 1000)));
            intValue = (int) modifierValue % (24 * 60 * 60 * 1000);
        } else {
            intValue = (int) modifierValue;
        }
        now.add(modifierUnit, intValue);
        Date date = now.getTime();
        String value = new SimpleDateFormat(!Objects.isNull(datePattern) ? datePattern : internalTimestampPattern).format(date);
        populateValue(value, JsonFieldType.STRING);
    }

    private void computeSimpleDate(String pattern) {
        computeDate(pattern, "today");
    }

    private void generateDate() {
        Calendar now = new GregorianCalendar();
        int modifierValue;
        int modifierUnit;
        String modifierType;
        if (pattern.equals("date")) {
            modifierValue = -generateRandomInt(1, 5);
            modifierUnit = Calendar.DAY_OF_YEAR;
            modifierType = "d";
        } else {
            modifierType = getTimeUnitType(pattern.split("r")[0]);
            modifierUnit = convertTimeUnitTypeToCalendarUnit(modifierType);
            modifierValue = Integer.parseInt(pattern.split("r")[0].split("date")[1].split(modifierType)[0]);
            if (pattern.endsWith("r")) {
                pattern = pattern.split("r")[0];
                modifierUnit = Calendar.DAY_OF_YEAR;
                switch (modifierType) {
                    case "y":
                        modifierValue = modifierValue * 365;
                        break;
                    case "m":
                        modifierValue = modifierValue * 30;
                        break;
                    default:
                        break;
                }
            }
        }
        if (modifierValue > 0) {
            modifierValue = generateRandomInt(1, modifierValue);
        } else {
            modifierValue = generateRandomInt(modifierValue, 1);
        }
        now.add(modifierUnit, modifierValue);
        Date date = now.getTime();
        String value = new SimpleDateFormat(!Objects.isNull(datePattern) ? datePattern : internalDatePattern).format(date);
        populateValue(value, JsonFieldType.STRING);
    }

    private int getArrayLength() {
        int length = 0;
        if (pattern.startsWith("[")) {
            length = Integer.parseInt(getLength(pattern.split("\\[")[1].split("]")[0]));
            pattern = pattern.split("]")[1];
        }
        return length;
    }

    private String getValueLength(String pattern) {
        pattern = pattern.split(":")[1].split("u")[0].split("l")[0];
        return getLength(pattern);
    }

    private String getLength(String input) {
        if (input.contains(",")) {
            int lowerBound = Integer.parseInt(input.split(",")[0]);
            int upperBound = Integer.parseInt(input.split(",")[1]);
            return String.valueOf(generateRandomInt(lowerBound, upperBound));
        } else {
            return input;
        }
    }

    private String getCasePattern() {
        String modifier = pattern.substring(pattern.length() - 1);
        switch (modifier) {
            case "u":
                return "A-Z";
            case "l":
                return "a-z";
            default:
                return "A-Za-z";
        }
    }

    private long generateRandomLong(long lowerBound, long upperBound) {
        return lowerBound + (long) (Math.random() * (upperBound - lowerBound + 1));
    }

    private int generateRandomInt(int lowerBound, int upperBound) {
        return new Random().nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    private double generateRandomDouble(double lowerBound, double upperBound, int decimals) {
        Double value =  lowerBound + (upperBound - lowerBound) * new Random().nextDouble();
        if (decimals == -1) {
            int lowerBoundDecimalsLength = (lowerBound + "").split("\\.")[1].length();
            int upperBoundDecimalsLength = (upperBound + "").split("\\.")[1].length();
            if (lowerBound < upperBoundDecimalsLength) {
                decimals = generateRandomInt(lowerBoundDecimalsLength, upperBoundDecimalsLength);
            } else {
                decimals = generateRandomInt(upperBoundDecimalsLength, lowerBoundDecimalsLength);
            }
        }
        return new BigDecimal(value.toString()).setScale(decimals, RoundingMode.HALF_UP).doubleValue();
    }

    private void computeDate(String pattern, String type) {
        Calendar calendar = new GregorianCalendar();
        int patternLength = pattern.split(":")[0].length();

        modifierCheck:
        if (patternLength > type.length()) {
            String modifierType = getTimeUnitType(pattern);
            int modifierUnit = convertTimeUnitTypeToCalendarUnit(modifierType);
            if (modifierUnit == -1) {
                break modifierCheck;
            }
            int modifierValue = Integer.parseInt(pattern.split(type)[1].split(modifierType)[0]);
            calendar.add(modifierUnit, modifierValue);
        }
        if (type.equals("now") && pattern.contains(":")) {
            long value = System.currentTimeMillis();
            if (pattern.split(":")[1].equals("unix")) {
                value = value/1000;
            }
            populateValue(value, JsonFieldType.LONG);
        } else {
            Date date = calendar.getTime();
            String value = new SimpleDateFormat(!Objects.isNull(datePattern) ? datePattern
                    : (type.equals("now") ? internalTimestampPattern : internalDatePattern)).format(date);
            populateValue(value, JsonFieldType.STRING);
        }
    }

    private String getTimeUnitType(String pattern) {
        String modifierType;
        if (pattern.endsWith("min")) {
            modifierType = "min";
        } else if (pattern.endsWith("ms")) {
            modifierType = "ms";
        } else {
            modifierType = pattern.substring(pattern.length() - 1);
        }
        return modifierType;
    }

    private int convertTimeUnitTypeToCalendarUnit(String modifierType) {
        int modifierUnit = -1;
        switch (modifierType) {
            case "y":
                modifierUnit = Calendar.YEAR;
                break;
            case "m":
                modifierUnit = Calendar.MONTH;
                break;
            case "d":
                modifierUnit = Calendar.DAY_OF_YEAR;
                break;
            case "h":
                modifierUnit = Calendar.HOUR;
                break;
            case "min":
                modifierUnit = Calendar.MINUTE;
                break;
            case "s":
                modifierUnit = Calendar.SECOND;
                break;
            case "ms":
                modifierUnit = Calendar.MILLISECOND;
                break;
            default:
                break;
        }
        return modifierUnit;
    }

    private void populateValue(Object value, JsonFieldType type) {
        switch (type) {
            case STRING:
                stringValue = initialValue.replace(format("{%s}", initialPattern), (String) value);
                break;
            case INTEGER:
                integerValue = ((Long) value).intValue();
                break;
            case LONG:
                longValue = (Long) value;
                break;
            case DOUBLE:
                doubleValue = (Double) value;
                break;
            case BOOLEAN:
                booleanValue = (Boolean) value;
                break;
            case ARRAY:
                arrayValue = (Object[]) value;
                break;
            default:
                break;
        }
        generatedFieldType = type;
    }

    public enum JsonFieldType {STRING, INTEGER, LONG, DOUBLE, BOOLEAN, ARRAY, NULL}


//keeping this in order to quickly test a new or existing pattern
//    @Override
//    public String toString() {
//        return "ValueGenerator{" +
//                "stringValue='" + stringValue + '\'' +
//                ", intValue=" + integerValue +
//                ", longValue=" + longValue +
//                ", doubleValue=" + doubleValue +
//                ", boolValue=" + booleanValue +
//                ", arrayValue=" + Arrays.toString(arrayValue) +
//                '}';
//    }
//
//    public static void main(String[] args) {
//
//        Serenity.setSessionVariable("stringValue").to("value");
//        Serenity.setSessionVariable("numberValue").to(5);
//        Serenity.setSessionVariable("boolValue").to(true);
//        Serenity.setSessionVariable("arrayValue").to(new String[]{"val1", "val2"});
//
//        String[] patterns = {
//                "{int:6}",
//                "{[3,6]-int:3}",
//                "{INT:1,9}",
//                "{[5]INt:2,7s}",
//                "{int:-99999..5}",
//                "{INT=86400}",
//                "{-long:15}",
//                "{[2,4]long:6}",
//                "{[4]LONG:3,7}",
//                "{LOng:10,14s}",
//                "{long:-80000004..-378903}",
//                "{LONG=5443345432342}",
//                "{DOUBLE:4,7}",
//                "{DOuble:5.10}",
//                "{[2,5]double:3,5.1,6}",
//                "{[3]DOUble:45.33..100.89}",
//                "{dOuBLe=123.456}",
//                "Value: {douBle:4,7.5,14s}",
//                "{StRIng:7}_ID",
//                "{string:2,5U}",
//                "{[1]sTRiNg:7U}",
//                "42{STRING:1L}",
//                "{ALPHANUMERIC:4}",
//                "{[3]ALPHANUMERIC:2,5}",
//                "\"{alPhANUmeRiC:9U}\"",
//                "{\"id\": {alphanumeric:3,13L}}",
//                "char sequence: [{special:7}]",
//                "{SPECIAL:1,4}",
//                "{[2,4]SPECIAL:10}",
//                "Output: \t{RANDOM:11,16}",
//                "TID: 000{random:6U} >>>",
//                "{rANDOM:8L}",
//                "{[1,5]RANdOM:2,4}",
//                "{REGEX: \\|([A-Z]{5}[0-9]{4}\\|){2,3}}",
//                "hexa:{regex:[0-9a-f]{10}}",
//                "Value: {SERENITY:stringValue}",
//                "{serenity:numberValue}",
//                "{SerENItY:boolValue}?",
//                "{SERenity:arrayValue}",
//                "< {sEReNity:missingValue} >",
//                "{ENUM:DummyEnum.DummyField}",
//                "{FAKER:NAME.firstname}",
//                "Faker value: {faker:Address.StreetAddress}",
//                "{TRUE}",
//                "{[3]true}",
//                "{false}",
//                "{[2,4]FALSE}",
//                "{SPACE}",
//                "{[2]SPAce:5,9}",
//                "{space:5}",
//                "{spACE:1,10}",
//                "{EMPTY}",
//                "{NULL}",
//                "UUID={uuid}",
//                "{[4]uuid}",
//                "{ARRAY:str1,-123f7,012378,123854}",
//                "{array:-345,786912,0}",
//                "{arRAY:2356,2,-3565347890433,42}",
//                "{aRRaY:0,-1253,35.99}",
//                "{ArRAy:235,3562.2435,str}",
//                "{ARRay:true,false,true,true}",
//                "{ARRAY:false,false,true }",
//                "{NOw}0300",
//                "{NOW-912ms}",
//                "Date: {now+12s}.",
//                "{NoW+44min} /end",
//                "{nOW-1h}",
//                "Current time in millis: {now:MILLIS}",
//                "Unix time: {NOW:unix}",
//                "timestamp} {Now-2d}",
//                "{noW+11m}",
//                "{nOw-1y}",
//                "{TIMESTAMP}",
//                "{TIMEstamp-500ms}",
//                "TS: {TIMESTAMP+44s}",
//                "{TIMESTAMP-3min}",
//                "{TIMESTAMP+4h}",
//                "{TIMESTAMP-22d} | date",
//                "{timeSTAMP+5m}",
//                "{TiMEstAmp-11mr}",
//                "+++ {TIMESTAMP-3y} +++",
//                "{timestamp+2yr}",
//                "{TODAY}",
//                "local date was {toDAY+11d}",
//                "@{TOday-1m}@",
//                "{today+1y}ZZZ",
//                "{DATE}",
//                "local date was {DAtE-14d}",
//                "({datE+14m})",
//                "{datE-7mr}:>>",
//                "{date-3y}...",
//                "{dATE+4yr}",
//                "=======================NEGATIVE CASES=======================",
//                "Not identified as a pattern {NOT_A_SUPPORTED_PATTERN}",
//                "Newline is \n not supported {STRING:6}",
//                "Missing } {STRING:2,6",
//                "STRING:2,7} Missing {",
//                "non pattern string value"
//        };
//        for (String pattern : patterns) {
//            JsonValueGenerator gen = new JsonValueGenerator();
//            gen.generateJsonValue(pattern);
//            if (gen.generatedFieldType != JsonFieldType.NULL) {
//                switch (gen.generatedFieldType) {
//                    case STRING:
//                        System.out.println(format("%s => %s", pattern, gen.stringValue));
//                        break;
//                    case INTEGER:
//                        System.out.println(format("%s => %s", pattern, gen.integerValue));
//                        break;
//                    case LONG:
//                        System.out.println(format("%s => %s", pattern, gen.longValue));
//                        break;
//                    case DOUBLE:
//                        System.out.println(format("%s => %s", pattern, gen.doubleValue));
//                        break;
//                    case BOOLEAN:
//                        System.out.println(format("%s => %s", pattern, gen.booleanValue));
//                        break;
//                    case ARRAY:
//                        System.out.println(format("%s => %s", pattern, Arrays.toString(gen.arrayValue)));
//                        break;
//                    default:
//                        break;
//                }
//            } else {
//                System.out.println(format("%s => %s", pattern, gen.toString()));
//            }
//        }
//    }
}