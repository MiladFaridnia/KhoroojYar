package com.faridnia.khoroojyar.data.data_store.dto

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object TimeSettingsSerializer : Serializer<TimeSettings> {

    override val defaultValue: TimeSettings
        get() = TimeSettings()

    override suspend fun readFrom(input: InputStream): TimeSettings {
        return try {
            Json.decodeFromString(
                deserializer = TimeSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: TimeSettings, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = TimeSettings.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}