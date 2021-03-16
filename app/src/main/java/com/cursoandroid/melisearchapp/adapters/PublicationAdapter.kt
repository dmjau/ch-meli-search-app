package com.cursoandroid.melisearchapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.cursoandroid.melisearchapp.R.layout
import com.cursoandroid.melisearchapp.common.Constants
import com.cursoandroid.melisearchapp.data.models.Attribute
import com.cursoandroid.melisearchapp.data.models.PublicationType
import com.cursoandroid.melisearchapp.ui.publication.DetailPublicationActivity
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlinx.android.synthetic.main.publication.view.*
import kotlin.math.roundToInt


class PublicationAdapter : RecyclerView.Adapter<PublicationAdapter.ViewHolder>() {

    private var dataList = listOf<Attribute>()

    fun setDataList(data: List<Attribute>) {
        dataList = data
    }


    //4 metodos propios de un recycler. ViewHolder: Armo el item. onCreateViewHolder: "inflado" o creacion de cada item. onBindViewHolder: indico la correlacion de cada item con el listado de variables.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUrl: String){
            val url = imageUrl.replace("http:", "https:")
            Picasso.get().load(url).into(itemView.logo_product)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout.publication, parent, false)
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

        println(publication.thumbnail)
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
            Intent(it.context, DetailPublicationActivity::class.java).apply {
                putExtra(Constants.PARAM_PUB, Gson().toJson(publication))
                it.context.startActivity(this)
            }
        }

    }
}