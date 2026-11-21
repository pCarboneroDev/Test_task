package com.example.data.gtfsFileSource

import java.io.InputStream

interface GtfsFileSource {
    fun open(fileName: String): InputStream
}