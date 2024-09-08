package com.fps69.abworkmanager.dataclass

data class Message(
    val token: String? = null,
    val notification: NotificationContent? = null,
    val data: Map<String, String>? = null
)
