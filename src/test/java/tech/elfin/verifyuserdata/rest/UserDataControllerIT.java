package tech.elfin.verifyuserdata.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import tech.elfin.verifyuserdata.model.UserData;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserDataControllerIT {
    @Autowired
    private MockMvc mockMvc;

    private UserData validUserData;
    private UserData errorUserData;

    @Before
    public void setUp() throws Exception {
        validUserData = new UserData("Кузнецов Андрей Павлович", "4617", "540677",
                "wanderer_2006@mail.ru", "Комментарий");
        errorUserData = new UserData("Кузнецов Андрей Павлович", "1234", "540677",
                "wanderer_2006@mail.ru", "Комментарий");
    }

    /**
     * Интеграционный тест UserDataController -> UserDataService -> DaDataRestClient
     * На вход подаются данные клиента с корректным паспортом
     * @throws Exception
     */
    @Test
    public void validUserDataTest() throws Exception {
        mockMvc.perform(post("/api/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(validUserData)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("isPassportValid").value(true));
    }

    /**
     * Интеграционный тест UserDataController -> UserDataService -> DaDataRestClient
     * На вход подаются данные клиента с некорректным паспортом
     * @throws Exception
     */
    @Test
    public void errorUserDataTest() throws Exception {
        mockMvc.perform(post("/api/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(errorUserData)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("isPassportValid").value(false));
    }
}