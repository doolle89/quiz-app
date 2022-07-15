package com.example.quizapp.repository.api.model
import com.google.gson.annotations.SerializedName


internal data class Kahoot(
    val uuid: String? = null,
    val language: String? = null,
    val creator: String? = null,
    @SerializedName("creator_username") val creatorUsername: String? = null,
    val compatibilityLevel: Int = -1,
    @SerializedName("creator_primary_usage") val creatorPrimaryUsage: String? = null,
    val folderId: String? = null,
    val themeId: String? = null,
    val visibility: Int = -1,
    val audience: String? = null,
    val title: String? = null,
    val description: String? = null,
    val quizType: String? = null,
    val cover: String? = null,
    val coverMetadata: CoverMetadata? = null,
    val questions: List<Question>? = null,
    val metadata: Metadata? = null,
    val resources: String? = null,
    val slug: String? = null,
    val languageInfo: LanguageInfo? = null,
    val inventoryItemIds: List<Any>? = null,
    val isValid: Boolean = false,
    val type: String? = null,
    val created: Long = -1,
    val modified: Long = -1
)

internal data class CoverMetadata(
    val id: String? = null,
    val contentType: String? = null,
    val resources: String? = null
)

internal data class Question(
    val type: String? = null,
    val question: String? = null,
    val time: Int? = null,
    val points: Boolean? = null,
    val pointsMultiplier: Int? = null,
    val choices: List<Choice>? = null,
    val image: String? = null,
    val imageMetadata: ImageMetadata? = null,
    val resources: String? = null,
    val video: Video? = null,
    val questionFormat: Int = -1,
    val languageInfo: LanguageInfo? = null,
    val media: List<Any>? = null
)

internal data class Metadata(
    val resolution: String? = null,
    val moderation: Moderation? = null,
    val access: Access? = null,
    val duplicationProtection: Boolean = false,
    val lastEdit: LastEdit? = null
)

internal data class LanguageInfo(
    val language: String? = null,
    val lastUpdatedOn: Long = 0,
    val readAloudSupported: Boolean = false
)

internal data class Choice(
    val answer: String? = null,
    val correct: Boolean? = null,
    val languageInfo: LanguageInfo? = null
)

internal data class ImageMetadata(
    val id: String? = null,
    val contentType: String? = null,
    val effects: List<Any?>? = null,
    val resources: String? = null
)

internal data class Video(
    val id: String? = null,
    val startTime: Double = -1.0,
    val endTime: Double = -1.0,
    val service: String? = null,
    val fullUrl: String? = null
)

internal data class Moderation(
    val flaggedTimestamp: Int = -1,
    val timestampResolution: Long = -1,
    val resolution: String? = null
)

internal data class Access(
    val groupRead: List<String?>? = null,
    val folderGroupIds: List<Any?>? = null,
    val features: List<String?>? = null
)

internal data class LastEdit(
    val editorUserId: String? = null,
    val editorUsername: String? = null,
    val editTimestamp: Long = -1
)