package com.vilas.seeder.firestore.repository

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository
import com.vilas.seeder.firestore.document.SeederDocument
import org.springframework.stereotype.Repository

@Repository
interface SeederRepository : FirestoreReactiveRepository<SeederDocument> {
}