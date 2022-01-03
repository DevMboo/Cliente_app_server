package io.github.devMboo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class Internacionalizacao {
    /*
    *  Classe de formatação das mensagem de erros
    *  em caso de mal utilização na introdução de
    *  dados no banco de dados, para a tratativa
    *  mais agradavel para o usuário.
    * */
    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        messageSource.setDefaultLocale( Locale.getDefault() );
        return messageSource;
    }
    /*
    * Verifica o local do qual o usuário se
    * localiza, de acordo com seu sistema ope-
    * -racional, fazendo uma padronização da
    * mensagem exibida no console.
    * */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
