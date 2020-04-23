package com.deezer.test.ui.albumlist

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.deezer.test.R
import com.deezer.test.data.model.Data
import com.deezer.test.ui.MainActivity
import com.deezer.test.ui.albumdetails.AlbumDetailsFragment
import com.deezer.test.utils.SpaceGrid
import com.deezer.test.viewmodel.AlbumViewModel
import kotlinx.android.synthetic.main.fragment_album_list.*
import org.koin.android.ext.android.inject

class AlbumFragmentList : Fragment(R.layout.fragment_album_list) {

    private val albumViewModel: AlbumViewModel by inject()
    private val adapter = AlbumsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (activity is MainActivity) {
            (activity as MainActivity).setSupportActionBar(toolbar)
            (activity as MainActivity?)!!.supportActionBar!!.setDisplayShowHomeEnabled(true)
            (activity as MainActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)


            toolbar.title = getString(R.string.album_appbar_title)

        }
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        albumViewModel.albumList.observe(this, Observer {
            setRecyclerView(it)
        })

    }

    private fun initRecyclerView() {
        album_list!!.layoutManager = GridLayoutManager(context, 2)
        album_list!!.addItemDecoration(SpaceGrid(2, 10, true))
        adapter.setAlbumClickListener { showAlbumDetail(it) }
        album_list!!.adapter = adapter
    }

    private fun setRecyclerView(list: List<Data>) {
        adapter.setAlbum(list)
    }

    private fun showAlbumDetail(albumData: Data) {
        albumViewModel.setAlbumDetails(albumData)

        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.id_container, AlbumDetailsFragment.newInstance())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}