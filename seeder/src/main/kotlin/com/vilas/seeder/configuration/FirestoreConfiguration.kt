package com.vilas.seeder.configuration

import com.google.cloud.spring.data.firestore.mapping.FirestoreMappingContext
import com.google.cloud.spring.data.firestore.mapping.FirestorePersistentEntity
import com.google.cloud.spring.data.firestore.mapping.FirestorePersistentEntityImpl
import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.util.TypeInformation

@Configuration
@EnableReactiveFirestoreRepositories(basePackages = ["com.vilas.seeder.firestore"])
class FirestoreConfiguration(@Value("\${firestore.collection.prefix}") val collectionPrefix: String) {
    @Bean
    fun firestoreMappingContext(): FirestoreMappingContext {
        return VilasSeederFirestoreMappingContext(collectionPrefix)
    }
}

class VilasSeederFirestoreMappingContext(private val collectionPrefix: String) : FirestoreMappingContext() {
    override fun <T : Any> createPersistentEntity(typeInformation: TypeInformation<T>): FirestorePersistentEntity<*> {
        return PrefixedFirestorePersistenceEntity(typeInformation, collectionPrefix)
    }
}

class PrefixedFirestorePersistenceEntity<T>(
    information: TypeInformation<T>,
    private val collectionPrefix: String = "local"
) : FirestorePersistentEntityImpl<T>(information) {
    override fun collectionName(): String {
        return collectionPrefix.uppercase() + "_" + super.collectionName()
    }
}
