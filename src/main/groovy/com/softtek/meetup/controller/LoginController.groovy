package com.softtek.meetup.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller

import com.softtek.meetup.service.LocaleService

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller
class LoginController {

  @Autowired
  LocaleService localeService

  Logger log = LoggerFactory.getLogger(this.class)

  @RequestMapping("/login")
  ModelAndView login(@RequestParam Optional<String> error){
    log.info "Calling login"
    ModelAndView modelAndView = new ModelAndView('login/login')
    if(error.isPresent()){
      modelAndView.addObject('error', localeService.getMessage('login.invalid.credentials'));
    }
    modelAndView
  }

}

