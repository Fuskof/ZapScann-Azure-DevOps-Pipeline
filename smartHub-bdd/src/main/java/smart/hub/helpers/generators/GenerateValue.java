package smart.hub.helpers.generators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static smart.hub.helpers.Constants.TIMESTAMP;

/**
 * This annotation should be used in all request model classes.
 * All fields from these request model classes should be declared as Object
 *  and should be annotated with @GenerateValue.
 * <p>
 * The parameters supported by the annotation are as follows:
 *      - type: specifies the real class of the field,
 *          the class that should be used for generating valid requests
 *          Default: String.class - if the field is a string, this parameter doesn't need to be specified
 * <p>
 *      - value: the specific value or pattern used to generate valid requests
 *          To set a default value for the field, it must be String, Integer/Long or Boolean.
 *          Since only strings can be transmitted as the value for this param,
 *          for the other types, we need to use the following ValueGenerator patterns:
 *              - Integer: "{INT=N}", where N is the int value to be set
 *              - Long: "{LONG=N}", where N is the long value to be set
 *              - Double: "{DOUBLE=N}", where N is the double value to be set
 *              - Boolean: "{TRUE}" or "{FALSE}"
 *          Default: "{STRING:5,10}" - generates a random string value, with length between 5 and 10 chars
 * <p>
 *      - isOptional: marks the field as optional or not
 *          Default: false - if the field is mandatory, this parameter doesn't need to be specified
 * <p>
 *      - dateFormat: if the field should have a specific date format, that format can be specified here.
 *          Otherwise, this parameter doesn't need to be specified.
 *          Default: the TIMESTAMP date pattern defined in the Constants class ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
 * <p>
 *      - arrayLengths: if the field is an array (multidimensional or not), this parameter can be used
 *          to specify the lengths of the generated arrays.
 *          Default: {2}
 *
 *          If the array is multidimensional, each dimension/level has its own length. These lengths
 *              should be specified in the arrayLengths parameter, starting with the topmost level.
 *          Example:
 *              Array structure: [[v00, v01], [v10, v11], [v20, v21]]
 *              The array has 2 dimensions/levels, so the arrayLengths parameter should have 2 values:
 *                  - the first value should be the length of the first level array, which is 3
 *                  - the second value should be the length of the second level array, which is 2
 *              Therefore, in this case, the arrayLengths parameter should have the following value: {3,2}
 *
 *          Note: if the values of the arrayLengths parameter are fewer than the expected dimensions of the array,
 *              then the code will take the last value from the arrayLengths list and it will use it further.
 *          Example:
 *              Provided field type: String[][][].class
 *                  - we have a 3-dimensional array, we expect 3 values for the arrayLengths parameter
 *              Provided arrayLengths: {3,2}
 *                  - the arrayLengths list contains only 2 values, not 3 as expected
 *              In this case, the last value from the arrayLengths list (which is 2),
 *                  will be used as the length for the 3rd array level
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenerateValue {
    Class type() default String.class;

    String value() default "{STRING:5,10}";

    boolean isOptional() default false;

    String dateFormat() default TIMESTAMP;

    int[] arrayLengths() default {2};
}
