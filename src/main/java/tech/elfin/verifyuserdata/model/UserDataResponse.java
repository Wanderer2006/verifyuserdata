package tech.elfin.verifyuserdata.model;

import java.io.Serializable;

/**
 * POJO-класс для сериализации в JSON-ответ
 */
public class UserDataResponse implements Serializable {
    private boolean isPassportValid;

    public UserDataResponse(boolean isPassportValid) {
        this.isPassportValid = isPassportValid;
    }

    public boolean getIsPassportValid() {
        return isPassportValid;
    }

    public void setIsPassportValid(boolean isPassportValid) {
        this.isPassportValid = isPassportValid;
    }
}
