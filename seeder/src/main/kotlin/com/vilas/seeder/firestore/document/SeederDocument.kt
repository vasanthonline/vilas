package com.vilas.seeder.firestore.document

import com.google.cloud.firestore.annotation.DocumentId
import com.google.cloud.spring.data.firestore.Document

@Document(collectionName = "seeder")
data class SeederDocument(
    @DocumentId
    val id: String? = null,
    val jobId: String? = null,
    val url: String? = null,
)
