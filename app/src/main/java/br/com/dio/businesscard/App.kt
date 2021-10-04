package br.com.dio.businesscard

import android.app.Application
import br.com.dio.businesscard.data.roomDB.AppDatabase
import br.com.dio.businesscard.data.repository.BusinessCardRepository

class App : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusinessCardRepository(database.businessDao()) }
}