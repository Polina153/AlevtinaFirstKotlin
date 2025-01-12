package com.example.alevtinafirstkotlin.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.alevtinafirstkotlin.R
import com.example.alevtinafirstkotlin.databinding.ActivityMainBinding
import com.example.alevtinafirstkotlin.view.experiments.ThreadsFragment
import com.example.alevtinafirstkotlin.view.main.MainFragment
import com.example.alevtinafirstkotlin.view.main.MyBroadcastReceiver

class MainActivity : AppCompatActivity() {

    private val receiver = MyBroadcastReceiver()//MainBroadcastReceiver()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.getRoot()
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitAllowingStateLoss()
        }
        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        /*var clickListener: View.OnClickListener = object : View.OnClickListener {

        @RequiresApi(Build.VERSION_CODES.N)
        override fun onClick(v: View?) {
        try {
            val uri = URL(binding.url.text.toString())
            val handler = Handler() //Запоминаем основной поток
            Thread {
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET" //установка метода получения данных -- GET
                    urlConnection.readTimeout = 10000 //установка таймаута -- 10 000 миллисекунд
                    val reader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream)) //читаем данные в поток
                    val result = getLines(reader)

                    // Возвращаемся к основному потоку
                    handler.post {
                        binding.webview.loadData(result, "text/html; charset=utf-8", "utf-8")
                    }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
        }
        @RequiresApi(Build.VERSION_CODES.N)
               private fun getLines(reader: BufferedReader): String {
                   return reader.lines().collect(Collectors.joining("\n"))
               }
           }*/
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_threads -> {
                supportFragmentManager.beginTransaction()
                        .add(R.id.container, ThreadsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}