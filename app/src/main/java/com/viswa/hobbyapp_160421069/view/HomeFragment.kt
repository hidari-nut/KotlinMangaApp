package com.viswa.hobbyapp_160421069.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.viswa.hobbyapp_160421069.R
import com.viswa.hobbyapp_160421069.databinding.FragmentHomeBinding
import com.viswa.hobbyapp_160421069.viewmodel.MangaListViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: MangaListViewModel
    private lateinit var binding:FragmentHomeBinding
    private val mangaListAdapter = MangaListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MangaListViewModel::class.java)

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = mangaListAdapter

        binding.recView.visibility = View.GONE
        observeViewModel()
        viewModel.refresh()

        binding.fabSetting.setOnClickListener{
            val action = HomeFragmentDirections.actionSettingFragmentH()
            Navigation.findNavController(it).navigate(action)
        }
    }
    fun observeViewModel(){
        viewModel.mangasLD.observe(viewLifecycleOwner
            , Observer {mangaListAdapter.updateMangaList(it)
                Log.d("checkloading", "OK" + it.toString())
                viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
                    if(it==true){
                        binding.recView.visibility= View.GONE
                    }else{
                        binding.recView.visibility = View.VISIBLE
                    }
            })


    })}



}