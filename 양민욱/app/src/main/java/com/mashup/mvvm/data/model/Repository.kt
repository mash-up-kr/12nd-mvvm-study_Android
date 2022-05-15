package com.mashup.mvvm.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonNames
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class Repository(
    val id: Int,
    val name: String,
    val owner: Owner,
    val language: String?,
    @Serializable(with = RepositoryDateSerializer::class)
    @JsonNames("updated_at") val updatedAt: Date
) : java.io.Serializable

@Serializer(forClass = Date::class)
object RepositoryDateSerializer : KSerializer<Date> {
    private const val DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    private val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.KOREA)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(dateFormat.format(value))
    }

    override fun deserialize(decoder: Decoder): Date {
        return try {
            dateFormat.parse(decoder.decodeString()) ?: Date()
        } catch (e: ParseException) {
            Date()
        }
    }
}