package com.merryblue.myvoicealarm.data.model

data class LibraryItem(
        var libraryId: String? = null,
        var title: String? = null,
        var isSelect: Boolean?,
        var path: String? = null
)