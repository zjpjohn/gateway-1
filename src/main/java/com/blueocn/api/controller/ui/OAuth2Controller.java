package com.blueocn.api.controller.ui;

import com.blueocn.api.enums.MessageTypeEnum;
import com.blueocn.api.service.MatrixService;
import com.blueocn.api.service.OAuthService;
import com.blueocn.user.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Title: OAuth2Controller
 * Description:
 *
 * @author Yufan
 * @version 1.0.0
 * @since 2016-02-25 10:49
 */
@Controller
@RequestMapping("oauth2")
public class OAuth2Controller extends AbstractUIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2Controller.class);

    @Autowired
    private MatrixService matrixService;

    @Autowired
    private OAuthService oAuthService;

    /**
     * 登录页面, 接收来自开发者应用请求的 clientId, 返回登录页面
     */
    @RequestMapping(value = "authorize", method = RequestMethod.GET)
    public String oAuthLogin(@RequestParam(value = "client_id", required = false) String clientId, Model model) {
        try {
            if (isBlank(clientId) || !oAuthService.isValidClientId(clientId)) {
                setMessage(model, MessageTypeEnum.ERROR, "应用标识(Client Id) 无效, 请联系您的应用开发者.");
            }
        } catch (IOException e) {
            LOGGER.info("Kong 访问异常", e);
            setMessage(model, MessageTypeEnum.ERROR, e.getMessage());
        }
        return "oauth2/authorize";
    }

    @RequestMapping(value = "authorize", method = RequestMethod.POST)
    public String oAuthCertificate(@RequestParam("username") String username,
        @RequestParam("password") String password, @RequestParam("client_id") String client_id,
        @RequestParam(value = "scopes", required = false) String scopes,
        HttpServletResponse response, Model model) throws IOException {
        UserInfo loginUser = matrixService.login(username, password);
        model.addAttribute("client_id", client_id);
        model.addAttribute("scopes", scopes);
        if (loginUser != null) {
            model.addAttribute("loginUserId", loginUser.getUserId());
            return "oauth2/approval";
        } else {
            setMessage(model, MessageTypeEnum.ERROR, "用户登录失败, 请查证后重试.");
            return "oauth2/authorize";
        }
    }
}
