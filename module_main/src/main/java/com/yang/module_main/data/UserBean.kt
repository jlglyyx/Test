package com.yang.module_main.data


/**
 * @ClassName UserBean
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/23 14:07
 */
data class UserBean(

    val id: Int? = null,
    val level: Int = 0,
    val type: Int = 0,
    val updatetime: String? = null,
    val createtime: String? = null,
    val useraccount: String,
    val username: String? = null,
    val userpassword: String
)
//data class UserBean(var userName:String,var password:String)