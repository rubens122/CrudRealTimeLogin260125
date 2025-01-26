package com.example.crudrealtimelogin260125.utils

fun String.encodeEmail()=this.replace("@", "_AT_").replace(".", "_DOT_")
