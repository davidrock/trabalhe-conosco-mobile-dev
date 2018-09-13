package com.picpay.david.davidrockpicpay.features.creditCard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picpay.david.davidrockpicpay.R
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*

class RecyclerCardsAdapter(val items: ArrayList<CreditCard>, val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerCardsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: CreditCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position, listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardNumber = itemView.tvNumber
        val check = itemView.imgDefault
        val card = itemView.cv

        fun bind(item: CreditCard, pos: Int, listener: OnItemClickListener) {

            card.setOnClickListener { listener.onItemClick(item) }

            if (item.Default)
                check.visibility = View.VISIBLE

            cardNumber.text = item.CardNumber.toString()
        }
    }
}