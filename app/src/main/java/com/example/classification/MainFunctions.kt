package com.example.classification
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.classification.ml.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
class MainFunctions : AppCompatActivity() {
    lateinit var gallery: Button
    lateinit var image: Bitmap
    var normality: TextView? = null
    var imageView: ImageView? = null
    var result: TextView? = null
    var imageSize = 224
    var resultt: String? = null
    var norm = ""
    lateinit var add: Button
    lateinit var view: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_functions)
        add = findViewById(R.id.addrepobtn)
        view = findViewById(R.id.historybtn)
        gallery = findViewById(R.id.button2)
        normality = findViewById(R.id.normality)
        result = findViewById(R.id.result)
        imageView = findViewById(R.id.imageView)

        gallery.setOnClickListener(View.OnClickListener {
            val cameraIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, 1)
        })


        add.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Addarepp::class.java)
            startActivity(intent)
        })

        view.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ListReports::class.java)
            startActivity(intent)
            //replacecontaineer(listreport)

        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                image = (data!!.extras!!["data"] as Bitmap?)!!
                val dimension = Math.min(image!!.width, image!!.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                imageView!!.setImageBitmap(image)
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                resultt = classifyimages(image)
                result!!.text = resultt // type cell
                if (resultt == "Lymphocyte") {
                    norm = Lymphocyte(image) //normal
                    normality!!.text = norm
                } else if (resultt == "Basophil") {
                    norm = Basophil(image)
                    normality!!.text = norm
                } else if (resultt == "Eosinophil") {
                    norm = Eosinophil(image)
                    normality!!.text = norm
                } else if (resultt == "Erythroblast") {
                    norm = Erythroblast(image)
                    normality!!.text = norm
                } else if (resultt == "monocyte") {
                    norm = monocyte(image)
                    normality!!.text = norm
                } else if (resultt == "monocyte") {
                    norm = Neutrophil(image)
                    normality!!.text = norm
                } else if (resultt == "Myeloblast_abnormal") {
                    normality!!.text = "Abnormal"
                } else if (resultt == "Promyelocyte_abnormal") {
                    normality!!.text = "Abnormal"
                } else if (resultt == "Platelet_normal") {
                    normality!!.text = "Normal"
                } else if (resultt == "Immature Granulocytes_normal") {
                    normality!!.text = "Normal"
                }
            } else {
                val dat = data!!.data
                var image: Bitmap? = null
                try {
                    image = MediaStore.Images.Media.getBitmap(this.contentResolver, dat)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                imageView!!.setImageBitmap(image)
                image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
                resultt = classifyimages(image)
                result!!.text = resultt
                if (resultt == "Lymphocyte") {
                    norm = Lymphocyte(image)
                    normality!!.text = norm
                } else if (resultt == "Basophil") {
                    norm = Basophil(image)
                    normality!!.text = norm
                } else if (resultt == "Eosinophil") {
                    norm = Eosinophil(image)
                    normality!!.text = norm
                } else if (resultt == "Erythroblast") {
                    norm = Erythroblast(image)
                    normality!!.text = norm
                } else if (resultt == "monocyte") {
                    norm = monocyte(image)
                    normality!!.text = norm
                } else if (resultt == "Neutrophil") {
                    norm = Neutrophil(image)
                    normality!!.text = norm
                } else if (resultt == "Myeloblast_abnormal") {
                    normality!!.text = "Abnormal"
                } else if (resultt == "Promyelocyte_abnormal") {
                    normality!!.text = "Abnormal"
                } else if (resultt == "Platelet_normal") {
                    normality!!.text = "Normal"
                } else if (resultt == "Immature Granulocytes_normal") {
                    normality!!.text = "Normal"
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun classifyimages(image: Bitmap): String? {
        var res = ""
        try {
            val model = Classification.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }

            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Classification.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences = outputFeature0.floatArray
            //            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "Basophil",
                "Eosinophil",
                "Erythroblast",
                "Immature Granulocytes_normal",
                "Lymphocyte",
                "Monocyte",
                "Myeloblast_abnormal",
                "Neutrophil",
                "Platelet_normal",
                "Promyelocyte_abnormal"
            )
            result!!.text = classes[maxPos]
            res = classes[maxPos]


            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }

        return res
    }
    private fun Basophil(image: Bitmap?): String {
        var res = ""
        try {
            val model = Basophil1.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0: TensorBuffer =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val tensorimage = TensorImage(DataType.FLOAT32)
            tensorimage.load(image)
            val byteBuffer: ByteBuffer = tensorimage.getBuffer()
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Basophil1.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences: FloatArray = outputFeature0.getFloatArray()
            //            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Abnormal", "Normal")
            normality!!.text = classes[maxPos]
            res = classes[maxPos]
            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
        return res

    }
    private fun Eosinophil(image: Bitmap?): String {
        var res = ""
        try {
            val model = Esinophil1.newInstance(applicationContext)

//         Creates inputs for reference.
            // Creates inputs for reference.
            val inputFeature0: TensorBuffer =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val tensorimage = TensorImage(DataType.FLOAT32)
            tensorimage.load(image)
            val byteBuffer: ByteBuffer = tensorimage.getBuffer()
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Esinophil1.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences: FloatArray = outputFeature0.getFloatArray()
            //            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Abnormal", "Normal")

            normality!!.text = classes[maxPos]
            res = classes[maxPos]
            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
        return res

    }
    private fun Erythroblast(image: Bitmap?): String {
        var res = ""
        try {
            val model = Erythroblast1.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0: TensorBuffer =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val tensorimage = TensorImage(DataType.FLOAT32)
            tensorimage.load(image)
            val byteBuffer: ByteBuffer = tensorimage.getBuffer()
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Erythroblast1.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences: FloatArray = outputFeature0.getFloatArray()
            //            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Abnormal", "Normal")

            normality!!.text = classes[maxPos]
            res = classes[maxPos]
            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
        return res
    }
    private fun Lymphocyte(image: Bitmap?): String {
        var res = ""
        try {
            val model = Lymphocyte1.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0: TensorBuffer =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val tensorimage = TensorImage(DataType.FLOAT32)
            tensorimage.load(image)
            val byteBuffer: ByteBuffer = tensorimage.getBuffer()
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Lymphocyte1.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences: FloatArray = outputFeature0.getFloatArray()
            //            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Abnormal", "Normal")

            normality!!.text = classes[maxPos]
            res = classes[maxPos]
            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
        return res
    }
    private fun monocyte(image: Bitmap?): String {
        var res = ""
        try {
            val model = Monocyte1.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0: TensorBuffer =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val tensorimage = TensorImage(DataType.FLOAT32)
            tensorimage.load(image)
            val byteBuffer: ByteBuffer = tensorimage.getBuffer()
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Monocyte1.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences: FloatArray = outputFeature0.getFloatArray()
            //            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Abnormal", "Normal")

            normality!!.text = classes[maxPos]
            res = classes[maxPos]
            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
        return res
    }
    private fun Neutrophil(image: Bitmap?): String {
        var res = ""
        try {
            val model = Neutrophil1.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0: TensorBuffer =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val tensorimage = TensorImage(DataType.FLOAT32)
            tensorimage.load(image)
            val byteBuffer: ByteBuffer = tensorimage.getBuffer()
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: Neutrophil1.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences: FloatArray = outputFeature0.getFloatArray()
            //            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Abnormal", "Normal")
            normality!!.text = classes[maxPos]
            res = classes[maxPos]
            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
        return res
    }
}
