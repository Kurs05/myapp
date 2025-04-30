package com.example.myapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.myapp.data.AppDatabase
import com.example.myapp.data.BookEntity
import com.example.myapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import android.text.method.ScrollingMovementMethod


class MainActivity : AppCompatActivity() {
    var counter = 0
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.bookTextView.text = "Это текст из кода"
        val db = AppDatabase.getDb(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.bookTextView.movementMethod = ScrollingMovementMethod()
        lifecycleScope.launch {
            val book = db.bookDao().getFirstBook()
            if (book != null) {
                binding.bookTextView.text = book.content
            } else {
                binding.bookTextView.text = "Книга не найдена"
            }
        }
        binding.addText.setOnClickListener {
            val book =BookEntity(0,"хуй","1. Предыстория развития темы.\n" +
                    "В современных условиях обработки информации телефонные справочники остаются важным инструментом для управления контактными данными. С развитием цифровых технологий традиционные бумажные справочники были заменены электронными системами, которые позволяют хранить и обрабатывать большие объемы данных, а также обеспечивать удобный и быстрый доступ к информации. Внедрение баз данных и автоматизированных информационных систем сделало возможным эффективное управление контактными сведениями, что особенно актуально для организаций, корпоративных пользователей и даже частных лиц.\n" +
                    "2. Область проекта и предметная область\n" +
                    "Проект относится к области информационных систем и баз данных, направленных на автоматизацию процессов управления контактной информацией. Он охватывает такие аспекты, как учет пользователей, хранение контактных данных (телефоны, адреса, email), управление группами пользователей, а также ведение истории звонков и сообщений. Современные телефонные справочники не только обеспечивают поиск информации, но и позволяют интегрироваться с различными сервисами связи, что делает их удобным инструментом в работе с клиентами и партнерами.\n" +
                    "3. Обоснование выбора темы \n" +
                    "Выбор данной темы обусловлен актуальностью задачи упорядоченного хранения и обработки контактных данных. Несмотря на наличие множества существующих решений, многие компании и частные пользователи сталкиваются с проблемами, связанными с дублированием контактов, сложностью управления группами пользователей и отсутствием централизованного хранения данных. Разработка собственной информационной системы телефонного справочника позволит предложить адаптируемое и масштабируемое решение, отвечающее конкретным требованиям пользователей.\n")
            lifecycleScope.launch {
                db.bookDao().insertBook(book)
                binding.bookTextView.text = book.content
            }.start()

             }
    }

    override fun onStart()
    {
        super.onStart()
        Log.d("MyLogAct","onStart")
    }
    override fun onPause()
    {
        super.onPause()
        Log.d("MyLogAct","onPause")
    }
    override fun onDestroy()
    {
        super.onDestroy()
        Log.d("MyLogAct","onDestroy")
    }


}
