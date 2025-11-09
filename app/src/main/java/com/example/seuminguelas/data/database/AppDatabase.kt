package com.example.seuminguelas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.seuminguelas.data.dao.*
import com.example.seuminguelas.data.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Usuario::class, Lanche::class, Carrinho::class, Pedido::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun lancheDao(): LancheDao
    abstract fun carrinhoDao(): CarrinhoDao
    abstract fun pedidoDao(): PedidoDao

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch(Dispatchers.IO) {
            }
        }
    }

    companion object {
        suspend fun populateDatabase(database: AppDatabase) {
            val usuarioDao = database.usuarioDao()
            val lancheDao = database.lancheDao()

            usuarioDao.inserir(
                Usuario(
                    nome = "admin",
                    senha = "admin123",
                    isAdmin = true
                )
            )

            lancheDao.inserir(
                Lanche(
                    nome = "Shawarma de Carne",
                    descricao = "Pão sírio, carne, batata frita, tomate, cebola",
                    preco = 25.00
                )
            )

            lancheDao.inserir(
                Lanche(
                    nome = "Shawarma de Frango",
                    descricao = "Pão sírio, frango, batata frita, tomate, cebola",
                    preco = 20.00
                )
            )

            lancheDao.inserir(
                Lanche(
                    nome = "Shawarma Misto",
                    descricao = "Pão sírio, carne, frango, batata frita, tomate, cebola",
                    preco = 30.00
                )
            )
        }
    }
}
