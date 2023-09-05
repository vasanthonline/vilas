#### Configure backend ####
terraform {
    required_version = "1.5.6"
    backend "gcs" {}
    required_providers {
        google = {
            source = "hashicorp/google"
            version = "4.51.0"
        }
    }
}