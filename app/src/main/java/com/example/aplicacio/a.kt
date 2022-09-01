package com.example.aplicacio

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.io.ByteArrayOutputStream

class a {
    init {
        val imageView: ImageView? = null
        val bitmap = (imageView!!.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val mountainsRef: StorageReference? = null
        val uploadTask = mountainsRef!!.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...
        }
    }
}