package au.edu.utas.zhe4.babytracker.presentation.record.nappy

import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Nappy
import au.edu.utas.zhe4.babytracker.domain.NappyCons
import au.edu.utas.zhe4.babytracker.domain.createNappy
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases
import au.edu.utas.zhe4.babytracker.utils.CurrentTime
import au.edu.utas.zhe4.babytracker.utils.LocalDateTimeToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.TimeStringToLocalDateTime
import au.edu.utas.zhe4.babytracker.utils.randomUUID
import java.time.LocalDate

class NappyRecordViewModel(
    application: Application,
    useCases: UseCases,
) : BabyTrackerViewModel(
    application,
    useCases,
) {
    private var ID : String? = ""

    val nappyCons = MutableLiveData<String>(NappyCons.WET.toString())
    val nappyTime = MutableLiveData<String>(LongToLocalDateTimeString(CurrentTime()))
    val nappyPhoto = MutableLiveData<Uri>(Uri.EMPTY)
    var nappyNote = MutableLiveData<String>("")

    override fun setDate(year: Int, month: Int, day: Int) {
        val hour = TimeStringToLocalDateTime(nappyTime.value).hour
        val minute = TimeStringToLocalDateTime(nappyTime.value).minute

        val datetime = LocalDate.of(year, month, day).atTime(hour, minute)

        nappyTime.value = LocalDateTimeToLocalDateTimeString(datetime)
    }

    override fun setTime(hour: Int, minute: Int) {
        val year = TimeStringToLocalDateTime(nappyTime.value).year
        val month = TimeStringToLocalDateTime(nappyTime.value).month
        val day = TimeStringToLocalDateTime(nappyTime.value).dayOfMonth

        val date = LocalDate.of(year, month, day).atTime(hour, minute)

        nappyTime.value = LocalDateTimeToLocalDateTimeString(date)
    }

    fun updateID(id: String) {
        ID = id
    }

    fun updateNappyCons(npyCons: String) {
        nappyCons.value = npyCons
    }

    fun updateNappyTime(npyTime: String) {
        nappyTime.value = npyTime
    }

    fun updateNappyImage(npyPhoto: Uri) {
        nappyPhoto.value = npyPhoto
    }

    fun updateNappyNote(npyNote: String) {
        nappyNote.value = npyNote
    }

    fun saveOrUpdateToDatabase() {
        val npyObj : Nappy = createNappy(ID,
            nappyTime.value,
            nappyCons.value,
            getPathFromUri(application.baseContext, nappyPhoto.value!!),
            nappyPhoto.value!!.lastPathSegment,
            nappyNote.value)

        when(npyObj.id.isNullOrEmpty()) {
            true -> {
                npyObj.id = randomUUID()
                useCases.addNappy(npyObj)
            }
            else ->useCases.modifyNappy(npyObj)
        }
    }

    fun getPathFromUri(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.getContentResolver().query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }
}