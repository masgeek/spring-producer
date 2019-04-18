package com.masgeek.producerservice.services

import com.masgeek.producerservice.ProducerServiceApplication
import com.masgeek.producerservice.entity.CustomMessage
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

import java.util.Random

@Service
class CustomMessageSender(private val rabbitTemplate: RabbitTemplate) {

    companion object {
        private val log = LoggerFactory.getLogger(CustomMessageSender::class.java)
    }

    @Scheduled(fixedDelay = 300L)
    fun sendMessage() {
        val message = CustomMessage("Hello there!", Random().nextInt(50), false)
        log.info("Sending message...")
        rabbitTemplate.convertAndSend(ProducerServiceApplication.EXCHANGE_NAME, ProducerServiceApplication.ROUTING_KEY, message)
    }
}
