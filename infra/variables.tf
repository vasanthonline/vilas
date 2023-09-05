variable "project_id" {
    type = string
}

variable "region" {
    type = string
}

variable "zone" {
    type = string
}

variable "env" {
    type = string
}

variable "tf_service_account" {
    type = string
}

variable "seeder_pub_sub_topic" {
    type = string
}

variable "seeder_dlq_pub_sub_topic" {
    type = string
}

variable "seeder_pub_sub_subscription" {
    type = string
}
