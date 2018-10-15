package tech.elfin.verifyuserdata.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tech.elfin.verifyuserdata.model.UserData;
import tech.elfin.verifyuserdata.model.UserDataResponse;
import tech.elfin.verifyuserdata.service.UserDataService;

/**
 * Контроллер для обработки REST-запросов вида http://host:port/api/verify.
 * Данные передаются в теле POST-запроса
 */
@RestController
@RequestMapping(value = "/api")
public class UserDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataService.class);

    @Autowired
    private UserDataService userDataService;

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public UserDataResponse verifyUserData(@RequestBody UserData userData) {
        LOGGER.info(userData.toString());
        return userDataService.verifyUserData(userData);
    }
}
