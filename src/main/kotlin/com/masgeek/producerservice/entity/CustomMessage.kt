package com.masgeek.producerservice.entity

import com.fasterxml.jackson.annotation.JsonProperty

import java.io.Serializable

class CustomMessage(@param:JsonProperty("text") val text: String,
                    @param:JsonProperty("priority") val priority: Int,
                    @param:JsonProperty("secret") val isSecret: Boolean) : Serializable {

    override fun toString(): String {
        return "CustomMessage{" +
                "text='" + text + '\''.toString() +
                ", priority=" + priority +
                ", secret=" + isSecret +
                '}'.toString()
    }
}
