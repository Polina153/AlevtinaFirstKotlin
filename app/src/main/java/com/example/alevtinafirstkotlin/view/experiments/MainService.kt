import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.alevtinafirstkotlin.view.experiments.TEST_BROADCAST_INTENT_FILTER
import com.example.alevtinafirstkotlin.view.experiments.THREADS_FRAGMENT_BROADCAST_EXTRA

private const val TAG = "MainServiceTAG"
const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"

class MainService(name: String = "MainService") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        //createLogMessage("onHandleIntent ${intent?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")
        intent?.let {
            sendBack(it.getStringExtra(MAIN_SERVICE_STRING_EXTRA) ?: "Nil")
        }
    }

    private fun sendBack(result: String) {
        val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA, result)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    override fun onCreate() {
        createLogMessage("onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLogMessage("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        createLogMessage("onDestroy")
        super.onDestroy()
    }

    //Выводим уведомление в строке состояния
    private fun createLogMessage(message: String) {
        Log.d(TAG, message)
    }
}
