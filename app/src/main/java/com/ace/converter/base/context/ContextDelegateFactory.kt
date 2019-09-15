package com.ace.converter.base.context

import android.content.Context

object ContextDelegateFactory {

    fun create(context: Context) = ContextContextDelegate(context)
}