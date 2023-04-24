package au.edu.utas.zhe4.babytracker.presentation.record.nappy

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import au.edu.utas.zhe4.babytracker.databinding.ActivityNappyRecordBinding
import au.edu.utas.zhe4.babytracker.databinding.FragmentNappyRecordCameraBinding
import au.edu.utas.zhe4.babytracker.domain.NappyCons
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModelFactory
import au.edu.utas.zhe4.babytracker.presentation.record.DatePickerFragment
import au.edu.utas.zhe4.babytracker.presentation.record.TimePickerFragment
import java.io.File
import java.util.Locale
import java.util.concurrent.ExecutorService


class NappyRecordActivity() : AppCompatActivity() {
    private lateinit var ui : ActivityNappyRecordBinding
    private lateinit var cameraUI : FragmentNappyRecordCameraBinding

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var viewModel: NappyRecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraUI = FragmentNappyRecordCameraBinding.inflate(layoutInflater)
        ui = ActivityNappyRecordBinding.inflate(layoutInflater)
        setContentView(ui.root)

        viewModel = ViewModelProvider(
            this,
            BabyTrackerViewModelFactory
        )[NappyRecordViewModel::class.java]

        updateViewModelByIntent(viewModel)

        showPage(viewModel)

        viewModel.nappyTime.observe(this, Observer {
            showNappyTime(viewModel)
        })

        viewModel.nappyPhoto.observe(this, Observer {
            showNappyImage(viewModel)
        })

        ui.rgNappyCons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                ui.rbNappyConsWet.id ->
                    viewModel.updateNappyCons(NappyCons.WET.toString())
                else ->
                    viewModel.updateNappyCons(NappyCons.WET_DIRTY.toString())
            }
        }

        ui.btTimePickerPopUp.setOnClickListener {
            val timePicker = TimePickerFragment(viewModel::setTime)
            timePicker.show(supportFragmentManager, "timePicker")

            val datePicker = DatePickerFragment(viewModel::setDate)
            datePicker.show(supportFragmentManager, "datePicker")
        }

        ui.rgNappyCons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                ui.rbNappyConsWet.id ->
                    viewModel.updateNappyCons(NappyCons.WET.toString())
                else ->
                    viewModel.updateNappyCons(NappyCons.WET_DIRTY.toString())
            }
        }

        ui.btCameraPopUp.setOnClickListener {
            setContentView(cameraUI.root)
            requestCameraPermissions()
        }

        ui.tietNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.updateNappyNote(s.toString())
            }

        })

        ui.btSave.setOnClickListener{
            viewModel.saveOrUpdateToDatabase()
            finish()
        }

        cameraUI.btImageCapture.setOnClickListener {
            takePhoto()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //cameraExecutor.shutdown()
    }

    private fun updateViewModelByIntent(viewModel: NappyRecordViewModel) {
        if (intent.hasExtra("id")) {
            val id = intent.getStringExtra("id")
            viewModel.updateID(id!!)
        }

        if (intent.hasExtra("nappyTime")) {
            val npyTime = intent.getStringExtra("nappyTime").toString()
            viewModel.updateNappyTime(npyTime)
        }
        if (intent.hasExtra("nappyCons")) {
            val npyCons = intent.getStringExtra("nappyCons").toString()
            viewModel.updateNappyCons(npyCons)
        }

        if (intent.hasExtra("nappyImg")) {
            val npyImg = intent.getStringExtra("nappyImg").toString()
            viewModel.updateNappyImage(Uri.fromFile(File(npyImg)))
        }

        if (intent.hasExtra("nappyNote")) {
            val npyNote = intent.getStringExtra("nappyNote").toString()
            viewModel.updateNappyNote(npyNote)
        }
    }

    private fun showPage(viewModel : NappyRecordViewModel) {
        showNappyTime(viewModel)
        showNappyCons(viewModel)
        showNappyImage(viewModel)
        showFeedingNote(viewModel)
    }

    private fun showNappyTime(viewModel : NappyRecordViewModel) {
        ui.tvTimePickerCurrentTime.text = viewModel.nappyTime.value!!
    }

    private fun showNappyCons(viewModel : NappyRecordViewModel) {
        when(viewModel.nappyCons.value) {
            NappyCons.WET.toString() -> {
                ui.rbNappyConsWet.isChecked = true
                ui.rbNappyConsWetDirty.isChecked = false
            }
            else -> {
                ui.rbNappyConsWet.isChecked = false
                ui.rbNappyConsWetDirty.isChecked = true
            }
        }
    }

    private fun showNappyImage(viewModel : NappyRecordViewModel) {
        ui.ivPhoto.setImageURI(viewModel.nappyPhoto.value)
    }

    private fun showFeedingNote(viewModel : NappyRecordViewModel) {
        ui.tietNote.setText(viewModel.nappyNote.value!!)
        ui.tietNote.setSelection(viewModel.nappyNote.value!!.length)
    }

    private fun requestCameraPermissions() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    viewModel.updateNappyImage(output.savedUri!!)

                    setContentView(ui.root)
                    //showNappyImage(viewModel)
                    //ui.ivPhoto.setImageURI(output.savedUri)

                    Log.d(TAG, msg)
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(cameraUI.pvCameraPreview.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

                if (Build.VERSION.SDK_INT >= 33) {
                    add(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }.toTypedArray()
    }

}