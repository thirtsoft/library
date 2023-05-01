package com.library.controller;

import com.library.controller.model.MessageModel;
import com.library.services.OrangeSMSService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class OrangeSMSController {

    @Autowired
    private OrangeSMSService orangeSMSService;

    @PostMapping(value = APP_ROOT + "/sendSMS")
    @ApiOperation(value = "Envoyer un sms",
            notes = "Cette méthode permet d'envoyer un message à un client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le message a été envoyé")
    })
    public void sendSMS(@RequestBody MessageModel messageModel) {
        orangeSMSService.sendSMS(messageModel.getMobile(), messageModel.getMessage());
    }

}


