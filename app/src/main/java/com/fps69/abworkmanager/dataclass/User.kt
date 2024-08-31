package com.fps69.abworkmanager.dataclass

import java.util.UUID

data class User(
    val id : String = UUID.randomUUID().toString(),
    val userType:String?=null,
    val userId:String?=null,
    val userName:String?=null,
    val userEmail:String?=null,
    val userPassword:String?=null,
    val userImage:String?=null
)
