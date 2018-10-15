package tech.elfin.verifyuserdata.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.elfin.verifyuserdata.model.UserData;
import tech.elfin.verifyuserdata.model.UserDataResponse;
import tech.elfin.verifyuserdata.rest.DaDataRestClient;

/**
 * Сервис обработки(проверки) данных, введенных клиентом в форме Camunda BPM
 */
@Service
public class UserDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataService.class);

    @Autowired
    private DaDataRestClient daDataRestClient;

    /**
     * Проверка введенных дпользователем данных
     * @param userData - пользовательские данные
     * @return - объект, содержащий признак валидности введенных данных
     */
    public UserDataResponse verifyUserData(UserData userData) {
        String passSeries = userData.getPassSeries().replaceAll(" ", "");
        String passNumber = userData.getPassNumber().replaceAll(" ", "");

        if (passElementValid(passSeries, 4) &&
                passElementValid(passNumber, 6) &&
                emailValid(userData.getEmail())) {
            LOGGER.info("Данные пользователя действительны");
            if (daDataRestClient.verifyPassport(passSeries, passNumber)) {
                LOGGER.info("Паспорт действителен");
                return new UserDataResponse(true);
            } else {
                LOGGER.info("Паспорт недействителен");
            }
        }
        LOGGER.info("Данные пользователя недействительны. Серия: " + passElementValid(passSeries, 4) +
                ", Номер: " + passElementValid(passNumber, 6)  +
                ", E-Mail: " + emailValid(userData.getEmail()));
        return new UserDataResponse(false);
    }

    /**
     * Проверка Серии и Номера паспорта на допустимость введенных символов (цифры)
     * и длинну (Серия - 4 цифры, Номер - 6 цифр)
     * @param passElement - введенные Серия или Номер
     * @param lengthElement - длинна эелемента в символах
     * @return - признак валидности элемента
     */
    private boolean passElementValid(String passElement, int lengthElement) {
        passElement = passElement.replaceAll(" ", "");
        Pattern pPassport = Pattern.compile("[0-9]{" + lengthElement + "," + lengthElement + "}");
        Matcher mPassElement = pPassport.matcher(passElement);
        boolean elementValid =  mPassElement.matches() && (passElement.length() == lengthElement);
        if (!elementValid) {
            if (lengthElement == 4) {
                LOGGER.info("Серия Паспорта недействительна!!!");
            } else {
                LOGGER.info("Серия Паспорта действительна");
            }
        }
        return elementValid;
    }

    /**
     * Проверка E-Mail-а на соответсвие стандарту
     * @param email - введенный e-mail
     * @return - признак валидности e-mail
     */
    private boolean emailValid(String email) {
        Pattern pEmail = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
        Matcher mEmail = pEmail.matcher(email);
        boolean elementValid = mEmail.matches();
        if (!elementValid) {
            LOGGER.info("Email не соответсвует стандарту!!!");
        }
        return elementValid;
    }
}
