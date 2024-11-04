import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opsc7312cashsend.PushNotifications.ChatState
import com.example.opsc7312cashsend.PushNotifications.FcmApi
import com.example.opsc7312cashsend.PushNotifications.NotificationBody
import com.example.opsc7312cashsend.PushNotifications.SendMessageDto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class ChatViewModel : ViewModel() {
    var state by mutableStateOf(ChatState())
        private set

    private val api: FcmApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/") // Replace with your backend URL
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(FcmApi::class.java)

    fun onRemoteTokenChange(newToken: String) {
        state = state.copy(remoteToken = newToken)
    }

    fun onMessageChange(message: String) {
        state = state.copy(messageText = message)
    }

    fun sendMessage(isBroadcast: Boolean) {
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to = if (isBroadcast) null else state.remoteToken,
                notification = NotificationBody(
                    title = "New Message",
                    body = state.messageText
                )
            )
            try {
                if (isBroadcast) {
                    api.broadcast(messageDto)
                } else {
                    api.sendMessage(messageDto)
                }
                state = state.copy(messageText = "")
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}