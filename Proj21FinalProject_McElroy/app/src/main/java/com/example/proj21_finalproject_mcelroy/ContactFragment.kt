package com.example.proj21_finalproject_mcelroy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proj21_finalproject_mcelroy.databinding.FragmentContactBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements and set text/image resources
        binding.imageContact.setImageResource(R.drawable.contact_image)

        binding.buttonSubmit.setOnClickListener {
            val userName = binding.editTextName.text.toString().trim()
            val userEmail = binding.editTextEmail.text.toString().trim()
            val message = binding.editTextMessage.text.toString().trim()

            sendEmail(userName, userEmail, message)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun sendEmail(userName: String, userEmail: String, message: String) {
        // Create a JSONObject with the email data
        val jsonData = JSONObject().apply {
            put("service_id", "contact_service")
            put("template_id", "contact_form")
            put("user_id", "O5Y3KQsezL7BVPQ7S")
            put("template_params", JSONObject().apply {
                put("user_name", userName)
                put("user_email", userEmail)
                put("message", message)
            })
        }

        // Create a coroutine to make the network call asynchronously
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Create a URL object with the EmailJS API endpoint
                val url = URL("https://api.emailjs.com/api/v1.0/email/send")

                // Create an HttpURLConnection object
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "POST"
                urlConnection.doOutput = true

                // Write JSON data to the output stream
                val outputStream: OutputStream = urlConnection.outputStream
                val writer = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                writer.write(jsonData.toString())
                writer.flush()
                writer.close()
                outputStream.close()

                // Get the response from the server
                val responseCode = urlConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Email sent successfully
                    Log.d("EmailJS", "Email sent successfully")
                } else {
                    // Error handling
                    Log.e("EmailJS", "Error sending email. Response code: $responseCode")
                }
            } catch (e: Exception) {
                // Exception handling
                Log.e("EmailJS", "Exception: ${e.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

