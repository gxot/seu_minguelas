package com.example.seuminguelas.di

import android.content.Context
import androidx.room.Room
import com.example.seuminguelas.data.database.AppDatabase
import com.example.seuminguelas.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "seu_minguelas_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides fun provideUsuarioDao(db: AppDatabase): UsuarioDao = db.usuarioDao()
    @Provides fun provideLancheDao(db: AppDatabase): LancheDao = db.lancheDao()
    @Provides fun provideCarrinhoDao(db: AppDatabase): CarrinhoDao = db.carrinhoDao()
    @Provides fun providePedidoDao(db: AppDatabase): PedidoDao = db.pedidoDao()
}
