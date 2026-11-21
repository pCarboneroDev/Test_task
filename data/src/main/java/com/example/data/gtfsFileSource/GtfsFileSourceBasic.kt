package com.example.data.gtfsFileSource

import java.io.File
import java.io.InputStream

class GtfsFileSourceBasic(private val directory: File): GtfsFileSource {
    override fun open(fileName: String): InputStream {
        return File(directory, fileName).inputStream()
    }
}