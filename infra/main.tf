provider "google" {
    project = var.project_id
    region = var.region
    zone = var.zone
    impersonate_service_account = var.tf_service_account
}

resource "google_pubsub_topic" "vilas_seeder" {
    name = var.seeder_pub_sub_topic
    project = var.project_id
    labels = {}
}

resource "google_pubsub_topic" "vilas_seeder_dlq" {
    name = var.seeder_dlq_pub_sub_topic
    project = var.project_id
    labels = {}
}

resource "google_pubsub_subscription" "vilas_seeder_pull_subscriptions" {
    name = var.seeder_pub_sub_subscription
    project = var.project_id
    topic  = var.seeder_pub_sub_topic
    ack_deadline_seconds        = 10
    retain_acked_messages       = false
    message_retention_duration  = "604800s"

    expiration_policy {
        ttl = ""
    }

    dead_letter_policy {
        dead_letter_topic     = google_pubsub_topic.vilas_seeder_dlq.id
        max_delivery_attempts = 5
    }

    retry_policy {
        minimum_backoff = "10s"
        maximum_backoff = "60s"
    }

    depends_on = [google_pubsub_topic.vilas_seeder, google_pubsub_topic.vilas_seeder_dlq]

}
