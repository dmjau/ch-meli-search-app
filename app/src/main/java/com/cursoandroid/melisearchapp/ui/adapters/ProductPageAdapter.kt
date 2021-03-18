package com.cursoandroid.melisearchapp.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.cursoandroid.melisearchapp.R.layout
import com.cursoandroid.melisearchapp.data.common.Constants
import com.cursoandroid.melisearchapp.domain.models.Attribute
import com.cursoandroid.melisearchapp.domain.models.PublicationType
import com.cursoandroid.melisearchapp.ui.activities.ProductPageActivity
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlinx.android.synthetic.main.product_item.view.*
import kotlin.math.roundToInt


class ProductPageAdapter : RecyclerView.Adapter<ProductPageAdapter.ViewHolder>() {

    private var dataList = listOf<Attribute>()

    fun setDataList(data: List<Attribute>) {
        dataList = data
    }

    /*
     * 4 metodos propios de un recycler.
     * ViewHolder: Armo el item.
     * onCreateViewHolder: "inflado" o creacion de cada item.
     * getItemCount: contabiliza el tamano de la lista.
     * onBindViewHolder: indico la correlacion de cada item con el listado de variables.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUrl: String){
            val url = imageUrl.replace("http:", "https:")
            Picasso.get().load(url).into(itemView.logo_product)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val publication = dataList[position]
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.currency = Currency.getInstance(publication.currency_id)
        val price = format.format(publication.price.roundToInt())

        holder.itemView.title.text = publication.title
        holder.itemView.price.text ="$ ${price.replace(",00","").replace("ARS","")}"

        holder.bind(publication.thumbnail)


        if(publication.listing_type_id ==  PublicationType.GOLD_PREMIUM){
            holder.itemView.ship_normally.visibility = View.VISIBLE
        }

        if (publication.shipping.free_shipping) {
            holder.itemView.free_shipping.visibility = View.VISIBLE
        }else{
            holder.itemView.free_shipping.visibility = View.INVISIBLE
        }

        holder.itemView.setOnClickListener {
            Intent(it.context, ProductPageActivity::class.java).apply {
                putExtra(Constants.PARAM_PUB, Gson().toJson(publication))
                it.context.startActivity(this)
            }
        }

    }
}