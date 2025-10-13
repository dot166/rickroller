package com.j.rickroller

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RestrictTo
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@RestrictTo(RestrictTo.Scope.LIBRARY)
class ShareBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)
        view.findViewById<View?>(R.id.button1)?.setOnClickListener { v ->
            // User chooses the "Send URL" action. Share the video URL
            val sendIntent = Intent(Intent.ACTION_SEND)

            //set rickroll url
            sendIntent.putExtra(Intent.EXTRA_TEXT, Config.getURL(v.context))

            sendIntent.setType("text/plain")

            // Show the Sharesheet
            startActivity(Intent.createChooser(sendIntent, null))
        }
        view.findViewById<View?>(R.id.button2)?.setOnClickListener { v ->
            val context: Context = requireContext()
            val outFile = File(context.cacheDir, "rickroll.mp4")

            // Copy from res/raw to cache
            context.resources.openRawResource(R.raw.rickroll).use { input ->
                FileOutputStream(outFile).use { output ->
                    input.copyTo(output)
                }
            }

            // Get a content URI
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                outFile
            )

            // Share intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "video/mp4"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            // Launch chooser
            context.startActivity(
                Intent.createChooser(shareIntent, "Share Rickroll Video")
            )

            // Clean up after 1 minute (best-effort)
            CoroutineScope(Dispatchers.IO).launch {
                delay(60_000L) // 60 seconds
                if (outFile.exists()) {
                    outFile.delete()
                }
            }
        }
        return view
    }

    companion object {
        const val TAG: String = "ShareBottomSheet"
    }
}
