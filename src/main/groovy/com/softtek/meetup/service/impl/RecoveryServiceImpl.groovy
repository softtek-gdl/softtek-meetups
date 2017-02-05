package com.softtek.meetup.service.impl

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

import com.softtek.meetup.service.RecoveryService
import com.softtek.meetup.service.RestService
import com.softtek.meetup.repository.RegistrationCodeRepository
import com.softtek.meetup.model.RegistrationCode
import com.softtek.meetup.command.Command
import com.softtek.meetup.command.MessageCommand

@Service
class RecoveryServiceImpl implements RecoveryService {

  @Autowired
  RegistrationCodeRepository repository
  @Autowired
  RestService restService

  @Value('${server.name}')
  String serverName
  @Value('${emailer.template.register}')
  String template

  void sendConfirmationAccountToken(String email){
    RegistrationCode registrationCode = new RegistrationCode(email:email)  
    repository.save(registrationCode)
    Command command = new MessageCommand(email:email, template:template, url:"${serverName}${registrationCode.token}")
    restService.sendCommand(command)
  }

}