package commaciejprogramuje.facebook.edu_calculator;

import java.io.Serializable;


enum Operation implements Serializable {
    NONE(""), ADD("+"), SUBSTRACT("-"), DIVIDE("/"), MULTIPLY("*"), SQRT("SQRT"), PERCENT("%"), SIN("SIN"), COS("COS"), TAN("TAN"), POW("POW");

    private final String key;

    Operation(String key) {
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
