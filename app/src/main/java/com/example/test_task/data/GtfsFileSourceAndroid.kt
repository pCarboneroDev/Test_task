package com.example.test_task.data

import android.content.Context
import com.example.data.gtfsFileSource.GtfsFileSource
import java.io.InputStream

class GtfsFileSourceAndroid(private val context: Context): GtfsFileSource {
    override fun open(fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}