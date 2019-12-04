package com.appwiz.quoteshub.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.TinyQuote
import kotlinx.android.synthetic.main.action_buttons_bar.view.*

class TagQuotesAdapter(val context: Context, var quotes: List<TinyQuote>) : RecyclerView.Adapter<TagQuotesAdapter.MyViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagQuotesAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quote_item_tagless, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: TagQuotesAdapter.MyViewHolder, position: Int) {
        val quote = quotes[position]
        holder.setData(quote)

        holder.itemView.action_copy.setOnClickListener(View.OnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?;
            val clip = ClipData.newPlainText("label", quote.title)
            clipboard!!.setPrimaryClip(clip)
            Toast.makeText(context, "copied to clipboard", Toast.LENGTH_SHORT).show()
        })

        holder.itemView.action_share.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, quote.title)
            intent.type = "text/plain"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val chooserIntent = Intent.createChooser(intent, "Share to:")
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooserIntent)
        })
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(quote : TinyQuote?) {
            val quoteText = itemView.findViewById<RecyclerView>(R.id.quote_text_tagless) as TextView
            val quoteSrc = itemView.findViewById<RecyclerView>(R.id.quote_src_tagless) as TextView
            quoteText.text = quote?.title
            quoteSrc.text = quote?.source?.name
        }
    }
}