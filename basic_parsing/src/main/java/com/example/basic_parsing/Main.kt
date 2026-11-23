package com.example.basic_parsing

import com.example.data.GtfsDatasource
import com.example.data.gtfsFileSource.GtfsFileSourceBasic
import com.example.data.repositoriesImpl.DepartureRepositoryImpl
import com.example.domain.entities.Departure
import com.example.domain.entities.params.NextThreeDeparturesParams
import com.example.domain.usecases.GetNextThreeDeparturesUseCase
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

suspend fun main(args: Array<String>) {
    try {
        if(args.isEmpty()){
            println("You must pass the path of the dataset (file where the unzipped .txt files are located)")
        }

        val path = File(args[0]);

        val datasource = GtfsDatasource(GtfsFileSourceBasic(path))
        val repo = DepartureRepositoryImpl(datasource)
        val useCase = GetNextThreeDeparturesUseCase(repo);

        val time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS)
        val date = LocalDate.now()

        val departures = useCase.call(NextThreeDeparturesParams(
            308,time, date
        ))

        println(generateMarkdown("Plaza Elíptica 4", departures, time))
    } catch (e: NoSuchFileException) {
        println("Error: File not found. Make sure the unzipped files are in the path")

    } catch (e: FileNotFoundException) {
        println("Error: File not found. Make sure the unzipped files are in the path")
    } catch (e: Exception){
        println("Unexpected error")
    }
}

fun generateMarkdown(
    stopName: String,
    departures: List<Departure>,
    time: LocalTime
): String {

    return buildString {
        appendLine("# Next departures")
        appendLine()
        appendLine("**Target Stop:** $stopName")
        appendLine()
        appendLine("**Current time: ${time}**")
        appendLine()

        appendLine("| Time | Route             |")
        appendLine("|------|-------------------|")

        departures.forEach { departure ->

            appendLine(
                "| ${departure.departureTime} | " +
                        "${departure.routeName} | "
            )
        }
    }
}