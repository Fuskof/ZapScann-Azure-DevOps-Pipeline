package smart.hub.helpers.generators;

import lombok.SneakyThrows;
import net.serenitybdd.core.Serenity;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class JsonObjectGenerator {

    /**
     * This method will try to build and return an object of type T, the class input parameter.
     * Specific combinations of field and value can be provided in the form of a map (constructed from feature file data tables),
     *  these values will be assigned to their respective fields in the resulting object.
     * For all other fields not specified in the input map (or if the map is missing) the parameters from each field's
     *  @GenerateJsonValue annotation will help generate a proper value.
     * If the input map contains the {root: full} key-value pair, then values for ALL fields will be generated,
     *  including for fields marked as optional in the model definition.
     *  (this {root: full} pair can be sent directly from the feature files or from the step methods)
     * <p>
     * The source model classes need to respect the following:
     *      - each field must have the @GenerateValue annotation
     *      - each field must be declared as an Object (to allow easy creation of negative tests)
     *      - the "real" class of each field must be specified in the "type" param of the @GenerateValue annotation
     *      - the "real" classes must be one of the following:
     *      - String.class
     *      - Integer.class
     *      - Long.class
     *      - Double.class
     *      - Boolean.class
     *      - Array classes (not lists), for example: String[].class, Long[][].class)
     *      - Enums
     *      - Other models defined in this same way
     *
     * <p>
     * The input map is constructed from entries in the format: | field | value |, from feature files.
     * The accepted forms for | value | are detailed in the JsonValueGenerator class.
     * For illustrating the capabilities/limitations of | field | consider the following json:
     *      {
     *          "correlationId" : "uuid",
     *          "merchantId" : intVal1,
     *          "timestamp" : "dateVal",
     *          "user" : {
     *              "firstName" : "strVal1",
     *              "lastName" : "strVal2",
     *              "addresses" : [
     *                  {
     *                      "streetName" : "strVal3",
     *                      "streetNumber" : intVal2
     *                  },
     *                  {
     *                      "streetName" : "strVal4",
     *                      "streetNumber" : intVal3
     *                  }
     *              ],
     *              "luckyNumberPairs" : [
     *                  [
     *                      no00,
     *                      no01,
     *                      no02
     *                  ],
     *                  [
     *                      no10,
     *                      no11,
     *                      no12
     *                  ]
     *              ]
     *          }
     *      }
     * A valid feature file input for the above json (considering just the fields) could be:
     *      | correlationId                 | uuid    |     - top level fields are specified naturally, with their names
     *      | merchantId                    | intVal1 |
     *      | timestamp                     | dateVal |
     *      | user.firstName                | strVal1 |     - lower level fields are specified through a field chain:
     *      | user.lastName                 | strVal2 |         firstLevel.secondLevel.thirdLevel and so on
     *      | user.addreses[0].streetName   | strVal3 |     - array values are specified through [position]
     *      | user.addreses[0].streetNumber | intVal2 |
     *      | user.addreses[1].streetName   | strVal4 |
     *      | user.addreses[1].streetNumber | intVal3 |
     *      | user.luckyNumberPairs[0][1]   | no01    |     - multidimensional arrays are also accessed through positions:
     *      | user.luckyNumberPairs[1][2]   | no12    |         fieldName[positionInLevel1][positionInLevel2]..[positionInLeveln]

     */
    @SneakyThrows
    public static <T> T generateJsonObject(Class<T> clazz, Map<String, String>... input) {
        if (clazz.isEnum()) {
            int x = new Random().nextInt(clazz.getEnumConstants().length);
            return clazz.getEnumConstants()[x];
        }
        boolean explicitValues = input.length == 1;
        T object = clazz.getDeclaredConstructor().newInstance();

        List<Field> fields = getAllFields(new ArrayList<>(), clazz);

        for (Field field : fields) {
            boolean hasFieldInputValue = true;
            JsonValueGenerator jsonValueGenerator = new JsonValueGenerator();
            field.setAccessible(true);
            String fieldName = field.getName();
            Class<?> fieldClass = field.getAnnotation(GenerateValue.class).type();
            Class<?> componentClass = fieldClass.getComponentType();
            String fieldDefaultValue = field.getAnnotation(GenerateValue.class).value();
            boolean fieldIsOptional = field.getAnnotation(GenerateValue.class).isOptional();
            String fieldDateFormat = field.getAnnotation(GenerateValue.class).dateFormat();
            int[] arrayLengths = field.getAnnotation(GenerateValue.class).arrayLengths();
            int fieldArrayLength = arrayLengths[0];

            Map<String, String> customInput = new HashMap<>();
            if (explicitValues) {
                Set<String> keys = input[0].keySet();
                for (String key : keys) {
                    if (key.equals(fieldName) || key.startsWith(fieldName + ".") || key.startsWith(fieldName + "[")) {
                        customInput.put(key, input[0].get(key));
                    }
                }
                if (customInput.size() > 0) {
                    Set<String> customKeys = customInput.keySet();
                    int customInputLength = customInput.size();
                    if (customKeys.iterator().next().startsWith(fieldName + ".")) {
                        Map<String, String> compoundObjectMap = new HashMap<>();
                        for (String key : customKeys) {
                            compoundObjectMap.put(key.replace(fieldName + ".", ""), customInput.get(key));
                        }
                        if (Objects.isNull(componentClass)) {
                            field.set(object, generateJsonObject(fieldClass, compoundObjectMap));
                        } else {
                            Object array = Array.newInstance(componentClass, fieldArrayLength);
                            for (int i = 0; i < fieldArrayLength; i++) {
                                Array.set(array, i, generateJsonObject(componentClass, compoundObjectMap));
                            }
                            field.set(object, array);
                        }
                    } else if (customKeys.iterator().next().startsWith(fieldName + "[")) {
                        int level = 0;
                        Object array = generateArrayWithCustomValue(componentClass, arrayLengths, level, customKeys,
                                customInputLength, fieldDefaultValue, customInput, jsonValueGenerator, fieldName);
                        field.set(object, array);
                    } else if (customKeys.iterator().next().equals(fieldName)) {
                        jsonValueGenerator.generateJsonValue(customInput.get(fieldName));
                        JsonValueGenerator.JsonFieldType generatedFieldType = jsonValueGenerator.getGeneratedFieldType();
                        switch (generatedFieldType) {
                            case STRING:
                                field.set(object, jsonValueGenerator.getStringValue());
                                break;
                            case INTEGER:
                                field.set(object, jsonValueGenerator.getIntegerValue());
                                break;
                            case LONG:
                                field.set(object, jsonValueGenerator.getLongValue());
                                break;
                            case DOUBLE:
                                field.set(object, jsonValueGenerator.getDoubleValue());
                                break;
                            case BOOLEAN:
                                field.set(object, jsonValueGenerator.getBooleanValue());
                                break;
                            case ARRAY:
                                field.set(object, jsonValueGenerator.getArrayValue());
                                break;
                            default:
                                field.set(object, null);
                        }
                    }
                } else {
                    hasFieldInputValue = false;
                }
            } else {
                hasFieldInputValue = false;
            }

            if (!hasFieldInputValue) {

                boolean isFullRequest = input.length > 0
                        && input[0].containsKey("root")
                        && input[0].get("root").equalsIgnoreCase("full");
                if (!fieldIsOptional || isFullRequest) {
                    jsonValueGenerator.setDatePattern(fieldDateFormat);
                    jsonValueGenerator.generateJsonValue(fieldDefaultValue);
                    if (Objects.isNull(componentClass)) {
                        if (fieldClass.equals(String.class)) {
                            field.set(object, jsonValueGenerator.getStringValue());
                        } else if (fieldClass.equals(Integer.class)) {
                            field.set(object, jsonValueGenerator.getIntegerValue());
                        } else if (fieldClass.equals(Long.class)) {
                            field.set(object, jsonValueGenerator.getLongValue());
                        } else if (fieldClass.equals(Double.class)) {
                            field.set(object, jsonValueGenerator.getDoubleValue());
                        } else if (fieldClass.equals(Boolean.class)) {
                            field.set(object, jsonValueGenerator.getBooleanValue());
                        } else {
                            if (!isFullRequest) {
                                field.set(object, generateJsonObject(fieldClass));
                            } else {
                                Map<String, String> map = new HashMap<>();
                                map.put("root", "full");
                                field.set(object, generateJsonObject(fieldClass, map));
                            }
                        }
                    } else {
                        int level = 0;
                        Object array = generateArrayWithDefaultValue(componentClass, arrayLengths, level, isFullRequest, jsonValueGenerator.getArrayValue());
                        field.set(object, array);
                    }
                }
            }
        }
        Serenity.setSessionVariable("generatedJsonObject").to(object);
        return object;
    }

    private static Object generateArrayWithCustomValue(Class componentClass, int[] arrayLengths, int level, Set<String> customKeys,
                                                       int customInputLength, String fieldDefaultValue, Map<String, String> customInput,
                                                       JsonValueGenerator jsonValueGenerator, String fieldName) {

        int fieldArrayLength;
        if (level > arrayLengths.length - 1) {
            fieldArrayLength = arrayLengths[arrayLengths.length - 1];
        } else {
            fieldArrayLength = arrayLengths[level];
        }
        Object array = Array.newInstance(Object.class, fieldArrayLength);

        if (!Objects.isNull(componentClass.getComponentType())) {
            level++;
            for (int i = 0; i < fieldArrayLength; i++) {
                String currentIndex = fieldName + String.format("[%s]", i);
                Array.set(array, i, generateArrayWithCustomValue(componentClass.getComponentType(), arrayLengths, level, customKeys,
                        customInputLength, fieldDefaultValue, customInput, jsonValueGenerator, currentIndex));
            }
            return array;
        } else {
            Set<String> uniqueArrayEntries = new HashSet<>();
            for (String key : customKeys) {
                uniqueArrayEntries.add(key.split("\\.")[0]);
            }
            if (uniqueArrayEntries.size() > fieldArrayLength) {
                fieldArrayLength = customInputLength;
            }

            generateArrayLoop:
            for (int i = 0; i < fieldArrayLength; i++) {
                String currentIndex = fieldName + String.format("[%s]", i);
                boolean isValueSpecifiedInInput = false;
                String value = fieldDefaultValue.startsWith("{[")
                        ? "{" + fieldDefaultValue.split("]")[1]
                        : fieldDefaultValue;
                getCustomValueLoop:
                for (String entry : uniqueArrayEntries) {
                    if (entry.contains(currentIndex)) {
                        isValueSpecifiedInInput = true;
                        Map<String, String> arrayEntry = new HashMap<>();
                        for (String key : customKeys) {
                            if (key.contains(entry + ".")) {
                                arrayEntry.put(key.replace(entry + ".", ""), customInput.get(key));
                            }
                        }
                        if (arrayEntry.size() == 0) {
                            value = customInput.get(entry);
                        } else {
                            Array.set(array, i, generateJsonObject(componentClass, arrayEntry));
                            continue generateArrayLoop;
                        }
                        break getCustomValueLoop;
                    }
                }
                if (!isValueSpecifiedInInput) {
                    if (!(componentClass.equals(String.class)
                            || componentClass.equals(Integer.class)
                            || componentClass.equals(Long.class)
                            || componentClass.equals(Double.class)
                            || componentClass.equals(Boolean.class))) {
                        Array.set(array, i, generateJsonObject(componentClass));
                        continue generateArrayLoop;
                    }
                }
                jsonValueGenerator.generateJsonValue(value);
                JsonValueGenerator.JsonFieldType generatedFieldType = jsonValueGenerator.getGeneratedFieldType();
                switch (generatedFieldType) {
                    case STRING:
                        Array.set(array, i, jsonValueGenerator.getStringValue());
                        break;
                    case INTEGER:
                        Array.set(array, i, jsonValueGenerator.getIntegerValue());
                        break;
                    case LONG:
                        Array.set(array, i, jsonValueGenerator.getLongValue());
                        break;
                    case DOUBLE:
                        Array.set(array, i, jsonValueGenerator.getDoubleValue());
                        break;
                    case BOOLEAN:
                        Array.set(array, i, jsonValueGenerator.getBooleanValue());
                        break;
                    default:
                        Array.set(array, i, null);
                }
            }
            return array;
        }
    }

    private static Object generateArrayWithDefaultValue(Class componentClass, int[] arrayLengths, int level, boolean isFullRequest, Object generatedArrayValue) {
        int fieldArrayLength;
        if (level > arrayLengths.length - 1) {
            fieldArrayLength = arrayLengths[arrayLengths.length - 1];
        } else {
            fieldArrayLength = arrayLengths[level];
        }
        Object array = Array.newInstance(Object.class, fieldArrayLength);

        if (!Objects.isNull(componentClass.getComponentType())) {
            level++;
            for (int i = 0; i < fieldArrayLength; i++) {
                Array.set(array, i, generateArrayWithDefaultValue(componentClass.getComponentType(), arrayLengths, level, isFullRequest, generatedArrayValue));
            }
            return array;
        } else {
            if (componentClass.equals(String.class)
                    || componentClass.equals(Integer.class)
                    || componentClass.equals(Long.class)
                    || componentClass.equals(Double.class)
                    || componentClass.equals(Boolean.class)) {
                return generatedArrayValue;
            } else {
                for (int i = 0; i < fieldArrayLength; i++) {
                    Array.set(array, i, generateJsonObject(componentClass));
                    if (!isFullRequest) {
                        Array.set(array, i, generateJsonObject(componentClass));
                    } else {
                        Map<String, String> map = new HashMap<>();
                        map.put("root", "full");
                        Array.set(array, i, generateJsonObject(componentClass, map));
                    }
                }
                return array;
            }
        }
    }

    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }
}