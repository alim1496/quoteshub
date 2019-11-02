package com.example.quoteshub.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R
import com.example.quoteshub.models.Quote
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.action_buttons_bar.view.*
import kotlinx.android.synthetic.main.author_quote_item.view.*
import java.lang.Exception
import androidx.core.content.ContextCompat.startActivity
import com.example.quoteshub.activities.SingleTag
import com.example.quoteshub.models.Tag


class AuthorQuotesAdapter(val context: Context, var quotes: List<Quote>) : RecyclerView.Adapter<AuthorQuotesAdapter.MyViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AuthorQuotesAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.author_quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: AuthorQuotesAdapter.MyViewHolder, position: Int) {
        val quote = quotes[position]
        holder.setData(quote)

        val tagsManager = FlexboxLayoutManager(context)
        tagsManager.flexDirection = FlexDirection.ROW
        tagsManager.justifyContent = JustifyContent.FLEX_START

        holder.itemView.author_quote_tags.apply {
            layoutManager = tagsManager
            adapter = TagsAdapterMain(context, quote.tags) { item: Tag, position: Int ->
                val intent = Intent(context, SingleTag::class.java)
                intent.putExtra("tagID", item.id)
                intent.putExtra("tagName", item.name)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this.context.startActivity(intent)
            }
            setRecycledViewPool(viewPool)
        }

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

    fun addItems(newQuotes: List<Quote>) {
        quotes += newQuotes
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(quote : Quote?) {
            val quoteText = itemView.findViewById<RecyclerView>(R.id.auth_quote_text) as TextView
            quoteText.text = quote?.title
        }
    }
}