package com.fps69.abworkmanager.dataclass

import java.util.UUID

data class Works(
    val id : String = UUID.randomUUID().toString(),
    val workId : String? = null,
    val workTitle : String? = null,
    val workDesc : String? = null,
    val workPriority : String? = null,
    val workLastDate : String? = null,
    val workStatus : String? = null,
    var expanded : Boolean = false,
    val bossId : String? = null
)
