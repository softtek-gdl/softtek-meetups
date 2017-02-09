package com.softtek.meetup.service.impl

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

import com.softtek.meetup.service.RecoveryService
import com.softtek.meetup.service.RegistrationService
import com.softtek.meetup.service.RestService
import com.softtek.meetup.repository.RegistrationCodeRepository
import com.softtek.meetup.repository.UserRepository
import com.softtek.meetup.model.RegistrationCode
import com.softtek.meetup.model.User
import com.softtek.meetup.command.Command
import com.softtek.meetup.command.MessageCommand

@Service
class RecoveryServiceImpl implements RecoveryService {

  @Autowired
  RegistrationCodeRepository repository
  @Autowired
  UserRepository userRepository
  @Autowired
  RegistrationService registrationService
  @Autowired
  RestService restService

  @Value('${server.name}')
  String serverName
  @Value('${template.register.name}')
  String template
  @Value('${template.register.path}')
  String path

  void sendConfirmationAccountToken(String email){
    RegistrationCode registrationCode = new RegistrationCode(email:email)  
    repository.save(registrationCode)
    Command command = new MessageCommand(email:email, template:template, url:"${serverName}${path}${registrationCode.token}")
    restService.sendCommand(command)
  }

   User confirmAccountForToken(String token){
    User user = getUserByToken(token)
    if(!user) throw new UserNotFoundException(localeService.getMessage('exception.user.not.found'))
    user.enabled = true
    userRepository.save(user)
    user
  }

  User getUserByToken(String token){
    String email = registrationService.findEmailByToken(token)
    if(!email) throw new VetlogException(localeService.getMessage('exception.token.not.found'))
    User user = userRepository.findByEmail(email)
    user
  }

}