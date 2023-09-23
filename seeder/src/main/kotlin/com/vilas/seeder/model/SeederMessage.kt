package com.vilas.seeder.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SeederMessage(
    @JsonProperty("document_id")
    val documentId: String?,

    val url: String?,

    @JsonProperty("job_id")
    val jobId: String?
)
