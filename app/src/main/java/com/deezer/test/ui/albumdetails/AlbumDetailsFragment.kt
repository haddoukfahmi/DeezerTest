package com.deezer.test.ui.albumdetails

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deezer.test.R
import com.deezer.test.data.model.Data
import com.deezer.test.data.model.DataTrackList
import com.deezer.test.utils.ExoplayerLaunch
import com.deezer.test.ui.MainActivity
import com.deezer.test.utils.loadPicture
import com.deezer.test.viewmodel.AlbumViewModel
import com.deezer.test.viewmodel.TrackListViewModel
import kotlinx.android.synthetic.main.fragment_album_detail.*
import kotlinx.android.synthetic.main.fragment_play_track.*
import kotlinx.android.synthetic.main.frame_album_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AlbumDetailsFragment : Fragment(R.layout.fragment_album_detail) {

    private var playWhenReady: Boolean
    private var playbackPosition: Long
    private var currentWindow: Int
    private val isBtnClicked = MutableLiveData<Boolean>()
    private val exoplayerLaunch: ExoplayerLaunch by inject()
    private val albumViewModel: AlbumViewModel by sharedViewModel()
    private val trackListViewModel: TrackListViewModel by inject()
    private val adapter = TracksListAdapter()


    companion object {
        fun newInstance() = AlbumDetailsFragment()

    }

    init {
        isBtnClicked.value = false
        playWhenReady = true
        playbackPosition = 0
        currentWindow = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (activity is MainActivity) {
            (activity as MainActivity).setSupportActionBar(toolbar)
            (activity as MainActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            val upArrow =
                ContextCompat.getDrawable(requireContext(), R.drawable.abc_ic_ab_back_material)
            val porterDuffColorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(requireContext(), R.color.colorAccent),
                PorterDuff.Mode.SRC_ATOP
            )
            upArrow!!.colorFilter = porterDuffColorFilter

            (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(upArrow)
        }

        albumViewModel.albumDetail.observe(viewLifecycleOwner, Observer {
            if (it.alternative != null) {
                trackListViewModel.getTrackList(it.alternative.id.toString())
            } else {
                trackListViewModel.getTrackList(it.id.toString())
            }
            toolbar.title = it.title
            updateUI(it)
        })

        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        trackListViewModel.trackList.observe(
            this,
            Observer { setRecyclerView(it) })

        isBtnClicked.observe(this, Observer { isClicked ->
            if (isClicked) {
                trackListViewModel.trackUrl.observe(this, Observer {
                    if (player_fragment.isVisible) {
                        releasePlayer()
                        initializePlayer(it)
                    } else {
                        player_fragment.visibility = View.VISIBLE
                        initializePlayer(it)
                    }
                })
                exoplayer_close_btn.setOnClickListener { closePlayer() }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        isBtnClicked.observe(this, Observer { isClicked ->
            if (isClicked) {
                if (!exoplayerLaunch.player!!.isPlaying) {
                    trackListViewModel.trackUrl.observe(this, Observer {
                        if (player_fragment.isVisible) {
                            releasePlayer()
                            initializePlayer(it)
                        } else {
                            player_fragment.visibility = View.VISIBLE
                            initializePlayer(it)
                        }
                    })
                    exoplayer_close_btn.setOnClickListener { closePlayer() }
                }
            }

        })

    }

    override fun onPause() {
        super.onPause()
        if (player_view != null)
            player_view.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        if (player_view != null)
            player_view.onPause()
        releasePlayer()
    }

    private fun updateUI(data: Data) {
        album_detail_img.loadPicture(data.cover_medium)
        album_detail_title.text = data.title
        album_artist_img.loadPicture(data.artist.picture_small)
        album_artist_title.text = data.artist.name
        album_title_nbr.text = getString(R.string.nb_tracks_message, data.nb_tracks)
        album_release_date.text = getString(R.string.release_date_message, data.release_date)

    }

    private fun initRecyclerView() {
        track_list!!.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(context, LinearLayout.HORIZONTAL)
        track_list.addItemDecoration(itemDecor)
        adapter.setTrackClickListener { dataTrackList ->
            trackListViewModel.setTrackUrl(dataTrackList.preview)
            isBtnClicked.value = true
        }
        track_list!!.adapter = adapter
    }

    private fun setRecyclerView(list: List<DataTrackList>) {
        adapter.setTracksList(list)
    }


    private fun initializePlayer(url: String) {
        try {
            exoplayerLaunch.initExoplayerBuilder()
            val uri = Uri.parse(url)
            val mediaSource = exoplayerLaunch.buildMediaSource(uri)

            exoplayerLaunch.player!!.playWhenReady = playWhenReady
            exoplayerLaunch.player!!.seekTo(currentWindow, playbackPosition)
            exoplayerLaunch.player!!.prepare(mediaSource!!, true, false)
            player_view.player = exoplayerLaunch.player

        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }

    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        player_view.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun releasePlayer() {
        if (exoplayerLaunch.player != null) {
            playWhenReady = exoplayerLaunch.player!!.playWhenReady
            playbackPosition = exoplayerLaunch.player!!.currentPosition
            currentWindow = exoplayerLaunch.player!!.currentWindowIndex
            exoplayerLaunch.player!!.release()
            exoplayerLaunch.player = null
        }
    }

    private fun closePlayer() {
        if (player_fragment.isVisible) {
            player_fragment.visibility = View.GONE
            releasePlayer()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                (activity as MainActivity).onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}