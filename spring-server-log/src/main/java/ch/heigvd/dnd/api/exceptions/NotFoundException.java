package ch.heigvd.dnd.api.exceptions;

import ch.heigvd.dnd.api.exceptions.ApiException;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-28T16:00:17.982Z")

public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
