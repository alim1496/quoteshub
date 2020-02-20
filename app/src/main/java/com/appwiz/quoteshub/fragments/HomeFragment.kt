package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleAuthor
import com.appwiz.quoteshub.activities.SingleCategory
import com.appwiz.quoteshub.activities.SingleTag
import com.appwiz.quoteshub.adapters.*
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.models.Source
import com.appwiz.quoteshub.models.Tag
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.entity.*
import com.appwiz.quoteshub.services.Injection
import com.appwiz.quoteshub.utils.CommonUtils
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.HomeQuotesVM
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.action_buttons_bar.*
import kotlinx.android.synthetic.main.common_error_container.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception

class HomeFragment : Fragment() {
    lateinit var adapter: HomeQuotesAdapter
    lateinit var adapter5: HomeQuotesAdapter
    lateinit var adapter2 : HomeAuthorsAdapter
    lateinit var adapter3 : TagsAdapter
    lateinit var adapter4 : EventsAdapter
    lateinit var viewModel: HomeQuotesVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        viewModel.loadHomeData()
    }

    private fun setupUI() {
        adapter = HomeQuotesAdapter(viewModel.recentQuotes.value?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter5 = HomeQuotesAdapter(viewModel.featuredQuotes.value?: emptyList())
        featured_recyclerView.layoutManager = LinearLayoutManager(activity)
        featured_recyclerView.adapter = adapter5

        adapter3 = TagsAdapter(viewModel.quotes.value?.DayQuote?.data?.tags?: emptyList()) { item: Tag, position: Int ->
            val intent = Intent(context, SingleTag::class.java)
            intent.putExtra("tagID", item.id)
            intent.putExtra("tagName", item.name)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        val tagsManager = FlexboxLayoutManager(activity)
        day_tag_recycler.layoutManager = tagsManager
        tagsManager.flexDirection = FlexDirection.ROW
        tagsManager.justifyContent = JustifyContent.FLEX_START
        day_tag_recycler.adapter = adapter3

        adapter2 = HomeAuthorsAdapter(viewModel.featuredAuthors.value?: emptyList()) { author: AuthorEntity, position: Int ->
            val intent = Intent(context, SingleAuthor::class.java)
            intent.putExtra("authorID", author.id)
            intent.putExtra("authorname", author.name)
            startActivity(intent)
        }
        authors_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        authors_recyclerview.adapter = adapter2

        adapter4 = EventsAdapter(viewModel.eventsToday.value?: emptyList())
        events_recyclerview.layoutManager = LinearLayoutManager(activity)
        events_recyclerview.adapter = adapter4

        recent_see_all.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, SingleCategory::class.java)
            intent.putExtra("catName", "Recent Quotes")
            intent.putExtra("moreName", "recent=true")
            startActivity(intent)
        })

        featured_see_all.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, SingleCategory::class.java)
            intent.putExtra("catName", "Featured Quotes")
            intent.putExtra("moreName", "featured=true")
            startActivity(intent)
        })
    }

    private fun setupViewModel() {
        val db = context?.let { AppDB(it) }
        if (db != null) {
            viewModel = ViewModelProviders.of(this, BaseViewModelFactory{HomeQuotesVM(Injection.getHomeQuotesRepo(db.homeDao()))}).get(HomeQuotesVM::class.java)
        }
        viewModel.titlesHome.observe(this, renderTitles)
        viewModel.recentQuotes.observe(this, renderRecent)
        viewModel.featuredQuotes.observe(this, renderFeatured)
        viewModel.dayQuote.observe(this, renderdayQuote)
        viewModel.featuredAuthors.observe(this, renderAuthors)
        viewModel.eventsToday.observe(this, renderEvents)
        viewModel.onMessageError.observe(this, renderError)
        viewModel.isViewLoading.observe(this, renderLoader)
    }

    private val renderTitles = Observer<List<TitleEntity>> {
        if (it.size == 5) {
            featured_quotes_title.text = it[1].title
            recent_quotes_title.text = it[0].title
            quote_day_title.text = it[2].title
            featured_authors_title.text = it[3].title
            today_events_title.text = it[4].title
        }
    }

    private val renderRecent = Observer<List<HomeEntity>> {
        home_screen_container.visibility = View.VISIBLE
        adapter.updateData(it)
    }

    private val renderFeatured = Observer<List<HomeEntity>> {
        adapter5.updateData(it)
    }

    private val renderdayQuote = Observer<DayQuoteEntity> {
        try {
            val data = Quote(it.id, it.text, Source(0, ""), false, 0, ArrayList())
            val author = it.author
            day_quote_title.text = it.text
            day_quote_src.text = it.author
            val tagModels:MutableList<Tag> = ArrayList()
            for (entity in it.tags) {
                val _tag = Tag(entity.id, entity.name)
                tagModels.add(_tag)
            }
            adapter3.updateData(tagModels)
            action_favorite.setOnClickListener {
                context?.let { it1 ->
                    CommonUtils().favQuote(it1, data, author) {
                        action_favorite.setImageResource(R.drawable.ic_star_black_24dp)
                    }
                }
            }
            action_copy.setOnClickListener(View.OnClickListener {
                context?.let { it1 -> CommonUtils().copyQuote(it1, data, author) }
            })

            action_share.setOnClickListener(View.OnClickListener {
                context?.let { it1 -> CommonUtils().shareQuote(it1, data, author) }
            })
        } catch (e:Exception) {
            e.stackTrace
        }
    }

    private val renderAuthors = Observer<List<AuthorEntity>> {
        adapter2.addData(it)
    }

    private val renderEvents = Observer<List<EventEntity>> {
        adapter4.updateData(it)
    }

//    private val renderFeed = Observer<FeedModel> {
//        home_screen_container.visibility = View.VISIBLE
//
//        recent_quotes_title.text = it.RecentQuotes.title
//        if (it.RecentQuotes.data.isEmpty()) {
//            recent_quotes_container.visibility = View.GONE
//        } else {
//            adapter.updateData(it.RecentQuotes.data)
//        }
//
//        today_events_title.text = it.EventsToday.title
//        if (it.EventsToday.data.isEmpty()) {
//            today_events_container.visibility = View.GONE
//        } else {
//            adapter4.updateData(it.EventsToday.data)
//        }
//
//    }

    private val renderLoader = Observer<Boolean> {
        net_err_holder.visibility = View.GONE
        val visible = if (it) View.VISIBLE else View.GONE
        home_screen_loader.visibility = visible
    }

    private val renderError = Observer<Any> {
        net_err_holder.visibility = View.VISIBLE
        try_again_btn.setOnClickListener(View.OnClickListener {
            home_screen_loader.visibility = View.VISIBLE
            net_err_holder.visibility = View.GONE
            viewModel.loadHomeData()
        })
    }

}
