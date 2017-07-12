package commaciejprogramuje.facebook.edu_calculator;

/**
 * Created by m.szymczyk on 2017-07-12.
 */

enum Operation {
    NONE(""), ADD("+"), SUBSTRACT("-"), DIVIDE("/"), MULTIPLY("*"), SQRT("SQRT"), PERCENT("%");

    private final String key;

    private Operation(String key) {
        this.key = key;
    }

    public static Operation operationFromKey(String key) {
        for (Operation operation : values()) {
            if(operation.key.equals(key)) {
                return operation;
            }
        }
        return NONE;
    }
}
