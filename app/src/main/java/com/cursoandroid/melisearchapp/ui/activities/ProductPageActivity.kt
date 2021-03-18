package com.cursoandroid.melisearchapp.ui.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.cursoandroid.melisearchapp.R
import com.cursoandroid.melisearchapp.ui.adapters.PictureAdapter
import com.cursoandroid.melisearchapp.data.common.ConnectivityCheck
import com.cursoandroid.melisearchapp.data.common.Constants
import com.cursoandroid.melisearchapp.domain.models.Attribute
import com.cursoandroid.melisearchapp.ui.viewmodels.ProductPageViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_product_page.*
import kotlinx.android.synthetic.main.product_item.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt
/*
 * Activity del detalle de la publicacion.
 */
class ProductPageActivity : AppCompatActivity() {

    private lateinit var productPageViewModel: ProductPageViewModel
    private  var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_page)

        productPageViewModel = ViewModelProvider(this).get(ProductPageViewModel::class.java)

        setObservers()

        val iconBack: ImageButton = findViewById(R.id.ic_back)
        setBackButton(iconBack)

        validateIntent()
    }

    private fun validateIntent() {
        if (intent.extras!!.containsKey(Constants.PARAM_PUB)) {
            val detailPublication: Attribute =
                Gson().fromJson(
                        intent.extras!!.getString(Constants.PARAM_PUB), Attribute::class.java
                )
            populatePublication(detailPublication)
        }
    }

    private fun setBackButton(iconBack: ImageButton) {
        iconBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun populatePublication(detailPublication: Attribute) {
        val titleProduct: TextView = findViewById(R.id.title_product)
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        var price = format.format(detailPublication.price.roundToInt())
        val priceProduct: TextView = findViewById(R.id.price_product)
        val linkPublicationPermanent = detailPublication.permalink

        titleProduct.text = detailPublication.title
        format.currency = Currency.getInstance(detailPublication.currency_id)
        price = price.replace(",00", "").replace("ARS", "")
        priceProduct.text = price

        if (ConnectivityCheck.verifyConnection(applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)) {
            val container: View = findViewById(R.id.containerDetailProduct)

            snackbar = Snackbar.make(container, getString(R.string.loading), Snackbar.LENGTH_LONG)

            snackbar!!.show()
            setCondition(detailPublication.condition, detailPublication.sold_quantity)
            setDescription(detailPublication.id)
            setCarousel(detailPublication.id)

        } else {
            Toast.makeText(applicationContext, getString(R.string.no_connection), Toast.LENGTH_LONG).show()
        }

        setBuyButton(linkPublicationPermanent)
    }

    private fun setCondition(conditionPublication: String, soldQuantity: Int) {
        val condition : TextView = findViewById(R.id.condition)
        if (conditionPublication == "new") {
            condition.text = "Nuevo - $soldQuantity vendidos"
        } else {
            condition.text = "$soldQuantity vendidos"
        }
    }

    private fun setDescription(idPublication: String) {
        if (productPageViewModel.itemDetailProduct.value == null) {
            productPageViewModel.getDetailProduct(idPublication)
        }
    }

    private fun setCarousel(idPublication: String) {
        if (productPageViewModel.item.value == null) {
            productPageViewModel.getItems(idPublication)
        }
    }

    private fun setBuyButton(url_permaLink: String) {
        button_buy_detail.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url_permaLink))
            startActivity(browserIntent)
        }
    }

    private fun setObservers() {

        productPageViewModel.message.observe(this, Observer {
            Toast.makeText(applicationContext, getString(it), Toast.LENGTH_LONG)
        })

        productPageViewModel.item.observe(this, Observer {
            val imagesProduct: ViewPager = findViewById(R.id.image_product)
            val pictureAdapter = PictureAdapter(it.pictures)

            imagesProduct.setAdapter(pictureAdapter).also {
                if (snackbar != null) {
                    snackbar!!.dismiss()
                }
            }
        })

        productPageViewModel.itemDetailProduct.observe(this, Observer {
            val descriptionProduct: TextView = findViewById(R.id.description_product)
            descriptionProduct.text = it[0].plain_text
        })

        productPageViewModel.itemDetailProduct.observe(this, Observer {
            val descriptionProduct: TextView = findViewById(R.id.description_product)
            descriptionProduct.text = it[0].plain_text
        })
    }

}