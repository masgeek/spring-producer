package com.masgeek.producerservice

import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.amqp.core.BindingBuilder

import org.springframework.amqp.core.Binding
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@EnableScheduling
class ProducerServiceApplication {
    companion object {
        val EXCHANGE_NAME = "appExchange"
        val QUEUE_GENERIC_NAME = "appGenericQueue"
        val QUEUE_SPECIFIC_NAME = "appSpecificQueue"
        val ROUTING_KEY = "messages.key"
    }


    @Bean
    fun appExchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    fun appQueueGeneric(): Queue {
        return Queue(QUEUE_GENERIC_NAME)
    }

    @Bean
    fun appQueueSpecific(): Queue {
        return Queue(QUEUE_SPECIFIC_NAME)
    }

    @Bean
    fun declareBindingGeneric(): Binding {
        return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY)
    }

    @Bean
    fun declareBindingSpecific(): Binding {
        return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with(ROUTING_KEY)
    }


    // You can comment the two methods below to use the default serialization / deserialization (instead of JSON)
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = producerJackson2MessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

}

fun main(args: Array<String>) {
    runApplication<ProducerServiceApplication>(*args)
}

