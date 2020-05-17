package com.appwiz.quoteshub.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.utils.CommonUtils
import java.lang.Exception

class QuotesPagedAdapter(val context: Context) : PagedListAdapter<Quote, RecyclerView.ViewHolder> (DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.common_quote_item, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quote = getItem(position)
        var color: String
        try {
            color = COLOR[position % COLOR.size]
        } catch (e:Exception) {
            color = EXTRA
        }
        quote?.let {
            holder.itemView.setBackgroundColor(Color.parseColor(color))
            (holder as QuoteViewHolder).bindView(it, context)
        }
    }

    companion object {
        private var DIFF_CALLBACK = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem.title == newItem.title && oldItem.id == newItem.id
            }

        }
        private val COLOR = arrayOf("#F7E6C5", "#72E6DA", "#FFD492", "#FFCCD5", "#FEF2DF", "#FCF18B", "#E6E4E4", "#C9FFFD", "#EED2EE", "#F4AAAB")
        private val EXTRA = "#C1E950"
    }

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(quote: Quote, context: Context) {
            val title:TextView = itemView.findViewById(R.id.quote_title)
            val source:TextView = itemView.findViewById(R.id.quote_author)
            val fbShare:ImageView = itemView.findViewById(R.id.fb_icon)
            val msgShare:ImageView = itemView.findViewById(R.id.msg_icon)
            val twtShare:ImageView = itemView.findViewById(R.id.twt_icon)
            val whapShare:ImageView = itemView.findViewById(R.id.whap_icon)
            val vbrShare:ImageView = itemView.findViewById(R.id.vbr_icon)
            val linShare:ImageView = itemView.findViewById(R.id.lin_icon)
            val copy:ImageView = itemView.findViewById(R.id.copy_icon)

            fbShare.setOnClickListener { CommonUtils().shareSocial(context, "com.facebook.katana", quote) }
            msgShare.setOnClickListener { CommonUtils().shareSocial(context, "com.facebook.orca", quote) }
            twtShare.setOnClickListener { CommonUtils().shareSocial(context, "com.twitter.android", quote) }
            whapShare.setOnClickListener { CommonUtils().shareSocial(context, "com.whatsapp", quote) }
            vbrShare.setOnClickListener { CommonUtils().shareSocial(context, "com.viber.voip", quote) }
            linShare.setOnClickListener { CommonUtils().shareSocial(context, "com.linkedin.android", quote) }
            copy.setOnClickListener { CommonUtils().copyQuote(context, quote) }

            title.text = quote.title
            try {
                source.text = quote.source.name
            } catch (e:Exception) {
                source.visibility = View.GONE
            }
        }
    }
}