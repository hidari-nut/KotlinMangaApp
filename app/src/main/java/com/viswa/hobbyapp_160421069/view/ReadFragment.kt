package com.viswa.hobbyapp_160421069.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import com.viswa.hobbyapp_160421069.R
import com.viswa.hobbyapp_160421069.databinding.FragmentReadBinding
import com.viswa.hobbyapp_160421069.model.Blog
import com.viswa.hobbyapp_160421069.viewmodel.MangaDetailViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class ReadFragment : Fragment() {
    private lateinit var viewModel:MangaDetailViewModel
    private lateinit var binding: FragmentReadBinding

    private  var pageCount = 1
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadBinding.inflate(inflater, container, false)
        return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mangaId = ReadFragmentArgs.fromBundle(requireArguments()).mangaId
        viewModel = ViewModelProvider(this).get(MangaDetailViewModel::class.java)
        viewModel.fetch(mangaId)

        observeViewModel()
        binding.btnNext.setOnClickListener {
            if(currentPage+1 < pageCount){
                currentPage ++
                observeViewModel()
            }
            disabblingButtons()
        }
        binding.btnPrev.setOnClickListener {
            if(currentPage > 0){
                currentPage --
                observeViewModel()
            }
            disabblingButtons()
        }
        binding.fabSetting.setOnClickListener{
            val action = HomeFragmentDirections.actionSettingFragmentH()
            Navigation.findNavController(it).navigate(action)
        }
    }
    fun observeViewModel(){
        viewModel.mangaLD.observe(viewLifecycleOwner, Observer { manga ->
            manga?.let {
                if (manga.cont != null) {
                    Picasso.get().load(manga.picUrl).into(binding.imgBlogD)
                    binding.txtTitleD.text = manga.title
                    binding.textUserD.text = manga.writer
                } else {
                    Log.d("ReadFragment", "Manga Data: $manga")
                }
            }
        })
        viewModel.paragraphLD.observe(viewLifecycleOwner, Observer{
            Log.d("ReadParagraph", "Paragraph Data: ${it.toString()}")
            binding.txtContent.text = it[currentPage]
            pageCount = it.size

            disabblingButtons()
        })
    }

    private fun disabblingButtons(){

        if(currentPage+1 >= pageCount){
            binding.btnNext.isEnabled=false
        }
        else{
            binding.btnNext.isEnabled=true
        }
        if(currentPage <= 0){
            binding.btnPrev.isEnabled=false
        }
        else{
            binding.btnPrev.isEnabled=true
        }
    }
}